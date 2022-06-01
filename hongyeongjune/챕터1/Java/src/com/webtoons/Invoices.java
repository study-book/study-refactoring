package com.webtoons;

import java.util.List;

public class Invoices {
    private String customer;
    private List<Performance> performances;

    public Invoices(String customer, List<Performance> performances) {
        this.customer = customer;
        this.performances = performances;
    }

    public String getCustomer() {
        return customer;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public static class Performance {
        private String playID;
        private int audience;

        public Performance(String playID, int audience) {
            this.playID = playID;
            this.audience = audience;
        }

        public String getPlayID() {
            return playID;
        }

        public int getAudience() {
            return audience;
        }
    }
}
