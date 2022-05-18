package lotto.domain;

import lotto.domain.dto.RankingCountDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoResultTest {
    @Test
    @DisplayName("당첨 결과를 생성하고 순위별 당첨된 로또 티켓 수를 반환한다.")
    void count() {
        // given
        List<Ranking> rankings = Arrays.asList(Ranking.SECOND, Ranking.FIFTH, Ranking.MISS, Ranking.MISS);
        LottoResult lottoResult = new LottoResult(rankings);

        // when
        List<RankingCountDto> rankingCountDtos = lottoResult.combineRankingCounts();

        // then
        assertThat(rankingCountDtos).hasSize(Ranking.values().length - 1); // MISS 제외
        assertThat(rankingCountDtos).extracting("count")
                .containsExactly(1, 0, 0, 1, 0);
    }

    @Test
    @DisplayName("당첨 결과를 생성하고 총 수익률을 구한다.")
    void rateOfReturn() {
        // given
        List<Ranking> rankings = Arrays.asList(Ranking.FIFTH, Ranking.MISS);
        LottoResult lottoResult = new LottoResult(rankings);

        // when
        double rateOfReturn = lottoResult.calculateRateOfReturn();

        // then
        assertThat(rateOfReturn).isEqualTo(2.5);
    }
}
