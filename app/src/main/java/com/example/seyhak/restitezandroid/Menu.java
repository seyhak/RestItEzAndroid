package com.example.seyhak.restitezandroid;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.*;
import android.database.Cursor;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.LinearLayout.*;
import android.app.*;
//import android.support.design.widget.Snackbar;

public class Menu extends AppCompatActivity /*implements LoaderManager.LoaderCallbacks<Cursor>*/{


//    public List<SkładnikMenu> listaSM = new ArrayList<SkładnikMenu>();
//    public List<String> lista = new ArrayList<String>();
//    public List<SkładnikMenu> listaZamówionychSM = new ArrayList<SkładnikMenu>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        /////////////////////////////tworzenie listy menu/////////////////////////////////
        if(!DataFiller.alreadyWasCreated)
        {
            try{
                TCPClient tc = new TCPClient();
                DataFiller.listaSM = tc.GetMenu();
              //  DataFiller.CreateSM();
                DataFiller.alreadyWasCreated = true;
            }
            catch (Exception e)
            {

            }
        }
         Collections.sort(DataFiller.listaSM);
        //Adapter
        CustomAdapter adapter = new CustomAdapter(DataFiller.listaSM,getApplicationContext());
        ListView lv = (ListView) findViewById(R.id.ListViewMenu);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) //Rozpoznaje kliknięcia na obiekt z listy
            {

                SkładnikMenu składnikData= DataFiller.listaSM.get(position);
                DataFiller.listaZamówionychSM.add(składnikData);
            }
        });
//////////////////////////////////////////////////////KONIEC TWORZENIA LISTY MENU

        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);

    }
    public void Koszyk_click(View view)
    {
        if(DataFiller.listaZamówionychSM.size()!=0)
        {
            Intent intent = new Intent(this, Koszyk.class);
            startActivity(intent);
        }
    }
//    @Override
//    protected void onTaskRemoved(Intent rootIntent)
//    {
//
//        try{
//            TCPClient tc = new TCPClient();
//            tc.Logout();
//            //Log.e("FLAGX : ", ServiceInfo.FLAG_STOP_WITH_TASK + "");
//            super.onTaskRemoved(rootIntent);
//            System.out.println("kill");
//        }
//        catch (Exception e)
//        {
//
//        }
//
//
//    }
//    protected void onDestroy()
//    {
//        try{
//        TCPClient tc = new TCPClient();
//        tc.Logout();
//        super.onDestroy();
//        System.out.println("kill");
//    }
//        catch (Exception e)
//        {
//
//        }
//    }

}