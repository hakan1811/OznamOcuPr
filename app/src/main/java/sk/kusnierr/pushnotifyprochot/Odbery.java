package sk.kusnierr.pushnotifyprochot;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.widget.Toolbar;

public class Odbery extends MainActivity  {
    private Toolbar tb;
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
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("allSw", MODE_PRIVATE).edit();
                    editor.putBoolean("oznamySwSw", false);
                    editor.commit();                }
                if (predaj.isChecked() && poruchy.isChecked() && oznamOU.isChecked()){
                    all.setChecked(true);
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
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("allSw", MODE_PRIVATE).edit();
                    editor.putBoolean("predajSwSw", false);
                    editor.commit();                }
                if (predaj.isChecked() && poruchy.isChecked() && oznamOU.isChecked()){
                    all.setChecked(true);
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
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("allSw", MODE_PRIVATE).edit();
                    editor.putBoolean("poruchySwSw", false);
                    editor.commit();                }
                if (predaj.isChecked() && poruchy.isChecked() && oznamOU.isChecked()){
                    all.setChecked(true);
                }
            }
        });
    }


}