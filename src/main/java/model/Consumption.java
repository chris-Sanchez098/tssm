package model;

public class Consumption {
    private int minutes;
    private int msg;
    private double gbCloud;
    private double gbShare;
    private double gbData;
    private String phoneNumber;

    public Consumption(int minutes, int msg, double gbCloud, double gbShare, double gbData, String phoneNumber) {
        this.minutes = minutes;
        this.msg = msg;
        this.gbCloud = gbCloud;
        this.gbShare = gbShare;
        this.gbData = gbData;
        this.phoneNumber = phoneNumber;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getMsg() {
        return msg;
    }

    public double getGbCloud() {
        return gbCloud;
    }

    public double getGbShare() {
        return gbShare;
    }

    public double getGbData() {
        return gbData;
    }
}
