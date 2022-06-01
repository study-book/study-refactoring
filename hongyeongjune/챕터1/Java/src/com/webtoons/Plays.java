package com.webtoons;

import java.util.HashMap;
import java.util.Map;

public class Plays {
    private Map<String, Play> map = new HashMap<>();

    public Map<String, Play> getMap() {
        return map;
    }

    public Plays(Map<String, Play> map) {
        this.map = map;
    }

    public static class Play {
        private String name;
        private String type;

        public Play(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }
    }
}
