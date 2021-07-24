package ambulancesimulation.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MessageWindow {

    static boolean answer;

    public static boolean displayConfirmation(String message) {
        Stage window = createWindow();

        Label text = new Label(message);
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        HBox buttonRow = new HBox(15);
        buttonRow.getChildren().addAll(yesButton, noButton);
        buttonRow.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15);
        layout.getChildren().addAll(text, buttonRow);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(MessageWindow.class.getResource("Styles.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

    //do komunikatów z odczytu plików
    public static void displayMessage(String message) {
        Stage window = createWindow();

        Label text = new Label(message);
        Button okButton = new Button("Ok");
        okButton.setOnAction(e -> window.close());

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(0, 10, 0, 10));
        layout.getChildren().addAll(text, okButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(MessageWindow.class.getResource("Styles.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();
    }

    private static Stage createWindow() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);
        window.setMinHeight(120);
        return window;
    }

}
