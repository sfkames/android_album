package com.example.photos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EditTagActivity extends AppCompatActivity {

    ListView listView;
    Button deleteTagBtn, prevBtn;
    private Photo photo = new Photo(null, null, 0, 0);
    public ArrayList<Tags> tagList = new ArrayList<Tags>();
    public ArrayList<String> tags = new ArrayList<String>();
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tag);
        Intent intent = getIntent();
        int albumIndex = intent.getIntExtra("album", -1);
        int photoIndex = intent.getIntExtra("photo", -1);
        System.out.println(Serialize.albums.size() + "album size in edit");

        if(photoIndex == -1) {
            photoIndex = 0;
        }
        photo = Serialize.albums.get(albumIndex).getPhotos().get(photoIndex);
        tagList = photo.getTags();
        System.out.println("album index" + albumIndex);
        System.out.println("album index" + Serialize.albums.size());
        System.out.println("photo size " + photo.getTags().size());
        populateListView();


        deleteTagBtn=(Button)findViewById(R.id.deleteTagBtn);
        prevBtn = (Button)findViewById(R.id.prevBtn);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        deleteTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRemove();
            }
        });
    }

    void handleRemove() {
        System.out.println("print in handleRemove() " + tagList.size());
        if(selectedIndex != -1) {
            if (tagList.get(selectedIndex).getTagType().equalsIgnoreCase("location")) {
                Search.locationTags.remove(tagList.get(selectedIndex).getTagName());
                System.out.println("removed: location tag");
            }
            else if(tagList.get(selectedIndex).getTagType().equalsIgnoreCase("person")) {
                Search.personTags.remove(tagList.get(selectedIndex).getTagName());
                System.out.println("removed: person tag");
            }
            tagList.remove(selectedIndex);
            if(tagList.size() == 0) {
                selectedIndex = -1;
            }
            populateListView();
        }
    }


    void populateListView() {
        listView = findViewById(R.id.listView);

        if (tagList.size() == 0) {
            System.out.println("taglist = 0");
            ArrayList<String> listPointer = new ArrayList<String>();
            listPointer.add("No Tags");
            //populate ListView
            listView.setAdapter(
                    new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, (List) listPointer)
            );
            listView.setChoiceMode(0);
        } else {
            tags.clear();
            ArrayList<String> listPointer = tags;
            for (int i = 0; i < tagList.size(); i++) {
                listPointer.add(tagList.get(i).getTagType() + " : " + tagList.get(i).getTagName());
            }
            listView.setAdapter(
                    new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, (List) listPointer)
            );
            listView.setOnItemClickListener((p, V, pos, id) -> {
                selectedIndex = pos;
            });
            listView.setChoiceMode(0);
        }
    }
}
