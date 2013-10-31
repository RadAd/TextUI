// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Console;

public interface MenuCommand extends MenuItem
{
    String GetVerb();
    boolean IsEnabled();
    void DoCommand(ConsoleState state);
    
    public abstract class Default implements MenuCommand
    {
        private final String verb_;
        
        public Default(String verb)
        {
            verb_ = verb;
        }
        
        public String GetVerb()
        {
            return verb_;
        }
        
        public boolean IsEnabled()
        {
            return true;
        }
    }
}
