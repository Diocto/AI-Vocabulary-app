package org.andoidtown.ai_vocabulary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class AddWGListViewAdpater extends BaseAdapter {
    private ArrayList<AddWGListViewItem> listView = new ArrayList<>();

    public AddWGListViewAdpater() {

    }

    @Override
    public int getCount() {
        return listView.size();
    }

    @Override
    public Object getItem(int i) {
        return listView.get(i);
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
            convertView = inflater.inflate(R.layout.word_add_listview_item,parent,false);

        }

        AddWGListViewItem addWordItem = listView.get(i);

        return convertView;
    }
    public void addView()
    {
        listView.add(new AddWGListViewItem());
    }
}
