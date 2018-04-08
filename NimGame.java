import java.util.Scanner;

public class NimGame {
	static int initialStones;
	static int upperBound;
	static NimPlayer  player1;
	static NimPlayer player2;
	
	public NimGame(int initialStones, int upperBound, NimPlayer player1, NimPlayer player2) {
		NimGame.initialStones = initialStones;
		NimGame.upperBound = upperBound;
		NimGame.player1 = player1;
		NimGame.player2 = player2;
	}
	
	public static void playOneRound(Scanner playersInput) {
		
		int leftStones;
		int countMove = 1; //the flag of who's move it is, initialize to 1 for every round
		int removeStones;

		leftStones = initialStones;
		System.out.print(initialStones + " stones left:");
		for(int i = 0; i < initialStones; i++)
			System.out.print(" *");
		System.out.println("");

		while(leftStones > 0) {
			//odd number is player1's move
			if(countMove%2 == 1) { 
				System.out.println(player1.givenName+ "'s turn - remove how many?");
				removeStones = player1.removeStones(playersInput);
			}
			//even number is player2's move.
			else {
				System.out.println(player2.givenName + "'s turn - remove how many?");
				removeStones = player2.removeStones(playersInput);
			}
			System.out.println("");
			leftStones -=  removeStones;
			
			if(leftStones == 0){
				System.out.println("Game Over");
				if(countMove%2 == 1) {
					player2.updateStatistics(true);
					player1.updateStatistics(false);
					System.out.println(player2.givenName + " " + player2.familyName + " wins!");
				}
				else {
					player1.updateStatistics(true);
					player2.updateStatistics(false);
					System.out.println(player1.givenName + " " + player1.familyName + " wins!");
				}
			}
			else {
				System.out.print(leftStones + " stones left:");
				for(int i = 0; i < leftStones; i++)
					System.out.print(" *");
				System.out.println("");
				countMove++;
			}
		}
		System.out.printf("\n$");
	}
}