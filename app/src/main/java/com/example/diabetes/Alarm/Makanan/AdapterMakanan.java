/**************************************************************************
 *
 * Copyright (C) 2012-2015 Alex Taradov <alex@taradov.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *************************************************************************/

package com.example.diabetes.Alarm.Makanan;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.content.Intent;
import android.content.Context;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.diabetes.R;

public class AdapterMakanan extends BaseAdapter
{
  private final String TAG = "alarmmakanan";

  private Context mContext;
  private DataSourceMak mDataSourceMak;
  private LayoutInflater mInflater;
  private DateTimemak mDateTime;
  private int mColorOutdated;
  private int mColorActive;
  private AlarmManager mAlarmManager;


  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public AdapterMakanan(Context context)
  {
    mContext = context;
    mDataSourceMak = DataSourceMak.getInstance(context);

    Log.i(TAG, "AdapterMakanan.create()");

    mInflater = LayoutInflater.from(context);
    mDateTime = new DateTimemak(context);

    mColorOutdated = mContext.getResources().getColor(R.color.alarm_title_outdated);
    mColorActive = mContext.getResources().getColor(R.color.alarm_title_active);

    mAlarmManager = (AlarmManager)context.getSystemService(mContext.ALARM_SERVICE);

    dataSetChanged();
  }

  public void save()
  {
    mDataSourceMak.save();
  }


  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void update(AlarmMak alarmMak)
  {
    mDataSourceMak.update(alarmMak);
    dataSetChanged();
  }


  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void updateAlarms()
  {
    Log.i(TAG, "AdapterMakanan.updateAlarms()");
    for (int i = 0; i < mDataSourceMak.size(); i++)
      mDataSourceMak.update(mDataSourceMak.get(i));
    dataSetChanged();
  }


  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void add(AlarmMak alarmMak)
  {
    mDataSourceMak.add(alarmMak);
    dataSetChanged();
  }


  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void delete(int index)
  {
    cancelAlarm(mDataSourceMak.get(index));
    mDataSourceMak.remove(index);
    dataSetChanged();
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void onSettingsUpdated()
  {
    mDateTime.update();
    dataSetChanged();
  }

  public int getCount()
  {
    return mDataSourceMak.size();
  }

  public AlarmMak getItem(int position)
  {
    return mDataSourceMak.get(position);
  }

  public long getItemId(int position)
  {
    return position;
  }

  public View getView(int position, View convertView, ViewGroup parent)
  {
    ViewHolder holder;
    AlarmMak alarmMak = mDataSourceMak.get(position);

    if (convertView == null)
    {
      convertView = mInflater.inflate(R.layout.listalarmmakanan, null);

      holder = new ViewHolder();
      holder.title = (TextView)convertView.findViewById(R.id.item_title);
      holder.details = (TextView)convertView.findViewById(R.id.item_details);

      convertView.setTag(holder);
    }
    else
    {
      holder = (ViewHolder)convertView.getTag();
    }
  
    holder.title.setText(alarmMak.getTitle());
    holder.details.setText(mDateTime.formatDetails(alarmMak) + (alarmMak.getEnabled() ? "" : " [disabled]"));

    if (alarmMak.getOutdated())
      holder.title.setTextColor(mColorOutdated);
    else
      holder.title.setTextColor(mColorActive);

    return convertView;
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private void dataSetChanged()
  {
    for (int i = 0; i < mDataSourceMak.size(); i++)
      setAlarm(mDataSourceMak.get(i));

    notifyDataSetChanged();
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private void setAlarm(AlarmMak alarmMak)
  {
    PendingIntent sender;
    Intent intent;

    if (alarmMak.getEnabled() && !alarmMak.getOutdated())
    {
      intent = new Intent(mContext, AlarmReceiverMak.class);
      alarmMak.toIntent(intent);
      sender = PendingIntent.getBroadcast(mContext, (int) alarmMak.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
      mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmMak.getDate(), sender);
      Log.i(TAG, "AdapterMakanan.setAlarm(" + alarmMak.getId() + ", '" + alarmMak.getTitle() + "', " + alarmMak.getDate()+")");
    }
  }

  private void cancelAlarm(AlarmMak alarmMak)
  {
    PendingIntent sender;
    Intent intent;

    intent = new Intent(mContext, AlarmReceiverMak.class);
    sender = PendingIntent.getBroadcast(mContext, (int) alarmMak.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    mAlarmManager.cancel(sender);
  }

  static class ViewHolder
  {
    TextView title;
    TextView details;
  }
}

