package model;

public class Payment {

    String name;
    String customerId;
    String basicPay;
    String extraPayMin;
    String extraPayData;
    String taxes;

    public Payment(String name, String customerId, String basicPay,
                   String extraPayMin, String extraPayData, String taxes) {
        this.name = name;
        this.customerId = customerId;
        this.basicPay = basicPay;
        this.extraPayMin = extraPayMin;
        this.extraPayData = extraPayData;
        this.taxes = taxes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBasicPay() {
        return basicPay;
    }

    public void setBasicPay(String basicPay) {
        this.basicPay = basicPay;
    }

    public String getExtraPayMin() {
        return extraPayMin;
    }

    public void setExtraPayMin(String extraPayMin) {
        this.extraPayMin = extraPayMin;
    }

    public String getExtraPayData() {
        return extraPayData;
    }

    public void setExtraPayData(String extraPayData) {
        this.extraPayData = extraPayData;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }
}
