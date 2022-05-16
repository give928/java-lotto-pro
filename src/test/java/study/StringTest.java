package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringTest {
    @Test
    @DisplayName("요구사항 1 - \"1,2\"을 ,로 split 했을 때 1과 2로 잘 분리되는지 확인하는 학습 테스트")
    void split12() {
        String[] result = "1,2".split(",");
        assertThat(result).containsExactly("1", "2");
    }

    @Test
    @DisplayName("요구사항 1 - \"1\"을 ,로 split 했을 때 1만을 포함하는 배열이 반환되는지에 대한 학습 테스트")
    void split1() {
        String[] result = "1".split(",");
        assertThat(result).containsExactly("1");
    }

    @Test
    @DisplayName("요구사항 2 - \"(1,2)\" 값이 주어졌을 때 String의 substring() 메소드를 활용해 ()을 제거하고 \"1,2\"를 반환")
    void substring() {
        String str = "(1,2)";
        String result = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
        assertThat(result).isEqualTo("1,2");
    }

    @Test
    @DisplayName("요구사항 3 - \"abc\" 값이 주어졌을 때 String의 charAt() 메소드를 활용해 특정 위치의 문자를 가져오는 학습 테스트")
    void charAt1() {
        char c = "abc".charAt(0);
        assertThat(c).isEqualTo('a');
    }

    @Test
    @DisplayName("요구사항 3 - String의 charAt() 메소드를 활용해 특정 위치의 문자를 가져올 때 위치 값을 벗어나면 StringIndexOutOfBoundsException이 발생하는 부분에 대한 학습 테스트")
    void charAt2() {
        assertThatThrownBy(() -> "abc".charAt(3))
                .isInstanceOf(StringIndexOutOfBoundsException.class);
    }
}
