package calculator;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringAddCalculatorTest {
    @DisplayName("쉼표(,) 또는 콜론(:)을 구분자로 가지는 문자열을 전달하는 경우 구분자를 기준으로 분리한 각 숫자의 합을 반환 (예: “” => 0, \"1,2\" => 3, \"1,2,3\" => 6, “1,2:3” => 6)")
    @ParameterizedTest(name = "{displayName} message={0}")
    @CsvSource(value = {" 0", "'' 0", "'1' 1", "'1,2' 3", "'1,2,3' 6", "'1,2:3' 6"}, delimiter = ' ')
    void sumFromExpression(String value, int result) {
        // given

        // when
        int sum = StringAddCalculator.splitAndSum(value);

        // then
        assertThat(sum).isEqualTo(result);
    }

    @Test
    @DisplayName("앞의 기본 구분자(쉼표, 콜론)외에 커스텀 구분자를 지정할 수 있다. 커스텀 구분자는 문자열 앞부분의 “//”와 “\\n” 사이에 위치하는 문자를 커스텀 구분자로 사용한다. 예를 들어 “//;\\n1;2;3”과 같이 값을 입력할 경우 커스텀 구분자는 세미콜론(;)이며, 결과 값은 6이 반환되어야 한다.")
    void sumFromCustomSplitter() {
        // given
        String value = "//;\n1;2;3";

        // when
        int sum = StringAddCalculator.splitAndSum(value);

        // then
        assertThat(sum).isEqualTo(6);
    }

    @DisplayName("“//”와 “\\n” 사이에 커스텀 구분자를 입력하지 않거나, //”와 “\\n” 중 하나만 입력하는 경우에도 정상적으로 계산된다.")
    @ParameterizedTest(name = "{displayName} message={0}")
    @CsvSource(value = {"'//\n1:2:3' 6", "'//1:2:3' 6", "'\n1:2:3' 6", "'//^\n1^2^3' 6", "'//$\n1$2$3' 6", "'//.\n1.2.3' 6", "'//?\n1?2?3' 6", "'//*\n1*2*3' 6"}, delimiterString = " ")
    void sumFromCustomSplitterComplex(String value, int result) {
        // given

        // when
        int sum = StringAddCalculator.splitAndSum(value);

        // then
        assertThat(sum).isEqualTo(result);
    }

    @DisplayName("^$.?* 등의 특수문자를 여러개 입력하는 경우에도 정상적으로 계산된다.")
    @ParameterizedTest(name = "{displayName} message={0}")
    @CsvSource(value = {"'//---\n1---2---3' 6", "'//^\n1^2^3' 6", "'//$\n1$2$3' 6", "'//.\n1.2.3' 6", "'//?\n1?2?3' 6", "'//*\n1*2*3' 6"}, delimiterString = " ")
    void sumFromCustomMultipleSplitter(String value, int result) {
        // given

        // when
        int sum = StringAddCalculator.splitAndSum(value);

        // then
        assertThat(sum).isEqualTo(result);
    }

    @DisplayName("문자열 계산기에 숫자 이외의 값 또는 음수를 전달하는 경우 RuntimeException 예외를 throw한다.")
    @ParameterizedTest(name = "{displayName} message={0}")
    @CsvSource(value = {"'-1:2:3' 4", "'a:2:3' 5"}, delimiterString = " ")
    void sumFromCustomSplitterThenException(String value, int result) {
        // given

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> StringAddCalculator.splitAndSum(value);

        // then
        assertThatThrownBy(throwingCallable).isInstanceOf(RuntimeException.class);
    }

    // 이하 테스트 샘플 코드
    @Test
    void splitAndSum_null_또는_빈문자() {
        int result = StringAddCalculator.splitAndSum(null);
        assertThat(result).isZero();

        result = StringAddCalculator.splitAndSum("");
        assertThat(result).isZero();
    }

    @Test
    void splitAndSum_숫자하나() throws Exception {
        int result = StringAddCalculator.splitAndSum("1");
        assertThat(result).isEqualTo(1);
    }

    @Test
    void splitAndSum_쉼표구분자() throws Exception {
        int result = StringAddCalculator.splitAndSum("1,2");
        assertThat(result).isEqualTo(3);
    }

    @Test
    void splitAndSum_쉼표_또는_콜론_구분자() throws Exception {
        int result = StringAddCalculator.splitAndSum("1,2:3");
        assertThat(result).isEqualTo(6);
    }

    @Test
    void splitAndSum_custom_구분자() throws Exception {
        int result = StringAddCalculator.splitAndSum("//;\n1;2;3");
        assertThat(result).isEqualTo(6);
    }

    @Test
    void splitAndSum_negative() throws Exception {
        assertThatThrownBy(() -> StringAddCalculator.splitAndSum("-1,2,3"))
                .isInstanceOf(RuntimeException.class);
    }
}
