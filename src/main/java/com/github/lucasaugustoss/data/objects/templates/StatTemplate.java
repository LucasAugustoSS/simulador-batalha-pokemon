package com.github.lucasaugustoss.data.objects.templates;

import com.github.lucasaugustoss.data.classes.Stat;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.data.properties.stats.StatType;

public class StatTemplate {
    private String name;
    private StatName nameShort;
    private StatType type;

    public StatTemplate(String name, StatName nameShort, StatType type) {
        this.name = name;
        this.nameShort = nameShort;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public StatName getNameShort() {
        return nameShort;
    }

    public StatType getType() {
        return type;
    }

    public boolean compare(StatTemplate other) {
        return this.nameShort == other.nameShort;
    }

    public boolean compare(Stat stat) {
        return this.nameShort == stat.getNameShort();
    }
}
