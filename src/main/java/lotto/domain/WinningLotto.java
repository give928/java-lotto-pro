package lotto.domain;

import java.util.Objects;

public class WinningLotto {
    private final LottoTicket winningNumbers;
    private final LottoNumber bonusNumber;

    public WinningLotto(LottoTicket winningNumbers, LottoNumber bonusNumber) {
        validate(winningNumbers, bonusNumber);
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    private void validate(LottoTicket winningNumbers, LottoNumber bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("보너스 번호를 당첨 번호에 포합되지 않는 숫자로 입력해 주세요.");
        }
    }

    public Ranking draw(LottoTicket lottoTicket) {
        int countOfMatch = lottoTicket.getCountOfMatch(winningNumbers);
        boolean matchBonus = lottoTicket.contains(bonusNumber);
        return Ranking.valueOf(countOfMatch, matchBonus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WinningLotto winningLotto = (WinningLotto) o;
        return Objects.equals(this.winningNumbers, winningLotto.winningNumbers) && Objects.equals(bonusNumber,
                                                                                                  winningLotto.bonusNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(winningNumbers, bonusNumber);
    }
}
