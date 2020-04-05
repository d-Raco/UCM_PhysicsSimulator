package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class AverageTable extends AbstractTableModel implements SimulatorObserver {
	
	private List<Body> _bodies;
	private static String[] columnNames = { "Identifier", "Average Velocity"};
	private int steps = 1;
	private double[] averageVelocity;

	AverageTable(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return _bodies.size();
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null;
		switch(columnIndex) {
		case 0:
			value = _bodies.get(rowIndex).getId();
			break;
		case 1:
			value = averageVelocity[rowIndex] / steps;
			break;
		}
		return value;
	}
	
	// SimulatorObserver methods
	// ...
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_bodies = bodies;
				averageVelocity = new double[_bodies.size()];
				for	(int i = 0; i < _bodies.size(); ++i) {
					averageVelocity[i] = _bodies.get(i).getVelocity().magnitude();
				}
				fireTableStructureChanged();
			}
		});	
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_bodies = bodies;
				steps = 1;
				for	(int i = 0; i < _bodies.size(); ++i) {
					averageVelocity[i] = 0;
				}
				fireTableStructureChanged();
			}
		});	
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_bodies = bodies;
				steps = 1;
				averageVelocity = new double[_bodies.size()];
				for	(int i = 0; i < _bodies.size(); ++i) {
					averageVelocity[i] = _bodies.get(i).getVelocity().magnitude();
				}
				fireTableStructureChanged();
			}
		});	
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_bodies = bodies;
				for (int i = 0; i < _bodies.size(); ++i) {
					averageVelocity[i] += _bodies.get(i).getVelocity().magnitude();
				}
				steps++;
				fireTableStructureChanged();
			}
		});	
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// TODO Auto-generated method stub
		
	}
}