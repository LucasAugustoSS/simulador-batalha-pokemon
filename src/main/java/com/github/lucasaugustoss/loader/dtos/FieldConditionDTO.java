package com.github.lucasaugustoss.loader.dtos;

import java.util.Map;

public class FieldConditionDTO {
    public int index;
    public String id;
    public String name;
    public String type;
    public FieldConditionEffectDTO[] effect;
    public String messages;
    public Map<String, Integer> defaultParams;
}
