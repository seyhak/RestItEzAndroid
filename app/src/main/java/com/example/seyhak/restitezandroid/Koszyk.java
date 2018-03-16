package com.example.seyhak.restitezandroid;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Koszyk extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koszyk);
//        Intent intent = getIntent();
//        List<Integer> listIDs = new ArrayList<Integer>();
//        listIDs.addAll(intent.getIntegerArrayListExtra("ListKosz"));
//
//
//
//        ListView lv = (ListView) findViewById(R.id.listViewKoszyk);
//        List<String> listIDsStr = new ArrayList<String>();
//        for (Integer liczba:
//             listIDs) {
//            listIDsStr.add(Integer.toString(liczba));
//
//        }
//        ArrayList<String> araj = new ArrayList<String>();
//
//        araj.addAll(listIDsStr);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,araj);
//       // ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, R.layout.activity_koszyk,araj);
//       lv.setAdapter(adapter);
//       adapter.notifyDataSetChanged();
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) //Rozpoznaje kliknięcia na obiekt z listy, MA być tutaj usuwanie pozycji
//            {
//
//            }
//        });
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        //Adapter
        CustomAdapter adapter = new CustomAdapter(DataFiller.listaZamówionychSM,getApplicationContext());
        ListView lv = (ListView) findViewById(R.id.listViewKoszyk);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) //Rozpoznaje kliknięcia na obiekt z listy
            {

                //SkładnikMenu składnikData= DataFiller.listaZamówionychSM. get(position);
                DataFiller.listaZamówionychSM.remove(position);
                CustomAdapter adapter = new CustomAdapter(DataFiller.listaZamówionychSM,getApplicationContext());
                ListView lv = (ListView) findViewById(R.id.listViewKoszyk);
                lv.setAdapter(adapter);
                Podlicz();
            }
        });
        Podlicz();
    }
    private  void Podlicz()
    {
        double cena = 0;
        for (SkładnikMenu sm:DataFiller.listaZamówionychSM
             ) {
            cena += sm.getCenaSM();
        }
        cena*=100;
        cena= Math.round(cena);
        cena /=100;
        Button btn = (Button) findViewById(R.id.button_confirm);
        String btnName = getString(R.string.confirmButton) + " " + Double.toString(cena);
        btn.setText(btnName);
    }
    public void Potwierdz_click(View view) {
        //wysłanie zamówienia na serwer
    }
}