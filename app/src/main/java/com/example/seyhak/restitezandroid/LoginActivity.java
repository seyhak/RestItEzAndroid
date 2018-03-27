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

    public void OnLoginClick(View view)
    {
        EditText LoginEditText = (EditText) findViewById(R.id.LoginText);
        LoginEditText.setText("");

    }
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
      if((!password.equals(new String(""))&&!login.equals(new String(""))))
      {

          ///////////WĄTKOWANIE//////////
          TCPClient tc = new TCPClient();
          Boolean result =tc.Login(login,password);
          if(result)//jeżeli fajnie to wbijamy do kolejnego activity
          {
              DataFiller.workerLogin = login;
              Intent intent = new Intent(this, Menu.class);
              intent.putExtra(EXTRA_MESSAGE, login);
              startActivity(intent);

          }
          else
          {
              LoginEditText.setText("Błędne hasło lub login");
          }
      }
    }
    public void sendMessage(View view) {

        // Do something in response to button
    }
}




