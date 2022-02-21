package com.jdojo.crm.main;

/* @author Neo*/

import com.jdojo.crm.model.B2BCustomers;
import com.jdojo.crm.view.B2BCustomersPresenter;
import com.jdojo.crm.view.B2BCustomersView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class B2BCustomersApp extends Application {
    public static void main(String args[]){
        Application.launch(args);
    }
    Stage stage;
    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;
        B2BCustomers model = new B2BCustomers();
        B2BCustomersView view = new B2BCustomersView(model);
        
        Scene scene = new Scene(view);
        B2BCustomersPresenter presenter = new B2BCustomersPresenter(model, view);
        view.setStyle("-fx-background-color: lightsteelblue; "
                + "-fx-font-family: Arial");
        primaryStage.setScene(scene);
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(visualBounds.getMinX());
        primaryStage.setY(visualBounds.getMinY());
        primaryStage.setWidth(visualBounds.getWidth());
        primaryStage.setHeight(visualBounds.getHeight());
        primaryStage.setTitle("RidgeMount Sales Management");
        primaryStage.setOnCloseRequest(e->
        {
            e.consume();
            saveAndClose();
        });
        stage.show();
    }    
    private void saveAndClose(){
        boolean confirmation = false;
        String warningNote = "Are You Sure You Want To Quit ?\n\n" +
                "Please ensure you save your information\n" +
                "to a text file before closing the application.\n"+
                "Use the Save-To-File Button feature below\n" +
                "to ensure your work is saved.";
        confirmation = confirmationBox.show(warningNote,"Confirm", "Yes", "No");
        if(confirmation){        
            stage.close();
        }
    }
}
