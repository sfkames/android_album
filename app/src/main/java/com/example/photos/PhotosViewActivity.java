package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PhotosViewActivity extends AppCompatActivity {

    ImageView imageView;
    Button addBtn;
    Button removeBtn;
    Button tagBtn;
    Button slideBtn;
    Button backBtn;
    TextView textView;
    private static int RESULT_LOAD_IMG = 1;
    public ArrayList<Photo> album = new ArrayList<Photo>();
    public ArrayList<Tags> tags = new ArrayList<Tags>();
    private ListView listView;
    private int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_view);
        populateListView();
        configureNextButton();
        Intent intent = getIntent();

        int albumIndex = intent.getIntExtra("album", -1);
        System.out.println("album index" + albumIndex);

        album = Serialize.albums.get(albumIndex).getPhotos();

        imageView=(ImageView)findViewById(R.id.imageView);
        addBtn=(Button)findViewById(R.id.addBtn);
        removeBtn = (Button)findViewById(R.id.removeBtn);
        tagBtn = (Button)findViewById(R.id.tagBtn);
        slideBtn = (Button)findViewById(R.id.slideBtn);
        backBtn = (Button)findViewById(R.id.backBtn);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectImage();

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()){
//            @Override
//            public void onItemClick(AdapterView<?> adapter, View v, int position) {
//                ItemClicked item = adapter.getItemAtPosition(position);
//
//                Intent intent = new Intent(Activity.this, destinationActivity.class);
//                startActivity(intent);
//            }
//        };

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPickerIntent.setType("image/*");
//                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                getImageFromAlbum();
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRemove();
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
                album.add(new Photo(selectedImage));
                populateListView();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(PhotosViewActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(PhotosViewActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void getImageFromAlbum(){
        try{
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMG);
        }catch(Exception exp){
            Log.i("Error",exp.toString());
        }
    }

    private void handleRemove(){
        CharSequence text = "Select Photo";
        Toast toast = Toast.makeText(this, text,Toast.LENGTH_SHORT);
        //check if valid index then remove album (after populate listview)
        if(selectedIndex == -1){
            toast.show();
            return; // if nothing selected end this method (return)
        }
        if(selectedIndex >= album.size()){
            toast.show();
            return;
        }
        album.remove((selectedIndex));
        selectedIndex = -1;
        populateListView();
        if(album.size() == 0) {
            imageView.setImageBitmap(null);
        }
        else {
            imageView.setImageBitmap(album.get(0).getImage());
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.photos_view);
//        populateListView();
//    }

    private void populateListView() {

        //connect ListView variable to actual ListView in xml
        listView = findViewById(R.id.photoList);
        //check if albumList is Empty and create pointer to Album

        if(album.size() == 0) {
            ArrayList<String> listPointer = new ArrayList<String>();
            listPointer.add(Album.emptyAlbum().getName());

            System.out.println(listPointer.get(0));

            //populate ListView
            listView.setAdapter(
                    new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, (List) listPointer)
            );

            listView.setChoiceMode(0);


        }else{
            com.example.photos.PhotoAdapter adapter = new com.example.photos.PhotoAdapter(getApplicationContext(), album);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((p, V, pos, id) -> {
                        selectedIndex = pos;
                        imageView.setImageBitmap(album.get(pos).getImage());
                    }
            );


        }
        try {
            FileOutputStream outputStream = getApplicationContext().openFileOutput("albums.txt", Context.MODE_PRIVATE);
            Serialize.save(Serialize.albums, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void configureNextButton() {

        Button tagBtn = (Button) findViewById(R.id.tagBtn);
        tagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PhotosViewActivity.this, TagActivity.class));
            }
        });
    }
}

