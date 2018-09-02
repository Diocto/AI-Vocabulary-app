package org.andoidtown.ai_vocabulary.incorrect_word_list_component;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.andoidtown.ai_vocabulary.R;
import org.andoidtown.ai_vocabulary.mainactivity_component.MainActivity;

import java.util.ArrayList;

public class IncorrectWordListActivity extends AppCompatActivity {
    IncorrectWordListAdapter adapter;
    ListView inwordGroupList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrect_word_list);
        Intent intent = getIntent();
        String values[] = {intent.getStringExtra("groupName")};
        ArrayList<IncorrectWordListItem> itemlist = new ArrayList<>();

        adapter = new IncorrectWordListAdapter(itemlist);
        inwordGroupList = (ListView) findViewById(R.id.listview_inword_grouplist);
        inwordGroupList.setAdapter(adapter);

        SQLiteDatabase db = openOrCreateDatabase(MainActivity.databaseName,MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("select * from incorrect_word where incorrect_word_group_name = ?",values);

        for(int i = 0; i < cursor.getCount(); i++)
        {
            cursor.moveToNext();
            adapter.addItem(new IncorrectWordListItem(cursor.getString(0),cursor.getString(1)));
        }
        adapter.notifyDataSetChanged();
    }
}
