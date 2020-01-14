package com.example.diabetes.Food;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.diabetes.Exercises.Exercise;
import com.example.diabetes.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FoodFragment extends Fragment  {

    /**
     * Called when the activity is first created.
     */
    com.example.diabetes.Food.DatabaseHelper myDbHelper;
    protected Cursor cursor;
    protected ListAdapter adapter;
    protected ListView listMakanan;
    private SearchView searchView;

    ArrayList<String> list;
    ArrayAdapter<String > arrayadapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food, container, false);
        listMakanan = (ListView) v.findViewById(R.id.list_makanan);
       // setHasOptionsMenu(true);
      //  searchView = (SearchView) v.findViewById(R.id.searchView);


    myDbHelper = new DatabaseHelper(getActivity());
        try {
            myDbHelper.createDataBase();

        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list);
        listMakanan.setAdapter(adapter);



      /*   try {
             myDbHelper.openDataBase();

         }catch(SQLException sqle){
             throw sqle;
         }
         */
        view ();
        return v;
    }


    private void view (){
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        //db.getReadableDatabase();

        try{
            cursor = db.rawQuery("SELECT * FROM tbmakanan", null);
            adapter= new SimpleCursorAdapter(getActivity(),R.layout.listmakanan, cursor,
                    new String [] {"nama","berat","kalori","unit"},
                    new int [] {R.id.nama,R.id.berat,R.id.kalori,R.id.unit});
            listMakanan.setAdapter(adapter);
        }catch(SQLException sqle){
            throw sqle;
        }


    }



}