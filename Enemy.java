import java.util.*;


class Enemy{
	private int x,y;
	private boolean remove=false;

	public Enemy(int x, int y){
	this.x = x;
	this.y = y;

}

	public int getX(){
	return x;

	}
	public int getY(){
	return y;

	}

	public void setX(int x){
	this.x = x;

	}

	public void setY(int y){
	this.y = y;
	
	}

	public boolean getRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

}
