/* @author Neo
 */
package com.jdojo.crm.view;

import com.jdojo.crm.model.B2BCustomers;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomersTableUtil {
    //method used to load default test data, i.e can be altered to load data
    //from CSV textfile  
    /*public static ObservableList<B2BCustomers>getCustomerList(){
        B2BCustomers c1 = new B2BCustomers("Francos Place", "34 Church Street", 
                "Pretoria", "Gauteng", 45000.00, "Top-Wear", "www.francos.com");
        return FXCollections.<B2BCustomers>observableArrayList(c1);
    }*/
    
    public static TableColumn<B2BCustomers,Integer>getIDColumn(){
        TableColumn<B2BCustomers,Integer> customerIDCol = 
                new TableColumn<>("Cust ID");
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerIDCol.setPrefWidth(60);
        return customerIDCol;
    }
    public static TableColumn<B2BCustomers,Integer>getNameColumn(){
        TableColumn<B2BCustomers,Integer> customerNameCol = 
                new TableColumn<>("Comapany Name");
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerNameCol.setPrefWidth(120);
        return customerNameCol;
    }
    public static TableColumn<B2BCustomers,Integer>getAddressColumn(){
        TableColumn<B2BCustomers,Integer> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.setPrefWidth(120);
        return addressCol;
    }
    public static TableColumn<B2BCustomers,Integer>getCityColumn(){
        TableColumn<B2BCustomers,Integer> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        cityCol.setPrefWidth(120);
        return cityCol;
    }
    public static TableColumn<B2BCustomers,Integer>getProvinceColumn(){
        TableColumn<B2BCustomers,Integer> provinceCol = 
                new TableColumn<>("Province");
        provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));
        provinceCol.setPrefWidth(120);
        return provinceCol;
    }
    public static TableColumn<B2BCustomers,Integer>getSalesColumn(){
        TableColumn<B2BCustomers,Integer> salesCol = new TableColumn<>("Sales");
        salesCol.setCellValueFactory(new PropertyValueFactory<>("sales"));
        salesCol.setPrefWidth(120);
        return salesCol;
    } 
    public static TableColumn<B2BCustomers,Integer>getIndustryColumn(){
        TableColumn<B2BCustomers,Integer> productCategory = 
                new TableColumn<>("Product Category");
        productCategory.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        productCategory.setPrefWidth(120);
        return productCategory;
    }
    public static TableColumn<B2BCustomers,Integer>getURLColumn(){
        TableColumn<B2BCustomers,Integer> urlCol = new TableColumn<>("Web-Site");
        urlCol.setCellValueFactory(new PropertyValueFactory<>("url"));
        urlCol.setPrefWidth(120);
        return urlCol;
    }
}
