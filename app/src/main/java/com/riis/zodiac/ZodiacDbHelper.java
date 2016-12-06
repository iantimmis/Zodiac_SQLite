package com.riis.zodiac;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by itimmis on 12/6/2016.
 */

public class ZodiacDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Zodiac.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ZodiacContract.ZodiacEntry.TABLE_NAME + " (" +
                    ZodiacContract.ZodiacEntry._ID + " INTEGER PRIMARY KEY," +
                    ZodiacContract.ZodiacEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ZodiacContract.ZodiacEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    ZodiacContract.ZodiacEntry.COLUMN_NAME_SYMBOL + TEXT_TYPE + COMMA_SEP +
                    ZodiacContract.ZodiacEntry.COLUMN_NAME_MONTH + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ZodiacContract.ZodiacEntry.TABLE_NAME;

    public ZodiacDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
