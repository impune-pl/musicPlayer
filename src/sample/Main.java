package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Music Player");
        Scene mainScene = new Scene(root, 450, 140);
        primaryStage.setScene(mainScene);
        Controller mainController = fxmlLoader.getController();
        mainController.setMainScene(mainScene);
        primaryStage.show();
    }
}
