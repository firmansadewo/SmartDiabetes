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

package com.example.diabetes.Alarm.Minumobat;

import android.content.Context;
import android.util.Log;

import com.example.diabetes.Alarm.Minumobat.AlarmMin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class DataSourceMin
{
  private static final String TAG = "alarmminumobat";

  private static final DataSourceMin mDataSourceMin = new DataSourceMin();
  private static Context mContext = null;
  private static ArrayList<AlarmMin> mList = null;
  private static long mNextId;

  private static final String DATA_FILE_NAME = "alarmminumobat.txt";
  private static final long MAGIC_NUMBER = 0x54617261646f7641L;

  protected DataSourceMin()
  {
  }

  public static synchronized DataSourceMin getInstance(Context context)
  {
    if (mContext == null)
    {
      mContext = context.getApplicationContext();
      load();
    }
    return mDataSourceMin;
  }

  private static void load()
  {
    Log.i(TAG, "DataSourceMin.load()");

    mList = new ArrayList<AlarmMin>();
    mNextId = 1;

    try
    {
      DataInputStream dis = new DataInputStream(mContext.openFileInput(DATA_FILE_NAME));
      long magic = dis.readLong();
      int size;

      if (MAGIC_NUMBER == magic)
      {
        mNextId = dis.readLong();
        size = dis.readInt();

        for (int i = 0; i < size; i++)
        {
          AlarmMin alarm = new AlarmMin(mContext);
          alarm.deserialize(dis);
          mList.add(alarm);
        }
      }

      dis.close();
    } catch (IOException e)
    {
    }
  }

  public static void save()
  {
    Log.i(TAG, "DataSourceMin.save()");

    try
    {
      DataOutputStream dos = new DataOutputStream(mContext.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE));

      dos.writeLong(MAGIC_NUMBER);
      dos.writeLong(mNextId);
      dos.writeInt(mList.size());

      for (int i = 0; i < mList.size(); i++)
        mList.get(i).serialize(dos);

      dos.close();
    } catch (IOException e)
    {
    }
  }

  public static int size()
  {
    return mList.size();
  }

  public static AlarmMin get(int position)
  {
    return mList.get(position);
  }

  public static void add(AlarmMin alarm)
  {
    alarm.setId(mNextId++);
    mList.add(alarm);
    Collections.sort(mList);
    save();
  }

  public static void remove(int index)
  {
    mList.remove(index);
    save();
  }

  public static void update(AlarmMin alarm)
  {
    alarm.update();
    Collections.sort(mList);
    save();
  }

  public static long getNextId()
  {
    return mNextId;
  }
}

