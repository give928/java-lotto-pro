package lotto.view;

import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    private static final String EMPTY = "";
    private static final String INPUT_MESSAGE_BUY_MONEY = "구입금액을 입력해 주세요.";
    private static final String INPUT_MESSAGE_WINNING_NUMBERS = "지난 주 당첨 번호를 입력해 주세요.";
    private static final String INPUT_MESSAGE_BONUS_NUMBERS = "보너스 볼을 입력해 주세요.";

    private InputView() {
    }

    public static String inputPurchaseMoney() {
        return input(INPUT_MESSAGE_BUY_MONEY);
    }

    public static String inputWinningLottoNumbers() {
        print(EMPTY);
        return input(INPUT_MESSAGE_WINNING_NUMBERS);
    }

    public static String inputBonusNumber() {
        return input(INPUT_MESSAGE_BONUS_NUMBERS);
    }

    private static String input(String message) {
        print(message);
        return scanner.nextLine();
    }

    public static void error(String message) {
        print(message);
    }

    private static void print(String message) {
        System.out.println(message);
    }
}
