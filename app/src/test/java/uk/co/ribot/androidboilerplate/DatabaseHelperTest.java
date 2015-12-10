package com.gamefriq.androidboilerplate;

import android.database.Cursor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import rx.observers.TestSubscriber;
import com.gamefriq.androidboilerplate.data.local.DatabaseHelper;
import com.gamefriq.androidboilerplate.data.local.Db;
import com.gamefriq.androidboilerplate.data.model.Ribot;
import com.gamefriq.androidboilerplate.test.common.TestDataFactory;
import com.gamefriq.androidboilerplate.test.common.rules.TestComponentRule;
import com.gamefriq.androidboilerplate.util.DefaultConfig;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class DatabaseHelperTest {

    private DatabaseHelper mDatabaseHelper;

    @Rule
    public final TestComponentRule component =
            new TestComponentRule(RuntimeEnvironment.application);

    @Before
    public void setUp() {
        mDatabaseHelper = component.getDatabaseHelper();
        mDatabaseHelper.clearTables().subscribe();
    }

    @Test
    public void setRibots() {
        Ribot ribot1 = TestDataFactory.makeRibot();
        Ribot ribot2 = TestDataFactory.makeRibot();
        List<Ribot> ribots = Arrays.asList(ribot1, ribot2);

        TestSubscriber<Ribot> result = new TestSubscriber<>();
        mDatabaseHelper.setRibots(ribots).subscribe(result);
        result.assertNoErrors();
        result.assertReceivedOnNext(ribots);

        Cursor cursor = mDatabaseHelper.getBriteDb()
                .query("SELECT * FROM " + Db.RibotProfileTable.TABLE_NAME);
        assertEquals(2, cursor.getCount());
        for (Ribot ribot : ribots) {
            cursor.moveToNext();
            assertEquals(ribot.profile, Db.RibotProfileTable.parseCursor(cursor));
        }
    }

    @Test
    public void getRibots() {
        Ribot ribot1 = TestDataFactory.makeRibot();
        Ribot ribot2 = TestDataFactory.makeRibot();
        List<Ribot> ribots = Arrays.asList(ribot1, ribot2);

        mDatabaseHelper.setRibots(ribots).subscribe();

        TestSubscriber<List<Ribot>> result = new TestSubscriber<>();
        mDatabaseHelper.getRibots().subscribe(result);
        result.assertNoErrors();
        result.assertValue(ribots);
    }

}