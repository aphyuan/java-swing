
import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author evelynpan
 */
public class FlightDate {
    private Date day;
    public FlightDate(){}
    public FlightDate(Date day)
    {
        setDate(day);
    }
    public void setDate(Date day)
    {
        this.day = day;
    }
    public Date getDate()
    {
        return day;
    }

}
