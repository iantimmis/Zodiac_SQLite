package com.riis.zodiac;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URLDecoder;

public class ZodiacDetailActivity extends Activity {

    public static final String EXTRA_SIGN = "ZodiacSign";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zodiac_detail);

        String name = (String)getIntent().getExtras().get(ZodiacContract.ZodiacEntry.COLUMN_NAME_NAME);
        String description = (String)getIntent().getExtras().get(ZodiacContract.ZodiacEntry.COLUMN_NAME_DESCRIPTION);
        String symbol = (String)getIntent().getExtras().get(ZodiacContract.ZodiacEntry.COLUMN_NAME_SYMBOL);
        String month = (String)getIntent().getExtras().get(ZodiacContract.ZodiacEntry.COLUMN_NAME_MONTH);

        Zodiac zodiac = new Zodiac(name, description, symbol, month);

        TextView tvName = (TextView) findViewById(R.id.name);
        tvName.setText(zodiac.getName());

        TextView tvDescription = (TextView) findViewById(R.id.description);
        tvDescription.setText(zodiac.getDescription());

        TextView tvSymbol = (TextView) findViewById(R.id.symbol);
        tvSymbol.setText(zodiac.getSymbol());

        TextView tvMonth = (TextView) findViewById(R.id.month);
        tvMonth.setText(zodiac.getMonth());

        new AsyncTaskParseJson(zodiac).execute();

    }

    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {
        String yourJsonStringUrl = "http://a.knrz.co/horoscope-api/current/";
        String horoscope = "";

        public AsyncTaskParseJson(Zodiac sign) {
            yourJsonStringUrl += sign.getName().toLowerCase();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... arg0) {
            try {
                // instantiate our json parser
                JsonParser jParser = new JsonParser();

                // get json string from url
                JSONObject json = jParser.getJSONFromUrl(yourJsonStringUrl);
                horoscope = json.getString("prediction");
                horoscope = URLDecoder.decode(horoscope);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            TextView display = (TextView) findViewById(R.id.daily);
            display.setText(horoscope);
        }
    }
}