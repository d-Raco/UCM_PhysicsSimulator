package simulator.model;

import simulator.misc.Vector;

public class MassLossingBody extends Body{

	private double lossFactor, lossFrequency, counter;
	
	public MassLossingBody(String str, Vector v, Vector a, Vector p, double mass, double lossFactor, double lossFrequency) {
		super(str, v, a, p, mass);
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
		counter = 0;
	}
	
	void move(double t) {
		super.move(t);
		counter+= t;
		if(counter >= lossFrequency) { 
			/*massAux = super.getMass();
			massAux *= (1 - lossFactor); */
			mass *= (1 - lossFactor);
			counter = 0;
		}	
	}
}
