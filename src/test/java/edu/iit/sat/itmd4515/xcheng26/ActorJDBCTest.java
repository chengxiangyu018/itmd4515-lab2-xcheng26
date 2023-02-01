/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.xcheng26;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Administrator
 */
public class ActorJDBCTest {
    
    private Connection getConnection() throws SQLException{
         String jdbcURL="jdbc:mysql://localhost:3306/sakila?zeroDateTimeBehavior=CONVERT_TO_NULL";
        String username="itmd4515";
        String password="itmd4515";
        return DriverManager.getConnection(jdbcURL, username, password); 
    }
    private void createAActor(Actor a) throws SQLException{
        String query = "insert into actor "
                +"(actor_id, first_name, last_name, last_update)"
                +"values(?,?,?,?)";
        try (PreparedStatement ps = getConnection().prepareStatement(query);) {
            ps.setInt(1, a.getId());
            ps.setString(2, a.getFirstName());
            ps.setString(3, a.getLastName());
            ps.setObject(4, a.getLastUpdate());
            ps.executeUpdate();
        }
    }
    
    private void updateAActor(Actor a) throws SQLException{
        String query = "update actor set "
                +"first_name = ?, "
                +"last_name = ?, "
                +"last_update = ? "
                +"where actor_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(query);) {
            
            ps.setString(1, a.getFirstName());
            ps.setString(2, a.getLastName());
            ps.setObject(3, a.getLastUpdate());
            ps.setInt(4, a.getId());
            ps.executeUpdate();
        }
    }
    @Test
    public void jdbcCreateTest() throws SQLException{
       
        Actor a = new Actor(
                999,
                "James",
                "Green",
                LocalDate.of(2021, 7, 12));
        
        createAActor(a);
        
        Actor actorReadBackFromDb=findAActor(999);
        
        Assertions.assertEquals(a.getId(), actorReadBackFromDb.getId());
        
        deleteAActor(999);
    }
    @Test
    public void jdbcReadTest() throws SQLException{
       
        Actor actorReadBackFromDb=findAActor(33);
        Assertions.assertNotNull(actorReadBackFromDb);
        Assertions.assertEquals(33, actorReadBackFromDb.getId());
        
        deleteAActor(999);
    }
    @Test
    public void jdbcUpdateTest() throws SQLException{
       
        Actor a = new Actor(
                999,
                "James",
                "Green",
                LocalDate.of(2021, 7, 12));
        
        createAActor(a);
        
        a.setFirstName("");
        updateAActor(a);
        
        
        Actor actorReadBackFromDb=findAActor(999);
        
        Assertions.assertEquals(a.getId(), actorReadBackFromDb.getId());
        //Assertions.assertEquals("Empty", actorReadBackFromDb.getFirstName());
        deleteAActor(999);
    }
    @Test
    public void jdbcDeleteTest() throws SQLException{
       Actor a = new Actor(
                888,
                "James",
                "Green",
                LocalDate.of(2021, 7, 12));
        
        createAActor(a);
        deleteAActor(888);
        Actor actorReadBackFromDb=findAActor(999);
        Assertions.assertNull(actorReadBackFromDb);
    }
    private Actor findAActor(Integer id) throws SQLException {
        String query = "SELECT * FROM actor where actor_id = ?";

        Actor a = null;

        try (PreparedStatement ps = getConnection().prepareStatement(query);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a = new Actor();
                a.setId(rs.getInt("actor_id"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                a.setLastUpdate(rs.getObject("last_update", LocalDate.class));
            }
        }

        return a;
    }
    
     private void deleteAActor(Integer id) throws SQLException{
        String query = "DELETE FROM actor where actor_id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(query);) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

}

