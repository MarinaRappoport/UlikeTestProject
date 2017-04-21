package com.marina.uliketest.classes;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Marina on 21.04.2017.
 *
 * Class represents Action
 */
public class Action {

    public enum ActionType{
        ANIMATION, TOAST, CALL, NOTIFICATION;
    }

//    public enum ActionType{
//        ANIMATION("animation"), TOAST("toast"), CALL("call"), NOTIFICATION("notification");
//        private String type;
//
//        ActionType(String type) {
//            this.type = type;
//        }
//
//        public String getValue() {
//            return type;
//        }
//    }

    private ActionType type;
    private boolean enabled;
    private int priority;
    private int[] validDays;
    private int coolDown;
    private Date lastCall;

    public Action(ActionType type, boolean enabled, int priority, int[] validDays, int coolDown) {
        this.type = type;
        this.enabled = enabled;
        this.priority = priority;
        this.validDays = validDays;
        this.coolDown = coolDown;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int[] getValidDays() {
        return validDays;
    }

    public void setValidDays(int[] validDays) {
        this.validDays = validDays;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public Date getLastCall() {
        return lastCall;
    }

    public void setLastCall(Date lastCall) {
        this.lastCall = lastCall;
    }

    @Override
    public String toString() {
        return "Action{" +
                "type=" + type +
                ", enabled=" + enabled +
                ", priority=" + priority +
                ", validDays=" + Arrays.toString(validDays) +
                ", coolDown=" + coolDown +
                ", lastCall=" + lastCall +
                '}';
    }
}
