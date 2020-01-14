package com.example.diabetes.Exercises.YogaGymnastic.Yoga;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.diabetes.R;


public class TreeP extends Fragment {
        Button buttonstart;
    public static TreeP newInstance() {
        TreeP fragment = new TreeP();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_tree, container, false);
        buttonstart = (Button) v.findViewById(R.id.btnstart);

        buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTreePstart();

            }

            private void openTreePstart() {
                TreePstart fragment = TreePstart.newInstance();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                transaction.addToBackStack(null);
                transaction.add(R.id.fragment_container, fragment).commit();
            }
        });
        return v;
    }
}
