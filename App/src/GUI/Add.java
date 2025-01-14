package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//This class represents the "ADD" window for adding income/expenses
public class Add extends JFrame {
    //GUI components
    private JPanel panel1;
    private JPanel panel;
    private JButton backButton;
    private JButton incomeButton;
    private JButton expensesButton;

    //constructor to initiaalize the "add" window
    public Add() {
        setContentPane(panel1);//set the content pane
        pack(); //resizes the frame
        setLocationRelativeTo(null); //position the frame in the center of the screen
        setSize(450,500);//set a fixed size for the frame
        setMinimumSize(new Dimension(450,500));
        setMaximumSize(new Dimension(450,500));
        setResizable(false);//make the frame non-resizable
        setTitle("Add Income/Expense");//set the title of the frame
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//set close operation
        setVisible(true);//make the frame visible

        //add action listeners to buttons
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open the home page window
                new HomePage();
                //dispose the current window
                dispose();
            }
        });
        expensesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open the expense window
                new Expense();
                //dispose the current window
                dispose();
            }
        });
        incomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open the income window
                new Income();
                //dispose the current window
                dispose();
            }
        });
    }

    //main method to run the application
    public static void main(String[] args) {
        //create an instance of the "add" window
        Add Add = new Add();
    }
}
