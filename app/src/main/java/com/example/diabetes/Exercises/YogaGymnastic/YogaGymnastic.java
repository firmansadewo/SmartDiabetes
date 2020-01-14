package com.example.diabetes.Exercises.YogaGymnastic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.diabetes.Exercises.YogaGymnastic.Yoga.Yoga;
import com.example.diabetes.R;


public class YogaGymnastic extends Fragment {

    public static YogaGymnastic newInstance() {
        YogaGymnastic fragment = new YogaGymnastic();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.yogagymnastic, container, false);
        Button btnFragment1=(Button)v.findViewById(R.id.button1);
        Button btnFragment2=(Button)v.findViewById(R.id.button2);
        btnFragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new Yoga());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new Gymnastic());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });


        return v;
    }
}
