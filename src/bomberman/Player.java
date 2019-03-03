package bomberman;

import java.awt.Point;
import java.awt.event.KeyEvent;

public class Player extends Mob {
	
	private Point mPreviousPosition;
	
	public Player()
	{
		super(8, 8, 32, 32, 16, 2, Game.MainGame.getResourceManager().getBluePlayer());
		mName = "player";
	}
	
	protected void move()
	{
		mPreviousPosition = getTileCoords();
		if(Input.isKeyDown(KeyEvent.VK_W))
			mVelocityY -= mSpeed;
		if(Input.isKeyDown(KeyEvent.VK_A))
			mVelocityX -= mSpeed;
		if(Input.isKeyDown(KeyEvent.VK_S))
			mVelocityY += mSpeed;
		if(Input.isKeyDown(KeyEvent.VK_D))
			mVelocityX += mSpeed;
	}
	
	public void die()
	{
		super.die();
		Game.MainGame.getScene().addEntity(new Explosion(mPositionX + mSizeX / 2, mPositionY + mSizeY / 2, false, false));
	}
	
	public void update()
	{
		super.update();
		
		Point now = getTileCoords();
		if(!now.equals(mPreviousPosition))
			Game.MainGame.getScene().getPathFindingToPlayer().update();
		
		if(Input.getKeyState(KeyEvent.VK_SPACE) == Input.KEY_JUST_PRESSED)
		{
			if(Bomb.canSpawnAt(mPositionX + mSizeX / 2, mPositionY + mSizeY - 8))
				Game.MainGame.getScene().addEntity(new Bomb(mPositionX + mSizeX / 2, mPositionY + mSizeY - 8));
		}
	}
	
	public void render(Renderer r)
	{
		super.render(r);
	}
	
}
