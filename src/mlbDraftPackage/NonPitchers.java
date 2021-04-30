package mlbDraftPackage;

public class NonPitchers extends PlayerData {
	private double avg;
	private double obp;
	private double ab;
	private double slg;
	private double sb;

	public NonPitchers() {
		super();
		this.avg = 0.0;
		this.obp = 0.0;
		this.ab = 0.0;
		this.slg = 0.0;
		this.sb = 0.0;
	}

	public NonPitchers(String name, String position, double avg, double obp, double ab, double slg, double sb) {
		super(name, position);
		this.avg = avg;
		this.obp = obp;
		this.ab = ab;
		this.slg = slg;
		this.sb = sb;
	}
	
	// getters
	public double getAVG() {
		return avg;
	}

	public double getOBP() {
		return obp;
	}

	public double getAB() {
		return ab;
	}

	public double getSLG() {
		return slg;
	}

	public double getSB() {
		return sb;
	}

	
	
	public String toString() {
		return getPlayerName() + "," + getPosition() + "," + this.avg + "," + this.obp + "," + this.ab + "," + this.slg + "," + this.sb;
	}
	
	public String getPrintableData() {
		return getPlayerName() + " " + getPosition() + " AVG: " + this.avg + " " + this.obp + " " + this.ab + " " + this.slg + " " + this.sb;
	}
}
