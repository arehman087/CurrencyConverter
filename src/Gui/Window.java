package Gui;

import DataBaseCode.CurrencyDatabase;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public class Window extends Application {
    private CurrencyDatabase cd;

    private Stage stage;
    private Scene scene;
    private BorderPane borderPane;
    private GridPane layout;
    private Button convertButton;
    private VBox titleHolder;

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

    /**
     * sets the stage and title
     * @param s
     */
    private void setStage(Stage s){
        this.stage = s;
        this.stage.setTitle("Currency Converter");
    }

    /**
     * Organises the panes to hold different objects
     */
    private void setPane(){
        this.borderPane = new BorderPane();
        this.layout = new GridPane();
        this.layout.setPrefSize(250, 150);
        this.titleHolder = new VBox();
        this.titleHolder.setPrefSize(250, 100);
        this.titleHolder.setPadding(new Insets(50, 50, 50, 100));

        this.layout.setPadding(new Insets(10,10,10,10));
        this.layout.setHgap(8);
        this.layout.setVgap(10);

        this.layout.addColumn(3);
        this.layout.addRow(3);

        this.borderPane.setTop(titleHolder);
        this.borderPane.setCenter(this.layout);
        this.borderPane.setPadding(new Insets(0, 50, 50, 50));
    }

    /**
     * sets the scene
     */
    private void setInnerScene(){
        this.scene = new Scene(this.borderPane, 400, 300);
        this.stage.setScene(this.scene);
    }

    /**
     * set up all the buttons, dropdown menus, and labels
     */
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

        this.convertButton = new Button();
        this.convertButton.setOnAction(e->this.buttonAction());
        this.convertButton.setText("Convert");
        this.convertButton.setPrefSize(75, 20);
        GridPane.setConstraints(this.convertButton, 1, 1);

        Label title = new Label("Currency Conversion");
        title.setScaleX(2);
        title.setScaleY(2);
        this.titleHolder.getChildren().add(title);

        this.layout.getChildren().addAll(fromLabel, toLabel, convertLabel, this.beforeList, this.afterList, this.convertButton,this.inputText,this.outputText);


    }

    /**
     * action method for the convert button, puts the reult of the currency
     * into a the output text.
     */
    private void buttonAction(){
        String beforeSym = this.cd.getSymbol(this.beforeList.getValue());
        String afterSym = this.cd.getSymbol(this.afterList.getValue());

        if (beforeSym == "FAILED" || afterSym == "FAILED"){
            AlertBox.display("ERROR", "Please choose 'To' and 'From' values!!");
            return;
        }

        try{

            try{
                Double.parseDouble(this.inputText.getText());
            }
            catch (Exception ex){
                AlertBox.display("ERROR", "PLEASE ENTER A VALID VALUE INTO TEXTBOX");
                return;
            }

            double from = this.cd.getValues(beforeSym);
            double to = this.cd.getValues(afterSym);


            double res = this.cd.calculateCurrency(from, to);

            BigDecimal bda = new BigDecimal(res);
            BigDecimal bdb = new BigDecimal(this.inputText.getText());

            BigDecimal bdc = bda.multiply(bdb).setScale(2, BigDecimal.ROUND_HALF_UP);
            this.outputText.setText(bdc.toString());
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }



    }





}
