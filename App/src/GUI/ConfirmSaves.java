
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//this class represents the confirmation window for saving transactions
public class ConfirmSaves extends JFrame{
    //GUI components
    private JPanel panel2;
    private JPanel panel1;
    private JLabel message;
    private JButton OK;

    //constructor to initialize the confirmation window
    public ConfirmSaves(){
        setContentPane(panel1);//set the content pane
        pack(); //resizes the frame
        setLocationRelativeTo(null); //position the frame in the center of screen
        setSize(450,500);//set a fixd size of the frame
        setMinimumSize(new Dimension(450,500));
        setMaximumSize(new Dimension(450,500));
        setResizable(false);//make the frame non-resizable
        setTitle("Add Income/Expense");//set the title of the frame
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//set close operation
        setVisible(true);//make the frame visible

        //add action listener to OK button
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open the "add" window
                new Add();
                //dispose the current window
                dispose();
            }
        });
    }

    //main method to run the application
    public static void main(String[] args) {
        //create an instance of the confirmation window
        ConfirmSaves Add = new ConfirmSaves();
    }
}
