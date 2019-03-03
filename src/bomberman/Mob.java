package bomberman;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class Mob extends IEntity {

	protected static enum Direction
	{
		Up, Down, Left, Right
	};
	
	protected int mSpeed = 2;
	protected int mSizeX = 32;
	protected int mSizeY = 32;
	protected int mBoxYOffset = 16;
	protected int mPositionX, mPositionY;
	protected int mVelocityX = 0, mVelocityY = 0;
	
	protected Direction mDirection = Direction.Down;
	protected final int mAnimSteps = 13;
	protected int mAnimTimer = 0;
	protected int mAnimIndex = 0;
	protected boolean mMoving = false;
	protected BufferedImage[] mFrames;
	
	public Mob(int x, int y, int sx, int sy, int byo, int speed, BufferedImage[] frames)
	{
		mPositionX = x;
		mPositionY = y;
		mSizeX = sx;
		mSizeY = sy;
		mBoxYOffset = byo;
		mSpeed = speed;
		mFrames = frames;
	}
	
	public Point getCenteredCoords()
	{
		return new Point(mPositionX + mSizeX / 2, mPositionY + mSizeY / 2 + mBoxYOffset);
	}
	
	public Point getTileCoords()
	{
		return new Point((mPositionX + mSizeX / 2) / Tile.TILE_SIZE, (mPositionY + mSizeY / 2 + mBoxYOffset) / Tile.TILE_SIZE);
	}
	
	public boolean collidesWith(Mob b)
	{
		int tx = mPositionX + mSizeX / 2;
		int ty = mPositionY + mSizeY / 2 + mBoxYOffset;
		
		int ox = b.mPositionX + b.mSizeX / 2;
		int oy = b.mPositionY + b.mSizeY / 2 + b.mBoxYOffset;
		
		if(Math.abs(tx - ox) <= mSizeX / 2 + b.mSizeX / 2)
			if(Math.abs(ty - oy) <= mSizeY / 2 + b.mSizeY/ 2)
				return true;
		return false;
	}
	
	public boolean collidesWith(int x, int y, int w, int h)
	{
		int tx = mPositionX + mSizeX / 2;
		int ty = mPositionY + mSizeY / 2 + mBoxYOffset;
		
		int ox = x + w / 2;
		int oy = y + h / 2;
		
		if(Math.abs(tx - ox) <= mSizeX / 2 + w / 2)
			if(Math.abs(ty - oy) <= mSizeY / 2 + h / 2)
				return true;
		return false;
	}
	
	protected boolean canWalk(int vx, int vy)
	{
		int nx = mPositionX + vx + mSizeX / 2;
		int ny = mPositionY + vy + mSizeY / 2 + mBoxYOffset;
		
		if(nx - mSizeX / 2 < 0)
			return false;
		if(ny - mSizeY / 2 - mBoxYOffset < 0)
			return false;
		if(nx + mSizeX / 2 >= Game.WIDTH)
			return false;
		if(ny + mSizeY / 2 >= Game.HEIGHT)
			return false;
		
		for(int i = Math.max(nx / Tile.TILE_SIZE - 2, 0); i < Math.min(nx / Tile.TILE_SIZE + 2, Tile.TILE_MAP_WIDTH); i++)
		{
			for(int j = Math.max(ny / Tile.TILE_SIZE - 2, 0); j < Math.min(ny / Tile.TILE_SIZE + 2, Tile.TILE_MAP_HEIGHT); j++)
			{
				Tile t = Game.MainGame.getScene().getTileAt(i, j);
				if(t.isWalkable())
					continue;
				int tcx = i * Tile.TILE_SIZE + Tile.TILE_SIZE / 2;
				int tcy = j * Tile.TILE_SIZE + Tile.TILE_SIZE / 2;
				
				if(Math.abs(nx - tcx) <= mSizeX / 2 + Tile.TILE_SIZE / 2)
					if(Math.abs(ny - tcy) <= mSizeY / 2 + Tile.TILE_SIZE / 2)
						return false;
			}			
		}
		
		return true;
	}
	
	protected void move()
	{
		
	}
	
	@Override
	public void update() {
		mVelocityX = 0;
		mVelocityY = 0;
		mMoving = false;
		
		move();
		
		if(mVelocityX != 0)
		{
			if(canWalk(mVelocityX, 0))
			{
				mMoving = true;
				mPositionX += mVelocityX;
				if(mVelocityX > 0)
					mDirection = Direction.Right;
				else mDirection = Direction.Left;
			}
		}
		if(mVelocityY != 0)
		{
			if(canWalk(0, mVelocityY))
			{
				mMoving = true;
				mPositionY += mVelocityY;
				if(mVelocityY < 0)
					mDirection = Direction.Up;
				else mDirection = Direction.Down;
			}			
		}
		
		mAnimTimer++;
		if(mAnimTimer == mAnimSteps)
		{
			mAnimTimer = 0;
			mAnimIndex++;
			mAnimIndex %= 2;
		}
	}

	@Override
	public void render(Renderer r) {
		switch (mDirection) {
		case Up:
			if(mMoving)
				r.drawImage(mFrames[ResourceManager.PLAYER_BACK_WALK1 + mAnimIndex], mPositionX, mPositionY, 10);
			else r.drawImage(mFrames[ResourceManager.PLAYER_BACK_STAND], mPositionX, mPositionY, 10);
			break;
		case Down:
			if(mMoving)
				r.drawImage(mFrames[ResourceManager.PLAYER_FORWARD_WALK1 + mAnimIndex], mPositionX, mPositionY, 10);
			else r.drawImage(mFrames[ResourceManager.PLAYER_FORWARD_STAND], mPositionX, mPositionY, 10);		
			break;
		case Left:
			if(mMoving)
				r.drawImage(mFrames[ResourceManager.PLAYER_LEFT_WALK1 + mAnimIndex], mPositionX, mPositionY, 10);
			else r.drawImage(mFrames[ResourceManager.PLAYER_LEFT_STAND], mPositionX, mPositionY, 10);
			break;
		case Right:
			if(mMoving)
				r.drawImage(mFrames[ResourceManager.PLAYER_RIGHT_WALK1 + mAnimIndex], mPositionX, mPositionY, 10);
			else r.drawImage(mFrames[ResourceManager.PLAYER_RIGHT_STAND], mPositionX, mPositionY, 10);
			break;
		default:
			break;
		}
	}

}
