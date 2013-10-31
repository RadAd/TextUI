// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Buffer;
import au.radsoft.console.CharKey;
import au.radsoft.console.Console;
import au.radsoft.console.Event;

// TODO
// This class needs a better name
// Should result be an object???

public class ConsoleState
{
    private Console console_;
    private int wx_;
    private int wy_;
    private WindowChildren modal_;
    private boolean exit_ = false;
    private int result_ = 0;
    
    public ConsoleState(Console console, int wx, int wy, WindowChildren modal)
    {
        console_ = console;
        wx_ = wx;
        wy_ = wy;
        modal_ = modal;
    }
    
    public Console GetConsole()
    {
        return console_;
    }
    
    public boolean GetExit()
    {
        return exit_;
    }
    
    public int GetResult()
    {
        return result_;
    }
    
    public void exit()
    {
        exit_ = true;
    }
    
    public void result(int result)
    {
        result_ = result;
    }
    
    public boolean getKeyDown(CharKey key)
    {
        return console_.getKeyDown(key);
    }
    
    public void setCapture(Window child)
    {
        modal_.setCapture(child);
    }
    
    public void showCursor(boolean show)
    {
        console_.showCursor(show);
    }

    public void setCursor(int x, int y)
    {
        console_.setCursor(modal_.ox_ + x, modal_.oy_ + y);
    }
    
    public void sendEvent(Event ev)
    {
        modal_.onEvent(this, ev);
    }

    public static int doeventloop(Console console, WindowChildren modal, boolean frame)
    {
        //System.out.println("DialogModal domodal " + ox_ + " " + oy_ + " " + GetTitle());
        int offset = frame ? 1 : 0;
        int buffer = frame ? 2 : 0;
        Buffer b = new Buffer(modal.w_ + buffer, modal.h_ + buffer);
        console.read(modal.ox_, modal.oy_, b);
        Buffer w = new Buffer(modal.w_ + buffer, modal.h_ + buffer);
        //long b = System.currentTimeMillis();
        ConsoleState state = new ConsoleState(console, offset, offset, modal);
        modal.onFocus(state, true);
        while (!state.GetExit() && console.isValid())
        {
            if (modal.getInvalid())
                modal.draw(w, offset, offset);

            console.write(modal.ox_ - offset, modal.oy_ - offset, w);
            
            Event ev = console.getEvent();

            while (ev != null)
            {
                if (ev instanceof Event.MouseButton)
                {
                    Event.MouseButton mbev = (Event.MouseButton) ev;
                    ev = new Event.MouseButton(mbev.key, mbev.state, mbev.mx - modal.ox_, mbev.my - modal.oy_);
                }
                else if (ev instanceof Event.MouseMoved)
                {
                    Event.MouseMoved mmev = (Event.MouseMoved) ev;
                    ev = new Event.MouseMoved(mmev.mx - modal.ox_, mmev.my - modal.oy_);
                }
                //System.out.println("DialogModal domodal " + ev);
                modal.onEvent(state, ev);
                ev = console.getEventNoWait();
            }
        }
        modal.onFocus(state, false);
        console.write(modal.ox_ - offset, modal.oy_ - offset, b);
        return state.GetResult();
    }
}
