package lotto.domain;

import java.util.Objects;

public class WinningLotto {
    public static final String ERROR_MESSAGE_INVALID_DUPLICATE_BONUS_NUMBER = "보너스 번호를 당첨 번호에 포합되지 않는 숫자로 입력해 주세요.";

    private final LottoTicket winningNumbers;
    private final LottoNumber bonusNumber;

    public WinningLotto(LottoTicket winningNumbers, LottoNumber bonusNumber) {
        validate(winningNumbers, bonusNumber);
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    private void validate(LottoTicket winningNumbers, LottoNumber bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_DUPLICATE_BONUS_NUMBER);
        }
    }

    public Ranking draw(LottoTicket lottoTicket) {
        int countOfMatch = lottoTicket.findCountOfMatch(winningNumbers);
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
        return Objects.equals(this.winningNumbers, winningLotto.winningNumbers) && Objects.equals(bonusNumber, winningLotto.bonusNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(winningNumbers, bonusNumber);
    }
}
