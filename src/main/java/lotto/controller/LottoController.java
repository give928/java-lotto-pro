package lotto.controller;

import lotto.domain.*;
import lotto.view.InputView;
import lotto.view.ResultView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LottoController {
    private final LottoNumbersStrategy lottoNumbersStrategy;

    public LottoController(LottoNumbersStrategy lottoNumbersStrategy) {
        this.lottoNumbersStrategy = lottoNumbersStrategy;
    }

    public void run() {
        Purchase purchase = inputPurchaseMoney();
        List<LottoTicket> manualLottoTickets = inputManualLotto(purchase.getIssueCount());
        ResultView.printPurchaseCount(purchase.getIssueCount() - manualLottoTickets.size(), manualLottoTickets.size());

        LottoTickets lottoTickets = LottoTickets.of(lottoNumbersStrategy, purchase, manualLottoTickets);
        ResultView.printLottoTickets(lottoTickets);

        LottoTicket winningNumbers = inputWinningLottoNumbers();
        WinningLotto winningLotto = inputBonusNumber(winningNumbers);
        LottoResult lottoResult = lottoTickets.draw(winningLotto);
        ResultView.printLottoResult(lottoResult);
    }

    private Purchase inputPurchaseMoney() {
        try {
            String text = InputView.inputPurchaseMoney();
            return Purchase.from(text);
        } catch (IllegalArgumentException e) {
            InputView.error(e.getMessage());
            return inputPurchaseMoney();
        }
    }

    private List<LottoTicket> inputManualLotto(int maxCount) {
        int manualLottoCount = inputManualLottoCount(maxCount, true);
        if (manualLottoCount > 0) {
            return inputManualLottoNumbers(manualLottoCount);
        }
        return Collections.emptyList();
    }

    private int inputManualLottoCount(int maxCount, boolean first) {
        try {
            String text = InputView.inputManualLottoCount(first);
            int manualLottoCount = Integer.parseInt(text);
            if (manualLottoCount < 0 || manualLottoCount > maxCount) {
                throw new IllegalArgumentException();
            }
            return manualLottoCount;
        } catch (IllegalArgumentException e) {
            InputView.errorManualLottoCount(maxCount);
            return inputManualLottoCount(maxCount, false);
        }
    }

    private List<LottoTicket> inputManualLottoNumbers(int manualLottoCount) {
        InputView.inputManualLottoNumbers();
        List<LottoTicket> manualLottoTickets = new ArrayList<>();
        for (int i = 0; i < manualLottoCount; i++) {
            manualLottoTickets.add(inputManualLottoNumber());
        }
        return manualLottoTickets;
    }

    private LottoTicket inputManualLottoNumber() {
        try {
            String text = InputView.read();
            return LottoTicket.from(text);
        } catch (IllegalArgumentException e) {
            InputView.error(e.getMessage());
            return inputManualLottoNumber();
        }
    }

    private LottoTicket inputWinningLottoNumbers() {
        try {
            String text = InputView.inputWinningLottoNumbers();
            return LottoTicket.from(text);
        } catch (IllegalArgumentException e) {
            InputView.error(e.getMessage());
            return inputWinningLottoNumbers();
        }
    }

    private WinningLotto inputBonusNumber(LottoTicket winningNumbers) {
        try {
            String text = InputView.inputBonusNumber();
            return new WinningLotto(winningNumbers, LottoNumber.from(text));
        } catch (IllegalArgumentException e) {
            InputView.error(e.getMessage());
            return inputBonusNumber(winningNumbers);
        }
    }
}
