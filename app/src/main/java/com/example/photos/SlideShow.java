package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class SlideShow extends AppCompatActivity {

    public ImageView imageView;
    Button backBtn, leftBtn, rightBtn;
    public ArrayList<Photo> album = new ArrayList<Photo>();
    private int albumIndex = -1;
    private int photoIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        backBtn=(Button)findViewById(R.id.Back);
        leftBtn=(Button)findViewById(R.id.leftBtn);
        rightBtn=(Button)findViewById(R.id.rightBtn);
        imageView = (ImageView)findViewById(R.id.imageView);

        Intent intent = getIntent();

        albumIndex = intent.getIntExtra("album", -1);

        album = Serialize.albums.get(albumIndex).getPhotos();

        imageView.setImageBitmap(PhotosViewActivity.byteToBitmap(album.get(0)));

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((photoIndex-1) > -1) {
                    photoIndex--;
                }
                else {
                    photoIndex = album.size() - 1;
                }
                setImageView();
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((photoIndex+1) < album.size()) {
                    photoIndex++;
                }
                else {
                    photoIndex = 0;
                }
                setImageView();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void setImageView() {
        imageView.setImageBitmap(PhotosViewActivity.byteToBitmap(album.get(photoIndex)));
    }

}