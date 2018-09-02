package org.andoidtown.ai_vocabulary.wordtest_component;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.andoidtown.ai_vocabulary.R;
import org.andoidtown.ai_vocabulary.WordParceble;
import org.andoidtown.ai_vocabulary.mainactivity_component.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InstantIncorrectWordListActivity extends AppCompatActivity {
    private ArrayList<WordParceble> incorrectWordList;
    private ListView incorrectListView;
    private Button okButton;
    private FloatingActionButton mainFABButton;
    private FloatingActionButton saveFABButton;
    private FloatingActionButton printFABButton;
    private TextView saveFABText;
    private TextView printFABText;
    private boolean isFABOpen = false;
    private Animation fabOpen, fabClose;
    InstantIncorrectWordListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_incorrect_word_list);

        incorrectWordList = new ArrayList<>();
        incorrectWordList = getIntent().getParcelableArrayListExtra("incorrectList");

        incorrectListView = findViewById(R.id.listview_inwordlist_inwordlist);
        adapter = new InstantIncorrectWordListViewAdapter();
        incorrectListView.setAdapter(adapter);
        incorrectWordList = getIntent().getExtras().getParcelableArrayList("incorrectList");

        for (int i = 0; i < incorrectWordList.size(); i++)
        {
            adapter.addItem(incorrectWordList.get(i).getWord(),incorrectWordList.get(i).getMeaning());
        }
        adapter.notifyDataSetChanged();

        okButton = findViewById(R.id.button_inwordlist_ok);
        saveFABButton = findViewById(R.id.fab_inwordlist_save);
        mainFABButton = findViewById(R.id.fab_inwordlist_main);
        printFABButton = findViewById(R.id.fab_inwordlist_print);
        saveFABText = findViewById(R.id.text_inwordlist_savefab);
        printFABText = findViewById(R.id.text_inwordlist_printfab);


        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        mainFABButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.d("clicked","클릭됨");
                changeFABState();
            }
        });
        saveFABButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                saveInwordListOnDB();
            }
        });
    }
    public void saveInwordListOnDB()
    {
        SQLiteDatabase db = openOrCreateDatabase(MainActivity.databaseName,MODE_PRIVATE,null);
        String values[] = {"","",""};
        String groupName = "";
        Date nowDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        groupName = dateFormat.format(nowDate);
        groupName = "틀린단어 (" + groupName + ")";
        for (WordParceble word : incorrectWordList)
        {
            values[0] = word.getWord(); values[1] = word.getMeaning(); values[2] = groupName;
            db.execSQL("insert into incorrect_word(incorrect_word, incorrect_word_meaning, incorrect_word_group_name) values(?,?,?)",values);
        }
        Toast.makeText(this,"틀린단어 목록에 저장되었습니다",Toast.LENGTH_LONG).show();
        closeFABs();
    }
    public void changeFABState()
    {
        if (isFABOpen)
        {
            closeFABs();
            isFABOpen = false;
        }
        else
        {
            openFABs();
            isFABOpen = true;
        }
    }
    private void openFABs()
    {
        saveFABButton.setVisibility(View.VISIBLE);
        saveFABText.setVisibility(View.VISIBLE);
        saveFABButton.startAnimation(fabOpen);
        saveFABText.startAnimation(fabOpen);
        saveFABButton.setClickable(true);

        printFABButton.setVisibility(View.VISIBLE);
        printFABText.setVisibility(View.VISIBLE);
        printFABButton.startAnimation(fabOpen);
        printFABText.startAnimation(fabOpen);
        printFABButton.setClickable(true);
    }
    private void closeFABs()
    {
        saveFABButton.startAnimation(fabClose);
        saveFABText.startAnimation(fabClose);
        saveFABButton.setVisibility(View.INVISIBLE);
        saveFABText.setVisibility(View.INVISIBLE);
        saveFABButton.setClickable(false);

        printFABButton.startAnimation(fabClose);
        printFABText.startAnimation(fabClose);
        printFABButton.setVisibility(View.INVISIBLE);
        printFABText.setVisibility(View.INVISIBLE);
        printFABButton.setClickable(false);
    }
}
