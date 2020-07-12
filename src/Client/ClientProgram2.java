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

public class ClientProgram2 extends Application {

    final String serverHost = "localhost";
    final int port = 4321;
    Socket socketClient = null;
    BufferedWriter bos = null;

    TextField textName_2;
    TextField textInput_2;
    ScrollPane scrollPane_2;
    public static TextArea textAreaDisplay_2;

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox vBox_2 = new VBox();
        scrollPane_2 = new ScrollPane();
        HBox hBox_2 = new HBox();
        textAreaDisplay_2 = new TextArea();
        textAreaDisplay_2.setEditable(false);
        scrollPane_2.setContent(textAreaDisplay_2);
        scrollPane_2.setFitToWidth(true);
        scrollPane_2.setFitToHeight(true);
        textName_2 = new TextField();
        textName_2.setPromptText("UserName");
        textName_2.setTooltip(new Tooltip("Write your name:"));
        textInput_2 = new TextField();
        textInput_2.setPromptText("New Message");
        textInput_2.setTooltip(new Tooltip("Write your message: "));
        Button buttonSend_2 = new Button("Send");
        buttonSend_2.setOnAction(new ButtonListener());

        hBox_2.getChildren().addAll(textName_2, textInput_2, buttonSend_2);
        hBox_2.setHgrow(textInput_2, Priority.ALWAYS);  //set textfield to grow as window size grows

        //set center and bottom of the borderPane with scrollPane and hBox
        vBox_2.getChildren().addAll(scrollPane_2, hBox_2);
        vBox_2.setVgrow(scrollPane_2, Priority.ALWAYS);

        //create a scene and display
        Scene scene_2 = new Scene(vBox_2, 450, 500);
        primaryStage.setTitle("Client_2");
        primaryStage.setScene(scene_2);
        primaryStage.show();


        try {

            socketClient = new Socket(serverHost, port);
            textAreaDisplay_2.appendText("Successful Connection \n");

            bos = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            ClientReaderThread2 clientReaderThread_2 = new ClientReaderThread2(socketClient);
            Thread thread_2 = new Thread(clientReaderThread_2);
            thread_2.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }


    private class ButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            try {
                //get username and message
                String username_2 = textName_2.getText().trim();
                String message_2 = textInput_2.getText().trim();

                //if username is empty set it to 'Unknown'
                if (username_2.length() == 0) {
                    username_2 = "Unknown";
                }
                //send message to server
                bos.write("[" + username_2 + "]: " + message_2 + "");
                bos.newLine();
//                textAreaDisplay_2.appendText("[" + username_2 + "]: " + message_2 + "");
                bos.flush();

                //clear the textfield
                textInput_2.clear();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
