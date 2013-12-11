import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;
import java.io.PrintWriter;
class Score {
	public Score (int current){
		
		//sets the player's points at the end of the game
		currentScore=current;
		
		//initializes the scoreboard array empty
		scoreBoard = new int[5]; 
		for(int i=0; i<5; i++){
			scoreBoard[i]=0;
		}
		
		//create a Scanner to open a text file and store the containing numbers into the scoreBoard array
		try{
			s=new Scanner(new BufferedReader(new FileReader("scores.txt")));
			while(s.hasNext()){
				
				temp=s.nextInt();
				scoreBoard[count]=temp;
				count++;
			}
		}catch(IOException e){
			System.out.println("IO Exception");
			
		} finally{
			//closes the file
			if(s !=null){
				s.close();
			}
		}
	}
	
	//determines if the player scored highly enough to make it into the high scores record
	public void compareScores(){
		for(int i=0; i<5; i++){
			
			//places the new score into the appropriate place on the scoreboard
			if(currentScore>scoreBoard[i]){
				temp=scoreBoard[i];
				scoreBoard[i]=currentScore;
				currentScore=temp;
				
			}
		} 
	}
	
	//returns a specific score, is called by a loop in GameWorld
	public int getScores(int index){
		i=index;
		return scoreBoard[i];
	}

	//writes the new scoreboard to the text file for long term storage
	public void writeScore(){
		try{PrintWriter writer= new PrintWriter("scores.txt", "UTF-8");
		writer.flush();
		for(int i=0; i<5; i++){
			writer.println(scoreBoard[i]);
		}
		writer.close();}
		catch(Exception e){
			System.out.println("File not found.");
		}
	}

	private String line;
	private int i;
	private int[] scoreBoard;
	private int currentScore;
	private int temp=0;
	private int count=0;
	private String tempString=null;
	private Scanner s;
}
