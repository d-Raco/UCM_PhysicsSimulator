package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings({ "serial" })
public class StatusBar extends JPanel implements SimulatorObserver {
	// ...
	private JLabel _currTime;	  // for current time
	private JLabel _currLaws;     // for gravity laws
	private JLabel _numOfBodies;  // for number of bodies
	private static final JLabel _Time  = new JLabel("Time: ");	  
	private static final JLabel _Laws = new JLabel("Laws: ");     
	private static final JLabel _Bodies = new JLabel("Bodies: ");
	
	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));

		JSeparator separator1 = new JSeparator(SwingConstants.VERTICAL);
		separator1.setPreferredSize(new Dimension(1, 20));
		JSeparator separator2 = new JSeparator(SwingConstants.VERTICAL);
		separator2.setPreferredSize(new Dimension(1, 20));
	
		_currTime = new JLabel();
		this.add(_Time);
		this.add(_currTime);
		this.add(Box.createRigidArea(new Dimension(25, 0)));
		this.add(separator1);
		
		_numOfBodies = new JLabel();
		this.add(_Bodies);
		this.add(_numOfBodies);
		this.add(Box.createRigidArea(new Dimension(25, 0)));
		this.add(separator2);
		
		_currLaws = new JLabel();
		this.add(_Laws);
		this.add(_currLaws);
		// TODO complete the code to build the tool bar
	}
	
	
	// other private/protected methods
	// ...
	
	
	// SimulatorObserver methods
	// ...
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currTime.setText(Double.toString(0.0));
				_numOfBodies.setText(Double.toString(bodies.size()));
				_currLaws.setText(gLawsDesc);
			}
		});
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currTime.setText(Double.toString(0.0));
				_numOfBodies.setText(Double.toString(0.0));
				_currLaws.setText(gLawsDesc);
			}
		});
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_numOfBodies.setText(Integer.toString(bodies.size()));		
			}
		});
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currTime.setText(Double.toString(Double.parseDouble(_currTime.getText()) + time));
			}
		});
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currTime.setText(Double.toString(Double.parseDouble(_currTime.getText()) + dt));
			}
		});
	}
	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currLaws.setText(gLawsDesc);
			}
		});
	}
}

