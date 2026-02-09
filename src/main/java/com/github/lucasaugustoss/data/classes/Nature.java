package com.github.lucasaugustoss.data.classes;

public class Nature {
    String id;
    String name;
    Stat boostedStat;
    Stat reducedStat;

    public Nature(String id, String name, Stat boostedStat, Stat reducedStat) {
        this.id = id;
        this.name = name;
        this.boostedStat = boostedStat;
        this.reducedStat = reducedStat;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Stat getBoostedStat() {
        return boostedStat;
    }

    public Stat getReducedStat() {
        return reducedStat;
    }

    public double multiplier(Stat stat) {
        if (boostedStat.compare(reducedStat)) {
            return 1;
        }

        if (stat.compare(boostedStat)) {
            return 1.1;
        } else if (stat.compare(reducedStat)) {
            return 0.9;
        } else {
            return 1;
        }
    }
}
