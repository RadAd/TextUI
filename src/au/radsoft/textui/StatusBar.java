// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Buffer;
import au.radsoft.console.Color;

public class StatusBar extends Window
{
    private String status_;
    
    public StatusBar(int ox, int oy, int w)
    {
        super(ox, oy, w, 1);
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
    protected void draw(Buffer w, int wx, int wy)
    {
        //System.err.println("MainMenu::Draw");
        super.draw(w, wx, wy);
        if (status_ != null)
            w.write(wx, wy, status_);
    }
    
    public void set(String s)
    {
        status_ = s;
        invalid_ = true;
    }
}
