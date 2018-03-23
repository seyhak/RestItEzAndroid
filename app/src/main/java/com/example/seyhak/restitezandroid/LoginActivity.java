package com.example.seyhak.restitezandroid;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private Socket s;

    public static final String EXTRA_MESSAGE = "com.example.seyhak.restitezandroid.MESSAGE";//klucz używany do sparowania z wartością w intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    boolean LoadData()
    {

        return true;
    }
   public void Login(View view)throws Exception
    {
      EditText LoginEditText = (EditText) findViewById(R.id.LoginText);
      String login = LoginEditText.getText().toString();
      EditText PasswordEditText = (EditText) findViewById(R.id.PassText);
      String password = PasswordEditText.getText().toString();
      ///////////WĄTKOWANIE//////////
      ExecutorService executor = Executors.newCachedThreadPool();
      //executor.submit(new ClientThread(login,password));// until the result came back.
      Future<Boolean> futureCall = executor.submit(new ClientThread(login,password));
      Boolean result = futureCall.get(20, TimeUnit.SECONDS); // Here the thread will be blocked

        //String result = futureCall.get(10,TimeUnit.SECONDS); // Will waiting for ten seconds the result.
       // futureCall.cancel(true);//How to cancel the response time and unlock the thread?

      //Thread thr = new Thread(new ClientThread(login,password));
      //thr.start();
        //TCPClient tc = new TCPClient();
     //   Boolean ab =Login(login,password);
      if(false)//jeżeli fajnie to wbijamy do kolejnego activity
      {
          Intent intent = new Intent(this, Menu.class);
          intent.putExtra(EXTRA_MESSAGE, login);
          startActivity(intent);

      }
      else
          {
              LoginEditText.setText("Błędne hasło lub login");
          }
    }
    public void sendMessage(View view) {

        // Do something in response to button
    }

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
            //System.out.println("Received: "+ st);
            return st;
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
    class ClientThread implements Callable<Boolean> {
        String log, pass;

        public ClientThread(String l, String p) {
            log = l;
            pass = p;

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
}

