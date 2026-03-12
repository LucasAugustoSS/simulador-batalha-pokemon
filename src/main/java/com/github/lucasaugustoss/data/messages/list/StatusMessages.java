package com.github.lucasaugustoss.data.messages.list;

import java.util.Map;

import com.github.lucasaugustoss.data.messages.Message;

public class StatusMessages {
    // non-volatile

    public static final Message burn = new Message(
        "burn",
        Map.of(
            "start", "(Pokemon) was burned!",
            "start by ability", "(Causer)'s (Ability) burned (Pokemon)!",
            "start by item", "(Pokemon) was burned by the (Item)!",
            "repeat", "(Pokemon) is already burned!",
            "chip damage", "(Pokemon) was hurt by its burn!",
            "end", "(Pokemon)'s burn was healed!"
        )
    );

    public static final Message paralysis = new Message(
        "paralysis",
        Map.of(
            "start", "(Pokemon) is paralyzed, so it may be unable to move!",
            "start by ability", "(Causer)'s (Ability) paralyzed (Pokemon)!",
            "repeat", "(Pokemon) is already paralyzed!",
            "stop move", "(Pokemon) couldn't move because it's paralyzed!",
            "end", "(Pokemon) was cured of paralysis!"
        )
    );

    public static final Message poison = new Message(
        "poison",
        Map.of(
            "start", "(Pokemon) was poisoned!",
            "start by ability", "(Causer)'s (Ability) poisoned (Pokemon)!",
            "repeat", "(Pokemon) is already poisoned!",
            "chip damage", "(Pokemon) was hurt by poison!",
            "end", "(Pokemon) was cured of its poisoning!"
        )
    );

    public static final Message bad_poison = new Message(
        "bad_poison",
        Map.of(
            "start", "(Pokemon) was badly poisoned!",
            "start by ability", "(Causer)'s (Ability) badly poisoned (Pokemon)!",
            "start by item", "(Pokemon) was badly poisoned by the (Item)!",
            "repeat", "(Pokemon) is already poisoned!",
            "chip damage", "(Pokemon) was hurt by poison!",
            "end", "(Pokemon) was cured of its poisoning!"
        )
    );

    public static final Message sleep = new Message(
        "sleep",
        Map.of(
            "start", "(Pokemon) fell asleep!",
            "start by ability", "(Causer)'s (Ability) put (Pokemon) to sleep!",
            "repeat", "(Pokemon) is already asleep!",
            "stop move", "(Pokemon) is fast asleep.",
            "end", "(Pokemon) woke up!"
        )
    );

    public static final Message freeze = new Message(
        "freeze",
        Map.of(
            "start", "(Pokemon) was frozen solid!",
            "repeat", "(Pokemon) is already frozen solid!",
            "stop move", "(Pokemon) is frozen solid!",
            "end", "(Pokemon) thawed out!"
        )
    );

    public static final Message frostbite = new Message(
        "frostbite",
        Map.of(
            "start", "(Pokemon) got frostbite!",
            "start by ability", "(Causer)'s (Ability) frostbit (Pokemon)!",
            "repeat", "(Pokemon) is already frostbitten!",
            "chip damage", "(Pokemon) was hurt by its frostbite!",
            "end", "(Pokemon)'s frostbite was healed!"
        )
    );


    // volatile status conditions

    public static final Message confusion = new Message(
        "confusion",
        Map.of(
            "start", "(Pokemon) became confused!",
            "start by ability", "(Causer)'s (Ability) confused (Pokemon)!",
            "repeat", "(Pokemon) is already confused!",
            "try move", "(Pokemon) is confused!",
            "stop move", "It hurt itself in its confusion!",
            "end", "(Pokemon) snapped out of confusion!"
        )
    );

    public static final Message flinch = new Message(
        "flinch",
        Map.of(
            "stop move", "(Pokemon) flinched and couldn't move!"
        )
    );

    public static final Message bind = new Message(
        "bind",
        Map.of(
            "chip damage", "(Pokemon) is hurt by (Move)!",
            "block switch", "\n!- (Pokemon) can't escape because of (Move) -!\n",
            "end", "(Pokemon) was freed from (Move)!"
        )
    );

    public static final Message taunt = new Message(
        "taunt",
        Map.of(
            "start", "(Pokemon) fell for the taunt!",
            "stop move", "(Pokemon) can't use (Move) after the taunt!",
            "block move selection", "\n!- (Pokemon) can't use (Move) after the taunt -!\n",
            "end", "(Pokemon) shook off the taunt!"
        )
    );

    public static final Message seed = new Message(
        "seed",
        Map.of(
            "start", "(Pokemon) was seeded!",
            "chip damage", "(Pokemon)'s health is sapped by Leech Seed!",
            "end", "(Pokemon) was freed from Leech Seed!"
        )
    );

    public static final Message unusable_move_turn = new Message(
        "unusable_move_turn",
        Map.of(
            "block move selection", "\n!- (Pokemon) can't use (Move) -!\n"
        )
    );

    public static final Message protection = new Message(
        "protection",
        Map.of(
            "start", "(Pokemon) protected itself!"
        )
    );

    public static final Message torment = new Message(
        "torment",
        Map.of(
            "start", "(Pokemon) was subjected to torment!",
            "block move selection", "\n!- (Pokemon) can't use (Move) after the torment -!\n",
            "end", "(Pokemon) is no longer tormented!"
        )
    );

    public static final Message hydrokinesis = new Message(
        "hydrokinesis",
        Map.of(
            "start", "(Pokemon) is preparing to control water!",
            "steal move", "(Pokemon) took control of (Target)'s move!"
        )
    );

    public static final Message snatch = new Message(
        "snatch",
        Map.of(
            "start", "(Pokemon) is waiting for a target to make a move!",
            "steal move", "(Pokemon) snatched (Target)'s move!"
        )
    );

    public static final Message substitute = new Message(
        "substitute",
        Map.of(
            "start", "(Pokemon) cut (Number) HP to put in a substitute!",
            "repeat", "(Pokemon) already has a substitute!",
            "end", "(Pokemon)'s substitute faded!"
        )
    );

    public static final Message drowsiness = new Message(
        "drowsiness",
        Map.of(
            "start", "(Pokemon) grew drowsy!"
        )
    );

    public static final Message endure = new Message(
        "endure",
        Map.of(
            "start", "(Pokemon) braced itself!"
        )
    );

    public static final Message imprison = new Message(
        "imprison",
        Map.of(
            "start", "(Pokemon) sealed any moves its target shares with it!",
            "stop move", "(Pokemon) can't use its sealed (Move)!",
            "block move selection", "\n!- (Pokemon) can't use its sealed (Move) -!\n"
        )
    );

    public static final Message curse = new Message(
        "curse",
        Map.of(
            "start", "(Causer) cut its own HP and put a curse on (Pokemon)!",
            "chip damage", "(Pokemon) is afflicted by the curse!"
        )
    );

    public static final Message encore = new Message(
        "encore",
        Map.of(
            "start", "(Pokemon) must do an encore!",
            "block move selection", "\n!- (Pokemon) must use (Move) after the encore -!\n",
            "end", "(Pokemon)'s encore ended!"
        )
    );

    public static final Message focus = new Message(
        "focus",
        Map.of(
            "start", "(Pokemon) tightened its focus!",
            "stop move", "(Pokemon) lost its focus and couldn't move!"
        )
    );

    public static final Message pumped = new Message(
        "pumped",
        Map.of(
            "start", "(Pokemon) is getting pumped!",
            "start Z", "(Pokemon) boosted its critical-hit ratio using its Z-Power!"
        )
    );

    public static final Message suppressed_ability = new Message(
        "suppressed_ability",
        Map.of(
            "start", "(Pokemon)'s Ability was suppressed!"
        )
    );

    public static final Message grounded = new Message(
        "grounded",
        Map.of(
            "start", "(Pokemon) fell straight down!"
        )
    );

    public static final Message throat_chop = new Message(
        "throat_chop",
        Map.of(
            "start", "(Pokemon)'s throat was injured, preventing it from making sounds!",
            "stop move", "The effects of Throat Chop prevent (Pokemon) from using certain moves!",
            "block move selection", "\n!- The effects of Throat Chop prevent (Pokemon) from using certain moves -!\n",
            "end", "(Pokemon)'s throat healed!"
        )
    );

    public static final Message trapped = new Message(
        "trapped",
        Map.of(
            "start", "(Pokemon) can no longer escape!",
            "block switch", "\n!- (Pokemon) can't escape -!\n",
            "end", "(Pokemon) was freed to flee!"
        )
    );

    public static final Message move_disabled = new Message(
        "move_disabled",
        Map.of(
            "start", "(Pokemon)'s (Move) was disabled!",
            "stop move", "(Pokemon)'s (Move) is disabled!",
            "block move selection", "\n!- (Pokemon)'s (Move) is disabled -!\n",
            "end", "(Pokemon)'s move is no longer disabled!"
        )
    );

    public static final Message perish_song = new Message(
        "perish_song",
        Map.of(
            "count down", "(Pokemon)'s perish count fell to (Number)!"
        )
    );

    public static final Message taking_aim = new Message(
        "taking_aim",
        Map.of(
            "start", "(Causer) took aim at (Pokemon)!"
        )
    );

    public static final Message aqua_ring = new Message(
        "aqua_ring",
        Map.of(
            "start", "(Pokemon) surrounded itself with a veil of water!",
            "chip heal", "A veil of water restored (Pokemon)'s HP!"
        )
    );

    public static final Message destiny_bond = new Message(
        "destiny_bond",
        Map.of(
            "start", "(Pokemon) is hoping to take its attacker down with it!",
            "cause faint", "\n(Pokemon) took its attacker down with it!"
        )
    );

    public static final Message magic_coat = new Message(
        "magic_coat",
        Map.of(
            "start", "(Pokemon) shrouded itself with Magic Coat!"
        )
    );

    public static final Message magnet_rise = new Message(
        "magnet_rise",
        Map.of(
            "start", "(Pokemon) levitated with electromagnetism!",
            "end", "(Pokemon)'s electromagnetism wore off!"
        )
    );

    public static final Message ingrain = new Message(
        "ingrain",
        Map.of(
            "start", "(Pokemon) planted its roots!",
            "chip heal", "(Pokemon) absorbed nutrients with its roots!",
            "block switch", "\n!- (Pokemon) is anchored in place with its roots -!\n",
            "block forced switch", "(Pokemon) is anchored in place with its roots!"
        )
    );

    public static final Message laser_focus = new Message(
        "laser_focus",
        Map.of(
            "start", "(Pokemon) concentrated intensely!"
        )
    );

    public static final Message infatuation = new Message(
        "infatuation",
        Map.of(
            "start", "(Pokemon) fell in love!",
            "try move", "(Pokemon) is in love with (Causer)!",
            "stop move", "(Pokemon) is immobilized by love!",
            "end", "(Pokemon) got over its infatuation"
        )
    );
}
