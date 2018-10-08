package org.andoidtown.ai_vocabulary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.andoidtown.ai_vocabulary.Manager.AdjustDateManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static com.google.common.truth.Truth.assertThat;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class AdjustDateManagerTest {

    private SQLiteDatabase database;
    private Cursor cursor;
    private AdjustDateManager dateManager;
    @Before
    public void setUp() throws Exception {
        database = SQLiteDatabase.create(null);
        dateManager = new AdjustDateManager(database);
        database.execSQL("CREATE TABLE word_group (" +
                "group_name text PRIMARY KEY," +
                "registered_date datetime," +
                "num_of_test integer," +
                "next_test_date datetime)");
        database.execSQL("CREATE TABLE word_test (" +
                "test_date date," +
                "correct_answer_num integer," +
                "incorrect_answer_num integer," +
                "test_time datetime," +
                "group_name text" +
                ")");
    }
    @After
    public void tearDown() throws Exception {
        database.execSQL("DROP TABLE word_group");
        database.execSQL("DROP TABLE word_test");
        database.close();
        dateManager = null;
    }
    @Test
    public void afterFirstAddWordDealyTest()
    {
        database.execSQL("INSERT INTO word_group (group_name, registered_date, num_of_test, next_test_date)" +
                " VALUES('testgroup1', '2018-08-17 00:00:00', 0, '2018-08-18 00:00:00')");
        Calendar destDateCalendar = Calendar.getInstance();
        destDateCalendar.set(2018,8,23,16,20,33);
        long expectedDateDifference = 5;
        String expectedDate = "2018-08-23 00:00:00";

        long dateDifference = dateManager.adjustTestDate(destDateCalendar);
        Cursor cursor = database.rawQuery("SELECT next_test_date FROM word_group ORDER BY next_test_date DESC LIMIT 1",null);

        assertThat(dateDifference).isEqualTo(expectedDateDifference);
        assertThat(cursor.getCount()).isEqualTo(1);
        assertThat(cursor.moveToNext()).isTrue();
        assertThat(cursor.getString(0)).isEqualTo(expectedDate);
    }
    @Test
    public void afterTestDelayAdjustTest()
    {
        database.execSQL("INSERT INTO word_group (group_name, registered_date, num_of_test, next_test_date)" +
                " VALUES('testgroup1', '2018-08-15 00:00:00', 4, '2018-08-21 00:00:00')");
        database.execSQL("INSERT INTO word_group (group_name, registered_date, num_of_test, next_test_date)" +
                " VALUES('testgroup2', '2018-08-16 00:00:00', 3, '2018-08-19 00:00:00')");
        database.execSQL("INSERT INTO word_group (group_name, registered_date, num_of_test, next_test_date)" +
                " VALUES('testgroup3', '2018-08-17 00:00:00', 2, '2018-08-19 00:00:00')");
        database.execSQL("INSERT INTO word_test(test_date, group_name)" +
                "values('2018-08-15 00:00:00', 'testgroup1')");
        database.execSQL("INSERT INTO word_test(test_date, group_name)" +
                "values('2018-08-16 00:00:00', 'testgroup1')" );
        database.execSQL("INSERT INTO word_test(test_date, group_name)" +
                "values('2018-08-17 00:00:00', 'testgroup1')" );
        database.execSQL("INSERT INTO word_test(test_date, group_name)" +
                "values('2018-08-18 00:00:00', 'testgroup1')" );
        database.execSQL("INSERT INTO word_test(test_date, group_name)" +
                "values('2018-08-16 00:00:00', 'testgroup2')" );
        database.execSQL("INSERT INTO word_test(test_date, group_name)" +
                "values('2018-08-17 00:00:00', 'testgroup2')" );
        database.execSQL("INSERT INTO word_test(test_date, group_name)" +
                "values('2018-08-18 00:00:00', 'testgroup2')" );
        database.execSQL("INSERT INTO word_test(test_date, group_name)" +
                "values('2018-08-17 00:00:00', 'testgroup3')" );
        database.execSQL("INSERT INTO word_test(test_date, group_name)" +
                "values('2018-08-18 00:00:00', 'testgroup3')");
        Calendar destDateCalendar = Calendar.getInstance();
        destDateCalendar.set(2018,8,22);
        int expectedDiffday = 1;
        String expected[] = {"2018-08-22 00:00:00", "2018-08-20 00:00:00", "2018-08-20 00:00:00"};

        long diffDay = dateManager.adjustTestDate(destDateCalendar);
        Cursor cursor = database.rawQuery("SELECT next_test_date FROM word_group ORDER BY next_test_date DESC",null);


        assertEquals(expectedDiffday,diffDay);

        assertThat(cursor.moveToNext()).isTrue();
        assertThat(cursor.getString(0)).isEqualTo(expected[0]);

        assertThat(cursor.moveToNext()).isTrue();
        assertThat(cursor.getString(0)).isEqualTo(expected[1]);

        assertThat(cursor.moveToNext()).isTrue();
        assertThat(cursor.getString(0)).isEqualTo(expected[2]);
    }
}