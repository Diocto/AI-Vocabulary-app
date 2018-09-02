package org.andoidtown.ai_vocabulary.add_word_component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import org.andoidtown.ai_vocabulary.R;

import java.util.ArrayList;

public class AddWGListViewAdpater extends BaseAdapter {
    private ArrayList<AddWGListViewItem> myItemList;
    private Context context;
    final static int IS_CLICKED_ONCE = 0;
    final static int IS_NOT_CLICKED = 1;
    public AddWGListViewAdpater(Context context, ArrayList<AddWGListViewItem> listView) {
        this.myItemList = listView;
        this.context = context;
    }

    @Override
    public int getCount() {
        return myItemList.size();
    }

    @Override
    public AddWGListViewItem getItem(int i) {
        return myItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.word_add_listview_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.word = (EditText) view.findViewById(R.id.add_word_editText);
            viewHolder.meaning = (EditText) view.findViewById(R.id.add_meaning_editText);
            viewHolder.word.setPrivateImeOptions("defaultInputmode=english;");
            viewHolder.meaning.setPrivateImeOptions("defaultInputmode=korean;");
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.word.setText(myItemList.get(i).word);
        viewHolder.meaning.setText(myItemList.get(i).wordsMeaning);
        viewHolder.word.setId(i);
        viewHolder.meaning.setId(i);
        viewHolder.word.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    final int position = v.getId();
                    final EditText wordEdit = (EditText) v;
                    myItemList.get(position).word = wordEdit.getText().toString();
                }
            }
        });
        viewHolder.meaning.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    final int position = v.getId();
                    final EditText meaningEdit = (EditText) v;
                    myItemList.get(position).wordsMeaning = meaningEdit.getText().toString();
                }
            }
        });
        return view;
    }
    public void addNewItem()
    {
        AddWGListViewItem newItem = new AddWGListViewItem();
        myItemList.add(newItem);
    }
    static class ViewHolder {
        EditText word;
        EditText meaning;
    }
}
