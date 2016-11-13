package com.mvhs.personalcloud;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

public class FullScreenImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        ImageView imageView = (ImageView) findViewById(R.id.full_screen_image);
        if(getIntent().hasExtra("imageLocation"))
        {
            String path = getIntent().getStringExtra("imageLocation");
            Log.d("FullScreenImage", path);
            path = path.substring(1);
            Log.d("FullScreenImage", path);
            File image = new File(path, path.substring(path.lastIndexOf("/")+1));
//            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//            Bitmap bitmap = BitmapFactory.decodeFile(path,bmOptions);
            Bitmap bitmap = BitmapFactory.decodeFile(path);


            imageView.setImageBitmap(bitmap);

        }

    }
}
