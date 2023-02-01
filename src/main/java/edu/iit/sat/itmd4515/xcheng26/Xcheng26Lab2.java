/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.iit.sat.itmd4515.xcheng26;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Administrator
 */
public class Xcheng26Lab2 {

    public static void main(String[] args) {
        
        String jdbcURL="jdbc:mysql://localhost:3306/sakila?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username="itmd4515";
        String password="itmd4515";
        String query = "SELECT * FROM customer where last_name = ?";
        
        try (Connection c = DriverManager.getConnection(jdbcURL, username, password); 
                PreparedStatement ps = c.prepareStatement(query);) {

            ResultSet rs;
            
            ps.setString(1, "SMITH");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                int customerId = rs.getInt("customer_id");
                String lastName = rs.getString("last_name");
                
                System.out.println(customerId + " " + lastName);
            }
            
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Xcheng26Lab2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
