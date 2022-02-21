/*@author Neo*/
package com.jdojo.crm.view;


import java.util.List;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import static javafx.geometry.Pos.CENTER_LEFT;
import static javafx.stage.Modality.APPLICATION_MODAL;

public class messageBox {
    public static void show(List<String> message, String title)
    {
        Stage stage = new Stage();
        stage.initModality(APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(250);
        
        String msg = "";
        for(String s: message){
            msg += msg + s + "\n";
        }
        
        Label lbl = new Label();
        lbl.setText(msg);
        
        Button btnOK = new Button( );
        btnOK.setText("OK");
        btnOK.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"
                + "-fx-background-color: lightseagreen;");
        btnOK.setOnAction(e->stage.close());
        HBox msgPane = new HBox(lbl);
        HBox btnPane = new HBox(btnOK);
        btnPane.setAlignment(Pos.CENTER);
        VBox pane = new VBox(20);
        pane.setPadding(new Insets(20,10,30,20));
        pane.getChildren().addAll(msgPane,btnPane);
        pane.setStyle("-fx-background-color: lightsteelblue");
        
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.showAndWait();
    }
    
}

