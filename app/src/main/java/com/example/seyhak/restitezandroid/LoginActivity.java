package com.example.seyhak.restitezandroid;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class LoginActivity extends AppCompatActivity {

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
   public void Login(View view)
    {
      EditText LoginEditText = (EditText) findViewById(R.id.LoginText);
      String login = LoginEditText.getText().toString();
      EditText PasswordEditText = (EditText) findViewById(R.id.PassText);
      String password = PasswordEditText.getText().toString();

      if(true||(login==""&&password==""))//jeżeli fajnie to wbijamy do kolejnego activity
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
}
