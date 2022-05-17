package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AutoLottoNumbersStrategyTest {
    @Test
    @DisplayName("자동 로또 번호를 생성한다.")
    void createAutoLottoNumbersStrategy() {
        // given
        int startNumber = 1;
        int endNumber = 45;
        int numberSize = 6;
        LottoNumbersStrategy lottoNumbersStrategy = new AutoLottoNumbersStrategy(startNumber, endNumber, numberSize);

        // when
        List<Integer> numbers = lottoNumbersStrategy.generate();

        // then
        assertThat(numbers).hasSize(numberSize)
                .allMatch(number -> number >= startNumber && number <= endNumber)
                .doesNotHaveDuplicates()
                .isSorted();
    }

    @Test
    @DisplayName("1 ~ 6 사이의 6개의 숫자를 생성하면 [1, 2, 3, 4, 5, 6] 이 반환된다.")
    void createAutoLottoNumbersStrategyExactly() {
        // given
        int startNumber = 1;
        int endNumber = 6;
        int numberSize = 6;
        LottoNumbersStrategy lottoNumbersStrategy = new AutoLottoNumbersStrategy(startNumber, endNumber, numberSize);

        // when
        List<Integer> numbers = lottoNumbersStrategy.generate();

        // then
        assertThat(numbers).containsExactly(1, 2, 3, 4, 5, 6);
    }
}
