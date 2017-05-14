package client.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static client.data.Constants.TITLE;

/**
 * Created by vvrud on 31.03.17.
 *
 * @author VVRud
 */
public class WorkspaceWindowFX extends Application {
    private String ip;
    private String port;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layout/LoginWindow.fxml"));
        primaryStage.setTitle(TITLE + " | On socket " + ip + ":" + port);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void showWindow(String ip, String port) {
        this.ip = ip;
        this.port = port;
        launch();
    }
}
