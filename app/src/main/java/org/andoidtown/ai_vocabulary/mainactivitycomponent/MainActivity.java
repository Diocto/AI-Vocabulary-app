package org.andoidtown.ai_vocabulary.mainactivitycomponent;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.andoidtown.ai_vocabulary.R;

public class MainActivity extends AppCompatActivity {
    ViewPager mainVP;
    Button graphTapButton;
    Button testTapButton;
    Button managementTapButton;
    SQLiteDatabase db;
    String databaseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainVP = findViewById(R.id.mainViewPager);
        graphTapButton = findViewById(R.id.growthGraphTapButton);
        testTapButton = findViewById(R.id.wordTestTapButton);
        managementTapButton = findViewById(R.id.managementWordsTapButton);

        mainVP.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        mainVP.setCurrentItem(1);

        graphTapButton.setOnClickListener(movePageListener);
        graphTapButton.setTag(0);
        testTapButton.setOnClickListener(movePageListener);
        testTapButton.setTag(1);
        managementTapButton.setOnClickListener(movePageListener);
        managementTapButton.setTag(2);

        databaseName = "vocabularyDataBase";
        if ( createDatabase(databaseName) ) {
            createWordTable();
            createWordGroupTable();
            createWordTestTable();
        }
    }
    private boolean createDatabase(String databaseName) {
        try{

            db = openOrCreateDatabase(databaseName,MODE_PRIVATE,null);
        } catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private void createWordTable() {
        try{
            db.execSQL("create table word(" +
                    "value text," +
                    "meaning text," +
                    "correct_answer_num integer," +
                    "incorrect_answer_num integer," +
                    "group_name text" +
                    "" +
                    ");");
        }
        catch(Exception ex)
        {
            Log.d("exception",ex.toString());
        }
    }
    private void createWordGroupTable()
    {
        try{
            db.execSQL("create table word_group (" +
                    "group_name text PRIMARY KEY," +
                    "registered_date datetime," +
                    "num_of_test integer," +
                    "next_test_date datetime)");
        }
        catch(Exception ex)
        {
            Log.d("exception",ex.toString());
        }
    }

    private void createWordTestTable()
    {
        try{
            db.execSQL("create table word_test (" +
                    "test_date date," +
                    "correct_answer_num integer," +
                    "incorrect_answer_num integer," +
                    "test_time datetime," +
                    "group_name text" +
                    ")");
        }
        catch (Exception ex)
        {
            Log.d("exception", ex.toString());
        }
    }

;
    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            mainVP.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(android.support.v4.app.FragmentManager  fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch (position) {
                case 0:
                    return new AchievementGraphFragment();
                case 1:
                    return new WordTestFragment();
                case 2:
                    return new ManagementWordsFragment();
                default:
                    return new WordTestFragment();
            }
        }
        @Override
        public int getCount()
        {
            return 3;
        }

    }
}
