package data.messages.list;

import java.util.HashMap;
import java.util.Map;

import data.messages.Message;

public class StatusMessages {
    // non-volatile

    public static final Message burn;
    public static final Message paralysis;
    public static final Message poison;
    public static final Message bad_poison;
    public static final Message sleep;
    public static final Message freeze;
    public static final Message frostbite;

    static {
        Map<String, String> burn_map = new HashMap<>();
        Map<String, String> paralysis_map = new HashMap<>();
        Map<String, String> poison_map = new HashMap<>();
        Map<String, String> bad_poison_map = new HashMap<>();
        Map<String, String> sleep_map = new HashMap<>();
        Map<String, String> freeze_map = new HashMap<>();
        Map<String, String> frostbite_map = new HashMap<>();

        burn_map.put("start", "(Pokemon) was burned!");
        burn_map.put("start by ability", "(Causer)'s (Ability) burned (Pokemon)!");
        burn_map.put("start by item", "(Pokemon) was burned by the (Item)!");
        burn_map.put("repeat", "(Pokemon) is already burned!");
        burn_map.put("end", "(Pokemon)'s burn was healed!");

        paralysis_map.put("start", "(Pokemon) is paralyzed, so it may be unable to move!");
        paralysis_map.put("start by ability", "(Causer)'s (Ability) paralyzed (Pokemon)!");
        paralysis_map.put("repeat", "(Pokemon) is already paralyzed!");
        paralysis_map.put("end", "(Pokemon) was cured of paralysis!");

        poison_map.put("start", "(Pokemon) was poisoned!");
        poison_map.put("start by ability", "(Causer)'s (Ability) poisoned (Pokemon)!");
        poison_map.put("repeat", "(Pokemon) is already poisoned!");
        poison_map.put("end", "(Pokemon) was cured of its poisoning!");

        bad_poison_map.put("start", "(Pokemon) was badly poisoned!");
        bad_poison_map.put("start by ability", "(Causer)'s (Ability) badly poisoned (Pokemon)!");
        bad_poison_map.put("start by item", "(Pokemon) was badly poisoned by the (Item)!");
        bad_poison_map.put("repeat", "(Pokemon) is already poisoned!");
        bad_poison_map.put("end", "(Pokemon) was cured of its poisoning!");

        sleep_map.put("start", "(Pokemon) fell asleep!");
        sleep_map.put("start by ability", "(Causer)'s (Ability) put (Pokemon) to sleep!");
        sleep_map.put("repeat", "(Pokemon) is already asleep!");
        sleep_map.put("end", "(Pokemon) woke up!");

        freeze_map.put("start", "(Pokemon) was frozen solid!");
        freeze_map.put("repeat", "(Pokemon) is already frozen solid!");
        freeze_map.put("end", "(Pokemon) thawed out!");

        frostbite_map.put("start", "(Pokemon) got frostbite!");
        frostbite_map.put("start by ability", "(Causer)'s (Ability) frostbit (Pokemon)!");
        frostbite_map.put("repeat", "(Pokemon) is already frostbitten!");
        frostbite_map.put("end", "(Pokemon)'s frostbite was healed!");

        burn = new Message(burn_map);
        paralysis = new Message(paralysis_map);
        poison = new Message(poison_map);
        bad_poison = new Message(bad_poison_map);
        sleep = new Message(sleep_map);
        freeze = new Message(freeze_map);
        frostbite = new Message(frostbite_map);
    }


    // volatile status conditions

    public static final Message confusion;
    public static final Message taunt;
    public static final Message seed;
    public static final Message protection;
    public static final Message torment;
    public static final Message hydrokinesis;
    public static final Message snatch;
    public static final Message substitute;
    public static final Message drowsiness;
    public static final Message endure;
    public static final Message imprison;
    public static final Message curse;
    public static final Message encore;
    public static final Message focus;
    public static final Message pumped;
    public static final Message suppressed_ability;
    public static final Message grounded;
    public static final Message throat_chop;
    public static final Message trapped;
    public static final Message move_disabled;
    public static final Message taking_aim;
    public static final Message aqua_ring;
    public static final Message destiny_bond;
    public static final Message magic_coat;
    public static final Message magnet_rise;
    public static final Message ingrain;
    public static final Message laser_focus;
    public static final Message infatuation;

    static {
        Map<String, String> confusion_map = new HashMap<>();
        Map<String, String> taunt_map = new HashMap<>();
        Map<String, String> seed_map = new HashMap<>();
        Map<String, String> protection_map = new HashMap<>();
        Map<String, String> torment_map = new HashMap<>();
        Map<String, String> hydrokinesis_map = new HashMap<>();
        Map<String, String> snatch_map = new HashMap<>();
        Map<String, String> substitute_map = new HashMap<>();
        Map<String, String> drowsiness_map = new HashMap<>();
        Map<String, String> endure_map = new HashMap<>();
        Map<String, String> imprison_map = new HashMap<>();
        Map<String, String> curse_map = new HashMap<>();
        Map<String, String> encore_map = new HashMap<>();
        Map<String, String> focus_map = new HashMap<>();
        Map<String, String> pumped_map = new HashMap<>();
        Map<String, String> suppressed_ability_map = new HashMap<>();
        Map<String, String> grounded_map = new HashMap<>();
        Map<String, String> throat_chop_map = new HashMap<>();
        Map<String, String> trapped_map = new HashMap<>();
        Map<String, String> move_disabled_map = new HashMap<>();
        Map<String, String> taking_aim_map = new HashMap<>();
        Map<String, String> aqua_ring_map = new HashMap<>();
        Map<String, String> destiny_bond_map = new HashMap<>();
        Map<String, String> magic_coat_map = new HashMap<>();
        Map<String, String> magnet_rise_map = new HashMap<>();
        Map<String, String> ingrain_map = new HashMap<>();
        Map<String, String> laser_focus_map = new HashMap<>();
        Map<String, String> infatuation_map = new HashMap<>();

        confusion_map.put("start", "(Pokemon) became confused!");
        confusion_map.put("start by ability", "(Causer)'s (Ability) confused (Pokemon)!");
        confusion_map.put("repeat", "(Pokemon) is already confused!");
        confusion_map.put("end", "(Pokemon) snapped out of confusion");

        taunt_map.put("start", "(Pokemon) fell for the taunt!");
        taunt_map.put("end", "(Pokemon) shook off the taunt!");

        seed_map.put("start", "(Pokemon) was seeded!");
        seed_map.put("end", "(Pokemon) was freed from Leech Seed");

        protection_map.put("start", "(Pokemon) protected itself!");

        torment_map.put("start", "(Pokemon) was subjected to torment!");
        torment_map.put("end", "(Pokemon) is no longer tormented!");

        hydrokinesis_map.put("start", "(Pokemon) is preparing to control water!");

        snatch_map.put("start", "(Pokemon) is waiting for a target to make a move!");

        substitute_map.put("start", "(Pokemon) cut (Number) HP to put in a substitute!");
        substitute_map.put("repeat", "(Pokemon) already has a substitute!");
        substitute_map.put("end", "(Pokemon)'s substitute faded!");

        drowsiness_map.put("start", "(Pokemon) grew drowsy!");

        endure_map.put("start", "(Pokemon) braced itself!");

        imprison_map.put("start", "(Pokemon) sealed any moves its target shares with it!");

        curse_map.put("start", "(Causer) cut its own HP and put a curse on (Pokemon)!");

        encore_map.put("start", "(Pokemon) must do an encore!");
        encore_map.put("end", "(Pokemon)'s encore ended!");

        focus_map.put("start", "(Pokemon) tightened its focus!");

        pumped_map.put("start", "(Pokemon) is getting pumped!");
        pumped_map.put("start Z", "(Pokemon) boosted its critical-hit ratio using its Z-Power!");

        suppressed_ability_map.put("start", "(Pokemon)'s Ability was suppressed!");

        grounded_map.put("start", "(Pokemon) fell straight down!");

        throat_chop_map.put("start", "(Pokemon)'s throat was injured, preventing it from making sounds!"); /* ! MENSAGEM NÃO OFICIAL ! */
        throat_chop_map.put("end", "(Pokemon)'s throat healed!"); /* ! MENSAGEM NÃO OFICIAL ! */

        trapped_map.put("start", "(Pokemon) can no longer escape!");
        trapped_map.put("end", "(Pokemon) was freed to flee!"); /* ! MENSAGEM NÃO OFICIAL ! */

        move_disabled_map.put("start", "(Pokemon)'s (Move) was disabled!");
        move_disabled_map.put("end", "(Pokemon)'s move is no longer disabled!");

        taking_aim_map.put("start", "(Causer) took aim at (Pokemon)!");

        aqua_ring_map.put("start", "(Pokemon) surrounded itself with a veil of water!");

        destiny_bond_map.put("start", "(Pokemon) is hoping to take its attacker down with it!");

        magic_coat_map.put("start", "(Pokemon) shrouded itself with Magic Coat!");

        magnet_rise_map.put("start", "(Pokemon) levitated with electromagnetism!");
        magnet_rise_map.put("end", "(Pokemon)'s electromagnetism wore off!");

        ingrain_map.put("start", "(Pokemon) planted its roots!");

        laser_focus_map.put("start", "(Pokemon) concentrated intensely!");

        infatuation_map.put("start", "(Pokemon) fell in love!");
        infatuation_map.put("end", "(Pokemon) got over its infatuation");

        confusion = new Message(confusion_map);
        taunt = new Message(taunt_map);
        seed = new Message(seed_map);
        protection = new Message(protection_map);
        torment = new Message(torment_map);
        hydrokinesis = new Message(hydrokinesis_map);
        snatch = new Message(snatch_map);
        substitute = new Message(substitute_map);
        drowsiness = new Message(drowsiness_map);
        endure = new Message(endure_map);
        imprison = new Message(imprison_map);
        curse = new Message(curse_map);
        encore = new Message(encore_map);
        focus = new Message(focus_map);
        pumped = new Message(pumped_map);
        suppressed_ability = new Message(suppressed_ability_map);
        grounded = new Message(grounded_map);
        throat_chop = new Message(throat_chop_map);
        trapped = new Message(trapped_map);
        move_disabled = new Message(move_disabled_map);
        taking_aim = new Message(taking_aim_map);
        aqua_ring = new Message(aqua_ring_map);
        destiny_bond = new Message(destiny_bond_map);
        magic_coat = new Message(magic_coat_map);
        magnet_rise = new Message(magnet_rise_map);
        ingrain = new Message(ingrain_map);
        laser_focus = new Message(laser_focus_map);
        infatuation = new Message(infatuation_map);
    }
}
