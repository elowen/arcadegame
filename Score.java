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
		currentScore=current;
		scoreBoard = new int[5]; 
		for(int i=0; i<5; i++){
			scoreBoard[i]=0;
		}
		//File file=new File("scores.txt");
		
		try{
			s=new Scanner(new BufferedReader(new FileReader("scores.txt")));
			while(s.hasNext()){
				//System.out.println(line);
				temp=s.nextInt();
				scoreBoard[count]=temp;
				count++;
			}
		}catch(IOException e){
			System.out.println("IO Exception");
			
		} finally{
			if(s !=null){
				s.close();
			}
		}
	}
	public void compareScores(){
		for(int i=0; i<5; i++){
		//	System.out.println(i+":"+currentScore+" "+scoreBoard[i]);
			if(currentScore>scoreBoard[i]){
				temp=scoreBoard[i];
				scoreBoard[i]=currentScore;
				currentScore=temp;
				
			}
		} 
		System.out.println("Current Score" + currentScore);
		for(int i=0; i<5; i++){
			System.out.println(scoreBoard[i]);
		}
		
	}
	public int getScores(int index){
		i=index;
		return scoreBoard[i];
	}

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
