package com.example.diabetes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.diabetes.Exercises.Muscle.Muscle;

import org.w3c.dom.Text;

import static com.android.volley.VolleyLog.TAG;


public class About extends Fragment {
TextView text;

    public static About newInstance() {
        About fragment = new About();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.aboutfragment, container, false);

        text = (TextView) v.findViewById(R.id.textabout);
        text.setGravity(Gravity.CENTER);

return v;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "About.onDestroy()");
//    mAlarmListAdapter.save();
    }
}
