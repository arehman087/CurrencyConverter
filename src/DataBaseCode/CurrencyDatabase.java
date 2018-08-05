package DataBaseCode;

import API.ParsedCurrencyData;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;


public class CurrencyDatabase {
    private Connection conn;
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public CurrencyDatabase(){
      try{
          Class.forName(this.JDBC_DRIVER);
          this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/currencyconversion?" +
                  "autoReconnect=true&useSSL=false","root", "password");
          this.deleteDB();
          this.populateDB();

      }
      catch(SQLException ex) {
          System.out.println("SQLException: " + ex.getMessage());
          System.out.println("SQLState: " + ex.getSQLState());
          System.out.println("VendorError: " + ex.getErrorCode());
      }
      catch(ClassNotFoundException e){
          System.out.println("Driver has failed error message: " + e.getMessage());
      }

    }

    public void populateDB(){

        try {
            ParsedCurrencyData p = new ParsedCurrencyData();
            Hashtable <String, String> names = p.getSymName();
            Hashtable <String, Double> values = p.getSymVal();

            String sql = "INSERT INTO symbol_to_name(SYMBOL, NAME) VALUES(?, ?)";

            String sql2 = "INSERT INTO symbol_to_value(SYMBOL, VALUE) VALUES(?, ?)";

            PreparedStatement ps1 = conn.prepareStatement(sql);
            PreparedStatement ps2 = conn.prepareStatement(sql2);

            for (String n: names.keySet()) {
                ps1.setString(1, n);
                ps1.setString(2, names.get(n));

                ps2.setString(1, n);
                ps2.setDouble(2, values.get(n));

                ps1.addBatch();
                ps2.addBatch();

                ps1.clearParameters();
                ps2.clearParameters();
            }

            ps1.executeBatch();
            ps2.executeBatch();

            throw new SQLException("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void deleteDB(){
        try{
            Statement stmt = conn.createStatement();
            String sql = "TRUNCATE symbol_to_name;";
            stmt.executeUpdate(sql);
            String sql2 = "Truncate symbol_to_value;";
            stmt.executeUpdate(sql2);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public String getName(String s){


        try {
            PreparedStatement ps = conn.prepareStatement("SELECT NAME FROM symbol_to_name WHERE SYMBOL = (?);");

            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return rs.getString("NAME");
            }
            else
            {
                throw new SQLException("SYMBOL DOES NOT EXIST");
            }

        } catch (SQLException e) {
            System.out.println("GETNAME HAS FAILED!");
            e.printStackTrace();
        }
        return "FAILED";
    }

    public String getSymbol(String s){
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT SYMBOL FROM symbol_to_name WHERE NAME = (?);");

            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return rs.getString("SYMBOL");
            }
            else
            {

            }

        } catch (SQLException e) {
            System.out.println("GETSYMBOL HAS FAILED!");
            e.printStackTrace();
        }
        return "FAILED";
    }

    public double getValues(String s){
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT VALUE FROM symbol_to_value WHERE SYMBOL = (?);");
            ps.setString(1, s);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getBigDecimal("VALUE").doubleValue();
            }
            else {
                throw new SQLException("NAME DOES NOT EXIST");
            }

        }
        catch(SQLException e){
            System.out.println("GETVALUE HAS FAILED!");
            e.getMessage();
            e.printStackTrace();
        }
        return -1.0;
    }

    public ResultSet getAllNameSym(){

        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM symbol_to_name";
            return stmt.executeQuery(sql);
        }
        catch(SQLException e){

            System.out.println("GETVALUE HAS FAILED!");
            e.getMessage();
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getNames(){
        ResultSet rs = this.getAllNameSym();
        ArrayList<String> sym = new ArrayList<String>();
        try{
            while(rs.next()){
                sym.add(rs.getString("NAME"));
            }
        }
        catch(Exception ex){
            ex.getMessage();
            ex.printStackTrace();

        }
        return sym;
    }

    public double calculateCurrency(double from, double to){
        double result =  from/to;
        return result;
    }
}
