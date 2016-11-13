package com.mvhs.personalcloud;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by tyler on 11/12/2016.
 */

public class GalleryActivity extends AppCompatActivity
{
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ArrayList<File> foundFiles = new ArrayList<>();
        File defaultImageDirectory = new File(ImageManager.IMG_PATH);
        File[] files = defaultImageDirectory.listFiles();


        for (File CurFile : files)
        {
            if (CurFile.getName().toLowerCase().endsWith("jpg") || CurFile.getName().toLowerCase().endsWith("png") ||
                    CurFile.getName().toLowerCase().endsWith("gif") || CurFile.getName().toLowerCase().endsWith("jpeg"))
            {
                foundFiles.add(CurFile);
                Log.d("onCreate", "Found file: " + CurFile.toString());
            }
        }

        GridView gridview = (GridView) findViewById(R.id.imageGridView);
        imageAdapter = new ImageAdapter(this, foundFiles);
        gridview.setAdapter(imageAdapter);
        Log.d("onCreate", "Set adapter");

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id)
            {
                Toast.makeText(GalleryActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

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



        ((Button) findViewById(R.id.viewgallery)).setOnClickListener(new View.OnClickListener() {

            String url = "http://ryank3egan.ftp.sh:8080/login_ui.php";
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));

                startActivity(i);
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

                String filePath = LoginActivity.imageManager.getPath(selectedImage, this);
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);

                Log.d(NetworkManager.TAG, filePath);

                try
                {
                    if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png"))
                    {
                        LoginActivity.imageManager.uploadFile(filePath, this);
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

}
