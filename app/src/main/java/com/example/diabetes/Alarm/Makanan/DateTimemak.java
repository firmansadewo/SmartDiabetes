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

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DateTimemak
{
  private Context mContext;
  private String[] mFullDayNames;
  private String[] mShortDayNames;
  private boolean mWeekStartsOnMonday;
  private boolean m24hClock;
  private SimpleDateFormat mTimeFormat;
  private SimpleDateFormat mDateFormat;

  public DateTimemak(Context context)
  {
    mContext = context;
    update();
  }

  public void update()
  {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    mWeekStartsOnMonday = prefs.getBoolean("week_starts_pref", false);
    m24hClock = prefs.getBoolean("use_24h_pref", false);

    mDateFormat = new SimpleDateFormat("E MMM d, yyyy");

    if (m24hClock)
      mTimeFormat = new SimpleDateFormat("H:mm");
    else
      mTimeFormat = new SimpleDateFormat("h:mm a");

    mFullDayNames = new String[7];
    mShortDayNames = new String[7];

    SimpleDateFormat fullFormat = new SimpleDateFormat("EEEE");
    SimpleDateFormat shortFormat = new SimpleDateFormat("E");
    Calendar calendar;

    if (mWeekStartsOnMonday)
      calendar = new GregorianCalendar(2012, Calendar.AUGUST, 6);
    else
      calendar = new GregorianCalendar(2012, Calendar.AUGUST, 5);

    for (int i = 0; i < 7; i++)
    {
      mFullDayNames[i] = fullFormat.format(calendar.getTime());
      mShortDayNames[i] = shortFormat.format(calendar.getTime());
      calendar.add(Calendar.DAY_OF_WEEK, 1);
    }
  }

  public boolean is24hClock()
  {
    return m24hClock;
  }

  public String formatTime(AlarmMak alarmMak)
  {
    return mTimeFormat.format(new Date(alarmMak.getDate()));
  }

  public String formatDate(AlarmMak alarmMak)
  {
    return mDateFormat.format(new Date(alarmMak.getDate()));
  }

  public String formatDays(AlarmMak alarmMak)
  {
    boolean[] days = getDays(alarmMak);
    String res = "";

    if (alarmMak.getDays() == alarmMak.NEVER)
      res = "Never";
    else if (alarmMak.getDays() == alarmMak.EVERY_DAY)
      res = "Every day";
    else
    {
      for (int i = 0; i < 7; i++)
        if (days[i])
          res += ("" == res) ? mShortDayNames[i] : ", " + mShortDayNames[i];
    }

//    alarmMak.getNextOccurence();
//    res += " (" + formatDate(alarmMak) + ")";

    return res;
  }

  public String formatDetails(AlarmMak alarmMak)
  {
    String res = "???";

    if (alarmMak.getOccurence() == AlarmMak.ONCE)
      res = formatDate(alarmMak);
    else if (alarmMak.getOccurence() == AlarmMak.WEEKLY)
      res = formatDays(alarmMak);

    res += ", " + formatTime(alarmMak);

    return res;
  }

  public boolean[] getDays(AlarmMak alarmMak)
  {
    int offs = mWeekStartsOnMonday ? 0 : 1; 
    boolean[] rDays = new boolean[7];
    int aDays = alarmMak.getDays();

    for (int i = 0; i < 7; i++)
      rDays[(i+offs) % 7] = (aDays & (1 << i)) > 0;

    return rDays;
  }

  public void setDays(AlarmMak alarmMak, boolean[] days)
  {
    int offs = mWeekStartsOnMonday ? 0 : 1; 
    int sDays = 0;

    for (int i = 0; i < 7; i++)
      sDays |= days[(i+offs) % 7] ? (1 << i) : (0 << i);

    alarmMak.setDays(sDays);
  }

  public String[] getFullDayNames()
  {
    return mFullDayNames;
  }

  public String[] getShortDayNames()
  {
    return mShortDayNames;
  }
}

