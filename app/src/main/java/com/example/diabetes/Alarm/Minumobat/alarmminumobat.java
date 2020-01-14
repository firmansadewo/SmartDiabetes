package com.example.diabetes.Alarm.Minumobat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.diabetes.Alarm.Minumobat.AlarmMin;
import com.example.diabetes.R;

import static android.app.Activity.RESULT_OK;


public class alarmminumobat extends Fragment {

    private final String TAG = "alarmminumobat";

    private ListView mAlarmListMin;
    private AdapterMinumobat mAdapterMinumobat;
    private AlarmMin mCurrentAlarmMin;

    private final int NEW_ALARM_ACTIVITY = 0;
    private final int EDIT_ALARM_ACTIVITY = 1;



    private final int CONTEXT_MENU_EDIT = 0;
    private final int CONTEXT_MENU_DELETE = 1;
    private final int CONTEXT_MENU_DUPLICATE = 2;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarmminumobat, container, false);
        Log.i(TAG, "alarmminumobat.onCreate()");
        final View button = v.findViewById(R.id.add_alarmmin);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), EditAlarmMinumobat.class);

                        mCurrentAlarmMin = new AlarmMin(getActivity());
                        mCurrentAlarmMin.toIntent(intent);
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        alarmminumobat.this.startActivityForResult(intent, NEW_ALARM_ACTIVITY);
                    }});

        mAlarmListMin = (ListView) v.findViewById(R.id.alarm_listmin);

        mAdapterMinumobat = new AdapterMinumobat(getActivity());
        mAlarmListMin.setAdapter(mAdapterMinumobat);
        mAlarmListMin.setOnItemClickListener(mListOnItemClickListener);
        registerForContextMenu(mAlarmListMin);

        mCurrentAlarmMin = null;
        return v;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "alarmminumobat.onDestroy()");
//    mAlarmListAdapter.save();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(TAG, "alarmminumobat.onResume()");
        mAdapterMinumobat.updateAlarms();
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == NEW_ALARM_ACTIVITY)
        {
            if (resultCode == RESULT_OK)
            {
                mCurrentAlarmMin.fromIntent(data);
                mAdapterMinumobat.add(mCurrentAlarmMin);
            }
            mCurrentAlarmMin = null;
        }
        else if (requestCode == EDIT_ALARM_ACTIVITY)
        {
            if (resultCode == RESULT_OK)
            {
                mCurrentAlarmMin.fromIntent(data);
                mAdapterMinumobat.update(mCurrentAlarmMin);
            }
            mCurrentAlarmMin = null;
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        if (v.getId() == R.id.alarm_listmin)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

            menu.setHeaderTitle(mAdapterMinumobat.getItem(info.position).getTitle());
            menu.add(Menu.NONE, CONTEXT_MENU_EDIT, Menu.NONE, "Edit");
            menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete");
            menu.add(Menu.NONE, CONTEXT_MENU_DUPLICATE, Menu.NONE, "Duplicate");
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index = item.getItemId();

        if (index == CONTEXT_MENU_EDIT)
        {
            Intent intent = new Intent(getActivity(), EditAlarmMinumobat.class);

            mCurrentAlarmMin = mAdapterMinumobat.getItem(info.position);
            mCurrentAlarmMin.toIntent(intent);
            startActivityForResult(intent, EDIT_ALARM_ACTIVITY);
        }
        else if (index == CONTEXT_MENU_DELETE)
        {
            mAdapterMinumobat.delete(info.position);
        }
        else if (index == CONTEXT_MENU_DUPLICATE)
        {
            AlarmMin alarm = mAdapterMinumobat.getItem(info.position);
            AlarmMin newAlarm = new AlarmMin(getActivity());
            Intent intent = new Intent();

            alarm.toIntent(intent);
            newAlarm.fromIntent(intent);
            newAlarm.setTitle(alarm.getTitle() + " (copy)");
            mAdapterMinumobat.add(newAlarm);
        }

        return true;
    }

    private AdapterView.OnItemClickListener mListOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(getActivity(), EditAlarmMinumobat.class);

            mCurrentAlarmMin = mAdapterMinumobat.getItem(position);
            mCurrentAlarmMin.toIntent(intent);
            alarmminumobat.this.startActivityForResult(intent, EDIT_ALARM_ACTIVITY);
        }
    };

}