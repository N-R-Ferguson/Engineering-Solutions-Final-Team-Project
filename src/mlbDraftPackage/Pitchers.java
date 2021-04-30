package mlbDraftPackage;

public class Pitchers extends PlayerData {
	private double g;
	private double gs;
	private double era;
	private double ip;
	private double bb;

	public Pitchers() {
		super();
		this.g = 0.0;
		this.gs = 0.0;
		this.era = 0.0;
		this.ip = 0.0;
		this.bb = 0.0;
	}

	public Pitchers(String name, String position, double g, double gs, double era, double ip, double bb) {
		super(name, position);
		this.g = g;
		this.gs = gs;
		this.era = era;
		this.ip = ip;
		this.bb = bb;
	}
	
	public double getG() {
		return this.g;
	}

	public double getGS() {
		return this.gs;
	}

	public double getERA() {
		return this.era;
	}

	public double getIP() {
		return this.ip;
	}

	public double getBB() {
		return this.bb;
	}

	public String printData() {
		return getPlayerName() + " " + this.g + " " + this.gs + " " + this.era + " " + this.ip
				+ " " + this.bb;
	}
	
	public String toString() {
		return getPlayerName() + "," + getPosition() + "," + this.g + "," + this.gs + "," + this.era + "," + this.ip
				+ "," + this.bb;
	}

}
