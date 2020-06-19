package com.example.carrental.initial.host;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.carrental.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class ImageUploadActivity extends AppCompatActivity {

    //constant to track image chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;
    Button change,next_image;
    public static Uri filePath;
    ImageView car_image;
    Boolean select_image = false;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        change=findViewById(R.id.change);
        next_image=findViewById(R.id.next_image);
        car_image=findViewById(R.id.car_image);
        relativeLayout=findViewById(R.id.RelativeLayout);


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        next_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_image)
                {
                    startActivity(new Intent(ImageUploadActivity.this,CarFeaturesActivity.class));
                }
                else
                {
                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, "Pzz Select image for the car", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
            }
        });

    }
    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            select_image=true;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                car_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
