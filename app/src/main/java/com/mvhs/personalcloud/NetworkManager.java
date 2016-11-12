package com.mvhs.personalcloud;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by ayates on 11/12/16.
 */

public class NetworkManager
{
    public static final String TAG = "PersonalCloud";
    public static final int NO_CONNECTION = 1;
    public static final int WRONG_CREDENTIALS = 2;

    public static final String SESSION_COOKIE = "PHPSESSID";

    public String sessionId;
    private String url;

    public NetworkManager(String url)
    {
        this.url = url;
    }

    public int login(String username, String password)
    {
        try
        {
            Connection.Response response = Jsoup.connect(url).data("username", username, "password", password)
                    .method(Connection.Method.POST).execute();
            sessionId = response.cookie(SESSION_COOKIE);

            Document doc = response.parse();

            if (doc.body().toString().equalsIgnoreCase("failed"))
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
}
