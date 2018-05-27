package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ParsedCurrencyData {
    private String base;                                    // base Currency Symbol
    private Hashtable<String, Double> SymVal;               // table containing the symbol abd the value
    private Hashtable<String, String> SymName;              // table containing the symbol w/ respect to the name
    private Hashtable<String, String> NameSym;              // table containing the name w/ respect to the symbol


    public ParsedCurrencyData() throws IOException {
        this.SymVal = new Hashtable<String, Double>();
        this.SymName = new Hashtable<String, String>();
        this.NameSym = new Hashtable<String, String>();

        this.parseData(APIFetch.getLatest());
        this.parseSymbolNames();
        this.nameToSym();
    }

    /**
     * Takes in a stream of data and parses it into a table
     * @param iS the stream of the data
     */
    public void parseData(InputStream iS){

        InputStreamReader iSR = new InputStreamReader(iS);
        BufferedReader br = new BufferedReader(iSR);

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(br);

        ArrayList<String> keys = new ArrayList<String>();
        for(Map.Entry<String, JsonElement> j : root.getAsJsonObject().entrySet()){
            keys.add(j.getKey());
        }

        this.base = root.getAsJsonObject().get(keys.get(2)).getAsString();

        JsonElement allCurrencies = root.getAsJsonObject().get(keys.get(4));
        for (Map.Entry<String, JsonElement> j : allCurrencies.getAsJsonObject().entrySet()) {
            this.SymVal.put(j.getKey(), j.getValue().getAsDouble());
        }
    }

    /**
     * Takes the stream of data and parses it into symbols that can be used for
     * identifying currency from symbol to name
     * @throws IOException if something goes wrong when connecting
     */
    public void parseSymbolNames() throws IOException {
        InputStream iS = APIFetch.getSymbols();
        InputStreamReader iSR = new InputStreamReader(iS);
        BufferedReader br = new BufferedReader(iSR);

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(br);

        ArrayList<String> keys = new ArrayList<String>();
        for(Map.Entry<String, JsonElement> j : root.getAsJsonObject().entrySet()){
            keys.add(j.getKey());
        }

        System.out.println(keys);

        JsonElement allSymbols = root.getAsJsonObject().get(keys.get(1));
        for (Map.Entry<String, JsonElement> j : allSymbols.getAsJsonObject().entrySet()) {
            this.SymName.put(j.getKey(), j.getValue().getAsString());
        }
    }

    /**
     * Creates a table that has a list of names w/ respect to symbols to
     * identify the currency form name to symbol
     */
    public void nameToSym(){
        for(String s : this.SymName.keySet()) {
            this.NameSym.put(this.SymName.get(s), s);
        }
    }
}
