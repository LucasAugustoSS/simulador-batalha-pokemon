package main.actions;

import java.util.ArrayList;

public class PriorityBracket {
    public ArrayList<Action> actions;
    public int priority;

    public PriorityBracket(int priority) {
        this.actions = new ArrayList<>();
        this.priority = priority;
    }
    public PriorityBracket(ArrayList<Action> actions, int priority) {
        this.actions = actions;
        this.priority = priority;
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public void removeAction(Action action) {
        actions.remove(action);
    }
}
