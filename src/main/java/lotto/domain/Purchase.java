package lotto.domain;

import util.StringUtils;

import java.util.Optional;

public class Purchase {
    private static final int PRICE = 1_000;
    private static final String MONEY_PATTERN = "^\\d+$";
    private static final String ERROR_MESSAGE_INVALID_PURCHASE_MONEY = "구입 금액은 %s원 이상부터 가능합니다.";

    private final int money;

    public Purchase(int money) {
        validate(money);
        this.money = money;
    }

    public static Purchase from(String text) {
        return new Purchase(toInteger(text));
    }

    private static Integer toInteger(String text) {
        return Optional.ofNullable(text)
                .filter(str -> str.matches(MONEY_PATTERN))
                .map(Integer::parseInt)
                .orElseThrow(Purchase::throwInvalidPurchaseMoneyException);
    }

    private static IllegalArgumentException throwInvalidPurchaseMoneyException() {
        throw new IllegalArgumentException(
                String.format(ERROR_MESSAGE_INVALID_PURCHASE_MONEY, StringUtils.formatThousandsSeparators(PRICE)));
    }

    public static int calculatePurchaseMoney(int count) {
        return PRICE * count;
    }

    private void validate(int money) {
        if (money < PRICE) {
            throwInvalidPurchaseMoneyException();
        }
    }

    public int getIssueCount() {
        return money / PRICE;
    }
}
