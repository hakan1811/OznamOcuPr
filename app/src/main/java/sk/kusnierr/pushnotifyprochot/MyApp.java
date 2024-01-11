package sk.kusnierr.pushnotifyprochot;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.onesignal.OSNotification;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OSSubscriptionStateChanges;
import com.onesignal.OneSignal;

import org.json.JSONObject;

public class MyApp extends Application {
    private static final String ONESIGNAL_APP_ID = "0786abf4-c0e4-405e-8b2c-7704e82eb417";

    SharedPreferences sharedPrefs;
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

    }
}
