// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Buffer;
import au.radsoft.console.CharKey;
import au.radsoft.console.Color;
import au.radsoft.console.Event;

public class ScrollFrameWindow extends FrameWindow
{
    protected int sx_ = 0;
    protected int sy_ = 0;
    protected int sw_ = 0;
    protected int sh_ = 0;
    
	public ScrollFrameWindow(int ox, int oy, int w, int h)
	{
        super(ox, oy, w, h);
	}
    
    @Override
	protected void draw(Buffer w, int wx, int wy)
	{
        super.draw(w, wx, wy);
        if (hasHScrollbar())
            drawHScrollbar(w, wx, wy);
        if (hasVScrollbar())
            drawVScrollbar(w, wx, wy);
    }
    
    public boolean inScrollView(int x, int y)
    {
        return (x >= sx_ && x < (sx_ + w_) && y >= sy_ && y < (sy_ + h_));
    }
    
    public int getScrollX(int x)
    {   // Translates x to screen coordinates
        return x - sx_;
    }
    
    public int getScrollY(int y)
    {   // Translates y to screen coordinates
        return y - sy_;
    }
    
    @Override
	public void onEvent(ConsoleState state, Event.MouseButton mbev)
	{
        super.onEvent(state, mbev);
        //System.out.println("ScrollWindowTest:onEvent " + mbev);
        //System.out.println("ScrollWindowTest:onEvent2 " + ox_ + ", " + oy_ + " " + (ox_ + w_) + ", " + (oy_ + h_));
        if (mbev.key == CharKey.MOUSE_BUTTON1 && mbev.state == Event.State.RELEASED)
        {
            if (mbev.my == (oy_ + h_))
            {   // Horizontal scrollbar
                if (mbev.mx == (ox_ + w_ - 1))
                {
                    ++sx_;
                    if (sx_ > (sw_ - w_))
                        sx_ = sw_ - w_;
                    invalid_ = true;
                }
                else if (mbev.mx == ox_)
                {
                    --sx_;
                    if (sx_ < 0)
                        sx_ = 0;
                    invalid_ = true;
                }
            }
            else if (mbev.mx == (ox_ + w_))
            {   // Veritcal scrollbar
                if (mbev.my == (oy_ + h_ - 1))
                {
                    ++sy_;
                    if (sy_ > (sh_ - h_))
                        sy_ = sh_ - h_;
                    invalid_ = true;
                }
                else if (mbev.my == oy_)
                {
                    --sy_;
                    if (sy_ < 0)
                        sy_ = 0;
                    invalid_ = true;
                }
            }
        }
	}
    
    public boolean hasHScrollbar()
    {
        return sw_ > w_;
    }
    
    public boolean hasVScrollbar()
    {
        return sh_ > h_;
    }
    
    void drawHScrollbar(Buffer w, int wx, int wy)
    {
        int sbw = w_ - 2;
        w.write(wx, wy + h_, (char) 17, Color.BLACK, Color.WHITE);
        w.write(wx + w_ - 1, wy + h_, (char) 16, Color.BLACK, Color.WHITE);
        w.fill(wx + 1, wy + h_, sbw, 1, (char) 177, Color.WHITE, Color.BLACK);
        
        int ssw = w_ * sbw / sw_;
        int sso = sx_ * (sbw - ssw) / (sw_ - w_);
        w.fill(wx + 1 + sso, wy + h_, ssw, 1, (char) 219, Color.WHITE, Color.BLACK);
    }
    
    void drawVScrollbar(Buffer w, int wx, int wy)
    {
        int sbh = h_ - 2;
        w.write(wx + w_, wy, (char) 30, Color.BLACK, Color.WHITE);
        w.write(wx + w_, wy + h_ - 1, (char) 31, Color.BLACK, Color.WHITE);
        w.fill(wx + w_, wy + 1, 1, sbh, (char) 177, Color.WHITE, Color.BLACK);
        
        int ssh = h_ * sbh / sh_;
        int sso = sy_ * (sbh - ssh) / (sh_ - h_);
        w.fill(wx + w_, wy + 1 + sso, 1, ssh, (char) 219, Color.WHITE, Color.BLACK);
    }
}
