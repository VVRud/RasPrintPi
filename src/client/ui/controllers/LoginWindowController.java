package client.ui.controllers;

import client.Client;
import client.ui.WorkspaceWindowFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import static client.data.Constants.*;

public class LoginWindowController {

    @FXML
    public TextField ipField;
    @FXML
    public ToggleButton offlineSwitcher;
    @FXML
    public TextField portField;
    @FXML
    public ImageView image;
    @FXML
    public Label message;

    public void startWorkspace(ActionEvent actionEvent) throws IOException {

        showMessageWindow(ERROR, PORT_ERR, actionEvent);
        boolean isOffline = offlineSwitcher.isSelected();

        if (isOffline) {
            new WorkspaceWindowFX().showWindow("OFFLINE", "MODE");
            System.out.println("OFFLINE MODE");
            return;
        }

        //Get IP and port from TextFields
        String ip = ipField.getText();
        int port;
        try {
            port = Integer.parseInt(portField.getText());
        } catch (NumberFormatException numErr) {
            //TODO ERROR HERE
            return;
        }

        //Try connect to socket
        try {
            Socket socket = new Socket(ip, port);

            Client.setDataOutput(new DataOutputStream(socket.getOutputStream()));
            Client.setDataInput(new DataInputStream(socket.getInputStream()));
            new WorkspaceWindowFX().showWindow(ip, String.valueOf(port));
        } catch (UnknownHostException hostErr) {
            //TODO ERROR HERE
        } catch (IOException ioErr) {
            //TODO ERROR HERE
        } catch (IllegalArgumentException ilArgErr) {
            //TODO ERROR HERE
        }

        //Set fields empty
        ipField.setText("");
        portField.setText("");
    }

    private void showMessageWindow(int type, String msg, ActionEvent actionEvent) throws IOException {
        String title = "";
        switch (type) {
            case ERROR:
                title = ERR_TITLE;
                break;
            case WARNING:
                title = WARN_TITLE;
                break;
            case MESSAGE:
                title = MESSAGE_TITLE;
                break;
        }


        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/ui/layout/ErrorWindow.fxml"));
        Parent root = loader.load();

        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        message.setText(msg);
        switch (type) {
            case ERROR:
                image.setImage(new Image("/client/ui/res/error.png"));
                break;
            case WARNING:
                image.setImage(new Image("/client/ui/res/warning.png"));
                break;
            case MESSAGE:
                //TODO add message image
                break;
        }
        stage.showAndWait();

    }
}
