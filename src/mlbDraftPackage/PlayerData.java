package mlbDraftPackage;

public class PlayerData {
	private String playerName;
	private String position;
	public PlayerData() {
		this.playerName="";
		this.position = "";
	}
	
	public PlayerData(String playerName, String position) {
		this.playerName=playerName;
		this.position = position;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getPosition() {
		return this.position;
	}

//	public double getAverage() {
//		return NonPitchers.getAVG();
//	}

//	public double getOBP() {
//		return NonPitchers.getOBP(0);
//	}
	
}
