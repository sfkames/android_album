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

public class AlbumAdapter extends ArrayAdapter<Album> {
    public ArrayList<Album> albumList;
    private Context context;

    public AlbumAdapter(@NonNull Context context, ArrayList<Album> albumList) {
        super(context,0, albumList);
        this.context = context;
        this.albumList=albumList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
             convertView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        }
        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail) ;
        thumbnail.setImageResource((int) R.drawable.question_mark);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.album_name);
        String AlbumName = albumList.get(position).getName();
        titleTextView.setText(AlbumName);
        return convertView;
    }
}
