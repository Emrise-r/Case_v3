package Server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class RunServer extends Application {
    public static TextArea textAreaDisplay;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        textAreaDisplay = new TextArea();
        textAreaDisplay.setEditable(false);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textAreaDisplay);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 300, 500);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        Server_v2 server = new Server_v2();
        server.start();
    }
}
