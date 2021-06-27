package Boxinator;

import java.sql.*;

public class database_connector {
    private static String path = "jdbc:mysql://localhost:3306/shipping";
    private static String username = "root";
    private static String password = "root";
    private static String insertStatementStart = "Insert into boxes (name, weight, color, country, price) Values (\"";
    private static String selectStatment = "Select * from boxes";




    public static int insertData(String name, int weight, String color, String country){
        int price = weight;
        if(country.equals("swe")){
            price *= 1.3;
        }
        else if(country.equals("chi")){
            price *= 4;
        }
        else if(country.equals("bra")){
            price *= 8.6;
        }
        else if(country.equals("aus")){
            price *= 7.2;
        }

        try{
            // try to connect to database
            Connection conn = connect();

            // add together the statement
            String insertStatement = insertStatementStart + name +"\","+ weight +",\""+ color +"\",\""+ country +"\","+ price+")";

            // pepare the statment and then execute statment to the database
            PreparedStatement ps = conn.prepareStatement(insertStatement);
            int rows = ps.executeUpdate();

            // close connection
            conn.close();
            return rows;



        } catch (SQLException e) {
            System.out.print(e);
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static ResultSet getAllData()  {
        try{
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(selectStatment);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private static Connection connect() throws Exception{

            // try to connect to database
            Connection conn = DriverManager.getConnection(path, username, password);
            return conn;

    }
}
