package org.andoidtown.ai_vocabulary.addwordcomponent;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.andoidtown.ai_vocabulary.R;

public class SeekbarDialog extends Dialog{
    SeekBar bar;
    TextView barValue;
    Button okButton;
    Button cancelButton;
    AddWordGruopActivity parentActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.seekbar_dialog);
        bar = findViewById(R.id.seekBar_addword_wordnumber);
        barValue = findViewById(R.id.text_seekbar_wordnumber);
        okButton = findViewById(R.id.button_seekbar_ok);
        cancelButton = findViewById(R.id.button_seekbar_cancel);

        Toast.makeText(parentActivity,"체인지",Toast.LENGTH_LONG);
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                parentActivity.finish();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Log.d("hi",parentActivity.toString());
                parentActivity.setEditTextNum(Integer.parseInt(barValue.getText().toString()));
                parentActivity.notifyListViewDataChanged();
                Toast.makeText(parentActivity,"확인 버튼이 클릭됨",Toast.LENGTH_LONG);
                dismiss();
            }
        });
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                barValue.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                ;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ;
            }
        });
    }


    public SeekbarDialog(Context context) {
        super(context);

        parentActivity = (AddWordGruopActivity) context;
    }
}
