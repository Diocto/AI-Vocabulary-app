package org.andoidtown.ai_vocabulary.incorrect_word_list_component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.andoidtown.ai_vocabulary.R;

import java.util.ArrayList;

public class IncorrectWordListAdapter extends BaseAdapter {
    private ArrayList<IncorrectWordListItem> incorrectWordList;
    public IncorrectWordListAdapter(ArrayList<IncorrectWordListItem> itemList)
    {
        this.incorrectWordList = itemList;
    }
    @Override
    public int getCount() {
        return incorrectWordList.size();
    }

    @Override
    public Object getItem(int position) {
        return incorrectWordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context parentContext = parent.getContext();
        View view = convertView;
        InwordViewHolder viewHolder;
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) parentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.incorrectword_listview_item,parent,false);
            viewHolder = new InwordViewHolder();
            viewHolder.word = (TextView) view.findViewById(R.id.text_inworditem_word);
            viewHolder.meaning = (TextView) view.findViewById(R.id.text_inworditem_meaning);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (InwordViewHolder) view.getTag();
        }
        viewHolder.word.setText(incorrectWordList.get(position).word);
        viewHolder.meaning.setText(incorrectWordList.get(position).meaning);
        return view;
    }
    public void addItem(IncorrectWordListItem item)
    {
        incorrectWordList.add(item);
    }
    static class InwordViewHolder {
        TextView word;
        TextView meaning;
    }
}