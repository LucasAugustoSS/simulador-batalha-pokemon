package com.github.lucasaugustoss.loader.factories;

import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.classes.Nature;
import com.github.lucasaugustoss.data.objects.templates.StatTemplate;
import com.github.lucasaugustoss.loader.JSONLoader;
import com.github.lucasaugustoss.loader.dtos.NatureDTO;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;

public class NatureFactory {
    private final Map<String, Nature> natureList = new HashMap<>();

    public Map<String, Nature> build(JSONLoader data) {
        createNature(data);
        return Map.copyOf(natureList);
    }

    private void createNature(JSONLoader data) {
        for (NatureDTO dto : data.getNatureData().values()) {
            Nature nature = new Nature(
                dto.name,
                dto.boostedStat,
                dto.reducedStat
            );

            natureList.put(FactoryTools.formatName(dto.name), nature);
        }
    }

    public void convertStats(Map<String, Nature> natureMap, Map<String, StatTemplate> statMap) {
        for (Nature nature : natureMap.values()) {
            nature.setBoostedStat(statMap.get(nature.getBoostedStatID()));
            nature.setReducedStat(statMap.get(nature.getReducedStatID()));
        }
    }
}
