package net.sf.regadb.ui.framework.form.action;

import net.sf.regadb.ui.framework.RegaDBApplication;
import net.sf.regadb.ui.framework.RegaDBMain;
import net.sf.regadb.ui.framework.form.IForm;
import net.sf.regadb.ui.framework.tree.TreeMenuNode;

public class PutFormAction implements ITreeAction
{
	private IForm form_;
	
	public PutFormAction(IForm form)
	{
		form_ = form;
	}
	
	public void performAction(TreeMenuNode node)
	{
		RegaDBMain.getApp().getFormContainer().setForm(form_);
	}
}
