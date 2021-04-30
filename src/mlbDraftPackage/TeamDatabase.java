package mlbDraftPackage;

public class TeamDatabase{
	private PlayerData[] team;
	private int maxSize;
	private int nElems;
	public TeamDatabase() {
		maxSize=100;
		nElems = 0;
		team = new PlayerData[maxSize];
	}
	
	public TeamDatabase(int sz) {
		maxSize = sz;
		nElems=0;
		team = new PlayerData[maxSize];
	}
	
	public void addToDatabase(PlayerData player) {
		if(nElems<14) {
		team[nElems] = player;
		nElems++;
		}else {
			System.out.println("Your team is at maximum capacity.");
		}
	}
	
	public String printLine(int location) {
		//prints only the player at the specified location
		return team[location].toString();
	}
	
	//Get player data
	public String getPlayerName(int location) {
		return team[location].getPlayerName();
	}
	
	public String getPosition(int location) {
		return team[location].getPosition();
	}

	public PlayerData getPlayerData(int location) {
		return team[location];
	}

	
	public int getLength() {
		return nElems;
	}
	
	public int getNelems() {
		return nElems;
	}
	
	public PlayerData[] getTeam(){
		return team;
	}
	
}
