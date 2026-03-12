package com.github.lucasaugustoss.data.lists;

import java.util.ArrayList;

import com.github.lucasaugustoss.data.messages.Message;
import com.github.lucasaugustoss.data.messages.list.FieldMessages;
import com.github.lucasaugustoss.data.messages.list.GeneralMessages;
import com.github.lucasaugustoss.data.messages.list.StatusMessages;

public class AllMessages {
    public static final ArrayList<Message> allMessages = new ArrayList<>();

    static {
        allMessages.add(GeneralMessages.modify_health);
        allMessages.add(GeneralMessages.stat_change);

        allMessages.add(FieldMessages.sun);
        allMessages.add(FieldMessages.desolate_land);
        allMessages.add(FieldMessages.rain);
        allMessages.add(FieldMessages.primordial_sea);
        allMessages.add(FieldMessages.sand);
        allMessages.add(FieldMessages.snow);
        allMessages.add(FieldMessages.delta_stream);
        allMessages.add(FieldMessages.electric_terrain);
        allMessages.add(FieldMessages.grassy_terrain);
        allMessages.add(FieldMessages.misty_terrain);
        allMessages.add(FieldMessages.psychic_terrain);
        allMessages.add(FieldMessages.spikes);
        allMessages.add(FieldMessages.stealth_rock);
        allMessages.add(FieldMessages.sticky_web);
        allMessages.add(FieldMessages.mist);
        allMessages.add(FieldMessages.safeguard);
        allMessages.add(FieldMessages.tailwind);
        allMessages.add(FieldMessages.reflect);
        allMessages.add(FieldMessages.light_screen);
        allMessages.add(FieldMessages.aurora_veil);
        allMessages.add(FieldMessages.quick_guard);
        allMessages.add(FieldMessages.wide_guard);
        allMessages.add(FieldMessages.magic_room);
        allMessages.add(FieldMessages.uproar);
        allMessages.add(FieldMessages.trick_room);
        allMessages.add(FieldMessages.gravity);
        allMessages.add(FieldMessages.wonder_room);
        allMessages.add(FieldMessages.ion_deluge);

        allMessages.add(StatusMessages.burn);
        allMessages.add(StatusMessages.paralysis);
        allMessages.add(StatusMessages.poison);
        allMessages.add(StatusMessages.bad_poison);
        allMessages.add(StatusMessages.sleep);
        allMessages.add(StatusMessages.freeze);
        allMessages.add(StatusMessages.frostbite);
        allMessages.add(StatusMessages.confusion);
        allMessages.add(StatusMessages.flinch);
        allMessages.add(StatusMessages.bind);
        allMessages.add(StatusMessages.taunt);
        allMessages.add(StatusMessages.seed);
        allMessages.add(StatusMessages.unusable_move_turn);
        allMessages.add(StatusMessages.protection);
        allMessages.add(StatusMessages.torment);
        allMessages.add(StatusMessages.hydrokinesis);
        allMessages.add(StatusMessages.snatch);
        allMessages.add(StatusMessages.substitute);
        allMessages.add(StatusMessages.drowsiness);
        allMessages.add(StatusMessages.endure);
        allMessages.add(StatusMessages.imprison);
        allMessages.add(StatusMessages.curse);
        allMessages.add(StatusMessages.encore);
        allMessages.add(StatusMessages.focus);
        allMessages.add(StatusMessages.pumped);
        allMessages.add(StatusMessages.suppressed_ability);
        allMessages.add(StatusMessages.grounded);
        allMessages.add(StatusMessages.throat_chop);
        allMessages.add(StatusMessages.trapped);
        allMessages.add(StatusMessages.move_disabled);
        allMessages.add(StatusMessages.perish_song);
        allMessages.add(StatusMessages.taking_aim);
        allMessages.add(StatusMessages.aqua_ring);
        allMessages.add(StatusMessages.destiny_bond);
        allMessages.add(StatusMessages.magic_coat);
        allMessages.add(StatusMessages.magnet_rise);
        allMessages.add(StatusMessages.ingrain);
        allMessages.add(StatusMessages.laser_focus);
        allMessages.add(StatusMessages.infatuation);
    }
}
