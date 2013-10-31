// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Color;
import au.radsoft.console.Console;
import au.radsoft.console.Buffer;
import au.radsoft.console.Event;

public class Dialog extends FrameWindow
{
    private Button defButton_ = null;
    
	public Dialog(int ox, int oy, int w, int h)
	{
        super(ox, oy, w, h);
	}
    
    public void centre(Console console)
    {
        ox_ = (console.getWidth() - w_)/2;
        oy_ = (console.getHeight() - h_)/2;
    }
    
    public void add(Button child)
    {
        if (defButton_ == null)
            defButton_ = child;
        super.add(child);
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
    
    @Override
    public void onEvent(ConsoleState state, Event.Key kev)
    {
        super.onEvent(state, kev);
        
        switch (kev.key)
        {
        case ENTER:
            if (defButton_ != null)
            {
                defButton_.onEvent(state, kev);
            }
            break;
            
        case ESCAPE:
            if (kev.state == Event.State.RELEASED)
            {
                state.exit();
            }
            break;
        }
    }
    
    public int domodal(Console console)
    {
        return ConsoleState.doeventloop(console, this, true);
    }
}
