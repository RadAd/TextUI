// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

import au.radsoft.console.Console;

// TODO
// Support other button options ie Yes/No, Ok/Cancel

public class MsgBox extends Dialog
{
    public MsgBox(String title, String msg, int w)
    {
        super(0, 0, w, 7);
        setTitle(title);
        String[] sa = splitIntoLine(msg, w - 2);
        h_ = sa.length + 3;
        int y = 1;
        for (String s : sa)
        {
            add(new StringField(1, y, s));
            ++y;
        }
        add(new Button(w - 11, y + 1, "Ok"));
    }
    
    private static String[] splitIntoLine(String input, int maxCharInLine)
    {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            while(word.length() > maxCharInLine){
                output.append(word.substring(0, maxCharInLine-lineLen) + "\n");
                word = word.substring(maxCharInLine-lineLen);
                lineLen = 0;
            }

            if (lineLen + word.length() > maxCharInLine) {
                output.append("\n");
                lineLen = 0;
            }
            output.append(word + " ");

            lineLen += word.length() + 1;
        }
        // output.split();
        // return output.toString();
        return output.toString().split("\n");
    }
    
    @Override
	public void onEvent(ConsoleState state, Button.Event bev)
	{
        super.onEvent(state, bev);
        
        state.result(1);
        state.exit();
	}
    
    static public int domodal(Console console, String title, String msg, int w)
    {
        MsgBox mb = new MsgBox(title, msg, w);
        mb.centre(console);
        return mb.domodal(console);
    }
}
