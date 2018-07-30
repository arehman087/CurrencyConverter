package Gui;
import API.ParsedCurrencyData;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Window extends Application {

    private ParsedCurrencyData p;           //Currency API info

    public Window(){
        try{
            throw new Exception("TEST");

        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }

    public void begin(){
        launch();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Currency Converter");

        StackPane layout = new StackPane();
        layout.getChildren();


        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
