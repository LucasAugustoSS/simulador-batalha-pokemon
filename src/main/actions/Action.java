package main.actions;

import data.classes.Move;
import data.classes.Pokemon;

public class Action {
    public Move move;
    public Pokemon user;
    public Pokemon target;

    public int priorityBracket;
    public int actionSpeed;

    public Action actionTetheredBefore;
    public Action actionTetheredAfter;
    public boolean lockedAtEndOfBracket;

    public boolean executed;

    public Action(Move move, Pokemon user, Pokemon target) {
        this.move = move;
        this.user = user;
        this.target = target;
    }

    public void tether(Action actionBefore) {
        actionTetheredBefore = actionBefore;

        if (actionBefore.actionTetheredAfter != null) {
            actionBefore.actionTetheredAfter.tether(this);
        }
        actionBefore.actionTetheredAfter = this;

        if (actionBefore.lockedAtEndOfBracket) {
            lockAtEnd();
        }
    }

    public void lockAtEnd() {
        lockedAtEndOfBracket = true;
        if (actionTetheredAfter != null) {
            actionTetheredAfter.lockAtEnd();
        }
    }



    // debug

    public void getBeforeSequence() {
        System.out.print(move.getName());

        if (actionTetheredBefore != null) {
            System.out.print(" <-- ");
            actionTetheredBefore.getBeforeSequence();
        } else {
            System.out.println();
        }
    }

    public void getAfterSequence() {
        System.out.print(move.getName());

        if (actionTetheredAfter != null) {
            System.out.print(" --> ");
            actionTetheredAfter.getAfterSequence();
        } else {
            System.out.println();
        }
    }

    public Action lastAfter() {
        if (actionTetheredAfter != null) {
            return actionTetheredAfter.lastAfter();
        }
        return this;
    }
}
