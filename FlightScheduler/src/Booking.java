
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
    private String day;
    private String name;
    private String flight;
    //constructor
    public Booking()
    {
    }
    
    public Booking(int bookingID, String day, String name, String flight)
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
    
    public void setDay(String day)
    {
        this.day = day;
    }
    
    public String getDay()
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
