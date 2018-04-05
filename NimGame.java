import java.lang.Math;

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
	
	public static void playOneRound(String removeStones) {
		
		int leftStones;
		int countMove = 1; //the flag of who's move it is, initialize to 1 for every round
		leftStones = initialStones;
		int[] removeStoneEachRound = new int[removeStones.length()];
		for(int i = 0; i < removeStones.length(); i++) {
			removeStoneEachRound[i] = removeStones.charAt(i) - '0';
		}
		System.out.print(initialStones + " stones left:");
		for(int i = 0; i < initialStones; i++)
			System.out.print(" *");
		System.out.println("");
		
		int countMoveStoneIndex = 0;
		while(leftStones > 0) {
			System.out.print(leftStones + " stones left:");
			for(int i = 0; i < leftStones; i++)
				System.out.print(" *");
			System.out.println("");

			//odd number is player1's move
			if(countMove%2 == 1) { 
				System.out.println(player1.userName + "'s turn - remove how many?");
			}
			//even number is player2's move.
			else {
				System.out.println(player2.userName + "'s turn - remove how many?");
			}
			System.out.print(removeStoneEachRound[countMoveStoneIndex]);
			System.out.println("");

			if(removeStoneEachRound[countMoveStoneIndex] > Math.min(upperBound, leftStones)|| removeStoneEachRound[countMove - 1] < 1) {
					System.out.printf("Invalid move. You must remove between 1 and %d stones.\n\n", Math.min(upperBound, leftStones));
					continue;
			}
			else {
				countMove++;
				leftStones -= removeStoneEachRound[countMoveStoneIndex];
			}
			countMoveStoneIndex++;
			
			if(leftStones == 0){
				System.out.println("Game Over");
				if(countMove%2 == 1) {
					System.out.println(player2.userName + " wins!");
				}
				else {
					System.out.println(player1.userName + " wins!");
				}
				System.out.println("");
			}
		}
	}
}
