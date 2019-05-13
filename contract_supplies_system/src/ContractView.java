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
import source.ContractedItem;
import source.OrderedItem;
import source.Supplier;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;

@SuppressWarnings("Duplicates")
class ContractView extends ViewUtils {

    private sql sql_query = new sql();

    ContractView() {

    }

    /**
     * Contract View
     */
    VBox getContractView() {
        VBox contractView = new VBox(20);
        contractView.getStyleClass().add("container");
        Label contractTitle = getTitleLabel("Create Contract", "title");

        VBox contractDetailVBox = new VBox(20);
        TextField tfSupplierNumber = new TextField();
        HBox supplierNumberHBox = getFormTextfield(10, "Supplier Number");
        supplierNumberHBox.getChildren().add(tfSupplierNumber);

        DatePicker datePicker = new DatePicker();
        HBox dateOfContractHBox = getFormDatePicker("Date of Contract");
        dateOfContractHBox.getChildren().add(datePicker);

        /*
        Contracted Items
* */
        VBox contactedItemVBox = new VBox(20);
        TableView contractedItemTableView = new TableView();
        contractedItemTableView.setEditable(true);
//        Item Number
        TableColumn<String, OrderedItem> itemNumberColumn = new TableColumn<>("Item Number");
        itemNumberColumn.prefWidthProperty().bind(contractedItemTableView.widthProperty().multiply(0.3));
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<>("itemNo"));
//        Contract Price
        TableColumn<String, OrderedItem> contractPriceColumn = new TableColumn<>("Contract Price");
        contractPriceColumn.prefWidthProperty().bind(contractedItemTableView.widthProperty().multiply(0.3));
        contractPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        // Contract Amount
        TableColumn<String, OrderedItem> contractAmountColumn = new TableColumn<>("Contract Amount");
        contractAmountColumn.prefWidthProperty().bind(contractedItemTableView.widthProperty().multiply(0.3));
        contractAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        contractedItemTableView.getColumns().addAll(itemNumberColumn, contractPriceColumn, contractAmountColumn);
        contractedItemTableView.setPrefSize(400, 200);

        TextField tfItemNumber = new TextField();
        HBox itemNumberHBox = getFormTextfield(10, "Item Number");
        itemNumberHBox.getChildren().add(tfItemNumber);

        TextField tfContactPrice = new TextField();
        HBox contactPriceHBox = getFormTextfield(10, "Contract Price");
        contactPriceHBox.getChildren().add(tfContactPrice);

        TextField tfContactAmount = new TextField();
        HBox contactAmountHBox = getFormTextfield(10, "Contact Amount");
        contactAmountHBox.getChildren().add(tfContactAmount);
        Button addBtn = new Button("Add");

        LinkedList<ContractedItem> contractedItems = new LinkedList<>();
        addBtn.setOnAction(event -> {
            int itemNumber = Integer.parseInt(tfItemNumber.getText());
            double contractPrice = Double.parseDouble(tfContactPrice.getText());
            int contractAmount = Integer.parseInt(tfContactAmount.getText());

            contractedItems.add(new ContractedItem(itemNumber, contractPrice, contractAmount));
            contractedItemTableView.getItems().add(new ContractedItem(itemNumber, contractPrice, contractAmount));
            tfItemNumber.setText("");
            tfContactPrice.setText("");
            tfContactAmount.setText("");
//            System.out.println(itemNumber + "  " + contractPrice + "  " + contractAmount);
//            System.out.println(contractedItems.size());
        });

        contactedItemVBox.getChildren().addAll(contractedItemTableView, itemNumberHBox, contactPriceHBox, contactAmountHBox, addBtn);


        Button submitBtn = getSubmitButton();
        submitBtn.setOnAction(event -> {
//       Item Number, Date of Contract, Contract Price, Contract Amount
//      SQL - Supplier Number, Date of Contract
            int itemNumber = Integer.parseInt(tfSupplierNumber.getText());
            LocalDate dateOfContract = datePicker.getValue();
            Date date = new Date(dateOfContract.toEpochDay());
//                UI, Connecting to Database
            insert_contracts(itemNumber, date, contractedItems);
//            Cleanup Data
            contractedItemTableView.getItems().clear();
            contractedItems.clear();
            tfSupplierNumber.setText("");
            tfItemNumber.setText("");
            tfContactPrice.setText("");
            tfContactAmount.setText("");
        });

        contractDetailVBox.getChildren().addAll(contractTitle, supplierNumberHBox, dateOfContractHBox);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(contractDetailVBox);
        borderPane.setRight(contactedItemVBox);

        contractView.getChildren().addAll(borderPane, submitBtn);

        return contractView;
    }

    /**
     * Get Price of Item Contract
     * */
    VBox getPriceOfItemContract() {
        //        Find View
        VBox priceOfItemView = new VBox(10);
        priceOfItemView.getStyleClass().add("container");

        Label findItemTitle = getTitleLabel("Price Of Item In Contract", "title");

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

            double result = sql_query.price_of_item_in_contract(itemNumber, contractNumber);
            resultLabel.setText(String.valueOf(result));

            itemNoTextField.setText("");
            contactNumberTextField.setText("");
        });

        return priceOfItemView;
    }


    /**
     * Get Price of Item Order
     * */
    VBox getFindSupplier() {
        //        Find View
        VBox priceOfItemView = new VBox(10);
        priceOfItemView.getStyleClass().add("container");

        Label findItemTitle = getTitleLabel("Find Supplier", "title");

        HBox searchHbox = new HBox(20);
        searchHbox.setAlignment(Pos.CENTER);

        Label contractNumberLabel = new Label("Contract Number: ");
        contractNumberLabel.setFont(Font.font("Helvetica", FontWeight.MEDIUM, 18));

        /* Item Number Textfield */
        TextField contractNumberTextField = new TextField();
        HBox.setHgrow(contractNumberTextField, Priority.ALWAYS);

        /* Search Button */
        Button searchButton = new Button("Find");
        searchButton.getStyleClass().add("searchButton");
        searchButton.setPadding(new Insets(0, 10, 0, 10));
        searchHbox.getChildren().addAll(contractNumberLabel, contractNumberTextField, searchButton);

        /* Table View */
        Label resultTitle = new Label("Result");
        resultTitle.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 24));
        resultTitle.setPadding(new Insets(50, 0, 0, 00));
        Label resultLabel = new Label("NaN");
        resultLabel.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 32));

        priceOfItemView.getChildren().addAll(findItemTitle, searchHbox, resultTitle, resultLabel);

        searchButton.setOnAction(event -> {

            int contractNumber = Integer.parseInt(contractNumberTextField.getText());

            Supplier supplier = sql_query.find_supplier_no_for_contract(contractNumber);
            if (supplier != null) {
                resultLabel.setText("Supplier Number: " + supplier.getNo()
                        + "\nSupplier Name: " + supplier.getName()
                        + "\nSupplier Address: " + supplier.getAddress());
            } else {
                resultLabel.setText("Supplier Doesn't Exist");
            }


            contractNumberTextField.setText("");
        });

        return priceOfItemView;
    }

    /**
     * Get Find Quantity Left
     * */
    VBox getFindQuantityLeftContract() {
        //        Find View
        VBox findQuantityLeftView = new VBox(10);
        findQuantityLeftView.getStyleClass().add("container");

        Label findItemTitle = getTitleLabel("Find Quantity Left", "title");

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

        /* Results */
        Label resultTitle = new Label("Result");
        resultTitle.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 24));
        resultTitle.setPadding(new Insets(50, 0, 0, 00));
        Label resultLabel = new Label("NaN");
        resultLabel.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 32));

        findQuantityLeftView.getChildren().addAll(findItemTitle, searchHbox, resultTitle, resultLabel);

        searchButton.setOnAction(event -> {

            int itemNumber = Integer.parseInt(itemNoTextField.getText());
            int contractNumber = Integer.parseInt(contactNumberTextField.getText());

            double result = sql_query.find_quantity_left(itemNumber, contractNumber);
            resultLabel.setText(String.valueOf(result));

            itemNoTextField.setText("");
            contactNumberTextField.setText("");
        });

        return findQuantityLeftView;
    }
}
