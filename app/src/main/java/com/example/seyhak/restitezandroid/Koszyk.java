package com.example.seyhak.restitezandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Collections;
import java.util.List;

import static java.lang.Math.*;

public class Koszyk extends AppCompatActivity {

    double cena = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koszyk);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        Collections.sort(DataFiller.listaZamówionychSM);
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
        cena = 0;
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
    public void Potwierdz_click(View view)throws Exception {
        //wysłanie zamówienia na serwer
        TCPClient tc = new TCPClient();
        Boolean bln= tc.SendOrders(DataFiller.listaZamówionychSM,cena);
        if(bln)
        {
            DataFiller.listaZamówionychSM=new ArrayList<SkładnikMenu>();
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Wysłano zamówienie");
            dlgAlert.setTitle("Potwierdzenie");
            dlgAlert.setCancelable(true);
            Intent intent = new Intent(this, Menu.class);
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                            Intent intent = new Intent(Koszyk.this, Menu.class);
                            startActivity(intent);
                        }
                    });
            dlgAlert.create().show();
        }
    }
}
