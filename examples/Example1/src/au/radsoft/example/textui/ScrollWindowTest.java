// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.example.textui;

import au.radsoft.console.Buffer;

import au.radsoft.textui.ConsoleState;
import au.radsoft.textui.ScrollFrameWindow;

class ScrollWindowTest extends ScrollFrameWindow
{
	public ScrollWindowTest(int ox, int oy, int w, int h)
	{
        super(ox, oy, w, h);
        sw_ = 20;
        sh_ = 20;
	}
    
    @Override
	protected void draw(Buffer w, int wx, int wy)
	{
        super.draw(w, wx, wy);
        drawScroll(w, wx, wy);
    }
    
    void drawScroll(Buffer w, int wx, int wy)
    {
        for (int y = 0; y < sh_; ++y)
        {
            for (int x = 0; x < sw_; ++x)
            {
                if (inScrollView(x, y))
                {
                    w.write(wx + getScrollX(x), wy + getScrollY(y), (char) ('A' + y + x));
                }
            }
        }
    }
}
