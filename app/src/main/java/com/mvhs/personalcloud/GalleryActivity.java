package com.mvhs.personalcloud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by tyler on 11/12/2016.
 */

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ArrayList<File> foundFiles = new ArrayList<>();
        String[] fileExtensions =  new String[] {"jpg", "png", "gif","jpeg"};
        File defaultImageDirectory = new File("storage/emulated/0/Download"); //requires <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> in manifest
        File camera = new File ("storage/emulated/0/DCIM/Camera");
        Log.d("onCreate","Established directory to search");
        ArrayList<File> foundImages = new ArrayList<>();
        File[] files = defaultImageDirectory.listFiles();



        for (File CurFile : files) {
            if (CurFile.getName().toLowerCase().endsWith("jpg") || CurFile.getName().toLowerCase().endsWith("png") ||
                    CurFile.getName().toLowerCase().endsWith("gif") || CurFile.getName().toLowerCase().endsWith("jpeg")) {
                foundFiles.add(CurFile);
                Log.d("onCreate","Found file: " + CurFile.toString());
            }
        }

        GridView gridview = (GridView) findViewById(R.id.imageGridView);
        gridview.setAdapter(new ImageAdapter(this, foundFiles));
        Log.d("onCreate","Set adapter");

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(GalleryActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
