package com.example.photos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PhotoAdapter extends ArrayAdapter<Photo> {
    public ArrayList<Photo> album;
    private Context context;

    public PhotoAdapter(@NonNull Context context, ArrayList<Photo> album) {
        super(context,0, album);
        this.context = context;
        this.album=album;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.photo_item,parent,false);
        }
        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail) ;
        thumbnail.setImageBitmap(album.get(position).getImage());
        return convertView;
    }
}
