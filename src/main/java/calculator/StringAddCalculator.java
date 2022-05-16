package calculator;

import util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringAddCalculator {
    private static final String SPLITTER_PATTERN_GROUP = "splitter";
    private static final String NUMBERS_PATTERN_GROUP = "numbers";
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(
            "^(?://)?(?<" + SPLITTER_PATTERN_GROUP + ">.*?)(?:\\n)?(?<" + NUMBERS_PATTERN_GROUP + ">.*)$");
    private static final String DEFAULT_SPLITTER = ",|:";
    private static final String SPLIT_APPENDER = "|";
    private static final String SPLIT_ESCAPE = "\\";

    private StringAddCalculator() {
    }

    public static int splitAndSum(String expression) {
        if (expression == null || expression.isEmpty()) {
            return 0;
        }

        String[] numbers = parsingNumbers(expression);
        return sum(numbers);
    }

    private static int sum(String[] numbers) {
        int sum = 0;
        for (String number : numbers) {
            sum += parsingNumber(number);
        }
        return sum;
    }

    private static String[] parsingNumbers(String expression) {
        String splitter = getSplitter(expression);
        String numberExpression = parsingNumberExpression(expression);
        return numberExpression.split(splitter);
    }

    private static String getSplitter(String expression) {
        String customSplitter = parsingCustomSplitter(expression);
        if (StringUtils.isEmpty(customSplitter)) {
            return DEFAULT_SPLITTER;
        }
        return DEFAULT_SPLITTER + SPLIT_APPENDER + SPLIT_ESCAPE + customSplitter;
    }

    private static String parsingCustomSplitter(String expression) {
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        if (matcher.find()) {
            return matcher.group(SPLITTER_PATTERN_GROUP);
        }
        return "";
    }

    private static String parsingNumberExpression(String expression) {
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        if (matcher.find()) {
            return matcher.group(NUMBERS_PATTERN_GROUP);
        }
        return expression;
    }

    private static int parsingNumber(String number) {
        try {
            int n = Integer.parseInt(number);
            return validateIfNegative(n);
        } catch (NumberFormatException e) {
            throw new RuntimeException();
        }
    }

    private static int validateIfNegative(int n) {
        if (n < 0) {
            throw new RuntimeException();
        }
        return n;
    }
}
