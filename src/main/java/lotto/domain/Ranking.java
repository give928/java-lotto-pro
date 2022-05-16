package lotto.domain;

import java.util.stream.Stream;

public enum Ranking {
    FIRST(6, 200_0000_000),
    SECOND(5, 1_500_000),
    THIRD(4, 50_000),
    FOURTH(3, 5_000),
    MISS(0, 0);

    private final int countOfMatch;
    private final int prizeMoney;

    Ranking(int countOfMatch, int prizeMoney) {
        this.countOfMatch = countOfMatch;
        this.prizeMoney = prizeMoney;
    }

    public static Ranking valueOf(int countOfMatch) {
        if (isMiss(countOfMatch)) {
            return MISS;
        }

        return Stream.of(values()).filter(ranking -> ranking.isMatch(countOfMatch))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d개에 해당하는 로또 순위가 없습니다.", countOfMatch)));
    }

    private static boolean isMiss(int countOfMatch) {
        return countOfMatch >= MISS.countOfMatch && countOfMatch < FOURTH.countOfMatch;
    }

    private boolean isMatch(int countOfMatch) {
        return this.countOfMatch == countOfMatch;
    }
}
