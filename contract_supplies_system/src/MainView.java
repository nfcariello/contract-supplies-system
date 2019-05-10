import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

class MainView {

    sql sql_query = new sql();

    MainView() {
    }

    VBox getOrderView() {
        VBox orderView = new VBox(20);
        orderView.getStyleClass().add("container");
        Label orderTitle = getTitleLabel("Create Order", "title");

        DatePicker dPDateRequired = new DatePicker();
        HBox dateRequiredHBox = getFormDatePicker("Date Required");
        dateRequiredHBox.getChildren().add(dPDateRequired);

        TextField tfProjectNumber = new TextField();
        HBox projectNoHBox = getFormTextfield(10, "Project No");
        projectNoHBox.getChildren().add(tfProjectNumber);

        TextField tfContractNumber = new TextField();
        HBox contractNoHBox = getFormTextfield(10, "Contract No");
        contractNoHBox.getChildren().add(tfContractNumber);

        Button submitBtn = getSubmitButton();
        submitBtn.setOnAction(event -> {
            LocalDate dateRequired = dPDateRequired.getValue();
            String projectNumber = tfProjectNumber.getText();
            String contractNumber = tfContractNumber.getText();

            if (!projectNumber.isEmpty() && !contractNumber.isEmpty()) {
                tfProjectNumber.setText("");
                tfContractNumber.setText("");
            }
        });

        orderView.getChildren().add(orderTitle);
        orderView.getChildren().add(dateRequiredHBox);
        orderView.getChildren().add(projectNoHBox);
        orderView.getChildren().add(contractNoHBox);
        orderView.getChildren().add(submitBtn);
        return orderView;
    }

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

    HBox getFormDatePicker(String title) {
        HBox dateOfContractHBox = new HBox(10);
        Label label = getTitleLabel(title, "subTitle");
        dateOfContractHBox.getChildren().add(label);
        return dateOfContractHBox;
    }

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

    VBox getTitleAndDesc(Label itemTitle, HBox labelTextfieldHBox, Button submitBtn) {
        VBox itemView = new VBox(20);
        itemView.getStyleClass().add("container");
        itemView.getChildren().add(itemTitle); // 0 - Label
        itemView.getChildren().add(labelTextfieldHBox);  // 1 - Textfield
        itemView.getChildren().add(submitBtn); // 2 - Button
        return itemView;
    }

    Button getSubmitButton() {
        Button submitBtn = new Button("Submit");
        submitBtn.getStyleClass().add("pillButton");
        return submitBtn;
    }

    Label getTitleLabel(String text, String css) {
        Label projectTitle = new Label(text);
        projectTitle.getStyleClass().add(css);
        return projectTitle;
    }

    HBox getFormTextfield(int spacing, String title) {
        HBox hBox = new HBox(spacing);
        Label label = getTitleLabel(title, "subTitle");
        hBox.getChildren().add(label); // 0 - Label
        return hBox;
    }
}