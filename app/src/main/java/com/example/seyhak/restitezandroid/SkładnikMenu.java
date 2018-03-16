package com.example.seyhak.restitezandroid; /**
 * Created by Seyhak on 12.03.2018.
 */
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class SkładnikMenu {
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

    //Date currentDate = new Date();

}
