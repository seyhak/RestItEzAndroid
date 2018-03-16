package com.example.seyhak.restitezandroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Seyhak on 15.03.2018.
 */

public class DataFiller {
    public static boolean alreadyWasCreated = false;
    public static List<SkładnikMenu> listaSM = new ArrayList<SkładnikMenu>();
    public static List<String> lista = new ArrayList<String>();
    public static List<SkładnikMenu> listaZamówionychSM = new ArrayList<SkładnikMenu>();
    public static void CreateSM()//funkcja tymczasowa, będzie zbędna po dodaniu sieci
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String dateString = "04.08.2015 07:42:00";
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SkładnikMenu sm;
        for(int a = 0; a<30;a++)
        {
            sm = new SkładnikMenu(a,"ziemniaki " + Integer.toString(a) ,"warzywa",10.1,date);
            listaSM.add(sm);
            lista.add(Integer.toString(a));
        }
    }
}
