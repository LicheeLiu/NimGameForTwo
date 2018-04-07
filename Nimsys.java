
import java.util.ArrayList;
import java.lang.Math;
import java.util.Collections;
import java.util.Scanner;

public class Nimsys {
	
	private static ArrayList<NimPlayer> playerList;
	private static Scanner playersInput;

	public static void main(String[] args) {
		playersInput = new Scanner(System.in); 
		System.out.println("Welcome to Nim");
		System.out.println("");
		
		while(true){
			String newInput = playersInput.next();
			ArrayList<Integer> keyIndex = listenForKeyWords (newInput);
			ArrayList<String> stringToDiffMethod = new ArrayList<String>();
			for(int i = 0; i < keyIndex.size() - 1; i++ ) {
				stringToDiffMethod.add(newInput.substring(keyIndex.get(i), keyIndex.get(i + 1)));
			}
			stringToDiffMethod.add(newInput.substring(keyIndex.get(keyIndex.size() - 1)));
			for(int i = 0; i < stringToDiffMethod.size() ; i ++) {
				String inputStringPart = stringToDiffMethod.get(i);
				if(inputStringPart.startsWith("addplayer")) {
					addPlayer(inputStringPart);
				}
				else if(inputStringPart.startsWith("removeplayer")) {
					removePlayer(inputStringPart);
				}
				else if(inputStringPart.startsWith("editPlayer")) {
					editPlayer(inputStringPart);
				}
				else if(inputStringPart.startsWith("resetstats")) {
					resetStats(inputStringPart);
				}
				else if(inputStringPart.startsWith("displayplayer")) {
					displayPlayer(inputStringPart);
				}
				else if(inputStringPart.startsWith("rankings")) {
					rankings(inputStringPart);
				}
				else if(inputStringPart.startsWith("startgame")) {
					startgame(inputStringPart);
				}
				else if(inputStringPart.startsWith("exit")) {
					System.exit(0);
				}
			}
		}
	}

	private static boolean userExist(String userName) {
		for(int i = 0; i< playerList.size(); i++) {
			if(playerList.get(i).userName == userName) {
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
				System.out.printf("%d/%%5s %s games | %s %s\n", player.winningRatio(), "|", winNum, player.givenName, player.familyName);
			}
			System.out.println("");
		}
		else{
			for(int i = 0; i < Math.min(descPlayerList.size(),10); i++ ) {
				NimPlayer player = descPlayerList.get(i);
				String winNum = String.format("%02d", player.numberOfGamesPlayed);
				System.out.printf("%d/%%5s %s games | %s %s\n", player.winningRatio(), "|", winNum, player.givenName, player.familyName);
			}
			System.out.println("");
		}
	}

	private static ArrayList<Integer> listenForKeyWords(String newInput){
		ArrayList<Integer> keyIndex = new ArrayList<Integer>(); 
		int index = newInput.indexOf("addplayer");
		keyIndex.add(index);
		while(index >= 0) {
			index = newInput.indexOf("addplayer", index + 1);
			keyIndex.add(index);
		}
		index = newInput.indexOf("removeplayer");
		keyIndex.add(index);
		while(index >= 0) {
			index = newInput.indexOf("removeplayer", index + 1);
			keyIndex.add(index);
		}
		index = newInput.indexOf("editplayer");
		keyIndex.add(index);
		while(index >= 0) {
			index = newInput.indexOf("editplayer", index + 1);
			keyIndex.add(index);
		}
		index = newInput.indexOf("resetstats");
		keyIndex.add(index);
		while(index >= 0) {
			index = newInput.indexOf("resetstats", index + 1);
			keyIndex.add(index);
		}
		index = newInput.indexOf("displayplayer");
		keyIndex.add(index);
		while(index >= 0) {
			index = newInput.indexOf("displayplayer", index + 1);
			keyIndex.add(index);
		}
		index = newInput.indexOf("rankings");
		keyIndex.add(index);
		while(index >= 0) {
			index = newInput.indexOf("rankings", index + 1);
			keyIndex.add(index);
		}
		index = newInput.indexOf("startgame");
		keyIndex.add(index);
		while(index >= 0) {
			index = newInput.indexOf("startgame", index + 1);
			keyIndex.add(index);
		}
		index = newInput.indexOf("exit");
		keyIndex.add(index);
		Collections.sort(keyIndex);
		return keyIndex;
	}

	private static void displayPlayer(String inputStringPart) {
		String diaplayPlayerString = inputStringPart.replaceFirst("^displayPlayer", "");
		if(!diaplayPlayerString.isEmpty()) {
			String userName = diaplayPlayerString.replaceFirst(" ", "");
			if(userExist(userName)){
				for(int i= 0; i < playerList.size(); i++) {
					if(playerList.get(i).userName == userName) {
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
			for(int i = 0; i < playerList.size(); i++) {
				NimPlayer displayPlayer = playerList.get(i);
				System.out.printf("%s,%s,%s,%d games,%d wins\n", displayPlayer.userName, displayPlayer.givenName, displayPlayer.familyName, displayPlayer.numberOfGamesPlayed, displayPlayer.numberOfGamesWon);
			}
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
			System.out.println("The player already exists\n");
		}
		else{
			int index1 = addPlayerString.indexOf(",", index);
			String givenName = addPlayerString.substring(index, index1);
			String firstName = addPlayerString.substring(index1);
			NimPlayer player = new NimPlayer(userName, givenName, firstName);
			playerList.add(player);
			System.out.println("");
		}
	}
	
	private static void removePlayer(String inputStringPart) {
		String removePlayerString = inputStringPart.replaceFirst("^removeplayer", "");
		if(!removePlayerString .startsWith(" ")) {
			if(removePlayerString .startsWith("y")) {
				playerList.clear();
			}
			System.out.println("");
		}
		else {
			boolean exist = false;
			String userName = removePlayerString.replaceFirst(" ", "");
			for(int i = 0; i < playerList.size(); i++) {
				if(playerList.get(i).userName == userName) {
					playerList.remove(i);
					exist = true;
					System.out.println("");
					break;
				}
			}
			if(!exist) {
				System.out.println("The player does not exist.\n");
			}
		}
	}
	
	private static void editPlayer(String inputStringPart){
		boolean exist = false;
		String editPlayerString = inputStringPart.replaceFirst("^editplayer ", "");	
		int index = editPlayerString.indexOf(",");
		String userName = editPlayerString.substring(0, index);
		for(int i = 0; i < playerList.size(); i++) {
			if(userName == playerList.get(i).userName) {
				exist = true;
				int index1 = editPlayerString.indexOf(",", index);
				String familyName = editPlayerString.substring(index, index1);
				String givenName = editPlayerString.substring(index1);
				playerList.get(i).givenName = givenName;
				playerList.get(i).familyName = familyName;
				System.out.println("");
				break;
			}
		}
		if(!exist) {
			System.out.println("The player does not exist.\n");
		}
	}

	private static void startgame(String inputStringPart) {
		String startGameString = inputStringPart.replaceFirst("^startgame ", "");
		String[] initialGame = startGameString.split(",");
		int nameEndIndex = 0;
		for(int i = 0; i< initialGame[3].length(); i++) {
			if(initialGame[3].charAt(i) >='1' && initialGame[3].charAt(i) <= '9') {
				nameEndIndex = i;
				break;
			}
		}
		String userNames = initialGame[3].substring(0, nameEndIndex);
		String removeStones = initialGame[3].substring(nameEndIndex);
		initialGame[3] = userNames;
		if(userExist(initialGame[2]) && userExist(initialGame[3])) {
			initializeNim(initialGame);
			NimGame.playOneRound(removeStones);
		}
		else {
			System.out.println("One of the players does not exist.\n");
		}
	}

	private static void initializeNim(String[] initialGame){
		NimPlayer player1 = null;
		NimPlayer player2 = null;
		int initialStones = Integer.parseInt(initialGame[0]);
		System.out.printf("Initial stone count: %d\n", initialStones);
		int upperBound = Integer.parseInt(initialGame[1]);
		System.out.printf("Maximum stone removal: %d\n", upperBound);
		for(int i = 0; i < playerList.size(); i++) {
			if(playerList.get(i).userName == initialGame[2]) {
				player1 = playerList.get(i);
				break;
			}
		}
		for(int i = 0; i < playerList.size(); i++) {
			if(playerList.get(i).userName == initialGame[3]) {
				player2 = playerList.get(i);
				break;
			}
		}
		System.out.printf("Player 1: %s %s\n", player1.givenName, player1.familyName);
		System.out.printf("Player 2: %s %s\n", player2.givenName, player2.givenName);
		System.out.println("");
		new NimGame(initialStones,upperBound, player1,player2);
	}
	

}
