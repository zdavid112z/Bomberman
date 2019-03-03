package bomberman;

import java.awt.Point;
import java.util.Random;

import bomberman.PathFinding.Path;

public class Monster extends Mob {
	
	protected Random mRandom = new Random();
	protected int mWalkDirection = 4;
	protected int mActionTime = 1;
	protected int mActionTimeMin = 70, mActionTimeMax = 300;
	protected Path mPathToPlayer = null;
	protected Point mTarget;
	protected int mPathLeft;
	
	public Monster(int x, int y) {
		super(x, y, 32, 32, 16, 1, Game.MainGame.getResourceManager().getGoomba());
		mBoxYOffset = 0;
		mSizeX = 40;
		mSizeY = 40;
		mName = "monster";
		mSpeed = 2;
	}
	
	private void updateTarget()
	{
		mPathToPlayer = Game.MainGame.getScene().getPathFindingToPlayer().getPathToPlayer(getTileCoords(), mPathToPlayer);
		if(mPathToPlayer != null)
		{
			if(mPathToPlayer.path.size() < 1) {
				Player p = ((Player)Game.MainGame.getScene().getEntityByName("player", 1));
				if(p != null)
					mTarget = p.getCenteredCoords();
				else mTarget = null;
			}
			else {
				mTarget = new Point(mPathToPlayer.path.get(0));
				mTarget.x = mTarget.x * Tile.TILE_SIZE + Tile.TILE_SIZE / 2;
				mTarget.y = mTarget.y * Tile.TILE_SIZE + Tile.TILE_SIZE / 2;
			}
		}
		else mTarget = null;
	}
	
	protected void move()
	{
		updateTarget();
		if(mTarget == null) {
			mActionTime--;
			if(mActionTime <= 0)
			{
				mActionTime = mRandom.nextInt(mActionTimeMax - mActionTimeMin) + mActionTimeMin;
				mWalkDirection = mRandom.nextInt(9);
			}
			mVelocityX = (mWalkDirection / 3 - 1) * mSpeed;
			mVelocityY = (mWalkDirection % 3 - 1) * mSpeed;
		}
		else {
			Point cc = getCenteredCoords();
			Point delta = new Point(
					mTarget.x - cc.x, 
					mTarget.y - cc.y);
			mVelocityX = delta.x;
			mVelocityX = Math.min(mSpeed, Math.max(-mSpeed, mVelocityX)); 
			mVelocityY = delta.y;
			mVelocityY = Math.min(mSpeed, Math.max(-mSpeed, mVelocityY));
		}
	}
	
	public void die()
	{
		super.die();
		Game.MainGame.getScene().addEntity(new Explosion(mPositionX + mSizeX / 2, mPositionY + mSizeY / 2, false, false));
	}
	
	public void update()
	{
		super.update();
		if(!mMoving && mTarget != null)
			mTarget = null;
		if(Game.MainGame.getScene().getEntityByName("player", 1) != null)
		{
			if(collidesWith((Mob)Game.MainGame.getScene().getEntityByName("player", 1)))
			{
				Game.MainGame.getScene().getEntityByName("player", 1).die();
			}
		}
			
	}
	
	public void render(Renderer r)
	{
		super.render(r);
	}

}
