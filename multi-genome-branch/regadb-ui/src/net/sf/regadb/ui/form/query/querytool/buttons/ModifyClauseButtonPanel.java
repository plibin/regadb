package net.sf.regadb.ui.form.query.querytool.buttons;

import net.sf.regadb.ui.form.query.querytool.awceditor.WAWCSelectorPanel;
import net.sf.regadb.ui.form.query.querytool.tree.QueryTreeNode;
import net.sf.regadb.ui.form.query.querytool.widgets.WButtonPanel;
import net.sf.witty.wt.SignalListener;
import net.sf.witty.wt.WMouseEvent;
import net.sf.witty.wt.WPushButton;


public class ModifyClauseButtonPanel extends WButtonPanel {
	private QueryTreeNode node;
	private WAWCSelectorPanel editPanel;
	
	public ModifyClauseButtonPanel(QueryTreeNode node, WAWCSelectorPanel editPanel) {
		super(Style.Default);
		this.node = node;
		this.editPanel = editPanel;
		init();
	}
	
	
	private void init() {
		WPushButton okButton = new WPushButton(tr("form.general.button.ok"));
		addButton(okButton);

		WPushButton cancelButton = new WPushButton(tr("form.general.button.cancel"));
		addButton(cancelButton);

		okButton.clicked.addListener(new SignalListener<WMouseEvent>() {
			public void notify(WMouseEvent a) {
				editPanel.getSelectedClause().applyEditings();
				node.replaceNode(editPanel.getSelectedClause().getClause());
				node.showContent();
			}
		});
		
		cancelButton.clicked.addListener(new SignalListener<WMouseEvent>() {
			public void notify(WMouseEvent a) {
				node.showContent();
			}
		});
	}
}