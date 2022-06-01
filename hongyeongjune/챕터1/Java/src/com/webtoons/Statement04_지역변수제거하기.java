package com.webtoons;

import java.util.Currency;
import java.util.Locale;

import com.webtoons.Invoices.Performance;
import com.webtoons.Plays.Play;

public class Statement04_지역변수제거하기 {
    private String statement(Invoices invoices, Plays plays) throws Exception {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "청구 내역 (고객명: " + invoices.getCustomer() + ")\n";

        for (Performance performance : invoices.getPerformances()) {
            volumeCredits += volumeCreditsFor(performance, plays);

            result += playFor(performance, plays).getName() + ": " + usd((amountFor(performance, plays)/100.0)) + " " + performance.getAudience() + "석\n";
            totalAmount += amountFor(performance, plays);
        }

        result += "총액: " + usd(totalAmount/100.0) + "\n";
        result += "적립 포인트: " + volumeCredits + "점\n";

        return result;
    }

    public String usd(Double price) {
        return price + Currency.getInstance(Locale.US).getSymbol();
    }
    /**
     * volumeCredits 지역변수 제거하기
     * @return volumeCredits 지역변수 계산한 값
     */
    private int volumeCreditsFor(Performance performance, Plays plays) {
        int volumeCredits = 0;
        volumeCredits += Math.max(performance.getAudience() - 30, 0);

        if ("comedy".equals(playFor(performance, plays).getType())) {
            volumeCredits += Math.floor(performance.getAudience() / 5.0);
        }

        return volumeCredits;
    }
    /**
     * play 변수제거하기
     * @return Play 변수 값
     */
    private Play playFor(Performance performance, Plays plays) {
        return plays.getMap().get(performance.getPlayID());
    }

    /**
     * 먼저 전체 동작을 각각의 부분으로 나눌 수 있는 지점을 찾는다.
     * 코드 조각을 별도의 함수로 추출하는 방식
     * @return : 한 번의 공연 요금을 계산한 결과
     */
    private int amountFor(Performance performance, Plays plays) throws Exception {
        int thisAmount = 0;
        switch (playFor(performance, plays).getType()) {
            case "tragedy":
                thisAmount = 40000;
                if (performance.getAudience() > 30) {
                    thisAmount += 1000 * (performance.getAudience() - 30);
                }
                break;
            case "comedy":
                thisAmount = 30000;
                if (performance.getAudience() > 20) {
                    thisAmount += 10000 + 500 * (performance.getAudience() - 20);
                }
                thisAmount += 300 * performance.getAudience();
                break;
            default:
                throw new Exception("알 수 없는 장르: " + playFor(performance, plays).getType());
        }
        return thisAmount;
    }

    public static void main(String[] args) throws Exception {
        Statement04_지역변수제거하기 statement = new Statement04_지역변수제거하기();
        String result = statement.statement(DataBinding.bindingToInvoices(), DataBinding.bindingToPlays());
        System.out.println(result);
    }
}
