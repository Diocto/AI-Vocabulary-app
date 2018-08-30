package org.andoidtown.ai_vocabulary.wordlistviewcomponent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.andoidtown.ai_vocabulary.R;

import java.util.ArrayList;

public class WordGroupListViewAdapter extends BaseAdapter {
    private ArrayList<WordGroupListViewItem> wordLists = new ArrayList<>();

    public WordGroupListViewAdapter(){
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
            convertView = inflater.inflate(R.layout.word_group_listview_item, parent, false);
        }

        TextView wordGroupName = (TextView) convertView.findViewById(R.id.wordListName);
        TextView memNumber = (TextView) convertView.findViewById(R.id.memWordsNum);
        TextView notMemNumber =  (TextView) convertView.findViewById(R.id.notMemWordsNum);

        WordGroupListViewItem listItem = wordLists.get(i);

        wordGroupName.setText(listItem.getItemName());
        memNumber.setText(listItem.getMemorizedNumber());
        notMemNumber.setText(listItem.getNotMemorizedNumber());
        return convertView;
    }

    public void addItem(String name, String memNum, String notMemNum)
    {
        WordGroupListViewItem item = new WordGroupListViewItem();
        item.setItemName(name);
        item.setMemorizedNumber(memNum);
        item.setNotMemorizedNumber(notMemNum);

        wordLists.add(item);
    }

}
