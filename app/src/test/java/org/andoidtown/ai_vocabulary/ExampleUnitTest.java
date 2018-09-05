package org.andoidtown.ai_vocabulary;

import android.database.sqlite.SQLiteDatabase;

import org.andoidtown.ai_vocabulary.mainactivity_component.MainActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    MainActivity testActivity;
    SQLiteDatabase testDB;
    @Before
    public void setUp() throws Exception {
        testActivity = new MainActivity();
        testDB = SQLiteDatabase.openOrCreateDatabase("testDatabase",null);
        testDB.execSQL("create table test_table(test_wordtest_date datetime)");
    }
    @After
    public void tearDown() throws Exception {
        testActivity = null;
        testDB.execSQL("drop table test_table");
        testDB.close();
    }
    @Test
    public void getDiffLastTestDayToTodayTest() {
        testDB.execSQL("insert into test_table(test_wordtest_date) values (strftime('%Y-%m-%d',date('now','- 3 day'),'localtime'))",null);
        long diffDay = testActivity.getDiffLastTestDayToToday();
        assertEquals(3,diffDay);
    }
}