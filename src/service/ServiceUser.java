package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.DatabaseConnection;
import model.Model_Message;
import model.Model_Register;

public class ServiceUser {
    public ServiceUser() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    public Model_Message register(Model_Register data) {
        
        Model_Message message = new Model_Message();
        
        try {
        	PreparedStatement p = con.prepareStatement("SELECT * FROM test.user WHERE username = ? LIMIT 1");
        	p.setString(1, data.getUserName());
        	ResultSet r = p.executeQuery();
        	
            if (r.next()) {
            	System.out.println("co tk");
                message.setAction(false);
                message.setMessage("User Already Exist");
            } else {
            	System.out.println("khong co tk");
                message.setAction(true);
            }
            r.close();
            p.close();
            if (message.isAction()) {
                //  Insert User Register
                p = con.prepareStatement("INSERT INTO test.user (username, password) VALUES (?, ?)");
                p.setString(1, data.getUserName());
                p.setString(2, data.getPassword());
                p.execute();
                p.close();
                message.setAction(true);
                message.setMessage("Ok");
            }
            
        } catch (SQLException e) {
            message.setAction(false);
            message.setMessage("Server Error");
        }
        
        return message;
    }

    //  SQL statements
    //  Instance
    private final Connection con;
}
