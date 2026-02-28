package com.github.lucasaugustoss.loader.factories.otherEffects;

import com.github.lucasaugustoss.data.activationConditions.ItemActivation;
import com.github.lucasaugustoss.data.classes.effectFunctions.ItemEffectFunction;
import com.github.lucasaugustoss.data.objects.templates.PokemonTemplate;

public class OtherItemEffects {
    public static final ItemEffectFunction focus_sash =
        (thisItem, holder, user, opponent, move, damage, activation) -> {
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
        };

    public static final ItemEffectFunction force_use =
        (thisItem, holder, user, opponent, move, damage, activation) -> {
            return thisItem.activate(holder, opponent, user, move, null, ItemActivation.ForceUse);
        };

    public static final ItemEffectFunction primal_reversion =
        (thisItem, holder, user, opponent, move, damage, activation) -> {
            PokemonTemplate baseForm = thisItem.getUsers()[0].getBaseForm();

            if (holder.compare(baseForm, true) && holder.compareWithForm(baseForm)) {
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
                System.out.println(holder.getName(true, true) + "'s Primal Reversion! It reverted to its primal form!");
                holder.changeForm(thisItem.getTransformsInto().getForm());
                System.out.println("\n. . . . . . . . . . . . . . . . . . . . . .\n");
            }

            return null;
        };
}
