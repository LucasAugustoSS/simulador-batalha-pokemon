package com.github.lucasaugustoss.data.classes;

import com.github.lucasaugustoss.data.objects.templates.StatTemplate;

public class Nature {
    String name;
    String boostedStatID;
    StatTemplate boostedStat;
    String reducedStatID;
    StatTemplate reducedStat;

    public Nature(String name, String boostedStatID, String reducedStatID) {
        this.name = name;
        this.boostedStatID = boostedStatID;
        this.reducedStatID = reducedStatID;
    }

    public String getName() {
        return name;
    }

    public String getBoostedStatID() {
        return boostedStatID;
    }

    public StatTemplate getBoostedStat() {
        return boostedStat;
    }

    public void setBoostedStat(StatTemplate boostedStat) {
        this.boostedStat = boostedStat;
    }

    public String getReducedStatID() {
        return reducedStatID;
    }

    public StatTemplate getReducedStat() {
        return reducedStat;
    }

    public void setReducedStat(StatTemplate reducedStat) {
        this.reducedStat = reducedStat;
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
