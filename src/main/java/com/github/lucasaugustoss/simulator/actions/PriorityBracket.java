package com.github.lucasaugustoss.simulator.actions;

import java.util.ArrayList;
import java.util.List;

public class PriorityBracket {
    public List<Action> actions;
    public int priority;

    public PriorityBracket(int priority) {
        this.actions = new ArrayList<>();
        this.priority = priority;
    }
    public PriorityBracket(List<Action> actions, int priority) {
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
