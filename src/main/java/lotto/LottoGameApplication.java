package lotto;

import lotto.controller.LottoController;
import lotto.domain.AutoLottoNumbersStrategy;
import lotto.domain.LottoNumbersStrategy;

public class LottoGameApplication {
    public static void main(String[] args) {
        LottoNumbersStrategy lottoNumbersStrategy = new AutoLottoNumbersStrategy();

        LottoController lottoController = new LottoController(lottoNumbersStrategy);
        lottoController.run();
    }
}
