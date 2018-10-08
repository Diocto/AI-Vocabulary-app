package org.andoidtown.ai_vocabulary.word_listview_component;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.andoidtown.ai_vocabulary.R;
import org.andoidtown.ai_vocabulary.mainactivity_component.MainActivity;

public class WordGroupListViewActivity extends AppCompatActivity {
    WordGroupListViewAdapter adapter;
    ListView wordlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_group_list);
        adapter = new WordGroupListViewAdapter();

        wordlistView = findViewById(R.id.wordGroupListView);

        wordlistView.setAdapter(adapter);
        wordlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String groupName = ((WordGroupListViewItem) adapterView.getAdapter().getItem(i)).getItemName();
                Intent intent = new Intent(getApplicationContext() ,WordListActivity.class);
                intent.putExtra("groupName",groupName);
                startActivity(intent);
            }
        });
        showWordList();
    }

    public void showWordList()
    {
        SQLiteDatabase db = openOrCreateDatabase(MainActivity.databaseName,MODE_PRIVATE,null);
        String aSQL = "select * from word_group";
        try {
            Cursor cursor = db.rawQuery(aSQL, null);
            Log.d("커서 갯수",Integer.toString(cursor.getCount()));
            for(int i = 0; i < cursor.getCount(); i++)
            {
                cursor.moveToNext();
                Log.d("아이템",cursor.getString(0) + cursor.getString(3));
                adapter.addItem(cursor.getString(0),cursor.getString(3));
            }
        } catch (Exception ex)
        {
            Log.d("cursor",ex.toString());
        }
        adapter.notifyDataSetChanged();
    }

    public void onClickWordGroupView(View view)
    {
        TextView groupNameView = view.findViewById(R.id.groupNameTextView);
        String groupName = groupNameView.getText().toString();
        Log.d("groupName", groupName);
    }
}
