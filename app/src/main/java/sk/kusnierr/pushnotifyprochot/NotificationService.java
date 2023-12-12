package sk.kusnierr.pushnotifyprochot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.system.Os;
import android.view.Gravity;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.JsonObject;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationService  implements OneSignal.OSRemoteNotificationReceivedHandler{
    String currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());
    public static final String ACTION_NOTIFICATION_RECIVED = "ACTION_NOTIFICATION_RECEIVED";
    @Override
    public void remoteNotificationReceived(Context context, OSNotificationReceivedEvent osNotificationReceivedEvent) {
        OSNotification notification = osNotificationReceivedEvent.getNotification();
       Intent intent = new Intent(NotificationService.ACTION_NOTIFICATION_RECIVED);
       intent.putExtra("date",currentDate);
       intent.putExtra("title", notification.getTitle());
       intent.putExtra("body",notification.getBody());
       LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        /*SharedPreferences settings = context.getSharedPreferences("Push_Values", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("date", currentDate);
        editor.putString("title", notification.getTitle());
        editor.putString("body", notification.getBody());
        editor.apply();
        editor.commit();
        editor.clear();*/
        osNotificationReceivedEvent.complete(notification);


    }

}
