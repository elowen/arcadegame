import javax.swing.*;

import java.awt.*;
import java.util.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.Timer;

import java.awt.event.*;


class GameWorld extends JComponent implements KeyListener {
	private ArrayList<Dust> snow;
	private ArrayList<Enemy> enemyList;
	private int enemySpeed =1;
	public GameWorld( ) {
		timer.start();
		enemyList = new ArrayList<Enemy>();


		snow = new ArrayList<Dust>( );
		for(int i = 0; i < 100; i++) {
			snow.add(new Dust( ));

		}

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
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			//.right();
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			//.left();
		}else if(e.getKeyCode() == KeyEvent.VK_UP){
			//.up();
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			//.down();
		}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}


	}
	
	public void paintComponent(Graphics g) {
		/* set the color to light blue */
		g.setColor(Color.black);
		enemyList.add(new Enemy((int)(Math.random()*500),20));
		g.fillRect(0, 0, 500, 500);
		g.setColor(Color.red);
		for(Enemy e: enemyList){
			g.drawRect(e.getX(),e.getY(),30,30);
			e.setY(e.getY() + enemySpeed);
			if(e.getY()>530)
			{
				e.setRemove(true);
			}
		}



		g.setColor(Color.white);
		for(Dust f : snow) {
			f.draw(g);
		}

		/* now update */
		for(Dust f : snow) {
			f.update( );
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
		
		ArrayList<Enemy> tempEnemy=new ArrayList<Enemy>();
		for(Enemy e: enemyList){
			if(!e.getRemove()){
				tempEnemy.add(e);
			}
		}
		enemyList=tempEnemy;
		
	}
}
