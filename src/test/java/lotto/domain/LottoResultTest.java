package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoResultTest {
    @Test
    @DisplayName("당첨 결과를 생성하고 순위별 당첨 로또 수를 반환한다.")
    void count() {
        // given
        List<Ranking> rankings = Arrays.asList(Ranking.FIRST, Ranking.FOURTH, Ranking.MISS, Ranking.MISS);
        LottoResult lottoResult = new LottoResult(rankings);

        // when
        int count1 = lottoResult.count(Ranking.FIRST);
        int count2 = lottoResult.count(Ranking.SECOND);
        int count3 = lottoResult.count(Ranking.THIRD);
        int count4 = lottoResult.count(Ranking.FOURTH);
        int countMiss = lottoResult.count(Ranking.MISS);

        // then
        assertThat(count1).isEqualTo(1);
        assertThat(count2).isZero();
        assertThat(count3).isZero();
        assertThat(count4).isEqualTo(1);
        assertThat(countMiss).isEqualTo(2);
    }

    @Test
    @DisplayName("당첨 결과를 생성하고 총 수익률을 구한다.")
    void rateOfReturn() {
        // given
        List<Ranking> rankings = Arrays.asList(Ranking.FOURTH, Ranking.MISS);
        LottoResult lottoResult = new LottoResult(rankings);

        // when
        double rateOfReturn = lottoResult.rateOfReturn();

        // then
        assertThat(rateOfReturn).isEqualTo(2.5);
    }
}
