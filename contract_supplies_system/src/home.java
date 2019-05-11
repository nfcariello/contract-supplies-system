import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class home extends SideBar {

    private final ViewUtils viewUtils = new ViewUtils();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        final BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("body");

        ImageView imageView = getHeaderAndLogo();

        Button projectBtn = getMenuButton("Project");
        Button supplierBtn = getMenuButton("Supplier");

        /*Item */
        MenuButton itemBtn = getDropdownMenuButton("Item");
        MenuItem createItem = getMenuItem("Create Item");
        MenuItem findOrder = getMenuItem("Find Orders");
        itemBtn.getItems().addAll(createItem, findOrder);

        /**
         * Contract
         * */
        MenuButton contractBtn = getDropdownMenuButton("Contract");
        contractBtn.setMinWidth(150);
        MenuItem createContract = getMenuItem("Create Contract");
        MenuItem priceOfItemContract = getMenuItem("Price Of Item");
        MenuItem findSupplierNumber = getMenuItem("Find Supplier Number");
        contractBtn.getItems().addAll(createContract, priceOfItemContract, findSupplierNumber);

        /**
         * Order
         * */
        MenuButton orderBtn = getDropdownMenuButton("Order");
        MenuItem createOrderItem = getMenuItem("Create Order");
        MenuItem findItemsItem = getMenuItem("Find Items");
        MenuItem priceOfItem = getMenuItem("Price of Item");
        orderBtn.getItems().addAll(createOrderItem, findItemsItem, priceOfItem);

        HBox topBar = getMenuSidebar(imageView, projectBtn, itemBtn, contractBtn, supplierBtn, orderBtn);

        borderPane.setTop(topBar);

//        Project View
        VBox projectView = viewUtils.getProjectView();

//        Item View
        ItemView itemView = new ItemView();
        VBox mainViewItemView = itemView.getItemView();
        VBox findOrderView = itemView.getFindOrdersInItem();

//        Contract View
        ContractView contractView = new ContractView();
        VBox mainContractView = contractView.getContractView();
        VBox contractPriceOfItem = contractView.getPriceOfItemContract();
        VBox findSupplier = contractView.getFindSupplier();

//        Supplier View
        VBox supplierView = viewUtils.getSupplierView();

//        Order View
        OrderView orderView = new OrderView();
        VBox createOrder = orderView.getOrderView();
        VBox findItemView = orderView.getFindItemsInOrder();
        VBox priceOfItemView = orderView.getPriceOfItemOrder();

/**
 * Main View
 * */
        borderPane.setCenter(supplierView);

        //        Supplier Button
        supplierBtn.setOnAction(value -> {
            borderPane.setCenter(supplierView);
        });

        /**
         * Item Button
         * */
        createItem.setOnAction(value -> {
            borderPane.setCenter(mainViewItemView);
        });
        findOrder.setOnAction(value -> {
            borderPane.setCenter(findOrderView);
        });

//        Project Button
        projectBtn.setOnAction(value -> {
            borderPane.setCenter(projectView);
        });

        /**
         * Contract Button
         * */
        createContract.setOnAction(value -> {
            borderPane.setCenter(mainContractView);
        });
        priceOfItemContract.setOnAction(value -> {
            borderPane.setCenter(contractPriceOfItem);
        });
        findSupplierNumber.setOnAction(value -> {
            borderPane.setCenter(findSupplier);
        });

        /**
         * Order Button
         * */
        createOrderItem.setOnAction(event -> borderPane.setCenter(createOrder));
        findItemsItem.setOnAction(value -> {
            borderPane.setCenter(findItemView);
        });
        priceOfItem.setOnAction(event -> {
            borderPane.setCenter(priceOfItemView);
        });

        primaryStage.setTitle("Turner Construction System | Contract-Supplies System");
        Scene scene = new Scene(borderPane, 950, 550, Color.rgb(44, 62, 80));
        scene.getStylesheets().add("source/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox getSupplierView() {

        return viewUtils.getSupplierView();
    }

    private HBox getFormDatePicker(String title) {
        return viewUtils.getFormDatePicker(title);
    }

    private VBox getProjectView() {
        return viewUtils.getProjectView();
    }

    private VBox getTitleAndDesc(Label itemTitle, HBox labelTextfieldHBox) {
        Button submitBtn = getSubmitButton();

        return viewUtils.getTitleAndDesc(itemTitle, labelTextfieldHBox, submitBtn);
    }

    private Button getSubmitButton() {
        return viewUtils.getSubmitButton();
    }

    private Label getTitleLabel(String text, String css) {
        return viewUtils.getTitleLabel(text, css);
    }

    private HBox getFormTextfield(int spacing, String title) {
        return viewUtils.getFormTextfield(spacing, title);
    }

}
