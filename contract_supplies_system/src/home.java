import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class home extends SideBar {

    sql sql_query = new sql();
    private final MainView mainView = new MainView();

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        sql_query.insert_suppliers("Nick", "26 Eden Lane");

        final BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("body");

        ImageView imageView = getHeaderAndLogo();

        Button projectBtn = getMenuButton("Project");
        Button itemBtn = getMenuButton("Item");
        Button contractBtn = getMenuButton("Contract");
        Button supplierBtn = getMenuButton("Supplier");
        Button orderBtn = getMenuButton("Order");

        VBox sideBar = getMenuSidebar(imageView, projectBtn, itemBtn, contractBtn, supplierBtn, orderBtn);

        borderPane.setLeft(sideBar);

//        Project View
        VBox projectView = mainView.getProjectView();

//        Item View
        VBox itemView = mainView.getItemView();

//        Contract View
        VBox contractView = mainView.getContractView();

//        Supplier View
        VBox supplierView = mainView.getSupplierView();

//        Order View
        VBox orderView = mainView.getOrderView();

//        Main
        borderPane.setCenter(projectView);

//        Project Button
        projectBtn.setOnAction(value -> {
            borderPane.setCenter(projectView);
        });

//        Item Button
        itemBtn.setOnAction(value -> {
            borderPane.setCenter(itemView);
        });

//        Contract Button
        contractBtn.setOnAction(value -> {
            borderPane.setCenter(contractView);
        });

//        Supplier Button
        supplierBtn.setOnAction(value -> {
            borderPane.setCenter(supplierView);
        });

//        Order Button
        orderBtn.setOnAction(value -> {
            borderPane.setCenter(orderView);
        });

        primaryStage.setTitle("Turner Construction System | Contract-Supplies System");
        Scene scene = new Scene(borderPane, 900, 550, Color.rgb(44, 62, 80));
        scene.getStylesheets().add("source/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox getOrderView() {

        return mainView.getOrderView();
    }

    private VBox getSupplierView() {

        return mainView.getSupplierView();
    }

    private VBox getContractView() {

        return mainView.getContractView();
    }

    private HBox getFormDatePicker(String title) {
        return mainView.getFormDatePicker(title);
    }

    private VBox getProjectView() {
        return mainView.getProjectView();
    }

    private VBox getItemView() {
        return mainView.getItemView();
    }

    private VBox getTitleAndDesc(Label itemTitle, HBox labelTextfieldHBox) {

        return mainView.getTitleAndDesc(itemTitle, labelTextfieldHBox);
    }

    private Button getSubmitButton() {
        return mainView.getSubmitButton();
    }

    private Label getTitleLabel(String text, String css) {
        return mainView.getTitleLabel(text, css);
    }

    private HBox getFormTextfield(int spacing, String title) {
        return mainView.getFormTextfield(spacing, title);
    }

}
