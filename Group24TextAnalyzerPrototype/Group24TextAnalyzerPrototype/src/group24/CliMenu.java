package group24; // this file belongs to the group24 package

import java.io.IOException; // error type for file problems
import java.nio.file.Paths; // turns text into a Path
import java.util.Scanner; // reads what the user types

/** Console menu for the Text Analyzer: reads the user's choice and prints the results. */
public class CliMenu { // UML class CliMenu

    private final Scanner scanner = new Scanner(System.in); // reads keyboard input
    private final AnalysisService service; // does the actual analysis
    private final ReportGenerator reportGenerator = new ReportGenerator(); // formats and saves reports
    private final ActivityLogger logger = new ActivityLogger(); // writes events to activity.log
    private AnalysisResult lastResult; // remembers the most recent analysis
    private boolean running = true; // the menu loop runs while this is true

    /** Creates the menu and the analysis service behind it. */
    public CliMenu(String dictionaryPath) { // receives the dictionary file path
        this.service = new AnalysisService(dictionaryPath); // create the service with that dictionary
    } // end of the constructor

    /** Shows the menu again and again until the user chooses Exit. */
    public void start() { // called from Main
        System.out.println("====================================================="); // banner top line
        System.out.println("          GROUP 24: TEXT ANALYZER TOOL (CLI)         "); // banner title
        System.out.println("====================================================="); // banner bottom line

        while (running) { // keep going until the user exits
            printMenu(); // show the list of options
            String choice = scanner.nextLine().trim(); // read what the user typed
            try { // try to turn the text into a number
                handleChoice(Integer.parseInt(choice)); // convert to int and run that option
            } catch (NumberFormatException e) { // the user typed something that is not a number
                System.out.println("Invalid option. Please choose 1-5."); // tell the user
            } // end of try-catch
        } // end of the while loop
    } // end of start

    /** Prints the list of options. */
    private void printMenu() { // private, only used inside this class
        System.out.println("\nSelect Option:"); // heading
        System.out.println("1. Analyze Direct Text Input"); // option 1
        System.out.println("2. Analyze External Text File (.txt)"); // option 2
        System.out.println("3. Display Last Analysis Result"); // option 3
        System.out.println("4. Save/Export Analysis Report"); // option 4
        System.out.println("5. Exit"); // option 5
        System.out.print("Choice > "); // prompt, no line break so the user types next to it
    } // end of printMenu

    /** Carries out the option the user picked. */
    private void handleChoice(int choice) { // choice = the option number
        switch (choice) { // pick the code that matches the number
            case 1: // option 1: analyse typed text
                System.out.print("\nEnter text: "); // ask for the text
                String input = scanner.nextLine(); // read the text
                lastResult = service.runAll(input); // analyse it and remember the result
                System.out.println("\n" + reportGenerator.toText(lastResult)); // show the report
                break; // leave the switch

            case 2: // option 2: analyse a file or folder
                System.out.print("\nEnter file path (e.g., sample.txt): "); // ask for the path
                String path = scanner.nextLine().trim(); // read the path
                try { // reading a file can fail
                    lastResult = service.analyzeFile(Paths.get(path)); // load and analyse it, remember the result
                    System.out.println("\n" + reportGenerator.toText(lastResult)); // show the report
                } catch (IOException e) { // the file could not be read
                    System.out.println("Error reading file: " + e.getMessage()); // tell the user
                    logger.error("Error reading file: " + path); // record the problem in activity.log
                } // end of try-catch
                break; // leave the switch

            case 3: // option 3: show the last result again
                if (lastResult == null) { // nothing has been analysed yet
                    System.out.println("No analysis has been run yet."); // tell the user
                } else { // there is a result
                    System.out.println("\n" + reportGenerator.toText(lastResult)); // show it
                } // end of the if-else
                break; // leave the switch

            case 4: // option 4: save the report to a file
                if (lastResult == null) { // nothing to save yet
                    System.out.println("No analysis has been run yet to export."); // tell the user
                } else { // there is a result to save
                    System.out.print("Enter output file name (e.g., report.txt): "); // ask for the file name
                    String outName = scanner.nextLine().trim(); // read the file name
                    try { // writing a file can fail
                        reportGenerator.saveReport(lastResult, Paths.get(outName)); // write the report to the file
                        System.out.println("Report successfully saved to " + outName); // confirm to the user
                        logger.info("Report exported to " + outName); // record the event in activity.log
                    } catch (IOException e) { // the file could not be written
                        System.out.println("Error writing report: " + e.getMessage()); // tell the user
                        logger.error("Error writing report: " + outName); // record the problem in activity.log
                    } // end of try-catch
                } // end of the if-else
                break; // leave the switch

            case 5: // option 5: exit
                running = false; // makes the menu loop stop
                System.out.println("Exiting application. Goodbye!"); // say goodbye
                logger.info("User exited CLI application."); // record the event in activity.log
                break; // leave the switch

            default: // any other number
                System.out.println("Invalid option. Please choose 1-5."); // tell the user
        } // end of the switch
    } // end of handleChoice
} // end of class CliMenu