/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.sql.Date;
import java.util.*;
/**
 *
 * @author evelynpan
 */
public class BookingQueries {
    private static final String URL = "jdbc:derby://localhost:1527/FlightSchedulerhpp5036";
    private static final String USERNAME = "java";
    private static final String PASSWORD = "java";
    
    private Connection connection;
    private PreparedStatement getFlightSeats;
    private PreparedStatement insertNewCustomer;
    
    public BookingQueries()
    {
        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            insertNewCustomer = connection.prepareStatement("INSERT INTO bookings "+"(day, name, flight) " + "VALUES (?, ?, ?)");
                    
            getFlightSeats = connection.prepareStatement("select count(flight) from bookings where flight = ? and day = ?"); 
                       
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public int addBooking(Date day, String name, String flight)
    {
        int result = 0;
        try
        {
            insertNewCustomer.setDate(1, day);
            insertNewCustomer.setString(2, name);
            insertNewCustomer.setString(3, flight);
            
            result = insertNewCustomer.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
        return result;
    }
    
    public void close()
    {
        try
        {connection.close();}
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
