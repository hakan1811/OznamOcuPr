package sk.kusnierr.pushnotifyprochot;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Oznamy extends MainActivity {
    private Toolbar tb;
    private ListView lv_oznamyList;
    ArrayAdapter oznamyArrayAdapter;
    DataBaseHelper dataBaseHelper;
    SimpleCursorAdapter adapter;
    TextView Datum, Predmet, Body;
    String currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oznamy);
        lv_oznamyList = findViewById(R.id.listOznamy);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        // Nastavenie výšky ListView na 60% výšky displeja (môžete upraviť podľa potreby)
        int listViewHeight = (int) (0.6 * screenHeight);
        lv_oznamyList.getLayoutParams().height = listViewHeight;
        lv_oznamyList.requestLayout();
        //tb = findViewById(R.id.toolbar);
        //setSupportActionBar(tb);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.test_oznamy);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = (item.getItemId());
            if (id == R.id.test_oznamy) {
                return true;}
            if (id == R.id.test_home){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

        Datum = (TextView)findViewById(R.id.mtrl_list_item_date);
        Predmet = (TextView)findViewById(R.id.mtrl_list_item_title);
        Body = (TextView)findViewById(R.id.mtrl_list_item_body);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        dataBaseHelper = new DataBaseHelper(Oznamy.this);
        zobrazenieVysledkov(dataBaseHelper);

        OneSignal.setNotificationOpenedHandler(new OneSignal.OSNotificationOpenedHandler() {
            //OSNotificationOpenedResult osNotificationOpenedResult = new OSNotificationOpenedResult(str);
            @Override
            public void notificationOpened(OSNotificationOpenedResult osNotificationOpenedResult) {

                OSNotification notification = osNotificationOpenedResult.getNotification();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                      CustomModel customModel = new CustomModel(-1,currentDate,notification.getTitle(),notification.getBody());
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(Oznamy.this);
                        boolean b = dataBaseHelper.addOne(customModel);
                        Intent intent = new Intent(Oznamy.this, Detail.class);
                        intent.putExtra("date", currentDate);
                        intent.putExtra("title", notification.getTitle());
                        intent.putExtra("body", notification.getBody());
                        startActivity(intent);
                    }
                });
                osNotificationOpenedResult.getNotification();
            }
        });
       lv_oznamyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?>  adapterView, View view, int position, long l) {
                Cursor row = (Cursor) adapterView.getItemAtPosition(position);
                String date = row.getString(row.getColumnIndexOrThrow("OZNAMY_DATE"));
                String title = row.getString(row.getColumnIndexOrThrow("OZNAMY_PREDMET"));
                String body = row.getString(row.getColumnIndexOrThrow("OZNAMY_BODY"));
                Intent intent = new Intent(Oznamy.this, Detail.class);
                intent.putExtra("date",date);
                intent.putExtra("title", title);
                intent.putExtra("body",body);

                startActivity(intent);
            }
        });
        lv_oznamyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView1, View view, int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Oznamy.this);
                builder.setIcon(android.R.drawable.ic_dialog_alert).setMessage("Naozaj chcete položku vymazať?").setPositiveButton("Áno",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Cursor row = (Cursor) adapterView1.getItemAtPosition(pos);
                                Integer id = Integer.parseInt(row.getString(row.getColumnIndexOrThrow("ID")));
                                //CustomModel clickedOznam = (CustomModel) adapterView1.getItemAtPosition(pos);
                                dataBaseHelper.delete(id);
                                zobrazenieVysledkov(dataBaseHelper);
                                Toast.makeText(Oznamy.this, "Vymazané ",Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                Toast.makeText(getApplicationContext(),"Nevymazané!",Toast.LENGTH_LONG).show();
                            }
                        });

                final AlertDialog alert = builder.create();
                builder.show();

                return true;
            }

        });

    }

    private void zobrazenieVysledkov(DataBaseHelper dataBaseHelper) {
        lv_oznamyList.findViewById(R.id.listOznamy);
        int layoutstyle=R.layout.list_item_tree;
        int[] xml_id = new int[] {
                R.id.mtrl_list_item_id,
                R.id.mtrl_list_item_date,
                R.id.mtrl_list_item_title,
                R.id.mtrl_list_item_body
        };
        String[] column = new String[] {
                "ID",
                "OZNAMY_DATE",
                "OZNAMY_PREDMET",
                "OZNAMY_BODY"
        };
        Cursor row = dataBaseHelper.fetchAllData();
        adapter = new SimpleCursorAdapter(this, layoutstyle,row,column, xml_id, 0);
        lv_oznamyList.setAdapter(adapter);

    }
   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_oznamy);
        if (item_id == R.id.bottom_home){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            return true;
        }
        if (item_id == R.id.bottom_oznamy){
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