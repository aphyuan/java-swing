/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twodementionapp;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;

/**
 *
 * @author panpanyou
 */
public class Rectangle extends Shape{
    public Rectangle(int x1, int y1, Paint paint, boolean filled, boolean dashed, int width, float dashWidth)
    {
        super(x1, y1, paint, filled, dashed, width, dashWidth);
    }
    public void draw(Graphics g)
    {
        Graphics2D graph = (Graphics2D) g;
        graph.setPaint(this.getPaint());
        Stroke s;
        int recWidth = Math.abs(this.getX1()-this.getX2());
        int recHeight = Math.abs(this.getY1()-this.getY2());
        int smallerx;
        int smallery;
        if(this.getX1()< this.getX2())
            smallerx = this.getX1();
        else 
            smallerx = this.getX2();
        
        if (this.getY1()< this.getY2())
            smallery = this.getY1();
        else
            smallery = this.getY2();
        
        if (this.isFilled())
            graph.fillRect(smallerx, smallery, recWidth, recHeight);
        else
            graph.drawRect(smallerx, smallery, recWidth, recHeight);
        
        if (this.isDashed())
            s = new BasicStroke(this.getWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 10, this.getDashedWidth(),0);
        else
            s = new BasicStroke(this.getWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        
        graph.setStroke(s);
        
    }
}
