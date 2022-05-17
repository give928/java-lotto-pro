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
        List<LottoTicket> values = Arrays.asList(makeLottoTicket(1, 6), makeLottoTicket(7, 12),
                                                 makeLottoTicket(13, 18));

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
        LottoTickets lottoTickets = new LottoTickets(lottoNumbersStrategy, purchase);

        // then
        assertThat(lottoTickets).isEqualTo(new LottoTickets(lottoNumbersStrategy, purchase));
    }

    @Test
    @DisplayName("로또 여러장의 순위를 구한다.")
    void draw() {
        // given
        List<LottoTicket> values = Arrays.asList(makeLottoTicket(1, 6), makeLottoTicket(2, 7), makeLottoTicket(3, 8),
                                                 makeLottoTicket(4, 9), makeLottoTicket(5, 10));
        LottoTickets lottoTickets = new LottoTickets(values);
        LottoTicket winningLottoTicket = makeLottoTicket(1, 6);

        // when
        LottoResult lottoResult = lottoTickets.draw(winningLottoTicket);

        // then
        assertThat(lottoResult.getLottoRankingResults()).hasSize(Ranking.values().length - 1); // MISS 제외
        assertThat(lottoResult.getLottoRankingResults()).extracting("count")
                .containsExactly(1, 1, 1, 1);
        assertThat(lottoResult.calculateRateOfReturn()).isEqualTo(
                (double) (Ranking.FIRST.getWinningMoney() + Ranking.SECOND.getWinningMoney() + Ranking.THIRD.getWinningMoney()
                        + Ranking.FOURTH.getWinningMoney() + Ranking.MISS.getWinningMoney()) / 5000);
    }

    private LottoTicket makeLottoTicket(int startNumber, int endNumber) {
        List<LottoNumber> lottoNumbers = IntStream.rangeClosed(startNumber, endNumber)
                .mapToObj(LottoNumber::from)
                .collect(Collectors.toList());
        return new LottoTicket(lottoNumbers);
    }
}
