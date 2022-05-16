package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class LottoTicketsTest {
    @Test
    @DisplayName("로또 여러장 생성")
    void createLottoTickets() {
        // given
        List<LottoNumber> lottoNumbers = IntStream.rangeClosed(1, 6).mapToObj(LottoNumber::of).collect(
                Collectors.toList());
        List<LottoTicket> values = IntStream.rangeClosed(1, 3).mapToObj(
                value -> new LottoTicket(lottoNumbers)).collect(Collectors.toList());

        // when
        LottoTickets lottoTickets = new LottoTickets(values);

        // then
        assertThat(lottoTickets).isEqualTo(new LottoTickets(values));
    }
}
