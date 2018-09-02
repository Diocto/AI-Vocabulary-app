package org.andoidtown.ai_vocabulary.incorrect_word_list_component;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.andoidtown.ai_vocabulary.R;

import java.util.ArrayList;

public class IncorrectWordGroupListAdapter extends BaseAdapter {
    private ArrayList<IncorrectWordGroupListItem> incorrectWordList;
    private IncorrectWordGroupListActivity parentActivity;
    public IncorrectWordGroupListAdapter(ArrayList<IncorrectWordGroupListItem> itemList, IncorrectWordGroupListActivity parentActivity)
    {
        this.incorrectWordList = itemList;
        this.parentActivity = parentActivity;
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
        final InwordViewHolder viewHolder;
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) parentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.incorrectword_group_listview_item,parent,false);
            viewHolder = new InwordViewHolder();
            viewHolder.groupName = (TextView) view.findViewById(R.id.text_inwordgroup_groupname);
            viewHolder.wordNumber = (TextView) view.findViewById(R.id.text_inwordgroup_wordn);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (InwordViewHolder) view.getTag();
        }
        viewHolder.groupName.setText(incorrectWordList.get(position).groupName);
        viewHolder.wordNumber.setText(incorrectWordList.get(position).wordNumber);
        return view;
    }
    public void addItem(IncorrectWordGroupListItem item)
    {
        incorrectWordList.add(item);
    }
    static class InwordViewHolder {
        TextView groupName;
        TextView wordNumber;
    }
}