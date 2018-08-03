package org.andoidtown.ai_vocabulary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class WordListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_group_list);

        ListView listView;
        WordListViewAdapter adapter;
        adapter = new WordListViewAdapter();

        listView = findViewById(R.id.wordGroupListView);

        listView.setAdapter(adapter);
        adapter.addItem("testName","testNum","testNotNum");

    }
}
