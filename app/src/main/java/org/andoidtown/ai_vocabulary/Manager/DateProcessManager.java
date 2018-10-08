package org.andoidtown.ai_vocabulary.Manager;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class DateProcessManager{
    private SimpleDateFormat dateFormat;
    public DateProcessManager()
    {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }
    public String getTodayDate()
    {
        String todayString;
        todayString = getFormattedDate(Calendar.getInstance().getTime());
        return todayString;
    }
    public String cutFromYearToDay(String rawDate)
    {
        SimpleDateFormat yMdDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String yMdDateString = yMdDateFormat.format(stringToDate(rawDate));
        return yMdDateString;
    }
    public String getFormattedDate(String dateString)
    {
        Date date;
        String formattedDateString = "";
        try
        {
            date = dateFormat.parse(dateString);
            formattedDateString = dateFormat.format(date);
        } catch(Exception ex)
        {
            Log.d("expection",ex.toString());
        }
        return formattedDateString;
    }
    public String getFormattedDate(Date date)
    {
        String formattedDateString = dateFormat.format(date);
        return formattedDateString;
    }
    public void setDateFormat(String format)
    {
        dateFormat = new SimpleDateFormat(format);
    }
    public Date stringToDate(String date)
    {
        Date lastTestDay = new Date();
        if(!date.equals(""));
        {
            try {
                Log.d("date",date);
                lastTestDay = dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return lastTestDay;
    }
}
