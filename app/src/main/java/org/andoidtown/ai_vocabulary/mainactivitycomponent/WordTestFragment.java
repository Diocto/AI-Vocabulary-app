package org.andoidtown.ai_vocabulary.mainactivitycomponent;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.andoidtown.ai_vocabulary.R;
import org.andoidtown.ai_vocabulary.wordtestcomponent.WordTestActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class WordTestFragment extends Fragment {
    private  Button testWordButton;
    private TextView numOfRegisteredWord;
    private TextView numOfMemorizedWord;
    private TextView numOfNotMemorizedWord;
    private TextView numOfPerfectWord;
    private View rootView;
    public WordTestFragment() {
        // Required empty public constructor
    }

    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_word_test, container, false);
        testWordButton = rootView.findViewById(R.id.testWordButton);
        testWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WordTestActivity.class);
                startActivity(intent);
            }
        });

        numOfRegisteredWord = rootView.findViewById(R.id.text_wordtestf_wordnum);
        numOfMemorizedWord = rootView.findViewById(R.id.text_wordtestf_memwordnum);
        numOfNotMemorizedWord = rootView.findViewById(R.id.text_wordtestf_notmemwordnum);
        numOfPerfectWord = rootView.findViewById(R.id.text_wordtestf_perfectwordnum);

        db = ((MainActivity)getActivity()).getDB();

        setStatusMyProgress();
        setButtonText();
        return rootView;
    }
    private void setButtonText() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        String values[] = {dataFormat.format(calendar.getTime())};
        Cursor cursor = db.rawQuery("select * from word_group where next_test_date = ?",values);
        String textOfButton;
        if (cursor.getCount() != 0)
        {
            cursor.moveToNext();
            dataFormat = new SimpleDateFormat("yy년 MM월 dd일");
            String testDate = dataFormat.format(calendar.getTime());
            textOfButton = testDate + " 시험\n" + "클릭해서 시험 시작";
            testWordButton.setText(textOfButton);
        }
        else
        {
            textOfButton = "오늘 볼 시험이 없습니다";
            testWordButton.setText(textOfButton);
        }

    }
    private void setStatusMyProgress() {
        Cursor cursor = db.rawQuery("select count(*) from word",null);
        cursor.moveToNext();
        numOfRegisteredWord.setText(cursor.getString(0));
        cursor = db.rawQuery("select count(*) from word where correct_answer_num > incorrect_answer_num",null);
        cursor.moveToNext();
        numOfMemorizedWord.setText(cursor.getString(0));
        cursor = db.rawQuery("select count(*) from word where correct_answer_num < incorrect_answer_num",null);
        cursor.moveToNext();
        numOfNotMemorizedWord.setText(cursor.getString(0));
        cursor = db.rawQuery("select count(*) from word where correct_answer_num > 5", null);
        cursor.moveToNext();
        numOfPerfectWord.setText(cursor.getString(0));
    }
}
