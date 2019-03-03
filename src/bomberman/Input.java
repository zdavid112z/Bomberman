package bomberman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

	private static byte[] mKeys;
	
	public static final byte KEY_RELEASED = 0;
	public static final byte KEY_JUST_RELEASED = 1;
	public static final byte KEY_PRESSED = 2;
	public static final byte KEY_JUST_PRESSED = 3;
	
	public Input()
	{
		mKeys = new byte[256];
		for(int i = 0; i < 256; i++)
			mKeys[i] = 0;
	}
	
	public static void update()
	{
		for(int i = 0; i < 256; i++)
		{
			if(mKeys[i] == KEY_JUST_PRESSED)
				mKeys[i] = KEY_PRESSED;
			else if(mKeys[i] == KEY_JUST_RELEASED)
				mKeys[i] = KEY_RELEASED;
		}
	}
	
	public static boolean isKeyDown(int k)
	{
		return (mKeys[k] & 2) != 0;
	}
	
	public static byte getKeyState(int k)
	{
		return mKeys[k];
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		mKeys[arg0.getKeyCode()] = KEY_JUST_PRESSED;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		mKeys[arg0.getKeyCode()] = KEY_JUST_RELEASED;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	
	
}
