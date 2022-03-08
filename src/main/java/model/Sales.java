package model;

public class Sales {

    private String date;
    private String id;
    private String name;
    private String customerType;
    private int phonePlan;
    private int lines;

    /**
     * constructor
     * @param date
     * @param id
     * @param name
     * @param customerType
     * @param phonePlan
     * @param lines
     */
    public Sales(String date, String id, String name, String customerType, int phonePlan, int lines) {
        this.date = date;
        this.id = id;
        this.name = name;
        this.customerType = customerType;
        this.phonePlan = phonePlan;
        this.lines = lines;
    }

    /**
     *
     * @return date of a sale
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @return id from a customer
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return name from a customer
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return type of customer
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     *
     * @return phone plan of a sale
     */
    public int getPhonePlan() {
        return phonePlan;
    }

    /**
     *
     * @return lines linked of a sale
     */
    public int getLines() {
        return lines;
    }

}
