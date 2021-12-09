package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button openBtn, createBtn, deleteBtn, renameBtn;
    private EditText renameText;
    public ArrayList<Album> albumList = Serialize.albums;
    public int selectedIndex = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //links buttons and onClick Listeners
        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCreate();
            }
        });

        deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDelete();
            }
        });

        renameBtn = findViewById(R.id.renameBtn);
        renameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRename();
            }
        });

        openBtn = findViewById(R.id.openBtn);
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOpen();
            }
        });

        //load albums
        boolean load = false;
        try {
            FileInputStream inputStream = getApplicationContext().openFileInput("albums.txt");
            load = Serialize.load( inputStream );
            System.out.println("Successful load: "+ load);

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



    private void populateListView() {
        //connect ListView variable to actual ListView in xml
        listView = findViewById(R.id.albumList);

        //check if albumList is Empty and create pointer to Album -- or if not empty use albumAdapter
        if(albumList.size() == 0) {
            ArrayList<String> listPointer = new ArrayList<String>();
            listPointer.add(Album.emptyAlbum().getName());
            //populate ListView
            listView.setAdapter(
                    new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, (List) listPointer)
            );
            listView.setChoiceMode(0);
        }else{
            AlbumAdapter adapter = new AlbumAdapter(getApplicationContext(), albumList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((p, V, pos, id) -> {
                System.out.println("Pos: "+pos);
                selectedIndex = pos;
            });
        }

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
        populateListView();
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
        albumList.remove((selectedIndex));
        selectedIndex = -1;
        populateListView();

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
        populateListView();
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
        startActivity(intent);



    }
}