package dao;// file connection// tables 

import java.sql.Connection;// 
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Tables {

    public static void main(String[] args) {
        try {
            Connection con = ConnectionProvider.getCon();
            Statement stm = con.createStatement();

            // Creating the 'appuser' table
            stm.executeUpdate("CREATE TABLE IF NOT EXISTS appuser (appuser_pk INT AUTO_INCREMENT PRIMARY KEY, "
                    + "userRole VARCHAR(200), name VARCHAR(200), dob VARCHAR(200), mobileNumber VARCHAR(50), "
                    + "email VARCHAR(200), username VARCHAR(200), password VARCHAR(50), address VARCHAR(200))");

            // Inserting a sample record into the 'appuser' table
            stm.executeUpdate("INSERT INTO appuser (userRole, name, dob, mobileNumber, email, username, password, address) "
                    + "VALUES ('Admin', 'Admin', '20-02-2001', '01613805702', 'ahsanhaibrahat11@gmail.com', 'Ahsan', '123', 'Bangladesh')");

            // Creating the 'medicine' table
            stm.executeUpdate("CREATE TABLE IF NOT EXISTS medicine (medicine_pk INT AUTO_INCREMENT PRIMARY KEY, "
                    + "uniqueID VARCHAR(200), name VARCHAR(200), companyName VARCHAR(200), quantity BIGINT, price VARCHAR(200),buy VARCHAR(200) ,pack VARCHAR(200),alaram BIGINT)");
            stm.executeUpdate("create table if not exists bill (bill_pk int AUTO_INCREMENT primary key,billId varchar (200),billDate varchar(50),totalPaid BIGINT,generatedBy varchar(50),Due varchar(200),CustomerAddress varchar(200) )");
            // Creating 'withdraw' table
            stm.executeUpdate("create table if not exists withdraw (withdraw_pk int AUTO_INCREMENT primary key,reason varchar(200),issuedBy varchar(200),issuedTo varchar(200),amount varchar(200),mobile varchar(200),address varchar(200))");
            // Creating 'due' table
            stm.executeUpdate("create table if not exists due (due_pk int AUTO_INCREMENT primary key,due varchar(200),customerName varchar(200),mobileNumber varchar(200),address varchar(200))");

            JOptionPane.showMessageDialog(null, "Tables Created Successfully.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
