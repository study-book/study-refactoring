package com.webtoons;

import com.webtoons.Invoices.Performance;

public class TragedyCalculator extends PerformanceCalculator {

    public TragedyCalculator(Performance performance) {
        super(performance);
    }

    @Override
    public int getAmount() {
        int result = 40000;
        if (this.getPerformance().getAudience() > 30) {
            result += 1000 * (this.getPerformance().getAudience() - 30);
        }
        return result;
    }
}
