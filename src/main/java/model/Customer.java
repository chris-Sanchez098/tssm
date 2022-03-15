package model;

public class Customer {
    private String cc;
    private String name;
    private String email;
    private int addressId;
    private String address;
    private String city;
    private String department;
    private int customerTypeId;
    private String customerType;
    private int phonePlanId;
    private String phoneNumber;
    private double price;
    private String gbCloud;
    private String gbShare;
    private boolean minutesUnLimited;
    private boolean msgUnLimited;
    private int minutes;
    private int netflix;
    private String details;

    public Customer(String cc, String name, String email, int addressId, String address, String city, String department,
                    int customerTypeId, String customerType, int phonePlanId, String phoneNumber, double price,
                    String gbCloud, String gbShare, boolean minutesUnLimited, boolean msgUnLimited, int minutes,
                    int netflix, String details) {
        this.cc = cc;
        this.name = name;
        this.email = email;
        this.addressId = addressId;
        this.address = address;
        this.city = city;
        this.department = department;
        this.customerTypeId = customerTypeId;
        this.customerType = customerType;
        this.phonePlanId = phonePlanId;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.gbCloud = gbCloud;
        this.gbShare = gbShare;
        this.minutesUnLimited = minutesUnLimited;
        this.msgUnLimited = msgUnLimited;
        this.minutes = minutes;
        this.netflix = netflix;
        this.details = details;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(int customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public int getPhonePlanId() {
        return phonePlanId;
    }

    public void setPhonePlanId(int phonePlanId) {
        this.phonePlanId = phonePlanId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGbCloud() {
        return gbCloud;
    }

    public void setGbCloud(String gbCloud) {
        this.gbCloud = gbCloud;
    }

    public String getGbShare() {
        return gbShare;
    }

    public void setGbShare(String gbShare) {
        this.gbShare = gbShare;
    }

    public boolean isMinutesUnLimited() {
        return minutesUnLimited;
    }

    public void setMinutesUnLimited(boolean minutesUnLimited) {
        this.minutesUnLimited = minutesUnLimited;
    }

    public boolean isMsgUnLimited() {
        return msgUnLimited;
    }

    public void setMsgUnLimited(boolean msgUnLimited) {
        this.msgUnLimited = msgUnLimited;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getNetflix() {
        return netflix;
    }

    public void setNetflix(int netflix) {
        this.netflix = netflix;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean getMsgUnLimited(){
        return msgUnLimited;
    }

    public boolean getMinutesUnLimited(){
        return minutesUnLimited;
    }

    /**
     * Turn phonePlan to a short description
     * @return String
     */
    public String detailsMobilePlan(){
        switch (phonePlanId){
            case 1:
                return "Plan 15 GB";
            case 2:
                return "Plan 25 GB";
            case 3:
                return "Plan 40 GB";
            default:
                return "Ilimitado";
        }
    }
}
