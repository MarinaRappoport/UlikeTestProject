package com.marina.uliketest.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.marina.uliketest.classes.Action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Marina on 21.04.2017.
 *
 * class that helps to chose action to play
 */
public class ActionHelper {

    public static Action choseAction(Context context){
        ArrayList<Action> actions = CommonData.getInstance().getActions();

        Set<Action> actionsToRemove = new HashSet<>();

        //get today's day number
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        // -1 because days in the API week starting from sunday == 0 and in the Calendar == 1
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;

        for (Action action: actions) {
            //delete disabled actions
            if(!action.isEnabled()) actionsToRemove.add(action);
            //delete actions that don't fit the day
            if(!containsDay(action.getValidDays(), dayOfWeek)) actionsToRemove.add(action);
            //delete actions that are in cool down period
            if (action.getLastCall()!=null){
                Date now = new Date();
                long timeDiff = now.getTime() - action.getLastCall().getTime();
                if(timeDiff<=action.getCoolDown()){
                    actionsToRemove.add(action);
                }
            }
        }

        //delete action TOAST if no internet connection
        if(!isInternetConnected(context))
            actionsToRemove.add(CommonData.getInstance().findActionByType(Action.ActionType.TOAST));

        ArrayList<Action> actionsCopy = (ArrayList<Action>) actions.clone();
        for (Action action: actionsToRemove) {
            actionsCopy.remove(action);
        }

        int chosenActivityIndex = 0;

        //choose from rest action with higher priority
        if(actionsCopy.isEmpty()){
            return null;
        }else {
            for (int i = 1; i <actionsCopy.size(); i++) {
                if(actionsCopy.get(i).getPriority()>actionsCopy.get(chosenActivityIndex).getPriority())
                    chosenActivityIndex=i;
            }
        }
        return actionsCopy.get(chosenActivityIndex);
    }

    //return true if array of valid days contains the day number, else false
    private static boolean containsDay(int[] validDays, int dayOfWeek) {
        for (int day: validDays) {
            if (day == dayOfWeek) {
                return true;
            }
        }
        return false;
    }

    //check Internet connection
    private static boolean isInternetConnected(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null
                && activeNetwork.isAvailable()
                && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }
}
