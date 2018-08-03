package org.andoidtown.ai_vocabulary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class AddWordGruopActivity extends AppCompatActivity {
    Button cancelButton;
    Button addWGButton;
    AddWGListViewAdpater adapter;
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
}
