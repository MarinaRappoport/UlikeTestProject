package com.marina.uliketest.common;

import com.marina.uliketest.classes.Action;

import java.util.ArrayList;

/**
 * Created by Marina on 21.04.2017.
 *
 * singleton with common data
 */
public class CommonData {
    private ArrayList<Action> actions;
    private boolean isFirstStart = true;

    private static CommonData ourInstance = new CommonData();

    public static CommonData getInstance() {
        return ourInstance;
    }

    private CommonData() {
        actions = new ArrayList<>();
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public boolean isFirstStart() {
        return isFirstStart;
    }

    public void setIsFirstStart(boolean isFirstStart) {
        this.isFirstStart = isFirstStart;
    }

    public Action findActionByType(Action.ActionType type){
        for (Action action:
             actions) {
            if (action.getType()==type) return action;
        }
        return null;
    }
}
