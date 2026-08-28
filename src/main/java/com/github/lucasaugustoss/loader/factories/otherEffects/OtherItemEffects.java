package com.github.lucasaugustoss.loader.factories.otherEffects;

import java.util.Map;

import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.classes.effectFunctions.ItemEffectFunction;
import com.github.lucasaugustoss.data.messages.MessageHandler;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;

public class OtherItemEffects {
    public static final ItemEffectFunction focus_sash =
        (thisItem, holder, user, opponent, move, damage, showMessages, activation) -> {
            if (activation == ItemActivation.DeductHP) {
                if (damage.amount >= user.getHP() &&
                    user.getCurrentHP() == user.getHP()) {
                    thisItem.setConsumed(true);
                    return true;
                }
                return false;
            }

            if (activation == ItemActivation.Consumed) {
                MessageHandler.add(thisItem.getMessages().getName(), "activate", Map.of(
                    "Pokemon", user.getName(true, true)
                ));

                thisItem.consume(true, false);
            }

            return null;
        };

    public static final ItemEffectFunction choice_lock =
        (thisItem, holder, user, opponent, move, damage, showMessages, activation) -> {
            if (activation == ItemActivation.UseMove) {
                thisItem.setAffectedMove(move);
            }

            if (activation == ItemActivation.TrySelectMove) {
                if (thisItem.getAffectedMove() != null && move != thisItem.getAffectedMove()) {
                    if (showMessages) {
                        thisItem.getMessages().print("block move selection", Map.of(
                            "Pokemon", user.getName(true, false),
                            "Move", thisItem.getAffectedMove().getName()
                        ));
                    }
                    return false;
                }
                return true;
            }

            return null;
        };

    public static final ItemEffectFunction force_use =
        (thisItem, holder, user, opponent, move, damage, showMessages, activation) -> {
            return thisItem.activate(holder, opponent, user, move, damage, showMessages, ItemActivation.ForceUse);
        };

    public static final ItemEffectFunction primal_reversion =
        (thisItem, holder, user, opponent, move, damage, showMessages, activation) -> {
            PokemonTemplate baseForm = thisItem.getUsers()[0].getBaseForm();

            if (holder.compare(baseForm, true) && holder.compareWithForm(baseForm)) {
                MessageHandler.add("gimmick", "primal reversion", Map.of(
                    "Pokemon", user.getName(true, true)
                ));
                holder.changeForm(thisItem.getTransformsInto().getForm());
            }

            return null;
        };
}
