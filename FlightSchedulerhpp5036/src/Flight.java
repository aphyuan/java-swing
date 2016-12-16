/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author evelynpan
 */
public class Flight {
    private String flight;
    private int seats;
    
    public Flight()
    {}
    
    public Flight(String flight, int seats)
    {
        setFlight(flight);
        setSeats(seats);
    }
    
    public void setFlight(String flight)
    {
        this.flight = flight;
    }
    
    public void setSeats(int seats)
    {
        this.seats = seats;
    }
    
    public String getFlight()
    {
        return flight;
    }
    
    public int getSeats()
    {
        return seats;
    }
}


