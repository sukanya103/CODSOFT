import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WordCounter extends Application {
    private TextArea textArea;
    private Label wordCountLabel;
    private Label uniqueWordsLabel;
    private Label wordFrequencyLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Word Counter");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        // Text area for user input
        textArea = new TextArea();
        textArea.setPromptText("Enter text or load a file...");
        textArea.setPrefRowCount(10);
        GridPane.setConstraints(textArea, 0, 0, 2, 1);

        // Button to load a file
        Button loadButton = new Button("Load File");
        GridPane.setConstraints(loadButton, 0, 1);
        loadButton.setOnAction(e -> loadFile(primaryStage));

        // Button to count words
        Button countButton = new Button("Count Words");
        GridPane.setConstraints(countButton, 1, 1);
        countButton.setOnAction(e -> countWords());

        // Labels for displaying word count, unique words, and word frequency
        wordCountLabel = new Label();
        GridPane.setConstraints(wordCountLabel, 0, 2);

        uniqueWordsLabel = new Label();
        GridPane.setConstraints(uniqueWordsLabel, 0, 3);

        wordFrequencyLabel = new Label();
        GridPane.setConstraints(wordFrequencyLabel, 0, 4, 2, 1);

        grid.getChildren().addAll(textArea, loadButton, countButton, wordCountLabel, uniqueWordsLabel, wordFrequencyLabel);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                reader.close();
                textArea.setText(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void countWords() {
        String text = textArea.getText();
        if (!text.isEmpty()) {
            // Split the string into an array of words using space or punctuation as delimiters
            String[] words = text.split("[\\s\\p{Punct}]+");

            // Initialize a counter variable to keep track of the number of words
            int wordCount = words.length;

            // Iterate through the array of words and increment the counter for each word encountered
            Map<String, Integer> wordFrequency = new HashMap<>();
            for (String word : words) {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }

            // Update the labels with the word count, unique words, and word frequency
            wordCountLabel.setText("Total word count: " + wordCount);
            uniqueWordsLabel.setText("Number of unique words: " + wordFrequency.size());

            StringBuilder frequencyBuilder = new StringBuilder();
            frequencyBuilder.append("Word frequency:").append("\n");
            for (String word : wordFrequency.keySet()) {
                int frequency = wordFrequency.get(word);
                frequencyBuilder.append(word).append(": ").append(frequency).append("\n");
            }
            wordFrequencyLabel.setText(frequencyBuilder.toString());
        }
    }
}
