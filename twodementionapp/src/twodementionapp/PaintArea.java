/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twodementionapp;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author panpanyou
 */
public class PaintArea extends JFrame {
    private final JPanel drawPanel;
    private final JPanel buttonPanel;
    private final JPanel bottomPanel;
    private JLabel shapetext;
    private JComboBox<String> shapeOptions;
    private JButton undoButton;
    private JButton clearButton;
    private JCheckBox filledBox;
//    private JLabel filledtext;
    private JCheckBox gradientBox;
    private JButton firstColorButton;
    private JButton secondColorButton;
//    private JColorChooser firstColorChooser;
//    private JColorChooser secondColorChooser;
    private JLabel lineText;
    private JLabel dashText;
    private JTextField lineWidth;
    private JTextField dashLength;
    private JCheckBox DashedCheckbox;
    private DrawArea drawArea;
    private JLabel coordinates = new JLabel();
    private Color color1 = Color.WHITE;
    private Color color2 = Color.WHITE;
    public PaintArea()
    {
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        drawPanel = new JPanel();
        JPanel firstLine = new JPanel();
        JPanel secondLine = new JPanel ();
        
       
        //*******************first line begins***********************//
        //set the undo button
        undoButton = new JButton("undo");
        firstLine.add(undoButton);
        undoButton.addActionListener(new UndoButtonHandler());
        
        //set the clear button
        clearButton = new JButton("clear");
        firstLine.add(clearButton);
        clearButton.addActionListener(new ClearButtonHandler());
        
        //set JLABEL for shapes
        shapetext = new JLabel("Shapes: ");
        firstLine.add(shapetext);
        
        //set ComboBox Shapes
        String[] shapes = {"Line", "Oval", "Rectangle"};
        shapeOptions = new JComboBox<String>(shapes);
        firstLine.add(shapeOptions);
        shapeOptions.addActionListener(new ShapeOptionHandler());
        
        //set filled checkbox
        filledBox = new JCheckBox("filled");
        firstLine.add(filledBox);
        filledBox.addActionListener(new FilledCheckedHandler());
        
        //set JLABEL for filled
//        filledtext = new JLabel();
//        firstLine.add(filledtext);
        
        //////////////************* first Line finished ***************///////
        
        //************************second Line begins ******************///////
        
        //set gradient checkbox
        gradientBox = new JCheckBox("Use Gradient");
        gradientBox.addActionListener(new GradientCheckBoxHandler());
        secondLine.add(gradientBox);
        
        //add two color choosers
        firstColorButton = new JButton("1st Color");
        secondColorButton = new JButton("2nd Color");
        firstColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color1 = JColorChooser.showDialog(PaintArea.this, "Choose a color", color1);
                drawArea.setPaint(color1, color2, gradientBox.isSelected());
            }
        });
        secondColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color2 = JColorChooser.showDialog(PaintArea.this, "Choose a color", color1);
                drawArea.setPaint(color1, color2, gradientBox.isSelected());
            }
        });
        secondLine.add(firstColorButton);
        secondLine.add(secondColorButton);
        
        //JLAbel of line width
        lineText = new JLabel("Line Width: ");
        secondLine.add(lineText);
        
        //JTextField of line width
        lineWidth = new JTextField();
        lineWidth.setColumns(3);
        lineWidth.addActionListener(new LineWidthHandler());
        secondLine.add(lineWidth);
              
        //JLabel of dash width
        dashText = new JLabel("Dash Length: ");
        secondLine.add(dashText);
        
        //JTextField of dash length
        dashLength = new JTextField();
        dashLength.setColumns(3);
        dashLength.addActionListener(new DashLengthHandler());
        secondLine.add(dashLength);
        
        //JCheckBox for dashed
        DashedCheckbox = new JCheckBox("Dashed");
        DashedCheckbox.addActionListener(new DashCheckHandler());
        secondLine.add(DashedCheckbox);
        
        /////////////////////*****second line finished******////////////////
        
        
        
        
        JLabel coordinates = new JLabel("hello world");
        drawArea = new DrawArea(coordinates);
        
        //panel combination
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        
        buttonPanel.add(firstLine, BorderLayout.NORTH);
        buttonPanel.add(secondLine, BorderLayout.SOUTH);
        
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        
        bottomPanel.add(drawArea, BorderLayout.NORTH);
        bottomPanel.add(coordinates, BorderLayout.SOUTH);
        
        drawPanel.setLayout(new BorderLayout());
        drawPanel.add(buttonPanel, BorderLayout.NORTH);
        drawPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(buttonPanel, BorderLayout.NORTH);
        add(drawArea, BorderLayout.CENTER);
        add(coordinates, BorderLayout.SOUTH);
    }
    
    private class UndoButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawArea.undo();
        }
    }
    
    //TODO:SET Clear button handler
    
    private class ClearButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawArea.clear();
        }
    }
    
    //TODO:SET Shape Option handler
    
    private class ShapeOptionHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawArea.setShapeType(shapeOptions.getSelectedIndex());
        }
    }
    
    //TODO:SET Filled CheckBOx handler
    
    private class FilledCheckedHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawArea.filled(filledBox.isSelected());
        }
    
    }
    //TODO:SET Gradient CHeckbox handler
    private class GradientCheckBoxHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawArea.setPaint(color1, color2, gradientBox.isSelected());
        }
    }
    //TODO: SET Line Width handler
    
    private class LineWidthHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawArea.setLineWidth(Integer.parseInt(lineWidth.getText()));
        }
    }
    
    //TODO: SET Dash length handler
    
    private class DashLengthHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawArea.setDashLength(Float.parseFloat(dashLength.getText()));
        }
    }
    
    //set dash checkbox
    
    private class DashCheckHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawArea.dashed(DashedCheckbox.isSelected());
        }
    }


}
