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

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
            bottomNavigationView.setSelectedItemId(R.id.test_home);
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int id = (item.getItemId());
                    if (id == R.id.test_home) {
                        return true;}
                    if (id == R.id.test_oznamy){
                        startActivity(new Intent(getApplicationContext(),Oznamy.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        return true;}
                    if (id == R.id.test_odbery){
                        startActivity(new Intent(getApplicationContext(), Odbery.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        return true;}
                    if (id == R.id.test_kontakty){
                        startActivity(new Intent(getApplicationContext(), Kontakty.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        return true;}
                else {
                        return false;
                    }

            });

        //Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
        String currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());
        String currentDate1 = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        //setSupportActionBar(tb);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

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
                OneSignal.sendTag("oznamy", "1");
                OneSignal.sendTag("predaj", "2");
                OneSignal.sendTag("poruchy", "3");
            }



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
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }*/

  /*

   @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
       BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
       bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        if (item_id == R.id.bottom_home){
            //Toast.makeText(this,"Vybral si home",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item_id == R.id.bottom_oznamy){
            startActivity(new Intent(getApplicationContext(), Oznamy.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            return true;
        }
            if (item_id == R.id.bottom_odbery){
                startActivity(new Intent(getApplicationContext(), Odbery.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
        if (item_id == R.id.bottom_kontakty){
            startActivity(new Intent(getApplicationContext(), Kontakty.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            return true;
        }
        //return false;
        return super.onOptionsItemSelected(item);
    }*/



}