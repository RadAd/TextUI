// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Console;

public class MenuList implements MenuItem
{
    private final String verb_;
    private final MenuItem[] children_;
    
    public MenuList(String verb, MenuItem... mc)
    {
        verb_ = verb;
        children_ = mc;
    }

    public String GetVerb()
    {
        return verb_;
    }
    
    public MenuItem[] GetChildren()
    {
        return children_;
    }
    
    MenuCommand domenu(Console console, int x, int y)
    {
        //System.err.println("+MenuList domenu");
        MenuPopup mp = new MenuPopup(this, x, y);
        if (ConsoleState.doeventloop(console, mp, true) == 0)
        {
            //System.err.println("-MenuList domenu");
            return null;
        }
        else
        {
            //System.err.println("-MenuList domenu");
            return mp.get();
        }
    }
}
