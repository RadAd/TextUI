// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Color;
import au.radsoft.console.Console;
import au.radsoft.console.Buffer;

public class ToolWindow extends FrameWindow
{
	public ToolWindow(int ox, int oy, int w, int h)
	{
        super(ox, oy, w, h);
	}
    
    @Override
    protected Color getBackgroundColor()
    {
        return Color.LIGHT_GRAY;
    }
    
    @Override
    protected Color getForegroundColor()
    {
        return Color.BLACK;
    }
    
    @Override
    protected Color getFrameColor()
    {
        return Color.BLACK;
    }
    
    @Override
    public void drawFrame(Buffer w, int wx, int wy)
    {
        drawSquareSingle(w, wx - 1, wy - 1, w_ + 2, h_ + 2, getFrameColor(), getBackgroundColor());
    }
}
