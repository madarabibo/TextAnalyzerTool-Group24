package group24; // this file belongs to the group24 package

import java.util.ArrayList; // list implementation
import java.util.HashMap; // map used for counting words
import java.util.LinkedHashMap; // map that keeps the sorted order
import java.util.List; // general list type
import java.util.Map; // general map type

/** Counts how many times each word occurs and stores the counts, most frequent first. */
public class WordFrequencyAnalyzer implements TextAnalyzer { // implements the TextAnalyzer interface

    private Map<String, Integer> counts = new HashMap<>(); // word -> number of times it appears

    @Override // this method comes from the TextAnalyzer interface
    public void analyze(String text, AnalysisResult result) { // does the counting
        List<String> words = TextUtils.extractWords(text); // get the cleaned lowercase words
        counts = new HashMap<>(); // start with a fresh empty map for every analysis

        for (int i = 0; i < words.size(); i++) { // go through every word
            String word = words.get(i); // take the current word
            if (counts.containsKey(word)) { // if we have seen this word before
                counts.put(word, counts.get(word) + 1); // add 1 to its count
            } else { // if this is the first time we see it
                counts.put(word, 1); // start its count at 1
            } // end of the if-else
        } // end of the for loop
        result.setWordFrequencies(sortByCount()); // store the counts in the result, sorted
    } // end of analyze

    /** Returns the n most frequent words as "word: count" text. */
    public List<String> topN(int n) { // n = how many words we want
        List<String> top = new ArrayList<>(); // will hold the answer
        for (Map.Entry<String, Integer> entry : sortByCount().entrySet()) { // go through the sorted words
            if (top.size() >= n) { // if we already have enough words
                break; // stop the loop
            } // end of the size check
            top.add(entry.getKey() + ": " + entry.getValue()); // add "word: count" to the answer
        } // end of the for loop
        return top; // return the top-n list
    } // end of topN

    /** Sorts the words by count, highest first (ties are sorted alphabetically). */
    private Map<String, Integer> sortByCount() { // private, only used inside this class
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(counts.entrySet()); // copy the entries so we can sort them
        entries.sort((a, b) -> { // compare two entries a and b
            int byCount = Integer.compare(b.getValue(), a.getValue()); // bigger counts come first
            return byCount != 0 ? byCount : a.getKey().compareTo(b.getKey()); // if the counts are equal, sort alphabetically
        }); // end of the sort
        Map<String, Integer> sorted = new LinkedHashMap<>(); // this map remembers the order we add things
        for (int i = 0; i < entries.size(); i++) { // go through the sorted entries
            sorted.put(entries.get(i).getKey(), entries.get(i).getValue()); // add each one in order
        } // end of the for loop
        return sorted; // return the sorted map
    } // end of sortByCount

    @Override // this method comes from the TextAnalyzer interface
    public String getName() { // returns the analyzer name
        return "WordFrequencyAnalyzer"; // the name of this analyzer
    } // end of getName
} // end of class WordFrequencyAnalyzer