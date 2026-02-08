package com.github.lucasaugustoss.data.messages.list;

import java.util.Map;

import com.github.lucasaugustoss.data.messages.Message;

public class GeneralMessages {
    public static final Message modify_health = new Message(Map.of(
        "heal", "(Pokemon) restored (Number) HP!",
        "heal Z", "(Pokemon) restored its HP using its Z-Power!",
        "full health", "(Pokemon)'s HP is full!"
    ));

    public static final Message stat_change = new Message(Map.ofEntries(
        Map.entry("+1", "(Pokemon)'s (Stat) rose!"),
        Map.entry("+1 ability own", "(Pokemon)'s (Ability) raised its (Stat)!"),
        Map.entry("+1 ability other", "(Causer)'s (Ability) raised (Pokemon)'s (Stat)!"),
        Map.entry("+1 Z", "(Pokemon) boosted its (Stat) using its Z-Power!"),
        Map.entry("+1 many Z", "(Pokemon) boosted its stats using its Z-Power!"),

        Map.entry("+2", "(Pokemon)'s (Stat) rose sharply!"),
        Map.entry("+2 ability own", "(Pokemon)'s (Ability) sharply raised its (Stat)!"),
        Map.entry("+2 ability other", "(Causer)'s (Ability) sharply raised (Pokemon)'s (Stat)!"),
        Map.entry("+2 Z", "(Pokemon) boosted its (Stat) sharply using its Z-Power!"),

        Map.entry("+3", "(Pokemon)'s (Stat) rose drastically!"),
        Map.entry("+3 ability own", "(Pokemon)'s (Ability) drastically raised its (Stat)!"),
        Map.entry("+3 ability other", "(Causer)'s (Ability) drastically raised (Pokemon)'s (Stat)!"),
        Map.entry("+3 Z", "(Pokemon) boosted its (Stat) drastically using its Z-Power!"),

        Map.entry("-1", "(Pokemon)'s (Stat) fell!"),
        Map.entry("-1 ability own", "(Pokemon)'s (Ability) lowered its (Stat)!"),
        Map.entry("-1 ability other", "(Causer)'s (Ability) lowered (Pokemon)'s (Stat)!"),

        Map.entry("-2", "(Pokemon)'s (Stat) harshly fell!"),
        Map.entry("-2 ability own", "(Pokemon)'s (Ability) harshly lowered its (Stat)!"),
        Map.entry("-2 ability other", "(Causer)'s (Ability) harshly lowered (Pokemon)'s (Stat)!"),

        Map.entry("-3", "(Pokemon)'s (Stat) severely fell!"),
        Map.entry("-3 ability own", "(Pokemon)'s (Ability) severely lowered its (Stat)!"),
        Map.entry("-3 ability other", "(Causer)'s (Ability) severely lowered (Pokemon)'s (Stat)!"),

        Map.entry("inc limit", "(Pokemon)'s (Stat) can't go any higher!"),
        Map.entry("dec limit", "(Pokemon)'s (Stat) can't go any lower!"),
        Map.entry("reset Z", "(Pokemon) returned its decreased stats to normal using its Z-Power!")
    ));
}
