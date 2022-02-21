package model;

public class Customer {
    private int customer_id;
    private String cc;
    private String name;
    private String email;
    private int addressId;
    private int customerTypeId;
    private int phonePlanId;

    public Customer(int customer_id, String cc, String name, String email, int addressId, int customerTypeId, int phonePlanId) {
        this.customer_id = customer_id;
        this.cc = cc;
        this.name = name;
        this.email = email;
        this.addressId = addressId;
        this.customerTypeId = customerTypeId;
        this.phonePlanId = phonePlanId;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public int getPhonePlanId() {
        return phonePlanId;
    }

    public void setPhonePlanId(int phonePlanId) {
        this.phonePlanId = phonePlanId;
    }
}
