package net.sf.regadb.ui.tree.items.singlePatient;

import net.sf.regadb.db.Privileges;
import net.sf.regadb.db.Therapy;
import net.sf.regadb.db.Transaction;
import net.sf.regadb.ui.datatable.therapy.SelectTherapyForm;
import net.sf.regadb.ui.form.singlePatient.TherapyForm;
import net.sf.regadb.ui.framework.RegaDBMain;
import net.sf.regadb.ui.framework.forms.IForm;
import net.sf.regadb.ui.framework.forms.InteractionState;
import net.sf.regadb.ui.framework.forms.ObjectForm;
import net.sf.regadb.ui.framework.tree.TreeMenuNode;
import net.sf.regadb.ui.tree.FormNavigationNode;
import net.sf.regadb.ui.tree.ObjectTreeNode;
import net.sf.regadb.util.date.DateUtils;
import eu.webtoolkit.jwt.WString;
import eu.webtoolkit.jwt.WWidget;

public class TherapyTreeNode extends ObjectTreeNode<Therapy>{
	private FormNavigationNode copyLast;

	public TherapyTreeNode(TreeMenuNode parent) {
		super("therapy", parent);
	}
	
	@Override
	protected void init(){
		super.init();
		
		copyLast = new FormNavigationNode(getMenuResource("copylast"), this, true)
		{
			public ObjectForm<Therapy> createForm()
			{
				return new TherapyForm(WWidget.tr("form.therapy.add"), InteractionState.Adding, TherapyTreeNode.this, null, true);
			}
		};
	}
	
	public FormNavigationNode getCopyLastNode(){
		return copyLast;
	}

	@Override
	public String getArgument(Therapy type) {
		if(type != null)
			return DateUtils.format(type.getStartDate());
		else
			return "";
	}

	@Override
	public void applyPrivileges(Privileges priv){
		super.applyPrivileges(priv);
		copyLast.setDisabled(priv != Privileges.READWRITE);
	}

	@Override
	protected ObjectForm<Therapy> createForm(WString name, InteractionState interactionState, Therapy selectedObject) {
		return new TherapyForm(name, interactionState, TherapyTreeNode.this, selectedObject, false);
	}

	@Override
	protected IForm createSelectionForm() {
		return new SelectTherapyForm(this);
	}

	@Override
	protected String getObjectId(Therapy object) {
		return object.getTherapyIi() +"";
	}

	@Override
	protected Therapy getObjectById(Transaction t, String id) {
		Therapy therapy = t.getTherapy(Integer.parseInt(id));
		
		if(RegaDBMain.getApp().getSelectedPatient().getTherapies().contains(therapy))
			return therapy;
		else
			return null;
	}
}
