package com.example.pos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class PosDB extends SQLiteOpenHelper{

    private Context context;
    public static final String DB_NAME = "pos_machine.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "pos_machine";
    public static final String CODE = "item_code";
    public static final String NAME = "item_name";
    public static final String UNIT = "unit";
    public static final String PRICE = "unit_price";
    public PosDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE pos_machine ( " + CODE + " TEXT PRIMARY KEY, " + NAME + " TEXT, " + UNIT + " TEXT, "+ PRICE + " FLOAT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addItem(Integer code, String item, String unit, Double price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CODE, code);
        contentValues.put(NAME, item);
        contentValues.put(UNIT, unit);
        contentValues.put(PRICE, price);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            Toast.makeText(context, "Failed to insert data or Repeated Code", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context, "Data Inserted Successful", Toast.LENGTH_SHORT).show();
        }
    }


    public String[] searchItemByCode(String code) {
        String itemName = null;
         String unitPrice = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {"item_name, unit_price"};


        String selection = "item_code = ?";


        String[] selectionArgs = {code};

        Cursor cursor = db.query("pos_machine", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToNext()) {
            itemName = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
            unitPrice = cursor.getString(cursor.getColumnIndexOrThrow("unit_price"));
        }

        cursor.close();
        db.close();
       String [] Item_Info = {itemName, unitPrice};

        return Item_Info;
    }

}

