import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import source.Order;

import java.util.LinkedList;

class ItemView extends ViewUtils {

    private sql sql_query = new sql();

    ItemView() {

    }

    /**
     * Item View
     */
    VBox getItemView() {
        Label itemTitle = getTitleLabel("Create Item", "title");

        TextField txItemDescription = new TextField();
        HBox labelTextfieldHBox = getFormTextfield(10, "Item Description");
        labelTextfieldHBox.getChildren().add(txItemDescription);

        Button submitBtn = getSubmitButton();
        submitBtn.setOnAction(event -> {
            if (!txItemDescription.getText().isEmpty()) {
                insert_item(txItemDescription.getText());
                txItemDescription.setText("");
            }
        });

        return getTitleAndDesc(itemTitle, labelTextfieldHBox, submitBtn);
    }

    /**
     * Find Orders In Item
     * */
    VBox getFindOrdersInItem() {
        //        Find View
        VBox findOrderView = new VBox(10);
        findOrderView.getStyleClass().add("container");

        Label findItemTitle = getTitleLabel("Find Orders in Item", "title");

        HBox searchHbox = new HBox(20);
        searchHbox.setAlignment(Pos.CENTER);

        Label searchLabel = new Label("Item Number: ");
        searchLabel.setFont(Font.font("Helvetica", FontWeight.MEDIUM, 20));

        /* Search Textfield */
        TextField searchField = new TextField();
        HBox.setHgrow(searchField, Priority.ALWAYS);

        /* Search Button */
        Button searchOrderButton = new Button("Search");
        searchOrderButton.getStyleClass().add("searchButton");
        searchOrderButton.setPadding(new Insets(0, 10, 0, 10));
        searchHbox.getChildren().addAll(searchLabel, searchField, searchOrderButton);

        /* Table View */
        TableView findOrderTableView = new TableView();

//        Item Number
        TableColumn<String, Order> itemNumberColumn = new TableColumn<>("Order Number");
        itemNumberColumn.prefWidthProperty().bind(findOrderTableView.widthProperty().multiply(0.5));
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNo"));

        findOrderTableView.getColumns().addAll(itemNumberColumn);
        findOrderTableView.setPrefSize(400, 200);

        findOrderView.getChildren().addAll(findItemTitle, searchHbox, findOrderTableView);

        searchOrderButton.setOnAction(event -> {
            findOrderTableView.getItems().clear();
            LinkedList<Integer> resultOrders = sql_query.find_orders_for_item(Integer.parseInt(searchField.getText()));

            for (int i = 0; i < resultOrders.size(); i++) {
                int itemNumber = resultOrders.get(i).intValue();

                findOrderTableView.getItems().add(new Order(itemNumber));
            }
//            orderedItemTableView.getItems().add(new OrderedItem(itemNumber, orderQuantity));
            searchField.setText("");
        });

        return findOrderView;
    }
}
