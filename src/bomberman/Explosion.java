package bomberman;

public class Explosion extends IEntity {

	protected int mSizeX = 48, mSizeY = 48;
	protected int mPositionX, mPositionY;
	protected int mDuration = 60;
	protected int mLife = 0;
	
	public Explosion(int x, int y, boolean isTile, boolean harmful)
	{
		//Game.MainGame.getResourceManager().getExplosionSFX().stop();
		//Game.MainGame.getResourceManager().getExplosionSFX().setToStart();
		//Game.MainGame.getResourceManager().getExplosionSFX().play();
		if(!harmful)
			(new Audio("/sfx/explosion.wav")).play();
		mName = "explosion";
		if(isTile)
		{
			mPositionX = x * Tile.TILE_SIZE + Tile.TILE_SIZE / 2 - mSizeX / 2;
			mPositionY = y * Tile.TILE_SIZE + Tile.TILE_SIZE / 2 - mSizeY / 2;
		}
		else
		{
			mPositionX = x - mSizeX / 2;
			mPositionY = y - mSizeY / 2;
		}
		if(harmful)
		{
			for(int i = 0; i < Game.MainGame.getScene().getNumEntities(); i++)
			{
				if(Game.MainGame.getScene().getEntityAt(i) instanceof Mob)
				{
					Mob m = (Mob)Game.MainGame.getScene().getEntityAt(i);
					if(m.collidesWith(mPositionX, mPositionY, mSizeX, mSizeY))
					{
						m.die();
					}
				}
				else if(Game.MainGame.getScene().getEntityAt(i) instanceof Bomb)
				{
					if(((Bomb)Game.MainGame.getScene().getEntityAt(i)).mAlive)
						if(mPositionX + 8 == ((Bomb)Game.MainGame.getScene().getEntityAt(i)).mPositionX)
							if(mPositionY + 8 == ((Bomb)Game.MainGame.getScene().getEntityAt(i)).mPositionY)
								((Bomb)Game.MainGame.getScene().getEntityAt(i)).die();
				}
			}
		}
	}
	
	@Override
	public void update() {
		mLife++;
		if(mLife >= mDuration)
		{
			die();
		}
	}

	@Override
	public void render(Renderer r) {
		int index = (int)(((float)mLife / (float)mDuration) * (float)Game.MainGame.getResourceManager().getExplosion().length);
		r.drawImage(Game.MainGame.getResourceManager().getExplosion()[index], mPositionX, mPositionY, 12);
	}

	
	
}
