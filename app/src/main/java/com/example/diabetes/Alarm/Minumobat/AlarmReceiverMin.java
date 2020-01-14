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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiverMin extends BroadcastReceiver
{
  private final String TAG = "AlarmFragment";

  @Override
  public void onReceive(Context context, Intent intent)
  {
    Intent newIntent = new Intent(context, AlarmNotificationMin.class);
    AlarmMin alarmMin = new AlarmMin(context);

    alarmMin.fromIntent(intent);
    alarmMin.toIntent(newIntent);
    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

    Log.i(TAG, "AlarmReceiverMin.onReceive('" + alarmMin.getTitle() + "')");

    context.startActivity(newIntent);
  }
}

