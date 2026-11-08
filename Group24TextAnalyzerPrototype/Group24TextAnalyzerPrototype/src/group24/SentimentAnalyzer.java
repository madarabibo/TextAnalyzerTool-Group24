package group24; // this file belongs to the group24 package

import java.util.Arrays; // used to build the word lists
import java.util.HashSet; // set implementation (fast lookups)
import java.util.List; // general list type
import java.util.Set; // general set type

/** Lexical sentiment analysis: score = positive words minus negative words. */
public class SentimentAnalyzer implements TextAnalyzer { // implements the TextAnalyzer interface

    // the words that count as positive
    private final Set<String> positive = new HashSet<>(Arrays.asList(
            "good", "great", "excellent", "happy", "best", "love", "nice",
            "positive", "wonderful", "clean", "fast", "efficient", "success"));

    // the words that count as negative
    private final Set<String> negative = new HashSet<>(Arrays.asList(
            "bad", "terrible", "poor", "sad", "worst", "hate", "ugly",
            "negative", "horrible", "slow", "failure", "error", "problem"));

    @Override // this method comes from the TextAnalyzer interface
    public void analyze(String text, AnalysisResult result) { // calculates the sentiment
        int score = score(text); // positive minus negative
        if (score > 0) { // more positive words than negative words
            result.setSentiment("Positive (+" + score + ")"); // e.g. Positive (+2)
        } else if (score < 0) { // more negative words than positive words
            result.setSentiment("Negative (" + score + ")"); // e.g. Negative (-1), the minus sign is part of the number
        } else { // the same number of both
            result.setSentiment("Neutral (0)"); // neutral
        } // end of the if-else chain
    } // end of analyze

    /** Returns the number of positive words minus the number of negative words. */
    private int score(String text) { // private helper method
        List<String> words = TextUtils.extractWords(text); // get the cleaned lowercase words
        int posCount = 0; // counts the positive words
        int negCount = 0; // counts the negative words

        for (int i = 0; i < words.size(); i++) { // go through every word
            String word = words.get(i); // take the current word
            if (positive.contains(word)) { // if it is a positive word
                posCount++; // add 1 to the positive count
            } else if (negative.contains(word)) { // if it is a negative word
                negCount++; // add 1 to the negative count
            } // end of the if-else
        } // end of the for loop
        return posCount - negCount; // the final score
    } // end of score

    @Override // this method comes from the TextAnalyzer interface
    public String getName() { // returns the analyzer name
        return "SentimentAnalyzer"; // the name of this analyzer
    } // end of getName
} // end of class SentimentAnalyzer