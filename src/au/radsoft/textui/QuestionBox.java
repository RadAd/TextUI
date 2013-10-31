// TextUI Copyright (C) 2013  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.textui;

// TODO
// Add buttons Ok/Cancel

public class QuestionBox extends Dialog
{
    private final EditField field_;
    private final Button okButton_;
    private final Button cancelButton_;
    
    public QuestionBox(String title, String msg, String value, int length)
    {
        super(0, 0, Math.max(length + 2, 23), 7);
        setTitle(title);
        add(new StringField(1, 1, msg));
        add(field_ = new EditField(1, 3, value, length));
        add(okButton_ = new Button(w_ - 22, h_ - 2, "Ok"));
        add(cancelButton_ = new Button(w_ - 11, h_ - 2, "Cancel"));
        setActive(null, field_);
    }
    
    public String getValue()
    {
        return field_.getValue();
    }
    
    @Override
	public void onEvent(ConsoleState state, Button.Event bev)
	{
        super.onEvent(state, bev);
        
        if (bev.button == okButton_)
            state.result(1);
        state.exit();
	}
}
