/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guessthenumbertest;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author panpanyou
 */
public class NumberFrame extends JFrame{
    private final JButton restartbutton;
    private final JLabel label1;
    private JLabel label2;
    private JTextField textField1;
    private JLabel label3 = new JLabel();
    Color defaultcolor = getContentPane().getBackground();
    public NumberFrame()
    {
        super("Guess the number game!");
        setLayout(new FlowLayout());
        //set up the question
        label1 = new JLabel("I have a number between 1 and 1000. Can you guess my number?", JLabel.CENTER);
        add(label1);
        label2 = new JLabel("Please enter your first guess.");
        add(label2);
        //set up the input number
        textField1 = new JTextField(7);
        add(textField1);
        add(label3);       
        //initialize the restart button and set the button visibility to false
        restartbutton = new JButton("restart");
        restartbutton.setVisible(false);
        add(restartbutton);
        //register the event handler
        Handler thandler = new Handler();
        textField1.addActionListener(thandler);
        restartbutton.addActionListener(thandler);
       
    }
    
    private class Handler implements ActionListener
        {
            //generates a random number
            Random rand = new Random();
            int n = rand.nextInt(1000) + 1;
            //first set the compare value to 1001 to do the first time comparision
            int compare = 1001;
            //initialize an integer for difference variable
            int difference;
            
            @Override
            public void actionPerformed(ActionEvent event)
            {
                String string;
                if (event.getSource() == textField1)
                {
                    string = String.format("%s", event.getActionCommand());
                    int value;
                    value = Integer.parseInt(string);
                    if (value == n)
                    {
                        label3.setText("correct!");
                        //when the user got the correct answer, make the restart button visible
                        textField1.setEditable(false);
                        restartbutton.setVisible(true);                       
                    }else if (value < n)
                    {
                        label3.setText("Too Low");
                    }else{
                        label3.setText("Too High");
                    }                   
                    difference = java.lang.Math.abs(value - n);
                    if (difference<compare)
                        getContentPane().setBackground(Color.red);
                    else
                        getContentPane().setBackground(Color.BLUE);
                    compare = difference;
                }else if (event.getSource() == restartbutton)
                {
                    //to set a new game
                      label3.setText("");
                      textField1.setText("");
                      n = rand.nextInt(1000) + 1;
                      getContentPane().setBackground(defaultcolor);
                      textField1.setEditable(true);
                      restartbutton.setVisible(false);
                      compare = 1001;
                      difference = 0;
                }
            }
        }
    
    
    
}
