//authorship statement: this file was writen by Hanqi Liu, Student ID: 966599,  on 12/03/2018.

import java.util.Scanner;

public class NimPlayer {
	
	String userName;
	String givenName;
	String familyName;
	int numberOfGamesPlayed;
	int numberOfGamesWon;
	//constructor
	public NimPlayer(String userName, String givenName, String familyName){
		this.userName = userName;
		this.givenName = givenName;
		this.familyName = familyName;
	}
	
	public void editNames(String givenName, String familyName) {
		this.givenName = givenName;
		this.familyName = familyName;
	}
	
	public void setNumberGamesPlayed(int numberGamesPlayed) {
		this.numberOfGamesPlayed = numberGamesPlayed + 1;
	}
	
	public void setNumberofGamesWon(int numberOfGamesWon) {
		this.numberOfGamesWon = numberOfGamesWon + 1;
	}
	
	public void resetGames() {
		this.numberOfGamesPlayed = 0;
		this.numberOfGamesWon = 0;
	}
	
	public void updateStatistics(boolean win) {
		this.numberOfGamesPlayed ++;
		if(win) 
			this.numberOfGamesWon += 1;
	}
	
	public int winningRatio() {
		float winningRatio = (float)numberOfGamesWon/numberOfGamesPlayed;
		return (int)(winningRatio * 100);
	}
	
	public int removeStones(Scanner playersInput) {
		int removeStones;
		removeStones = playersInput.nextInt();
		return removeStones;
	}
}