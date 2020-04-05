package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class Average extends JPanel{
	
	Average(Controller ctrl) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2),"Average velocity",TitledBorder.LEFT, TitledBorder.TOP));
		// TODO complete

		AverageTable avg = new AverageTable(ctrl);
		JTable velocity = new JTable(avg);
		this.add(new JScrollPane(velocity));
		velocity.setFillsViewportHeight(true);
	}
}