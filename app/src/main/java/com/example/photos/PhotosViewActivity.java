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
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PhotosViewActivity extends AppCompatActivity {

    ImageView imageView;
    Button addBtn, removeBtn, tagBtn, slideBtn, backBtn, editTagBtn;
    TextView textView;
    private static int RESULT_LOAD_IMG = 1;
    public ArrayList<Photo> album = new ArrayList<Photo>();
    public ArrayList<Tags> tags = new ArrayList<Tags>();
    private ListView listView;
    private int selectedIndex = -1;
    private int albumIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_view);
        configureNextButton();
        Intent intent = getIntent();


        albumIndex = intent.getIntExtra("album", -1);


        album = Serialize.albums.get(albumIndex).getPhotos();
        populateListView();

        imageView=(ImageView)findViewById(R.id.imageView);
        addBtn=(Button)findViewById(R.id.addBtn);
        removeBtn = (Button)findViewById(R.id.removeBtn);
        tagBtn = (Button)findViewById(R.id.tagBtn);
        slideBtn = (Button)findViewById(R.id.slideBtn);
        backBtn = (Button)findViewById(R.id.backBtn);
        listView = findViewById(R.id.photoList);
        textView = findViewById(R.id.textView);
        editTagBtn = (Button) findViewById(R.id.editTagBtn);

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

        editTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotosViewActivity.this, EditTagActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("album", albumIndex);
                bundle.putInt("photo", selectedIndex);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        slideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotosViewActivity.this, SlideShow.class);
                Bundle bundle = new Bundle();
                bundle.putInt("album", albumIndex);
                bundle.putInt("photo", selectedIndex);
                intent.putExtras(bundle);
                if(album.size() == 0) {
                    Toast.makeText(PhotosViewActivity.this, "No Photos to Display", Toast.LENGTH_LONG).show();
                }
                else {
                    startActivity(intent);
                }
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
                Bitmap bitmap = selectedImage;


                int size = bitmap.getRowBytes() * bitmap.getHeight();
                ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                bitmap.copyPixelsToBuffer(byteBuffer);
                byte[] byteArray = byteBuffer.array();

                album.add(new Photo(byteArray,bitmap.getConfig().name(),bitmap.getWidth(),bitmap.getHeight()));

                populateListView();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(PhotosViewActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(PhotosViewActivity.this, "You haven't picked an Image",Toast.LENGTH_LONG).show();
        }
    }

    private void getImageFromAlbum(){
        textView = findViewById(R.id.textView);
        try{
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMG);
        }catch(Exception exp){
            Log.i("Error",exp.toString());
        }
        textView.setText("No Tags to Display");
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
            imageView.setImageBitmap(byteToBitmap(album.get(0)));
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
        textView = findViewById(R.id.textView);
        //check if albumList is Empty and create pointer to Album

        if(album.size() == 0) {
            ArrayList<String> listPointer = new ArrayList<String>();
            listPointer.add("No Photos to Display");

            System.out.println(listPointer.get(0));

            //populate ListView
            listView.setAdapter(
                    new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, (List) listPointer)
            );

            listView.setChoiceMode(0);
            textView.setText("No Tags to Display");


        }else{
            com.example.photos.PhotoAdapter adapter = new com.example.photos.PhotoAdapter(getApplicationContext(), album);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((p, V, pos, id) -> {
                selectedIndex = pos;
                imageView.setImageBitmap(byteToBitmap(album.get(pos)));

                String locationTag= "Location: ";
                        String personTag = "Person: ";

                        if(album.get(pos).getTags() != null) {
                            if (album.get(pos).getTags().size() != 0) {
                                ArrayList<Tags> tagList = album.get(pos).getTags();
                                for (int i = 0; i < tagList.size(); i++) {
                                    if (tagList.get(i).getTagType().equalsIgnoreCase("location")) {
                                        locationTag = locationTag + tagList.get(i).getTagName() + " ";
                                    } else {
                                        personTag = personTag + tagList.get(i).getTagName() + "\n";
                                    }
                                }
                                textView.setText(locationTag + "\n" + personTag);
                            } else {
                                textView.setText("No Tags to Display");
                            }
                        } else {
                            textView.setText("No Tags to Display");
                        }
                    }
            );


        }
        try {
            FileOutputStream outputStream = getApplicationContext().openFileOutput("albums.txt", Context.MODE_PRIVATE);
            Serialize.save(outputStream);
            System.out.println("Saved with size" + Serialize.albums.get(0).getPhotos().size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void configureNextButton() {

        Button tagBtn = (Button) findViewById(R.id.tagBtn);
        tagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotosViewActivity.this, TagActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("album", albumIndex);
                bundle.putInt("photo", selectedIndex);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public static Bitmap byteToBitmap(Photo photo){

        Bitmap.Config configBmp = Bitmap.Config.valueOf(photo.name);
        Bitmap bitmap_tmp = Bitmap.createBitmap(photo.width, photo.height, configBmp);
        ByteBuffer buffer = ByteBuffer.wrap(photo.getImage());
        bitmap_tmp.copyPixelsFromBuffer(buffer);
        return bitmap_tmp;
    }

}

