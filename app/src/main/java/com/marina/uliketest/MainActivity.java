package com.marina.uliketest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.marina.uliketest.classes.Action;
import com.marina.uliketest.common.ActionConfigLoader;
import com.marina.uliketest.common.ActionHelper;
import com.marina.uliketest.common.CommonData;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sPref = getPreferences(MODE_PRIVATE);

        if(CommonData.getInstance().isFirstStart()) {
            ActionConfigLoader loader = new ActionConfigLoader(this, sPref);
            loader.execute();
        }
    }

    public void startAction(View view) {
        Action action = ActionHelper.choseAction(this);
        if(action==null){
            Toast.makeText(this,"Sorry, no action is available", Toast.LENGTH_LONG).show();
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        }else {
            Intent callIntent = new Intent(Intent.ACTION_DIAL, null);
            switch (action.getType()) {
                case TOAST:
                    Toast.makeText(this,"Current Action is Toast", Toast.LENGTH_SHORT).show();
                    break;
                case ANIMATION:
                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
                    view.startAnimation(animation);
                    break;
                case CALL:
                    startActivity(callIntent);
                    break;
                case NOTIFICATION:
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("Ulike")
                                    .setContentText("Current action is Notification");
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addNextIntent(callIntent);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentIntent(resultPendingIntent);
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(0, mBuilder.build());
                    break;
            }
            Date now = new Date();
            action.setLastCall(now);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putLong(action.getType().toString(), now.getTime());
            ed.commit();
        }
    }

    public void openList(View view) {
        startActivity(new Intent(this, ActionListActivity.class));
    }
}
