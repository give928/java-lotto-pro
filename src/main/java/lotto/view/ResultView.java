package lotto.view;

import lotto.domain.LottoResult;
import lotto.domain.LottoTicket;
import lotto.domain.LottoTickets;
import lotto.domain.dto.RankingCountDto;

import java.util.List;

public class ResultView {
    private static final String RESULT_MESSAGE_PURCHASE_LOTTO_TICKET_COUNT = "수동으로 %d장, 자동으로 %d개를 구매했습니다.";
    private static final String RESULT_MESSAGE_LOTTO_RESULT_TITLE = "당첨 통계";
    private static final String RESULT_MESSAGE_LOTTO_RESULT_LINE = "---------";
    private static final String RESULT_MESSAGE_LOTTO_RESULT_RANK = "%d개 일치 (%d원)- %d개%n";
    private static final String RESULT_MESSAGE_LOTTO_RESULT_RATE_OF_RETURN = "총 수익률은 %.2f입니다.";
    private static final String RESULT_MESSAGE_LOTTO_RESULT_YOU_LOST = "(기준이 1이기 때문에 결과적으로 손해라는 의미임)";
    private static final String RESULT_MESSAGE_EMPTY = "";

    private ResultView() {
    }

    public static void printPurchaseCount(int autoLottoCount, int manualLottoCount) {
        print(String.format(RESULT_MESSAGE_PURCHASE_LOTTO_TICKET_COUNT, manualLottoCount, autoLottoCount));
    }

    public static void printLottoTickets(LottoTickets lottoTickets) {
        for (LottoTicket lottoTicket : lottoTickets) {
            print(lottoTicket.toString());
        }
    }

    public static void printLottoResult(LottoResult lottoResult) {
        printLottoResultTitle();
        printLottoRankingCounts(lottoResult);
        printLottoRateOfReturn(lottoResult);
        printLine();
    }

    private static void printLottoResultTitle() {
        printLine();
        print(RESULT_MESSAGE_LOTTO_RESULT_TITLE);
        print(RESULT_MESSAGE_LOTTO_RESULT_LINE);
    }

    private static void printLottoRankingCounts(LottoResult lottoResult) {
        List<RankingCountDto> rankingCountDtos = lottoResult.getLottoRankingResults();
        for (RankingCountDto rankingCountDto : rankingCountDtos) {
            printf(RESULT_MESSAGE_LOTTO_RESULT_RANK, rankingCountDto.getRanking().getCountOfMatch(),
                   rankingCountDto.getRanking().getWinningMoney(), rankingCountDto.getCount());
        }
    }

    private static void printLottoRateOfReturn(LottoResult lottoResult) {
        double rateOfReturn = lottoResult.calculateRateOfReturn();
        printf(RESULT_MESSAGE_LOTTO_RESULT_RATE_OF_RETURN, rateOfReturn);
        if (rateOfReturn < 1) {
            printf(RESULT_MESSAGE_LOTTO_RESULT_YOU_LOST);
        }
    }

    public static void print(Object message) {
        System.out.println(message);
    }

    public static void printf(String message, Object... args) {
        System.out.printf(message, args);
    }

    public static void printLine() {
        print(RESULT_MESSAGE_EMPTY);
    }
}
