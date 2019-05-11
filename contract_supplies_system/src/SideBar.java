import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.File;

public abstract class SideBar extends Application {

    protected HBox getMenuSidebar(ImageView imageView, Button projectBtn, MenuButton itemBtn, MenuButton contractBtn, Button supplierBtn, MenuButton orderBtn) {
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
        imageView.setFitWidth(200);
        return imageView;
    }

    protected Button getMenuButton(String name) {
        Button projectBtn = new Button(name);
        projectBtn.getStyleClass().add("menuButton");
        return projectBtn;
    }

    protected MenuButton getDropdownMenuButton(String name) {
        MenuButton menuButton = new MenuButton(name);
        menuButton.setTextFill(Color.BLACK);
        menuButton.getStyleClass().add("dropdownButton");
        return menuButton;
    }

    protected MenuItem getMenuItem(String name) {
        MenuItem menuItem = new MenuItem(name);
        menuItem.getStyleClass().add("menuButton");
        return menuItem;
    }
}
