package org.andoidtown.ai_vocabulary.mainactivitycomponent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.andoidtown.ai_vocabulary.addwordcomponent.AddWordGruopActivity;
import org.andoidtown.ai_vocabulary.R;
import org.andoidtown.ai_vocabulary.wordlistviewcomponent.WordGroupListViewActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManagementWordsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManagementWordsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagementWordsFragment extends Fragment {

    Button showWordGroupListButton;
    Button showAddWGListViewButton;
    public ManagementWordsFragment() {
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_management_words, container, false);
        showWordGroupListButton = rootView.findViewById(R.id.showWordsListButton);
        showWordGroupListButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WordGroupListViewActivity.class);
                startActivity(intent);
            }
        });
        showAddWGListViewButton = rootView.findViewById(R.id.addWordButton);
        showAddWGListViewButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), AddWordGruopActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }


}