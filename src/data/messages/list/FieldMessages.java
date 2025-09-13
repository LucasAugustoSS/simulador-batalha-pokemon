package data.messages.list;

import java.util.HashMap;
import java.util.Map;

import data.messages.Message;

public class FieldMessages {
    // weather

    public static final Message sun;
    public static final Message desolate_land;
    public static final Message rain;
    public static final Message primordial_sea;
    public static final Message sand;
    public static final Message snow;
    public static final Message delta_stream;

    static {
        Map<String, String> sun_map = new HashMap<>();
        Map<String, String> desolate_land_map = new HashMap<>();
        Map<String, String> rain_map = new HashMap<>();
        Map<String, String> primordial_sea_map = new HashMap<>();
        Map<String, String> sand_map = new HashMap<>();
        Map<String, String> snow_map = new HashMap<>();
        Map<String, String> delta_stream_map = new HashMap<>();

        sun_map.put("start", "The sunlight turned harsh!");
        sun_map.put("start by ability", "(Pokemon)'s (Ability) intensified the sun's rays!");
        sun_map.put("end", "The harsh sunlight faded.");

        desolate_land_map.put("start", "The sunlight turned extremely harsh!");
        desolate_land_map.put("start by ability", "(Pokemon)'s (Ability) intensified the sun's rays to the extreme!");
        desolate_land_map.put("fail replace", "The extremely harsh sunlight was not lessened at all!");
        desolate_land_map.put("end", "The harsh sunlight faded.");

        rain_map.put("start", "It started to rain!");
        rain_map.put("start by ability", "(Pokemon)'s (Ability) made it rain!");
        rain_map.put("end", "The rain stopped.");

        primordial_sea_map.put("start", "A heavy rain began to fall!");
        primordial_sea_map.put("start by ability", "(Pokemon)'s (Ability) made it rain heavily!");
        primordial_sea_map.put("fail replace", "There is no relief from this heavy rain!");
        primordial_sea_map.put("end", "The heavy rain has lifted.");

        sand_map.put("start", "A sandstorm kicked up!");
        sand_map.put("start by ability", "(Pokemon)'s (Ability) whipped up a sandstorm!");
        sand_map.put("end", "The sandstorm subsided.");

        snow_map.put("start", "It started to snow!");
        snow_map.put("start by ability", "(Pokemon)'s (Ability) whipped up a snowstorm!");
        snow_map.put("end", "The snow stopped.");

        delta_stream_map.put("start", "Mysterious strong winds are protecting Flying-type Pokémon!");
        delta_stream_map.put("start by ability", "(Pokemon)'s (Ability) created mysterious strong winds protecting Flying-type Pokémon!");
        delta_stream_map.put("fail replace", "The mysterious strong winds blow on regardless!");
        delta_stream_map.put("end", "The mysterious strong winds have dissipated.");

        sun = new Message(sun_map);
        desolate_land = new Message(desolate_land_map);
        rain = new Message(rain_map);
        primordial_sea = new Message(primordial_sea_map);
        sand = new Message(sand_map);
        snow = new Message(snow_map);
        delta_stream = new Message(delta_stream_map);
    }


    // terrain

    public static final Message electric_terrain;
    public static final Message grassy_terrain;
    public static final Message misty_terrain;
    public static final Message psychic_terrain;

    static {
        Map<String, String> electric_terrain_map = new HashMap<>();
        Map<String, String> grassy_terrain_map = new HashMap<>();
        Map<String, String> misty_terrain_map = new HashMap<>();
        Map<String, String> psychic_terrain_map = new HashMap<>();

        electric_terrain_map.put("start", "An electric current ran across the battlefield!");
        electric_terrain_map.put("start by ability", "(Pokemon)'s (Ability) supercharged the battlefield!");
        electric_terrain_map.put("end", "The electricity disappeared from the battlefield.");

        grassy_terrain_map.put("start", "Grass grew to cover the battlefield!");
        grassy_terrain_map.put("start by ability", "(Pokemon)'s (Ability) made the battlefield bloom!");
        grassy_terrain_map.put("end", "The grass disappeared from the battlefield.");

        misty_terrain_map.put("start", "Mist swirled around the battlefield!");
        misty_terrain_map.put("start by ability", "(Pokemon)'s (Ability) created mist on the battlefield!");
        misty_terrain_map.put("end", "The mist disappeared from the battlefield.");

        psychic_terrain_map.put("start", "The battlefield got weird!");
        psychic_terrain_map.put("start by ability", "(Pokemon)'s (Ability) spread weirdness on the battlefield!");
        psychic_terrain_map.put("end", "The weirdness disappeared from the battlefield.");

        electric_terrain = new Message(electric_terrain_map);
        grassy_terrain = new Message(grassy_terrain_map);
        misty_terrain = new Message(misty_terrain_map);
        psychic_terrain = new Message(psychic_terrain_map);
    }


    // entry hazards

    public static final Message spikes;
    public static final Message stealth_rock;
    public static final Message sticky_web;

    static {
        Map<String, String> spikes_map = new HashMap<>();
        Map<String, String> stealth_rock_map = new HashMap<>();
        Map<String, String> sticky_web_map = new HashMap<>();

        spikes_map.put("start", "Spikes were scattered on the ground all around (Team) team!");
        spikes_map.put("end", "The spikes disappeared from the ground around (Team) team.");

        stealth_rock_map.put("start", "Pointed stones float in the air around (Team) team!");
        stealth_rock_map.put("end", "The pointed stones disappeared from around (Team) team.");

        sticky_web_map.put("start", "A sticky web has been laid out on the ground around (Team) team!");
        sticky_web_map.put("end", "The sticky web has disappeared from the ground around (Team) team.");

        spikes = new Message(spikes_map);
        stealth_rock = new Message(stealth_rock_map);
        sticky_web = new Message(sticky_web_map);
    }


    // other

    public static final Message mist;
    public static final Message safeguard;
    public static final Message tailwind;
    public static final Message reflect;
    public static final Message light_screen;
    public static final Message aurora_veil;
    public static final Message quick_guard;
    public static final Message wide_guard;
    public static final Message magic_room;
    public static final Message uproar;
    public static final Message trick_room;
    public static final Message gravity;
    public static final Message wonder_room;
    public static final Message ion_deluge;

    static {
        Map<String, String> mist_map = new HashMap<>();
        Map<String, String> safeguard_map = new HashMap<>();
        Map<String, String> tailwind_map = new HashMap<>();
        Map<String, String> reflect_map = new HashMap<>();
        Map<String, String> light_screen_map = new HashMap<>();
        Map<String, String> aurora_veil_map = new HashMap<>();
        Map<String, String> quick_guard_map = new HashMap<>();
        Map<String, String> wide_guard_map = new HashMap<>();
        Map<String, String> magic_room_map = new HashMap<>();
        Map<String, String> uproar_map = new HashMap<>();
        Map<String, String> trick_room_map = new HashMap<>();
        Map<String, String> gravity_map = new HashMap<>();
        Map<String, String> wonder_room_map = new HashMap<>();
        Map<String, String> ion_deluge_map = new HashMap<>();

        mist_map.put("start", "(Team) team became shrouded in mist!");
        mist_map.put("end", "The mist surrounding (Team) team dissipated.");

        safeguard_map.put("start", "(Team) team became cloaked in a mystical veil!");
        safeguard_map.put("end", "(Team) team is no longer protected by Safeguard.");

        tailwind_map.put("start", "The Tailwind blew from behind (Team) team!");
        tailwind_map.put("end", "(Team) team's Tailwind petered out.");

        reflect_map.put("start", "Reflect made (Team) team stronger against physical moves!");
        reflect_map.put("end", "(Team) team's Reflect wore off.");

        light_screen_map.put("start", "Light Screen made (Team) team stronger against special moves!");
        light_screen_map.put("end", "(Team) team's Light Screen wore off.");

        aurora_veil_map.put("start", "Aurora Veil made (Team) team stronger against physical and special moves!");
        aurora_veil_map.put("end", "(Team) team's Aurora Veil wore off.");

        quick_guard_map.put("start", "Quick Guard protected (Team) team!");

        wide_guard_map.put("start", "Wide Guard protected (Team) team!");

        magic_room_map.put("start", "It created a bizarre area in which Pokémon's held items lose their effects!");
        magic_room_map.put("end", "Magic Room wore off, and held items' effects returned to normal!");

        uproar_map.put("start", "(Pokemon) caused an uproar!");
        uproar_map.put("end", "(Pokemon) calmed down!");

        trick_room_map.put("start", "(Pokemon) twisted the dimensions!");
        trick_room_map.put("end", "The twisted dimensions returned to normal!");

        gravity_map.put("start", "Gravity intensified!");
        gravity_map.put("end", "Gravity returned to normal!");

        wonder_room_map.put("start", "It created a bizarre area in which Defense and Sp. Def stats are swapped!");
        wonder_room_map.put("end", "Wonder Room wore off, and Defense and Sp. Def stats returned to normal!");

        ion_deluge_map.put("start", "A deluge of ions showers the battlefield!");

        mist = new Message(mist_map);
        safeguard = new Message(safeguard_map);
        tailwind = new Message(tailwind_map);
        reflect = new Message(reflect_map);
        light_screen = new Message(light_screen_map);
        aurora_veil = new Message(aurora_veil_map);
        quick_guard = new Message(quick_guard_map);
        wide_guard = new Message(wide_guard_map);
        magic_room = new Message(magic_room_map);
        uproar = new Message(uproar_map);
        trick_room = new Message(trick_room_map);
        gravity = new Message(gravity_map);
        wonder_room = new Message(wonder_room_map);
        ion_deluge = new Message(ion_deluge_map);
    }
}
