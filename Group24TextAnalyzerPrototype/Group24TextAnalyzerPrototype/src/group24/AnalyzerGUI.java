package group24; // this file belongs to the group24 package

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.nio.file.Path;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/** 
 * Swing Graphical User Interface for the Text Analyzer Tool.
 * Supports both manual text entry and document file uploads.
 */
public class AnalyzerGUI {

    private JFrame frame;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private final AnalysisService service;
    private final ReportGenerator reportGenerator = new ReportGenerator();
    private final FileTextLoader fileLoader = new FileTextLoader();

    /** 
     * Creates the GUI controller and initializes the underlying service.
     * 
     * @param dictionaryPath Path to the spell-check dictionary file
     */
    public AnalyzerGUI(String dictionaryPath) {
        this.service = new AnalysisService(dictionaryPath);
    }

    /** 
     * Constructs and displays the main application window.
     */
    public void show() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Text Analyzer Tool - Group 24");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(850, 650);

            // Input text area
            inputArea = new JTextArea();
            inputArea.setLineWrap(true);
            inputArea.setWrapStyleWord(true);
            inputArea.setBackground(new Color(250, 250, 255));
            inputArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            // Output text area (report display)
            outputArea = new JTextArea();
            outputArea.setEditable(false);
            outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            outputArea.setBackground(new Color(240, 242, 245));

            JScrollPane inputScroll = new JScrollPane(inputArea);
            inputScroll.getViewport().setBackground(new Color(250, 250, 255));

            JScrollPane outputScroll = new JScrollPane(outputArea);
            outputScroll.getViewport().setBackground(new Color(240, 242, 245));

            // Center panel layout
            JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            centerPanel.setBackground(new Color(220, 224, 230));
            centerPanel.add(inputScroll);
            centerPanel.add(outputScroll);

            // Bottom buttons panel (Upload Document + Analyze)
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
            
            JButton uploadButton = new JButton("Upload Document (.txt)");
            uploadButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
            uploadButton.addActionListener(e -> onUploadFile());

            JButton analyzeButton = new JButton("Analyze Text");
            analyzeButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
            analyzeButton.addActionListener(e -> onAnalyze());

            buttonPanel.add(uploadButton);
            buttonPanel.add(analyzeButton);

            // Assembly
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(buttonPanel, BorderLayout.SOUTH);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    /** 
     * Opens a file browser allowing the user to select a text document,
     * loads its content into the input box, and runs the analysis automatically.
     */
    private void onUploadFile() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                Path path = selectedFile.toPath();

                // Use your existing FileTextLoader to load the document content
                String fileContent = fileLoader.load(path);

                if (fileContent != null && !fileContent.trim().isEmpty()) {
                    // Populate the input area so the user can see what text was loaded
                    inputArea.setText(fileContent);

                    // Automatically run the analysis pipeline on the loaded text
                    AnalysisResult analysisResult = service.runAll(fileContent);
                    outputArea.setText(reportGenerator.toText(analysisResult));
                } else {
                    outputArea.setText("Warning: The selected file is empty.");
                }
            }
        }catch (Exception e) {
    outputError("Error loading document: " + e.getMessage(), e);
}
    }

    /** 
     * Event handler executed when the "Analyze Text" button is clicked.
     */
    private void onAnalyze() {
        try {
            String text = inputArea.getText();
            
            if (text == null || text.trim().isEmpty()) {
                outputArea.setText("Warning: No text provided. Please enter text or upload a document.");
                return;
            }

            AnalysisResult result = service.runAll(text);
            outputArea.setText(reportGenerator.toText(result));
            
        } catch (Exception e) {
    outputError("Error during analysis: " + e.getMessage(), e);
}
    }

  private void outputError(String message, Exception e) {
    outputArea.setText(message);
    e.printStackTrace(); // Prints stack trace to console
}
}