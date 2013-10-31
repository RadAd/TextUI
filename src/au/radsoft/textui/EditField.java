// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.CharKey;
import au.radsoft.console.Color;
import au.radsoft.console.Event;
import au.radsoft.console.Buffer;

public class EditField extends Window
{
    private final StringBuffer value_;
    
    public EditField(int x, int y, String value, int length)
    {
        super(x, y, length, 1);
        if (value != null)
            value_ = new StringBuffer(value);
        else
            value_ = new StringBuffer();
    }
    
    @Override
    protected Color getBackgroundColor()
    {
        return Color.BLACK;
    }
    
    @Override
    protected Color getForegroundColor()
    {
        return Color.WHITE;
    }

    @Override
    protected void draw(Buffer w, int wx, int wy)
    {
        super.draw(w, wx, wy);
        w.write(wx, wy, getValue());    // TODO Need someway of setting cursor pos
    }
    
    @Override
    public void onEvent(ConsoleState state, Event.Key kev)
    {
        super.onEvent(state, kev);
        
        if (kev.state == Event.State.RELEASED)
        {
            switch (kev.key)
            {
            case ENTER:
            case TAB:
                break;
                
            case BACK_SPACE:
                if (value_.length() > 0)
                {
                    invalid_ = true;
                    /*value_ =*/ value_.deleteCharAt(value_.length() - 1);
                    state.setCursor(ox_ + value_.length(), oy_);
                }
                break;
                
            default:
                if (kev.key.c != '\0' && value_.length() < w_)
                {
                    invalid_ = true;
                    // TODO Process CAPS_LOCK properly
                    if (state.getKeyDown(CharKey.SHIFT) || state.getKeyDown(CharKey.CAPS_LOCK))
                        value_.append(kev.key.c);
                    else
                        value_.append(Character.toLowerCase(kev.key.c));
                    state.setCursor(ox_ + value_.length(), oy_);
                }
                break;
            }
        }
    }
    
    @Override
    public boolean onFocus(ConsoleState state, boolean focus)
    {
        boolean ret = super.onFocus(state, focus);
        if (ret && state != null)
        {
            state.showCursor(focus);
            if (focus)
            {
                //System.out.println("EditField OnFocus " + ox_ + " " + oy_);
                state.setCursor(ox_ + value_.length(), oy_);
            }
        }
        return ret;
    }
    
    public String getValue()
    {
        return value_.toString();
    }
}
