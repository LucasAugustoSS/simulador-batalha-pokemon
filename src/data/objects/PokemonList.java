package data.objects;

import data.classes.Ability;
import data.classes.Item;
import data.classes.Move;
import data.classes.Pokemon;

public class PokemonList {
    /* Geração 1 */

    public static final Pokemon bulbasaur = new Pokemon(
        "Bulbasaur",
        1,
        TypeList.grass, TypeList.poison,
        new double[] {87.5, 12.5},
        6.9,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.chlorophyll
        },
        new Move[] {
            MoveList.growl,
            MoveList.growth,
            MoveList.leech_seed,
            MoveList.poison_powder,
            MoveList.power_whip,
            MoveList.razor_leaf,
            MoveList.seed_bomb,
            MoveList.sleep_powder,
            MoveList.solar_beam,
            MoveList.sweet_scent,
            MoveList.synthesis,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.vine_whip,
            MoveList.worry_seed
        },
        45, 49, 49, 65, 65, 45
    );

    public static final Pokemon ivysaur = new Pokemon(
        "Ivysaur",
        1,
        TypeList.grass, TypeList.poison,
        new double[] {87.5, 12.5},
        13,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.chlorophyll
        },
        new Move[] {
            MoveList.growl,
            MoveList.growth,
            MoveList.leech_seed,
            MoveList.poison_powder,
            MoveList.power_whip,
            MoveList.razor_leaf,
            MoveList.seed_bomb,
            MoveList.sleep_powder,
            MoveList.solar_beam,
            MoveList.sweet_scent,
            MoveList.synthesis,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.vine_whip,
            MoveList.worry_seed
        },
        60, 62, 63, 80, 80, 60
    );

    public static final Pokemon venusaur = new Pokemon(
        "Venusaur",
        1,
        TypeList.grass, TypeList.poison,
        new double[] {87.5, 12.5},
        100,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.chlorophyll
        },
        new Move[] {
            MoveList.growl,
            MoveList.growth,
            MoveList.leech_seed,
            MoveList.petal_blizzard,
            MoveList.petal_dance,
            MoveList.poison_powder,
            MoveList.power_whip,
            MoveList.razor_leaf,
            MoveList.seed_bomb,
            MoveList.sleep_powder,
            MoveList.solar_beam,
            MoveList.sweet_scent,
            MoveList.synthesis,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.vine_whip,
            MoveList.worry_seed
        },
        80, 82, 83, 100, 100, 80
    );

    public static final Pokemon venusaur_mega = new Pokemon( // Mega Evolução
        "Venusaur",
        "Mega",
        false,
        1,
        TypeList.grass, TypeList.poison,
        new double[] {87.5, 12.5},
        155.5,
        new Ability[] {
            AbilityList.thick_fat
        },
        new Move[] {
            MoveList.growl,
            MoveList.growth,
            MoveList.leech_seed,
            MoveList.petal_blizzard,
            MoveList.petal_dance,
            MoveList.poison_powder,
            MoveList.power_whip,
            MoveList.razor_leaf,
            MoveList.seed_bomb,
            MoveList.sleep_powder,
            MoveList.solar_beam,
            MoveList.sweet_scent,
            MoveList.synthesis,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.vine_whip,
            MoveList.worry_seed
        },
        PokemonList.venusaur,
        false,
        80, 100, 123, 122, 120, 80
    );

    public static final Pokemon charmander = new Pokemon(
        "Charmander",
        1,
        TypeList.fire,
        new double[] {87.5, 12.5},
        8.5,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.solar_power
        },
        new Move[] {
            MoveList.dragon_breath,
            MoveList.ember,
            MoveList.fire_fang,
            MoveList.fire_spin,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.growl,
            MoveList.inferno,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.slash,
            MoveList.smokescreen
        },
        39, 52, 43, 60, 50, 65
    );

    public static final Pokemon charmeleon = new Pokemon(
        "Charmeleon",
        1,
        TypeList.fire,
        new double[] {87.5, 12.5},
        19,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.solar_power
        },
        new Move[] {
            MoveList.dragon_breath,
            MoveList.ember,
            MoveList.fire_fang,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.growl,
            MoveList.inferno,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.slash,
            MoveList.smokescreen
        },
        58, 64, 58, 80, 65, 80
    );

    public static final Pokemon charizard = new Pokemon(
        "Charizard",
        1,
        TypeList.fire, TypeList.flying,
        new double[] {87.5, 12.5},
        90.5,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.solar_power
        },
        new Move[] {
            MoveList.air_slash,
            MoveList.dragon_breath,
            MoveList.dragon_claw,
            MoveList.ember,
            MoveList.fire_fang,
            MoveList.fire_spin,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.growl,
            MoveList.heat_wave,
            MoveList.inferno,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.slash,
            MoveList.smokescreen
        },
        78, 84, 78, 109, 85, 100
    );

    public static final Pokemon charizard_mega_x = new Pokemon( // Mega Evolução
        "Charizard",
        "Mega X",
        false,
        1,
        TypeList.fire, TypeList.dragon,
        new double[] {87.5, 12.5},
        110.5,
        new Ability[] {
            AbilityList.tough_claws
        },
        new Move[] {
            MoveList.air_slash,
            MoveList.dragon_breath,
            MoveList.dragon_claw,
            MoveList.ember,
            MoveList.fire_fang,
            MoveList.fire_spin,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.growl,
            MoveList.heat_wave,
            MoveList.inferno,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.slash,
            MoveList.smokescreen
        },
        PokemonList.charizard,
        false,
        78, 130, 111, 130, 85, 100
    );

    public static final Pokemon charizard_mega_y = new Pokemon( // Mega Evolução
        "Charizard",
        "Mega Y",
        false,
        1,
        TypeList.fire, TypeList.flying,
        new double[] {87.5, 12.5},
        100.5,
        new Ability[] {
            AbilityList.drought
        },
        new Move[] {
            MoveList.air_slash,
            MoveList.dragon_breath,
            MoveList.dragon_claw,
            MoveList.ember,
            MoveList.fire_fang,
            MoveList.fire_spin,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.growl,
            MoveList.heat_wave,
            MoveList.inferno,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.slash,
            MoveList.smokescreen
        },
        PokemonList.charizard,
        false,
        78, 104, 78, 159, 115, 100
    );

    public static final Pokemon squirtle = new Pokemon(
        "Squirtle",
        1,
        TypeList.water,
        new double[] {87.5, 12.5},
        9,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.rain_dish
        },
        new Move[] {
            MoveList.aqua_tail,
            MoveList.bite,
            MoveList.hydro_pump,
            MoveList.iron_defense,
            MoveList.protect,
            MoveList.rain_dance,
            MoveList.rapid_spin,
            MoveList.shell_smash,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.water_gun,
            MoveList.water_pulse,
            MoveList.wave_crash,
            MoveList.withdraw
        },
        44, 48, 65, 50, 64, 43
    );

    public static final Pokemon wartortle = new Pokemon(
        "Wartortle",
        1,
        TypeList.water,
        new double[] {87.5, 12.5},
        22.5,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.rain_dish
        },
        new Move[] {
            MoveList.aqua_tail,
            MoveList.bite,
            MoveList.hydro_pump,
            MoveList.iron_defense,
            MoveList.protect,
            MoveList.rain_dance,
            MoveList.rapid_spin,
            MoveList.shell_smash,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.water_gun,
            MoveList.water_pulse,
            MoveList.wave_crash,
            MoveList.withdraw
        },
        59, 63, 80, 65, 80, 58
    );

    public static final Pokemon blastoise = new Pokemon(
        "Blastoise",
        1,
        TypeList.water,
        new double[] {87.5, 12.5},
        85.5,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.rain_dish
        },
        new Move[] {
            MoveList.aqua_tail,
            MoveList.bite,
            MoveList.flash_cannon,
            MoveList.hydro_pump,
            MoveList.iron_defense,
            MoveList.protect,
            MoveList.rain_dance,
            MoveList.rapid_spin,
            MoveList.shell_smash,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.water_gun,
            MoveList.water_pulse,
            MoveList.wave_crash,
            MoveList.withdraw
        },
        79, 83, 100, 85, 105, 78
    );

    public static final Pokemon blastoise_mega = new Pokemon( // Mega Evolução
        "Blastoise",
        "Mega",
        false,
        1,
        TypeList.water,
        new double[] {87.5, 12.5},
        101.1,
        new Ability[] {
            AbilityList.mega_launcher
        },
        new Move[] {
            MoveList.aqua_tail,
            MoveList.bite,
            MoveList.flash_cannon,
            MoveList.hydro_pump,
            MoveList.iron_defense,
            MoveList.protect,
            MoveList.rain_dance,
            MoveList.rapid_spin,
            MoveList.shell_smash,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.water_gun,
            MoveList.water_pulse,
            MoveList.wave_crash,
            MoveList.withdraw
        },
        PokemonList.blastoise,
        false,
        79, 103, 120, 135, 115, 78
    );

    public static final Pokemon caterpie = new Pokemon(
        "Caterpie",
        1,
        TypeList.bug,
        new double[] {50, 50},
        2.9,
        new Ability[] {
            AbilityList.shield_dust,
            AbilityList.run_away
        },
        new Move[] {
            MoveList.bug_bite,
            MoveList.string_shot,
            MoveList.tackle
        },
        45, 30, 35, 20, 20, 45
    );

    public static final Pokemon metapod = new Pokemon(
        "Metapod",
        1,
        TypeList.bug,
        new double[] {50, 50},
        9.9,
        new Ability[] {
            AbilityList.shed_skin
        },
        new Move[] {
            MoveList.bug_bite,
            MoveList.harden,
            MoveList.string_shot,
            MoveList.tackle
        },
        50, 20, 55, 25, 25, 30
    );

    public static final Pokemon butterfree = new Pokemon(
        "Butterfree",
        1,
        TypeList.bug, TypeList.flying,
        new double[] {50, 50},
        32,
        new Ability[] {
            AbilityList.compound_eyes,
            AbilityList.tinted_lens
        },
        new Move[] {
            MoveList.air_slash,
            MoveList.bug_bite,
            MoveList.bug_buzz,
            MoveList.confusion,
            MoveList.gust,
            MoveList.harden,
            MoveList.poison_powder,
            MoveList.psybeam,
            MoveList.quiver_dance,
            MoveList.rage_powder,
            MoveList.safeguard,
            MoveList.sleep_powder,
            MoveList.string_shot,
            MoveList.stun_spore,
            MoveList.supersonic,
            MoveList.tackle,
            MoveList.tailwind,
            MoveList.whirlwind
        },
        60, 45, 50, 90, 80, 70
    );

    public static final Pokemon articuno = new Pokemon(
        "Articuno",
        "Kanto",
        1,
        TypeList.ice, TypeList.flying,
        new double[] {0, 0},
        55.4,
        new Ability[] {
            AbilityList.pressure,
            AbilityList.snow_cloak
        },
        new Move[] {
            MoveList.agility,
            MoveList.ancient_power,
            MoveList.blizzard,
            MoveList.freeze_dry,
            MoveList.gust,
            MoveList.haze,
            MoveList.hurricane,
            MoveList.ice_beam,
            MoveList.ice_shard,
            MoveList.mist,
            MoveList.powder_snow,
            MoveList.reflect,
            MoveList.roost,
            MoveList.sheer_cold,
            MoveList.snowscape,
            MoveList.tailwind
        },
        90, 85, 100, 95, 125, 85
    );

    public static final Pokemon articuno_galar = new Pokemon( // Forma Regional
        "Articuno",
        "Galar",
        true,
        1,
        TypeList.psychic, TypeList.flying,
        new double[] {0, 0},
        50.9,
        new Ability[] {
            AbilityList.competitive
        },
        new Move[] {
            MoveList.agility,
            MoveList.ancient_power,
            MoveList.confusion,
            MoveList.double_team,
            MoveList.dream_eater,
            MoveList.freezing_glare,
            MoveList.future_sight,
            MoveList.gust,
            MoveList.hurricane,
            MoveList.hypnosis,
            MoveList.psycho_cut,
            MoveList.recover,
            MoveList.reflect,
            MoveList.tailwind,
            MoveList.trick_room
        },
        PokemonList.articuno,
        false,
        90, 85, 85, 125, 100, 95
    );

    public static final Pokemon zapdos = new Pokemon(
        "Zapdos",
        "Kanto",
        1,
        TypeList.electric, TypeList.flying,
        new double[] {0, 0},
        52.6,
        new Ability[] {
            AbilityList.pressure,
            AbilityList.static_ab
        },
        new Move[] {
            MoveList.agility,
            MoveList.ancient_power,
            MoveList.charge,
            MoveList.detect,
            MoveList.discharge,
            MoveList.drill_peck,
            MoveList.light_screen,
            MoveList.magnetic_flux,
            MoveList.peck,
            MoveList.pluck,
            MoveList.rain_dance,
            MoveList.roost,
            MoveList.thunder_shock,
            MoveList.thunder_wave,
            MoveList.thunder,
            MoveList.zap_cannon
        },
        90, 90, 85, 125, 90, 100
    );

    public static final Pokemon zapdos_galar = new Pokemon( // Forma Regional
        "Zapdos",
        "Galar",
        true,
        1,
        TypeList.fighting, TypeList.flying,
        new double[] {0, 0},
        58.2,
        new Ability[] {
            AbilityList.defiant
        },
        new Move[] {
            MoveList.agility,
            MoveList.ancient_power,
            MoveList.brick_break,
            MoveList.bulk_up,
            MoveList.close_combat,
            MoveList.counter,
            MoveList.detect,
            MoveList.drill_peck,
            MoveList.focus_energy,
            MoveList.light_screen,
            MoveList.peck,
            MoveList.pluck,
            MoveList.quick_guard,
            MoveList.reversal,
            MoveList.rock_smash,
            MoveList.thunderous_kick
        },
        PokemonList.zapdos,
        false,
        90, 125, 90, 85, 90, 100
    );

    public static final Pokemon moltres = new Pokemon(
        "Moltres",
        "Kanto",
        1,
        TypeList.fire, TypeList.flying,
        new double[] {0, 0},
        100,
        new Ability[] {
            AbilityList.pressure,
            AbilityList.flame_body
        },
        new Move[] {
            MoveList.agility,
            MoveList.air_slash,
            MoveList.ancient_power,
            MoveList.ember,
            MoveList.endure,
            MoveList.gust,
            MoveList.heat_wave,
            MoveList.hurricane,
            MoveList.incinerate,
            MoveList.leer,
            MoveList.overheat,
            MoveList.roost,
            MoveList.safeguard,
            MoveList.sky_attack,
            MoveList.sunny_day,
            MoveList.wing_attack
        },
        90, 100, 90, 125, 85, 90
    );

    public static final Pokemon moltres_galar = new Pokemon( // Forma Regional
        "Moltres",
        "Galar",
        true,
        1,
        TypeList.dark, TypeList.flying,
        new double[] {0, 0},
        100,
        new Ability[] {
            AbilityList.berserk
        },
        new Move[] {
            MoveList.after_you,
            MoveList.agility,
            MoveList.air_slash,
            MoveList.ancient_power,
            MoveList.endure,
            MoveList.fiery_wrath,
            MoveList.gust,
            MoveList.hurricane,
            MoveList.leer,
            MoveList.memento,
            MoveList.nasty_plot,
            MoveList.payback,
            MoveList.safeguard,
            MoveList.sky_attack,
            MoveList.sucker_punch,
            MoveList.wing_attack
        },
        PokemonList.moltres,
        false,
        90, 85, 90, 100, 125, 90
    );

    public static final Pokemon dratini = new Pokemon(
        "Dratini",
        1,
        TypeList.dragon,
        new double[] {50, 50},
        3.3,
        new Ability[] {
            AbilityList.shed_skin,
            AbilityList.marvel_scale
        },
        new Move[] {
            MoveList.agility,
            MoveList.aqua_tail,
            MoveList.dragon_dance,
            MoveList.dragon_rush,
            MoveList.dragon_tail,
            MoveList.hyper_beam,
            MoveList.leer,
            MoveList.outrage,
            MoveList.rain_dance,
            MoveList.safeguard,
            MoveList.slam,
            MoveList.thunder_wave,
            MoveList.twister,
            MoveList.wrap
        },
        41, 64, 45, 50, 50, 50
    );

    public static final Pokemon dragonair = new Pokemon(
        "Dragonair",
        1,
        TypeList.dragon,
        new double[] {50, 50},
        16.5,
        new Ability[] {
            AbilityList.shed_skin,
            AbilityList.marvel_scale
        },
        new Move[] {
            MoveList.agility,
            MoveList.aqua_tail,
            MoveList.dragon_dance,
            MoveList.dragon_rush,
            MoveList.dragon_tail,
            MoveList.hyper_beam,
            MoveList.leer,
            MoveList.outrage,
            MoveList.rain_dance,
            MoveList.safeguard,
            MoveList.slam,
            MoveList.thunder_wave,
            MoveList.twister,
            MoveList.wrap
        },
        61, 84, 65, 70, 70, 70
    );

    public static final Pokemon dragonite = new Pokemon(
        "Dragonite",
        1,
        TypeList.dragon, TypeList.flying,
        new double[] {50, 50},
        210,
        new Ability[] {
            AbilityList.inner_focus,
            AbilityList.multiscale
        },
        new Move[] {
            MoveList.agility,
            MoveList.aqua_tail,
            MoveList.dragon_dance,
            MoveList.dragon_rush,
            MoveList.dragon_tail,
            MoveList.extreme_speed,
            MoveList.fire_punch,
            MoveList.hurricane,
            MoveList.hyper_beam,
            MoveList.leer,
            MoveList.outrage,
            MoveList.rain_dance,
            MoveList.roost,
            MoveList.safeguard,
            MoveList.slam,
            MoveList.thunder_punch,
            MoveList.thunder_wave,
            MoveList.twister,
            MoveList.wing_attack,
            MoveList.wrap
        },
        91, 134, 95, 100, 100, 80
    );

    // TODO Mega Dragonite

    public static final Pokemon mewtwo = new Pokemon(
        "Mewtwo",
        1,
        TypeList.psychic,
        new double[] {0, 0},
        122,
        new Ability[] {
            AbilityList.pressure,
            AbilityList.unnerve
        },
        new Move[] {
            MoveList.amnesia,
            MoveList.ancient_power,
            MoveList.aura_sphere,
            MoveList.confusion,
            MoveList.disable,
            MoveList.future_sight,
            MoveList.guard_swap,
            MoveList.life_dew,
            MoveList.mist,
            MoveList.power_swap,
            MoveList.psychic,
            MoveList.psycho_cut,
            MoveList.psystrike,
            MoveList.recover,
            MoveList.safeguard,
            MoveList.swift
        },
        106, 110, 90, 154, 90, 130
    );

    public static final Pokemon mewtwo_mega_x = new Pokemon( // Mega Evolução
        "Mewtwo",
        "Mega X",
        false,
        1,
        TypeList.psychic, TypeList.fighting,
        new double[] {0, 0},
        127,
        new Ability[] {
            AbilityList.steadfast
        },
        new Move[] {
            MoveList.amnesia,
            MoveList.ancient_power,
            MoveList.aura_sphere,
            MoveList.confusion,
            MoveList.disable,
            MoveList.future_sight,
            MoveList.guard_swap,
            MoveList.life_dew,
            MoveList.mist,
            MoveList.power_swap,
            MoveList.psychic,
            MoveList.psycho_cut,
            MoveList.psystrike,
            MoveList.recover,
            MoveList.safeguard,
            MoveList.swift
        },
        PokemonList.mewtwo,
        false,
        106, 190, 100, 154, 100, 130
    );

    public static final Pokemon mewtwo_mega_y = new Pokemon( // Mega Evolução
        "Mewtwo",
        "Mega Y",
        false,
        1,
        TypeList.psychic,
        new double[] {0, 0},
        33,
        new Ability[] {
            AbilityList.neuroforce
        },
        new Move[] {
            MoveList.amnesia,
            MoveList.ancient_power,
            MoveList.aura_sphere,
            MoveList.confusion,
            MoveList.disable,
            MoveList.future_sight,
            MoveList.guard_swap,
            MoveList.life_dew,
            MoveList.mist,
            MoveList.power_swap,
            MoveList.psychic,
            MoveList.psycho_cut,
            MoveList.psystrike,
            MoveList.recover,
            MoveList.safeguard,
            MoveList.swift
        },
        PokemonList.mewtwo,
        false,
        106, 150, 70, 194, 120, 140
    );

    public static final Pokemon mew = new Pokemon(
        "Mew",
        1,
        TypeList.psychic,
        new double[] {0, 0},
        4,
        new Ability[] {
            AbilityList.synchronize
        },
        new Move[] {
            MoveList.amnesia,
            MoveList.ancient_power,
            MoveList.aura_sphere,
            MoveList.baton_pass,
            MoveList.imprison,
            MoveList.life_dew,
            MoveList.metronome,
            MoveList.nasty_plot,
            MoveList.pound,
            MoveList.psychic,
            MoveList.reflect_type,
            MoveList.transform
        },
        100, 100, 100, 100, 100, 100
    );


    /* Geração 2 */

    public static final Pokemon chikorita = new Pokemon(
        "Chikorita",
        2,
        TypeList.grass,
        new double[] {87.5, 12.5},
        6.4,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.leaf_guard
        },
        new Move[] {
            MoveList.body_slam,
            MoveList.giga_drain,
            MoveList.growl,
            MoveList.leech_seed,
            MoveList.light_screen,
            MoveList.magical_leaf,
            MoveList.poison_powder,
            MoveList.razor_leaf,
            MoveList.reflect,
            MoveList.safeguard,
            MoveList.solar_beam,
            MoveList.sweet_scent,
            MoveList.synthesis,
            MoveList.tackle
        },
        45, 49, 65, 49, 65, 45
    );

    public static final Pokemon bayleef = new Pokemon(
        "Bayleef",
        2,
        TypeList.grass,
        new double[] {87.5, 12.5},
        15.8,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.leaf_guard
        },
        new Move[] {
            MoveList.body_slam,
            MoveList.giga_drain,
            MoveList.growl,
            MoveList.leech_seed,
            MoveList.light_screen,
            MoveList.magical_leaf,
            MoveList.poison_powder,
            MoveList.razor_leaf,
            MoveList.reflect,
            MoveList.safeguard,
            MoveList.solar_beam,
            MoveList.sweet_scent,
            MoveList.synthesis,
            MoveList.tackle
        },
        60, 62, 80, 63, 80, 60
    );

    public static final Pokemon meganium = new Pokemon(
        "Meganium",
        2,
        TypeList.grass,
        new double[] {87.5, 12.5},
        100.5,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.leaf_guard
        },
        new Move[] {
            MoveList.body_slam,
            MoveList.giga_drain,
            MoveList.growl,
            MoveList.leech_seed,
            MoveList.light_screen,
            MoveList.magical_leaf,
            MoveList.petal_blizzard,
            MoveList.petal_dance,
            MoveList.poison_powder,
            MoveList.razor_leaf,
            MoveList.reflect,
            MoveList.safeguard,
            MoveList.solar_beam,
            MoveList.sweet_scent,
            MoveList.synthesis,
            MoveList.tackle
        },
        80, 82, 100, 83, 100, 80
    );

    public static final Pokemon cyndaquil = new Pokemon(
        "Cyndaquil",
        2,
        TypeList.fire,
        new double[] {87.5, 12.5},
        7.9,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.flash_fire
        },
        new Move[] {
            MoveList.defense_curl,
            MoveList.double_edge,
            MoveList.ember,
            MoveList.eruption,
            MoveList.flame_charge,
            MoveList.flame_wheel,
            MoveList.flamethrower,
            MoveList.inferno,
            MoveList.lava_plume,
            MoveList.leer,
            MoveList.overheat,
            MoveList.quick_attack,
            MoveList.rollout,
            MoveList.smokescreen,
            MoveList.swift,
            MoveList.tackle
        },
        39, 52, 43, 60, 50, 65
    );

    public static final Pokemon quilava = new Pokemon(
        "Quilava",
        2,
        TypeList.fire,
        new double[] {87.5, 12.5},
        19,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.flash_fire
        },
        new Move[] {
            MoveList.defense_curl,
            MoveList.double_edge,
            MoveList.ember,
            MoveList.eruption,
            MoveList.flame_charge,
            MoveList.flame_wheel,
            MoveList.flamethrower,
            MoveList.inferno,
            MoveList.lava_plume,
            MoveList.leer,
            MoveList.overheat,
            MoveList.quick_attack,
            MoveList.rollout,
            MoveList.smokescreen,
            MoveList.swift,
            MoveList.tackle
        },
        58, 64, 58, 80, 65, 80
    );

    public static final Pokemon typhlosion = new Pokemon(
        "Typhlosion",
        "Johto",
        2,
        TypeList.fire,
        new double[] {87.5, 12.5},
        79.5,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.flash_fire
        },
        new Move[] {
            MoveList.defense_curl,
            MoveList.double_edge,
            MoveList.ember,
            MoveList.eruption,
            MoveList.flame_charge,
            MoveList.flame_wheel,
            MoveList.flamethrower,
            MoveList.gyro_ball,
            MoveList.inferno,
            MoveList.lava_plume,
            MoveList.leer,
            MoveList.overheat,
            MoveList.quick_attack,
            MoveList.rollout,
            MoveList.smokescreen,
            MoveList.swift,
            MoveList.tackle
        },
        78, 84, 78, 109, 85, 100
    );

    public static final Pokemon typhlosion_hisui = new Pokemon( // Forma Regional
        "Typhlosion",
        "Hisui",
        true,
        2,
        TypeList.fire, TypeList.ghost,
        new double[] {87.5, 12.5},
        69.8,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.frisk
        },
        new Move[] {
            MoveList.defense_curl,
            MoveList.double_edge,
            MoveList.ember,
            MoveList.eruption,
            MoveList.flame_charge,
            MoveList.flame_wheel,
            MoveList.flamethrower,
            MoveList.gyro_ball,
            MoveList.infernal_parade,
            MoveList.inferno,
            MoveList.lava_plume,
            MoveList.leer,
            MoveList.overheat,
            MoveList.quick_attack,
            MoveList.rollout,
            MoveList.smokescreen,
            MoveList.swift,
            MoveList.tackle
        },
        PokemonList.typhlosion,
        false,
        73, 84, 78, 119, 85, 95
    );

    public static final Pokemon totodile = new Pokemon(
        "Totodile",
        2,
        TypeList.water,
        new double[] {87.5, 12.5},
        9.5,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.sheer_force
        },
        new Move[] {
            MoveList.aqua_tail,
            MoveList.bite,
            MoveList.crunch,
            MoveList.flail,
            MoveList.hydro_pump,
            MoveList.ice_fang,
            MoveList.leer,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.screech,
            MoveList.slash,
            MoveList.superpower,
            MoveList.thrash,
            MoveList.water_gun
        },
        50, 65, 64, 44, 48, 43
    );

    public static final Pokemon croconaw = new Pokemon(
        "Croconaw",
        2,
        TypeList.water,
        new double[] {87.5, 12.5},
        25,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.sheer_force
        },
        new Move[] {
            MoveList.aqua_tail,
            MoveList.bite,
            MoveList.crunch,
            MoveList.flail,
            MoveList.hydro_pump,
            MoveList.ice_fang,
            MoveList.leer,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.screech,
            MoveList.slash,
            MoveList.superpower,
            MoveList.thrash,
            MoveList.water_gun
        },
        65, 80, 80, 59, 63, 58
    );

    public static final Pokemon feraligatr = new Pokemon(
        "Feraligatr",
        2,
        TypeList.water,
        new double[] {87.5, 12.5},
        88.8,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.sheer_force
        },
        new Move[] {
            MoveList.agility,
            MoveList.aqua_tail,
            MoveList.bite,
            MoveList.crunch,
            MoveList.flail,
            MoveList.hydro_pump,
            MoveList.ice_fang,
            MoveList.leer,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.screech,
            MoveList.slash,
            MoveList.superpower,
            MoveList.thrash,
            MoveList.water_gun
        },
        85, 105, 100, 79, 83, 78
    );

    public static final Pokemon togepi = new Pokemon(
        "Togepi",
        2,
        TypeList.fairy,
        new double[] {87.5, 12.5},
        1.5,
        new Ability[] {
            AbilityList.hustle,
            AbilityList.serene_grace,
            AbilityList.super_luck
        },
        new Move[] {
            MoveList.after_you,
            MoveList.ancient_power,
            MoveList.baton_pass,
            MoveList.charm,
            MoveList.double_edge,
            MoveList.follow_me,
            MoveList.growl,
            MoveList.last_resort,
            MoveList.life_dew,
            MoveList.metronome,
            MoveList.pound,
            MoveList.safeguard,
            MoveList.sweet_kiss,
            MoveList.wish,
            MoveList.yawn
        },
        35, 20, 65, 40, 65, 20
    );

    public static final Pokemon togetic = new Pokemon(
        "Togetic",
        2,
        TypeList.fairy, TypeList.flying,
        new double[] {87.5, 12.5},
        3.2,
        new Ability[] {
            AbilityList.hustle,
            AbilityList.serene_grace,
            AbilityList.super_luck
        },
        new Move[] {
            MoveList.after_you,
            MoveList.ancient_power,
            MoveList.baton_pass,
            MoveList.charm,
            MoveList.double_edge,
            MoveList.fairy_wind,
            MoveList.follow_me,
            MoveList.growl,
            MoveList.last_resort,
            MoveList.life_dew,
            MoveList.metronome,
            MoveList.pound,
            MoveList.safeguard,
            MoveList.sweet_kiss,
            MoveList.wish,
            MoveList.yawn
        },
        55, 40, 85, 80, 105, 40
    );

    public static final Pokemon raikou = new Pokemon(
        "Raikou",
        2,
        TypeList.electric,
        new double[] {0, 0},
        178,
        new Ability[] {
            AbilityList.pressure,
            AbilityList.inner_focus
        },
        new Move[] {
            MoveList.bite,
            MoveList.calm_mind,
            MoveList.charge,
            MoveList.crunch,
            MoveList.discharge,
            MoveList.extrasensory,
            MoveList.extreme_speed,
            MoveList.howl,
            MoveList.leer,
            MoveList.quick_attack,
            MoveList.rain_dance,
            MoveList.reflect,
            MoveList.roar,
            MoveList.spark,
            MoveList.thunder_fang,
            MoveList.thunder_shock,
            MoveList.thunder,
            MoveList.zap_cannon
        },
        90, 85, 75, 115, 100, 115
    );

    public static final Pokemon entei = new Pokemon(
        "Entei",
        2,
        TypeList.fire,
        new double[] {0, 0},
        198,
        new Ability[] {
            AbilityList.pressure,
            AbilityList.inner_focus
        },
        new Move[] {
            MoveList.bite,
            MoveList.calm_mind,
            MoveList.crunch,
            MoveList.ember,
            MoveList.eruption,
            MoveList.extrasensory,
            MoveList.extreme_speed,
            MoveList.fire_blast,
            MoveList.fire_fang,
            MoveList.flame_wheel,
            MoveList.lava_plume,
            MoveList.leer,
            MoveList.roar,
            MoveList.sacred_fire,
            MoveList.scary_face,
            MoveList.smokescreen,
            MoveList.stomp,
            MoveList.sunny_day,
            MoveList.swagger
        },
        115, 115, 85, 90, 75, 100
    );

    public static final Pokemon suicune = new Pokemon(
        "Suicune",
        2,
        TypeList.water,
        new double[] {0, 0},
        187,
        new Ability[] {
            AbilityList.pressure,
            AbilityList.inner_focus
        },
        new Move[] {
            MoveList.bite,
            MoveList.blizzard,
            MoveList.calm_mind,
            MoveList.crunch,
            MoveList.extrasensory,
            MoveList.extreme_speed,
            MoveList.gust,
            MoveList.hydro_pump,
            MoveList.ice_fang,
            MoveList.leer,
            MoveList.mirror_coat,
            MoveList.mist,
            MoveList.rain_dance,
            MoveList.roar,
            MoveList.sheer_cold,
            MoveList.surf,
            MoveList.tailwind,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        100, 75, 115, 90, 115, 85
    );

    public static final Pokemon lugia = new Pokemon(
        "Lugia",
        2,
        TypeList.psychic, TypeList.flying,
        new double[] {0, 0},
        216,
        new Ability[] {
            AbilityList.pressure,
            AbilityList.multiscale
        },
        new Move[] {
            MoveList.aeroblast,
            MoveList.ancient_power,
            MoveList.calm_mind,
            MoveList.dragon_rush,
            MoveList.extrasensory,
            MoveList.future_sight,
            MoveList.gust,
            MoveList.hydro_pump,
            MoveList.mist,
            MoveList.rain_dance,
            MoveList.recover,
            MoveList.safeguard,
            MoveList.sky_attack,
            MoveList.weather_ball,
            MoveList.whirlwind
        },
        106, 90, 130, 90, 154, 110
    );

    public static final Pokemon ho_oh = new Pokemon(
        "Ho-Oh",
        2,
        TypeList.fire, TypeList.flying,
        new double[] {0, 0},
        199,
        new Ability[] {
            AbilityList.pressure,
            AbilityList.regenerator
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.calm_mind,
            MoveList.extrasensory,
            MoveList.fire_blast,
            MoveList.future_sight,
            MoveList.gust,
            MoveList.life_dew,
            MoveList.overheat,
            MoveList.recover,
            MoveList.sacred_fire,
            MoveList.safeguard,
            MoveList.sky_attack,
            MoveList.sunny_day,
            MoveList.weather_ball,
            MoveList.whirlwind
        },
        106, 130, 90, 110, 154, 90
    );

    public static final Pokemon celebi = new Pokemon(
        "Celebi",
        2,
        TypeList.psychic, TypeList.grass,
        new double[] {0, 0},
        5,
        new Ability[] {
            AbilityList.natural_cure
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.baton_pass,
            MoveList.confusion,
            MoveList.future_sight,
            MoveList.heal_bell,
            MoveList.healing_wish,
            MoveList.leaf_storm,
            MoveList.leech_seed,
            MoveList.life_dew,
            MoveList.magical_leaf,
            MoveList.perish_song,
            MoveList.recover
        },
        100, 100, 100, 100, 100, 100
    );


    /* Geração 3 */

    public static final Pokemon treecko = new Pokemon(
        "Treecko",
        3,
        TypeList.grass,
        new double[] {87.5, 12.5},
        5,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.unburden
        },
        new Move[] {
            MoveList.assurance,
            MoveList.detect,
            MoveList.double_team,
            MoveList.endeavor,
            MoveList.energy_ball,
            MoveList.giga_drain,
            MoveList.leaf_storm,
            MoveList.leafage,
            MoveList.leer,
            MoveList.mega_drain,
            MoveList.pound,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.screech,
            MoveList.slam
        },
        40, 45, 35, 65, 55, 70
    );

    public static final Pokemon grovyle = new Pokemon(
        "Grovyle",
        3,
        TypeList.grass,
        new double[] {87.5, 12.5},
        21.6,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.unburden
        },
        new Move[] {
            MoveList.assurance,
            MoveList.detect,
            MoveList.double_team,
            MoveList.endeavor,
            MoveList.energy_ball,
            MoveList.false_swipe,
            MoveList.fury_cutter,
            MoveList.giga_drain,
            MoveList.leaf_blade,
            MoveList.leaf_storm,
            MoveList.leafage,
            MoveList.leer,
            MoveList.mega_drain,
            MoveList.pound,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.screech,
            MoveList.slam,
            MoveList.x_scissor
        },
        50, 65, 45, 85, 65, 95
    );

    public static final Pokemon sceptile = new Pokemon(
        "Sceptile",
        3,
        TypeList.grass,
        new double[] {87.5, 12.5},
        52.2,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.unburden
        },
        new Move[] {
            MoveList.assurance,
            MoveList.detect,
            MoveList.double_team,
            MoveList.endeavor,
            MoveList.energy_ball,
            MoveList.false_swipe,
            MoveList.fury_cutter,
            MoveList.giga_drain,
            MoveList.leaf_blade,
            MoveList.leaf_storm,
            MoveList.leafage,
            MoveList.leer,
            MoveList.mega_drain,
            MoveList.pound,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.screech,
            MoveList.shed_tail,
            MoveList.slam,
            MoveList.x_scissor
        },
        70, 85, 65, 105, 85, 120
    );

    public static final Pokemon sceptile_mega = new Pokemon( // Mega Evolução
        "Sceptile",
        "Mega",
        false,
        3,
        TypeList.grass, TypeList.dragon,
        new double[] {87.5, 12.5},
        55.2,
        new Ability[] {
            AbilityList.lightning_rod
        },
        new Move[] {
            MoveList.assurance,
            MoveList.detect,
            MoveList.double_team,
            MoveList.endeavor,
            MoveList.energy_ball,
            MoveList.false_swipe,
            MoveList.fury_cutter,
            MoveList.giga_drain,
            MoveList.leaf_blade,
            MoveList.leaf_storm,
            MoveList.leafage,
            MoveList.leer,
            MoveList.mega_drain,
            MoveList.pound,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.screech,
            MoveList.shed_tail,
            MoveList.slam,
            MoveList.x_scissor
        },
        PokemonList.sceptile,
        false,
        70, 110, 75, 145, 85, 145
    );

    public static final Pokemon torchic = new Pokemon(
        "Torchic",
        3,
        TypeList.fire,
        new double[] {87.5, 12.5},
        2.5,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.speed_boost
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.bounce,
            MoveList.detect,
            MoveList.ember,
            MoveList.feather_dance,
            MoveList.flame_charge,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.focus_energy,
            MoveList.growl,
            MoveList.quick_attack,
            MoveList.reversal,
            MoveList.sand_attack,
            MoveList.scratch,
            MoveList.slash
        },
        45, 60, 40, 70, 50, 45
    );

    public static final Pokemon combusken = new Pokemon(
        "Combusken",
        3,
        TypeList.fire, TypeList.fighting,
        new double[] {87.5, 12.5},
        19.5,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.speed_boost
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.blaze_kick,
            MoveList.bounce,
            MoveList.bulk_up,
            MoveList.detect,
            MoveList.double_kick,
            MoveList.ember,
            MoveList.feather_dance,
            MoveList.flame_charge,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.focus_energy,
            MoveList.growl,
            MoveList.quick_attack,
            MoveList.reversal,
            MoveList.sand_attack,
            MoveList.scratch,
            MoveList.slash
        },
        60, 85, 60, 85, 60, 55
    );

    public static final Pokemon blaziken = new Pokemon(
        "Blaziken",
        3,
        TypeList.fire, TypeList.fighting,
        new double[] {87.5, 12.5},
        52,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.speed_boost
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.blaze_kick,
            MoveList.bounce,
            MoveList.brave_bird,
            MoveList.bulk_up,
            MoveList.detect,
            MoveList.double_kick,
            MoveList.ember,
            MoveList.feather_dance,
            MoveList.fire_punch,
            MoveList.flame_charge,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.focus_energy,
            MoveList.growl,
            MoveList.quick_attack,
            MoveList.reversal,
            MoveList.sand_attack,
            MoveList.scratch,
            MoveList.slash
        },
        80, 120, 70, 110, 70, 80
    );

    public static final Pokemon blaziken_mega = new Pokemon( // Mega Evolução
        "Blaziken",
        "Mega",
        false,
        3,
        TypeList.fire, TypeList.fighting,
        new double[] {87.5, 12.5},
        52,
        new Ability[] {
            AbilityList.speed_boost
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.blaze_kick,
            MoveList.bounce,
            MoveList.brave_bird,
            MoveList.bulk_up,
            MoveList.detect,
            MoveList.double_kick,
            MoveList.ember,
            MoveList.feather_dance,
            MoveList.fire_punch,
            MoveList.flame_charge,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.focus_energy,
            MoveList.growl,
            MoveList.quick_attack,
            MoveList.reversal,
            MoveList.sand_attack,
            MoveList.scratch,
            MoveList.slash
        },
        PokemonList.blaziken,
        false,
        80, 160, 80, 130, 80, 100
    );

    public static final Pokemon mudkip = new Pokemon(
        "Mudkip",
        3,
        TypeList.water,
        new double[] {87.5, 12.5},
        7.6,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.damp
        },
        new Move[] {
            MoveList.amnesia,
            MoveList.endeavor,
            MoveList.growl,
            MoveList.hydro_pump,
            MoveList.protect,
            MoveList.rock_slide,
            MoveList.rock_smash,
            MoveList.rock_throw,
            MoveList.screech,
            MoveList.supersonic,
            MoveList.surf,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        50, 70, 50, 50, 50, 40
    );

    public static final Pokemon marshtomp = new Pokemon(
        "Marshtomp",
        3,
        TypeList.water, TypeList.ground,
        new double[] {87.5, 12.5},
        28,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.damp
        },
        new Move[] {
            MoveList.amnesia,
            MoveList.endeavor,
            MoveList.growl,
            MoveList.hydro_pump,
            MoveList.mud_shot,
            MoveList.muddy_water,
            MoveList.protect,
            MoveList.rock_slide,
            MoveList.rock_smash,
            MoveList.rock_throw,
            MoveList.screech,
            MoveList.supersonic,
            MoveList.surf,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        70, 85, 70, 60, 70, 50
    );

    public static final Pokemon swampert = new Pokemon(
        "Swampert",
        3,
        TypeList.water, TypeList.ground,
        new double[] {87.5, 12.5},
        81.9,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.damp
        },
        new Move[] {
            MoveList.amnesia,
            MoveList.earthquake,
            MoveList.endeavor,
            MoveList.growl,
            MoveList.hammer_arm,
            MoveList.hydro_pump,
            MoveList.mud_shot,
            MoveList.muddy_water,
            MoveList.protect,
            MoveList.rock_slide,
            MoveList.rock_smash,
            MoveList.rock_throw,
            MoveList.screech,
            MoveList.supersonic,
            MoveList.surf,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        100, 110, 90, 85, 90, 60
    );

    public static final Pokemon swampert_mega = new Pokemon( // Mega Evolução
        "Swampert",
        "Mega",
        false,
        3,
        TypeList.water, TypeList.ground,
        new double[] {87.5, 12.5},
        102,
        new Ability[] {
            AbilityList.swift_swim
        },
        new Move[] {
            MoveList.amnesia,
            MoveList.earthquake,
            MoveList.endeavor,
            MoveList.growl,
            MoveList.hammer_arm,
            MoveList.hydro_pump,
            MoveList.mud_shot,
            MoveList.muddy_water,
            MoveList.protect,
            MoveList.rock_slide,
            MoveList.rock_smash,
            MoveList.rock_throw,
            MoveList.screech,
            MoveList.supersonic,
            MoveList.surf,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        PokemonList.swampert,
        false,
        100, 150, 110, 95, 110, 70
    );

    public static final Pokemon castform = new Pokemon(
        "Castform",
        "Normal",
        3,
        TypeList.normal,
        new double[] {50, 50},
        0.8,
        new Ability[] {
            AbilityList.forecast
        },
        new Move[] {
            MoveList.blizzard,
            MoveList.ember,
            MoveList.fire_blast,
            MoveList.headbutt,
            MoveList.hurricane,
            MoveList.hydro_pump,
            MoveList.powder_snow,
            MoveList.rain_dance,
            MoveList.snowscape,
            MoveList.sunny_day,
            MoveList.tackle,
            MoveList.water_gun,
            MoveList.weather_ball
        },
        70, 70, 70, 70, 70, 70
    );

    public static final Pokemon castform_sunny = new Pokemon( // Forma Alternativa
        "Castform",
        "Sunny",
        false,
        3,
        TypeList.fire,
        new double[] {50, 50},
        0.8,
        new Ability[] {
            AbilityList.forecast
        },
        new Move[] {
            MoveList.blizzard,
            MoveList.ember,
            MoveList.fire_blast,
            MoveList.headbutt,
            MoveList.hurricane,
            MoveList.hydro_pump,
            MoveList.powder_snow,
            MoveList.rain_dance,
            MoveList.snowscape,
            MoveList.sunny_day,
            MoveList.tackle,
            MoveList.water_gun,
            MoveList.weather_ball
        },
        PokemonList.castform,
        true,
        70, 70, 70, 70, 70, 70
    );

    public static final Pokemon castform_rainy = new Pokemon( // Forma Alternativa
        "Castform",
        "Rainy",
        false,
        3,
        TypeList.water,
        new double[] {50, 50},
        0.8,
        new Ability[] {
            AbilityList.forecast
        },
        new Move[] {
            MoveList.blizzard,
            MoveList.ember,
            MoveList.fire_blast,
            MoveList.headbutt,
            MoveList.hurricane,
            MoveList.hydro_pump,
            MoveList.powder_snow,
            MoveList.rain_dance,
            MoveList.snowscape,
            MoveList.sunny_day,
            MoveList.tackle,
            MoveList.water_gun,
            MoveList.weather_ball
        },
        PokemonList.castform,
        true,
        70, 70, 70, 70, 70, 70
    );

    public static final Pokemon castform_snowy = new Pokemon( // Forma Alternativa
        "Castform",
        "Snowy",
        false,
        3,
        TypeList.ice,
        new double[] {50, 50},
        0.8,
        new Ability[] {
            AbilityList.forecast
        },
        new Move[] {
            MoveList.blizzard,
            MoveList.ember,
            MoveList.fire_blast,
            MoveList.headbutt,
            MoveList.hurricane,
            MoveList.hydro_pump,
            MoveList.powder_snow,
            MoveList.rain_dance,
            MoveList.snowscape,
            MoveList.sunny_day,
            MoveList.tackle,
            MoveList.water_gun,
            MoveList.weather_ball
        },
        PokemonList.castform,
        true,
        70, 70, 70, 70, 70, 70
    );

    public static final Pokemon regirock = new Pokemon(
        "Regirock",
        3,
        TypeList.rock,
        new double[] {0, 0},
        230,
        new Ability[] {
            AbilityList.clear_body,
            AbilityList.rocky_payload
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.bulldoze,
            MoveList.charge_beam,
            MoveList.curse,
            MoveList.explosion,
            MoveList.hammer_arm,
            MoveList.hyper_beam,
            MoveList.iron_defense,
            MoveList.lock_on,
            MoveList.rock_slide,
            MoveList.rock_throw,
            MoveList.stomp,
            MoveList.stone_edge,
            MoveList.superpower,
            MoveList.zap_cannon
        },
        80, 100, 200, 50, 100, 50
    );

    public static final Pokemon regice = new Pokemon(
        "Regice",
        3,
        TypeList.ice,
        new double[] {0, 0},
        175,
        new Ability[] {
            AbilityList.clear_body,
            AbilityList.absolute_zero
        },
        new Move[] {
            MoveList.amnesia,
            MoveList.ancient_power,
            MoveList.blizzard,
            MoveList.bulldoze,
            MoveList.charge_beam,
            MoveList.curse,
            MoveList.explosion,
            MoveList.hammer_arm,
            MoveList.hyper_beam,
            MoveList.ice_beam,
            MoveList.icy_wind,
            MoveList.lock_on,
            MoveList.stomp,
            MoveList.superpower,
            MoveList.zap_cannon
        },
        80, 50, 100, 100, 200, 50
    );

    public static final Pokemon registeel = new Pokemon(
        "Registeel",
        3,
        TypeList.steel,
        new double[] {0, 0},
        205,
        new Ability[] {
            AbilityList.clear_body,
            AbilityList.steelworker
        },
        new Move[] {
            MoveList.amnesia,
            MoveList.ancient_power,
            MoveList.bulldoze,
            MoveList.charge_beam,
            MoveList.curse,
            MoveList.explosion,
            MoveList.flash_cannon,
            MoveList.hammer_arm,
            MoveList.heavy_slam,
            MoveList.hyper_beam,
            MoveList.iron_defense,
            MoveList.iron_head,
            MoveList.lock_on,
            MoveList.metal_claw,
            MoveList.stomp,
            MoveList.superpower,
            MoveList.zap_cannon
        },
        80, 75, 150, 75, 150, 50
    );

    public static final Pokemon latias = new Pokemon(
        "Latias",
        3,
        TypeList.dragon, TypeList.psychic,
        new double[] {0, 100},
        40,
        new Ability[] {
            AbilityList.levitate
        },
        new Move[] {
            MoveList.charm,
            MoveList.confusion,
            MoveList.dragon_breath,
            MoveList.dragon_pulse,
            MoveList.guard_split,
            MoveList.heal_pulse,
            MoveList.healing_wish,
            MoveList.helping_hand,
            MoveList.mist_ball,
            MoveList.psychic,
            MoveList.recover,
            MoveList.reflect_type,
            MoveList.stored_power,
            MoveList.tailwind,
            MoveList.wish,
            MoveList.zen_headbutt
        },
        80, 80, 90, 110, 130, 110
    );

    public static final Pokemon latias_mega = new Pokemon( // Mega Evolução
        "Latias",
        "Mega",
        false,
        3,
        TypeList.dragon, TypeList.psychic,
        new double[] {0, 100},
        52,
        new Ability[] {
            AbilityList.levitate
        },
        new Move[] {
            MoveList.charm,
            MoveList.confusion,
            MoveList.dragon_breath,
            MoveList.dragon_pulse,
            MoveList.guard_split,
            MoveList.heal_pulse,
            MoveList.healing_wish,
            MoveList.helping_hand,
            MoveList.mist_ball,
            MoveList.psychic,
            MoveList.recover,
            MoveList.reflect_type,
            MoveList.stored_power,
            MoveList.tailwind,
            MoveList.wish,
            MoveList.zen_headbutt
        },
        PokemonList.latias,
        false,
        80, 100, 120, 140, 150, 110
    );

    public static final Pokemon latios = new Pokemon(
        "Latios",
        3,
        TypeList.dragon, TypeList.psychic,
        new double[] {100, 0},
        60,
        new Ability[] {
            AbilityList.levitate
        },
        new Move[] {
            MoveList.ally_switch,
            MoveList.confusion,
            MoveList.dragon_breath,
            MoveList.dragon_dance,
            MoveList.dragon_pulse,
            MoveList.heal_pulse,
            MoveList.helping_hand,
            MoveList.luster_purge,
            MoveList.memento,
            MoveList.power_split,
            MoveList.psychic,
            MoveList.recover,
            MoveList.simple_beam,
            MoveList.stored_power,
            MoveList.tailwind,
            MoveList.zen_headbutt
        },
        80, 90, 80, 130, 110, 110
    );

    public static final Pokemon latios_mega = new Pokemon( // Mega Evolução
        "Latios",
        "Mega",
        false,
        3,
        TypeList.dragon, TypeList.psychic,
        new double[] {100, 0},
        70,
        new Ability[] {
            AbilityList.levitate
        },
        new Move[] {
            MoveList.ally_switch,
            MoveList.confusion,
            MoveList.dragon_breath,
            MoveList.dragon_dance,
            MoveList.dragon_pulse,
            MoveList.heal_pulse,
            MoveList.helping_hand,
            MoveList.luster_purge,
            MoveList.memento,
            MoveList.power_split,
            MoveList.psychic,
            MoveList.recover,
            MoveList.simple_beam,
            MoveList.stored_power,
            MoveList.tailwind,
            MoveList.zen_headbutt
        },
        PokemonList.latios,
        false,
        80, 130, 100, 160, 120, 110
    );

    public static final Pokemon kyogre = new Pokemon(
        "Kyogre",
        3,
        TypeList.water,
        new double[] {0, 0},
        352,
        new Ability[] {
            AbilityList.drizzle
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.aqua_ring,
            MoveList.aqua_tail,
            MoveList.body_slam,
            MoveList.calm_mind,
            MoveList.double_edge,
            MoveList.hydro_pump,
            MoveList.ice_beam,
            MoveList.muddy_water,
            MoveList.origin_pulse,
            MoveList.scary_face,
            MoveList.sheer_cold,
            MoveList.water_pulse,
            MoveList.water_spout
        },
        100, 100, 90, 150, 140, 90
    );

    public static final Pokemon kyogre_primal = new Pokemon( // Reversão Primitiva
        "Kyogre",
        "Primal",
        false,
        3,
        TypeList.water,
        new double[] {0, 0},
        430,
        new Ability[] {
            AbilityList.primordial_sea
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.aqua_ring,
            MoveList.aqua_tail,
            MoveList.body_slam,
            MoveList.calm_mind,
            MoveList.double_edge,
            MoveList.hydro_pump,
            MoveList.ice_beam,
            MoveList.muddy_water,
            MoveList.origin_pulse,
            MoveList.scary_face,
            MoveList.sheer_cold,
            MoveList.water_pulse,
            MoveList.water_spout
        },
        PokemonList.kyogre,
        false,
        100, 150, 90, 180, 160, 90
    );

    public static final Pokemon groudon = new Pokemon(
        "Groudon",
        3,
        TypeList.ground,
        new double[] {0, 0},
        950,
        new Ability[] {
            AbilityList.drought
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.bulk_up,
            MoveList.earth_power,
            MoveList.earthquake,
            MoveList.eruption,
            MoveList.fire_blast,
            MoveList.fissure,
            MoveList.hammer_arm,
            MoveList.lava_plume,
            MoveList.mud_shot,
            MoveList.precipice_blades,
            MoveList.rest,
            MoveList.scary_face,
            MoveList.solar_beam
        },
        100, 150, 140, 100, 90, 90
    );

    public static final Pokemon groudon_primal = new Pokemon( // Reversão Primitiva
        "Groudon",
        "Primal",
        false,
        3,
        TypeList.ground, TypeList.fire,
        new double[] {0, 0},
        999.7,
        new Ability[] {
            AbilityList.desolate_land
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.bulk_up,
            MoveList.earth_power,
            MoveList.earthquake,
            MoveList.eruption,
            MoveList.fire_blast,
            MoveList.fissure,
            MoveList.hammer_arm,
            MoveList.lava_plume,
            MoveList.mud_shot,
            MoveList.precipice_blades,
            MoveList.rest,
            MoveList.scary_face,
            MoveList.solar_beam
        },
        PokemonList.groudon,
        false,
        100, 180, 160, 150, 90, 90
    );

    public static final Pokemon rayquaza = new Pokemon(
        "Rayquaza",
        3,
        TypeList.dragon, TypeList.flying,
        new double[] {0, 0},
        206.5,
        new Ability[] {
            AbilityList.air_lock
        },
        new Move[] {
            MoveList.air_slash,
            MoveList.ancient_power,
            MoveList.crunch,
            MoveList.dragon_ascent,
            MoveList.dragon_dance,
            MoveList.dragon_pulse,
            MoveList.extreme_speed,
            MoveList.fly,
            MoveList.hurricane,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.outrage,
            MoveList.rest,
            MoveList.scary_face,
            MoveList.twister
        },
        105, 150, 90, 150, 90, 95
    );

    public static final Pokemon rayquaza_mega = new Pokemon( // Mega Evolução
        "Rayquaza",
        "Mega",
        false,
        3,
        TypeList.dragon, TypeList.flying,
        new double[] {0, 0},
        392,
        new Ability[] {
            AbilityList.delta_stream
        },
        new Move[] {
            MoveList.air_slash,
            MoveList.ancient_power,
            MoveList.crunch,
            MoveList.dragon_ascent,
            MoveList.dragon_dance,
            MoveList.dragon_pulse,
            MoveList.extreme_speed,
            MoveList.fly,
            MoveList.hurricane,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.outrage,
            MoveList.rest,
            MoveList.scary_face,
            MoveList.twister
        },
        PokemonList.rayquaza,
        false,
        105, 180, 100, 180, 100, 115
    );

    public static final Pokemon jirachi = new Pokemon(
        "Jirachi",
        3,
        TypeList.steel, TypeList.psychic,
        new double[] {0, 0},
        1.1,
        new Ability[] {
            AbilityList.serene_grace
        },
        new Move[] {
            MoveList.confusion,
            MoveList.cosmic_power,
            MoveList.doom_desire,
            MoveList.double_edge,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.last_resort,
            MoveList.life_dew,
            MoveList.meteor_mash,
            MoveList.psychic,
            MoveList.rest,
            MoveList.swift,
            MoveList.wish,
            MoveList.zen_headbutt
        },
        100, 100, 100, 100, 100, 100
    );

    public static final Pokemon deoxys = new Pokemon(
        "Deoxys",
        "Normal",
        3,
        TypeList.psychic,
        new double[] {0, 0},
        60.8,
        new Ability[] {
            AbilityList.pressure
        },
        new Move[] {
            MoveList.agility,
            MoveList.amnesia,
            MoveList.cosmic_power,
            MoveList.counter,
            MoveList.double_team,
            MoveList.extreme_speed,
            MoveList.gravity,
            MoveList.hyper_beam,
            MoveList.iron_defense,
            MoveList.knock_off,
            MoveList.leer,
            MoveList.mirror_coat,
            MoveList.night_shade,
            MoveList.protect,
            MoveList.psychic,
            MoveList.psycho_boost,
            MoveList.psyshock,
            MoveList.recover,
            MoveList.skill_swap,
            MoveList.spikes,
            MoveList.superpower,
            MoveList.swift,
            MoveList.taunt,
            MoveList.teleport,
            MoveList.wrap,
            MoveList.zap_cannon,
            MoveList.zen_headbutt
        },
        50, 150, 50, 150, 50, 150
    );

    public static final Pokemon deoxys_attack = new Pokemon( // Forma Alternativa
        "Deoxys",
        "Attack",
        true,
        3,
        TypeList.psychic,
        new double[] {0, 0},
        60.8,
        new Ability[] {
            AbilityList.pressure
        },
        new Move[] {
            MoveList.agility,
            MoveList.amnesia,
            MoveList.cosmic_power,
            MoveList.counter,
            MoveList.double_team,
            MoveList.extreme_speed,
            MoveList.gravity,
            MoveList.hyper_beam,
            MoveList.iron_defense,
            MoveList.knock_off,
            MoveList.leer,
            MoveList.mirror_coat,
            MoveList.night_shade,
            MoveList.protect,
            MoveList.psychic,
            MoveList.psycho_boost,
            MoveList.psyshock,
            MoveList.recover,
            MoveList.skill_swap,
            MoveList.spikes,
            MoveList.superpower,
            MoveList.swift,
            MoveList.taunt,
            MoveList.teleport,
            MoveList.wrap,
            MoveList.zap_cannon,
            MoveList.zen_headbutt
        },
        PokemonList.deoxys,
        false,
        50, 180, 20, 180, 20, 150
    );

    public static final Pokemon deoxys_defense = new Pokemon( // Forma Alternativa
        "Deoxys",
        "Defense",
        true,
        3,
        TypeList.psychic,
        new double[] {0, 0},
        60.8,
        new Ability[] {
            AbilityList.pressure
        },
        new Move[] {
            MoveList.agility,
            MoveList.amnesia,
            MoveList.cosmic_power,
            MoveList.counter,
            MoveList.double_team,
            MoveList.extreme_speed,
            MoveList.gravity,
            MoveList.hyper_beam,
            MoveList.iron_defense,
            MoveList.knock_off,
            MoveList.leer,
            MoveList.mirror_coat,
            MoveList.night_shade,
            MoveList.protect,
            MoveList.psychic,
            MoveList.psycho_boost,
            MoveList.psyshock,
            MoveList.recover,
            MoveList.skill_swap,
            MoveList.spikes,
            MoveList.superpower,
            MoveList.swift,
            MoveList.taunt,
            MoveList.teleport,
            MoveList.wrap,
            MoveList.zap_cannon,
            MoveList.zen_headbutt
        },
        PokemonList.deoxys,
        false,
        50, 70, 160, 70, 160, 90
    );

    public static final Pokemon deoxys_speed = new Pokemon( // Forma Alternativa
        "Deoxys",
        "Speed",
        true,
        3,
        TypeList.psychic,
        new double[] {0, 0},
        60.8,
        new Ability[] {
            AbilityList.pressure
        },
        new Move[] {
            MoveList.agility,
            MoveList.amnesia,
            MoveList.cosmic_power,
            MoveList.counter,
            MoveList.double_team,
            MoveList.extreme_speed,
            MoveList.gravity,
            MoveList.hyper_beam,
            MoveList.iron_defense,
            MoveList.knock_off,
            MoveList.leer,
            MoveList.mirror_coat,
            MoveList.night_shade,
            MoveList.protect,
            MoveList.psychic,
            MoveList.psycho_boost,
            MoveList.psyshock,
            MoveList.recover,
            MoveList.skill_swap,
            MoveList.spikes,
            MoveList.superpower,
            MoveList.swift,
            MoveList.taunt,
            MoveList.teleport,
            MoveList.wrap,
            MoveList.zap_cannon,
            MoveList.zen_headbutt
        },
        PokemonList.deoxys,
        false,
        50, 95, 90, 95, 90, 180
    );


    /* Geração 4 */

    public static final Pokemon turtwig = new Pokemon(
        "Turtwig",
        4,
        TypeList.grass,
        new double[] {87.5, 12.5},
        10.2,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.shell_armor
        },
        new Move[] {
            MoveList.absorb,
            MoveList.bite,
            MoveList.crunch,
            MoveList.curse,
            MoveList.giga_drain,
            MoveList.leaf_storm,
            MoveList.leech_seed,
            MoveList.mega_drain,
            MoveList.razor_leaf,
            MoveList.synthesis,
            MoveList.tackle,
            MoveList.withdraw
        },
        55, 68, 64, 45, 55, 31
    );

    public static final Pokemon grotle = new Pokemon(
        "Grotle",
        4,
        TypeList.grass,
        new double[] {87.5, 12.5},
        97,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.shell_armor
        },
        new Move[] {
            MoveList.absorb,
            MoveList.bite,
            MoveList.crunch,
            MoveList.curse,
            MoveList.giga_drain,
            MoveList.leaf_storm,
            MoveList.leech_seed,
            MoveList.mega_drain,
            MoveList.razor_leaf,
            MoveList.synthesis,
            MoveList.tackle,
            MoveList.withdraw
        },
        75, 89, 85, 55, 65, 36
    );

    public static final Pokemon torterra = new Pokemon(
        "Torterra",
        4,
        TypeList.grass, TypeList.ground,
        new double[] {87.5, 12.5},
        310,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.shell_armor
        },
        new Move[] {
            MoveList.absorb,
            MoveList.bite,
            MoveList.crunch,
            MoveList.curse,
            MoveList.earthquake,
            MoveList.giga_drain,
            MoveList.headlong_rush,
            MoveList.leaf_storm,
            MoveList.leech_seed,
            MoveList.mega_drain,
            MoveList.razor_leaf,
            MoveList.synthesis,
            MoveList.tackle,
            MoveList.withdraw,
            MoveList.wood_hammer
        },
        95, 109, 105, 75, 85, 56
    );

    public static final Pokemon chimchar = new Pokemon(
        "Chimchar",
        4,
        TypeList.fire,
        new double[] {87.5, 12.5},
        6.2,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.iron_fist
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.ember,
            MoveList.facade,
            MoveList.fire_spin,
            MoveList.flame_wheel,
            MoveList.flamethrower,
            MoveList.fury_swipes,
            MoveList.leer,
            MoveList.nasty_plot,
            MoveList.scratch,
            MoveList.slack_off,
            MoveList.taunt,
            MoveList.torment
        },
        44, 58, 44, 58, 44, 61
    );

    public static final Pokemon monferno = new Pokemon(
        "Monferno",
        4,
        TypeList.fire, TypeList.fighting,
        new double[] {87.5, 12.5},
        22,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.iron_fist
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.close_combat,
            MoveList.ember,
            MoveList.facade,
            MoveList.feint,
            MoveList.fire_spin,
            MoveList.flame_wheel,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.fury_swipes,
            MoveList.leer,
            MoveList.mach_punch,
            MoveList.nasty_plot,
            MoveList.scratch,
            MoveList.slack_off,
            MoveList.taunt,
            MoveList.torment
        },
        64, 78, 52, 78, 52, 81
    );

    public static final Pokemon infernape = new Pokemon(
        "Infernape",
        4,
        TypeList.fire, TypeList.fighting,
        new double[] {87.5, 12.5},
        55,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.iron_fist
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.calm_mind,
            MoveList.close_combat,
            MoveList.ember,
            MoveList.facade,
            MoveList.feint,
            MoveList.fire_spin,
            MoveList.flame_wheel,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.fury_swipes,
            MoveList.leer,
            MoveList.mach_punch,
            MoveList.nasty_plot,
            MoveList.raging_fury,
            MoveList.scratch,
            MoveList.slack_off,
            MoveList.taunt,
            MoveList.torment
        },
        76, 104, 71, 104, 71, 108
    );

    public static final Pokemon piplup = new Pokemon(
        "Piplup",
        4,
        TypeList.water,
        new double[] {87.5, 12.5},
        5.2,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.competitive
        },
        new Move[] {
            MoveList.brine,
            MoveList.bubble_beam,
            MoveList.charm,
            MoveList.drill_peck,
            MoveList.fury_attack,
            MoveList.growl,
            MoveList.hydro_pump,
            MoveList.mist,
            MoveList.peck,
            MoveList.pound,
            MoveList.swagger,
            MoveList.water_gun,
            MoveList.whirlpool
        },
        53, 51, 53, 61, 56, 40
    );

    public static final Pokemon prinplup = new Pokemon(
        "Prinplup",
        4,
        TypeList.water,
        new double[] {87.5, 12.5},
        23,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.competitive
        },
        new Move[] {
            MoveList.brine,
            MoveList.bubble_beam,
            MoveList.charm,
            MoveList.drill_peck,
            MoveList.fury_attack,
            MoveList.growl,
            MoveList.hydro_pump,
            MoveList.metal_claw,
            MoveList.mist,
            MoveList.peck,
            MoveList.pound,
            MoveList.swagger,
            MoveList.tackle,
            MoveList.water_gun,
            MoveList.whirlpool
        },
        64, 66, 68, 81, 76, 50
    );

    public static final Pokemon empoleon = new Pokemon(
        "Empoleon",
        4,
        TypeList.water, TypeList.steel,
        new double[] {87.5, 12.5},
        84.5,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.competitive
        },
        new Move[] {
            MoveList.aqua_jet,
            MoveList.brine,
            MoveList.bubble_beam,
            MoveList.charm,
            MoveList.drill_peck,
            MoveList.fury_attack,
            MoveList.growl,
            MoveList.hydro_pump,
            MoveList.metal_claw,
            MoveList.mist,
            MoveList.peck,
            MoveList.pound,
            MoveList.swagger,
            MoveList.swords_dance,
            MoveList.tackle,
            MoveList.water_gun,
            MoveList.wave_crash,
            MoveList.whirlpool
        },
        84, 86, 88, 111, 101, 60
    );

    public static final Pokemon riolu = new Pokemon(
        "Riolu",
        4,
        TypeList.fighting,
        new double[] {87.5, 12.5},
        20.2,
        new Ability[] {
            AbilityList.steadfast,
            AbilityList.inner_focus,
            AbilityList.prankster
        },
        new Move[] {
            MoveList.copycat,
            MoveList.counter,
            MoveList.endure,
            MoveList.feint,
            MoveList.final_gambit,
            MoveList.force_palm,
            MoveList.helping_hand,
            MoveList.metal_claw,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.reversal,
            MoveList.rock_smash,
            MoveList.screech,
            MoveList.swords_dance,
            MoveList.vacuum_wave,
            MoveList.work_up
        },
        40, 70, 40, 35, 40, 60
    );

    public static final Pokemon lucario = new Pokemon(
        "Lucario",
        4,
        TypeList.fighting, TypeList.steel,
        new double[] {87.5, 12.5},
        54,
        new Ability[] {
            AbilityList.steadfast,
            AbilityList.inner_focus,
            AbilityList.justified
        },
        new Move[] {
            MoveList.aura_sphere,
            MoveList.bone_rush,
            MoveList.calm_mind,
            MoveList.close_combat,
            MoveList.copycat,
            MoveList.counter,
            MoveList.detect,
            MoveList.dragon_pulse,
            MoveList.endure,
            MoveList.extreme_speed,
            MoveList.feint,
            MoveList.final_gambit,
            MoveList.force_palm,
            MoveList.heal_pulse,
            MoveList.helping_hand,
            MoveList.life_dew,
            MoveList.metal_claw,
            MoveList.metal_sound,
            MoveList.meteor_mash,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.reversal,
            MoveList.rock_smash,
            MoveList.screech,
            MoveList.swords_dance,
            MoveList.vacuum_wave,
            MoveList.work_up
        },
        70, 110, 70, 115, 70, 90
    );

    public static final Pokemon lucario_mega = new Pokemon( // Mega Evolução
        "Lucario",
        "Mega",
        false,
        4,
        TypeList.fighting, TypeList.steel,
        new double[] {87.5, 12.5},
        57.5,
        new Ability[] {
            AbilityList.adaptability
        },
        new Move[] {
            MoveList.aura_sphere,
            MoveList.bone_rush,
            MoveList.calm_mind,
            MoveList.close_combat,
            MoveList.copycat,
            MoveList.counter,
            MoveList.detect,
            MoveList.dragon_pulse,
            MoveList.endure,
            MoveList.extreme_speed,
            MoveList.feint,
            MoveList.final_gambit,
            MoveList.force_palm,
            MoveList.heal_pulse,
            MoveList.helping_hand,
            MoveList.life_dew,
            MoveList.metal_claw,
            MoveList.metal_sound,
            MoveList.meteor_mash,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.reversal,
            MoveList.rock_smash,
            MoveList.screech,
            MoveList.swords_dance,
            MoveList.vacuum_wave,
            MoveList.work_up
        },
        PokemonList.lucario,
        false,
        70, 145, 88, 140, 70, 112
    );

    public static final Pokemon togekiss = new Pokemon(
        "Togekiss",
        4,
        TypeList.fairy, TypeList.flying,
        new double[] {87.5, 12.5},
        38,
        new Ability[] {
            AbilityList.hustle,
            AbilityList.serene_grace,
            AbilityList.super_luck
        },
        new Move[] {
            MoveList.after_you,
            MoveList.air_slash,
            MoveList.ancient_power,
            MoveList.aura_sphere,
            MoveList.baton_pass,
            MoveList.charm,
            MoveList.double_edge,
            MoveList.extreme_speed,
            MoveList.fairy_wind,
            MoveList.follow_me,
            MoveList.growl,
            MoveList.last_resort,
            MoveList.life_dew,
            MoveList.metronome,
            MoveList.pound,
            MoveList.safeguard,
            MoveList.sky_attack,
            MoveList.sweet_kiss,
            MoveList.tri_attack,
            MoveList.wish,
            MoveList.yawn
        },
        85, 50, 95, 120, 115, 80
    );

    public static final Pokemon uxie = new Pokemon(
        "Uxie",
        "Sinnoh",
        4,
        TypeList.psychic,
        new double[] {0, 0},
        0.3,
        new Ability[] {
            AbilityList.levitate,
            AbilityList.magic_guard
        },
        new Move[] {
            MoveList.ally_switch,
            MoveList.amnesia,
            MoveList.confusion,
            MoveList.endure,
            MoveList.expanding_force,
            MoveList.extrasensory,
            MoveList.flail,
            MoveList.future_sight,
            MoveList.imprison,
            MoveList.iron_tail,
            MoveList.magic_room,
            MoveList.memento,
            MoveList.mystical_power,
            MoveList.psybeam,
            MoveList.psychic,
            MoveList.psycho_cut,
            MoveList.rest,
            MoveList.round,
            MoveList.safeguard,
            MoveList.snore,
            MoveList.swift,
            MoveList.tri_attack,
            MoveList.wonder_room,
            MoveList.yawn
        },
        75, 75, 130, 75, 130, 95
    );

    public static final Pokemon uxie_bralia = new Pokemon( // Forma Regional
        "Uxie",
        "Bralia",
        true,
        4,
        TypeList.dark,
        new double[] {0, 0},
        0.3,
        new Ability[] {
            AbilityList.prankster,
            AbilityList.magic_guard
        },
        new Move[] {
            MoveList.ally_switch,
            MoveList.amnesia,
            MoveList.dark_pulse,
            MoveList.endure,
            MoveList.feint_attack,
            MoveList.flail,
            MoveList.foul_play,
            MoveList.iron_tail,
            MoveList.knock_off,
            MoveList.magic_coat,
            MoveList.magic_room,
            MoveList.mystical_corruption,
            MoveList.night_slash,
            MoveList.parting_shot,
            MoveList.payback,
            MoveList.perish_song,
            MoveList.rest,
            MoveList.round,
            MoveList.snore,
            MoveList.swift,
            MoveList.taunt,
            MoveList.tri_attack,
            MoveList.wonder_room,
            MoveList.yawn
        },
        PokemonList.uxie,
        false,
        85, 80, 130, 80, 130, 75
    );

    public static final Pokemon mesprit = new Pokemon(
        "Mesprit",
        "Sinnoh",
        4,
        TypeList.psychic,
        new double[] {0, 0},
        0.3,
        new Ability[] {
            AbilityList.levitate,
            AbilityList.unaware
        },
        new Move[] {
            MoveList.ally_switch,
            MoveList.charm,
            MoveList.confusion,
            MoveList.copycat,
            MoveList.expanding_force,
            MoveList.extrasensory,
            MoveList.flatter,
            MoveList.future_sight,
            MoveList.healing_wish,
            MoveList.imprison,
            MoveList.iron_tail,
            MoveList.magic_room,
            MoveList.mystical_power,
            MoveList.protect,
            MoveList.psybeam,
            MoveList.psychic,
            MoveList.psycho_cut,
            MoveList.rest,
            MoveList.round,
            MoveList.safeguard,
            MoveList.snore,
            MoveList.swift,
            MoveList.tri_attack,
            MoveList.wonder_room
        },
        80, 105, 105, 105, 105, 80
    );

    public static final Pokemon mesprit_bralia = new Pokemon( // Forma Regional
        "Mesprit",
        "Bralia",
        true,
        4,
        TypeList.dark,
        new double[] {0, 0},
        0.3,
        new Ability[] {
            AbilityList.prankster,
            AbilityList.unaware
        },
        new Move[] {
            MoveList.ally_switch,
            MoveList.charm,
            MoveList.copycat,
            MoveList.dark_pulse,
            MoveList.feint_attack,
            MoveList.flatter,
            MoveList.foul_play,
            MoveList.iron_tail,
            MoveList.knock_off,
            MoveList.magic_coat,
            MoveList.magic_room,
            MoveList.mystical_corruption,
            MoveList.night_slash,
            MoveList.parting_shot,
            MoveList.payback,
            MoveList.protect,
            MoveList.rest,
            MoveList.round,
            MoveList.snatch,
            MoveList.snore,
            MoveList.swift,
            MoveList.taunt,
            MoveList.tri_attack,
            MoveList.wonder_room
        },
        PokemonList.mesprit,
        false,
        75, 110, 95, 110, 95, 95
    );

    public static final Pokemon azelf = new Pokemon(
        "Azelf",
        "Sinnoh",
        4,
        TypeList.psychic,
        new double[] {0, 0},
        0.3,
        new Ability[] {
            AbilityList.levitate,
            AbilityList.sheer_force
        },
        new Move[] {
            MoveList.ally_switch,
            MoveList.assurance,
            MoveList.confusion,
            MoveList.detect,
            MoveList.expanding_force,
            MoveList.explosion,
            MoveList.extrasensory,
            MoveList.future_sight,
            MoveList.imprison,
            MoveList.iron_tail,
            MoveList.last_resort,
            MoveList.magic_room,
            MoveList.mystical_power,
            MoveList.nasty_plot,
            MoveList.payback,
            MoveList.psybeam,
            MoveList.psychic,
            MoveList.psycho_cut,
            MoveList.rest,
            MoveList.round,
            MoveList.safeguard,
            MoveList.self_destruct,
            MoveList.snore,
            MoveList.swift,
            MoveList.tri_attack,
            MoveList.uproar,
            MoveList.wonder_room
        },
        75, 125, 70, 125, 70, 115
    );

    public static final Pokemon azelf_bralia = new Pokemon( // Forma Regional
        "Azelf",
        "Bralia",
        true,
        4,
        TypeList.dark,
        new double[] {0, 0},
        0.3,
        new Ability[] {
            AbilityList.prankster,
            AbilityList.sheer_force
        },
        new Move[] {
            MoveList.ally_switch,
            MoveList.assurance,
            MoveList.dark_pulse,
            MoveList.destiny_bond,
            MoveList.detect,
            MoveList.feint_attack,
            MoveList.foul_play,
            MoveList.iron_tail,
            MoveList.knock_off,
            MoveList.last_resort,
            MoveList.magic_coat,
            MoveList.magic_room,
            MoveList.mystical_corruption,
            MoveList.nasty_plot,
            MoveList.night_slash,
            MoveList.parting_shot,
            MoveList.payback,
            MoveList.rest,
            MoveList.round,
            MoveList.self_destruct,
            MoveList.snore,
            MoveList.sucker_punch,
            MoveList.swift,
            MoveList.taunt,
            MoveList.tri_attack,
            MoveList.uproar,
            MoveList.wonder_room
        },
        PokemonList.azelf,
        false,
        70, 135, 60, 135, 60, 120
    );

    public static final Pokemon dialga = new Pokemon(
        "Dialga",
        "Altered",
        4,
        TypeList.steel, TypeList.dragon,
        new double[] {0, 0},
        683,
        new Ability[] {
            AbilityList.pressure,
            AbilityList.telepathy
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.aura_sphere,
            MoveList.dragon_breath,
            MoveList.dragon_claw,
            MoveList.earth_power,
            MoveList.flash_cannon,
            MoveList.iron_tail,
            MoveList.metal_burst,
            MoveList.metal_claw,
            MoveList.power_gem,
            MoveList.roar_of_time,
            MoveList.scary_face,
            MoveList.slash
        },
        100, 120, 120, 150, 100, 90
    );

    public static final Pokemon dialga_origin = new Pokemon( // Forma Alternativa
        "Dialga",
        "Origin",
        true,
        4,
        TypeList.steel, TypeList.dragon,
        new double[] {0, 0},
        850,
        new Ability[] {
            AbilityList.metatype,
            AbilityList.immovability
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.aura_sphere,
            MoveList.dragon_breath,
            MoveList.dragon_claw,
            MoveList.earth_power,
            MoveList.flash_cannon,
            MoveList.iron_tail,
            MoveList.metal_burst,
            MoveList.metal_claw,
            MoveList.power_gem,
            MoveList.roar_of_time,
            MoveList.scary_face,
            MoveList.slash
        },
        PokemonList.dialga,
        false,
        100, 100, 120, 150, 120, 90
    );

    public static final Pokemon palkia = new Pokemon(
        "Palkia",
        "Altered",
        4,
        TypeList.water, TypeList.dragon,
        new double[] {0, 0},
        336,
        new Ability[] {
            AbilityList.pressure,
            AbilityList.telepathy
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.aqua_ring,
            MoveList.aqua_tail,
            MoveList.aura_sphere,
            MoveList.dragon_breath,
            MoveList.earth_power,
            MoveList.hydro_pump,
            MoveList.power_gem,
            MoveList.scary_face,
            MoveList.slash,
            MoveList.spacial_rend,
            MoveList.water_pulse
        },
        90, 120, 100, 150, 120, 100
    );

    public static final Pokemon palkia_origin = new Pokemon( // Forma Alternativa
        "Palkia",
        "Origin",
        true,
        4,
        TypeList.water, TypeList.dragon,
        new double[] {0, 0},
        660,
        new Ability[] {
            AbilityList.metatype,
            AbilityList.unstoppability
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.aqua_ring,
            MoveList.aqua_tail,
            MoveList.aura_sphere,
            MoveList.dragon_breath,
            MoveList.earth_power,
            MoveList.hydro_pump,
            MoveList.power_gem,
            MoveList.scary_face,
            MoveList.slash,
            MoveList.spacial_rend,
            MoveList.water_pulse
        },
        PokemonList.palkia,
        false,
        90, 100, 100, 150, 120, 120
    );

    public static final Pokemon heatran = new Pokemon(
        "Heatran",
        4,
        TypeList.fire, TypeList.steel,
        new double[] {50, 50},
        430,
        new Ability[] {
            AbilityList.flash_fire,
            AbilityList.flame_body
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.crunch,
            MoveList.earth_power,
            MoveList.fire_fang,
            MoveList.fire_spin,
            MoveList.heat_wave,
            MoveList.iron_head,
            MoveList.lava_plume,
            MoveList.leer,
            MoveList.magma_storm,
            MoveList.metal_claw,
            MoveList.metal_sound,
            MoveList.scary_face,
            MoveList.stone_edge
        },
        91, 90, 106, 130, 106, 77
    );

    public static final Pokemon regigigas = new Pokemon(
        "Regigigas",
        4,
        TypeList.normal,
        new double[] {0, 0},
        420,
        new Ability[] {
            AbilityList.slow_start
        },
        new Move[] {
            MoveList.body_press,
            MoveList.confuse_ray,
            MoveList.crush_grip,
            MoveList.facade,
            MoveList.giga_impact,
            MoveList.hammer_arm,
            MoveList.heavy_slam,
            MoveList.knock_off,
            MoveList.mega_punch,
            MoveList.payback,
            MoveList.pound,
            MoveList.protect,
            MoveList.stomp,
            MoveList.wide_guard,
            MoveList.zen_headbutt
        },
        110, 160, 110, 80, 110, 100
    );

    public static final Pokemon giratina = new Pokemon(
        "Giratina",
        "Altered",
        4,
        TypeList.ghost, TypeList.dragon,
        new double[] {0, 0},
        750,
        new Ability[] {
            AbilityList.pressure,
            AbilityList.telepathy
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.aura_sphere,
            MoveList.defog,
            MoveList.destiny_bond,
            MoveList.dragon_breath,
            MoveList.dragon_claw,
            MoveList.earth_power,
            MoveList.hex,
            MoveList.pain_split,
            MoveList.scary_face,
            MoveList.shadow_claw,
            MoveList.shadow_force,
            MoveList.shadow_sneak,
            MoveList.slash
        },
        150, 100, 120, 100, 120, 90
    );

    public static final Pokemon giratina_origin = new Pokemon( // Forma Alternativa
        "Giratina",
        "Origin",
        true,
        4,
        TypeList.ghost, TypeList.dragon,
        new double[] {0, 0},
        650,
        new Ability[] {
            AbilityList.levitate,
            AbilityList.antithesis
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.aura_sphere,
            MoveList.defog,
            MoveList.destiny_bond,
            MoveList.dragon_breath,
            MoveList.dragon_claw,
            MoveList.earth_power,
            MoveList.hex,
            MoveList.pain_split,
            MoveList.scary_face,
            MoveList.shadow_claw,
            MoveList.shadow_force,
            MoveList.shadow_sneak,
            MoveList.slash
        },
        PokemonList.giratina,
        false,
        150, 120, 100, 120, 100, 90
    );

    public static final Pokemon cresselia = new Pokemon(
        "Cresselia",
        4,
        TypeList.psychic,
        new double[] {0, 100},
        85.6,
        new Ability[] {
            AbilityList.levitate
        },
        new Move[] {
            MoveList.ally_switch,
            MoveList.aurora_beam,
            MoveList.confusion,
            MoveList.double_team,
            MoveList.future_sight,
            MoveList.lunar_blessing,
            MoveList.lunar_dance,
            MoveList.mist,
            MoveList.moonblast,
            MoveList.moonlight,
            MoveList.psybeam,
            MoveList.psychic,
            MoveList.psycho_cut,
            MoveList.safeguard,
            MoveList.slash
        },
        120, 70, 110, 75, 120, 85
    );

    public static final Pokemon phione = new Pokemon(
        "Phione",
        4,
        TypeList.water,
        new double[] {0, 0},
        3.1,
        new Ability[] {
            AbilityList.hydration
        },
        new Move[] {
            MoveList.acid_armor,
            MoveList.aqua_ring,
            MoveList.bubble_beam,
            MoveList.charm,
            MoveList.dive,
            MoveList.rain_dance,
            MoveList.supersonic,
            MoveList.take_heart,
            MoveList.water_gun,
            MoveList.water_pulse,
            MoveList.whirlpool
        },
        80, 80, 80, 80, 80, 80
    );

    public static final Pokemon manaphy = new Pokemon(
        "Manaphy",
        4,
        TypeList.water,
        new double[] {0, 0},
        1.4,
        new Ability[] {
            AbilityList.hydration
        },
        new Move[] {
            MoveList.acid_armor,
            MoveList.aqua_ring,
            MoveList.bubble_beam,
            MoveList.charm,
            MoveList.dive,
            MoveList.heart_swap,
            MoveList.rain_dance,
            MoveList.supersonic,
            MoveList.tail_glow,
            MoveList.take_heart,
            MoveList.water_gun,
            MoveList.water_pulse,
            MoveList.whirlpool
        },
        100, 100, 100, 100, 100, 100
    );

    public static final Pokemon darkrai = new Pokemon(
        "Darkrai",
        4,
        TypeList.dark,
        new double[] {0, 0},
        50.5,
        new Ability[] {
            AbilityList.bad_dreams
        },
        new Move[] {
            MoveList.dark_pulse,
            MoveList.dark_void,
            MoveList.disable,
            MoveList.double_team,
            MoveList.dream_eater,
            MoveList.haze,
            MoveList.hypnosis,
            MoveList.nasty_plot,
            MoveList.night_shade,
            MoveList.quick_attack,
            MoveList.sucker_punch
        },
        70, 90, 90, 135, 90, 125
    );

    public static final Pokemon shaymin = new Pokemon(
        "Shaymin",
        "Land",
        4,
        TypeList.grass,
        new double[] {0, 0},
        2.1,
        new Ability[] {
            AbilityList.natural_cure
        },
        new Move[] {
            MoveList.air_slash,
            MoveList.energy_ball,
            MoveList.grassy_terrain,
            MoveList.growth,
            MoveList.healing_wish,
            MoveList.leaf_storm,
            MoveList.leech_seed,
            MoveList.magical_leaf,
            MoveList.play_rough,
            MoveList.quick_attack,
            MoveList.seed_flare,
            MoveList.sweet_kiss,
            MoveList.sweet_scent,
            MoveList.synthesis,
            MoveList.worry_seed
        },
        100, 100, 100, 100, 100, 100
    );

    public static final Pokemon shaymin_sky = new Pokemon( // Forma Alternativa
        "Shaymin",
        "Sky",
        true,
        4,
        TypeList.grass, TypeList.flying,
        new double[] {0, 0},
        5.2,
        new Ability[] {
            AbilityList.serene_grace
        },
        new Move[] {
            MoveList.air_slash,
            MoveList.energy_ball,
            MoveList.grassy_terrain,
            MoveList.growth,
            MoveList.healing_wish,
            MoveList.leaf_storm,
            MoveList.leech_seed,
            MoveList.magical_leaf,
            MoveList.play_rough,
            MoveList.quick_attack,
            MoveList.seed_flare,
            MoveList.sweet_kiss,
            MoveList.sweet_scent,
            MoveList.synthesis,
            MoveList.worry_seed
        },
        PokemonList.shaymin,
        false,
        100, 103, 75, 120, 75, 127
    );

    public static final Pokemon arceus = new Pokemon(
        "Arceus",
        "Normal",
        4,
        TypeList.normal,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_fighting = new Pokemon( // Forma Alternativa
        "Arceus",
        "Fighting",
        true,
        4,
        TypeList.fighting,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_flying = new Pokemon( // Forma Alternativa
        "Arceus",
        "Flying",
        true,
        4,
        TypeList.flying,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_poison = new Pokemon( // Forma Alternativa
        "Arceus",
        "Poison",
        true,
        4,
        TypeList.poison,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_ground = new Pokemon( // Forma Alternativa
        "Arceus",
        "Ground",
        true,
        4,
        TypeList.ground,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_rock = new Pokemon( // Forma Alternativa
        "Arceus",
        "Rock",
        true,
        4,
        TypeList.rock,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_bug = new Pokemon( // Forma Alternativa
        "Arceus",
        "Bug",
        true,
        4,
        TypeList.bug,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_ghost = new Pokemon( // Forma Alternativa
        "Arceus",
        "Ghost",
        true,
        4,
        TypeList.ghost,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_steel = new Pokemon( // Forma Alternativa
        "Arceus",
        "Steel",
        true,
        4,
        TypeList.steel,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_fire = new Pokemon( // Forma Alternativa
        "Arceus",
        "Fire",
        true,
        4,
        TypeList.fire,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_water = new Pokemon( // Forma Alternativa
        "Arceus",
        "Water",
        true,
        4,
        TypeList.water,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_grass = new Pokemon( // Forma Alternativa
        "Arceus",
        "Grass",
        true,
        4,
        TypeList.grass,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_electric = new Pokemon( // Forma Alternativa
        "Arceus",
        "Electric",
        true,
        4,
        TypeList.electric,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_psychic = new Pokemon( // Forma Alternativa
        "Arceus",
        "Psychic",
        true,
        4,
        TypeList.psychic,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_ice = new Pokemon( // Forma Alternativa
        "Arceus",
        "Ice",
        true,
        4,
        TypeList.ice,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_dragon = new Pokemon( // Forma Alternativa
        "Arceus",
        "Dragon",
        true,
        4,
        TypeList.dragon,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_dark = new Pokemon( // Forma Alternativa
        "Arceus",
        "Dark",
        true,
        4,
        TypeList.dark,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );

    public static final Pokemon arceus_fairy = new Pokemon( // Forma Alternativa
        "Arceus",
        "Fairy",
        true,
        4,
        TypeList.fairy,
        new double[] {0, 0},
        320,
        new Ability[] {
            AbilityList.multitype
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.earth_power,
            MoveList.extreme_speed,
            MoveList.future_sight,
            MoveList.gravity,
            MoveList.healing_wish,
            MoveList.hyper_beam,
            MoveList.hyper_voice,
            MoveList.judgment,
            MoveList.perish_song,
            MoveList.recover,
            MoveList.seismic_toss
        },
        PokemonList.arceus,
        false,
        120, 120, 120, 120, 120, 120
    );


    /* Geração 5 */

    public static final Pokemon victini = new Pokemon(
        "Victini",
        5,
        TypeList.psychic, TypeList.fire,
        new double[] {0, 0},
        4,
        new Ability[] {
            AbilityList.victory_star
        },
        new Move[] {
            MoveList.confusion,
            MoveList.double_edge,
            MoveList.endure,
            MoveList.final_gambit,
            MoveList.flame_charge,
            MoveList.flare_blitz,
            MoveList.focus_energy,
            MoveList.headbutt,
            MoveList.incinerate,
            MoveList.inferno,
            MoveList.overheat,
            MoveList.quick_attack,
            MoveList.reversal,
            MoveList.searing_shot,
            MoveList.stored_power,
            MoveList.v_create,
            MoveList.work_up,
            MoveList.zen_headbutt
        },
        100, 100, 100, 100, 100, 100
    );

    public static final Pokemon snivy = new Pokemon(
        "Snivy",
        5,
        TypeList.grass,
        new double[] {87.5, 12.5},
        8.1,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.contrary
        },
        new Move[] {
            MoveList.coil,
            MoveList.gastro_acid,
            MoveList.giga_drain,
            MoveList.growth,
            MoveList.leaf_blade,
            MoveList.leaf_storm,
            MoveList.leech_seed,
            MoveList.leer,
            MoveList.magical_leaf,
            MoveList.mega_drain,
            MoveList.slam,
            MoveList.tackle,
            MoveList.vine_whip,
            MoveList.wrap
        },
        45, 45, 55, 45, 55, 63
    );

    public static final Pokemon servine = new Pokemon(
        "Servine",
        5,
        TypeList.grass,
        new double[] {87.5, 12.5},
        16,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.contrary
        },
        new Move[] {
            MoveList.coil,
            MoveList.gastro_acid,
            MoveList.giga_drain,
            MoveList.growth,
            MoveList.leaf_blade,
            MoveList.leaf_storm,
            MoveList.leech_seed,
            MoveList.leer,
            MoveList.magical_leaf,
            MoveList.mega_drain,
            MoveList.slam,
            MoveList.tackle,
            MoveList.vine_whip,
            MoveList.wrap
        },
        60, 60, 75, 60, 75, 83
    );

    public static final Pokemon serperior = new Pokemon(
        "Serperior",
        5,
        TypeList.grass,
        new double[] {87.5, 12.5},
        63,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.contrary
        },
        new Move[] {
            MoveList.coil,
            MoveList.gastro_acid,
            MoveList.giga_drain,
            MoveList.growth,
            MoveList.leaf_blade,
            MoveList.leaf_storm,
            MoveList.leech_seed,
            MoveList.leer,
            MoveList.magical_leaf,
            MoveList.mega_drain,
            MoveList.slam,
            MoveList.tackle,
            MoveList.vine_whip,
            MoveList.wrap
        },
        75, 75, 95, 75, 95, 113
    );

    public static final Pokemon tepig = new Pokemon(
        "Tepig",
        5,
        TypeList.fire,
        new double[] {87.5, 12.5},
        9.9,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.thick_fat
        },
        new Move[] {
            MoveList.assurance,
            MoveList.defense_curl,
            MoveList.ember,
            MoveList.endure,
            MoveList.flame_charge,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.head_smash,
            MoveList.heat_crash,
            MoveList.roar,
            MoveList.rollout,
            MoveList.smog,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.take_down
        },
        65, 63, 45, 45, 45, 45
    );

    public static final Pokemon pignite = new Pokemon(
        "Pignite",
        5,
        TypeList.fire, TypeList.fighting,
        new double[] {87.5, 12.5},
        55.5,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.thick_fat
        },
        new Move[] {
            MoveList.arm_thrust,
            MoveList.assurance,
            MoveList.defense_curl,
            MoveList.ember,
            MoveList.endure,
            MoveList.flame_charge,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.head_smash,
            MoveList.heat_crash,
            MoveList.roar,
            MoveList.rollout,
            MoveList.smog,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.take_down
        },
        90, 93, 55, 70, 55, 55
    );

    public static final Pokemon emboar = new Pokemon(
        "Emboar",
        5,
        TypeList.fire, TypeList.fighting,
        new double[] {87.5, 12.5},
        150,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.reckless
        },
        new Move[] {
            MoveList.arm_thrust,
            MoveList.assurance,
            MoveList.defense_curl,
            MoveList.ember,
            MoveList.endure,
            MoveList.flame_charge,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.hammer_arm,
            MoveList.head_smash,
            MoveList.heat_crash,
            MoveList.roar,
            MoveList.rollout,
            MoveList.smog,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.take_down
        },
        110, 123, 65, 100, 65, 65
    );

    public static final Pokemon oshawott = new Pokemon(
        "Oshawott",
        5,
        TypeList.water,
        new double[] {87.5, 12.5},
        5.9,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.shell_armor
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.aqua_jet,
            MoveList.aqua_tail,
            MoveList.encore,
            MoveList.focus_energy,
            MoveList.fury_cutter,
            MoveList.hydro_pump,
            MoveList.razor_shell,
            MoveList.retaliate,
            MoveList.soak,
            MoveList.swords_dance,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        55, 55, 45, 63, 45, 45
    );

    public static final Pokemon dewott = new Pokemon(
        "Dewott",
        5,
        TypeList.water,
        new double[] {87.5, 12.5},
        24.5,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.shell_armor
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.aqua_jet,
            MoveList.aqua_tail,
            MoveList.encore,
            MoveList.focus_energy,
            MoveList.fury_cutter,
            MoveList.hydro_pump,
            MoveList.razor_shell,
            MoveList.retaliate,
            MoveList.soak,
            MoveList.swords_dance,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        75, 75, 60, 83, 60, 60
    );

    public static final Pokemon samurott = new Pokemon(
        "Samurott",
        "Unova",
        5,
        TypeList.water,
        new double[] {87.5, 12.5},
        94.6,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.shell_armor
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.aqua_jet,
            MoveList.aqua_tail,
            MoveList.encore,
            MoveList.focus_energy,
            MoveList.fury_cutter,
            MoveList.hydro_pump,
            MoveList.megahorn,
            MoveList.razor_shell,
            MoveList.retaliate,
            MoveList.slash,
            MoveList.soak,
            MoveList.swords_dance,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        95, 100, 85, 108, 70, 70
    );

    public static final Pokemon samurott_hisui = new Pokemon( // Forma Regional
        "Samurott",
        "Hisui",
        true,
        5,
        TypeList.water, TypeList.dark,
        new double[] {87.5, 12.5},
        58.2,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.sharpness
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.aqua_jet,
            MoveList.aqua_tail,
            MoveList.ceaseless_edge,
            MoveList.encore,
            MoveList.focus_energy,
            MoveList.fury_cutter,
            MoveList.hydro_pump,
            MoveList.megahorn,
            MoveList.razor_shell,
            MoveList.retaliate,
            MoveList.slash,
            MoveList.soak,
            MoveList.sucker_punch,
            MoveList.swords_dance,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        PokemonList.samurott,
        false,
        90, 108, 80, 100, 65, 85
    );

    public static final Pokemon zorua = new Pokemon(
        "Zorua",
        "Unova",
        5,
        TypeList.dark,
        new double[] {87.5, 12.5},
        12.5,
        new Ability[] {
            AbilityList.illusion
        },
        new Move[] {
            MoveList.agility,
            MoveList.fake_tears,
            MoveList.foul_play,
            MoveList.fury_swipes,
            MoveList.hone_claws,
            MoveList.imprison,
            MoveList.knock_off,
            MoveList.leer,
            MoveList.nasty_plot,
            MoveList.night_daze,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.taunt,
            MoveList.torment
        },
        40, 65, 40, 80, 40, 65
    );

    public static final Pokemon zorua_hisui = new Pokemon( // Forma Regional
        "Zorua",
        "Hisui",
        true,
        5,
        TypeList.normal, TypeList.ghost,
        new double[] {87.5, 12.5},
        12.5,
        new Ability[] {
            AbilityList.illusion
        },
        new Move[] {
            MoveList.agility,
            MoveList.bitter_malice,
            MoveList.curse,
            MoveList.foul_play,
            MoveList.hone_claws,
            MoveList.knock_off,
            MoveList.leer,
            MoveList.nasty_plot,
            MoveList.scratch,
            MoveList.shadow_ball,
            MoveList.shadow_sneak,
            MoveList.spite,
            MoveList.taunt,
            MoveList.torment
        },
        PokemonList.zorua,
        false,
        35, 60, 40, 85, 40, 70
    );

    public static final Pokemon zoroark = new Pokemon(
        "Zoroark",
        "Unova",
        5,
        TypeList.dark,
        new double[] {87.5, 12.5},
        81.1,
        new Ability[] {
            AbilityList.illusion
        },
        new Move[] {
            MoveList.agility,
            MoveList.fake_tears,
            MoveList.foul_play,
            MoveList.fury_swipes,
            MoveList.hone_claws,
            MoveList.imprison,
            MoveList.knock_off,
            MoveList.leer,
            MoveList.nasty_plot,
            MoveList.night_daze,
            MoveList.night_slash,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.taunt,
            MoveList.torment,
            MoveList.u_turn
        },
        60, 105, 60, 120, 60, 105
    );

    public static final Pokemon zoroark_hisui = new Pokemon( // Forma Regional
        "Zoroark",
        "Hisui",
        true,
        5,
        TypeList.normal, TypeList.ghost,
        new double[] {87.5, 12.5},
        73,
        new Ability[] {
            AbilityList.illusion
        },
        new Move[] {
            MoveList.agility,
            MoveList.bitter_malice,
            MoveList.curse,
            MoveList.foul_play,
            MoveList.hone_claws,
            MoveList.knock_off,
            MoveList.leer,
            MoveList.nasty_plot,
            MoveList.scratch,
            MoveList.shadow_ball,
            MoveList.shadow_claw,
            MoveList.shadow_sneak,
            MoveList.spite,
            MoveList.taunt,
            MoveList.torment,
            MoveList.u_turn
        },
        PokemonList.zoroark,
        false,
        55, 100, 60, 125, 60, 110
    );

    public static final Pokemon cobalion = new Pokemon(
        "Cobalion",
        5,
        TypeList.steel, TypeList.fighting,
        new double[] {0, 0},
        250,
        new Ability[] {
            AbilityList.justified
        },
        new Move[] {
            MoveList.close_combat,
            MoveList.double_kick,
            MoveList.helping_hand,
            MoveList.iron_head,
            MoveList.leer,
            MoveList.metal_burst,
            MoveList.metal_claw,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.retaliate,
            MoveList.sacred_sword,
            MoveList.swords_dance,
            MoveList.take_down,
            MoveList.work_up
        },
        91, 90, 129, 90, 72, 108
    );

    public static final Pokemon terrakion = new Pokemon(
        "Terrakion",
        5,
        TypeList.rock, TypeList.fighting,
        new double[] {0, 0},
        260,
        new Ability[] {
            AbilityList.justified
        },
        new Move[] {
            MoveList.close_combat,
            MoveList.double_kick,
            MoveList.helping_hand,
            MoveList.leer,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.retaliate,
            MoveList.rock_slide,
            MoveList.sacred_sword,
            MoveList.smack_down,
            MoveList.stone_edge,
            MoveList.swords_dance,
            MoveList.take_down,
            MoveList.work_up
        },
        91, 129, 90, 72, 90, 108
    );

    public static final Pokemon virizion = new Pokemon(
        "Virizion",
        5,
        TypeList.grass, TypeList.fighting,
        new double[] {0, 0},
        200,
        new Ability[] {
            AbilityList.justified
        },
        new Move[] {
            MoveList.close_combat,
            MoveList.double_kick,
            MoveList.giga_drain,
            MoveList.helping_hand,
            MoveList.leaf_blade,
            MoveList.leer,
            MoveList.magical_leaf,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.retaliate,
            MoveList.sacred_sword,
            MoveList.swords_dance,
            MoveList.synthesis,
            MoveList.take_down,
            MoveList.work_up
        },
        91, 90, 72, 90, 129, 108
    );

    public static final Pokemon tornadus = new Pokemon(
        "Tornadus",
        "Incarnate",
        5,
        TypeList.flying,
        new double[] {100, 0},
        63,
        new Ability[] {
            AbilityList.prankster,
            AbilityList.defiant
        },
        new Move[] {
            MoveList.agility,
            MoveList.air_cutter,
            MoveList.air_slash,
            MoveList.astonish,
            MoveList.bite,
            MoveList.bleakwind_storm,
            MoveList.crunch,
            MoveList.extrasensory,
            MoveList.gust,
            MoveList.hammer_arm,
            MoveList.hurricane,
            MoveList.leer,
            MoveList.snowscape,
            MoveList.swagger,
            MoveList.tailwind,
            MoveList.thrash,
            MoveList.uproar
        },
        79, 115, 70, 125, 80, 111
    );

    public static final Pokemon tornadus_therian = new Pokemon( // Forma Alternativa
        "Tornadus",
        "Therian",
        true,
        5,
        TypeList.flying,
        new double[] {100, 0},
        63,
        new Ability[] {
            AbilityList.regenerator
        },
        new Move[] {
            MoveList.agility,
            MoveList.air_cutter,
            MoveList.air_slash,
            MoveList.astonish,
            MoveList.bite,
            MoveList.bleakwind_storm,
            MoveList.crunch,
            MoveList.extrasensory,
            MoveList.gust,
            MoveList.hammer_arm,
            MoveList.hurricane,
            MoveList.leer,
            MoveList.snowscape,
            MoveList.swagger,
            MoveList.tailwind,
            MoveList.thrash,
            MoveList.uproar
        },
        PokemonList.tornadus,
        false,
        79, 100, 80, 110, 90, 121
    );

    public static final Pokemon thundurus = new Pokemon(
        "Thundurus",
        "Incarnate",
        5,
        TypeList.electric, TypeList.flying,
        new double[] {100, 0},
        61,
        new Ability[] {
            AbilityList.prankster,
            AbilityList.defiant
        },
        new Move[] {
            MoveList.agility,
            MoveList.astonish,
            MoveList.bite,
            MoveList.charge,
            MoveList.crunch,
            MoveList.discharge,
            MoveList.hammer_arm,
            MoveList.leer,
            MoveList.rain_dance,
            MoveList.shock_wave,
            MoveList.swagger,
            MoveList.thrash,
            MoveList.thunder_shock,
            MoveList.thunder,
            MoveList.uproar,
            MoveList.volt_switch,
            MoveList.wildbolt_storm
        },
        79, 115, 70, 125, 80, 111
    );

    public static final Pokemon thundurus_therian = new Pokemon( // Forma Alternativa
        "Thundurus",
        "Therian",
        true,
        5,
        TypeList.electric, TypeList.flying,
        new double[] {100, 0},
        61,
        new Ability[] {
            AbilityList.volt_absorb
        },
        new Move[] {
            MoveList.agility,
            MoveList.astonish,
            MoveList.bite,
            MoveList.charge,
            MoveList.crunch,
            MoveList.discharge,
            MoveList.hammer_arm,
            MoveList.leer,
            MoveList.rain_dance,
            MoveList.shock_wave,
            MoveList.swagger,
            MoveList.thrash,
            MoveList.thunder_shock,
            MoveList.thunder,
            MoveList.uproar,
            MoveList.volt_switch,
            MoveList.wildbolt_storm
        },
        PokemonList.thundurus,
        false,
        79, 105, 70, 145, 80, 101
    );

    public static final Pokemon reshiram = new Pokemon(
        "Reshiram",
        5,
        TypeList.dragon, TypeList.fire,
        new double[] {0, 0},
        330,
        new Ability[] {
            AbilityList.turboblaze
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.blue_flare,
            MoveList.crunch,
            MoveList.dragon_breath,
            MoveList.dragon_pulse,
            MoveList.extrasensory,
            MoveList.fire_blast,
            MoveList.fire_fang,
            MoveList.flamethrower,
            MoveList.fusion_flare,
            MoveList.hyper_voice,
            MoveList.imprison,
            MoveList.noble_roar,
            MoveList.outrage,
            MoveList.slash
        },
        100, 120, 100, 150, 120, 90
    );

    public static final Pokemon zekrom = new Pokemon(
        "Zekrom",
        5,
        TypeList.dragon, TypeList.electric,
        new double[] {0, 0},
        345,
        new Ability[] {
            AbilityList.teravolt
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.bolt_strike,
            MoveList.crunch,
            MoveList.dragon_breath,
            MoveList.dragon_claw,
            MoveList.fusion_bolt,
            MoveList.hyper_voice,
            MoveList.imprison,
            MoveList.noble_roar,
            MoveList.outrage,
            MoveList.slash,
            MoveList.thunder_fang,
            MoveList.thunder,
            MoveList.thunderbolt,
            MoveList.zen_headbutt
        },
        100, 150, 120, 120, 100, 90
    );

    public static final Pokemon landorus = new Pokemon(
        "Landorus",
        "Incarnate",
        5,
        TypeList.ground, TypeList.flying,
        new double[] {100, 0},
        68,
        new Ability[] {
            AbilityList.sand_force,
            AbilityList.sheer_force
        },
        new Move[] {
            MoveList.block,
            MoveList.bulldoze,
            MoveList.earth_power,
            MoveList.earthquake,
            MoveList.extrasensory,
            MoveList.fissure,
            MoveList.hammer_arm,
            MoveList.imprison,
            MoveList.leer,
            MoveList.outrage,
            MoveList.rock_slide,
            MoveList.rock_tomb,
            MoveList.sand_tomb,
            MoveList.sandsear_storm,
            MoveList.sandstorm,
            MoveList.smack_down,
            MoveList.stone_edge
        },
        89, 125, 90, 115, 80, 101
    );

    public static final Pokemon landorus_therian = new Pokemon( // Forma Alternativa
        "Landorus",
        "Therian",
        true,
        5,
        TypeList.ground, TypeList.flying,
        new double[] {100, 0},
        68,
        new Ability[] {
            AbilityList.intimidate
        },
        new Move[] {
            MoveList.block,
            MoveList.bulldoze,
            MoveList.earth_power,
            MoveList.earthquake,
            MoveList.extrasensory,
            MoveList.fissure,
            MoveList.hammer_arm,
            MoveList.imprison,
            MoveList.leer,
            MoveList.outrage,
            MoveList.rock_slide,
            MoveList.rock_tomb,
            MoveList.sand_tomb,
            MoveList.sandsear_storm,
            MoveList.sandstorm,
            MoveList.smack_down,
            MoveList.stone_edge
        },
        PokemonList.landorus,
        false,
        89, 145, 90, 105, 80, 91
    );

    public static final Pokemon kyurem = new Pokemon(
        "Kyurem",
        "Empty",
        5,
        TypeList.dragon, TypeList.ice,
        new double[] {0, 0},
        325,
        new Ability[] {
            AbilityList.pressure
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.blizzard,
            MoveList.dragon_breath,
            MoveList.dragon_pulse,
            MoveList.endeavor,
            MoveList.freeze_dry,
            MoveList.glaciate,
            MoveList.hyper_voice,
            MoveList.ice_beam,
            MoveList.imprison,
            MoveList.noble_roar,
            MoveList.outrage,
            MoveList.scary_face,
            MoveList.sheer_cold,
            MoveList.slash
        },
        125, 130, 90, 130, 90, 95
    );

    public static final Pokemon kyurem_white = new Pokemon( // Forma Alternativa
        "Kyurem",
        "White",
        true,
        5,
        TypeList.dragon, TypeList.ice,
        new double[] {0, 0},
        325,
        new Ability[] {
            AbilityList.turboblaze
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.blizzard,
            MoveList.dragon_breath,
            MoveList.dragon_pulse,
            MoveList.endeavor,
            MoveList.freeze_dry,
            MoveList.fusion_flare,
            MoveList.hyper_voice,
            MoveList.ice_beam,
            MoveList.ice_burn,
            MoveList.imprison,
            MoveList.noble_roar,
            MoveList.outrage,
            MoveList.sheer_cold,
            MoveList.slash
        },
        PokemonList.kyurem,
        false,
        125, 120, 90, 170, 100, 95
    );

    public static final Pokemon kyurem_black = new Pokemon( // Forma Alternativa
        "Kyurem",
        "Black",
        true,
        5,
        TypeList.dragon, TypeList.ice,
        new double[] {0, 0},
        325,
        new Ability[] {
            AbilityList.teravolt
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.blizzard,
            MoveList.dragon_breath,
            MoveList.dragon_pulse,
            MoveList.endeavor,
            MoveList.freeze_dry,
            MoveList.freeze_shock,
            MoveList.fusion_bolt,
            MoveList.hyper_voice,
            MoveList.ice_beam,
            MoveList.imprison,
            MoveList.noble_roar,
            MoveList.outrage,
            MoveList.sheer_cold,
            MoveList.slash
        },
        PokemonList.kyurem,
        false,
        125, 170, 100, 120, 90, 95
    );

    public static final Pokemon keldeo = new Pokemon(
        "Keldeo",
        "Ordinary",
        5,
        TypeList.water, TypeList.fighting,
        new double[] {0, 0},
        48.5,
        new Ability[] {
            AbilityList.justified
        },
        new Move[] {
            MoveList.aqua_jet,
            MoveList.aqua_tail,
            MoveList.bubble_beam,
            MoveList.close_combat,
            MoveList.double_kick,
            MoveList.helping_hand,
            MoveList.hydro_pump,
            MoveList.leer,
            MoveList.quick_guard,
            MoveList.retaliate,
            MoveList.sacred_sword,
            MoveList.secret_sword,
            MoveList.swords_dance,
            MoveList.take_down,
            MoveList.work_up
        },
        91, 72, 90, 129, 90, 108
    );

    public static final Pokemon keldeo_resolute = new Pokemon( // Forma Alternativa
        "Keldeo",
        "Resolute",
        true,
        5,
        TypeList.water, TypeList.fighting,
        new double[] {0, 0},
        48.5,
        new Ability[] {
            AbilityList.justified
        },
        new Move[] {
            MoveList.aqua_jet,
            MoveList.aqua_tail,
            MoveList.bubble_beam,
            MoveList.close_combat,
            MoveList.double_kick,
            MoveList.helping_hand,
            MoveList.hydro_pump,
            MoveList.leer,
            MoveList.quick_guard,
            MoveList.retaliate,
            MoveList.sacred_sword,
            MoveList.secret_sword,
            MoveList.swords_dance,
            MoveList.take_down,
            MoveList.work_up
        },
        PokemonList.keldeo,
        false,
        91, 72, 90, 129, 90, 108
    );

    public static final Pokemon meloetta = new Pokemon(
        "Meloetta",
        "Aria",
        5,
        TypeList.normal, TypeList.psychic,
        new double[] {0, 0},
        6.5,
        new Ability[] {
            AbilityList.serene_grace
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.close_combat,
            MoveList.confusion,
            MoveList.echoed_voice,
            MoveList.hyper_voice,
            MoveList.perish_song,
            MoveList.psybeam,
            MoveList.psychic,
            MoveList.quick_attack,
            MoveList.relic_song,
            MoveList.role_play,
            MoveList.round,
            MoveList.sing,
            MoveList.teeter_dance,
            MoveList.u_turn
        },
        100, 77, 77, 128, 128, 90
    );

    public static final Pokemon meloetta_pirouette = new Pokemon( // Forma Alternativa
        "Meloetta",
        "Pirouette",
        false,
        5,
        TypeList.normal, TypeList.fighting,
        new double[] {0, 0},
        6.5,
        new Ability[] {
            AbilityList.serene_grace
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.close_combat,
            MoveList.confusion,
            MoveList.echoed_voice,
            MoveList.hyper_voice,
            MoveList.perish_song,
            MoveList.psybeam,
            MoveList.psychic,
            MoveList.quick_attack,
            MoveList.relic_song,
            MoveList.role_play,
            MoveList.round,
            MoveList.sing,
            MoveList.teeter_dance,
            MoveList.u_turn
        },
        PokemonList.meloetta,
        false,
        100, 128, 90, 77, 77, 128
    );

    public static final Pokemon genesect = new Pokemon(
        "Genesect",
        "No Drive",
        5,
        TypeList.bug, TypeList.steel,
        new double[] {0, 0},
        82.5,
        new Ability[] {
            AbilityList.download
        },
        new Move[] {
            MoveList.bug_buzz,
            MoveList.fell_stinger,
            MoveList.flame_charge,
            MoveList.fury_cutter,
            MoveList.lock_on,
            MoveList.magnet_rise,
            MoveList.metal_claw,
            MoveList.metal_sound,
            MoveList.quick_attack,
            MoveList.screech,
            MoveList.self_destruct,
            MoveList.simple_beam,
            MoveList.techno_blast,
            MoveList.x_scissor,
            MoveList.zap_cannon
        },
        71, 120, 95, 120, 95, 99
    );

    public static final Pokemon genesect_douse = new Pokemon( // Forma Alternativa
        "Genesect",
        "Douse",
        true,
        5,
        TypeList.bug, TypeList.steel,
        new double[] {0, 0},
        82.5,
        new Ability[] {
            AbilityList.download
        },
        new Move[] {
            MoveList.bug_buzz,
            MoveList.fell_stinger,
            MoveList.flame_charge,
            MoveList.fury_cutter,
            MoveList.lock_on,
            MoveList.magnet_rise,
            MoveList.metal_claw,
            MoveList.metal_sound,
            MoveList.quick_attack,
            MoveList.screech,
            MoveList.self_destruct,
            MoveList.simple_beam,
            MoveList.techno_blast,
            MoveList.x_scissor,
            MoveList.zap_cannon
        },
        PokemonList.genesect,
        false,
        71, 120, 95, 120, 95, 99
    );

    public static final Pokemon genesect_shock = new Pokemon( // Forma Alternativa
        "Genesect",
        "Shock",
        true,
        5,
        TypeList.bug, TypeList.steel,
        new double[] {0, 0},
        82.5,
        new Ability[] {
            AbilityList.download
        },
        new Move[] {
            MoveList.bug_buzz,
            MoveList.fell_stinger,
            MoveList.flame_charge,
            MoveList.fury_cutter,
            MoveList.lock_on,
            MoveList.magnet_rise,
            MoveList.metal_claw,
            MoveList.metal_sound,
            MoveList.quick_attack,
            MoveList.screech,
            MoveList.self_destruct,
            MoveList.simple_beam,
            MoveList.techno_blast,
            MoveList.x_scissor,
            MoveList.zap_cannon
        },
        PokemonList.genesect,
        false,
        71, 120, 95, 120, 95, 99
    );

    public static final Pokemon genesect_burn = new Pokemon( // Forma Alternativa
        "Genesect",
        "Burn",
        true,
        5,
        TypeList.bug, TypeList.steel,
        new double[] {0, 0},
        82.5,
        new Ability[] {
            AbilityList.download
        },
        new Move[] {
            MoveList.bug_buzz,
            MoveList.fell_stinger,
            MoveList.flame_charge,
            MoveList.fury_cutter,
            MoveList.lock_on,
            MoveList.magnet_rise,
            MoveList.metal_claw,
            MoveList.metal_sound,
            MoveList.quick_attack,
            MoveList.screech,
            MoveList.self_destruct,
            MoveList.simple_beam,
            MoveList.techno_blast,
            MoveList.x_scissor,
            MoveList.zap_cannon
        },
        PokemonList.genesect,
        false,
        71, 120, 95, 120, 95, 99
    );

    public static final Pokemon genesect_chill = new Pokemon( // Forma Alternativa
        "Genesect",
        "Chill",
        true,
        5,
        TypeList.bug, TypeList.steel,
        new double[] {0, 0},
        82.5,
        new Ability[] {
            AbilityList.download
        },
        new Move[] {
            MoveList.bug_buzz,
            MoveList.fell_stinger,
            MoveList.flame_charge,
            MoveList.fury_cutter,
            MoveList.lock_on,
            MoveList.magnet_rise,
            MoveList.metal_claw,
            MoveList.metal_sound,
            MoveList.quick_attack,
            MoveList.screech,
            MoveList.self_destruct,
            MoveList.simple_beam,
            MoveList.techno_blast,
            MoveList.x_scissor,
            MoveList.zap_cannon
        },
        PokemonList.genesect,
        false,
        71, 120, 95, 120, 95, 99
    );


    /* Geração 6 */

    public static final Pokemon chespin = new Pokemon(
        "Chespin",
        6,
        TypeList.grass,
        new double[] {87.5, 12.5},
        9,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.bulletproof
        },
        new Move[] {
            MoveList.bite,
            MoveList.body_slam,
            MoveList.growl,
            MoveList.leech_seed,
            MoveList.mud_shot,
            MoveList.pain_split,
            MoveList.pin_missile,
            MoveList.rollout,
            MoveList.seed_bomb,
            MoveList.take_down,
            MoveList.vine_whip,
            MoveList.wood_hammer
        },
        56, 61, 65, 48, 45, 38
    );

    public static final Pokemon quilladin = new Pokemon(
        "Quilladin",
        6,
        TypeList.grass,
        new double[] {87.5, 12.5},
        29,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.bulletproof
        },
        new Move[] {
            MoveList.bite,
            MoveList.body_slam,
            MoveList.bulk_up,
            MoveList.growl,
            MoveList.leech_seed,
            MoveList.mud_shot,
            MoveList.pain_split,
            MoveList.pin_missile,
            MoveList.rollout,
            MoveList.seed_bomb,
            MoveList.take_down,
            MoveList.vine_whip,
            MoveList.wood_hammer
        },
        61, 78, 95, 56, 58, 57
    );

    public static final Pokemon chesnaught = new Pokemon(
        "Chesnaught",
        6,
        TypeList.grass, TypeList.fighting,
        new double[] {87.5, 12.5},
        90,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.bulletproof
        },
        new Move[] {
            MoveList.bite,
            MoveList.body_slam,
            MoveList.bulk_up,
            MoveList.feint,
            MoveList.giga_impact,
            MoveList.growl,
            MoveList.hammer_arm,
            MoveList.leech_seed,
            MoveList.mud_shot,
            MoveList.pain_split,
            MoveList.pin_missile,
            MoveList.rollout,
            MoveList.seed_bomb,
            MoveList.spiky_shield,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.vine_whip,
            MoveList.wood_hammer
        },
        88, 107, 122, 74, 75, 64
    );

    public static final Pokemon fennekin = new Pokemon(
        "Fennekin",
        6,
        TypeList.fire,
        new double[] {87.5, 12.5},
        9.4,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.magician
        },
        new Move[] {
            MoveList.ember,
            MoveList.fire_blast,
            MoveList.fire_spin,
            MoveList.flame_charge,
            MoveList.flamethrower,
            MoveList.howl,
            MoveList.light_screen,
            MoveList.psybeam,
            MoveList.psychic,
            MoveList.psyshock,
            MoveList.scratch,
            MoveList.sunny_day,
            MoveList.tail_whip,
            MoveList.will_o_wisp
        },
        40, 45, 40, 62, 60, 60
    );

    public static final Pokemon braixen = new Pokemon(
        "Braixen",
        6,
        TypeList.fire,
        new double[] {87.5, 12.5},
        14.5,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.magician
        },
        new Move[] {
            MoveList.ember,
            MoveList.fire_blast,
            MoveList.fire_spin,
            MoveList.flame_charge,
            MoveList.flamethrower,
            MoveList.howl,
            MoveList.light_screen,
            MoveList.magic_room,
            MoveList.psybeam,
            MoveList.psychic,
            MoveList.psyshock,
            MoveList.scratch,
            MoveList.sunny_day,
            MoveList.tail_whip,
            MoveList.will_o_wisp
        },
        59, 59, 58, 90, 70, 73
    );

    public static final Pokemon delphox = new Pokemon(
        "Delphox",
        6,
        TypeList.fire, TypeList.psychic,
        new double[] {87.5, 12.5},
        39,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.magician
        },
        new Move[] {
            MoveList.ember,
            MoveList.fire_blast,
            MoveList.fire_spin,
            MoveList.flame_charge,
            MoveList.flamethrower,
            MoveList.future_sight,
            MoveList.howl,
            MoveList.light_screen,
            MoveList.magic_room,
            MoveList.mystical_fire,
            MoveList.psybeam,
            MoveList.psychic,
            MoveList.psyshock,
            MoveList.role_play,
            MoveList.scratch,
            MoveList.shadow_ball,
            MoveList.sunny_day,
            MoveList.switcheroo,
            MoveList.tail_whip,
            MoveList.will_o_wisp
        },
        75, 69, 72, 114, 100, 104
    );

    public static final Pokemon froakie = new Pokemon(
        "Froakie",
        6,
        TypeList.water,
        new double[] {87.5, 12.5},
        7,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.protean
        },
        new Move[] {
            MoveList.bounce,
            MoveList.double_team,
            MoveList.fling,
            MoveList.growl,
            MoveList.hydro_pump,
            MoveList.lick,
            MoveList.pound,
            MoveList.quick_attack,
            MoveList.round,
            MoveList.smack_down,
            MoveList.smokescreen,
            MoveList.substitute,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        41, 56, 40, 62, 44, 71
    );

    public static final Pokemon frogadier = new Pokemon(
        "Frogadier",
        6,
        TypeList.water,
        new double[] {87.5, 12.5},
        10.9,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.protean
        },
        new Move[] {
            MoveList.bounce,
            MoveList.double_team,
            MoveList.fling,
            MoveList.growl,
            MoveList.hydro_pump,
            MoveList.lick,
            MoveList.pound,
            MoveList.quick_attack,
            MoveList.round,
            MoveList.smack_down,
            MoveList.smokescreen,
            MoveList.substitute,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        54, 63, 52, 83, 56, 97
    );

    public static final Pokemon greninja = new Pokemon(
        "Greninja",
        6,
        TypeList.water, TypeList.dark,
        new double[] {87.5, 12.5},
        40,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.protean
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.bounce,
            MoveList.double_team,
            MoveList.extrasensory,
            MoveList.fling,
            MoveList.growl,
            MoveList.haze,
            MoveList.hydro_pump,
            MoveList.lick,
            MoveList.night_slash,
            MoveList.pound,
            MoveList.quick_attack,
            MoveList.role_play,
            MoveList.round,
            MoveList.shadow_sneak,
            MoveList.smack_down,
            MoveList.smokescreen,
            MoveList.spikes,
            MoveList.substitute,
            MoveList.water_gun,
            MoveList.water_pulse,
            MoveList.water_shuriken
        },
        72, 95, 67, 103, 71, 122
    );

    public static final Pokemon hawlucha = new Pokemon(
        "Hawlucha",
        6,
        TypeList.fighting, TypeList.flying,
        new double[] {50, 50},
        21.5,
        new Ability[] {
            AbilityList.limber,
            AbilityList.unburden,
            AbilityList.mold_breaker
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.bounce,
            MoveList.brick_break,
            MoveList.detect,
            MoveList.encore,
            MoveList.endeavor,
            MoveList.feather_dance,
            MoveList.flying_press,
            MoveList.high_jump_kick,
            MoveList.hone_claws,
            MoveList.roost,
            MoveList.sky_attack,
            MoveList.swords_dance,
            MoveList.tackle,
            MoveList.taunt,
            MoveList.wing_attack
        },
        78, 92, 75, 74, 63, 118
    );

    public static final Pokemon xerneas = new Pokemon(
        "Xerneas",
        6,
        TypeList.fairy,
        new double[] {0, 0},
        215,
        new Ability[] {
            AbilityList.fairy_aura
        },
        new Move[] {
            MoveList.aromatherapy,
            MoveList.aurora_beam,
            MoveList.close_combat,
            MoveList.geomancy,
            MoveList.giga_impact,
            MoveList.gravity,
            MoveList.heal_pulse,
            MoveList.horn_leech,
            MoveList.ingrain,
            MoveList.light_screen,
            MoveList.megahorn,
            MoveList.misty_terrain,
            MoveList.moonblast,
            MoveList.nature_power,
            MoveList.night_slash,
            MoveList.outrage,
            MoveList.psych_up,
            MoveList.tackle,
            MoveList.take_down
        },
        126, 131, 95, 131, 98, 99
    );

    public static final Pokemon yveltal = new Pokemon(
        "Yveltal",
        6,
        TypeList.dark, TypeList.flying,
        new double[] {0, 0},
        203,
        new Ability[] {
            AbilityList.dark_aura
        },
        new Move[] {
            MoveList.air_slash,
            MoveList.dark_pulse,
            MoveList.disable,
            MoveList.double_team,
            MoveList.dragon_rush,
            MoveList.focus_blast,
            MoveList.foul_play,
            MoveList.gust,
            MoveList.hurricane,
            MoveList.hyper_beam,
            MoveList.oblivion_wing,
            MoveList.phantom_force,
            MoveList.psychic,
            MoveList.roost,
            MoveList.sky_attack,
            MoveList.snarl,
            MoveList.sucker_punch,
            MoveList.tailwind,
            MoveList.taunt
        },
        126, 131, 95, 131, 98, 99
    );

    public static final Pokemon zygarde = new Pokemon(
        "Zygarde",
        "50%",
        6,
        TypeList.dragon, TypeList.ground,
        new double[] {0, 0},
        305,
        new Ability[] {
            AbilityList.aura_break,
            AbilityList.power_construct
        },
        new Move[] {
            MoveList.bind,
            MoveList.bite,
            MoveList.bulldoze,
            MoveList.coil,
            MoveList.core_enforcer,
            MoveList.crunch,
            MoveList.dig,
            MoveList.dragon_breath,
            MoveList.dragon_pulse,
            MoveList.earthquake,
            MoveList.glare,
            MoveList.haze,
            MoveList.lands_wrath,
            MoveList.outrage,
            MoveList.safeguard,
            MoveList.sandstorm,
            MoveList.thousand_arrows,
            MoveList.thousand_waves
        },
        108, 100, 121, 81, 95, 95
    );

    public static final Pokemon zygarde_10 = new Pokemon( // Forma Alternativa
        "Zygarde",
        "10%",
        true,
        6,
        TypeList.dragon, TypeList.ground,
        new double[] {0, 0},
        33.5,
        new Ability[] {
            AbilityList.aura_break,
            AbilityList.power_construct
        },
        new Move[] {
            MoveList.bind,
            MoveList.bite,
            MoveList.bulldoze,
            MoveList.coil,
            MoveList.core_enforcer,
            MoveList.crunch,
            MoveList.dig,
            MoveList.dragon_breath,
            MoveList.dragon_pulse,
            MoveList.earthquake,
            MoveList.glare,
            MoveList.haze,
            MoveList.lands_wrath,
            MoveList.outrage,
            MoveList.safeguard,
            MoveList.sandstorm,
            MoveList.thousand_arrows,
            MoveList.thousand_waves
        },
        PokemonList.zygarde,
        false,
        54, 100, 71, 61, 85, 115
    );

    public static final Pokemon zygarde_complete = new Pokemon( // Forma Alternativa
        "Zygarde",
        "Complete",
        false,
        6,
        TypeList.dragon, TypeList.ground,
        new double[] {0, 0},
        610,
        new Ability[] {
            AbilityList.power_construct
        },
        new Move[] {
            MoveList.bind,
            MoveList.bite,
            MoveList.bulldoze,
            MoveList.coil,
            MoveList.core_enforcer,
            MoveList.crunch,
            MoveList.dig,
            MoveList.dragon_breath,
            MoveList.dragon_pulse,
            MoveList.earthquake,
            MoveList.glare,
            MoveList.haze,
            MoveList.lands_wrath,
            MoveList.outrage,
            MoveList.safeguard,
            MoveList.sandstorm,
            MoveList.thousand_arrows,
            MoveList.thousand_waves
        },
        PokemonList.zygarde,
        false,
        216, 100, 121, 91, 95, 85
    );

    public static final Pokemon diancie = new Pokemon(
        "Diancie",
        6,
        TypeList.rock, TypeList.fairy,
        new double[] {0, 0},
        8.8,
        new Ability[] {
            AbilityList.clear_body
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.diamond_storm,
            MoveList.flail,
            MoveList.guard_split,
            MoveList.harden,
            MoveList.light_screen,
            MoveList.moonblast,
            MoveList.power_gem,
            MoveList.rock_polish,
            MoveList.rock_slide,
            MoveList.skill_swap,
            MoveList.smack_down,
            MoveList.stealth_rock,
            MoveList.stone_edge,
            MoveList.tackle
        },
        50, 100, 150, 100, 150, 50
    );

    public static final Pokemon diancie_mega = new Pokemon( // Mega Evolução
        "Diancie",
        "Mega",
        false,
        6,
        TypeList.rock, TypeList.fairy,
        new double[] {0, 0},
        27.8,
        new Ability[] {
            AbilityList.magic_bounce
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.diamond_storm,
            MoveList.flail,
            MoveList.guard_split,
            MoveList.harden,
            MoveList.light_screen,
            MoveList.moonblast,
            MoveList.power_gem,
            MoveList.rock_polish,
            MoveList.rock_slide,
            MoveList.skill_swap,
            MoveList.smack_down,
            MoveList.stealth_rock,
            MoveList.stone_edge,
            MoveList.tackle
        },
        PokemonList.diancie,
        false,
        50, 160, 110, 160, 110, 110
    );

    public static final Pokemon hoopa = new Pokemon(
        "Hoopa",
        "Confined",
        6,
        TypeList.psychic, TypeList.ghost,
        new double[] {0, 0},
        9,
        new Ability[] {
            AbilityList.magician
        },
        new Move[] {
            MoveList.ally_switch,
            MoveList.astonish,
            MoveList.confusion,
            MoveList.dark_pulse,
            MoveList.destiny_bond,
            MoveList.guard_split,
            MoveList.hyperspace_hole,
            MoveList.knock_off,
            MoveList.light_screen,
            MoveList.nasty_plot,
            MoveList.phantom_force,
            MoveList.power_split,
            MoveList.psybeam,
            MoveList.psychic,
            MoveList.shadow_ball,
            MoveList.skill_swap,
            MoveList.trick_room,
            MoveList.trick,
            MoveList.wonder_room,
            MoveList.zen_headbutt
        },
        80, 110, 60, 150, 130, 70
    );

    public static final Pokemon hoopa_unbound = new Pokemon( // Forma Alternativa
        "Hoopa",
        "Unbound",
        6,
        TypeList.psychic, TypeList.dark,
        new double[] {0, 0},
        490,
        new Ability[] {
            AbilityList.magician
        },
        new Move[] {
            MoveList.ally_switch,
            MoveList.astonish,
            MoveList.confusion,
            MoveList.dark_pulse,
            MoveList.destiny_bond,
            MoveList.guard_split,
            MoveList.hyperspace_fury,
            MoveList.knock_off,
            MoveList.light_screen,
            MoveList.nasty_plot,
            MoveList.phantom_force,
            MoveList.power_split,
            MoveList.psybeam,
            MoveList.psychic,
            MoveList.shadow_ball,
            MoveList.skill_swap,
            MoveList.trick_room,
            MoveList.trick,
            MoveList.wonder_room,
            MoveList.zen_headbutt
        },
        80, 160, 60, 170, 130, 80
    );

    public static final Pokemon volcanion = new Pokemon(
        "Volcanion",
        6,
        TypeList.fire, TypeList.water,
        new double[] {0, 0},
        195,
        new Ability[] {
            AbilityList.water_absorb
        },
        new Move[] {
            MoveList.explosion,
            MoveList.fire_spin,
            MoveList.flame_charge,
            MoveList.flare_blitz,
            MoveList.haze,
            MoveList.hydro_pump,
            MoveList.incinerate,
            MoveList.leer,
            MoveList.mist,
            MoveList.overheat,
            MoveList.scald,
            MoveList.scary_face,
            MoveList.steam_eruption,
            MoveList.stomp,
            MoveList.take_down,
            MoveList.water_gun,
            MoveList.water_pulse,
            MoveList.weather_ball
        },
        80, 110, 120, 130, 90, 70
    );


    /* Geração 7 */

    public static final Pokemon rowlet = new Pokemon(
        "Rowlet",
        7,
        TypeList.grass, TypeList.flying,
        new double[] {87.5, 12.5},
        1.5,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.long_reach
        },
        new Move[] {
            MoveList.astonish,
            MoveList.brave_bird,
            MoveList.feather_dance,
            MoveList.growl,
            MoveList.leaf_blade,
            MoveList.leafage,
            MoveList.nasty_plot,
            MoveList.peck,
            MoveList.pluck,
            MoveList.razor_leaf,
            MoveList.shadow_sneak,
            MoveList.sucker_punch,
            MoveList.synthesis,
            MoveList.tackle
        },
        68, 55, 55, 50, 50, 42
    );

    public static final Pokemon dartrix = new Pokemon(
        "Dartrix",
        7,
        TypeList.grass, TypeList.flying,
        new double[] {87.5, 12.5},
        16,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.long_reach
        },
        new Move[] {
            MoveList.astonish,
            MoveList.brave_bird,
            MoveList.feather_dance,
            MoveList.growl,
            MoveList.leaf_blade,
            MoveList.leafage,
            MoveList.nasty_plot,
            MoveList.peck,
            MoveList.pluck,
            MoveList.razor_leaf,
            MoveList.shadow_sneak,
            MoveList.sucker_punch,
            MoveList.synthesis,
            MoveList.tackle
        },
        78, 75, 75, 70, 70, 52
    );

    public static final Pokemon decidueye = new Pokemon(
        "Decidueye",
        "Alola",
        7,
        TypeList.grass, TypeList.ghost,
        new double[] {87.5, 12.5},
        36.6,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.long_reach
        },
        new Move[] {
            MoveList.astonish,
            MoveList.brave_bird,
            MoveList.feather_dance,
            MoveList.growl,
            MoveList.leaf_blade,
            MoveList.leaf_storm,
            MoveList.leafage,
            MoveList.nasty_plot,
            MoveList.peck,
            MoveList.phantom_force,
            MoveList.pluck,
            MoveList.razor_leaf,
            MoveList.shadow_sneak,
            MoveList.spirit_shackle,
            MoveList.spite,
            MoveList.sucker_punch,
            MoveList.synthesis,
            MoveList.tackle,
            MoveList.u_turn
        },
        78, 107, 75, 100, 100, 70
    );

    public static final Pokemon decidueye_hisui = new Pokemon( // Forma Regional
        "Decidueye",
        "Hisui",
        true,
        7,
        TypeList.grass, TypeList.fighting,
        new double[] {87.5, 12.5},
        37,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.scrappy
        },
        new Move[] {
            MoveList.astonish,
            MoveList.brave_bird,
            MoveList.bulk_up,
            MoveList.feather_dance,
            MoveList.growl,
            MoveList.leaf_blade,
            MoveList.leaf_storm,
            MoveList.leafage,
            MoveList.peck,
            MoveList.pluck,
            MoveList.razor_leaf,
            MoveList.shadow_sneak,
            MoveList.sucker_punch,
            MoveList.synthesis,
            MoveList.tackle,
            MoveList.triple_arrows,
            MoveList.u_turn
        },
        PokemonList.decidueye,
        false,
        88, 112, 80, 95, 95, 60
    );

    public static final Pokemon litten = new Pokemon(
        "Litten",
        7,
        TypeList.fire,
        new double[] {87.5, 12.5},
        4.3,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.intimidate
        },
        new Move[] {
            MoveList.bite,
            MoveList.double_kick,
            MoveList.ember,
            MoveList.fire_fang,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.fury_swipes,
            MoveList.growl,
            MoveList.lick,
            MoveList.roar,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.swagger,
            MoveList.thrash
        },
        45, 65, 40, 60, 40, 70
    );

    public static final Pokemon torracat = new Pokemon(
        "Torracat",
        7,
        TypeList.fire,
        new double[] {87.5, 12.5},
        25,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.intimidate
        },
        new Move[] {
            MoveList.bite,
            MoveList.double_kick,
            MoveList.ember,
            MoveList.fire_fang,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.fury_swipes,
            MoveList.growl,
            MoveList.lick,
            MoveList.roar,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.swagger,
            MoveList.thrash
        },
        65, 85, 50, 80, 50, 90
    );

    public static final Pokemon incineroar = new Pokemon(
        "Incineroar",
        7,
        TypeList.fire, TypeList.dark,
        new double[] {87.5, 12.5},
        83,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.intimidate
        },
        new Move[] {
            MoveList.bite,
            MoveList.bulk_up,
            MoveList.cross_chop,
            MoveList.darkest_lariat,
            MoveList.double_kick,
            MoveList.ember,
            MoveList.fire_fang,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.fury_swipes,
            MoveList.growl,
            MoveList.lick,
            MoveList.roar,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.swagger,
            MoveList.thrash,
            MoveList.throat_chop
        },
        95, 115, 90, 80, 90, 60
    );

    public static final Pokemon popplio = new Pokemon(
        "Popplio",
        7,
        TypeList.water,
        new double[] {87.5, 12.5},
        7.5,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.liquid_voice
        },
        new Move[] {
            MoveList.aqua_jet,
            MoveList.baby_doll_eyes,
            MoveList.bubble_beam,
            MoveList.disarming_voice,
            MoveList.encore,
            MoveList.growl,
            MoveList.hydro_pump,
            MoveList.hyper_voice,
            MoveList.icy_wind,
            MoveList.misty_terrain,
            MoveList.moonblast,
            MoveList.pound,
            MoveList.sing,
            MoveList.water_gun
        },
        50, 54, 54, 66, 56, 40
    );

    public static final Pokemon brionne = new Pokemon(
        "Brionne",
        7,
        TypeList.water,
        new double[] {87.5, 12.5},
        17.5,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.liquid_voice
        },
        new Move[] {
            MoveList.aqua_jet,
            MoveList.baby_doll_eyes,
            MoveList.bubble_beam,
            MoveList.disarming_voice,
            MoveList.encore,
            MoveList.growl,
            MoveList.hydro_pump,
            MoveList.hyper_voice,
            MoveList.icy_wind,
            MoveList.misty_terrain,
            MoveList.moonblast,
            MoveList.pound,
            MoveList.sing,
            MoveList.water_gun
        },
        60, 69, 69, 91, 81, 50
    );

    public static final Pokemon primarina = new Pokemon(
        "Primarina",
        7,
        TypeList.water, TypeList.fairy,
        new double[] {87.5, 12.5},
        44,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.liquid_voice
        },
        new Move[] {
            MoveList.aqua_jet,
            MoveList.baby_doll_eyes,
            MoveList.bubble_beam,
            MoveList.disarming_voice,
            MoveList.encore,
            MoveList.growl,
            MoveList.hydro_pump,
            MoveList.hyper_voice,
            MoveList.icy_wind,
            MoveList.misty_terrain,
            MoveList.moonblast,
            MoveList.pound,
            MoveList.sing,
            MoveList.sparkling_aria,
            MoveList.water_gun
        },
        80, 74, 74, 126, 116, 60
    );

    public static final Pokemon grubbin = new Pokemon(
        "Grubbin",
        7,
        TypeList.bug,
        new double[] {50, 50},
        4.4,
        new Ability[] {
            AbilityList.swarm
        },
        new Move[] {
            MoveList.bite,
            MoveList.bug_bite,
            MoveList.crunch,
            MoveList.dig,
            MoveList.mud_slap,
            MoveList.spark,
            MoveList.sticky_web,
            MoveList.string_shot,
            MoveList.vise_grip,
            MoveList.x_scissor
        },
        47, 62, 45, 55, 45, 46
    );

    public static final Pokemon charjabug = new Pokemon(
        "Charjabug",
        7,
        TypeList.bug, TypeList.electric,
        new double[] {50, 50},
        10.5,
        new Ability[] {
            AbilityList.battery
        },
        new Move[] {
            MoveList.bite,
            MoveList.bug_bite,
            MoveList.charge,
            MoveList.crunch,
            MoveList.dig,
            MoveList.discharge,
            MoveList.iron_defense,
            MoveList.mud_slap,
            MoveList.spark,
            MoveList.sticky_web,
            MoveList.string_shot,
            MoveList.vise_grip,
            MoveList.x_scissor
        },
        57, 82, 95, 55, 75, 36
    );

    public static final Pokemon vikavolt = new Pokemon(
        "Vikavolt",
        7,
        TypeList.bug, TypeList.electric,
        new double[] {50, 50},
        45,
        new Ability[] {
            AbilityList.levitate
        },
        new Move[] {
            MoveList.agility,
            MoveList.bite,
            MoveList.bug_bite,
            MoveList.bug_buzz,
            MoveList.charge,
            MoveList.crunch,
            MoveList.dig,
            MoveList.discharge,
            MoveList.fly,
            MoveList.guillotine,
            MoveList.iron_defense,
            MoveList.mud_slap,
            MoveList.spark,
            MoveList.sticky_web,
            MoveList.string_shot,
            MoveList.thunderbolt,
            MoveList.vise_grip,
            MoveList.x_scissor,
            MoveList.zap_cannon
        },
        77, 70, 90, 145, 75, 43
    );

    public static final Pokemon type_null = new Pokemon(
        "Type: Null",
        7,
        TypeList.normal,
        new double[] {0, 0},
        120.5,
        new Ability[] {
            AbilityList.battle_armor
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        95, 95, 95, 95, 95, 59
    );

    public static final Pokemon silvally = new Pokemon(
        "Silvally",
        "Normal",
        7,
        TypeList.normal,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_fighting = new Pokemon( // Forma Alternativa
        "Silvally",
        "Fighting",
        true,
        7,
        TypeList.fighting,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_flying = new Pokemon( // Forma Alternativa
        "Silvally",
        "Flying",
        true,
        7,
        TypeList.flying,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_poison = new Pokemon( // Forma Alternativa
        "Silvally",
        "Poison",
        true,
        7,
        TypeList.poison,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_ground = new Pokemon( // Forma Alternativa
        "Silvally",
        "Ground",
        true,
        7,
        TypeList.ground,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_rock = new Pokemon( // Forma Alternativa
        "Silvally",
        "Rock",
        true,
        7,
        TypeList.rock,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_bug = new Pokemon( // Forma Alternativa
        "Silvally",
        "Bug",
        true,
        7,
        TypeList.bug,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_ghost = new Pokemon( // Forma Alternativa
        "Silvally",
        "Ghost",
        true,
        7,
        TypeList.ghost,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_steel = new Pokemon( // Forma Alternativa
        "Silvally",
        "Steel",
        true,
        7,
        TypeList.steel,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_fire = new Pokemon( // Forma Alternativa
        "Silvally",
        "Fire",
        true,
        7,
        TypeList.fire,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_water = new Pokemon( // Forma Alternativa
        "Silvally",
        "Water",
        true,
        7,
        TypeList.water,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_grass = new Pokemon( // Forma Alternativa
        "Silvally",
        "Grass",
        true,
        7,
        TypeList.grass,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_electric = new Pokemon( // Forma Alternativa
        "Silvally",
        "Electric",
        true,
        7,
        TypeList.electric,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_psychic = new Pokemon( // Forma Alternativa
        "Silvally",
        "Psychic",
        true,
        7,
        TypeList.psychic,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_ice = new Pokemon( // Forma Alternativa
        "Silvally",
        "Ice",
        true,
        7,
        TypeList.ice,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_dragon = new Pokemon( // Forma Alternativa
        "Silvally",
        "Dragon",
        true,
        7,
        TypeList.dragon,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_dark = new Pokemon( // Forma Alternativa
        "Silvally",
        "Dark",
        true,
        7,
        TypeList.dark,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon silvally_fairy = new Pokemon( // Forma Alternativa
        "Silvally",
        "Fairy",
        true,
        7,
        TypeList.fairy,
        new double[] {0, 0},
        100.5,
        new Ability[] {
            AbilityList.rks_system
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.air_slash,
            MoveList.bite,
            MoveList.crunch,
            MoveList.crush_claw,
            MoveList.double_edge,
            MoveList.double_hit,
            MoveList.explosion,
            MoveList.fire_fang,
            MoveList.ice_fang,
            MoveList.imprison,
            MoveList.iron_head,
            MoveList.metal_sound,
            MoveList.multi_attack,
            MoveList.parting_shot,
            MoveList.poison_fang,
            MoveList.scary_face,
            MoveList.tackle,
            MoveList.take_down,
            MoveList.thunder_fang,
            MoveList.tri_attack,
            MoveList.x_scissor
        },
        PokemonList.silvally,
        false,
        95, 95, 95, 95, 95, 95
    );

    public static final Pokemon tapu_koko = new Pokemon(
        "Tapu Koko",
        7,
        TypeList.electric, TypeList.fairy,
        new double[] {0, 0},
        20.5,
        new Ability[] {
            AbilityList.electric_surge,
            AbilityList.telepathy
        },
        new Move[] {
            MoveList.agility,
            MoveList.brave_bird,
            MoveList.charge,
            MoveList.discharge,
            MoveList.electric_terrain,
            MoveList.fairy_wind,
            MoveList.false_swipe,
            MoveList.mean_look,
            MoveList.natures_madness,
            MoveList.power_swap,
            MoveList.quick_attack,
            MoveList.screech,
            MoveList.shock_wave,
            MoveList.spark,
            MoveList.thunder_shock,
            MoveList.wild_charge,
            MoveList.withdraw
        },
        70, 115, 85, 95, 75, 130
    );

    public static final Pokemon tapu_lele = new Pokemon(
        "Tapu Lele",
        7,
        TypeList.psychic, TypeList.fairy,
        new double[] {0, 0},
        18.6,
        new Ability[] {
            AbilityList.psychic_surge,
            AbilityList.telepathy
        },
        new Move[] {
            MoveList.aromatherapy,
            MoveList.aromatic_mist,
            MoveList.astonish,
            MoveList.confusion,
            MoveList.draining_kiss,
            MoveList.extrasensory,
            MoveList.flatter,
            MoveList.mean_look,
            MoveList.moonblast,
            MoveList.natures_madness,
            MoveList.psybeam,
            MoveList.psychic_terrain,
            MoveList.psyshock,
            MoveList.skill_swap,
            MoveList.sweet_scent,
            MoveList.tickle,
            MoveList.withdraw
        },
        70, 85, 75, 130, 115, 95
    );

    public static final Pokemon tapu_bulu = new Pokemon(
        "Tapu Bulu",
        7,
        TypeList.grass, TypeList.fairy,
        new double[] {0, 0},
        45.5,
        new Ability[] {
            AbilityList.grassy_surge,
            AbilityList.telepathy
        },
        new Move[] {
            MoveList.disable,
            MoveList.grassy_terrain,
            MoveList.horn_attack,
            MoveList.horn_leech,
            MoveList.leafage,
            MoveList.leech_seed,
            MoveList.mean_look,
            MoveList.mega_drain,
            MoveList.megahorn,
            MoveList.natures_madness,
            MoveList.rock_smash,
            MoveList.scary_face,
            MoveList.skull_bash,
            MoveList.whirlwind,
            MoveList.withdraw,
            MoveList.wood_hammer,
            MoveList.zen_headbutt
        },
        70, 130, 115, 85, 95, 75
    );

    public static final Pokemon tapu_fini = new Pokemon(
        "Tapu Fini",
        7,
        TypeList.water, TypeList.fairy,
        new double[] {0, 0},
        21.2,
        new Ability[] {
            AbilityList.misty_surge,
            AbilityList.telepathy
        },
        new Move[] {
            MoveList.aqua_ring,
            MoveList.brine,
            MoveList.defog,
            MoveList.disarming_voice,
            MoveList.haze,
            MoveList.heal_pulse,
            MoveList.hydro_pump,
            MoveList.mean_look,
            MoveList.mist,
            MoveList.misty_terrain,
            MoveList.moonblast,
            MoveList.muddy_water,
            MoveList.natures_madness,
            MoveList.soak,
            MoveList.surf,
            MoveList.water_gun,
            MoveList.water_pulse,
            MoveList.withdraw
        },
        70, 75, 115, 95, 130, 85
    );

    public static final Pokemon cosmog = new Pokemon(
        "Cosmog",
        7,
        TypeList.psychic,
        new double[] {0, 0},
        0.1,
        new Ability[] {
            AbilityList.unaware
        },
        new Move[] {
            MoveList.splash,
            MoveList.teleport
        },
        43, 29, 31, 29, 31, 37
    );

    public static final Pokemon cosmoem = new Pokemon(
        "Cosmoem",
        7,
        TypeList.psychic,
        new double[] {0, 0},
        999.9,
        new Ability[] {
            AbilityList.sturdy
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.splash,
            MoveList.teleport
        },
        43, 29, 131, 29, 131, 37
    );

    public static final Pokemon solgaleo = new Pokemon(
        "Solgaleo",
        7,
        TypeList.psychic, TypeList.steel,
        new double[] {0, 0},
        230,
        new Ability[] {
            AbilityList.full_metal_body
        },
        new Move[] {
            MoveList.cosmic_power,
            MoveList.crunch,
            MoveList.flare_blitz,
            MoveList.flash_cannon,
            MoveList.giga_impact,
            MoveList.iron_head,
            MoveList.metal_burst,
            MoveList.metal_claw,
            MoveList.metal_sound,
            MoveList.morning_sun,
            MoveList.noble_roar,
            MoveList.solar_beam,
            MoveList.splash,
            MoveList.sunsteel_strike,
            MoveList.teleport,
            MoveList.wide_guard,
            MoveList.wild_charge,
            MoveList.zen_headbutt
        },
        137, 137, 107, 113, 89, 97
    );

    public static final Pokemon lunala = new Pokemon(
        "Lunala",
        7,
        TypeList.psychic, TypeList.ghost,
        new double[] {0, 0},
        120,
        new Ability[] {
            AbilityList.shadow_shield
        },
        new Move[] {
            MoveList.air_slash,
            MoveList.confuse_ray,
            MoveList.confusion,
            MoveList.cosmic_power,
            MoveList.dream_eater,
            MoveList.hypnosis,
            MoveList.moonblast,
            MoveList.moongeist_beam,
            MoveList.moonlight,
            MoveList.night_shade,
            MoveList.phantom_force,
            MoveList.psychic,
            MoveList.shadow_ball,
            MoveList.splash,
            MoveList.teleport,
            MoveList.wide_guard
        },
        137, 113, 89, 137, 107, 97
    );

    public static final Pokemon necrozma = new Pokemon(
        "Necrozma",
        "Empty",
        7,
        TypeList.psychic,
        new double[] {0, 0},
        230,
        new Ability[] {
            AbilityList.prism_armor
        },
        new Move[] {
            MoveList.charge_beam,
            MoveList.confusion,
            MoveList.gravity,
            MoveList.iron_defense,
            MoveList.metal_claw,
            MoveList.moonlight,
            MoveList.morning_sun,
            MoveList.night_slash,
            MoveList.photon_geyser,
            MoveList.power_gem,
            MoveList.prismatic_laser,
            MoveList.psycho_cut,
            MoveList.rock_blast,
            MoveList.slash,
            MoveList.stealth_rock,
            MoveList.stored_power
        },
        97, 107, 101, 127, 89, 79
    );

    public static final Pokemon necrozma_dusk_mane = new Pokemon( // Forma Alternativa
        "Necrozma",
        "Dusk Mane",
        true,
        7,
        TypeList.psychic, TypeList.steel,
        new double[] {0, 0},
        460,
        new Ability[] {
            AbilityList.prism_armor
        },
        new Move[] {
            MoveList.charge_beam,
            MoveList.confusion,
            MoveList.gravity,
            MoveList.iron_defense,
            MoveList.metal_claw,
            MoveList.moonlight,
            MoveList.morning_sun,
            MoveList.night_slash,
            MoveList.photon_geyser,
            MoveList.power_gem,
            MoveList.prismatic_laser,
            MoveList.psycho_cut,
            MoveList.rock_blast,
            MoveList.slash,
            MoveList.stealth_rock,
            MoveList.stored_power,
            MoveList.sunsteel_strike
        },
        PokemonList.necrozma,
        false,
        97, 157, 127, 113, 109, 77
    );

    public static final Pokemon necrozma_dawn_wings = new Pokemon( // Forma Alternativa
        "Necrozma",
        "Dawn Wings",
        true,
        7,
        TypeList.psychic, TypeList.ghost,
        new double[] {0, 0},
        350,
        new Ability[] {
            AbilityList.prism_armor
        },
        new Move[] {
            MoveList.charge_beam,
            MoveList.confusion,
            MoveList.gravity,
            MoveList.iron_defense,
            MoveList.metal_claw,
            MoveList.moongeist_beam,
            MoveList.moonlight,
            MoveList.morning_sun,
            MoveList.night_slash,
            MoveList.photon_geyser,
            MoveList.power_gem,
            MoveList.prismatic_laser,
            MoveList.psycho_cut,
            MoveList.rock_blast,
            MoveList.slash,
            MoveList.stealth_rock,
            MoveList.stored_power
        },
        PokemonList.necrozma,
        false,
        97, 113, 109, 157, 127, 77
    );

    public static final Pokemon necrozma_ultra = new Pokemon( // Ultraexplosão
        "Necrozma",
        "Ultra",
        false,
        7,
        TypeList.psychic, TypeList.dragon,
        new double[] {0, 0},
        230,
        new Ability[] {
            AbilityList.neuroforce
        },
        new Move[] {
            MoveList.charge_beam,
            MoveList.confusion,
            MoveList.gravity,
            MoveList.iron_defense,
            MoveList.metal_claw,
            MoveList.moongeist_beam,
            MoveList.moonlight,
            MoveList.morning_sun,
            MoveList.night_slash,
            MoveList.photon_geyser,
            MoveList.power_gem,
            MoveList.prismatic_laser,
            MoveList.psycho_cut,
            MoveList.rock_blast,
            MoveList.slash,
            MoveList.stealth_rock,
            MoveList.stored_power,
            MoveList.sunsteel_strike
        },
        PokemonList.necrozma,
        false,
        97, 167, 97, 167, 97, 129
    );

    public static final Pokemon magearna = new Pokemon(
        "Magearna",
        7,
        TypeList.steel, TypeList.fairy,
        new double[] {0, 0},
        80.5,
        new Ability[] {
            AbilityList.soul_heart
        },
        new Move[] {
            MoveList.aura_sphere,
            MoveList.aurora_beam,
            MoveList.defense_curl,
            MoveList.flash_cannon,
            MoveList.fleur_cannon,
            MoveList.gyro_ball,
            MoveList.helping_hand,
            MoveList.iron_defense,
            MoveList.iron_head,
            MoveList.lock_on,
            MoveList.magnetic_flux,
            MoveList.pain_split,
            MoveList.psybeam,
            MoveList.rollout,
            MoveList.shift_gear,
            MoveList.trick,
            MoveList.zap_cannon
        },
        80, 95, 115, 130, 115, 65
    );

    public static final Pokemon marshadow = new Pokemon(
        "Marshadow",
        7,
        TypeList.fighting, TypeList.ghost,
        new double[] {0, 0},
        22.2,
        new Ability[] {
            AbilityList.technician
        },
        new Move[] {
            MoveList.assurance,
            MoveList.close_combat,
            MoveList.copycat,
            MoveList.counter,
            MoveList.drain_punch,
            MoveList.endeavor,
            MoveList.feint,
            MoveList.fire_punch,
            MoveList.force_palm,
            MoveList.ice_punch,
            MoveList.laser_focus,
            MoveList.psych_up,
            MoveList.role_play,
            MoveList.shadow_punch,
            MoveList.shadow_sneak,
            MoveList.spectral_thief,
            MoveList.sucker_punch,
            MoveList.thunder_punch
        },
        90, 125, 80, 90, 90, 125
    );

    public static final Pokemon zeraora = new Pokemon(
        "Zeraora",
        7,
        TypeList.electric,
        new double[] {0, 0},
        44.5,
        new Ability[] {
            AbilityList.volt_absorb
        },
        new Move[] {
            MoveList.agility,
            MoveList.charge,
            MoveList.close_combat,
            MoveList.discharge,
            MoveList.fake_out,
            MoveList.fury_swipes,
            MoveList.hone_claws,
            MoveList.plasma_fists,
            MoveList.power_up_punch,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.scratch,
            MoveList.slash,
            MoveList.snarl,
            MoveList.spark,
            MoveList.thunder_punch,
            MoveList.volt_switch,
            MoveList.wild_charge
        },
        88, 112, 75, 102, 80, 143
    );

    public static final Pokemon meltan = new Pokemon(
        "Meltan",
        7,
        TypeList.steel,
        new double[] {0, 0},
        8,
        new Ability[] {
            AbilityList.magnet_pull
        },
        new Move[] {
            MoveList.acid_armor,
            MoveList.flash_cannon,
            MoveList.harden,
            MoveList.headbutt,
            MoveList.tail_whip,
            MoveList.thunder_shock,
            MoveList.thunder_wave
        },
        46, 65, 65, 55, 35, 34
    );

    public static final Pokemon melmetal = new Pokemon(
        "Melmetal",
        7,
        TypeList.steel,
        new double[] {0, 0},
        800,
        new Ability[] {
            AbilityList.iron_fist
        },
        new Move[] {
            MoveList.acid_armor,
            MoveList.discharge,
            MoveList.double_iron_bash,
            MoveList.dynamic_punch,
            MoveList.flash_cannon,
            MoveList.harden,
            MoveList.headbutt,
            MoveList.hyper_beam,
            MoveList.mega_punch,
            MoveList.protect,
            MoveList.superpower,
            MoveList.tail_whip,
            MoveList.thunder_punch,
            MoveList.thunder_shock,
            MoveList.thunder_wave
        },
        135, 143, 143, 80, 65, 34
    );


    /* Geração 8 */

    public static final Pokemon grookey = new Pokemon(
        "Grookey",
        8,
        TypeList.grass,
        new double[] {87.5, 12.5},
        5,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.grassy_surge
        },
        new Move[] {
            MoveList.branch_poke,
            MoveList.endeavor,
            MoveList.growl,
            MoveList.knock_off,
            MoveList.razor_leaf,
            MoveList.scratch,
            MoveList.screech,
            MoveList.slam,
            MoveList.taunt,
            MoveList.uproar,
            MoveList.wood_hammer
        },
        50, 65, 50, 40, 40, 65
    );

    public static final Pokemon thwackey = new Pokemon(
        "Thwackey",
        8,
        TypeList.grass,
        new double[] {87.5, 12.5},
        14,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.grassy_surge
        },
        new Move[] {
            MoveList.branch_poke,
            MoveList.double_hit,
            MoveList.endeavor,
            MoveList.growl,
            MoveList.knock_off,
            MoveList.razor_leaf,
            MoveList.scratch,
            MoveList.screech,
            MoveList.slam,
            MoveList.taunt,
            MoveList.uproar,
            MoveList.wood_hammer
        },
        70, 85, 70, 55, 60, 80
    );

    public static final Pokemon rillaboom = new Pokemon(
        "Rillaboom",
        8,
        TypeList.grass,
        new double[] {87.5, 12.5},
        90,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.grassy_surge
        },
        new Move[] {
            MoveList.boomburst,
            MoveList.branch_poke,
            MoveList.double_hit,
            MoveList.drum_beating,
            MoveList.endeavor,
            MoveList.grassy_terrain,
            MoveList.growl,
            MoveList.knock_off,
            MoveList.noble_roar,
            MoveList.razor_leaf,
            MoveList.scratch,
            MoveList.screech,
            MoveList.slam,
            MoveList.taunt,
            MoveList.uproar,
            MoveList.wood_hammer
        },
        100, 125, 90, 60, 70, 85
    );

    public static final Pokemon scorbunny = new Pokemon(
        "Scorbunny",
        8,
        TypeList.fire,
        new double[] {87.5, 12.5},
        4.5,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.libero
        },
        new Move[] {
            MoveList.agility,
            MoveList.bounce,
            MoveList.counter,
            MoveList.double_edge,
            MoveList.double_kick,
            MoveList.ember,
            MoveList.flame_charge,
            MoveList.growl,
            MoveList.headbutt,
            MoveList.quick_attack,
            MoveList.tackle
        },
        50, 71, 40, 40, 40, 69
    );

    public static final Pokemon raboot = new Pokemon(
        "Raboot",
        8,
        TypeList.fire,
        new double[] {87.5, 12.5},
        9,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.libero
        },
        new Move[] {
            MoveList.agility,
            MoveList.bounce,
            MoveList.counter,
            MoveList.double_edge,
            MoveList.double_kick,
            MoveList.ember,
            MoveList.flame_charge,
            MoveList.growl,
            MoveList.headbutt,
            MoveList.quick_attack,
            MoveList.tackle
        },
        65, 86, 60, 55, 60, 94
    );

    public static final Pokemon cinderace = new Pokemon(
        "Cinderace",
        8,
        TypeList.fire,
        new double[] {87.5, 12.5},
        33,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.libero
        },
        new Move[] {
            MoveList.agility,
            MoveList.bounce,
            MoveList.counter,
            MoveList.court_change,
            MoveList.double_edge,
            MoveList.double_kick,
            MoveList.ember,
            MoveList.feint,
            MoveList.flame_charge,
            MoveList.growl,
            MoveList.headbutt,
            MoveList.pyro_ball,
            MoveList.quick_attack,
            MoveList.tackle
        },
        80, 116, 75, 65, 75, 119
    );

    public static final Pokemon sobble = new Pokemon(
        "Sobble",
        8,
        TypeList.water,
        new double[] {87.5, 12.5},
        4,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.sniper
        },
        new Move[] {
            MoveList.bind,
            MoveList.growl,
            MoveList.liquidation,
            MoveList.pound,
            MoveList.rain_dance,
            MoveList.soak,
            MoveList.sucker_punch,
            MoveList.tearful_look,
            MoveList.u_turn,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        50, 40, 40, 70, 40, 70
    );

    public static final Pokemon drizzile = new Pokemon(
        "Drizzile",
        8,
        TypeList.water,
        new double[] {87.5, 12.5},
        11.5,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.sniper
        },
        new Move[] {
            MoveList.bind,
            MoveList.growl,
            MoveList.liquidation,
            MoveList.pound,
            MoveList.rain_dance,
            MoveList.soak,
            MoveList.sucker_punch,
            MoveList.tearful_look,
            MoveList.u_turn,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        65, 60, 55, 95, 55, 90
    );

    public static final Pokemon inteleon = new Pokemon(
        "Inteleon",
        8,
        TypeList.water,
        new double[] {87.5, 12.5},
        45.2,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.sniper
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.bind,
            MoveList.growl,
            MoveList.hydro_pump,
            MoveList.liquidation,
            MoveList.pound,
            MoveList.rain_dance,
            MoveList.snipe_shot,
            MoveList.soak,
            MoveList.sucker_punch,
            MoveList.tearful_look,
            MoveList.u_turn,
            MoveList.water_gun,
            MoveList.water_pulse
        },
        70, 85, 65, 125, 65, 120
    );

    public static final Pokemon indeedee = new Pokemon(
        "Indeedee",
        "Male",
        8,
        TypeList.psychic, TypeList.normal,
        new double[] {100, 0},
        28,
        new Ability[] {
            AbilityList.inner_focus,
            AbilityList.synchronize,
            AbilityList.psychic_surge
        },
        new Move[] {
            MoveList.after_you,
            MoveList.calm_mind,
            MoveList.disarming_voice,
            MoveList.encore,
            MoveList.healing_wish,
            MoveList.helping_hand,
            MoveList.last_resort,
            MoveList.play_nice,
            MoveList.power_split,
            MoveList.psybeam,
            MoveList.psychic_terrain,
            MoveList.psychic,
            MoveList.stored_power
        },
        60, 65, 55, 105, 95, 95
    );

    public static final Pokemon indeedee_female = new Pokemon( // Forma Alternativa
        "Indeedee",
        "Female",
        true,
        8,
        TypeList.psychic, TypeList.normal,
        new double[] {0, 100},
        28,
        new Ability[] {
            AbilityList.own_tempo,
            AbilityList.synchronize,
            AbilityList.psychic_surge
        },
        new Move[] {
            MoveList.baton_pass,
            MoveList.calm_mind,
            MoveList.disarming_voice,
            MoveList.follow_me,
            MoveList.guard_split,
            MoveList.healing_wish,
            MoveList.helping_hand,
            MoveList.play_nice,
            MoveList.psybeam,
            MoveList.psychic_terrain,
            MoveList.psychic,
            MoveList.stored_power
        },
        PokemonList.indeedee,
        false,
        70, 55, 65, 95, 105, 85
    );

    public static final Pokemon zacian = new Pokemon(
        "Zacian",
        "Hero of Many Battles",
        8,
        TypeList.fairy,
        new double[] {0, 0},
        110,
        new Ability[] {
            AbilityList.intrepid_sword
        },
        new Move[] {
            MoveList.bite,
            MoveList.close_combat,
            MoveList.crunch,
            MoveList.giga_impact,
            MoveList.howl,
            MoveList.iron_head,
            MoveList.metal_claw,
            MoveList.moonblast,
            MoveList.noble_roar,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.sacred_sword,
            MoveList.slash,
            MoveList.swords_dance
        },
        92, 120, 115, 80, 115, 138
    );

    public static final Pokemon zacian_crowned = new Pokemon( // Forma Alternativa
        "Zacian",
        "Crowned Sword",
        true,
        8,
        TypeList.fairy, TypeList.steel,
        new double[] {0, 0},
        355,
        new Ability[] {
            AbilityList.intrepid_sword
        },
        new Move[] {
            MoveList.behemoth_blade,
            MoveList.bite,
            MoveList.close_combat,
            MoveList.crunch,
            MoveList.giga_impact,
            MoveList.howl,
            MoveList.metal_claw,
            MoveList.moonblast,
            MoveList.noble_roar,
            MoveList.quick_attack,
            MoveList.quick_guard,
            MoveList.sacred_sword,
            MoveList.slash,
            MoveList.swords_dance
        },
        PokemonList.zacian,
        false,
        92, 150, 115, 80, 115, 148
    );

    public static final Pokemon zamazenta = new Pokemon(
        "Zamazenta",
        "Hero of Many Battles",
        8,
        TypeList.fighting,
        new double[] {0, 0},
        210,
        new Ability[] {
            AbilityList.dauntless_shield
        },
        new Move[] {
            MoveList.bite,
            MoveList.close_combat,
            MoveList.crunch,
            MoveList.giga_impact,
            MoveList.howl,
            MoveList.iron_defense,
            MoveList.iron_head,
            MoveList.metal_burst,
            MoveList.metal_claw,
            MoveList.moonblast,
            MoveList.quick_attack,
            MoveList.slash,
            MoveList.wide_guard
        },
        92, 120, 115, 80, 115, 138
    );

    public static final Pokemon zamazenta_crowned = new Pokemon( // Forma Alternativa
        "Zamazenta",
        "Crowned Shield",
        true,
        8,
        TypeList.fighting, TypeList.steel,
        new double[] {0, 0},
        785,
        new Ability[] {
            AbilityList.dauntless_shield
        },
        new Move[] {
            MoveList.behemoth_bash,
            MoveList.bite,
            MoveList.close_combat,
            MoveList.crunch,
            MoveList.giga_impact,
            MoveList.howl,
            MoveList.iron_defense,
            MoveList.metal_burst,
            MoveList.metal_claw,
            MoveList.moonblast,
            MoveList.quick_attack,
            MoveList.slash,
            MoveList.wide_guard
        },
        PokemonList.zamazenta,
        false,
        92, 120, 140, 80, 140, 128
    );

    public static final Pokemon eternatus = new Pokemon(
        "Eternatus",
        8,
        TypeList.poison, TypeList.dragon,
        new double[] {0, 0},
        950,
        new Ability[] {
            AbilityList.pressure
        },
        new Move[] {
            MoveList.agility,
            MoveList.confuse_ray,
            MoveList.cosmic_power,
            MoveList.cross_poison,
            MoveList.dragon_dance,
            MoveList.dragon_pulse,
            MoveList.dragon_tail,
            MoveList.dynamax_cannon,
            MoveList.eternabeam,
            MoveList.flamethrower,
            MoveList.hyper_beam,
            MoveList.outrage,
            MoveList.poison_tail,
            MoveList.recover,
            MoveList.toxic,
            MoveList.venoshock
        },
        140, 85, 95, 145, 95, 130
    );

    public static final Pokemon eternatus_eternamax = new Pokemon( // Forma Eternamax
        "Eternatus",
        "Eternamax",
        false,
        8,
        TypeList.poison, TypeList.dragon,
        new double[] {0, 0},
        999.9,
        new Ability[] {
            AbilityList.darkest_day
        },
        new Move[] {
            MoveList.agility,
            MoveList.confuse_ray,
            MoveList.cosmic_power,
            MoveList.cross_poison,
            MoveList.dragon_dance,
            MoveList.dragon_pulse,
            MoveList.dragon_tail,
            MoveList.dynamax_cannon,
            MoveList.eternabeam,
            MoveList.flamethrower,
            MoveList.hyper_beam,
            MoveList.outrage,
            MoveList.poison_tail,
            MoveList.recover,
            MoveList.toxic,
            MoveList.venoshock
        },
        PokemonList.eternatus,
        true,
        140, 85, 95, 145, 95, 130
    );

    public static final Pokemon kubfu = new Pokemon(
        "Kubfu",
        8,
        TypeList.fighting,
        new double[] {87.5, 12.5},
        12,
        new Ability[] {
            AbilityList.inner_focus
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.brick_break,
            MoveList.bulk_up,
            MoveList.close_combat,
            MoveList.counter,
            MoveList.detect,
            MoveList.dynamic_punch,
            MoveList.endure,
            MoveList.focus_energy,
            MoveList.focus_punch,
            MoveList.headbutt,
            MoveList.iron_head,
            MoveList.leer,
            MoveList.rock_smash,
            MoveList.scary_face
        },
        60, 90, 60, 53, 50, 72
    );

    public static final Pokemon urshifu = new Pokemon(
        "Urshifu",
        "Single Strike",
        8,
        TypeList.fighting, TypeList.dark,
        new double[] {87.5, 12.5},
        105,
        new Ability[] {
            AbilityList.unseen_fist
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.brick_break,
            MoveList.bulk_up,
            MoveList.close_combat,
            MoveList.counter,
            MoveList.detect,
            MoveList.dynamic_punch,
            MoveList.endure,
            MoveList.focus_energy,
            MoveList.focus_punch,
            MoveList.headbutt,
            MoveList.iron_head,
            MoveList.leer,
            MoveList.rock_smash,
            MoveList.scary_face,
            MoveList.sucker_punch,
            MoveList.wicked_blow
        },
        100, 130, 100, 63, 60, 97
    );

    public static final Pokemon urshifu_rapid = new Pokemon( // Forma Alternativa
        "Urshifu",
        "Rapid Strike",
        true,
        8,
        TypeList.fighting, TypeList.water,
        new double[] {87.5, 12.5},
        105,
        new Ability[] {
            AbilityList.unseen_fist
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.aqua_jet,
            MoveList.brick_break,
            MoveList.bulk_up,
            MoveList.close_combat,
            MoveList.counter,
            MoveList.detect,
            MoveList.dynamic_punch,
            MoveList.endure,
            MoveList.focus_energy,
            MoveList.focus_punch,
            MoveList.headbutt,
            MoveList.iron_head,
            MoveList.leer,
            MoveList.rock_smash,
            MoveList.scary_face,
            MoveList.surging_strikes
        },
        PokemonList.urshifu,
        false,
        100, 130, 100, 63, 60, 97
    );

    public static final Pokemon zarude = new Pokemon(
        "Zarude",
        8,
        TypeList.dark, TypeList.grass,
        new double[] {0, 0},
        70,
        new Ability[] {
            AbilityList.leaf_guard
        },
        new Move[] {
            MoveList.bind,
            MoveList.bite,
            MoveList.energy_ball,
            MoveList.fury_swipes,
            MoveList.grass_knot,
            MoveList.growth,
            MoveList.hammer_arm,
            MoveList.jungle_healing,
            MoveList.leer,
            MoveList.power_whip,
            MoveList.scary_face,
            MoveList.scratch,
            MoveList.swagger,
            MoveList.synthesis,
            MoveList.thrash,
            MoveList.u_turn,
            MoveList.vine_whip
        },
        105, 120, 105, 70, 95, 105
    );

    public static final Pokemon regieleki = new Pokemon(
        "Regieleki",
        8,
        TypeList.electric,
        new double[] {0, 0},
        145,
        new Ability[] {
            AbilityList.clear_body,
            AbilityList.transistor
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.electroweb,
            MoveList.explosion,
            MoveList.extreme_speed,
            MoveList.hyper_beam,
            MoveList.lock_on,
            MoveList.magnet_rise,
            MoveList.rapid_spin,
            MoveList.shock_wave,
            MoveList.thrash,
            MoveList.thunder_cage,
            MoveList.thunder_shock,
            MoveList.thunder_wave,
            MoveList.thunderbolt,
            MoveList.zap_cannon
        },
        80, 100, 50, 100, 50, 200
    );

    public static final Pokemon regidrago = new Pokemon(
        "Regidrago",
        8,
        TypeList.dragon,
        new double[] {0, 0},
        200,
        new Ability[] {
            AbilityList.clear_body,
            AbilityList.dragons_maw
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.bite,
            MoveList.crunch,
            MoveList.dragon_breath,
            MoveList.dragon_claw,
            MoveList.dragon_dance,
            MoveList.dragon_energy,
            MoveList.explosion,
            MoveList.focus_energy,
            MoveList.hammer_arm,
            MoveList.hyper_beam,
            MoveList.thrash,
            MoveList.twister,
            MoveList.vise_grip
        },
        200, 100, 50, 100, 50, 80
    );

    public static final Pokemon glastrier = new Pokemon(
        "Glastrier",
        8,
        TypeList.ice,
        new double[] {0, 0},
        800,
        new Ability[] {
            AbilityList.chilling_neigh
        },
        new Move[] {
            MoveList.avalanche,
            MoveList.double_edge,
            MoveList.double_kick,
            MoveList.icicle_crash,
            MoveList.iron_defense,
            MoveList.mist,
            MoveList.stomp,
            MoveList.swords_dance,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.take_down,
            MoveList.taunt,
            MoveList.thrash,
            MoveList.torment
        },
        100, 145, 130, 65, 110, 30
    );

    public static final Pokemon spectrier = new Pokemon(
        "Spectrier",
        8,
        TypeList.ghost,
        new double[] {0, 0},
        44.5,
        new Ability[] {
            AbilityList.grim_neigh
        },
        new Move[] {
            MoveList.agility,
            MoveList.confuse_ray,
            MoveList.disable,
            MoveList.double_edge,
            MoveList.double_kick,
            MoveList.haze,
            MoveList.hex,
            MoveList.nasty_plot,
            MoveList.shadow_ball,
            MoveList.stomp,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.take_down,
            MoveList.thrash
        },
        100, 65, 60, 145, 80, 130
    );

    public static final Pokemon calyrex = new Pokemon(
        "Calyrex",
        "Steedless",
        8,
        TypeList.psychic, TypeList.grass,
        new double[] {0, 0},
        7.7,
        new Ability[] {
            AbilityList.unnerve
        },
        new Move[] {
            MoveList.confusion,
            MoveList.energy_ball,
            MoveList.future_sight,
            MoveList.giga_drain,
            MoveList.grassy_terrain,
            MoveList.growth,
            MoveList.heal_pulse,
            MoveList.helping_hand,
            MoveList.leech_seed,
            MoveList.life_dew,
            MoveList.mega_drain,
            MoveList.pound,
            MoveList.psychic_terrain,
            MoveList.psychic,
            MoveList.psyshock,
            MoveList.solar_beam
        },
        100, 80, 80, 80, 80, 80
    );

    public static final Pokemon calyrex_ice = new Pokemon( // Forma Alternativa
        "Calyrex",
        "Ice Rider",
        true,
        8,
        TypeList.psychic, TypeList.ice,
        new double[] {0, 0},
        809.1,
        new Ability[] {
            AbilityList.as_one_ice
        },
        new Move[] {
            MoveList.avalanche,
            MoveList.confusion,
            MoveList.double_edge,
            MoveList.double_kick,
            MoveList.energy_ball,
            MoveList.future_sight,
            MoveList.giga_drain,
            MoveList.glacial_lance,
            MoveList.grassy_terrain,
            MoveList.growth,
            MoveList.heal_pulse,
            MoveList.helping_hand,
            MoveList.icicle_crash,
            MoveList.iron_defense,
            MoveList.leech_seed,
            MoveList.life_dew,
            MoveList.mega_drain,
            MoveList.mist,
            MoveList.pound,
            MoveList.psychic_terrain,
            MoveList.psychic,
            MoveList.psyshock,
            MoveList.solar_beam,
            MoveList.stomp,
            MoveList.swords_dance,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.take_down,
            MoveList.taunt,
            MoveList.thrash,
            MoveList.torment
        },
        PokemonList.calyrex,
        false,
        100, 165, 150, 85, 130, 50
    );

    public static final Pokemon calyrex_shadow = new Pokemon( // Forma Alternativa
        "Calyrex",
        "Shadow Rider",
        true,
        8,
        TypeList.psychic, TypeList.ghost,
        new double[] {0, 0},
        53.6,
        new Ability[] {
            AbilityList.as_one_shadow
        },
        new Move[] {
            MoveList.agility,
            MoveList.astral_barrage,
            MoveList.confuse_ray,
            MoveList.confusion,
            MoveList.disable,
            MoveList.double_edge,
            MoveList.double_kick,
            MoveList.energy_ball,
            MoveList.future_sight,
            MoveList.giga_drain,
            MoveList.grassy_terrain,
            MoveList.growth,
            MoveList.haze,
            MoveList.heal_pulse,
            MoveList.helping_hand,
            MoveList.hex,
            MoveList.leech_seed,
            MoveList.life_dew,
            MoveList.mega_drain,
            MoveList.nasty_plot,
            MoveList.pound,
            MoveList.psychic_terrain,
            MoveList.psychic,
            MoveList.psyshock,
            MoveList.shadow_ball,
            MoveList.solar_beam,
            MoveList.stomp,
            MoveList.tackle,
            MoveList.tail_whip,
            MoveList.take_down,
            MoveList.thrash
        },
        PokemonList.calyrex,
        false,
        100, 85, 80, 165, 100, 150
    );

    public static final Pokemon enamorus = new Pokemon(
        "Enamorus",
        "Incarnate",
        8,
        TypeList.fairy, TypeList.flying,
        new double[] {0, 100},
        48,
        new Ability[] {
            AbilityList.cute_charm,
            AbilityList.contrary
        },
        new Move[] {
            MoveList.astonish,
            MoveList.dazzling_gleam,
            MoveList.draining_kiss,
            MoveList.extrasensory,
            MoveList.fairy_wind,
            MoveList.flatter,
            MoveList.healing_wish,
            MoveList.imprison,
            MoveList.iron_defense,
            MoveList.moonblast,
            MoveList.mystical_fire,
            MoveList.outrage,
            MoveList.springtide_storm,
            MoveList.sunny_day,
            MoveList.superpower,
            MoveList.torment,
            MoveList.twister,
            MoveList.uproar
        },
        74, 115, 70, 135, 80, 106
    );

    public static final Pokemon enamorus_therian = new Pokemon( // Forma Alternativa
        "Enamorus",
        "Therian",
        8,
        TypeList.fairy, TypeList.flying,
        new double[] {0, 100},
        48,
        new Ability[] {
            AbilityList.overcoat
        },
        new Move[] {
            MoveList.astonish,
            MoveList.dazzling_gleam,
            MoveList.draining_kiss,
            MoveList.extrasensory,
            MoveList.fairy_wind,
            MoveList.flatter,
            MoveList.healing_wish,
            MoveList.imprison,
            MoveList.iron_defense,
            MoveList.moonblast,
            MoveList.mystical_fire,
            MoveList.outrage,
            MoveList.springtide_storm,
            MoveList.sunny_day,
            MoveList.superpower,
            MoveList.torment,
            MoveList.twister,
            MoveList.uproar
        },
        74, 115, 110, 135, 100, 46
    );


    /* Geração 9 */

    public static final Pokemon sprigatito = new Pokemon(
        "Sprigatito",
        9,
        TypeList.grass,
        new double[] {87.5, 12.5},
        4.1,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.protean
        },
        new Move[] {
            MoveList.bite,
            MoveList.energy_ball,
            MoveList.hone_claws,
            MoveList.leafage,
            MoveList.magical_leaf,
            MoveList.play_rough,
            MoveList.quick_attack,
            MoveList.scratch,
            MoveList.seed_bomb,
            MoveList.slash,
            MoveList.tail_whip,
            MoveList.u_turn,
            MoveList.worry_seed
        },
        40, 61, 54, 45, 45, 65
    );

    public static final Pokemon floragato = new Pokemon(
        "Floragato",
        9,
        TypeList.grass,
        new double[] {87.5, 12.5},
        12.2,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.protean
        },
        new Move[] {
            MoveList.bite,
            MoveList.energy_ball,
            MoveList.hone_claws,
            MoveList.leaf_storm,
            MoveList.leafage,
            MoveList.magical_leaf,
            MoveList.play_rough,
            MoveList.quick_attack,
            MoveList.scratch,
            MoveList.seed_bomb,
            MoveList.slash,
            MoveList.tail_whip,
            MoveList.u_turn,
            MoveList.worry_seed
        },
        61, 80, 63, 60, 63, 83
    );

    public static final Pokemon meowscarada = new Pokemon(
        "Meowscarada",
        9,
        TypeList.grass, TypeList.dark,
        new double[] {87.5, 12.5},
        31.2,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.protean
        },
        new Move[] {
            MoveList.bite,
            MoveList.double_team,
            MoveList.energy_ball,
            MoveList.flower_trick,
            MoveList.grassy_terrain,
            MoveList.hone_claws,
            MoveList.knock_off,
            MoveList.leaf_storm,
            MoveList.leafage,
            MoveList.magical_leaf,
            MoveList.night_slash,
            MoveList.play_rough,
            MoveList.quick_attack,
            MoveList.scratch,
            MoveList.seed_bomb,
            MoveList.slash,
            MoveList.tail_whip,
            MoveList.trick,
            MoveList.u_turn,
            MoveList.worry_seed
        },
        76, 110, 70, 81, 70, 123
    );

    public static final Pokemon fuecoco = new Pokemon(
        "Fuecoco",
        9,
        TypeList.fire,
        new double[] {87.5, 12.5},
        9.8,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.unaware
        },
        new Move[] {
            MoveList.bite,
            MoveList.ember,
            MoveList.fire_blast,
            MoveList.flamethrower,
            MoveList.hyper_voice,
            MoveList.incinerate,
            MoveList.leer,
            MoveList.roar,
            MoveList.round,
            MoveList.snarl,
            MoveList.tackle,
            MoveList.yawn
        },
        67, 45, 59, 63, 40, 36
    );

    public static final Pokemon crocalor = new Pokemon(
        "Crocalor",
        9,
        TypeList.fire,
        new double[] {87.5, 12.5},
        30.7,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.unaware
        },
        new Move[] {
            MoveList.bite,
            MoveList.ember,
            MoveList.fire_blast,
            MoveList.flamethrower,
            MoveList.hyper_voice,
            MoveList.incinerate,
            MoveList.leer,
            MoveList.lick,
            MoveList.roar,
            MoveList.round,
            MoveList.snarl,
            MoveList.tackle,
            MoveList.will_o_wisp,
            MoveList.yawn
        },
        81, 55, 78, 90, 58, 49
    );

    public static final Pokemon skeledirge = new Pokemon(
        "Skeledirge",
        9,
        TypeList.fire, TypeList.ghost,
        new double[] {87.5, 12.5},
        326.5,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.unaware
        },
        new Move[] {
            MoveList.bite,
            MoveList.ember,
            MoveList.fire_blast,
            MoveList.flamethrower,
            MoveList.hex,
            MoveList.hyper_voice,
            MoveList.incinerate,
            MoveList.leer,
            MoveList.lick,
            MoveList.overheat,
            MoveList.roar,
            MoveList.round,
            MoveList.scary_face,
            MoveList.shadow_ball,
            MoveList.sing,
            MoveList.snarl,
            MoveList.tackle,
            MoveList.torch_song,
            MoveList.will_o_wisp,
            MoveList.yawn
        },
        104, 75, 100, 110, 75, 66
    );

    public static final Pokemon quaxly = new Pokemon(
        "Quaxly",
        9,
        TypeList.water,
        new double[] {87.5, 12.5},
        6.1,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.moxie
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.air_slash,
            MoveList.aqua_cutter,
            MoveList.aqua_jet,
            MoveList.double_hit,
            MoveList.focus_energy,
            MoveList.growl,
            MoveList.liquidation,
            MoveList.pound,
            MoveList.water_gun,
            MoveList.wing_attack,
            MoveList.work_up
        },
        55, 65, 45, 50, 45, 50
    );

    public static final Pokemon quaxwell = new Pokemon(
        "Quaxwell",
        9,
        TypeList.water,
        new double[] {87.5, 12.5},
        21.5,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.moxie
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.air_slash,
            MoveList.aqua_cutter,
            MoveList.aqua_jet,
            MoveList.double_hit,
            MoveList.feather_dance,
            MoveList.focus_energy,
            MoveList.growl,
            MoveList.liquidation,
            MoveList.low_sweep,
            MoveList.pound,
            MoveList.water_gun,
            MoveList.water_pulse,
            MoveList.wing_attack,
            MoveList.work_up
        },
        70, 85, 65, 65, 60, 65
    );

    public static final Pokemon quaquaval = new Pokemon(
        "Quaquaval",
        9,
        TypeList.water,
        new double[] {87.5, 12.5},
        61.9,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.moxie
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.air_slash,
            MoveList.aqua_cutter,
            MoveList.aqua_jet,
            MoveList.aqua_step,
            MoveList.close_combat,
            MoveList.counter,
            MoveList.double_hit,
            MoveList.feather_dance,
            MoveList.focus_energy,
            MoveList.growl,
            MoveList.liquidation,
            MoveList.low_sweep,
            MoveList.mega_kick,
            MoveList.pound,
            MoveList.water_gun,
            MoveList.water_pulse,
            MoveList.wave_crash,
            MoveList.wing_attack,
            MoveList.work_up
        },
        85, 120, 80, 85, 75, 85
    );

    public static final Pokemon finizen = new Pokemon(
        "Finizen",
        9,
        TypeList.water,
        new double[] {50, 50},
        60.2,
        new Ability[] {
            AbilityList.water_veil
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.aqua_jet,
            MoveList.aqua_tail,
            MoveList.astonish,
            MoveList.charm,
            MoveList.dive,
            MoveList.double_hit,
            MoveList.encore,
            MoveList.focus_energy,
            MoveList.hydro_pump,
            MoveList.mist,
            MoveList.supersonic,
            MoveList.water_gun
        },
        70, 45, 40, 45, 40, 75
    );

    public static final Pokemon palafin = new Pokemon(
        "Palafin",
        "Zero",
        9,
        TypeList.water,
        new double[] {50, 50},
        60.2,
        new Ability[] {
            AbilityList.zero_to_hero
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.aqua_jet,
            MoveList.aqua_tail,
            MoveList.astonish,
            MoveList.charm,
            MoveList.dive,
            MoveList.double_hit,
            MoveList.encore,
            MoveList.flip_turn,
            MoveList.focus_energy,
            MoveList.focus_punch,
            MoveList.hydro_pump,
            MoveList.jet_punch,
            MoveList.mist,
            MoveList.supersonic,
            MoveList.water_gun,
            MoveList.wave_crash
        },
        100, 70, 72, 53, 62, 100
    );

    public static final Pokemon palafin_hero = new Pokemon( // Forma Alternativa
        "Palafin",
        "Hero",
        false,
        9,
        TypeList.water,
        new double[] {50, 50},
        97.4,
        new Ability[] {
            AbilityList.zero_to_hero
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.aqua_jet,
            MoveList.aqua_tail,
            MoveList.astonish,
            MoveList.charm,
            MoveList.dive,
            MoveList.double_hit,
            MoveList.encore,
            MoveList.flip_turn,
            MoveList.focus_energy,
            MoveList.focus_punch,
            MoveList.hydro_pump,
            MoveList.jet_punch,
            MoveList.mist,
            MoveList.supersonic,
            MoveList.water_gun,
            MoveList.wave_crash
        },
        PokemonList.palafin,
        false,
        100, 160, 97, 106, 87, 100
    );

    public static final Pokemon wo_chien = new Pokemon(
        "Wo-Chien",
        9,
        TypeList.dark, TypeList.grass,
        new double[] {0, 0},
        74.2,
        new Ability[] {
            AbilityList.tablets_of_ruin
        },
        new Move[] {
            MoveList.absorb,
            MoveList.dark_pulse,
            MoveList.foul_play,
            MoveList.giga_drain,
            MoveList.grassy_terrain,
            MoveList.growth,
            MoveList.ingrain,
            MoveList.knock_off,
            MoveList.leaf_storm,
            MoveList.leech_seed,
            MoveList.mean_look,
            MoveList.mega_drain,
            MoveList.payback,
            MoveList.poison_powder,
            MoveList.power_whip,
            MoveList.ruination,
            MoveList.spite,
            MoveList.stun_spore,
            MoveList.tickle
        },
        85, 85, 100, 95, 135, 70
    );

    public static final Pokemon chien_pao = new Pokemon(
        "Chien-Pao",
        9,
        TypeList.dark, TypeList.ice,
        new double[] {0, 0},
        152.2,
        new Ability[] {
            AbilityList.sword_of_ruin
        },
        new Move[] {
            MoveList.dark_pulse,
            MoveList.haze,
            MoveList.ice_shard,
            MoveList.icicle_crash,
            MoveList.icy_wind,
            MoveList.mean_look,
            MoveList.mist,
            MoveList.night_slash,
            MoveList.payback,
            MoveList.powder_snow,
            MoveList.recover,
            MoveList.ruination,
            MoveList.sacred_sword,
            MoveList.sheer_cold,
            MoveList.snowscape,
            MoveList.spite,
            MoveList.sucker_punch,
            MoveList.swords_dance,
            MoveList.throat_chop
        },
        80, 120, 80, 90, 65, 135
    );

    public static final Pokemon ting_lu = new Pokemon(
        "Ting-Lu",
        9,
        TypeList.dark, TypeList.ground,
        new double[] {0, 0},
        699.7,
        new Ability[] {
            AbilityList.vessel_of_ruin
        },
        new Move[] {
            MoveList.bulldoze,
            MoveList.dark_pulse,
            MoveList.earthquake,
            MoveList.fissure,
            MoveList.mean_look,
            MoveList.memento,
            MoveList.payback,
            MoveList.rock_slide,
            MoveList.ruination,
            MoveList.sand_tomb,
            MoveList.spikes,
            MoveList.spite,
            MoveList.stomp,
            MoveList.stomping_tantrum,
            MoveList.taunt,
            MoveList.thrash,
            MoveList.throat_chop,
            MoveList.whirlwind
        },
        155, 110, 125, 55, 80, 45
    );

    public static final Pokemon chi_yu = new Pokemon(
        "Chi-Yu",
        9,
        TypeList.dark, TypeList.fire,
        new double[] {0, 0},
        4.9,
        new Ability[] {
            AbilityList.beads_of_ruin
        },
        new Move[] {
            MoveList.bounce,
            MoveList.confuse_ray,
            MoveList.dark_pulse,
            MoveList.ember,
            MoveList.flame_charge,
            MoveList.flame_wheel,
            MoveList.incinerate,
            MoveList.inferno,
            MoveList.lava_plume,
            MoveList.mean_look,
            MoveList.memento,
            MoveList.nasty_plot,
            MoveList.overheat,
            MoveList.payback,
            MoveList.ruination,
            MoveList.spite,
            MoveList.swagger,
            MoveList.will_o_wisp
        },
        55, 80, 80, 135, 120, 100
    );

    public static final Pokemon koraidon = new Pokemon(
        "Koraidon",
        9,
        TypeList.fighting, TypeList.dragon,
        new double[] {0, 0},
        303,
        new Ability[] {
            AbilityList.orichalcum_pulse
        },
        new Move[] {
            MoveList.agility,
            MoveList.ancient_power,
            MoveList.breaking_swipe,
            MoveList.brick_break,
            MoveList.close_combat,
            MoveList.collision_course,
            MoveList.counter,
            MoveList.dragon_claw,
            MoveList.drain_punch,
            MoveList.flamethrower,
            MoveList.flare_blitz,
            MoveList.giga_impact,
            MoveList.outrage,
            MoveList.rock_smash,
            MoveList.screech,
            MoveList.sunny_day
        },
        100, 135, 115, 85, 100, 135
    );

    public static final Pokemon miraidon = new Pokemon(
        "Miraidon",
        9,
        TypeList.electric, TypeList.dragon,
        new double[] {0, 0},
        240,
        new Ability[] {
            AbilityList.hadron_engine
        },
        new Move[] {
            MoveList.agility,
            MoveList.charge,
            MoveList.discharge,
            MoveList.dragon_breath,
            MoveList.dragon_pulse,
            MoveList.electric_terrain,
            MoveList.electro_drift,
            MoveList.hyper_beam,
            MoveList.metal_sound,
            MoveList.mirror_coat,
            MoveList.outrage,
            MoveList.overheat,
            MoveList.parabolic_charge,
            MoveList.shock_wave,
            MoveList.thunder_shock,
            MoveList.thunder
        },
        100, 85, 100, 135, 115, 135
    );

    public static final Pokemon okidogi = new Pokemon(
        "Okidogi",
        9,
        TypeList.poison, TypeList.fighting,
        new double[] {100, 0},
        92.2,
        new Ability[] {
            AbilityList.toxic_chain,
            AbilityList.guard_dog
        },
        new Move[] {
            MoveList.bite,
            MoveList.brutal_swing,
            MoveList.bulk_up,
            MoveList.counter,
            MoveList.crunch,
            MoveList.force_palm,
            MoveList.giga_impact,
            MoveList.howl,
            MoveList.low_kick,
            MoveList.poison_fang,
            MoveList.poison_jab,
            MoveList.superpower
        },
        88, 128, 115, 58, 86, 80
    );

    public static final Pokemon munkidori = new Pokemon(
        "Munkidori",
        9,
        TypeList.poison, TypeList.psychic,
        new double[] {100, 0},
        12.2,
        new Ability[] {
            AbilityList.toxic_chain,
            AbilityList.frisk
        },
        new Move[] {
            MoveList.clear_smog,
            MoveList.confusion,
            MoveList.fake_out,
            MoveList.flatter,
            MoveList.future_sight,
            MoveList.helping_hand,
            MoveList.nasty_plot,
            MoveList.parting_shot,
            MoveList.poison_jab,
            MoveList.psybeam,
            MoveList.psychic,
            MoveList.scratch,
            MoveList.sludge_wave
        },
        88, 75, 66, 130, 90, 106
    );

    public static final Pokemon fezandipiti = new Pokemon(
        "Fezandipiti",
        9,
        TypeList.poison, TypeList.fairy,
        new double[] {100, 0},
        30.1,
        new Ability[] {
            AbilityList.toxic_chain,
            AbilityList.technician
        },
        new Move[] {
            MoveList.attract,
            MoveList.beat_up,
            MoveList.cross_poison,
            MoveList.disarming_voice,
            MoveList.double_kick,
            MoveList.flatter,
            MoveList.moonblast,
            MoveList.peck,
            MoveList.poison_gas,
            MoveList.quick_attack,
            MoveList.roost,
            MoveList.swagger,
            MoveList.tail_slap,
            MoveList.wing_attack
        },
        88, 91, 82, 70, 125, 99
    );

    public static final Pokemon ogerpon = new Pokemon(
        "Ogerpon",
        "Teal",
        9,
        TypeList.grass,
        new double[] {0, 100},
        39.8,
        new Ability[] {
            AbilityList.defiant
        },
        new Move[] {
            MoveList.counter,
            MoveList.double_kick,
            MoveList.focus_energy,
            MoveList.follow_me,
            MoveList.growth,
            MoveList.horn_leech,
            MoveList.ivy_cudgel,
            MoveList.leech_seed,
            MoveList.low_sweep,
            MoveList.power_whip,
            MoveList.quick_attack,
            MoveList.retaliate,
            MoveList.slam,
            MoveList.spiky_shield,
            MoveList.superpower,
            MoveList.synthesis,
            MoveList.throat_chop,
            MoveList.vine_whip,
            MoveList.wood_hammer
        },
        80, 120, 84, 60, 96, 110
    );

    public static final Pokemon ogerpon_wellspring = new Pokemon( // Forma Alternativa
        "Ogerpon",
        "Wellspring",
        true,
        9,
        TypeList.grass, TypeList.water,
        new double[] {0, 100},
        39.8,
        new Ability[] {
            AbilityList.water_absorb
        },
        new Move[] {
            MoveList.counter,
            MoveList.double_kick,
            MoveList.focus_energy,
            MoveList.follow_me,
            MoveList.growth,
            MoveList.horn_leech,
            MoveList.ivy_cudgel,
            MoveList.leech_seed,
            MoveList.low_sweep,
            MoveList.power_whip,
            MoveList.quick_attack,
            MoveList.retaliate,
            MoveList.slam,
            MoveList.spiky_shield,
            MoveList.superpower,
            MoveList.synthesis,
            MoveList.throat_chop,
            MoveList.vine_whip,
            MoveList.wood_hammer
        },
        PokemonList.ogerpon,
        false,
        80, 120, 84, 60, 96, 110
    );

    public static final Pokemon ogerpon_hearthflame = new Pokemon( // Forma Alternativa
        "Ogerpon",
        "Hearthflame",
        true,
        9,
        TypeList.grass, TypeList.fire,
        new double[] {0, 100},
        39.8,
        new Ability[] {
            AbilityList.mold_breaker
        },
        new Move[] {
            MoveList.counter,
            MoveList.double_kick,
            MoveList.focus_energy,
            MoveList.follow_me,
            MoveList.growth,
            MoveList.horn_leech,
            MoveList.ivy_cudgel,
            MoveList.leech_seed,
            MoveList.low_sweep,
            MoveList.power_whip,
            MoveList.quick_attack,
            MoveList.retaliate,
            MoveList.slam,
            MoveList.spiky_shield,
            MoveList.superpower,
            MoveList.synthesis,
            MoveList.throat_chop,
            MoveList.vine_whip,
            MoveList.wood_hammer
        },
        PokemonList.ogerpon,
        false,
        80, 120, 84, 60, 96, 110
    );

    public static final Pokemon ogerpon_cornerstone = new Pokemon( // Forma Alternativa
        "Ogerpon",
        "Cornerstone",
        true,
        9,
        TypeList.grass, TypeList.rock,
        new double[] {0, 100},
        39.8,
        new Ability[] {
            AbilityList.sturdy
        },
        new Move[] {
            MoveList.counter,
            MoveList.double_kick,
            MoveList.focus_energy,
            MoveList.follow_me,
            MoveList.growth,
            MoveList.horn_leech,
            MoveList.ivy_cudgel,
            MoveList.leech_seed,
            MoveList.low_sweep,
            MoveList.power_whip,
            MoveList.quick_attack,
            MoveList.retaliate,
            MoveList.slam,
            MoveList.spiky_shield,
            MoveList.superpower,
            MoveList.synthesis,
            MoveList.throat_chop,
            MoveList.vine_whip,
            MoveList.wood_hammer
        },
        PokemonList.ogerpon,
        false,
        80, 120, 84, 60, 96, 110
    );

    public static final Pokemon terapagos = new Pokemon(
        "Terapagos",
        9,
        TypeList.normal,
        new double[] {50, 50},
        6.5,
        new Ability[] {
            AbilityList.tera_shift
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.double_edge,
            MoveList.earth_power,
            MoveList.gyro_ball,
            MoveList.headbutt,
            MoveList.heavy_slam,
            MoveList.protect,
            MoveList.rapid_spin,
            MoveList.rock_polish,
            MoveList.tera_starstorm,
            MoveList.tri_attack,
            MoveList.withdraw
        },
        90, 65, 85, 65, 85, 60
    );

    public static final Pokemon terapagos_terastal = new Pokemon( // Forma Alternativa
        "Terapagos",
        "Terastal",
        false,
        9,
        TypeList.normal,
        new double[] {50, 50},
        16,
        new Ability[] {
            AbilityList.tera_shell
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.double_edge,
            MoveList.earth_power,
            MoveList.gyro_ball,
            MoveList.headbutt,
            MoveList.heavy_slam,
            MoveList.protect,
            MoveList.rapid_spin,
            MoveList.rock_polish,
            MoveList.tera_starstorm,
            MoveList.tri_attack,
            MoveList.withdraw
        },
        PokemonList.terapagos,
        false,
        95, 95, 110, 105, 110, 85
    );

    public static final Pokemon terapagos_stellar = new Pokemon( // Forma Tera
        "Terapagos",
        "Stellar",
        false,
        9,
        TypeList.normal,
        new double[] {50, 50},
        77,
        new Ability[] {
            AbilityList.teraform_zero
        },
        new Move[] {
            MoveList.ancient_power,
            MoveList.double_edge,
            MoveList.earth_power,
            MoveList.gyro_ball,
            MoveList.headbutt,
            MoveList.heavy_slam,
            MoveList.protect,
            MoveList.rapid_spin,
            MoveList.rock_polish,
            MoveList.tera_starstorm,
            MoveList.tri_attack,
            MoveList.withdraw
        },
        PokemonList.terapagos,
        false,
        160, 105, 110, 130, 110, 85
    );

    public static final Pokemon pecharunt = new Pokemon(
        "Pecharunt",
        9,
        TypeList.poison, TypeList.ghost,
        new double[] {0, 0},
        0.3,
        new Ability[] {
            AbilityList.poison_puppeteer
        },
        new Move[] {
            MoveList.astonish,
            MoveList.defense_curl,
            MoveList.destiny_bond,
            MoveList.fake_tears,
            MoveList.malignant_chain,
            MoveList.mean_look,
            MoveList.memento,
            MoveList.nasty_plot,
            MoveList.parting_shot,
            MoveList.poison_gas,
            MoveList.recover,
            MoveList.rollout,
            MoveList.shadow_ball,
            MoveList.smog,
            MoveList.toxic,
            MoveList.withdraw
        },
        88, 88, 160, 88, 88, 88
    );


    /* Fakémon */

    public static final Pokemon chrysprout = new Pokemon(
        "Chrysprout",
        -1,
        TypeList.grass,
        new double[] {87.5, 12.5},
        3.8,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.prankster
        },
        new Move[] {
            MoveList.bite,
            MoveList.energy_ball,
            MoveList.growl,
            MoveList.howl,
            MoveList.leaf_blade,
            MoveList.leaf_tornado,
            MoveList.leafage,
            MoveList.moonlight,
            MoveList.pursuit,
            MoveList.quick_attack,
            MoveList.razor_leaf,
            MoveList.scratch,
            MoveList.swagger,
            MoveList.taunt
        },
        45, 60, 45, 45, 60, 65
    );

    public static final Pokemon chrysora = new Pokemon(
        "Chrysora",
        -1,
        TypeList.grass, TypeList.dark,
        new double[] {87.5, 12.5},
        15,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.prankster
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.bite,
            MoveList.energy_ball,
            MoveList.growl,
            MoveList.howl,
            MoveList.leaf_blade,
            MoveList.leaf_tornado,
            MoveList.leafage,
            MoveList.moonlight,
            MoveList.pursuit,
            MoveList.quick_attack,
            MoveList.razor_leaf,
            MoveList.scratch,
            MoveList.snarl,
            MoveList.swagger,
            MoveList.taunt,
            MoveList.torment
        },
        60, 85, 65, 55, 70, 85
    );

    public static final Pokemon chrypuscule = new Pokemon(
        "Chrypuscule",
        -1,
        TypeList.grass, TypeList.dark,
        new double[] {87.5, 12.5},
        34.7,
        new Ability[] {
            AbilityList.overgrow,
            AbilityList.prankster
        },
        new Move[] {
            MoveList.aerial_ace,
            MoveList.bite,
            MoveList.energy_ball,
            MoveList.facade,
            MoveList.growl,
            MoveList.howl,
            MoveList.leaf_blade,
            MoveList.leaf_storm,
            MoveList.leaf_tornado,
            MoveList.leafage,
            MoveList.moonlight,
            MoveList.night_slash,
            MoveList.pursuit,
            MoveList.quick_attack,
            MoveList.razor_leaf,
            MoveList.scratch,
            MoveList.snarl,
            MoveList.swagger,
            MoveList.taunt,
            MoveList.torment,
            MoveList.veiled_assault
        },
        70, 115, 75, 70, 85, 105
    );

    public static final Pokemon blasnake = new Pokemon(
        "Blasnake",
        -1,
        TypeList.fire, TypeList.poison,
        new double[] {87.5, 12.5},
        2.8,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.white_smoke
        },
        new Move[] {
            MoveList.acid_spray,
            MoveList.coil,
            MoveList.ember,
            MoveList.fire_fang,
            MoveList.fire_spin,
            MoveList.flamethrower,
            MoveList.glare,
            MoveList.leer,
            MoveList.lick,
            MoveList.poison_gas,
            MoveList.poison_sting,
            MoveList.sludge_bomb,
            MoveList.tackle,
            MoveList.wrap
        },
        60, 45, 60, 45, 60, 50
    );

    public static final Pokemon slinflame = new Pokemon(
        "Slinflame",
        -1,
        TypeList.fire, TypeList.poison,
        new double[] {87.5, 12.5},
        16.3,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.white_smoke
        },
        new Move[] {
            MoveList.acid_spray,
            MoveList.coil,
            MoveList.ember,
            MoveList.fire_blast,
            MoveList.fire_fang,
            MoveList.fire_spin,
            MoveList.flamethrower,
            MoveList.glare,
            MoveList.leer,
            MoveList.lick,
            MoveList.poison_gas,
            MoveList.poison_sting,
            MoveList.shadow_sneak,
            MoveList.sludge_bomb,
            MoveList.tackle,
            MoveList.will_o_wisp,
            MoveList.wrap
        },
        85, 60, 70, 75, 70, 60
    );

    public static final Pokemon boitabright = new Pokemon(
        "Boitabright",
        -1,
        TypeList.fire, TypeList.ghost,
        new double[] {87.5, 12.5},
        61.2,
        new Ability[] {
            AbilityList.blaze,
            AbilityList.white_smoke
        },
        new Move[] {
            MoveList.acid_spray,
            MoveList.coil,
            MoveList.ember,
            MoveList.fire_blast,
            MoveList.fire_fang,
            MoveList.fire_spin,
            MoveList.flamethrower,
            MoveList.glare,
            MoveList.guardian_flame,
            MoveList.hex,
            MoveList.leer,
            MoveList.lick,
            MoveList.overheat,
            MoveList.poison_gas,
            MoveList.poison_sting,
            MoveList.shadow_ball,
            MoveList.shadow_sneak,
            MoveList.sludge_bomb,
            MoveList.tackle,
            MoveList.will_o_wisp,
            MoveList.wrap
        },
        90, 85, 85, 105, 85, 70
    );

    public static final Pokemon flowphin = new Pokemon(
        "Flowphin",
        -1,
        TypeList.water,
        new double[] {87.5, 12.5},
        4.1,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.storm_drain
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.aqua_jet,
            MoveList.bubble,
            MoveList.charm,
            MoveList.dazzling_gleam,
            MoveList.disarming_voice,
            MoveList.flip_turn,
            MoveList.liquidation,
            MoveList.mist,
            MoveList.pound,
            MoveList.surf,
            MoveList.sweet_kiss,
            MoveList.tail_whip,
            MoveList.water_pulse
        },
        55, 45, 60, 60, 45, 55
    );

    public static final Pokemon cetaqua = new Pokemon(
        "Cetaqua",
        -1,
        TypeList.water, TypeList.fairy,
        new double[] {87.5, 12.5},
        17.3,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.storm_drain
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.aqua_jet,
            MoveList.bubble,
            MoveList.charm,
            MoveList.dazzling_gleam,
            MoveList.disarming_voice,
            MoveList.flip_turn,
            MoveList.hydro_pump,
            MoveList.liquidation,
            MoveList.mist,
            MoveList.play_rough,
            MoveList.pound,
            MoveList.rain_dance,
            MoveList.soak,
            MoveList.surf,
            MoveList.sweet_kiss,
            MoveList.tail_whip,
            MoveList.water_pulse
        },
        70, 55, 75, 80, 65, 75
    );

    public static final Pokemon botempest = new Pokemon(
        "Botempest",
        -1,
        TypeList.water, TypeList.fairy,
        new double[] {87.5, 12.5},
        54,
        new Ability[] {
            AbilityList.torrent,
            AbilityList.storm_drain
        },
        new Move[] {
            MoveList.acrobatics,
            MoveList.aqua_jet,
            MoveList.bubble,
            MoveList.captivate,
            MoveList.charm,
            MoveList.dazzling_gleam,
            MoveList.disarming_voice,
            MoveList.flip_turn,
            MoveList.hydro_pump,
            MoveList.hydrokinesis,
            MoveList.liquidation,
            MoveList.mist,
            MoveList.moonblast,
            MoveList.play_rough,
            MoveList.pound,
            MoveList.rain_dance,
            MoveList.soak,
            MoveList.substitute,
            MoveList.surf,
            MoveList.sweet_kiss,
            MoveList.tail_whip,
            MoveList.water_pulse
        },
        80, 70, 85, 115, 75, 95
    );



    static {
        // Evoluções

        bulbasaur.setEvolutions(new Pokemon[] {PokemonList.ivysaur});
        ivysaur.setEvolutions(new Pokemon[] {PokemonList.venusaur});
        charmander.setEvolutions(new Pokemon[] {PokemonList.charmeleon});
        charmeleon.setEvolutions(new Pokemon[] {PokemonList.charizard});
        squirtle.setEvolutions(new Pokemon[] {PokemonList.wartortle});
        wartortle.setEvolutions(new Pokemon[] {PokemonList.blastoise});
        caterpie.setEvolutions(new Pokemon[] {PokemonList.metapod});
        metapod.setEvolutions(new Pokemon[] {PokemonList.butterfree});

        chikorita.setEvolutions(new Pokemon[] {PokemonList.bayleef});
        bayleef.setEvolutions(new Pokemon[] {PokemonList.meganium});
        cyndaquil.setEvolutions(new Pokemon[] {PokemonList.quilava});
        quilava.setEvolutions(new Pokemon[] {PokemonList.typhlosion, PokemonList.typhlosion_hisui});
        totodile.setEvolutions(new Pokemon[] {PokemonList.croconaw});
        croconaw.setEvolutions(new Pokemon[] {PokemonList.feraligatr});
        togepi.setEvolutions(new Pokemon[] {PokemonList.togetic});
        togetic.setEvolutions(new Pokemon[] {PokemonList.togekiss});

        treecko.setEvolutions(new Pokemon[] {PokemonList.grovyle});
        grovyle.setEvolutions(new Pokemon[] {PokemonList.sceptile});
        torchic.setEvolutions(new Pokemon[] {PokemonList.combusken});
        combusken.setEvolutions(new Pokemon[] {PokemonList.blaziken});
        mudkip.setEvolutions(new Pokemon[] {PokemonList.marshtomp});
        marshtomp.setEvolutions(new Pokemon[] {PokemonList.swampert});

        turtwig.setEvolutions(new Pokemon[] {PokemonList.grotle});
        grotle.setEvolutions(new Pokemon[] {PokemonList.torterra});
        chimchar.setEvolutions(new Pokemon[] {PokemonList.monferno});
        monferno.setEvolutions(new Pokemon[] {PokemonList.infernape});
        piplup.setEvolutions(new Pokemon[] {PokemonList.prinplup});
        prinplup.setEvolutions(new Pokemon[] {PokemonList.empoleon});
        riolu.setEvolutions(new Pokemon[] {PokemonList.lucario});

        snivy.setEvolutions(new Pokemon[] {PokemonList.servine});
        servine.setEvolutions(new Pokemon[] {PokemonList.serperior});
        tepig.setEvolutions(new Pokemon[] {PokemonList.pignite});
        pignite.setEvolutions(new Pokemon[] {PokemonList.emboar});
        oshawott.setEvolutions(new Pokemon[] {PokemonList.dewott});
        dewott.setEvolutions(new Pokemon[] {PokemonList.samurott, PokemonList.samurott_hisui});
        zorua.setEvolutions(new Pokemon[] {PokemonList.zoroark});
        zorua_hisui.setEvolutions(new Pokemon[] {PokemonList.zoroark_hisui});

        chespin.setEvolutions(new Pokemon[] {PokemonList.quilladin});
        quilladin.setEvolutions(new Pokemon[] {PokemonList.chesnaught});
        fennekin.setEvolutions(new Pokemon[] {PokemonList.braixen});
        braixen.setEvolutions(new Pokemon[] {PokemonList.delphox});
        froakie.setEvolutions(new Pokemon[] {PokemonList.frogadier});
        frogadier.setEvolutions(new Pokemon[] {PokemonList.greninja});

        rowlet.setEvolutions(new Pokemon[] {PokemonList.dartrix});
        dartrix.setEvolutions(new Pokemon[] {PokemonList.decidueye, PokemonList.decidueye_hisui});
        litten.setEvolutions(new Pokemon[] {PokemonList.torracat});
        torracat.setEvolutions(new Pokemon[] {PokemonList.incineroar});
        popplio.setEvolutions(new Pokemon[] {PokemonList.brionne});
        brionne.setEvolutions(new Pokemon[] {PokemonList.primarina});
        grubbin.setEvolutions(new Pokemon[] {PokemonList.charjabug});
        charjabug.setEvolutions(new Pokemon[] {PokemonList.vikavolt});
        type_null.setEvolutions(new Pokemon[] {PokemonList.silvally});
        cosmog.setEvolutions(new Pokemon[] {PokemonList.cosmoem});
        cosmoem.setEvolutions(new Pokemon[] {PokemonList.solgaleo, PokemonList.lunala});
        meltan.setEvolutions(new Pokemon[] {PokemonList.melmetal});

        grookey.setEvolutions(new Pokemon[] {PokemonList.thwackey});
        thwackey.setEvolutions(new Pokemon[] {PokemonList.rillaboom});
        scorbunny.setEvolutions(new Pokemon[] {PokemonList.raboot});
        raboot.setEvolutions(new Pokemon[] {PokemonList.cinderace});
        sobble.setEvolutions(new Pokemon[] {PokemonList.drizzile});
        drizzile.setEvolutions(new Pokemon[] {PokemonList.inteleon});
        kubfu.setEvolutions(new Pokemon[] {PokemonList.urshifu, PokemonList.urshifu_rapid});

        sprigatito.setEvolutions(new Pokemon[] {PokemonList.floragato});
        floragato.setEvolutions(new Pokemon[] {PokemonList.meowscarada});
        fuecoco.setEvolutions(new Pokemon[] {PokemonList.crocalor});
        crocalor.setEvolutions(new Pokemon[] {PokemonList.skeledirge});
        quaxly.setEvolutions(new Pokemon[] {PokemonList.quaxwell});
        quaxwell.setEvolutions(new Pokemon[] {PokemonList.quaquaval});
        finizen.setEvolutions(new Pokemon[] {PokemonList.palafin});

        chrysprout.setEvolutions(new Pokemon[] {PokemonList.chrysora});
        chrysora.setEvolutions(new Pokemon[] {PokemonList.chrypuscule});
        blasnake.setEvolutions(new Pokemon[] {PokemonList.slinflame});
        slinflame.setEvolutions(new Pokemon[] {PokemonList.boitabright});
        flowphin.setEvolutions(new Pokemon[] {PokemonList.cetaqua});
        cetaqua.setEvolutions(new Pokemon[] {PokemonList.botempest});


        // Formas Alternativas

        castform.setForms(new Pokemon[] {PokemonList.castform, PokemonList.castform_sunny, PokemonList.castform_rainy, PokemonList.castform_snowy});
        castform_sunny.setForms(new Pokemon[] {PokemonList.castform, PokemonList.castform_sunny, PokemonList.castform_rainy, PokemonList.castform_snowy});
        castform_rainy.setForms(new Pokemon[] {PokemonList.castform, PokemonList.castform_sunny, PokemonList.castform_rainy, PokemonList.castform_snowy});
        castform_snowy.setForms(new Pokemon[] {PokemonList.castform, PokemonList.castform_sunny, PokemonList.castform_rainy, PokemonList.castform_snowy});
        deoxys.setForms(new Pokemon[] {PokemonList.deoxys, PokemonList.deoxys_attack, PokemonList.deoxys_defense, PokemonList.deoxys_speed});
        deoxys_attack.setForms(new Pokemon[] {PokemonList.deoxys, PokemonList.deoxys_attack, PokemonList.deoxys_defense, PokemonList.deoxys_speed});
        deoxys_defense.setForms(new Pokemon[] {PokemonList.deoxys, PokemonList.deoxys_attack, PokemonList.deoxys_defense, PokemonList.deoxys_speed});
        deoxys_speed.setForms(new Pokemon[] {PokemonList.deoxys, PokemonList.deoxys_attack, PokemonList.deoxys_defense, PokemonList.deoxys_speed});

        dialga.setForms(new Pokemon[] {PokemonList.dialga, PokemonList.dialga_origin});
        dialga_origin.setForms(new Pokemon[] {PokemonList.dialga, PokemonList.dialga_origin});
        palkia.setForms(new Pokemon[] {PokemonList.palkia, PokemonList.palkia_origin});
        palkia_origin.setForms(new Pokemon[] {PokemonList.palkia, PokemonList.palkia_origin});
        giratina.setForms(new Pokemon[] {PokemonList.giratina, PokemonList.giratina_origin});
        giratina_origin.setForms(new Pokemon[] {PokemonList.giratina, PokemonList.giratina_origin});
        shaymin.setForms(new Pokemon[] {PokemonList.shaymin, PokemonList.shaymin_sky});
        shaymin_sky.setForms(new Pokemon[] {PokemonList.shaymin, PokemonList.shaymin_sky});
        arceus.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_fighting.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_flying.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_poison.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_ground.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_rock.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_bug.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_ghost.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_steel.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_fire.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_water.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_grass.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_electric.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_psychic.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_ice.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_dragon.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_dark.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});
        arceus_fairy.setForms(new Pokemon[] {PokemonList.arceus, PokemonList.arceus_fighting, PokemonList.arceus_flying, PokemonList.arceus_poison, PokemonList.arceus_ground, PokemonList.arceus_rock, PokemonList.arceus_bug, PokemonList.arceus_ghost, PokemonList.arceus_steel, PokemonList.arceus_fire, PokemonList.arceus_water, PokemonList.arceus_grass, PokemonList.arceus_electric, PokemonList.arceus_psychic, PokemonList.arceus_ice, PokemonList.arceus_dragon, PokemonList.arceus_dark, PokemonList.arceus_fairy});

        tornadus.setForms(new Pokemon[] {tornadus, tornadus_therian});
        tornadus_therian.setForms(new Pokemon[] {tornadus, tornadus_therian});
        thundurus.setForms(new Pokemon[] {thundurus, thundurus_therian});
        thundurus_therian.setForms(new Pokemon[] {thundurus, thundurus_therian});
        landorus.setForms(new Pokemon[] {landorus, landorus_therian});
        landorus_therian.setForms(new Pokemon[] {landorus, landorus_therian});
        kyurem.setForms(new Pokemon[] {kyurem, kyurem_white, kyurem_black});
        kyurem_white.setForms(new Pokemon[] {kyurem, kyurem_white, kyurem_black});
        kyurem_black.setForms(new Pokemon[] {kyurem, kyurem_white, kyurem_black});
        keldeo.setForms(new Pokemon[] {keldeo, keldeo_resolute});
        keldeo_resolute.setForms(new Pokemon[] {keldeo, keldeo_resolute});
        meloetta.setForms(new Pokemon[] {meloetta, meloetta_pirouette});
        meloetta_pirouette.setForms(new Pokemon[] {meloetta, meloetta_pirouette});
        genesect.setForms(new Pokemon[] {genesect, genesect_douse, genesect_shock, genesect_burn, genesect_chill});
        genesect_douse.setForms(new Pokemon[] {genesect, genesect_douse, genesect_shock, genesect_burn, genesect_chill});
        genesect_shock.setForms(new Pokemon[] {genesect, genesect_douse, genesect_shock, genesect_burn, genesect_chill});
        genesect_burn.setForms(new Pokemon[] {genesect, genesect_douse, genesect_shock, genesect_burn, genesect_chill});
        genesect_chill.setForms(new Pokemon[] {genesect, genesect_douse, genesect_shock, genesect_burn, genesect_chill});

        zygarde.setForms(new Pokemon[] {PokemonList.zygarde, PokemonList.zygarde_10, PokemonList.zygarde_complete});
        zygarde_10.setForms(new Pokemon[] {PokemonList.zygarde, PokemonList.zygarde_10, PokemonList.zygarde_complete});
        zygarde_complete.setForms(new Pokemon[] {PokemonList.zygarde, PokemonList.zygarde_10, PokemonList.zygarde_complete});
        hoopa.setForms(new Pokemon[] {PokemonList.hoopa, PokemonList.hoopa_unbound});
        hoopa_unbound.setForms(new Pokemon[] {PokemonList.hoopa, PokemonList.hoopa_unbound});

        silvally.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_fighting.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_flying.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_poison.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_ground.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_rock.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_bug.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_ghost.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_steel.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_fire.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_water.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_grass.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_electric.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_psychic.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_ice.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_dragon.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_dark.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        silvally_fairy.setForms(new Pokemon[] {PokemonList.silvally, PokemonList.silvally_fighting, PokemonList.silvally_flying, PokemonList.silvally_poison, PokemonList.silvally_ground, PokemonList.silvally_rock, PokemonList.silvally_bug, PokemonList.silvally_ghost, PokemonList.silvally_steel, PokemonList.silvally_fire, PokemonList.silvally_water, PokemonList.silvally_grass, PokemonList.silvally_electric, PokemonList.silvally_psychic, PokemonList.silvally_ice, PokemonList.silvally_dragon, PokemonList.silvally_dark, PokemonList.silvally_fairy});
        necrozma.setForms(new Pokemon[] {PokemonList.necrozma, PokemonList.necrozma_dusk_mane, PokemonList.necrozma_dawn_wings, PokemonList.necrozma_ultra});
        necrozma_dusk_mane.setForms(new Pokemon[] {PokemonList.necrozma, PokemonList.necrozma_dusk_mane, PokemonList.necrozma_dawn_wings, PokemonList.necrozma_ultra});
        necrozma_dawn_wings.setForms(new Pokemon[] {PokemonList.necrozma, PokemonList.necrozma_dusk_mane, PokemonList.necrozma_dawn_wings, PokemonList.necrozma_ultra});
        necrozma_ultra.setForms(new Pokemon[] {PokemonList.necrozma, PokemonList.necrozma_dusk_mane, PokemonList.necrozma_dawn_wings, PokemonList.necrozma_ultra});

        indeedee.setForms(new Pokemon[] {PokemonList.indeedee, PokemonList.indeedee_female});
        indeedee_female.setForms(new Pokemon[] {PokemonList.indeedee, PokemonList.indeedee_female});

        zacian.setForms(new Pokemon[] {PokemonList.zacian, PokemonList.zacian_crowned});
        zacian_crowned.setForms(new Pokemon[] {PokemonList.zacian, PokemonList.zacian_crowned});
        zamazenta.setForms(new Pokemon[] {PokemonList.zamazenta, PokemonList.zamazenta_crowned});
        zamazenta_crowned.setForms(new Pokemon[] {PokemonList.zamazenta, PokemonList.zamazenta_crowned});
        eternatus.setForms(new Pokemon[] {PokemonList.eternatus, PokemonList.eternatus_eternamax});
        eternatus_eternamax.setForms(new Pokemon[] {PokemonList.eternatus, PokemonList.eternatus_eternamax});
        urshifu.setForms(new Pokemon[] {PokemonList.urshifu, PokemonList.urshifu_rapid});
        urshifu_rapid.setForms(new Pokemon[] {PokemonList.urshifu, PokemonList.urshifu_rapid});
        calyrex.setForms(new Pokemon[] {PokemonList.calyrex, PokemonList.calyrex_ice, PokemonList.calyrex_shadow});
        calyrex_ice.setForms(new Pokemon[] {PokemonList.calyrex, PokemonList.calyrex_ice, PokemonList.calyrex_shadow});
        calyrex_shadow.setForms(new Pokemon[] {PokemonList.calyrex, PokemonList.calyrex_ice, PokemonList.calyrex_shadow});
        enamorus.setForms(new Pokemon[] {PokemonList.enamorus, PokemonList.enamorus_therian});
        enamorus_therian.setForms(new Pokemon[] {PokemonList.enamorus, PokemonList.enamorus_therian});

        palafin.setForms(new Pokemon[] {PokemonList.palafin, PokemonList.palafin_hero});
        palafin_hero.setForms(new Pokemon[] {PokemonList.palafin, PokemonList.palafin_hero});
        ogerpon.setForms(new Pokemon[] {PokemonList.ogerpon, PokemonList.ogerpon_wellspring, PokemonList.ogerpon_hearthflame, PokemonList.ogerpon_cornerstone});
        ogerpon_wellspring.setForms(new Pokemon[] {PokemonList.ogerpon, PokemonList.ogerpon_wellspring, PokemonList.ogerpon_hearthflame, PokemonList.ogerpon_cornerstone});
        ogerpon_hearthflame.setForms(new Pokemon[] {PokemonList.ogerpon, PokemonList.ogerpon_wellspring, PokemonList.ogerpon_hearthflame, PokemonList.ogerpon_cornerstone});
        ogerpon_cornerstone.setForms(new Pokemon[] {PokemonList.ogerpon, PokemonList.ogerpon_wellspring, PokemonList.ogerpon_hearthflame, PokemonList.ogerpon_cornerstone});
        terapagos.setForms(new Pokemon[] {PokemonList.terapagos, PokemonList.terapagos_terastal, PokemonList.terapagos_stellar});
        terapagos_terastal.setForms(new Pokemon[] {PokemonList.terapagos, PokemonList.terapagos_terastal, PokemonList.terapagos_stellar});
        terapagos_stellar.setForms(new Pokemon[] {PokemonList.terapagos, PokemonList.terapagos_terastal, PokemonList.terapagos_stellar});


        // Formas Regionais

        articuno.setForms(new Pokemon[] {PokemonList.articuno, PokemonList.articuno_galar});
        articuno_galar.setForms(new Pokemon[] {PokemonList.articuno, PokemonList.articuno_galar});
        zapdos.setForms(new Pokemon[] {PokemonList.zapdos, PokemonList.zapdos_galar});
        zapdos_galar.setForms(new Pokemon[] {PokemonList.zapdos, PokemonList.zapdos_galar});
        moltres.setForms(new Pokemon[] {PokemonList.moltres, PokemonList.moltres_galar});
        moltres_galar.setForms(new Pokemon[] {PokemonList.moltres, PokemonList.moltres_galar});

        typhlosion.setForms(new Pokemon[] {PokemonList.typhlosion, PokemonList.typhlosion_hisui});
        typhlosion_hisui.setForms(new Pokemon[] {PokemonList.typhlosion, PokemonList.typhlosion_hisui});

        uxie.setForms(new Pokemon[] {PokemonList.uxie, PokemonList.uxie_bralia});
        uxie_bralia.setForms(new Pokemon[] {PokemonList.uxie, PokemonList.uxie_bralia});
        mesprit.setForms(new Pokemon[] {PokemonList.mesprit, PokemonList.mesprit_bralia});
        mesprit_bralia.setForms(new Pokemon[] {PokemonList.mesprit, PokemonList.mesprit_bralia});
        azelf.setForms(new Pokemon[] {PokemonList.azelf, PokemonList.azelf_bralia});
        azelf_bralia.setForms(new Pokemon[] {PokemonList.azelf, PokemonList.azelf_bralia});

        samurott.setForms(new Pokemon[] {PokemonList.samurott, PokemonList.samurott_hisui});
        samurott_hisui.setForms(new Pokemon[] {PokemonList.samurott, PokemonList.samurott_hisui});
        zorua.setForms(new Pokemon[] {PokemonList.zorua, PokemonList.zorua_hisui});
        zorua_hisui.setForms(new Pokemon[] {PokemonList.zorua, PokemonList.zorua_hisui});
        zoroark.setForms(new Pokemon[] {PokemonList.zoroark, PokemonList.zoroark_hisui});
        zoroark_hisui.setForms(new Pokemon[] {PokemonList.zoroark, PokemonList.zoroark_hisui});

        decidueye.setForms(new Pokemon[] {PokemonList.decidueye, PokemonList.decidueye_hisui});
        decidueye_hisui.setForms(new Pokemon[] {PokemonList.decidueye, PokemonList.decidueye_hisui});


        // Mega Evoluções/Reversões Primitivas

        venusaur.setForms(new Pokemon[] {PokemonList.venusaur, PokemonList.venusaur_mega});
        venusaur_mega.setForms(new Pokemon[] {PokemonList.venusaur, PokemonList.venusaur_mega});
        charizard.setForms(new Pokemon[] {PokemonList.charizard, PokemonList.charizard_mega_x, PokemonList.charizard_mega_y});
        charizard_mega_x.setForms(new Pokemon[] {PokemonList.charizard, PokemonList.charizard_mega_x, PokemonList.charizard_mega_y});
        charizard_mega_y.setForms(new Pokemon[] {PokemonList.charizard, PokemonList.charizard_mega_x, PokemonList.charizard_mega_y});
        blastoise.setForms(new Pokemon[] {PokemonList.blastoise, PokemonList.blastoise_mega});
        blastoise_mega.setForms(new Pokemon[] {PokemonList.blastoise, PokemonList.blastoise_mega});
        mewtwo.setForms(new Pokemon[] {PokemonList.mewtwo, PokemonList.mewtwo_mega_x, PokemonList.mewtwo_mega_y});
        mewtwo_mega_x.setForms(new Pokemon[] {PokemonList.mewtwo, PokemonList.mewtwo_mega_x, PokemonList.mewtwo_mega_y});
        mewtwo_mega_y.setForms(new Pokemon[] {PokemonList.mewtwo, PokemonList.mewtwo_mega_x, PokemonList.mewtwo_mega_y});

        sceptile.setForms(new Pokemon[] {PokemonList.sceptile, PokemonList.sceptile_mega});
        sceptile_mega.setForms(new Pokemon[] {PokemonList.sceptile, PokemonList.sceptile_mega});
        blaziken.setForms(new Pokemon[] {PokemonList.blaziken, PokemonList.blaziken_mega});
        blaziken_mega.setForms(new Pokemon[] {PokemonList.blaziken, PokemonList.blaziken_mega});
        swampert.setForms(new Pokemon[] {PokemonList.swampert, PokemonList.swampert_mega});
        swampert_mega.setForms(new Pokemon[] {PokemonList.swampert, PokemonList.swampert_mega});
        latias.setForms(new Pokemon[] {PokemonList.latias, PokemonList.latias_mega});
        latias_mega.setForms(new Pokemon[] {PokemonList.latias, PokemonList.latias_mega});
        latios.setForms(new Pokemon[] {PokemonList.latios, PokemonList.latios_mega});
        latios_mega.setForms(new Pokemon[] {PokemonList.latios, PokemonList.latios_mega});
        kyogre.setForms(new Pokemon[] {PokemonList.kyogre, PokemonList.kyogre_primal});
        kyogre_primal.setForms(new Pokemon[] {PokemonList.kyogre, PokemonList.kyogre_primal});
        groudon.setForms(new Pokemon[] {PokemonList.groudon, PokemonList.groudon_primal});
        groudon_primal.setForms(new Pokemon[] {PokemonList.groudon, PokemonList.groudon_primal});
        rayquaza.setForms(new Pokemon[] {PokemonList.rayquaza, PokemonList.rayquaza_mega});
        rayquaza_mega.setForms(new Pokemon[] {PokemonList.rayquaza, PokemonList.rayquaza_mega});

        lucario.setForms(new Pokemon[] {PokemonList.lucario, PokemonList.lucario_mega});
        lucario_mega.setForms(new Pokemon[] {PokemonList.lucario, PokemonList.lucario_mega});

        diancie.setForms(new Pokemon[] {PokemonList.diancie, PokemonList.diancie_mega});
        diancie_mega.setForms(new Pokemon[] {PokemonList.diancie, PokemonList.diancie_mega});


        // Itens necessários para formas

        venusaur_mega.setItemsNeededForForm(new Item[] {ItemList.venusaurite});
        charizard_mega_x.setItemsNeededForForm(new Item[] {ItemList.charizardite_x});
        charizard_mega_y.setItemsNeededForForm(new Item[] {ItemList.charizardite_y});
        blastoise_mega.setItemsNeededForForm(new Item[] {ItemList.blastoisinite});
        mewtwo_mega_x.setItemsNeededForForm(new Item[] {ItemList.mewtwonite_x});
        mewtwo_mega_y.setItemsNeededForForm(new Item[] {ItemList.mewtwonite_y});

        sceptile_mega.setItemsNeededForForm(new Item[] {ItemList.sceptilite});
        blaziken_mega.setItemsNeededForForm(new Item[] {ItemList.blazikenite});
        swampert_mega.setItemsNeededForForm(new Item[] {ItemList.swampertite});
        latias_mega.setItemsNeededForForm(new Item[] {ItemList.latiasite});
        latios_mega.setItemsNeededForForm(new Item[] {ItemList.latiosite});
        kyogre_primal.setItemsNeededForForm(new Item[] {ItemList.blue_orb});
        groudon_primal.setItemsNeededForForm(new Item[] {ItemList.red_orb});

        lucario_mega.setItemsNeededForForm(new Item[] {ItemList.lucarionite});
        dialga_origin.setItemsNeededForForm(new Item[] {ItemList.adamant_crystal});
        palkia_origin.setItemsNeededForForm(new Item[] {ItemList.lustrous_globe});
        giratina_origin.setItemsNeededForForm(new Item[] {ItemList.griseous_core});
        arceus_fighting.setItemsNeededForForm(new Item[] {ItemList.fist_plate, ItemList.fightinium_z});
        arceus_flying.setItemsNeededForForm(new Item[] {ItemList.sky_plate, ItemList.flyinium_z});
        arceus_poison.setItemsNeededForForm(new Item[] {ItemList.toxic_plate, ItemList.poisonium_z});
        arceus_ground.setItemsNeededForForm(new Item[] {ItemList.earth_plate, ItemList.groundium_z});
        arceus_rock.setItemsNeededForForm(new Item[] {ItemList.stone_plate, ItemList.rockium_z});
        arceus_bug.setItemsNeededForForm(new Item[] {ItemList.insect_plate, ItemList.buginium_z});
        arceus_ghost.setItemsNeededForForm(new Item[] {ItemList.spooky_plate, ItemList.ghostium_z});
        arceus_steel.setItemsNeededForForm(new Item[] {ItemList.iron_plate, ItemList.steelium_z});
        arceus_fire.setItemsNeededForForm(new Item[] {ItemList.flame_plate, ItemList.firium_z});
        arceus_water.setItemsNeededForForm(new Item[] {ItemList.splash_plate, ItemList.waterium_z});
        arceus_grass.setItemsNeededForForm(new Item[] {ItemList.meadow_plate, ItemList.grassium_z});
        arceus_electric.setItemsNeededForForm(new Item[] {ItemList.zap_plate, ItemList.electrium_z});
        arceus_psychic.setItemsNeededForForm(new Item[] {ItemList.mind_plate, ItemList.psychium_z});
        arceus_ice.setItemsNeededForForm(new Item[] {ItemList.icicle_plate, ItemList.icium_z});
        arceus_dragon.setItemsNeededForForm(new Item[] {ItemList.draco_plate, ItemList.dragonium_z});
        arceus_dark.setItemsNeededForForm(new Item[] {ItemList.dread_plate, ItemList.darkinium_z});
        arceus_fairy.setItemsNeededForForm(new Item[] {ItemList.pixie_plate, ItemList.fairium_z});

        genesect_douse.setItemsNeededForForm(new Item[] {ItemList.douse_drive});
        genesect_shock.setItemsNeededForForm(new Item[] {ItemList.shock_drive});
        genesect_burn.setItemsNeededForForm(new Item[] {ItemList.burn_drive});
        genesect_chill.setItemsNeededForForm(new Item[] {ItemList.chill_drive});

        diancie_mega.setItemsNeededForForm(new Item[] {ItemList.diancite});

        silvally_fighting.setItemsNeededForForm(new Item[] {ItemList.fighting_memory});
        silvally_flying.setItemsNeededForForm(new Item[] {ItemList.flying_memory});
        silvally_poison.setItemsNeededForForm(new Item[] {ItemList.poison_memory});
        silvally_ground.setItemsNeededForForm(new Item[] {ItemList.ground_memory});
        silvally_rock.setItemsNeededForForm(new Item[] {ItemList.rock_memory});
        silvally_bug.setItemsNeededForForm(new Item[] {ItemList.bug_memory});
        silvally_ghost.setItemsNeededForForm(new Item[] {ItemList.ghost_memory});
        silvally_steel.setItemsNeededForForm(new Item[] {ItemList.steel_memory});
        silvally_fire.setItemsNeededForForm(new Item[] {ItemList.fire_memory});
        silvally_water.setItemsNeededForForm(new Item[] {ItemList.water_memory});
        silvally_grass.setItemsNeededForForm(new Item[] {ItemList.grass_memory});
        silvally_electric.setItemsNeededForForm(new Item[] {ItemList.electric_memory});
        silvally_psychic.setItemsNeededForForm(new Item[] {ItemList.psychic_memory});
        silvally_ice.setItemsNeededForForm(new Item[] {ItemList.ice_memory});
        silvally_dragon.setItemsNeededForForm(new Item[] {ItemList.dragon_memory});
        silvally_dark.setItemsNeededForForm(new Item[] {ItemList.dark_memory});
        silvally_fairy.setItemsNeededForForm(new Item[] {ItemList.fairy_memory});
        necrozma_ultra.setItemsNeededForForm(new Item[] {ItemList.ultranecrozium_z});

        zacian_crowned.setItemsNeededForForm(new Item[] {ItemList.rusted_sword});
        zamazenta_crowned.setItemsNeededForForm(new Item[] {ItemList.rusted_shield});
        eternatus_eternamax.setItemsNeededForForm(new Item[] {ItemList.eternal_wishing_star});

        ogerpon_wellspring.setItemsNeededForForm(new Item[] {ItemList.wellspring_mask});
        ogerpon_hearthflame.setItemsNeededForForm(new Item[] {ItemList.hearthflame_mask});
        ogerpon_cornerstone.setItemsNeededForForm(new Item[] {ItemList.cornerstone_mask});
        terapagos_stellar.setItemsNeededForForm(new Item[] {ItemList.stellar_orb});


        // Movimentos necessários para formas

        rayquaza_mega.setMoveNeededForForm(MoveList.dragon_ascent);
        keldeo_resolute.setMoveNeededForForm(MoveList.secret_sword);
    }
}