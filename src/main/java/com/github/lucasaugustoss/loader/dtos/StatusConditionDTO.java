package com.github.lucasaugustoss.loader.dtos;

import java.util.Map;

public class StatusConditionDTO {
    public int index;
    public String id;
    public String name;
    public boolean volatileCondition;
    public String similarCondition;
    public StatusConditionEffectDTO[] effect;
    public boolean stackable;
    public String messages;
    public Map<String, Object> defaultParams;
}
