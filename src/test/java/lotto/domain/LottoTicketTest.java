package lotto.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

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
                .mapToObj(LottoNumber::from)
                .collect(Collectors.toList());
        if (appendNumber != null && appendNumber.length > 0) {
            for (int number : appendNumber) {
                lottoNumbers.add(LottoNumber.from(number));
            }
        }
        return lottoNumbers;
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

    @DisplayName("숫자 6개의 문자열로 로또 티켓을 생성한다.")
    @ParameterizedTest(name = "{displayName} numbers={0}")
    @ValueSource(strings = {"1,2,3,4,5,6", "1, 2, 3, 4, 5, 6"})
    void createLottoTicket(String value) {
        // given

        // when
        LottoTicket lottoTicket = LottoTicket.from(value);

        // then
        assertThat(lottoTicket).isEqualTo(LottoTicket.from(value));
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
        lottoNumbers.add(LottoNumber.from(5));

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new LottoTicket(lottoNumbers);

        // then
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 숫자 6개에 특정 숫자 포함 여부를 확인한다.")
    @ParameterizedTest(name = "{displayName} number={0}, contains={1}")
    @CsvSource(value = {"1,true", "7,false"}, delimiterString = ",")
    void contains(int number, boolean expected) {
        // given
        List<LottoNumber> lottoNumbers = getLottoNumbers(1, 6);
        LottoTicket lottoTicket = new LottoTicket(lottoNumbers);

        // when
        boolean actual = lottoTicket.contains(LottoNumber.from(number));

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
