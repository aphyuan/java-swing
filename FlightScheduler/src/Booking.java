
import java.sql.Date;
import java.sql.Timestamp;


//import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author evelynpan
 */
public class Booking {
    private int bookingID;
    private Date day;
    private String name;
    private String flight;
//    private Timestamp Created_at;
    //constructor
    public Booking()
    {
    }
    
    public Booking(int bookingID, Date day, String name, String flight)
    {
        setBookingID(bookingID);
        setDay(day);
        setName(name);
        setFlight(flight);
    }
    
    public void setBookingID(int bookingID)
    {
        this.bookingID = bookingID;
    }
    
    public int getBookingID()
    {
        return bookingID;
    }
    
    public void setDay(Date day)
    {
        this.day = day;
    }
    
    public Date getDay()
    {
        return day;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setFlight(String flight)
    {
        this.flight = flight;
    }
    
    public String getFlight()
    {
        return flight;
    }
}
