package group24; // this file belongs to the group24 package

import java.util.ArrayList; // ArrayList is the list type we create
import java.util.List; // List is the general list type we return

/** Helper methods that are shared by the analyzers. */
public class TextUtils { // utility class, we never create objects of it

    /** Cleans punctuation and splits the text into lowercase words. */
    public static List<String> extractWords(String text) { // static, so we call it as TextUtils.extractWords(...)
        List<String> words = new ArrayList<>(); // empty list that will hold the cleaned words
        if (text == null || text.trim().isEmpty()) { // if there is no text at all
            return words; // return the empty list
        } // end of the empty-text check

        // replace every character that is not a letter, digit or whitespace with a space, then lowercase
        String clean = text.replaceAll("[^a-zA-Z0-9\\s]", " ").toLowerCase();
        String[] tokens = clean.split("\\s+"); // split the cleaned text wherever there is whitespace

        for (int i = 0; i < tokens.length; i++) { // go through every token one by one
            String word = tokens[i].trim(); // take the current token without extra spaces
            if (!word.isEmpty()) { // ignore empty tokens
                words.add(word); // add the real word to the list
            } // end of the empty-token check
        } // end of the for loop
        return words; // give the finished list back to the caller
    } // end of extractWords
} // end of class TextUtils