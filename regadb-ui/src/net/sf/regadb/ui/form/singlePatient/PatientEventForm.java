package net.sf.regadb.ui.form.singlePatient;

import java.util.List;

import net.sf.regadb.db.Event;
import net.sf.regadb.db.EventNominalValue;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.PatientEventValue;
import net.sf.regadb.db.Transaction;
import net.sf.regadb.db.ValueTypes;
import net.sf.regadb.ui.framework.RegaDBMain;
import net.sf.regadb.ui.framework.forms.FormWidget;
import net.sf.regadb.ui.framework.forms.InteractionState;
import net.sf.regadb.ui.framework.forms.fields.ComboBox;
import net.sf.regadb.ui.framework.forms.fields.DateField;
import net.sf.regadb.ui.framework.forms.fields.FormField;
import net.sf.regadb.ui.framework.forms.fields.Label;
import net.sf.regadb.ui.framework.forms.fields.TextField;
import net.sf.regadb.ui.framework.widgets.formtable.FormTable;
import net.sf.regadb.ui.framework.widgets.messagebox.MessageBox;
import net.sf.regadb.util.date.DateUtils;
import net.sf.witty.wt.SignalListener;
import net.sf.witty.wt.WContainerWidget;
import net.sf.witty.wt.WEmptyEvent;
import net.sf.witty.wt.WGroupBox;
import net.sf.witty.wt.i8n.WMessage;

public class PatientEventForm extends FormWidget
{
	private PatientEventValue patientEvent_;
	
	// FORM COMPONENTS \\
	private WGroupBox mainFrameGroup_;
	private FormTable mainFrameTable_;
	private Label lblEvent, lblValue, lblStartDate, lblEndDate;
	private WContainerWidget valueContainer;
	private FormField ffValue;
	private ComboBox<Event> cmbEvents;
	private DateField startDate, endDate;
	
	public PatientEventForm(InteractionState state, WMessage formName, boolean literal, PatientEventValue patientEvent) {
		super(formName, state, literal);
		
		patientEvent_ = patientEvent;
		
		init();
		fillData();
	}
	
	private void init()
	{
		mainFrameGroup_= new WGroupBox(tr("general.group.general"), this);
		mainFrameTable_ = new FormTable(mainFrameGroup_);
		
		lblStartDate = new Label(tr("date.start"));
		startDate = new DateField(getInteractionState(), this);
		startDate.setMandatory(true);
		mainFrameTable_.addLineToTable(lblStartDate, startDate);
		
		lblEndDate = new Label(tr("date.end"));
		endDate = new DateField(getInteractionState(), this);
		mainFrameTable_.addLineToTable(lblEndDate, endDate);
		
		lblEvent = new Label(tr("event.form"));
		cmbEvents = new ComboBox<Event>(getInteractionState(), this);
        cmbEvents.setMandatory(true);
        mainFrameTable_.addLineToTable(lblEvent, cmbEvents);
		
		Transaction t = RegaDBMain.getApp().createTransaction();
		
        List<Event> eventList = t.getEvents();
        
        for(Event e : eventList)
        {
        	cmbEvents.addItem(new DataComboMessage<Event>(e, e.getName()));
        }
        
        cmbEvents.sort();
        
        cmbEvents.addComboChangeListener(new SignalListener<WEmptyEvent>()
		{
			public void notify(WEmptyEvent a)
			{
				updateValue();
			}
        });
        
        t.commit();
		
        int row = mainFrameTable_.numRows();
		lblValue = new Label(tr("event.value"));
		lblValue.setLabelUIMandatory(this);
		valueContainer = new WContainerWidget();
		
        mainFrameTable_.putElementAt(row, 0, lblValue);
        mainFrameTable_.putElementAt(row, 1, valueContainer);
        
		addControlButtons();
	}
	
	private void fillData() {
		if( getInteractionState() == InteractionState.Adding )
		{
			patientEvent_ = new PatientEventValue();
		}
		else
		{
			cmbEvents.selectItem(patientEvent_.getEvent().getName());
		}
		
		updateValue();
		
		if( getInteractionState() != InteractionState.Adding )
        {
            if( isNominalValue() )
            {
                ((ComboBox<PatientEventValue>)ffValue).selectItem(patientEvent_.getEventNominalValue().getValue());
            }
            else
            {
            	ffValue.setText(patientEvent_.getValue());
            }
        }
		
		startDate.setDate(patientEvent_.getStartDate());
		endDate.setDate(patientEvent_.getEndDate());
	}
	
	private void updateValue()
	{
		removeFormField(ffValue);
        
		if ( isNominalValue() )
		{
			ffValue = new ComboBox<PatientEventValue>(getInteractionState(), this);
			
	        for(EventNominalValue env : cmbEvents.currentValue().getEventNominalValues())
	        {
	        	((ComboBox)ffValue).addItem(new DataComboMessage<EventNominalValue>(env, env.getValue()));
	        }
	        
	        ((ComboBox<PatientEventValue>)ffValue).sort();
		}
		else
		{
			ffValue = getTextField(ValueTypes.getValueType(cmbEvents.currentValue().getValueType()));
		}
		
        valueContainer.clear();
        valueContainer.addWidget(ffValue);
	}
	
	@Override
	public void cancel() {
		if( getInteractionState() == InteractionState.Adding )
        {
            redirectToSelect(RegaDBMain.getApp().getTree().getTreeContent().patientEvent, RegaDBMain.getApp().getTree().getTreeContent().patientEventSelect);
        }
        else
        {
            redirectToView(RegaDBMain.getApp().getTree().getTreeContent().patientEventSelected, RegaDBMain.getApp().getTree().getTreeContent().patientEventView);
        }
	}
	
	@Override
	public WMessage deleteObject()
	{
		Transaction t = RegaDBMain.getApp().createTransaction();
        
        t.delete(patientEvent_);
        
        t.commit();
        
        return null;
	}
	
	@Override
	public void redirectAfterDelete() {
		RegaDBMain.getApp().getTree().getTreeContent().patientEventSelect.selectNode();
        RegaDBMain.getApp().getTree().getTreeContent().patientEventSelected.setSelectedItem(null);
	}
	
	@Override
	public void saveData() {
		Transaction t = RegaDBMain.getApp().createTransaction();
		
		Patient p = RegaDBMain.getApp().getTree().getTreeContent().patientSelected.getSelectedItem();
		t.attach(p);
		
		if( endDate.getDate() != null && DateUtils.compareDates(startDate.getDate(), endDate.getDate()) > 0)
        {
            MessageBox.showWarningMessage(tr("message.therapy.enddate"));
        }
        else
        {
        	if( getInteractionState() == InteractionState.Adding )
			{
				patientEvent_ = p.addPatientEvent(cmbEvents.currentValue());
			}
			
			patientEvent_.setStartDate(startDate.getDate());
			patientEvent_.setEndDate(endDate.getDate());
			
			patientEvent_.setEvent(cmbEvents.currentValue());
			
			if ( isNominalValue() )
			{
				patientEvent_.setEventNominalValue( (EventNominalValue)((ComboBox)ffValue).currentValue() );
			}
			else
			{
				patientEvent_.setValue( ((TextField)ffValue).getFormText() );
			}
			
			update(patientEvent_, t);
			t.commit();
			
	        RegaDBMain.getApp().getTree().getTreeContent().patientEventSelected.setSelectedItem(patientEvent_);
	        redirectToView(RegaDBMain.getApp().getTree().getTreeContent().patientEventSelected, RegaDBMain.getApp().getTree().getTreeContent().patientEventView);
		}
	}
	
	public boolean isNominalValue()
	{
		return (ValueTypes.getValueType(cmbEvents.currentValue().getValueType()) == ValueTypes.NOMINAL_VALUE);
	}
}
