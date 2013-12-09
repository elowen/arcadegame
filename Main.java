import javax.swing.*;

import java.awt.*;
import java.util.*;

import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Main {
  public static void main(String args[]) {
    // create and set up the window.
    JFrame frame = new JFrame("TOTALLY KICK ASS ROCKING GAME");

    // make the program close when the window closes
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // add the GameWorld component
    //frame.add(new GameWorld( ));
	GameWorld g = new GameWorld();
	frame.add(g);
	frame.addKeyListener(g);	

    // display the window.
    frame.setSize(500, 500);
    frame.setVisible(true);
	
  }    
}

