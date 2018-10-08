package org.andoidtown.ai_vocabulary.wordtest_component;

import android.content.Context;
import android.util.Log;

import org.andoidtown.ai_vocabulary.WordParceble;

import java.util.ArrayList;
import java.util.Calendar;

public class WordTestPresenter implements WordTestContract.Presenter
{
    private WordTestContract.View wordTestView;
    private WordTestRepository wordTestRepository;
    public WordTestPresenter(WordTestContract.View wordTestView, Context contextUsingDB)
    {
        this.wordTestView = wordTestView;
        this.wordTestRepository = new WordTestRepository(contextUsingDB);
    }

    @Override
    public void loadWordList(Calendar date) {
        wordTestRepository.loadTodaysWords();
    }
    @Override
    public boolean moveNextWord() {
        int state = wordTestRepository.moveToNextWord();
        if(state == 0)
        {
            return true;
        }
        else if(state == 1)
        {
            processExitGroupTest();
            return true;
        }
        else
        {
            processExitGroupTest();
            processExitAllTest(wordTestView.getTimerText());
            return false;
        }
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
        Log.d("테스트1",wordTestRepository.getNowGroupName());
        int nowGroupTestNumber = wordTestRepository.getGroupsTestNumber(wordTestRepository.getNowGroupName());
        int addNextTestNumber = getNextTestDay(nowGroupTestNumber);
        wordTestRepository.increaseNextTestDate(addNextTestNumber, wordTestRepository.getNowGroupName());
    }
    @Override
    public void processExitAllTest(String testTime) {
        wordTestRepository.insertNewWordTestData(testTime);
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
        if(moveNextWord() == true) {
            wordTestView.coverBlind();
            setNowWord();
        }
    }

    @Override
    public void onClickNo() {
        wordTestRepository.increaseNowWordsIncorrectNumber();
        if(moveNextWord() == true) {
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
