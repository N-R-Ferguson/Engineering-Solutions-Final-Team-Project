package mlbDraftPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SaveRestore {
	private LeagueMembers members[];
	private ArrayList<PlayerData> draftDatabase;

	public SaveRestore() {
		members = new LeagueMembers[4];
		draftDatabase = new ArrayList<PlayerData>();
	}

	public void save(LeagueMembers[] members, ArrayList<PlayerData> playersLeftToDraft, String fileName) {

		File fileToSaveTo = new File(fileName);
		TeamDatabase teamDB;
		int i, j;

		try {
			PrintWriter writeToSaveFile = new PrintWriter(new FileWriter(fileToSaveTo)); // opens the file use a
																							// PrintWriter object

			for (i = 0; i < members.length; i++) { // loops through the league members
				if (members[i].getTeamDatabase() != null) { // checks to see if the league members have any players
					writeToSaveFile.println(members[i].getName()); // write the league members name to the file
					writeToSaveFile.println(members[i].getTeamDatabase().getLength()); // writes how many players
																						// they
					teamDB = members[i].getTeamDatabase();
					for (j = 0; j < teamDB.getLength(); j++) {
						writeToSaveFile.println(teamDB.printLine(j));

					}
					members[i].setDataSaved(true); // for JUnit testing only
				}
			}

			writeToSaveFile.println(playersLeftToDraft.size()); // writes size of playersLeftToDraft to file
			if (playersLeftToDraft != null) { // checks to see if there is anything in playersLeftToDraft
				for (PlayerData player : playersLeftToDraft) { // loops through playersLeftToDraft
					writeToSaveFile.println(player.toString()); // writes the data of each element in
																// playersLeftToDraft to the file
				}

			}
			writeToSaveFile.close(); // Closes the file
			// Exception handling
		} catch (FileNotFoundException e) {
			System.out.println("The file " + fileToSaveTo + " could not be found.");
		} catch (IOException e) {
			System.out.println("There was an error reading " + fileToSaveTo);
		}
	}

	public void restore(String fileName) {
		File fileToReadDataFrom = new File(fileName);
		String lineReadFromFile = "";
		int playerCounter = 0;
		int lineCounter = 0;
		int memberCounter = 0;

		try {
			BufferedReader readFromFile = new BufferedReader(new FileReader(fileToReadDataFrom)); // Opens the file to
																									// read from

			while ((lineReadFromFile = readFromFile.readLine()) != null) {
				if (lineCounter - 2 >= playerCounter || memberCounter>3) { // checks to see that the number of times its looped through the
														// members team is equal to the number of players on that team
					 // increaments what member is being used
					memberCounter++;
					if(memberCounter<=3) {
						
						lineCounter = 0; // resets the line counter
					}else if(memberCounter == 4) // checks to see if all members teams have been read in
						lineCounter=1;  //Sets lineCOunter to 1 so that it can read in the number of players in draftDatabase
					else // increments lineCounter by one to make up for there being no name for the
						lineCounter++;				// draftDatabase
			
				}
				if (lineCounter == 0 && memberCounter < 4) { // checks to see that it hasnt looped through for the
																// current member and that not all members have been
																// read in
					members[memberCounter] = new LeagueMembers(lineReadFromFile, 13); // creates the LeagueMembers
																						// object for the current membet
					lineCounter++; // increments the lineCounter by 1
				} else if (lineCounter == 1) { // will be the number of players on the team
					playerCounter = Integer.parseInt(lineReadFromFile); // turns the line into an integer
					if (playerCounter == 0) { // checks to see if there are no players on the team
						lineCounter = 0; // resets the lineCounter to 0
						memberCounter++; // increments the current team member
					} else
						lineCounter++; // increments lineCounter
				} else if (lineCounter - 2 < playerCounter && playerCounter != 0 && memberCounter < 4) {
					// checks to see that the number of times its looped through the members team is
					// equal to the number of players on that team, the
					// lineCounter isn't 0 and that there are members to go
					// through
					if (playerCounter != 0) // checks that there are players on the team
						restoreLeagueMembers(lineReadFromFile, memberCounter); // calls the function to add that player
																				// to its respective team and league
																				// member

					lineCounter++; // increments lineCounter

				} else if (memberCounter > 3) { // checks that all players have been read in
					if (playerCounter != 0) // checks that there are players in the saved draftDatabase
						restoreDraftDatabase(lineReadFromFile); // calls the function that adds the player to the
																// draftDatabase
				} else {
					lineCounter++; // increments lineCounter
					playerCounter = Integer.parseInt(lineReadFromFile); // turns the line into an integer
					memberCounter = 4; // sets the current member to 4 which will flag that the draft database is ready
										// to be read in.
				}

			}
			readFromFile.close(); // closes the file

			// Exception handling
		} catch (FileNotFoundException e) {
			System.out.println("The file " + fileToReadDataFrom + " could not be found.");
		} catch (IOException e) {
			System.out.println("There was an error reading " + fileToReadDataFrom);
		}

	}

	public void restoreLeagueMembers(String player, int memberCounter) {
		String[] splitLine;
		splitLine = player.trim().split(","); // splits the entry based on where commas are
		if (splitLine[1] == "P")// checks to see if the players position is that of a pitcher
			members[memberCounter].addPlayer(new Pitchers(splitLine[0], splitLine[1], Double.parseDouble(splitLine[2]),
					Double.parseDouble(splitLine[3]), Double.parseDouble(splitLine[4]),
					Double.parseDouble(splitLine[5]), Double.parseDouble(splitLine[6])));// adds the player to their
																							// team
		else // every other position goes here
			members[memberCounter]
					.addPlayer(new NonPitchers(splitLine[0], splitLine[1], Double.parseDouble(splitLine[2]),
							Double.parseDouble(splitLine[3]), Double.parseDouble(splitLine[4]),
							Double.parseDouble(splitLine[5]), Double.parseDouble(splitLine[6]))); // adds the player to
																									// their team
	}

	public void restoreDraftDatabase(String player) {
		String[] splitLine = player.trim().split(",");// splits the entry based on where commas are
		if (splitLine[1] == "P") // checks to see if the players position is that of a pitcher
			draftDatabase.add(new Pitchers(splitLine[0], splitLine[1], Double.parseDouble(splitLine[2]),
					Double.parseDouble(splitLine[3]), Double.parseDouble(splitLine[4]),
					Double.parseDouble(splitLine[5]), Double.parseDouble(splitLine[6])));// adds the player to their
																							// team
		else // every other position goes here
			draftDatabase.add(new NonPitchers(splitLine[0], splitLine[1], Double.parseDouble(splitLine[2]),
					Double.parseDouble(splitLine[3]), Double.parseDouble(splitLine[4]),
					Double.parseDouble(splitLine[5]), Double.parseDouble(splitLine[6])));// adds the player to their
																							// team

	}

	public LeagueMembers[] getMembers() { // used to return the members array to other classes
		return members;
	}

	public ArrayList<PlayerData> getDraftDatabase() { // used to return the draftDatabase array list to other classes
		return draftDatabase;
	}
}
