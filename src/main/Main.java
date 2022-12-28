package main;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Connection.DatabaseConnection;
import service.Service;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	public Main() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 612, 407);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 576, 346);
		contentPane.add(textArea);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
	        public void windowOpened(java.awt.event.WindowEvent evt) {
	            formWindowOpened(evt);
	        }
	    });
		
	}
	
	private void formWindowOpened(java.awt.event.WindowEvent evt) {
	    try {
	        DatabaseConnection.getInstance().connectToDatabase();
	        Service.getInstance(textArea).startServer();
	    } catch (Exception e) {
	        textArea.append("Error: " + e + "\n");
	    }
	}
}
