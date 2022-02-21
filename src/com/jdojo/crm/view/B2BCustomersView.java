/* @author Neo
*/
package com.jdojo.crm.view;

import com.jdojo.crm.model.B2BCustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;


public class B2BCustomersView extends BorderPane {

    private final B2BCustomers model;
    
    Label lblName = new Label("Name:");
    Label lblAddress = new Label("Address:");
    Label lblCity = new Label("City:");
    Label lblProvince = new Label("Province:");
    Label lblSales = new Label("Sales:");
    Label lblproductCat = new Label("Product Catergory:");
    Label lblURL = new Label("Web-Site:");
    
    TextField txtCustomerID = new TextField();
    TextField txtName = new TextField();
    TextField txtAddress = new TextField();
    TextField txtCity = new TextField();
    //combo or Choice-Box
   ObservableList<String> provinceList = FXCollections.observableArrayList("Eastern Cape", 
           "Free-State","Gauteng", "KwaZulu-Natal", "Limpopo", "Mpumalanga", "Northern-Cape",
           "North-West", "Western-Cape");
   ChoiceBox <String> provinceBox = new ChoiceBox<>(provinceList);
   TextField txtSales = new TextField();
   ObservableList<String> productCatList = FXCollections.observableArrayList("Top-Wear",
           "Jackets","T-Shirts", "Pants", "Foot-Wear", "Other");
   ChoiceBox<String> prodCategoryBox = new ChoiceBox<>(productCatList);
   TextField txtURL = new TextField();
    
   Button btnAdd = new Button("Add");
   Button btnDelete = new Button("Delete");
   Button btnViewSummary = new Button("Summary");
   Button btnSave = new Button("Save-To-File"); 
           
   TableView<B2BCustomers> table;
   
   public B2BCustomersView(B2BCustomers model){
        this.model = model;
        bindFieldsToModel();
        layOutWindow();
    }
   
    private void layOutWindow(){
        //-------TOP PANE CREATION-----------------
        Text heading = new Text("RidgeMount B2B Sales Information System");
        heading.setFont(new Font("Arial", 20));
        HBox paneTop = new HBox(heading);
        paneTop.setPadding(new Insets(20, 10, 20, 10));
 
        //------- CENTER PANE ----------------------
      
      
        //get Default List, Ensures table view is not empty (i.e. load from file)
        //CustomersTableUtil.getCustomerList()
        table = new TableView<>();
        
        table.getColumns().addAll(CustomersTableUtil.getIDColumn(),
                CustomersTableUtil.getNameColumn(),
                CustomersTableUtil.getAddressColumn(),
                CustomersTableUtil.getCityColumn(), 
                CustomersTableUtil.getProvinceColumn(),
                CustomersTableUtil.getSalesColumn(),
                CustomersTableUtil.getIndustryColumn(),
                CustomersTableUtil.getURLColumn());
        table.setMinSize(900, 400);
        HBox centerPane1 = new HBox(table);
        centerPane1.setPadding(new Insets(20, 10, 20, 10));
        //----------CENTER PANE B-----------------------------
        txtName.setPrefWidth(120);
        txtName.setPromptText("Enter Client Name"); 
        txtAddress.setPrefWidth(120);
        txtAddress.setPromptText("Enter Address");
        txtCity.setPrefWidth(120);
        txtCity.setPromptText("Enter Client's City"); 
        provinceBox.setPrefWidth(120);
        txtSales.setPrefWidth(120);
        txtSales.setPromptText("Enter Sales");
        prodCategoryBox.setPrefWidth(120);
        txtURL.setPrefWidth(120);
        txtURL.setPromptText("Enter WebSite");

        btnAdd.setPrefWidth(95);
        btnAdd.setStyle("-fx-background-color: lightseagreen;"
                + "-fx-text-fill: white;");
        btnDelete.setPrefWidth(95);
        btnDelete.setStyle("-fx-background-color: lightseagreen;"
                + "-fx-text-fill: white;");
        btnViewSummary.setPrefWidth(95);
        btnViewSummary.setStyle("-fx-background-color: lightseagreen;"
                + "-fx-text-fill: white;");
        btnSave.setPrefWidth(95);
        btnSave.setStyle("-fx-background-color: lightseagreen;"
                + "-fx-text-fill: white;");
        GridPane centerPane2 = new GridPane();
        centerPane2.setHgap(8);
        centerPane2.setVgap(8);
        centerPane2.add(lblName, 1,1);
        centerPane2.add(txtName, 1,2);
        centerPane2.add(lblAddress, 2,1);
        centerPane2.add(txtAddress,2,2); 
        centerPane2.add(lblCity,3,1); 
        centerPane2.add(txtCity,3,2);
        centerPane2.add(lblProvince,4,1);
        centerPane2.add(provinceBox,4,2);
        centerPane2.add(lblSales, 5,1);
        centerPane2.add(txtSales,5,2);
        centerPane2.add(lblproductCat,6,1);
        centerPane2.add(prodCategoryBox,6,2);
        centerPane2.add(lblURL,7,1); 
        centerPane2.add(txtURL,7,2);
        centerPane2.add(btnAdd,8,1);
        centerPane2.add(btnViewSummary, 8,2);
        centerPane2.add(btnDelete,9,1);
        centerPane2.add(btnSave,9,2);
        
        
        VBox mainCenterPane = new VBox(10);
        Label lblPrompt = new Label("\tEnter Record You Want to Add Below or "
                + "Delete A Record From Table:");
        mainCenterPane.getChildren().addAll(centerPane1, lblPrompt ,centerPane2);
        
        this.setTop(paneTop);
        
        this.setCenter(mainCenterPane);
    }
    private void bindFieldsToModel(){
        txtName.textProperty().bindBidirectional(model.nameProperty());
        txtAddress.textProperty().bindBidirectional(model.addressProperty());
        txtCity.textProperty().bindBidirectional(model.cityProperty());
        provinceBox.valueProperty().bindBidirectional(model.provinceProperty());
        txtSales.textProperty().bindBidirectional(model.salesProperty(),
                new NumberStringConverter());
        prodCategoryBox.valueProperty().bindBidirectional(model.productCategoryProperty());
        txtURL.textProperty().bindBidirectional(model.urlProperty());
    }
    public void clearFields(){
        txtName.clear();
        txtAddress.clear();
        txtCity.clear();
        provinceBox.getSelectionModel().clearSelection();
        txtSales.clear();
        prodCategoryBox.getSelectionModel().clearSelection();
        txtURL.clear();
    }
}