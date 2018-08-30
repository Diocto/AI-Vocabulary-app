package org.andoidtown.ai_vocabulary.wordlistviewcomponent;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.andoidtown.ai_vocabulary.R;

public class WordListActivity extends AppCompatActivity {
    ListView listView;
    TextView groupNameTextView;
    WordListViewAdapter adapter;
    String groupName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        adapter = new WordListViewAdapter();

        listView = findViewById(R.id.wordListInWG);
        listView.setAdapter(adapter);

        Intent parentIntent = getIntent();
        groupName = parentIntent.getStringExtra("groupName");
        groupNameTextView = findViewById(R.id.groupNameTextView);
        groupNameTextView.setText(groupName);
        SQLiteDatabase db = openOrCreateDatabase("vocabularyDataBase",MODE_PRIVATE,null);
        String values[] = {groupName};
        Cursor cursor = db.rawQuery("select * from word where group_name = ?",values);
        Log.d("cursorNum",Integer.toString(cursor.getCount()));
        for (int i = 0; i < cursor.getCount(); i++)
        {
            Log.d("cursorNum",Integer.toString(cursor.getCount()));
            cursor.moveToNext();
            adapter.addItem(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
        }
        adapter.notifyDataSetChanged();
    }
}
