/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twodementionapp;

import java.awt.Graphics;
import java.awt.Paint;

/**
 *
 * @author panpanyou
 */
public abstract class Shape {
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private float[] dashLength;
    private boolean filled;
    private boolean dashed;
    private int width;
    private float dashWidth[] = {5};
    private Paint paint;
    
    public Shape(int x1, int y1, Paint paint, boolean filled, boolean dashed, int width, float dashWidth)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.paint = paint;
        this.filled = filled;
        this.dashed = dashed;
        this.width = width;
        this.dashWidth[0] = dashWidth;
    }
    public abstract void draw(Graphics g);
    public boolean isFilled()
    {
        return filled;
    }
    
    public void setFilled(boolean filled)
    {
        this.filled = filled;
    }
    
    public boolean isDashed()
    {
        return dashed;
    }
    
    public void setDashed(boolean dashed)
    {
        this.dashed = dashed;
    }
    
    public float[] getDashedWidth()
    {
        return this.dashWidth;
    }
    
    public void setWidth(int width)
    {
        this.width = width;
    }
    
    public int getWidth()
    {
        return this.width;
    }
    
    public Paint getPaint()
    {
        return this.paint;
    }
    
    public int getX1()
    {
        return x1;
    }
    
    public int getX2()
    {
        return x2;
    }
    
    public int getY1()
    {
        return y1;
    }
    
    public int getY2()
    {
        return y2;
    }
    
    public void setX1(int x1)
    {
        this.x1 = x1;
    }
    
    public void setX2(int x2)
    {
        this.x2 = x2;
    }
    
    public void setY1(int y1)
    {
        this.y1 = y1;
    }
    
    public void setY2(int y2)
    {
        this.y2 = y2;
    }
    
}
