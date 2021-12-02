package com.example.photos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Serialize implements Serializable {

    public static ArrayList<Album> albums = new ArrayList<Album>();

    public static void writeAlbums(ArrayList<Album> user, FileOutputStream fos) throws IOException {

        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(user);

    }
    public static boolean save(ArrayList<Album> album, FileOutputStream fos) {
        try {
            writeAlbums(album, fos);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ObjectInputStream readAlbums( FileInputStream fos ) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(fos);
        return ois;

    }
    public static boolean load(FileInputStream fos) throws ClassNotFoundException {
        try {
            ObjectInputStream albumsOIS = readAlbums(fos);
            albums = (ArrayList<Album>) albumsOIS.readObject();
            return true;
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }
}

