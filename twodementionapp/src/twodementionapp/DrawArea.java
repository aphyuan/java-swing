/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twodementionapp;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author panpanyou
 */
public class DrawArea extends JPanel{
    private JLabel coordinates;
    private Paint currentPaint;
    private Paint paint;
    private int currentShapeType;
    private ArrayList<Shape> shapes;
    private boolean filled;
    private boolean dashed;
    private int lineWidth;
    private float dashWidth;
    
    public DrawArea(JLabel coordinates) 
    {
        this.coordinates = coordinates;
        addMouseMotionListener(new MouseHandler());
        
    }
    
    
    
    private class MouseHandler extends MouseAdapter implements MouseMotionListener{
        private int startX;
        private int startY;
        @Override
        public void mousePressed(MouseEvent e)
        {
            this.startX = e.getX();
            this.startY = e.getY();
            Shape x;
            if (currentShapeType == 0)
                x = new Line(startX, startY, paint, filled, dashed, lineWidth, dashWidth);
            else if (currentShapeType == 1)
                x = new Oval(startX, startY, paint, filled, dashed, lineWidth, dashWidth);
            else
                x = new Rectangle(startX, startY, paint, filled, dashed, lineWidth, dashWidth);
            shapes.add(x);
        }    
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for(Shape x : shapes){
            x.draw(g);
        }
    }
    public void undo()
    {
        shapes.remove(shapes.size()-1);
        repaint();
    }
    
    public void clear()
    {
        shapes.clear();
        repaint();   
    }
    
    public void setPaint(Color color1, Color color2, boolean g)
    {
    if(g)
        this.paint = new GradientPaint(0, 0 ,color1, 50, 50, color2, true);
    else
        this.paint = color1;
    }
}
