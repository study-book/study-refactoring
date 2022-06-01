package com.webtoons;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.webtoons.Invoices.Performance;
import com.webtoons.Plays.Play;

public class DataBinding {
    public static Invoices bindingToInvoices() {
        return new Invoices(
            "BigCo",
            Arrays.asList(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)
            )
        );
    }

    public static Plays bindingToPlays() {
        Map<String, Play> map = new HashMap<>();
        map.put("hamlet", new Play("Hamlet", "tragedy"));
        map.put("as-like", new Play("As You Like It", "comedy"));
        map.put("othello", new Play("Othello", "tragedy"));
        return new Plays(map);
    }
}
