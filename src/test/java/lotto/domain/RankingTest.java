package lotto.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RankingTest {
    public static Stream<Arguments> rankingValueOfParameter() {
        return Stream.of(Arguments.of(6, false, Ranking.FIRST), Arguments.of(5, true, Ranking.SECOND),
                         Arguments.of(5, false, Ranking.THIRD), Arguments.of(4, false, Ranking.FOURTH),
                         Arguments.of(3, false, Ranking.FIFTH), Arguments.of(2, false, Ranking.MISS),
                         Arguments.of(0, false, Ranking.MISS));
    }

    @DisplayName("일치 숫자 수에 해당하는 당첨 순위를 반환한다.")
    @ParameterizedTest(name = "{displayName} countOfMatch={0}, matchBonus={1}, ranking={2}")
    @MethodSource("rankingValueOfParameter")
    void rankingValueOf(int countOfMatch, boolean matchBonus, Ranking expected) {
        // given
        System.out.println("countOfMatch = " + countOfMatch);
        System.out.println("matchBonus = " + matchBonus);
        System.out.println("expected = " + expected);

        // when
        Ranking actual = Ranking.valueOf(countOfMatch, matchBonus);
        System.out.println("actual = " + actual);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("일치 숫자 수가 없으면 IllegalArgumentException 이 발생한다.")
    @ParameterizedTest(name = "{displayName} countOfMatch={0}")
    @ValueSource(ints = {-1, 7})
    void thrownByInvalidCountOfMatch(int countOfMatch) {
        // given

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Ranking.valueOf(countOfMatch, false);

        // then
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalArgumentException.class);
    }
}
