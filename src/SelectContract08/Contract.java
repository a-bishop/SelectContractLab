package SelectContract08;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Home
 */
public class Contract {
    private final String contractID;
    private final String originCity;
    private final String destCity;
    private final String orderItem;
    
    public Contract (String contractID, String originCity, String destCity, String orderItem) {
        this.contractID = contractID;
        this.originCity = originCity;
        this.destCity = destCity;
        this.orderItem = orderItem;
    }
    
    public String getContractID() {
        return contractID;
    }
    
    public String getOriginCity() {
        return originCity;
    }
    
    public String getDestCity() {
        return destCity;
    }
    
    public String getOrderItem() {
        return orderItem;
    }

    boolean contains(String city) {
        return (city == originCity);
    }
}

