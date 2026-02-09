package com.github.lucasaugustoss.loader.factories;

import java.util.HashMap;
import java.util.Map;

import com.github.lucasaugustoss.data.classes.Nature;
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
                dto.id,
                dto.name,
                FactoryTools.convertStat(dto.boostedStat),
                FactoryTools.convertStat(dto.reducedStat)
            );

            natureList.put(dto.id, nature);
        }
    }
}
