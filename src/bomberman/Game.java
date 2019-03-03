package bomberman;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	public static final int WIDTH = Tile.TILE_SIZE * Tile.TILE_MAP_WIDTH;
	public static final int HEIGHT = Tile.TILE_SIZE * Tile.TILE_MAP_HEIGHT;
	public static final int UPS = 90;
	public static Game MainGame;

	private static final long serialVersionUID = 1L;
	private boolean mRunning = false;
	private Renderer mRenderer;
	private ResourceManager mResManager;
	private Thread mThread;
	private JFrame mFrame;
	private Input mInput;
	private Scene mScene;
	
	private long mTimer;
	
	public ResourceManager getResourceManager()
	{
		return mResManager;
	}
	
	public Renderer getRenderer()
	{
		return mRenderer;
	}
	
	public Scene getScene()
	{
		return mScene;
	}
	
	public void loadScene(Scene s)
	{
		mScene = s;
	}

	public static void main(String[] args) {
		MainGame = new Game();
		MainGame.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainGame.mFrame.setTitle("Bomberman");
		MainGame.mFrame.setVisible(true);
		MainGame.mFrame.setEnabled(true);
		MainGame.mFrame.setResizable(false);
		MainGame.mFrame.add(MainGame);
		MainGame.mFrame.pack();
		MainGame.mFrame.setLocationRelativeTo(null);
		MainGame.requestFocusInWindow();
		
		MainGame.start();
	}
	
	public Game()
	{
		mFrame = new JFrame();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		mRenderer = new Renderer(WIDTH, HEIGHT);
		mInput = new Input();
		addKeyListener(mInput);
	}
	
	public void start()
	{
		if(mRunning)
			return;
		mRunning = true;
		mThread = new Thread(this, "display");
		mThread.start();
	}

	public void stop()
	{
		if(!mRunning)
			return;
		mRunning = false;
		try {
			mThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		mResManager = new ResourceManager();
		Tile.init(mResManager);
		mScene = new Scene(1);
		
		mTimer = System.nanoTime();
		int interval = 1000000000 / UPS;
		
		while(mRunning)
		{
			long t = System.nanoTime();
			while(t - mTimer >= interval)
			{
				mTimer += interval;
				update();
				render();
				//System.out.println(1.0f / (((float)(System.nanoTime() - t) / 1000000) / 1000.0f));
			}
		}
		
	}

	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		mRenderer.begin(g);
		mScene.render(mRenderer);
		mRenderer.end();
		g.dispose();
		bs.show();
	}
	
	public void update()
	{
		mScene.update();	
		Input.update();
	}
	
}
