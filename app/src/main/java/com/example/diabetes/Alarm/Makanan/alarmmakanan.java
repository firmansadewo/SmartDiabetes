package com.example.diabetes.Alarm.Makanan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.Intent;

import com.example.diabetes.R;

import static android.app.Activity.RESULT_OK;


public class alarmmakanan extends Fragment {

    private final String TAG = "alarmmakanan";

    private ListView mAlarmListMak;
    private AdapterMakanan mAdapterMakanan;
    private AlarmMak mCurrentAlarmMak;

    private final int NEW_ALARM_ACTIVITY = 0;
    private final int EDIT_ALARM_ACTIVITY = 1;



    private final int CONTEXT_MENU_EDIT = 0;
    private final int CONTEXT_MENU_DELETE = 1;
    private final int CONTEXT_MENU_DUPLICATE = 2;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarmmakanan, container, false);
        Log.i(TAG, "alarmmakanan.onCreate()");
        final View button = v.findViewById(R.id.add_alarmmak);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), EditAlarmMakanan.class);

                        mCurrentAlarmMak = new AlarmMak(getActivity());
                        mCurrentAlarmMak.toIntent(intent);
                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        alarmmakanan.this.startActivityForResult(intent, NEW_ALARM_ACTIVITY);
                    }});

        mAlarmListMak = (ListView) v.findViewById(R.id.alarm_listmak);

        mAdapterMakanan = new AdapterMakanan(getActivity());
        mAlarmListMak.setAdapter(mAdapterMakanan);
        mAlarmListMak.setOnItemClickListener(mListOnItemClickListener);
        registerForContextMenu(mAlarmListMak);

        mCurrentAlarmMak = null;
        return v;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "alarmmakanan.onDestroy()");
//    mAdapterMakanan.save();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(TAG, "alarmmakanan.onResume()");
        mAdapterMakanan.updateAlarms();
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == NEW_ALARM_ACTIVITY)
        {
            if (resultCode == RESULT_OK)
            {
                mCurrentAlarmMak.fromIntent(data);
                mAdapterMakanan.add(mCurrentAlarmMak);
            }
            mCurrentAlarmMak = null;
        }
        else if (requestCode == EDIT_ALARM_ACTIVITY)
        {
            if (resultCode == RESULT_OK)
            {
                mCurrentAlarmMak.fromIntent(data);
                mAdapterMakanan.update(mCurrentAlarmMak);
            }
            mCurrentAlarmMak = null;
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        if (v.getId() == R.id.alarm_listmak)
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

            menu.setHeaderTitle(mAdapterMakanan.getItem(info.position).getTitle());
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
            Intent intent = new Intent(getActivity(), EditAlarmMakanan.class);

            mCurrentAlarmMak = mAdapterMakanan.getItem(info.position);
            mCurrentAlarmMak.toIntent(intent);
            startActivityForResult(intent, EDIT_ALARM_ACTIVITY);
        }
        else if (index == CONTEXT_MENU_DELETE)
        {
            mAdapterMakanan.delete(info.position);
        }
        else if (index == CONTEXT_MENU_DUPLICATE)
        {
            AlarmMak alarmMak = mAdapterMakanan.getItem(info.position);
            AlarmMak newAlarmMak = new AlarmMak(getActivity());
            Intent intent = new Intent();

            alarmMak.toIntent(intent);
            newAlarmMak.fromIntent(intent);
            newAlarmMak.setTitle(alarmMak.getTitle() + " (copy)");
            mAdapterMakanan.add(newAlarmMak);
        }

        return true;
    }

    private AdapterView.OnItemClickListener mListOnItemClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(getActivity(), EditAlarmMakanan.class);

            mCurrentAlarmMak = mAdapterMakanan.getItem(position);
            mCurrentAlarmMak.toIntent(intent);
            alarmmakanan.this.startActivityForResult(intent, EDIT_ALARM_ACTIVITY);
        }
    };

}