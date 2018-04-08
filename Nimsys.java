import java.util.ArrayList;
import java.lang.Math;
import java.util.Collections;
import java.util.Scanner;

public class Nimsys {
	
	private static ArrayList<NimPlayer> playerList = new ArrayList<NimPlayer>();
	private static Scanner playersInput;

	public static void main(String[] args) {
		playersInput = new Scanner(System.in); 
		System.out.print("Welcome to Nim\n\n$");
		NimGame gameRound = null;
		while(true) {
				if(gameRound != null) {
					NimGame.playOneRound(playersInput);
					gameRound = null;
				}
				else {
					String newInput = playersInput.nextLine();
					if(newInput.startsWith("addplayer")) {
						addPlayer(newInput);
						System.out.printf("\n$");
					}
					else if(newInput.startsWith("removeplayer") || newInput.equals("y") || newInput.equals("n")) {
						if(removePlayer(newInput)){
							System.out.printf("\n$");
						}
					}
					else if(newInput.startsWith("editplayer")) {
						editPlayer(newInput);
						System.out.printf("\n$");
					}
					else if(newInput.startsWith("resetstats")) {
						resetStats(newInput);
						System.out.printf("\n$");
					}
					else if(newInput.startsWith("displayplayer")) {
						displayPlayer(newInput);
						System.out.printf("\n$");
					}
					else if(newInput.startsWith("rankings")) {
						rankings(newInput);
						System.out.printf("\n$");
					}
					else if(newInput.startsWith("startgame")) {
						gameRound = startgame(newInput);
						continue;
					}
					else if(newInput.startsWith("exit")) {
						System.out.printf("\n");
						System.exit(0);
					}
				}
				
		}
	}

	private static boolean userExist(String userName) {
		if(playerList.isEmpty())
			return false;
		for(int i = 0; i< playerList.size(); i++) {
			if(playerList.get(i).userName.equals(userName)) {
				return true;
			}
		}
		return false;
	}
	
	private static ArrayList<NimPlayer> playerRank(ArrayList<NimPlayer> playerList) {
		for(int i = 0; i < playerList.size(); i++) {
			int maxWinRate = playerList.get(i).winningRatio();
			int maxIndex = i;
			for(int j = i + 1; j < playerList.size(); j++) {
				if(playerList.get(j).winningRatio() > maxWinRate) {
					maxWinRate = playerList.get(j).winningRatio();
					maxIndex = j;
				}
			}
			Collections.swap(playerList, i, maxIndex);
		}
		return playerList;
	}
	
	private static void rankings(String inputStringPart) {
		String rankingsString = inputStringPart.replaceFirst("^rankings", "");
		ArrayList<NimPlayer> descPlayerList = playerRank(playerList);
		if(rankingsString == " asc") {
			for(int i = descPlayerList.size() -1 ;i >= Math.max(0, descPlayerList.size() - 10); i--) {
				NimPlayer player = descPlayerList.get(i);
				String winNum = String.format("%02d", player.numberOfGamesPlayed);
				String winRatio = player.winningRatio() + "%";
				String displayWinRatio = String.format("%-5s", winRatio);
				System.out.printf("%s| %s games | %s %s\n" , displayWinRatio, winNum, player.givenName, player.familyName);
			}
		}
		else{
			for(int i = 0; i < Math.min(descPlayerList.size(),10); i++ ) {
				NimPlayer player = descPlayerList.get(i);
				String winNum = String.format("%02d", player.numberOfGamesPlayed);
				String winRatio = player.winningRatio() + "%";
				String displayWinRatio = String.format("%-5s", winRatio);
				System.out.printf("%s| %s games | %s %s\n" , displayWinRatio, winNum, player.givenName, player.familyName);
			}
		}
	}


	private static void displayPlayer(String inputStringPart) {
		String diaplayPlayerString = inputStringPart.replaceFirst("^displayplayer", "");
		if(!diaplayPlayerString.isEmpty()) {
			String userName = diaplayPlayerString.replaceFirst("^ ", "");
			if(userExist(userName)){
				for(int i= 0; i < playerList.size(); i++) {
					if(playerList.get(i).userName.equals(userName)) {
						NimPlayer displayPlayer = playerList.get(i);
						System.out.printf("%s,%s,%s,%d games,%d wins\n", displayPlayer.userName, displayPlayer.givenName, displayPlayer.familyName, displayPlayer.numberOfGamesPlayed, displayPlayer.numberOfGamesWon);
						break;
					}
				}
			}
			else {
				System.out.println("The player does not exist.");
			}
		}
		else {
			displayAlphabeticalOrder();
			for(int i = 0; i < playerList.size(); i++) {
				NimPlayer displayPlayer = playerList.get(i);
				System.out.printf("%s,%s,%s,%d games,%d wins\n", displayPlayer.userName, displayPlayer.givenName, displayPlayer.familyName, displayPlayer.numberOfGamesPlayed, displayPlayer.numberOfGamesWon);
			}
		}
	}
	
	
	private static void displayAlphabeticalOrder(){
		for(int i = 0; i < playerList.size() - 1; i++) {
			int minIndex = i;
			String minUserName = playerList.get(i).userName;
			for(int j = i + 1; j < playerList.size(); j++) {
				if(playerList.get(j).userName.compareTo(minUserName) < 0) {
					minIndex = j;
					minUserName = playerList.get(j).userName;
				}
			}
			NimPlayer tempPlayer = playerList.get(i);
			playerList.set(i, playerList.get(minIndex));
			playerList.set(minIndex, tempPlayer);
		}
	}

	private static void resetStats(String inputStringPart) {
		String resetStatsString = inputStringPart.replaceFirst("resetstats", "");
		if(resetStatsString.startsWith(" ")) {
			String userName = resetStatsString.replaceFirst(" ", "");
			if(userExist(userName)) {
				for(int i = 0; i < playerList.size(); i++) {
					if(playerList.get(i).userName == userName) {
						playerList.get(i).resetGames();
						System.out.println("");
						break;
					}
				}
			}
			else {
				System.out.println("The player does not exist.\n");
			}
		}
		else {
			System.out.println("Are you sure you want to reset all player statistics? (y/n)");
			if(resetStatsString.startsWith("y")) {
				for(int i = 0; i < playerList.size(); i++) {
					{
						playerList.get(i).resetGames();
					}
				}
				System.out.println("");
			}
		}
	}
	
	private static void addPlayer(String inputStringPart){
		boolean exist = false;
		String addPlayerString = inputStringPart.replaceFirst("^addplayer ", "");
		int index = addPlayerString.indexOf(",");
		String userName = addPlayerString.substring(0, index);
		exist = userExist(userName);
		if(exist) {
			System.out.println("The player already exists.");
		}
		else{
			int index1 = addPlayerString.indexOf(",", index + 1);
			String familyName = addPlayerString.substring(index + 1, index1);
			String givenName = addPlayerString.substring(index1 + 1);
			NimPlayer player = new NimPlayer(userName, givenName, familyName);
			playerList.add(player);
		}
	}
	
	private static boolean removePlayer(String inputStringPart) {
		if(inputStringPart.equals("y")) {
			playerList.clear();
			return true;
		}
		if(inputStringPart.equals("n"))
			return true;
		String removePlayerString = inputStringPart.replaceFirst("^removeplayer", "");
		if(removePlayerString.isEmpty()) {
			 System.out.println("Are you sure you want to remove all players? (y/n)");
			 return false;
			}
		else {
			boolean exist = false;
			String userName = removePlayerString.replaceFirst(" ", "");
			for(int i = 0; i < playerList.size(); i++) {
				if(playerList.get(i).userName.equals(userName)) {
					playerList.remove(i);
					exist = true;
					break;
				}
			}
			if(!exist) {
				System.out.println("The player does not exist.");
			}
			return true;
		}
	}
	
	private static void editPlayer(String inputStringPart){
		String editPlayerString = inputStringPart.replaceFirst("^editplayer ", "");	
		int index = editPlayerString.indexOf(",");
		String userName = editPlayerString.substring(0, index);
		if(userExist(userName)){
			for(int i = 0; i < playerList.size(); i++) {
				if(userName.equals(playerList.get(i).userName)) {
					int index1 = editPlayerString.indexOf(",", index + 1);
					String familyName = editPlayerString.substring(index + 1, index1);
					String givenName = editPlayerString.substring(index1 + 1);
					playerList.get(i).editNames(givenName, familyName);
					break;
				}
			}
		}
		else {
			System.out.println("The player does not exist.");
		}
	}

	private static NimGame startgame(String inputStringPart) {
		String startGameString = inputStringPart.replaceFirst("^startgame ", "");
		String[] initialGame = startGameString.split(",");
		String userName1 = initialGame[2];
		String userName2 = initialGame[3];
		int initialStone = Integer.parseInt(initialGame[0]);
		System.out.printf("\nInitial stone count: %d\n", initialStone);
		int upperBound = Integer.parseInt(initialGame[1]);
		System.out.printf("Maximum stone removal: %d\n", upperBound);
		NimPlayer player1 = null, player2 = null;
		NimGame gameRound = null;
		if(userExist(userName1) && userExist(userName2)) {
			for(int i = 0; i < playerList.size(); i++) {
				if(playerList.get(i).userName.equals(userName1)) {
					player1 = playerList.get(i);
					break;
				}
			}
			for(int i = 0; i < playerList.size(); i++) {
				if(playerList.get(i).userName.equals(userName2)) {
					player2 = playerList.get(i);
					break;
				}
			}
			System.out.printf("Player 1: " + player1.givenName + " " + player1.familyName + "\n");
			System.out.printf("Player 2: " + player2.givenName + " " + player2.familyName + "\n\n");
			gameRound = new NimGame(initialStone, upperBound, player1, player2);
			return gameRound;
		}
		else {
			System.out.println("One of the players does not exist.");
			return null;
		}
	}
}
