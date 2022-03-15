package model;

public class Pay {
    private Integer minutes;
    private Double gbCloud;
    private Double gbShare;
    private Double gbData;
    private Boolean unlimited;
    private Integer price;
    private Double tCloud;
    private Double tShare;
    private Double tData;
    private Integer tMinutes;
    private Integer tMsg;
    private Double extraMin = 0.0;
    private Double extraGb = 0.0;
    private Double taxes;
    private Double total;
    private String name;

    public Pay(Integer minutes, Double gbCloud, Double gbShare, Double gbData,
               Boolean unlimited, Integer price, Double tCloud, Double tShare,
               Double tData, Integer tMinutes, Integer tMsgn, String name) {
        this.minutes = minutes;
        this.gbCloud = gbCloud;
        this.gbShare = gbShare;
        this.gbData = gbData;
        this.unlimited = unlimited;
        this.price = price;
        this.tCloud = tCloud;
        this.tShare = tShare;
        this.tData = tData;
        this.tMinutes = tMinutes;
        this.tMsg = tMsg;
        this.name = name;
        if(!unlimited){
            if(tMinutes > minutes){
                extraMin = (double) ((tMinutes - minutes) * price / minutes);
            }
            if(tCloud > gbCloud){
                extraGb += Math.ceil((tCloud / gbCloud) - 1) * price / 2;
            }
            if(tData > gbData){
                extraGb += Math.ceil((tData / gbData) - 1) * price / 2;
            }
            if(tShare > gbShare){
                extraGb += Math.ceil((tShare / gbShare) - 1) * price / 2;
            }
        }
        taxes = 0.19 * (price + extraMin + extraGb);
        total = taxes + price + extraMin + extraGb;
    }


    public Integer getMinutes() {
        return minutes;
    }

    public Double getGbCloud() {
        return gbCloud;
    }

    public Double getGbShare() {
        return gbShare;
    }

    public Double getGbData() {
        return gbData;
    }

    public Boolean isUnlimited() {
        return unlimited;
    }

    public Double getPrice() {
        return Math.round(price * 100) / 100.00;
    }

    public Double getTCloud() {
        return tCloud;
    }

    public Double getTShare() {
        return tShare;
    }

    public Double getTData() {
        return tData;
    }

    public Integer getTMinutes() {
        return tMinutes;
    }

    public Integer getTMsg() {
        return tMsg;
    }

    public Double getExtraMin() {
        return Math.round(extraMin * 100) / 100.00;
    }

    public Double getExtraGb() {
        return Math.round(extraGb * 100) / 100.00 ;
    }

    public Double getTaxes() {
        return Math.round(taxes * 100) / 100.00;
    }

    public Double getTotal(){
        return Math.round(total * 100) / 100.00;
    }

    public String getName() {
        return name;
    }
}
