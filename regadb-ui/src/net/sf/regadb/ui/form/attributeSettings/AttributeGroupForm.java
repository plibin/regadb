package net.sf.regadb.ui.form.attributeSettings;

import net.sf.regadb.db.AttributeGroup;
import net.sf.regadb.db.Transaction;
import net.sf.regadb.ui.framework.RegaDBMain;
import net.sf.regadb.ui.framework.forms.FormWidget;
import net.sf.regadb.ui.framework.forms.InteractionState;
import net.sf.regadb.ui.framework.forms.fields.Label;
import net.sf.regadb.ui.framework.forms.fields.TextField;
import net.sf.regadb.ui.framework.widgets.formtable.FormTable;
import net.sf.witty.wt.WGroupBox;
import net.sf.witty.wt.i8n.WMessage;

public class AttributeGroupForm extends FormWidget 
{
    private AttributeGroup attributeGroup_;
    
    //general group
    private WGroupBox generalGroup_;
    private FormTable generalGroupTable_;
    private Label nameL;
    private TextField nameTF;
    
    public AttributeGroupForm(InteractionState interactionState, WMessage formName, boolean literal, AttributeGroup attributeGroup)
    {
        super(formName, interactionState, literal);
        attributeGroup_ = attributeGroup;
        
        init();
        
        fillData();
    }
    
    private void init() 
    {
        generalGroup_ = new WGroupBox(tr("general.group.general"), this);
        generalGroupTable_= new FormTable(generalGroup_);
        nameL = new Label(tr("general.name"));
        nameTF = new TextField(getInteractionState(), this);
        nameTF.setMandatory(true);
        generalGroupTable_.addLineToTable(nameL, nameTF);
        
        addControlButtons();
    }
    
    private void fillData() 
    {
        if(getInteractionState()==InteractionState.Adding)
        {
            attributeGroup_ = new AttributeGroup();
        }
        
        if(getInteractionState()!=InteractionState.Adding)
        {
            Transaction t = RegaDBMain.getApp().createTransaction();
            
            t.attach(attributeGroup_);
            
            nameTF.setText(attributeGroup_.getGroupName());
            
            t.commit();
        }
    }

    @Override
    public void saveData() 
    {
        Transaction t = RegaDBMain.getApp().createTransaction();
        
        if(getInteractionState()!=InteractionState.Adding)
        {
            t.attach(attributeGroup_);
        }
        
        attributeGroup_.setGroupName(nameTF.text());
        
        update(attributeGroup_, t);
        t.commit();
        
        RegaDBMain.getApp().getTree().getTreeContent().attributeGroupsSelected.setSelectedItem(attributeGroup_);
        redirectToView(RegaDBMain.getApp().getTree().getTreeContent().attributeGroupsSelected, RegaDBMain.getApp().getTree().getTreeContent().attributeGroupsView);
    }
    
    @Override
    public void cancel()
    {
        if(getInteractionState()==InteractionState.Adding)
        {
            redirectToSelect(RegaDBMain.getApp().getTree().getTreeContent().attributeGroups, RegaDBMain.getApp().getTree().getTreeContent().attributeGroupsSelect);
        }
        else
        {
            redirectToView(RegaDBMain.getApp().getTree().getTreeContent().attributeGroupsSelected, RegaDBMain.getApp().getTree().getTreeContent().attributeGroupsView);
        }
    }
    
    @Override
    public WMessage deleteObject()
    {
        Transaction t = RegaDBMain.getApp().createTransaction();
        
        try
        {
        	t.delete(attributeGroup_);
            
            t.commit();
            
            return null;
        }
        catch(Exception e)
        {
        	t.clear();
        	t.rollback();
        	
        	return tr("message.general.inuse");
        }
        
    }

    @Override
    public void redirectAfterDelete() 
    {
        RegaDBMain.getApp().getTree().getTreeContent().attributeGroupsSelect.selectNode();
        RegaDBMain.getApp().getTree().getTreeContent().attributeGroupsSelected.setSelectedItem(null);
    }
}
