package org.example.Controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientController {
    @FXML
    private JFXTextArea cltxtArea;

    @FXML
    private TextField cltxtfield;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String msg="";

    public void initialize() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 3000);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                cltxtArea.appendText("Server started. \n");

                while (!socket.isClosed()) {

                    msg = dataInputStream.readUTF();
                    cltxtArea.appendText("Server: " + msg + "\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void clientSendOnAction(ActionEvent event) throws IOException {
        String text = cltxtfield.getText();
        dataOutputStream.writeUTF(text);
        dataOutputStream.flush();
        cltxtfield.clear();
    }
}
