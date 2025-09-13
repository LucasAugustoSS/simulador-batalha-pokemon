package data.messages.list;

import java.util.HashMap;
import java.util.Map;

import data.messages.Message;

public class GeneralMessages {
    public static final Message modify_health;
    public static final Message stat_change;

    static {
        Map<String, String> modify_health_map = new HashMap<>();
        Map<String, String> stat_change_map = new HashMap<>();

        modify_health_map.put("heal", "(Pokemon) restored (Number) HP!");
        modify_health_map.put("heal Z", "(Pokemon) restored its HP using its Z-Power!");
        modify_health_map.put("full health", "(Pokemon)'s HP is full!");

        stat_change_map.put("+1", "(Pokemon)'s (Stat) rose!");
        stat_change_map.put("+1 ability own", "(Pokemon)'s (Ability) raised its (Stat)!");
        stat_change_map.put("+1 ability other", "(Causer)'s (Ability) raised (Pokemon)'s (Stat)!");
        stat_change_map.put("+1 Z", "(Pokemon) boosted its (Stat) using its Z-Power!");
        stat_change_map.put("+1 many Z", "(Pokemon) boosted its stats using its Z-Power!");

        stat_change_map.put("+2", "(Pokemon)'s (Stat) rose sharply!");
        stat_change_map.put("+2 ability own", "(Pokemon)'s (Ability) sharply raised its (Stat)!");
        stat_change_map.put("+2 ability other", "(Causer)'s (Ability) sharply raised (Pokemon)'s (Stat)!");
        stat_change_map.put("+2 Z", "(Pokemon) boosted its (Stat) sharply using its Z-Power!");

        stat_change_map.put("+3", "(Pokemon)'s (Stat) rose drastically!");
        stat_change_map.put("+3 ability own", "(Pokemon)'s (Ability) drastically raised its (Stat)!");
        stat_change_map.put("+3 ability other", "(Causer)'s (Ability) drastically raised (Pokemon)'s (Stat)!");
        stat_change_map.put("+3 Z", "(Pokemon) boosted its (Stat) drastically using its Z-Power!");

        stat_change_map.put("-1", "(Pokemon)'s (Stat) fell!");
        stat_change_map.put("-1 ability own", "(Pokemon)'s (Ability) lowered its (Stat)!");
        stat_change_map.put("-1 ability other", "(Causer)'s (Ability) lowered (Pokemon)'s (Stat)!");

        stat_change_map.put("-2", "(Pokemon)'s (Stat) harshly fell!");
        stat_change_map.put("-2 ability own", "(Pokemon)'s (Ability) harshly lowered its (Stat)!");
        stat_change_map.put("-2 ability other", "(Causer)'s (Ability) harshly lowered (Pokemon)'s (Stat)!");

        stat_change_map.put("-3", "(Pokemon)'s (Stat) severely fell!");
        stat_change_map.put("-3 ability own", "(Pokemon)'s (Ability) severely lowered its (Stat)!");
        stat_change_map.put("-3 ability other", "(Causer)'s (Ability) severely lowered (Pokemon)'s (Stat)!");

        stat_change_map.put("inc limit", "(Pokemon)'s (Stat) can't go any higher!");
        stat_change_map.put("dec limit", "(Pokemon)'s (Stat) can't go any lower!");
        stat_change_map.put("reset Z", "(Pokemon) returned its decreased stats to normal using its Z-Power!");

        modify_health = new Message(modify_health_map);
        stat_change = new Message(stat_change_map);
    }
}
