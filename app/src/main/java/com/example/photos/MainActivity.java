package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button openBtn, createBtn, deleteBtn, renameBtn, searchBtn;
    private EditText renameText;
    private TextView currAlbum;
    public ArrayList<Album> albumList = Serialize.albums;
    public int selectedIndex = -1;
    public AlbumAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //links buttons and onClick Listeners
        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { handleCreate(); }
        });

        deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (albumList.size() == 0) {
                    Toast.makeText(MainActivity.this, "No Albums to Modify", Toast.LENGTH_LONG).show();
                } else {
                    handleDelete();
                }
            }
        });

        renameBtn = findViewById(R.id.renameBtn);
        renameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (albumList.size() == 0) {
                    Toast.makeText(MainActivity.this, "No Albums to Modify", Toast.LENGTH_LONG).show();
                } else {
                    handleRename();
                }
            }
        });

        openBtn = findViewById(R.id.openBtn);
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (albumList.size() == 0) {
                    Toast.makeText(MainActivity.this, "No Albums to Modify", Toast.LENGTH_LONG).show();
                } else {
                    handleOpen();
                }
            }
        });

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearch();
            }
        });

        currAlbum = findViewById(R.id.currAlbum);

        //load albums
        boolean load = false;
        try {
            FileInputStream inputStream = getApplicationContext().openFileInput("albums.txt");
            load = Serialize.load( inputStream );
            System.out.println("Successful load: "+ load);
            Search.loadHints();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            load = false;
            System.out.println("error caught");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            load = false;
        }

//        try {
//            FileOutputStream outputStream = getApplicationContext().openFileOutput("albums.txt", Context.MODE_PRIVATE);
//            Serialize.save(albumList, outputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        //load alvums and populate ListView
        albumList = Serialize.albums;
        populateListView();
    }

    private void handleSearch() {

        if (albumList.size() == 0){
            return;
        }
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);



    }

    private void populateListView() {
        //connect ListView variable to actual ListView in xml
        listView = findViewById(R.id.albumList);
        adapter = new AlbumAdapter(getApplicationContext(), albumList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((p, V, pos, id) -> {
            System.out.println("Pos: "+pos);
            selectedIndex = pos;
            updateCurrAlbum();
        });
        //save with a FileOutputStream to 'albums.txt'
        try {
            FileOutputStream outputStream = getApplicationContext().openFileOutput("albums.txt", Context.MODE_PRIVATE);
            Serialize.save(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void handleCreate(){
        //# of 'new album' names already in list
        int NACheck = 0;
        for (int i = 0; i < albumList.size(); i++) {

            if (albumList.get(i).getName().contains("New Album")) {
                if (NACheck < Integer.parseInt(albumList.get(i).getName().substring(10))) {
                    NACheck = Integer.parseInt(albumList.get(i).getName().substring(10));
                }
            }
        }
        NACheck++;

        //create new album, add to List, and populate list view
        albumList.add(new Album("New Album " + NACheck, new ArrayList<Photo>()));
//        populateListView();

        adapter.notifyDataSetChanged();
        updateCurrAlbum();
        System.out.println(Serialize.albums.size() + "album size");
    }
    private void handleDelete(){
        CharSequence text = "Select Album";
        Toast toast = Toast.makeText(this, text,Toast.LENGTH_SHORT);
        //check if valid index then remove album (after populate listview)
        if(selectedIndex == -1){
            toast.show();
            return; // if nothing selected end this method (return)
        }
        if(selectedIndex >= albumList.size()){
            toast.show();
           return;
        }

        ArrayList<Photo> album = albumList.get(selectedIndex).getPhotos();
        for(int i = 0; i< album.size(); i++){
            Photo photo = album.get(i);
            for(int j = 0; j < photo.getTags().size(); j++){
                if(photo.getTags().get(j).tagType.equalsIgnoreCase("location")){
                    Search.locationTags.remove(photo.getTags().get(j).tagName);
                }else{
                    Search.personTags.remove(photo.getTags().get(j).tagName);
                }
            }
        }

        albumList.remove((selectedIndex));
        selectedIndex = -1;
//        populateListView();
        adapter.notifyDataSetChanged();
        updateCurrAlbum();
    }
    private void handleRename() {
        renameText = findViewById(R.id.renameText);
        String input= renameText.getText().toString().trim();

        if(input.equals("")){
            return;
        }

        if (selectedIndex == -1 || selectedIndex >= albumList.size()){
            return;
        }

        albumList.get(selectedIndex).setName(input);
//        populateListView();
        adapter.notifyDataSetChanged();
        updateCurrAlbum();
        renameText.setText("");
    }

    private void handleOpen() {

        if (selectedIndex == -1 || selectedIndex >= albumList.size()){
            return;
        }
        Intent intent = new Intent(this, PhotosViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("album", selectedIndex);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if(reqCode == 1){
            System.out.println("updated after back");
//            populateListView();
            adapter.notifyDataSetChanged();
            updateCurrAlbum();
        }
    }
    private void updateCurrAlbum(){
        if(albumList.size() == 0 || selectedIndex == -1){
            currAlbum.setText("Nothing Selected");
        }else{
            currAlbum.setText("Selected: \n"+ albumList.get(selectedIndex).getName());
        }
        try {
            FileOutputStream outputStream = getApplicationContext().openFileOutput("albums.txt", Context.MODE_PRIVATE);
            Serialize.save(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}