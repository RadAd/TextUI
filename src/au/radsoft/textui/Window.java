// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Color;
import au.radsoft.console.Event;
import au.radsoft.console.Buffer;

public class Window
{
	public Window(int ox, int oy, int w, int h)
	{
		ox_ = ox;
		oy_ = oy;
        w_ = w;
        h_ = h;
        invalid_ = true;
	}
    
	protected void draw(Buffer w, int wx, int wy)
	{
        eraseBackground(w, wx, wy);
        invalid_ = false;
	}
    
    protected void eraseBackground(Buffer w, int wx, int wy)
    {
        w.fill(wx, wy, w_, h_, getBackgroundChar(), getForegroundColor(), getBackgroundColor());
    }
	
    protected Color getBackgroundColor()
    {
        return Color.BLUE;
    }
    
    protected Color getForegroundColor()
    {
        return Color.YELLOW;
    }
    
    protected char getBackgroundChar()
    {
        return ' ';
    }
    
	protected void onEvent(final ConsoleState state, Event ev)
	{
        //System.err.println("Window::onEvent " + ev);
        if (ev instanceof Button.Event)
            onEvent(state, (Button.Event) ev);
        else
        {
            Event.Handler h = new Event.Handler() {
                @Override
                public void handle(Event.Key kev)
                {
                    onEvent(state, kev);
                }
                @Override
                public void handle(Event.MouseButton mbev)
                {
                    onEvent(state, mbev);
                }
                @Override
                public void handle(Event.MouseMoved mmev)
                {
                    onEvent(state, mmev);
                }
            };
            ev.handle(h);
        }
	}
        
	public void onEvent(ConsoleState state, Event.Key kev)
	{
	}
        
	public void onEvent(ConsoleState state, Event.MouseButton mbev)
	{
	}
        
	public void onEvent(ConsoleState state, Event.MouseMoved mmev)
	{
	}
    
	public void onEvent(ConsoleState state, Button.Event bev)
	{
	}
    
    public boolean onFocus(ConsoleState state, boolean focus)
    {
        return true;
    }
    
    public void onMouseEnter(ConsoleState state)
    {
    }
    
    public void onMouseLeave(ConsoleState state)
    {
    }
   
    public boolean inside(int x, int y)
    {
        return x >= ox_ && x < (ox_ + w_)
            && y >= oy_ && y < (oy_ + h_);
    }
    
    public boolean getInvalid() { return invalid_; }
	
	public int ox_;
	public int oy_;
	public int w_;
	public int h_;
    protected boolean invalid_;
}
