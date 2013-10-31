// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.CharKey;
import au.radsoft.console.Color;
import au.radsoft.console.Console;
import au.radsoft.console.Event;
import au.radsoft.console.Buffer;

// TODO
// Is there a better way to handle the instanceof?
// Show menu description in status bar

public class MenuPopup extends FrameWindow
{
    private final MenuList menuList_;
    private int sel_ = 0;
    private MenuCommand menuCommand_ = null;
    
    public MenuPopup(MenuList menuList, int ox, int oy)
    {
        super(ox, oy, CalcWidth(menuList), CalcHeight(menuList));
        //System.out.println("MenuPopup " + ox_ + " " + oy_ + " " + GetTitle());
        menuList_ = menuList;
        //SetTitle(menuList_.GetVerb());
    }
    
    private static int CalcWidth(MenuList menuList)
    {
        int w = menuList.GetVerb().length() + 2;
        for (MenuItem mi : menuList.GetChildren())
        {
            String s = mi.GetVerb();
            if (mi instanceof MenuList)
                w = Math.max(w, s.length() + 2);
            else
                w = Math.max(w, s.length());
        }
        return w;
    }
    
    private static int CalcHeight(MenuList menuList)
    {
        return menuList.GetChildren().length;
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
    
    protected Color getHighlightColor()
    {
        return Color.GREEN;
    }
    
    protected Color getHighlightTextColor()
    {
        return Color.BLACK;
    }
    
    protected Color getDisabledTextColor()
    {
        return Color.GRAY;
    }
    
    @Override
    public void drawFrame(Buffer w, int wx, int wy)
    {
        drawSquareSingle(w, wx - 1, wy - 1, w_ + 2, h_ + 2, getFrameColor(), getBackgroundColor());
    }
    
    @Override
    protected void draw(Buffer w, int wx, int wy)
    {
        //System.err.println("MenuPopup::Draw");
        super.draw(w, wx, wy);
        int x = 0;
        int y = 0;
        int i = 0;
        for (MenuItem mi : menuList_.GetChildren())
        {
            String s = mi.GetVerb();
            Color fg = i == sel_ ? getHighlightTextColor() : getForegroundColor();
            Color bg = i == sel_ ? getHighlightColor() : getBackgroundColor();
            if (mi instanceof MenuCommand)
            {
                MenuCommand mc = (MenuCommand) mi;
                if (!mc.IsEnabled())
                {
                    fg = getDisabledTextColor();
                }
            }
            w.fill(x + wx, y + wy, w_, 1, fg, bg);
            w.write(x + wx, y + wy, s);
            if (mi instanceof MenuList)
                w.write(x + wx + w_ - 1, y + wy, (char) 16);
            y += 1;
            ++i;
        }
        drawFrame(w, wx, wy);
    }
    
    @Override
    public void onEvent(ConsoleState state, Event.MouseButton mbev)
    {
        super.onEvent(state, mbev);
        //System.err.println("MenuPopup OnEvent " + mbev);
        
        if (mbev.state == Event.State.PRESSED
            && mbev.key == CharKey.MOUSE_BUTTON1)
        {
            //state.setCapture(this);
            sel_ = get(mbev.my);
            invalid_ = true;
        }
        else if (mbev.state == Event.State.RELEASED
            && mbev.key == CharKey.MOUSE_BUTTON1)
        {
            //state.setCapture(null);
            if (inside(mbev.mx + ox_, mbev.my + oy_))
            {
                if (sel_ >= 0 && sel_ == get(mbev.my))
                {
                    select(state);
                }
                sel_ = -1;
                invalid_ = true;
            }
            else
                state.exit();
        }
    }
    
    @Override
	public void onEvent(ConsoleState state, Event.MouseMoved mmev)
	{
        super.onEvent(state, mmev);
        //System.err.println("MenuPopup OnEvent " + mmev);
        
        if (state.getKeyDown(CharKey.MOUSE_BUTTON1))
        {
            if (inside(mmev.mx + ox_, mmev.my + oy_))
                sel_ = get(mmev.my);
            else
                sel_ = -1;
            invalid_ = true;
        }
	}
    
    @Override
    public void onEvent(ConsoleState state, Event.Key kev)
    {
        super.onEvent(state, kev);
        
        if (kev.state == Event.State.RELEASED)
        {
            switch (kev.key)
            {
            case UP:
                --sel_;
                if (sel_ < 0)
                    sel_ = menuList_.GetChildren().length - 1;
                invalid_ = true;
                break;
                
            case DOWN:
                ++sel_;
                if (sel_ >= menuList_.GetChildren().length)
                    sel_ = 0;
                invalid_ = true;
                break;
                
            case RIGHT:
            case ENTER:
                select(state);
                break;
                
            case LEFT:
            case ESCAPE:
                //System.err.println("menu escape");
                state.exit();
                break;
            }
        }
    }
    
    private void select(ConsoleState state)
    {
        Console console = state.GetConsole();
        try
        {
            MenuItem mi = menuList_.GetChildren()[sel_];
            if (mi instanceof MenuCommand)
            {
                MenuCommand mc = (MenuCommand) mi;
                if (mc.IsEnabled())
                {
                    menuCommand_ = mc;
                    //mc.DoCommand(console);
                    state.result(1);
                    state.exit();
                }
            }
            else if (mi instanceof MenuList)
            {
                MenuList ml = (MenuList) mi;
                menuCommand_ = ml.domenu(console, ox_ + w_ + 1, oy_ + sel_);
                //System.err.println("MenuPopup menuCommand_ " + menuCommand_);
                if (menuCommand_ != null)
                {
                    state.result(1);
                    state.exit();
                }
            }
        }
        catch (java.lang.reflect.UndeclaredThrowableException ex)
        {
            Throwable t = ex.getCause();
            //System.err.println("MenuPopup OnEvent");
            ex.printStackTrace();
            MsgBox.domodal(console, "Error", t.toString(), console.getWidth()/2);
        }
        catch (Exception e)
        {
            //System.err.println("MenuPopup OnEvent");
            e.printStackTrace();
            MsgBox.domodal(console, "Error", e.toString(), console.getWidth()/2);
        }
    }
    
    private int get(int y)
    {
        if (y >= 0 && y < menuList_.GetChildren().length)
            return y;
        else
            return -1;
    }
    
    MenuCommand get()
    {
        return menuCommand_;
    }
}
