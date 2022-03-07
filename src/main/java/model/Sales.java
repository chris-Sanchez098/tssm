package model;

public class Sales {

    private String date;
    private String id;
    private String name;
    private String customerType;
    private int phonePlan;
    private int lines;

    public Sales(String date, String id, String name, String customerType, int phonePlan, int lines) {
        this.date = date;
        this.id = id;
        this.name = name;
        this.customerType = customerType;
        this.phonePlan = phonePlan;
        this.lines = lines;
    }



    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public int getPhonePlan() {
        return phonePlan;
    }

    public void setPhonePlan(int phonePlan) {
        this.phonePlan = phonePlan;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }
}
