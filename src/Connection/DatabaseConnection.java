package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private static DatabaseConnection instance;
    private Connection conn;

    public static DatabaseConnection getInstance() {
        if (instance == null) {	
            instance = new DatabaseConnection();
        }	
        return instance;
    }

    private DatabaseConnection() {
        // Initialize the connection field
        conn = null;
    }

    public void connectToDatabase() throws SQLException {
    	try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test ","root","admin1234" );
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void setConnection(Connection connection) {
        this.conn = connection;
    }
    
    
}
