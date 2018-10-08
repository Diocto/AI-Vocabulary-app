package org.andoidtown.ai_vocabulary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.andoidtown.ai_vocabulary.Manager.DateProcessManager;
import org.andoidtown.ai_vocabulary.Manager.StandardDataManager;

import java.util.ArrayList;
import java.util.Calendar;

public class WordDAO extends SQLiteOpenHelper
{
    public SQLiteDatabase database;
    private static WordDAO singleTone;
    public static final int DB_VERSION = 1;
    public static final String DBFILE_CONTACT = "vocabularyDataBase";
    private WordDAO(Context context)
    {
        super(context, DBFILE_CONTACT, null, DB_VERSION);
        database = getReadableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE word(" +
                    "value text," +
                    "meaning text," +
                    "correct_answer_num integer," +
                    "incorrect_answer_num integer," +
                    "group_name text)");
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public static WordDAO getWordDAO(Context context)
    {
        if(singleTone == null)
        {
            singleTone = new WordDAO(context);
        }
        return singleTone;
    }
    public ArrayList<ArrayList<WordParceble> > getTodaysWordTest()
    {
        ArrayList<ArrayList<WordParceble>> wordGroupList = new ArrayList<>();
        DateProcessManager dateProcessManager = new DateProcessManager();
        String today = dateProcessManager.getTodayDate();
        today = dateProcessManager.cutFromYearToDay(today);
        String todayDate[] = {today};
        String groupSql = "SELECT * FROM word_group WHERE strftime('%Y-%m-%d',next_test_date) = ?";
        Cursor wordGroupCursor = database.rawQuery(groupSql, todayDate);
        if(wordGroupCursor.getCount() != 0)
        {
            for(int group_i = 0; group_i < wordGroupCursor.getCount(); group_i++)
            {
                wordGroupList.add(new ArrayList<WordParceble>());
                wordGroupCursor.moveToNext();
                String wordSql = "SELECT * FROM word WHERE group_name = ?";
                String groupName[] = {wordGroupCursor.getString(0)};
                Cursor wordCursor = database.rawQuery(wordSql, groupName);
                if(wordCursor.getCount() != 0)
                {
                    for(int word_i = 0; word_i < wordCursor.getCount(); word_i++)
                    {
                        wordCursor.moveToNext();
                        WordParceble word = new WordParceble(wordCursor.getString(0),
                                wordCursor.getString(1), wordCursor.getString(4));
                        wordGroupList.get(group_i).add(word);
                    }
                }
            }
        }
        return wordGroupList;
    }
    public void increseIncorrectNum(WordParceble word)
    {
        String values[] = {word.getId()};
        database.execSQL("UPDATE word SET incorrect_answer_num = incorrect_answer_num + 1 WHERE value = ?");
    }
    public void increseCorrectNum(WordParceble word)
    {
        String values[] = {word.getWord()};
        database.execSQL("UPDATE word SET correct_answer_num = correct_answer_num + 1 WHERE value = ?");
    }
    public void insertWordTest(String[] values)
    {
        database.execSQL("insert into word_test(test_date, correct_answer_num, incorrect_answer_num, test_time)" +
                " values(?,?,?,?)",values);
    }
    public void increaseNextTestDate(int increaseNumber, String group_name)
    {
        String values[] = {group_name};
        database.execSQL("update word_group set next_test_date =" +
                " datetime(next_test_date,'+"+increaseNumber+" day') WHERE group_name = ?",values);
    }
    public int getTestNumber(String[] group_name)
    {
        Cursor testCursor = database.rawQuery("select * from word_test where group_name = ?",group_name);
        Integer testNumber = testCursor.getCount();
        return testNumber;
    }

}
