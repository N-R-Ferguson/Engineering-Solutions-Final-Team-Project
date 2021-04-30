package mlbDraftPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class DraftCommands {
	private String evalExpression;
	private String pevalExpression;
	private LeagueMembers members[];
	private ArrayList<PlayerData> playerDraftDatabase;
	SaveRestore sr;

	// DraftCommands can either be used to call commands in a
	// different class or it can have the code for the commands
	// written hereEither way this the class that is called from main.

	// Each method is set to void since there should be nothing that gets returned
	// however this can be changed if need be.
	public DraftCommands() { // Not a command. Just a constructor.
		members = new LeagueMembers[4];
		playerDraftDatabase = new ArrayList<PlayerData>();
		evalExpression = "";
		pevalExpression = "";
		sr = new SaveRestore();
		members[0] = new LeagueMembers("A", 13);
		members[1] = new LeagueMembers("B", 13);
		members[2] = new LeagueMembers("C", 13);
		members[3] = new LeagueMembers("D", 13);
	}

	public void pullMLBData() { // No parameters. Just pulls data from mlb.com
		// not a command that can be called from main/command line
		// automatically pulls data at start.
		String playerData = "";
		String splitPlayerData[];
		String name;
		String pos;
		double avg;
		double obp;
		double ab;
		double slg;
		double sb;
		int counter = 0;
		double g;
		double gs;
		double era;
		double ip;
		double bb;

		try {
			Scanner sc = new Scanner(new File("UpdatedMLBstatsNonPitchersFSN.csv"));
			sc.useDelimiter(",");
			while (sc.hasNext()) {

				playerData = playerData + sc.next() + ",";
				// System.out.println(playerData);
				counter++;
				if (counter >= 7) {
					splitPlayerData = playerData.trim().split(",");

					name = splitPlayerData[0];
					// System.out.println(name);
					pos = splitPlayerData[1];
					// System.out.println(pos);
					avg = Double.parseDouble(splitPlayerData[2]);
					// System.out.println(avg);
					obp = Double.parseDouble(splitPlayerData[3]);
					// System.out.println(obp);
					ab = Double.parseDouble(splitPlayerData[4]);
					// System.out.println(ab);
					slg = Double.parseDouble(splitPlayerData[5]);
					// System.out.println(slg);
					sb = Double.parseDouble(splitPlayerData[6]);
					// System.out.println(sb);
					playerDraftDatabase.add(new NonPitchers(name, pos, avg, obp, ab, slg, sb));
					counter = 0;
					playerData = "";
				}
			}

		}

		catch (InputMismatchException e) {
			System.out.println(e.getMessage()); // try to find out specific reason.
		}

		catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		playerData = "";
		counter = 0;

		try {
			Scanner in = new Scanner(new File("MLBStatsPitcher2019FSN - Sheet1 (1).csv"));
			in.useDelimiter(",");
			while (in.hasNext()) {

				playerData = playerData + in.next() + ",";
				// System.out.println(playerData);
				counter++;
				if (counter >= 7) {
					splitPlayerData = playerData.trim().split(",");

					name = splitPlayerData[0];
					// System.out.println(name);
					pos = splitPlayerData[1];
					// System.out.println(pos);
					g = Double.parseDouble(splitPlayerData[2]);
					// System.out.println(avg);
					gs = Double.parseDouble(splitPlayerData[3]);
					// System.out.println(obp);
					era = Double.parseDouble(splitPlayerData[4]);
					// System.out.println(ab);
					ip = Double.parseDouble(splitPlayerData[5]);
					// System.out.println(slg);
					bb = Double.parseDouble(splitPlayerData[6]);
					// System.out.println(sb);
					playerDraftDatabase.add(new Pitchers(name, pos, g, gs, era, ip, bb));
					counter = 0;
					playerData = "";
				}
			}

		}

		catch (InputMismatchException e) {
			System.out.println(e.getMessage()); // try to find out specific reason.
		}

		catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	// ************COMMANDS THAT CAN BE CALLED START HERE************
	public void odraft(String playerName, String leagueMemberName) {

		Scanner in = new Scanner(System.in);
		int counter = 0;
		int counters = 0;
		int count = 0;

		while (!(leagueMemberName.equalsIgnoreCase("A") || leagueMemberName.equalsIgnoreCase("B")
				|| leagueMemberName.equalsIgnoreCase("C") || leagueMemberName.equalsIgnoreCase("D"))) {
			System.out.println("You must choose members A, B, C, D, or Q if you'd like to return to menu");
			leagueMemberName = in.nextLine();
			if (leagueMemberName.equals("Q")) {
				return;
			}

		}

		while (counter != 1) {
			if (numberOfPlayersWithThatName(playerName) > 1) {
				counter = 2;

				System.out.println(
						"That name is not specific enough to find a unique player. Please enter a new name or Q to return to menu");
				playerName = in.nextLine();
				if (playerName.equals("Q")) {
					return;
				}

			} else {
				for (PlayerData element : playerDraftDatabase) {

					// Checks if this player was drafted anywhere or exists, otherwise proceeds
					if (element.getPlayerName().contains(playerName)) {

						// Match league member
						for (LeagueMembers member : members) {
							if (member.getName().equals(leagueMemberName)) {
								// TeamDatabase.team a = member.getTeamDatabase();
								// check size of team
								if (member.getTeamDatabase().getLength() >= 13) {
									System.out.println("Team is full, no player drafted, returning to main menu");
									return;
								}
								// check duplicate pos on roster of team
								PlayerData[] a = member.getTeamDatabase().getTeam();
								for (PlayerData player : a) {
									if ((player == null)) {
										continue;
									} else if (player.getPosition().equals(element.getPosition())) {
										if (player.getPosition().equals("P")) {
											for (PlayerData players : a) {
												if (players == null) {
													continue;
												} else if (players.getPosition().equals("P")) {
													count++;
												}
											}
											if (count < 5) {
												break;
											} else
												System.out.println("This League member team already has maximum 5 "
														+ element.getPosition()
														+ "'s on there team. Not drafted and returning to main menu");
										}

										else
											System.out.println(
													"This League member team already has a " + element.getPosition()
															+ " on there team. Not drafted and returning to main menu");
										return;
									}

								}
								if (!element.getPosition().equalsIgnoreCase("DH")) {
									member.addPlayer(element);
									playerDraftDatabase.remove(element);
									System.out.println(playerName + " was added to member " + leagueMemberName);
									counter = 1;
									return;
								}
							}
						}
					}
				}

				if (counter != 1) {
					System.out.println(
							"That name for a player was not found or has been drafted, please enter a new name or Q to return");
					playerName = in.nextLine();
					if (playerName.equals("Q")) {
						return;
					}
				}
			}
		}
	}

	public void idraft(String playerName) {
		Scanner in = new Scanner(System.in);
		int counter = 0;
		int counters = 0;
		int count = 0;
		String leagueMemberName = "A";

		while (!(leagueMemberName.equalsIgnoreCase("A") || leagueMemberName.equalsIgnoreCase("B")
				|| leagueMemberName.equalsIgnoreCase("C") || leagueMemberName.equalsIgnoreCase("D"))) {
			System.out.println("You must choose members A, B, C, D, or Q if you'd like to return to menu");
			leagueMemberName = in.nextLine();
			if (leagueMemberName.equals("Q")) {
				return;
			}

		}

		while (counter != 1) {
			if (numberOfPlayersWithThatName(playerName) > 1) {
				counter = 2;

				System.out.println(
						"That name is not specific enough to find a unique player. Please enter a new name or Q to return to menu");
				playerName = in.nextLine();
				if (playerName.equals("Q")) {
					return;
				}

			} else {
				for (PlayerData element : playerDraftDatabase) {

					// Checks if this player was drafted anywhere or exists, otherwise proceeds
					if (element.getPlayerName().contains(playerName)) {

						// Match league member
						for (LeagueMembers member : members) {
							if (member.getName().equals(leagueMemberName)) {
								// TeamDatabase.team a = member.getTeamDatabase();
								// check size of team
								if (member.getTeamDatabase().getNelems() >= 13) {
									System.out.println("Team is full, no player drafted, returning to main menu");
									return;
								}
								// check duplicate pos on roster of team
								PlayerData[] a = member.getTeamDatabase().getTeam();
								for (PlayerData player : a) {
									if ((player == null)) {
										continue;
									} else if (player.getPosition().equals(element.getPosition())) {
										if (player.getPosition().equals("P")) {
											for (PlayerData players : a) {
												if (players == null) {
													continue;
												} else if (players.getPosition().equals("P")) {
													count++;
												}
											}
											if (count < 5) {
												break;
											} else
												System.out.println("This League member team already has maximum 5 "
														+ element.getPosition()
														+ "'s on there team. Not drafted and returning to main menu");
										}

										else
											System.out.println(
													"This League member team already has a " + element.getPosition()
															+ " on there team. Not drafted and returning to main menu");
										return;
									}

								}
								if (!element.getPosition().equalsIgnoreCase("DH")) {
									member.addPlayer(element);
									playerDraftDatabase.remove(element);
									System.out.println(playerName + " was added to member " + leagueMemberName);
									counter = 1;
									return;
								}
							}
						}
					}
				}

				if (counter != 1) {
					System.out.println(
							"That name for a player was not found or has been drafted, please enter a new name or Q to return");
					playerName = in.nextLine();
					if (playerName.equals("Q")) {
						return;
					}
				}
			}
		}
	}

	public int numberOfPlayersWithThatName(String name) {
		int numName = 0;
		for (PlayerData element : playerDraftDatabase) {
			if (element.getPlayerName().contains(name))
				numName++;
		}
		return numName;
	}

	public void overall(String position) throws ScriptException {
		// AVG, OBP, AB, SLG, SB
		ArrayList<PlayerData> playersOfChosenPosition = new ArrayList<PlayerData>(getPlayersOfChosenPosition(position));
		int i, j, k;
		PlayerData temp = null;
		if (evalExpression != "") {
			for (i = 0; i < playersOfChosenPosition.size(); i++) {
				temp = playersOfChosenPosition.get(i);
				k = i - 1;
				for (j = k; j >= 0; j--) {
					// Compare the two values and determine if they need to be swapped.
					if ((double) calculate(getOverallExpression(temp))
							- (double) calculate(getOverallExpression(playersOfChosenPosition.get(j))) < 0) {
						break;
					}
					playersOfChosenPosition.set(j + 1, playersOfChosenPosition.get(j)); // insert the lower value into
																						// its new spot

				}
				playersOfChosenPosition.set(j + 1, temp); // insert the higher value into its new spot
			}

		}

		if (!position.equalsIgnoreCase("DH")) {
			if (playersOfChosenPosition != null && members[0].getHasPosition(position) == false) {
				//
				for (PlayerData element : playersOfChosenPosition) {
					System.out.println(element.getPlayerName() + " " + element.getPosition() + " AVG: "
							+ ((NonPitchers) element).getAVG() + " OBP: " + ((NonPitchers) element).getOBP() + " AB: "
							+ ((NonPitchers) element).getAB() + " SLG: " + ((NonPitchers) element).getSLG() + " SB: "
							+ ((NonPitchers) element).getSB());
				}
			} else {
				if (members[0].getHasPosition(position) == true) {
					System.out.println(
							"You already have the maximum number of players a league member can have for that position.");
				}
			}
		} else {
			System.out.println("DH is not a position that is availabl in the draft");
		}
	}

	public void poverall() throws ScriptException { // Since its for pitchers only then
		// they only have to type in POVERALL as the command
		ArrayList<PlayerData> pitchers = new ArrayList<PlayerData>(getPlayersOfChosenPosition("P"));
		int i, j, k;
		PlayerData temp = null;
		if (pevalExpression != "") {
			for (i = 0; i < pitchers.size(); i++) {
				temp = pitchers.get(i); // set the current value to temp
				k = i - 1;
				for (j = k; j >= 0; j--) {
					// Compare the two values and determine if they need to be swapped.
					if ((double) calculate(getPoverallExpression(temp))
							- (double) calculate(getPoverallExpression(pitchers.get(j))) < 0) {
						break;
					}
					pitchers.set(j + 1, pitchers.get(j)); // insert the lower value into its new spot

				}
				pitchers.set(j + 1, temp); // insert the higher value into its new spot
			}

		}
		if (pitchers != null && members[0].getHasPosition("P") == false) {
			for (PlayerData element : pitchers) {
				System.out.println(
						element.getPlayerName() + " " + element.getPosition() + " G: " + ((Pitchers) element).getG()
								+ " GS: " + ((Pitchers) element).getGS() + " ERA: " + ((Pitchers) element).getERA()
								+ " IP: " + ((Pitchers) element).getIP() + " BB: " + ((Pitchers) element).getBB());
			}
		} else {
			if (members[0].getHasPosition("P") == true) {
				System.out.println("You already have the maximum number of pitchers a league member can have.");
			}
		}

	}

	public ArrayList<PlayerData> getPlayersOfChosenPosition(String position) {
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		if (position != "") {
			for (PlayerData element : playerDraftDatabase) {
				if (element.getPosition().equalsIgnoreCase(position)) {
					players.add(element);
				}
			}
		}
		return players;
	}

	public String getOverallExpression(PlayerData player) {
		String overallExpression = "";
		if (evalExpression != null) {
			String[] tokens = evalExpression.split(" ");
			int i = 0;
			// G, GS, ERA, IP, BB

			while (i < tokens.length) {
				if (tokens[i].equals("avg")) {
					tokens[i] = String.valueOf(((NonPitchers) player).getAVG());
				} else if (tokens[i].equals("obp")) {
					tokens[i] = String.valueOf(((NonPitchers) player).getOBP());
				} else if (tokens[i].equals("ab")) {
					tokens[i] = String.valueOf(((NonPitchers) player).getAB());
				} else if (tokens[i].equals("slg")) {
					tokens[i] = String.valueOf(((NonPitchers) player).getSLG());
				} else if (tokens[i].equals("sb")) {
					tokens[i] = String.valueOf(((NonPitchers) player).getSB());
				}
				overallExpression = overallExpression + " " + tokens[i];
				i++;
			}

		}
		return overallExpression;
	}

	public String getPoverallExpression(PlayerData player) {
		String poverallExpression = "";
		if (pevalExpression != null) {
			String[] tokens = pevalExpression.split(" ");
			int i = 0;
			// G, GS, ERA, IP, BB
			while (i < tokens.length) {
				if (tokens[i].equals("g")) {
					tokens[i] = String.valueOf(((Pitchers) player).getG());
				} else if (tokens[i].equals("gs")) {
					tokens[i] = String.valueOf(((Pitchers) player).getGS());
				} else if (tokens[i].equals("era")) {
					tokens[i] = String.valueOf(((Pitchers) player).getERA());
				} else if (tokens[i].equals("ip")) {
					tokens[i] = String.valueOf(((Pitchers) player).getIP());
				} else if (tokens[i].equals("bb")) {
					tokens[i] = String.valueOf(((Pitchers) player).getBB());
				}
				poverallExpression = poverallExpression + " " + tokens[i];
				i++;
			}
		}
		return poverallExpression;
	}

	public void team(String player) { // Sara's method
		// Position then Player Name
		// Sorted method
		// C, 1B, 2B, 3B, SS, LF, CF, RF, P1, P2, P3, P4, P5

		String[] sortedP = new String[13];
		String[] sortedN = new String[13];
		int p;
		boolean incorrectFormat = false;
		player = player.toUpperCase();

		if (player.contentEquals("A")) {
			p = 0;
		} else if (player.contentEquals("B")) {
			p = 1;
		} else if (player.contentEquals("C")) {
			p = 2;
		} else if (player.contentEquals("D")) {
			p = 3;
		} else {
			incorrectFormat = true;
			p = -1;
		}

		try {
			if (incorrectFormat != true) {
				// Sorting it
				for (int i = 0; i < members[p].getTeamDatabase().getLength(); i++) {
					if (members[p].getTeamDatabase().getPosition(i).equalsIgnoreCase("C")) {
						sortedP[1] = members[p].getTeamDatabase().getPosition(i);
						sortedN[1] = members[p].getTeamDatabase().getPlayerName(i);
					}
					if (members[p].getTeamDatabase().getPosition(i).equalsIgnoreCase("1B")) {
						sortedP[1] = members[p].getTeamDatabase().getPosition(i);
						sortedN[1] = members[p].getTeamDatabase().getPlayerName(i);
					}
					if (members[p].getTeamDatabase().getPosition(i).equalsIgnoreCase("2B")) {
						sortedP[2] = members[p].getTeamDatabase().getPosition(i);
						sortedN[2] = members[p].getTeamDatabase().getPlayerName(i);
					}
					if (members[p].getTeamDatabase().getPosition(i).equalsIgnoreCase("3B")) {
						sortedP[3] = members[p].getTeamDatabase().getPosition(i);
						sortedN[3] = members[p].getTeamDatabase().getPlayerName(i);
					}
					if (members[p].getTeamDatabase().getPosition(i).equalsIgnoreCase("SS")) {
						sortedP[4] = members[p].getTeamDatabase().getPosition(i);
						sortedN[4] = members[p].getTeamDatabase().getPlayerName(i);
					}
					if (members[p].getTeamDatabase().getPosition(i).equalsIgnoreCase("LF")) {
						sortedP[5] = members[p].getTeamDatabase().getPosition(i);
						sortedN[5] = members[p].getTeamDatabase().getPlayerName(i);
					}
					if (members[p].getTeamDatabase().getPosition(i).equalsIgnoreCase("CF")) {
						sortedP[6] = members[p].getTeamDatabase().getPosition(i);
						sortedN[6] = members[p].getTeamDatabase().getPlayerName(i);
					}
					if (members[p].getTeamDatabase().getPosition(i).equalsIgnoreCase("RF")) {
						sortedP[7] = members[p].getTeamDatabase().getPosition(i);
						sortedN[7] = members[p].getTeamDatabase().getPlayerName(i);
					}

					if(members[p].getTeamDatabase().getPosition(i).equalsIgnoreCase("P")) {
						if(sortedP[8] == null) {
							sortedP[8] = members[p].getTeamDatabase().getPosition(i);
							sortedN[8] = members[p].getTeamDatabase().getPlayerName(i);
						}
						else if(sortedP[9] == null) {
							sortedP[9] = members[p].getTeamDatabase().getPosition(i);
							sortedN[9] = members[p].getTeamDatabase().getPlayerName(i);
						}
						else if(sortedP[10] == null) {
							sortedP[10] = members[p].getTeamDatabase().getPosition(i);
							sortedN[10] = members[p].getTeamDatabase().getPlayerName(i);
						}
						else if(sortedP[11] == null) {
							sortedP[11] = members[p].getTeamDatabase().getPosition(i);
							sortedN[11] = members[p].getTeamDatabase().getPlayerName(i);
						}
						else if(sortedP[12] == null) {
							sortedP[12] = members[p].getTeamDatabase().getPosition(i);
							sortedN[12] = members[p].getTeamDatabase().getPlayerName(i);
						}

					}

				}

				for (int i = 0; i < 13; i++) {
					if (sortedP[i] == null) {
						sortedP[i] = "";
						sortedN[i] = "";
					}
				}

				// Printing it
				System.out.println("Printing roster for: " + player);
				for (int i = 0; i < sortedP.length; i++) {
					if (sortedP[i] != "") {
						System.out.println(sortedP[i] + " " + sortedN[i]);
					}
				}

			} else if (incorrectFormat == true) {
				System.out.println("Incorrect player.");
			}
		}

		catch (NullPointerException e) { // In case no players have been drafted
			System.out.println("There are currently no players in this draft.");
		}

	}

	public void stars(String player) { // Sara's method
		// Position then Player Name
		// By when they drafted them
		int p;
		boolean incorrectFormat = false;
		player = player.toUpperCase();

		if (player.contentEquals("A")) {
			p = 0;
		} else if (player.contentEquals("B")) {
			p = 1;
		} else if (player.contentEquals("C")) {
			p = 2;
		} else if (player.contentEquals("D")) {
			p = 3;
		} else {
			incorrectFormat = true;
			p = -1;
		}

		try {
			if (incorrectFormat != true) {
				if (incorrectFormat != true) {
					System.out.println("Printing roster for: " + player);

					for (int i = 0; i < members[p].getTeamDatabase().getLength(); i++) {
						if (members[p].getTeamDatabase().getPosition(i) != "") {
							System.out.println(members[p].getTeamDatabase().getPosition(i) + " "
									+ members[p].getTeamDatabase().getPlayerName(i));
						}

					}
				} else if (incorrectFormat == true) {
					System.out.println("Incorrect player.");
				}
			}
		} catch (NullPointerException e) { // In case no players have been drafted
			System.out.println("There are currently no players in this draft.");
		}

	}

	public void save(String fileName) {
		sr.save(members, playerDraftDatabase, fileName);
		System.out.println("The current state of the draft has been saved in " + fileName);
	}

	public void quit() {
		System.exit(0);
	}

	public void restore(String fileName) {
		sr.restore(fileName);
		playerDraftDatabase = new ArrayList<PlayerData>(sr.getDraftDatabase()); // Need to retrieve the array list from
																				// SaveRestore sr
		members = sr.getMembers(); // need to retrieve the members array from SaveRestore sr.
		System.out.println("The draft has been restored from  " + fileName);
		printDraftDatabase();
	}

	public void evalfun(String expression) { // Sara's method
		// AVG, OBP, AB, SLG, SB
		expression = expression.toLowerCase();
		String[] tokens = expression.split(" ");
		int i = 0;

		// Tests to see if the expression is in the correct format
		// Currently only takes the format as 1.5 * avg / AB
		// Will return nothing if format is like this 1.5*ab*AB
		while (i < tokens.length) {
			if (tokens[i].equals("avg") || tokens[i].equals("obp") || tokens[i].equals("ab") || tokens[i].equals("slg")
					|| tokens[i].equals("sb")) {
				evalExpression = expression;
				i++;
			} else if (tokens[i].contains("1") || tokens[i].contains("2") || tokens[i].contains("3")
					|| tokens[i].contains("4") || tokens[i].contains("5") || tokens[i].contains("6")
					|| tokens[i].contains("7") || tokens[i].contains("8") || tokens[i].contains("9")
					|| tokens[i].contains("0") || tokens[i].contains(".")) {
				evalExpression = expression;
				i++;
			} else if (tokens[i].equals("*") || tokens[i].equals("+") || tokens[i].equals("/")
					|| tokens[i].equals("-")) {
				evalExpression = expression;
				i++;
			} else {
				evalExpression = "";
				i = tokens.length + 1;
				System.out.println("Incorrect format. An example of the correct format is: 1.05 + AVG * SB");
			}

		}
		System.out.println(evalExpression);
	}

	public void pevalfun(String expression) { // Sara's method
		// G, GS, ERA, IP, BB
		expression = expression.toLowerCase();
		String[] tokens = expression.split(" ");
		int i = 0;

		// Tests to see if the expression is in the correct format
		// Currently only takes the format as 1.5 * G / GS
		// Will return nothing if format is like this 1.5*g*gs
		while (i < tokens.length) {
			if (tokens[i].equals("g") || tokens[i].equals("gs") || tokens[i].equals("era") || tokens[i].equals("ip")
					|| tokens[i].equals("bb")) {
				pevalExpression = expression;
				i++;
			} else if (tokens[i].contains("1") || tokens[i].contains("2") || tokens[i].contains("3")
					|| tokens[i].contains("4") || tokens[i].contains("5") || tokens[i].contains("6")
					|| tokens[i].contains("8") || tokens[i].contains("8") || tokens[i].contains("9")
					|| tokens[i].contains("0") || tokens[i].contains(".")) {
				pevalExpression = expression;
				i++;
			} else if (tokens[i].equals("*") || tokens[i].equals("+") || tokens[i].equals("/")
					|| tokens[i].equals("-")) {
				pevalExpression = expression;
				i++;
			} else {
				pevalExpression = "";
				i = tokens.length + 1;
				System.out.println("Incorrect format. An example of the correct format is: 1.05 + G * IP");
			}

		}
		System.out.println(pevalExpression);
	}

	public String getExpressions(String choice) {
		if (choice.equals("e")) {
			return evalExpression;
		} else if (choice.equals("p")) {
			return pevalExpression;
		}
		return "";
	}

	public Object calculate(String equation) throws ScriptException { // Calcuates equation, used for overall and
																		// poverall
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");

		Object valuation = null;
		if (evalExpression != "" || pevalExpression != "")
			valuation = (double) engine.eval(equation);
		return valuation;
	}

	public void help() { // Sara's method
		// No parameters since this is just to print out commands
		System.out.println("ODRAFT 'playerName' leagueMember - Draft a league member for a certain player.");
		System.out.println("IDRAFT 'playerName' - Draft a league member for player A.");
		System.out.println("OVERALL [position] - Prints the ranking of all league members for non-pitching positions.");
		System.out.println("POVERALL - Prints the ranking of pitching positions.");
		System.out.println("TEAM leagueMember - Prints roster of the team in a certain order.");
		System.out.println("STARS leagueMember - Prints roster of the team by when they were drafted.");
		System.out.println("EVAL equation - Sets how all but the pitchers will be sorted.");
		System.out.println("PEVAL equation - Sets how the pitchers will be sorted.");
		System.out.println("SAVE - Saves current state of the draft to a given file.");
		System.out.println("RESTORE - Restores current state of the draft in a given file.");
		System.out.println("QUIT - Quits the program.");
	}

	//
	public void printDraftDatabase() {
		for (PlayerData element : playerDraftDatabase) {
			System.out.println(element.toString());
		}
	}

	// For JUnit Testing only
	public ArrayList<PlayerData> getDraftDatabase() {
		return playerDraftDatabase;
	}

	public LeagueMembers[] getLeagueMembers() {
		return members;
	}
}
