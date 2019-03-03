package bomberman;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Tile {
	
	public static class NullTile extends Tile
	{

		public NullTile() {
			super(null, TILE_NULL, true);
		}
		public void renderAt(Renderer r, int x, int y)
		{
			r.fillRect(Color.BLACK, x, y, TILE_SIZE, TILE_SIZE, 0);
		}
	};
	
	public static final int TILE_NULL = 0;
	public static final int TILE_GROUND = 1;
	public static final int TILE_WALL1 = 2;
	public static final int TILE_WALL2 = 3;
	public static final int TILE_WALL3 = 4;
	public static final int TILE_WALL4 = 5;
	public static final int TILE_UNBREAKABLE_WALL = 6;
	public static final int TILE_COUNT = 7;
	
	public static final int TILE_SIZE = 64;
	public static final int TILE_MAP_WIDTH = 15;
	public static final int TILE_MAP_HEIGHT = 9;
	
	public static List<Tile> Tiles;
	
	public static void init(ResourceManager r)
	{
		Tiles = new ArrayList<Tile>();
		Tiles.add(new NullTile());
		Tiles.add(new Tile(r.getGroundTile(), TILE_GROUND, true));
		Tiles.add(new Tile(r.getBreakableTiles()[0], TILE_WALL1, false));
		Tiles.add(new Tile(r.getBreakableTiles()[1], TILE_WALL2, false));
		Tiles.add(new Tile(r.getBreakableTiles()[2], TILE_WALL3, false));
		Tiles.add(new Tile(r.getBreakableTiles()[3], TILE_WALL4, false));
		Tiles.add(new Tile(r.getUnbreakableTile(), TILE_UNBREAKABLE_WALL, false));
	}
	
	private BufferedImage mImage;
	private int mID;
	private boolean mWalkable;
	
	public Tile(BufferedImage image, int id, boolean walkable)
	{
		mImage = image;
		mID = id;
		mWalkable = walkable;
	}
	
	public int getID()
	{
		return mID;
	}
	
	public boolean isWalkable()
	{
		return mWalkable;
	}
	
	public void renderAt(Renderer r, int x, int y)
	{
		r.drawImage(mImage, x, y, 0);
	}
	
}
