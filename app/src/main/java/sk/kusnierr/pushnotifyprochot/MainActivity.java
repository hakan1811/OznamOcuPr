package sk.kusnierr.pushnotifyprochot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalNotificationManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


    public class MainActivity extends AppCompatActivity {
    TextView Title,Message,Date;
    int count = 0;


  /*   BroadcastReceiver notificationReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            count++;
            if (intent!=null && intent.getAction() != null){
                if (intent.getAction().equals(NotificationService.ACTION_NOTIFICATION_RECIVED)){
                    *//*if (count == 1) {
                        CustomModel customModel = new CustomModel(-1, intent.getStringExtra("date"), intent.getStringExtra("title"), intent.getStringExtra("body"));
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                        boolean b = dataBaseHelper.addOne(customModel);
                    }*//*
                    Date.setText(intent.getStringExtra("date"));
                    Date.setGravity(Gravity.LEFT);
                    Title.setText(intent.getStringExtra("title"));
                    Title.setGravity(Gravity.LEFT);
                    Message.setText(intent.getStringExtra("body"));

                }
            }

        }
    };*/
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  imageLogo = (ImageView) findViewById(R.id.imageViewLogo);
        //.setAlpha(50);
        Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
        String currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());
        String currentDate1 = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            // first time run?
            if (pref.getBoolean("firstTimeRun", true)) {

                // start the preferences activity
                startActivity(new Intent(getBaseContext(), Odbery.class));

                //get the preferences editor
                SharedPreferences.Editor editor = pref.edit();

                // avoid for next run
                editor.putBoolean("firstTimeRun", false);
                editor.commit();
            }


            /*IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(NotificationService.ACTION_NOTIFICATION_RECIVED);
            LocalBroadcastManager.getInstance(this).registerReceiver(notificationReciver, intentFilter);*/

        Title = (TextView)findViewById(R.id.textPredmet);
        Message = (TextView)findViewById(R.id.textMessage);
        Date = (TextView)findViewById(R.id.textDate);
        // nastavit domovsku obrazovku

                Date.setGravity(Gravity.CENTER);
                Title.setGravity(Gravity.CENTER);
                Date.setText("Dnes je " + currentDate1 + "." );
                Title.setText("Vitajte v testovacej verzii aplikácie eRozhlas OÚ Prochot.");



        OneSignal.setNotificationOpenedHandler(new OneSignal.OSNotificationOpenedHandler() {
            //OSNotificationOpenedResult osNotificationOpenedResult = new OSNotificationOpenedResult(str);
            @Override
            public void notificationOpened(OSNotificationOpenedResult osNotificationOpenedResult) {


                    OSNotification notification = osNotificationOpenedResult.getNotification();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                           CustomModel customModel = new CustomModel(-1,currentDate,notification.getTitle(),notification.getBody());
                           DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                            boolean b = dataBaseHelper.addOne(customModel);
                            Intent intent = new Intent(MainActivity.this, Detail.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

   /*  @Override
        protected void onDestroy() {
            super.onDestroy();
           LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationReciver);
           count = 0;
        }*/

        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();

        if (item_id == R.id.oznamy){
            Toast.makeText(this,"Vybral si oznamy",Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.activity_oznamy);
                    Intent intent = new Intent(MainActivity.this, Oznamy.class);
                    startActivity(intent);
                    return true;
        }
        if (item_id == R.id.kontakt){
            Toast.makeText(this,"Vybral si kontakty",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_kontakty);
            startActivity(new Intent(MainActivity.this,Kontakty.class));
            return true;
        }
            if (item_id == R.id.odbery){
                Toast.makeText(this,"Vybral si odbery",Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_odbery);
                startActivity(new Intent(MainActivity.this,Odbery.class));
                return true;
            }
        if (item_id == R.id.home){
            Toast.makeText(this,"Návrat na domácu obrazovku",Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
        @Override
        public void onBackPressed() {
            // Do Here what ever you want do on back press;
        }



}