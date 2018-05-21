package API;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class APIFetch {
    private static String API_KEY = "17829b9cb9d342f0a856c42dd0e5c401";
    private static String QUERY = "http://data.fixer.io/api/";

    private static String PARAM_API_KEY = "access_key=";

    public enum DataMode {
        LATEST,
        SYMBOLS,
        HISTORICAL;

        /**
         * gives a parameter of the url based on type of data to be entered
         * @param mode the type of the data to be retrieved
         * @return string representation of the type
         */
        public static String getURLParam(DataMode mode) {
            switch (mode){
                case LATEST:        return "latest";
                case SYMBOLS:       return "symbols";
                case HISTORICAL:    return "YYYY-MM-DD";
                default:            throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Builds a url depending on the type of data to be retrieved
     * @param mode type of data to be retrieved
     * @return A URL that request
     */
    private static String buildURL(DataMode mode) {
        StringBuilder sB = new StringBuilder();

        sB.append(QUERY);

        sB.append(DataMode.getURLParam(mode));
        sB.append("?");

        sB.append(PARAM_API_KEY);
        sB.append(API_KEY);

        return sB.toString();
    }

    /**
     * Builds a url depending on the type of data to be retrieved
     * @param mode type of data to be retrieved
     * @param date date for the specific day data should be retrieved from (YYYY-MM-DD)
     * @return A URL that request
     * @throws ParseException If an error occurs when fetching the data
     * @throws IllegalArgumentException If the year put in is not in the database
     */
    private static String buildURL(DataMode mode, String date) throws ParseException, IllegalArgumentException {
        StringBuilder sB = new StringBuilder();

        sB.append(QUERY);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.parse(date);

        String[] dates = date.split("-");
        if (Integer.parseInt(dates[0]) < 1999){
            throw new IllegalArgumentException("Year is too high");
        }

        sB.append(date);
        sB.append("?");

        sB.append(PARAM_API_KEY);
        sB.append(API_KEY);

        return sB.toString();
    }
    /**
     * gets the currency data form the specified url
     * @param urlLink link that will be used to connect to the API
     * @return The stream of data
     * @throws IOException if there was an error fetching the data
     */
    private static InputStream getAllCurrency(String urlLink) throws IOException {

        URL url = new URL(urlLink);
        URLConnection conn = url.openConnection();

        return conn.getInputStream();
    }

    /**
     * Gets the latest data for all available currency and returns a stream for it
     * @return The stream containing the data
     * @throws IOException if there was an error fetching the data
     */
    public static InputStream getLatest() throws IOException{
        String query = APIFetch.buildURL(DataMode.LATEST);
        return APIFetch.getAllCurrency(query);
    }

    /**
     * Gets the symbols and their corresponding name and returns a stream for it
     * @return The stream containing the data
     * @throws IOException if there was an error fetching the data
     */
    public static InputStream getSymbols() throws IOException{
        String query = APIFetch.buildURL(DataMode.SYMBOLS);
        return APIFetch.getAllCurrency(query);
    }

    /**
     * Gets the data for a specific day and returns a stream for it
     * @param date date for the specific day data should be retrieved from (YYYY-MM-DD)
     * @return The stream containing the data
     * @throws IOException if there was an error fetching the data
     * @throws ParseException if there was an error parsing the data
     */
    public static InputStream getHistoric(String date) throws IOException, ParseException{
        String query = APIFetch.buildURL(DataMode.HISTORICAL, date);
        return APIFetch.getAllCurrency(query);
    }
}
