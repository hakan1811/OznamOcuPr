package sk.kusnierr.pushnotifyprochot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Odbery extends MainActivity  {
    private Toolbar tb;
    String currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());
    SharedPreferences sharedPrefs;

    //Switch All, oznamOU, Predaj, Poruchy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odbery);
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Switch all = (Switch) findViewById(R.id.switchAll);
        Switch oznamOU = (Switch) findViewById(R.id.switchOznamyOU);
        Switch predaj = (Switch) findViewById(R.id.switchPredaj);
        Switch poruchy = (Switch) findViewById(R.id.switchPoruchy);

        sharedPrefs = getSharedPreferences("allSw", MODE_PRIVATE);
            all.setChecked(sharedPrefs.getBoolean("allSwSw", true));
            if (all.isChecked()) {
                oznamOU.setEnabled(false);
                predaj.setEnabled(false);
                poruchy.setEnabled(false);
            }

            oznamOU.setChecked(sharedPrefs.getBoolean("oznamySwSw", true));
            predaj.setChecked(sharedPrefs.getBoolean("predajSwSw", true));
            poruchy.setChecked(sharedPrefs.getBoolean("poruchySwSw", true));

        OneSignal.setNotificationOpenedHandler(new OneSignal.OSNotificationOpenedHandler() {
            //OSNotificationOpenedResult osNotificationOpenedResult = new OSNotificationOpenedResult(str);
            @Override
            public void notificationOpened(OSNotificationOpenedResult osNotificationOpenedResult) {

                OSNotification notification = osNotificationOpenedResult.getNotification();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        CustomModel customModel = new CustomModel(-1,currentDate,notification.getTitle(),notification.getBody());
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(Odbery.this);
                        boolean b = dataBaseHelper.addOne(customModel);
                        Intent intent = new Intent(Odbery.this, Detail.class);
                        intent.putExtra("date", currentDate);
                        intent.putExtra("title", notification.getTitle());
                        intent.putExtra("body", notification.getBody());
                        startActivity(intent);
                    }
                });
                osNotificationOpenedResult.getNotification();
            }
        });
        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                if (toggleButton.isChecked())
                {
                    oznamOU.setChecked(true);
                    predaj.setChecked(true);
                    poruchy.setChecked(true);
                    oznamOU.setEnabled(false);
                    predaj.setEnabled(false);
                    poruchy.setEnabled(false);
                        SharedPreferences.Editor editor = getSharedPreferences("allSw", MODE_PRIVATE).edit();
                    editor.putBoolean("allSwSw", true);
                    editor.putBoolean("oznamySwSw", true);
                    editor.putBoolean("predajSwSw", true);
                    editor.putBoolean("poruchySwSw", true);
                    editor.commit();
                }
                else
                {
                    oznamOU.setChecked(false);
                    predaj.setChecked(false);
                    poruchy.setChecked(false);
                    oznamOU.setEnabled(true);
                    predaj.setEnabled(true);
                    poruchy.setEnabled(true);
                    SharedPreferences.Editor editor = getSharedPreferences("allSw", MODE_PRIVATE).edit();
                    editor.putBoolean("allSwSw", false);
                    editor.putBoolean("oznamySwSw", false);
                    editor.putBoolean("predajSwSw", false);
                    editor.putBoolean("poruchySwSw", false);
                    editor.commit();
                    Toast.makeText(Odbery.this,"POZOR! Pri tomto nastavení nebudete dostávať žiadne upozornenia!",Toast.LENGTH_LONG).show();
                }
            }
        });

        oznamOU.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("allSw", MODE_PRIVATE).edit();
                    editor.putBoolean("oznamySwSw", true);
                    editor.commit();
                    OneSignal.sendTag("oznamy", "1");
                    OneSignal.sendTag("predaj", "2");
                    OneSignal.sendTag("poruchy", "3");
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("allSw", MODE_PRIVATE).edit();
                    editor.putBoolean("oznamySwSw", false);
                    editor.commit();
                    OneSignal.deleteTag("oznamy");
                    OneSignal.deleteTag("predaj");
                    OneSignal.deleteTag("poruchy");
                }
                if (predaj.isChecked() && poruchy.isChecked() && oznamOU.isChecked()){
                    all.setChecked(true);
                }
                if (predaj.isChecked() == false && poruchy.isChecked()== false && oznamOU.isChecked()== false){
                    Toast.makeText(Odbery.this,"POZOR! Pri tomto nastavení nebudete dostávať žiadne upozornenia!",Toast.LENGTH_LONG).show();
                }
            }
        });

        predaj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("allSw", MODE_PRIVATE).edit();
                    editor.putBoolean("predajSwSw", true);
                    editor.commit();
                    OneSignal.sendTag("predaj", "2");
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("allSw", MODE_PRIVATE).edit();
                    editor.putBoolean("predajSwSw", false);
                    editor.commit();
                    OneSignal.deleteTag("predaj");
                    }
                if (predaj.isChecked() && poruchy.isChecked() && oznamOU.isChecked()){
                    all.setChecked(true);
                }
                if (predaj.isChecked() == false && poruchy.isChecked()== false && oznamOU.isChecked()== false){
                    Toast.makeText(Odbery.this,"POZOR! Pri tomto nastavení nebudete dostávať žiadne upozornenia!",Toast.LENGTH_LONG).show();
                }
            }
        });

        poruchy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("allSw", MODE_PRIVATE).edit();
                    editor.putBoolean("poruchySwSw", true);
                    editor.commit();
                    OneSignal.sendTag("poruchy", "3");
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("allSw", MODE_PRIVATE).edit();
                    editor.putBoolean("poruchySwSw", false);
                    editor.commit();
                    OneSignal.deleteTag("poruchy");
                    }
                if (predaj.isChecked() && poruchy.isChecked() && oznamOU.isChecked()){
                    all.setChecked(true);
                }
                if (predaj.isChecked() == false && poruchy.isChecked()== false && oznamOU.isChecked()== false){
                    Toast.makeText(Odbery.this,"POZOR! Pri tomto nastavení nebudete dostávať žiadne upozornenia!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}