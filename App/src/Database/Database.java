package Database;
import Implementation.User;
import Implementation.UserDistrubiter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    //singleton instance of the Database class
    private static Database instance;
    //Connection object for database access
    private static Connection con = null;
    //default password for database access
    private static String myPassword = "Hlliiv87!."; //change password easily

    //Constructor for Database class
    private Database(){
        System.out.println("Creating database...");

        try
        {
            // set the path for the database
            String ConnectionURL = "jdbc:mysql://localhost:3306";

            // create connection
            con = DriverManager.getConnection(ConnectionURL,"root",myPassword) ;

            // create statement object
            Statement st = con.createStatement();

            String database = "muhfazatak";

            // execute sql statement
            st.executeUpdate("CREATE DATABASE "+database);

            System.out.println("Database created");

            // close connection
            con.close();

            //create tables
            createUserTable();
            createTransTable();
            createUserBalance();
        }
        catch (SQLException s){
            System.out.println("Database already exist!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //this method is to create an instance of the Database class (Singleton pattern)
    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }
    //this method is to create a table called "userinfo"
    private static void createUserTable() throws SQLException {
        //Get connection to the database
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL, "root", myPassword);

        //to check if the table already exists
        boolean tableExists = tableExists(con,"userInfo");
        Statement st = con.createStatement();
        if (!tableExists){
            // create table userInfo
            String userTable = "CREATE TABLE userInfo (\n"
                    + " userID INT PRIMARY KEY AUTO_INCREMENT ,\n"
                    + " username VARCHAR(25) ,\n"
                    + " email VARCHAR(25),\n"
                    + " password VARCHAR(25)\n"
                    + ");";
            st.execute(userTable);
            System.out.println("Table: userInfo created sucessfuly!");
        }
    }
    //this method is to create a table called "transaction"
    private static void createTransTable() throws SQLException {
        //Get connection to the database
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL, "root", myPassword);

        //to check if the table already exists
        boolean tableExists = tableExists(con,"transaction");
        Statement st = con.createStatement();
        if (!tableExists){
            // create table transaction
            String transTable = "CREATE TABLE transaction (\n"
                    + " transID INT PRIMARY KEY AUTO_INCREMENT ,\n"
                    + " userID INT NOT NULL ,\n"
                    + " method VARCHAR(25),\n"
                    + " date DATE,\n"
                    + " category VARCHAR(25),\n"
                    + " amount FLOAT(10, 2),\n"
                    + " note VARCHAR(255),\n"
                    + " frequency VARCHAR(25),\n"
                    + " FOREIGN KEY (userID) REFERENCES userInfo (userID)"
                    + ");";
            st.execute(transTable);
            System.out.println("Table: transaction created sucessfuly!");
        }
    }

    //this method is to create a table called "userBalance"
    private static void createUserBalance() throws SQLException {
        //Get connection to the database
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL, "root", myPassword);

        //to check if the table already exists
        boolean tableExists = tableExists(con,"userBalance");
        Statement st = con.createStatement();
        if (!tableExists){
            // create table balance
            String userBalance = "CREATE TABLE userBalance (\n"
                    + " userID INT NOT NULL ,\n"
                    + " total FLOAT(10, 2),\n"
                    + " income FLOAT(10, 2),\n"
                    + " expense FLOAT(10, 2),\n"
                    + " FOREIGN KEY (userID) REFERENCES userInfo (userID)"
                    + ");";
            st.execute(userBalance);
            System.out.println("Table: userBalance created sucessfuly!");
        }
    }
    //method to check if tables exists in the database
    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});

        return resultSet.next();
    }

    // method to check if the username/ password is exists in the db or not
    public static boolean isExist(String username, String password) throws SQLException {
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL,"root",myPassword) ;
        Statement st = con.createStatement();
        String emailQuery = "SELECT username FROM userInfo WHERE username = '" + username + "' AND password = '"+ password + "'" ;
        ResultSet rs = st.executeQuery(emailQuery);
        return rs.next();


    }
    // method to check if username or email already exists
    public static boolean isTaken(String username, String email) throws SQLException {
        // method to check weather the username/ email is taken or not, return true if its taken , else false..
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL,"root",myPassword) ;
        Statement st = con.createStatement();

        String emailQuery = "SELECT email FROM userInfo WHERE username = '" + username + "' AND email = '" + email + "'";
        ResultSet rs = st.executeQuery(emailQuery);
        return rs.next();
    }
    //method to add user in user information into the database
    public void addUserInfo(String username, String email,String password) throws SQLException {
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL,"root",myPassword) ;
        //addUserInfo(username,email,password,con);
        Statement st = con.createStatement();
        String insertQuery = " INSERT INTO userInfo (username , email , password)" +
                "VALUES('" + username + "', '" + email + "', '" + password + "')";
        st.executeUpdate(insertQuery);
    }
    //this method is to retrieve the email of a specific user
    public String retrieveEmail(String username) throws SQLException {
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL,"root",myPassword) ;
        Statement st = con.createStatement();
        String emailQuery = "SELECT email FROM userInfo WHERE username = '" + username + "'";
        ResultSet rs = st.executeQuery(emailQuery);
        String email = "";
        while (rs.next()){
            email = rs.getString("email");
        }
        return email;

    }

    //method to retrieve the user ID and set it in the User object
    public String saveUserID(User user) throws SQLException {
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL,"root",myPassword) ;
        Statement st = con.createStatement();

        String userIDQuery = "SELECT userID FROM userInfo WHERE username = '" + user.getUsername() + "' AND email = '" + user.getEmail() + "'";
        ResultSet rs = st.executeQuery(userIDQuery);
        int userID = 0;
        while (rs.next()){
            userID = rs.getInt("userID");
        }
        String sUserID = String.valueOf(userID);
        user.setUserID(sUserID);
        rs.close();
        st.close();
        return sUserID;
    }
    //method to add an income transaction
    public void addIncomeTransaction(String userID,String amount, java.sql.Date date, String category, String note, String paymentMethod, String frequency) throws SQLException {
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL,"root",myPassword) ;
        userID = saveUserID(UserDistrubiter.getUser());
        Statement st = con.createStatement();
        String insertQuery = " INSERT INTO transaction(userID,amount , date , category, note, method, frequency)" +
                "VALUES('"+ userID +"', '" + amount + "', '" + date + "', '" + category +"', '" + note +"', '" +paymentMethod+"', '" +frequency+ "')";
        st.executeUpdate(insertQuery);
    }
    //method to get the count of transactions for a user
    public int getTransactionCount(String userID) throws SQLException {
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL,"root",myPassword) ;
        Statement st = con.createStatement();
        ResultSet r = st.executeQuery("SELECT COUNT(*) AS recordCount FROM transaction WHERE userID = '" + userID + "'");
        int count = 0;
        while (r.next()){
            count = r.getInt("recordCount");
        }

        r.close();
        return count;
    }
    //method to get the result set of transactions for a user
    public ResultSet transactionResultSet(String userID) throws SQLException {
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL,"root",myPassword) ;
        Statement st = con.createStatement();
        ResultSet r = st.executeQuery("SELECT * FROM transaction WHERE userID = '" + userID + "'");
        return r;
    }

    //method to get the total income for a user
    public int getTotalIncome(String userID) throws SQLException {
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL,"root",myPassword) ;
        Statement st = con.createStatement();
        ResultSet r = st.executeQuery("SELECT * FROM transaction WHERE userID = '" + userID + "'");
        String[] options = {"Gift","Salary","Sale"};
        double number = 0;
        int total = 0;
        while (r.next()){
            String category = r.getString("category");
            String amount = r.getString("amount");
            for (int i = 0; i < options.length ; i++){
                if (category.equalsIgnoreCase(options[i])){
                    number = Double.parseDouble(amount);
                    total =(int) (total + number);
                }
            }
        }

        return total;
    }
    //method to get the total expense for a user
    public int getTotalExpense(String userID) throws SQLException {
        String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
        con = DriverManager.getConnection(ConnectionURL,"root",myPassword) ;
        Statement st = con.createStatement();
        ResultSet r = st.executeQuery("SELECT * FROM transaction WHERE userID = '" + userID + "'");
        String[] options = {"Bills","Shopping","Food"};
        double number = 0;
        int total = 0;
        while (r.next()){
            String category = r.getString("category");
            String amount = r.getString("amount");
            for (int i = 0; i < options.length ; i++){
                if (category.equalsIgnoreCase(options[i])){
                    number = Double.parseDouble(amount);
                    total =(int) (total + number);
                }
            }
        }

        return total;
    }
    //Method to reset the database by dropping and recreating all tables
    public void resetDatabase(){
        try {
            String ConnectionURL = "jdbc:mysql://localhost:3306/muhfazatak";
            con = DriverManager.getConnection(ConnectionURL,"root",myPassword) ;
            Statement st = con.createStatement();

            //Drop existing tables
            st.executeUpdate("DROP TABLE IF EXISTS userBalance");
            st.executeUpdate("DROP TABLE IF EXISTS transaction");
            st.executeUpdate("DROP TABLE IF EXISTS userInfo");

            //Recreate tables
            createUserTable();
            createTransTable();
            createUserBalance();

            System.out.println("Database reset successfully!");
        } catch (SQLException e) {
            throw new RuntimeException("Error resetting database: "+e.getMessage());
        }
    }
}
