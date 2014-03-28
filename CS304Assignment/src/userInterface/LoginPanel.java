package userInterface;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import java.awt.Color;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;

public class LoginPanel extends JPanel {
	MainWindow parent;
	private JButton btnClerk;
	private JButton btnBorrower;
	private JButton btnLibrarian;
	public LoginPanel(MainWindow parent) {
		this.parent = parent;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("1px:grow"),
				ColumnSpec.decode("130px"),
				ColumnSpec.decode("1px:grow"),},
			new RowSpec[] {
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("1px:grow"),
				RowSpec.decode("125px"),
				RowSpec.decode("1px:grow"),}));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(SystemColor.control);
		add(panel, "3, 3, fill, fill");
		panel.setLayout(null);
		
		btnClerk = new JButton("Clerk");
		btnClerk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getMyParent().displayClerkPanel();
			}
		});
		btnClerk.setBounds(20, 12, 98, 25);
		panel.add(btnClerk);
		
		btnBorrower = new JButton("Borrower");
		btnBorrower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getMyParent().displayBorrowerPanel();
			}
		});
		btnBorrower.setBounds(20, 49, 98, 25);
		panel.add(btnBorrower);
		
		btnLibrarian = new JButton("Librarian");
		btnLibrarian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getMyParent().displayLibrarianPanel();
			}
		});
		btnLibrarian.setBounds(20, 86, 98, 25);
		panel.add(btnLibrarian);
	}
	private MainWindow getMyParent(){
		return parent;
	}
}
