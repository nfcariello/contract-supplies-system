import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class home extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        final BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("body");

        primaryStage.setTitle("Turner Construction System | Contract-Supplies System");
        Scene scene = new Scene(borderPane, 900, 750, Color.rgb(44, 62, 80));
        scene.getStylesheets().add("source/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
