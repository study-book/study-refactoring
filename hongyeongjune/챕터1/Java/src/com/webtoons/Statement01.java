package com.webtoons;

import java.util.Currency;
import java.util.Locale;

import com.webtoons.Invoices.Performance;
import com.webtoons.Plays.Play;

public class Statement01 {
    private String statement(Invoices invoices, Plays plays) throws Exception {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "청구 내역 (고객명: " + invoices.getCustomer() + ")\n";
        String format = Currency.getInstance(Locale.US).getSymbol();

        for (Performance performance : invoices.getPerformances()) {
            Play play = plays.getMap().get(performance.getPlayID());
            int thisAmount = 0;

            switch (play.getType()) {
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
                    throw new Exception("알 수 없는 장르: " + play.getType());
            }
            volumeCredits += Math.max(performance.getAudience() - 30, 0);

            if ("comedy".equals(play.getType())) {
                volumeCredits += Math.floor(performance.getAudience() / 5.0);
            }

            result += play.getName() + ": " + (thisAmount/100.0) + " " + format + " " + performance.getAudience() + "석\n";
            totalAmount += thisAmount;
        }

        result += "총액: " + (totalAmount/100.0) + "\n";
        result += "적립 포인트: " + volumeCredits + "점\n";

        return result;
    }

    public static void main(String[] args) throws Exception {
        Statement01 statement01 = new Statement01();
        System.out.println(statement01.statement(DataBinding.bindingToInvoices(), DataBinding.bindingToPlays()));
    }
}
