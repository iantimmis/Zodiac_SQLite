package com.riis.zodiac;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends ListActivity {

    SQLiteDatabase db;
    ZodiacDbHelper zodiacDbHelper;
    ArrayList<Zodiac> signs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        zodiacDbHelper = new ZodiacDbHelper(getApplicationContext());
        File database = getApplicationContext().getDatabasePath(zodiacDbHelper.DATABASE_NAME);

        // If no database exists, create a new one with default info
        if (!database.exists()) {
            Toast.makeText(getApplicationContext(), "No DB Exists, will create one", Toast.LENGTH_SHORT);
            db = zodiacDbHelper.getWritableDatabase();
            for (int x = 0; x < Zodiac.signs.length; x++)
            {
                ContentValues values = new ContentValues();
                values.put(ZodiacContract.ZodiacEntry.COLUMN_NAME_NAME, Zodiac.signs[x].getName());
                values.put(ZodiacContract.ZodiacEntry.COLUMN_NAME_DESCRIPTION, Zodiac.signs[x].getDescription());
                values.put(ZodiacContract.ZodiacEntry.COLUMN_NAME_MONTH, Zodiac.signs[x].getMonth());
                values.put(ZodiacContract.ZodiacEntry.COLUMN_NAME_SYMBOL, Zodiac.signs[x].getSymbol());
                long newRowId = db.insert(ZodiacContract.ZodiacEntry.TABLE_NAME, null, values);
            }
        }

        // Read Database values and create zodiac list
        db = zodiacDbHelper.getReadableDatabase();

        String[] projection = {
                ZodiacContract.ZodiacEntry.COLUMN_NAME_NAME,
                ZodiacContract.ZodiacEntry.COLUMN_NAME_DESCRIPTION,
                ZodiacContract.ZodiacEntry.COLUMN_NAME_MONTH,
                ZodiacContract.ZodiacEntry.COLUMN_NAME_SYMBOL,
        };

        Cursor cursor = db.query(
                ZodiacContract.ZodiacEntry.TABLE_NAME,
                projection,
                null, // Selection
                null, // SelectionArgs
                null, // groupBy
                null, // having
                null  // OrderBy
        );


        signs = new ArrayList<Zodiac>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ZodiacContract.ZodiacEntry.COLUMN_NAME_NAME));
            String description = cursor.getString(cursor.getColumnIndex(ZodiacContract.ZodiacEntry.COLUMN_NAME_DESCRIPTION));
            String month = cursor.getString(cursor.getColumnIndex(ZodiacContract.ZodiacEntry.COLUMN_NAME_MONTH));
            String symbol = cursor.getString(cursor.getColumnIndex(ZodiacContract.ZodiacEntry.COLUMN_NAME_SYMBOL));
            signs.add(new Zodiac(name, description,symbol, month));
        }


        ListView listSigns = getListView();
        ArrayAdapter<Zodiac> listAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, signs);
        listSigns.setAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        Intent intent = new Intent(MainActivity.this, ZodiacDetailActivity.class);
        intent.putExtra(ZodiacContract.ZodiacEntry.COLUMN_NAME_NAME, signs.get(position).getName());
        intent.putExtra(ZodiacContract.ZodiacEntry.COLUMN_NAME_DESCRIPTION, signs.get(position).getDescription());
        intent.putExtra(ZodiacContract.ZodiacEntry.COLUMN_NAME_MONTH, signs.get(position).getMonth());
        intent.putExtra(ZodiacContract.ZodiacEntry.COLUMN_NAME_SYMBOL, signs.get(position).getSymbol());
        startActivity(intent);
    }
}
