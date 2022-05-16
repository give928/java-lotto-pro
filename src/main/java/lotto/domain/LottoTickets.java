package lotto.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LottoTickets {
    private final List<LottoTicket> values;

    public LottoTickets(List<LottoTicket> values) {
        this.values = values;
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

    public List<Ranking> rank(LottoTicket winningLottoTicket) {
        return values.stream()
                .map(lottoTicket -> lottoTicket.rank(winningLottoTicket))
                .collect(Collectors.toList());
    }
}
