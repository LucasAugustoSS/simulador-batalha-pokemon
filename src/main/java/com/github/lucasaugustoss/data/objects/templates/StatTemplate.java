package com.github.lucasaugustoss.data.objects.templates;

import com.github.lucasaugustoss.data.classes.Stat;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.data.properties.stats.StatType;

public class StatTemplate extends Template {
    private String name;
    private StatName nameShort;
    private StatType type;

    public StatTemplate(
        int index, String id,
        String name, StatName nameShort, StatType type
    ) {
        super(index, id);
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
