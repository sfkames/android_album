package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class TagActivity extends AppCompatActivity {

    Button returnBtn, addTagBtn;
    private EditText personText, locationText;
    public ArrayList<Album> albums = new ArrayList<Album>();
    public ArrayList<Photo> album = new ArrayList<Photo>();
    public ArrayList<Tags> tags = new ArrayList<Tags>();
    public int selectedIndex = -1;
    private boolean duplicate = false;
    private Photo photo = new Photo(null, null, 0, 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        configureNextButton();
        Intent intent = getIntent();
        addTagBtn=(Button)findViewById(R.id.addTagBtn);
        returnBtn = (Button)findViewById(R.id.returnBtn);

        int albumIndex = intent.getIntExtra("album", -1);
        int photoIndex = intent.getIntExtra("photo", -1);


        albums = Serialize.albums;
        album = albums.get(albumIndex).getPhotos();
        photo = album.get(photoIndex);

        addTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePersonTag();
                handleLocationTag();
            }
        });
    }

    private void handlePersonTag() {
        ArrayList<Tags> tagList = photo.getTags();
        personText = findViewById(R.id.PersonText);
        String input= personText.getText().toString().trim();

        if(input.equals("")){
            return;
        }

        for (int i = 0; i < tagList.size(); i++) {
            Tags tag = tagList.get(i);
            if (tag instanceof CustomTags) {
                CustomTags custom = (CustomTags) tag;
                if (tag.getTagType().equalsIgnoreCase("person")) {

                    ArrayList<String> people = new ArrayList<>();

                    for (int j = 0; j < custom.getCustomTags().size(); j++) {
                        people = custom.getCustomTags();
                        if(people.get(j).equalsIgnoreCase(input)) {
                            duplicate = true;
                            Toast.makeText(TagActivity.this, "Duplicate Tag",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
        if (!duplicate) {
            ArrayList<String> cusTags = new ArrayList<>();
            cusTags.add(input);
            CustomTags newTag = new CustomTags(cusTags);
            tagList.add(newTag);
            photo.setTags(tagList);
            Toast.makeText(TagActivity.this, "Tag Successfully Added",Toast.LENGTH_LONG).show();
        }
        personText.setText("");
        System.out.println("Reached");
    }

    private void handleLocationTag() {
        ArrayList<Tags> tagList = photo.getTags();
        locationText = findViewById(R.id.LocationText);
        String input= locationText.getText().toString().trim();

        if(input.equals("")){
            return;
        }
        for(int i = 0; i < tagList.size(); i++) {
            if(tagList.get(i).getTagName().equalsIgnoreCase(input)) {
                duplicate = true;
                Toast.makeText(TagActivity.this, "Duplicate Tag",Toast.LENGTH_LONG).show();
            }
        }
        if (!duplicate) {
            Tags newTag = new Tags(input);
            tagList.add(newTag);
            photo.setTags(tagList);
            Toast.makeText(TagActivity.this, "Tag Successfully Added",Toast.LENGTH_LONG).show();
        }
        locationText.setText("");
    }

    private void configureNextButton() {

        Button returnBtn = (Button) findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}