package bomberman;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Renderer {

	public static abstract class DrawCall
	{
		public float z;
		public abstract void render(Graphics g);
	};
	
	public static class DrawCallImage extends DrawCall
	{
		BufferedImage image;
		int x, y, w, h;
		
		public void render(Graphics g)
		{
			if(w == 0)
				g.drawImage(image, x, y, null);
			else g.drawImage(image, x, y, w, h, null);
		}
	};
	
	public static class DrawCallText extends DrawCall
	{
		String text;
		int x, y;
		Font font;
		Color color;
		
		@Override
		public void render(Graphics g) {
			g.setColor(color);
			g.setFont(font);
			g.drawString(text, x, y);
		}
		
	};
	
	public static class DrawCallRect extends DrawCall
	{
		Color color;
		int x, y, w, h;
		
		public void render(Graphics g)
		{
			g.setColor(color);
			g.fillRect(x, y, w, h);
		}
	};
	
	protected final int mWidth, mHeight;
	private Graphics mGraphics;
	private List<DrawCall> mDrawCalls = new ArrayList<DrawCall>();
	private Comparator<DrawCall> mDrawCallComparator;
	
	public Renderer(int w, int h)
	{
		mWidth = w;
		mHeight = h;
		mDrawCallComparator = new Comparator<DrawCall>()
		{

			@Override
			public int compare(DrawCall a, DrawCall b) {
				if(a.z - b.z > 0)
					return 1;
				else if(a.z - b.z < 0)
					return -1;
				return 0;
			}
			
		};
	}
	
	public void begin(Graphics g)
	{
		mDrawCalls.clear();
		mGraphics = g;
	}
	
	public void end()
	{
		mDrawCalls.sort(mDrawCallComparator);
		for(int i = 0; i < mDrawCalls.size(); i++)
			mDrawCalls.get(i).render(mGraphics);
	}
	
	public Graphics getGraphics()
	{
		return mGraphics;
	}
	
	public void drawImageNow(BufferedImage image, int x, int y)
	{
		mGraphics.drawImage(image, x, y, null);
	}

	public void drawImageNow(BufferedImage image, int x, int y, int w, int h)
	{
		mGraphics.drawImage(image, x, y, w, h, null);
	}
	
	public void fillRectNow(Color color, int x, int y, int w, int h)
	{
		mGraphics.setColor(color);
		mGraphics.fillRect(x, y, w, h);
	}
	
	public void drawStringNow(String text, Color color, Font font, int x, int y)
	{
		mGraphics.setColor(color);
		mGraphics.setFont(font);
		mGraphics.drawString(text, x, y);
	}
	
	public Point getFontMetrics(String text, Font font)
	{
		FontMetrics fm = mGraphics.getFontMetrics(font);
		Point p = new Point();
		p.x = fm.stringWidth(text);
		p.y = fm.getHeight();
		return p;
	}
	
	public void drawString(String text, Color color, Font font, int x, int y, int z)
	{
		DrawCallText i = new DrawCallText();
		i.font = font;
		i.text = text;
		i.color = color;
		i.x = x;
		i.y = y;
		i.z = z;
		mDrawCalls.add(i);
	}
	
	public void drawImage(BufferedImage image, int x, int y, float z)
	{
		DrawCallImage i = new DrawCallImage();
		i.image = image;
		i.x = x;
		i.y = y;
		i.w = 0;
		i.h = 0;
		i.z = z;
		mDrawCalls.add(i);
	}
	
	public void drawImage(BufferedImage image, int x, int y, int w, int h, float z)
	{
		DrawCallImage i = new DrawCallImage();
		i.image = image;
		i.x = x;
		i.y = y;
		i.w = w;
		i.h = h;
		i.z = z;
		mDrawCalls.add(i);
	}
	
	public void fillRect(Color color, int x, int y, int w, int h, float z)
	{
		DrawCallRect i = new DrawCallRect();
		i.color = color;
		i.x = x;
		i.y = y;
		i.w = w;
		i.h = h;
		i.z = z;
		mDrawCalls.add(i);
	}
	
	public void fillRectBorder(Color color, int x, int y, int w, int h, float z, Color borderColor, int thickness)
	{
		fillRect(color, x, y, w, h, z);
		fillRect(borderColor, x - thickness, y - thickness, w + thickness * 2, thickness * 2, z);
		fillRect(borderColor, x - thickness, y - thickness, thickness * 2, h + thickness * 2, z);
		fillRect(borderColor, x + w - thickness, y - thickness, thickness * 2, h + thickness * 2, z);
		fillRect(borderColor, x - thickness, y + h - thickness, w + thickness * 2, thickness * 2, z);
	}
	
}
