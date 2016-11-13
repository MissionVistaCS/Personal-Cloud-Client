package com.mvhs.personalcloud;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

/**
 * Created by ayates on 11/12/16.
 */

public class LoginActivity extends AppCompatActivity
{
    public static NetworkManager networkManager;
    public static ImageManager imageManager;

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

        networkManager = new NetworkManager("http://ryank3egan.ftp.sh:8080/");
        imageManager = new ImageManager();

        ImageManager.IMG_PATH = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).getAbsolutePath() + "/personalcloud/";

        File f = new File(ImageManager.IMG_PATH);
        if (!f.exists()) f.mkdir();

        Storage.readImageList();
    }

    private void handleLogin(int code)
    {
        if (code == 0)
        {
            Intent i = new Intent(this, GalleryActivity.class);
            startActivity(i);
            Log.d(NetworkManager.TAG, "code=" + code + " & sessionID=" + networkManager.sessionId);
        }
        else
        {
            Log.d(NetworkManager.TAG, "code=" + code + " & sessionID=" + networkManager.sessionId);
        }
    }

    private class LoginTask extends AsyncTask<String, Integer, Integer>
    {

        @Override
        protected Integer doInBackground(String... strings)
        {
            return networkManager.login(strings[0], strings[1], "login.php");
        }

        @Override
        protected void onPostExecute(Integer integer)
        {
            super.onPostExecute(integer);

            handleLogin(integer);
        }
    }
}
