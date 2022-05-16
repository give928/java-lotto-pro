package lotto.domain;

import util.StringUtils;

public class Purchase {
    private static final int PRICE = 1_000;
    private static final String ERROR_MESSAGE_INVALID_PURCHASE_MONEY = "구입 금액은 %s원 이상부터 가능합니다.";

    private final int money;

    public Purchase(int money) {
        validate(money);
        this.money = money;
    }

    private void validate(int money) {
        if (money < PRICE) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE_INVALID_PURCHASE_MONEY, StringUtils.formatThousandsSeparators(PRICE)));
        }
    }

    public int count() {
        return money / PRICE;
    }

    public static int money(int count) {
        return PRICE * count;
    }
}
