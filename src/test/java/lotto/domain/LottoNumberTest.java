package lotto.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoNumberTest {
    @DisplayName("1 ~ 45 사이의 로또 번호를 생성한다.")
    @ParameterizedTest(name = "{displayName} value={0}")
    @ValueSource(ints = {1, 45})
    void createLottoNumber(int value) {
        // given

        // when
        LottoNumber lottoNumber = LottoNumber.from(value);

        // then
        assertThat(lottoNumber).isEqualTo(LottoNumber.from(value));
    }

    @DisplayName("1 ~ 45 사이의 로또 번호가 아니면 IllegalArgumentException 이 발생한다.")
    @ParameterizedTest(name = "{displayName} value={0}")
    @ValueSource(ints = {0, 46})
    void thrownByInvalidLottoNumber(int value) {
        // given

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> LottoNumber.from(value);

        // then
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalArgumentException.class);
    }
}
