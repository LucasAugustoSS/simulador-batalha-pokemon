package com.github.lucasaugustoss.data.lists;

import java.util.ArrayList;

import com.github.lucasaugustoss.data.classes.Stat;

public class AllStats {
    public static final ArrayList<Stat> allStats = new ArrayList<>();

    static {
        allStats.add(Stat.atk);
        allStats.add(Stat.def);
        allStats.add(Stat.spa);
        allStats.add(Stat.spd);
        allStats.add(Stat.spe);
        allStats.add(Stat.acc);
        allStats.add(Stat.eva);
    }
}
