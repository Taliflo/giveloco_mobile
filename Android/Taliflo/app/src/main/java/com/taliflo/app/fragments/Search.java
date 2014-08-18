package com.taliflo.app.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.taliflo.app.R;

/**
 * A simple {@link android.app.Fragment} subclass.
 *
 */
public class Search extends Fragment {


    // Layout views
    private EditText searchInput;
    private ListView searchResults;

    public Search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        searchInput = (EditText) v.findViewById(R.id.search_input);
        searchResults = (ListView) v.findViewById(R.id.search_results);

        searchInput.setSelectAllOnFocus(true);
        searchInput.setOnEditorActionListener( new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Perform search
                    return true;
                }
                return false;
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
