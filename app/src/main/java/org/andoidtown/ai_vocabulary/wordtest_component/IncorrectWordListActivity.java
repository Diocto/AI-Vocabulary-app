package org.andoidtown.ai_vocabulary.wordtest_component;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import org.andoidtown.ai_vocabulary.R;
import org.andoidtown.ai_vocabulary.WordParceble;

import java.util.ArrayList;

public class IncorrectWordListActivity extends AppCompatActivity {
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
    IncorrectWordListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrect_word_list);

        incorrectWordList = new ArrayList<>();
        incorrectWordList = getIntent().getParcelableArrayListExtra("incorrectList");

        incorrectListView = findViewById(R.id.listview_inwordlist_inwordlist);
        adapter = new IncorrectWordListViewAdapter();
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
    }
    public void saveInwordListOnDB()
    {

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
