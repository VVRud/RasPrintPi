package client.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static client.data.Constants.TITLE;

/**
 * Created by vvrud on 30.03.17.
 *
 * @author VVRud
 */
public class LoginWindowFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layout/LoginWindow.fxml"));
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void openWindow(String[] args) {
        launch(args);
    }
}
