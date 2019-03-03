package bomberman;

public abstract class IEntity {

	protected String mName;
	protected boolean mAlive = true;
	
	public String getName() {return mName;}
	public void die() {mAlive = false;}
	public boolean isAlive() {return mAlive;}
	public abstract void update();
	public abstract void render(Renderer r);
	
}
