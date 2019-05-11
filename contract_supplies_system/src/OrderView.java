import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import source.Item;
import source.OrderedItem;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;

public class OrderView extends ViewUtils {

    private sql sql_query = new sql();

    OrderView() {

    }

    /**
     * Order View
     */
    VBox getOrderView() {
        VBox orderView = new VBox(20);
        orderView.getStyleClass().add("container");
        Label orderTitle = getTitleLabel("Create Order", "title");

        /*
        Order Details
* */
        VBox orderDetailVBox = new VBox(20);
        DatePicker dPDateRequired = new DatePicker();
        HBox dateRequiredHBox = getFormDatePicker("Date Required");
        dateRequiredHBox.getChildren().add(dPDateRequired);

        TextField tfProjectNumber = new TextField();
        HBox projectNoHBox = getFormTextfield(10, "Project No");
        projectNoHBox.getChildren().add(tfProjectNumber);

        TextField tfContractNumber = new TextField();
        HBox contractNoHBox = getFormTextfield(10, "Contract No");
        contractNoHBox.getChildren().add(tfContractNumber);

        orderDetailVBox.getChildren().addAll(orderTitle, dateRequiredHBox, projectNoHBox, contractNoHBox);
/*
        Ordered Items
* */
        VBox orderedItemVBox = new VBox(20);
        TableView orderedItemTableView = new TableView();
        orderedItemTableView.setEditable(true);
//        Item Number
        TableColumn<String, OrderedItem> itemNumberColumn = new TableColumn<>("Item Number");
        itemNumberColumn.prefWidthProperty().bind(orderedItemTableView.widthProperty().multiply(0.5));
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<>("itemNo"));
//        Order Quantity
        TableColumn<String, OrderedItem> orderQuantityColumn = new TableColumn<>("Order Quantity");
        orderQuantityColumn.prefWidthProperty().bind(orderedItemTableView.widthProperty().multiply(0.4));
        orderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("orderQty"));

        orderedItemTableView.getColumns().addAll(itemNumberColumn, orderQuantityColumn);
        orderedItemTableView.setPrefSize(400, 200);

        TextField tfItemNumber = new TextField();
        HBox itemNumberHBox = getFormTextfield(10, "Item Number");
        itemNumberHBox.getChildren().add(tfItemNumber);

        TextField tfOrderQuantity = new TextField();
        HBox orderQuantityHBox = getFormTextfield(10, "Order Quantity");
        orderQuantityHBox.getChildren().add(tfOrderQuantity);
        Button addBtn = new Button("Add");

        LinkedList<OrderedItem> orderedItems = new LinkedList<>();
        addBtn.setOnAction(event -> {
            int itemNumber = Integer.parseInt(tfItemNumber.getText());
            int orderQuantity = Integer.parseInt(tfOrderQuantity.getText());

            orderedItems.add(new OrderedItem(itemNumber, orderQuantity));
            orderedItemTableView.getItems().add(new OrderedItem(itemNumber, orderQuantity));
            tfItemNumber.setText("");
            tfOrderQuantity.setText("");
        });

        orderedItemVBox.getChildren().addAll(orderedItemTableView, itemNumberHBox, orderQuantityHBox, addBtn);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(orderDetailVBox);
        borderPane.setRight(orderedItemVBox);

        Button submitBtn = getSubmitButton();

        submitBtn.setOnAction(event -> {
            LocalDate dateRequired = dPDateRequired.getValue();
            int projectNumber = Integer.parseInt(tfProjectNumber.getText());
            int contractNumber = Integer.parseInt(tfContractNumber.getText());

            Date date = new Date(dateRequired.toEpochDay());
            sql_query.insert_order(date, projectNumber, contractNumber, orderedItems);
//            Cleanup
            orderedItems.clear();
            orderedItemTableView.getItems().clear();
            tfProjectNumber.setText("");
            tfContractNumber.setText("");
        });

        orderView.getChildren().addAll(borderPane, submitBtn);
        return orderView;
    }

    /**
     * Get Find Items In Order
     * */
    VBox getFindItemsInOrder() {
        //        Find View
        VBox findItemView = new VBox(10);
        findItemView.getStyleClass().add("container");

        Label findItemTitle = getTitleLabel("Find Items in Order", "title");

        HBox searchHbox = new HBox(20);
        searchHbox.setAlignment(Pos.CENTER);

        Label searchLabel = new Label("Order Number: ");
        searchLabel.setFont(Font.font("Helvetica", FontWeight.MEDIUM, 20));

        /* Search Textfield */
        TextField searchField = new TextField();
        HBox.setHgrow(searchField, Priority.ALWAYS);

        /* Search Button */
        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("searchButton");
        searchButton.setPadding(new Insets(0, 10, 0, 10));
        searchHbox.getChildren().addAll(searchLabel, searchField, searchButton);

        /* Table View */
        TableView findItemTableView = new TableView();
        findItemTableView.setEditable(true);

//        Item Number
        TableColumn<String, Item> itemNumberColumn = new TableColumn<>("Item Number");
        itemNumberColumn.prefWidthProperty().bind(findItemTableView.widthProperty().multiply(0.5));
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<>("itemNo"));

        TableColumn<String, Item> itemDescriptionColumn = new TableColumn<>("Item Description");
        itemDescriptionColumn.prefWidthProperty().bind(findItemTableView.widthProperty().multiply(0.5));
        itemDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("desc"));

        findItemTableView.getColumns().addAll(itemNumberColumn, itemDescriptionColumn);
        findItemTableView.setPrefSize(400, 200);

        findItemView.getChildren().addAll(findItemTitle, searchHbox, findItemTableView);

        searchButton.setOnAction(event -> {
            findItemTableView.getItems().clear();
            LinkedList<Item> resultFindItem = sql_query.find_items_in_order(Integer.parseInt(searchField.getText()));

            for (int i = 0; i < resultFindItem.size(); i++) {
                int itemNumber = resultFindItem.get(i).getItemNo();
                String itemDescription = resultFindItem.get(i).getDesc();

                findItemTableView.getItems().add(new Item(itemNumber, itemDescription));
            }
//            orderedItemTableView.getItems().add(new OrderedItem(itemNumber, orderQuantity));
            searchField.setText("");
        });

        return findItemView;
    }

    /**
     * Get Price of Item Order
     * */
    VBox getPriceOfItemOrder() {
        //        Find View
        VBox priceOfItemView = new VBox(10);
        priceOfItemView.getStyleClass().add("container");

        Label findItemTitle = getTitleLabel("Price Of Item In Order", "title");

        HBox searchHbox = new HBox(20);
        searchHbox.setAlignment(Pos.CENTER);

        Label itemNumberLabel = new Label("Item Number: ");
        itemNumberLabel.setFont(Font.font("Helvetica", FontWeight.MEDIUM, 18));

        /* Item Number Textfield */
        TextField itemNoTextField = new TextField();
        HBox.setHgrow(itemNoTextField, Priority.ALWAYS);

        Label contactNumberLabel = new Label("Contract Number: ");
        contactNumberLabel.setFont(Font.font("Helvetica", FontWeight.MEDIUM, 18));

        /* Item Number Textfield */
        TextField contactNumberTextField = new TextField();
        HBox.setHgrow(contactNumberTextField, Priority.ALWAYS);

        /* Search Button */
        Button searchButton = new Button("Find");
        searchButton.getStyleClass().add("searchButton");
        searchButton.setPadding(new Insets(0, 10, 0, 10));
        searchHbox.getChildren().addAll(itemNumberLabel, itemNoTextField, contactNumberLabel, contactNumberTextField, searchButton);

        /* Table View */
        Label resultTitle = new Label("Result");
        resultTitle.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 24));
        resultTitle.setPadding(new Insets(50, 0, 0, 00));
        Label resultLabel = new Label("NaN");
        resultLabel.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 32));

        priceOfItemView.getChildren().addAll(findItemTitle, searchHbox, resultTitle, resultLabel);

        searchButton.setOnAction(event -> {

            int itemNumber = Integer.parseInt(itemNoTextField.getText());
            int contractNumber = Integer.parseInt(contactNumberTextField.getText());

            double result = sql_query.price_of_item_in_order(itemNumber, contractNumber);
            resultLabel.setText(String.valueOf(result));

            itemNoTextField.setText("");
            contactNumberTextField.setText("");
        });

        return priceOfItemView;
    }
}
