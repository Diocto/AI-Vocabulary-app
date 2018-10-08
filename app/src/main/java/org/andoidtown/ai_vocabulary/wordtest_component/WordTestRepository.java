package org.andoidtown.ai_vocabulary.wordtest_component;

import android.app.Activity;
import android.content.Context;

import org.andoidtown.ai_vocabulary.Manager.DateProcessManager;
import org.andoidtown.ai_vocabulary.WordDAO;
import org.andoidtown.ai_vocabulary.WordParceble;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WordTestRepository
{
    private int nowWordIndex;
    private int nowGroupIndex;
    private int testCorrectWordNum;
    private int testIncorrectWordNum;
    private ArrayList<ArrayList<WordParceble>> groupList;
    private ArrayList<WordParceble> incorrectWordList;
    private DateProcessManager dateProcessManager;
    private WordDAO wordDAO;
    private Context context;
    public WordTestRepository(Context contextUsingDB)
    {
        this.context = contextUsingDB;
        wordDAO = WordDAO.getWordDAO(context);
        incorrectWordList = new ArrayList<>();
        dateProcessManager = new DateProcessManager();
    }
    public int getNowWordIndex() {
        return nowWordIndex;
    }

    public void setNowWordIndex(int nowWordIndex) {
        this.nowWordIndex = nowWordIndex;
    }

    public int getNowGroupIndex() {
        return nowGroupIndex;
    }

    public void setNowGroupIndex(int nowGroupIndex) {
        this.nowGroupIndex = nowGroupIndex;
    }
    public int getNowGroupSize()
    {
        return this.groupList.get(nowGroupIndex).size();
    }
    public int getGroupListSize()
    {
        return this.groupList.size();
    }
    public void loadTodaysWords()
    {
        groupList = wordDAO.getTodaysWordTest();
        nowWordIndex = 0;
        nowGroupIndex = 0;
    }
    public boolean isNowLastWord()
    {
        return nowGroupIndex == groupList.size() -1 &&
                nowWordIndex == groupList.get(nowGroupIndex).size()-1;
    }
    //0 : normal state
    //1 : group test exit
    //2 : all test exit
    public int moveToNextWord()
    {
        if(isNowLastWord())
        {
            return 2;
        }
        else if(nowWordIndex == groupList.get(nowGroupIndex).size()-1)
        {
            nowWordIndex = 0;
            nowGroupIndex++;
            return 1;
        }
        else
        {
            nowWordIndex++;
            return 0;
        }
    }
    public void increaseNowWordsCorrectNumber()
    { 
        testCorrectWordNum++;
        wordDAO.increseCorrectNum(getNowWord());
    }
    public void increaseNowWordsIncorrectNumber()
    { 
        testIncorrectWordNum++;
        wordDAO.increseIncorrectNum(getNowWord());
        addIncorrectWord(getNowWord());
    }
    public void increaseNextTestDate(int day,String groupName)
    {
        wordDAO.increaseNextTestDate(day,groupName);
    }
    public WordParceble getNowWord()
    {
        return groupList.get(nowGroupIndex).get(nowWordIndex);
    }
    public String getNowGroupName()
    {
        return groupList.get(nowGroupIndex).get(0).getGroup_name();
    }
    public int getGroupsTestNumber(String groupName)
    {
        String values[] = {groupName};
        return wordDAO.getTestNumber(values);
    }

    public void addIncorrectWord(WordParceble incorrectWord)
    {
        incorrectWordList.add(incorrectWord);
    }
    public ArrayList<WordParceble> getIncorrectWordList()
    {
        return incorrectWordList;
    }
    public void insertNewWordTestData(String testTime)
    {
        Date date = Calendar.getInstance().getTime();
        String today = dateProcessManager.getFormattedDate(date);
        String values[] = {today, Integer.toString(testCorrectWordNum),
                Integer.toString(testIncorrectWordNum),testTime};
        wordDAO.insertWordTest(values);
    }

}
