package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements SimulatorObserver {
	// ...
	private Controller _ctrl;
	private JToolBar _toolBar;
	private JButton _loadButton, _runButton, _stopButton, _quitButton, _gravityButton;
	private JSpinner _stepsSpinner, _delaySpinner;
	private JTextField _deltaTime;
	private JFileChooser _fileChooser;
	private volatile Thread _thread;
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		// TODO build the tool bar by adding buttons, etc.
		setLayout(new BorderLayout());
		_toolBar = new JToolBar();
		add(_toolBar, BorderLayout.PAGE_START);
		
		_fileChooser = new JFileChooser();
		_loadButton = new JButton();
		_loadButton.setToolTipText("Load bodies file into the editor");
		_loadButton.setIcon(loadImage("resources/icons/open.png"));
		_loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
		});
		_toolBar.add(_loadButton);
		_fileChooser.setCurrentDirectory(new File("C:\\Users\\R\\eclipse-workspace\\PhysicsSimulatorV2\\resources"));
		_toolBar.addSeparator();
		
		
		_gravityButton = new JButton();
		_gravityButton.setToolTipText("Select gravity strategy");
		_gravityButton.setIcon(loadImage("resources/icons/physics.png"));
		_gravityButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectGravityStrategy();
			}
		});
		_toolBar.add(_gravityButton);
		_toolBar.addSeparator();
		
		
		_runButton = new JButton();
		_runButton.setToolTipText("Run the simulator");
		_runButton.setIcon(loadImage("resources/icons/run.png"));
		_runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				try {
					_ctrl.setDeltaTime(Double.parseDouble(_deltaTime.getText()));
					_thread = new Thread(){
						public void run() {
							run_sim((int) _stepsSpinner.getValue(), Long.parseLong("" + _delaySpinner.getValue()));
							SwingUtilities.invokeLater( new Runnable() {
								
								@Override
								public void run() {
									enableButtons(true);
									_thread = null;	
								}
							});
						}
					};
					_thread.start();
				} catch (NumberFormatException error) {
					JOptionPane.showMessageDialog(null, "Invalid delta time.", "Error running the simulation", JOptionPane.ERROR_MESSAGE);
					enableButtons(true);
				}
			}
		});
		_toolBar.add(_runButton);
		_toolBar.addSeparator();
		
		
		_stopButton = new JButton();
		_stopButton.setToolTipText("Stop the simulator");
		_stopButton.setIcon(loadImage("resources/icons/stop.png"));
		_stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(_thread != null) 
					_thread.interrupt();
			}
		});
		_toolBar.add(_stopButton);
		
		_delaySpinner = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
		_delaySpinner.setToolTipText("Delay between consecutive steps");
		_delaySpinner.setMaximumSize(new Dimension(60, 40));
		_delaySpinner.setMinimumSize(new Dimension(60, 40));
		_delaySpinner.setPreferredSize(new Dimension(60, 40));
		_toolBar.add(new JLabel(" Delay"));
		_toolBar.addSeparator();
		_toolBar.add(_delaySpinner);
		
		
		_stepsSpinner = new JSpinner(new SpinnerNumberModel(10000, 0, 100000, 100));
		_stepsSpinner.setToolTipText("Simulation steps which are executed");
		_stepsSpinner.setMaximumSize(new Dimension(60, 40));
		_stepsSpinner.setMinimumSize(new Dimension(60, 40));
		_stepsSpinner.setPreferredSize(new Dimension(60, 40));
		_toolBar.add(new JLabel(" Steps"));
		_toolBar.addSeparator();
		_toolBar.add(_stepsSpinner);
		
		
		_deltaTime = new JTextField(10);
		_deltaTime.setToolTipText("Delay between steps in milliseconds");
		_deltaTime.setMaximumSize(new Dimension(70, 70));
		_deltaTime.setMinimumSize(new Dimension(70, 70));
		_toolBar.add(new JLabel(" Delta-Time"));
		_toolBar.addSeparator();
		_toolBar.add(_deltaTime);
		
		
		_toolBar.add(Box.createGlue());
		_toolBar.addSeparator();
		
		
		_quitButton = new JButton();
		_quitButton.setToolTipText("Exit");
		_quitButton.setIcon(loadImage("resources/icons/exit.png"));
		_quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});
		_toolBar.add(_quitButton);
	}
	
	// other private/protected methods
	// ...

	protected void selectGravityStrategy() {
		List<JSONObject> _gravityFactoryOptions = _ctrl.getGravityLawsFactory().getInfo();
		String[] options = new String[_gravityFactoryOptions.size()];
		for(int i = 0; i < options.length; ++i) {
			options[i] =  _gravityFactoryOptions.get(i).getString("desc") + " (" +  _gravityFactoryOptions.get(i).getString("type") + ")";
		}
		
		String str = (String) JOptionPane.showInputDialog(this.getParent(), "Select gavity laws to be used.", "Gravity Laws Selector", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		JSONObject option = null;
		for(int i = 0; i < options.length; ++i) {
			if(str.equals(options[i])) {
				option = _gravityFactoryOptions.get(i);
				break;
			}
		}
		_ctrl.setGravityLaws(option);
	}
	
	protected void loadFile() {
		int value = _fileChooser.showOpenDialog(this.getParent());
		if(value == JFileChooser.APPROVE_OPTION) {
			File file = _fileChooser.getSelectedFile();
			try {
				_ctrl.reset();
				_ctrl.loadBodies(new FileInputStream(file));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this.getParent(), "Something went wrong while loading the file.", "Error loading the file", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	protected ImageIcon loadImage(String direction) {
		return new ImageIcon(direction);
	}
	
	protected void quit() {
		int pane = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(pane == 0) {
			System.exit(0);
		}
	}
	
	protected void setDeltaTime(double dt) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_deltaTime.setText("" + dt);
			}
		});
	}
	
	private void run_sim(int n, long delay) {
		while (n > 0 && !_thread.isInterrupted()) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(null, "Something went wrong while running the simulation.", "Error running the simulation", JOptionPane.ERROR_MESSAGE);
					}
				});
				return;
			}
		
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				_thread.interrupt();
			}
			
			n--;
		}
	}
	
	protected void enableButtons(boolean b) {
		_loadButton.setEnabled(b);
		_runButton.setEnabled(b);
		_quitButton.setEnabled(b);
		_gravityButton.setEnabled(b);
		_stepsSpinner.setEnabled(b);
		_deltaTime.setEnabled(b);
		_delaySpinner.setEnabled(b);
	}
	
	// SimulatorObserver methods
	// ...
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_deltaTime.setText("" + dt);
			}
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_deltaTime.setText("" + 2500.0);
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_deltaTime.setText("" + dt);
			}
		});
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
				
	}
}