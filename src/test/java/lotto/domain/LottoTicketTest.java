package lotto.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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

    private static List<LottoNumber> getLottoNumbers(int startNumber, int endNumber) {
        return IntStream.rangeClosed(startNumber, endNumber)
                .mapToObj(LottoNumber::of)
                .collect(Collectors.toList());
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
}
