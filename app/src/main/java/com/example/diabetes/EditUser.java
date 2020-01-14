package com.example.diabetes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.ActionBar;

import static com.android.volley.VolleyLog.TAG;


public class EditUser extends Fragment {

    public static EditUser newInstance() {
        EditUser fragment = new EditUser();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edituserfragment, container, false);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "edituser.onDestroy()");
//    mAlarmListAdapter.save();
    }

}