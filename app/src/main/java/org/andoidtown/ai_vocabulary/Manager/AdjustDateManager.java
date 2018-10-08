package org.andoidtown.ai_vocabulary.Manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.andoidtown.ai_vocabulary.mainactivity_component.MainActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AdjustDateManager
{
    private SQLiteDatabase database;
    private DateProcessManager dateProcessManager;
    public AdjustDateManager(SQLiteDatabase database)
    {
        this.database = database;
        this.dateProcessManager = new DateProcessManager();
    }

    public long adjustTestDate(Calendar theDay)
    {

        long diffDay = getDayDifferenceBtwRecentTestDayAndTheDay(theDay);

        if(diffDay > 0)
        {
            queryToAdjustTestDates(diffDay);
        }
        return diffDay;
    }
    private long getDayDifferenceBtwRecentTestDayAndTheDay(Calendar theDayCalendar)
    {
        long diffDay = -1;
        Date theDate = theDayCalendar.getTime();
        String dateString = dateProcessManager.getFormattedDate(theDate);
        Date recentTestDate;
        Cursor cursor = database.rawQuery("SELECT next_test_date FROM word_group" +
                " WHERE date(next_test_date) < date('"+ dateString +"') ORDER BY next_test_date DESC LIMIT 1",null);
        if(cursor.getCount() != 0)
        {
            cursor.moveToNext();
            Log.d("testDate",cursor.getString(0));
            recentTestDate = dateProcessManager.stringToDate(cursor.getString(0));
            diffDay = getDayDifference(theDate, recentTestDate);
        }
        cursor.close();
        return diffDay;
    }
    public Calendar getZeroTimeCal(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal;
    }

    public long getDayDifference(Date substractee, Date substracter)
    {
        Calendar substracteeCal = getZeroTimeCal(substractee);
        Calendar substracterCal =  getZeroTimeCal(substracter);
        long diffMillis = substracteeCal.getTimeInMillis() - substracterCal.getTimeInMillis();
        if(diffMillis <= 0)
            return -1;
        Log.d("diffMilli",Long.toString(diffMillis));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(diffMillis);
        long diffDay = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        Log.d("diffDay",Long.toString(diffDay));
        return diffDay;
    }
    private void queryToAdjustTestDates(long diffDay)
    {
        Log.d("차이값 조정",Long.toString(diffDay));
        String values[] = {};
        database.execSQL("update word_group set next_test_date =" +
                " datetime(next_test_date,'+"+diffDay+" day')",values);
    }
}
