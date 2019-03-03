package bomberman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scene implements IScene {

	
	private final int mPlatformSpace = 3;
	private List<IEntity> mEntities = new ArrayList<IEntity>();
	private int[][] mTiles;
	private Random mRandom = new Random();
	private Player mPlayer;
	private PathFinding mPathFinding;
	private int mEnemiesAlive;
	private int mLevel;
	private Audio mMusic;
	// 0 = still running, 1 = player died (not frozen), 2 = all enemies destroyed (not frozen)
	// 3 = player died (frozen), 4 = all enemies destroyed (frozen)
	private int mFinishState = 0;
	
	private int mFreezeDuration= 80;
	private int mFreezeTime = 0;
	
	public Scene(int level)
	{
		mMusic = Game.MainGame.getResourceManager().getMusic();
		mLevel = level;
		mEnemiesAlive = level;
		mTiles = new int[Tile.TILE_MAP_WIDTH][Tile.TILE_MAP_HEIGHT];
		for(int i = 0; i < Tile.TILE_MAP_WIDTH; i++)
		{
			for(int j = 0; j < Tile.TILE_MAP_HEIGHT; j++)
			{
				if(i % 2 == 1 && j % 2 == 1)
					mTiles[i][j] = Tile.TILE_UNBREAKABLE_WALL;
				// player platform
				else if(i < mPlatformSpace  && j < mPlatformSpace )
					mTiles[i][j] = Tile.TILE_GROUND;
				// mob1
				else if((i > Tile.TILE_MAP_WIDTH - mPlatformSpace - 1 && j > Tile.TILE_MAP_HEIGHT - mPlatformSpace - 1) && mLevel >= 1)
					mTiles[i][j] = Tile.TILE_GROUND;
				//mob2
				else if((i > Tile.TILE_MAP_WIDTH - mPlatformSpace - 1 && j < mPlatformSpace ) && mLevel >= 2)
					mTiles[i][j] = Tile.TILE_GROUND;
				//mob3
				else if((i < mPlatformSpace && j > Tile.TILE_MAP_HEIGHT - mPlatformSpace - 1) && mLevel >= 3)
					mTiles[i][j] = Tile.TILE_GROUND;
				else 
				{
					int r = mRandom.nextInt(4);
					mTiles[i][j] = Tile.TILE_WALL1 + r;
				}
			}
		}
		mPlayer = new Player();
		mEntities.add(mPlayer);
		if(mLevel >= 1)
			mEntities.add(new Monster((Tile.TILE_MAP_WIDTH - 1) * Tile.TILE_SIZE + 8, (Tile.TILE_MAP_HEIGHT - 1) * Tile.TILE_SIZE + 8));
		if(mLevel >= 2)
			mEntities.add(new Monster((Tile.TILE_MAP_WIDTH - 1) * Tile.TILE_SIZE + 8, 8));
		if(mLevel >= 3)
			mEntities.add(new Monster(8, (Tile.TILE_MAP_HEIGHT - 1) * Tile.TILE_SIZE + 8));
		mPathFinding = new PathFinding(mPlayer);
	}

	public PathFinding getPathFindingToPlayer()
	{
		return mPathFinding;
	}
	
	public Random getRandom()
	{
		return mRandom;
	}
	
	public Tile getTileAt(int x, int y)
	{
		return Tile.Tiles.get(mTiles[x][y]);
	}
	
	public void setTile(int x, int y, int id)
	{
		mTiles[x][y] = id;
	}
	
	public void addEntity(IEntity e)
	{
		mEntities.add(e);
	}
	
	public void removeEntity(IEntity e)
	{
		mEntities.remove(e);
	}
	
	/*
	 * @param repeat Specifies the nth entity with the specified name to be selected
	 * A value of 1 returns the first entity
	 */
	public IEntity getEntityByName(String name, int repeat)
	{
		for(IEntity e : mEntities)
		{
			if(e.getName().equals(name))
			{
				repeat--;
				if(repeat == 0)
					return e;
			}
		}
		return null;
	}
	
	public int getNumEntities()
	{
		return mEntities.size();
	}
	
	public IEntity getEntityAt(int index)
	{
		return mEntities.get(index);
	}
	
	@Override
	public void update() {
		if(!mMusic.isPlaying())
		{
			mMusic.setToStart();
			mMusic.play();
		}
		if(mFinishState == 0 || mFreezeTime < mFreezeDuration)
		{
			for(int i = 0; i < mEntities.size(); i++)
			{
				mEntities.get(i).update();
				if(!mEntities.get(i).isAlive())
				{
					if(mEntities.get(i) instanceof Monster)
					{
						mEnemiesAlive--;
						if(mEnemiesAlive == 0)
						{
							if(mFinishState == 0)
								mFinishState = 1;
						}
					}
					else if(mEntities.get(i) instanceof Player)
					{
						if(mFinishState == 0)
							mFinishState = 2;
					}
					mEntities.remove(i);
					i--;
				}
			}	
		}
		if(mFinishState != 0)
		{
			if(mFinishState <= 2)
			{
				if(mFreezeTime == mFreezeDuration)
				{
					mFinishState += 2;
				}
				else mFreezeTime++;				
			}
			
			if(mFinishState == 4)
			{
				if(Input.getKeyState(KeyEvent.VK_SPACE) == Input.KEY_JUST_PRESSED)
					Game.MainGame.loadScene(new Scene(1));
			}
			else if(mFinishState == 3)
			{
				if(Input.getKeyState(KeyEvent.VK_SPACE) == Input.KEY_JUST_PRESSED)
				{
					if(mLevel < 3)
						Game.MainGame.loadScene(new Scene(mLevel + 1));
					else Game.MainGame.loadScene(new Scene(1));
				}
			}
		}
	}
	
	private void renderFinishRect(Renderer r, String text)
	{
		Font font = Game.MainGame.getResourceManager().getFontCalibri();
		Point metrics = r.getFontMetrics(text, font);
		Point textPos = new Point(Game.WIDTH / 2 - metrics.x / 2, Game.HEIGHT / 2 + metrics.y / 2);
		Point textRectOffset = new Point(24, 24);
		r.drawString(text, Color.white, font, textPos.x, textPos.y, 15);
		r.fillRectBorder(new Color(0xbbbbbb), textPos.x - textRectOffset.x, textPos.y - metrics.y - textRectOffset.y, metrics.x + textRectOffset.x * 2, metrics.y + textRectOffset.y * 2, 14, new Color(0x959595), 3);
	}

	@Override
	public void render(Renderer r) {
		for(int i = 0; i < Game.WIDTH / Tile.TILE_SIZE; i++)
		{
			for(int j = 0; j < Game.HEIGHT / Tile.TILE_SIZE; j++)
			{
				Tile.Tiles.get(mTiles[i][j]).renderAt(r, i * Tile.TILE_SIZE, j * Tile.TILE_SIZE);
			}
		}
		for(IEntity e : mEntities)
			e.render(r);
		if(mFinishState == 3)
		{
			if(mLevel < 3)
				renderFinishRect(r, "You won! Press SPACE to move to the next level");
			else renderFinishRect(r, "You won the game! Press SPACE to restart from the beginning");
		}
		else if(mFinishState == 4)
		{
			renderFinishRect(r, "You lost! Press SPACE to restart");
		}
	}
	
}
