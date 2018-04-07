
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
	
	public int winningRatio() {
		float winningRatio = (float)numberOfGamesWon/numberOfGamesPlayed;
		return (int)(winningRatio * 100);
	}
}
