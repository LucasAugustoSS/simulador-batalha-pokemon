package data.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.activationConditions.AbilityActivation;
import data.activationConditions.ItemActivation;
import data.activationConditions.MoveEffectActivation;
import data.activationConditions.StatusActivation;
import data.classes.FieldCondition;
import data.classes.Item;
import data.classes.Move;
import data.classes.Pokemon;
import data.classes.Stat;
import data.classes.StatusCondition;
import data.classes.Type;
import data.lists.AllMoves;
import data.messages.list.GeneralMessages;
import data.properties.fieldConditions.FieldConditionType;
import data.properties.items.ItemType;
import data.properties.moves.*;
import data.properties.other.DamageSource;
import data.properties.stats.StatName;
import main.Battle;
import main.Damage;
import main.actions.Action;
import main.actions.PriorityBracket;

public class MoveList {
    // não movimentos

    public static final Move _placeholder_ = new Move(
        "Placeholder",
        TypeList.typeless,
        null,
        0,
        0,
        -1,
        0,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty._NotMove_
        }
    );

    public static final Move _switch_ = new Move(
        "Switch Pokémon",
        TypeList.typeless,
        null,
        1,
        0,
        -1,
        8,
        MoveTarget.User,
        null,
        null,
        new MoveEffectActivation[0],
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty._NotMove_
        }
    );

    public static final Move _mega_evolve_ = new Move(
        "Mega Evolve Pokémon",
        TypeList.typeless,
        null,
        1,
        0,
        -1,
        7,
        MoveTarget.User,
        null,
        null,
        new MoveEffectActivation[0],
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty._NotMove_
        }
    );

    public static final Move _ultra_burst_ = new Move(
        "Ultra Burst",
        TypeList.typeless,
        null,
        1,
        0,
        -1,
        7,
        MoveTarget.User,
        null,
        null,
        new MoveEffectActivation[0],
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty._NotMove_
        }
    );

    public static final Move _terastallize_ = new Move(
        "Terastallize",
        TypeList.typeless,
        null,
        1,
        0,
        -1,
        7,
        MoveTarget.User,
        null,
        null,
        new MoveEffectActivation[0],
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty._NotMove_
        }
    );



    // debug

    public static final Move _heal_all_ = new Move( // debug
        "Heal 100% HP",
        TypeList.normal,
        Category.Status,
        30,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            Damage.heal(user, thisMove, user.getHP(), true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move _cure_non_volatile_ = new Move( // debug
        "Cure Non-Volatile Status Condition",
        TypeList.normal,
        Category.Status,
        30,
        0,
        -1,
        0,
        MoveTarget.User,
        (_, user, _, _, _, _, _, _) -> {
            user.endNonVolatileStatus(true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move _freeze_move_ = new Move( // debug
        "Freeze",
        TypeList.ice,
        Category.Status,
        15,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.freeze.apply(pokemon, thisMove, showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );



    // movimentos normais

    public static final Move absorb = new Move(
        "Absorb",
        TypeList.grass,
        Category.Special,
        25,
        20,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            if (user.getCurrentHP() < user.getHP()) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }

                Damage.heal(user, thisMove, Integer.max(damage.amount/2, 1), true, false);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move acid_armor = new Move(
        "Acid Armor",
        TypeList.poison,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Def).change(2, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move acid_spray = new Move(
        "Acid Spray",
        TypeList.poison,
        Category.Special,
        20,
        40,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.SpD).change(-2, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.BallBomb
        }
    );

    public static final Move acrobatics = new Move(
        "Acrobatics",
        TypeList.flying,
        Category.Physical,
        15,
        55,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            if (user.getItem().compare(ItemList.none)) {
                return 110.0;
            }
            return 55.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move aerial_ace = new Move(
        "Aerial Ace",
        TypeList.flying,
        Category.Physical,
        20,
        60,
        -1,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move aeroblast = new Move(
        "Aeroblast",
        TypeList.flying,
        Category.Special,
        5,
        100,
        95,
        2,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move after_you = new Move(
        "After You",
        TypeList.normal,
        Category.Status,
        15,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, condition) -> {
            Action userAction = Battle.findAction(thisMove, user);
            Action targetAction = Battle.findAction(target, false);

            if (condition == MoveEffectActivation.TryUse) {
                return !Battle.actionIsAfterOther(userAction, targetAction);
            }
            if (condition == MoveEffectActivation.AfterMove) {
                Battle.moveAction(targetAction, userAction);
                System.out.println(target.getName(true, true) + " took the kind offer!");
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute,
            InherentProperty.MetronomeUncallable,
            InherentProperty.NotReflectable
        }
    );

    public static final Move agility = new Move(
        "Agility",
        TypeList.psychic,
        Category.Status,
        30,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Spe).change(2, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move air_cutter = new Move(
        "Air Cutter",
        TypeList.flying,
        Category.Special,
        25,
        60,
        95,
        2,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move air_slash = new Move(
        "Air Slash",
        TypeList.flying,
        Category.Special,
        15,
        75,
        95,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move ally_switch = new Move(
        "Ally Switch",
        TypeList.psychic,
        Category.Status,
        15,
        0,
        -1,
        2,
        MoveTarget.User,
        (_, _, _, _, _, _, _, condition) -> {
            // sem uso em singles
            if (condition == MoveEffectActivation.TryUse) {
                return false;
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(2, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotSnatchable
        }
    );

    public static final Move amnesia = new Move(
        "Amnesia",
        TypeList.psychic,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.SpD).change(2, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move ancient_power = new Move(
        "Ancient Power",
        TypeList.rock,
        Category.Special,
        5,
        60,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }
                user.getStat(StatName.Atk).change(1, thisMove, true, true, false);
                user.getStat(StatName.Def).change(1, thisMove, true, true, false);
                user.getStat(StatName.SpA).change(1, thisMove, true, true, false);
                user.getStat(StatName.SpD).change(1, thisMove, true, true, false);
                user.getStat(StatName.Spe).change(1, thisMove, true, true, false);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move aqua_cutter = new Move(
        "Aqua Cutter",
        TypeList.water,
        Category.Physical,
        20,
        70,
        100,
        2,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move aqua_jet = new Move(
        "Aqua Jet",
        TypeList.water,
        Category.Physical,
        20,
        40,
        100,
        1,
        true,
        1,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move aqua_ring = new Move(
        "Aqua Ring",
        TypeList.water,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusConditionList.aqua_ring.apply(user, thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move aqua_step = new Move(
        "Aqua Step",
        TypeList.water,
        Category.Physical,
        10,
        80,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.Spe).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move aqua_tail = new Move(
        "Aqua Tail",
        TypeList.water,
        Category.Physical,
        10,
        90,
        90,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move arm_thrust = new Move(
        "Arm Thrust",
        TypeList.fighting,
        Category.Physical,
        20,
        15, 100,
        100,
        1,
        false,
        0,
        new int[] {2, 5},
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move aromatherapy = new Move(
        "Aromatherapy",
        TypeList.grass,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.UserAndAlly,
        (_, user, _, _, _, _, _, _) -> {
            System.out.println("A soothing aroma wafted through the area!");
            for (Pokemon pokemon : Battle.teams[user.getTeam()]) {
                if (pokemon != null) {
                    pokemon.endNonVolatileStatus(true);
                }
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return Damage.heal(user, thisMove, user.getHP(), true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move aromatic_mist = new Move(
        "Aromatic Mist",
        TypeList.fairy,
        Category.Status,
        20,
        0,
        -1,
        5,
        MoveTarget.Ally,
        (_, _, _, _, _, _, _, condition) -> {
            // sem uso em singles
            if (condition == MoveEffectActivation.TryUse) {
                return false;
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(2, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move assurance = new Move(
        "Assurance",
        TypeList.dark,
        Category.Physical,
        10,
        60,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            if (target.wasDamagedThisTurn()) {
                return 120.0;
            }
            return 60.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move astonish = new Move(
        "Astonish",
        TypeList.ghost,
        Category.Physical,
        15,
        30,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move astral_barrage = new Move(
        "Astral Barrage",
        TypeList.ghost,
        Category.Special,
        5,
        120,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move attract = new Move(
        "Attract",
        TypeList.normal,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, showMessages, condition) -> {
            if (!user.getGender().equals("Unknown") &&
                !target.getGender().equals("Unknown") &&
                !target.getGender().equals(user.getGender())) {
                Pokemon pokemon;
                if (condition != MoveEffectActivation.TestImmunities) {
                    pokemon = target;
                } else {
                    pokemon = new Pokemon(target, target.getTeam());
                    if (target.getAbility().isActive()) {
                        pokemon.getAbility().setActive(true);
                    }
                }

                return StatusConditionList.infatuation.apply(pokemon, thisMove, user, showMessages);
            }
            return false;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move aura_sphere = new Move(
        "Aura Sphere",
        TypeList.fighting,
        Category.Special,
        20,
        80,
        -1,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.BallBomb,
            MoveType.Pulse
        }
    );

    public static final Move aurora_beam = new Move(
        "Aurora Beam",
        TypeList.ice,
        Category.Special,
        20,
        65,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.Atk).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move aurora_veil = new Move(
        "Aurora Veil",
        TypeList.ice,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.UserField,
        (thisMove, user, _, _, _, _, _, _) -> {
            if (Battle.getWeather().compare(FieldConditionList.snow)) {
                FieldConditionList.aurora_veil.apply(user.getTeam(), thisMove, true);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move avalanche = new Move(
        "Avalanche",
        TypeList.ice,
        Category.Physical,
        10,
        60,
        100,
        1,
        true,
        -4,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            if (user.getDamager() == target) {
                return 120.0;
            }
            return 60.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move baby_doll_eyes = new Move(
        "Baby-Doll Eyes",
        TypeList.fairy,
        Category.Status,
        30,
        0,
        100,
        1,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Atk).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move baton_pass = new Move(
        "Baton Pass",
        TypeList.normal,
        Category.Status,
        40,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            Action moveLocation = Battle.findAction(thisMove, user);
            Move switchMove = new Move(MoveList._switch_, user);
            switchMove.addProperty(TemporaryProperty._Pivot_);
            switchMove.addProperty(TemporaryProperty._TransferValues_);
            Battle.addAction(new Action(switchMove, user, user), moveLocation);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotSnatchable
        }
    );

    public static final Move beat_up = new Move(
        "Beat Up",
        TypeList.dark,
        Category.Physical,
        10,
        0, 100,
        100,
        1,
        true,
        0,
        new int[] {1},
        MoveTarget.Normal,
        (_, user, _, _, _, hit, _, condition) -> {
            ArrayList<Pokemon> attackers = new ArrayList<>();
            for (Pokemon pokemon : Battle.teams[user.getTeam()]) {
                if (pokemon != null &&
                    !Battle.faintCheck(pokemon, false) &&
                    (pokemon == user || pokemon.getNonVolatileStatus().compare(StatusConditionList.none))) {
                    attackers.add(pokemon);
                }
            }

            if (condition == MoveEffectActivation.CallHits) {
                return attackers.size();
            }

            if (condition == MoveEffectActivation.CallPower) {
                return (attackers.get(hit).getBaseStat(StatName.Atk)/10.0) + 5;
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallHits,
            MoveEffectActivation.CallPower
        }
    );

    public static final Move behemoth_bash = new Move(
        "Behemoth Bash",
        TypeList.steel,
        Category.Physical,
        5,
        100,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move behemoth_blade = new Move(
        "Behemoth Blade",
        TypeList.steel,
        Category.Physical,
        5,
        100,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move bind = new Move(
        "Bind",
        TypeList.normal,
        Category.Physical,
        20,
        15,
        85,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (target.getVolatileStatus(StatusConditionList.bind) == null) {
                StatusConditionList.bind.apply(target, thisMove, (int) Math.ceil(Math.random()*2)+3, user, true); // 4-5 turnos
                if (!Battle.faintCheck(target, false)) {
                    System.out.println(target.getName(true, true) + " was squeezed by " + user.getName(true, false) + "!");
                }
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move bite = new Move(
        "Bite",
        TypeList.dark,
        Category.Physical,
        25,
        60,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move bitter_malice = new Move(
        "Bitter Malice",
        TypeList.ghost,
        Category.Special,
        10,
        85,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (user.getAbility().compare(AbilityList.illusion) &&
                user.getAbility().isActive()) {
                Pokemon pokemonDisguise = (Pokemon) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.CallUserData);

                Move moveDisguise = null;
                for (Move move : pokemonDisguise.getMoves()) {
                    if (move != null &&
                        move.getCategory() != Category.Status) {
                        moveDisguise = move;
                        break;
                    }
                }

                if (moveDisguise != null) {
                    return moveDisguise;
                }
            }

            return thisMove;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallMoveData
        }
    );

    public static final Move blaze_kick = new Move(
        "Blaze Kick",
        TypeList.fire,
        Category.Physical,
        10,
        85,
        90,
        2,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move bleakwind_storm = new Move(
        "Bleakwind Storm",
        TypeList.flying,
        Category.Special,
        10,
        100,
        80,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        (_, _, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AccuracyCalc) {
                if (Battle.getWeather().compare(FieldConditionList.snow)) {
                    return -1.0;
                } else {
                    return 80.0;
                }
            }
            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                double chance = 0.3;
                if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                    chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                }

                if (Math.random() < chance) {
                    target.getStat(StatName.Spe).change(-1, thisMove, false, true, false);
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.AccuracyCalc
        }
    );

    public static final Move blizzard = new Move(
        "Blizzard",
        TypeList.ice,
        Category.Special,
        5,
        110,
        70,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        (_, _, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AccuracyCalc) {
                if (Battle.getWeather().compare(FieldConditionList.snow)) {
                    return -1.0;
                }
                return 70.0;
            }
            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                double chance = 0.1;
                if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                    chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                }

                if (Math.random() < chance) {
                    StatusConditionList.frostbite.apply(target, thisMove, true);
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.AccuracyCalc
        }
    );

    public static final Move block = new Move(
        "Block",
        TypeList.normal,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.trapped.apply(pokemon, thisMove, user, showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresProtection
        }
    );

    public static final Move blue_flare = new Move(
        "Blue Flare",
        TypeList.fire,
        Category.Special,
        5,
        130,
        85,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.2;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move body_press = new Move(
        "Body Press",
        TypeList.fighting,
        Category.Physical,
        10,
        80,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallAttackingStat
        }
    );

    public static final Move body_slam = new Move(
        "Body Slam",
        TypeList.normal,
        Category.Physical,
        15,
        85,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.paralysis.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move bolt_strike = new Move(
        "Bolt Strike",
        TypeList.electric,
        Category.Physical,
        5,
        130,
        85,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.2;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.paralysis.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move bone_rush = new Move(
        "Bone Rush",
        TypeList.ground,
        Category.Physical,
        10,
        25, 140,
        90,
        1,
        false,
        0,
        new int[] {2, 5},
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move boomburst = new Move(
        "Boomburst",
        TypeList.normal,
        Category.Special,
        10,
        140,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move bounce = new Move(
        "Bounce",
        TypeList.flying,
        Category.Physical,
        5,
        85,
        85,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, condition) -> {
            StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);

            if (condition == MoveEffectActivation.AfterMove) {
                if (chargeCondition == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.semi_invulnerable_charging_turn.apply(user, thisMove, true);

                    System.out.println(user.getName(true, true) + " sprang up!");
                } else {
                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }
            }
            if (condition == MoveEffectActivation.Miss) {
                user.setReadiedMove(null);
                user.endVolatileStatus(chargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);

                if (chargeCondition == null) { // ativa depois do ataque, quando perde chargeCondition
                    double chance = 0.3;
                    if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                        chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                    }

                    if (Math.random() < chance) {
                        StatusConditionList.paralysis.apply(target, thisMove, true);
                    }
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss
        },
        new InherentProperty[] {
            InherentProperty.Charges,
            InherentProperty.GravityUnusable
        }
    );

    public static final Move branch_poke = new Move(
        "Branch Poke",
        TypeList.grass,
        Category.Physical,
        40,
        40,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move brave_bird = new Move(
        "Brave Bird",
        TypeList.flying,
        Category.Physical,
        15,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            int recoilDamage = damage.amount/3;
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            String message = user.getName(true, true) + " was damaged by the recoil!";
            Damage.indirectDamage(user, user, recoilDamage, DamageSource.Recoil, thisMove, message, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.Recoil
        }
    );

    public static final Move breaking_swipe = new Move(
        "Breaking Swipe",
        TypeList.dragon,
        Category.Physical,
        15,
        60,
        100,
        1,
        true,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Atk).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move brick_break = new Move(
        "Brick Break",
        TypeList.fighting,
        Category.Physical,
        15,
        75,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            ArrayList<FieldCondition> field = Battle.teamFields.get(target.getTeam());
            for (FieldCondition fieldCondition : field) {
                if (fieldCondition.compare(FieldConditionList.reflect) ||
                    fieldCondition.compare(FieldConditionList.light_screen) ||
                    fieldCondition.compare(FieldConditionList.aurora_veil)) {
                    fieldCondition.end(field);
                }
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.BeforeMove
        }
    );

    public static final Move brine = new Move(
        "Brine",
        TypeList.water,
        Category.Physical,
        10,
        65,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            if (target.getCurrentHP() <= target.getHP()/2) {
                return 130.0;
            }
            return 65.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move brutal_swing = new Move(
        "Brutal Swing",
        TypeList.dark,
        Category.Physical,
        20,
        60,
        100,
        1,
        true,
        0,
        MoveTarget.AllAdjacent,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move bubble = new Move(
        "Bubble",
        TypeList.water,
        Category.Special,
        30,
        40,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.Spe).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move bubble_beam = new Move(
        "Bubble Beam",
        TypeList.water,
        Category.Special,
        20,
        65,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.Spe).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move bug_bite = new Move(
        "Bug Bite",
        TypeList.bug,
        Category.Physical,
        20,
        60,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (target.getItem().getType() == ItemType.Berry) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }
                System.out.println(user.getName(true, true) + " stole and ate its target's " + target.getItem().getName());

                target.getItem().activate(target, user, target, thisMove, null, ItemActivation.ForceUse);

                if (target.getItem().shouldActivate(ItemActivation.Consumed)) {
                    target.getItem().activate(target, user, target, thisMove, null, ItemActivation.Consumed);
                }
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move bug_buzz = new Move(
        "Bug Buzz",
        TypeList.bug,
        Category.Special,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.SpD).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move bulk_up = new Move(
        "Bulk Up",
        TypeList.fighting,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Atk).change(1, thisMove, true, true, false);
            user.getStat(StatName.Def).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Atk).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move bulldoze = new Move(
        "Bulldoze",
        TypeList.ground,
        Category.Physical,
        20,
        60,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Spe).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move calm_mind = new Move(
        "Calm Mind",
        TypeList.psychic,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.SpA).change(1, thisMove, true, true, false);
            user.getStat(StatName.SpD).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move captivate = new Move(
        "Captivate",
        TypeList.normal,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.AllOpponents,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (!user.getGender().equals("Unknown") &&
                !target.getGender().equals("Unknown") &&
                !target.getGender().equals(user.getGender())) {
                target.getStat(StatName.SpA).change(-2, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(2, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move ceaseless_edge = new Move(
        "Ceaseless Edge",
        TypeList.dark,
        Category.Physical,
        15,
        65,
        90,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> { // considerado secundário
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            FieldConditionList.spikes.apply(target.getTeam(), 1, thisMove, true);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move charge = new Move(
        "Charge",
        TypeList.electric,
        Category.Status,
        20,
        0,
        -1,
        4,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            System.out.println(user.getName(true, true) + " began charging power!");
            StatusConditionList.charge.apply(user, thisMove, true);
            user.getStat(StatName.SpD).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move charge_beam = new Move(
        "Charge Beam",
        TypeList.electric,
        Category.Special,
        10,
        50,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.7;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }
                user.getStat(StatName.SpA).change(1, thisMove, true, true, false);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move charm = new Move(
        "Charm",
        TypeList.fairy,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Atk).change(-2, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move clear_smog = new Move(
        "Clear Smog",
        TypeList.poison,
        Category.Special,
        15,
        50,
        -1,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            for (Stat stat : target.getStats()) {
                stat.setStages(0);
            }
            System.out.println("All stat changes were eliminated!");
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move close_combat = new Move(
        "Close Combat",
        TypeList.fighting,
        Category.Physical,
        5,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.Def).change(-1, thisMove, true, true, false);
            user.getStat(StatName.SpD).change(-1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move coil = new Move(
        "Coil",
        TypeList.poison,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Atk).change(1, thisMove, true, true, false);
            user.getStat(StatName.Def).change(1, thisMove, true, true, false);
            user.getStat(StatName.Acc).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move collision_course = new Move(
        "Collision Course",
        TypeList.fighting,
        Category.Physical,
        5,
        100,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            double effectivenessMultiplier = 1;

            effectivenessMultiplier *= Damage.superEffective(thisMove, target);
            effectivenessMultiplier /= Damage.notVeryEffective(thisMove, target);

            if (effectivenessMultiplier > 1) {
                return 5461.0/4096.0;
            }
            return 1.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.DamageCalc
        }
    );

    public static final Move confuse_ray = new Move(
        "Confuse Ray",
        TypeList.ghost,
        Category.Status,
        10,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.confusion.apply(pokemon, thisMove, (int) Math.ceil(Math.random()*4)+1, showMessages); // 2-5 turnos
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move confusion = new Move(
        "Confusion",
        TypeList.psychic,
        Category.Special,
        25,
        50,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.confusion.apply(target, thisMove, (int) Math.ceil(Math.random()*4)+1, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move copycat = new Move(
        "Copycat",
        TypeList.normal,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, condition) -> {
            Move copiedMove = null;
            if (Battle.lastUsedMove != null &&
                !Battle.lastUsedMove.hasInherentProperty(InherentProperty.CopycatFails)) {
                if (Battle.lastUsedMove.isZMove()) {
                    copiedMove = new Move(Battle.lastUsedMove, Battle.lastUsedMove.getMoveOrigin(), user);
                } else {
                    copiedMove = new Move(Battle.lastUsedMove, user);
                }
            }
            if (thisMove.isZPowered() &&
                copiedMove != null &&
                copiedMove.getCategory() != Category.Status) {
                copiedMove = new Move(copiedMove.turnZMove(), copiedMove, user);
            }

            if (condition == MoveEffectActivation.TryUse) {
                return copiedMove != null;
            }

            if (condition == MoveEffectActivation.AfterMove) {
                if (thisMove.isZPowered()) {
                    System.out.print("Z-");
                }
                System.out.println("Copycat called " + copiedMove.getName() + "!");

                for (TemporaryProperty temporaryProperties : thisMove.getTemporaryProperties()) {
                    copiedMove.addProperty(temporaryProperties);
                }
                copiedMove.addProperty(TemporaryProperty.Called);

                Pokemon moveTarget;
                if (copiedMove.targetsOpponent()) {
                    if (user.getTeam() == 0) {
                        moveTarget = Battle.opponentActivePokemon;
                    } else {
                        moveTarget = Battle.yourActivePokemon;
                    }
                } else {
                    if (user.getTeam() == 0) {
                        moveTarget = Battle.yourActivePokemon;
                    } else {
                        moveTarget = Battle.opponentActivePokemon;
                    }
                }

                Action moveLocation = Battle.findAction(thisMove, user);
                Battle.addAction(new Action(copiedMove, user, moveTarget), moveLocation);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            // efeito de chamar um Z-Move é feito em primaryEffect
            return user.getStat(StatName.Acc).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails,
            InherentProperty.NotSnatchable
        }
    );

    public static final Move core_enforcer = new Move(
        "Core Enforcer",
        TypeList.dragon,
        Category.Special,
        10,
        100, 140,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        (thisMove, user, target, _, _, _, _, _) -> {
            Action userAction = Battle.findAction(thisMove, user);
            Action targetAction = Battle.findAction(target, true);

            if (Battle.actionIsAfterOther(userAction, targetAction) &&
                !target.getAbility().isNotSuppressable()) {
                StatusConditionList.suppressed_ability.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move cosmic_power = new Move(
        "Cosmic Power",
        TypeList.psychic,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Def).change(1, thisMove, true, true, false);
            user.getStat(StatName.SpD).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move counter = new Move(
        "Counter",
        TypeList.fighting,
        Category.Physical,
        20,
        0,
        100,
        1,
        true,
        -5,
        MoveTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            StatusCondition counterCondition = user.getVolatileStatus(StatusConditionList.countering);

            if (condition == MoveEffectActivation.TurnStart) {
                StatusConditionList.countering.apply(user, thisMove, true);
            }
            if (condition == MoveEffectActivation.ChangeTarget) {
                if (counterCondition != null &&
                    counterCondition.getAffectedMove() != null) {
                    return counterCondition.getAffectedMove().getUser();
                }
                return user;
            }
            if (condition == MoveEffectActivation.TryUse) {
                if (counterCondition == null) {
                    return false;
                }

                Action userAction = Battle.findAction(thisMove, user);
                Action targetAction = Battle.findAction(target, false);
                if (!Battle.actionIsAfterOther(userAction, targetAction)) {
                    return false;
                }

                return counterCondition.getCounter() > 0;
            }
            if (condition == MoveEffectActivation.FixedDamage) {
                return counterCondition.getCounter()*2;
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TurnStart,
            MoveEffectActivation.ChangeTarget,
            MoveEffectActivation.TryUse,
            MoveEffectActivation.FixedDamage
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails
        }
    );

    public static final Move court_change = new Move(
        "Court Change",
        TypeList.normal,
        Category.Status,
        10,
        0,
        100,
        0,
        MoveTarget.AllFields,
        (_, user, _, _, _, _, _, _) -> {
            ArrayList<FieldCondition> temp = new ArrayList<>(Battle.teamFields.get(0));
            Battle.teamFields.get(0).clear();
            Battle.teamFields.get(0).addAll(Battle.teamFields.get(1));
            Battle.teamFields.get(1).clear();
            Battle.teamFields.get(1).addAll(temp);

            System.out.println(user.getName(true, true) + " swapped the battle effects affecting each side of the field!");

            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move cross_chop = new Move(
        "Cross Chop",
        TypeList.fighting,
        Category.Physical,
        5,
        100,
        80,
        2,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move cross_poison = new Move(
        "Cross Poison",
        TypeList.poison,
        Category.Physical,
        20,
        70,
        100,
        2,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.poison.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move crunch = new Move(
        "Crunch",
        TypeList.dark,
        Category.Physical,
        15,
        80,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.2;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.Def).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move crush_claw = new Move(
        "Crush Claw",
        TypeList.normal,
        Category.Physical,
        10,
        75,
        95,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.5;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.Def).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move crush_grip = new Move(
        "Crush Grip",
        TypeList.normal,
        Category.Physical,
        5,
        0, 160,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            return Math.max(120.0*target.getCurrentHP()/target.getHP(), 1.0);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move curse = new Move(
        "Curse",
        TypeList.ghost,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, condition) -> {
            boolean ghostUser = false;
            for (Type userType : user.getTypes()) {
                if (userType.compare(TypeList.ghost)) {
                    ghostUser = true;
                    break;
                }
            }

            if (condition == MoveEffectActivation.AfterMove) {
                if (ghostUser) {
                    StatusConditionList.curse.apply(target, thisMove, user, true);

                    int remainingHP = Integer.max(user.getCurrentHP() - user.getHP()/2, 0);
                    user.setCurrentHP(remainingHP);

                    Battle.faintCheck(user, true);
                } else {
                    user.getStat(StatName.Spe).change(-1, thisMove, true, true, false);
                    user.getStat(StatName.Atk).change(1, thisMove, true, true, false);
                    user.getStat(StatName.Def).change(1, thisMove, true, true, false);
                }
            }
            if (condition == MoveEffectActivation.CallMoveTarget) {
                if (!ghostUser) {
                    return MoveTarget.User;
                }
                return MoveTarget.Normal;
            }
            if (condition == MoveEffectActivation.CallEffectTarget) {
                if (!ghostUser) {
                    return EffectTarget.User;
                }
                return EffectTarget.Target;
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.CallMoveTarget,
            MoveEffectActivation.CallEffectTarget
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            boolean ghostUser = false;
            for (Type userType : user.getTypes()) {
                if (userType.compare(TypeList.ghost)) {
                    ghostUser = true;
                    break;
                }
            }

            if (ghostUser) {
                return Damage.heal(user, thisMove, user.getHP(), true, true);
            } else {
                return user.getStat(StatName.Atk).change(1, thisMove, true, true, true);
            }
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresProtection,
            InherentProperty.IgnoresSubstitute,
            InherentProperty.NotReflectable,
            InherentProperty.NotSnatchable
        }
    );

    public static final Move dark_pulse = new Move(
        "Dark Pulse",
        TypeList.dark,
        Category.Special,
        15,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.2;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Pulse
        }
    );

    public static final Move dark_void = new Move(
        "Dark Void",
        TypeList.dark,
        Category.Status,
        10,
        0,
        50,
        0,
        MoveTarget.AllOpponents,
        (thisMove, user, target, _, _, _, showMessages, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (user.compare(PokemonList.darkrai, false)) {
                    return true;
                }
                System.out.println("But " + user.getName(true, false) + " can't use the move!");
                return false;
            } else {
                Pokemon pokemon;
                if (condition != MoveEffectActivation.TestImmunities) {
                    pokemon = target;
                } else {
                    pokemon = new Pokemon(target, target.getTeam());
                    if (target.getAbility().isActive()) {
                        pokemon.getAbility().setActive(true);
                    }
                }

                return StatusConditionList.sleep.apply(pokemon, thisMove, (int) Math.ceil(Math.random()*3), showMessages);
            }
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move darkest_lariat = new Move(
        "Darkest Lariat",
        TypeList.dark,
        Category.Physical,
        10,
        85,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty.IgnoresDefensiveAndEvasionStages
        }
    );

    public static final Move dazzling_gleam = new Move(
        "Dazzling Gleam",
        TypeList.fairy,
        Category.Special,
        10,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move defense_curl = new Move(
        "Defense Curl",
        TypeList.normal,
        Category.Status,
        40,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Def).change(1, thisMove, true, true, false);
            StatusConditionList.defense_curl.apply(user, thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Acc).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move defog = new Move(
        "Defog",
        TypeList.flying,
        Category.Status,
        15,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Eva).change(-1, thisMove, false, true, false);

            Battle.getTerrain().end();

            for (ArrayList<FieldCondition> field : Battle.teamFields) {
                for (FieldCondition fieldCondition : field) {
                    if (fieldCondition.getType() == FieldConditionType.ENTRY_HAZARD) {
                        fieldCondition.end(field);
                    }
                }
            }

            ArrayList<FieldCondition> field = Battle.teamFields.get(target.getTeam());
            for (FieldCondition fieldCondition : field) {
                if (fieldCondition.compare(FieldConditionList.reflect) ||
                    fieldCondition.compare(FieldConditionList.light_screen) ||
                    fieldCondition.compare(FieldConditionList.aurora_veil) ||
                    fieldCondition.compare(FieldConditionList.safeguard) ||
                    fieldCondition.compare(FieldConditionList.mist)) {
                    fieldCondition.end(field);
                }
            }
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Acc).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move destiny_bond = new Move(
        "Destiny Bond",
        TypeList.ghost,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return user.getVolatileStatus(StatusConditionList.destiny_bond) == null;
            }
            if (condition == MoveEffectActivation.AfterMove) {
                StatusConditionList.destiny_bond.apply(user, thisMove, true);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (_, _, _, _, _, _, _, _) -> {
            // TODO StatusCondition center_of_attention (volatile); muda o alvo para o Pokémon
            return true;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotSnatchable
        }
    );

    public static final Move detect = new Move(
        "Detect",
        TypeList.fighting,
        Category.Status,
        5,
        0,
        -1,
        4,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                Action userAction = Battle.findAction(thisMove, user);
                if (Battle.nextMove(userAction) == null) {
                    user.setConsecutiveProtections(0);
                    return false;
                }

                double chance = 1*(Math.pow(1.0/3.0, user.getConsecutiveProtections()));
                if (Math.random() < chance) {
                    return true;
                }

                user.setConsecutiveProtections(0);
                return false;
            }
            if (condition == MoveEffectActivation.AfterMove) {
                StatusConditionList.protection.apply(user, thisMove, true);
                user.addConsecutiveProtection();
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Eva).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails,
            InherentProperty.NotSnatchable
        }
    );

    public static final Move diamond_storm = new Move(
        "Diamond Storm",
        TypeList.rock,
        Category.Physical,
        5,
        100,
        95,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.5;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }
                user.getStat(StatName.Def).change(2, thisMove, true, true, false);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move dig = new Move(
        "Dig",
        TypeList.ground,
        Category.Physical,
        10,
        80,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, condition) -> {
            StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);

            if (condition == MoveEffectActivation.AfterMove) {
                if (chargeCondition == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.semi_invulnerable_charging_turn.apply(user, thisMove, true);

                    System.out.println(user.getName(true, true) + " burrowed its way under the ground!");
                } else {
                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }
            }
            if (condition == MoveEffectActivation.Miss) {
                user.setReadiedMove(null);
                user.endVolatileStatus(chargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss
        },
        new InherentProperty[] {
            InherentProperty.Charges
        }
    );

    public static final Move disable = new Move(
        "Disable",
        TypeList.normal,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return target.getLastUsedMove() != null &&
                       !target.getLastUsedMove().compare(MoveList.struggle);
            }
            if (condition == MoveEffectActivation.AfterMove) {
                Move disabledMove = target.getLastUsedMove().getMoveOrigin() == null ? target.getLastUsedMove() : target.getLastUsedMove().getMoveOrigin();
                StatusConditionList.move_disabled.apply(target, thisMove, 4, disabledMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move disarming_voice = new Move(
        "Disarming Voice",
        TypeList.fairy,
        Category.Special,
        15,
        40,
        -1,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move discharge = new Move(
        "Discharge",
        TypeList.electric,
        Category.Special,
        15,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.paralysis.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move dive = new Move(
        "Dive",
        TypeList.water,
        Category.Physical,
        10,
        80,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, condition) -> {
            StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);

            if (condition == MoveEffectActivation.AfterMove) {
                if (chargeCondition == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.semi_invulnerable_charging_turn.apply(user, thisMove, true);

                    System.out.println(user.getName(true, true) + " hid underwater!");
                } else {
                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }
            }
            if (condition == MoveEffectActivation.Miss) {
                user.setReadiedMove(null);
                user.endVolatileStatus(chargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss
        },
        new InherentProperty[] {
            InherentProperty.Charges
        }
    );

    public static final Move doom_desire = new Move(
        "Doom Desire",
        TypeList.steel,
        Category.Special,
        5,
        140,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (!thisMove.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                    for (Move delayedMove : Battle.delayedMoves.get(user.getTeam())) {
                        if (delayedMove.getUser() == user && (
                                delayedMove.compare(MoveList.doom_desire) ||
                                delayedMove.compare(MoveList.future_sight)
                            )) {
                            return false;
                        }
                    }
                }
                return true;
            }
            if (condition == MoveEffectActivation.AfterMove &&
                !thisMove.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));
                System.out.println(user.getName(true, true) + " chose Doom Desire as its destiny!");
            }
            if (condition == MoveEffectActivation.DelayedTurnEnd) {
                if (thisMove.getPrimaryEffectCounter() <= 0) {
                    if (!Battle.faintCheck(target, false)) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                        System.out.println(target.getName(true, true) + " took the Doom Desire attack!");
                        thisMove.addProperty(TemporaryProperty.FutureHit);
                        Battle.useMove(thisMove, user, target, false, false, false);
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }

                    int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                    Battle.delayedMoves.get(user.getTeam()).set(index, new Move(MoveList._placeholder_, user));
                }
            }
            return null;
        },
        EffectTarget.Target,
        3,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.DelayedTurnEnd
        },
        new MoveType[] {
            MoveType.Delayed
        }
    );

    public static final Move double_edge = new Move(
        "Double-Edge",
        TypeList.normal,
        Category.Physical,
        15,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            int recoilDamage = damage.amount/3;
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            String message = user.getName(true, true) + " was damaged by the recoil!";
            Damage.indirectDamage(user, user, recoilDamage, DamageSource.Recoil, thisMove, message, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.Recoil
        }
    );

    public static final Move double_hit = new Move(
        "Double Hit",
        TypeList.normal,
        Category.Physical,
        10,
        35, 140,
        90,
        1,
        true,
        0,
        new int[] {2},
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move double_iron_bash = new Move(
        "Double Iron Bash",
        TypeList.steel,
        Category.Physical,
        5,
        50, 180,
        100,
        1,
        true,
        0,
        new int[] {2},
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMoveMultiHit
        },
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move double_kick = new Move(
        "Double Kick",
        TypeList.fighting,
        Category.Physical,
        30,
        30, 100,
        100,
        1,
        true,
        0,
        new int[] {2},
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move double_team = new Move(
        "Double Team",
        TypeList.normal,
        Category.Status,
        15,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Eva).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move dragon_ascent = new Move(
        "Dragon Ascent",
        TypeList.flying,
        Category.Physical,
        5,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.Def).change(-1, thisMove, true, true, false);
            user.getStat(StatName.SpD).change(-1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move dragon_breath = new Move(
        "Dragon Breath",
        TypeList.dragon,
        Category.Special,
        20,
        60,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.paralysis.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move dragon_claw = new Move(
        "Dragon Claw",
        TypeList.dragon,
        Category.Physical,
        15,
        80,
        100,
        2,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move dragon_dance = new Move(
        "Dragon Dance",
        TypeList.dragon,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Atk).change(1, thisMove, true, true, false);
            user.getStat(StatName.Spe).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move dragon_energy = new Move(
        "Dragon Energy",
        TypeList.dragon,
        Category.Special,
        5,
        150,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        (_, user, _, _, _, _, _, _) -> {
            return Math.max(150.0*user.getCurrentHP()/user.getHP(), 1.0);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move dragon_pulse = new Move(
        "Dragon Pulse",
        TypeList.dragon,
        Category.Special,
        10,
        85,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Pulse
        }
    );

    public static final Move dragon_rush = new Move(
        "Dragon Rush",
        TypeList.dragon,
        Category.Physical,
        10,
        100,
        75,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.2;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move dragon_tail = new Move(
        "Dragon Tail",
        TypeList.dragon,
        Category.Physical,
        10,
        60,
        90,
        1,
        true,
        -6,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            if (Arrays.asList(target.getAbility().getConditions()).contains(AbilityActivation.TryForceSwitch) &&
                !(boolean) target.getAbility().activate(target, user, thisMove, null, damage, null, null, 0, AbilityActivation.TryForceSwitch)) {
                return null;
            }

            for (StatusCondition statusCondition : target.getVolatileStatusList()) {
                if (Arrays.asList(statusCondition.getActivation()).contains(StatusActivation.TryForceSwitch) &&
                    !(boolean) statusCondition.activate(target, user, thisMove, damage, true, StatusActivation.TryForceSwitch)) {
                    return null;
                }
            }

            Action moveLocation = Battle.findAction(thisMove, user);
            Move switchMove = new Move(MoveList._switch_, target);
            switchMove.addProperty(TemporaryProperty._Forced_);
            Battle.addAction(new Action(switchMove, target, target), moveLocation);

            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.CopycatFails
        }
    );

    public static final Move drain_punch = new Move(
        "Drain Punch",
        TypeList.fighting,
        Category.Physical,
        10,
        75,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            if (user.getCurrentHP() < user.getHP()) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }

                Damage.heal(user, thisMove, Integer.max(damage.amount/2, 1), true, false);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move draining_kiss = new Move(
        "Draining Kiss",
        TypeList.fairy,
        Category.Special,
        10,
        50,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            if (user.getCurrentHP() < user.getHP()) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }

                Damage.heal(user, thisMove, Integer.max((int) (damage.amount*0.75), 1), true, false);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move dream_eater = new Move(
        "Dream Eater",
        TypeList.psychic,
        Category.Special,
        15,
        100,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (target.getNonVolatileStatus().compare(StatusConditionList.sleep)) {
                    return true;
                }
                return false;
            }

            if (condition == MoveEffectActivation.AfterMove) {
                if (user.getCurrentHP() < user.getHP()) {
                    if (Battle.faintCheck(target, false)) {
                        System.out.println();
                    }

                    Damage.heal(user, thisMove, Integer.max(damage.amount/2, 1), true, false);
                }
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move drill_peck = new Move(
        "Drill Peck",
        TypeList.flying,
        Category.Physical,
        20,
        80,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move drum_beating = new Move(
        "Drum Beating",
        TypeList.grass,
        Category.Physical,
        10,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Spe).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move dual_wingbeat = new Move(
        "Dual Wingbeat",
        TypeList.flying,
        Category.Physical,
        10,
        40, 100,
        90,
        1,
        true,
        0,
        new int[] {2},
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move dynamax_cannon = new Move(
        "Dynamax Cannon",
        TypeList.dragon,
        Category.Special,
        5,
        100,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            if (user.getAbility().compare(AbilityList.darkest_day)) {
                return MoveTarget.AllOpponents;
            }
            return MoveTarget.Normal;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallMoveTarget
        }
    );

    public static final Move dynamic_punch = new Move(
        "Dynamic Punch",
        TypeList.fighting,
        Category.Physical,
        5,
        100,
        50,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            StatusConditionList.confusion.apply(target, thisMove, (int) Math.ceil(Math.random()*4)+1, true); // 2-5 turnos
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move earth_power = new Move(
        "Earth Power",
        TypeList.ground,
        Category.Special,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.SpD).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move earthquake = new Move(
        "Earthquake",
        TypeList.ground,
        Category.Physical,
        10,
        100,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move echoed_voice = new Move(
        "Echoed Voice",
        TypeList.normal,
        Category.Special,
        15,
        40,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, _, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove || condition == MoveEffectActivation.Miss) {
                FieldConditionList.echoed_voice.apply(thisMove, 1, true);
            }

            if (condition == MoveEffectActivation.CallPower) {
                double power = 40.0;

                for (FieldCondition fieldCondition : Battle.generalField) {
                    if (fieldCondition.compare(FieldConditionList.echoed_voice)) {
                        power *= fieldCondition.getCounter();
                        break;
                    }
                }

                return power;
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss,
            MoveEffectActivation.CallPower
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move electric_terrain = new Move(
        "Electric Terrain",
        TypeList.electric,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.AllFields,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.electric_terrain.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move electro_drift = new Move(
        "Electro Drift",
        TypeList.electric,
        Category.Special,
        5,
        100,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            double effectivenessMultiplier = 1;

            effectivenessMultiplier *= Damage.superEffective(thisMove, target);
            effectivenessMultiplier /= Damage.notVeryEffective(thisMove, target);

            if (effectivenessMultiplier > 1) {
                return 5461.0/4096.0;
            }
            return 1.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.DamageCalc
        }
    );

    public static final Move electroweb = new Move(
        "Electroweb",
        TypeList.electric,
        Category.Special,
        15,
        55,
        95,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Spe).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move ember = new Move(
        "Ember",
        TypeList.fire,
        Category.Special,
        25,
        40,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move encore = new Move(
        "Encore",
        TypeList.normal,
        Category.Status,
        5,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return target.getLastUsedMove() != null &&
                       target.getLastUsedMove().getCurrentPP() > 0 &&
                       !target.getLastUsedMove().hasInherentProperty(InherentProperty.EncoreFails);
            }
            if (condition == MoveEffectActivation.AfterMove) {
                StatusConditionList.encore.apply(target, thisMove, 3, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute,
            InherentProperty.EncoreFails
        }
    );

    public static final Move endeavor = new Move(
        "Endeavor",
        TypeList.normal,
        Category.Physical,
        5,
        0, 160,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            int moveDamage = target.getCurrentHP() - user.getCurrentHP();
            if (moveDamage < 0) {
                return 0;
            }
            return moveDamage;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.FixedDamage
        }
    );

    public static final Move endure = new Move(
        "Endure",
        TypeList.normal,
        Category.Status,
        10,
        0,
        -1,
        4,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                Action userAction = Battle.findAction(thisMove, user);
                if (Battle.nextMove(userAction) == null) {
                    user.setConsecutiveProtections(0);
                    return false;
                }

                double chance = 1*(Math.pow(1.0/3.0, user.getConsecutiveProtections()));
                if (Math.random() < chance) {
                    return true;
                }

                user.setConsecutiveProtections(0);
                return false;
            }
            if (condition == MoveEffectActivation.AfterMove) {
                StatusConditionList.endure.apply(user, thisMove, true);
                user.addConsecutiveProtection();
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails,
            InherentProperty.NotSnatchable
        }
    );

    public static final Move energy_ball = new Move(
        "Energy Ball",
        TypeList.grass,
        Category.Special,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.SpD).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.BallBomb
        }
    );

    public static final Move eruption = new Move(
        "Eruption",
        TypeList.fire,
        Category.Special,
        5,
        150,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        (_, user, _, _, _, _, _, _) -> {
            return Math.max(150.0*user.getCurrentHP()/user.getHP(), 1.0);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move eternabeam = new Move(
        "Eternabeam",
        TypeList.dragon,
        Category.Special,
        5,
        160,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                boolean willChangeForm = user.compare(PokemonList.eternatus, true) && user.compareWithForm(PokemonList.eternatus) &&
                                         user.getItem().compare(ItemList.eternal_wishing_star) && !user.getItem().wasActivated();

                if (!willChangeForm &&
                    !user.getAbility().compare(AbilityList.darkest_day)) {
                    StatusCondition rechargeCondition = user.getVolatileStatus(StatusConditionList.recharging_turn);

                    if (rechargeCondition == null) {
                        user.setReadiedMove(thisMove);
                        StatusConditionList.recharging_turn.apply(user, thisMove, true);
                    } else {
                        user.setReadiedMove(null);
                        user.endVolatileStatus(rechargeCondition, true);
                    }
                } else if (willChangeForm ||
                           user.getAbility().compare(AbilityList.darkest_day)) {
                    StatusCondition unusableMoveCondition = user.getVolatileStatus(StatusConditionList.unusable_move_turn);

                    if (unusableMoveCondition == null) {
                        StatusConditionList.unusable_move_turn.apply(user, thisMove, 1, thisMove, true);
                    } else {
                        user.endVolatileStatus(unusableMoveCondition, true);
                    }
                }

                if (willChangeForm) {
                    System.out.println("\nThe Eternal Wishing Star is sustaining " + user.getName(true, false) + "'s original form!");
                    user.changeForm("Eternamax");
                    user.getItem().setActivated(true);
                }
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.Recharges
        }
    );

    public static final Move expanding_force = new Move(
        "Expanding Force",
        TypeList.psychic,
        Category.Special,
        10,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, condition) -> {
            boolean terrainActive = Battle.getTerrain().compare(FieldConditionList.psychic_terrain) && user.isGrounded(null);

            if (condition == MoveEffectActivation.CallPower) {
                if (terrainActive) {
                    return 120.0;
                }
                return 80.0;
            }
            if (condition == MoveEffectActivation.CallMoveTarget) {
                if (terrainActive) {
                    return MoveTarget.AllOpponents;
                }
                return MoveTarget.Normal;
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower,
            MoveEffectActivation.CallMoveTarget
        }
    );

    public static final Move explosion = new Move(
        "Explosion",
        TypeList.normal,
        Category.Physical,
        5,
        250,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        (_, user, target, _, _, _, _, _) -> {
            if (!user.getAbility().compare(AbilityList.damp) &&
                !target.getAbility().compare(AbilityList.damp)) {
                user.setCurrentHP(0);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.BeforeMove,
            MoveEffectActivation.Miss
        }
    );

    public static final Move extrasensory = new Move(
        "Extrasensory",
        TypeList.psychic,
        Category.Special,
        20,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move extreme_speed = new Move(
        "Extreme Speed",
        TypeList.normal,
        Category.Physical,
        5,
        80,
        100,
        1,
        true,
        2,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move facade = new Move(
        "Facade",
        TypeList.normal,
        Category.Physical,
        20,
        70,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            if (user.getNonVolatileStatus().compare(StatusConditionList.burn) ||
                user.getNonVolatileStatus().compare(StatusConditionList.paralysis) ||
                user.getNonVolatileStatus().compare(StatusConditionList.poison) ||
                user.getNonVolatileStatus().compare(StatusConditionList.bad_poison) ||
                user.getNonVolatileStatus().compare(StatusConditionList.frostbite)) {
                return 140.0;
            }
            return 70.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move fairy_wind = new Move(
        "Fairy Wind",
        TypeList.fairy,
        Category.Special,
        30,
        40,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move fake_out = new Move(
        "Fake Out",
        TypeList.normal,
        Category.Physical,
        10,
        40,
        100,
        1,
        true,
        3,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (user.getTurnsOnField() < 2) {
                    return true;
                }
            }
            return false;
        },
        EffectTarget.User,
        (thisMove, _, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move fake_tears = new Move(
        "Fake Tears",
        TypeList.dark,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.SpD).change(-2, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move false_swipe = new Move(
        "False Swipe",
        TypeList.normal,
        Category.Physical,
        40,
        40,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, _, target, _, damage, _, _, _) -> {
            int dealtDamage = damage.amount;
            if (target.getCurrentHP() <= dealtDamage) {
                dealtDamage = target.getCurrentHP() - 1;
            }
            return dealtDamage;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.FinalDamage
        }
    );

    public static final Move feather_dance = new Move(
        "Feather Dance",
        TypeList.flying,
        Category.Status,
        15,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Atk).change(-2, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move feint = new Move(
        "Feint",
        TypeList.normal,
        Category.Physical,
        10,
        30,
        100,
        1,
        false,
        2,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty.IgnoresProtection,
            InherentProperty.BreaksProtection
        }
    );

    public static final Move feint_attack = new Move(
        "Feint Attack",
        TypeList.dark,
        Category.Physical,
        20,
        60,
        -1,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move fell_stinger = new Move(
        "Fell Stinger",
        TypeList.bug,
        Category.Physical,
        25,
        50,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
                user.getStat(StatName.Atk).change(3, thisMove, true, true, false);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move fiery_wrath = new Move(
        "Fiery Wrath",
        TypeList.dark,
        Category.Physical,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.2;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move final_gambit = new Move(
        "Final Gambit",
        TypeList.fighting,
        Category.Special,
        5,
        0, 180,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            int moveDamage = user.getCurrentHP();
            user.setCurrentHP(0);
            return moveDamage;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.FixedDamage
        }
    );

    public static final Move fire_blast = new Move(
        "Fire Blast",
        TypeList.fire,
        Category.Special,
        5,
        110,
        85,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move fire_fang = new Move(
        "Fire Fang",
        TypeList.fire,
        Category.Physical,
        15,
        65,
        95,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance1 = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance1 *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance1) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }


            double chance2 = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance2 *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance2) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move fire_punch = new Move(
        "Fire Punch",
        TypeList.fire,
        Category.Physical,
        15,
        75,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move fire_spin = new Move(
        "Fire Spin",
        TypeList.fire,
        Category.Special,
        15,
        35,
        85,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (target.getVolatileStatus(StatusConditionList.bind) == null) {
                StatusConditionList.bind.apply(target, thisMove, (int) Math.ceil(Math.random()*2)+3, user, true); // 4-5 turnos
                if (!Battle.faintCheck(target, false)) {
                    System.out.println(target.getName(true, true) + " became trapped in the fiery vortex!");
                }
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move fissure = new Move(
        "Fissure",
        TypeList.ground,
        Category.Physical,
        5,
        0, 180,
        30,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return user.getLevel() >= target.getLevel();
            }
            if (condition == MoveEffectActivation.OneHitKOAccuracy) {
                double hitChance = (user.getLevel() - target.getLevel() + 30);
                return Math.random() < hitChance/100.0;
            }
            if (condition == MoveEffectActivation.FixedDamage) {
                System.out.println("It's a one-hit KO!");
                return target.getCurrentHP();
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.OneHitKOAccuracy,
            MoveEffectActivation.FixedDamage
        },
        new InherentProperty[] {
            InherentProperty.OneHitKO
        }
    );

    public static final Move flail = new Move(
        "Flail",
        TypeList.normal,
        Category.Physical,
        15,
        0, 160,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            if (user.getCurrentHP() < user.getHP() * 0.042) { // HP < 4.2%
                return 200.0;
            } else if (user.getCurrentHP() < user.getHP() * 0.104) { // 4.2% ≤ HP < 10.4%
                return 150.0;
            } else if (user.getCurrentHP() < user.getHP() * 0.208) { // 10.4% ≤ HP < 20.8%
                return 100.0;
            } else if (user.getCurrentHP() < user.getHP() * 0.354) { // 20.8% ≤ HP < 35.4%
                return 80.0;
            } else if (user.getCurrentHP() < user.getHP() * 0.688) { // 35.4% ≤ HP < 68.8%
                return 40.0;
            } else { // HP ≥ 68.8%
                return 20.0;
            }
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move flame_charge = new Move(
        "Flame Charge",
        TypeList.fire,
        Category.Physical,
        20,
        50,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Spe).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move flame_wheel = new Move(
        "Flame Wheel",
        TypeList.fire,
        Category.Physical,
        25,
        60,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.ThawsUser
        }
    );

    public static final Move flamethrower = new Move(
        "Flamethrower",
        TypeList.fire,
        Category.Special,
        15,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move flare_blitz = new Move(
        "Flare Blitz",
        TypeList.fire,
        Category.Physical,
        15,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            int recoilDamage = damage.amount/3;
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            String message = user.getName(true, true) + " was damaged by the recoil!";
            Damage.indirectDamage(user, user, recoilDamage, DamageSource.Recoil, thisMove, message, false);
            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.ThawsUser,
            InherentProperty.Recoil
        }
    );

    public static final Move flash_cannon = new Move(
        "Flash Cannon",
        TypeList.steel,
        Category.Special,
        10,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.SpD).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move flash_freeze = new Move( // fanmade
        "Flash Freeze",
        TypeList.ice,
        Category.Status,
        15,
        0,
        85,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.frostbite.apply(pokemon, thisMove, showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move flatter = new Move(
        "Flatter",
        TypeList.dark,
        Category.Status,
        15,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.SpA).change(2, thisMove, false, true, false);
            StatusConditionList.confusion.apply(target, thisMove, (int) Math.ceil(Math.random()*4)+1, true); // 2-5 turnos
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move fleur_cannon = new Move(
        "Fleur Cannon",
        TypeList.fairy,
        Category.Special,
        5,
        130,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.SpA).change(-2, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move fling = new Move(
        "Fling",
        TypeList.dark,
        Category.Physical,
        10,
        0,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (user.getItem().compare(ItemList.none) || user.getItem().cantFling()) {
                    return false;
                }
                if (user.getItem().heldByValidUser(true) && user.getItem().isTetheredToValidUser()) {
                    return false;
                }
                return true;
            }
            if (condition == MoveEffectActivation.BeforeMove) {
                System.out.println(user.getName(true, true) + " flung its " + user.getItem().getName());
            }
            if (condition == MoveEffectActivation.CallPower) {
                return user.getItem().getFlingPower();
            }
            if (condition == MoveEffectActivation.AfterMove) {
                if (!Battle.faintCheck(target, false)) {
                    if (user.getItem().getType() == ItemType.Berry) {
                        user.getItem().activate(user, target, user, thisMove, null, ItemActivation.ForceUse);

                        if (user.getItem().shouldActivate(ItemActivation.Consumed)) {
                            user.getItem().activate(user, target, user, thisMove, null, ItemActivation.Consumed);
                        }
                    } else {
                        user.getItem().activateFlingEffect(user, target, user, thisMove);

                        user.getItem().setConsumed(true);
                        user.getItem().consume(true, false);
                    }
                } else {
                    user.getItem().setConsumed(true);
                    user.getItem().consume(true, false);
                }
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.BeforeMove,
            MoveEffectActivation.CallPower,
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move flip_turn = new Move(
        "Flip Turn",
        TypeList.water,
        Category.Physical,
        20,
        60,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            Action moveLocation = Battle.findAction(thisMove, user);
            Move switchMove = new Move(MoveList._switch_, user);
            switchMove.addProperty(TemporaryProperty._Pivot_);
            Battle.addAction(new Action(switchMove, user, user), moveLocation);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move flower_trick = new Move(
        "Flower Trick",
        TypeList.grass,
        Category.Physical,
        10,
        70,
        -1,
        10, // garantido
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move fly = new Move(
        "Fly",
        TypeList.flying,
        Category.Physical,
        15,
        90,
        95,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, condition) -> {
            StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);

            if (condition == MoveEffectActivation.AfterMove) {
                if (chargeCondition == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.semi_invulnerable_charging_turn.apply(user, thisMove, true);

                    System.out.println(user.getName(true, true) + " flew up high!");
                } else {
                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }
            }
            if (condition == MoveEffectActivation.Miss) {
                user.setReadiedMove(null);
                user.endVolatileStatus(chargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss
        },
        new InherentProperty[] {
            InherentProperty.Charges,
            InherentProperty.GravityUnusable
        }
    );

    public static final Move flying_press = new Move(
        "Flying Press",
        TypeList.fighting,
        Category.Physical,
        10,
        100, 170,
        95,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, _, _, _, _, _, _, _) -> {
            return new Type[] {TypeList.fighting, TypeList.flying};
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.EffectivenessCalc
        },
        new InherentProperty[] {
            InherentProperty.GravityUnusable
        }
    );

    public static final Move focus_blast = new Move(
        "Focus Blast",
        TypeList.fighting,
        Category.Special,
        5,
        120,
        70,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.SpD).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.BallBomb
        }
    );

    public static final Move focus_energy = new Move(
        "Focus Energy",
        TypeList.normal,
        Category.Status,
        30,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusConditionList.pumped.apply(user, thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Acc).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move focus_punch = new Move(
        "Focus Punch",
        TypeList.fighting,
        Category.Physical,
        20,
        150,
        100,
        1,
        true,
        -3,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            StatusConditionList.focus.apply(user, thisMove, true);
            System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TurnStart
        },
        new MoveType[] {
            MoveType.Punch
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails
        }
    );

    public static final Move follow_me = new Move(
        "Follow Me",
        TypeList.normal,
        Category.Status,
        20,
        0,
        -1,
        2,
        MoveTarget.User,
        (_, user, _, _, _, _, _, condition) -> {
            System.out.println(user.getName(true, true) + " became the center of attention!");
            // TODO StatusCondition center_of_attention (volatile); muda o alvo para o Pokémon
            // sem uso em singles
            if (condition == MoveEffectActivation.TryUse) {
                return false;
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails,
            InherentProperty.NotSnatchable
        }
    );

    public static final Move force_palm = new Move(
        "Force Palm",
        TypeList.fighting,
        Category.Physical,
        10,
        60,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.paralysis.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move foul_play = new Move(
        "Foul Play",
        TypeList.dark,
        Category.Physical,
        15,
        95,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            return target.getStat(StatName.Atk);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallAttackingStat
        }
    );

    public static final Move freeze_dry = new Move(
        "Freeze-Dry",
        TypeList.ice,
        Category.Special,
        10,
        70,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, _, _, type, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.CallSuperEffective) {
                if (type.compare(TypeList.water)) {
                    List<Type> newWeaknesses = new ArrayList<>(Arrays.asList(type.getSuperEffective(null, true)));

                    boolean alreadyWeak = false;
                    for (Type weakness : newWeaknesses) {
                        if (weakness.compare(thisMove.getType(false))) {
                            alreadyWeak = true;
                            break;
                        }
                    }

                    if (!alreadyWeak) {
                        newWeaknesses.add(thisMove.getType(false));
                    }

                    return newWeaknesses.toArray(new Type[0]);
                }

                return type.getSuperEffective(null, true);
            }

            if (condition == MoveEffectActivation.CallNotVeryEffective) {
                if (type.compare(TypeList.water)) {
                    List<Type> newResistances = new ArrayList<>(Arrays.asList(type.getNotVeryEffective(null, true)));

                    for (Type resistance : newResistances) {
                        if (resistance.compare(thisMove.getType(false))) {
                            newResistances.remove(resistance);
                            break;
                        }
                    }

                    return newResistances.toArray(new Type[0]);
                }

                return type.getNotVeryEffective(null, true);
            }

            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                double chance = 0.1;
                if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                    chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                }

                if (Math.random() < chance) {
                    StatusConditionList.frostbite.apply(target, thisMove, true);
                }
            }

            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallSuperEffective,
            MoveEffectActivation.CallNotVeryEffective,
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move freeze_shock = new Move(
        "Freeze Shock",
        TypeList.ice,
        Category.Physical,
        5,
        140,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, condition) -> {
            StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.charging_turn);

            if (condition == MoveEffectActivation.AfterMove) {
                if (chargeCondition == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.charging_turn.apply(user, thisMove, true);

                    System.out.println(user.getName(true, true) + " became cloaked in a freezing light!");
                } else {
                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }
            }
            if (condition == MoveEffectActivation.Miss) {
                user.setReadiedMove(null);
                user.endVolatileStatus(chargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.charging_turn);

                if (chargeCondition == null) { // ativa depois do ataque, quando perde chargeCondition
                    double chance = 0.3;
                    if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                        chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                    }

                    if (Math.random() < chance) {
                        StatusConditionList.paralysis.apply(target, thisMove, true);
                    }
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss
        },
        new InherentProperty[] {
            InherentProperty.Charges
        }
    );

    public static final Move freezing_glare = new Move(
        "Freezing Glare",
        TypeList.psychic,
        Category.Special,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.frostbite.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move frost_breath = new Move(
        "Frost Breath",
        TypeList.ice,
        Category.Special,
        10,
        60,
        90,
        10, // garantido
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move fury_attack = new Move(
        "Fury Attack",
        TypeList.normal,
        Category.Physical,
        20,
        15, 100,
        85,
        1,
        true,
        0,
        new int[] {2, 5},
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move fury_cutter = new Move(
        "Fury Cutter",
        TypeList.bug,
        Category.Physical,
        20,
        40,
        95,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, _, _, _, _, _, _, _) -> {
            double power = 40.0;
            for (int i = 0; i < thisMove.getConsecutiveUses(); i++) {
                power *= 2;

                if (power == 160.0) {
                    break;
                }
            }
            return power;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        },
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move fury_swipes = new Move(
        "Fury Swipes",
        TypeList.normal,
        Category.Physical,
        15,
        18, 100,
        80,
        1,
        true,
        0,
        new int[] {2, 5},
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move fusion_bolt = new Move(
        "Fusion Bolt",
        TypeList.electric,
        Category.Physical,
        5,
        100,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, _, _, _, _, _, _) -> {
            if (Battle.lastUsedMove != null &&
                Battle.lastUsedMove.compare(MoveList.fusion_flare)) {
                return 200.0;
            }
            return 100.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move fusion_flare = new Move(
        "Fusion Flare",
        TypeList.fire,
        Category.Special,
        5,
        100,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, _, _, _, _, _, _) -> {
            if (Battle.lastUsedMove != null &&
                Battle.lastUsedMove.compare(MoveList.fusion_bolt)) {
                return 200.0;
            }
            return 100.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        },
        new InherentProperty[] {
            InherentProperty.ThawsUser
        }
    );

    public static final Move future_sight = new Move(
        "Future Sight",
        TypeList.psychic,
        Category.Special,
        10,
        120,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (!thisMove.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                    for (Move delayedMove : Battle.delayedMoves.get(user.getTeam())) {
                        if (delayedMove.getUser() == user && (
                                delayedMove.compare(MoveList.doom_desire) ||
                                delayedMove.compare(MoveList.future_sight)
                            )) {
                            return false;
                        }
                    }
                }
                return true;
            }
            if (condition == MoveEffectActivation.AfterMove &&
                !thisMove.getTemporaryProperties().contains(TemporaryProperty.FutureHit)) {
                Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));
                System.out.println(user.getName(true, true) + " foresaw an attack!");
            }
            if (condition == MoveEffectActivation.DelayedTurnEnd) {
                if (thisMove.getPrimaryEffectCounter() <= 0) {
                    if (!Battle.faintCheck(target, false)) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                        System.out.println(target.getName(true, true) + " took the Future Sight attack!");
                        thisMove.addProperty(TemporaryProperty.FutureHit);
                        Battle.useMove(thisMove, user, target, false, false, false);
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }

                    int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                    Battle.delayedMoves.get(user.getTeam()).set(index, new Move(MoveList._placeholder_, user));
                }
            }
            return null;
        },
        EffectTarget.Target,
        3,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.DelayedTurnEnd
        },
        new MoveType[] {
            MoveType.Delayed
        }
    );

    public static final Move gastro_acid = new Move(
        "Gastro Acid",
        TypeList.poison,
        Category.Status,
        10,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            if (!target.getAbility().isNotSuppressable()) {
                StatusConditionList.suppressed_ability.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move geomancy = new Move(
        "Geomancy",
        TypeList.fairy,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, condition) -> {
            StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.charging_turn);

            if (condition == MoveEffectActivation.AfterMove) {
                if (chargeCondition == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.charging_turn.apply(user, thisMove, true);

                    System.out.println(user.getName(true, true) + " is absorbing power!");
                } else {
                    user.getStat(StatName.SpA).change(2, thisMove, true, true, false);
                    user.getStat(StatName.SpD).change(2, thisMove, true, true, false);
                    user.getStat(StatName.Spe).change(2, thisMove, true, true, false);

                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }
            }
            if (condition == MoveEffectActivation.Miss) {
                user.setReadiedMove(null);
                user.endVolatileStatus(chargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            boolean boostAtk = user.getStat(StatName.Atk).change(1, thisMove, true, false, true);
            boolean boostDef = user.getStat(StatName.Def).change(1, thisMove, true, false, true);
            boolean boostSpA = user.getStat(StatName.SpA).change(1, thisMove, true, false, true);
            boolean boostSpD = user.getStat(StatName.SpD).change(1, thisMove, true, false, true);
            boolean boostSpe = user.getStat(StatName.Spe).change(1, thisMove, true, false, true);

            if (boostAtk || boostDef || boostSpA || boostSpD || boostSpe) {
                GeneralMessages.stat_change.print("+1 many Z", user);
                return true;
            }
            return false;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.Charges,
            InherentProperty.NotSnatchable
        }
    );

    public static final Move giga_drain = new Move(
        "Giga Drain",
        TypeList.grass,
        Category.Special,
        10,
        75,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            if (user.getCurrentHP() < user.getHP()) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }

                Damage.heal(user, thisMove, Integer.max(damage.amount/2, 1), true, false);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move giga_impact = new Move(
        "Giga Impact",
        TypeList.normal,
        Category.Physical,
        5,
        150,
        90,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusCondition rechargeCondition = user.getVolatileStatus(StatusConditionList.recharging_turn);

            if (rechargeCondition == null) {
                user.setReadiedMove(thisMove);
                StatusConditionList.recharging_turn.apply(user, thisMove, true);
            } else {
                user.setReadiedMove(null);
                user.endVolatileStatus(rechargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.Recharges
        }
    );

    public static final Move glacial_lance = new Move(
        "Glacial Lance",
        TypeList.ice,
        Category.Physical,
        5,
        120,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move glaciate = new Move(
        "Glaciate",
        TypeList.ice,
        Category.Special,
        10,
        65,
        95,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Spe).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move glare = new Move(
        "Glare",
        TypeList.normal,
        Category.Status,
        30,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.paralysis.apply(pokemon, thisMove, showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move grass_knot = new Move(
        "Grass Knot",
        TypeList.grass,
        Category.Special,
        20,
        0, 160,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            if (target.getWeight(thisMove) < 10) {
                return 20.0;
            } else if (target.getWeight(thisMove) < 25) {
                return 40.0;
            } else if (target.getWeight(thisMove) < 50) {
                return 60.0;
            } else if (target.getWeight(thisMove) < 100) {
                return 80.0;
            } else if (target.getWeight(thisMove) < 200) {
                return 100.0;
            } else {
                return 120.0;
            }
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move grassy_terrain = new Move(
        "Grassy Terrain",
        TypeList.grass,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.AllFields,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.grassy_terrain.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move gravity = new Move(
        "Gravity",
        TypeList.psychic,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.AllFields,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.gravity.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move growl = new Move(
        "Growl",
        TypeList.normal,
        Category.Status,
        40,
        0,
        100,
        0,
        MoveTarget.AllOpponents,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Atk).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move growth = new Move(
        "Growth",
        TypeList.normal,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            int stages = 1;
            if (Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land)) {
                stages = 2;
            }

            user.getStat(StatName.Atk).change(stages, thisMove, true, true, false);
            user.getStat(StatName.SpA).change(stages, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move guard_split = new Move(
        "Guard Split",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            int newDef = (user.getStat(StatName.Def).getValue() + target.getStat(StatName.Def).getValue())/2;
            int newSpD = (user.getStat(StatName.SpD).getValue() + target.getStat(StatName.SpD).getValue())/2;

            // usa modificadores como Assault Vest e Eviolite, mas não stat changes

            user.getStat(StatName.Def).setValue(newDef);
            user.getStat(StatName.SpD).setValue(newSpD);

            target.getStat(StatName.Def).setValue(newDef);
            target.getStat(StatName.SpD).setValue(newSpD);

            System.out.println(user.getName(true, true) + " shared its guard with the target!");

            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move guard_swap = new Move(
        "Guard Swap",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            int userDefStages = user.getStat(StatName.Def).getTrueStages();
            int userSpDStages = user.getStat(StatName.SpD).getTrueStages();

            int targetDefStages = target.getStat(StatName.Def).getTrueStages();
            int targetSpDStages = target.getStat(StatName.SpD).getTrueStages();

            user.getStat(StatName.Def).setStages(targetDefStages);
            user.getStat(StatName.SpD).setStages(targetSpDStages);

            target.getStat(StatName.Def).setStages(userDefStages);
            target.getStat(StatName.SpD).setStages(userSpDStages);

            System.out.println(user.getName(true, true) + " switched all changes to its Defense and Sp. Def with its target!");

            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move guardian_flame = new Move( // fanmade
        "Guardian Flame",
        TypeList.fire,
        Category.Special,
        10,
        70,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            int stages = target.getDamageDealt()/100;

            target.getStat(StatName.Def).change(-stages, thisMove, false, true, false);
            target.getStat(StatName.SpD).change(-stages, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move guillotine = new Move(
        "Guillotine",
        TypeList.normal,
        Category.Physical,
        5,
        0, 180,
        30,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return user.getLevel() >= target.getLevel();
            }
            if (condition == MoveEffectActivation.OneHitKOAccuracy) {
                double hitChance = (user.getLevel() - target.getLevel() + 30);
                return Math.random() < hitChance/100.0;
            }
            if (condition == MoveEffectActivation.FixedDamage) {
                System.out.println("It's a one-hit KO!");
                return target.getCurrentHP();
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.OneHitKOAccuracy,
            MoveEffectActivation.FixedDamage
        },
        new InherentProperty[] {
            InherentProperty.OneHitKO
        }
    );

    public static final Move gust = new Move(
        "Gust",
        TypeList.flying,
        Category.Special,
        35,
        40,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move gyro_ball = new Move(
        "Gyro Ball",
        TypeList.steel,
        Category.Physical,
        5,
        0, 160,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            int targetSpeed = target.getStat(StatName.Spe).getEffectiveValue(user, null, false, null);
            int userSpeed = user.getStat(StatName.Spe).getEffectiveValue(target, null, false, null);

            return Math.min(25.0*targetSpeed/userSpeed + 1, 150.0);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        },
        new MoveType[] {
            MoveType.BallBomb
        }
    );

    public static final Move hammer_arm = new Move(
        "Hammer Arm",
        TypeList.fighting,
        Category.Physical,
        10,
        100,
        90,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.Spe).change(-1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move harden = new Move(
        "Harden",
        TypeList.normal,
        Category.Status,
        30,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Def).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move haze = new Move(
        "Haze",
        TypeList.ice,
        Category.Status,
        30,
        0,
        -1,
        0,
        MoveTarget.AllFields,
        (_, _, _, _, _, _, _, _) -> {
            for (Stat stat : Battle.yourActivePokemon.getStats()) {
                stat.setStages(0);
            }
            for (Stat stat : Battle.opponentActivePokemon.getStats()) {
                stat.setStages(0);
            }
            System.out.println("All stat changes were eliminated!");
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return Damage.heal(user, thisMove, user.getHP(), true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move head_smash = new Move(
        "Head Smash",
        TypeList.rock,
        Category.Physical,
        5,
        150,
        80,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            int recoilDamage = damage.amount/2;
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            String message = user.getName(true, true) + " was damaged by the recoil!";
            Damage.indirectDamage(user, user, recoilDamage, DamageSource.Recoil, thisMove, message, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.Recoil
        }
    );

    public static final Move headbutt = new Move(
        "Headbutt",
        TypeList.normal,
        Category.Physical,
        15,
        70,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move headlong_rush = new Move(
        "Headlong Rush",
        TypeList.ground,
        Category.Physical,
        5,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.Def).change(-1, thisMove, true, true, false);
            user.getStat(StatName.SpD).change(-1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move heal_bell = new Move(
        "Heal Bell",
        TypeList.normal,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.UserAndAlly,
        (_, user, _, _, _, _, _, _) -> {
            System.out.println("A bell chimed!");
            for (Pokemon pokemon : Battle.teams[user.getTeam()]) {
                if (pokemon != null) {
                    pokemon.endNonVolatileStatus(true);
                }
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return Damage.heal(user, thisMove, user.getHP(), true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move heal_pulse = new Move(
        "Heal Pulse",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            double healAmount = 0.5;
            if (user.getAbility().compare(AbilityList.mega_launcher) &&
                user.getAbility().shouldActivate(null)) {
                healAmount = 0.75;
            }

            int healedDamage = Integer.max((int) Math.floor(target.getHP() * healAmount), 1);
            Damage.heal(target, thisMove, healedDamage, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Pulse
        }
    );

    public static final Move healing_wish = new Move(
        "Healing Wish",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                boolean teamFainted = true;
                for (Pokemon pokemon : Battle.teams[user.getTeam()]) {
                    if (pokemon != null &&
                        pokemon != user &&
                        !Battle.faintCheck(pokemon, false)) {
                        teamFainted = false;
                    }
                }

                if (!teamFainted) {
                    Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));
                    System.out.println(user.getName(true, true) + " made its final wish!");

                    user.setCurrentHP(0);

                    Battle.faintCheck(user, true);
                }
            }
            if (condition == MoveEffectActivation.DelayedSwitch) {
                if (target.getCurrentHP() >= target.getHP() &&
                    target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                    return null;
                }

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

                System.out.println("The healing wish came true for " + target.getName(true, false) + "!");

                if (target.getCurrentHP() < target.getHP()) {
                    Damage.heal(target, thisMove, target.getHP(), true, false);
                }

                target.endNonVolatileStatus(true);

                int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                Battle.delayedMoves.get(user.getTeam()).set(index, new Move(MoveList._placeholder_, user));

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.DelayedSwitch
        },
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Delayed
        }
    );

    public static final Move heart_swap = new Move(
        "Heart Swap",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            for (Stat stat : user.getStats()) {
                int userStages = stat.getTrueStages();
                int targetStages = target.getStat(stat.getNameShort()).getTrueStages();

                stat.setStages(targetStages);
                target.getStat(stat.getNameShort()).setStages(userStages);
            }

            System.out.println(user.getName(true, true) + " switched stat changes with its target!");

            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return StatusConditionList.pumped.apply(user, thisMove, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move heat_crash = new Move(
        "Heat Crash",
        TypeList.fire,
        Category.Physical,
        10,
        0, 160,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (user.getWeight(thisMove) >= target.getWeight(thisMove)*5) { // ≤ 20%
                return 120.0;
            } else if (user.getWeight(thisMove) >= target.getWeight(thisMove)*4) { // ≤ 25%
                return 100.0;
            } else if (user.getWeight(thisMove) >= target.getWeight(thisMove)*3) { // ≤ 33.34%
                return 80.0;
            } else if (user.getWeight(thisMove) >= target.getWeight(thisMove)*2) { // ≤ 50%
                return 60.0;
            } else { // > 50%
                return 40.0;
            }
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move heat_wave = new Move(
        "Heat Wave",
        TypeList.fire,
        Category.Special,
        10,
        95,
        90,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move heavy_slam = new Move(
        "Heavy Slam",
        TypeList.steel,
        Category.Physical,
        10,
        0, 160,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (user.getWeight(thisMove) >= target.getWeight(thisMove)*5) { // ≤ 20%
                return 120.0;
            } else if (user.getWeight(thisMove) >= target.getWeight(thisMove)*4) { // ≤ 25%
                return 100.0;
            } else if (user.getWeight(thisMove) >= target.getWeight(thisMove)*3) { // ≤ 33.34%
                return 80.0;
            } else if (user.getWeight(thisMove) >= target.getWeight(thisMove)*2) { // ≤ 50%
                return 60.0;
            } else { // > 50%
                return 40.0;
            }
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move helping_hand = new Move(
        "Helping Hand",
        TypeList.normal,
        Category.Status,
        20,
        0,
        -1,
        5,
        MoveTarget.Ally,
        (_, _, _, _, _, _, _, condition) -> {
            // TODO StatusCondition helping_hand (volatile)
            // sem uso em singles
            if (condition == MoveEffectActivation.TryUse) {
                return false;
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails
        }
    );

    public static final Move hex = new Move(
        "Hex",
        TypeList.ghost,
        Category.Special,
        10,
        65, 160,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            if (!target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                return 130.0;
            }
            return 65.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move high_jump_kick = new Move(
        "High Jump Kick",
        TypeList.fighting,
        Category.Physical,
        10,
        130,
        90,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            int crashDamage = user.getHP()/2;
            String message = user.getName(true, true) + " kept going and crashed!";
            Damage.indirectDamage(user, user, crashDamage, DamageSource.Crash, thisMove, message, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.Miss
        },
        new InherentProperty[] {
            InherentProperty.CrashDamage,
            InherentProperty.GravityUnusable
        }
    );

    public static final Move hone_claws = new Move(
        "Hone Claws",
        TypeList.dark,
        Category.Status,
        15,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Atk).change(1, thisMove, true, true, false);
            user.getStat(StatName.Acc).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Atk).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move horn_attack = new Move(
        "Horn Attack",
        TypeList.normal,
        Category.Physical,
        25,
        65,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move horn_leech = new Move(
        "Horn Leech",
        TypeList.grass,
        Category.Physical,
        10,
        75,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            if (user.getCurrentHP() < user.getHP()) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }

                Damage.heal(user, thisMove, Integer.max(damage.amount/2, 1), true, false);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move howl = new Move(
        "Howl",
        TypeList.normal,
        Category.Status,
        40,
        0,
        -1,
        0,
        MoveTarget.UserAndAlly,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Atk).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Atk).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Sound
        }
    );

    public static final Move hurricane = new Move(
        "Hurricane",
        TypeList.flying,
        Category.Special,
        10,
        110,
        70,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AccuracyCalc) {
                if (Battle.getWeather().compare(FieldConditionList.rain) || Battle.getWeather().compare(FieldConditionList.primordial_sea)) {
                    return -1.0;
                } else if (Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land)) {
                    return 50.0;
                } else {
                    return 70.0;
                }
            }
            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                double chance = 0.1;
                if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                    chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                }

                if (Math.random() < chance) {
                    StatusConditionList.confusion.apply(target, thisMove, (int) Math.ceil(Math.random()*4)+1, true);
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.AccuracyCalc
        }
    );

    public static final Move hydro_pump = new Move(
        "Hydro Pump",
        TypeList.water,
        Category.Special,
        5,
        110,
        80,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move hydrokinesis = new Move( // fanmade
        "Hydrokinesis",
        TypeList.water,
        Category.Status,
        15,
        0,
        -1,
        4,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusConditionList.hydrokinesis.apply(user, thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(2, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotSnatchable
        }
    );

    public static final Move hyper_beam = new Move(
        "Hyper Beam",
        TypeList.normal,
        Category.Special,
        5,
        150,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusCondition rechargeCondition = user.getVolatileStatus(StatusConditionList.recharging_turn);

            if (rechargeCondition == null) {
                user.setReadiedMove(thisMove);
                StatusConditionList.recharging_turn.apply(user, thisMove, true);
            } else {
                user.setReadiedMove(null);
                user.endVolatileStatus(rechargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.Recharges
        }
    );

    public static final Move hyper_voice = new Move(
        "Hyper Voice",
        TypeList.normal,
        Category.Special,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move hyperspace_fury = new Move(
        "Hyperspace Fury",
        TypeList.dark,
        Category.Physical,
        5,
        100,
        -1,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (user.compareWithForm(PokemonList.hoopa_unbound)) {
                    return true;
                } else if (user.compare(PokemonList.hoopa, true)) {
                    System.out.println("But " + user.getName(true, false) + " can't use it the way it is now!");
                    return false;
                } else {
                    System.out.println("But " + user.getName(true, false) + " can't use the move!");
                    return false;
                }
            }

            if (condition == MoveEffectActivation.AfterMove) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }
                user.getStat(StatName.Def).change(-1, thisMove, true, true, false);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.IgnoresProtection,
            InherentProperty.BreaksProtection,
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move hyperspace_hole = new Move(
        "Hyperspace Hole",
        TypeList.psychic,
        Category.Special,
        5,
        80,
        -1,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty.IgnoresProtection,
            InherentProperty.BreaksProtection,
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move hypnosis = new Move(
        "Hypnosis",
        TypeList.psychic,
        Category.Status,
        20,
        0,
        60,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.sleep.apply(pokemon, thisMove, (int) Math.ceil(Math.random()*3), showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move ice_beam = new Move(
        "Ice Beam",
        TypeList.ice,
        Category.Special,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.frostbite.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move ice_burn = new Move(
        "Ice Burn",
        TypeList.ice,
        Category.Special,
        5,
        140,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, condition) -> {
            StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.charging_turn);

            if (condition == MoveEffectActivation.AfterMove) {
                if (chargeCondition == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.charging_turn.apply(user, thisMove, true);

                    System.out.println(user.getName(true, true) + " became cloaked in freezing air!");
                } else {
                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }
            }
            if (condition == MoveEffectActivation.Miss) {
                user.setReadiedMove(null);
                user.endVolatileStatus(chargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.charging_turn);

                if (chargeCondition == null) { // ativa depois do ataque, quando perde chargeCondition
                    double chance = 0.3;
                    if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                        chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                    }

                    if (Math.random() < chance) {
                        StatusConditionList.burn.apply(target, thisMove, true);
                    }
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss
        },
        new InherentProperty[] {
            InherentProperty.Charges
        }
    );

    public static final Move ice_fang = new Move(
        "Ice Fang",
        TypeList.ice,
        Category.Physical,
        15,
        65,
        95,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance1 = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance1 *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance1) {
                StatusConditionList.frostbite.apply(target, thisMove, true);
            }


            double chance2 = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance2 *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance2) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move ice_punch = new Move(
        "Ice Punch",
        TypeList.ice,
        Category.Physical,
        15,
        75,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.frostbite.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move ice_shard = new Move(
        "Ice Shard",
        TypeList.ice,
        Category.Physical,
        30,
        40,
        100,
        1,
        false,
        1,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move icicle_crash = new Move(
        "Icicle Crash",
        TypeList.ice,
        Category.Physical,
        10,
        85,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move icy_wind = new Move(
        "Icy Wind",
        TypeList.ice,
        Category.Special,
        15,
        55,
        95,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Spe).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move imprison = new Move(
        "Imprison",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusConditionList.imprison.apply(user, thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(2, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move incinerate = new Move(
        "Incinerate",
        TypeList.fire,
        Category.Special,
        15,
        60,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        (_, _, target, _, _, _, _, _) -> {
            if (!target.getItem().compare(ItemList.none) &&
                target.getItem().getType() == ItemType.Berry) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }
                System.out.println(target.getName(true, true) + "'s " + target.getItem().getName() + " was burned up!");

                target.getItem().setConsumed(true);
                target.getItem().consume(false, true);
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move infernal_parade = new Move(
        "Infernal Parade",
        TypeList.ghost,
        Category.Special,
        15,
        60,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.CallPower) {
                if (!target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                    return 120.0;
                }
                return 60.0;
            }
            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                double chance = 0.3;
                if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                    chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                }

                if (Math.random() < chance) {
                    StatusConditionList.burn.apply(target, thisMove, true);
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower,
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move inferno = new Move(
        "Inferno",
        TypeList.fire,
        Category.Special,
        5,
        100,
        50,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            StatusConditionList.burn.apply(target, thisMove, true);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move ingrain = new Move(
        "Ingrain",
        TypeList.grass,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusConditionList.ingrain.apply(user, thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move iron_defense = new Move(
        "Iron Defense",
        TypeList.steel,
        Category.Status,
        15,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Def).change(2, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move iron_head = new Move(
        "Iron Head",
        TypeList.steel,
        Category.Physical,
        15,
        80,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move iron_tail = new Move(
        "Iron Tail",
        TypeList.steel,
        Category.Physical,
        15,
        100,
        75,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.Def).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move ivy_cudgel = new Move(
        "Ivy Cudgel",
        TypeList.grass,
        Category.Physical,
        10,
        100,
        100,
        2,
        true,
        0,
        MoveTarget.Normal,
        (_, user, _, type, _, _, _, _) -> {
            if (user.compare(PokemonList.ogerpon, false) &&
                user.getItem().getType() == ItemType.Mask &&
                user.getItem().shouldActivate(null)) {
                return user.getItem().getChangesTypeTo();
            }
            return type;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallType
        }
    );

    public static final Move jet_punch = new Move(
        "Jet Punch",
        TypeList.water,
        Category.Physical,
        15,
        60,
        100,
        1,
        true,
        1,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move judgment = new Move(
        "Judgment",
        TypeList.normal,
        Category.Special,
        10,
        100,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, user, _, type, _, _, _, _) -> {
            if (user.getItem().getType() == ItemType.Plate &&
                user.getItem().shouldActivate(null)) {
                return user.getItem().getChangesTypeTo();
            }
            return type;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallType
        }
    );

    public static final Move jungle_healing = new Move(
        "Jungle Healing",
        TypeList.grass,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.UserAndAlly,
        (thisMove, user, _, _, _, _, _, _) -> {
            int healedDamage = Integer.max((int) Math.floor(user.getHP() * 1/4), 1);
            Damage.heal(user, thisMove, healedDamage, true, false);
            user.endNonVolatileStatus(true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move knock_off = new Move(
        "Knock Off",
        TypeList.dark,
        Category.Physical,
        20,
        65,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, condition) -> {
            boolean removable = !target.getItem().heldByValidUser(true) || !target.getItem().isTetheredToValidUser() || target.getItem().getType() != ItemType.ZCrystal;

            if (condition == MoveEffectActivation.AfterMove) {
                if (removable && !target.getItem().compare(ItemList.none)) {
                    if (Battle.faintCheck(target, false)) {
                        System.out.println();
                    }
                    System.out.println(user.getName(true, true) + " knocked off " + target.getName(true, false) + "'s " + target.getItem().getName());

                    target.getItem().setConsumed(true);
                    target.getItem().consume(false, false);
                }
            }
            if (condition == MoveEffectActivation.CallPower) {
                if (removable && !target.getItem().compare(ItemList.none)) {
                    return 97.5;
                }
                return 65.0;
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.CallPower
        }
    );

    public static final Move lands_wrath = new Move(
        "Land's Wrath",
        TypeList.ground,
        Category.Physical,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move laser_focus = new Move(
        "Laser Focus",
        TypeList.normal,
        Category.Status,
        30,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusConditionList.laser_focus.apply(user, thisMove, 1, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Atk).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move last_resort = new Move(
        "Last Resort",
        TypeList.normal,
        Category.Physical,
        5,
        140,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            if (user.getMove(MoveList.last_resort) == null) {
                return false;
            }

            boolean onlyMove = true;
            boolean hasUnusedMove = false;
            for (Move move : user.getMoves()) {
                if (move != null &&
                    !move.compare(MoveList.last_resort)) {
                    onlyMove = false;
                    if (!move.isUsed()) {
                        hasUnusedMove = true;
                    }
                }
            }

            if (!onlyMove && !hasUnusedMove) {
                return true;
            }
            return false;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse
        }
    );

    public static final Move lava_plume = new Move(
        "Lava Plume",
        TypeList.fire,
        Category.Special,
        15,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move leaf_blade = new Move(
        "Leaf Blade",
        TypeList.grass,
        Category.Physical,
        15,
        90,
        100,
        2,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move leaf_storm = new Move(
        "Leaf Storm",
        TypeList.grass,
        Category.Special,
        5,
        130,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.SpA).change(-2, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move leaf_tornado = new Move(
        "Leaf Tornado",
        TypeList.grass,
        Category.Special,
        10,
        65,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.5;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.Acc).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move leafage = new Move(
        "Leafage",
        TypeList.grass,
        Category.Physical,
        40,
        40,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move leech_seed = new Move(
        "Leech Seed",
        TypeList.grass,
        Category.Status,
        10,
        0,
        90,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.seed.apply(pokemon, thisMove, user, showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move leer = new Move(
        "Leer",
        TypeList.normal,
        Category.Status,
        30,
        0,
        100,
        0,
        MoveTarget.AllOpponents,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Def).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Atk).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move lick = new Move(
        "Lick",
        TypeList.ghost,
        Category.Physical,
        30,
        30,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.paralysis.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move life_dew = new Move(
        "Life Dew",
        TypeList.water,
        Category.Status,
        30,
        0,
        -1,
        0,
        MoveTarget.UserAndAlly,
        (thisMove, user, _, _, _, _, _, _) -> {
            int healedDamage = Integer.max((int) Math.floor(user.getHP() * 1/2), 1);
            Damage.heal(user, thisMove, healedDamage, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move light_screen = new Move(
        "Light Screen",
        TypeList.psychic,
        Category.Status,
        30,
        0,
        -1,
        0,
        MoveTarget.UserField,
        (thisMove, user, _, _, _, _, _, _) -> {
            FieldConditionList.light_screen.apply(user.getTeam(), thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move liquidation = new Move(
        "Liquidation",
        TypeList.water,
        Category.Physical,
        10,
        85,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.2;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.Def).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move lock_on = new Move(
        "Lock-On",
        TypeList.normal,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (target.getVolatileStatus(StatusConditionList.taking_aim, user) == null) {
                StatusConditionList.taking_aim.apply(target, thisMove, 1, user, true);
                StatusConditionList.taking_aim.apply(user, thisMove, 1, user, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move low_kick = new Move(
        "Low Kick",
        TypeList.fighting,
        Category.Physical,
        20,
        0, 160,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            if (target.getWeight(thisMove) < 10) {
                return 20.0;
            } else if (target.getWeight(thisMove) < 25) {
                return 40.0;
            } else if (target.getWeight(thisMove) < 50) {
                return 60.0;
            } else if (target.getWeight(thisMove) < 100) {
                return 80.0;
            } else if (target.getWeight(thisMove) < 200) {
                return 100.0;
            } else {
                return 120.0;
            }
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move low_sweep = new Move(
        "Low Sweep",
        TypeList.fighting,
        Category.Physical,
        20,
        65,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Spe).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move lunar_blessing = new Move(
        "Lunar Blessing",
        TypeList.psychic,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.UserAndAlly,
        (thisMove, user, _, _, _, _, _, _) -> {
            int healedDamage = Integer.max((int) Math.floor(user.getHP() * 1/4), 1);
            Damage.heal(user, thisMove, healedDamage, true, false);
            user.endNonVolatileStatus(true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move lunar_dance = new Move(
        "Lunar Dance",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                boolean teamFainted = true;
                for (Pokemon pokemon : Battle.teams[user.getTeam()]) {
                    if (pokemon != null &&
                        pokemon != user &&
                        !Battle.faintCheck(pokemon, false)) {
                        teamFainted = false;
                    }
                }

                if (!teamFainted) {
                    Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));
                    System.out.println(user.getName(true, true) + " performed a final dance to the moon!");

                    user.setCurrentHP(0);

                    Battle.faintCheck(user, true);
                }
            }
            if (condition == MoveEffectActivation.DelayedSwitch) {
                boolean fullPP = true;
                for (Move move : target.getMoves()) {
                    if (move != null &&
                        move.getCurrentPP() < move.getPP()) {
                        fullPP = false;
                        break;
                    }
                }

                if (target.getCurrentHP() >= target.getHP() && fullPP &&
                    target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                    return null;
                }

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

                System.out.println(target.getName(true, true) + " became cloaked in mystical moonlight!");

                if (target.getCurrentHP() < target.getHP()) {
                    Damage.heal(target, thisMove, target.getHP(), true, false);
                }

                for (Move move : target.getMoves()) {
                    if (move != null &&
                        move.getCurrentPP() < move.getPP()) {
                        int restoredPP = move.getPP() - move.getCurrentPP();
                        move.setCurrentPP(move.getPP());
                        System.out.println(target.getName(true, true) + " restored " + restoredPP + " PP to its move " + move.getName());
                    }
                }

                target.endNonVolatileStatus(true);

                int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                Battle.delayedMoves.get(user.getTeam()).set(index, new Move(MoveList._placeholder_, user));

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.DelayedSwitch
        },
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Delayed
        }
    );

    public static final Move luster_purge = new Move(
        "Luster Purge",
        TypeList.psychic,
        Category.Special,
        5,
        95,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.5;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.SpD).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move mach_punch = new Move(
        "Mach Punch",
        TypeList.fighting,
        Category.Physical,
        30,
        40,
        100,
        1,
        true,
        1,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move magic_coat = new Move(
        "Magic Coat",
        TypeList.psychic,
        Category.Status,
        15,
        0,
        -1,
        4,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                Action userAction = Battle.findAction(thisMove, user);
                return Battle.nextMove(userAction) != null;
            }
            if (condition == MoveEffectActivation.AfterMove) {
                StatusConditionList.magic_coat.apply(user, thisMove, true);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(2, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotSnatchable
        }
    );

    public static final Move magic_room = new Move(
        "Magic Room",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.AllFields,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.magic_room.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move magical_leaf = new Move(
        "Magical Leaf",
        TypeList.grass,
        Category.Special,
        20,
        60,
        -1,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move magma_storm = new Move(
        "Magma Storm",
        TypeList.fire,
        Category.Special,
        5,
        100,
        75,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (target.getVolatileStatus(StatusConditionList.bind) == null) {
                StatusConditionList.bind.apply(target, thisMove, (int) Math.ceil(Math.random()*2)+3, user, true); // 4-5 turnos
                if (!Battle.faintCheck(target, false)) {
                    System.out.println(target.getName(true, true) + " became trapped by swirling magma!");
                }
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move magnet_rise = new Move(
        "Magnet Rise",
        TypeList.electric,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            if (user.getVolatileStatus(StatusConditionList.ingrain) == null) {
                StatusConditionList.magnet_rise.apply(user, thisMove, 5, true);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Eva).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.GravityUnusable
        }
    );

    public static final Move magnetic_flux = new Move(
        "Magnetic Flux",
        TypeList.electric,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.UserAndAlly,
        (thisMove, user, _, _, _, _, _, _) -> {
            if (user.getAbility().compare(AbilityList.plus) || user.getAbility().compare(AbilityList.minus)) {
                user.getStat(StatName.Def).change(1, thisMove, true, true, false);
                user.getStat(StatName.SpD).change(1, thisMove, true, true, false);
            }
            // TODO afeta aliados também
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move malignant_chain = new Move(
        "Malignant Chain",
        TypeList.poison,
        Category.Special,
        5,
        100,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.5;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.bad_poison.apply(target, thisMove, 1, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move mean_look = new Move(
        "Mean Look",
        TypeList.normal,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.trapped.apply(pokemon, thisMove, user, showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresProtection
        }
    );

    public static final Move mega_drain = new Move(
        "Mega Drain",
        TypeList.grass,
        Category.Special,
        15,
        40, 120,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            if (user.getCurrentHP() < user.getHP()) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }

                Damage.heal(user, thisMove, Integer.max(damage.amount/2, 1), true, false);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move mega_kick = new Move(
        "Mega Kick",
        TypeList.normal,
        Category.Physical,
        5,
        120,
        75,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move mega_punch = new Move(
        "Mega Punch",
        TypeList.normal,
        Category.Physical,
        20,
        80,
        85,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move megahorn = new Move(
        "Megahorn",
        TypeList.bug,
        Category.Physical,
        10,
        120,
        85,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move memento = new Move(
        "Memento",
        TypeList.dark,
        Category.Status,
        10,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                target.getStat(StatName.Atk).change(-2, thisMove, false, true, false);
                target.getStat(StatName.SpA).change(-2, thisMove, false, true, false);
            }

            user.setCurrentHP(0);

            Battle.faintCheck(user, true);

            if (thisMove.isZPowered()) {
                thisMove.activateZEffect(user, target, null, null, 0, true, MoveEffectActivation.ZPrimarySuccess);
            }

            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss
        },
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.ZPrimarySuccess) {
                boolean teamFainted = true;
                for (Pokemon pokemon : Battle.teams[user.getTeam()]) {
                    if (pokemon != null &&
                        pokemon != user &&
                        !Battle.faintCheck(pokemon, false)) {
                        teamFainted = false;
                    }
                }

                if (!teamFainted) {
                    Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));
                }
            }
            if (condition == MoveEffectActivation.DelayedSwitch) {
                if (target.getCurrentHP() >= target.getHP() &&
                    target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                    return null;
                }

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

                System.out.println("The Z-Power restored " + target.getName(true, false) + "'s health!");

                Damage.heal(target, thisMove, target.getHP(), true, false);

                int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                Battle.delayedMoves.get(user.getTeam()).set(index, new Move(MoveList._placeholder_, user));

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZPrimarySuccess,
            MoveEffectActivation.ZDelayedSwitch
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move metal_burst = new Move(
        "Metal Burst",
        TypeList.steel,
        Category.Physical,
        10,
        0,
        100,
        1,
        false,
        0,
        MoveTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            StatusCondition counterCondition = user.getVolatileStatus(StatusConditionList.countering);

            if (condition == MoveEffectActivation.TurnStart) {
                StatusConditionList.countering.apply(user, thisMove, true);
            }
            if (condition == MoveEffectActivation.ChangeTarget) {
                if (counterCondition != null &&
                    counterCondition.getAffectedMove() != null) {
                    return counterCondition.getAffectedMove().getUser();
                }
                return user;
            }
            if (condition == MoveEffectActivation.TryUse) {
                if (counterCondition == null) {
                    return false;
                }

                Action userAction = Battle.findAction(thisMove, user);
                Action targetAction = Battle.findAction(target, false);
                if (!Battle.actionIsAfterOther(userAction, targetAction)) {
                    return false;
                }

                return counterCondition.getCounter() > 0;
            }
            if (condition == MoveEffectActivation.FixedDamage) {
                return (int) Math.floor(user.getVolatileStatus(StatusConditionList.countering).getCounter()*1.5);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TurnStart,
            MoveEffectActivation.ChangeTarget,
            MoveEffectActivation.TryUse,
            MoveEffectActivation.FixedDamage
        }
    );

    public static final Move metal_claw = new Move(
        "Metal Claw",
        TypeList.steel,
        Category.Physical,
        35,
        50,
        95,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }
                user.getStat(StatName.Atk).change(1, thisMove, true, true, false);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move metal_sound = new Move(
        "Metal Sound",
        TypeList.steel,
        Category.Status,
        40,
        0,
        85,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.SpD).change(-2, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move meteor_mash = new Move(
        "Meteor Mash",
        TypeList.steel,
        Category.Physical,
        10,
        90,
        90,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.2;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }
                user.getStat(StatName.Atk).change(1, thisMove, true, true, false);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move metronome = new Move(
        "Metronome",
        TypeList.normal,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            Move calledMove = null;
            do {
                int calledMoveIndex = (int) (Math.random()*AllMoves.allMoves.size());
                calledMove = new Move(AllMoves.allMoves.get(calledMoveIndex), user);
            } while (calledMove.hasInherentProperty(InherentProperty.MetronomeUncallable));

            if (thisMove.isZPowered() &&
                calledMove.getCategory() != Category.Status) {
                calledMove = new Move(calledMove.turnZMove(), calledMove, user);
            }

            if (thisMove.isZPowered()) {
                System.out.print("Z-");
            }
            System.out.println("Metronome called " + calledMove.getName() + "!");

            for (TemporaryProperty temporaryProperties : thisMove.getTemporaryProperties()) {
                calledMove.addProperty(temporaryProperties);
            }
            calledMove.addProperty(TemporaryProperty.Called);

            Pokemon moveTarget;
            if (calledMove.targetsOpponent()) {
                if (user.getTeam() == 0) {
                    moveTarget = Battle.opponentActivePokemon;
                } else {
                    moveTarget = Battle.yourActivePokemon;
                }
            } else {
                if (user.getTeam() == 0) {
                    moveTarget = Battle.yourActivePokemon;
                } else {
                    moveTarget = Battle.opponentActivePokemon;
                }
            }

            Action moveLocation = Battle.findAction(thisMove, user);
            Battle.addAction(new Action(calledMove, user, moveTarget), moveLocation);

            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        null, // efeito de chamar um Z-Move é feito em primaryEffect
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails,
            InherentProperty.NotSnatchable
        }
    );

    public static final Move mirror_coat = new Move(
        "Mirror Coat",
        TypeList.psychic,
        Category.Special,
        20,
        0,
        100,
        1,
        false,
        -5,
        MoveTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            StatusCondition counterCondition = user.getVolatileStatus(StatusConditionList.countering);

            if (condition == MoveEffectActivation.TurnStart) {
                StatusConditionList.countering.apply(user, thisMove, true);
            }
            if (condition == MoveEffectActivation.ChangeTarget) {
                if (counterCondition != null &&
                    counterCondition.getAffectedMove() != null) {
                    return counterCondition.getAffectedMove().getUser();
                }
                return user;
            }
            if (condition == MoveEffectActivation.TryUse) {
                if (counterCondition == null) {
                    return false;
                }

                Action userAction = Battle.findAction(thisMove, user);
                Action targetAction = Battle.findAction(target, false);
                if (!Battle.actionIsAfterOther(userAction, targetAction)) {
                    return false;
                }

                return counterCondition.getCounter() > 0;
            }
            if (condition == MoveEffectActivation.FixedDamage) {
                return counterCondition.getCounter()*2;
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TurnStart,
            MoveEffectActivation.ChangeTarget,
            MoveEffectActivation.TryUse,
            MoveEffectActivation.FixedDamage
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails
        }
    );

    public static final Move mist = new Move(
        "Mist",
        TypeList.ice,
        Category.Status,
        30,
        0,
        -1,
        0,
        MoveTarget.UserField,
        (thisMove, user, _, _, _, _, _, _) -> {
            FieldConditionList.mist.apply(user.getTeam(), thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return Damage.heal(user, thisMove, user.getHP(), true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move mist_ball = new Move(
        "Mist Ball",
        TypeList.psychic,
        Category.Special,
        5,
        95,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.5;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.SpA).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.BallBomb
        }
    );

    public static final Move misty_terrain = new Move(
        "Misty Terrain",
        TypeList.fairy,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.AllFields,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.misty_terrain.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move moonblast = new Move(
        "Moonblast",
        TypeList.fairy,
        Category.Special,
        15,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.SpA).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move moongeist_beam = new Move(
        "Moongeist Beam",
        TypeList.ghost,
        Category.Special,
        5,
        100,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty.IgnoresAbility
        }
    );

    public static final Move moonlight = new Move(
        "Moonlight",
        TypeList.fairy,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            int healedDamage;
            if (Battle.getWeather().compare(FieldConditionList.clear) || Battle.getWeather().compare(FieldConditionList.delta_stream)) {
                healedDamage = Integer.max((int) Math.floor(user.getHP() * 1/2), 1);
            } else if (Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land)) {
                healedDamage = Integer.max((int) Math.floor(user.getHP() * 2/3), 1);
            } else {
                healedDamage = Integer.max((int) Math.floor(user.getHP() * 1/4), 1);
            }
            Damage.heal(user, thisMove, healedDamage, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move morning_sun = new Move(
        "Morning Sun",
        TypeList.normal,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            int healedDamage;
            if (Battle.getWeather().compare(FieldConditionList.clear) || Battle.getWeather().compare(FieldConditionList.delta_stream)) {
                healedDamage = Integer.max((int) Math.floor(user.getHP() * 1/2), 1);
            } else if (Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land)) {
                healedDamage = Integer.max((int) Math.floor(user.getHP() * 2/3), 1);
            } else {
                healedDamage = Integer.max((int) Math.floor(user.getHP() * 1/4), 1);
            }
            Damage.heal(user, thisMove, healedDamage, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move mud_shot = new Move(
        "Mud Shot",
        TypeList.ground,
        Category.Special,
        15,
        55,
        95,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Spe).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move mud_slap = new Move(
        "Mud-Slap",
        TypeList.ground,
        Category.Special,
        10,
        20,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Acc).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move muddy_water = new Move(
        "Muddy Water",
        TypeList.water,
        Category.Special,
        10,
        90,
        85,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.Acc).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move multi_attack = new Move(
        "Multi-Attack",
        TypeList.normal,
        Category.Physical,
        10,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, _, type, _, _, _, _) -> {
            if (user.getItem().getType() == ItemType.Memory &&
                user.getItem().shouldActivate(null)) {
                return user.getItem().getChangesTypeTo();
            }
            return type;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallType
        }
    );

    public static final Move mystical_corruption = new Move( // fanmade
        "Mystical Corruption",
        TypeList.dark,
        Category.Special,
        10,
        70,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.SpD).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move mystical_fire = new Move(
        "Mystical Fire",
        TypeList.fire,
        Category.Special,
        10,
        75,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.SpA).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move mystical_power = new Move(
        "Mystical Power",
        TypeList.psychic,
        Category.Special,
        10,
        70,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.SpA).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move nasty_plot = new Move(
        "Nasty Plot",
        TypeList.dark,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.SpA).change(2, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move nature_power = new Move(
        "Nature Power",
        TypeList.normal,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            Move transformedMove = null;
            if (Battle.getTerrain().compare(FieldConditionList.electric_terrain)) {
                transformedMove = new Move(MoveList.thunderbolt, user);
            } else if (Battle.getTerrain().compare(FieldConditionList.grassy_terrain)) {
                transformedMove = new Move(MoveList.energy_ball, user);
            } else if (Battle.getTerrain().compare(FieldConditionList.misty_terrain)) {
                transformedMove = new Move(MoveList.moonblast, user);
            } else if (Battle.getTerrain().compare(FieldConditionList.psychic_terrain)) {
                transformedMove = new Move(MoveList.psychic, user);
            } else {
                transformedMove = new Move(MoveList.tri_attack, user);
            }

            if (thisMove.isZPowered() &&
                transformedMove.getCategory() != Category.Status) {
                transformedMove = new Move(transformedMove.turnZMove(), transformedMove, user);
            }

            if (transformedMove != null) {
                if (thisMove.isZPowered()) {
                    System.out.print("Z-");
                }
                System.out.println("Nature Power turned into " + transformedMove.getName() + "!");

                for (TemporaryProperty temporaryProperties : thisMove.getTemporaryProperties()) {
                    transformedMove.addProperty(temporaryProperties);
                }
                transformedMove.addProperty(TemporaryProperty.Called);

                Action moveLocation = Battle.findAction(thisMove, user);
                Battle.addAction(new Action(transformedMove, user, target), moveLocation);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        null, // efeito de chamar um Z-Move é feito em primaryEffect
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.NotReflectable
        }
    );

    public static final Move natures_madness = new Move(
        "Nature's Madness",
        TypeList.fairy,
        Category.Special,
        10,
        0,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            return Integer.max(target.getCurrentHP()/2, 1);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.FixedDamage
        }
    );

    public static final Move night_daze = new Move(
        "Night Daze",
        TypeList.dark,
        Category.Special,
        10,
        85,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (user.getAbility().compare(AbilityList.illusion) &&
                user.getAbility().isActive()) {
                Pokemon pokemonDisguise = (Pokemon) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.CallUserData);

                Move moveDisguise = null;
                for (Move move : pokemonDisguise.getMoves()) {
                    if (move != null &&
                        move.getCategory() != Category.Status) {
                        moveDisguise = move;
                        break;
                    }
                }

                if (moveDisguise != null) {
                    return moveDisguise;
                }
            }

            return thisMove;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallMoveData
        }
    );

    public static final Move night_shade = new Move(
        "Night Shade",
        TypeList.ghost,
        Category.Special,
        15,
        0,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            return user.getLevel();
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.FixedDamage
        }
    );

    public static final Move night_slash = new Move(
        "Night Slash",
        TypeList.dark,
        Category.Physical,
        15,
        70,
        100,
        2,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move noble_roar = new Move(
        "Noble Roar",
        TypeList.normal,
        Category.Status,
        30,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Atk).change(-1, thisMove, false, true, false);
            target.getStat(StatName.SpA).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute,
            InherentProperty.NotReflectable
        }
    );

    public static final Move oblivion_wing = new Move(
        "Oblivion Wing",
        TypeList.flying,
        Category.Special,
        10,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            if (user.getCurrentHP() < user.getHP()) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }

                Damage.heal(user, thisMove, Integer.max((int) (damage.amount*0.75), 1), true, false);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move origin_pulse = new Move(
        "Origin Pulse",
        TypeList.water,
        Category.Special,
        10,
        110,
        85,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Pulse
        }
    );

    public static final Move outrage = new Move(
        "Outrage",
        TypeList.dragon,
        Category.Physical,
        10,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusCondition rampage = user.getVolatileStatus(StatusConditionList.rampage);
            if (rampage == null) {
                user.setReadiedMove(thisMove);
                StatusConditionList.rampage.apply(user, thisMove, (int) Math.ceil(Math.random()*2), true); // 2-3 turnos (primeiro não é contado)
            } else if (rampage.getCounter() <= 0) {
                user.endVolatileStatus(rampage, true);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move overheat = new Move(
        "Overheat",
        TypeList.fire,
        Category.Special,
        5,
        130,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.SpA).change(-2, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move pain_split = new Move(
        "Pain Split",
        TypeList.normal,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            int newHP = (user.getCurrentHP() + target.getCurrentHP())/2;
            user.setCurrentHP(newHP);
            target.setCurrentHP(newHP);
            System.out.println("The battlers shared their pain!");
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move parabolic_charge = new Move(
        "Parabolic Charge",
        TypeList.electric,
        Category.Special,
        20,
        65,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        (thisMove, user, target, _, damage, _, _, _) -> {
            if (user.getCurrentHP() < user.getHP()) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }

                Damage.heal(user, thisMove, Integer.max(damage.amount/2, 1), true, false);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move parting_shot = new Move(
        "Parting Shot",
        TypeList.dark,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            boolean atkDropped = target.getStat(StatName.Atk).change(-1, thisMove, false, true, false);
            boolean spaDropped = target.getStat(StatName.SpA).change(-1, thisMove, false, true, false);

            if (atkDropped || spaDropped) {
                Action moveLocation = Battle.findAction(thisMove, user);
                Move switchMove = new Move(MoveList._switch_, user);
                switchMove.addProperty(TemporaryProperty._Pivot_);
                Battle.addAction(new Action(switchMove, user, user), moveLocation);

                if (thisMove.isZPowered()) {
                    thisMove.activateZEffect(user, target, null, null, 0, true, MoveEffectActivation.ZPrimarySuccess);
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.ZPrimarySuccess) {
                boolean teamFainted = true;
                for (Pokemon pokemon : Battle.teams[user.getTeam()]) {
                    if (pokemon != null &&
                        pokemon != user &&
                        !Battle.faintCheck(pokemon, false)) {
                        teamFainted = false;
                    }
                }

                if (!teamFainted) {
                    Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));
                }
            }
            if (condition == MoveEffectActivation.DelayedSwitch) {
                if (target.getCurrentHP() >= target.getHP() &&
                    target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                    return null;
                }

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

                System.out.println("The Z-Power restored " + target.getName(true, false) + "'s health!");

                Damage.heal(target, thisMove, target.getHP(), true, false);

                int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                Battle.delayedMoves.get(user.getTeam()).set(index, new Move(MoveList._placeholder_, user));

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZPrimarySuccess,
            MoveEffectActivation.ZDelayedSwitch
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move payback = new Move(
        "Payback",
        TypeList.dark,
        Category.Physical,
        10,
        50,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            Action userAction = Battle.findAction(thisMove, user);
            Action targetAction = Battle.findAction(target, true);

            if (Battle.actionIsAfterOther(userAction, targetAction)) {
                return 100.0;
            }
            return 50.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move peck = new Move(
        "Peck",
        TypeList.flying,
        Category.Physical,
        35,
        35,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move perish_song = new Move(
        "Perish Song",
        TypeList.normal,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.All,
        (thisMove, user, _, _, _, _, _, _) -> {
            Move perishTest = new Move(thisMove, user);
            perishTest.setMoveTarget(MoveTarget.Normal);

            if (!Battle.yourActivePokemon.getAbility().shouldActivate(perishTest, AbilityActivation.TryHitUser) ||
                (boolean) Battle.yourActivePokemon.getAbility().activate(Battle.yourActivePokemon, user, perishTest, null, null, null, null, 0, AbilityActivation.TryHitUser)) {
                StatusConditionList.perish_song.apply(Battle.yourActivePokemon, perishTest, 4, true);
            }
            if (!Battle.opponentActivePokemon.getAbility().shouldActivate(perishTest, AbilityActivation.TryHitUser) ||
                (boolean) Battle.opponentActivePokemon.getAbility().activate(Battle.opponentActivePokemon, user, perishTest, null, null, null, null, 0, AbilityActivation.TryHitUser)) {
                StatusConditionList.perish_song.apply(Battle.opponentActivePokemon, perishTest, 4, true);
            }
            System.out.println("All Pokémon that heard the song will faint in three turns!");

            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresProtection,
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move petal_blizzard = new Move(
        "Petal Blizzard",
        TypeList.grass,
        Category.Physical,
        15,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move petal_dance = new Move(
        "Petal Dance",
        TypeList.grass,
        Category.Special,
        10,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusCondition rampage = user.getVolatileStatus(StatusConditionList.rampage);
            if (rampage == null) {
                user.setReadiedMove(thisMove);
                StatusConditionList.rampage.apply(user, thisMove, (int) Math.ceil(Math.random()*2), true); // 2-3 turnos (primeiro não é contado)
            } else if (rampage.getCounter() <= 0) {
                user.endVolatileStatus(rampage, true);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move phantom_force = new Move(
        "Phantom Force",
        TypeList.ghost,
        Category.Physical,
        10,
        90,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, condition) -> {
            StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);

            if (condition == MoveEffectActivation.AfterMove) {
                if (chargeCondition == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.semi_invulnerable_charging_turn.apply(user, thisMove, true);

                    System.out.println(user.getName(true, true) + " vanished instantly!");
                } else {
                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }
            }
            if (condition == MoveEffectActivation.Miss) {
                user.setReadiedMove(null);
                user.endVolatileStatus(chargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss
        },
        new InherentProperty[] {
            InherentProperty.Charges,
            InherentProperty.IgnoresProtection,
            InherentProperty.BreaksProtection
        }
    );

    public static final Move photon_geyser = new Move(
        "Photon Geyser",
        TypeList.psychic,
        Category.Special,
        5,
        100,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            int userAtk = user.getStat(StatName.Atk).getValue();
            int userAtkStages = user.getStat(StatName.Atk).getStages(null, null);
            double valAtk = 1 + Math.abs(userAtkStages)*0.5;
            userAtk = (int) (userAtkStages >= 0 ? userAtk*valAtk : userAtk/valAtk);

            int userSpA = user.getStat(StatName.SpA).getValue();
            int userSpAStages = user.getStat(StatName.SpA).getStages(null, null);
            double valSpA = 1 + Math.abs(userSpAStages)*0.5;
            userSpA = (int) (userSpAStages >= 0 ? userSpA*valSpA : userSpA/valSpA);

            if (userAtk > userSpA) {
                return Category.Physical;
            } else {
                return Category.Special;
            }
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallCategory
        },
        new InherentProperty[] {
            InherentProperty.IgnoresAbility
        }
    );

    public static final Move pin_missile = new Move(
        "Pin Missile",
        TypeList.bug,
        Category.Physical,
        20,
        25, 140,
        95,
        1,
        false,
        0,
        new int[] {2, 5},
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move plasma_fists = new Move(
        "Plasma Fists",
        TypeList.electric,
        Category.Physical,
        15,
        100,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.ion_deluge.apply(thisMove, 0, true);
            return null;
        },
        EffectTarget.All,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move play_nice = new Move(
        "Play Nice",
        TypeList.normal,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Atk).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresProtection
        }
    );

    public static final Move play_rough = new Move(
        "Play Rough",
        TypeList.fairy,
        Category.Physical,
        10,
        90,
        90,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.Atk).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move pluck = new Move(
        "Pluck",
        TypeList.flying,
        Category.Physical,
        20,
        60,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (target.getItem().getType() == ItemType.Berry) {
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }
                System.out.println(user.getName(true, true) + " stole and ate its target's " + target.getItem().getName());

                target.getItem().activate(target, user, target, thisMove, null, ItemActivation.ForceUse);

                if (target.getItem().shouldActivate(ItemActivation.Consumed)) {
                    target.getItem().activate(target, user, target, thisMove, null, ItemActivation.Consumed);
                }
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move poison_fang = new Move(
        "Poison Fang",
        TypeList.poison,
        Category.Physical,
        15,
        50,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.5;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.bad_poison.apply(target, thisMove, 1, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move poison_gas = new Move(
        "Poison Gas",
        TypeList.poison,
        Category.Status,
        40,
        0,
        90,
        0,
        MoveTarget.AllOpponents,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.poison.apply(pokemon, thisMove, showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move poison_jab = new Move(
        "Poison Jab",
        TypeList.poison,
        Category.Physical,
        20,
        80,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.poison.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move poison_powder = new Move(
        "Poison Powder",
        TypeList.poison,
        Category.Status,
        35,
        0,
        75,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.poison.apply(pokemon, thisMove, showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Powder
        }
    );

    public static final Move poison_sting = new Move(
        "Poison Sting",
        TypeList.poison,
        Category.Physical,
        35,
        15,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.poison.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move poison_tail = new Move(
        "Poison Tail",
        TypeList.poison,
        Category.Physical,
        25,
        50,
        100,
        2,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.poison.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move pound = new Move(
        "Pound",
        TypeList.normal,
        Category.Physical,
        35,
        40,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move powder_snow = new Move(
        "Powder Snow",
        TypeList.ice,
        Category.Special,
        25,
        40,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.frostbite.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move power_gem = new Move(
        "Power Gem",
        TypeList.rock,
        Category.Special,
        20,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move power_split = new Move(
        "Power Split",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            int newAtk = (user.getStat(StatName.Atk).getValue() + target.getStat(StatName.Atk).getValue())/2;
            int newSpA = (user.getStat(StatName.SpA).getValue() + target.getStat(StatName.SpA).getValue())/2;

            user.getStat(StatName.Atk).setValue(newAtk);
            user.getStat(StatName.SpA).setValue(newSpA);

            target.getStat(StatName.Atk).setValue(newAtk);
            target.getStat(StatName.SpA).setValue(newSpA);

            System.out.println(user.getName(true, true) + " shared its power with the target!");

            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move power_swap = new Move(
        "Power Swap",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            int userAtkStages = user.getStat(StatName.Atk).getTrueStages();
            int userSpAStages = user.getStat(StatName.SpA).getTrueStages();

            int targetAtkStages = target.getStat(StatName.Atk).getTrueStages();
            int targetSpAStages = target.getStat(StatName.SpA).getTrueStages();

            user.getStat(StatName.Atk).setStages(targetAtkStages);
            user.getStat(StatName.SpA).setStages(targetSpAStages);

            target.getStat(StatName.Atk).setStages(userAtkStages);
            target.getStat(StatName.SpA).setStages(userSpAStages);

            System.out.println(user.getName(true, true) + " switched all changes to its Attack and Sp. Atk with its target!");

            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move power_up_punch = new Move(
        "Power-Up Punch",
        TypeList.fighting,
        Category.Physical,
        20,
        40,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.Atk).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move power_whip = new Move(
        "Power Whip",
        TypeList.grass,
        Category.Physical,
        10,
        120,
        85,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move precipice_blades = new Move(
        "Precipice Blades",
        TypeList.ground,
        Category.Physical,
        10,
        120,
        85,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move prismatic_laser = new Move(
        "Prismatic Laser",
        TypeList.psychic,
        Category.Special,
        10,
        160,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusCondition rechargeCondition = user.getVolatileStatus(StatusConditionList.recharging_turn);

            if (rechargeCondition == null) {
                user.setReadiedMove(thisMove);
                StatusConditionList.recharging_turn.apply(user, thisMove, true);
            } else {
                user.setReadiedMove(null);
                user.endVolatileStatus(rechargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.Recharges
        }
    );

    public static final Move protect = new Move(
        "Protect",
        TypeList.normal,
        Category.Status,
        10,
        0,
        -1,
        4,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                Action userAction = Battle.findAction(thisMove, user);
                if (Battle.nextMove(userAction) == null) {
                    user.setConsecutiveProtections(0);
                    return false;
                }

                double chance = 1*(Math.pow(1.0/3.0, user.getConsecutiveProtections()));
                if (Math.random() < chance) {
                    return true;
                }

                user.setConsecutiveProtections(0);
                return false;
            }
            if (condition == MoveEffectActivation.AfterMove) {
                StatusConditionList.protection.apply(user, thisMove, true);
                user.addConsecutiveProtection();
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails,
            InherentProperty.NotSnatchable
        }
    );

    public static final Move psybeam = new Move(
        "Psybeam",
        TypeList.psychic,
        Category.Special,
        20,
        65,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.confusion.apply(target, thisMove, (int) Math.ceil(Math.random()*4)+1, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move psych_up = new Move(
        "Psych Up",
        TypeList.normal,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            for (Stat stat : user.getStats()) {
                int targetStages = target.getStat(stat.getNameShort()).getTrueStages();
                stat.setStages(targetStages);
            }

            if (target.getVolatileStatus(StatusConditionList.pumped) != null &&
                user.getVolatileStatus(StatusConditionList.pumped) == null) {
                StatusConditionList.pumped.apply(user, thisMove, false);
            } else if (target.getVolatileStatus(StatusConditionList.pumped) == null &&
                       user.getVolatileStatus(StatusConditionList.pumped) != null) {
                user.endVolatileStatus(StatusConditionList.pumped, false);
            }

            if (target.getVolatileStatus(StatusConditionList.laser_focus) != null &&
                user.getVolatileStatus(StatusConditionList.laser_focus) == null) {
                StatusConditionList.laser_focus.apply(user, thisMove, 1, false);
            } else if (target.getVolatileStatus(StatusConditionList.laser_focus) == null &&
                       user.getVolatileStatus(StatusConditionList.laser_focus) != null) {
                user.endVolatileStatus(StatusConditionList.laser_focus, false);
            }

            System.out.println(user.getName(true, true) + " copied " + target.getName(true, false) + "'s stat changes!");

            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return Damage.heal(user, thisMove, user.getHP(), true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move psychic = new Move(
        "Psychic",
        TypeList.psychic,
        Category.Special,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.SpD).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move psychic_terrain = new Move(
        "Psychic Terrain",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.AllFields,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.psychic_terrain.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move psycho_boost = new Move(
        "Psycho Boost",
        TypeList.psychic,
        Category.Special,
        5,
        140,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.SpA).change(-2, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move psycho_cut = new Move(
        "Psycho Cut",
        TypeList.psychic,
        Category.Physical,
        20,
        70,
        100,
        2,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move psyshock = new Move(
        "Psyshock",
        TypeList.psychic,
        Category.Special,
        10,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            return target.getStat(StatName.Def);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallDefendingStat
        }
    );

    public static final Move psystrike = new Move(
        "Psystrike",
        TypeList.psychic,
        Category.Special,
        10,
        100,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            return target.getStat(StatName.Def);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallDefendingStat
        }
    );

    public static final Move pursuit = new Move(
        "Pursuit",
        TypeList.dark,
        Category.Physical,
        20,
        40,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.OpponentSwitch) {
                Action userAction = Battle.findAction(thisMove, user);
                Action targetAction = Battle.findAction(MoveList._switch_, target);

                Battle.moveAction(userAction, targetAction);
            }
            if (condition == MoveEffectActivation.CallPower) {
                if (target.getVolatileStatus(StatusConditionList.readying_switch) != null) {
                    return 80.0;
                }
                return 40.0;
            }
            if (condition == MoveEffectActivation.AccuracyCalc) {
                if (target.getVolatileStatus(StatusConditionList.readying_switch) != null) {
                    return -1.0;
                }
                return 100.0;
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.OpponentSwitch,
            MoveEffectActivation.CallPower,
            MoveEffectActivation.AccuracyCalc
        }
    );

    public static final Move pyro_ball = new Move(
        "Pyro Ball",
        TypeList.fire,
        Category.Physical,
        5,
        120,
        90,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.BallBomb
        },
        new InherentProperty[] {
            InherentProperty.ThawsUser
        }
    );

    public static final Move quick_attack = new Move(
        "Quick Attack",
        TypeList.normal,
        Category.Physical,
        30,
        40,
        100,
        1,
        true,
        1,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move quick_guard = new Move(
        "Quick Guard",
        TypeList.fighting,
        Category.Status,
        15,
        0,
        -1,
        3,
        MoveTarget.UserField,
        (thisMove, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                Action userAction = Battle.findAction(thisMove, user);
                return Battle.nextMove(userAction) != null;
            }
            if (condition == MoveEffectActivation.AfterMove) {
                FieldConditionList.quick_guard.apply(user.getTeam(), 0, thisMove, true);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails
        }
    );

    public static final Move quiver_dance = new Move(
        "Quiver Dance",
        TypeList.bug,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.SpA).change(1, thisMove, true, true, false);
            user.getStat(StatName.SpD).change(1, thisMove, true, true, false);
            user.getStat(StatName.Spe).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move rage_powder = new Move(
        "Rage Powder",
        TypeList.bug,
        Category.Status,
        20,
        0,
        -1,
        2,
        MoveTarget.User,
        (_, user, _, _, _, _, _, condition) -> {
            System.out.println(user.getName(true, true) + " became the center of attention!");
            // TODO StatusCondition center_of_attention (volatile); muda o alvo para o Pokémon
            // sem uso em singles
            if (condition == MoveEffectActivation.TryUse) {
                return false;
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Powder
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails,
            InherentProperty.NotSnatchable
        }
    );

    public static final Move raging_fury = new Move(
        "Raging Fury",
        TypeList.fire,
        Category.Physical,
        10,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusCondition rampage = user.getVolatileStatus(StatusConditionList.rampage);
            if (rampage == null) {
                user.setReadiedMove(thisMove);
                StatusConditionList.rampage.apply(user, thisMove, (int) Math.ceil(Math.random()*2), true); // 2-3 turnos (primeiro não é contado)
            } else if (rampage.getCounter() <= 0) {
                user.endVolatileStatus(rampage, true);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move rain_dance = new Move(
        "Rain Dance",
        TypeList.water,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.AllFields,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.rain.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move rapid_spin = new Move(
        "Rapid Spin",
        TypeList.normal,
        Category.Physical,
        40,
        50,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            if (!Battle.faintCheck(user, false)) {
                for (StatusCondition statusCondition : user.getVolatileStatusList()) {
                    if (statusCondition.compare(StatusConditionList.bind) ||
                        statusCondition.compare(StatusConditionList.seed)) {
                        if (Battle.faintCheck(target, false)) {
                            System.out.println();
                        }

                        user.endVolatileStatus(statusCondition, true);

                        if (statusCondition.compare(StatusConditionList.bind)) {
                            System.out.println(user.getName(true, true) + " was freed from " + statusCondition.getCausingMove().getName() + "!");
                        }
                    }
                }
                user.orderVolatileStatusList();

                ArrayList<FieldCondition> field = Battle.teamFields.get(user.getTeam());
                for (FieldCondition fieldCondition : field) {
                    if (fieldCondition.getType() == FieldConditionType.ENTRY_HAZARD) {
                        fieldCondition.end(field);
                    }
                }
            }
            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.Spe).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move razor_leaf = new Move(
        "Razor Leaf",
        TypeList.grass,
        Category.Physical,
        25,
        55,
        95,
        2,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move razor_shell = new Move(
        "Razor Shell",
        TypeList.water,
        Category.Physical,
        10,
        75,
        95,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.5;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.Def).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move recover = new Move(
        "Recover",
        TypeList.normal,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            int healedDamage = (int) Math.ceil(user.getHP()/2.0);
            Damage.heal(user, thisMove, healedDamage, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move reflect = new Move(
        "Reflect",
        TypeList.psychic,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.UserField,
        (thisMove, user, _, _, _, _, _, _) -> {
            FieldConditionList.reflect.apply(user.getTeam(), thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move reflect_type = new Move(
        "Reflect Type",
        TypeList.normal,
        Category.Status,
        15,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            boolean typeless = true;
            for (Type targetType : target.getTypes()) {
                if (!targetType.compare(TypeList.typeless)) {
                    typeless = false;
                    break;
                }
            }

            if (!typeless) {
                user.setTypes(target.getTypes());
                System.out.println(user.getName(true, true) + "'s type became the same as " + target.getName(true, false) + "'s type!");
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move relic_song = new Move(
        "Relic Song",
        TypeList.normal,
        Category.Special,
        10,
        75,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            if (user.compare(PokemonList.meloetta, true)) {
                if (user.compareWithForm(PokemonList.meloetta)) {
                    user.changeForm("Pirouette");
                    System.out.println(user.getName(true, true) + " transformed into its Pirouette form!");
                } else {
                    user.changeForm("Aria");
                    System.out.println(user.getName(true, true) + " transformed into its Aria form!");
                }
            }
            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.sleep.apply(target, thisMove, (int) Math.ceil(Math.random()*3), true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move rest = new Move(
        "Rest",
        TypeList.psychic,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                StatusCondition sleepTest = StatusConditionList.sleep.cause(thisMove, 2, user); // com causer pra impedir que seja bloqueado por Safeguard

                if (user.getCurrentHP() == user.getHP() ||
                    user.getNonVolatileStatus().compare(StatusConditionList.sleep) ||
                    sleepTest.immune(user) || sleepTest.targetProtected(user, true)) {
                    return false;
                }
                return true;
            }

            if (condition == MoveEffectActivation.AfterMove) {
                user.endNonVolatileStatus(false);
                StatusConditionList.sleep.apply(user, thisMove, 2, false);
                System.out.println(user.getName(true, true) + " slept and became healthy!");
                Damage.heal(user, thisMove, user.getHP(), true, false);
            }

            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move retaliate = new Move(
        "Retaliate",
        TypeList.normal,
        Category.Physical,
        5,
        70,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            if (Battle.pokemonFaintedLastTurn[user.getTeam()] > 0) {
                return 140.0;
            }
            return 70.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move reversal = new Move(
        "Reversal",
        TypeList.fighting,
        Category.Physical,
        15,
        0, 160,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            if (user.getCurrentHP() < user.getHP() * 0.042) { // HP < 4.2%
                return 200.0;
            } else if (user.getCurrentHP() < user.getHP() * 0.104) { // 4.2% ≤ HP < 10.4%
                return 150.0;
            } else if (user.getCurrentHP() < user.getHP() * 0.208) { // 10.4% ≤ HP < 20.8%
                return 100.0;
            } else if (user.getCurrentHP() < user.getHP() * 0.354) { // 20.8% ≤ HP < 35.4%
                return 80.0;
            } else if (user.getCurrentHP() < user.getHP() * 0.688) { // 35.4% ≤ HP < 68.8%
                return 40.0;
            } else { // HP ≥ 68.8%
                return 20.0;
            }
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move roar = new Move(
        "Roar",
        TypeList.normal,
        Category.Status,
        20,
        0,
        -1,
        -6,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            if (Arrays.asList(target.getAbility().getConditions()).contains(AbilityActivation.TryForceSwitch) &&
                !(boolean) target.getAbility().activate(target, user, thisMove, null, damage, null, null, 0, AbilityActivation.TryForceSwitch)) {
                return null;
            }

            for (StatusCondition statusCondition : target.getVolatileStatusList()) {
                if (Arrays.asList(statusCondition.getActivation()).contains(StatusActivation.TryForceSwitch) &&
                    !(boolean) statusCondition.activate(target, user, thisMove, damage, true, StatusActivation.TryForceSwitch)) {
                    return null;
                }
            }

            Action moveLocation = Battle.findAction(thisMove, user);
            Move switchMove = new Move(MoveList._switch_, target);
            switchMove.addProperty(TemporaryProperty._Forced_);
            Battle.addAction(new Action(switchMove, target, target), moveLocation);

            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresProtection,
            InherentProperty.IgnoresSubstitute,
            InherentProperty.CopycatFails
        }
    );

    public static final Move roar_of_time = new Move(
        "Roar of Time",
        TypeList.dragon,
        Category.Special,
        5,
        150,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusCondition rechargeCondition = user.getVolatileStatus(StatusConditionList.recharging_turn);

            if (rechargeCondition == null) {
                user.setReadiedMove(thisMove);
                StatusConditionList.recharging_turn.apply(user, thisMove, true);
            } else {
                user.setReadiedMove(null);
                user.endVolatileStatus(rechargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.Recharges
        }
    );

    public static final Move rock_blast = new Move(
        "Rock Blast",
        TypeList.rock,
        Category.Physical,
        10,
        25, 140,
        90,
        1,
        false,
        0,
        new int[] {2, 5},
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move rock_polish = new Move(
        "Rock Polish",
        TypeList.rock,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Spe).change(2, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move rock_slide = new Move(
        "Rock Slide",
        TypeList.rock,
        Category.Physical,
        10,
        75,
        90,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move rock_smash = new Move(
        "Rock Smash",
        TypeList.fighting,
        Category.Physical,
        15,
        40,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.5;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.Def).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move rock_throw = new Move(
        "Rock Throw",
        TypeList.rock,
        Category.Physical,
        15,
        50,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move rock_tomb = new Move(
        "Rock Tomb",
        TypeList.rock,
        Category.Physical,
        15,
        60,
        95,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Spe).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move role_play = new Move(
        "Role Play",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            if (!target.getAbility().isNotTransferable() &&
                !user.getAbility().isNotReplaceable() &&
                !user.getAbility().compare(target.getAbility())) {
                System.out.println(user.getName(true, true) + " copied " + target.getName(true, false) + "'s " + target.getAbility().getName() + "!");
                user.setAbility(target.getAbility(), true, target);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresProtection,
            InherentProperty.IgnoresSubstitute,
            InherentProperty.NotReflectable
        }
    );

    public static final Move rollout = new Move(
        "Rollout",
        TypeList.rock,
        Category.Physical,
        20,
        30,
        90,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, condition) -> {
            StatusCondition locked = user.getVolatileStatus(StatusConditionList.locked);

            if (condition == MoveEffectActivation.AfterMove) {
                if (locked == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.locked.apply(user, thisMove, 4, true); // 5 turnos (primeiro não é contado)
                } else if (locked.getCounter() <= 0) {
                    user.endVolatileStatus(locked, true);
                }
            }

            if (condition == MoveEffectActivation.CallPower) {
                double power = 30.0;
                if (locked != null) {
                    for (int i = 5; i > locked.getCounter(); i--) {
                        power *= 2;
                    }
                }

                if (user.getVolatileStatus(StatusConditionList.defense_curl) != null) {
                    power *= 2;
                }

                return power;
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.CallPower
        }
    );

    public static final Move roost = new Move(
        "Roost",
        TypeList.flying,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            int healedDamage = (int) Math.ceil(user.getHP()/2.0);
            Damage.heal(user, thisMove, healedDamage, true, false);
            if (user.hasType(TypeList.flying)) {
                user.getType(TypeList.flying).setSuppressed(true);
            }
            StatusConditionList.roost.apply(user, thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move round = new Move(
        "Round",
        TypeList.normal,
        Category.Special,
        15,
        60,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.OpponentMove) {
                Action userAction = Battle.findAction(thisMove, user);
                Action targetAction = Battle.findAction(MoveList.round, target);

                Battle.moveAction(userAction, targetAction);
            }
            if (condition == MoveEffectActivation.CallPower) {
                Action userAction = Battle.findAction(thisMove, user);
                Action targetAction = Battle.findAction(MoveList.round, target);

                if (Battle.actionIsAfterOther(userAction, targetAction)) {
                    return 120.0;
                }
                return 60.0;
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.OpponentMove,
            MoveEffectActivation.CallPower
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move ruination = new Move(
        "Ruination",
        TypeList.dark,
        Category.Special,
        10,
        0,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            return Integer.max(target.getCurrentHP()/2, 1);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.FixedDamage
        }
    );

    public static final Move sacred_fire = new Move(
        "Sacred Fire",
        TypeList.fire,
        Category.Physical,
        5,
        100,
        95,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.5;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.ThawsUser
        }
    );

    public static final Move sacred_sword = new Move(
        "Sacred Sword",
        TypeList.fighting,
        Category.Physical,
        15,
        90,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Slicing
        },
        new InherentProperty[] {
            InherentProperty.IgnoresDefensiveAndEvasionStages
        }
    );

    public static final Move safeguard = new Move(
        "Safeguard",
        TypeList.normal,
        Category.Status,
        25,
        0,
        -1,
        0,
        MoveTarget.UserField,
        (thisMove, user, _, _, _, _, _, _) -> {
            FieldConditionList.safeguard.apply(user.getTeam(), thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move sand_attack = new Move(
        "Sand Attack",
        TypeList.ground,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Acc).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Eva).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move sand_tomb = new Move(
        "Sand Tomb",
        TypeList.ground,
        Category.Physical,
        15,
        35,
        85,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (target.getVolatileStatus(StatusConditionList.bind) == null) {
                StatusConditionList.bind.apply(target, thisMove, (int) Math.ceil(Math.random()*2)+3, user, true); // 4-5 turnos
                if (!Battle.faintCheck(target, false)) {
                    System.out.println(target.getName(true, true) + " became trapped by the quicksand!");
                }
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move sandsear_storm = new Move(
        "Sandsear Storm",
        TypeList.ground,
        Category.Special,
        10,
        100,
        80,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        (_, _, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AccuracyCalc) {
                if (Battle.getWeather().compare(FieldConditionList.sand)) {
                    return -1.0;
                } else {
                    return 80.0;
                }
            }
            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                double chance = 0.2;
                if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                    chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                }

                if (Math.random() < chance) {
                    StatusConditionList.burn.apply(target, thisMove, true);
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.AccuracyCalc
        }
    );

    public static final Move sandstorm = new Move(
        "Sandstorm",
        TypeList.rock,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.AllFields,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.sand.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move scald = new Move(
        "Scald",
        TypeList.water,
        Category.Special,
        15,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.ThawsUser,
            InherentProperty.ThawsTarget
        }
    );

    public static final Move scary_face = new Move(
        "Scary Face",
        TypeList.normal,
        Category.Status,
        10,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Spe).change(-2, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move scratch = new Move(
        "Scratch",
        TypeList.normal,
        Category.Physical,
        35,
        40,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move screech = new Move(
        "Screech",
        TypeList.normal,
        Category.Status,
        40,
        0,
        85,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Def).change(-2, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Atk).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move searing_shot = new Move(
        "Searing Shot",
        TypeList.fire,
        Category.Special,
        5,
        100,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.BallBomb
        }
    );

    public static final Move secret_sword = new Move(
        "Secret Sword",
        TypeList.fighting,
        Category.Special,
        10,
        85,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            return target.getStat(StatName.Def);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallDefendingStat
        },
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move seed_bomb = new Move(
        "Seed Bomb",
        TypeList.grass,
        Category.Physical,
        15,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.BallBomb
        }
    );

    public static final Move seed_flare = new Move(
        "Seed Flare",
        TypeList.grass,
        Category.Special,
        5,
        120,
        85,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.4;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.SpD).change(-2, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move seismic_toss = new Move(
        "Seismic Toss",
        TypeList.fighting,
        Category.Physical,
        20,
        0,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            return user.getLevel();
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.FixedDamage
        }
    );

    public static final Move self_destruct = new Move(
        "Self-Destruct",
        TypeList.normal,
        Category.Physical,
        5,
        200,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        (_, user, target, _, _, _, _, _) -> {
            if (!user.getAbility().compare(AbilityList.damp) &&
                !target.getAbility().compare(AbilityList.damp)) {
                user.setCurrentHP(0);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.BeforeMove,
            MoveEffectActivation.Miss
        }
    );

    public static final Move shadow_ball = new Move(
        "Shadow Ball",
        TypeList.ghost,
        Category.Special,
        15,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                target.getStat(StatName.SpD).change(-1, thisMove, false, true, false);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.BallBomb
        }
    );

    public static final Move shadow_claw = new Move(
        "Shadow Claw",
        TypeList.ghost,
        Category.Physical,
        15,
        70,
        100,
        2,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move shadow_force = new Move(
        "Shadow Force",
        TypeList.ghost,
        Category.Physical,
        5,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, condition) -> {
            StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.semi_invulnerable_charging_turn);

            if (condition == MoveEffectActivation.AfterMove) {
                if (chargeCondition == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.semi_invulnerable_charging_turn.apply(user, thisMove, true);

                    System.out.println(user.getName(true, true) + " vanished instantly!");
                } else {
                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }
            }
            if (condition == MoveEffectActivation.Miss) {
                user.setReadiedMove(null);
                user.endVolatileStatus(chargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss
        },
        new InherentProperty[] {
            InherentProperty.Charges,
            InherentProperty.IgnoresProtection,
            InherentProperty.BreaksProtection
        }
    );

    public static final Move shadow_punch = new Move(
        "Shadow Punch",
        TypeList.ghost,
        Category.Physical,
        20,
        60,
        -1,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move shadow_sneak = new Move(
        "Shadow Sneak",
        TypeList.ghost,
        Category.Physical,
        30,
        40,
        100,
        1,
        true,
        1,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move shed_tail = new Move(
        "Shed Tail",
        TypeList.normal,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                boolean teamFainted = true;
                for (Pokemon pokemon : Battle.teams[user.getTeam()]) {
                    if (pokemon != null &&
                        pokemon != Battle.yourActivePokemon &&
                        !Battle.faintCheck(pokemon, false)) {
                        teamFainted = false;
                    }
                }

                if (user.getVolatileStatus(StatusConditionList.substitute) == null &&
                    !teamFainted) {
                    int cutHP = Integer.max(user.getHP()/4, 1);

                    if (user.getCurrentHP() > cutHP) {
                        int remainingHP = user.getCurrentHP() - cutHP;
                        user.setCurrentHP(remainingHP);

                        thisMove.setPrimaryEffectCounter(cutHP);
                        Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));

                        System.out.println(user.getName(true, true) + " cut " + cutHP + " and shed its tail to create a decoy!");

                        Action moveLocation = Battle.findAction(thisMove, user);
                        Move switchAction = new Move(MoveList._switch_, user);
                        switchAction.addProperty(TemporaryProperty._Pivot_);
                        Battle.addAction(new Action(switchAction, user, user), moveLocation);
                    }
                }
            }
            if (condition == MoveEffectActivation.DelayedSwitch) {
                StatusConditionList.substitute.apply(target, thisMove, thisMove.getPrimaryEffectCounter(), false);

                int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                Battle.delayedMoves.get(user.getTeam()).set(index, new Move(MoveList._placeholder_, user));
            }
            return null;
        },
        EffectTarget.User,
        0,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.DelayedSwitch
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return Damage.heal(user, thisMove, user.getHP(), true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Delayed
        },
        new InherentProperty[] {
            InherentProperty.NotSnatchable
        }
    );

    public static final Move sheer_cold = new Move(
        "Sheer Cold",
        TypeList.ice,
        Category.Special,
        5,
        0, 180,
        30,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return user.getLevel() >= target.getLevel();
            }
            if (condition == MoveEffectActivation.OneHitKOAccuracy) {
                int baseHitChance = target.hasType(TypeList.ice) ? 30 : 20;
                double hitChance = (user.getLevel() - target.getLevel() + baseHitChance);
                return Math.random() < hitChance/100.0;
            }
            if (condition == MoveEffectActivation.FixedDamage) {
                System.out.println("It's a one-hit KO!");
                return target.getCurrentHP();
            }
            if (condition == MoveEffectActivation.TestImmunities) {
                return !target.hasType(TypeList.ice);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.OneHitKOAccuracy,
            MoveEffectActivation.FixedDamage,
            MoveEffectActivation.TestImmunities
        },
        new InherentProperty[] {
            InherentProperty.OneHitKO
        }
    );

    public static final Move shell_smash = new Move(
        "Shell Smash",
        TypeList.normal,
        Category.Status,
        15,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Def).change(-1, thisMove, true, true, false);
            user.getStat(StatName.SpD).change(-1, thisMove, true, true, false);
            user.getStat(StatName.Atk).change(2, thisMove, true, true, false);
            user.getStat(StatName.SpA).change(2, thisMove, true, true, false);
            user.getStat(StatName.Spe).change(2, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move shift_gear = new Move(
        "Shift Gear",
        TypeList.steel,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Spe).change(2, thisMove, true, true, false);
            user.getStat(StatName.Atk).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move shock_wave = new Move(
        "Shock Wave",
        TypeList.electric,
        Category.Special,
        20,
        60,
        -1,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move simple_beam = new Move(
        "Simple Beam",
        TypeList.normal,
        Category.Status,
        15,
        0,
        100,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            if (!target.getAbility().isNotReplaceable() &&
                !target.getAbility().compare(AbilityList.simple)) {
                System.out.println(target.getName(true, true) + "'s ability changed to Simple!");
                target.setAbility(AbilityList.simple, true, user);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move sing = new Move(
        "Sing",
        TypeList.normal,
        Category.Status,
        15,
        0,
        55,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.sleep.apply(pokemon, thisMove, (int) Math.ceil(Math.random()*3), showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move skill_swap = new Move(
        "Skill Swap",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            if (!user.getAbility().isNotTransferable() &&
                !target.getAbility().isNotTransferable() &&
                !user.getAbility().isNotReplaceable() &&
                !target.getAbility().isNotReplaceable()) {
                System.out.println(user.getName(true, true) + " swapped abilities with its target!");
                user.swapAbilities(target);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move skull_bash = new Move(
        "Skull Bash",
        TypeList.normal,
        Category.Physical,
        10,
        130,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, condition) -> {
            StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.charging_turn);

            if (condition == MoveEffectActivation.AfterMove) {
                if (chargeCondition == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.charging_turn.apply(user, thisMove, true);

                    System.out.println(user.getName(true, true) + " tucked in its head!");
                    user.getStat(StatName.Def).change(1, thisMove, true, true, false);
                } else {
                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }
            }
            if (condition == MoveEffectActivation.Miss) {
                user.setReadiedMove(null);
                user.endVolatileStatus(chargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss
        },
        new InherentProperty[] {
            InherentProperty.Charges
        }
    );

    public static final Move sky_attack = new Move(
        "Sky Attack",
        TypeList.flying,
        Category.Physical,
        5,
        140,
        90,
        2,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, condition) -> {
            StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.charging_turn);

            if (condition == MoveEffectActivation.AfterMove) {
                if (chargeCondition == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.charging_turn.apply(user, thisMove, true);

                    System.out.println(user.getName(true, true) + " became cloaked in a harsh light!");
                } else {
                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }
            }
            if (condition == MoveEffectActivation.Miss) {
                user.setReadiedMove(null);
                user.endVolatileStatus(chargeCondition, true);
            }

            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.charging_turn);

                if (chargeCondition == null) { // ativa depois do ataque, quando perde chargeCondition
                    double chance = 0.3;
                    if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                        chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                    }

                    if (Math.random() < chance) {
                        StatusConditionList.flinch.apply(target, thisMove, true);
                    }
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss
        },
        new InherentProperty[] {
            InherentProperty.Charges
        }
    );

    public static final Move slack_off = new Move(
        "Slack Off",
        TypeList.normal,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            int healedDamage = (int) Math.ceil(user.getHP()/2.0);
            Damage.heal(user, thisMove, healedDamage, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move slam = new Move(
        "Slam",
        TypeList.normal,
        Category.Physical,
        20,
        80,
        75,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move slash = new Move(
        "Slash",
        TypeList.normal,
        Category.Physical,
        20,
        70,
        100,
        2,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move sleep_powder = new Move(
        "Sleep Powder",
        TypeList.grass,
        Category.Status,
        15,
        0,
        75,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.sleep.apply(pokemon, thisMove, (int) Math.ceil(Math.random()*3), showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Powder
        }
    );

    public static final Move sludge_bomb = new Move(
        "Sludge Bomb",
        TypeList.poison,
        Category.Special,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.poison.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.BallBomb
        }
    );

    public static final Move sludge_wave = new Move(
        "Sludge Wave",
        TypeList.poison,
        Category.Special,
        10,
        95,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.poison.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move smack_down = new Move(
        "Smack Down",
        TypeList.rock,
        Category.Physical,
        15,
        50,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            if (!target.isGrounded(null)) {
                StatusConditionList.grounded.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move smog = new Move(
        "Smog",
        TypeList.poison,
        Category.Special,
        20,
        30,
        70,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.4;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.poison.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move smokescreen = new Move(
        "Smokescreen",
        TypeList.normal,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Acc).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Eva).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move snarl = new Move(
        "Snarl",
        TypeList.dark,
        Category.Special,
        15,
        55,
        95,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.SpA).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move snatch = new Move(
        "Snatch",
        TypeList.dark,
        Category.Status,
        10,
        0,
        -1,
        4,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusConditionList.snatch.apply(user, thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(2, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotSnatchable
        }
    );

    public static final Move snipe_shot = new Move(
        "Snipe Shot",
        TypeList.water,
        Category.Special,
        15,
        80,
        100,
        2,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
        // TODO ignora redireção
    );

    public static final Move snore = new Move(
        "Snore",
        TypeList.normal,
        Category.Special,
        15,
        50,
        100,
        1,
        false,
        3,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                if (user.getNonVolatileStatus().compare(StatusConditionList.sleep)) {
                    return true;
                }
            }
            return false;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                double chance = 0.3;
                if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                    chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                }

                if (Math.random() < chance) {
                    StatusConditionList.flinch.apply(target, thisMove, true);
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute,
            InherentProperty.UsableOnSleep
        }
    );

    public static final Move snowscape = new Move(
        "Snowscape",
        TypeList.ice,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.AllFields,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.snow.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move soak = new Move(
        "Soak",
        TypeList.water,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            boolean pureWaterType = true;
            boolean typeless = true;
            for (Type targetType : target.getTypes()) {
                if (!targetType.compare(TypeList.water) && !targetType.compare(TypeList.typeless)) {
                    pureWaterType = false;
                }
                if (!targetType.compare(TypeList.typeless)) {
                    typeless = false;
                }
            }
            if (typeless) {
                pureWaterType = false;
            }

            if (!pureWaterType) {
                target.setTypes(new Type[] {TypeList.water});
                System.out.println(target.getName(true, true) + " transformed into the Water type!");
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move solar_beam = new Move(
        "Solar Beam",
        TypeList.grass,
        Category.Special,
        10,
        120,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, condition) -> {
            StatusCondition chargeCondition = user.getVolatileStatus(StatusConditionList.charging_turn);

            if (condition == MoveEffectActivation.AfterMove) {
                if (chargeCondition == null) {
                    user.setReadiedMove(thisMove);
                    StatusConditionList.charging_turn.apply(user, thisMove, true);

                    System.out.println(user.getName(true, true) + " absorbed light!");

                    if (Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land)) {
                        System.out.println("\nThe harsh sunlight made " + user.getName(true, false) + "'s attack charge quickly!");

                        Action moveLocation = Battle.findAction(thisMove, user);
                        Battle.addAction(new Action(thisMove, user, target), moveLocation);
                    }
                } else {
                    user.setReadiedMove(null);
                    user.endVolatileStatus(chargeCondition, true);
                }
            }
            if (condition == MoveEffectActivation.Miss) {
                user.setReadiedMove(null);
                user.endVolatileStatus(chargeCondition, true);
            }
            if (condition == MoveEffectActivation.CallPower) {
                if (Battle.getWeather().compare(FieldConditionList.rain) ||
                    Battle.getWeather().compare(FieldConditionList.primordial_sea) ||
                    Battle.getWeather().compare(FieldConditionList.sand) ||
                    Battle.getWeather().compare(FieldConditionList.snow)) {
                    return 60.0;
                }
                return 120.0;
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.Miss,
            MoveEffectActivation.CallPower
        },
        new InherentProperty[] {
            InherentProperty.Charges
        }
    );

    public static final Move spacial_rend = new Move(
        "Spacial Rend",
        TypeList.dragon,
        Category.Special,
        5,
        100,
        95,
        2,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move spark = new Move(
        "Spark",
        TypeList.electric,
        Category.Physical,
        20,
        65,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.paralysis.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move sparkling_aria = new Move(
        "Sparkling Aria",
        TypeList.water,
        Category.Special,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        null,
        null,
        (_, _, target, _, _, _, _, _) -> { // considerado secundário
            if (target.getNonVolatileStatus().compare(StatusConditionList.burn) &&
                !Battle.faintCheck(target, false)) {
                target.endNonVolatileStatus(true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move spectral_thief = new Move(
        "Spectral Thief",
        TypeList.ghost,
        Category.Physical,
        10,
        90,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            boolean stoleChange = false;
            for (Stat stat : target.getStats()) {
                int stages = stat.getTrueStages();

                if (stages > 0) {
                    stat.setStages(0);
                    user.getStat(stat.getNameShort()).change(stages, thisMove, true, false, false);
                    stoleChange = true;
                }
            }
            if (stoleChange) {
                System.out.println(user.getName(true, true) + " stole the target's boosted stats!");
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.BeforeMove
        }
    );

    public static final Move spikes = new Move(
        "Spikes",
        TypeList.ground,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.OpponentField,
        (thisMove, _, target, _, _, _, _, _) -> {
            FieldConditionList.spikes.apply(target.getTeam(), 1, thisMove, true);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move spiky_shield = new Move(
        "Spiky Shield",
        TypeList.grass,
        Category.Status,
        10,
        0,
        -1,
        4,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                Action userAction = Battle.findAction(thisMove, user);
                if (Battle.nextMove(userAction) == null) {
                    user.setConsecutiveProtections(0);
                    return false;
                }

                double chance = 1*(Math.pow(1.0/3.0, user.getConsecutiveProtections()));
                if (Math.random() < chance) {
                    return true;
                }

                user.setConsecutiveProtections(0);
                return false;
            }
            if (condition == MoveEffectActivation.AfterMove) {
                StatusConditionList.protection.apply(user, thisMove, true);
                user.addConsecutiveProtection();
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails,
            InherentProperty.NotSnatchable
        }
    );

    public static final Move spirit_shackle = new Move(
        "Spirit Shackle",
        TypeList.ghost,
        Category.Physical,
        10,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            StatusConditionList.trapped.apply(target, thisMove, user, true);
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move spite = new Move(
        "Spite",
        TypeList.ghost,
        Category.Status,
        10,
        0,
        100,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                return target.getLastUsedMove() != null &&
                       target.getLastUsedMove().getCurrentPP() != 0 &&
                       !target.getLastUsedMove().compare(MoveList.struggle);
            }
            if (condition == MoveEffectActivation.AfterMove) {
                int remainingPP = target.getLastUsedMove().getCurrentPP() - 4;

                if (remainingPP < 0) {
                    target.getLastUsedMove().setCurrentPP(0);
                } else {
                    target.getLastUsedMove().setCurrentPP(remainingPP);
                }

                int reducingAmount = remainingPP >= 0 ? 4 : remainingPP + 4;

                System.out.println("It reduced the PP of " + target.getName(true, false) + "'s " + target.getLastUsedMove().getName() + " by " + reducingAmount);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return Damage.heal(user, thisMove, user.getHP(), true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move splash = new Move(
        "Splash",
        TypeList.normal,
        Category.Status,
        40,
        0,
        -1,
        0,
        MoveTarget.User,
        (_, _, _, _, _, _, _, _) -> {
            System.out.println("But nothing happened!");
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Atk).change(3, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.GravityUnusable
        }
    );

    public static final Move springtide_storm = new Move(
        "Springtide Storm",
        TypeList.fairy,
        Category.Special,
        10,
        100,
        80,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        (_, _, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AccuracyCalc) {
                if (Battle.getWeather().compare(FieldConditionList.sun)) {
                    return -1.0;
                } else {
                    return 80.0;
                }
            }
            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                double chance = 0.3;
                if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                    chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                }

                if (Math.random() < chance) {
                    target.getStat(StatName.Atk).change(-1, thisMove, false, true, false);
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.AccuracyCalc
        }
    );

    public static final Move stealth_rock = new Move(
        "Stealth Rock",
        TypeList.rock,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.OpponentField,
        (thisMove, _, target, _, _, _, _, _) -> {
            FieldConditionList.stealth_rock.apply(target.getTeam(), -1, thisMove, true);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move steam_eruption = new Move(
        "Steam Eruption",
        TypeList.water,
        Category.Special,
        5,
        110,
        95,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.burn.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.ThawsUser,
            InherentProperty.ThawsTarget
        }
    );

    public static final Move sticky_web = new Move(
        "Sticky Web",
        TypeList.bug,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.OpponentField,
        (thisMove, _, target, _, _, _, _, _) -> {
            FieldConditionList.sticky_web.apply(target.getTeam(), -1, thisMove, true);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move stomp = new Move(
        "Stomp",
        TypeList.normal,
        Category.Physical,
        20,
        65,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move stomping_tantrum = new Move(
        "Stomping Tantrum",
        TypeList.ground,
        Category.Physical,
        10,
        75,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            if (user.lastMoveFailed()) {
                return 150.0;
            }
            return 75.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move stone_edge = new Move(
        "Stone Edge",
        TypeList.rock,
        Category.Physical,
        5,
        100,
        80,
        2,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move stored_power = new Move(
        "Stored Power",
        TypeList.psychic,
        Category.Special,
        10,
        20, 160,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            int damageMultiplier = 1;

            if (user.getStat(StatName.Atk).getTrueStages() > 0) {
                damageMultiplier += user.getStat(StatName.Atk).getTrueStages();
            }
            if (user.getStat(StatName.Def).getTrueStages() > 0) {
                damageMultiplier += user.getStat(StatName.Def).getTrueStages();
            }
            if (user.getStat(StatName.SpA).getTrueStages() > 0) {
                damageMultiplier += user.getStat(StatName.SpA).getTrueStages();
            }
            if (user.getStat(StatName.SpD).getTrueStages() > 0) {
                damageMultiplier += user.getStat(StatName.SpD).getTrueStages();
            }
            if (user.getStat(StatName.Spe).getTrueStages() > 0) {
                damageMultiplier += user.getStat(StatName.Spe).getTrueStages();
            }
            if (user.getStat(StatName.Acc).getTrueStages() > 0) {
                damageMultiplier += user.getStat(StatName.Acc).getTrueStages();
            }
            if (user.getStat(StatName.Eva).getTrueStages() > 0) {
                damageMultiplier += user.getStat(StatName.Eva).getTrueStages();
            }

            return 20.0*damageMultiplier;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move string_shot = new Move(
        "String Shot",
        TypeList.bug,
        Category.Status,
        40,
        0,
        95,
        0,
        MoveTarget.AllOpponents,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Spe).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move struggle = new Move(
        "Struggle",
        TypeList.normal,
        Category.Physical,
        1,
        50, 1,
        -1,
        1,
        false,
        0,
        MoveTarget.RandomOpponent,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.CallType) {
                return TypeList.typeless;
            }
            if (condition == MoveEffectActivation.AfterMove) {
                int recoilDamage = Integer.max((int) Math.floor(user.getHP()/4), 1);
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }
                String message = user.getName(true, true) + " was damaged by the recoil!";
                Damage.indirectDamage(user, user, recoilDamage, DamageSource.Recoil, thisMove, message, false);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallType,
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails,
            InherentProperty.EncoreFails,
            InherentProperty.Recoil
        }
    );

    public static final Move stun_spore = new Move(
        "Stun Spore",
        TypeList.grass,
        Category.Status,
        30,
        0,
        75,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.paralysis.apply(pokemon, thisMove, showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Powder
        }
    );

    public static final Move substitute = new Move(
        "Substitute",
        TypeList.normal,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            if (user.getVolatileStatus(StatusConditionList.substitute) == null) {
                int cutHP = Integer.max(user.getHP()/4, 1);

                if (user.getCurrentHP() > cutHP) {
                    int remainingHP = user.getCurrentHP() - cutHP;
                    user.setCurrentHP(remainingHP);

                    StatusConditionList.substitute.apply(user, thisMove, cutHP, true);
                }
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move sucker_punch = new Move(
        "Sucker Punch",
        TypeList.dark,
        Category.Physical,
        5,
        70,
        100,
        1,
        true,
        1,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            Action userAction = Battle.findAction(thisMove, user);
            Action targetAction = Battle.findAction(target, false);

            if (Battle.actionIsAfterOther(userAction, targetAction)) {
                return false;
            }

            if (targetAction.move.getCategory() != null &&
                targetAction.move.getCategory() != Category.Status) {
                return true;
            }
            return false;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse
        }
    );

    public static final Move sunny_day = new Move(
        "Sunny Day",
        TypeList.fire,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.AllFields,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.sun.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move sunsteel_strike = new Move(
        "Sunsteel Strike",
        TypeList.steel,
        Category.Physical,
        5,
        100,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty.IgnoresAbility
        }
    );

    public static final Move superpower = new Move(
        "Superpower",
        TypeList.fighting,
        Category.Physical,
        5,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.Atk).change(-1, thisMove, true, true, false);
            user.getStat(StatName.Def).change(-1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move supersonic = new Move(
        "Supersonic",
        TypeList.normal,
        Category.Status,
        20,
        0,
        55,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.confusion.apply(pokemon, thisMove, (int) Math.ceil(Math.random()*4)+1, showMessages); // 2-5 turnos
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move surf = new Move(
        "Surf",
        TypeList.water,
        Category.Special,
        15,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.AllAdjacent,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move surging_strikes = new Move(
        "Surging Strikes",
        TypeList.water,
        Category.Physical,
        5,
        25, 140,
        100,
        10, // garantido
        true,
        0,
        new int[] {3},
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move swagger = new Move(
        "Swagger",
        TypeList.normal,
        Category.Status,
        15,
        0,
        85,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Atk).change(2, thisMove, false, true, false);
            StatusConditionList.confusion.apply(target, thisMove, (int) Math.ceil(Math.random()*4)+1, true); // 2-5 turnos
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move sweet_kiss = new Move(
        "Sweet Kiss",
        TypeList.fairy,
        Category.Status,
        10,
        0,
        75,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.confusion.apply(pokemon, thisMove, (int) Math.ceil(Math.random()*4)+1, showMessages); // 2-5 turnos
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move sweet_scent = new Move(
        "Sweet Scent",
        TypeList.normal,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.AllOpponents,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Eva).change(-2, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Acc).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move swift = new Move(
        "Swift",
        TypeList.normal,
        Category.Special,
        20,
        60,
        -1,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move switcheroo = new Move(
        "Switcheroo",
        TypeList.dark,
        Category.Status,
        10,
        0,
        100,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                boolean userRemovable = (!user.getItem().heldByValidUser(true) || !user.getItem().isTetheredToValidUser()) && user.getItem().getType() != ItemType.ZCrystal;
                boolean userGivable = !target.getItem().isValidUser(user) || !target.getItem().isTetheredToValidUser();
                boolean targetRemovable = (!target.getItem().heldByValidUser(true) || !target.getItem().isTetheredToValidUser()) && target.getItem().getType() != ItemType.ZCrystal;
                boolean targetGivable = !user.getItem().isValidUser(target) || !user.getItem().isTetheredToValidUser();

                return userRemovable && targetRemovable && userGivable && targetGivable &&
                        (
                            !user.getItem().compare(ItemList.none) ||
                            !target.getItem().compare(ItemList.none)
                        );
            }
            if (condition == MoveEffectActivation.AfterMove) {
                Item userItem = user.takeItem();
                Item targetItem = target.takeItem();
                user.giveItem(targetItem);
                target.giveItem(userItem);

                System.out.println(user.getName(true, true) + " switched items with its target!");
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(2, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move swords_dance = new Move(
        "Swords Dance",
        TypeList.normal,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Atk).change(2, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move synthesis = new Move(
        "Synthesis",
        TypeList.grass,
        Category.Status,
        5,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            int healedDamage;
            if (Battle.getWeather().compare(FieldConditionList.clear) || Battle.getWeather().compare(FieldConditionList.delta_stream)) {
                healedDamage = Integer.max((int) Math.floor(user.getHP() * 1/2), 1);
            } else if (Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land)) {
                healedDamage = Integer.max((int) Math.floor(user.getHP() * 2/3), 1);
            } else {
                healedDamage = Integer.max((int) Math.floor(user.getHP() * 1/4), 1);
            }
            Damage.heal(user, thisMove, healedDamage, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move tackle = new Move(
        "Tackle",
        TypeList.normal,
        Category.Physical,
        35,
        40,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move tail_glow = new Move(
        "Tail Glow",
        TypeList.bug,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.SpA).change(3, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move tail_slap = new Move(
        "Tail Slap",
        TypeList.normal,
        Category.Physical,
        10,
        25, 140,
        85,
        1,
        true,
        0,
        new int[] {2, 5},
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move tail_whip = new Move(
        "Tail Whip",
        TypeList.normal,
        Category.Status,
        30,
        0,
        100,
        0,
        MoveTarget.AllOpponents,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Def).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Atk).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move tailwind = new Move(
        "Tailwind",
        TypeList.flying,
        Category.Status,
        15,
        0,
        -1,
        0,
        MoveTarget.UserField,
        (thisMove, user, _, _, _, _, _, _) -> {
            FieldConditionList.tailwind.apply(user.getTeam(), 4, thisMove, true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return StatusConditionList.pumped.apply(user, thisMove, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move take_down = new Move(
        "Take Down",
        TypeList.normal,
        Category.Physical,
        20,
        90,
        85,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            int recoilDamage = damage.amount/4;
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            String message = user.getName(true, true) + " was damaged by the recoil!";
            Damage.indirectDamage(user, user, recoilDamage, DamageSource.Recoil, thisMove, message, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.Recoil
        }
    );

    public static final Move take_heart = new Move(
        "Take Heart",
        TypeList.psychic,
        Category.Status,
        15,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.SpA).change(1, thisMove, true, true, false);
            user.getStat(StatName.SpD).change(1, thisMove, true, true, false);
            user.endNonVolatileStatus(true);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (_, user, _, _, _, _, _, _) -> {
            boolean reset = false;
            for (Stat stat : user.getStats()) {
                if (stat.getStages(null, null) < 0) {
                    stat.setStages(0);
                    reset = true;
                }
            }
            if (reset) {
                GeneralMessages.stat_change.print("reset Z", user);
            }
            return reset;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move taunt = new Move(
        "Taunt",
        TypeList.dark,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            int counter = 4;
            boolean userMoved = false;
            boolean targetMoved = false;

            for (PriorityBracket priorityBracket : Battle.actionOrder) {
                for (Action action : priorityBracket.actions) {
                    if (action.user == user) {
                        userMoved = true;
                    }
                    if (action.user == target) {
                        targetMoved = true;
                    }

                    if (userMoved && !targetMoved) {
                        counter = 3;
                        break;
                    }
                    if (!userMoved && targetMoved) {
                        counter = 4;
                        break;
                    }
                }
                if (userMoved && !targetMoved ||
                    !userMoved && targetMoved) {
                    break;
                }
            }

            StatusConditionList.taunt.apply(target, thisMove, counter, true);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Atk).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move tearful_look = new Move(
        "Tearful Look",
        TypeList.normal,
        Category.Status,
        20,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Atk).change(-1, thisMove, false, true, false);
            target.getStat(StatName.SpA).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresProtection
        }
    );

    public static final Move techno_blast = new Move(
        "Techno Blast",
        TypeList.normal,
        Category.Special,
        5,
        120,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, user, _, type, _, _, _, _) -> {
            if (user.getItem().getType() == ItemType.Drive &&
                user.getItem().shouldActivate(null)) {
                return user.getItem().getChangesTypeTo();
            }
            return type;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallType
        }
    );

    public static final Move teeter_dance = new Move(
        "Teeter Dance",
        TypeList.normal,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.AllAdjacent,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.confusion.apply(pokemon, thisMove, (int) Math.ceil(Math.random()*4)+1, showMessages); // 2-5 turnos
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move teleport = new Move(
        "Teleport",
        TypeList.psychic,
        Category.Status,
        20,
        0,
        -1,
        -6,
        MoveTarget.User,
        (thisMove, user, target, _, _, _, _, _) -> {
            Action moveLocation = Battle.findAction(thisMove, user);
            Move switchMove = new Move(MoveList._switch_, user);
            switchMove.addProperty(TemporaryProperty._Pivot_);
            Battle.addAction(new Action(switchMove, user, user), moveLocation);
            if (thisMove.isZPowered()) {
                thisMove.activateZEffect(user, target, null, null, 0, true, MoveEffectActivation.ZPrimarySuccess);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.ZPrimarySuccess) {
                boolean teamFainted = true;
                for (Pokemon pokemon : Battle.teams[user.getTeam()]) {
                    if (pokemon != null &&
                        pokemon != user &&
                        !Battle.faintCheck(pokemon, false)) {
                        teamFainted = false;
                    }
                }

                if (!teamFainted) {
                    Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));
                }
            }
            if (condition == MoveEffectActivation.DelayedSwitch) {
                if (target.getCurrentHP() >= target.getHP() &&
                    target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                    return null;
                }

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");

                System.out.println("The Z-Power restored " + target.getName(true, false) + "'s health!");

                Damage.heal(target, thisMove, target.getHP(), true, false);

                int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                Battle.delayedMoves.get(user.getTeam()).set(index, new Move(MoveList._placeholder_, user));

                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZPrimarySuccess,
            MoveEffectActivation.ZDelayedSwitch
        },
        new InherentProperty[] {
            InherentProperty.NotSnatchable
        }
    );

    public static final Move tera_starstorm = new Move(
        "Tera Starstorm",
        TypeList.normal,
        Category.Special,
        5,
        120,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, user, _, type, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.CallType) {
                if (user.compareWithForm(PokemonList.terapagos_stellar)) {
                    return TypeList.stellar;
                }
                return type;
            }

            if (condition == MoveEffectActivation.CallMoveTarget) {
                if (user.compareWithForm(PokemonList.terapagos_stellar)) {
                    return MoveTarget.AllOpponents;
                }
                return MoveTarget.Normal;
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallType,
            MoveEffectActivation.CallMoveTarget
        }
    );

    public static final Move thousand_arrows = new Move(
        "Thousand Arrows",
        TypeList.ground,
        Category.Physical,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        (thisMove, _, target, _, _, _, _, _) -> {
            if (!target.isGrounded(null)) {
                StatusConditionList.grounded.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.HitsAirborne
        }
    );

    public static final Move thousand_waves = new Move(
        "Thousand Waves",
        TypeList.ground,
        Category.Physical,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        (thisMove, user, target, _, _, _, _, _) -> {
            StatusConditionList.trapped.apply(target, thisMove, user, true);
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move thrash = new Move(
        "Thrash",
        TypeList.normal,
        Category.Physical,
        10,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            StatusCondition rampage = user.getVolatileStatus(StatusConditionList.rampage);
            if (rampage == null) {
                user.setReadiedMove(thisMove);
                StatusConditionList.rampage.apply(user, thisMove, (int) Math.ceil(Math.random()*2), true); // 2-3 turnos (primeiro não é contado)
            } else if (rampage.getCounter() <= 0) {
                user.endVolatileStatus(rampage, true);
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move throat_chop = new Move(
        "Throat Chop",
        TypeList.dark,
        Category.Physical,
        15,
        80,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            StatusConditionList.throat_chop.apply(target, thisMove, 2, true);
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move thunder = new Move(
        "Thunder",
        TypeList.electric,
        Category.Special,
        10,
        110,
        70,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AccuracyCalc) {
                if (Battle.getWeather().compare(FieldConditionList.rain) || Battle.getWeather().compare(FieldConditionList.primordial_sea)) {
                    return -1.0;
                } else if (Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land)) {
                    return 50.0;
                } else {
                    return 70.0;
                }
            }
            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                double chance = 0.3;
                if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                    chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                }

                if (Math.random() < chance) {
                    StatusConditionList.paralysis.apply(target, thisMove, true);
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.AccuracyCalc
        }
    );

    public static final Move thunder_cage = new Move(
        "Thunder Cage",
        TypeList.electric,
        Category.Special,
        15,
        80,
        90,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (target.getVolatileStatus(StatusConditionList.bind) == null) {
                StatusConditionList.bind.apply(target, thisMove, (int) Math.ceil(Math.random()*2)+3, user, true); // 4-5 turnos
                if (!Battle.faintCheck(target, false)) {
                    System.out.println(user.getName(true, true) + " trapped " + target.getName(true, false) + "!");
                }
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move thunder_fang = new Move(
        "Thunder Fang",
        TypeList.electric,
        Category.Physical,
        15,
        65,
        95,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance1 = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance1 *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance1) {
                StatusConditionList.paralysis.apply(target, thisMove, true);
            }


            double chance2 = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance2 *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance2) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move thunder_punch = new Move(
        "Thunder Punch",
        TypeList.electric,
        Category.Physical,
        15,
        75,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.paralysis.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Punch
        }
    );

    public static final Move thunder_shock = new Move(
        "Thunder Shock",
        TypeList.electric,
        Category.Special,
        30,
        40,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.paralysis.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move thunder_wave = new Move(
        "Thunder Wave",
        TypeList.electric,
        Category.Status,
        20,
        0,
        90,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.paralysis.apply(pokemon, thisMove, showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.TypeChartAffected
        }
    );

    public static final Move thunderbolt = new Move(
        "Thunderbolt",
        TypeList.electric,
        Category.Special,
        15,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.paralysis.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move thunderous_kick = new Move(
        "Thunderous Kick",
        TypeList.fighting,
        Category.Physical,
        10,
        90,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Def).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move tickle = new Move(
        "Tickle",
        TypeList.normal,
        Category.Status,
        20,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            target.getStat(StatName.Atk).change(-1, thisMove, false, true, false);
            target.getStat(StatName.Def).change(-1, thisMove, false, true, false);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move torch_song = new Move(
        "Torch Song",
        TypeList.fire,
        Category.Special,
        10,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.SpA).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move torment = new Move(
        "Torment",
        TypeList.dark,
        Category.Status,
        15,
        0,
        100,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            StatusConditionList.torment.apply(target, thisMove, true);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move toxic = new Move(
        "Toxic",
        TypeList.poison,
        Category.Status,
        10,
        0,
        90,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, showMessages, condition) -> {
            if (condition != MoveEffectActivation.HitGuarantee) {
                Pokemon pokemon;
                if (condition != MoveEffectActivation.TestImmunities) {
                    pokemon = target;
                } else {
                    pokemon = new Pokemon(target, target.getTeam());
                    if (target.getAbility().isActive()) {
                        pokemon.getAbility().setActive(true);
                    }
                }

                return StatusConditionList.bad_poison.apply(pokemon, thisMove, 1, showMessages);
            } else {
                for (Type userType : user.getTypes()) {
                    if (userType.compare(TypeList.poison)) {
                        thisMove.addProperty(TemporaryProperty.CantMiss);
                        break;
                    }
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities,
            MoveEffectActivation.HitGuarantee
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpA).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move transform = new Move(
        "Transform",
        TypeList.normal,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            if (user.transform(target, true)) {
                System.out.println(user.getName(true, true) + " transformed into " + target.getSpecies());
                user.transform(target, false);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return Damage.heal(user, thisMove, user.getHP(), true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresProtection,
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails,
            InherentProperty.EncoreFails,
            InherentProperty.NotReflectable
        }
    );

    public static final Move tri_attack = new Move(
        "Tri Attack",
        TypeList.normal,
        Category.Special,
        10,
        80,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.2;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                switch ((int) (Math.random()*3)) {
                    case 0:
                        StatusConditionList.burn.apply(target, thisMove, true);
                        break;

                    case 1:
                        StatusConditionList.paralysis.apply(target, thisMove, true);
                        break;

                    case 2:
                        StatusConditionList.frostbite.apply(target, thisMove, true);
                        break;

                    default:
                        break;
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move trick = new Move(
        "Trick",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        100,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                boolean userRemovable = (!user.getItem().heldByValidUser(true) || !user.getItem().isTetheredToValidUser()) && user.getItem().getType() != ItemType.ZCrystal;
                boolean userGivable = !target.getItem().isValidUser(user) || !target.getItem().isTetheredToValidUser();
                boolean targetRemovable = (!target.getItem().heldByValidUser(true) || !target.getItem().isTetheredToValidUser()) && target.getItem().getType() != ItemType.ZCrystal;
                boolean targetGivable = !user.getItem().isValidUser(target) || !user.getItem().isTetheredToValidUser();

                return userRemovable && targetRemovable && userGivable && targetGivable &&
                        (
                            !user.getItem().compare(ItemList.none) ||
                            !target.getItem().compare(ItemList.none)
                        );
            }
            if (condition == MoveEffectActivation.AfterMove) {
                Item userItem = user.takeItem();
                Item targetItem = target.takeItem();
                user.giveItem(targetItem);
                target.giveItem(userItem);

                System.out.println(user.getName(true, true) + " switched items with its target!");
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(2, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.NotReflectable
        }
    );

    public static final Move trick_room = new Move(
        "Trick Room",
        TypeList.psychic,
        Category.Status,
        5,
        0,
        -1,
        -7,
        MoveTarget.AllFields,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.trick_room.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Acc).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move triple_arrows = new Move(
        "Triple Arrows",
        TypeList.fighting,
        Category.Physical,
        10,
        90,
        100,
        2,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance1 = 0.5;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance1 *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance1) {
                target.getStat(StatName.Def).change(-1, thisMove, false, true, false);
            }


            double chance2 = 0.3;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance2 *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance2) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move twister = new Move(
        "Twister",
        TypeList.dragon,
        Category.Special,
        20,
        40,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.2;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move u_turn = new Move(
        "U-turn",
        TypeList.bug,
        Category.Physical,
        20,
        70,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            Action moveLocation = Battle.findAction(thisMove, user);
            Move switchMove = new Move(MoveList._switch_, user);
            switchMove.addProperty(TemporaryProperty._Pivot_);
            Battle.addAction(new Action(switchMove, user, user), moveLocation);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move uproar = new Move(
        "Uproar",
        TypeList.normal,
        Category.Special,
        10,
        90,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            StatusCondition locked = user.getVolatileStatus(StatusConditionList.locked);

            if (locked == null) {
                user.setReadiedMove(thisMove);
                StatusConditionList.locked.apply(user, thisMove, 2, true); // 3 turnos (primeiro não é contado)
                if (Battle.faintCheck(target, false)) {
                    System.out.println();
                }
                FieldConditionList.uproar.apply(thisMove, -1, true);
            } else if (locked.getCounter() <= 0) {
                user.endVolatileStatus(locked, true);
            }

            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Sound
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move v_create = new Move(
        "V-create",
        TypeList.fire,
        Category.Physical,
        5,
        180, 220,
        95,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            user.getStat(StatName.Def).change(-1, thisMove, true, true, false);
            user.getStat(StatName.SpD).change(-1, thisMove, true, true, false);
            user.getStat(StatName.Spe).change(-1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move vacuum_wave = new Move(
        "Vacuum Wave",
        TypeList.fighting,
        Category.Special,
        30,
        40,
        100,
        1,
        false,
        1,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move veiled_assault = new Move( // fanmade
        "Veiled Assault",
        TypeList.grass,
        Category.Physical,
        10,
        70,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty.IgnoresProtection
        }
    );

    public static final Move venoshock = new Move(
        "Venoshock",
        TypeList.poison,
        Category.Special,
        10,
        65,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, target, _, _, _, _, _) -> {
            if (target.getNonVolatileStatus().compare(StatusConditionList.poison) ||
                target.getNonVolatileStatus().compare(StatusConditionList.bad_poison)) {
                return 130.0;
            }
            return 65.0;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move vine_whip = new Move(
        "Vine Whip",
        TypeList.grass,
        Category.Physical,
        25,
        45,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move vise_grip = new Move(
        "Vise Grip",
        TypeList.normal,
        Category.Physical,
        30,
        55,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move volt_switch = new Move(
        "Volt Switch",
        TypeList.electric,
        Category.Special,
        20,
        70,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, _, _, _, _, _, _) -> {
            Action moveLocation = Battle.findAction(thisMove, user);
            Move switchMove = new Move(MoveList._switch_, user);
            switchMove.addProperty(TemporaryProperty._Pivot_);
            Battle.addAction(new Action(switchMove, user, user), moveLocation);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move water_gun = new Move(
        "Water Gun",
        TypeList.water,
        Category.Special,
        25,
        40,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move water_pulse = new Move(
        "Water Pulse",
        TypeList.water,
        Category.Special,
        20,
        60,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.2;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.confusion.apply(target, thisMove, (int) Math.ceil(Math.random()*4)+1, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.Pulse
        }
    );

    public static final Move water_shuriken = new Move(
        "Water Shuriken",
        TypeList.water,
        Category.Special,
        20,
        15, 100,
        100,
        1,
        false,
        1,
        new int[] {2, 5},
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move water_spout = new Move(
        "Water Spout",
        TypeList.water,
        Category.Special,
        5,
        150,
        100,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        (_, user, _, _, _, _, _, _) -> {
            return Math.max(150.0*user.getCurrentHP()/user.getHP(), 1.0);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallPower
        }
    );

    public static final Move wave_crash = new Move(
        "Wave Crash",
        TypeList.water,
        Category.Physical,
        10,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            int recoilDamage = damage.amount/3;
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            String message = user.getName(true, true) + " was damaged by the recoil!";
            Damage.indirectDamage(user, user, recoilDamage, DamageSource.Recoil, thisMove, message, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.Recoil
        }
    );

    public static final Move weather_ball = new Move(
        "Weather Ball",
        TypeList.normal,
        Category.Special,
        10,
        50, 160,
        100,
        1,
        false,
        0,
        MoveTarget.Normal,
        (_, _, _, type, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.CallType || condition == MoveEffectActivation.ZCallType) {
                if (Battle.getWeather().compare(FieldConditionList.sun) || Battle.getWeather().compare(FieldConditionList.desolate_land)) {
                    return TypeList.fire;
                } else if (Battle.getWeather().compare(FieldConditionList.rain) || Battle.getWeather().compare(FieldConditionList.primordial_sea)) {
                    return TypeList.water;
                } else if (Battle.getWeather().compare(FieldConditionList.sand)) {
                    return TypeList.rock;
                } else if (Battle.getWeather().compare(FieldConditionList.snow)) {
                    return TypeList.ice;
                } else {
                    return type;
                }
            }
            if (condition == MoveEffectActivation.CallPower) {
                if (!Battle.getWeather().compare(FieldConditionList.clear) && !Battle.getWeather().compare(FieldConditionList.delta_stream)) {
                    return 100.0;
                }
                return 50.0;
            }
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallType,
            MoveEffectActivation.ZCallType,
            MoveEffectActivation.CallPower
        },
        new MoveType[] {
            MoveType.BallBomb
        }
    );

    public static final Move whirlpool = new Move(
        "Whirlpool",
        TypeList.water,
        Category.Special,
        15,
        35,
        85,
        1,
        false,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (target.getVolatileStatus(StatusConditionList.bind) == null) {
                StatusConditionList.bind.apply(target, thisMove, (int) Math.ceil(Math.random()*2)+3, user, true); // 4-5 turnos
                if (!Battle.faintCheck(target, false)) {
                    System.out.println(target.getName(true, true) + " became trapped in the vortex!");
                }
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move whirlwind = new Move(
        "Whirlwind",
        TypeList.normal,
        Category.Status,
        20,
        0,
        -1,
        -6,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            if (Arrays.asList(target.getAbility().getConditions()).contains(AbilityActivation.TryForceSwitch) &&
                !(boolean) target.getAbility().activate(target, user, thisMove, null, damage, null, null, 0, AbilityActivation.TryForceSwitch)) {
                return null;
            }

            for (StatusCondition statusCondition : target.getVolatileStatusList()) {
                if (Arrays.asList(statusCondition.getActivation()).contains(StatusActivation.TryForceSwitch) &&
                    !(boolean) statusCondition.activate(target, user, thisMove, damage, true, StatusActivation.TryForceSwitch)) {
                    return null;
                }
            }

            Action moveLocation = Battle.findAction(thisMove, user);
            Move switchMove = new Move(MoveList._switch_, target);
            switchMove.addProperty(TemporaryProperty._Forced_);
            Battle.addAction(new Action(switchMove, target, target), moveLocation);

            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresProtection,
            InherentProperty.IgnoresSubstitute,
            InherentProperty.CopycatFails
        }
    );

    public static final Move wicked_blow = new Move(
        "Wicked Blow",
        TypeList.dark,
        Category.Physical,
        5,
        75,
        100,
        10, // garantido
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move wide_guard = new Move(
        "Wide Guard",
        TypeList.rock,
        Category.Status,
        10,
        0,
        -1,
        3,
        MoveTarget.UserField,
        (thisMove, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                Action userAction = Battle.findAction(thisMove, user);
                return Battle.nextMove(userAction) != null;
            }
            if (condition == MoveEffectActivation.AfterMove) {
                FieldConditionList.wide_guard.apply(user.getTeam(), 0, thisMove, true);
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails
        }
    );

    public static final Move wild_charge = new Move(
        "Wild Charge",
        TypeList.electric,
        Category.Physical,
        15,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            int recoilDamage = damage.amount/3;
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            String message = user.getName(true, true) + " was damaged by the recoil!";
            Damage.indirectDamage(user, user, recoilDamage, DamageSource.Recoil, thisMove, message, false);
            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.1;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.paralysis.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.Recoil
        }
    );

    public static final Move wildbolt_storm = new Move(
        "Wildbolt Storm",
        TypeList.electric,
        Category.Special,
        10,
        100,
        80,
        1,
        false,
        0,
        MoveTarget.AllOpponents,
        (_, _, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AccuracyCalc) {
                if (Battle.getWeather().compare(FieldConditionList.rain)) {
                    return -1.0;
                } else {
                    return 80.0;
                }
            }
            return null;
        },
        EffectTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                double chance = 0.2;
                if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                    chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
                }

                if (Math.random() < chance) {
                    StatusConditionList.paralysis.apply(target, thisMove, true);
                }
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.AccuracyCalc
        }
    );

    public static final Move will_o_wisp = new Move(
        "Will-O-Wisp",
        TypeList.fire,
        Category.Status,
        15,
        0,
        85,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, showMessages, condition) -> {
            Pokemon pokemon;
            if (condition != MoveEffectActivation.TestImmunities) {
                pokemon = target;
            } else {
                pokemon = new Pokemon(target, target.getTeam());
                if (target.getAbility().isActive()) {
                    pokemon.getAbility().setActive(true);
                }
            }

            return StatusConditionList.burn.apply(pokemon, thisMove, showMessages);
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.TestImmunities
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Atk).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move wing_attack = new Move(
        "Wing Attack",
        TypeList.flying,
        Category.Physical,
        35,
        60,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move wish = new Move(
        "Wish",
        TypeList.normal,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, target, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.AfterMove) {
                Battle.delayedMoves.get(user.getTeam()).add(new Move(thisMove, user));
                System.out.println(user.getName(true, true) + " made a wish!");
            }
            if (condition == MoveEffectActivation.DelayedTurnEnd) {
                if (thisMove.getPrimaryEffectCounter() <= 0) {
                    if (!Battle.faintCheck(target, false)) {
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                        System.out.println(user.getName(true, true) + "'s wish came true!");
                        Damage.heal(target, thisMove, user.getHP()/2, true, false);
                        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                    }

                    int index = Battle.delayedMoves.get(user.getTeam()).indexOf(thisMove);
                    Battle.delayedMoves.get(user.getTeam()).set(index, new Move(MoveList._placeholder_, user));
                }
            }
            return null;
        },
        EffectTarget.User,
        2,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove,
            MoveEffectActivation.DelayedTurnEnd
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new MoveType[] {
            MoveType.Delayed
        }
    );

    public static final Move withdraw = new Move(
        "Withdraw",
        TypeList.water,
        Category.Status,
        40,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Def).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Def).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move wonder_room = new Move(
        "Wonder Room",
        TypeList.psychic,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.AllFields,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.wonder_room.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.SpD).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move wood_hammer = new Move(
        "Wood Hammer",
        TypeList.grass,
        Category.Physical,
        15,
        120,
        100,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, damage, _, _, _) -> {
            int recoilDamage = damage.amount/3;
            if (Battle.faintCheck(target, false)) {
                System.out.println();
            }
            String message = user.getName(true, true) + " was damaged by the recoil!";
            Damage.indirectDamage(user, user, recoilDamage, DamageSource.Recoil, thisMove, message, false);
            return null;
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new InherentProperty[] {
            InherentProperty.Recoil
        }
    );

    public static final Move work_up = new Move(
        "Work Up",
        TypeList.normal,
        Category.Status,
        30,
        0,
        -1,
        0,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, _) -> {
            user.getStat(StatName.Atk).change(1, thisMove, true, true, false);
            user.getStat(StatName.SpA).change(1, thisMove, true, true, false);
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Atk).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move worry_seed = new Move(
        "Worry Seed",
        TypeList.grass,
        Category.Status,
        10,
        0,
        100,
        0,
        MoveTarget.Normal,
        (_, user, target, _, _, _, _, _) -> {
            if (!target.getAbility().isNotReplaceable() &&
                !target.getAbility().compare(AbilityList.insomnia)) {
                System.out.println(target.getName(true, true) + "'s ability changed to Insomnia!");
                target.setAbility(AbilityList.insomnia, true, user);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        }
    );

    public static final Move wrap = new Move(
        "Wrap",
        TypeList.normal,
        Category.Physical,
        20,
        15,
        90,
        1,
        true,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            if (target.getVolatileStatus(StatusConditionList.bind) == null) {
                StatusConditionList.bind.apply(target, thisMove, (int) Math.ceil(Math.random()*2)+3, user, true); // 4-5 turnos
                if (!Battle.faintCheck(target, false)) {
                    System.out.println(target.getName(true, true) + " was wrapped by " + user.getName(true, false) + "!");
                }
            }
            return null;
        },
        EffectTarget.Target,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move x_scissor = new Move(
        "X-Scissor",
        TypeList.bug,
        Category.Physical,
        15,
        80,
        100,
        2,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new MoveType[] {
            MoveType.Slicing
        }
    );

    public static final Move yawn = new Move(
        "Yawn",
        TypeList.normal,
        Category.Status,
        10,
        0,
        -1,
        0,
        MoveTarget.Normal,
        (thisMove, _, target, _, _, _, _, _) -> {
            if (target.getNonVolatileStatus().compare(StatusConditionList.none)) {
                StatusConditionList.drowsiness.apply(target, thisMove, 1, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        (thisMove, user, _, _, _, _, _, _) -> {
            return user.getStat(StatName.Spe).change(1, thisMove, true, true, true);
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.ZNormal
        },
        new InherentProperty[] {
            InherentProperty.IgnoresSubstitute
        }
    );

    public static final Move zap_cannon = new Move(
        "Zap Cannon",
        TypeList.electric,
        Category.Special,
        5,
        120,
        50,
        1,
        false,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, target, _, _, _, _, _) -> {
            StatusConditionList.paralysis.apply(target, thisMove, true);
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        },
        new MoveType[] {
            MoveType.BallBomb
        }
    );

    public static final Move zen_headbutt = new Move(
        "Zen Headbutt",
        TypeList.psychic,
        Category.Physical,
        15,
        80,
        90,
        1,
        true,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, user, target, _, _, _, _, _) -> {
            double chance = 0.2;
            if (user.getAbility().shouldActivate(AbilityActivation.EffectChanceCalc)) {
                chance *= (double) user.getAbility().activate(user, target, thisMove, null, null, null, null, 0, AbilityActivation.EffectChanceCalc);
            }

            if (Math.random() < chance) {
                StatusConditionList.flinch.apply(target, thisMove, true);
            }
            return null;
        },
        EffectTarget.Target,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );



    // Z-Moves

    public static final Move breakneck_blitz = new Move(
        "Breakneck Blitz",
        true, false,
        TypeList.normal,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move all_out_pummeling = new Move(
        "All-Out Pummeling",
        true, false,
        TypeList.fighting,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move supersonic_skystrike = new Move(
        "Supersonic Skystrike",
        true, false,
        TypeList.flying,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move acid_downpour = new Move(
        "Acid Downpour",
        true, false,
        TypeList.poison,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move tectonic_rage = new Move(
        "Tectonic Rage",
        true, false,
        TypeList.ground,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move continental_crush = new Move(
        "Continental Crush",
        true, false,
        TypeList.rock,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move savage_spin_out = new Move(
        "Savage Spin-Out",
        true, false,
        TypeList.bug,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move never_ending_nightmare = new Move(
        "Never-Ending Nightmare",
        true, false,
        TypeList.ghost,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move corkscrew_crash = new Move(
        "Corkscrew Crash",
        true, false,
        TypeList.steel,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move inferno_overdrive = new Move(
        "Inferno Overdrive",
        true, false,
        TypeList.fire,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move hydro_vortex = new Move(
        "Hydro Vortex",
        true, false,
        TypeList.water,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move bloom_doom = new Move(
        "Bloom Doom",
        true, false,
        TypeList.grass,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move gigavolt_havoc = new Move(
        "Gigavolt Havoc",
        true, false,
        TypeList.electric,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move shattered_psyche = new Move(
        "Shattered Psyche",
        true, false,
        TypeList.psychic,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move subzero_slammer = new Move(
        "Subzero Slammer",
        true, false,
        TypeList.ice,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move devastating_drake = new Move(
        "Devastating Drake",
        true, false,
        TypeList.dragon,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move black_hole_eclipse = new Move(
        "Black Hole Eclipse",
        true, false,
        TypeList.dark,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move twinkle_tackle = new Move(
        "Twinkle Tackle",
        true, false,
        TypeList.fairy,
        1,
        -1,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move genesis_supernova = new Move(
        "Genesis Supernova",
        true, true,
        TypeList.psychic,
        Category.Special,
        1,
        185,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        (thisMove, _, _, _, _, _, _, _) -> {
            FieldConditionList.psychic_terrain.apply(thisMove, false, true);
            return null;
        },
        EffectTarget.All,
        new MoveEffectActivation[] {
            MoveEffectActivation.AfterMove
        }
    );

    public static final Move sinister_arrow_raid = new Move(
        "Sinister Arrow Raid",
        true, true,
        TypeList.ghost,
        Category.Physical,
        1,
        180,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move malicious_moonsault = new Move(
        "Malicious Moonsault",
        true, true,
        TypeList.dark,
        Category.Physical,
        1,
        180,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move oceanic_operetta = new Move(
        "Oceanic Operetta",
        true, true,
        TypeList.water,
        Category.Special,
        1,
        195,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );

    public static final Move guardian_of_alola = new Move(
        "Guardian of Alola",
        true, true,
        TypeList.fairy,
        Category.Special,
        1,
        0,
        -1,
        1,
        0,
        MoveTarget.Normal,
        (thisMove, user, target, _, _, _, _, _) -> {
            int moveDamage = (int) (target.getCurrentHP()*0.75);

            // dano fixo não passa por cálculo, então OpponentDamageCalc tem que ser ativado aqui
            if (target.getVolatileStatus(StatusConditionList.protection) != null) {
                moveDamage *= (double) target.getVolatileStatus(StatusConditionList.protection).activate(target, user, thisMove, new Damage(moveDamage, DamageSource.Move), true, StatusActivation.OpponentDamageCalc);
            }

            return Integer.max(moveDamage, 1);
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.FixedDamage
        }
    );

    public static final Move searing_sunraze_smash = new Move(
        "Searing Sunraze Smash",
        true, true,
        TypeList.steel,
        Category.Physical,
        1,
        200,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty.IgnoresAbility
        }
    );

    public static final Move menacing_moonraze_maelstrom = new Move(
        "Menacing Moonraze Maelstrom",
        true, true,
        TypeList.ghost,
        Category.Special,
        1,
        200,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty.IgnoresAbility
        }
    );

    public static final Move light_that_burns_the_sky = new Move(
        "Light That Burns the Sky",
        true, true,
        TypeList.psychic,
        Category.Special,
        1,
        200,
        -1,
        1,
        0,
        MoveTarget.Normal,
        (_, user, _, _, _, _, _, _) -> {
            int userAtk = user.getStat(StatName.Atk).getValue();
            int userAtkStages = user.getStat(StatName.Atk).getStages(null, null);
            double valAtk = 1 + Math.abs(userAtkStages)*0.5;
            userAtk = (int) (userAtkStages >= 0 ? userAtk*valAtk : userAtk/valAtk);

            int userSpA = user.getStat(StatName.SpA).getValue();
            int userSpAStages = user.getStat(StatName.SpA).getStages(null, null);
            double valSpA = 1 + Math.abs(userSpAStages)*0.5;
            userSpA = (int) (userSpAStages >= 0 ? userSpA*valSpA : userSpA/valSpA);

            if (userAtk > userSpA) {
                return Category.Physical;
            } else {
                return Category.Special;
            }
        },
        EffectTarget.User,
        null,
        null,
        new MoveEffectActivation[] {
            MoveEffectActivation.CallCategory
        },
        new InherentProperty[] {
            InherentProperty.IgnoresAbility
        }
    );

    public static final Move soul_stealing_7_star_strike = new Move(
        "Soul-Stealing 7-Star Strike",
        true, true,
        TypeList.ghost,
        Category.Physical,
        1,
        195,
        -1,
        1,
        0,
        MoveTarget.Normal,
        null,
        null,
        null,
        null,
        new MoveEffectActivation[0]
    );



    // Max Moves

    public static final Move max_guard = new Move(
        "Max Guard",
        TypeList.normal,
        Category.Status,
        1,
        0,
        -1,
        4,
        MoveTarget.User,
        (thisMove, user, _, _, _, _, _, condition) -> {
            if (condition == MoveEffectActivation.TryUse) {
                Action userAction = Battle.findAction(thisMove, user);
                if (Battle.nextMove(userAction) == null) {
                    user.setConsecutiveProtections(0);
                    return false;
                }

                double chance = 1*(Math.pow(1.0/3.0, user.getConsecutiveProtections()));
                if (Math.random() < chance) {
                    return true;
                }

                user.setConsecutiveProtections(0);
                return false;
            }
            if (condition == MoveEffectActivation.AfterMove) {
                StatusConditionList.protection.apply(user, thisMove, true);
                user.addConsecutiveProtection();
            }
            return null;
        },
        EffectTarget.User,
        new MoveEffectActivation[] {
            MoveEffectActivation.TryUse,
            MoveEffectActivation.AfterMove
        },
        null,
        null,
        new MoveEffectActivation[0],
        new InherentProperty[] {
            InherentProperty.MetronomeUncallable,
            InherentProperty.CopycatFails,
            InherentProperty.NotSnatchable
        }
    );
}
