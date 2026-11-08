package group24; // this file belongs to the group24 package
import javax.swing.SwingUtilities;
/** Entry point: runs the console menu (the GUI will be added in the next phase). */
public class Main { // UML class Main
    public static void main(String[] args) { // the program starts here
        String dictionaryPath = "dictionary (2).txt"; // the dictionary file in the project root folder
        // Runs the console-based prototype directly (no GUI)
        //new CliMenu(dictionaryPath).start(); // open the console menu
        
        
     // Safely launch the GUI on the Swing Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            AnalyzerGUI gui = new AnalyzerGUI(dictionaryPath);
            gui.show(); 
           });
        
        
        
    } // end of main
} // end of class Main