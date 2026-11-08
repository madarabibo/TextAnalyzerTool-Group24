package group24; // this file belongs to the group24 package

import java.io.BufferedReader; // reads text line by line
import java.io.FileReader; // opens a file for reading
import java.io.IOException; // error type for file problems
import java.nio.file.Files; // helper methods for files and folders
import java.nio.file.Path; // represents a file location
import java.util.ArrayList; // list implementation
import java.util.Collections; // used to sort the list
import java.util.List; // general list type
import java.util.stream.Stream; // stream of paths returned by Files.walk

/** Reads text from disk: one file, or every .txt file inside a folder. */
public class FileTextLoader { // UML class FileTextLoader

    /** Reads a whole text file line by line. */
    public String load(Path path) throws IOException { // throws IOException if the file cannot be read
        StringBuilder sb = new StringBuilder(); // collects all the lines into one text
        try (BufferedReader br = new BufferedReader(new FileReader(path.toString()))) { // open the file, closes it automatically
            String line; // holds the current line
            while ((line = br.readLine()) != null) { // keep reading until there are no more lines
                sb.append(line).append("\n"); // add the line and a line break
            } // end of the while loop
        } // end of try-with-resources
        return sb.toString(); // return the whole file content as one String
    } // end of load

    /** Recursively reads every .txt file under a folder and joins their content. */
    public String loadDirectory(Path directory) throws IOException { // throws IOException if the folder cannot be searched
        List<Path> files = new ArrayList<>(); // will hold every .txt file that we find
        try (Stream<Path> walk = Files.walk(directory)) { // walk goes through the folder and all its subfolders
            walk.forEach(p -> { // look at every path that was found
                // keep it only if it is a real file whose name ends with .txt
                if (Files.isRegularFile(p) && p.getFileName().toString().toLowerCase().endsWith(".txt")) {
                    files.add(p); // add the .txt file to our list
                } // end of the .txt check
            }); // end of forEach
        } // end of try-with-resources
        Collections.sort(files); // sort the files so the order is always the same

        StringBuilder sb = new StringBuilder(); // collects the text of all files
        for (int i = 0; i < files.size(); i++) { // go through every file
            sb.append(load(files.get(i))); // read the file and add its text
        } // end of the for loop
        return sb.toString(); // return the combined text
    } // end of loadDirectory
} // end of class FileTextLoader