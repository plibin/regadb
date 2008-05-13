package net.sf.regadb.ui.form.query.querytool.buttons;

import java.util.ArrayList;
import java.util.List;

import net.sf.witty.wt.WContainerWidget;
import net.sf.witty.wt.WPushButton;
import net.sf.witty.wt.WText;
import net.sf.witty.wt.i8n.WMessage;

public class ButtonPanel extends WContainerWidget{
	private List<WPushButton> buttons;
	
	public enum Style {
		Flat,
		Default
	}
	
	public ButtonPanel(Style style) {
		buttons = new ArrayList<WPushButton>();
		if (style == Style.Flat) {
			this.setStyleClass("buttonpanel flatbuttonpanel");
		}
		else {
			this.setStyleClass("buttonpanel defaultbuttonpanel");
		}
	}
	
	public void addButton(WPushButton button) {
		buttons.add(button);
		this.addWidget(button);
	}
	
	public void addSeparator() {
		this.addWidget(new WText(new WMessage(" ", true)));
	}
	
	public void setEnabled(boolean enabled) {
		for (WPushButton button : buttons) {
			if (enabled) {
				button.enable();
			}
			else {
				button.disable();
			}
		}
	}
}
