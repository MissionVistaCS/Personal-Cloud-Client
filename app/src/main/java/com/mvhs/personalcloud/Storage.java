package com.mvhs.personalcloud;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * Created by ayates on 11/13/16.
 */

public class Storage
{
    public static final String FILE_STORE = "storage.txt";

    public static void writeImageList()
    {
        try
        {
            File fout = new File(ImageManager.IMG_PATH + FILE_STORE);
            if (!fout.exists()) fout.createNewFile();

            FileOutputStream fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (int i = 0; i < LoginActivity.imageManager.customImages.size(); i++)
            {
                bw.write(LoginActivity.imageManager.customImages.get(i).getImageId() + ":"
                        + LoginActivity.imageManager.customImages.get(i).getImagePath() + ":"
                        + LoginActivity.imageManager.customImages.get(i).getImageDate().toString());
                bw.newLine();
            }

            bw.close();
        }
        catch (IOException e)
        {
            Log.e(NetworkManager.TAG, "Oh no an error has popped up you ape", e);
        }
    }

    public static void readImageList()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(ImageManager.IMG_PATH + FILE_STORE));
            String line;
            while ((line = br.readLine()) != null)
            {
                long id = Long.parseLong(line.split(":")[0]);
                String path = line.split(":")[1];
                Date date = new Date(line.split(":")[2]);

                LoginActivity.imageManager.customImages.add(new CustomImage(id, path, date));
            }
        }
        catch (IOException e)
        {
            Log.e(NetworkManager.TAG, "Oh no an error has popped up you ape", e);
        }
    }
}
