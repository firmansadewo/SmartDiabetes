package com.example.diabetes.Exercises.YogaGymnastic.Yoga;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.diabetes.R;


public class Yoga extends Fragment {

    public static Yoga newInstance() {
        Yoga fragment = new Yoga();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_yoga, container, false);
        Button btnFragment1=(Button)v.findViewById(R.id.button1);
        Button btnFragment2=(Button)v.findViewById(R.id.button2);
        Button btnFragment3=(Button)v.findViewById(R.id.button3);
        Button btnFragment4=(Button)v.findViewById(R.id.button4);
        Button btnFragment5=(Button)v.findViewById(R.id.button5);

        btnFragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new MountainP());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new DownwardP());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new ChildsP());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new BridgeP());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new TreeP());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });


        return v;
    }
}
