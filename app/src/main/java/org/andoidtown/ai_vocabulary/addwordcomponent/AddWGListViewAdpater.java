package org.andoidtown.ai_vocabulary.addwordcomponent;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.andoidtown.ai_vocabulary.R;

import java.util.ArrayList;

public class AddWGListViewAdpater extends BaseAdapter {
    private ArrayList<AddWGListViewItem> listView ;
    private ArrayList<View> editList = new ArrayList<>();
    private Context context;
    final static int IS_CLICKED_ONCE = 0;
    final static int IS_NOT_CLICKED = 1;
    public AddWGListViewAdpater(Context context, ArrayList<AddWGListViewItem> listView) {
        this.listView = listView;
        this.context = context;
    }
    public View getEditText(int index)
    {
        return editList.get(index);
    }

    @Override
    public int getCount() {
        return listView.size();
    }

    @Override
    public AddWGListViewItem getItem(int i) {
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
        View view = convertView;
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.word_add_listview_item,parent,false);
            editList.add(view);
        }
        return view;
    }
    public void addBottomEditText(View view){
        if(view == null)
        {
            Log.d("user","view is null");
            return;
        }
        else
        {
            Log.d("user",view.toString());
        }
        if(view.getTag() == null) {
            Log.d("user","view tag is null");
            view.setTag(IS_CLICKED_ONCE);
            addEditText();
            notifyDataSetChanged();
        }
    }
    public void addEditText()
    {
        AddWGListViewItem newItem = new AddWGListViewItem();
        listView.add(newItem);
    }
}
