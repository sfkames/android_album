package com.example.photos;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Album class contains String Name, ArrayList<Photo> Photos, int numOfPhotos,
 * SimpleDateFormat oldestDate, SimpleDateFormat newestDate, String coverpath as
 * attributes. Contains constructor for Album object instance as well as getter
 * and setter methods for each attribute.
 *
 * Album contains an arraylist of Photos and implements Serializable so that
 * photos and corresponding information are maintained throughout different
 * sessions.
 *
 * @author Ahmed Elgazzar, Samantha Ames
 */

public class Album implements Serializable {
    private String Name;
    private ArrayList<Photo> Photos;
    private int numOfPhotos;
    private SimpleDateFormat oldestDate;
    private SimpleDateFormat newestDate;
    private String coverPath;

    /**
     * Album constructor creates an instance of Album.
     *
     * @param name   user inputs name of Album
     * @param photos ArrayList of photos that are contained within the album
     * @see Photo
     */
    public Album(String name, ArrayList<Photo> photos) {
        Name = name;
        Photos = photos;
        if (photos != null) {
            if (photos.size() != 0) {
                this.coverPath = photos.get(0).getPath();
            }
        }else{
            Photos = new ArrayList<Photo>();
        }

    }

    /**
     * updateCoverPath() void method maintains the String path of the first Photo in
     * the Photos ArrayList so that the first photo is displayed as the Album's
     * cover photo in the AlbumPage scene.
     *
     * @see Photos
     */

    public void updateCoverPath() {
        if (Photos != null) {
            if (Photos.size() > 0) {
                if (Photos.get(0).getPath() != null) {
                    this.coverPath = Photos.get(0).getPath();
                }
            } else {
                this.coverPath = null;
            }
        }
    }

    /**
     * getName() returns the String Name of the Album.
     *
     * @return Name of the specified Album
     */

    public String getName() {
        return Name;
    }

    /**
     * setName() void method takes in String name as parameter and sets as Name of
     * specified Album
     *
     * @param name name of specified Album
     */

    public void setName(String name) {
        Name = name;
    }

    /**
     * getPhotos() returns the ArrayList of Photos of the Album.
     *
     * @return ArrayList of Photos of the specified Album
     */

    public ArrayList<Photo> getPhotos() {
        return Photos;
    }

    /**
     * setPhotos() void method takes in ArrayList of Photo as parameter and sets as
     * the photos for specified Album
     *
     * @param photos name of specified Album
     */

    public void setPhotos(ArrayList<Photo> photos) {
        Photos = photos;
    }

    /**
     * getNumOfPhotos() returns the int for the number of photos in the Album.
     *
     * @return number of Photos in the specified Album
     */

    public int getNumOfPhotos() {
        return numOfPhotos;
    }

    /**
     * setNumOfPhotos() void method takes in int number of Photos in Photo ArrayList
     * as parameter and sets as the number of photos for specified Album
     *
     * @param photos number of photos in specified Album
     */

    public void setNumOfPhotos(int numOfPhotos) {
        this.numOfPhotos = numOfPhotos;
    }

    /**
     * getOldestDate() returns the SimpleDateFormat for the photo in the Album with
     * the oldest corresponding date
     *
     * @return the date of the oldest photo in the Album
     */

    public SimpleDateFormat getOldestDate() {
        return oldestDate;
    }

    /**
     * setOldestDate() takes in the SimpleDateFormat for the photo in the Album with
     * the oldest corresponding date and sets it for the specified Album
     *
     * @param the date of the oldest photo in the Album
     */
    public void setOldestDate(SimpleDateFormat oldestDate) {
        this.oldestDate = oldestDate;
    }

    /**
     * getNewestDate() returns the SimpleDateFormat for the photo in the Album with
     * the newest corresponding date
     *
     * @return the date of the newest photo in the Album
     */

    public SimpleDateFormat getNewestDate() {
        return newestDate;
    }

    /**
     * setNewestDate() takes in the SimpleDateFormat for the photo in the Album with
     * the newest corresponding date and sets it for the specified Album
     *
     * @param the date of the newest photo in the Album
     */
    public void setNewestDate(SimpleDateFormat newestDate) {
        this.newestDate = newestDate;
    }

    /**
     * getCoverPath() returns the String path of the first photo in the Album to use
     * as the cover photo for the Album's representation in the application
     *
     * @return the path of the first photo in the Photo ArrayList of the Album
     */

    public String getCoverPath() {
        return coverPath;
    }

    /**
     * setCoverPath() takes in the String for the path of the first photo in the
     * Album's Photo ArrayList and sets it for the specified Album
     *
     * @param the date of the oldest photo in the Album
     */
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public static Album emptyAlbum(){
        ArrayList<Photo>  photos = new ArrayList<Photo>();
        photos.add(new Photo("res" + File.separator+"drawable"+File.separator+"question_mark.jpeg"));
        Album emptyAlbum = new Album("No Albums",photos);
        return emptyAlbum;
    }

}

