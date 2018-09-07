package org.andoidtown.ai_vocabulary.wordtest_component;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.andoidtown.ai_vocabulary.R;
import org.andoidtown.ai_vocabulary.WordParceble;
import org.andoidtown.ai_vocabulary.mainactivity_component.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WordTestActivity extends AppCompatActivity
{
    TextSwitcher wordSwitcher;
    TextView meaningTextView;
    TextView remainWordTextView;
    TextView remainGroupTextView;
    TextView timerTextView;
    ImageView blind;
    ArrayList<WordParceble> wordList;;
    int nowWordIndex = 0;
    int nowTotalWordIndex = 0;
    int totalWordNum = 0;
    ArrayList<WordParceble> incorrectWordList;
    int correctNum = 0;
    int inCorrectNum = 0;
    ArrayList<Integer> groupWordNums;
    ArrayList<String> groupNames;
    int nowGroupIndex = 0;
    int groupNum = 0;
    AlphaAnimation alphaAnimation;
    SQLiteDatabase database;
    SimpleDateFormat dateFormat;
    Animation slide_in_left, slide_out_right;
    Timer testTimer;
    TimerTask testTimerTask;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test);
        initViews();
        loadDBtoLocal();
        if(groupNum != 0)
        {
            wordSwitcher.setText(new StringBuffer(wordList.get(nowTotalWordIndex).getWord()));
            meaningTextView.setText(new StringBuffer(wordList.get(nowTotalWordIndex).getMeaning()));
        }
        else
        {
            Toast.makeText(this,"ERROR:불러올 단어가 없습니다",Toast.LENGTH_LONG).show();
            finish();
        }
        incorrectWordList = new ArrayList<>();
        setTextState();
        testTimer = new Timer();
        testTimerTask = new TestTimer();
        testTimer.schedule(testTimerTask,0,1000);
    }

    private void initViews()
    {
        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(200);
        meaningTextView = findViewById(R.id.text_wordtest_meaning);
        remainGroupTextView = findViewById(R.id.text_wordtest_remainWGnum);
        remainWordTextView = findViewById(R.id.text_wordtest_remainword);
        wordSwitcher = (TextSwitcher) findViewById(R.id.switcher_wordtest_word);
        wordSwitcher.setFactory(new ViewSwitcher.ViewFactory()
        {
            @Override
            public View makeView()
            {
                TextView t = new TextView(WordTestActivity.this);
                t.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
                t.setTextSize(36);
                t.setTextColor(Color.WHITE);
                return t;
            }
        });
        blind = findViewById(R.id.image_wordtest_blind);
        slide_in_left = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);
        wordSwitcher.setInAnimation(slide_in_left);
        wordSwitcher.setOutAnimation(slide_out_right);
        timerTextView = findViewById(R.id.text_wordtest_timer);
    }

    private void loadDBtoLocal()
    {
        wordList = new ArrayList<>();
        groupWordNums = new ArrayList<>();
        groupNames = new ArrayList<>();
        Date currentTime = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(currentTime);
        String[] values = {today};
        database = openOrCreateDatabase(MainActivity.databaseName,MODE_PRIVATE,null);
        Cursor groupCursor;
        try
        {
            groupCursor=database.rawQuery("select * from word_group where next_test_date = ?", values);
            groupNum = groupCursor.getCount();
            for (int i = 0 ; i < groupNum; i++)
            {
                groupCursor.moveToNext();
                String[] group_name = {groupCursor.getString(0)};
                Cursor wordCursor = database.rawQuery("select * from word where group_name = ?", group_name);
                groupWordNums.add(wordCursor.getCount());
                groupNames.add(groupCursor.getString(0));
                totalWordNum += groupWordNums.get(i);
                for (int j = 0; j < groupWordNums.get(i); j++)
                {
                    wordCursor.moveToNext();
                    wordList.add(new WordParceble(wordCursor.getString(0), wordCursor.getString(1)));
                }
            }
        } catch (Exception ex)
        {
            Log.d("sql error", ex.toString());
        }
    }

    public void showNextWord()
    {
        behindBlind();
        nowWordIndex++;
        nowTotalWordIndex++;
        if(groupWordNums.get(nowGroupIndex) <= nowWordIndex)
        {
            processExitGroupTest();
        }
        wordSwitcher.setText(wordList.get(nowTotalWordIndex).getWord());
        meaningTextView.setText(wordList.get(nowTotalWordIndex).getMeaning());
        setTextState();
    }

    public void processExitGroupTest()
    {
        String[] values = {groupNames.get(nowGroupIndex)};
        Cursor groupCursor = database.rawQuery("select * from word_group where group_name = ?",values);
        Cursor testCursor = database.rawQuery("select * from word_test where group_name = ?",values);
        Integer testNumber = testCursor.getCount();
        Integer addNumber = getNextTestDay(testNumber);
        groupCursor.moveToNext();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, addNumber);
        String nextTestDate = dateFormat.format(cal.getTime());
        database.execSQL("update word_group set next_test_date = '" + nextTestDate + "' where group_name = ?", values);
        nowGroupIndex++;
        nowWordIndex = 0;
    }

    private void setButtonDelay(final View view, int milli)
    {
        view.setEnabled(false); // 클릭 무효화
        Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                view.setEnabled(true);
            }
        }, milli);
    }
    public void onClickYes(final View view){
        setButtonDelay(view, 100);
        correctNum++;
        String nowWord = wordList.get(nowTotalWordIndex).getWord();
        String[] values = {nowWord};
        try
        {
            database.execSQL("update word set correct_answer_num = correct_answer_num + 1 where value = ?",values);
        } catch (Exception ex)
        {
            Log.d("SQLerror",ex.toString());
        }
        if(isFinalWord())
        {
            processExitGroupTest();
            processExitAllTest();
        }
        else
        {
            showNextWord();
        }
    }
    public void onClickNo(final View view){
        setButtonDelay(view, 100);
        inCorrectNum++;
        String nowWord = wordList.get(nowTotalWordIndex).getWord();
        String nowMeaning = wordList.get(nowTotalWordIndex).getMeaning();
        String[] values = {nowWord};
        incorrectWordList.add(new WordParceble(nowWord, nowMeaning));
        try
        {
            database.execSQL("update word set incorrect_answer_num = incorrect_answer_num + 1 where value = ?",values);
        } catch (Exception ex)
        {
            Log.d("SQLerror",ex.toString());
        }
        if(isFinalWord())
        {
            processExitGroupTest();
            processExitAllTest();
        }
        else
        {
            showNextWord();
        }
    }
    public void processExitAllTest()
    {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(date);
        String values[] = {today, Integer.toString(correctNum),Integer.toString(inCorrectNum),timerTextView.getText().toString()};
        database.execSQL("insert into word_test(test_date, correct_answer_num, incorrect_answer_num, test_time)" +
                " values(?,?,?,?)",values);
        testTimer.cancel();
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
        alert_confirm.setCancelable(false);
        alert_confirm.setMessage("시험이 종료되었습니다! \n 확인 버튼을 누르면 결과로 넘어갑니다.");
        alert_confirm.setPositiveButton("확인", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("incorrectList",incorrectWordList);
                Intent intent = new Intent(WordTestActivity.this, InstantIncorrectWordListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = alert_confirm.create();
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }
    public void setTextState()
    {
        String remainGruop = "단어뭉치 " + (nowGroupIndex + 1) + "/" + groupWordNums.size();
        String remainWord = "단어" + (nowWordIndex + 1) + "/" + groupWordNums.get(nowGroupIndex);

        remainGroupTextView.setText(remainGruop);
        remainWordTextView.setText(remainWord);
    }
    public boolean isFinalWord()
    {
        if (inCorrectNum + correctNum == totalWordNum)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void onClickBlind(View view){
        blind.setClickable(false);
        blind.startAnimation(alphaAnimation);
        blind.setVisibility(View.INVISIBLE);
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
    public void behindBlind()
    {
        blind.setClickable(true);
        blind.setVisibility(View.VISIBLE);
    }

    class TestTimer extends TimerTask
    {
        private long startTimeMillis;
        TestTimer()
        {
            startTimeMillis = System.currentTimeMillis();
        }
        @Override
        public void run()
        {
             long nowMillis = System.currentTimeMillis() - startTimeMillis;
             long seconds = nowMillis / 1000;
             long minutes = seconds / 60;
             long hour = minutes / 60;
             seconds = seconds % 60;
             String nowTime = String.format("%02d",hour) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
             timerTextView.setText(nowTime);
        }
    }
}
