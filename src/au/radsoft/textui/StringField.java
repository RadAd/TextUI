// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Buffer;

public class StringField extends Window
{
    private String value_;
    
    public StringField(int x, int y, String value)
    {
        super(x, y, value.length(), 1);
        value_ = value;
    }
    
    @Override
    protected void eraseBackground(Buffer w, int wx, int wy)
    {
    }

    @Override
    protected void draw(Buffer w, int wx, int wy)
    {
        super.draw(w, wx, wy);
        w.write(wx, wy, value_);
    }
    
    @Override
    public boolean onFocus(ConsoleState state, boolean focus)
    {
        boolean ret = super.onFocus(state, focus);
        if (focus)
            ret = false;
        return ret;
    }
}
