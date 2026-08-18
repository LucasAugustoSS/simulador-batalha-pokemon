package com.github.lucasaugustoss.loader.factories;

import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.classes.Pokemon;
import com.github.lucasaugustoss.data.classes.effectFunctions.ItemEffectFunction;
import com.github.lucasaugustoss.data.messages.MessageHandler;
import com.github.lucasaugustoss.data.objects.effects.ItemEffect;
import com.github.lucasaugustoss.data.objects.templates.StatusConditionTemplate;
import com.github.lucasaugustoss.data.objects.templates.TypeTemplate;
import com.github.lucasaugustoss.loader.dtos.ItemEffectDTO;
import com.github.lucasaugustoss.loader.factories.otherEffects.OtherItemEffects;
import com.github.lucasaugustoss.loader.factories.tools.FactoryTools;
import com.github.lucasaugustoss.simulator.Battle;
import com.github.lucasaugustoss.simulator.Damage;

public class ItemEffectFactory {
    public static ItemEffect buildEffect(
        ItemEffectDTO dto,
        Map<String, TypeTemplate> typeMap,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        if (dto == null) {
            return null;
        }

        String type = dto.type;
        ItemActivation[] activation = FactoryTools.convertEnumArray(dto.activation, ItemActivation.class).toArray(new ItemActivation[0]);
        ItemEffectFunction effect = null;

        switch (type) {
            case "eat":
                effect = buildEat(dto);
                break;

            case "heal":
                effect = buildHeal(dto);
                break;

            case "status_condition":
                effect = buildStatusCondition(dto, statusConditionMap);
                break;

            case "power_boost":
                effect = buildPowerBoost(dto, typeMap);
                break;

            case "other":
                effect = getOther(dto.otherID);
                break;

            default:
                return null;
        }

        return new ItemEffect(type, activation, effect);
    }

    private static ItemEffectFunction buildEat(ItemEffectDTO dto) {
        final String eatCondition = dto.eatCondition;
        final double pinchHP = dto.pinchHP != null ? FactoryTools.convertFraction(dto.pinchHP) : 0;

        return (thisItem, holder, user, opponent, move, damage, activation) -> {
            boolean willEat = activation == ItemActivation.ForceUse;
            if (!willEat) {
                switch (eatCondition) {
                    case "pinch":
                        willEat = user.getCurrentHP() <= user.getHP()*pinchHP;
                        break;

                    default:
                        break;
                }
            }

            if (willEat) {
                thisItem.activate(holder, user, opponent, move, damage, ItemActivation.Eat);
                thisItem.setConsumed(true);
                thisItem.consume(true, false);
            }
            return null;
        };
    }

    private static ItemEffectFunction buildHeal(ItemEffectDTO dto) {
        final int healSet = dto.healSet;
        final double healFraction = dto.healFraction != null ? FactoryTools.convertFraction(dto.healFraction) : 0;

        return (thisItem, holder, user, opponent, move, damage, activation) -> {
            MessageHandler.add("modify_health", "heal item", Map.of(
                "Pokemon", user.getName(true, true),
                "Item", thisItem.getName()
            ));

            // System.out.println("\n" + user.getName(true, true) + " restored its health using its " + thisItem.getName() + "!");

            int healedDamage = healSet > 0 ? healSet : (int) Math.floor(user.getHP()*healFraction);
            Damage.heal(user, null, healedDamage, true, false);

            return null;
        };
    }

    private static ItemEffectFunction buildStatusCondition(
        ItemEffectDTO dto,
        Map<String, StatusConditionTemplate> statusConditionMap
    ) {
        final StatusConditionTemplate statusCondition = FactoryTools.convertObject(dto.statusCondition, statusConditionMap);
        final String target = dto.target;

        return (thisItem, holder, user, opponent, move, damage, activation) -> {
            Pokemon itemTarget = target.equals("user") ? user : opponent;
            if (!Battle.faintCheck(itemTarget, null, false) &&
                itemTarget.getNonVolatileStatus().compare(statusConditionMap.get("none"))) {
                // if (activation == ItemActivation.EndOfTurn) {
                //     System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                // }

                statusCondition.apply(itemTarget, thisItem, null, true, false);

                // if (activation == ItemActivation.EndOfTurn) {
                //     System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                // }
            }
            return null;
        };
    }

    private static ItemEffectFunction buildPowerBoost(
        ItemEffectDTO dto,
        Map<String, TypeTemplate> typeMap
    ) {
        final boolean validUserOnly = dto.validUserOnly;
        final TypeTemplate[] boostedTypes = FactoryTools.convertObjectArray(dto.boostedTypes, typeMap).toArray(new TypeTemplate[0]);
        final double boost = dto.boost;

        return (thisItem, holder, user, opponent, move, damage, activation) -> {
            if (validUserOnly && !thisItem.heldByValidUser(true)) {
                return 1.0;
            }

            if (boostedTypes.length > 0) {
                for (TypeTemplate type : boostedTypes) {
                    if (move.getType(false, false).compare(type)) {
                        return boost;
                    }
                }
            } else {
                return boost;
            }

            return 1.0;
        };
    }

    private static ItemEffectFunction getOther(String otherID) {
        switch (otherID) {
            case "focus_sash":
                return OtherItemEffects.focus_sash;

            case "force_use":
                return OtherItemEffects.force_use;

            case "primal_reversion":
                return OtherItemEffects.primal_reversion;
        
            default:
                return null;
        }
    }
}
