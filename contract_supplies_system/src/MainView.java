import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainView {

    public MainView() {
    }

    VBox getOrderView() {
        VBox orderView = new VBox(20);
        orderView.getStyleClass().add("container");
        Label orderTitle = getTitleLabel("Create Order", "title");
        HBox dateRequiredHBox = getFormDatePicker("Date Required");
        HBox projectNoHBox = getFormTextfield(10, "Project No");
        HBox contractNoHBox = getFormTextfield(10, "Contract No");
        Button submitBtn = getSubmitButton();

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
        HBox supplierNameHBox = getFormTextfield(10, "Supplier Name");
        HBox supplierAddressHBox = getFormTextfield(10, "Supplier Address");
        Button submitBtn = getSubmitButton();

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
        HBox itemNoHBox = getFormTextfield(10, "Item Number");
        HBox dateOfContractHBox = getFormDatePicker("Date of Contract");
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

    HBox getFormDatePicker(String title) {
        HBox dateOfContractHBox = new HBox(10);
        Label label = getTitleLabel(title, "subTitle");
        DatePicker datePicker = new DatePicker();
        dateOfContractHBox.getChildren().add(label);
        dateOfContractHBox.getChildren().add(datePicker);
        return dateOfContractHBox;
    }

    VBox getProjectView() {
        Label projectTitle = getTitleLabel("Create Project", "title");
        HBox labelTextfieldHBox = getFormTextfield(10, "Project Data");
        return getTitleAndDesc(projectTitle, labelTextfieldHBox);
    }

    VBox getItemView() {
        Label itemTitle = getTitleLabel("Create Item", "title");
        HBox labelTextfieldHBox = getFormTextfield(10, "Item Description");
        return getTitleAndDesc(itemTitle, labelTextfieldHBox);
    }

    VBox getTitleAndDesc(Label itemTitle, HBox labelTextfieldHBox) {
        Button submitBtn = getSubmitButton();

        VBox itemView = new VBox(20);
        itemView.getStyleClass().add("container");
        itemView.getChildren().add(itemTitle);
        itemView.getChildren().add(labelTextfieldHBox);
        itemView.getChildren().add(submitBtn);
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
        TextField textField = new TextField();
        hBox.getChildren().add(label);
        hBox.getChildren().add(textField);
        return hBox;
    }
}