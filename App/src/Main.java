import Database.Database;
import GUI.login;
import Implementation.User;
import Implementation.UserDistrubiter;

/*
the main class is the entry point of the application,
it initializes the database, creates a user instance, and sets up user distribution.
finally, it launches the login GUI
 */
public class Main {
    public static void main(String[] args) {
        //initialize the database
        Database database = Database.getInstance();

        //create a new user instance
        User user = new User();

        //set up user distribution
        UserDistrubiter userDistrubiter = new UserDistrubiter(user);

        //launch the login GUI
        new login();
    }
}