package lotto.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AutoLottoNumbersStrategy implements LottoNumbersStrategy {
    private final int startNumber;
    private final int endNumber;
    private final int numberSize;

    public AutoLottoNumbersStrategy() {
        this(LottoNumber.MIN, LottoNumber.MAX, LottoTicket.SIZE);
    }

    private AutoLottoNumbersStrategy(int startNumber, int endNumber, int numberSize) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
        this.numberSize = numberSize;
    }

    public static AutoLottoNumbersStrategy of(int startNumber, int endNumber, int numberSize) {
        return new AutoLottoNumbersStrategy(startNumber, endNumber, numberSize);
    }

    @Override
    public List<Integer> generate() {
        List<Integer> rangeNumbers = generateRangeNumbers(startNumber, endNumber);
        Collections.shuffle(rangeNumbers);
        List<Integer> shuffleNumbers = new ArrayList<>(rangeNumbers.subList(0, numberSize));
        Collections.sort(shuffleNumbers);
        return shuffleNumbers;
    }

    private static List<Integer> generateRangeNumbers(int startNumber, int endNumber) {
        return IntStream.rangeClosed(startNumber, endNumber)
                .boxed()
                .collect(Collectors.toList());
    }
}
