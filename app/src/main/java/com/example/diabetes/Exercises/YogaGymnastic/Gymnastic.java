package com.example.diabetes.Exercises.YogaGymnastic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.diabetes.R;

import android.widget.MediaController;
import android.widget.VideoView;
import android.net.Uri;


public class Gymnastic extends Fragment {

    public static Gymnastic newInstance() {
        Gymnastic fragment = new Gymnastic();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gymnastic, container, false);
        MediaController mc= new MediaController(getActivity());
        VideoView video = (VideoView)v.findViewById(R.id.video);
        String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.senamdiabetes;
        video.setVideoURI(Uri.parse(path));
        video.setMediaController(new MediaController(getActivity())); //error in (this)[enter image description here][1]
        video.requestFocus();
        video.start();
        return v;

    }

}



