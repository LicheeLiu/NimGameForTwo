//authorship statement: this file was writen by Hanqi Liu on 12/03/2018.

import java.util.Scanner;

public class NimPlayer {
	
	String name;
	//constructor
	NimPlayer(String name){
		this.name = name;
	}
	
	//get
	public String getName(){
		return name;
	}
	
	public int removeStones(Scanner playersInput) {
	{
		int removeStones;
		removeStones = playersInput.nextInt();
		return removeStones;
	}
	}
}
