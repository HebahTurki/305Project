package MultiThread;

import GUI.HomePage;
/*
the userThread class implements the Runnable interface tp create a new thread
    for displaying the homePage GUI.
this class is used to handle multiple users by running each user's homePage GUI in a separate thread
 */
public class userThread implements Runnable {
    //constructor
    public userThread() {
    }

    /*the run method is called when the thread is started.
        it creates a new instance of the homePage GUI and sets it visible
     */
    @Override
    public void run() {
        //create a new instance of HomePage GUI
        HomePage homePage = new HomePage();
        //set the HomePage GUI visible
        homePage.setVisible(true);
    }
}
