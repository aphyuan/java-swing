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
public class Line extends Shape{
    public Line(int x1, int y1, Paint paint, boolean filled, boolean dashed, int width, float dashWidth)
    {
        super(x1, y1, paint, filled, dashed, width, dashWidth);
    }
    
    public void draw(Graphics g)
    {
        Graphics2D graph = (Graphics2D) g;
        graph.setPaint(this.getPaint());
        Stroke s;
        if (this.isDashed())
            s = new BasicStroke(this.getWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 10, this.getDashedWidth(), 0);
        else
            s = new BasicStroke(this.getWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        graph.setStroke(s);
        graph.drawLine(this.getX1(), this.getY1(), this.getX2(), this.getY2());
    }
}
