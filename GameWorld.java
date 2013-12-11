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
	private int enemySpeed =1;
	private int lives = 1;
	private int points =0;
	private int screen =0;
	private Score j;
	private Sound s= new Sound("sample.aiff");
	private int[] tempInt= new int[5];
	Item player = new Item(250,420,32,30);
	long elapsed;
	private boolean saved=false;
	public GameWorld( ) {
		s.play();
		timer.start();
		elapsed = new Date().getTime();
		enemyList = new ArrayList<Item>();

		snow = new ArrayList<Dust>( );
		for(int i = 0; i < 100; i++) {
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

	public boolean touching(Item a, Item b){
		Rectangle one = new Rectangle(a.getX(),a.getY(),a.getWidth(),a.getHeight());
		Rectangle two = new Rectangle(b.getX(),b.getY(),b.getWidth(),b.getHeight());
		if(one.intersects(two)){
			return true;
		}
		else
			return false;
	}

	public void explode(int x,int y){

	}




	private Timer timer = new Timer(1000, new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(screen==1){
				enemyList.add(new Item((int)(Math.random()*500),20,30,30));
				enemySpeed++;
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
			player.setX(player.getX()+5);
		}else if(key == KeyEvent.VK_LEFT){
			player.setX(player.getX()-5);
		}else if(key == KeyEvent.VK_UP){
			player.setY(player.getY()-5);
		}else if(key == KeyEvent.VK_DOWN){
			player.setY(player.getY()+5);
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

			long now = new Date().getTime();
			double seconds = (now-elapsed)/1000.0f;
			elapsed=now;
			if(now-s.getTime()>46000){
				s.play();
			}			
		if(screen==0){
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
		else if(screen==1){
			if(lives<1){
				screen++;
			}

			/* set the color to light blue */
			g.setColor(Color.black);
			g.fillRect(0, 0, 500, 500);

			g.setColor(Color.red);
			for(Item e: enemyList){
				g.drawImage(badShip,e.getX(),e.getY(),null);
				e.setY(e.getY() + enemySpeed);
				if(touching(e,player)){
					explosionList.add(new Explosion(e.getX()-30,e.getY()-30));
					e.setRemove(true);
					lives--;
				}
				if(e.getY()>530){
					e.setRemove(true);
				}
			}
			for(Item e: bulletList){
				g.drawImage(laser,e.getX(), e.getY(),null);
				e.setY(e.getY()-10);
				for(Item e2: enemyList){
					if(touching(e,e2)&&!e2.getRemove()){
						explosionList.add((new Explosion(e.getX()-30,e.getY()-30)));
						points+=10;
						e.setRemove(true);
						e2.setRemove(true);
					}
					if(e.getY()<-50){
						e.setRemove(true);
					}
				}
			}
			for(Explosion e: explosionList){
				if(e.getCurrent()<16){
				g.drawImage(explosion[e.getCurrent()], e.getX(), e.getY(),null);
				}
			}


			g.setColor(Color.blue);
			g.drawImage(ship, player.getX(), player.getY(), null);

			g.setColor(Color.white);
			for(Dust f : snow) {
				f.draw(g);
			}

			/* now update */
			for(Dust f : snow) {
				f.update(seconds);
			}

			g.setColor(Color.magenta);
			g.setFont(new Font("Sarif", Font.BOLD,20));
			g.drawString("Lives: "+lives, 0, 20);
			g.drawString("Score: "+points, 350, 20);


			/* force an update */
			revalidate( );
			repaint( );

			/* sleep for 1/20th of a second */
			try {
				Thread.sleep(50);
			} catch(InterruptedException e) {
				Thread.currentThread( ).interrupt( );
			}

			/*Removers*/

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
				if(!(e.getCurrent()>=16)){
					tempExplosion.add(e);
				}
			}
			explosionList=tempExplosion;
		}
		else if(screen==2){
			j=new Score(points);
			j.compareScores();	
			g.setColor(Color.black);
			g.fillRect(0,0,500,500);
			g.setColor(Color.magenta);
			g.setFont(new Font("Sarif", Font.BOLD,42));
			g.drawString("You Deaded :C", 75, 125);
			g.drawString("High Scores", 110, 175);
			if(!saved){
			saved=true;
			for(int i=0; i<5; i++){
				tempInt[i]=j.getScores(i);
			}			
			for(int i=0; i<5; i++){
				g.drawString(""+tempInt[i], 200, 250+(i*50));
			}
			j.writeScore();
			}

		}

	}
}
