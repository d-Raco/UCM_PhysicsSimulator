package simulator.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private Controller _ctrl;
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		// TODO complete this method to build the GUI
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 40);
		this.setVisible(true);
		
		ControlPanel ctrl_panel = new ControlPanel(_ctrl);
		StatusBar statusbar = new StatusBar(_ctrl);
		JPanel panel = new JPanel();
		panel.setVisible(true);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		BodiesTable bt = new BodiesTable(_ctrl);
		bt.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 50));
		panel.add(bt);
		Average av = new Average(_ctrl);
		av.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 50));
		panel.add(av);
		Viewer vw = new Viewer(_ctrl);
		vw.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 200));
		panel.add(vw);
		
		mainPanel.add(ctrl_panel, BorderLayout.PAGE_START);
		mainPanel.add(statusbar, BorderLayout.PAGE_END);
		mainPanel.add(panel, BorderLayout.CENTER);
		
	}
	
	// other private/protected methods
	// ...
}
