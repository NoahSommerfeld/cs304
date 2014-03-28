package userInterface;

import javax.swing.JPanel;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JButton;

public class LibrarianView extends ContentPane {
	public LibrarianView(MainWindow parent, Controller newSession) {
		super(parent, newSession);
		/*
		JLabel lblClerkYo = new JLabel("Librarian, yo");
		add(lblClerkYo, "2, 2, left, top");
		
		JButton btnHaahaha = new JButton("HAAHAHA");
		add(btnHaahaha, "2, 14");*/
	}

	@Override
	public void signOut() {
		//TODO put any saving methods here. Maybe DB.commit?
		
	}

}
