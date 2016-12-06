package com.riis.zodiac;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml")
public class ZodiacDisplayUnitTest {
    private static final int ARIES_SIGN_INDEX = 0;
    private Activity zodiacDetailActivity;

    @Before
    public void setUp() {
        Intent intent = new Intent(RuntimeEnvironment.application, ZodiacDetailActivity.class);
        intent.putExtra(ZodiacDetailActivity.EXTRA_SIGN, ARIES_SIGN_INDEX);
        zodiacDetailActivity = Robolectric.buildActivity(ZodiacDetailActivity.class).withIntent(intent).create().get();
        assertNotNull("Zodiac Detail Activity not setup", zodiacDetailActivity);
    }

    @Test
    public void zodiacSymbolTest() throws Exception {
        TextView symbolTextView = (TextView) zodiacDetailActivity.findViewById(R.id.symbol);
        assertEquals(Zodiac.signs[ARIES_SIGN_INDEX].getSymbol(), symbolTextView.getText().toString());
    }

    @Test
    public void zodialMonthTest() throws Exception {
        TextView monthTextView = (TextView) zodiacDetailActivity.findViewById(R.id.month);
        assertEquals(Zodiac.signs[ARIES_SIGN_INDEX].getMonth(), monthTextView.getText().toString());
    }

    @Test
    public void zodiacNameTest() {
        TextView nameTextView = (TextView) zodiacDetailActivity.findViewById(R.id.name);
        assertEquals(Zodiac.signs[ARIES_SIGN_INDEX].getName(), nameTextView.getText().toString());
    }

    @Test
    public void zodiacDescriptionTest() {
        TextView descriptionTextView = (TextView) zodiacDetailActivity.findViewById(R.id.description);
        assertEquals(Zodiac.signs[ARIES_SIGN_INDEX].getDescription(), descriptionTextView.getText().toString());
    }

    @Test
    public void zodiacDailyTest() {
        TextView dailyTextView = (TextView) zodiacDetailActivity.findViewById(R.id.daily);
        assertEquals("You’ll continue playing dress-up this week, despite being almost 30, " +
                "and feeling kind of silly every time you put on that professional-looking suit.", dailyTextView.getText().toString());
    }

}