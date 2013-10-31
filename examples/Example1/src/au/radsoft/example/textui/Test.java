// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.example.textui;

import au.radsoft.console.Console;
import au.radsoft.console.ConsoleUtils;

import au.radsoft.textui.ConsoleState;
import au.radsoft.textui.FrameWindow;
import au.radsoft.textui.Screen;
import au.radsoft.textui.MenuCommand;
import au.radsoft.textui.MenuList;
import au.radsoft.textui.MsgBox;

import java.util.Random;

public class Test extends Screen {
    public static void main(String[] args) throws Exception {
        Console console = ConsoleUtils.create("Test", 80, 25, true);
        try
        {
            console.showCursor(false);
            console.enableMouse(true);
        
            Test test = new Test(console);
            test.setStatus("Ready");
            test.doeventloop(console);
        }
        finally
        {
            console.clear();
            console.close();
        }
    }
    
    MenuList getMainMenu()
    {
        return new MenuList("Main",
            new MenuList("File",
                new MenuList("New",
                    new MenuCommand.Default("Frame Window") {
                        @Override
                        public void DoCommand(ConsoleState state)
                        {
                            NewWindow(state.GetConsole());
                        }
                    },
                    new MenuCommand.Default("Scroll Window") {
                        @Override
                        public void DoCommand(ConsoleState state)
                        {
                            NewScrollWindow(state.GetConsole());
                        }
                    }
                ),
                new MenuCommand.Default("Exit") {
                    @Override
                    public void DoCommand(ConsoleState state)
                    {
                        state.exit();
                    }
                }
            ),
            new MenuList("Edit",
                new MenuCommand.Default("Cut") {
                    @Override
                    public void DoCommand(ConsoleState state)
                    {
                        MsgBox.domodal(state.GetConsole(), "Cut", "Cut to clipboard", 15);
                    }
                },
                new MenuCommand.Default("Copy") {
                    @Override
                    public void DoCommand(ConsoleState state)
                    {
                        MsgBox.domodal(state.GetConsole(), "Copy", "Copy to clipboard", 15);
                    }
                },
                new MenuCommand.Default("Paste") {
                    @Override
                    public void DoCommand(ConsoleState state)
                    {
                        MsgBox.domodal(state.GetConsole(), "Paste", "Paste from clipboard", 15);
                    }
                }
            ),
            new MenuList("Help",
                new MenuCommand.Default("About") {
                    @Override
                    public void DoCommand(ConsoleState state)
                    {
                        Package objPackage = getClass().getPackage();
                        MsgBox.domodal(state.GetConsole(), "About",
                            objPackage.getSpecificationTitle()
                            + " Version: " + objPackage.getSpecificationVersion(),
                            15);
                    }
                }
            )
        );
    }
    
    private int winno_ = 1;
    private Random r =new Random();
    
    Test(Console console)
    {
        super(console);
        
        setMenu(getMainMenu());
    }
    
    void NewWindow(Console console)
    {
        int w = 10 + r.nextInt(10);
        int h = 10 + r.nextInt(10);
        FrameWindow d = new FrameWindow(r.nextInt(console.getWidth() - w - 2) + 1, r.nextInt(console.getHeight() - 3 - h) + 2, w, h);
        d.setTitle("Window " + winno_++);
        add(d);
    }
    
    void NewScrollWindow(Console console)
    {
        int w = 10 + r.nextInt(10);
        int h = 10 + r.nextInt(10);
        FrameWindow d = new ScrollWindowTest(r.nextInt(console.getWidth() - w - 2) + 1, r.nextInt(console.getHeight() - 3 - h) + 2, w, h);
        d.setTitle("Window " + winno_++);
        add(d);
    }
}
