// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Color;
import au.radsoft.console.Console;
import au.radsoft.console.Buffer;

public class FrameWindow extends WindowChildren
{
	public FrameWindow(int ox, int oy, int w, int h)
	{
        super(ox, oy, w, h);
	}
    
    public void setTitle(String title)
    {
        title_ = title;
    }
    
    public String getTitle()
    {
        return title_;
    }
	
    @Override
	protected void draw(Buffer w, int wx, int wy)
	{
        super.draw(w, wx, wy);
        if (title_ != null)
        {
            drawFrame(w, wx, wy);
            drawTitle(w, wx, wy, title_);
        }
	}

    @Override
    public boolean inside(int x, int y)
    {
        return x >= (ox_ - 1) && x < (ox_ + w_ + 1)
            && y >= (oy_ - 1) && y < (oy_ + h_ + 1);
    }
    
    public boolean insideClientArea(int x, int y)
    {
        return x >= ox_ && x < (ox_ + w_)
            && y >= oy_ && y < (oy_ + h_);
    }
    
    protected Color getFrameColor()
    {
        return Color.WHITE;
    }
    
    public void drawFrame(Buffer w, int wx, int wy)
    {
        drawSquareDouble(w, wx - 1, wy - 1, w_ + 2, h_ + 2, getFrameColor(), getBackgroundColor());
    }
    
    public void drawTitle(Buffer w, int wx, int wy, String title)
    {
        w.write(wx, wy -  1, "[" + title + "]", getFrameColor(), getBackgroundColor());
    }
    
    private String title_;
    
    public static void drawSquareSingle(Buffer w, int sx, int sy, int sw, int sh, Color fg, Color bg)
    {
        int ex = sx + sw - 1;
        int ey = sy + sh - 1;
        for (int x = sx; x < ex; ++x)
        {
            w.write(x, sy, (char) 196, fg, bg);
            w.write(x, ey, (char) 196, fg, bg);
        }
        for (int y = sy; y < ey; ++y)
        {
            w.write(sx, y, (char) 179, fg, bg);
            w.write(ex, y, (char) 179, fg, bg);
        }
        w.write(sx, sy, (char) 218, fg, bg);
        w.write(ex, sy, (char) 191, fg, bg);
        w.write(sx, ey, (char) 192, fg, bg);
        w.write(ex, ey, (char) 217, fg, bg);
    }
    
    public static void drawSquareDouble(Buffer w, int sx, int sy, int sw, int sh, Color fg, Color bg)
    {
        int ex = sx + sw - 1;
        int ey = sy + sh - 1;
        for (int x = sx; x < ex; ++x)
        {
            w.write(x, sy, (char) 205, fg, bg);
            w.write(x, ey, (char) 205, fg, bg);
        }
        for (int y = sy; y < ey; ++y)
        {
            w.write(sx, y, (char) 186, fg, bg);
            w.write(ex, y, (char) 186, fg, bg);
        }
        w.write(sx, sy, (char) 201, fg, bg);
        w.write(ex, sy, (char) 187, fg, bg);
        w.write(sx, ey, (char) 200, fg, bg);
        w.write(ex, ey, (char) 188, fg, bg);
    }
}
