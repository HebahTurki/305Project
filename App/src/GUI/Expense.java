package GUI;

import Database.Database;
import Implementation.UserDistrubiter;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

//this class represents the GUI window for adding expenses
public class Expense extends JFrame {
    //GUI components
    private JButton BackButton;
    private JButton saveButton;
    private JTextField noteTextField;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panel41;
    private JRadioButton cashRadioButton;
    private JRadioButton cardRadioButton;
    private JTextField expenseTextField;
    private JRadioButton oneTimeRadioButton;
    private JRadioButton yearlyRadioButton;
    private JRadioButton monthlyRadioButton;
    private JRadioButton dailyRadioButton;
    private final ButtonGroup bg;
    private final ButtonGroup bg2;
    private final JComboBox<String> dropdown;
    private final JDatePickerImpl datePicker;

    //constructor to initialize the "Expense" window
    public Expense() {
        setContentPane(panel1);//set the content pane
        pack(); //resizes the frame
        setLocationRelativeTo(null); //position the frame in the center of screen
        setSize(450, 500);//set fixed size for the frame
        setMinimumSize(new Dimension(450, 500));
        setMaximumSize(new Dimension(450, 500));
        setResizable(false);//make the frame non-resizeable
        setTitle("Add Expense");//set the title of the frame
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//set close operation

        //ButtonGroup for payment method radio buttons
        bg = new ButtonGroup();
        bg.add(cashRadioButton);
        bg.add(cardRadioButton);

        //ButtonGroup for frequency radio buttons
        bg2 = new ButtonGroup();
        bg2.add(oneTimeRadioButton);
        bg2.add(yearlyRadioButton);
        bg2.add(monthlyRadioButton);
        bg2.add(dailyRadioButton);

        //drop down for expense categories
        String[] options = {"Bills", "Shopping", "Food"};
        dropdown = new JComboBox<>(options);
        panel41.add(dropdown);

        //date picker
        DatePicker createDatePicker = new DatePicker();
        datePicker = createDatePicker.DatePicker(panel6);
        panel6.add(datePicker);

        pack(); //resizes the frame
        setVisible(true);//make the frame visible

        //action listener for back button
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open the "add" window
                new Add();
                //dispose the current window
                dispose();
            }
        });

        //action listener for the save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //set action command for the payment method radio buttons
                cashRadioButton.setActionCommand("Cash");
                cardRadioButton.setActionCommand("Card");

                //set action command for frequency radio button
                oneTimeRadioButton.setActionCommand("One Time");
                yearlyRadioButton.setActionCommand("Yearly");
                monthlyRadioButton.setActionCommand("Monthly");
                dailyRadioButton.setActionCommand("Daily");
                try {
                //get data from user input
                    String expense = expenseTextField.getText();
                    SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
                    Object date = datePicker.getModel().getValue();
                    java.sql.Date sqldate = java.sql.Date.valueOf(dtf.format(date));
                    String category = dropdown.getSelectedItem().toString();
                    String note = noteTextField.getText();
                    String paymentMethod = bg.getSelection().getActionCommand();
                    String frequency = bg2.getSelection().getActionCommand();

                    //add transaction to database

                    Database.getInstance().addIncomeTransaction(UserDistrubiter.getUser().getUserID(), expense, sqldate, category, note, paymentMethod, frequency);
                    //open the confirmation window
                    new ConfirmSaves();
                    //dispose the current window
                    dispose();
                }catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(null, "Information missing: please fill all data required");
                }catch (NullPointerException ex){
                    JOptionPane.showMessageDialog(null, "Information missing: please select the missing options");
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

    }

    //main method to run the application
    public static void main(String[] args) {
        //create an instance of the "Expense" window
        Expense Add = new Expense();
    }
}

