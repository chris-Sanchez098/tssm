package views;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Customer;

public class infoCustomerController {
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfCc;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfCity;
    @FXML
    private TextField tfDepto;
    @FXML
    private TextField tfCustomerType;
    @FXML
    private TextField tfPlanId;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfGbC;
    @FXML
    private TextField tfGbS;
    @FXML
    private TextField tfMinutes;
    @FXML
    private TextField tfMessage;
    @FXML
    private TextField tfAmountMin;
    @FXML
    private TextField tfNetflix;
    @FXML
    private TextArea taDetails;

    public void initAttributes(Customer customer) {
        this.tfCc.setText(customer.getCc());
        this.tfName.setText(customer.getName());
        this.tfEmail.setText(customer.getEmail());
        this.tfAddress.setText(customer.getAddress());
        this.tfCity.setText(customer.getCity());
        this.tfDepto.setText(customer.getDepartment());
        this.tfCustomerType.setText(customer.getCustomerType());
        this.tfPlanId.setText(customer.getPhonePlanId() + "");
        this.tfPhoneNumber.setText(customer.getPhoneNumber());
        this.tfPrice.setText(customer.getPrice() + "");
        this.tfGbC.setText(customer.getGbCloud());
        this.tfGbS.setText(customer.getGbShare());
        this.tfMinutes.setText(customer.getMinutesUnLimited() + "");
        this.tfMessage.setText(customer.getMsgUnLimited() + "");
        this.tfAmountMin.setText(customer.getMinutes() +"");
        this.tfNetflix.setText(customer.getNetflix() +"");
        this.taDetails.setText(customer.getDetails());
    }

}
