/*
 * @author Neo
 */
package com.jdojo.crm.view;

import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class confirmationBox {
    
    static Stage stage;
    static boolean btnYesClicked;
    
    public static boolean show(String message, String title, String textYes, 
            String textNo)
    {
        btnYesClicked = false;
        
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(400);
        
        Label lblMsg = new Label(message);
        
        Button btnYES = new Button(textYes);
        btnYES.setDefaultButton(true);
        btnYES.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        btnYES.setOnAction(e-> btnYES_clicked());
        
        Button btnNO = new Button(textNo);
        
        btnNO.setOnAction(e-> btnNO_clicked());
        btnNO.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        HBox paneForBtn = new HBox(20, btnYES, btnNO); //We can add CSS, make window unique
        paneForBtn.setPadding(new Insets(10));
        //paneForBtn.setStyle("-fx-background"); //cancel 
        
        VBox mainPane = new VBox(20, lblMsg, paneForBtn);
        mainPane.setPadding(new Insets(10));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setStyle("-fx-background-color: lightsteelblue");
        
        Scene scene = new Scene(mainPane);
        /*String urlString = Test.class.getClassLoader().getResource("C:\\Users\Neo\\Documents\\NetBeansProjects\\Ridgeline_Mountain_Outfits\\src\\Supplier\\simple.css").toExternalForm();
        scene.getStylesheets().add(urlString);*/
        stage.setScene(scene);
        stage.showAndWait();
        return btnYesClicked;    
    }
    
    private static void btnYES_clicked(){
        stage.close();
        btnYesClicked = true;
    }
    
    private static void btnNO_clicked(){
        stage.close();
        btnYesClicked = false;
    }
}
