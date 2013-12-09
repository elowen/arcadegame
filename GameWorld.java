import javax.swing.*;

import java.awt.*;
import java.util.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.Timer;

import java.awt.event.*;


class GameWorld extends JComponent implements KeyListener {
	private ArrayList<Dust> snow;
	private ArrayList<Object> enemyList;
	private ArrayList<Object> bulletList=new ArrayList<Object>();
	private ArrayList<Object> explosionList=new ArrayList<Object>();
	private Image[] explosion= new Image[16];
	private int enemySpeed =1;
	Object player = new Object(250,420,30,30);
	long elapsed;
	public GameWorld( ) {
		timer.start();
		elapsed = new Date().getTime();
		enemyList = new ArrayList<Object>();

		snow = new ArrayList<Dust>( );
		for(int i = 0; i < 100; i++) {
			snow.add(new Dust( ));
		}
		//Image Loader

		for(int x=0;x<16;x++){
			try {
				explosion[x] = ImageIO.read(new File("boom"+x+".gif"));
			} catch(Exception e) {
				explosion[x] = null;
			}
		}

	}
	
	public boolean touching(Object a, Object b){
		Rectangle one = new Rectangle(a.getX(),a.getY(),a.getWidth(),a.getHeight());
		Rectangle two = new Rectangle(b.getX(),b.getY(),b.getWidth(),b.getHeight());
		if(one.contains(two)){
			return true;
		}
		else
			return false;
	}


	

private Timer timer = new Timer(1000, new ActionListener(){
	public void actionPerformed(ActionEvent e){

		enemySpeed++;

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
		bulletList.add(new Object(player.getX()+10,player.getY(),8,30));
	}else if(key == KeyEvent.VK_ESCAPE){
		System.exit(0);
	}


}

public void paintComponent(Graphics g) {
	long now = new Date().getTime();
	double seconds = (now-elapsed)/1000.0f;
	elapsed=now;
	System.out.println(seconds);

	
	/* set the color to light blue */
	g.setColor(Color.black);
	g.fillRect(0, 0, 500, 500);

	enemyList.add(new Object((int)(Math.random()*500),20,30,30));
	g.setColor(Color.red);
	for(Object e: enemyList){
		g.drawRect(e.getX(),e.getY(),30,30);
		e.setY(e.getY() + enemySpeed);
		
		if(e.getY()>530)
		{
			e.setRemove(true);
		}
	}
	for(Object e: bulletList){
		g.fillRect(e.getX(), e.getY(), 8, 30);
		e.setY(e.getY()-8);
	}


	g.setColor(Color.blue);
	g.drawRect(player.getX(), player.getY(), 30, 30);

	g.setColor(Color.white);
	for(Dust f : snow) {
		f.draw(g);
	}

	/* now update */
	for(Dust f : snow) {
		f.update(seconds);
	}

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

	ArrayList<Object> tempObject=new ArrayList<Object>();
	for(Object e: enemyList){
		if(!e.getRemove()){
			tempObject.add(e);
		}
	}
	enemyList=tempObject;

}
}
