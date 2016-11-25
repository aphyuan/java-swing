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
    private PreparedStatement getFlightStatus;
    private PreparedStatement getWaitListStatus;
    public BookingQueries()
    {
        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            insertNewCustomer = connection.prepareStatement("INSERT INTO bookings "+"(day, name, flight, Created_at) " + "VALUES (?, ?, ?, ?)");                  
            getFlightSeats = connection.prepareStatement("select count(flight) from bookings where flight = ? and day = ?"); 
            getFlightStatus = connection.prepareStatement("select * From bookings where flight = ? and day = ? ORDER BY Created_at ASC FETCH FIRST ? ROWS ONLY");
            getWaitListStatus = connection.prepareStatement("select * from bookings WHERE flight = ? and day = ? ORDER BY Created_at OFFSET 5 ROWS");         
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
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            insertNewCustomer.setTimestamp(4, currentTimestamp);
            
            
            result = insertNewCustomer.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
        return result;
    }
    
    public List<Booking> getFlightDayStatus(Date day, String flight)
    {
        List <Booking> results = null;
        ResultSet resultSet = null;
        ResultSet seatSet= null;
        int AvailableSeats = 5;
        try
        {
        getFlightSeats.setString(1, flight);
        getFlightSeats.setDate(2,day);
        seatSet = getFlightSeats.executeQuery();
        seatSet.next();
        int seats;
        seats = seatSet.getInt(1);
        if(seats == 0)
        {
        }
        else if(seats < AvailableSeats)
        {
//            ResultSet resultSet;
            try
            {
                results = new ArrayList< Booking >();
                
                getFlightStatus.setString(1, flight);
                getFlightStatus.setDate(2, day);
                getFlightStatus.setInt(3, seats);
                
                resultSet = getFlightStatus.executeQuery();
                while(resultSet.next())
                {
                    results.add(new Booking(resultSet.getInt("BookingID"),resultSet.getDate("day"),resultSet.getString("name"), resultSet.getString("flight"), resultSet.getTimestamp("Created_at")));

                }
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
            finally
        {
            try
            {
                resultSet.close();
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
                close();
            }
        }
            
        }
        else
        {
            try
                {
                    results = new ArrayList< Booking >();

                    getFlightStatus.setString(1, flight);
                    getFlightStatus.setDate(2, day);
                    getFlightStatus.setInt(3, 5);

                    resultSet = getFlightStatus.executeQuery();
                    while(resultSet.next())
                    {
                        results.add(new Booking(resultSet.getInt("BookingID"),resultSet.getDate("day"),resultSet.getString("name"), resultSet.getString("flight"), resultSet.getTimestamp("Created_at")));

                    }
                }
                catch(SQLException sqlException)
                {
                sqlException.printStackTrace();
                }
                finally
        {
            try
            {
                resultSet.close();
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
                close();
            }
        }
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                seatSet.close();
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
                close();
            }
        }
        
        return results;
    }
    
    public List<Booking> getWaitlistStatus(Date day, String flight)
    {
        List<Booking> results = null;
        ResultSet resultSet = null;
//        int AvailableSeats = 5;
        try
        {
        results = new ArrayList< Booking >();
        getWaitListStatus.setString(1, flight);
        getWaitListStatus.setDate(2, day);
        
        resultSet = getWaitListStatus.executeQuery();
        
            while(resultSet.next())
                    {
                        results.add(new Booking(resultSet.getInt("BookingID"),resultSet.getDate("day"),resultSet.getString("name"), resultSet.getString("flight"), resultSet.getTimestamp("Created_at")));
                    }
        
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
                close();
            }
        }
        
        return results;
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
