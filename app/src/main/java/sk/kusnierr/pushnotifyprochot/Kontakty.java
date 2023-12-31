package sk.kusnierr.pushnotifyprochot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OneSignal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Kontakty extends MainActivity {
    private Toolbar tb;
    String currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakty);
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView noteView = (TextView) findViewById(R.id.textTelef);
        noteView.setText("045/ 67 26 838");
        Linkify.addLinks(noteView, Linkify.ALL);
        TextView feedback = (TextView) findViewById(R.id.textEmail);
        feedback.setText(Html.fromHtml("<a href=\"obecprochot@prochot.dcom.sk\">obecprochot@prochot.dcom.sk</a>"));
        feedback.setMovementMethod(LinkMovementMethod.getInstance());

        OneSignal.setNotificationOpenedHandler(new OneSignal.OSNotificationOpenedHandler() {
            //OSNotificationOpenedResult osNotificationOpenedResult = new OSNotificationOpenedResult(str);
            @Override
            public void notificationOpened(OSNotificationOpenedResult osNotificationOpenedResult) {

                OSNotification notification = osNotificationOpenedResult.getNotification();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        CustomModel customModel = new CustomModel(-1,currentDate,notification.getTitle(),notification.getBody());
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(Kontakty.this);
                        boolean b = dataBaseHelper.addOne(customModel);
                        Intent intent = new Intent(Kontakty.this, MainActivity.class);
                        intent.putExtra("date", currentDate);
                        intent.putExtra("title", notification.getTitle());
                        intent.putExtra("body", notification.getBody());
                        startActivity(intent);
                    }
                });
                osNotificationOpenedResult.getNotification();
            }
        });

    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
        super.onBackPressed();
    }

}