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

class WinningLottoTest {
    private static LottoTicket lottoTicket(int startNumber, int endNumber, int... appendNumber) {
        List<LottoNumber> lottoNumbers = IntStream.rangeClosed(startNumber, endNumber)
                .mapToObj(LottoNumber::from)
                .collect(Collectors.toList());
        if (appendNumber != null && appendNumber.length > 0) {
            for (int number : appendNumber) {
                lottoNumbers.add(LottoNumber.from(number));
            }
        }
        return new LottoTicket(lottoNumbers);
    }

    public static Stream<Arguments> drawParameter() {
        return Stream.of(Arguments.of(lottoTicket(1, 6), Ranking.FIRST),
                         Arguments.of(lottoTicket(1, 5, 7), Ranking.SECOND),
                         Arguments.of(lottoTicket(1, 5, 45), Ranking.THIRD),
                         Arguments.of(lottoTicket(1, 4, 44, 45), Ranking.FOURTH),
                         Arguments.of(lottoTicket(1, 3, 43, 44, 45), Ranking.FIFTH),
                         Arguments.of(lottoTicket(1, 2, 42, 43, 44, 45), Ranking.MISS));
    }

    @Test
    @DisplayName("당첨 로또를 생성한다.")
    void createWinning() {
        // given
        LottoTicket winningLottoTicket = LottoTicket.from("1,2,3,4,5,6");
        LottoNumber bonusNumber = LottoNumber.from(7);

        // when
        WinningLotto winningLotto = new WinningLotto(winningLottoTicket, bonusNumber);

        // then
        assertThat(winningLotto).isEqualTo(new WinningLotto(winningLottoTicket, bonusNumber));
    }

    @Test
    @DisplayName("보너스 번호가 당첨 번호에 포함되면 IllegalArgumentException 이 발생한다.")
    void thrownByDuplicateBonusNumber() {
        // given
        LottoTicket winningLottoTicket = LottoTicket.from("1,2,3,4,5,6");
        LottoNumber bonusNumber = LottoNumber.from(6);

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new WinningLotto(winningLottoTicket, bonusNumber);

        // then
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 결과를 반환한다.")
    @ParameterizedTest(name = "{displayName} lottoTicket={0}, ranking={1}")
    @MethodSource("drawParameter")
    void draw(LottoTicket lottoTicket, Ranking expected) {
        // given
        LottoTicket winningNumbers = LottoTicket.from("1,2,3,4,5,6");
        LottoNumber bonusNumber = LottoNumber.from(7);
        WinningLotto winningLotto = new WinningLotto(winningNumbers, bonusNumber);

        // when
        Ranking actual = winningLotto.draw(lottoTicket);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
