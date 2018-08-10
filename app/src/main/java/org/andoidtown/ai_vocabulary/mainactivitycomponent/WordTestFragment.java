package org.andoidtown.ai_vocabulary.mainactivitycomponent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.andoidtown.ai_vocabulary.R;
import org.andoidtown.ai_vocabulary.wordtestcomponent.WordTestActivity;


public class WordTestFragment extends Fragment {

    public WordTestFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_word_test, container, false);
        Button testWordButton = rootView.findViewById(R.id.testWordButton);
        testWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WordTestActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
