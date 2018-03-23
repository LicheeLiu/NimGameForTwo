//authorship statement: this file was writen by Hanqi Liu on 12/03/2018.

import java.util.Scanner;

public class Nimsys {
	
	private static String playerName1;
	private static String playerName2;
	private static int upperBound;
	private static int initialStones;

	public static void main(String[] args) {
		char playAgain = 'Y';
		
		Scanner playersInput = new Scanner(System.in); 
		System.out.println("Welcome to Nim");
		System.out.println("");
		System.out.println("Please enter Player 1's name:");
		playerName1 = playersInput.next();
		System.out.println("");
		//player1 instance
		NimPlayer player1 = new NimPlayer(playerName1);
		System.out.println("Please enter Player 2's name:");
		playerName2 = playersInput.next();
		System.out.println("");
		//player2 instance
		NimPlayer player2 = new NimPlayer(playerName2);

		while(playAgain == 'Y') {
			initializeNim(playersInput);
			playAgain = playOneRound(player1, player2, playersInput);
		}
	}	
	
	public static void initializeNim(Scanner playersInput){

		System.out.println("Please enter upper bound of stone removal:");
		upperBound = playersInput.nextInt();
		System.out.println("");
		System.out.println("Please enter initial number of stones:");
		initialStones = playersInput.nextInt();
		System.out.println("");
		System.out.print(initialStones + " stones left:");
		for(int i = 0; i < initialStones; i++)
			System.out.print(" *");
		System.out.println("");
	}
	
	public static char playOneRound(NimPlayer player1, NimPlayer player2, Scanner playersInput) {
		
		int leftStones;
		int countMove = 1; //the flag of who's move it is, initialize to 1 for every round
		int removeStones;
		char playAgain;

		leftStones = initialStones;
		while(leftStones > 0) {
			//odd number is player1's move
			if(countMove%2 == 1) { 
				System.out.println(player1.getName() + "'s turn - remove how many?");
				removeStones = player1.removeStones(playersInput);
			}
			//even number is player2's move.
			else {
				System.out.println(player2.getName() + "'s turn - remove how many?");
				removeStones = player2.removeStones(playersInput);
			}
			System.out.println("");
			leftStones -=  removeStones;
			
			if(leftStones == 0){
				System.out.println("Game Over");
				if(countMove%2 == 1) {
					System.out.println(player2.getName() + " wins!");
				}
				else {
					System.out.println(player1.getName() + " wins!");
				}
				System.out.println("");
			}
			else {
				System.out.print(leftStones + " stones left:");
				for(int i = 0; i < leftStones; i++)
					System.out.print(" *");
				System.out.println("");
				countMove++;
			}
		}
		System.out.println("Do you want to play again (Y/N):");
		playAgain  = playersInput.next().charAt(0);
		return playAgain;
	}
}
