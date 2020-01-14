package com.example.diabetes.Alarm.Makanan;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.content.Intent;
import android.content.DialogInterface;

import com.example.diabetes.R;

public class EditAlarmMakanan extends Activity
{
  private EditText mTitle;
  private CheckBox mAlarmEnabled;
  private Spinner mOccurence;
  private Button mDateButton;
  private Button mTimeButton;

  private AlarmMak mAlarmMak;
  private DateTimemak mDateTime;

  private GregorianCalendar mCalendar;
  private int mYear;
  private int mMonth;
  private int mDay;
  private int mHour;
  private int mMinute;

  static final int DATE_DIALOG_ID = 0;
  static final int TIME_DIALOG_ID = 1;
  static final int DAYS_DIALOG_ID = 2;

  @Override
  public void onCreate(Bundle bundle)
  {
    super.onCreate(bundle);
    setContentView(R.layout.editalarmmakanan);

    //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    mTitle = (EditText)findViewById(R.id.title);
    mAlarmEnabled = (CheckBox)findViewById(R.id.alarm_checkbox);
    mOccurence = (Spinner)findViewById(R.id.occurence_spinner);
    mDateButton = (Button)findViewById(R.id.date_button);
    mTimeButton = (Button)findViewById(R.id.time_button);

    mAlarmMak = new AlarmMak(this);
    mAlarmMak.fromIntent(getIntent());

    mDateTime = new DateTimemak(this);

    mTitle.setText(mAlarmMak.getTitle());
    mTitle.addTextChangedListener(mTitleChangedListener);

    mOccurence.setSelection(mAlarmMak.getOccurence());
    mOccurence.setOnItemSelectedListener(mOccurenceSelectedListener);

    mAlarmEnabled.setChecked(mAlarmMak.getEnabled());
    mAlarmEnabled.setOnCheckedChangeListener(mAlarmEnabledChangeListener);

    mCalendar = new GregorianCalendar();
    mCalendar.setTimeInMillis(mAlarmMak.getDate());
    mYear = mCalendar.get(Calendar.YEAR);
    mMonth = mCalendar.get(Calendar.MONTH);
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
    mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
    mMinute = mCalendar.get(Calendar.MINUTE);

    updateButtons();
  }

  @Override
  protected Dialog onCreateDialog(int id)
  {
    if (DATE_DIALOG_ID == id)
      return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);

    else if (TIME_DIALOG_ID == id)
      return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, mDateTime.is24hClock());
    else if (DAYS_DIALOG_ID == id)
      return DaysPickerDialog();
    else

      return null;
  }

  @Override
  protected void onPrepareDialog(int id, Dialog dialog)
  {
    if (DATE_DIALOG_ID == id)
      ((DatePickerDialog)dialog).updateDate(mYear, mMonth, mDay);
    else if (TIME_DIALOG_ID == id)
      ((TimePickerDialog)dialog).updateTime(mHour, mMinute);


  }

  public void onDateClick(View view)
  {
    if (AlarmMak.ONCE == mAlarmMak.getOccurence())
      showDialog(DATE_DIALOG_ID);
    else if (AlarmMak.WEEKLY == mAlarmMak.getOccurence())
      showDialog(DAYS_DIALOG_ID);
  }

  public void onTimeClick(View view)
  {
    showDialog(TIME_DIALOG_ID);
  }

  public void onDoneClick(View view)
  {
    Intent intent = new Intent();

    mAlarmMak.toIntent(intent);
    setResult(RESULT_OK, intent);
    finish();
  }

  public void onCancelClick(View view)
  {
    setResult(RESULT_CANCELED, null);
    finish();
  }

  private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
      mYear = year;
      mMonth = monthOfYear;
      mDay = dayOfMonth;

      mCalendar = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute);

      mAlarmMak.setDate(mCalendar.getTimeInMillis());


      updateButtons();
    }
  };

  private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
      mHour = hourOfDay;
      mMinute = minute;

      mCalendar = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute);

      mAlarmMak.setDate(mCalendar.getTimeInMillis());



      updateButtons();
    }
  };

  private TextWatcher mTitleChangedListener = new TextWatcher()
  {
    public void afterTextChanged(Editable s)
    {
      mAlarmMak.setTitle(mTitle.getText().toString());
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
    }
  };

  private AdapterView.OnItemSelectedListener mOccurenceSelectedListener = new AdapterView.OnItemSelectedListener()
  {
    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
    {
      mAlarmMak.setOccurence(position);
      updateButtons();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }
  };

  private CompoundButton.OnCheckedChangeListener mAlarmEnabledChangeListener = new CompoundButton.OnCheckedChangeListener()
  {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
      mAlarmMak.setEnabled(isChecked);
    }
  };

  private void updateButtons()
  {
    if (AlarmMak.ONCE == mAlarmMak.getOccurence())
      mDateButton.setText(mDateTime.formatDate(mAlarmMak));
    else if (AlarmMak.WEEKLY == mAlarmMak.getOccurence())
      mDateButton.setText(mDateTime.formatDays(mAlarmMak));
    mTimeButton.setText(mDateTime.formatTime(mAlarmMak));

  }

  private Dialog DaysPickerDialog()
  {
    AlertDialog.Builder builder;
    final boolean[] days = mDateTime.getDays(mAlarmMak);
    final String[] names = mDateTime.getFullDayNames();

    builder = new AlertDialog.Builder(this);

    builder.setTitle("Week days");

    builder.setMultiChoiceItems(names, days, new DialogInterface.OnMultiChoiceClickListener()
    {
      public void onClick(DialogInterface dialog, int whichButton, boolean isChecked)
      {
      }
    });

    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface dialog, int whichButton)
      {
        mDateTime.setDays(mAlarmMak, days);
        updateButtons();
      }
    });

    builder.setNegativeButton("Cancel", null);

    return builder.create();
  }
}

