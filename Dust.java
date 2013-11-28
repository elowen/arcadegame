import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;


class Dust {
  private int x, y, dx, dy;

  public Dust( ) {
    Random r = new Random( );
    x = r.nextInt(500);
    y = r.nextInt(500);
    dx = r.nextInt(2) - 1;
    dy = r.nextInt(2) + 3;
  }

  public void draw(Graphics g) {
    g.fillRect(x, y, 3, 3);
  }

  public void update( ) {
    x += dx;
    y += dy;

    if(y < 0) y = 500;
    if(y > 500) y = 0;
    if(x < 0) x = 500;
    if(x > 500) x = 0;
  }
}



