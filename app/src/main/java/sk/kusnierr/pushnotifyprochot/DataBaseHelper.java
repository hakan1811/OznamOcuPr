package sk.kusnierr.pushnotifyprochot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String OZNAMY_TAB = "OZNAMY_TAB";
    public static final String COLUMN_OZNAMY_DATE = "OZNAMY_DATE";
    public static final String COLUMN_OZNAMY_PREDMET = "OZNAMY_PREDMET";
    public static final String COLUMN_OZNAMY_BODY = "OZNAMY_BODY";
    public static final String COLUMN_ID = "ID";

    Context context;

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatment = "CREATE TABLE " + OZNAMY_TAB + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_OZNAMY_DATE + " TEXT, " + COLUMN_OZNAMY_PREDMET + " TEXT, " + COLUMN_OZNAMY_BODY + " TEXT)";
        db.execSQL(createTableStatment);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(CustomModel customModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_OZNAMY_DATE, customModel.getDate());
        cv.put(COLUMN_OZNAMY_PREDMET,customModel.getPredmet());
        cv.put(COLUMN_OZNAMY_BODY,customModel.getBody());

        long insert = db.insert(OZNAMY_TAB, null, cv);
        if (insert==-1){
            return false;
        }
        else {
            return true;
        }

    }

    public boolean deleteOne(CustomModel customModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + OZNAMY_TAB + " WHERE " + COLUMN_ID + " = " + customModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+OZNAMY_TAB+" WHERE " + COLUMN_ID + " = " +id;
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            return true;
        }
        else {
            return false;
        }
    }
    public Cursor fetchAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ID as _id, * FROM "+OZNAMY_TAB + " ORDER BY ID DESC";
        Cursor row = db.rawQuery(query, null);
        if (row != null) {
            row.moveToFirst();
        }
        return row;
    }

    public List<CustomModel> getEveryone(){
        List<CustomModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + OZNAMY_TAB + " ORDER BY ID DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()){
            do {
                int oznamId = cursor.getInt(0);
                String oznamDatum = cursor.getString(1);
                String oznamPredmet = cursor.getString(2);
                String oznamBody = cursor.getString(3);

                CustomModel newOznam = new CustomModel(oznamId,oznamDatum,oznamPredmet,oznamBody);
                returnList.add(newOznam);

            } while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        db.close();
        return returnList;
    }
}
