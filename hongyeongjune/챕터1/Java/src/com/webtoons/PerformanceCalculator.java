package com.webtoons;

import com.webtoons.Invoices.Performance;

public class PerformanceCalculator {
    private Performance performance;

    public PerformanceCalculator(Performance performance) {
        this.performance = performance;
    }

    public int getAmount() throws Exception {
        throw new Exception("서브클래스에서 처리하도록 설계");
    }

    public double getVolumeCredits() {
        return Math.max(this.performance.getAudience() - 30, 0);
    }

    public Performance getPerformance() {
        return performance;
    }
}
