package lotto.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoTickets implements Iterable<LottoTicket> {
    private final List<LottoTicket> values;

    public LottoTickets(List<LottoTicket> values) {
        this.values = values;
    }

    public LottoTickets(LottoNumbersStrategy lottoNumbersStrategy, Purchase purchase) {
        this.values = createLottoTickets(lottoNumbersStrategy, purchase.count());
    }

    private List<LottoTicket> createLottoTickets(LottoNumbersStrategy lottoNumbersStrategy, int count) {
        return IntStream.range(0, count)
                .mapToObj(index -> new LottoTicket(generateLottoNumbers(lottoNumbersStrategy)))
                .collect(Collectors.toList());
    }

    private List<LottoNumber> generateLottoNumbers(LottoNumbersStrategy lottoNumbersStrategy) {
        return lottoNumbersStrategy.generate()
                .stream()
                .map(LottoNumber::from)
                .collect(Collectors.toList());
    }

    public LottoResult draw(WinningLotto winningLotto) {
        List<Ranking> rankings = values.stream()
                .map(winningLotto::draw)
                .collect(Collectors.toList());
        return new LottoResult(rankings);
    }

    @Override
    public Iterator<LottoTicket> iterator() {
        return values.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LottoTickets that = (LottoTickets) o;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
