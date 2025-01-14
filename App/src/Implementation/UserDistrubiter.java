package Implementation;
/*
this class is responsible for distributing the User object throughout the application
    it ensures the is only one instance of the User object available globally
 */
public class UserDistrubiter {
    //the static User object to be distributed
    static User user;

    //constructor UserDistribute object with the specified User instance
    public UserDistrubiter(User user){
        this.user = user;
    }

    //retrieves the User instance
    public static User getUser(){
        return user;
    }
}
