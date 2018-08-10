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
    ArrayList<String> wordList;
    ArrayList<String> meaningList;
    ArrayList<Integer> wordNums;
    ImageView blind;
    int correctNum = 0;
    int inCorrectNum = 0;
    int groupNum = 0;
    int nowWordIndex = 0;
    int nowGroupIndex = 0;
    int nowTotalWordIndex = 0;
    int totalWordNum = 0;
    AlphaAnimation alphaAnimation;
    SQLiteDatabase database;
    Animation slide_in_left, slide_out_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test);
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
        wordList = new ArrayList<>();
        meaningList = new ArrayList<>();
        wordNums = new ArrayList<>();
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(currentTime);
        String[] values = {today};

        wordSwitcher.setInAnimation(slide_in_left);
        wordSwitcher.setOutAnimation(slide_out_right);

        database = openOrCreateDatabase("vocabularyDataBase",MODE_PRIVATE,null);
        Cursor groupCursor = database.rawQuery("select * from word_group where next_test_date = ?", values);
        groupNum = groupCursor.getCount();
        for (int i = 0 ; i < groupNum; i++)
        {
            groupCursor.moveToNext();
            String[] group_name = {groupCursor.getString(i)};
            Cursor wordCursor = database.rawQuery("select * from word where group_name = ?", group_name);
            wordNums.add(wordCursor.getCount());
            totalWordNum += wordNums.get(i);
            for (int j = 0; j < wordNums.get(i); j++)
            {
                wordCursor.moveToNext();
                wordList.add(wordCursor.getString(0));
                meaningList.add(wordCursor.getString(1));
            }
        }
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
    public void showNextWord(){
        behindBlind();
        nowWordIndex++;
        nowTotalWordIndex++;
        if(wordNums.get(nowGroupIndex) <= nowWordIndex)
        {
            nowGroupIndex++;
            nowWordIndex = 0;
        }
        wordSwitcher.setText(wordList.get(nowTotalWordIndex));
        meaningTextView.setText(meaningList.get(nowTotalWordIndex));

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
            processExitTest();
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
            processExitTest();
        }
        else
        {
            showNextWord();
        }
    }
    public void processExitTest() {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
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
        alert.show();

    }

    public boolean isFinalWord()
    {
        Toast.makeText(this,"inc, cor, total : " + inCorrectNum + ", " + correctNum + ", " + totalWordNum
                ,Toast.LENGTH_LONG).show();
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
    public void behindBlind(){
        blind.setVisibility(View.VISIBLE);
    }
}
