package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.DatabaseConnection;
import model.Model_Message;
import model.Model_Register;
import model.Model_User_Account;

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
            	con.setAutoCommit(false);
                p = con.prepareStatement("INSERT INTO test.user (username, password) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                p.setString(1, data.getUserName());
                p.setString(2, data.getPassword());
                p.execute();
                r = p.getGeneratedKeys();
                r.first();
                int userid = r.getInt(1);
                r.close();
                p.close();
                // insert User Account
                p = con.prepareStatement("INSERT INTO test.user_account (userid, username) values (?,?)");
                p.setInt(1, userid);
                p.setString(2, data.getUserName());
                p.execute();
                p.close();
                con.commit();
                con.setAutoCommit(true);
                message.setAction(true);
                message.setMessage("Ok");
                message.setData(new Model_User_Account(userid, data.getUserName(), "", "", true));
            }
            
        } catch (SQLException e) {
        	System.out.println(e);
            message.setAction(false);
            message.setMessage("Server Error");
            try {
				if (con.getAutoCommit() == false) {
					con.rollback();
					con.setAutoCommit(true);
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
        }
        
        return message;
    }
    public List<Model_User_Account> getUser(int exitUser) throws SQLException {
        List<Model_User_Account> list = new ArrayList<>();
        PreparedStatement p = con.prepareStatement("select userid, username, gender, imageString from test.user_account where test.user_account.`status`='1' and userid<>?");
        p.setInt(1, exitUser);
        ResultSet r = p.executeQuery();
        while (r.next()) {
            int userid = r.getInt(1);
            String userName = r.getString(2);
            String gender = r.getString(3);
            String image = r.getString(4);
            list.add(new Model_User_Account(userid, userName, gender, image, true));
        }
        r.close();
        p.close();
        return list;
    }
    //  SQL statements
    //  Instance
    private final Connection con;
}
