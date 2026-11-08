package group24; // this file belongs to the group24 package

import java.io.Serializable; // lets the object be saved to a binary file
import java.util.ArrayList; // list implementation used for the default value
import java.util.LinkedHashMap; // map that keeps its insertion order
import java.util.List; // general list type
import java.util.Map; // general map type

/** Holds everything produced by one analysis run. */
@SuppressWarnings("serial") // hides a harmless compiler warning about the Map and List fields
public class AnalysisResult implements Serializable { // Serializable allows ReportGenerator.saveObject()

    private static final long serialVersionUID = 1L; // version number used when saving the object

    private int characterCountWithSpaces; // number of characters including spaces
    private int characterCountNoSpaces; // number of characters excluding spaces
    private int wordCount; // total number of words
    private int sentenceCount; // total number of sentences
    private Map<String, Integer> wordFrequencies = new LinkedHashMap<>(); // word -> how many times it appears
    private List<String> misspelled = new ArrayList<>(); // words that were not found in the dictionary
    private String sentiment = "Neutral (0)"; // sentiment text such as "Positive (+2)"

    public int getCharacterCountWithSpaces() { return characterCountWithSpaces; } // returns the count with spaces
    public int getCharacterCountNoSpaces() { return characterCountNoSpaces; } // returns the count without spaces
    public int getWordCount() { return wordCount; } // returns the word count
    public int getSentenceCount() { return sentenceCount; } // returns the sentence count
    public Map<String, Integer> getWordFrequencies() { return wordFrequencies; } // returns the frequency map
    public List<String> getMisspelled() { return misspelled; } // returns the misspelled words
    public String getSentiment() { return sentiment; } // returns the sentiment text

    public void setCharacterCountWithSpaces(int count) { this.characterCountWithSpaces = count; } // stores the count with spaces
    public void setCharacterCountNoSpaces(int count) { this.characterCountNoSpaces = count; } // stores the count without spaces
    public void setWordCount(int wordCount) { this.wordCount = wordCount; } // stores the word count
    public void setSentenceCount(int sentenceCount) { this.sentenceCount = sentenceCount; } // stores the sentence count
    public void setWordFrequencies(Map<String, Integer> frequencies) { this.wordFrequencies = frequencies; } // stores the frequency map
    public void setMisspelled(List<String> misspelled) { this.misspelled = misspelled; } // stores the misspelled words
    public void setSentiment(String sentiment) { this.sentiment = sentiment; } // stores the sentiment text

    /** Returns a one-line summary (the full report is built by ReportGenerator). */
    @Override // tells the compiler we are replacing the toString() of Object
    public String toString() { // called automatically when the object is printed
        return "AnalysisResult[words=" + wordCount // start of the summary with the word count
                + ", sentences=" + sentenceCount // add the sentence count
                + ", misspelled=" + misspelled.size() // add how many misspelled words were found
                + ", sentiment=" + sentiment + "]"; // add the sentiment and close the summary
    } // end of toString
} // end of class AnalysisResult