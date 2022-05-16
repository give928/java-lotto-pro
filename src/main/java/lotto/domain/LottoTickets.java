package lotto.domain;

import java.util.List;
import java.util.Objects;

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
}
