package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;


public class MainWindow extends JFrame {
	
	private Controller session;
	private JPanel loginPanel;
	private JPanel clerkPanel;


	/**
	 * Launch the application.
	 *//*// don't really need a MAIN here. 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the application.
	 */
	public MainWindow(Controller theSession) {
		session = theSession;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSignOut = new JMenuItem("Sign Out");
		mnFile.add(mntmSignOut);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				session.exit(0);
			}
		});
		mnFile.add(mntmExit);
		

		
		loginPanel = new LoginPanel(this);
		getContentPane().add(loginPanel, BorderLayout.CENTER);
	}

	public void displayClerkPanel(){
		if(loginPanel != null || loginPanel.getParent() != null){
			getContentPane().remove(loginPanel);
			loginPanel.setVisible(false);
		}
		if(clerkPanel == null){
			clerkPanel = new ClerkView();
		}

		getContentPane().add(clerkPanel, BorderLayout.CENTER);
		this.repaint();
		}
	}

