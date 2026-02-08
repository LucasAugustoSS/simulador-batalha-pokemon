package com.github.lucasaugustoss.data.messages.list;

import java.util.Map;

import com.github.lucasaugustoss.data.messages.Message;

public class FieldMessages {
    // weather

    public static final Message sun = new Message(Map.of(
        "start", "The sunlight turned harsh!",
        "start by ability", "(Pokemon)'s (Ability) intensified the sun's rays!",
        "end", "The harsh sunlight faded."
    ));

    public static final Message desolate_land = new Message(Map.of(
        "start", "The sunlight turned extremely harsh!",
        "start by ability", "(Pokemon)'s (Ability) intensified the sun's rays to the extreme!",
        "fail replace", "The extremely harsh sunlight was not lessened at all!",
        "end", "The harsh sunlight faded."
    ));

    public static final Message rain = new Message(Map.of(
        "start", "It started to rain!",
        "start by ability", "(Pokemon)'s (Ability) made it rain!",
        "end", "The rain stopped."
    ));

    public static final Message primordial_sea = new Message(Map.of(
        "start", "A heavy rain began to fall!",
        "start by ability", "(Pokemon)'s (Ability) made it rain heavily!",
        "fail replace", "There is no relief from this heavy rain!",
        "end", "The heavy rain has lifted."
    ));

    public static final Message sand = new Message(Map.of(
        "start", "A sandstorm kicked up!",
        "start by ability", "(Pokemon)'s (Ability) whipped up a sandstorm!",
        "end", "The sandstorm subsided."
    ));

    public static final Message snow = new Message(Map.of(
        "start", "It started to snow!",
        "start by ability", "(Pokemon)'s (Ability) whipped up a snowstorm!",
        "end", "The snow stopped."
    ));

    public static final Message delta_stream = new Message(Map.of(
        "start", "Mysterious strong winds are protecting Flying-type Pokémon!",
        "start by ability", "(Pokemon)'s (Ability) created mysterious strong winds protecting Flying-type Pokémon!",
        "fail replace", "The mysterious strong winds blow on regardless!",
        "end", "The mysterious strong winds have dissipated."
    ));


    // terrain

    public static final Message electric_terrain = new Message(Map.of(
        "start", "An electric current ran across the battlefield!",
        "start by ability", "(Pokemon)'s (Ability) supercharged the battlefield!",
        "end", "The electricity disappeared from the battlefield."
    ));

    public static final Message grassy_terrain = new Message(Map.of(
        "start", "Grass grew to cover the battlefield!",
        "start by ability", "(Pokemon)'s (Ability) made the battlefield bloom!",
        "end", "The grass disappeared from the battlefield."
    ));

    public static final Message misty_terrain = new Message(Map.of(
        "start", "Mist swirled around the battlefield!",
        "start by ability", "(Pokemon)'s (Ability) created mist on the battlefield!",
        "end", "The mist disappeared from the battlefield."
    ));

    public static final Message psychic_terrain = new Message(Map.of(
        "start", "The battlefield got weird!",
        "start by ability", "(Pokemon)'s (Ability) spread weirdness on the battlefield!",
        "end", "The weirdness disappeared from the battlefield."
    ));


    // entry hazards

    public static final Message spikes = new Message(Map.of(
        "start", "Spikes were scattered on the ground all around (Team) team!",
        "end", "The spikes disappeared from the ground around (Team) team."
    ));

    public static final Message stealth_rock = new Message(Map.of(
        "start", "Pointed stones float in the air around (Team) team!",
        "end", "The pointed stones disappeared from around (Team) team."
    ));

    public static final Message sticky_web = new Message(Map.of(
        "start", "A sticky web has been laid out on the ground around (Team) team!",
        "end", "The sticky web has disappeared from the ground around (Team) team."
    ));


    // other

    public static final Message mist = new Message(Map.of(
        "start", "(Team) team became shrouded in mist!",
        "end", "The mist surrounding (Team) team dissipated."
    ));

    public static final Message safeguard = new Message(Map.of(
        "start", "(Team) team became cloaked in a mystical veil!",
        "end", "(Team) team is no longer protected by Safeguard."
    ));

    public static final Message tailwind = new Message(Map.of(
        "start", "The Tailwind blew from behind (Team) team!",
        "end", "(Team) team's Tailwind petered out."
    ));

    public static final Message reflect = new Message(Map.of(
        "start", "Reflect made (Team) team stronger against physical moves!",
        "end", "(Team) team's Reflect wore off."
    ));

    public static final Message light_screen = new Message(Map.of(
        "start", "Light Screen made (Team) team stronger against special moves!",
        "end", "(Team) team's Light Screen wore off."
    ));

    public static final Message aurora_veil = new Message(Map.of(
        "start", "Aurora Veil made (Team) team stronger against physical and special moves!",
        "end", "(Team) team's Aurora Veil wore off."
    ));

    public static final Message quick_guard = new Message(Map.of(
        "start", "Quick Guard protected (Team) team!"
    ));

    public static final Message wide_guard = new Message(Map.of(
        "start", "Wide Guard protected (Team) team!"
    ));

    public static final Message magic_room = new Message(Map.of(
        "start", "It created a bizarre area in which Pokémon's held items lose their effects!",
        "end", "Magic Room wore off, and held items' effects returned to normal!"
    ));

    public static final Message uproar = new Message(Map.of(
        "start", "(Pokemon) caused an uproar!",
        "end", "(Pokemon) calmed down!"
    ));

    public static final Message trick_room = new Message(Map.of(
        "start", "(Pokemon) twisted the dimensions!",
        "end", "The twisted dimensions returned to normal!"
    ));

    public static final Message gravity = new Message(Map.of(
        "start", "Gravity intensified!",
        "end", "Gravity returned to normal!"
    ));

    public static final Message wonder_room = new Message(Map.of(
        "start", "It created a bizarre area in which Defense and Sp. Def stats are swapped!",
        "end", "Wonder Room wore off, and Defense and Sp. Def stats returned to normal!"
    ));

    public static final Message ion_deluge = new Message(Map.of(
        "start", "A deluge of ions showers the battlefield!"
    ));
}
