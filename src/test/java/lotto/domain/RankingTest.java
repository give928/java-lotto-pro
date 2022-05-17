package lotto.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RankingTest {
    public static Stream<Arguments> rankingValueOfParameter() {
        return Stream.of(Arguments.of(6, Ranking.FIRST), Arguments.of(5, Ranking.SECOND), Arguments.of(4, Ranking.THIRD),
                         Arguments.of(3, Ranking.FOURTH), Arguments.of(2, Ranking.MISS), Arguments.of(0, Ranking.MISS));
    }

    @DisplayName("일치 숫자 수에 해당하는 당첨 순위를 반환한다.")
    @ParameterizedTest(name = "{displayName} numbers={0}")
    @MethodSource("rankingValueOfParameter")
    void rankingValueOf(int countOfMatch, Ranking expected) {
        // given

        // when
        Ranking actual = Ranking.valueOf(countOfMatch);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("일치 숫자 수가 없으면 IllegalArgumentException 이 발생한다.")
    @ParameterizedTest(name = "{displayName} numbers={0}")
    @ValueSource(ints = {-1, 7})
    void thrownByInvalidCountOfMatch(int countOfMatch) {
        // given

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Ranking.valueOf(countOfMatch);

        // then
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalArgumentException.class);
    }
}
