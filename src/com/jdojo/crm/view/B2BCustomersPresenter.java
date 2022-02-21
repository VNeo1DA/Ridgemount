/* @author Neo
 */
package com.jdojo.crm.view;

import com.jdojo.crm.model.B2BCustomers;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

public class B2BCustomersPresenter {
    private final B2BCustomers model;
    private final B2BCustomersView view;
    //observable list needed for report purposes and summarizing data 
    private static ObservableList<B2BCustomers>list = 
            FXCollections.<B2BCustomers>observableArrayList();
 
    public B2BCustomersPresenter(B2BCustomers modelP, B2BCustomersView viewP){
        this.model = modelP;
        this.view = viewP;
        this.attachEvents();
    }
    
    private void attachEvents(){
        view.btnDelete.setOnAction(e->btnDelete_Clicked());
        view.btnAdd.setOnAction(e->btnAdd_Clicked());
        view.btnSave.setOnAction(e->saveData());
        view.btnViewSummary.setOnAction(e->summarize_Clicked());
    }
    
    public void btnAdd_Clicked(){
        String name = model.getName();
        String address = model.getAddress();
        String city = model.getCity();
        String province = model.getProvince();
        double sales = model.getSales();
        String prodCategory = model.getProductCategory();
        String url = model.getURL();
        B2BCustomers customer = new B2BCustomers(name, address,city, province,
                sales,prodCategory, url); 

        //check duplicated row
        //Business Rule: The Same Address of Customer Cannot Order Same Category 
        //of Goods In Same Day
        boolean isDuplicated = false;
        for(B2BCustomers existingCust: list){
            if(existingCust.getAddress().equalsIgnoreCase(customer.getAddress())
                    && existingCust.getProductCategory().
                            equals(customer.getProductCategory()))
                isDuplicated = true;
        }
 
        List<String>messageLog = new ArrayList<>();
   
        boolean isCustomerValid = model.isValidCustomer(messageLog);
        // add only if all fields are valid & no duplicated rows
        if(isCustomerValid && !(isDuplicated)){
                view.table.getItems().add(customer); 
                list.add(customer);
                
                messageLog.add("Row has been added successfully");
                messageBox.show(messageLog, "Row Successfully Added");
                view.clearFields();
          }else if(!(isCustomerValid)){
                messageBox.show(messageLog, "Missing Data");
          }else if(isDuplicated){
                messageLog.add("Row Already Exists!\n" +
                        "Please Add New Row That Has Not Been Added Before.");
                messageBox.show(messageLog, "Warning: Row Duplication");
          }  
    }

    public void btnDelete_Clicked(){
        ObservableList<B2BCustomers> sel, items;
        items = view.table.getItems();
        sel = view.table.getSelectionModel().getSelectedItems();
        
        List<String>errorLog = new ArrayList<>();
        errorLog.add("No Row Has Been Selected For Deletion\n" +
                "Please Select Minimum of 1 Row If You Wish\n" +
                "to Delete A Record");
        boolean reallyDelete = false;
        if(sel.isEmpty()== false){
            reallyDelete = confirmationBox.show("Are you sure you want to "
                    + "Delete record ?","Confirmation", "Yes", "No");
        }
        else{
            messageBox.show(errorLog, "No Row(s) Selected");
       }

        if(reallyDelete)
        {
            for(B2BCustomers c : sel)
            items.remove(c);
        }
        list.setAll(items);
        System.out.println("list after deletion:");
        System.out.println(list);
    }

    public void saveData(){
        List<String>messageLog = new ArrayList<>();
        //stream comes here
        String fileName = "out.txt";
        PrintWriter outputStream = null;
        if(list.isEmpty()){
            String errorMsg = "";
            errorMsg += "There is no records to save from the table:\n";
            errorMsg += "Ensure that rows are added before attempting\n";
            errorMsg += "to save your information to a text file.";
            messageLog.add(errorMsg);
            messageBox.show(messageLog, "No Records To Save");
        }else{
            try{
                outputStream = 
                        new PrintWriter(new FileOutputStream(fileName,true));                
            }catch(FileNotFoundException e){
                messageLog.add("Error opening file " + fileName + ".txt");
                messageBox.show(messageLog, "Text File Not Found");
            }
            //append contents to text file if file already exists
            for(B2BCustomers record: list){
                String row = record.toString();
                outputStream.println(row + "\n");
            }
            outputStream.close();
            messageLog.add("Customer records have been added to text file " 
                    + fileName);
            messageBox.show(messageLog,"Records Successfully Saved");
        }
    }
    //btnSummarize event Handler & its helper methods
    public void summarize_Clicked(){
        //---------Pane A ---------------------------
        //Verify if table list NOT empty, if so show Dialog
        ObservableList<PieChart.Data> chartData = getPieChartData();
       
        Label lblChartInfo;
        VBox root;
        if(chartData.isEmpty()){
            lblChartInfo = new Label("No Summary Available:\n"
                    + "List of Customers is Empty.\n" + 
                    "Close This Window And Add Records To Table");
            lblChartInfo.setStyle("fx-text-fill: black;-fx-font-size: 10pt;"
                    + "-fx-font-weight:bold;");
            root = new VBox();
            root.getChildren().add(lblChartInfo);
            root.setAlignment(Pos.CENTER_LEFT);
        }else{
            PieChart chart = new PieChart();
            chart.setTitle("Summarized Data");
            chart.setLegendSide(Side.LEFT);
            chart.setData(chartData);
            //add Tooltip for pie slices
            this.addTooltipToSlices(chart);
            //Add pieChart & labelChartInfo to root pane
            //Will display PieChart & quick summary of statistics
            String salesStats = summarizeSales();
            lblChartInfo = new Label(salesStats);
    
            StackPane piePane = new StackPane(chart);
            StackPane summaryPane = new StackPane(lblChartInfo);
            summaryPane.setStyle("-fx-border-color: black,black;"
                    + "-fx-border-width: 1,1;-fx-border-style:solid centered,"
                    + " solid centered;-fx-background-color:lightblue;");
            summaryPane.setPadding(new Insets(10,10,10,10));
            root = new VBox();
            root.getChildren().addAll(piePane, summaryPane);
        }

        root.setPadding(new Insets(20,10,20,10));
        Scene scene = new Scene(root); 
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.setTitle("Summary");
        stage.sizeToScene();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
    private ObservableList<PieChart.Data> getPieChartData(){
        double sumTopWear = 0.0, sumJackets = 0.0, sumTShirts = 0.0,
                sumPants = 0.0,sumFootwear = 0.0, sumOther = 0.0;
       ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
       
       //Aggregate Sales figures per Product Category
        List<String>errorMsg = new ArrayList<>();
        if(list.size()==0){ 
            errorMsg.add("Table Has No Rows, No Summary Available");
            messageBox.show(errorMsg, "No Records To Summarize");
        }else{
            for(B2BCustomers custData: list){
                if(custData.getProductCategory().equals("Top-Wear"))
                    sumTopWear = sumTopWear + custData.getSales();
                if(custData.getProductCategory().equals("Jackets"))
                    sumJackets = sumJackets + custData.getSales();
                if(custData.getProductCategory().equals("T-Shirts"))
                    sumTShirts = sumTShirts + custData.getSales();
                if(custData.getProductCategory().equals("Pants"))
                    sumPants = sumPants + custData.getSales();
                if(custData.getProductCategory().equals("Foot-Wear"))
                    sumFootwear = sumFootwear + custData.getSales();
                if(custData.getProductCategory().equals("Other"))
                    sumOther = sumOther + custData.getSales();
         }
            
	pieData.add(new PieChart.Data("Top-Wear",sumTopWear));
	pieData.add(new PieChart.Data("Jackets & Jerseys", sumJackets));
	pieData.add(new PieChart.Data("T-Shirts", sumTShirts));
	pieData.add(new PieChart.Data("Pants", sumPants));
	pieData.add(new PieChart.Data("Foot-Wear", sumFootwear));
	pieData.add(new PieChart.Data("Other", sumOther));
        }
	return pieData; 
    }//end getPieChartData()
    
    private void addTooltipToSlices(PieChart chartPar)
    {
        double totalPieValue = 0.0;
        for(PieChart.Data d: chartPar.getData()){
            totalPieValue += d.getPieValue();
        }
        
        for(PieChart.Data data: chartPar.getData()){
            Node sliceNode = data.getNode();
            double pieValue = data.getPieValue();
            double percentPieValue = (pieValue/totalPieValue) * 100;
            
            String msg = data.getName() + " = " + pieValue +
                    " (" + String.format("%.2f", percentPieValue) + "%)";
            Tooltip tt = new Tooltip(msg);
            tt.setStyle("-fx-background-color: yellow;" +
                    "-fx-text-fill: black;");
            Tooltip.install(sliceNode, tt);
        }
    }
    
    private String summarizeSales(){
         String summary = ""; //to hold summary repart details
        double sumTopWear = 0.0, sumJackets = 0.0, sumTShirts = 0.0,
                sumPants = 0.0,sumFootwear = 0.0, sumOther = 0.0;
        double totalSales = 0;
        if(list.size()==0){ 
                summary += "No Data has been summarized.\n"
                        + "Table Has No Contents/Records.";
                return summary;
        }else{
            for(B2BCustomers custData: list){
                if(custData.getProductCategory().equals("Top-Wear"))
                    sumTopWear = sumTopWear + custData.getSales();
                if(custData.getProductCategory().equals("Jackets"))
                    sumJackets = sumJackets + custData.getSales();
                if(custData.getProductCategory().equals("T-Shirts"))
                    sumTShirts = sumTShirts + custData.getSales();
                if(custData.getProductCategory().equals("Pants"))
                    sumPants = sumPants + custData.getSales();
                if(custData.getProductCategory().equals("Foot-Wear"))
                    sumFootwear = sumFootwear + custData.getSales();
                if(custData.getProductCategory().equals("Other"))
                    sumOther = sumOther + custData.getSales();               
            }
        }
        
        totalSales = totalSales + sumTopWear + sumJackets + sumTShirts +
                sumPants + sumFootwear + sumOther;
        double[] prodCatSubTotals = {sumTopWear, sumJackets, sumTShirts ,
            sumPants, sumFootwear, sumOther} ;

	String[] prodCategories = {"Top-Wear","Jackets","T-Shirts","Pants",
            "Foot-Wear","Others"};      
	Stack<String> stack = new Stack<>();
        //store 1st element as highest, to be compared with subsequent elements
	double highestTotal = prodCatSubTotals[0];
        //also store its category description as the top category
	stack.push(prodCategories[0]);
	int sizeOfArr = prodCatSubTotals.length;
        
        //Determine the best performing productCategories,
        //and store it(or them)in a Stack
	for(int i = 1; i < sizeOfArr; i++)
	{
		if(prodCatSubTotals[i] > highestTotal)
		{
			stack.clear();
			stack.push(prodCategories[i]);
			highestTotal = prodCatSubTotals[i];
		}else if(prodCatSubTotals[i] == highestTotal){
			stack.push(prodCategories[i]);
		}
	}
        
        String top = "";
        while(!(stack.isEmpty()))
        {
            top += "-> " + stack.pop();
            top += "\n";
        }
        
	int numberOfCust = list.size();
        DecimalFormat df = new DecimalFormat("#.##");
 	double averageSales = totalSales/numberOfCust;
        
	summary += "Number of Customers: " + numberOfCust + "\n";
	summary += "Total Revenue: R" + df.format(totalSales) + "\n";
	summary += "Average Sales: R" + df.format(averageSales) + "\n";
	summary += "Top Selling Product Category/Categories:\n";
	summary += top;
        summary += "With Sales of R" + df.format(highestTotal);
        
        return summary; 
    }

}