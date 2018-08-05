package Gui;

import DataBaseCode.CurrencyDatabase;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.omg.CORBA.TRANSACTION_UNAVAILABLE;

import java.util.ArrayList;
import java.util.Collections;

public class Window extends Application {
    private CurrencyDatabase cd;

    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    private GridPane layout;
    private Button convertButton;

    private ChoiceBox<String> beforeList;
    private ChoiceBox<String> afterList;
    private TextArea inputText;
    private TextArea outputText;


    public Window(){
        this.cd = new CurrencyDatabase();


    }
    public void begin(){ launch(); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //set up stage and button and layout
        this.setStage(primaryStage);
        this.setPane();
        this.setInnerScene();
        this.setChoices();

        primaryStage.show();
    }

    private void setStage(Stage s){
        this.stage = s;
        this.stage.setTitle("Currency Converter");
    }

    private void setPane(){
        this.borderPane = new BorderPane();
        this.layout = new GridPane();
        this.layout.setPrefSize(250, 150);


        this.layout.setPadding(new Insets(10,10,10,10));
        this.layout.setHgap(8);
        this.layout.setVgap(10);

        this.layout.addColumn(3);
        this.layout.addRow(3);

        this.borderPane.setCenter(this.layout);
        this.borderPane.setPadding(new Insets(100, 50, 50, 50));
    }

    private void setInnerScene(){
        this.scene = new Scene(this.borderPane, 400, 300);
        this.stage.setScene(this.scene);
    }

    private void setChoices(){
        this.beforeList = new ChoiceBox<>();
        this.afterList = new ChoiceBox<>();

        ArrayList<String> names = this.cd.getNames();
        Collections.sort(names);

        Label fromLabel = new Label("From");
        GridPane.setConstraints(fromLabel, 0,0);

        Label convertLabel = new Label(">>");
        GridPane.setConstraints(convertLabel, 1,0);

        Label toLabel = new Label("To");
        GridPane.setConstraints(toLabel, 2, 0);

        this.inputText = new TextArea();
        this.inputText.setPrefSize(100, 20);
        this.inputText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (inputText.getText().length() > 12){
                    String s = inputText.getText().substring(0,12);
                    inputText.setText(s);
                }
            }
        });
        GridPane.setConstraints(this.inputText, 0, 2);

        this.outputText = new TextArea();
        this.outputText.setPrefSize(100, 20);
        this.outputText.setEditable(true);
        GridPane.setConstraints(this.outputText,2,2);

        this.beforeList.getItems().addAll(names);
        this.beforeList.setPrefSize(100, 20);
        GridPane.setConstraints(beforeList, 0, 1);

        this.afterList.getItems().addAll(names);
        this.afterList.setPrefSize(100, 20);
        GridPane.setConstraints(afterList, 2,1);

        Button b = new Button();
        b.setOnAction(e->this.buttonAction());
        b.setText("Convert");
        b.setPrefSize(75, 20);
        GridPane.setConstraints(b, 1, 1);

        this.layout.getChildren().addAll(fromLabel, toLabel, convertLabel, this.beforeList, this.afterList, b,this.inputText,this.outputText);


    }

    private void buttonAction(){
        String beforeSym = this.cd.getSymbol(this.beforeList.getValue());
        String afterSym = this.cd.getSymbol(this.afterList.getValue());

        if (beforeSym == "FAILED" || afterSym == "FAILED"){
            AlertBox.display("ERROR", "Please choose 'To' and 'From' values!!");
            return;
        }

        try{
            //Start Calculation...
        }
        catch(Exception e){
            e.printStackTrace();
        }



    }





}
