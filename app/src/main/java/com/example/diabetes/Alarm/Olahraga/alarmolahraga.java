package com.example.diabetes.Alarm.Olahraga;

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

import com.example.diabetes.Alarm.Olahraga.AlarmOl;
import com.example.diabetes.R;

import static android.app.Activity.RESULT_OK;


public class alarmolahraga extends Fragment {

    private final String TAG = "alarmolahraga";

    private ListView mAlarmListOl;
    private AdapterOlahraga mAdapterOlahraga;
    private AlarmOl mCurrentAlarm;

    private final int NEW_ALARM_ACTIVITY = 0;
    private final int EDIT_ALARM_ACTIVITY = 1;



    private final int CONTEXT_MENU_EDIT = 0;
    private final int CONTEXT_MENU_DELETE = 1;
    private final int CONTEXT_MENU_DUPLICATE = 2;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarmolahraga, container, false);
        Log.i(TAG, "alarmolahraga.onCreate()");
        final View button = v.findViewById(R.id.add_alarmol);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), EditAlarmOlahraga.class);

                        mCurrentAlarm = new AlarmOl(getActivity());
                        mCurrentAlarm.toIntent(intent);
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        alarmolahraga.this.startActivityForResult(intent, NEW_ALARM_ACTIVITY);
                    }});

        mAlarmListOl = (ListView) v.findViewById(R.id.alarm_listol);

        mAdapterOlahraga = new AdapterOlahraga(getActivity());
        mAlarmListOl.setAdapter(mAdapterOlahraga);
        mAlarmListOl.setOnItemClickListener(mListOnItemClickListener);
        registerForContextMenu(mAlarmListOl);

        mCurrentAlarm = null;
        return v;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "AlarmFragment.onDestroy()");
//    mAlarmListAdapter.save();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(TAG, "AlarmFragment.onResume()");
        mAdapterOlahraga.updateAlarms();
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == NEW_ALARM_ACTIVITY)
        {
            if (resultCode == RESULT_OK)
            {
                mCurrentAlarm.fromIntent(data);
                mAdapterOlahraga.add(mCurrentAlarm);
            }
            mCurrentAlarm = null;
        }
        else if (requestCode == EDIT_ALARM_ACTIVITY)
        {
            if (resultCode == RESULT_OK)
            {
                mCurrentAlarm.fromIntent(data);
                mAdapterOlahraga.update(mCurrentAlarm);
            }
            mCurrentAlarm = null;
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        if (v.getId() == R.id.alarm_listol)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

            menu.setHeaderTitle(mAdapterOlahraga.getItem(info.position).getTitle());
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
            Intent intent = new Intent(getActivity(), EditAlarmOlahraga.class);

            mCurrentAlarm = mAdapterOlahraga.getItem(info.position);
            mCurrentAlarm.toIntent(intent);
            startActivityForResult(intent, EDIT_ALARM_ACTIVITY);
        }
        else if (index == CONTEXT_MENU_DELETE)
        {
            mAdapterOlahraga.delete(info.position);
        }
        else if (index == CONTEXT_MENU_DUPLICATE)
        {
            AlarmOl alarm = mAdapterOlahraga.getItem(info.position);
            AlarmOl newAlarm = new AlarmOl(getActivity());
            Intent intent = new Intent();

            alarm.toIntent(intent);
            newAlarm.fromIntent(intent);
            newAlarm.setTitle(alarm.getTitle() + " (copy)");
            mAdapterOlahraga.add(newAlarm);
        }

        return true;
    }

    private AdapterView.OnItemClickListener mListOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(getActivity(), EditAlarmOlahraga.class);

            mCurrentAlarm = mAdapterOlahraga.getItem(position);
            mCurrentAlarm.toIntent(intent);
            alarmolahraga.this.startActivityForResult(intent, EDIT_ALARM_ACTIVITY);
        }
    };

}