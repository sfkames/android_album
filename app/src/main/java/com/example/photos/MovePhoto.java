package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MovePhoto extends AppCompatActivity {

    private ListView listView;
    TextView textView;
    Button moveBtn, returnBtn;
    public ArrayList<Album> albumList = Serialize.albums;
    public ArrayList<Photo> album = new ArrayList<Photo>();
    private int selectedIndex = -1;
    private int albumIndex = -1;
    private int photoIndex = -1;
    private Photo photo = new Photo(null, null, 0, 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_photo);

        Intent intent = getIntent();
        albumIndex = intent.getIntExtra("album", -1);
        photoIndex = intent.getIntExtra("photo", -1);

        album = albumList.get(albumIndex).getPhotos();
        photo = album.get(photoIndex);
        returnBtn = findViewById(R.id.button);
        moveBtn = findViewById(R.id.moveBtn);

        moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movePhoto();
            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        populateListView();
    }

    void movePhoto() {

        System.out.println("Album Index: " +albumIndex);
        System.out.println("Photo Index: " +photoIndex);

        if (selectedIndex == -1) {
            //toast
            return;
        }
        String albumName = (String) textView.getText();
        int index = -1;
        for(int i = 0; i < albumList.size(); i++) {
            if(albumList.get(i).getName().equalsIgnoreCase(albumName)) {
                index = i;
                break;
            }
        }
        if(index != -1) {
            albumList.get(index).getPhotos().add(albumList.get(albumIndex).getPhotos().get(photoIndex));
            albumList.get(albumIndex).getPhotos().remove(photoIndex);
            //toast
            System.out.println("reached");
        }
    }

    void populateListView() {

        listView = findViewById(R.id.albumListView);
        textView = findViewById(R.id.albumTextView);

        if(albumList.size() == 0) {
            ArrayList<String> listPointer = new ArrayList<String>();
            listPointer.add(Album.emptyAlbum().getName());
            //populate ListView
            listView.setAdapter(
                    new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, (List) listPointer)
            );
            listView.setChoiceMode(0);
            textView.setText("Must Create a New Album");
        }else{
            System.out.println(albumIndex);
            ArrayList<Album> modifiedAlbums = new ArrayList<Album>();
            for(int i = 0; i < albumList.size(); i++) {
                if (i != albumIndex) {
                    modifiedAlbums.add(albumList.get(i));
                }
            }
            AlbumAdapter adapter = new AlbumAdapter(getApplicationContext(), modifiedAlbums);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((p, V, pos, id) -> {
                TextView textView2 = (TextView) V.findViewById(R.id.album_name);
                selectedIndex = pos;
                textView.setText(textView2.getText());
            });

        }


    }
}