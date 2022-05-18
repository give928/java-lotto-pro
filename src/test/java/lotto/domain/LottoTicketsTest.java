package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class LottoTicketsTest {
    @Test
    @DisplayName("로또 여러장을 생성한다.")
    void createLottoTickets() {
        // given
        List<LottoTicket> values = Arrays.asList(lottoTicket(1, 6), lottoTicket(7, 12),
                                                 lottoTicket(13, 18));

        // when
        LottoTickets lottoTickets = new LottoTickets(values);

        // then
        assertThat(lottoTickets).isEqualTo(new LottoTickets(values));
    }

    @Test
    @DisplayName("구입 금액에 해당하는 로또 여러장을 생성한다.")
    void createLottoTicketsOfPurchase() {
        // given
        Purchase purchase = Purchase.from("3000");
        LottoNumbersStrategy lottoNumbersStrategy = () -> Arrays.asList(1, 2, 3, 4, 5, 6);

        // when
        LottoTickets lottoTickets = LottoTickets.of(lottoNumbersStrategy, purchase);

        // then
        assertThat(lottoTickets).isEqualTo(LottoTickets.of(lottoNumbersStrategy, purchase));
    }

    @Test
    @DisplayName("로또 여러장의 순위를 구한다.")
    void draw() {
        // given
        LottoTickets lottoTickets = new LottoTickets(
                Arrays.asList(lottoTicket(1, 6), lottoTicket(2, 7), lottoTicket(2, 6, 8),
                              lottoTicket(3, 8), lottoTicket(4, 9), lottoTicket(5, 10)));
        WinningLotto winningLotto = new WinningLotto(lottoTicket(1, 6), LottoNumber.from(7));

        // when
        LottoResult lottoResult = lottoTickets.draw(winningLotto);

        // then
        assertThat(lottoResult.getLottoRankingResults()).hasSize(Ranking.values().length - 1); // MISS 제외
        assertThat(lottoResult.getLottoRankingResults()).extracting("count")
                .containsExactly(1, 1, 1, 1, 1);
        assertThat(lottoResult.calculateRateOfReturn()).isEqualTo(
                (double) (Ranking.FIRST.getWinningMoney() + Ranking.SECOND.getWinningMoney() + Ranking.THIRD.getWinningMoney()
                        + Ranking.FOURTH.getWinningMoney() + Ranking.FIFTH.getWinningMoney() + Ranking.MISS.getWinningMoney()) / 6000);
    }

    private LottoTicket lottoTicket(int startNumber, int endNumber, int... appendNumber) {
        List<LottoNumber> lottoNumbers = IntStream.rangeClosed(startNumber, endNumber)
                .mapToObj(LottoNumber::from)
                .collect(Collectors.toList());
        if (appendNumber != null && appendNumber.length > 0) {
            appendNumbers(lottoNumbers, appendNumber);
        }
        return new LottoTicket(lottoNumbers);
    }

    private void appendNumbers(List<LottoNumber> lottoNumbers, int[] appendNumber) {
        for (int number : appendNumber) {
            lottoNumbers.add(LottoNumber.from(number));
        }
    }

    @Test
    @DisplayName("수동 로또를 포함해 구입 금액에 해당하는 로또 여러장을 생성한다.")
    void createLottoTicketsOfManualAndPurchase() {
        // given
        Purchase purchase = Purchase.from("3000");
        LottoNumbersStrategy lottoNumbersStrategy = () -> Arrays.asList(3, 4, 5, 6, 7, 8);
        List<LottoTicket> manualLottoTickets = Arrays.asList(lottoTicket(1, 6), lottoTicket(2, 7));

        // when
        LottoTickets lottoTickets = LottoTickets.of(lottoNumbersStrategy, purchase, manualLottoTickets);

        // then
        assertThat(lottoTickets).isEqualTo(LottoTickets.of(lottoNumbersStrategy, purchase, manualLottoTickets));
    }
}
