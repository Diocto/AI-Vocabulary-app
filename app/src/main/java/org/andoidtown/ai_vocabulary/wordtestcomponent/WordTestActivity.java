package org.andoidtown.ai_vocabulary.wordtestcomponent;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WordTestActivity extends AppCompatActivity {
    TextSwitcher wordSwitcher;
    TextView meaningTextView;
    ImageView blind;
    ArrayList<String> wordList;
    ArrayList<String> meaningList;
    AlphaAnimation alphaAnimation;
    SQLiteDatabase database;
    SimpleDateFormat dateFormat;
    Animation slide_in_left, slide_out_right;
    int nowWordIndex = 0;
    ArrayList<Integer> groupWordNums;
    ArrayList<String> groupNames;
    int nowGroupIndex = 0;
    int correctNum = 0;
    int inCorrectNum = 0;
    int groupNum = 0;
    int nowTotalWordIndex = 0;
    int totalWordNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test);
        initViews();
        loadDBtoLocal();
        if(groupNum != 0)
        {
            wordSwitcher.setText(new StringBuffer(wordList.get(nowTotalWordIndex)));
            meaningTextView.setText(new StringBuffer(meaningList.get(nowTotalWordIndex)));
        }
        else
        {
            Toast.makeText(this,"ERROR:불러올 단어가 없습니다",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initViews() {
        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(100);
        meaningTextView = findViewById(R.id.text_wordtest_meaning);
        wordSwitcher = (TextSwitcher) findViewById(R.id.switcher_wordtest_word);
        wordSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
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
    }

    private void loadDBtoLocal() {
        wordList = new ArrayList<>();
        meaningList = new ArrayList<>();
        groupWordNums = new ArrayList<>();
        groupNames = new ArrayList<>();
        Date currentTime = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(currentTime);
        String[] values = {today};
        database = openOrCreateDatabase("vocabularyDataBase",MODE_PRIVATE,null);
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
                    wordList.add(wordCursor.getString(0));
                    meaningList.add(wordCursor.getString(1));
                }
            }
        } catch (Exception ex)
        {
            Log.d("sql error", ex.toString());
        }
    }

    public void showNextWord(){
        behindBlind();
        nowWordIndex++;
        nowTotalWordIndex++;
        if(groupWordNums.get(nowGroupIndex) <= nowWordIndex)
        {
            processExitGroupTest();
        }
        wordSwitcher.setText(wordList.get(nowTotalWordIndex));
        meaningTextView.setText(meaningList.get(nowTotalWordIndex));
    }

    public void processExitGroupTest() {
        String[] values = {groupNames.get(nowGroupIndex)};
        Cursor groupCursor = database.rawQuery("select * from word_group where group_name = ?",values);
        Cursor testCursor = database.rawQuery("select * from word_test where group_name = ?",values);
        Integer testNumber = testCursor.getCount();
        Integer addNumber = getNextTestDay(testNumber);
        groupCursor.moveToNext();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        String nextTestDate = dateFormat.format(cal.getTime());
        database.execSQL("update word_group set next_test_date = '" + nextTestDate + "' where group_name = ?", values);
        nowGroupIndex++;
        nowWordIndex = 0;
    }

    private void setButtonDelay(final View view, int milli) {
        view.setEnabled(false); // 클릭 무효화
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, milli);
    }
    public void onClickYes(final View view){
        setButtonDelay(view, 100);
        correctNum++;
        String nowWord = wordList.get(nowTotalWordIndex);
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
        String nowWord = wordList.get(nowTotalWordIndex);
        String[] values = {nowWord};
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
    public void processExitAllTest() {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
        alert_confirm.setCancelable(false);
        alert_confirm.setMessage("시험이 종료되었습니다! \n 확인 버튼을 누르면 결과로 넘어갑니다.");
        alert_confirm.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(WordTestActivity.this, IncorrectWordListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = alert_confirm.create();
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(false);
        alert.show();

    }

    public boolean isFinalWord()
    {
  //      Toast.makeText(this,"inc, cor, total : " + inCorrectNum + ", " + correctNum + ", " + totalWordNum
  //              ,Toast.LENGTH_LONG).show();
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
        blind.startAnimation(alphaAnimation);
        blind.setVisibility(View.INVISIBLE);
    }
    public int getNextTestDay(int testNumber) {
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
    public void behindBlind(){
        blind.setVisibility(View.VISIBLE);
    }
}
