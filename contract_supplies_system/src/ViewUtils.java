import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import source.SummarizedPurchase;

import java.util.LinkedList;

class ViewUtils extends sql {

//    private sql sql_query = new sql();

    ViewUtils() {
    }

    VBox getSummarizePurchases() {
        VBox summarizePurchasesVBox = new VBox(10);
        summarizePurchasesVBox.getStyleClass().add("container");

        Label summarizePurchases = getTitleLabel("Purchases", "title");

        /* Table View */
        /* Table View */
        TableView summarizePurchasesTableView = new TableView();

//        Item Number
        TableColumn<String, SummarizedPurchase> contractNumberColumn = new TableColumn<>("Contract Number");
        contractNumberColumn.prefWidthProperty().bind(summarizePurchasesTableView.widthProperty().multiply(0.3));
        contractNumberColumn.setCellValueFactory(new PropertyValueFactory<>("contract_no"));

        TableColumn<String, SummarizedPurchase> itemNumberColumn = new TableColumn<>("Item Number");
        itemNumberColumn.prefWidthProperty().bind(summarizePurchasesTableView.widthProperty().multiply(0.3));
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<>("contract_no"));

        TableColumn<String, SummarizedPurchase> orderQuantityColumn = new TableColumn<>("Order Quantity");
        orderQuantityColumn.prefWidthProperty().bind(summarizePurchasesTableView.widthProperty().multiply(0.3));
        orderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("contract_no"));

        summarizePurchasesTableView.getColumns().addAll(contractNumberColumn, itemNumberColumn, orderQuantityColumn);
        summarizePurchasesTableView.setPrefSize(600, 200);

        summarizePurchasesVBox.getChildren().addAll(summarizePurchases, summarizePurchasesTableView);

        LinkedList<SummarizedPurchase> summarizedPurchasesList = summerizePurchaseMike();
        summarizePurchasesTableView.getItems().clear();
        for (int i = 0; i < summarizedPurchasesList.size(); i++) {
            int contractNumber = summarizedPurchasesList.get(i).getContract_no();
            int itemNumber = summarizedPurchasesList.get(i).getItem_no();
            int orderQuantity = summarizedPurchasesList.get(i).getOrder_qty();

            summarizePurchasesTableView.getItems().add(new SummarizedPurchase(contractNumber, itemNumber, orderQuantity));
        }


        return summarizePurchasesVBox;
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
                insert_suppliers(supplierName, supplierAddress);
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
                insert_project(projectData);
                tfProjectData.setText("");
            }
        });

        return getTitleAndDesc(projectTitle, labelTextfieldHBox, submitBtn);
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