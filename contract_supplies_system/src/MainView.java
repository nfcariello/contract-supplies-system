import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import source.OrderedItem;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;

class MainView {

    sql sql_query = new sql();

    MainView() {
    }

    /**
     * Supplier View
     */
    VBox getSupplierView() {
        VBox supplierView = new VBox(20);
        supplierView.getStyleClass().add("container");
        Label supplierTitle = getTitleLabel("Create Supplier", "title");

        TextField tfSupplierName = new TextField();
        HBox supplierNameHBox = getFormTextfield(10, "Supplier Name");
        supplierNameHBox.getChildren().add(tfSupplierName); // 0 - TextField

        TextField tfSupplierAddress = new TextField();
        HBox supplierAddressHBox = getFormTextfield(10, "Supplier Address");
        supplierAddressHBox.getChildren().add(tfSupplierAddress);

        Button submitBtn = getSubmitButton();
        submitBtn.setOnAction(event -> {
            String supplierName = tfSupplierName.getText();
            String supplierAddress = tfSupplierAddress.getText();

            if (!supplierName.isEmpty() && !supplierAddress.isEmpty()) {
                sql_query.insert_suppliers(supplierName, supplierAddress);
                tfSupplierName.setText("");
                tfSupplierAddress.setText("");
            }
        });

        supplierView.getChildren().add(supplierTitle);
        supplierView.getChildren().add(supplierNameHBox);
        supplierView.getChildren().add(supplierAddressHBox);
        supplierView.getChildren().add(submitBtn);
        return supplierView;
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
                sql_query.insert_item(txItemDescription.getText());
                txItemDescription.setText("");
            }
        });

        return getTitleAndDesc(itemTitle, labelTextfieldHBox, submitBtn);
    }

    /**
     * Project View
     */
    VBox getProjectView() {
        Label projectTitle = getTitleLabel("Create Project", "title");

        TextField tfProjectData = new TextField();
        HBox labelTextfieldHBox = getFormTextfield(10, "Project Data");
        labelTextfieldHBox.getChildren().add(tfProjectData);

        Button submitBtn = getSubmitButton();
        submitBtn.setOnAction(event -> {
            String projectData = tfProjectData.getText();
            if (!projectData.isEmpty()) {
                sql_query.insert_project(projectData);
                tfProjectData.setText("");
            }
        });

        return getTitleAndDesc(projectTitle, labelTextfieldHBox, submitBtn);
    }

    /**
     * Contract View
     */
    VBox getContractView() {
        VBox contractView = new VBox(20);
        contractView.getStyleClass().add("container");
        Label contractTitle = getTitleLabel("Create Contract", "title");

        TextField tfItemNumber = new TextField();
        HBox itemNoHBox = getFormTextfield(10, "Item Number");
        itemNoHBox.getChildren().add(tfItemNumber);

        DatePicker datePicker = new DatePicker();
        HBox dateOfContractHBox = getFormDatePicker("Date of Contract");
        dateOfContractHBox.getChildren().add(datePicker);

        TextField tfContractPrice = new TextField();
        HBox contractPriceHBox = getFormTextfield(10, "Contract Price");
        contractPriceHBox.getChildren().add(tfContractPrice);

        TextField tfContractAmount = new TextField();
        HBox contractAmountHBox = getFormTextfield(10, "Contract Amount");
        contractAmountHBox.getChildren().add(tfContractAmount);

        Button submitBtn = getSubmitButton();
        submitBtn.setOnAction(event -> {
            // TODO: UI - Item Number, Date of Contract, Contract Price, Contract Amount
//     TODO: SQL - Supplier Number, Date of Contract
//            String itemNumber = tfItemNumber.getText();
//            LocalDate dateOfContract = datePicker.getValue();
//            String contractPrice = tfContractPrice.getText();
//            String contractAmount = tfContractAmount.getText();
//
//            if (!itemNumber.isEmpty() && !contractPrice.isEmpty() && !contractAmount.isEmpty()) {
//                sql_query.insert_contracts();
//            }
        });

        contractView.getChildren().add(contractTitle);
        contractView.getChildren().add(itemNoHBox);
        contractView.getChildren().add(dateOfContractHBox);
        contractView.getChildren().add(contractPriceHBox);
        contractView.getChildren().add(contractAmountHBox);
        contractView.getChildren().add(submitBtn);
        return contractView;
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
     * Vertical Box for Label, Textfield and Button
     */
    VBox getTitleAndDesc(Label itemTitle, HBox labelTextfieldHBox, Button submitBtn) {
        VBox itemView = new VBox(20);
        itemView.getStyleClass().add("container");
        itemView.getChildren().add(itemTitle); // 0 - Label
        itemView.getChildren().add(labelTextfieldHBox);  // 1 - Textfield
        itemView.getChildren().add(submitBtn); // 2 - Button
        return itemView;
    }

    /**
     * Set Button, with CSS
     */
    Button getSubmitButton() {
        Button submitBtn = new Button("Submit");
        submitBtn.getStyleClass().add("pillButton");
        return submitBtn;
    }

    /**
     * Set Title Label, with CSS
     */
    Label getTitleLabel(String text, String css) {
        Label projectTitle = new Label(text);
        projectTitle.getStyleClass().add(css);
        return projectTitle;
    }

    /**
     * Horizontal Box for Label and TextField
     */
    HBox getFormTextfield(int spacing, String title) {
        HBox hBox = new HBox(spacing);
        Label label = getTitleLabel(title, "subTitle");
        hBox.getChildren().add(label); // 0 - Label
        return hBox;
    }

    /**
     * Horizontal Box for Label and Date Picker
     */
    HBox getFormDatePicker(String title) {
        HBox dateOfContractHBox = new HBox(10);
        Label label = getTitleLabel(title, "subTitle");
        dateOfContractHBox.getChildren().add(label);
        return dateOfContractHBox;
    }
}