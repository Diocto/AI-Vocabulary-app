package org.andoidtown.ai_vocabulary.addwordcomponent;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.andoidtown.ai_vocabulary.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddWordGruopActivity extends AppCompatActivity {
    Button cancelButton;
    Button addWGButton;
    AddWGListViewAdpater adapter;
    ListView listview;
    ArrayList<AddWGListViewItem> itemList = new ArrayList<>();

    public static int addNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word_gruop);

        listview = findViewById(R.id.addWordListView);
        adapter = new AddWGListViewAdpater(this,itemList);
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
        SQLiteDatabase db;
        try{
            db = openOrCreateDatabase("vocabularyDataBase",MODE_PRIVATE,null);
        } catch(Exception ex)
        {
            ex.printStackTrace();
            return;
        }

        for (int i = 0; i < adapter.getCount(); i++)
        {
            View editTextItem = adapter.getEditText(i);
            EditText wordEditText = editTextItem.findViewById(R.id.add_word_editText);
            EditText meaningEditText = editTextItem.findViewById(R.id.add_meaning_editText);
            String word = wordEditText.getText().toString();
            String meaning = meaningEditText.getText().toString();

            insertWordToSQL(db, word, meaning,Integer.toString(addNum));
        }
        insertWGToDB(Integer.toString(addNum), db);

        finish();
        return;
    }

    private void insertWGToDB(String groupName, SQLiteDatabase db) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String today = dateFormat.format(cal.getTime());
        String testDay = dateFormat.format(cal.getTime());
        String values[] = {Integer.toString(addNum++), today,testDay};
        try{
            db.execSQL("insert into word_group(group_name, registered_date, next_test_date)" +
                    "values(?, ?, ?)",values);
            Toast.makeText(this,"단어 추가가 완료되었습니다", Toast.LENGTH_LONG).show();
        }catch (Exception ex)
        {
            Log.d("sqlerror",ex.toString());
        }
    }

    private void insertWordToSQL(SQLiteDatabase db, String word, String meaning, String group_name) {
        String subSQL = "insert into word(value, meaning, correct_answer_num, incorrect_answer_num, group_name)";
        try{
            if(!word.equals("") && !meaning.equals(""))
               db.execSQL(subSQL + " values ('" + word + "','" + meaning  + "',0,0,'" + group_name +"')");
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
