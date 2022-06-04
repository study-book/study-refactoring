package com.webtoons;

import java.util.Currency;
import java.util.Locale;

import com.webtoons.Invoices.Performance;
import com.webtoons.Plays.Play;

public class Statement07_다형성을활용해계산코드재구성하기 {
    private String statement(Invoices invoices, Plays plays) throws Exception {
        String result = "청구 내역 (고객명: " + invoices.getCustomer() + ")\n";

        for (Performance performance : invoices.getPerformances()) {
            result += playFor(performance, plays).getName() + ": " + usd((createPerformanceCalculator(performance, plays).getAmount()/100.0)) + " " + performance.getAudience() + "석\n";
        }

        result += "총액: " + usd(totalAmount(invoices, plays)/100.0) + "\n";
        result += "적립 포인트: " + totalVolumeCredits(invoices, plays) + "점\n";

        return result;
    }

    /**
     * 기존에 switch 문으로 나눴던 것을 클래스로 분리
     */
    public PerformanceCalculator createPerformanceCalculator(Performance performance, Plays plays) throws Exception {
        switch (playFor(performance, plays).getType()) {
            case "tragedy":
                return new TragedyCalculator(performance);
            case "comedy":
                return new ComedyCalculator(performance);
            default:
                throw new Exception("알 수 없는 장르: " + playFor(performance, plays).getType());
        }
    }

    /**
     * 기존에 반복문안에서 계산하던 총 금액을 함수로 분리
     * @return 총 금액 결과 값
     */
    public int totalAmount(Invoices invoices, Plays plays) throws Exception {
        int totalAmount = 0;
        for (Performance performance : invoices.getPerformances()) {
            totalAmount += createPerformanceCalculator(performance, plays).getAmount();
        }
        return totalAmount;
    }

    /**
     * 기존에 반복문안에서 계산하던 총 적립포인트를 함수로 분리
     * @return 총 적립 포인트 결과 값
     */
    public int totalVolumeCredits(Invoices invoices, Plays plays) {
        int totalVolumeCredits = 0;
        for (Performance performance : invoices.getPerformances()) {
            totalVolumeCredits += volumeCreditsFor(performance, plays);
        }
        return totalVolumeCredits;
    }

    /**
     * 화폐 단위를 formatting 하는 지역변수를 제거하기
     * @return 금액을 formatting 한 결과 값
     */
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

    public static void main(String[] args) throws Exception {
        Statement07_다형성을활용해계산코드재구성하기 statement = new Statement07_다형성을활용해계산코드재구성하기();
        String result = statement.statement(DataBinding.bindingToInvoices(), DataBinding.bindingToPlays());
        System.out.println(result);
    }
}
