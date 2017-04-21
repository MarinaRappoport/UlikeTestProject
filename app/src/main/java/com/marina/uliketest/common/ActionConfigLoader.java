package com.marina.uliketest.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.marina.uliketest.classes.Action;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by Marina on 21.04.2017.
 *
 * class loads action config from URL and parse it
 */
public class ActionConfigLoader extends AsyncTask<String, String, String> {

    private final static String URL = "https://androidexam.s3.amazonaws.com/butto_to_action_config.json";

    ProgressDialog pd;
    Context context;
    SharedPreferences sPref;

    public ActionConfigLoader(Context context, SharedPreferences sPref) {
        this.context = context;
        this.sPref = sPref;
    }

    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(context);
        pd.setMessage("Loading actions configuration...");
        pd.setCancelable(false);
        pd.show();
    }

    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            java.net.URL url = new URL(URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
                Log.d("Response: ", "> " + line);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (pd.isShowing()) {
            pd.dismiss();
        }
        if(result!=null) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject actionObj = jsonArray.getJSONObject(i);
                    Action.ActionType type = Action.ActionType.valueOf(actionObj.getString("type").toUpperCase());
                    boolean enabled = actionObj.getBoolean("enabled");
                    int priority = actionObj.getInt("priority");
                    JSONArray daysArray = actionObj.getJSONArray("valid_days");
                    int[] validDays = new int[daysArray.length()];
                    for (int j = 0; j < daysArray.length(); j++) {
                        validDays[j] = daysArray.getInt(j);
                    }
                    int coolDown = actionObj.getInt("cool_down");
                    Action action = new Action(type, enabled, priority, validDays, coolDown);

                    //get last call from ShPr
                    long lastCall = sPref.getLong(action.getType().toString(), 0);
                    if (lastCall != 0) {
                        action.setLastCall(new Date(lastCall));
                    }
                    CommonData.getInstance().getActions().add(action);
                }
                CommonData.getInstance().setIsFirstStart(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}