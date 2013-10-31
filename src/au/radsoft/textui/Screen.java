// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Console;
import au.radsoft.console.Color;

public class Screen extends WindowChildren
{
    private StatusBar status_;
    
    public Screen(Console console)
    {
        super(0, 0, console.getWidth(), console.getHeight());
    }
    
    @Override
    protected Color getBackgroundColor()
    {
        return Color.BLACK;
    }
    
    @Override
    protected Color getForegroundColor()
    {
        return Color.LIGHT_GRAY;
    }
    
    @Override
    protected char getBackgroundChar()
    {
        return 177;
    }
    
    public void setMenu(MenuList ml)
    {
        add(new MenuBar(ml, 0, 0, w_));
        // TODO have a variable for menu bar
    }
    
    public StatusBar getStatusBar()
    {
        if (status_ == null)
            add(status_ = new StatusBar(0, h_ - 1, w_));
        return status_;
    }
    
    public void setStatus(String s)
    {
        StatusBar sb = getStatusBar();
        sb.set(s);
    }
    
    public int doeventloop(Console console)
    {
        return ConsoleState.doeventloop(console, this, false);
    }
}
