package com.mvhs.personalcloud;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ayates on 11/12/16.
 */

public class LoginActivity extends AppCompatActivity
{
    public static NetworkManager networkManager;

    private EditText email;
    private EditText password;
    private Button signin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.email_sign_in_button);

        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new LoginTask().execute(email.getText().toString(), password.getText().toString());
            }
        });

        networkManager = new NetworkManager("http://192.168.1.2/login.php");
    }

    private void handleLogin(int code)
    {
        Log.d(NetworkManager.TAG, "code=" + code);
    }

    private class LoginTask extends AsyncTask<String, Integer, Integer>
    {

        @Override
        protected Integer doInBackground(String... strings)
        {
            return networkManager.login(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Integer integer)
        {
            super.onPostExecute(integer);

            handleLogin(integer);
        }
    }
}
