package group24; // this file belongs to the group24 package

import java.io.FileWriter; // writes text to a file
import java.io.IOException; // error type for file problems
import java.io.ObjectInputStream; // reads a saved object from a file
import java.io.ObjectOutputStream; // writes an object to a file
import java.io.PrintWriter; // makes writing text to a file easy
import java.nio.file.Files; // helper methods for files
import java.nio.file.Path; // represents a file location
import java.util.Map; // used to loop over the word frequencies

/** Formats an AnalysisResult as a text report, and saves or loads it on disk. */
public class ReportGenerator { // UML class ReportGenerator

    /** Builds the full report as printable text. */
    public String toText(AnalysisResult result) { // receives the result we want to show
        StringBuilder sb = new StringBuilder(); // collects the report text piece by piece
        sb.append("=========================================\n"); // top border
        sb.append("         TEXT ANALYSIS REPORT            \n"); // title line
        sb.append("=========================================\n"); // border under the title
        sb.append("Characters (with spaces) : ").append(result.getCharacterCountWithSpaces()).append("\n"); // character count with spaces
        sb.append("Characters (no spaces)   : ").append(result.getCharacterCountNoSpaces()).append("\n"); // character count without spaces
        sb.append("Total Words              : ").append(result.getWordCount()).append("\n"); // word count
        sb.append("Total Sentences          : ").append(result.getSentenceCount()).append("\n"); // sentence count
        sb.append("Sentiment Result         : ").append(result.getSentiment()).append("\n"); // sentiment
        sb.append("-----------------------------------------\n"); // divider
        sb.append("Potential Misspellings   : ").append(result.getMisspelled().toString()).append("\n"); // misspelled words
        sb.append("-----------------------------------------\n"); // divider
        sb.append("Word Frequencies:\n"); // heading for the frequency list
        for (Map.Entry<String, Integer> entry : result.getWordFrequencies().entrySet()) { // go through each word and its count
            sb.append("  - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n"); // one line per word
        } // end of the for loop
        sb.append("=========================================\n"); // bottom border
        return sb.toString(); // return the finished report
    } // end of toText

    /** Saves the text report to a file. */
    public void saveReport(AnalysisResult result, Path path) throws IOException { // throws IOException if writing fails
        try (PrintWriter writer = new PrintWriter(new FileWriter(path.toString()))) { // open the file, closes automatically
            writer.print(toText(result)); // write the whole report into the file
        } // end of try-with-resources
    } // end of saveReport

    /** Saves the result object itself (binary) so it can be loaded again later. */
    public void saveObject(AnalysisResult result, Path path) throws IOException { // throws IOException if writing fails
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) { // open the file for objects
            out.writeObject(result); // write the object into the file
        } // end of try-with-resources
    } // end of saveObject

    /** Loads a result that was saved earlier by saveObject. */
    public AnalysisResult loadObject(Path path) throws IOException, ClassNotFoundException { // can fail in two ways
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) { // open the file for objects
            return (AnalysisResult) in.readObject(); // read the object and convert it back to AnalysisResult
        } // end of try-with-resources
    } // end of loadObject
} // end of class ReportGenerator