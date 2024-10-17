package org.example.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {

    @FXML
    private TextArea servertxtArea;

    @FXML
    private TextField servertxtfield;

    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String msg = "";

    public void initialize() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(3000);
                servertxtArea.appendText("Server started. \n");
                socket = serverSocket.accept();
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (!socket.isClosed()) {
                    msg = dataInputStream.readUTF();
                    servertxtArea.appendText("Client: " + msg + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void serverSendOnAction(ActionEvent event) throws IOException {
        String message = servertxtfield.getText();
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();
        servertxtfield.clear();
    }
}
