package com.example.seyhak.restitezandroid; /**
 * Created by Seyhak on 12.03.2018.
 */
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.text.NumberFormat.*;

public class SkładnikMenu implements Comparable<SkładnikMenu> {
    private int idSM;
    private String nazwaSM;
    private String rodzajSM;
    private double cenaSM =0.0;
    //private SimpleDateFormat dataDodaniaSM = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private Date dataDodaniaSM=new Date();

    public  SkładnikMenu(int id, String nazwa, String rodzaj, double cena, Date data)
    {
        idSM = id;
        nazwaSM = nazwa;
        rodzajSM = rodzaj;
        cenaSM = cena;
        dataDodaniaSM = data;


    }
    public int getIdSM() {
        return idSM;
    }

    public String getNazwaSM() {
        return nazwaSM;
    }

    public String getRodzajSM() {
        return rodzajSM;
    }

    public double getCenaSM() {
        return cenaSM;
    }

    public Date getDataDodaniaSM() {
        return dataDodaniaSM;
    }
    public static SkładnikMenu BuildFromStringFromServer(String x)
    {
        String[] value_split = x.split("#");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String dateString = value_split[4];
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double d =Double.parseDouble(value_split[3].replace(',','.'));

        return new SkładnikMenu(Integer.parseInt(value_split[0]),value_split[1],value_split[2],d,date);
    }

    @Override
    public int compareTo(@NonNull SkładnikMenu o) {
        int comparedNames = nazwaSM.compareTo(o.nazwaSM);
        return comparedNames;
//        if(comparedNames == 0) {
//            return imie.compareTo(o.imie);
//        }
//        else {
//            return porownaneNazwiska;
//        }
    }
    }

    //Date currentDate = new Date()
