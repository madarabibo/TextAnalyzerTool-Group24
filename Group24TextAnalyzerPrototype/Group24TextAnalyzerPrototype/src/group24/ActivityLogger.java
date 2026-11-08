package group24; // this file belongs to the group24 package

import java.io.FileWriter; // writes text to a file
import java.io.IOException; // error type for file problems
import java.io.PrintWriter; // makes writing lines to a file easy
import java.nio.file.Path; // represents a file location
import java.nio.file.Paths; // creates Path objects
import java.time.LocalDateTime; // gives the current date and time

/** Appends timestamped events to a log file (activity.log by default). */
public class ActivityLogger { // UML class ActivityLogger

    private final Path logFile; // the file that every event is written to

    /** Creates a logger that writes to activity.log in the working folder. */
    public ActivityLogger() { // default constructor
        this(Paths.get("activity.log")); // calls the other constructor with the default file name
    } // end of the default constructor

    /** Creates a logger that writes to the given file. */
    public ActivityLogger(Path logFile) { // constructor with a custom file
        this.logFile = logFile; // remember which file to write to
    } // end of the constructor

    /** Records a normal event. */
    public void info(String message) { // used for things that went well
        write(message); // write the message as it is
    } // end of info

    /** Records an error event. */
    public void error(String message) { // used for things that went wrong
        write("ERROR: " + message); // write the message with an ERROR label in front
    } // end of error

    /** Writes one timestamped line at the end of the log file. */
    private void write(String message) { // private, only this class uses it
        try (PrintWriter out = new PrintWriter(new FileWriter(logFile.toString(), true))) { // true = add to the end, do not overwrite
            out.println("[" + LocalDateTime.now() + "] " + message); // write "[time] message" as one line
        } catch (IOException e) { // runs only if the file could not be written
            System.out.println("Could not write to activity log: " + e.getMessage()); // tell the user what happened
        } // end of try-catch
    } // end of write
} // end of class ActivityLogger