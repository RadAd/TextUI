// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.CharKey;
import au.radsoft.console.Color;
import au.radsoft.console.Event;
import au.radsoft.console.Buffer;

// TODO
// Support mouse move before to cancel button press
// Show focus indicator

public class Button extends Window
{
    private final String text_;
    private boolean pressed_ = false;
    
    public class Event implements au.radsoft.console.Event
    {
        public final Button button;
        
        Event(Button button)
        {
            this.button = button;
        }
        
        @Override
        public void handle(Handler h)
        {
            // TODO h.handle(this);
        }
    }
    
    public Button(int x, int y, String text)
    {
        super(x, y, Math.max(text.length() + 2, 10), 1);
        text_ = text;
    }
    
    @Override
    protected Color getBackgroundColor()
    {
        return Color.GREEN;
    }
    
    @Override
    protected Color getForegroundColor()
    {
        if (pressed_)
            return Color.WHITE;
        else
            return Color.BLACK;
    }

    @Override
    protected void draw(Buffer w, int wx, int wy)
    {
        super.draw(w, wx, wy);
        w.write(wx + 1 + (w_ - text_.length() - 1) / 2, wy, text_);
    }
    
    @Override
    public void onEvent(ConsoleState state, Event.MouseButton mbev)
    {
        super.onEvent(state, mbev);
        //System.err.println("Button::OnEvent " + mbev);
        
        if (mbev.state == Event.State.PRESSED
            && mbev.key == CharKey.MOUSE_BUTTON1)
        {
            state.setCapture(this);
            pressed_ = true;
            invalid_ = true;
        }
        else if (mbev.state == Event.State.RELEASED
            && mbev.key == CharKey.MOUSE_BUTTON1)
        {
            state.setCapture(null);
            pressed_ = false;
            invalid_ = true;
            pressed(state);
        }
    }
    
    @Override
    public void onEvent(ConsoleState state, Event.Key kev)
    {
        super.onEvent(state, kev);
        //System.err.println("Button::OnEvent " + kev);
        
        switch (kev.key)
        {
        case ENTER:
        case SPACE:
            switch (kev.state)
            {
            case PRESSED:
                pressed_ = true;
                invalid_ = true;
                break;
                
                
            case RELEASED:
                pressed_ = false;
                invalid_ = true;
                pressed(state);
                break;
            }
            break;
            
        case ESCAPE:
            pressed_ = false;
            invalid_ = true;
            break;
        }
    }
    
    void pressed(ConsoleState state)
    {
        state.sendEvent(new Event(this));
    }
}
