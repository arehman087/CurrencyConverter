package Gui;

import API.APIFetch;
import API.ParsedCurrencyData;
import DataBaseCode.CurrencyDatabase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.sql.ResultSet;

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
