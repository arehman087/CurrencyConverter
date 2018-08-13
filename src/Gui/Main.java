package Gui;

public class Main {

    public static void main(String[] args) {
        try {
            Window w = new Window();
            w.begin();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
