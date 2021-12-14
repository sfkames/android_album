package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    public static ArrayList<String> locationTags = new ArrayList<String>();
    public static ArrayList<String> personTags = new ArrayList<String>();
    private AutoCompleteTextView locationTV, personTV;
    private Button searchBtn, returnBtn;
    private Switch andSwitch, orSwitch;
    public ArrayList<Photo> results = new ArrayList<Photo>();
    public ListView listView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        locationTV = (AutoCompleteTextView)
                findViewById(R.id.locationInput);
        personTV = (AutoCompleteTextView)
                findViewById(R.id.personInput);
        ArrayList<String> LTags = fixList(locationTags);
        ArrayList<String> PTags = fixList(personTags);
        ArrayAdapter<String> Ladapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, LTags);
        ArrayAdapter<String> Padapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, PTags);
        locationTV.setAdapter(Ladapter);
        personTV.setAdapter(Padapter);

        searchBtn = (Button)findViewById(R.id.SearchResultsBtn);
        returnBtn = (Button)findViewById(R.id.goBackBtn);

        andSwitch = findViewById(R.id.andSwitch);
        orSwitch = findViewById(R.id.orSwitch);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearch();

            }
        });

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private ArrayList<String> fixList(ArrayList<String> locationTags) {
        ArrayList<String> finalArray = new ArrayList<String>();
        for(int i = 0; i< locationTags.size();i++){
            if(finalArray.indexOf(locationTags.get(i)) == -1){
                finalArray.add(locationTags.get(i));
            }
        }
        return finalArray;
    }

    private void handleSearch() {

        results.clear();
        System.out.println("RS: "+ results.size());
        listView= findViewById(R.id.searchListView);
        //first if both fields are empty- return
        String location = locationTV.getText().toString().trim();
        String Person = personTV.getText().toString().trim();

        System.out.println("locationTextView : " + location);
        if(location.isEmpty() && Person.isEmpty()){
            Toast toast = Toast.makeText(this, "Fields are empty",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if(!location.isEmpty() && !Person.isEmpty()){
            if(!andSwitch.isChecked() && !orSwitch.isChecked()){
                Toast toast = Toast.makeText(this, "Select a combination",Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if(andSwitch.isChecked() && orSwitch.isChecked()){
                Toast toast = Toast.makeText(this, "Select only ONE combination",Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if(andSwitch.isChecked()) {
                ArrayList<Album> albums = Serialize.albums;
                for (int i = 0; i < albums.size(); i++) {
                    ArrayList<Photo> album = albums.get(i).getPhotos();
                    for (int j = 0; j < album.size(); j++) {
                        ArrayList<Tags> photo = album.get(j).getTags();
                        boolean loc = false;
                        boolean per = false;
                        for (int k = 0; k < photo.size(); k++) {
                            if (photo.get(k).tagType.equalsIgnoreCase("location") && photo.get(k).tagName.equalsIgnoreCase(location)) {
                                loc = true;
                            }
                            if (photo.get(k).tagType.equalsIgnoreCase("person") && photo.get(k).tagName.equalsIgnoreCase(Person)) {
                                per = true;
                            }

                        }
                        if (loc && per) {
//                            if (locationTags.indexOf(location) == -1) {
//                                locationTags.add(location);
//                            }
//                            if (personTags.indexOf(Person) == -1) {
//                                personTags.add(Person);
//                            }
                            results.add(album.get(j));
                        }
                    }
                }
            }
            if(orSwitch.isChecked()) {
                ArrayList<Album> albums = Serialize.albums;
                for (int i = 0; i < albums.size(); i++) {
                    ArrayList<Photo> album = albums.get(i).getPhotos();
                    for (int j = 0; j < album.size(); j++) {
                        ArrayList<Tags> photo = album.get(j).getTags();
                        boolean loc = false;
                        boolean per = false;
                        for (int k = 0; k < photo.size(); k++) {
                            if (photo.get(k).tagType.equalsIgnoreCase("location") && photo.get(k).tagName.equalsIgnoreCase(location)) {
                                loc = true;
//                                if (locationTags.indexOf(location) == -1) {
//                                    locationTags.add(location);
//                                }
                                if(results.indexOf(album.get(j)) == -1) {
                                    results.add(album.get(j));
                                }
                            }
                            if (photo.get(k).tagType.equalsIgnoreCase("person") && photo.get(k).tagName.equalsIgnoreCase(Person)) {
                                per = true;
//                                if (personTags.indexOf(Person) == -1) {
//                                    personTags.add(Person);
//                                }
                                if(results.indexOf(album.get(j)) == -1) {
                                    results.add(album.get(j));
                                }
                            }

                        }
                    }
                }
            }
        }else{
            if(andSwitch.isChecked() || andSwitch.isChecked() ){
                Toast toast = Toast.makeText(this, "Uncheck combination(s)",Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            if(!location.isEmpty()){
                ArrayList<Album> albums = Serialize.albums;
                for(int i = 0; i < albums.size(); i++){
                    ArrayList<Photo> album = albums.get(i).getPhotos();
                    for(int j=0; j < album.size(); j++){
                        ArrayList<Tags> photo = album.get(j).getTags();
                        for(int k = 0; k<photo.size(); k++){
                            System.out.println(photo.get(k).tagType + " : " + photo.get(k).tagName);
                            if(photo.get(k).tagType.equalsIgnoreCase("location") && photo.get(k).tagName.equalsIgnoreCase(location)){
                                System.out.println("reached");
//                                if(locationTags.indexOf(location) == -1){
//
//                                }
                                locationTags.add(location);
                                results.add(album.get(j));
                                break;
                            }
                        }

                    }


                }

            }else{
                System.out.println("index: " + personTags.indexOf(Person));
                ArrayList<Album> albums = Serialize.albums;
                for(int i = 0; i < albums.size(); i++){

                    ArrayList<Photo> album = albums.get(i).getPhotos();
                    for(int j=0; j < album.size(); j++){
                        ArrayList<Tags> photo = album.get(j).getTags();
                        for(int k = 0; k<photo.size(); k++){
                            if(photo.get(k).tagType.equalsIgnoreCase("Person") && photo.get(k).tagName.equalsIgnoreCase(Person)){
                                System.out.println("ran "+ personTags.indexOf(Person));
//                                if(personTags.indexOf(Person) == -1) {
//
//                                }
                                personTags.add(Person);
                                results.add(album.get(j));
//                                    System.out.println("added");
                                break;
                            }
                        }

                    }


                }

            }

        }
        if(results.size() == 0) {
            ArrayList<String> listPointer = new ArrayList<String>();
            listPointer.add("No Photos to Display");

            System.out.println(listPointer.get(0));

            //populate ListView
            listView.setAdapter(
                    new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, (List) listPointer)
            );


            listView.setChoiceMode(0);
        }else{
            PhotoAdapter adapter = new PhotoAdapter(getApplicationContext(), results);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }




    }
    public static void loadHints(){
        ArrayList<Album> albums = Serialize.albums;
        for(int i = 0; i < albums.size(); i++){
            ArrayList<Photo> album = albums.get(i).getPhotos();
            for(int j=0; j < album.size(); j++){
                ArrayList<Tags> photo = album.get(j).getTags();
                for(int k = 0; k<photo.size(); k++){
                    if(photo.get(k).tagType.equals("location")){
//                        if(locationTags.indexOf(photo.get(k).tagName) == -1){
//
//                        }
                        locationTags.add(photo.get(k).tagName);
                    }else{
//                        if(personTags.indexOf(photo.get(k).tagName) == -1){
//
//                        }
                        personTags.add(photo.get(k).tagName);
                    }
                }

            }


        }
        System.out.println("L amount: "+ locationTags.size());
        System.out.println("P amount: "+ personTags.size());

    }
}