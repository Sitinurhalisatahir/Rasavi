package temurasa;

import javafx.application.Application;
import javafx.stage.Stage;
import temurasa.database.DatabaseHelper;
import temurasa.GUI.LoginWindow;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        DatabaseHelper.createTables();

        LoginWindow loginWindow = new LoginWindow();
        loginWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}