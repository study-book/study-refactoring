package com.webtoons;

import com.webtoons.Invoices.Performance;

public class ComedyCalculator extends PerformanceCalculator {

    public ComedyCalculator(Performance performance) {
        super(performance);
    }

    @Override
    public int getAmount() throws Exception {
        int result = 30000;
        if (this.getPerformance().getAudience() > 20) {
            result += 10000 + 500 * (this.getPerformance().getAudience() - 20);
        }
        result += 300 * this.getPerformance().getAudience();
        return result;
    }

    @Override
    public double getVolumeCredits() {
        return super.getVolumeCredits() + Math.floor(this.getPerformance().getAudience() / 5.0);
    }
}
