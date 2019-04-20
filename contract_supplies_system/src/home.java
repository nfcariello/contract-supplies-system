import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

public class home extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        final BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("body");

        File file = new File("contract_supplies_system/src/source/turner.png");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView();
        imageView.getStyleClass().add("logo");
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setImage(image);
        imageView.setFitWidth(150);

        Button projectBtn = getMenuButton("Project");
        Button itemBtn = getMenuButton("Item");
        Button contractBtn = getMenuButton("Contract");
        Button supplierBtn = getMenuButton("Supplier");
        Button orderBtn = getMenuButton("Order");

        VBox sideBar = new VBox(8);
        sideBar.getStyleClass().add("sidebar");
        sideBar.getChildren().add(imageView);
        sideBar.getChildren().add(projectBtn);
        sideBar.getChildren().add(itemBtn);
        sideBar.getChildren().add(contractBtn);
        sideBar.getChildren().add(supplierBtn);
        sideBar.getChildren().add(orderBtn);

        borderPane.setLeft(sideBar);

        primaryStage.setTitle("Turner Construction System | Contract-Supplies System");
        Scene scene = new Scene(borderPane, 900, 550, Color.rgb(44, 62, 80));
        scene.getStylesheets().add("source/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button getMenuButton(String name) {
        Button projectBtn = new Button(name);
        projectBtn.getStyleClass().add("button");
        return projectBtn;
    }
}
