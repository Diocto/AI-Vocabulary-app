package org.andoidtown.ai_vocabulary.wordtestcomponent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.andoidtown.ai_vocabulary.R;
import org.andoidtown.ai_vocabulary.WordParceble;

import java.util.ArrayList;

public class IncorrectWordListActivity extends AppCompatActivity {
    private ArrayList<WordParceble> incorrectWordList;
    private ListView incorrectListView;
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
    }
}
