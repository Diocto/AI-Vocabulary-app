package org.andoidtown.ai_vocabulary.wordtest_component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.andoidtown.ai_vocabulary.R;

import java.util.ArrayList;
public class InstantIncorrectWordListViewAdapter extends BaseAdapter{
    private ArrayList<InstantIncorrectWordListViewItem> listItem = new ArrayList<>();
    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public void addItem(String word, String meaning)
    {
        InstantIncorrectWordListViewItem item = new InstantIncorrectWordListViewItem(word, meaning);
        listItem.add(item);
    }
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        final Context context = parent.getContext();
        WordViewHolder viewHolder;
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.incorrectword_listview_item,parent,false);
            viewHolder = new WordViewHolder();
            viewHolder.wordTextView = view.findViewById(R.id.text_inworditem_word);
            viewHolder.meaningTextView = view.findViewById(R.id.text_inworditem_meaning);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (WordViewHolder) view.getTag();
        }
        viewHolder.wordTextView.setText(listItem.get(i).getWord());
        viewHolder.meaningTextView.setText(listItem.get(i).getMeaning());

        return view;
    }
    static class WordViewHolder {
        TextView wordTextView;
        TextView meaningTextView;
    }
}
