import java.util.*;


class Object{
	private int x,y,w,h;
	private boolean remove=false;

	public Object(int x, int y,int w,int h){
	this.x = x;
	this.y = y;
	this.setWidth(w);
	this.setHeight(h);

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

	public int getHeight() {
		return h;
	}

	public void setHeight(int h) {
		this.h = h;
	}

	public int getWidth() {
		return w;
	}

	public void setWidth(int w) {
		this.w = w;
	}

}
