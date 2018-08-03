package org.andoidtown.ai_vocabulary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WordListViewAdapter extends BaseAdapter {
    private ArrayList<WordsListViewItem> wordLists = new ArrayList<>();

    public WordListViewAdapter(){
    }
    @Override
    public int getCount() {
        return wordLists.size();
    }

    @Override
    public Object getItem(int i) {
        return wordLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final int pos = i;
        final Context context = parent.getContext();

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.word_listview_item, parent, false);
        }

        TextView wordGroupName = (TextView) convertView.findViewById(R.id.wordListName);
        TextView memNumber = (TextView) convertView.findViewById(R.id.memWordsNum);
        TextView notMemNumber =  (TextView) convertView.findViewById(R.id.notMemWordsNum);

        WordsListViewItem listItem = wordLists.get(i);

        wordGroupName.setText(listItem.getItemName());
        memNumber.setText(listItem.getMemorizedNumber());
        notMemNumber.setText(listItem.getNotMemorizedNumber());
        return convertView;
    }

    public void addItem(String name, String memNum, String notMemNum)
    {
        WordsListViewItem item = new WordsListViewItem();
        item.setItemName(name);
        item.setMemorizedNumber(memNum);
        item.setNotMemorizedNumber(notMemNum);

        wordLists.add(item);
    }

}
