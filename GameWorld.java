import javax.swing.*;

import java.awt.*;
import java.util.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.Timer;

import java.awt.event.*;
import javax.sound.sampled.*;


class GameWorld extends JComponent implements KeyListener {
	private ArrayList<Dust> snow;
	private ArrayList<Item> enemyList;
	private ArrayList<Item> bulletList=new ArrayList<Item>();
	private ArrayList<Explosion> explosionList=new ArrayList<Explosion>();
	private Image[] explosion= new Image[16];
	private Image ship,badShip,laser;
	private int enemySpeed =3;
	private int lives = 3;
	private int points =0;
	private int screen =0;
	private Score j;
	private Sound s= new Sound("sample.aiff");
	private int[] tempInt= new int[5];
	private Item player = new Item(250,420,32,30);
	private long elapsed;
	private int spawnSpeed=10;
	private long counter;
	private boolean saved=false;
	public GameWorld( ) {
		s.play();//play music
		timer.start();
		elapsed = new Date().getTime();
		enemyList = new ArrayList<Item>();
		snow = new ArrayList<Dust>( );
		for(int i = 0; i < 100; i++) {//spawn STARS
			snow.add(new Dust( ));
		}
		//Image Loader
		try {
			badShip = ImageIO.read(new File("badShip.png"));
			ship = ImageIO.read(new File("Ship.png"));
			laser = ImageIO.read(new File("Laser.png"));
		} catch(Exception e) {
			badShip=null;
			ship = null;
			laser=null;
		}
		for(int x=0;x<16;x++){
			try {
				explosion[x] = ImageIO.read(new File("boom"+x+".gif"));
			} catch(Exception e) {
				explosion[x] = null;
			}
		}
	}

	public boolean touching(Item a, Item b){//check to see if one object is touching another
		Rectangle one = new Rectangle(a.getX(),a.getY(),a.getWidth(),a.getHeight());
		Rectangle two = new Rectangle(b.getX(),b.getY(),b.getWidth(),b.getHeight());
		if(one.intersects(two)){
			return true;
		}
		else
			return false;
	}

	private Timer timer = new Timer(100, new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(screen==1){
				if(counter%spawnSpeed==0){
					enemyList.add(new Item((int)(Math.random()*450),-50,34,30));
				}
				if(counter%30==0){
					if(spawnSpeed>0)
						spawnSpeed--;
				}
				if(counter%45==0){
					if(enemySpeed<30)
						enemySpeed++;
				}
				System.out.println(counter);
				counter++;
			}
		}
	});	

	public void keyReleased(KeyEvent e){
	}
	public void keyTyped(KeyEvent e){
	}

	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT){
			if(player.getX()<=490)
				player.setX(player.getX()+10);
		}else if(key == KeyEvent.VK_LEFT){
			if(player.getX()>=10)
				player.setX(player.getX()-10);
		}else if(key == KeyEvent.VK_UP){
			if(player.getY()>=10)
				player.setY(player.getY()-10);
		}else if(key == KeyEvent.VK_DOWN){
			if(player.getY()<=490)
				player.setY(player.getY()+10);
		}else if(key == KeyEvent.VK_SPACE){
			bulletList.add(new Item(player.getX()+16,player.getY()+10,8,30));
		}else if(key == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}else if(key==KeyEvent.VK_ENTER){
			if(screen==0){
				screen++;
			}
		}
	}
	
	public void paintComponent(Graphics g) {

		long now = new Date().getTime();//update the timer
		double seconds = (now-elapsed)/1000.0f;
		elapsed=now;
		if(now-s.getTime()>46000){
			s.play();//repeat the music if 46 seconds have passed
		}			
		if(screen==0){//draw title screen
			g.setColor(Color.black);
			g.fillRect(0,0,500,500);
			g.setColor(Color.magenta);
			g.setFont(new Font("Sarif", Font.BOLD,42));
			g.drawString("Totally Kick Ass", 75, 125);
			g.drawString("Rockin Game", 110, 175);
			g.drawString("Press Enter to Begin", 35, 225);
			for(Dust f : snow) {
				f.update(seconds);
			}
			revalidate( );
			repaint( );
		}
		else if(screen==1){//game screen
			if(lives<1){
				screen++;
			}
			//draw the background
			g.setColor(Color.black);
			g.fillRect(0, 0, 500, 500);
			//draw the snow
			g.setColor(Color.white);
			for(Dust f : snow) {
				f.draw(g);
			}
			for(Dust f : snow) {//Update the snow
				f.update(seconds);
			}
			//Process the enemies
			for(Item e: enemyList){
				g.drawImage(badShip,e.getX(),e.getY(),null); //draw all enemies
				e.setY(e.getY() + enemySpeed);//move all enemies
				if(touching(e,player)){//check to see if any enemy is tounching the player
					explosionList.add(new Explosion(e.getX()-30,e.getY()-30));
					e.setRemove(true);
					lives--;
				}
				if(e.getY()>530){//check to see if enemy has left the screen
					e.setRemove(true);
				}
			}
			//Process the bullets
			for(Item e: bulletList){
				g.drawImage(laser,e.getX(), e.getY(),null);//draw bullets
				e.setY(e.getY()-10);//move bullets
				for(Item e2: enemyList){//check if bullets hit enemies
					if(touching(e,e2)&&!e2.getRemove()){
						explosionList.add((new Explosion(e.getX()-30,e.getY()-30)));
						points+=10;
						e.setRemove(true);
						e2.setRemove(true);
					}
					if(e.getY()<-50){//check if bullets leave screen
						e.setRemove(true);
					}
				}
			}
			//process explosions
			for(Explosion e: explosionList){
				if(e.getCurrent()<16){//if current frame is valid, draw explosion frame
					g.drawImage(explosion[e.getCurrent()], e.getX(), e.getY(),null);
				}
			}
			g.drawImage(ship, player.getX(), player.getY(), null);//draw player
			g.setColor(Color.magenta);
			g.setFont(new Font("Sarif", Font.BOLD,20));
			g.drawString("Lives: "+lives, 0, 20);//draw lives
			g.drawString("Score: "+points, 350, 20);//draw score

			/* force an update */
			revalidate( );
			repaint( );

			/* sleep for 1/20th of a second */
			try {
				Thread.sleep(50);
			} catch(InterruptedException e) {
				Thread.currentThread( ).interrupt( );
			}
			//Remove all the objects that have been flagged for removal
			ArrayList<Item> tempItem=new ArrayList<Item>();
			for(Item e: enemyList){
				if(!e.getRemove()){
					tempItem.add(e);
				}
			}
			enemyList=tempItem;
			ArrayList<Item> tempBullet=new ArrayList<Item>();
			for(Item e: bulletList){
				if(!e.getRemove()){
					tempBullet.add(e);
				}
			}
			bulletList=tempBullet;
			ArrayList<Explosion> tempExplosion=new ArrayList<Explosion>();
			for(Explosion e: explosionList){
				if(!(e.getCurrent()>=16)){//remove explosion if it is over
					tempExplosion.add(e);
				}
			}
			explosionList=tempExplosion;
		}
		else if(screen==2){//draw end screen
			j=new Score(points);
			j.compareScores();	
			g.setColor(Color.black);
			g.fillRect(0,0,500,500);
			g.setColor(Color.magenta);
			g.setFont(new Font("Sarif", Font.BOLD,42));
			g.drawString("You Deaded :C", 75, 125);
			g.drawString("High Scores", 110, 175);
			if(!saved){//do this only once
				saved=true;
				for(int i=0; i<5; i++){
					tempInt[i]=j.getScores(i);
				}			
				for(int i=0; i<5; i++){
					g.drawString(""+tempInt[i], 200, 250+(i*50));//draw highscores
				}
				j.writeScore();//write scores to file
			}
		}
	}
}
