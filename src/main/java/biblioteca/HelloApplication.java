package biblioteca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader =
                new FXMLLoader(
                        HelloApplication.class.getResource(
                                "biblioteca-view.fxml"
                        )
                );

        Scene scene =
                new Scene(
                        fxmlLoader.load(),
                        950,
                        650
                );

        stage.setTitle("Sistema de Biblioteca");

        stage.setScene(scene);

        stage.show();
    }
}