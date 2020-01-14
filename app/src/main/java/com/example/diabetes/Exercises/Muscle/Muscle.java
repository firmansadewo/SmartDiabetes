package com.example.diabetes.Exercises.Muscle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.diabetes.Exercises.Exercise;
import com.example.diabetes.R;


public class Muscle extends Fragment {

    public static Muscle newInstance() {
        Muscle fragment = new Muscle();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.muscle, container, false);
        Button btnFragment1=(Button)v.findViewById(R.id.button1);
        Button btnFragment2=(Button)v.findViewById(R.id.button2);
        Button btnFragment3=(Button)v.findViewById(R.id.button3);
        Button btnFragment4=(Button)v.findViewById(R.id.button4);
        Button btnFragment5=(Button)v.findViewById(R.id.button5);
        Button btnFragment6=(Button)v.findViewById(R.id.button6);
        Button btnFragment7=(Button)v.findViewById(R.id.button7);
        Button btnFragment8=(Button)v.findViewById(R.id.button8);
        Button btnFragment9=(Button)v.findViewById(R.id.button9);
        Button btnFragment10=(Button)v.findViewById(R.id.button10);

        btnFragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new BicepCurls());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new TricepsExtension());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new ShoulderPress());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new ChestPress());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new SeatedRow());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new ClassicCrunch());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new Plank());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new Squat());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new Lunges());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });
        btnFragment10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new HamstringCurl());
                fr.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                fr.addToBackStack(null);
                fr.commit();
            }
        });

        return v;
    }

}

