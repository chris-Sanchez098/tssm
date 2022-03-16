package model;

public class Pay {
    public Integer minutes;
    public Double gbCloud;
    public Double gbShare;
    public Double gbData;
    public Boolean unlimited;
    public Double price;
    public Double tCloud;
    public Double tShare;
    public Double tData;
    public Integer tMinutes;
    public Integer tMsg;
    public Double extraMin = 0.0;
    public Double extraGb = 0.0;
    public Double taxes;
    public Double total;
    public String name;
    public String dateInit;
    public String dateFinal;
    public String id;
    public Double late;

    public Pay(Integer minutes, Double gbCloud, Double gbShare, Double gbData,
               Boolean unlimited, Double price, Double tCloud, Double tShare,
               Double tData, Integer tMinutes, Integer tMsgn, String name, String dateInit, String dateFinal, String id) {
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
        this.tMsg = tMsgn;
        this.name = name;
        this.dateInit = dateInit;
        this.dateFinal = dateFinal;
        this.id = id;
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


    public Double getPrice() {
        return price;
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

    public String getDateInit() {
        return dateInit;
    }

    public String getDateFinal() {
        return dateFinal;
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

    public Boolean getUnlimited() {
        return unlimited;
    }

    public Double gettCloud() {
        return tCloud;
    }

    public Double gettShare() {
        return tShare;
    }

    public Double gettData() {
        return tData;
    }

    public Integer gettMinutes() {
        return tMinutes;
    }

    public Integer gettMsg() {
        return tMsg;
    }

    public String getId() {
        return id;
    }

    public Double getLate() {
        return Math.round(late * 100) / 100.00;
    }

    public void setLate(Double late) {
        taxes = 0.19 * (price + extraMin + extraGb + late);
        this.late = late;
        total = taxes + price + extraMin + extraGb + late;
    }
}
