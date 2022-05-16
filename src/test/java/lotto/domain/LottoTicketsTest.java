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
    @DisplayName("로또 여러장의 순위를 구한다.")
    void rank() {
        // given
        List<LottoTicket> values = Arrays.asList(makeLottoTicket(1, 6), makeLottoTicket(2, 7), makeLottoTicket(3, 8),
                                                 makeLottoTicket(4, 9), makeLottoTicket(5, 10));
        LottoTickets lottoTickets = new LottoTickets(values);
        LottoTicket winningLottoTicket = makeLottoTicket(1, 6);

        // when
        List<Ranking> rankings = lottoTickets.rank(winningLottoTicket);

        // then
        assertThat(rankings).containsExactly(Ranking.FIRST, Ranking.SECOND, Ranking.THIRD, Ranking.FOURTH, Ranking.MISS);
    }

    private LottoTicket makeLottoTicket(int startNumber, int endNumber) {
        List<LottoNumber> lottoNumbers = IntStream.rangeClosed(startNumber, endNumber)
                .mapToObj(LottoNumber::of)
                .collect(Collectors.toList());
        return new LottoTicket(lottoNumbers);
    }
}
