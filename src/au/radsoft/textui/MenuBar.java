// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Buffer;
import au.radsoft.console.CharKey;
import au.radsoft.console.Color;
import au.radsoft.console.Console;
import au.radsoft.console.Event;

// TODO
// Support mouse move to select another menu item or to cancel

public class MenuBar extends Window
{
    private MenuList menuList_;
    private int sel_ = -1;
    
    public MenuBar(MenuList menuList, int ox, int oy, int w)
    {
        super(ox, oy, w, 1);
        menuList_ = menuList;
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
    
    protected Color getHighlightColor()
    {
        return Color.GREEN;
    }
    
    protected Color getHighlightTextColor()
    {
        return Color.BLACK;
    }
    
    @Override
    protected void draw(Buffer w, int wx, int wy)
    {
        //System.err.println("MainMenu::Draw");
        super.draw(w, wx, wy);
        int x = 0;
        int y = 0;
        int i = 0;
        for (MenuItem mi : menuList_.GetChildren())
        {
            String s = mi.GetVerb();
            if (i == sel_)
            {
                w.fill(x + wx, y + wy, s.length() + 2, 1, getHighlightTextColor(), getHighlightColor());
            }
            w.write(x + wx + 1, y + wy, s);
            x += s.length() + 2;
            ++i;
        }
    }
    
    @Override
    public void onEvent(ConsoleState state, Event.MouseButton mbev)
    {
        super.onEvent(state, mbev);
        
        if (mbev.state == Event.State.PRESSED
            && mbev.key == CharKey.MOUSE_BUTTON1)
        {
            state.setCapture(this);
            sel_ = get(mbev.mx - ox_);
            invalid_ = true;
        }
        else if (mbev.state == Event.State.RELEASED
            && mbev.key == CharKey.MOUSE_BUTTON1)
        {
            state.setCapture(null);
            if (sel_ >= 0 && sel_ == get(mbev.mx - ox_))
            {
                select(state);
            }
            sel_ = -1;
            invalid_ = true;
        }
    }
    
    private void select(ConsoleState state)
    {
        Console console = state.GetConsole();
        try
        {
            MenuItem mi = menuList_.GetChildren()[sel_];
            MenuCommand mc = null;
            if (mi instanceof MenuCommand)
            {
                mc = (MenuCommand) mi;
            }
            else if (mi instanceof MenuList)
            {
                MenuList ml = (MenuList) mi;
                mc = ml.domenu(console, ox_ + getx(sel_) + 1, oy_ + 2);
            }
            
            if (mc != null)
            {
                mc.DoCommand(state);
            }
        }
        catch (java.lang.reflect.UndeclaredThrowableException ex)
        {
            Throwable t = ex.getCause();
            //System.err.println("MenuBar OnEvent");
            ex.printStackTrace();
            MsgBox.domodal(console, "Error", t.toString(), console.getWidth()/2);
        }
        catch (Exception e)
        {
            //System.err.println("MenuBar OnEvent");
            e.printStackTrace();
            MsgBox.domodal(console, "Error", e.toString(), console.getWidth()/2);
        }
    }
    
    private int get(int x)
    {
        int sel = 0;
        while (sel < menuList_.GetChildren().length && x >= 0)
        {
            MenuItem mi = menuList_.GetChildren()[sel];
            String s = mi.GetVerb();
            if (x < s.length() + 2)
                return sel;
            x -= s.length() + 2;
            ++sel;
        }
        return -1;
    }
    
    private int getx(int i)
    {
        int x = 0;
        while (i < menuList_.GetChildren().length && i > 0)
        {
            MenuItem mi = menuList_.GetChildren()[i];
            String s = mi.GetVerb();
            x += s.length() + 2;
            --i;
        }
        return x;
    }
}
