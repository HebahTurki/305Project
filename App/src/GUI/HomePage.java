package GUI;

import Database.Database;
import Implementation.UserDistrubiter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

//this class represents the home page of the application
public class HomePage extends JFrame {
    //GUI components
    private JPanel HomePagePanel;
    private JButton menuButton;
    private JLabel income;
    private JLabel total;
    private JLabel expenses;
    private JButton addButton;
    private JPanel mainPanel;
    private JPanel historyPanel;
    private JLabel incomeView;
    private JLabel expenseView;
    private JLabel totalAllView;
    JFrame frame = new JFrame();//JFrame instance

    //constructor to initialize the home pag
    public HomePage(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //terminates the program after hitting close button
        frame.setContentPane(mainPanel); //specify the panel to show
        frame.pack(); //resizes the frame
        frame.setLocationRelativeTo(null); //position the frame in the center of screen
        frame.setSize(450,500);//set fixed size for the frame
        frame.setMinimumSize(new Dimension(450,500));
        frame.setMaximumSize(new Dimension(450,500));
        frame.setResizable(false);//make the frame non-resizable
        frame.setVisible(true); //make the frame visible
        History();
        //show income and expense and balance
        //retrieve from database
        try {
            int totalIncome = Database.getInstance().getTotalIncome(UserDistrubiter.getUser().getUserID());
            String totalI = Integer.toString(totalIncome);
            incomeView.setText(totalI);
            int totalExpense = Database.getInstance().getTotalExpense(UserDistrubiter.getUser().getUserID());
            String totale = Integer.toString(totalExpense);
            expenseView.setText("-"+ totale);
            //no minus infront of zero
            if(totalExpense == 0) {
                expenseView.setText(totale);
            }
            int total = totalIncome - totalExpense;
            String totalAll = Integer.toString(total);
            totalAllView.setText(totalAll);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        frame.validate();
        //add action listener to the "add" button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open the "add" window
                new Add();
                //dispose the current frame
                frame.dispose();
            }
        });
        //add action listener to the "Menu" button
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open the "Menu" button
                new Menu();
                //dispose the current window
                frame.dispose();
            }
        });
    }

    //method to display the transaction history
    public void History(){
        //scrollable history panel
        JScrollPane scrollPane = new JScrollPane(UserDistrubiter.getUser().getHistoryList());
        historyPanel.add(scrollPane);

    }
    //main method to run the application
    public static void main(String[] args) {
        //create an instance of the home page
        new HomePage();
    }

}
