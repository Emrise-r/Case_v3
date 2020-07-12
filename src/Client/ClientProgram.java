package Client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class ClientProgram extends Application {

    final String serverHost = "localhost";
    final int port = 4321;
    Socket socketClient = null;
    BufferedWriter bos = null;

    TextField textName_1;
    TextField textInput_1;
    ScrollPane scrollPane_1;
    public static TextArea textAreaDisplay_1;

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox vBox_1 = new VBox();
        scrollPane_1 = new ScrollPane();
        HBox hBox_1 = new HBox();
        textAreaDisplay_1 = new TextArea();
        textAreaDisplay_1.setEditable(false);
        scrollPane_1.setContent(textAreaDisplay_1);
        scrollPane_1.setFitToWidth(true);
        scrollPane_1.setFitToHeight(true);
        textName_1 = new TextField();
        textName_1.setPromptText("UserName");
        textName_1.setTooltip(new Tooltip("Write your name:"));
        textInput_1 = new TextField();
        textInput_1.setPromptText("New Message");
        textInput_1.setTooltip(new Tooltip("Write your message:"));
        Button buttonSend_1 = new Button("Send");
        buttonSend_1.setOnAction(new ButtonListener());

        hBox_1.getChildren().addAll(textName_1, textInput_1, buttonSend_1);
        hBox_1.setHgrow(textInput_1, Priority.ALWAYS);  //set textfield to grow as window size grows

        //set center and bottom of the borderPane with scrollPane and hBox
        vBox_1.getChildren().addAll(scrollPane_1, hBox_1);
        vBox_1.setVgrow(scrollPane_1, Priority.ALWAYS);

        //create a scene and display
        Scene scene_1 = new Scene(vBox_1, 450, 500);
        primaryStage.setTitle("Client_1");
        primaryStage.setScene(scene_1);
        primaryStage.show();


        try {

            socketClient = new Socket(serverHost, port);
            textAreaDisplay_1.appendText("Successful Connection \n");

            bos = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ClientReaderThread clientReaderThread_1 = new ClientReaderThread(socketClient);
        Thread thread_1 = new Thread(clientReaderThread_1);
        thread_1.start();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private class ButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            try {
                //get username and message
                String username_1 = textName_1.getText().trim();
                String message_1 = textInput_1.getText().trim();

                //if username is empty set it to 'Unknown'
                if (username_1.length() == 0) {
                    username_1 = "Unknown";
                }

                bos.write("[" + username_1 + "]: " + message_1 + "");
                bos.newLine();
//                textAreaDisplay_1.appendText("[" + username_1 + "]: " + message_1 + "");
                bos.flush();

                //clear the textfield
                textInput_1.clear();

            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
