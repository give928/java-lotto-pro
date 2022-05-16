package lotto.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTicketTest {
    public static Stream<List<LottoNumber>> thrownByInvalidSizeLottoNumbersParameter() {
        return Stream.of(getLottoNumbers(1, 5), getLottoNumbers(1, 7));
    }

    private static List<LottoNumber> getLottoNumbers(int startNumber, int endNumber, int... appendNumber) {
        List<LottoNumber> lottoNumbers = IntStream.rangeClosed(startNumber, endNumber)
                .mapToObj(LottoNumber::of)
                .collect(Collectors.toList());
        if (appendNumber != null && appendNumber.length > 0) {
            for (int number : appendNumber) {
                lottoNumbers.add(LottoNumber.of(number));
            }
        }
        return lottoNumbers;
    }

    public static Stream<Arguments> rankParameter() {
        return Stream.of(Arguments.of(new LottoTicket(getLottoNumbers(1, 6)), Ranking.FIRST),
                         Arguments.of(new LottoTicket(getLottoNumbers(1, 5, 45)), Ranking.SECOND),
                         Arguments.of(new LottoTicket(getLottoNumbers(1, 4, 44, 45)), Ranking.THIRD),
                         Arguments.of(new LottoTicket(getLottoNumbers(1, 3, 43, 44, 45)), Ranking.FOURTH),
                         Arguments.of(new LottoTicket(getLottoNumbers(1, 2, 42, 43, 44, 45)), Ranking.MISS));
    }

    @Test
    @DisplayName("로또 숫자 6개로 로또 티켓을 생성한다.")
    void createLottoTicket() {
        // given
        List<LottoNumber> lottoNumbers = getLottoNumbers(1, 6);

        // when
        LottoTicket lottoTicket = new LottoTicket(lottoNumbers);

        // then
        assertThat(lottoTicket).isEqualTo(new LottoTicket(lottoNumbers));
    }

    @DisplayName("로또 숫자가 6개가 아니면 IllegalArgumentException 이 발생한다.")
    @ParameterizedTest(name = "{displayName} numbers={0}")
    @MethodSource("thrownByInvalidSizeLottoNumbersParameter")
    void thrownByInvalidSizeLottoNumbers(List<LottoNumber> lottoNumbers) {
        // given

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new LottoTicket(lottoNumbers);

        // then
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("로또 숫자가 중복되면 IllegalArgumentException 이 발생한다.")
    void thrownByInvalidSizeLottoNumbers() {
        // given
        List<LottoNumber> lottoNumbers = getLottoNumbers(1, 5);
        lottoNumbers.add(LottoNumber.of(5));

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new LottoTicket(lottoNumbers);

        // then
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호에 해당하는 순위를 반환한다.")
    @ParameterizedTest(name = "{displayName} numbers={0}, ranking={1}")
    @MethodSource("rankParameter")
    void rank(LottoTicket winningLottoTicket, Ranking expected) {
        // given
        List<LottoNumber> lottoNumbers = getLottoNumbers(1, 6);
        LottoTicket lottoTicket = new LottoTicket(lottoNumbers);

        // when
        Ranking actual = lottoTicket.rank(winningLottoTicket);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
