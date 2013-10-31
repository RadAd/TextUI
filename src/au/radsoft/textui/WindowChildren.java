// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Console;
import au.radsoft.console.Event;
import au.radsoft.console.Buffer;

import java.util.AbstractList;

public class WindowChildren extends Window
{
    private final AbstractList<Window> children_ = new java.util.ArrayList<Window>();
    private Window active_ = null;
    private Window capture_ = null;
    private Window last_ = null;
    
    public WindowChildren(int ox, int oy, int w, int h)
    {
        super(ox, oy, w, h);
    }
    
    public void add(Window child)
    {
        if (child == null)
            throw new IllegalArgumentException("Child window mustn't be null.");
        children_.add(child);
    }
    
    public boolean setActive(ConsoleState state, Window child)
    {
        // Assert child in children_
        boolean ret = true;
        if (ret && active_ != null) // && state != null)
            ret = active_.onFocus(state, false);
        if (ret)
        {
            active_ = null;
            if (child != null) // && state != null)
                ret = child.onFocus(state, true);
            if (ret)
                active_ = child;
        }
        return ret;
    }
    
    public void setCapture(Window child)
    {
        // Assert child in children_
        capture_ = child;
    }
    
    public AbstractList<Window> getChildren()
    {
        return children_;
    }
    
    @Override
    protected void draw(Buffer w, int wx, int wy)
    {
        if (super.getInvalid())
        {
            super.draw(w, wx, wy);
            
            for (Window d : children_)
            {
                d.draw(w, wx + d.ox_, wy + d.oy_);
            }
        }
        else
        {
            for (Window d : children_)
            {
                if (d.getInvalid())
                    d.draw(w, wx + d.ox_, wy + d.oy_);
            }
        }
    }
   
    @Override
	public void onEvent(ConsoleState state, Event.MouseButton mbev)
	{
        super.onEvent(state, mbev);
        //System.err.println("WindowChildren::OnEvent " + mbev);
        
        // TODO only send if in box, unless captured
        if (capture_ != null)
            capture_.onEvent(state, mbev);
        else
        {
            //for (Window d : children_) // Should be in reverse order
            java.util.ListIterator<Window> itr = children_.listIterator(children_.size());
            while(itr.hasPrevious())
            {
                Window d = itr.previous();
                if (d.inside(mbev.mx, mbev.my))
                {
                    d.onEvent(state, mbev);
                    break;
                }
            }
        }
	}
        
    @Override
	public void onEvent(ConsoleState state, Event.MouseMoved mev)
	{
        super.onEvent(state, mev);
        // TODO only send if in box, unless captured
        if (capture_ != null)
            capture_.onEvent(state, mev);
        else
        {
            for (Window d : children_)
            {
                if (d.inside(mev.mx, mev.my))
                {
                    if (last_ != d)
                    {
                        if (last_ != null)
                            last_.onMouseLeave(state);
                        d.onMouseEnter(state);
                    }
                    last_ = d;
                    d.onEvent(state, mev);
                    break;
                }
                else if (d == last_)
                {
                    d.onMouseLeave(state);
                    last_ = null;
                }
            }
        }
	}
    
    @Override
    public void onEvent(ConsoleState state, Event.Key kev)
    {
        //System.out.println("WindowChildren OnEvent.Key " + kev.key);
        super.onEvent(state, kev);
        if (active_ != null)
            active_.onEvent(state, kev);
            
        if (kev.state == Event.State.RELEASED)
        {
            switch (kev.key)
            {
            case TAB:
                if (children_.isEmpty())
                {
                    setActive(state, null);
                }
                else
                {
                    Window current = active_;
                    boolean found = false;
                    int i = active_ != null ? children_.indexOf(active_) + 1 : 0;
                    while (!found)
                    {
                        if (i >= children_.size())
                        {
                            if (current == null)
                                break;
                            else
                                i = 0;
                        }
                        Window active = children_.get(i);
                        found = setActive(state, active);
                        if (active == current)
                            break;
                        ++i;
                    }
                }
                break;
            }
        }
    }
    
    @Override
    public boolean onFocus(ConsoleState state, boolean focus)
    {
        boolean ret = super.onFocus(state, focus);
        if (ret && active_ != null)
            ret = active_.onFocus(state, focus);
        return ret;
    }
    
    @Override
    public boolean getInvalid()
    {
        if (super.getInvalid())
            return true;
        for (Window d : children_)
        {
            if (d.getInvalid())
                return true;
        }
        return false;
    }
}
