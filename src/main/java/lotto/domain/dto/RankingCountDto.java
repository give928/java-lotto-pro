package lotto.domain.dto;

import lotto.domain.Ranking;

import java.util.Optional;

public class RankingCountDto {
    private final Ranking ranking;
    private final int count;

    public RankingCountDto(Ranking ranking, Long count) {
        this.ranking = ranking;
        this.count = Optional.ofNullable(count)
                .orElse(0L)
                .intValue();
    }

    public Ranking getRanking() {
        return ranking;
    }

    public int getCount() {
        return count;
    }
}
