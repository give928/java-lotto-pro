package lotto.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class LottoTicket {
    private final Set<LottoNumber> lottoNumbers;

    public LottoTicket(List<LottoNumber> lottoNumbers) {
        Set<LottoNumber> lottoNumbersSet = new HashSet<>(lottoNumbers);
        validate(lottoNumbersSet);
        this.lottoNumbers = lottoNumbersSet;
    }

    private void validate(Set<LottoNumber> lottoNumbersSet) {
        if (lottoNumbersSet.size() != 6) {
            throw new IllegalArgumentException("로또 1장은 중복되지 않는 6개의 숫자로 구성되어야 합니다.");
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
        return lottoNumbers.toString();
    }
}
