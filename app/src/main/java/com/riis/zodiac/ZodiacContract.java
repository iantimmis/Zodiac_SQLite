package com.riis.zodiac;

import android.provider.BaseColumns;

/**
 * Created by itimmis on 12/6/2016.
 */

public final class ZodiacContract {

    private ZodiacContract() {}

    public static class ZodiacEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_SYMBOL = "symbol";
        public static final String COLUMN_NAME_MONTH = "month";
    }
}
