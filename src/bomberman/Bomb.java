package bomberman;

public class Bomb extends IEntity {

	protected static int mSizeX = 32, mSizeY = 32;
	protected int mPositionX, mPositionY;
	protected int mFuseTime = 190;
	protected int mLife = 0;
	protected int mPower = 3;
	
	public Bomb(int x, int y)
	{
		mName = "bomb";
		mPositionX = (x / Tile.TILE_SIZE) * Tile.TILE_SIZE + Tile.TILE_SIZE / 2 - mSizeX / 2;
		mPositionY = (y / Tile.TILE_SIZE) * Tile.TILE_SIZE + Tile.TILE_SIZE / 2 - mSizeY / 2;
	}
	
	public static boolean canSpawnAt(int x, int y)
	{
		x = (x / Tile.TILE_SIZE) * Tile.TILE_SIZE + Tile.TILE_SIZE / 2 - mSizeX / 2;
		y = (y / Tile.TILE_SIZE) * Tile.TILE_SIZE + Tile.TILE_SIZE / 2 - mSizeY / 2;
		IEntity e = Game.MainGame.getScene().getEntityByName("bomb", 1);
		for(int i = 0; e != null; i++)
		{
			if(((Bomb)e).mPositionX == x && ((Bomb)e).mPositionY == y)
				return false;
			e = Game.MainGame.getScene().getEntityByName("bomb", i + 2);
		}
		return true;
	}

	public void setFuseTime(int time)
	{
		mFuseTime = time;
	}
	
	@Override
	public void update() {
		mLife++;
		if(mLife >= mFuseTime)
		{
			die();
		}
	}

	public void die()
	{
		super.die();
		(new Audio("/sfx/explosion.wav")).play();
		
		int ex = (mPositionX + mSizeX / 2) / Tile.TILE_SIZE;
		int ey = (mPositionY + mSizeY / 2) / Tile.TILE_SIZE;
		
		Game.MainGame.getScene().addEntity(new Explosion(ex, ey, true, true));
		
		//-----------------------------------
		int power = mPower;
		for(int i = 1; i <= power; i++)
		{
			if(ex + i >= Game.WIDTH / Tile.TILE_SIZE)
				break;
			Tile t = Game.MainGame.getScene().getTileAt(ex + i, ey);
			if(t.getID() == Tile.TILE_UNBREAKABLE_WALL)
				break;
			if(Game.MainGame.getScene().getTileAt(ex + i, ey).getID() >= Tile.TILE_WALL1 && Game.MainGame.getScene().getTileAt(ex + i, ey).getID() <= Tile.TILE_WALL4)
			{
				Game.MainGame.getScene().setTile(ex + i, ey, Tile.TILE_GROUND);
				Game.MainGame.getScene().addEntity(new Explosion(ex + i, ey, true, true));
				power -= 1;
				//break;
			}
			else
			{
				Game.MainGame.getScene().addEntity(new Explosion(ex + i, ey, true, true));
			}
		}
		
		//-----------------------------------
		power = mPower;
		for(int i = 1; i <= power; i++)
		{
			if(ey + i >= Game.HEIGHT / Tile.TILE_SIZE)
				break;
			Tile t = Game.MainGame.getScene().getTileAt(ex, ey + i);
			if(t.getID() == Tile.TILE_UNBREAKABLE_WALL)
				break;
			if(Game.MainGame.getScene().getTileAt(ex, ey + i).getID() >= Tile.TILE_WALL1 && Game.MainGame.getScene().getTileAt(ex, ey + i).getID() <= Tile.TILE_WALL4)
			{
				Game.MainGame.getScene().setTile(ex, ey + i, Tile.TILE_GROUND);
				Game.MainGame.getScene().addEntity(new Explosion(ex, ey + i, true, true));
				power -= 1;
				//break;
			}
			else
			{
				Game.MainGame.getScene().addEntity(new Explosion(ex, ey + i, true, true));
			}
		}
		
		//-----------------------------------
		power = mPower;
		for(int i = 1; i <= power; i++)
		{
			if(ex - i < 0)
				break;
			Tile t = Game.MainGame.getScene().getTileAt(ex - i, ey);
			if(t.getID() == Tile.TILE_UNBREAKABLE_WALL)
				break;
			if(Game.MainGame.getScene().getTileAt(ex - i, ey).getID() >= Tile.TILE_WALL1 && Game.MainGame.getScene().getTileAt(ex - i, ey).getID() <= Tile.TILE_WALL4)
			{
				Game.MainGame.getScene().setTile(ex - i, ey, Tile.TILE_GROUND);
				Game.MainGame.getScene().addEntity(new Explosion(ex - i, ey, true, true));
				power -= 1;
				//break;
			}
			else
			{
				Game.MainGame.getScene().addEntity(new Explosion(ex - i, ey, true, true));
			}
		}
		
		//-----------------------------------
		power = mPower;
		for(int i = 1; i <= power; i++)
		{
			if(ey - i < 0)
				break;
			Tile t = Game.MainGame.getScene().getTileAt(ex, ey - i);
			if(t.getID() == Tile.TILE_UNBREAKABLE_WALL)
				break;
			if(Game.MainGame.getScene().getTileAt(ex, ey - i).getID() >= Tile.TILE_WALL1 && Game.MainGame.getScene().getTileAt(ex, ey - i).getID() <= Tile.TILE_WALL4)
			{
				Game.MainGame.getScene().setTile(ex, ey - i, Tile.TILE_GROUND);
				Game.MainGame.getScene().addEntity(new Explosion(ex, ey - i, true, true));
				power -= 1;
				//break;
			}
			else
			{
				Game.MainGame.getScene().addEntity(new Explosion(ex, ey - i, true, true));
			}
		}
		Game.MainGame.getScene().getPathFindingToPlayer().update();
	}
	
	@Override
	public void render(Renderer r) {
		int index = (int)(((float)mLife / (float)mFuseTime) * (float)Game.MainGame.getResourceManager().getBomb().length);
		r.drawImage(Game.MainGame.getResourceManager().getBomb()[index], mPositionX, mPositionY, 5);
	}
	
}
