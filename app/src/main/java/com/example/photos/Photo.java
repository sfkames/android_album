package com.example.photos;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * toString() method returns a formatted String of tag name, tag type on a
 * single line, in a format that can be printed next to its respective Photo
 *
 * @return String of tag type, " : ", tag name
 */

public class Photo implements Serializable {
    private Bitmap image;
    private Calendar dateTaken;
    private ArrayList<Tags> tags;


    public Photo(Bitmap image) {
        this.image = image;

    }

    public Bitmap getImage() {
        return image;
    }

    /**
     * setPath() void method takes a String and sets it to a representation of the
     * respective Photo's path
     *
     * @param path String representation of the Photo file's path
     */

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDateTaken() {

        return "" + dateTaken.get(Calendar.MONTH) + "-" + dateTaken.get(Calendar.DATE) + "-"
                + dateTaken.get(Calendar.YEAR);
    }

    /**
     * getCalendar() returns a Calendar instance comprised of the time that the
     * respective photo was taken
     *
     * @return dateTaken time that the respective Photo was taken
     */

    public Calendar getCalendar() {

        dateTaken.set(Calendar.MILLISECOND, 0);
        dateTaken.set(Calendar.SECOND, 0);
        dateTaken.set(Calendar.MINUTE, 0);
        dateTaken.set(Calendar.HOUR, 0);

        return dateTaken;
    }

    /**
     * setCalendar() takes a Calendar instance and sets it to the corresponding time
     * that the respective Photo was taken
     *
     * @param dateTaken Calendar instance representative of the time that the Photo
     *                  was taken
     */

    public void setDateTaken(Calendar dateTaken) {

        this.dateTaken = dateTaken;
        dateTaken.set(Calendar.MILLISECOND, 0);
    }

    /**
     * getCaption() returns the String of the respective Photo's caption
     *
     * @return caption String of the respective Photo's caption
     */

    public ArrayList<Tags> getTags() {
        return tags;
    }

    /**
     * setTags() takes an ArrayList of tags, including custom tags, and sets it for
     * the respective photo
     *
     * @param tags ArrayList of tags, including custom tags, for the respective
     *             photo
     */

    public void setTags(ArrayList<Tags> tag) {
        tags = tag;
    }

}

