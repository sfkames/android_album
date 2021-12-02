package com.example.photos;

import java.io.Serializable;

/**
 * Tags class represents Tag objects with attributes of Strings for the tag type
 * and tag name, and implements Serializable so that the tags remain associated
 * to their respective photos across sessions. Includes constructor to
 * instantiate Tag objects, and second constructor for location tags. Also
 * includes getter and setter methods for each attribute. Is the superclass for
 * CustomTags
 *
 * @author Ahmed Elgazzar, Samantha Ames
 * @see CustomTags
 */

public class Tags implements Serializable {

    public String tagType;
    public String tagName;

    /**
     * Main constructor for Tags class, which takes in Strings for tag type and tag
     * name
     *
     * @param tagType Type of tag, i.e. "location"
     * @param tagName Name of tag value, i.e. "New Brunswick"
     */

    public Tags(String tagType, String tagName) {

        this.tagType = tagType;
        this.tagName = tagName;
    }

    /**
     * getTagType() method returns the String for the tags corresponding type
     *
     * @return tagType Type of tag, i.e. "location"
     */

    public String getTagType() {
        return tagType;
    }

    /**
     * getTagName() method returns the String for the tags corresponding value name
     *
     * @return tagName Name of tag value, i.e. "New Brunswick"
     */

    public String getTagName() {
        return tagName;
    }

    /**
     * Overloaded Tags constructor represents "location" tags. Takes in input of
     * String for tagName while automatically setting tagType to "location"
     *
     * @param tagName Name of tag value, i.e. "New Brunswick"
     */

    // location tag
    public Tags(String tagName) {
        this("location", tagName);
    }

    /**
     * toString() method returns a String that is formatted to take the tag type and
     * tag name, with a colon in the center so that these can be printed on one line
     * next to their corresponding Photo.
     *
     * @return String tag type, " : ", tag name
     */

    @Override
    public String toString() {
        return String.format(tagType + " : " + tagName);
    }
}

