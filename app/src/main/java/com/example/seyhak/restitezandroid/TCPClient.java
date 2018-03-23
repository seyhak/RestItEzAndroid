package com.example.seyhak.restitezandroid;

/**
 * Created by Seyhak on 18.03.2018.
 */

// TCPClient.java
// A client program implementing TCP socket
import android.util.Log;

import java.net.*;
import java.io.*;

public class TCPClient
{
    private Socket s;
    public String send (String data)
    {// arguments supply message and hostname of destination


        try{

            DataInputStream input = new DataInputStream( s.getInputStream());//strumień bitów wchodzących
            DataOutputStream output = new DataOutputStream( s.getOutputStream());//to samo dla wychodzących

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
            for(int i = 0; i < 3; i++) {
                digitLgth[i] = input.readByte();
            }
            String strLgth = new String(digitLgth);
            int lgth = new Integer(strLgth);

            //Step 2 read byte
            byte[] digitsWord = new byte[lgth];
            for(int i = 0; i < lgth; i++) {
                digitsWord[i] = input.readByte();
            }
            ////////////////////////////////////////////////////////////////////////////////////////////
            String st ="nope";
            st = new String(digitsWord);
            String s = st;
            //System.out.println("Received: "+ st);
            return s;
        }
        catch (UnknownHostException e){
            System.out.println("Sock:"+e.getMessage());return "";}
        catch (EOFException e){
            System.out.println("EOF:"+e.getMessage());return  "";}
        catch (IOException e){
            Log.d("IO:" ,e.getMessage());return  "";}
    }
    public Boolean Login(String login, String haslo) {
        String str = send(("L"));
        if(new String("OK").equals(str))
        {
            login += " ";
            login += haslo;
            str = send((login));
            if(new String("C").equals(str))
                return true;
            else
                return  false;
        }
        else
            return  false;
    }
    class ClientThread implements Runnable
    {
        String log, pass;
        public ClientThread(String l, String p)
        {
            log=l;
            pass = p;

        }
        @Override
        public void run()
        {// arguments supply message and hostname of destination
            try{
                int serverPort = 8001;
                //String ip = "localhost";
                String ip = "10.0.2.2";
                //String data = "Hello, How are you?";
                InetAddress serverAddr = InetAddress.getByName(ip);
                s = new Socket(serverAddr, serverPort);//tworzy gniazdo
                Boolean a = Login(log,pass);
                // send("L");
            }
            catch (UnknownHostException e){
                System.out.println("Sock:"+e.getMessage());}
            catch (EOFException e){
                System.out.println("EOF:"+e.getMessage()); }
            catch (IOException e){
                System.out.println("IO:"+e.getMessage());}
//            finally {
//                if(s!=null)
//                    try {s.close();
//                    }
//                    catch (IOException e) {/*close failed*/}
//            }
            //return "nope";
        }
    }
}
