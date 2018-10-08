package org.andoidtown.ai_vocabulary.wordtest_component;

import org.andoidtown.ai_vocabulary.BasePresenter;
import org.andoidtown.ai_vocabulary.BaseView;
import org.andoidtown.ai_vocabulary.WordParceble;

import java.util.ArrayList;
import java.util.Calendar;

public interface WordTestContract {
    interface Presenter extends BasePresenter
    {
        void loadWordList(Calendar date);
        boolean moveNextWord();
        void setNowWord();
        void processExitGroupTest();
        void processExitAllTest(String testTime);
        void onClickYes();
        void onClickNo();
        ArrayList<WordParceble> getIncorrectWordList();
    }
    interface View extends BaseView<Presenter>
    {
        void setTestWord(WordParceble word);
        void makeTestExitAlert();
        void coverBlind();
        void setTestProgress(String wordProgress, String groupProgress);
        String getTimerText();
    }
}
