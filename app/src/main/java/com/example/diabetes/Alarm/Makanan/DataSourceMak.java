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

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import android.util.Log;
import android.content.Context;

public class DataSourceMak
{
  private static final String TAG = "alarmmakanan";

  private static final DataSourceMak mDataSourceMak = new DataSourceMak();
  private static Context mContext = null;
  private static ArrayList<AlarmMak> mList = null;
  private static long mNextId;

  private static final String DATA_FILE_NAME = "alarmmakanan.txt";
  private static final long MAGIC_NUMBER = 0x54617261646f7641L;

  protected DataSourceMak()
  {
  }

  public static synchronized DataSourceMak getInstance(Context context)
  {
    if (mContext == null)
    {
      mContext = context.getApplicationContext();
      load();
    }
    return mDataSourceMak;
  }

  private static void load()
  {
    Log.i(TAG, "DataSourceMak.load()");

    mList = new ArrayList<AlarmMak>();
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
          AlarmMak alarmMak = new AlarmMak(mContext);
          alarmMak.deserialize(dis);
          mList.add(alarmMak);
        }
      }

      dis.close();
    } catch (IOException e)
    {
    }
  }

  public static void save()
  {
    Log.i(TAG, "DataSourceMak.save()");

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

  public static AlarmMak get(int position)
  {
    return mList.get(position);
  }

  public static void add(AlarmMak alarmMak)
  {
    alarmMak.setId(mNextId++);
    mList.add(alarmMak);
    Collections.sort(mList);
    save();
  }

  public static void remove(int index)
  {
    mList.remove(index);
    save();
  }

  public static void update(AlarmMak alarmMak)
  {
    alarmMak.update();
    Collections.sort(mList);
    save();
  }

  public static long getNextId()
  {
    return mNextId;
  }
}

