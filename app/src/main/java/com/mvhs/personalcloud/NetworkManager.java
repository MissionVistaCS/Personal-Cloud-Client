package com.mvhs.personalcloud;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by ayates on 11/12/16.
 */

public class NetworkManager
{
    public static final String TAG = "PersonalCloud";
    public static final int NO_CONNECTION = 1;
    public static final int WRONG_CREDENTIALS = 2;
    public static final int UPLOAD_ERROR = 3;

    public static final String LOGIN_PAGE = "login.php";
    public static final String UPLOAD_PAGE = "upload.php";

    public static final String SESSION_COOKIE = "PHPSESSID";

    public String sessionId;
    private String url;

    public NetworkManager(String url)
    {
        this.url = url;
    }

    public int login(String username, String password, String extension)
    {
        try
        {
            Connection.Response response = Jsoup.connect(url + extension).data("username", username, "password", password)
                    .method(Connection.Method.POST).execute();
            sessionId = response.cookie(SESSION_COOKIE);

            Document doc = response.parse();

            //Log.d(TAG, doc.html().toString());

            if (doc.body().toString().contains("Failed"))
            {
                return WRONG_CREDENTIALS;
            }
        }
        catch (IOException e)
        {
            Log.e(TAG, "Uh oh no connection", e);
            return NO_CONNECTION;
        }

        return 0;
    }

    public long[] postFile(File f, String extension)
    {
        try
        {
            Document d = Jsoup.connect(url + extension).cookie(SESSION_COOKIE, sessionId)
                    .data("file", f.getName(), new FileInputStream(f)).post();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            long id = formatter.parse(d.text().toString()).getTime() / 1000;
            return new long[]{id, 0};
            //Log.d(TAG, d.html().toString());
        }
        catch (IOException e)
        {
            Log.e(TAG, "Uh oh another error", e);
            return new long[]{-1, UPLOAD_ERROR};
        }
        catch (NumberFormatException e)
        {
            Log.e(TAG, "Uh oh another error", e);
            return new long[]{-1, UPLOAD_ERROR};
        }
        catch (ParseException e)
        {
            Log.e(TAG, "Uh oh another error", e);
            return new long[]{-1, UPLOAD_ERROR};
        }
    }
}
