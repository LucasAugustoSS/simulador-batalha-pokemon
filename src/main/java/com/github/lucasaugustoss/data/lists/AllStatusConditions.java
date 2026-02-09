package com.github.lucasaugustoss.data.lists;

import java.util.ArrayList;

import com.github.lucasaugustoss.data.classes.StatusCondition;
import com.github.lucasaugustoss.data.objects.oldObjects.StatusConditionList;

public class AllStatusConditions {
    public static final ArrayList<StatusCondition> allStatusConditions = new ArrayList<>();

    static {
        allStatusConditions.add(StatusConditionList.readying_switch);
        allStatusConditions.add(StatusConditionList.burn);
        allStatusConditions.add(StatusConditionList.paralysis);
        allStatusConditions.add(StatusConditionList.poison);
        allStatusConditions.add(StatusConditionList.bad_poison);
        allStatusConditions.add(StatusConditionList.sleep);
        allStatusConditions.add(StatusConditionList.freeze);
        allStatusConditions.add(StatusConditionList.frostbite);
        allStatusConditions.add(StatusConditionList.confusion);
        allStatusConditions.add(StatusConditionList.flinch);
        allStatusConditions.add(StatusConditionList.bind);
        allStatusConditions.add(StatusConditionList.taunt);
        allStatusConditions.add(StatusConditionList.seed);
        allStatusConditions.add(StatusConditionList.charging_turn);
        allStatusConditions.add(StatusConditionList.semi_invulnerable_charging_turn);
        allStatusConditions.add(StatusConditionList.recharging_turn);
        allStatusConditions.add(StatusConditionList.unusable_move_turn);
        allStatusConditions.add(StatusConditionList.rampage);
        allStatusConditions.add(StatusConditionList.protection);
        allStatusConditions.add(StatusConditionList.torment);
        allStatusConditions.add(StatusConditionList.hydrokinesis);
        allStatusConditions.add(StatusConditionList.substitute);
        allStatusConditions.add(StatusConditionList.drowsiness);
        allStatusConditions.add(StatusConditionList.countering);
        allStatusConditions.add(StatusConditionList.endure);
        allStatusConditions.add(StatusConditionList.imprison);
        allStatusConditions.add(StatusConditionList.curse);
        allStatusConditions.add(StatusConditionList.roost);
        allStatusConditions.add(StatusConditionList.encore);
        allStatusConditions.add(StatusConditionList.charge);
        allStatusConditions.add(StatusConditionList.focus);
        allStatusConditions.add(StatusConditionList.pumped);
        allStatusConditions.add(StatusConditionList.locked);
        allStatusConditions.add(StatusConditionList.defense_curl);
        allStatusConditions.add(StatusConditionList.suppressed_ability);
        allStatusConditions.add(StatusConditionList.grounded);
        allStatusConditions.add(StatusConditionList.throat_chop);
        allStatusConditions.add(StatusConditionList.trapped);
        allStatusConditions.add(StatusConditionList.move_disabled);
        allStatusConditions.add(StatusConditionList.perish_song);
        allStatusConditions.add(StatusConditionList.taking_aim);
        allStatusConditions.add(StatusConditionList.aqua_ring);
        allStatusConditions.add(StatusConditionList.destiny_bond);
        allStatusConditions.add(StatusConditionList.magic_coat);
        allStatusConditions.add(StatusConditionList.snatch);
        allStatusConditions.add(StatusConditionList.magnet_rise);
        allStatusConditions.add(StatusConditionList.ingrain);
        allStatusConditions.add(StatusConditionList.laser_focus);
        allStatusConditions.add(StatusConditionList.infatuation);
    }
}
