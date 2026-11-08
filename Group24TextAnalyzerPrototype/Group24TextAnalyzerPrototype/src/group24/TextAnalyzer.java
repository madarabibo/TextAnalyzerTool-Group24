package group24; // this file belongs to the group24 package

/** Contract that every analysis module must follow (UML: <<interface>> TextAnalyzer). */
public interface TextAnalyzer { // an interface only declares methods, it has no logic of its own

    /** Analyses the text and stores the findings inside the shared result object. */
    void analyze(String text, AnalysisResult result); // every analyzer must write this method

    /** Returns a short readable name for the analyzer. */
    String getName(); // every analyzer must also write this method
} // end of interface TextAnalyzer