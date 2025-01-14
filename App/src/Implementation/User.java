package Implementation;

import Database.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
this class, represents a user in the system
 */
public class User {
    private String userID;//the ID of the user
    private String username;//the username of the user
    private String password;//the password of the user
    private String email;//the email address of the user
    private DefaultTableModel model;//the table model for user transaction history

    //constructors
    public User(){
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    ///setters & getters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    JTable table;//the table for user transaction history


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //method to retrieve the transaction history table for the user, return the transaction history table
    public JTable getTable() throws SQLException {

        //get number of rows for transaction for id = user id
        int rows = Database.getInstance().getTransactionCount(userID);

        String[] a = {"Category","Amount"};

        //create table model to fill data
        model = new DefaultTableModel(null,a);
        table = new JTable(model);

        //get result set for transaction
        ResultSet r = Database.getInstance().transactionResultSet(userID);
        //fill table model
        for (int i = 0; i < rows ; i++ ){
            //get category and amount from database
            while (r.next()){
                String category = r.getString("category");
                float amount = r.getFloat("amount");
                Object[] data = {category,amount};
                model.addRow(data);
            }
        }
        return table;
    }
    //retrieves the transaction history list for the user, return the transaction history list
    public JList getHistoryList() {

        JList list;
        try {
            //call getTable method
            table = getTable();
            String[] ecategories = {"Bills", "Shopping", "Food"}; //expense categories

            //create list to display using Jlist
            DefaultListModel dlm = new DefaultListModel();
            list = new JList(dlm);

            //print list
            for (int i = 0; i < table.getRowCount(); i++) {
                String name = table.getValueAt(i, 0).toString();
                double amt = Double.valueOf(table.getValueAt(i, 1).toString());

                if (check(ecategories, name)) {
                    dlm.addElement(name + " -" + amt);
                } else {
                    dlm.addElement(name + " +" + amt);

                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //check if the given name exists in the array
    public boolean check(String[] arr, String name){
        for (int j = 0; j < 3 ; j++){
            if (name.equalsIgnoreCase(arr[j])){
                return true;
            }
        }return false;
    }
}
