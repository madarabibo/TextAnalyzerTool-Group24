package group24; // this file belongs to the group24 package

import java.io.IOException; // error type for file problems
import java.nio.file.Files; // helper methods for files and folders
import java.nio.file.Path; // represents a file location
import java.util.ArrayList; // list implementation
import java.util.List; // general list type

/** Builds an AnalysisResult by running the basic counts and every TextAnalyzer. */
public class AnalysisService { // UML class AnalysisService

    private final List<TextAnalyzer> analyzers = new ArrayList<>(); // all the analyzers we run (1..*)
    private final FileTextLoader loader = new FileTextLoader(); // reads files for us
    private final ActivityLogger logger = new ActivityLogger(); // writes events to activity.log

    /** Creates the service and registers the analyzers. */
    public AnalysisService(String dictionaryPath) { // receives the dictionary file path
        analyzers.add(new WordFrequencyAnalyzer()); // analyzer 1: word frequencies
        analyzers.add(new SpellChecker(dictionaryPath)); // analyzer 2: spell checking
        analyzers.add(new SentimentAnalyzer()); // analyzer 3: sentiment
    } // end of the constructor

    /** Counts the basics, runs every analyzer, and returns the finished result. */
    public AnalysisResult runAll(String text) { // text = the text to analyse
        AnalysisResult result = new AnalysisResult(); // create an empty result
        result.setCharacterCountWithSpaces(countCharactersWithSpaces(text)); // fill in the characters with spaces
        result.setCharacterCountNoSpaces(countCharactersNoSpaces(text)); // fill in the characters without spaces
        result.setWordCount(countWords(text)); // fill in the word count
        result.setSentenceCount(countSentences(text)); // fill in the sentence count

        for (int i = 0; i < analyzers.size(); i++) { // go through every analyzer
            analyzers.get(i).analyze(text, result); // let it write its findings into the result
        } // end of the for loop

        logger.info("Analysis executed for text of " + result.getWordCount() + " words."); // record the event in activity.log
        return result; // return the finished result
    } // end of runAll

    /** Loads a file (or every .txt file in a folder) and analyses its content. */
    public AnalysisResult analyzeFile(Path path) throws IOException { // throws IOException if it cannot be read
        String text; // will hold the loaded text
        if (Files.isDirectory(path)) { // if the path is a folder
            text = loader.loadDirectory(path); // read all .txt files inside it
        } else { // if the path is a single file
            text = loader.load(path); // read that one file
        } // end of the if-else
        return runAll(text); // analyse the loaded text and return the result
    } // end of analyzeFile

    // ---------- basic text metrics ----------

    private int countCharactersWithSpaces(String text) { // counts every character
        if (text == null) { // no text at all
            return 0; // so zero characters
        } // end of the null check
        return text.length(); // the length of the text
    } // end of countCharactersWithSpaces

    private int countCharactersNoSpaces(String text) { // counts characters that are not whitespace
        if (text == null) { // no text at all
            return 0; // so zero characters
        } // end of the null check
        int count = 0; // running total
        for (int i = 0; i < text.length(); i++) { // go through every character
            if (!Character.isWhitespace(text.charAt(i))) { // if it is not a space, tab or line break
                count++; // add 1 to the total
            } // end of the whitespace check
        } // end of the for loop
        return count; // the final total
    } // end of countCharactersNoSpaces

    private int countWords(String text) { // counts the words
        if (text == null || text.trim().isEmpty()) { // no text or only spaces
            return 0; // so zero words
        } // end of the empty check
        String[] words = text.trim().split("\\s+"); // split the text wherever there is whitespace
        return words.length; // the number of pieces is the number of words
    } // end of countWords

    private int countSentences(String text) { // counts the sentences
        if (text == null || text.trim().isEmpty()) { // no text or only spaces
            return 0; // so zero sentences
        } // end of the empty check
        String[] sentences = text.trim().split("[.!?]+"); // split at every group of . ! or ?
        int count = 0; // running total
        for (int i = 0; i < sentences.length; i++) { // go through every piece
            if (!sentences[i].trim().isEmpty()) { // only count pieces that contain something
                count++; // add 1 to the total
            } // end of the empty-piece check
        } // end of the for loop
        return count == 0 ? 1 : count; // text with no . ! or ? still counts as 1 sentence
    } // end of countSentences
} // end of class AnalysisService