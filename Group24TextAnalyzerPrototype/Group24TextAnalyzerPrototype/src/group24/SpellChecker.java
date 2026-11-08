package group24; // this file belongs to the group24 package

import java.io.BufferedReader; // reads text line by line
import java.io.FileReader; // opens a file for reading
import java.io.IOException; // error type for file problems
import java.util.ArrayList; // list implementation
import java.util.HashSet; // set used for the dictionary (fast lookups)
import java.util.List; // general list type
import java.util.Set; // general set type

/** Flags words that are not in the dictionary file. */
public class SpellChecker implements TextAnalyzer { // implements the TextAnalyzer interface

    private final Set<String> dictionary = new HashSet<>(); // all the valid words
    private final String dictionaryPath; // the location of the dictionary file

    /** Creates the spell checker and loads the dictionary. */
    public SpellChecker(String dictionaryPath) { // constructor, receives the file path
        this.dictionaryPath = dictionaryPath; // remember the path
        loadDictionary(); // read the dictionary file into the set
    } // end of the constructor

    /** Reads the dictionary file into the HashSet. */
    private void loadDictionary() { // private, only called from the constructor
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryPath))) { // open the file, closes automatically
            String line; // holds the current line
            while ((line = reader.readLine()) != null) { // read until the file ends
                line = line.trim().toLowerCase(); // remove spaces and make it lowercase
                if (!line.isEmpty()) { // ignore empty lines
                    dictionary.add(line); // add the word to the dictionary
                } // end of the empty-line check
            } // end of the while loop
        } catch (IOException e) { // runs if the file could not be opened
            System.out.println("Warning: Could not load dictionary file at " + dictionaryPath); // warn the user
        } // end of try-catch
    } // end of loadDictionary

    @Override // this method comes from the TextAnalyzer interface
    public void analyze(String text, AnalysisResult result) { // finds the misspelled words
        List<String> misspelled = new ArrayList<>(); // will hold the wrong words
        if (!dictionary.isEmpty()) { // only check spelling if the dictionary was loaded
            List<String> words = TextUtils.extractWords(text); // get the cleaned lowercase words
            for (int i = 0; i < words.size(); i++) { // go through every word
                String word = words.get(i); // take the current word
                if (!isKnown(word) && !misspelled.contains(word)) { // unknown word that is not already listed
                    misspelled.add(word); // add it to the misspelled list
                } // end of the unknown-word check
            } // end of the for loop
        } // end of the dictionary check
        result.setMisspelled(misspelled); // store the misspelled words in the result
    } // end of analyze

    /** Returns true if the word is in the dictionary (numbers always count as known). */
    private boolean isKnown(String word) { // private helper method
        if (word.matches("\\d+")) { // if the word is only digits
            return true; // numbers are never treated as spelling mistakes
        } // end of the number check
        return dictionary.contains(word); // true if the word exists in the dictionary
    } // end of isKnown

    @Override // this method comes from the TextAnalyzer interface
    public String getName() { // returns the analyzer name
        return "SpellChecker"; // the name of this analyzer
    } // end of getName
} // end of class SpellChecker