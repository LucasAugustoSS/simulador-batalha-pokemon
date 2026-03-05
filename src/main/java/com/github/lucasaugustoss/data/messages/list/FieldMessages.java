package com.github.lucasaugustoss.data.messages.list;

import java.util.Map;

import com.github.lucasaugustoss.data.messages.Message;

public class FieldMessages {
    // weather

    public static final Message sun = new Message(
        "sun",
        Map.of(
            "start", "The sunlight turned harsh!",
            "start by ability", "(Pokemon)'s (Ability) intensified the sun's rays!",
            "end", "The harsh sunlight faded."
        )
    );

    public static final Message desolate_land = new Message(
        "desolate_land",
        Map.of(
            "start", "The sunlight turned extremely harsh!",
            "start by ability", "(Pokemon)'s (Ability) intensified the sun's rays to the extreme!",
            "fail replace", "The extremely harsh sunlight was not lessened at all!",
            "block move", "The Water-type attack evaporated in the harsh sunlight!",
            "end", "The harsh sunlight faded."
        )
    );

    public static final Message rain = new Message(
        "rain",
        Map.of(
            "start", "It started to rain!",
            "start by ability", "(Pokemon)'s (Ability) made it rain!",
            "end", "The rain stopped."
        )
    );

    public static final Message primordial_sea = new Message(
        "primordial_sea",
        Map.of(
            "start", "A heavy rain began to fall!",
            "start by ability", "(Pokemon)'s (Ability) made it rain heavily!",
            "fail replace", "There is no relief from this heavy rain!",
            "block move", "The Fire-type attack fizzled out in the heavy rain!",
            "end", "The heavy rain has lifted."
        )
    );

    public static final Message sand = new Message(
        "sand",
        Map.of(
            "start", "A sandstorm kicked up!",
            "start by ability", "(Pokemon)'s (Ability) whipped up a sandstorm!",
            "chip damage", "(Pokemon) is buffeted by the sandstorm!",
            "end", "The sandstorm subsided."
        )
    );

    public static final Message snow = new Message(
        "snow",
        Map.of(
            "start", "It started to snow!",
            "start by ability", "(Pokemon)'s (Ability) whipped up a snowstorm!",
            "end", "The snow stopped."
        )
    );

    public static final Message delta_stream = new Message(
        "delta_stream",
        Map.of(
            "start", "Mysterious strong winds are protecting Flying-type Pokémon!",
            "start by ability", "(Pokemon)'s (Ability) created mysterious strong winds protecting Flying-type Pokémon!",
            "fail replace", "The mysterious strong winds blow on regardless!",
            "end", "The mysterious strong winds have dissipated."
        )
    );


    // terrain

    public static final Message electric_terrain = new Message(
        "electric_terrain",
        Map.of(
            "start", "An electric current ran across the battlefield!",
            "start by ability", "(Pokemon)'s (Ability) supercharged the battlefield!",
            "block status", "(Pokemon) is protected by the Electric Terrain!",
            "end", "The electricity disappeared from the battlefield."
        )
    );

    public static final Message grassy_terrain = new Message(
        "grassy_terrain",
        Map.of(
            "start", "Grass grew to cover the battlefield!",
            "start by ability", "(Pokemon)'s (Ability) made the battlefield bloom!",
            "chip heal", "The Grassy Terrain restored (Pokemon)'s health!",
            "end", "The grass disappeared from the battlefield."
        )
    );

    public static final Message misty_terrain = new Message(
        "misty_terrain",
        Map.of(
            "start", "Mist swirled around the battlefield!",
            "start by ability", "(Pokemon)'s (Ability) created mist on the battlefield!",
            "block status", "(Pokemon) surrounds itself with a protective mist!",
            "end", "The mist disappeared from the battlefield."
        )
    );

    public static final Message psychic_terrain = new Message(
        "psychic_terrain",
        Map.of(
            "start", "The battlefield got weird!",
            "start by ability", "(Pokemon)'s (Ability) spread weirdness on the battlefield!",
            "block move", "(Pokemon) is protected by the Psychic Terrain!",
            "end", "The weirdness disappeared from the battlefield."
        )
    );


    // entry hazards

    public static final Message spikes = new Message(
        "spikes",
        Map.of(
            "start", "Spikes were scattered on the ground all around (Team) team!",
            "end", "The spikes disappeared from the ground around (Team) team."
        )
    );

    public static final Message stealth_rock = new Message(
        "stealth_rock",
        Map.of(
            "start", "Pointed stones float in the air around (Team) team!",
            "end", "The pointed stones disappeared from around (Team) team."
        )
    );

    public static final Message sticky_web = new Message(
        "sticky_web",
        Map.of(
            "start", "A sticky web has been laid out on the ground around (Team) team!",
            "end", "The sticky web has disappeared from the ground around (Team) team."
        )
    );


    // other

    public static final Message mist = new Message(
        "mist",
        Map.of(
            "start", "(Team) team became shrouded in mist!",
            "block stat drop", "(Pokemon) is protected by the mist!",
            "end", "The mist surrounding (Team) team dissipated."
        )
    );

    public static final Message safeguard = new Message(
        "safeguard",
        Map.of(
            "start", "(Team) team became cloaked in a mystical veil!",
            "block status", "(Pokemon) is protected by Safeguard!",
            "end", "(Team) team is no longer protected by Safeguard."
        )
    );

    public static final Message tailwind = new Message(
        "tailwind",
        Map.of(
            "start", "The Tailwind blew from behind (Team) team!",
            "end", "(Team) team's Tailwind petered out."
        )
    );

    public static final Message reflect = new Message(
        "reflect",
        Map.of(
            "start", "Reflect made (Team) team stronger against physical moves!",
            "end", "(Team) team's Reflect wore off."
        )
    );

    public static final Message light_screen = new Message(
        "light_screen",
        Map.of(
            "start", "Light Screen made (Team) team stronger against special moves!",
            "end", "(Team) team's Light Screen wore off."
        )
    );

    public static final Message aurora_veil = new Message(
        "aurora_veil",
        Map.of(
            "start", "Aurora Veil made (Team) team stronger against physical and special moves!",
            "end", "(Team) team's Aurora Veil wore off."
        )
    );

    public static final Message quick_guard = new Message(
        "quick_guard",
        Map.of(
            "start", "Quick Guard protected (Team) team!"
        )
    );

    public static final Message wide_guard = new Message(
        "wide_guard",
        Map.of(
            "start", "Wide Guard protected (Team) team!"
        )
    );

    public static final Message magic_room = new Message(
        "magic_room",
        Map.of(
            "start", "It created a bizarre area in which Pokémon's held items lose their effects!",
            "end", "Magic Room wore off, and held items' effects returned to normal!"
        )
    );

    public static final Message uproar = new Message(
        "uproar",
        Map.of(
            "start", "(Pokemon) caused an uproar!",
            "block status", "But the uproar kept (Pokemon) awake!",
            "end", "(Pokemon) calmed down!"
        )
    );

    public static final Message trick_room = new Message(
        "trick_room",
        Map.of(
            "start", "(Pokemon) twisted the dimensions!",
            "end", "The twisted dimensions returned to normal!"
        )
    );

    public static final Message gravity = new Message(
        "gravity",
        Map.of(
            "start", "Gravity intensified!",
            "block move selection", "\n!- (Pokemon) can't use (Move) because of gravity -!\n",
            "end", "Gravity returned to normal!"
        )
    );

    public static final Message wonder_room = new Message(
        "wonder_room",
        Map.of(
            "start", "It created a bizarre area in which Defense and Sp. Def stats are swapped!",
            "end", "Wonder Room wore off, and Defense and Sp. Def stats returned to normal!"
        )
    );

    public static final Message ion_deluge = new Message(
        "ion_deluge",
        Map.of(
            "start", "A deluge of ions showers the battlefield!"
        )
    );
}
