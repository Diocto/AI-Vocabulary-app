package org.andoidtown.ai_vocabulary.incorrect_word_list_component;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import org.andoidtown.ai_vocabulary.R;
import org.andoidtown.ai_vocabulary.mainactivity_component.MainActivity;

import java.util.ArrayList;

public class IncorrectWordGroupListActivity extends AppCompatActivity {
    IncorrectWordGroupListAdapter adapter;
    ListView inwordGroupList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrect_word_group_list);

        ArrayList<IncorrectWordGroupListItem> itemlist = new ArrayList<>();
        SQLiteDatabase db = openOrCreateDatabase(MainActivity.databaseName,MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("select incorrect_word_group_name, count(*) from incorrect_word group by incorrect_word_group_name",null);

        adapter = new IncorrectWordGroupListAdapter(itemlist,this);
        inwordGroupList = (ListView) findViewById(R.id.listview_inwordgroup_grouplist);
        inwordGroupList.setAdapter(adapter);
        inwordGroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String groupName = ((IncorrectWordGroupListItem) adapterView.getAdapter().getItem(i)).groupName;
                Intent intent = new Intent(getApplicationContext() ,IncorrectWordListActivity.class);
                intent.putExtra("groupName",groupName);
                startActivity(intent);
            }
        });
        for(int i = 0; i < cursor.getCount(); i++)
        {
            cursor.moveToNext();
            adapter.addItem(new IncorrectWordGroupListItem(cursor.getString(0),cursor.getString(1)));
        }
        adapter.notifyDataSetChanged();
    }
}
