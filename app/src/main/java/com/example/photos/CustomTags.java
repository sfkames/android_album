package com.example.photos;
import java.util.ArrayList;

/**
 * CustomTags class is a subclass of Tags class. Used for creating CustomTags
 * objects that can hold multiple values for a tag created by the user. Has
 * overloaded constructor for "person" tag type, as well as a getter and setter
 * method for its ArrayList of String tag values, and a method that formats the
 * tag name and tag types for its respective Photo.
 *
 * @author Ahmed Elgazzar, Samantha Ames
 * @see Tags
 */

// custom tag
public class CustomTags extends Tags {

    public ArrayList<String> tags;

    /**
     * Main constructor to for user to create custom tags that takes Strings for
     * both tag type and first tag value, which is the first value in the ArrayList
     * of Strings for tag values, as well as the ArrayList of String tag values.
     *
     * @param tagType tag type for Photo, i.e. "animals"
     * @param tagName first tag value in the ArrayList of Strings i.e. "donkey"
     * @param tags    ArrayList of String tag values i.e. "donkey, monkey, etc."
     */

    public CustomTags(String tagType, String tagName, ArrayList<String> tags) {

        super(tagType, tags.get(0));
        this.tags = tags;
    }

    /**
     * Overloaded constructor for "person" tag, which takes in ArrayList of Strings
     * for tag values and automatically sets the tag type to "person"
     *
     * @param tags ArrayList of String tag values i.e. "Ahmed, Samantha, etc."
     */

    // person tag
    public CustomTags(ArrayList<String> tags) {
        this("person", tags.get(0), tags);
    }

    /**
     * setCustomTags() void method takes an ArrayList of Strings and sets it as the
     * respective list of tag values for the tag
     *
     * @param tags ArrayList of String tag values i.e. "Ahmed, Samantha, etc."
     */

    public void setCustomTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    /**
     * getCustomTags() method returns ArrayList of String tag values for the
     * respective tag
     *
     * @return ArrayList of String tag values i.e. "Ahmed, Samantha, etc."
     */

    public ArrayList<String> getCustomTags() {
        return tags;
    }

    /**
     * toString() method returns a formatted String of tag name, tag type on a
     * single line, in a format that can be printed next to its respective Photo
     *
     * @return String of tag type, " : ", tag name
     */

    @Override
    public String toString() {
        return String.format(tagType + " : " + tagName);
    }
}
