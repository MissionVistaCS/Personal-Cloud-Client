package com.mvhs.personalcloud;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ayates on 11/12/16.
 */

public class ImageManager
{
    public static String IMG_PATH = "";

    public List<CustomImage> customImages = new ArrayList<>();

    private Activity activity;

    public ImageManager()
    {

    }

    public void uploadFile(String path, Activity activity)
    {
        this.activity = activity;
        new UploadImageTask().execute(path);
    }

    public void onFileUploaded(CustomImage img)
    {
        //Log.d(NetworkManager.TAG, "File uploaded with code=" + img.getImageDate().toString());

        if (img != null)
        {
            File f = new File(img.getImagePath());

            try
            {
                copyFile(f, new File(ImageManager.IMG_PATH + f.getName()));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            img.setLocalPath(new File(ImageManager.IMG_PATH + f.getName()).getAbsolutePath());

            Log.d(NetworkManager.TAG, ImageManager.IMG_PATH);
            Log.d(NetworkManager.TAG, ImageManager.IMG_PATH + f.getName());
            Log.d(NetworkManager.TAG, img.getImagePath());

            customImages.add(img);

            //((GalleryActivity) activity).imageAdapter = new ImageAdapter(activity, customImages)
            //((GalleryActivity) activity).imageAdapter.notifyDataSetChanged();

        } else
        {
            Log.d(NetworkManager.TAG, "A problem has occurred with image upload: You have already uploaded that image or ");
        }
    }

    public String getPath(Uri uri, Activity activity)
    {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);

        return cursor.getString(column_index);
    }


    private class UploadImageTask extends AsyncTask<String, Integer, CustomImage>
    {

        @Override
        protected CustomImage doInBackground(String... strings)
        {
            long[] lol = LoginActivity.networkManager.postFile(new File(strings[0]), NetworkManager.UPLOAD_PAGE);
            return new CustomImage(lol[0], strings[0], new Date(lol[0]));
        }

        @Override
        protected void onPostExecute(CustomImage integer)
        {
            super.onPostExecute(integer);

            onFileUploaded(integer.getImageId() == -1 ? null : integer);

            Log.d(NetworkManager.TAG, "" + integer.getImageId());
        }
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException
    {
        if (!destFile.exists())
        {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try
        {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        }
        finally
        {
            if (source != null)
            {
                source.close();
            }
            if (destination != null)
            {
                destination.close();
            }
        }

        // sourceFile.delete();
    }

    public static Bitmap decodeFile(File f)
    {
        try
        {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE)
            {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        }
        catch (FileNotFoundException e)
        {
        }
        return null;
    }
}

/*
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        ((Button) findViewById(R.id.upload)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK)
            {
                Uri selectedImage = data.getData();

                String filePath = getPath(selectedImage);
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);

                //Log.d(NetworkManager.TAG, filePath);

                try
                {
                    if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png"))
                    {
                        new UploadImageTask().execute(filePath);
                    }
                    else
                    {
                        //NOT IN REQUIRED FORMAT
                    }
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    }
*/