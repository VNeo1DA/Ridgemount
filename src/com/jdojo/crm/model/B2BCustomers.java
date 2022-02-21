/** @author Neo*/
package com.jdojo.crm.model;

//imports
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class B2BCustomers {
    private final ReadOnlyIntegerWrapper customerID = 
           new ReadOnlyIntegerWrapper (this, "customerID",
                   customerSequence.incrementAndGet());
    private final StringProperty name = new SimpleStringProperty(this,
            "name", null);
    private final StringProperty address = new SimpleStringProperty(this,
            "address", null);
    private final StringProperty city = new SimpleStringProperty(this,
            "city", null);
    private final StringProperty province = new SimpleStringProperty(this,
            "province", null);
    private final DoubleProperty sales = new SimpleDoubleProperty(this,
            "sales", 0);
    private final StringProperty productCategory = 
            new SimpleStringProperty(this, "productCategory", null);
    private final StringProperty url = new SimpleStringProperty(this,
            "url", null);
    //keeps track of last generated business customer ID
    private static AtomicInteger customerSequence = new AtomicInteger(0);
    
    public B2BCustomers(){this(null,null,null,null,0.0,null,null);}
    
    public B2BCustomers(String nameP, String addressP, String cityP,
            String provinceP,double salesP, String comType, String urlP){
        this.name.set(nameP);
        this.address.set(addressP);
        this.city.set(cityP);
        this.province.set(provinceP);
        this.sales.set(salesP);
        this.productCategory.set(comType);
        this.url.set(urlP);
    }
    
    //Business-to-Business Customer ID property
    public final int customerID(){ return customerID.get(); }
    
    public final ReadOnlyIntegerProperty customerIDProperty(){
        return customerID.getReadOnlyProperty();
    }
    //(Column 1: Name -> accessors, mutators, reference)
    public final String getName(){
        return name.get();
    }
    public void setName(String nameP){
        nameProperty().set(nameP);
    }
    public final StringProperty nameProperty(){
        return name;
    }
    
    //Colum 2: " """
    public final String getAddress(){
        return address.get();
    }
    public void setAddress(String addressP){
        addressProperty().set(addressP);
    }
    public final StringProperty addressProperty(){
        return address;
    }
    
    //Col 3
    public final String getCity(){
        return city.get();
    }
    public void setCity(String cityP){
        cityProperty().set(cityP);
    }
    public final StringProperty cityProperty(){
        return city;
    }
    
    //Col 4
    public final String getProvince(){
        return province.get();
    }
    public void setProvince(String provinceP){
        provinceProperty().set(provinceP);
    }
    public final StringProperty provinceProperty(){
        return province;
    }
    
    //Col 5,Sales
    public final double getSales(){
        return sales.get();
    }
    public void setSales(double salesP){
        salesProperty().set(salesP);
    }
    public final DoubleProperty salesProperty(){
        return sales;
    }
    
    //Col 6: Company-Type
    public final String getProductCategory(){
        return productCategory.get();
    }
    public void setCompanyType(String companyTypeP){
        productCategoryProperty().set(companyTypeP);
    }
    public final StringProperty productCategoryProperty(){
        return productCategory;
    }
    
    //Col 7: Web-Site
    public final String getURL(){
        return url.get();
    }
    public void setURL(String urlP){
        urlProperty().set(urlP);
    }
    public final StringProperty urlProperty(){
        return url;
    }
    
    /*Domain Specific business rule; verify B2Bcustomer*/
    public boolean isValidCustomer(List<String> errorLog){
        return isValidCustomer(this, errorLog);
    }
    public boolean isValidCustomer(B2BCustomers c, List<String> errorLog){
        boolean validBizCust = true;
        String msg = "";
        
        String b2bCustName = c.name.get();
        if(b2bCustName == null || b2bCustName.trim().length()==0){
            msg += "Name of business must be actual name with\n";
            msg += "a minimum of 1 character.\n";
            validBizCust = false;
        }
        String custAddress = c.address.get();
        if(custAddress == null || custAddress.trim().length()==0){
            msg += "Address must be a actual address location.\n";
            validBizCust = false;
        }
        String custCity = c.city.get();
        if(custCity == null || custCity.trim().length()==0){
            msg += "Supply actual city name of customer.\n";
            validBizCust = false;
        }
        //Although Province will use ChoiceBox we check to see if user actually
        //made a choice(selection), default is null
        String custProvince = c.province.get();
        if(custProvince == null){
            msg+= "No Province was selected of location of customer.\n";
            validBizCust = false;
        }
        double salesValue = c.sales.get();
        if(salesValue <= 0){
            msg+="Sales figure should be a numberic value, no zeros\n";
            msg+= "or negative values are allowed.\n";
            validBizCust = false;
        }
        String custCompType = c.productCategory.get();
        if(custCompType == null){//no choice in choiceBox
            msg+="No Product Category was selected.\n";
            validBizCust = false;
        }
        String custWebSite = c.url.get();
        if(custWebSite == null || custWebSite.trim().length()==0){
            msg+="Company WebSite was not supplied.\n";
            validBizCust = false;
        }
        errorLog.add(msg);
        return validBizCust;
    }

    @Override
    public String toString(){
        //return as Comma Separated Value(CSV)
        return name.get() + "," + address.get() + "," + city.get() +
                "," + province.get() + "," + sales.get() + "," +
                productCategory.get() + "," + url.get();
    }
}
