/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.*;
/**
 *
 * @author evelynpan
 */
public class BookingQueries {
    private static final String URL = "";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    
    private Connection connection;
    private PreparedStatement getFlightSeats;
    
    public BookingQueries()
    {
        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            getFlightSeats = connection.prepareStatement("select count(flight) from bookings where flight = ? and day = ?"); 
                       
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    
}
