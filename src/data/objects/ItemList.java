package data.objects;

import data.activationConditions.ItemActivation;
import data.classes.Item;
import data.classes.Pokemon;
import data.properties.items.ItemType;
import main.Battle;
import main.Damage;

public class ItemList {
    public static final Item none = new Item(
        "None",
        false,
        null,
        new ItemActivation[0],
        0,
        null
    );

    public static final Item focus_sash = new Item(
        "Focus Sash",
        true,
        (thisItem, _, user, _, _, damage, activation) -> {
            if (activation == ItemActivation.DeductHP) {
                if (damage.amount >= user.getHP() &&
                    user.getCurrentHP() == user.getHP()) {
                    thisItem.setConsumed(true);
                    return true;
                }
                return false;
            }
            if (activation == ItemActivation.Consumed) {
                System.out.println(user.getName(true, true) + " hung on using its Focus Sash!");
                thisItem.consume(true, false);
            }
            return null;
        },
        new ItemActivation[] {
            ItemActivation.DeductHP,
            ItemActivation.Consumed
        },
        10,
        null
    );

    public static final Item oran_berry = new Item(
        "Oran Berry",
        true,
        ItemType.Berry,
        (thisItem, _, user, _, _, _, activation) -> {
            if (activation == ItemActivation.ForceUse ||
                user.getCurrentHP() < user.getHP()/2.0) {
                System.out.println("\n" + user.getName(true, true) + " restored its health using its Oran Berry!");
                Damage.heal(user, null, 10, true, false);
                thisItem.setConsumed(true);
                thisItem.consume(true, false);
            }
            return null;
        },
        new ItemActivation[] {
            ItemActivation.Pinch,
            ItemActivation.ForceUse,
            ItemActivation.Given
        },
        10,
        null
    );

    public static final Item flame_orb = new Item(
        "Flame Orb",
        false,
        (thisItem, _, user, _, _, _, _) -> {
            if (!Battle.faintCheck(user, false) &&
                user.getNonVolatileStatus().compare(StatusConditionList.none)) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                StatusConditionList.burn.apply(user, thisItem, true);
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        new ItemActivation[] {
            ItemActivation.EndOfTurn
        },
        30,
        (_, _, user, _, move, _, _) -> {
            StatusConditionList.burn.apply(user, move, true);
            return null;
        }
    );

    public static final Item toxic_orb = new Item(
        "Toxic Orb",
        false,
        (thisItem, _, user, _, _, _, _) -> {
            if (!Battle.faintCheck(user, false) &&
                user.getNonVolatileStatus().compare(StatusConditionList.none)) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                StatusConditionList.bad_poison.apply(user, thisItem, 1, true);
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        new ItemActivation[] {
            ItemActivation.EndOfTurn
        },
        30,
        (_, _, user, _, move, _, _) -> {
            StatusConditionList.bad_poison.apply(user, move, 1, true);
            return null;
        }
    );

    public static final Item venusaurite = new Item(
        "Venusaurite",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.venusaur,
            PokemonList.venusaur_mega
        },
        PokemonList.venusaur_mega,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item charizardite_x = new Item(
        "Charizardite X",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.charizard,
            PokemonList.charizard_mega_x
        },
        PokemonList.charizard_mega_x,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item charizardite_y = new Item(
        "Charizardite Y",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.charizard,
            PokemonList.charizard_mega_y
        },
        PokemonList.charizard_mega_y,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item blastoisinite = new Item(
        "Blastoisinite",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.blastoise,
            PokemonList.blastoise_mega
        },
        PokemonList.blastoise_mega,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item mewtwonite_x = new Item(
        "Mewtwonite X",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.mewtwo,
            PokemonList.mewtwo_mega_x
        },
        PokemonList.mewtwo_mega_x,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item mewtwonite_y = new Item(
        "Mewtwonite Y",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.mewtwo,
            PokemonList.mewtwo_mega_y
        },
        PokemonList.mewtwo_mega_y,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item tyranitarite = new Item(
        "Tyranitarite",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.tyranitar,
            PokemonList.tyranitar_mega
        },
        PokemonList.tyranitar_mega,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item sceptilite = new Item(
        "Sceptilite",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.sceptile,
            PokemonList.sceptile_mega
        },
        PokemonList.sceptile_mega,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item blazikenite = new Item(
        "Blazikenite",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.blaziken,
            PokemonList.blaziken_mega
        },
        PokemonList.blaziken_mega,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item swampertite = new Item(
        "Swampertite",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.swampert,
            PokemonList.swampert_mega
        },
        PokemonList.swampert_mega,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item salamencite = new Item(
        "Salamencite",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.salamence,
            PokemonList.salamence_mega
        },
        PokemonList.salamence_mega,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item latiasite = new Item(
        "Latiasite",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.latias,
            PokemonList.latias_mega
        },
        PokemonList.latias_mega,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item latiosite = new Item(
        "Latiosite",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.latios,
            PokemonList.latios_mega
        },
        PokemonList.latios_mega,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item blue_orb = new Item(
        "Blue Orb",
        false,
        ItemType.PrimalOrb,
        new Pokemon[] {
            PokemonList.kyogre,
            PokemonList.kyogre_primal
        },
        PokemonList.kyogre_primal,
        (thisItem, holder, _, _, _, _, activation) -> {
            if (activation == ItemActivation.Entry) {
                if (holder.compare(PokemonList.kyogre, true) && holder.compareWithForm(PokemonList.kyogre)) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    System.out.println(holder.getName(true, true) + "'s Primal Reversion! It reverted to its primal form!");
                    holder.changeForm(thisItem.getTransformsInto().getForm());
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        },
        new ItemActivation[] {
            ItemActivation.Entry
        },
        true
    );

    public static final Item red_orb = new Item(
        "Red Orb",
        false,
        ItemType.PrimalOrb,
        new Pokemon[] {
            PokemonList.groudon,
            PokemonList.groudon_primal
        },
        PokemonList.groudon_primal,
        (thisItem, holder, _, _, _, _, activation) -> {
            if (activation == ItemActivation.Entry) {
                if (holder.compare(PokemonList.groudon, true) && holder.compareWithForm(PokemonList.groudon)) {
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    System.out.println(holder.getName(true, true) + "'s Primal Reversion! It reverted to its primal form!");
                    holder.changeForm(thisItem.getTransformsInto().getForm());
                    System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                }
            }
            return null;
        },
        new ItemActivation[] {
            ItemActivation.Entry
        },
        true
    );

    public static final Item lucarionite = new Item(
        "Lucarionite",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.lucario,
            PokemonList.lucario_mega
        },
        PokemonList.lucario_mega,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item adamant_crystal = new Item(
        "Adamant Crystal",
        false,
        ItemType.MythOrb,
        new Pokemon[] {
            PokemonList.dialga,
            PokemonList.dialga_origin
        },
        PokemonList.dialga_origin,
        (thisItem, _, _, _, move, _, _) -> {
            if (thisItem.heldByValidUser(true) &&
                (move.getType(false).compare(TypeList.steel) || move.getType(false).compare(TypeList.dragon))) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        true
    );

    public static final Item lustrous_globe = new Item(
        "Lustrous Globe",
        false,
        ItemType.MythOrb,
        new Pokemon[] {
            PokemonList.palkia,
            PokemonList.palkia_origin
        },
        PokemonList.palkia_origin,
        (thisItem, _, _, _, move, _, _) -> {
            if (thisItem.heldByValidUser(true) &&
                (move.getType(false).compare(TypeList.water) || move.getType(false).compare(TypeList.dragon))) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        true
    );

    public static final Item griseous_core = new Item(
        "Griseous Core",
        false,
        ItemType.MythOrb,
        new Pokemon[] {
            PokemonList.giratina,
            PokemonList.giratina_origin
        },
        PokemonList.giratina_origin,
        (thisItem, _, _, _, move, _, _) -> {
            if (thisItem.heldByValidUser(true) &&
                (move.getType(false).compare(TypeList.ghost) || move.getType(false).compare(TypeList.dragon))) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        true
    );

    public static final Item fist_plate = new Item(
        "Fist Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_fighting
        },
        PokemonList.arceus_fighting,
        TypeList.fighting,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.fighting)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item sky_plate = new Item(
        "Sky Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_flying
        },
        PokemonList.arceus_flying,
        TypeList.flying,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.flying)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item toxic_plate = new Item(
        "Toxic Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_poison
        },
        PokemonList.arceus_poison,
        TypeList.poison,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.poison)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item earth_plate = new Item(
        "Earth Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_ground
        },
        PokemonList.arceus_ground,
        TypeList.ground,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.ground)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item stone_plate = new Item(
        "Stone Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_rock
        },
        PokemonList.arceus_rock,
        TypeList.rock,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.rock)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item insect_plate = new Item(
        "Insect Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_bug
        },
        PokemonList.arceus_bug,
        TypeList.bug,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.bug)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item spooky_plate = new Item(
        "Spooky Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_ghost
        },
        PokemonList.arceus_ghost,
        TypeList.ghost,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.ghost)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item iron_plate = new Item(
        "Iron Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_steel
        },
        PokemonList.arceus_steel,
        TypeList.steel,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.steel)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item flame_plate = new Item(
        "Flame Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_fire
        },
        PokemonList.arceus_fire,
        TypeList.fire,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.fire)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item splash_plate = new Item(
        "Splash Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_water
        },
        PokemonList.arceus_water,
        TypeList.water,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.water)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item meadow_plate = new Item(
        "Meadow Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_grass
        },
        PokemonList.arceus_grass,
        TypeList.grass,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.grass)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item zap_plate = new Item(
        "Zap Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_electric
        },
        PokemonList.arceus_electric,
        TypeList.electric,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.electric)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item mind_plate = new Item(
        "Mind Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_psychic
        },
        PokemonList.arceus_psychic,
        TypeList.psychic,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.psychic)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item icicle_plate = new Item(
        "Icicle Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_ice
        },
        PokemonList.arceus_ice,
        TypeList.ice,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.ice)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item draco_plate = new Item(
        "Draco Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_dragon
        },
        PokemonList.arceus_dragon,
        TypeList.dragon,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.dragon)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item dread_plate = new Item(
        "Dread Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_dark
        },
        PokemonList.arceus_dark,
        TypeList.dark,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.dark)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item pixie_plate = new Item(
        "Pixie Plate",
        false,
        ItemType.Plate,
        new Pokemon[] {
            PokemonList.arceus,
            PokemonList.arceus_fairy
        },
        PokemonList.arceus_fairy,
        TypeList.fairy,
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false).compare(TypeList.fairy)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        90,
        null,
        true
    );

    public static final Item douse_drive = new Item(
        "Douse Drive",
        false,
        ItemType.Drive,
        new Pokemon[] {
            PokemonList.genesect,
            PokemonList.genesect_douse
        },
        PokemonList.genesect_douse,
        TypeList.water,
        null,
        new ItemActivation[0],
        70,
        null,
        true
    );

    public static final Item shock_drive = new Item(
        "Shock Drive",
        false,
        ItemType.Drive,
        new Pokemon[] {
            PokemonList.genesect,
            PokemonList.genesect_shock
        },
        PokemonList.genesect_shock,
        TypeList.electric,
        null,
        new ItemActivation[0],
        70,
        null,
        true
    );

    public static final Item burn_drive = new Item(
        "Burn Drive",
        false,
        ItemType.Drive,
        new Pokemon[] {
            PokemonList.genesect,
            PokemonList.genesect_burn
        },
        PokemonList.genesect_burn,
        TypeList.fire,
        null,
        new ItemActivation[0],
        70,
        null,
        true
    );

    public static final Item chill_drive = new Item(
        "Chill Drive",
        false,
        ItemType.Drive,
        new Pokemon[] {
            PokemonList.genesect,
            PokemonList.genesect_chill
        },
        PokemonList.genesect_chill,
        TypeList.ice,
        null,
        new ItemActivation[0],
        70,
        null,
        true
    );

    public static final Item diancite = new Item(
        "Diancite",
        false,
        ItemType.MegaStone,
        new Pokemon[] {
            PokemonList.diancie,
            PokemonList.diancie_mega
        },
        PokemonList.diancie_mega,
        null,
        new ItemActivation[0],
        80,
        null,
        true
    );

    public static final Item normalium_z = new Item(
        "Normalium Z",
        false,
        ItemType.ZCrystal,
        MoveList.breakneck_blitz,
        null,
        new ItemActivation[0]
    );

    public static final Item fightinium_z = new Item(
        "Fightinium Z",
        false,
        ItemType.ZCrystal,
        MoveList.all_out_pummeling,
        null,
        new ItemActivation[0]
    );

    public static final Item flyinium_z = new Item(
        "Flyinium Z",
        false,
        ItemType.ZCrystal,
        MoveList.supersonic_skystrike,
        null,
        new ItemActivation[0]
    );

    public static final Item poisonium_z = new Item(
        "Poisonium Z",
        false,
        ItemType.ZCrystal,
        MoveList.acid_downpour,
        null,
        new ItemActivation[0]
    );

    public static final Item groundium_z = new Item(
        "Groundium Z",
        false,
        ItemType.ZCrystal,
        MoveList.tectonic_rage,
        null,
        new ItemActivation[0]
    );

    public static final Item rockium_z = new Item(
        "Rockium Z",
        false,
        ItemType.ZCrystal,
        MoveList.continental_crush,
        null,
        new ItemActivation[0]
    );

    public static final Item buginium_z = new Item(
        "Buginium Z",
        false,
        ItemType.ZCrystal,
        MoveList.savage_spin_out,
        null,
        new ItemActivation[0]
    );

    public static final Item ghostium_z = new Item(
        "Ghostium Z",
        false,
        ItemType.ZCrystal,
        MoveList.never_ending_nightmare,
        null,
        new ItemActivation[0]
    );

    public static final Item steelium_z = new Item(
        "Steelium Z",
        false,
        ItemType.ZCrystal,
        MoveList.corkscrew_crash,
        null,
        new ItemActivation[0]
    );

    public static final Item firium_z = new Item(
        "Firium Z",
        false,
        ItemType.ZCrystal,
        MoveList.inferno_overdrive,
        null,
        new ItemActivation[0]
    );

    public static final Item waterium_z = new Item(
        "Waterium Z",
        false,
        ItemType.ZCrystal,
        MoveList.hydro_vortex,
        null,
        new ItemActivation[0]
    );

    public static final Item grassium_z = new Item(
        "Grassium Z",
        false,
        ItemType.ZCrystal,
        MoveList.bloom_doom,
        null,
        new ItemActivation[0]
    );

    public static final Item electrium_z = new Item(
        "Electrium Z",
        false,
        ItemType.ZCrystal,
        MoveList.gigavolt_havoc,
        null,
        new ItemActivation[0]
    );

    public static final Item psychium_z = new Item(
        "Psychium Z",
        false,
        ItemType.ZCrystal,
        MoveList.shattered_psyche,
        null,
        new ItemActivation[0]
    );

    public static final Item icium_z = new Item(
        "Icium Z",
        false,
        ItemType.ZCrystal,
        MoveList.subzero_slammer,
        null,
        new ItemActivation[0]
    );

    public static final Item dragonium_z = new Item(
        "Dragonium Z",
        false,
        ItemType.ZCrystal,
        MoveList.devastating_drake,
        null,
        new ItemActivation[0]
    );

    public static final Item darkinium_z = new Item(
        "Darkinium Z",
        false,
        ItemType.ZCrystal,
        MoveList.black_hole_eclipse,
        null,
        new ItemActivation[0]
    );

    public static final Item fairium_z = new Item(
        "Fairium Z",
        false,
        ItemType.ZCrystal,
        MoveList.twinkle_tackle,
        null,
        new ItemActivation[0]
    );

    public static final Item mewnium_z = new Item(
        "Mewnium Z",
        false,
        ItemType.ZCrystal,
        new Pokemon[] {
            PokemonList.mew
        },
        MoveList.genesis_supernova,
        MoveList.psychic,
        null,
        new ItemActivation[0]
    );

    public static final Item decidium_z = new Item(
        "Decidium Z",
        false,
        ItemType.ZCrystal,
        new Pokemon[] {
            PokemonList.decidueye
        },
        MoveList.sinister_arrow_raid,
        MoveList.spirit_shackle,
        null,
        new ItemActivation[0]
    );

    public static final Item incinium_z = new Item(
        "Incinium Z",
        false,
        ItemType.ZCrystal,
        new Pokemon[] {
            PokemonList.incineroar
        },
        MoveList.malicious_moonsault,
        MoveList.darkest_lariat,
        null,
        new ItemActivation[0]
    );

    public static final Item primarium_z = new Item(
        "Primarium Z",
        false,
        ItemType.ZCrystal,
        new Pokemon[] {
            PokemonList.primarina
        },
        MoveList.oceanic_operetta,
        MoveList.sparkling_aria,
        null,
        new ItemActivation[0]
    );

    public static final Item tapunium_z = new Item(
        "Tapunium Z",
        false,
        ItemType.ZCrystal,
        new Pokemon[] {
            PokemonList.tapu_koko,
            PokemonList.tapu_lele,
            PokemonList.tapu_bulu,
            PokemonList.tapu_fini
        },
        MoveList.guardian_of_alola,
        MoveList.natures_madness,
        null,
        new ItemActivation[0]
    );

    public static final Item solganium_z = new Item(
        "Solganium Z",
        false,
        ItemType.ZCrystal,
        new Pokemon[] {
            PokemonList.solgaleo,
            PokemonList.necrozma_dusk_mane
        },
        MoveList.searing_sunraze_smash,
        MoveList.sunsteel_strike,
        null,
        new ItemActivation[0]
    );

    public static final Item lunalium_z = new Item(
        "Lunalium Z",
        false,
        ItemType.ZCrystal,
        new Pokemon[] {
            PokemonList.lunala,
            PokemonList.necrozma_dawn_wings
        },
        MoveList.menacing_moonraze_maelstrom,
        MoveList.moongeist_beam,
        null,
        new ItemActivation[0]
    );

    public static final Item ultranecrozium_z = new Item(
        "Ultranecrozium Z",
        false,
        ItemType.ZCrystal,
        new Pokemon[] {
            PokemonList.necrozma_dusk_mane,
            PokemonList.necrozma_dawn_wings,
            PokemonList.necrozma_ultra
        },
        MoveList.light_that_burns_the_sky,
        MoveList.photon_geyser,
        null,
        new ItemActivation[0]
    );

    public static final Item marshadium_z = new Item(
        "Marshadium Z",
        false,
        ItemType.ZCrystal,
        new Pokemon[] {
            PokemonList.marshadow
        },
        MoveList.soul_stealing_7_star_strike,
        MoveList.spectral_thief,
        null,
        new ItemActivation[0]
    );

    public static final Item fighting_memory = new Item(
        "Fighting Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_fighting
        },
        PokemonList.silvally_fighting,
        TypeList.fighting,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item flying_memory = new Item(
        "Flying Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_flying
        },
        PokemonList.silvally_flying,
        TypeList.flying,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item poison_memory = new Item(
        "Poison Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_poison
        },
        PokemonList.silvally_poison,
        TypeList.poison,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item ground_memory = new Item(
        "Ground Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_ground
        },
        PokemonList.silvally_ground,
        TypeList.ground,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item rock_memory = new Item(
        "Rock Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_rock
        },
        PokemonList.silvally_rock,
        TypeList.rock,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item bug_memory = new Item(
        "Bug Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_bug
        },
        PokemonList.silvally_bug,
        TypeList.bug,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item ghost_memory = new Item(
        "Ghost Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_ghost
        },
        PokemonList.silvally_ghost,
        TypeList.ghost,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item steel_memory = new Item(
        "Steel Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_steel
        },
        PokemonList.silvally_steel,
        TypeList.steel,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item fire_memory = new Item(
        "Fire Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_fire
        },
        PokemonList.silvally_fire,
        TypeList.fire,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item water_memory = new Item(
        "Water Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_water
        },
        PokemonList.silvally_water,
        TypeList.water,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item grass_memory = new Item(
        "Grass Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_grass
        },
        PokemonList.silvally_grass,
        TypeList.grass,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item electric_memory = new Item(
        "Electric Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_electric
        },
        PokemonList.silvally_electric,
        TypeList.electric,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item psychic_memory = new Item(
        "Psychic Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_psychic
        },
        PokemonList.silvally_psychic,
        TypeList.psychic,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item ice_memory = new Item(
        "Ice Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_ice
        },
        PokemonList.silvally_ice,
        TypeList.ice,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item dragon_memory = new Item(
        "Dragon Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_dragon
        },
        PokemonList.silvally_dragon,
        TypeList.dragon,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item dark_memory = new Item(
        "Dark Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_dark
        },
        PokemonList.silvally_dark,
        TypeList.dark,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item fairy_memory = new Item(
        "Fairy Memory",
        false,
        ItemType.Memory,
        new Pokemon[] {
            PokemonList.silvally,
            PokemonList.silvally_fairy
        },
        PokemonList.silvally_fairy,
        TypeList.fairy,
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item rusted_sword = new Item(
        "Rusted Sword",
        false,
        new Pokemon[] {
            PokemonList.zacian,
            PokemonList.zacian_crowned
        },
        PokemonList.zacian_crowned,
        null,
        new ItemActivation[0],
        true
    );

    public static final Item rusted_shield = new Item(
        "Rusted Shield",
        false,
        new Pokemon[] {
            PokemonList.zamazenta,
            PokemonList.zamazenta_crowned
        },
        PokemonList.zamazenta_crowned,
        null,
        new ItemActivation[0],
        true
    );

    public static final Item eternal_wishing_star = new Item( // fanmade
        "Eternal Wishing Star",
        false,
        new Pokemon[] {
            PokemonList.eternatus,
            PokemonList.eternatus_eternamax
        },
        PokemonList.eternatus_eternamax,
        null,
        new ItemActivation[0],
        true
    );

    public static final Item wellspring_mask = new Item(
        "Wellspring Mask",
        false,
        ItemType.Mask,
        new Pokemon[] {
            PokemonList.ogerpon,
            PokemonList.ogerpon_wellspring
        },
        PokemonList.ogerpon_wellspring,
        TypeList.water,
        (thisItem, _, _, _, _, _, _) -> {
            if (thisItem.heldByValidUser(true)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        60,
        null,
        true
    );

    public static final Item hearthflame_mask = new Item(
        "Hearthflame Mask",
        false,
        ItemType.Mask,
        new Pokemon[] {
            PokemonList.ogerpon,
            PokemonList.ogerpon_hearthflame
        },
        PokemonList.ogerpon_hearthflame,
        TypeList.fire,
        (thisItem, _, _, _, _, _, _) -> {
            if (thisItem.heldByValidUser(true)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        60,
        null,
        true
    );

    public static final Item cornerstone_mask = new Item(
        "Cornerstone Mask",
        false,
        ItemType.Mask,
        new Pokemon[] {
            PokemonList.ogerpon,
            PokemonList.ogerpon_cornerstone
        },
        PokemonList.ogerpon_cornerstone,
        TypeList.rock,
        (thisItem, _, _, _, _, _, _) -> {
            if (thisItem.heldByValidUser(true)) {
                return 1.2;
            }
            return 1.0;
        },
        new ItemActivation[] {
            ItemActivation.PowerCalc
        },
        60,
        null,
        true
    );

    public static final Item stellar_orb = new Item( // fanmade
        "Stellar Orb",
        false,
        new Pokemon[] {
            PokemonList.terapagos,
            PokemonList.terapagos_terastal,
            PokemonList.terapagos_stellar
        },
        PokemonList.terapagos_stellar,
        null,
        new ItemActivation[0],
        true
    );
}
