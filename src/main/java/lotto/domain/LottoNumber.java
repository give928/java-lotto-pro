package lotto.domain;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoNumber {
    public static final int MIN = 1;
    public static final int MAX = 45;

    private static final String NULL_DEFAULT_VALUE = "0";
    private static final String ERROR_MESSAGE_INVALID_LOTTO_NUMBER = "로또 번호는 %d ~ %d 사이의 값만 가능합니다.";

    private static final Map<Integer, LottoNumber> values;

    static {
        values = IntStream.rangeClosed(MIN, MAX)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), LottoNumber::new));
    }

    private final int value;

    private LottoNumber(int value) {
        validate(value);
        this.value = value;
    }

    public static LottoNumber from(String value) {
        return from(Integer.parseInt(Optional.ofNullable(value)
                                             .orElse(NULL_DEFAULT_VALUE)));
    }

    public static LottoNumber from(int value) {
        LottoNumber lottoNumber = values.get(value);
        return Optional.ofNullable(lottoNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(ERROR_MESSAGE_INVALID_LOTTO_NUMBER, MIN, MAX)));
    }

    private static void validate(int value) {
        if (value < MIN || value > MAX) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE_INVALID_LOTTO_NUMBER, MIN, MAX));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LottoNumber that = (LottoNumber) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public int toInt() {
        return value;
    }
}
