package GUI;

import Database.Database;
import Implementation.UserDistrubiter;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

//this class represents the GUI for adding income transactions
public class Income {
    //GUI components
    private JPanel panel2;
    private JButton BackButton;
    private JButton saveButton;
    private JPanel panel3;
    private JPanel panel4;
    private JTextField noteTextField;
    private JPanel panel1;
    private JPanel panel31;
    private JPanel panel41;
    private JTextField IncomeTextField;
    private JRadioButton cashRadioButton;
    private JRadioButton cardRadioButton;
    private JRadioButton oneTimeRadioButton;
    private JRadioButton yearlyRadioButton;
    private JRadioButton monthlyRadioButton;
    private JRadioButton dailyRadioButton;
    private ButtonGroup bg,bg2;
    private JComboBox<String> dropdown;
    private JDatePickerImpl datePicker;
    JFrame frame = new JFrame();

    //constructor to initialize the income GUI
    public Income(){
        frame.setContentPane(panel1);//set the content pane
        frame.pack(); //resizes the frame
        frame.setLocationRelativeTo(null); //position the frame in the center of screen
        frame.setSize(450,500);//set fixed size for the frame
        frame.setMinimumSize(new Dimension(450,500));
        frame.setMaximumSize(new Dimension(450,500));
        frame.setResizable(false);//make the frame non-resizeable
        frame.setTitle("Add income");//set the title of the frame
        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//set close operation

        //ButtonGroup for payment method
        bg=new ButtonGroup();
        bg.add(cashRadioButton);bg.add(cardRadioButton);


        //ButtonGroup for frequency
        bg2=new ButtonGroup();
        bg2.add(oneTimeRadioButton);bg2.add(yearlyRadioButton);
        bg2.add(monthlyRadioButton);bg2.add(dailyRadioButton);

        //drop-down menu options
        String[] options = {"Gift","Salary","Sale"};
        dropdown = new JComboBox<>(options);
        panel41.add(dropdown);

        //date picker
        DatePicker createDatePicker = new DatePicker();
        datePicker = createDatePicker.DatePicker(panel31);
        panel31.add(datePicker);

        frame.setVisible(true);

        //back button action listener
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Add();
                frame.dispose();
            }
        });

        //save button action listener
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //set action commands for buttons
                cashRadioButton.setActionCommand("Cash");
                cardRadioButton.setActionCommand("Card");
                oneTimeRadioButton.setActionCommand("One Time");
                yearlyRadioButton.setActionCommand("Yearly");
                monthlyRadioButton.setActionCommand("Monthly");
                dailyRadioButton.setActionCommand("Daily");

                try {
                //get data from user input
                    String income = IncomeTextField.getText();
                    SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
                    Object date = datePicker.getModel().getValue();
                    java.sql.Date sqldate = java.sql.Date.valueOf(dtf.format(date));
                    String category = dropdown.getSelectedItem().toString();
                    String note = noteTextField.getText();
                    String paymentMethod = bg.getSelection().getActionCommand();
                    String frequency = bg2.getSelection().getActionCommand();

                //add transaction to database
                    Database.getInstance().addIncomeTransaction(UserDistrubiter.getUser().getUserID(), income,sqldate,category,note,paymentMethod,frequency);
                    //open confirmation window
                    new ConfirmSaves();
                    frame.dispose();

                }catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(null, "Information missing: please fill all data required");
                }catch (NullPointerException ex){
                    JOptionPane.showMessageDialog(null, "Information missing: please select the missing options");
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
    //main method to run the income GUI
    public static void main(String[] args) {
        Income income = new Income();
    }
}
