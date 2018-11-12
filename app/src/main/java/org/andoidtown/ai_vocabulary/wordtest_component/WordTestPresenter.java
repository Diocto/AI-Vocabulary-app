package org.andoidtown.ai_vocabulary.wordtest_component;

import android.content.Context;
import android.util.Log;

import org.andoidtown.ai_vocabulary.Manager.DateProcessManager;
import org.andoidtown.ai_vocabulary.WordParceble;

import java.util.ArrayList;
import java.util.Calendar;

public class WordTestPresenter implements WordTestContract.Presenter
{
    private WordTestContract.View wordTestView;
    private WordTestRepository wordTestRepository;
    private Calendar lastTestTimeCalender;
    private DateProcessManager timeProcessManager;
    public WordTestPresenter(WordTestContract.View wordTestView, Context contextUsingDB)
    {
        this.wordTestView = wordTestView;
        this.wordTestRepository = new WordTestRepository(contextUsingDB);
        lastTestTimeCalender = Calendar.getInstance();
        lastTestTimeCalender.set(Calendar.HOUR,0);
        lastTestTimeCalender.set(Calendar.MINUTE,0);
        lastTestTimeCalender.set(Calendar.SECOND,0);
        lastTestTimeCalender.set(Calendar.MILLISECOND,0);
        timeProcessManager = new DateProcessManager();
        timeProcessManager.setDateFormat("hh:mm:ss");
    }

    @Override
    public void loadWordList(Calendar date) {
        wordTestRepository.setTestWordList(date);
    }
    @Override
    public boolean moveNextWord() {
        if(wordTestRepository.isNowLastWord())
        {
            processExitGroupTest();
            processExitAllTest();
            return false;
        }
        else if(wordTestRepository.isNowEndOfGroup())
        {
            processExitGroupTest();
            wordTestRepository.moveToNextWord();
        }
        else
        {
            wordTestRepository.moveToNextWord();
        }
        return true;
    }
    @Override
    public void setNowWord()
    {
        WordParceble word = wordTestRepository.getNowWord();
        wordTestView.setTestWord(word);
        String wordProgress = "단어" + wordTestRepository.getNowWordIndex() + " / " + wordTestRepository.getNowGroupSize();
        String groupProgress = "단어뭉치" + wordTestRepository.getNowGroupIndex() + " / " + wordTestRepository.getGroupListSize();
        wordTestView.setTestProgress(wordProgress, groupProgress);
    }
    @Override
    public void processExitGroupTest() {
        Log.d("그룹테스트종료",wordTestRepository.getNowGroupName());
        insertNowTestData();
        increaseNextTestDate();
    }

    private void increaseNextTestDate() {
        int groupTestNumber = wordTestRepository.getGroupsTestNumber(wordTestRepository.getNowGroupName());
        int increaseNumber = getNextTestDay(groupTestNumber);
        wordTestRepository.increaseNextTestDate(increaseNumber, wordTestRepository.getNowGroupName());
    }

    private void insertNowTestData() {
        long testTimeMillis = timeProcessManager.stringToDate(wordTestView.getTimerText()).getTime();
        long lastTestTimeMillis = lastTestTimeCalender.getTime().getTime();
        testTimeMillis = testTimeMillis - lastTestTimeMillis;
        String testTime = timeProcessManager.millisToString(testTimeMillis);
        Log.d("testTime",testTime + "\n" + Long.toString(testTimeMillis));
        wordTestRepository.insertNewWordTestData(testTime);
    }

    @Override
    public void processExitAllTest() {
        wordTestView.makeTestExitAlert();
    }
    @Override
    public ArrayList<WordParceble> getIncorrectWordList()
    {
        return wordTestRepository.getIncorrectWordList();
    }

    @Override
    public void onClickYes() {
        wordTestRepository.increaseNowWordsCorrectNumber();
        if(moveNextWord()) {
            wordTestView.coverBlind();
            setNowWord();
        }
    }

    @Override
    public void onClickNo() {
        wordTestRepository.increaseNowWordsIncorrectNumber();
        if(moveNextWord()) {
            wordTestView.coverBlind();
            setNowWord();
        }
    }

    @Override
    public void start() {

    }
    public int getNextTestDay(int testNumber)
    {
        switch (testNumber)
        {
            case 0:
                return 1;
            case 1:
                return 1;
            case 2:
                return 1;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 8;
            case 6:
                return 15;
            default:
                return 0;
        }
    }
}
