package com.android.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

public class ForgotPasswordFragment extends Fragment {
    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        Button toolBarBackButton = (Button) v.findViewById(R.id.toolbarbtn);

        toolBarBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                UserFragment userFragment = new UserFragment();
                FragmentManager manager = getFragmentManager();
                assert manager != null;
                manager.beginTransaction()
                        .replace(R.id.container, userFragment)
                        .commit();
            }
        });

        return v;
    }
}