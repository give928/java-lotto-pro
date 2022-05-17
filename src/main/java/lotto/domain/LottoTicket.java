package lotto.domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LottoTicket {
    public static final int SIZE = 6;

    private static final String WINNING_NUMBERS_PATTERN = "^\\d+(,\\d|, \\d)*+$";
    private static final String WINNING_NUMBERS_SPLITTER = "\\s*,\\s*";
    private static final String ERROR_MESSAGE_INVALID_LOTTO_NUMBERS = "로또 1장은 중복되지 않는 %d개의 숫자로 구성되어야 합니다.";

    private final Set<LottoNumber> lottoNumbers;

    public LottoTicket(List<LottoNumber> lottoNumbers) {
        Set<LottoNumber> lottoNumbersSet = new HashSet<>(lottoNumbers);
        validate(lottoNumbersSet);
        this.lottoNumbers = lottoNumbersSet;
    }

    public static LottoTicket from(String text) {
        return new LottoTicket(Stream.of(toArray(text))
                                       .map(LottoNumber::from)
                                       .collect(Collectors.toList()));
    }

    private static String[] toArray(String text) {
        return Optional.ofNullable(text)
                .filter(str -> str.matches(WINNING_NUMBERS_PATTERN))
                .orElseThrow(LottoTicket::throwException)
                .split(WINNING_NUMBERS_SPLITTER);
    }

    private static IllegalArgumentException throwException() {
        throw new IllegalArgumentException(String.format(ERROR_MESSAGE_INVALID_LOTTO_NUMBERS, SIZE));
    }

    private void validate(Set<LottoNumber> lottoNumbersSet) {
        if (lottoNumbersSet.size() != SIZE) {
            throwException();
        }
    }

    public Ranking rank(LottoTicket winningLottoTicket) {
        int countOfMatch = (int) lottoNumbers.stream()
                .filter(winningLottoTicket::contains)
                .count();
        return Ranking.valueOf(countOfMatch);
    }

    private boolean contains(LottoNumber lottoNumber) {
        return lottoNumbers.contains(lottoNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LottoTicket that = (LottoTicket) o;
        return Objects.equals(lottoNumbers, that.lottoNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lottoNumbers);
    }

    @Override
    public String toString() {
        return lottoNumbers.stream()
                .sorted((o1, o2) -> o1.toInt() - o2.toInt())
                .collect(Collectors.toList())
                .toString();
    }
}
