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

    public double rateOfReturn() {
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
}
