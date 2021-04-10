package com.tss.pakbourini.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tss.pakbourini.R;

import static android.content.Context.MODE_PRIVATE;
import static com.tss.pakbourini.Activity.SignUp.userpref;

public class Profiles extends Fragment {
    View view;
    Context mContext;
    String name;
    String email;
    String mLastName;
    TextView tvName;

    public Profiles() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(userpref, MODE_PRIVATE);
        init();
        email = sharedPreferences.getString("email", "email");
        name = sharedPreferences.getString("fname", "fname");
        mLastName = sharedPreferences.getString("lname", "lastname");
        tvName.setText(name + " " + mLastName);
        return view;
    }

    private void init() {
        tvName = view.findViewById(R.id.username);
    }


}