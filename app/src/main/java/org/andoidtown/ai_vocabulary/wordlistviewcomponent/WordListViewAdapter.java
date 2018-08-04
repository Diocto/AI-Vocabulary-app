package org.andoidtown.ai_vocabulary.wordlistviewcomponent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.andoidtown.ai_vocabulary.R;

import java.util.ArrayList;

public class WordListViewAdapter extends BaseAdapter{
    ArrayList<WordListViewItem> wordList = new ArrayList<>();

    @Override
    public int getCount() {
        return wordList.size();
    }

    @Override
    public WordListViewItem getItem(int i) {
        return wordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent){
        final int pos = i;
        final Context context = parent.getContext();

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.word_listview_item, parent, false);
        }

        TextView word = convertView.findViewById(R.id.wordTextView);
        TextView meaning = convertView.findViewById(R.id.meaningTextView);

        WordListViewItem newItem = wordList.get(i);

        word.setText(newItem.getValue());
        meaning.setText(newItem.getMeaning());

        return convertView;
    }

    public void addItem(String value, String meaning, int caNum, int incaNum)
    {
        WordListViewItem newItem = new WordListViewItem();
        newItem.setValue(value);
        newItem.setMeaning(meaning);
        newItem.setCaNum(caNum);
        newItem.setIncaNum(incaNum);

        wordList.add(newItem);
    }
}
