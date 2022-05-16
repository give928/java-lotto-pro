package lotto.domain;

import java.util.List;

public class LottoResult {
    private final List<Ranking> rankings;

    public LottoResult(List<Ranking> rankings) {
        this.rankings = rankings;
    }

    public int count(Ranking ranking) {
        return (int) rankings.stream()
                .filter(r -> r == ranking)
                .count();
    }
}
