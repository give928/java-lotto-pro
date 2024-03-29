package lotto.domain;

import java.util.stream.Stream;

public enum Ranking {
    FIRST(6, 2_000_000_000, false),
    SECOND(5, 30_000_000, true),
    THIRD(5, 1_500_000, false),
    FOURTH(4, 50_000, false),
    FIFTH(3, 5_000, false),
    MISS(0, 0, false);

    public static final String ERROR_MESSAGE_INVALID_COUNT_OF_MATCH = "%d개에 해당하는 로또 순위가 없습니다.";

    private final int countOfMatch;
    private final int winningMoney;
    private final boolean matchBonus;

    Ranking(int countOfMatch, int winningMoney, boolean matchBonus) {
        this.countOfMatch = countOfMatch;
        this.winningMoney = winningMoney;
        this.matchBonus = matchBonus;
    }

    public int getCountOfMatch() {
        return countOfMatch;
    }

    public int getWinningMoney() {
        return winningMoney;
    }

    public static Ranking valueOf(int countOfMatch, boolean matchBonus) {
        if (isMiss(countOfMatch)) {
            return MISS;
        }

        return Stream.of(values())
                .filter(ranking -> ranking.isMatch(countOfMatch, matchBonus))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(ERROR_MESSAGE_INVALID_COUNT_OF_MATCH, countOfMatch)));
    }

    private static boolean isMiss(int countOfMatch) {
        return countOfMatch >= MISS.countOfMatch && countOfMatch < FIFTH.countOfMatch;
    }

    private boolean isMatch(int countOfMatch, boolean matchBonus) {
        if (matchBonus) {
            return this.countOfMatch == countOfMatch;
        }
        return !this.matchBonus && this.countOfMatch == countOfMatch;
    }
}
