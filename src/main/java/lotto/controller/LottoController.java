package lotto.controller;

import lotto.domain.*;
import lotto.view.InputView;
import lotto.view.ResultView;

public class LottoController {
    private final LottoNumbersStrategy lottoNumbersStrategy;

    public LottoController(LottoNumbersStrategy lottoNumbersStrategy) {
        this.lottoNumbersStrategy = lottoNumbersStrategy;
    }

    public void run() {
        Purchase purchase = inputPurchaseMoney();
        ResultView.printPurchaseCount(purchase.count());

        LottoTickets lottoTickets = new LottoTickets(lottoNumbersStrategy, purchase);
        ResultView.printLottoTickets(lottoTickets);

        LottoTicket winningLottoTicket = inputWinningLottoNumbers();
        LottoResult lottoResult = lottoTickets.draw(winningLottoTicket);
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

    private LottoTicket inputWinningLottoNumbers() {
        try {
            String text = InputView.inputWinningLottoNumbers();
            return LottoTicket.from(text);
        } catch (IllegalArgumentException e) {
            InputView.error(e.getMessage());
            return inputWinningLottoNumbers();
        }
    }
}
