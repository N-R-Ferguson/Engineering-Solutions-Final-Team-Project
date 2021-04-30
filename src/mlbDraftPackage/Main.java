package mlbDraftPackage;

import java.util.Scanner;

import javax.script.ScriptException;

public class Main {
	public static void main(String[] args) throws ScriptException {
		// Call the methods in DraftCommands from main
		Scanner input = new Scanner(System.in);
		DraftCommands dc = new DraftCommands();
		String command = "";
		String userName = "";
		String leagueName = "";
		String position = "";
		String fileName = "";
		String equation = "";
		String choice = "";
		dc.pullMLBData();
		while (!command.equals("QUIT")) {
			System.out.println(
					"The commands are: 'ODRAFT', 'IDRAFT', 'OVERALL', 'POVERALL', 'TEAM', 'STARS', 'EVALFUN', 'PEVALFUN', 'SAVE', 'RESTORE', 'HELP', and 'QUIT'.\nPlease enter a command:");
			command = input.nextLine();
			switch (command) {
			case "ODRAFT":
				System.out.println("Please enter a league player: ");
				leagueName = input.nextLine(); 
				System.out.println("Please enter a user: ");
				userName = input.nextLine();
				dc.odraft(leagueName, userName);
				break;
			case "IDRAFT":
				System.out.println("Please enter a league player: ");
				leagueName = input.nextLine(); 
				dc.idraft(leagueName);
				break;
			case "OVERALL":
				System.out.println("Please enter a position: ");
				position = input.nextLine();
				dc.overall(position);
				break;
			case "POVERALL":
				dc.poverall();
				break;
			case "TEAM":
				System.out.println("Please enter a user: ");
				userName = input.nextLine();
				dc.team(userName);
				break;
			case "STARS":
				System.out.println("Please enter a user: ");
				userName = input.nextLine();
				dc.stars(userName);
				break;
			case "SAVE":
				System.out.println("Please enter a fileName (.fantasy.txt is not needed): ");
				fileName = input.nextLine();
				dc.save(fileName + ".fantasy.txt");
				break;
			case "EVALFUN":
				System.out.println("Please enter an equation: ");	
				equation = input.nextLine();
				dc.evalfun(equation);
				break;
			case "PEVALFUN":
				System.out.println("Please enter an equation: ");
				equation = input.nextLine();
				dc.pevalfun(equation);
				break;
			case "RESTORE":
				System.out.println("Please enter a fileName (.fantasy.txt is not needed): ");
				fileName = input.nextLine();
				dc.restore(fileName + ".fantasy.txt");
				break;
			case "HELP":
				dc.help();
				break;
			case "QUIT":
				System.out.println("Would you like to save first?");
				choice = input.nextLine();
				choice = choice.toLowerCase();
				
				if(choice == "yes"){
					System.out.println("Please enter a fileName: ");
					fileName = input.next();
					dc.save(fileName);
					dc.quit();
				}
				
				else
				{
					dc.quit();
				}
				break;
			default:
				System.out.println("Not a valid command");
				break;
			}

		}
	}
}
