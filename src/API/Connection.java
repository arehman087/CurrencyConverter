package API;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Connection {
    private static String API_KEY = "17829b9cb9d342f0a856c42dd0e5c401";
    private static String URL = "http://data.fixer.io/api/";

    private static String PARAM_API_KEY = "access_key=";

    public enum DataMode {
        LATEST,
        SYMBOLS,
        HISTORICAL;

        /**
         *
         * @param mode the type of the data to be retrieved
         * @return string representation of the type
         */
        public static String getURL(DataMode mode) {
            switch (mode){
                case LATEST:        return "latest?";
                case SYMBOLS:       return "symbols?";
                case HISTORICAL:    return "YYYY-MM-DD";
                default:            throw new IllegalArgumentException();
            }
        }
    }



}
