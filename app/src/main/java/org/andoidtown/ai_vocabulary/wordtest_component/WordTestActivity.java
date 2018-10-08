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

import org.andoidtown.ai_vocabulary.Manager.DateProcessManager;
import org.andoidtown.ai_vocabulary.R;
import org.andoidtown.ai_vocabulary.WordParceble;
import org.andoidtown.ai_vocabulary.mainactivity_component.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WordTestActivity extends AppCompatActivity implements WordTestContract.View
{
    private TextSwitcher wordSwitcher;
    private TextView meaningTextView;
    private TextView remainWordTextView;
    private TextView remainGroupTextView;
    private TextView timerTextView;
    private ImageView blind;
    private WordTestContract.Presenter wordTestPresenter;
    private AlphaAnimation alphaAnimation;
    private SQLiteDatabase database;
    private Animation slide_in_left, slide_out_right;
    private Timer testTimer;
    private TimerTask testTimerTask;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test);
        initViews();
        Calendar calendar = Calendar.getInstance();
        wordTestPresenter = new WordTestPresenter(this,this);
        wordTestPresenter.loadWordList(calendar);
        wordTestPresenter.setNowWord();
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
    @Override
    public void setTestWord(WordParceble word)
    {
        wordSwitcher.setText(word.getWord());
        meaningTextView.setText(word.getMeaning());
    }
    @Override
    public void setTestProgress(String wordProgress, String groupProgress)
    {
        remainGroupTextView.setText(wordProgress);
        remainWordTextView.setText(groupProgress);
    }
    public void onClickYes(final View view){
        setButtonDelay(view,100);
        wordTestPresenter.onClickYes();
    }
    public void onClickNo(final View view){
        setButtonDelay(view,100);
        wordTestPresenter.onClickNo();
    }
    public void onClickBlind(View view){
        blind.setClickable(false);
        blind.startAnimation(alphaAnimation);
        blind.setVisibility(View.INVISIBLE);
    }
    @Override
    public String getTimerText()
    {
        return timerTextView.getText().toString();
    }
    @Override
    public void makeTestExitAlert()
    {
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
                bundle.putParcelableArrayList("incorrectList",wordTestPresenter.getIncorrectWordList());
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
    private void setButtonDelay(final View view, final int milli)
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
    public void coverBlind()
    {
        blind.setClickable(true);
        blind.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(WordTestContract.Presenter presenter) {
        this.wordTestPresenter = presenter;
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
