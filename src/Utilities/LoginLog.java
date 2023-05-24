package Utilities;


import Controller.LoginController;
import Model.Users;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class that creates a log of all attempted logins.
 */
public class LoginLog {

    static Users currentUser;
    static ZoneId utcID = ZoneId.of("UTC");
    static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    //public LoginLog() {}

    /**
     * Keeps track of login attempts.
     *
     * @param username the username.
     * @param successful if login was successful or not.
     */
    public static void inputLog(String username, boolean successful) {

        LocalDateTime nowUTC = LocalDateTime.now(utcID);
        ZonedDateTime zoneUTC = nowUTC.atZone(utcID);
        //String dateTime = timeFormat.format(zoneUTC);
        String dateTime = "On this date and time: " + timeFormat.format(zoneUTC) + ", Username: " + username + " - ";


        try {
            FileWriter fw = new FileWriter("login_activity.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            currentUser = LoginController.getCurrentUser();
            if (successful) {
                dateTime += "successfully logged in.";
            }
            else {
                dateTime += "was denied access.";
            }
            pw.println(dateTime);
            pw.close();
            System.out.println(dateTime);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


