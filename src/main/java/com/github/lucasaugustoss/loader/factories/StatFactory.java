package com.github.lucasaugustoss.loader.factories;

import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.objects.templates.StatTemplate;
import com.github.lucasaugustoss.data.properties.stats.StatName;
import com.github.lucasaugustoss.data.properties.stats.StatType;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.dtos.StatDTO;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;

public class StatFactory {
    private final Map<String, StatTemplate> statList = new HashMap<>();

    public Map<String, StatTemplate> build(JSONLoader data) {
        createStat(data);
        return Map.copyOf(statList);
    }

    private void createStat(JSONLoader data) {
        for (StatDTO dto : data.getStatData().values()) {
            StatTemplate stat = new StatTemplate(
                dto.name,
                FactoryTools.convertEnum(dto.nameShort, StatName.class),
                FactoryTools.convertEnum(dto.type, StatType.class)
            );

            statList.put(dto.nameShort, stat);
        }
    }
}
