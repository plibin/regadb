package net.sf.regadb.ui.form.query.querytool.buttons;

import net.sf.regadb.ui.form.query.querytool.awceditor.WAWCEditorPanel;
import net.sf.regadb.ui.form.query.querytool.dialog.SelectClauseDialog;
import net.sf.regadb.ui.form.query.querytool.tree.QueryTreeNode;
import net.sf.regadb.ui.form.query.querytool.widgets.WButtonPanel;

import com.pharmadm.custom.rega.queryeditor.UniqueNameContext.AssignMode;

import eu.webtoolkit.jwt.Signal1;
import eu.webtoolkit.jwt.WMouseEvent;
import eu.webtoolkit.jwt.WPushButton;

public class SelectClauseButtonPanel extends WButtonPanel {
	private QueryTreeNode owner;
	private SelectClauseDialog dialog;
	private WPushButton cancelButton;
	private WPushButton okButton;
	private boolean disabled;
	
	public SelectClauseButtonPanel(QueryTreeNode owner, SelectClauseDialog dialog) {
		super(Style.Default);
		this.owner = owner;
		this.dialog = dialog;
		init();
	}
	
	private void init() {
		okButton = new WPushButton(tr("form.general.button.ok"));
		addButton(okButton);

		cancelButton = new WPushButton(tr("form.general.button.cancel"));
		addButton(cancelButton);

		okButton.clicked().addListener(this, new Signal1.Listener<WMouseEvent>() {
			public void trigger(WMouseEvent a) {
				if (!disabled) {
					disabled = true;
					WAWCEditorPanel panel = dialog.getSelectedClause();
					if (panel == null || panel.getManager().isUseless()) {
						cancel();
					}
					else {
						panel.getManager().applyEditings();
						owner.getParentNode().addNode(panel.getManager().getClause(), AssignMode.output);
						owner.getParentNode().removeChildNode(owner);
						owner.getQueryApp().setQueryEditable(true);
					}
				}
			}
		});
		
		cancelButton.clicked().addListener(this, new Signal1.Listener<WMouseEvent>() {
			public void trigger(WMouseEvent a) {
				if (!disabled) {
					disabled = true;
					cancel();
				}
			}
		});
	}
	
	private void cancel() {
		owner.getQueryApp().setQueryEditable(true);
		owner.getParentNode().removeChildNode(owner);
	}
}
