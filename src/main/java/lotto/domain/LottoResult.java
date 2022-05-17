package lotto.domain;

import lotto.domain.dto.RankingCountDto;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LottoResult {
    private final List<Ranking> rankings;

    public LottoResult(List<Ranking> rankings) {
        this.rankings = rankings;
    }

    public double calculateRateOfReturn() {
        long winningMoney = calculateWinningMoney();
        if (winningMoney == 0) {
            return 0;
        }
        int purchaseMoney = calculatePurchaseMoney();
        return (double) winningMoney / purchaseMoney;
    }

    private int calculatePurchaseMoney() {
        return Purchase.money(rankings.size());
    }

    private int calculateWinningMoney() {
        return rankings.stream()
                .mapToInt(Ranking::getWinningMoney)
                .sum();
    }

    public List<RankingCountDto> getLottoRankingResults() {
        Map<Ranking, Long> rankingMap = rankings.stream().collect(Collectors.groupingBy(r -> r, Collectors.counting()));

        return Stream.of(Ranking.values())
                .filter(ranking -> ranking != Ranking.MISS)
                .map(ranking -> new RankingCountDto(ranking, rankingMap.getOrDefault(ranking, 0L)))
                .sorted(Comparator.comparingInt(r -> r.getRanking().getCountOfMatch()))
                .collect(Collectors.toList());
    }
}
