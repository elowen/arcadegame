
public class Explosion extends Object implements Runnable{
	private int x,y,current;
	private Thread t;
	public Explosion(int x,int y){
		this.x=x;
		this.y=y;
		t= new Thread(this);
		t.start();
	}
	public void run(){
		while(current<16){
			current++;
			try{
				Thread.sleep(125);
			}catch(Exception e){}
		}
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getCurrent() {
		return current;
	}
	
}
