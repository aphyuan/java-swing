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
    private PreparedStatement insertNewFlight;
    private PreparedStatement insertNewDate;
    private PreparedStatement getAllFlights;
    private PreparedStatement getAllDates;
    private PreparedStatement getCustomerNames;
    private PreparedStatement getSeatNumber;
    private PreparedStatement checkCustomerNameDayFlight;
    private PreparedStatement checkCustomerDayFlight;
    private PreparedStatement cancelBooking;
    private PreparedStatement cancelFlight;
    private PreparedStatement getStatusbyname;
    private PreparedStatement getWaitlistbyname;
    private PreparedStatement dropFlightWaitlist;
    private PreparedStatement updateFlightstatement;
    private PreparedStatement cancelBookingforDrop;
    private PreparedStatement updateWaitlistwhencancel;
    private PreparedStatement getBookedFlightSeats;
    private PreparedStatement selectupdateFlightStatement;
    private Flight currentFlight;
    private FlightDate currentDate;
    public BookingQueries()
    {
        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            insertNewCustomer = connection.prepareStatement("INSERT INTO bookings "+"(day, name, flight, Created_at, waitlist) " + "VALUES (?, ?, ?, ?, ?)");    
            insertNewFlight = connection.prepareStatement("INSERT INTO Flights "+"(flight, seats) " + "VALUES (?, ?)");
            insertNewDate = connection.prepareStatement("INSERT INTO Days " + "(date) " +"VALUES (?)");
            getFlightSeats = connection.prepareStatement("select count(flight) from bookings where flight = ? and day = ?"); 
            getBookedFlightSeats = connection.prepareStatement("select count(flight) from bookings where flight = ? and day = ? and waitlist = false"); 

            getFlightStatus = connection.prepareStatement("select * From bookings where flight = ? and day = ? ORDER BY Created_at ASC FETCH FIRST ? ROWS ONLY");
            getSeatNumber = connection.prepareStatement("select seats From Flights where flight = ?");
            getWaitListStatus = connection.prepareStatement("select * from bookings WHERE flight = ? and day = ? ORDER BY Created_at OFFSET ? ROWS");
            getAllFlights = connection.prepareStatement("select * from flights");
            getAllDates = connection.prepareStatement("select * from days");
            getCustomerNames = connection.prepareStatement("select distinct name from bookings ORDER by name ASC");
            checkCustomerNameDayFlight = connection.prepareStatement("select * from bookings WHERE flight = ? and day = ? and name = ?");
            checkCustomerDayFlight = connection.prepareStatement("select * from bookings WHERE day = ? and waitlist = false and name = ?");
            cancelBooking = connection.prepareStatement("DELETE FROM bookings WHERE flight = ? and day = ? and name = ?");
            cancelBookingforDrop = connection.prepareStatement("DELETE FROM bookings WHERE flight = ?");
            cancelFlight = connection.prepareStatement("DELETE FROM Flights where flight = ?");
            getStatusbyname = connection.prepareStatement("select * from bookings where name = ? and waitlist = false ORDER BY day ASC");
            getWaitlistbyname = connection.prepareStatement("select * from bookings where name = ? and waitlist = true ORDER BY day ASC");
            dropFlightWaitlist = connection.prepareStatement("DELETE FROM bookings WHERE flight = ? and waitlist = true");
//            selectupdateFlightStatement = connection.prepareStatement("SELECT * from bookings WHERE flight = ? and day = ? and waitlist = false fetch First ? ROWS ONLY");
            updateFlightstatement = connection.prepareStatement("UPDATE bookings SET flight = ? WHERE EXISTS(select * from bookings WHERE flight = ? and day =? and waitlist = false FETCH FIRST ? ROWS ONLY)");
            updateWaitlistwhencancel = connection.prepareStatement("UPDATE bookings SET waitlist = false WHERE flight = ? and day = ? and waitlist = true");
        }   
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public int dropTheWaitlist(String flight)
    {
        int result = 0;
        try{
            dropFlightWaitlist.setString(1, flight);
            result = dropFlightWaitlist.executeUpdate();
            
            System.out.print(result);
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
        return result;  
    }
    
    public int updateFlight(String flight)
    {
        int result = 0;
        int updaterow = 0;
        String theflight;
        Date theDate;
        List<FlightDate> allDate = null;
        List<Flight> allFlight = null;
        allFlight = getFlights();
        allDate = getDates();
        int numbershouldchangefortotal = 0;
        int numberofDates = allDate.size();
        int numberofEntries = allFlight.size();
   
        for(int j=0; j< numberofDates; j++){
            currentDate = allDate.get(j);
            theDate = currentDate.getDate();
            ResultSet seatnum;
            int seatnumber = 0;
            int overallrow = 0; 
            try
            {
                getBookedFlightSeats.setString(1, flight);
                getBookedFlightSeats.setDate(2, theDate);
                seatnum = getBookedFlightSeats.executeQuery();
                
                if (seatnum.next())
                {
                seatnumber = seatnum.getInt(1);
                System.out.print("seatnum:"+seatnumber);
                }
                
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
                close();
            }
            numbershouldchangefortotal += seatnumber;
            for(int i=0; i< numberofEntries;i++)
            {
            ResultSet seatnumberresult = null;
            int seatnumforFlight = 0;
            int rowforupdate = 0;
            currentFlight = allFlight.get(i);
            theflight = currentFlight.getFlight();
            try
            {
                getSeatNumber.setString(1, theflight);
                seatnumberresult = getSeatNumber.executeQuery();
                if(seatnumberresult.next())
                {
                    seatnumforFlight = seatnumberresult.getInt("seats");
                }
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
                close();
            }
            if(seatnumforFlight <= seatnumber)
            {
                rowforupdate = seatnumforFlight;
                seatnumber-=seatnumforFlight;
            }
            else
            {
                rowforupdate = seatnumber;
                seatnumber = 0;
            }
            if(rowforupdate > 0){
            try
            {
                updateFlightstatement.setString(1, theflight);
                updateFlightstatement.setString(2, flight);
                updateFlightstatement.setDate(3, theDate);
                updateFlightstatement.setInt(4, rowforupdate);
                updaterow = updateFlightstatement.executeUpdate();
                System.out.print("updaterow"+updaterow);
                overallrow += updaterow;
                
            }
             catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
                close();
            }
            }
            }
        }
        
        try{
            cancelBookingforDrop.setString(1, flight);
            result = cancelBookingforDrop.executeUpdate();
        }
        catch(SQLException sqlException)
        {
                sqlException.printStackTrace();
                close();
        }
        
        return result;
    }
    public int deleteFlight(String flight)
    {
        int result = 0;
        try
        {
            cancelFlight.setString(1, flight);
            result = cancelFlight.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
        return result;
    }         
    public int deleteBooking(Date day, String name, String flight)
    {
        boolean checkwaitlist = false;
        int result = 0;
        int updatewaitlistresult;
        ResultSet resultSet = null;
        try
        {
            checkCustomerNameDayFlight.setString(1, flight);
            checkCustomerNameDayFlight.setDate(2, day);
            checkCustomerNameDayFlight.setString(3, name);
            resultSet = checkCustomerNameDayFlight.executeQuery();
            if(resultSet.next())
            {
                checkwaitlist = resultSet.getBoolean("waitlist");
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
        
        if(!checkwaitlist)
        {
            try{
                updateWaitlistwhencancel.setString(1, flight);
                updateWaitlistwhencancel.setDate(2, day);
                        
           updatewaitlistresult = updateWaitlistwhencancel.executeUpdate();
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
                close();
            }
            }
        
        try
        {
        cancelBooking.setString(1, flight);
        cancelBooking.setDate(2, day);
        cancelBooking.setString(3, name);
        result = cancelBooking.executeUpdate();
        System.out.print("cancelbooking:line"+result);
        }
        catch(SQLException sqlException)
        {
        sqlException.printStackTrace();
        close();
        }
        
        
        return result;
    }
    
    public int addBooking(Date day, String name, String flight)
    {
        Boolean waitlist = true;
        ResultSet seatnum = null;
        int seatnumber = 0;
        try
        {
            getSeatNumber.setString(1, flight);
            seatnum = getSeatNumber.executeQuery();
            if (seatnum.next())
            {
                seatnumber = seatnum.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
       
        ResultSet flightS = null;
        int row = 0;
        try
        {
            getFlightStatus.setString(1, flight);
            getFlightStatus.setDate(2, day);
            getFlightStatus.setInt(3, seatnumber);
            flightS = getFlightStatus.executeQuery();
            while(flightS.next())
            {
                row++;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
        int result = 0;
        if (row < seatnumber)
        {
            waitlist = false;
        }
        
        try
        {
            insertNewCustomer.setDate(1, day);
            insertNewCustomer.setString(2, name);
            insertNewCustomer.setString(3, flight);
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            insertNewCustomer.setTimestamp(4, currentTimestamp);
            insertNewCustomer.setBoolean(5, waitlist);
            
            result = insertNewCustomer.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
        return result;
    }
    
    public int addFlight(String flight, int seats)
    {
        int result = 0;
        try{
            insertNewFlight.setString(1, flight);
            insertNewFlight.setInt(2, seats);
            
            result = insertNewFlight.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
        
        
        return result;
    }
    
    public int addDate(Date day)
    {
        int result = 0;
        try{
            insertNewDate.setDate(1, day);
            result = insertNewDate.executeUpdate();
        }
        catch(SQLException sqlException)
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
        
        int AvailableSeats = getSeatNumber(flight);
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
                    results.add(new Booking(resultSet.getInt("BookingID"),resultSet.getDate("day"),resultSet.getString("name"), resultSet.getString("flight"), resultSet.getTimestamp("Created_at"), resultSet.getBoolean("waitlist")));

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
                    getFlightStatus.setInt(3, AvailableSeats);

                    resultSet = getFlightStatus.executeQuery();
                    while(resultSet.next())
                    {
                        results.add(new Booking(resultSet.getInt("BookingID"),resultSet.getDate("day"),resultSet.getString("name"), resultSet.getString("flight"), resultSet.getTimestamp("Created_at"), resultSet.getBoolean("waitlist")));

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
        ResultSet seatSet = null;
        int seatnum = 0;
//        int AvailableSeats = 5;
        try
        {
            getSeatNumber.setString(1, flight);
            seatSet = getSeatNumber.executeQuery();
            if (seatSet.next())
            {
                seatnum = seatSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
        
        try
        {
        results = new ArrayList< Booking >();
        getWaitListStatus.setString(1, flight);
        getWaitListStatus.setDate(2, day);
        getWaitListStatus.setInt(3, seatnum);
        
        resultSet = getWaitListStatus.executeQuery();
        
            while(resultSet.next())
                    {
                        results.add(new Booking(resultSet.getInt("BookingID"),resultSet.getDate("day"),resultSet.getString("name"), resultSet.getString("flight"), resultSet.getTimestamp("Created_at"), resultSet.getBoolean("waitlist")));
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
    
    public List<Booking> getPersonStatus(String name)
    {
        List <Booking> results = null;
        ResultSet resultSet = null;
        try{
        results = new ArrayList<Booking>();
        getStatusbyname.setString(1, name);
        resultSet = getStatusbyname.executeQuery();
        while(resultSet.next())
        {
            results.add(new Booking(resultSet.getInt("BookingID"),resultSet.getDate("day"),resultSet.getString("name"), resultSet.getString("flight"), resultSet.getTimestamp("Created_at"), resultSet.getBoolean("waitlist")));
        }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
    
    return results;
    }
    
    public List<Booking> getPersonWaitlistStatus(String name)
    {
        List <Booking> results = null;
        ResultSet resultSet = null;
        try{
            results = new ArrayList<Booking>();
            getWaitlistbyname.setString(1, name);
            resultSet = getWaitlistbyname.executeQuery();
            while(resultSet.next())
        {
            results.add(new Booking(resultSet.getInt("BookingID"),resultSet.getDate("day"),resultSet.getString("name"), resultSet.getString("flight"), resultSet.getTimestamp("Created_at"), resultSet.getBoolean("waitlist")));
        }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
        return results;
    }
    
    
    public int getSeatNumber(String flight)
    {
        int num = 0;
        ResultSet resultSet = null;
        try
        {
            getSeatNumber.setString(1, flight);
            resultSet = getSeatNumber.executeQuery();
            while(resultSet.next())
            {
                num = resultSet.getInt("seats");
            }         
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
                close();
        }
        return num;
    }
    //TODO: add function of getallflights
    public List<Flight> getFlights()
    {
        List<Flight> results = null;
        ResultSet resultSet = null;
//        int AvailableSeats = 5;
        try
        {
        results = new ArrayList< Flight >();    
        resultSet = getAllFlights.executeQuery();
        
            while(resultSet.next())
                    {
                        results.add(new Flight(resultSet.getString("flight"), resultSet.getInt("seats")));
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
    //TODO: add function of getalldays
    
    public List<FlightDate> getDates()
    {
        List<FlightDate> results = null;
        ResultSet resultSet = null;
//        int AvailableSeats = 5;
        try
        {
        results = new ArrayList< FlightDate >();    
        resultSet = getAllDates.executeQuery();
        
            while(resultSet.next())
                    {
                        results.add(new FlightDate(resultSet.getDate("date")));
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
    
    public List getNames()
    {
        List results = null;
        ResultSet resultSet = null;
//        int AvailableSeats = 5;
        try
        {    
        
        resultSet = getCustomerNames.executeQuery();
        results = new ArrayList();
        
            while(resultSet.next())
                    {
                        results.add(resultSet.getString("name"));
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
    
    public boolean checkBooked(String flight, Date day, String name)
    {
        boolean booked = false;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        try
        {
            checkCustomerDayFlight.setDate(1,day);
            checkCustomerDayFlight.setString(2, name);
            resultSet2 = checkCustomerDayFlight.executeQuery();
            if(resultSet2.next())
            {
                booked = true;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
        try
        {
            checkCustomerNameDayFlight.setString(1, flight);
            checkCustomerNameDayFlight.setDate(2,day);
            checkCustomerNameDayFlight.setString(3, name);
            resultSet = checkCustomerNameDayFlight.executeQuery();
            
            if (resultSet.next())
                booked = true;
//            else if(resultSet2.next())
//                booked = true;
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            close();
        }
        return booked;
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
