package com.example.diabetes.Tracking;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.diabetes.Helper.DatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.diabetes.R;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


public class FragmentPuasa extends Fragment implements OnChartValueSelectedListener {
    LineChart grafikpuasa;
    EditText inputtanggalpuasa;
    EditText inputtrackpuasa;
    Button btnSubmit;

    protected Paint mYAxisSafeZonePaint;

    SimpleDateFormat sdfpuasa = new SimpleDateFormat("dd", Locale.getDefault());
    final Calendar myCalendar = Calendar.getInstance();

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    LineData lineData;
    LinearLayout hasil;
    TextView textnilai,texttanggal;
    String text;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_add_puasa, container, false);

        grafikpuasa = v.findViewById(R.id.mpChart);
        inputtanggalpuasa = v.findViewById(R.id.inputanggalpuasa);
        inputtrackpuasa = v.findViewById(R.id.inputtrackpuasa);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        databaseHelper = new DatabaseHelper(getActivity());
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        hasil= v.findViewById(R.id.texthasil);
        textnilai=  v.findViewById(R.id.textnilaiguladarah);
        texttanggal= v.findViewById(R.id.texttanggalguladarah);

        SubmitData();

        lineDataSet.setLineWidth(4);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };


        inputtanggalpuasa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        lineDataSet.setValues(getDataValues());
        //  lineDataSet.setLabel("Gula Darah");
        dataSets.clear();
        dataSets.add(lineDataSet);
        lineData = new LineData(dataSets);
        grafikpuasa.clear();
        grafikpuasa.setData(lineData);
        grafikpuasa.invalidate();


        //ngilangin y axis di kanan
        YAxis yAxis = grafikpuasa.getAxisLeft();
        YAxis yRAxis = grafikpuasa.getAxisRight();
        yRAxis.setEnabled(false);


        //Formatter x axis label jadi tanggal
        ValueFormatter Bismillah= new Bismillah();
        XAxis xAxis = grafikpuasa.getXAxis();
        xAxis.setValueFormatter(Bismillah);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1); // only intervals of 1 day

        //Limit Line mulai dari sini
        LimitLine ll1 = new LimitLine(126f, "Tinggi");
        ll1.setLineWidth(2f);
        ll1.setLineColor(getActivity().getResources().getColor(R.color.redtrans));
        ll1.enableDashedLine(10f, 5f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        //  ll1.setTypeface(tfRegular);

        LimitLine ll2 = new LimitLine(80f, "Normal");
        ll2.setLineWidth(2f);
        ll2.setLineColor(getActivity().getResources().getColor(R.color.greentrans));
        ll2.enableDashedLine(10f, 5f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        // ll2.setTypeface(tfRegular);

        LimitLine ll3 = new LimitLine(0f, "Rendah");
        ll3.setLineWidth(2f);
        ll3.setLineColor(getActivity().getResources().getColor(R.color.yellowtrans));
        ll3.enableDashedLine(10f, 5f, 0f);
        ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll3.setTextSize(10f);
        // ll3.setTypeface(tfRegular);

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(true);
        xAxis.setDrawLimitLinesBehindData(true);

        // add limit lines
        yAxis.addLimitLine(ll1);
        yAxis.addLimitLine(ll2);
        yAxis.addLimitLine(ll3);

        //kalo mau point nya bolong di true in
        lineDataSet.setDrawCircleHole(false);
        // disable description text
        grafikpuasa.getDescription().setEnabled(false);

        // enable touch gestures
        grafikpuasa.setTouchEnabled(true);

        grafikpuasa.animateXY(1000, 1000);

        Legend l = grafikpuasa.getLegend();
        l.setEnabled(false);
        grafikpuasa.setTouchEnabled(true);
        grafikpuasa.setOnChartValueSelectedListener(this);

//kalo mau keisi warna
        /*
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return grafikpuasa.getAxisLeft().getAxisMinimum();
            }
        });

        // set color of filled area
        if (Utils.getSDKInt() >= 18) {
            // drawables only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_blue);
            lineDataSet.setFillDrawable(drawable);
        } else {
            lineDataSet.setFillColor(Color.BLACK);
        }

        */

        return v;
    }

    private void SubmitData() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date tanggalpuasa= myCalendar.getTime();
                long xVal = tanggalpuasa.getTime();
                //float xVal = Float.parseFloat(String.valueOf(inputtanggalpuasa.getText()));
                float yVal = Float.parseFloat(String.valueOf(inputtrackpuasa.getText()));

                databaseHelper.insertDatab(yVal,xVal);



                lineDataSet.setValues(getDataValues());
                dataSets.clear();
                dataSets.add(lineDataSet);
                lineData = new LineData(dataSets);
                grafikpuasa.clear();
                grafikpuasa.setData(lineData);
                grafikpuasa.invalidate();
                // disable description text dan kotak biru legend
                grafikpuasa.getDescription().setEnabled(false);


                //tulisan hasil
                hasil.setVisibility(View.VISIBLE);
                text=inputtrackpuasa.getText().toString();
                textnilai.setText(text);
                text=inputtanggalpuasa.getText().toString();
                texttanggal.setText(text);
            }

        });
    }


    private ArrayList<Entry> getDataValues() {
        ArrayList<Entry> dataVals = new ArrayList<>();
        String[] columns = {"tanggalpuasa", "trackpuasa"};
        Cursor cursor = sqLiteDatabase.query("track_puasa", columns, null, null, null, null, null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            dataVals.add(new Entry(cursor.getLong(0)/100000, cursor.getFloat(1)));
        }

        return dataVals;
    }



    private void updateLabel() {
        String myFormat = "dd/MM/YY"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        inputtanggalpuasa.setText(sdf.format(myCalendar.getTime()));
    }



    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOW HIGH", "low: " + grafikpuasa.getLowestVisibleX() + ", high: " + grafikpuasa.getHighestVisibleX());
        Log.i("MIN MAX", "xMin: " + grafikpuasa.getXChartMin() + ", xMax: " + grafikpuasa.getXChartMax() + ", yMin: " + grafikpuasa.getYChartMin() + ", yMax: " + grafikpuasa.getYChartMax());


        //hasil.setVisibility(View.VISIBLE);


    }

    @Override
    public void onNothingSelected() {

    }
}







