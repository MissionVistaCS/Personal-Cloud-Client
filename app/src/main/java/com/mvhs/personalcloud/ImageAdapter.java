package com.mvhs.personalcloud;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

public class ImageAdapter extends BaseAdapter
{
    Context mContext;

    List<File> files;

    public ImageAdapter(Context context, List<File> files)
    {
        mContext = context;
        this.files = files;
    }


    @Override
    public int getCount()
    {
        return files.size();
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //Convert files.get(position) into imageView, then return it
        ImageView imageView;
        imageView = new ImageView(mContext);
        imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(10, 10, 10, 10);
        Log.d("getView","Formatted image: " + imageView.toString());
        imageView.setImageURI(Uri.fromFile(files.get(position)));
        return imageView;
    }
}

