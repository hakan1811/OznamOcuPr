package sk.kusnierr.pushnotifyprochot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Detail extends MainActivity {
    private Toolbar tb;
    TextView Title,Message,Date,Header;
    String currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Title = (TextView)findViewById(R.id.textViewTitle);
        Message = (TextView)findViewById(R.id.textViewBody);
        Date = (TextView)findViewById(R.id.textViewDate);
        Header = (TextView)findViewById(R.id.textViewHeader);
        Date.setGravity(Gravity.CENTER);
        Header.setText("Detail:");
        Date.setText(getIntent().getStringExtra("date"));
        Title.setText(getIntent().getStringExtra("title"));
        Message.setText(getIntent().getStringExtra("body"));

        OneSignal.setNotificationOpenedHandler(new OneSignal.OSNotificationOpenedHandler() {
            //OSNotificationOpenedResult osNotificationOpenedResult = new OSNotificationOpenedResult(str);
            @Override
            public void notificationOpened(OSNotificationOpenedResult osNotificationOpenedResult) {

                OSNotification notification = osNotificationOpenedResult.getNotification();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        CustomModel customModel = new CustomModel(-1,currentDate,notification.getTitle(),notification.getBody());
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(Detail.this);
                        boolean b = dataBaseHelper.addOne(customModel);
                        Intent intent = new Intent(Detail.this, Detail.class);
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
}