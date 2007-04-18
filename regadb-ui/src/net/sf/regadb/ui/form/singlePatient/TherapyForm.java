package net.sf.regadb.ui.form.singlePatient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.regadb.db.Patient;
import net.sf.regadb.db.Therapy;
import net.sf.regadb.db.TherapyCommercial;
import net.sf.regadb.db.TherapyGeneric;
import net.sf.regadb.db.Transaction;
import net.sf.regadb.ui.framework.RegaDBMain;
import net.sf.regadb.ui.framework.forms.FormWidget;
import net.sf.regadb.ui.framework.forms.InteractionState;
import net.sf.regadb.ui.framework.forms.fields.DateField;
import net.sf.regadb.ui.framework.forms.fields.Label;
import net.sf.regadb.ui.framework.forms.fields.TextField;
import net.sf.regadb.ui.framework.widgets.editableTable.EditableTable;
import net.sf.witty.wt.WGroupBox;
import net.sf.witty.wt.WTable;
import net.sf.witty.wt.i8n.WMessage;

public class TherapyForm extends FormWidget
{
	private Therapy therapy_;
	
	//general group
    private WGroupBox generalGroup_;
    private WTable generalGroupTable_;
    private Label startDateL;
    private DateField startDateTF;
    private Label stopDateL;
    private DateField stopDateTF;
    private Label commentL;
    private TextField commentTF;
    
    //generic drugs group
    private WGroupBox genericGroup_;
    private EditableTable<TherapyGeneric> drugGenericList_;
    private IGenericDrugSelectionEditableTable iGenericDrugSelectionEditableTable_;
    
    //commercial drugs group
    private WGroupBox commercialGroup_;
    private EditableTable<TherapyCommercial> drugCommercialList_;
    private ICommercialDrugSelectionEditableTable iCommercialDrugSelectionEditableTable_;
    
	public TherapyForm(InteractionState interactionState, WMessage formName, Therapy therapy)
	{
		super(formName, interactionState);
		therapy_ = therapy;
		
		init();
		
		fillData();
	}

	public void init()
	{
		//general group
        generalGroup_ = new WGroupBox(tr("form.therapy.editView.general"), this);
        generalGroupTable_ = new WTable(generalGroup_);
        startDateL = new Label(tr("form.therapy.editView.startDate"));
        startDateTF = new DateField(getInteractionState(), this);
        startDateTF.setMandatory(true);
        addLineToTable(generalGroupTable_, startDateL, startDateTF);
        stopDateL = new Label(tr("form.therapy.editView.stopDate"));
        stopDateTF = new DateField(getInteractionState(), this);
        addLineToTable(generalGroupTable_, stopDateL, stopDateTF);
        commentL = new Label(tr("form.therapy.editView.comment"));
        commentTF = new TextField(getInteractionState(), this);
        addLineToTable(generalGroupTable_, commentL, commentTF);
        genericGroup_ = new WGroupBox(tr("form.therapy.editableTable.genericDrugs"), this);
        commercialGroup_ = new WGroupBox(tr("form.therapy.editableTable.commercialDrugs"), this);
        
        addControlButtons();
	}
	
	private void fillData()
	{
        Transaction t = RegaDBMain.getApp().createTransaction();
        
		if(getInteractionState()==InteractionState.Adding)
		{
            Patient p = RegaDBMain.getApp().getTree().getTreeContent().patientSelected.getSelectedItem();
            t.attach(p);
            therapy_ = p.createTherapy(new Date(System.currentTimeMillis()));
        }
        else
        {
	        t.attach(therapy_);
        }
        
        t.commit();
	        
		startDateTF.setDate(therapy_.getStartDate());
		stopDateTF.setDate(therapy_.getStopDate());
		commentTF.setText(therapy_.getComment());
        
		//generic drugs group
        t = RegaDBMain.getApp().createTransaction();
        List<TherapyGeneric> tgs = new ArrayList<TherapyGeneric>();
        for(TherapyGeneric tg : therapy_.getTherapyGenerics())
        {
            tgs.add(tg);
        }
        t.commit();
        iGenericDrugSelectionEditableTable_ = new IGenericDrugSelectionEditableTable(this, therapy_);
        drugGenericList_ = new EditableTable<TherapyGeneric>(genericGroup_, iGenericDrugSelectionEditableTable_, tgs);
			
        //commercial drugs group
        t = RegaDBMain.getApp().createTransaction();
        List<TherapyCommercial> tcs = new ArrayList<TherapyCommercial>();
        for(TherapyCommercial tc : therapy_.getTherapyCommercials())
        {
            tcs.add(tc);
        }
        t.commit();
        iCommercialDrugSelectionEditableTable_ = new ICommercialDrugSelectionEditableTable(this, therapy_);
        drugCommercialList_ = new EditableTable<TherapyCommercial>(commercialGroup_, iCommercialDrugSelectionEditableTable_, tcs);
	}
	
	@Override
	public void saveData()
	{
		Transaction t = RegaDBMain.getApp().createTransaction();
		
		Patient p = RegaDBMain.getApp().getTree().getTreeContent().patientSelected.getSelectedItem();
		t.attach(p);
				
		therapy_.setStartDate(startDateTF.getDate());
		
		therapy_.setStopDate(stopDateTF.getDate());
		
		if(canStore(commentTF.text()))
		{
			therapy_.setComment(commentTF.text());
		}
		else
		{
			therapy_.setComment(null);
		}
		
        iCommercialDrugSelectionEditableTable_.setTransaction(t);
        drugCommercialList_.saveData();
        
        iGenericDrugSelectionEditableTable_.setTransaction(t);
        drugGenericList_.saveData();
        
		t.update(therapy_);
		t.commit();
		
		RegaDBMain.getApp().getTree().getTreeContent().therapiesSelected.setSelectedItem(therapy_);
        RegaDBMain.getApp().getTree().getTreeContent().therapiesSelected.expand();
        RegaDBMain.getApp().getTree().getTreeContent().therapiesSelected.refreshAllChildren();
        RegaDBMain.getApp().getTree().getTreeContent().therapiesView.selectNode();
	}
}
