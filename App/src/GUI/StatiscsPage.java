package GUI;
import Database.Database;
import Implementation.*;
import Network.SendEmail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

/*
this class, represents the statistics page of the application
 */
public class StatiscsPage {
    //GUI components
    private JPanel mainPanel;
    private JPanel AnalysisPicPanel;
    private JPanel historyPanel;
    private JLabel income;
    private JLabel expenses;
    private JLabel total;
    private JButton backButton;
    private JLabel incomeView;
    private JLabel expenseView;
    private JLabel totalAllView;
    private JButton saveAsPdf;
    private JPanel email;
    private JButton sendemail;
    JFrame frame = new JFrame();//JFrame for displaying the statistics page

    //constructor for the StaticsPage class
    public StatiscsPage(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //terminates the program after hitting close button
        frame.setContentPane(mainPanel); //specify the panel to show
        frame.pack(); //resizes the frame
        frame.setLocationRelativeTo(null); //position the frame in the center of screen
        frame.setSize(450,500);//set fixed size for the frame
        frame.setMinimumSize(new Dimension(450,500));
        frame.setMaximumSize(new Dimension(450,500));
        frame.setResizable(false);//make the frame non-resizeable
        frame.setVisible(true); //shows it

        //create a pie chart for expense overview
        ExpenseOverview pie = new ExpenseOverview();
        AnalysisPicPanel.add(pie.showPie());
        historyPanel.add(pie.scrollPane());
        frame.validate();//refresh (put after pie)

        //put data
        try {
            //get and display total income, expenses, and balance
            int totalIncome= Database.getInstance().getTotalIncome(UserDistrubiter.getUser().getUserID());
            String totalI = Integer.toString(totalIncome);
            incomeView.setText(totalI);

            int totalExpense = Database.getInstance().getTotalExpense(UserDistrubiter.getUser().getUserID());
            String totale = Integer.toString(totalExpense);
            expenseView.setText("-"+ totale);

            int total = totalIncome - totalExpense;
            String totalAll = Integer.toString(total);
            totalAllView.setText(totalAll);

            if(totalExpense == 0){
                expenseView.setText(totale);
                JOptionPane.showMessageDialog(null, "There is no expenses to show");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //action listener for the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //display the home page
                new HomePage();
                //dispose the current frame
                frame.dispose();
            }
        });
        //action listener for the save as PDF button
        saveAsPdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //save the pie chart as a PDF file
                    pie.saveAsPDF();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //action listener for the Send Email button
        sendemail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                    it will send an email to the user
                new SendEmail();

            }
        });
    }
    //main method to start the statistics page
    public static void main(String[] args) {
        new StatiscsPage();
    }
}
