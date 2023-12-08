package sk.kusnierr.pushnotifyprochot;

import android.app.Application;

import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

public class MyApp extends Application {
    private static final String ONESIGNAL_APP_ID = "0786abf4-c0e4-405e-8b2c-7704e82eb417";
    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

    }
}
