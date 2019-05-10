import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;

public abstract class SideBar extends Application {

    protected HBox getMenuSidebar(ImageView imageView, Button projectBtn, Button itemBtn, Button contractBtn, Button supplierBtn, Button orderBtn) {
        HBox sideBar = new HBox(10);
        sideBar.getStyleClass().add("sidebar");
        sideBar.getChildren().add(imageView);
        sideBar.getChildren().add(supplierBtn);
        sideBar.getChildren().add(itemBtn);
        sideBar.getChildren().add(projectBtn);
        sideBar.getChildren().add(contractBtn);
        sideBar.getChildren().add(orderBtn);
        return sideBar;
    }

    protected ImageView getHeaderAndLogo() {
        File file = new File("contract_supplies_system/src/source/turner.png");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView();
        imageView.getStyleClass().add("logo");
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setImage(image);
        imageView.setFitWidth(150);
        return imageView;
    }

    protected Button getMenuButton(String name) {
        Button projectBtn = new Button(name);
        projectBtn.getStyleClass().add("menuButton");
        return projectBtn;
    }
}
