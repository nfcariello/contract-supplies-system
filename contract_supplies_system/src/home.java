import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class home extends SideBar {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

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
        VBox projectView = getProjectView();

//        Item View
        VBox itemView = getItemView();

//        Contract View
        VBox contractView = getContractView();

//        Supplier View
        VBox supplierView = getSupplierView();

//

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
//            TODO - Create Order View
        });

        primaryStage.setTitle("Turner Construction System | Contract-Supplies System");
        Scene scene = new Scene(borderPane, 900, 550, Color.rgb(44, 62, 80));
        scene.getStylesheets().add("source/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox getSupplierView() {
        VBox supplierView = new VBox(20);
        supplierView.getStyleClass().add("container");
        Label supplierTitle = getTitleLabel("Create Supplier", "title");
        HBox supplierNameHBox = getFormTextfield(10, "Supplier Name");
        HBox supplierAddressHBox = getFormTextfield(10, "Supplier Address");
        Button submitBtn = getSubmitButton();

        supplierView.getChildren().add(supplierTitle);
        supplierView.getChildren().add(supplierNameHBox);
        supplierView.getChildren().add(supplierAddressHBox);
        supplierView.getChildren().add(submitBtn);
        return supplierView;
    }

    private VBox getContractView() {
        VBox contractView = new VBox(20);
        contractView.getStyleClass().add("container");
        Label contractTitle = getTitleLabel("Create Contract", "title");
        HBox itemNoHBox = getFormTextfield(10, "Item Number");
        HBox dateOfContractHBox = getFormDatePicker();
        HBox contractPriceHBox = getFormTextfield(10, "Contract Price");
        HBox contractAmountHBox = getFormTextfield(10, "Contract Amount");
        Button submitBtn = getSubmitButton();

        contractView.getChildren().add(contractTitle);
        contractView.getChildren().add(itemNoHBox);
        contractView.getChildren().add(dateOfContractHBox);
        contractView.getChildren().add(contractPriceHBox);
        contractView.getChildren().add(contractAmountHBox);
        contractView.getChildren().add(submitBtn);
        return contractView;
    }

    private HBox getFormDatePicker() {
        HBox dateOfContractHBox = new HBox(10);
        Label label = getTitleLabel("Date of Contract", "subTitle");
        DatePicker datePicker = new DatePicker();
        dateOfContractHBox.getChildren().add(label);
        dateOfContractHBox.getChildren().add(datePicker);
        return dateOfContractHBox;
    }

    private VBox getProjectView() {
        Label projectTitle = getTitleLabel("Create Project", "title");
        HBox labelTextfieldHBox = getFormTextfield(10, "Project Data");
        return getTitleAndDesc(projectTitle, labelTextfieldHBox);
    }

    private VBox getItemView() {
        Label itemTitle = getTitleLabel("Create Item", "title");
        HBox labelTextfieldHBox = getFormTextfield(10, "Item Description");
        return getTitleAndDesc(itemTitle, labelTextfieldHBox);
    }

    private VBox getTitleAndDesc(Label itemTitle, HBox labelTextfieldHBox) {
        Button submitBtn = getSubmitButton();

        VBox itemView = new VBox(20);
        itemView.getStyleClass().add("container");
        itemView.getChildren().add(itemTitle);
        itemView.getChildren().add(labelTextfieldHBox);
        itemView.getChildren().add(submitBtn);
        return itemView;
    }

    private Button getSubmitButton() {
        Button submitBtn = new Button("Submit");
        submitBtn.getStyleClass().add("pillButton");
        return submitBtn;
    }

    private Label getTitleLabel(String text, String css) {
        Label projectTitle = new Label(text);
        projectTitle.getStyleClass().add(css);
        return projectTitle;
    }

    private HBox getFormTextfield(int spacing, String title) {
        HBox hBox = new HBox(spacing);
        Label label = getTitleLabel(title, "subTitle");
        TextField textField = new TextField();
        hBox.getChildren().add(label);
        hBox.getChildren().add(textField);
        return hBox;
    }

}
