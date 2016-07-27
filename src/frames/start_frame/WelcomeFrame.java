package frames.start_frame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import programm.Programm;

public class WelcomeFrame extends Application {

    private Programm PROGRAMM;
    private WelcomeFrameController CONTROLLER;
    private Stage PRIMARY_STAGE;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.PRIMARY_STAGE = primaryStage;
        this.PROGRAMM = new Programm();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        CONTROLLER = new WelcomeFrameController(PROGRAMM, this);
        loader.setController(CONTROLLER);
        AnchorPane root = loader.load();

        PRIMARY_STAGE.setTitle("\"ООО\" Классическая гомеопатия");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public void stop(){
        PRIMARY_STAGE.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
