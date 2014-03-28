package userInterface;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.sql.SQLException;

public abstract class ContentPane extends JPanel {
	
	protected MainWindow parent;
	private JLabel lblStatusLabel;
	private JLabel lblStatsLabel1;
	protected Controller mySession;
	
	public ContentPane(MainWindow parent, Controller session) {
		this.parent = parent;
		mySession = session;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		panel_1.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		lblStatsLabel1 = new JLabel("Current users in DB: ");
		panel_1.add(lblStatsLabel1);
		

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		lblStatusLabel = new JLabel("No recent transactions");
		panel.add(lblStatusLabel);
		
		if(parent == null || session == null){
			return;
		}

		refreshStatsLbls();

		
		

		
	}
	
	protected Controller getMySession(){
		return mySession;
	}
	protected void refreshStatsLbls(){
		try{
			setStatsLbls(mySession.getNumberOfUsers());
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(this, e.getMessage());
			setStatsLbls(-1);
		}
		
	}
	
	protected void setStatsLbls(int numUsers){
		lblStatsLabel1.setText("Current users in DB: " + numUsers);
	}
	public void setStatusLbl(String newMessage){
		lblStatusLabel.setText(newMessage);
	}
	public abstract void signOut();

}
