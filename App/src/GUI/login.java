package GUI;

import Database.Database;
import Implementation.UserDistrubiter;
import MultiThread.userThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static Database.Database.isExist;

//this class represents the login GUI
public class login extends JFrame {
    //GUI components
    private JPanel panel1;
    private JLabel logo;
    private JLabel username;
    private JPasswordField passwordField1;
    private JButton button1Login;
    private JButton signUpButton;
    private JLabel laballogo;
    private JTextField UsernameField;

    //constructor to initialize the login GUI
    public login() {
        setContentPane(panel1);//set the content pane
        pack(); //resizes the frame
        setLocationRelativeTo(null); //position the frame in the center of screen
        setSize(450,500);//set fixed size for the frame
        setMinimumSize(new Dimension(450,500));
        setMaximumSize(new Dimension(450,500));
        setResizable(false);//make the frame non-resizeable
        setTitle("Login");//set the title of the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//set close operation
        setVisible(true);

        //action listener for the login button
        button1Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //retrieve the username from the username field
                String username = UsernameField.getText();
                //retrieve the password from the password field and covert it to a string
                String password = new String(passwordField1.getPassword());
                try {
                    if(!isExist(username ,password)){// if username/password are wrong , user cant login and message appear
                        //display an error message if the username/password is incorrect
                        JOptionPane.showMessageDialog(null, "Username/Password is incorrect . please try again");
                    }
                    else{
                        //set the username/password for the current user instance
                        UserDistrubiter.getUser().setUsername(username);
                        UserDistrubiter.getUser().setPassword(password);
                        //retrieve email from the database and set it for the current user instance
                        String email = Database.getInstance().retrieveEmail(username);
                        UserDistrubiter.getUser().setEmail(email);
                        //save the user ID from the database to the current user instance
                        Database.getInstance().saveUserID(UserDistrubiter.getUser());
                        // start a thread to handle multiple user
                        Runnable runnable = new userThread();
                        Thread thread = new Thread(runnable);
                        thread.start();
                        //close the login window
                        dispose();
                    }
                } catch (SQLException ex) {
                    //handle any SQL exception by throwing a runtime exception
                    throw new RuntimeException(ex);
                }
            }
        }); // end of login button

        //action listener for the sign-up button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //open the sign-up window
                    signUp signUp = new signUp();
                    signUp.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    //main method to run the login GUI
    public static void main(String[] args) {
        new login();
    }


}
