package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {
    @Test
    @DisplayName("매개변수 문자열이 null 또는 값이 없는 경우 true 를 반환한다.")
    void isEmpty() {
        // given
        String value1 = null;
        String value2 = "";
        String value3 = "a";

        // when
        boolean result1 = StringUtils.isEmpty(value1);
        boolean result2 = StringUtils.isEmpty(value2);
        boolean result3 = StringUtils.isEmpty(value3);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isFalse();
    }

    @Test
    @DisplayName("매개변수 숫자에 천단위 구분 기호(,)를 표시해서 반환한다.")
    void formatThousandsSeparators() {
        // given
        int value1 = 1000;
        int value2 = 1000000;

        // when
        String result1 = StringUtils.formatThousandsSeparators(value1);
        String result2 = StringUtils.formatThousandsSeparators(value2);

        // then
        assertThat(result1).isEqualTo("1,000");
        assertThat(result2).isEqualTo("1,000,000");
    }
}
