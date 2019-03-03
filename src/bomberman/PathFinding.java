package bomberman;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PathFinding {

	public static class Path
	{
		public List<Point> path = new ArrayList<Point>(); 
	};
	
	public static final int START = 0;
	public static final int WALL = -1;
	public static final int FREE = -2;
	private int[][] mMatrix = new int[Tile.TILE_MAP_WIDTH][Tile.TILE_MAP_HEIGHT];
	private List<Point> mList = new ArrayList<Point>();
	private Mob mMob;
	
	public PathFinding(Mob mob)
	{
		mMob = mob;
		for(int i = 0; i < Tile.TILE_MAP_WIDTH; i++)
		{
			for(int j = 0; j < Tile.TILE_MAP_HEIGHT; j++)
			{
				mMatrix[i][j] = WALL;
			}
		}
	}
	
	public void update()
	{
		for(int i = 0; i < Tile.TILE_MAP_WIDTH; i++)
		{
			for(int j = 0; j < Tile.TILE_MAP_HEIGHT; j++)
			{
				if(Game.MainGame.getScene().getTileAt(i, j).isWalkable())
					mMatrix[i][j] = FREE;
				else mMatrix[i][j] = WALL;
			}
		}
		Point pc = mMob.getTileCoords();
		mMatrix[pc.x][pc.y] = START;
		mList.clear();
		mList.add(pc);
		int left = 0;
		while(left != mList.size())
		{
			Point c = mList.get(left);
			
			if(c.x > 0) {
				if(mMatrix[c.x - 1][c.y] == FREE) {
					mList.add(new Point(c.x - 1, c.y));
					mMatrix[c.x - 1][c.y] = mMatrix[c.x][c.y] + 1;
				}
			}
			if(c.y > 0) {
				if(mMatrix[c.x][c.y - 1] == FREE) {
					mList.add(new Point(c.x, c.y - 1));
					mMatrix[c.x][c.y - 1] = mMatrix[c.x][c.y] + 1;
				}
			}
			if(c.x < Tile.TILE_MAP_WIDTH - 1) {
				if(mMatrix[c.x + 1][c.y] == FREE) {
					mList.add(new Point(c.x + 1, c.y));
					mMatrix[c.x + 1][c.y] = mMatrix[c.x][c.y] + 1;
				}
			}
			if(c.y < Tile.TILE_MAP_HEIGHT - 1) {
				if(mMatrix[c.x][c.y + 1] == FREE) {
					mList.add(new Point(c.x, c.y + 1));
					mMatrix[c.x][c.y + 1] = mMatrix[c.x][c.y] + 1;
				}
			}
			
			left++;
		}
	}
	
	public Path getPathToPlayer(Point start, Path path)
	{
		if(mMob == null)
			return null;
		if(path == null)
			path = new Path();
		if(mMatrix[start.x][start.y] < 0)
			return null;
		Point c = new Point(start);
		path.path.clear();
		//path.path.add(new Point(start));
		while(mMatrix[c.x][c.y] != START)
		{	
			if(c.x > 0) {
				if(mMatrix[c.x - 1][c.y] == mMatrix[c.x][c.y] - 1) {
					path.path.add(new Point(c.x - 1, c.y));
					c.x--;
					continue;
				}
			}
			if(c.y > 0) {
				if(mMatrix[c.x][c.y - 1] == mMatrix[c.x][c.y] - 1) {
					path.path.add(new Point(c.x, c.y - 1));
					c.y--;
					continue;
				}
			}
			if(c.x < Tile.TILE_MAP_WIDTH - 1) {
				if(mMatrix[c.x + 1][c.y] == mMatrix[c.x][c.y] - 1) {
					path.path.add(new Point(c.x + 1, c.y));
					c.x++;
					continue;
				}
			}
			if(c.y < Tile.TILE_MAP_HEIGHT - 1) {
				if(mMatrix[c.x][c.y + 1] == mMatrix[c.x][c.y] - 1) {
					path.path.add(new Point(c.x, c.y + 1));
					c.y++;
					continue;
				}
			}
			
		}
		if(path.path.size() > 0)
			path.path.remove(path.path.size() - 1);
		return path;
	}
	
}
