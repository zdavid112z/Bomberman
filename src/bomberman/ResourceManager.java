package bomberman;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ResourceManager {
	
	public static final int PLAYER_FORWARD_STAND = 0;
	public static final int PLAYER_FORWARD_WALK1 = 1;
	public static final int PLAYER_FORWARD_WALK2 = 2;
	public static final int PLAYER_LEFT_STAND = 3;
	public static final int PLAYER_LEFT_WALK1 = 4;
	public static final int PLAYER_LEFT_WALK2 = 5;
	public static final int PLAYER_RIGHT_STAND = 6;
	public static final int PLAYER_RIGHT_WALK1 = 7;
	public static final int PLAYER_RIGHT_WALK2 = 8;
	public static final int PLAYER_BACK_STAND = 9;
	public static final int PLAYER_BACK_WALK1 = 10;
	public static final int PLAYER_BACK_WALK2 = 11;
	
	private static ResourceManager sInstance;
	
	public static ResourceManager getInstance()
	{
		return sInstance;
	}
	
	private BufferedImage[] mRedPlayer = new BufferedImage[12];
	private BufferedImage[] mBluePlayer = new BufferedImage[12];
	private BufferedImage[] mGoomba = new BufferedImage[12];
	private BufferedImage[] mBreakableTiles = new BufferedImage[4];
	private BufferedImage[] mBomb = new BufferedImage[6];
	private BufferedImage[] mExplosion = new BufferedImage[8];
	private BufferedImage mUnbreakableTile;
	private BufferedImage mGroundTile;
	private Audio mMusic;
	private Audio mExplosionSFX;
	
	private Font mFontCalibri;
	
	public BufferedImage[] getRedPlayer() {
		return mRedPlayer;
	}
	
	public BufferedImage[] getBluePlayer() {
		return mBluePlayer;
	}
	
	public BufferedImage[] getGoomba()
	{
		return mGoomba;
	}
	
	public BufferedImage[] getBreakableTiles() {
		return mBreakableTiles;
	}

	public BufferedImage getUnbreakableTile() {
		return mUnbreakableTile;
	}

	public BufferedImage getGroundTile() {
		return mGroundTile;
	}
	
	public BufferedImage[] getBomb()
	{
		return mBomb;
	}
	
	public BufferedImage[] getExplosion()
	{
		return mExplosion;
	}
	
	public Font getFontCalibri()
	{
		return mFontCalibri;
	}
	
	public ResourceManager()
	{
		sInstance = this;
		init();
	}
	
	public Audio getMusic()
	{
		return mMusic;
	}
	
	public Audio getExplosionSFX()
	{
		return mExplosionSFX;
	}
	
	private void init()
	{
		try{
			mMusic = new Audio("/sfx/music3.wav");
			mExplosionSFX = new Audio("/sfx/explosion.wav");
			
			mFontCalibri = new Font("Calibri", Font.PLAIN, 32);
			
			mRedPlayer[PLAYER_FORWARD_STAND] = ImageIO.read(this.getClass().getResource("/red/red_fs.png"));
			mRedPlayer[PLAYER_FORWARD_WALK1] = ImageIO.read(this.getClass().getResource("/red/red_fw1.png"));
			mRedPlayer[PLAYER_FORWARD_WALK2] = ImageIO.read(this.getClass().getResource("/red/red_fw2.png"));
			mRedPlayer[PLAYER_LEFT_STAND] = ImageIO.read(this.getClass().getResource("/red/red_ls.png"));
			mRedPlayer[PLAYER_LEFT_WALK1] = ImageIO.read(this.getClass().getResource("/red/red_lw1.png"));
			mRedPlayer[PLAYER_LEFT_WALK2] = ImageIO.read(this.getClass().getResource("/red/red_lw2.png"));
			mRedPlayer[PLAYER_RIGHT_STAND] = ImageIO.read(this.getClass().getResource("/red/red_rs.png"));
			mRedPlayer[PLAYER_RIGHT_WALK1] = ImageIO.read(this.getClass().getResource("/red/red_rw1.png"));
			mRedPlayer[PLAYER_RIGHT_WALK2] = ImageIO.read(this.getClass().getResource("/red/red_rw2.png"));
			mRedPlayer[PLAYER_BACK_STAND] = ImageIO.read(this.getClass().getResource("/red/red_bs.png"));
			mRedPlayer[PLAYER_BACK_WALK1] = ImageIO.read(this.getClass().getResource("/red/red_bw1.png"));
			mRedPlayer[PLAYER_BACK_WALK2] = ImageIO.read(this.getClass().getResource("/red/red_bw2.png"));
			
			mGoomba[PLAYER_FORWARD_STAND] = ImageIO.read(this.getClass().getResource("/goomba/goomba_fs.png"));
			mGoomba[PLAYER_FORWARD_WALK1] = ImageIO.read(this.getClass().getResource("/goomba/goomba_fw1.png"));
			mGoomba[PLAYER_FORWARD_WALK2] = ImageIO.read(this.getClass().getResource("/goomba/goomba_fw2.png"));
			mGoomba[PLAYER_LEFT_STAND] = ImageIO.read(this.getClass().getResource("/goomba/goomba_ls.png"));
			mGoomba[PLAYER_LEFT_WALK1] = ImageIO.read(this.getClass().getResource("/goomba/goomba_lw1.png"));
			mGoomba[PLAYER_LEFT_WALK2] = ImageIO.read(this.getClass().getResource("/goomba/goomba_lw2.png"));
			mGoomba[PLAYER_RIGHT_STAND] = ImageIO.read(this.getClass().getResource("/goomba/goomba_rs.png"));
			mGoomba[PLAYER_RIGHT_WALK1] = ImageIO.read(this.getClass().getResource("/goomba/goomba_rw1.png"));
			mGoomba[PLAYER_RIGHT_WALK2] = ImageIO.read(this.getClass().getResource("/goomba/goomba_rw2.png"));
			mGoomba[PLAYER_BACK_STAND] = ImageIO.read(this.getClass().getResource("/goomba/goomba_bs.png"));
			mGoomba[PLAYER_BACK_WALK1] = ImageIO.read(this.getClass().getResource("/goomba/goomba_bw1.png"));
			mGoomba[PLAYER_BACK_WALK2] = ImageIO.read(this.getClass().getResource("/goomba/goomba_bw2.png"));
			
			mBluePlayer[PLAYER_FORWARD_STAND] = ImageIO.read(this.getClass().getResource("/blue/blue_fs.png"));
			mBluePlayer[PLAYER_FORWARD_WALK1] = ImageIO.read(this.getClass().getResource("/blue/blue_fw1.png"));
			mBluePlayer[PLAYER_FORWARD_WALK2] = ImageIO.read(this.getClass().getResource("/blue/blue_fw2.png"));
			mBluePlayer[PLAYER_LEFT_STAND] = ImageIO.read(this.getClass().getResource("/blue/blue_ls.png"));
			mBluePlayer[PLAYER_LEFT_WALK1] = ImageIO.read(this.getClass().getResource("/blue/blue_lw1.png"));
			mBluePlayer[PLAYER_LEFT_WALK2] = ImageIO.read(this.getClass().getResource("/blue/blue_lw2.png"));
			mBluePlayer[PLAYER_RIGHT_STAND] = ImageIO.read(this.getClass().getResource("/blue/blue_rs.png"));
			mBluePlayer[PLAYER_RIGHT_WALK1] = ImageIO.read(this.getClass().getResource("/blue/blue_rw1.png"));
			mBluePlayer[PLAYER_RIGHT_WALK2] = ImageIO.read(this.getClass().getResource("/blue/blue_rw2.png"));
			mBluePlayer[PLAYER_BACK_STAND] = ImageIO.read(this.getClass().getResource("/blue/blue_bs.png"));
			mBluePlayer[PLAYER_BACK_WALK1] = ImageIO.read(this.getClass().getResource("/blue/blue_bw1.png"));
			mBluePlayer[PLAYER_BACK_WALK2] = ImageIO.read(this.getClass().getResource("/blue/blue_bw2.png"));
			
			mBomb[0] = ImageIO.read(this.getClass().getResource("/bomb/bomb1.png"));
			mBomb[1] = ImageIO.read(this.getClass().getResource("/bomb/bomb2.png"));
			mBomb[2] = ImageIO.read(this.getClass().getResource("/bomb/bomb3.png"));
			mBomb[3] = ImageIO.read(this.getClass().getResource("/bomb/bomb4.png"));
			mBomb[4] = ImageIO.read(this.getClass().getResource("/bomb/bomb5.png"));
			mBomb[5] = ImageIO.read(this.getClass().getResource("/bomb/bomb6.png"));
			
			mExplosion[0] = ImageIO.read(this.getClass().getResource("/explosion/explosion1.png"));
			mExplosion[1] = ImageIO.read(this.getClass().getResource("/explosion/explosion2.png"));
			mExplosion[2] = ImageIO.read(this.getClass().getResource("/explosion/explosion3.png"));
			mExplosion[3] = ImageIO.read(this.getClass().getResource("/explosion/explosion4.png"));
			mExplosion[4] = ImageIO.read(this.getClass().getResource("/explosion/explosion5.png"));
			mExplosion[5] = ImageIO.read(this.getClass().getResource("/explosion/explosion6.png"));
			mExplosion[6] = ImageIO.read(this.getClass().getResource("/explosion/explosion7.png"));
			mExplosion[7] = ImageIO.read(this.getClass().getResource("/explosion/explosion8.png"));
			
			for(int i = 0; i < 4; i++)
				mBreakableTiles[i] = ImageIO.read(this.getClass().getResource("/breakable" + (i + 1) + ".png"));
			mUnbreakableTile = ImageIO.read(this.getClass().getResource("/unbreakable.png"));
			mGroundTile = ImageIO.read(this.getClass().getResource("/ground.png"));
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
