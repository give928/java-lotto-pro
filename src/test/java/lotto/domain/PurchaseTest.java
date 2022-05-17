package lotto.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PurchaseTest {
    @DisplayName("구입 금액 숫자에 해당하는 로또 매수를 반환한다.")
    @ParameterizedTest(name = "{displayName} money={0}, count={1}")
    @CsvSource(value = {"1000 1", "1500 1", "2000 2"}, delimiterString = " ")
    void createPurchase(int money, int count) {
        // given

        // when
        Purchase purchase = new Purchase(money);

        // then
        assertThat(purchase.count()).isEqualTo(count);
    }

    @DisplayName("구입 금액 문자에 해당하는 로또 매수를 반환한다.")
    @ParameterizedTest(name = "{displayName} money={0}, count={1}")
    @CsvSource(value = {"1000 1", "1500 1", "2000 2"}, delimiterString = " ")
    void createPurchaseFromString(String money, int count) {
        // given

        // when
        Purchase purchase = Purchase.from(money);

        // then
        assertThat(purchase.count()).isEqualTo(count);
    }

    @Test
    @DisplayName("구입 금액이 1,000원 미만이면 IllegalArgumentException 이 발생한다.")
    void thrownByInvalidMoney() {
        // given
        int money = 999;

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new Purchase(money);

        // then
        assertThatThrownBy(throwingCallable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("로또 매수에 해당하는 구입 금액을 반환한다.")
    void money() {
        // given

        // when
        int money = Purchase.money(3);

        // then
        assertThat(money).isEqualTo(3_000);
    }
}
