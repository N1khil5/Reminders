/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remconsole;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nzs52
 */
public class RemConsole {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Reminder", "username", "password");
            Statement statement = con.createStatement();
            System.out.println("Would you like to:");
            System.out.println("1. Add a reminder");
            System.out.println("2. View reminders");
            System.out.println("3. Edit a reminder");
            System.out.println("4. Exit");

            Scanner s = new Scanner(System.in);
            int answer = s.nextInt();
            while (true) {
                switch (answer) {
                    case 1:
                        System.out.println("You have chosen to add a reminder");
                        String id = UUID.randomUUID().toString();
                        System.out.println("Please enter your reminder below.");
                        String reminder = s.nextLine();
                        System.out.println("Your unique identifier ID for your reminder is " + id);
                        statement.execute("INSERT INTO REMINDERS(" + id + "," + reminder);
                        break;
                        
                    case 2:
                        System.out.println("You have chosen to view reminders");
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM REMINDERS");
                        ResultSetMetaData rsmd = resultSet.getMetaData();
                        int colNumber = rsmd.getColumnCount();
                        while (resultSet.next()) {
                            for(int i =1;i<=colNumber; i++)
                            {
                                if (i>1) System.out.print(", ");
                                String colValue = resultSet.getString(i);
                                System.out.print(colValue + " " + rsmd.getColumnName(i));
                            }
                            System.out.println("");
                        }
                        break;
                        
                    case 3:
                        System.out.println("You have chosen to edit a reminder");
                        System.out.println("Please enter the UUID from the reminder you wish to edit.");
                        String uuidCheck = s.next();
                        ResultSet idCheck = statement.executeQuery("SELECT * FROM REMINDER WHERE ReminderID = " + uuidCheck);
                        if(!idCheck.next()) {
                            System.out.println("There is no reminder matching this ID.");
                        }
                        else {
                            System.out.println("Please write the updated reminder.");
                            String update = s.nextLine();
                            statement.execute("UPDATE FROM REMINDER WHERE REMINDERID = " + idCheck + "AND SET REMINDER =" + update);
                        }
                        break;
                        
                    case 4:
                        System.exit(0);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(RemConsole.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

