package com.github.lucasaugustoss.data.messages.list;

import java.util.Map;

import com.github.lucasaugustoss.data.messages.Message;

public class StatusMessages {
    // non-volatile

    public static final Message burn = new Message(Map.of(
        "start", "(Pokemon) was burned!",
        "start by ability", "(Causer)'s (Ability) burned (Pokemon)!",
        "start by item", "(Pokemon) was burned by the (Item)!",
        "repeat", "(Pokemon) is already burned!",
        "end", "(Pokemon)'s burn was healed!"
    ));

    public static final Message paralysis = new Message(Map.of(
        "start", "(Pokemon) is paralyzed, so it may be unable to move!",
        "start by ability", "(Causer)'s (Ability) paralyzed (Pokemon)!",
        "repeat", "(Pokemon) is already paralyzed!",
        "end", "(Pokemon) was cured of paralysis!"
    ));

    public static final Message poison = new Message(Map.of(
        "start", "(Pokemon) was poisoned!",
        "start by ability", "(Causer)'s (Ability) poisoned (Pokemon)!",
        "repeat", "(Pokemon) is already poisoned!",
        "end", "(Pokemon) was cured of its poisoning!"
    ));

    public static final Message bad_poison = new Message(Map.of(
        "start", "(Pokemon) was badly poisoned!",
        "start by ability", "(Causer)'s (Ability) badly poisoned (Pokemon)!",
        "start by item", "(Pokemon) was badly poisoned by the (Item)!",
        "repeat", "(Pokemon) is already poisoned!",
        "end", "(Pokemon) was cured of its poisoning!"
    ));

    public static final Message sleep = new Message(Map.of(
        "start", "(Pokemon) fell asleep!",
        "start by ability", "(Causer)'s (Ability) put (Pokemon) to sleep!",
        "repeat", "(Pokemon) is already asleep!",
        "end", "(Pokemon) woke up!"
    ));

    public static final Message freeze = new Message(Map.of(
        "start", "(Pokemon) was frozen solid!",
        "repeat", "(Pokemon) is already frozen solid!",
        "end", "(Pokemon) thawed out!"
    ));

    public static final Message frostbite = new Message(Map.of(
        "start", "(Pokemon) got frostbite!",
        "start by ability", "(Causer)'s (Ability) frostbit (Pokemon)!",
        "repeat", "(Pokemon) is already frostbitten!",
        "end", "(Pokemon)'s frostbite was healed!"
    ));


    // volatile status conditions

    public static final Message confusion = new Message(Map.of(
        "start", "(Pokemon) became confused!",
        "start by ability", "(Causer)'s (Ability) confused (Pokemon)!",
        "repeat", "(Pokemon) is already confused!",
        "end", "(Pokemon) snapped out of confusion!"
    ));

    public static final Message taunt = new Message(Map.of(
        "start", "(Pokemon) fell for the taunt!",
        "end", "(Pokemon) shook off the taunt!"
    ));

    public static final Message seed = new Message(Map.of(
        "start", "(Pokemon) was seeded!",
        "end", "(Pokemon) was freed from Leech Seed!"
    ));

    public static final Message protection = new Message(Map.of(
        "start", "(Pokemon) protected itself!"
    ));

    public static final Message torment = new Message(Map.of(
        "start", "(Pokemon) was subjected to torment!",
        "end", "(Pokemon) is no longer tormented!"
    ));

    public static final Message hydrokinesis = new Message(Map.of(
        "start", "(Pokemon) is preparing to control water!"
    ));

    public static final Message snatch = new Message(Map.of(
        "start", "(Pokemon) is waiting for a target to make a move!"
    ));

    public static final Message substitute = new Message(Map.of(
        "start", "(Pokemon) cut (Number) HP to put in a substitute!",
        "repeat", "(Pokemon) already has a substitute!",
        "end", "(Pokemon)'s substitute faded!"
    ));

    public static final Message drowsiness = new Message(Map.of(
        "start", "(Pokemon) grew drowsy!"
    ));

    public static final Message endure = new Message(Map.of(
        "start", "(Pokemon) braced itself!"
    ));

    public static final Message imprison = new Message(Map.of(
        "start", "(Pokemon) sealed any moves its target shares with it!"
    ));

    public static final Message curse = new Message(Map.of(
        "start", "(Causer) cut its own HP and put a curse on (Pokemon)!"
    ));

    public static final Message encore = new Message(Map.of(
        "start", "(Pokemon) must do an encore!",
        "end", "(Pokemon)'s encore ended!"
    ));

    public static final Message focus = new Message(Map.of(
        "start", "(Pokemon) tightened its focus!"
    ));

    public static final Message pumped = new Message(Map.of(
        "start", "(Pokemon) is getting pumped!",
        "start Z", "(Pokemon) boosted its critical-hit ratio using its Z-Power!"
    ));

    public static final Message suppressed_ability = new Message(Map.of(
        "start", "(Pokemon)'s Ability was suppressed!"
    ));

    public static final Message grounded = new Message(Map.of(
        "start", "(Pokemon) fell straight down!"
    ));

    public static final Message throat_chop = new Message(Map.of(
        "start", "(Pokemon)'s throat was injured, preventing it from making sounds!",
        "end", "(Pokemon)'s throat healed!"
    ));

    public static final Message trapped = new Message(Map.of(
        "start", "(Pokemon) can no longer escape!",
        "end", "(Pokemon) was freed to flee!"
    ));

    public static final Message move_disabled = new Message(Map.of(
        "start", "(Pokemon)'s (Move) was disabled!",
        "end", "(Pokemon)'s move is no longer disabled!"
    ));

    public static final Message taking_aim = new Message(Map.of(
        "start", "(Causer) took aim at (Pokemon)!"
    ));

    public static final Message aqua_ring = new Message(Map.of(
        "start", "(Pokemon) surrounded itself with a veil of water!"
    ));

    public static final Message destiny_bond = new Message(Map.of(
        "start", "(Pokemon) is hoping to take its attacker down with it!"
    ));

    public static final Message magic_coat = new Message(Map.of(
        "start", "(Pokemon) shrouded itself with Magic Coat!"
    ));

    public static final Message magnet_rise = new Message(Map.of(
        "start", "(Pokemon) levitated with electromagnetism!",
        "end", "(Pokemon)'s electromagnetism wore off!"
    ));

    public static final Message ingrain = new Message(Map.of(
        "start", "(Pokemon) planted its roots!"
    ));

    public static final Message laser_focus = new Message(Map.of(
        "start", "(Pokemon) concentrated intensely!"
    ));

    public static final Message infatuation = new Message(Map.of(
        "start", "(Pokemon) fell in love!",
        "end", "(Pokemon) got over its infatuation"
    ));
}
