package com.example.diabetes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.android.volley.VolleyLog.TAG;


public class Upgrade extends Fragment {

    public static Upgrade newInstance() {
        Upgrade fragment = new Upgrade();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upgradefragment, container, false);

    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "upgrade.onDestroy()");
//    mAlarmListAdapter.save();
    }

}