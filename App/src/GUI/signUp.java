package GUI;

import Database.Database;
import Implementation.UserDistrubiter;
import MultiThread.userThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static Database.Database.isTaken;

/*
this class represents the sign-up screen of the application
 */
public class signUp extends JFrame {
    //GUI components
    private JTextField emailField1;
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JButton signUpButton;
    private JButton loginButton;
    private JPanel panel1;

    //constructor for the sign-up class, throw exception if a database error occurs
    public signUp() throws SQLException {
        setContentPane(panel1);//specify the panel to show
        pack(); //resizes the frame
        setLocationRelativeTo(null); //position the frame in the center of screen
        setSize(450,500);//set fixed size for the frame
        setMinimumSize(new Dimension(450,500));
        setMaximumSize(new Dimension(450,500));
        setResizable(false);//make the frame non-resizeable
        setTitle("Sign Up");//set the title of the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        //Invoked when an action occurs.
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //retrieve the user input
                    String username = usernameField.getText();
                    String email = emailField1.getText();
                    String password = new String(passwordField1.getPassword());

                    // check if username/email is already been taken
                    if(!isTaken(username , email)){
                        //add user info to the database
                        Database.getInstance().addUserInfo(username,email,password);

                        //set user information in the UserDistribute
                        UserDistrubiter.getUser().setUsername(username);
                        UserDistrubiter.getUser().setPassword(password);
                        UserDistrubiter.getUser().setEmail(email);

                        //get userid from database and save to instance
                        String userID = Database.getInstance().saveUserID(UserDistrubiter.getUser());

                        // start a thread to handle multiple user
                        Runnable runnable = new userThread();
                        Thread thread = new Thread(runnable);
                        thread.start();
                        //close the sign-up window
                        dispose();

                    }else {
                        JOptionPane.showMessageDialog(null, "Email/Username already used. please try again");
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });  // end of  signUpButton

        //action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login newlogin=  new login(); // display the login frame
                newlogin.setVisible(true);
                //close the sign-up window
                dispose();

            }

        }); // end of loginButton

    }


}

