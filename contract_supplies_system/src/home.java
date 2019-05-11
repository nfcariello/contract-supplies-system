import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class home extends SideBar {

    private final MainView mainView = new MainView();
    sql sql_query = new sql();

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        final BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("body");

        ImageView imageView = getHeaderAndLogo();

        Button projectBtn = getMenuButton("Project");
        Button itemBtn = getMenuButton("Item");
        Button contractBtn = getMenuButton("Contract");
        Button supplierBtn = getMenuButton("Supplier");

//       Order
        MenuButton orderBtn = getDropdownMenuButton("Order");
        MenuItem createOrderItem = getMenuItem("Create Order");
        MenuItem findItemsItem = getMenuItem("Find Items");
        orderBtn.getItems().addAll(createOrderItem, findItemsItem);

        HBox topBar = getMenuSidebar(imageView, projectBtn, itemBtn, contractBtn, supplierBtn, orderBtn);

        borderPane.setTop(topBar);

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

//        Find View
        VBox findItemView = new VBox(20);
        findItemsItem.getStyleClass().add("container");

        Label findItemTitle = getTitleLabel("Find Items in Order", "title");

        HBox searchHbox = new HBox(20);
        searchHbox.setAlignment(Pos.CENTER);
        searchHbox.setPadding(new Insets(0, 20, 0, 20));

        Label searchLabel = new Label("Search: ");
        searchLabel.setFont(Font.font("Helvetica", FontWeight.MEDIUM, 25));

        /* Search Textfield */
        TextField searchField = new TextField();
        HBox.setHgrow(searchField, Priority.ALWAYS);

        /* Search Button */
        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("searchButton");
        searchButton.setPadding(new Insets(0, 10, 0, 10));
        searchHbox.getChildren().addAll(searchLabel, searchField, searchButton);

        findItemView.getChildren().addAll(findItemTitle, searchHbox);

//        Main
        borderPane.setCenter(findItemView);

        //        Supplier Button
        supplierBtn.setOnAction(value -> {
            borderPane.setCenter(supplierView);
        });

        //        Item Button
        itemBtn.setOnAction(value -> {
            borderPane.setCenter(itemView);
        });

//        Project Button
        projectBtn.setOnAction(value -> {
            borderPane.setCenter(projectView);
        });

//        Contract Button
        contractBtn.setOnAction(value -> {
            borderPane.setCenter(contractView);
        });

//        Order Button
        createOrderItem.setOnAction(event -> borderPane.setCenter(orderView));
        findItemsItem.setOnAction(value -> {
            borderPane.setCenter(findItemView);
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
        Button submitBtn = getSubmitButton();

        return mainView.getTitleAndDesc(itemTitle, labelTextfieldHBox, submitBtn);
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
