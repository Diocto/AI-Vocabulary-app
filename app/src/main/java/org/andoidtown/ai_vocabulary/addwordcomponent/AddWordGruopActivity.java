package org.andoidtown.ai_vocabulary.addwordcomponent;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.andoidtown.ai_vocabulary.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddWordGruopActivity extends AppCompatActivity {
    Button cancelButton;
    Button addWGButton;
    AddWGListViewAdpater adapter;
    public static int addNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word_gruop);

        ListView listview = findViewById(R.id.addWordListView);
        adapter = new AddWGListViewAdpater();
        listview.setAdapter(adapter);

        cancelButton = findViewById(R.id.cancelAddButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addWGButton = findViewById(R.id.addWGToDBButton);

        for(int i = 0; i < 10 ;i ++)
        {
            adapter.addEditText();
        }
    }
    public void onclickedOnce(View view){
        Log.i("addLod","once Clicked!!!!!!");
        adapter.addBottomEditText(view);
    }

    public void onClickAddWGButton(View view)
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String today = dateFormat.format(date);
        SQLiteDatabase db;
        try{
            db = openOrCreateDatabase("vocabularyDataBase",MODE_PRIVATE,null);
        } catch(Exception ex)
        {
            ex.printStackTrace();
            return;
        }
        String subSQL = "insert into word(value, meaning, correct_answer_num, incorrect_answer_num, group_name)";
        for (int i = 0; i < adapter.getCount(); i++)
        {
                try{
                    db.execSQL(subSQL + "values ('" +adapter.getItem(i).getWord() +"', '" + adapter.getItem(i).getWordsMeaning() +"', 0, 0, " + today +")");
                } catch(Exception ex)
                {
                    ex.printStackTrace();
                    return;
                }
        }
        String values[] = {Integer.toString(addNum++), today, "0"};
        try{
            db.execSQL("insert into word_group(group_name, registered_data, num_of_test) values(?, ?, ?)",values);
        }catch (Exception ex)
        {
            Log.d("sqlerror",ex.toString());
        }

        Toast.makeText(this,"단어 추가가 완료되었습니다", Toast.LENGTH_LONG).show();
        finish();
        return;
    }
}
