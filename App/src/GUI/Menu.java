package GUI;

import Database.Database;
import Implementation.UserDistrubiter;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*this class represents the menu screen of the application, it provides options for the
    user to navigate to different parts of the application
 */
public class Menu {
    //GUI components
    private JPanel MainPanel;
    private JButton statisticsButton;
    private JButton logOutButton;
    private JButton backButton;
    private JButton resetDatabaseButton;
    JFrame frame = new JFrame();

    //constructor to initialize the menu GUI
    public Menu() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //terminates the program after hitting close button
        frame.setContentPane(MainPanel); //specify the panel to show
        frame.pack(); //resizes the frame
        frame.setLocationRelativeTo(null); //position the frame in the center of screen
        frame.setSize(300,400);//set fixed size for the frame
        frame.setMinimumSize(new Dimension(300,400));
        frame.setMaximumSize(new Dimension(300,500));
        frame.setResizable(false);//make the frame non-resizeable
        frame.setVisible(true);

        //action listener for the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //navigates back to the home page
                new HomePage();
                //disposes the current frame
                frame.dispose();
            }
        });

        //action listener for the statistics button
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //navigates to the statistics page
                new StatiscsPage();
                //disposes the current frame
                frame.dispose();
            }
        });

        //action listener for the logout button
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //logout the user and navigates back to the login page
                new login();
                //dispose hte current frame
                frame.dispose();
            }
        });

        //if the user is NOT admin, then the reset button should be hidden
        if(!UserDistrubiter.getUser().getUsername().equalsIgnoreCase("admin")){
            resetDatabaseButton.setVisible(false);
        }

        resetDatabaseButton.addActionListener(e -> {
            //check if the user is an admin
           if (UserDistrubiter.getUser().getUsername().equalsIgnoreCase("admin")){

               //if the user is an admin, reset the database
               Database.getInstance().resetDatabase();
               JOptionPane.showMessageDialog(null, "Database cleared successfully!");
               frame.dispose();//dispose the current frame
               new login();//navigate back to the login page
           }
        });
    }
    public static void main(String[] args) {
        //initializes the menu
        new Menu();
    }
}
