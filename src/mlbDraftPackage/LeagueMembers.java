package mlbDraftPackage;

public class LeagueMembers {
	//League Data can store which member the data is for as well as the members drafted team
	//Also saves the order in which the member drafted their team
	private String leagueMember;
	private TeamDatabase teamDatabase;
	private int maxSize;
	private boolean dataSaved;
	private boolean hasC;
	private boolean has1B;
	private boolean has2B;
	private boolean has3B;
	private boolean hasSS;
	private boolean hasLF;
	private boolean hasCF;
	private boolean hasRF;
	private boolean has5P;
	private int numPitchers;
	
	public LeagueMembers() {
		leagueMember = "";
		teamDatabase = new TeamDatabase(100);
		dataSaved = false;
		hasC = false;
		has1B = false;
		has2B = false;
		has3B = false;
		hasSS = false;
		hasLF = false;
		hasCF = false;
		hasRF = false;
		has5P = false;
		numPitchers=0;
	}
	
	public LeagueMembers(String name, int sz) {
		maxSize = sz;
		leagueMember = name;
		teamDatabase = new TeamDatabase(maxSize);
		dataSaved = false;
		hasC = false;
		has1B = false;
		has2B = false;
		has3B = false;
		hasSS = false;
		hasLF = false;
		hasCF = false;
		hasRF = false;
		has5P = false;
		numPitchers=0;
	}
	
	public void addPlayer(PlayerData player) {
		teamDatabase.addToDatabase(player);
	}
	
	//Setters
	public void  setDataSaved(boolean b) {
		dataSaved = b;
	}
	public void setHasC(boolean hasPosition) {
		hasC = hasPosition;
	}
	
	public void setHas1B(boolean hasPosition) {
		has1B = hasPosition;
	}
	
	public void setHas2B(boolean hasPosition) {
		has2B = hasPosition;
	}
	
	public void setHas3B(boolean hasPosition) {
		has3B = hasPosition;
	}
	
	public void setHasSS(boolean hasPosition) {
		hasSS = hasPosition;
	}
	
	public void setHasLF(boolean hasPosition) {
		hasLF = hasPosition;
	}
	
	public void setHasCF(boolean hasPosition) {
		hasCF = hasPosition;
	}
	
	public void setHasRF(boolean hasPosition) {
		hasRF = hasPosition;
	}
	
	public void setHas1B(int numPitchers) {
		if(numPitchers>5)
			has5P = true;
	}
	
	//Getters
	public boolean getHasPosition(String position) {
		boolean hasPosition=false;
		if(position.equalsIgnoreCase("C") && hasC == true)
			hasPosition = true;
		else if(position.equalsIgnoreCase("1B") && has1B == true)
			hasPosition = true;
		else if(position.equalsIgnoreCase("2B") && has2B == true)
			hasPosition = true;
		else if(position.equalsIgnoreCase("3B") && has3B == true)
			hasPosition = true;
		else if(position.equalsIgnoreCase("SS") && hasSS == true)
			hasPosition = true;
		else if(position.equalsIgnoreCase("LF") && hasLF == true)
			hasPosition = true;
		else if(position.equalsIgnoreCase("CF") && hasCF == true)
			hasPosition = true;
		else if(position.equalsIgnoreCase("RF") && hasRF == true)
			hasPosition = true;
		else if(position.equalsIgnoreCase("P") && has5P == true)
			hasPosition = true;
		
		return hasPosition;
	}
	public String getName() {
		return leagueMember;
	}
	public boolean getDataSaved() { //Only used for JUnit testing
		return dataSaved;
	}
	public TeamDatabase getTeamDatabase() {
		return teamDatabase; //returns the database containing the team information
	}
	
}

	
