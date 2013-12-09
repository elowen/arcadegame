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
  private double dx, dy, x,y;

  public Dust( ) {
    Random r = new Random( );
    x = r.nextFloat()*500;
    y = r.nextFloat()*500;
    dx = r.nextFloat()*50 - 25;
    dy = r.nextFloat()*50 + 100;
  }

  public void draw(Graphics g) {
    g.fillRect((int)x, (int)y, 3, 3);
  }

  public void update(double dt) {
    x += (dx * dt);
    y += (dy * dt);

    if(y < 0) y = 500;
    if(y > 500) y = 0;
    if(x < 0) x = 500;
    if(x > 500) x = 0;
  }
}



