package lotto.domain;

import lotto.domain.dto.RankingCountDto;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LottoResult {
    private static final long DEFAULT_RANKING_COUNT = 0L;
    private static final long MIN_TOTAL_WINNING_MONEY = 0;
    private static final long MIN_RATE_OF_RETURN = 0;

    private final List<Ranking> rankings;

    public LottoResult(List<Ranking> rankings) {
        this.rankings = rankings;
    }

    public List<RankingCountDto> combineRankingCounts() {
        Map<Ranking, Long> rankingMap = rankings.stream()
                .collect(Collectors.groupingBy(r -> r, Collectors.counting()));

        return Stream.of(Ranking.values())
                .filter(ranking -> ranking != Ranking.MISS)
                .map(ranking -> new RankingCountDto(ranking, rankingMap.getOrDefault(ranking, DEFAULT_RANKING_COUNT)))
                .sorted(Comparator.comparingInt(r -> r.getRanking().getWinningMoney()))
                .collect(Collectors.toList());
    }

    public double calculateRateOfReturn() {
        long totalWinningMoney = totalWinningMoney();
        if (totalWinningMoney == MIN_TOTAL_WINNING_MONEY) {
            return MIN_RATE_OF_RETURN;
        }
        int purchaseMoney = calculatePurchaseMoney();
        return (double) totalWinningMoney / purchaseMoney;
    }

    private int calculatePurchaseMoney() {
        return Purchase.calculatePurchaseMoney(rankings.size());
    }

    private int totalWinningMoney() {
        return rankings.stream()
                .mapToInt(Ranking::getWinningMoney)
                .sum();
    }
}
