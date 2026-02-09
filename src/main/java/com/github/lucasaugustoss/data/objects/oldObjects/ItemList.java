package com.github.lucasaugustoss.data.objects.oldObjects;

import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.classes.Item;
import com.github.lucasaugustoss.data.objects.Data;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;
import com.github.lucasaugustoss.data.properties.items.ItemType;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;

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
        new PokemonTemplate[] {
            Data.get().getPokemon("venusaur"),
            Data.get().getPokemon("venusaur_mega")
        },
        Data.get().getPokemon("venusaur_mega"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("charizard"),
            Data.get().getPokemon("charizard_mega_x")
        },
        Data.get().getPokemon("charizard_mega_x"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("charizard"),
            Data.get().getPokemon("charizard_mega_y")
        },
        Data.get().getPokemon("charizard_mega_y"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("blastoise"),
            Data.get().getPokemon("blastoise_mega")
        },
        Data.get().getPokemon("blastoise_mega"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("mewtwo"),
            Data.get().getPokemon("mewtwo_mega_x")
        },
        Data.get().getPokemon("mewtwo_mega_x"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("mewtwo"),
            Data.get().getPokemon("mewtwo_mega_y")
        },
        Data.get().getPokemon("mewtwo_mega_y"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("tyranitar"),
            Data.get().getPokemon("tyranitar_mega")
        },
        Data.get().getPokemon("tyranitar_mega"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("sceptile"),
            Data.get().getPokemon("sceptile_mega")
        },
        Data.get().getPokemon("sceptile_mega"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("blaziken"),
            Data.get().getPokemon("blaziken_mega")
        },
        Data.get().getPokemon("blaziken_mega"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("swampert"),
            Data.get().getPokemon("swampert_mega")
        },
        Data.get().getPokemon("swampert_mega"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("salamence"),
            Data.get().getPokemon("salamence_mega")
        },
        Data.get().getPokemon("salamence_mega"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("latias"),
            Data.get().getPokemon("latias_mega")
        },
        Data.get().getPokemon("latias_mega"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("latios"),
            Data.get().getPokemon("latios_mega")
        },
        Data.get().getPokemon("latios_mega"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("kyogre"),
            Data.get().getPokemon("kyogre_primal")
        },
        Data.get().getPokemon("kyogre_primal"),
        (thisItem, holder, _, _, _, _, activation) -> {
            if (activation == ItemActivation.Entry) {
                if (holder.compare(Data.get().getPokemon("kyogre"), true) && holder.compareWithForm(Data.get().getPokemon("kyogre"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("groudon"),
            Data.get().getPokemon("groudon_primal")
        },
        Data.get().getPokemon("groudon_primal"),
        (thisItem, holder, _, _, _, _, activation) -> {
            if (activation == ItemActivation.Entry) {
                if (holder.compare(Data.get().getPokemon("groudon"), true) && holder.compareWithForm(Data.get().getPokemon("groudon"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("lucario"),
            Data.get().getPokemon("lucario_mega")
        },
        Data.get().getPokemon("lucario_mega"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("dialga"),
            Data.get().getPokemon("dialga_origin")
        },
        Data.get().getPokemon("dialga_origin"),
        (thisItem, _, _, _, move, _, _) -> {
            if (thisItem.heldByValidUser(true) &&
                (move.getType(false, false).compare(Data.get().getType("steel")) || move.getType(false, false).compare(Data.get().getType("dragon")))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("palkia"),
            Data.get().getPokemon("palkia_origin")
        },
        Data.get().getPokemon("palkia_origin"),
        (thisItem, _, _, _, move, _, _) -> {
            if (thisItem.heldByValidUser(true) &&
                (move.getType(false, false).compare(Data.get().getType("water")) || move.getType(false, false).compare(Data.get().getType("dragon")))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("giratina"),
            Data.get().getPokemon("giratina_origin")
        },
        Data.get().getPokemon("giratina_origin"),
        (thisItem, _, _, _, move, _, _) -> {
            if (thisItem.heldByValidUser(true) &&
                (move.getType(false, false).compare(Data.get().getType("ghost")) || move.getType(false, false).compare(Data.get().getType("dragon")))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_fighting")
        },
        Data.get().getPokemon("arceus_fighting"),
        Data.get().getType("fighting"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("fighting"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_flying")
        },
        Data.get().getPokemon("arceus_flying"),
        Data.get().getType("flying"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("flying"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_poison")
        },
        Data.get().getPokemon("arceus_poison"),
        Data.get().getType("poison"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("poison"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_ground")
        },
        Data.get().getPokemon("arceus_ground"),
        Data.get().getType("ground"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("ground"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_rock")
        },
        Data.get().getPokemon("arceus_rock"),
        Data.get().getType("rock"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("rock"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_bug")
        },
        Data.get().getPokemon("arceus_bug"),
        Data.get().getType("bug"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("bug"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_ghost")
        },
        Data.get().getPokemon("arceus_ghost"),
        Data.get().getType("ghost"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("ghost"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_steel")
        },
        Data.get().getPokemon("arceus_steel"),
        Data.get().getType("steel"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("steel"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_fire")
        },
        Data.get().getPokemon("arceus_fire"),
        Data.get().getType("fire"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("fire"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_water")
        },
        Data.get().getPokemon("arceus_water"),
        Data.get().getType("water"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("water"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_grass")
        },
        Data.get().getPokemon("arceus_grass"),
        Data.get().getType("grass"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("grass"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_electric")
        },
        Data.get().getPokemon("arceus_electric"),
        Data.get().getType("electric"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("electric"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_psychic")
        },
        Data.get().getPokemon("arceus_psychic"),
        Data.get().getType("psychic"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("psychic"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_ice")
        },
        Data.get().getPokemon("arceus_ice"),
        Data.get().getType("ice"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("ice"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_dragon")
        },
        Data.get().getPokemon("arceus_dragon"),
        Data.get().getType("dragon"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("dragon"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_dark")
        },
        Data.get().getPokemon("arceus_dark"),
        Data.get().getType("dark"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("dark"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("arceus"),
            Data.get().getPokemon("arceus_fairy")
        },
        Data.get().getPokemon("arceus_fairy"),
        Data.get().getType("fairy"),
        (_, _, _, _, move, _, _) -> {
            if (move.getType(false, false).compare(Data.get().getType("fairy"))) {
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
        new PokemonTemplate[] {
            Data.get().getPokemon("genesect"),
            Data.get().getPokemon("genesect_douse")
        },
        Data.get().getPokemon("genesect_douse"),
        Data.get().getType("water"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("genesect"),
            Data.get().getPokemon("genesect_shock")
        },
        Data.get().getPokemon("genesect_shock"),
        Data.get().getType("electric"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("genesect"),
            Data.get().getPokemon("genesect_burn")
        },
        Data.get().getPokemon("genesect_burn"),
        Data.get().getType("fire"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("genesect"),
            Data.get().getPokemon("genesect_chill")
        },
        Data.get().getPokemon("genesect_chill"),
        Data.get().getType("ice"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("diancie"),
            Data.get().getPokemon("diancie_mega")
        },
        Data.get().getPokemon("diancie_mega"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("mew")
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
        new PokemonTemplate[] {
            Data.get().getPokemon("decidueye")
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
        new PokemonTemplate[] {
            Data.get().getPokemon("incineroar")
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
        new PokemonTemplate[] {
            Data.get().getPokemon("primarina")
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
        new PokemonTemplate[] {
            Data.get().getPokemon("tapu_koko"),
            Data.get().getPokemon("tapu_lele"),
            Data.get().getPokemon("tapu_bulu"),
            Data.get().getPokemon("tapu_fini")
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
        new PokemonTemplate[] {
            Data.get().getPokemon("solgaleo"),
            Data.get().getPokemon("necrozma_dusk_mane")
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
        new PokemonTemplate[] {
            Data.get().getPokemon("lunala"),
            Data.get().getPokemon("necrozma_dawn_wings")
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
        new PokemonTemplate[] {
            Data.get().getPokemon("necrozma_dusk_mane"),
            Data.get().getPokemon("necrozma_dawn_wings"),
            Data.get().getPokemon("necrozma_ultra")
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
        new PokemonTemplate[] {
            Data.get().getPokemon("marshadow")
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_fighting")
        },
        Data.get().getPokemon("silvally_fighting"),
        Data.get().getType("fighting"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_flying")
        },
        Data.get().getPokemon("silvally_flying"),
        Data.get().getType("flying"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_poison")
        },
        Data.get().getPokemon("silvally_poison"),
        Data.get().getType("poison"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_ground")
        },
        Data.get().getPokemon("silvally_ground"),
        Data.get().getType("ground"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_rock")
        },
        Data.get().getPokemon("silvally_rock"),
        Data.get().getType("rock"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_bug")
        },
        Data.get().getPokemon("silvally_bug"),
        Data.get().getType("bug"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_ghost")
        },
        Data.get().getPokemon("silvally_ghost"),
        Data.get().getType("ghost"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_steel")
        },
        Data.get().getPokemon("silvally_steel"),
        Data.get().getType("steel"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_fire")
        },
        Data.get().getPokemon("silvally_fire"),
        Data.get().getType("fire"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_water")
        },
        Data.get().getPokemon("silvally_water"),
        Data.get().getType("water"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_grass")
        },
        Data.get().getPokemon("silvally_grass"),
        Data.get().getType("grass"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_electric")
        },
        Data.get().getPokemon("silvally_electric"),
        Data.get().getType("electric"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_psychic")
        },
        Data.get().getPokemon("silvally_psychic"),
        Data.get().getType("psychic"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_ice")
        },
        Data.get().getPokemon("silvally_ice"),
        Data.get().getType("ice"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_dragon")
        },
        Data.get().getPokemon("silvally_dragon"),
        Data.get().getType("dragon"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_dark")
        },
        Data.get().getPokemon("silvally_dark"),
        Data.get().getType("dark"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("silvally"),
            Data.get().getPokemon("silvally_fairy")
        },
        Data.get().getPokemon("silvally_fairy"),
        Data.get().getType("fairy"),
        null,
        new ItemActivation[0],
        50,
        null,
        true
    );

    public static final Item rusted_sword = new Item(
        "Rusted Sword",
        false,
        new PokemonTemplate[] {
            Data.get().getPokemon("zacian"),
            Data.get().getPokemon("zacian_crowned")
        },
        Data.get().getPokemon("zacian_crowned"),
        null,
        new ItemActivation[0],
        true
    );

    public static final Item rusted_shield = new Item(
        "Rusted Shield",
        false,
        new PokemonTemplate[] {
            Data.get().getPokemon("zamazenta"),
            Data.get().getPokemon("zamazenta_crowned")
        },
        Data.get().getPokemon("zamazenta_crowned"),
        null,
        new ItemActivation[0],
        true
    );

    public static final Item eternal_wishing_star = new Item( // fanmade
        "Eternal Wishing Star",
        false,
        new PokemonTemplate[] {
            Data.get().getPokemon("eternatus"),
            Data.get().getPokemon("eternatus_eternamax")
        },
        Data.get().getPokemon("eternatus_eternamax"),
        null,
        new ItemActivation[0],
        true
    );

    public static final Item wellspring_mask = new Item(
        "Wellspring Mask",
        false,
        ItemType.Mask,
        new PokemonTemplate[] {
            Data.get().getPokemon("ogerpon"),
            Data.get().getPokemon("ogerpon_wellspring")
        },
        Data.get().getPokemon("ogerpon_wellspring"),
        Data.get().getType("water"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("ogerpon"),
            Data.get().getPokemon("ogerpon_hearthflame")
        },
        Data.get().getPokemon("ogerpon_hearthflame"),
        Data.get().getType("fire"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("ogerpon"),
            Data.get().getPokemon("ogerpon_cornerstone")
        },
        Data.get().getPokemon("ogerpon_cornerstone"),
        Data.get().getType("rock"),
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
        new PokemonTemplate[] {
            Data.get().getPokemon("terapagos"),
            Data.get().getPokemon("terapagos_terastal"),
            Data.get().getPokemon("terapagos_stellar")
        },
        Data.get().getPokemon("terapagos_stellar"),
        null,
        new ItemActivation[0],
        true
    );
}
