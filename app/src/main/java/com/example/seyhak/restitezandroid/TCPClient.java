package com.example.seyhak.restitezandroid;

/**
 * Created by Seyhak on 18.03.2018.
 */

// TCPClient.java
// A client program implementing TCP socket
import android.util.Log;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TCPClient {
    private Socket s;

    public  Boolean Logout() throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        //executor.submit(new ClientThread(login,password));// until the result came back.
        Future<Boolean> futureCall = executor.submit(new ClientThreadKill());
        Boolean result = futureCall.get(1, TimeUnit.SECONDS); // Here the thread will be blocked
        return result;
    }
    public Boolean Login(String l, String p) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        //executor.submit(new ClientThread(login,password));// until the result came back.
        Future<Boolean> futureCall = executor.submit(new ClientThread(l, p));
        Boolean result = futureCall.get(40, TimeUnit.SECONDS); // Here the thread will be blocked
        return result;
    }

    public Boolean SendOrders(List<SkładnikMenu> listOfOrders,double s) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        //executor.submit(new ClientThread(login,password));// until the result came back.
        Future<Boolean> futureCall = executor.submit(new OrderThread(listOfOrders,s));
        Boolean result = futureCall.get(40, TimeUnit.SECONDS); // Here the thread will be blocked
        return result;
    }

    public List<SkładnikMenu> GetMenu() throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        //executor.submit(new ClientThread(login,password));// until the result came back.
        Future<List<SkładnikMenu>> futureCall = executor.submit(new ClientThreadMenuLoader());
        List<SkładnikMenu> listaSM = new ArrayList<SkładnikMenu>();
        listaSM = futureCall.get(40, TimeUnit.SECONDS); // Here the thread will be blocked
        return listaSM;
    }

    class ClientThread implements Callable<Boolean> {

        String log, pass;

        public ClientThread(String l, String p) {
            log = l;
            pass = p;

        }

        public String sendLoginPass(String data, String pass) {// arguments supply message and hostname of destination


            try {

                DataInputStream input = new DataInputStream(s.getInputStream());//strumień bitów wchodzących
                DataOutputStream output = new DataOutputStream(s.getOutputStream());//to samo dla wychodzących

                //Step 1 send length
                //System.out.println("Length"+ data.length());
                //output.writeInt(data.length());
                //Step 2 send length
                // System.out.println("Writing.......");
                output.writeBytes(data); // UTF is a string encoding

                //Step 1 read length
                //int nb = input.readInt();
                //int nb = 256;
                /////////////////////////////////////odczyt długości////////////////////////////////////////
                byte[] digitLgth = new byte[3];
                for (int i = 0; i < 3; i++) {
                    digitLgth[i] = input.readByte();
                }
                String strLgth = new String(digitLgth);
                int lgth = new Integer(strLgth);

                //Step 2 read byte
                byte[] digitsWord = new byte[lgth];
                for (int i = 0; i < lgth; i++) {
                    digitsWord[i] = input.readByte();
                }
                ////////////////////////////////////////////////////////////////////////////////////////////
                String st = "nope";
                st = new String(digitsWord);
                //System.out.println("Received: "+ st);
                if (new String("OK").equals(st)) {
                    output.writeBytes(pass);
                    /////////////////////////////////////odczyt długości////////////////////////////////////////

                    digitLgth = new byte[3];
                    for (int i = 0; i < 3; i++) {
                        digitLgth[i] = input.readByte();
                    }
                    strLgth = new String(digitLgth);
                    lgth = new Integer(strLgth);

                    //Step 2 read byte
                    digitsWord = new byte[lgth];
                    for (int i = 0; i < lgth; i++) {
                        digitsWord[i] = input.readByte();
                    }
                    st = new String(digitsWord);
                    if (new String("C").equals(st))//odebranie pakietu menu
                    {
                        digitLgth = new byte[3];
                        for (int i = 0; i < 3; i++) {
                            digitLgth[i] = input.readByte();
                        }
                        strLgth = new String(digitLgth);
                        lgth = new Integer(strLgth);

                        //Step 2 read byte
                        digitsWord = new byte[lgth];
                        for (int i = 0; i < lgth; i++) {
                            digitsWord[i] = input.readByte();
                        }
                        st = new String(digitsWord);
                        DataFiller.workerID=Integer.valueOf(st);
                        s.close();
//                    /////////////////////////////////////odczyt długości////////////////////////////////////////
//                    digitLgth = new byte[3];
//                    for(int i = 0; i < 3; i++) {
//                        digitLgth[i] = input.readByte();
//                    }
//                    strLgth = new String(digitLgth);
//                    lgth = new Integer(strLgth);
//
//                    //Step 2 read byte
//                    digitsWord = new byte[lgth];
//                    for(int i = 0; i < lgth; i++) {
//                        digitsWord[i] = input.readByte();
//                    }
//                    st = new String(digitsWord);
                        return st="C";
                    } else {
                        return " ";
                    }
                } else
                    return " ";
            } catch (UnknownHostException e) {
                System.out.println("Sock:" + e.getMessage());
                return "";
            } catch (EOFException e) {
                System.out.println("EOF:" + e.getMessage());
                return "";
            } catch (IOException e) {
                Log.d("IO:", e.getMessage());
                return "";
            }
        }

        public Boolean Login(String login, String haslo) {
            login += " ";
            login += haslo;
            String str = sendLoginPass("L", login);

            if (new String("C").equals(str))
                return true;
            else
                return false;
        }

        @Override
        public Boolean call() throws Exception {// arguments supply message and hostname of destination
            int serverPort = 8001;
            //String ip = "localhost";
            String ip = "10.0.2.2";
            //String data = "Hello, How are you?";
            InetAddress serverAddr = InetAddress.getByName(ip);
            s = new Socket(serverAddr, serverPort);//tworzy gniazdo
            Boolean a = Login(log, pass);
            return a;
        }
    }

    class ClientThreadMenuLoader implements Callable<List<SkładnikMenu>> {


        @Override
        public List<SkładnikMenu> call() throws Exception {// arguments supply message and hostname of destination
            int serverPort = 8001;
            //String ip = "localhost";
            String ip = "10.0.2.2";
            //String data = "Hello, How are you?";
            InetAddress serverAddr = InetAddress.getByName(ip);
            s = new Socket(serverAddr, serverPort);//tworzy gniazdo


            try {

                DataInputStream input = new DataInputStream(s.getInputStream());//strumień bitów wchodzących
                DataOutputStream output = new DataOutputStream(s.getOutputStream());//to samo dla wychodzących

                //Step 1 send length
                //System.out.println("Length"+ data.length());
                //output.writeInt(data.length());
                //Step 2 send length
                // System.out.println("Writing.......");
                output.writeBytes("M"); // UTF is a string encoding

                //Step 1 read length
                //int nb = input.readInt();
                //int nb = 256;
                /////////////////////////////////////odczyt długości////////////////////////////////////////
                byte[] digitLgth = new byte[3];
                for (int i = 0; i < 3; i++) {
                    digitLgth[i] = input.readByte();
                }
                String strLgth = new String(digitLgth);
                int lgth = new Integer(strLgth);

                //Step 2 read byte
                byte[] digitsWord = new byte[lgth];
                for (int i = 0; i < lgth; i++) {
                    digitsWord[i] = input.readByte();
                }
                ////////////////////////////////////////////////////////////////////////////////////////////
                String st = "nope";
                st = new String(digitsWord);
                //System.out.println("Received: "+ st);
                if (new String("OK").equals(st)) {

                    /////////////////////////////////////odczyt długości menu////////////////////////////////////////

                    digitLgth = new byte[3];
                    for (int i = 0; i < 3; i++) {
                        digitLgth[i] = input.readByte();
                    }
                    strLgth = new String(digitLgth);
                    lgth = new Integer(strLgth);

                    //Step 2 read byte
                    List<SkładnikMenu> listaSM = new ArrayList<SkładnikMenu>();
                    for (int i = 0; i < lgth; i++)//odczytaj i dodaj do listy każdą pozycję
                    {
                        byte[] digitLgthTmp = new byte[3];
                        for (int j = 0; j < 3; j++) {
                            digitLgth[j] = input.readByte();
                        }
                        String strLgthTMP = new String(digitLgth);
                        int lgthTMP = new Integer(strLgthTMP);

                        //Step 2 read byte
                        byte[] digitsWordTMP = new byte[lgthTMP];
                        for (int j = 0; j < lgthTMP; j++) {
                            digitsWordTMP[j] = input.readByte();
                        }

                        String tmp = new String(digitsWordTMP);
                        //ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(tmp);
                        listaSM.add(SkładnikMenu.BuildFromStringFromServer(tmp));
                    }
                    s.close();
                    return listaSM;
                } else
                    s.close();
                return null;
            } catch (UnknownHostException e) {
                System.out.println("Sock:" + e.getMessage());
                return null;
            } catch (EOFException e) {
                System.out.println("EOF:" + e.getMessage());
                return null;
            } catch (IOException e) {
                Log.d("IO:", e.getMessage());
                return null;
            }
        }
    }

    class OrderThread implements Callable<Boolean> {

        List<SkładnikMenu> ordList = new ArrayList<SkładnikMenu>();
        double sum;
        public OrderThread( List<SkładnikMenu> l,double s) {
            ordList = l;
            sum = s;
        }
        @Override
        public Boolean call() throws Exception
        {// arguments supply message and hostname of destination
            int serverPort = 8001;
            //String ip = "localhost";
            String ip = "10.0.2.2";
            //String data = "Hello, How are you?";
            InetAddress serverAddr = InetAddress.getByName(ip);
            s = new Socket(serverAddr, serverPort);//tworzy gniazdo

            try {

                DataInputStream input = new DataInputStream(s.getInputStream());//strumień bitów wchodzących
                DataOutputStream output = new DataOutputStream(s.getOutputStream());//to samo dla wychodzących

                //Step 1 send length
                //System.out.println("Length"+ data.length());
                //output.writeInt(data.length());
                //Step 2 send length
                // System.out.println("Writing.......");
                output.writeBytes("O"); // UTF is a string encoding

                //Step 1 read length
                //int nb = input.readInt();
                //int nb = 256;
                /////////////////////////////////////odczyt długości////////////////////////////////////////
                byte[] digitLgth = new byte[3];
                for (int i = 0; i < 3; i++) {
                    digitLgth[i] = input.readByte();
                }
                String strLgth = new String(digitLgth);
                int lgth = new Integer(strLgth);

                //Step 2 read byte
                byte[] digitsWord = new byte[lgth];
                for (int i = 0; i < lgth; i++) {
                    digitsWord[i] = input.readByte();
                }
                ////////////////////////////////////////////////////////////////////////////////////////////
                String st = "nope";
                st = new String(digitsWord);
                //System.out.println("Received: "+ st);
                if (new String("OK").equals(st)) {
                    String IDs = String.valueOf(DataFiller.workerID);
                    IDs+="#";
                    String sumStr = String.valueOf(sum);
                    IDs+=sumStr;
                    for (SkładnikMenu sm: ordList
                         ) {
                        IDs+="#";
                        IDs+=sm.getIdSM();
                    }
                    output.writeBytes(IDs); // UTF is a string encoding
                        return true;
                    } else {
                        return false;
                    }
            } catch (UnknownHostException e) {
                System.out.println("Sock:" + e.getMessage());
                return false;
            } catch (EOFException e) {
                System.out.println("EOF:" + e.getMessage());
                return false;
            } catch (IOException e) {
                Log.d("IO:", e.getMessage());
                return false;
            }
        }
    }
    class ClientThreadKill implements Callable<Boolean> {

        public void sendLogout() {// arguments supply message and hostname of destination


            try {

                DataInputStream input = new DataInputStream(s.getInputStream());//strumień bitów wchodzących
                DataOutputStream output = new DataOutputStream(s.getOutputStream());//to samo dla wychodzących

                //Step 1 send length
                //System.out.println("Length"+ data.length());
                //output.writeInt(data.length());
                //Step 2 send length
                // System.out.println("Writing.......");
                output.writeBytes("W"); // UTF is a string encoding

                //Step 1 read length
                //int nb = input.readInt();
                //int nb = 256;
                /////////////////////////////////////odczyt długości////////////////////////////////////////
                byte[] digitLgth = new byte[3];
                for (int i = 0; i < 3; i++) {
                    digitLgth[i] = input.readByte();
                }
                String strLgth = new String(digitLgth);
                int lgth = new Integer(strLgth);

                //Step 2 read byte
                byte[] digitsWord = new byte[lgth];
                for (int i = 0; i < lgth; i++) {
                    digitsWord[i] = input.readByte();
                }
                ////////////////////////////////////////////////////////////////////////////////////////////
                String st = "nope";
                st = new String(digitsWord);
                //System.out.println("Received: "+ st);
                if (new String("OK").equals(st)) {
                    output.writeBytes(String.valueOf(DataFiller.workerLogin));
                    /////////////////////////////////////odczyt długości////////////////////////////////////////
                }
            } catch (UnknownHostException e) {
                System.out.println("Sock:" + e.getMessage());
            } catch (EOFException e) {
                System.out.println("EOF:" + e.getMessage());
            } catch (IOException e) {
                Log.d("IO:", e.getMessage());
            }
        }


        @Override
        public Boolean call() throws Exception {// arguments supply message and hostname of destination
            int serverPort = 8001;
            //String ip = "localhost";
            String ip = "10.0.2.2";
            //String data = "Hello, How are you?";
            InetAddress serverAddr = InetAddress.getByName(ip);
            s = new Socket(serverAddr, serverPort);//tworzy gniazdo
            sendLogout();
            return true;
        }
    }
}

