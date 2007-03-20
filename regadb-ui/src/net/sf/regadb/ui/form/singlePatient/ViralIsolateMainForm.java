package net.sf.regadb.ui.form.singlePatient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.regadb.db.NtSequence;
import net.sf.regadb.db.Transaction;
import net.sf.regadb.db.ViralIsolate;
import net.sf.regadb.ui.framework.forms.InteractionState;
import net.sf.regadb.ui.framework.forms.fields.DateField;
import net.sf.regadb.ui.framework.forms.fields.Label;
import net.sf.regadb.ui.framework.forms.fields.NucleotideField;
import net.sf.regadb.ui.framework.forms.fields.TextField;
import net.sf.regadb.ui.framework.widgets.messagebox.MessageBox;
import net.sf.witty.wt.core.utils.WHorizontalAlignment;
import net.sf.witty.wt.widgets.SignalListener;
import net.sf.witty.wt.widgets.WComboBox;
import net.sf.witty.wt.widgets.WContainerWidget;
import net.sf.witty.wt.widgets.WGroupBox;
import net.sf.witty.wt.widgets.WPushButton;
import net.sf.witty.wt.widgets.WTable;
import net.sf.witty.wt.widgets.event.WEmptyEvent;
import net.sf.witty.wt.widgets.event.WMouseEvent;

public class ViralIsolateMainForm extends WContainerWidget
{
	private ViralIsolateForm viralIsolateForm_;
    
    private ArrayList<NtSequence> removedSequences = new ArrayList<NtSequence>(); 

	// General group
	private WGroupBox generalGroup_;
	private WTable generalGroupTable_;
	private Label sampleDateL;
	private DateField sampleDateTF;
	private Label sampleIdL;
	private TextField sampleIdTF;
	//private Label sampleTypeL;
	//private TextField sampleTypeTF;

	// Sequence group
	private WGroupBox sequenceGroup_;
	private WTable sequenceGroupTable_;
	private WComboBox seqComboBox;
	private WPushButton addButton;
	private WPushButton deleteButton;
    private WPushButton confirmButton;
    private WPushButton cancelButton;

	// NtSeq group
	private WGroupBox ntSeqGroup_;
	private WTable ntSeqGroupTable_;
	private Label seqLabel;
	private TextField seqLabelTF;
	private Label seqDateL;
	private DateField seqDateTF;
    private Label ntL;
    private NucleotideField ntTF;
	//private Label subTypeL;
	//private TextField subTypeTF;
    
    private static final String defaultSequenceLabel_ = "Sequence ";

	public ViralIsolateMainForm(ViralIsolateForm viralIsolateForm)
	{
		super();
		viralIsolateForm_ = viralIsolateForm;

		init();
	}

	public void init()
	{
		// General group
		generalGroup_ = new WGroupBox(tr("form.viralIsolate.editView.general"), this);
		generalGroupTable_ = new WTable(generalGroup_);
		sampleDateL = new Label(tr("form.viralIsolate.editView.sampleDate"));
		sampleDateTF = new DateField(viralIsolateForm_.getInteractionState(), viralIsolateForm_);
		viralIsolateForm_.addLineToTable(generalGroupTable_, sampleDateL, sampleDateTF);
		sampleIdL = new Label(tr("form.viralIsolate.editView.sampleId"));
		sampleIdTF = new TextField(viralIsolateForm_.getInteractionState(), viralIsolateForm_);
		viralIsolateForm_.addLineToTable(generalGroupTable_, sampleIdL, sampleIdTF);
		//sampleTypeL = new Label(tr("form.viralIsolate.editView.sampleType"));
		//sampleTypeTF = new TextField(viralIsolateForm_.getInteractionState(), viralIsolateForm_);
		//viralIsolateForm_.addLineToTable(generalGroupTable_, sampleTypeL, sampleTypeTF);

		// Sequence group
		sequenceGroup_ = new WGroupBox(tr("form.viralIsolate.editView.sequence"), this);
		sequenceGroupTable_ = new WTable(sequenceGroup_);
		// comboBox
		seqComboBox = new WComboBox();
        sequenceGroupTable_.putElementAt(0, 0, seqComboBox);

		// NtSeq group
		sequenceGroupTable_.elementAt(1, 0).setColumnSpan(2);
		ntSeqGroup_ = new WGroupBox(tr("form.viralIsolate.editView.ntSeq"), sequenceGroupTable_.elementAt(1, 0));
		ntSeqGroupTable_ = new WTable(ntSeqGroup_);
		seqLabel = new Label(tr("form.viralIsolate.editView.seqLabel"));
		seqLabelTF = new TextField(viralIsolateForm_.getInteractionState(), viralIsolateForm_);
        seqLabelTF.setMandatory(true);
		viralIsolateForm_.addLineToTable(ntSeqGroupTable_, seqLabel, seqLabelTF);
		seqDateL = new Label(tr("form.viralIsolate.editView.seqDate"));
		seqDateTF = new DateField(viralIsolateForm_.getInteractionState(), viralIsolateForm_);
		viralIsolateForm_.addLineToTable(ntSeqGroupTable_, seqDateL, seqDateTF);
        ntL = new Label(tr("form.viralIsolate.editView.nucleotides"));
        ntTF = new NucleotideField(viralIsolateForm_.getInteractionState(), viralIsolateForm_);
        ntTF.setMandatory(true);
        viralIsolateForm_.addLineToTable(ntSeqGroupTable_, ntL, ntTF);
		//subTypeL = new Label(tr("form.viralIsolate.editView.subType"));
		//subTypeTF = new TextField(viralIsolateForm_.getInteractionState(), viralIsolateForm_);
		//viralIsolateForm_.addLineToTable(ntSeqGroupTable_, subTypeL, subTypeTF);
        
        addButtons();
	}
	
	public void fillData(ViralIsolate vi)
	{
		sampleDateTF.setDate(vi.getSampleDate());
		sampleIdTF.setText(vi.getSampleId());
        
        if(viralIsolateForm_.getInteractionState()==InteractionState.Adding)
        {
            addSeqData();
        }
        
        for(NtSequence ntseq : vi.getNtSequences())
        {
            if(ntseq.getLabel()==null || ntseq.getLabel().equals(""))
			{
				ntseq.setLabel(getUniqueSequenceLabel(vi));
            }
            seqComboBox.addItem(new DataComboMessage<NtSequence>(ntseq, ntseq.getLabel()));
        }
        seqComboBox.setCurrentIndex(0);
       
        setSequenceData(((DataComboMessage<NtSequence>)seqComboBox.currentText()).getValue());
        
        setFieldListeners();
        
        seqComboBox.changed.addListener(new SignalListener<WEmptyEvent>()
            {
                public void notify(WEmptyEvent a) 
                {
                    setSequenceData(((DataComboMessage<NtSequence>)seqComboBox.currentText()).getValue());
                }
            });

        addButton.clicked.addListener(new SignalListener<WMouseEvent>()
                {
                    public void notify(WMouseEvent a) 
                    {
                        DataComboMessage<NtSequence> msg = addSeqData();

                        seqComboBox.setCurrentItem(msg);
                        setSequenceData(msg.getValue());
                    }
                });
        
        deleteButton.clicked.addListener(new SignalListener<WMouseEvent>()
                {
                    public void notify(WMouseEvent a) 
                    {
                        if(seqComboBox.count()==1)
                        {
                            MessageBox.showWarningMessage(tr("form.viralIsolate.warning.minimumOneSequence"));
                        }
                        else
                        {
                            deleteSequence();
                            seqComboBox.enable();
                            addButton.enable();
                        }
                    }
                });
        
        cancelButton.clicked.addListener(new SignalListener<WMouseEvent>()
                {
                    public void notify(WMouseEvent a) 
                    {                        
                        setSequenceData(((DataComboMessage<NtSequence>)seqComboBox.currentText()).getValue());
                        
                        seqComboBox.enable();
                        addButton.enable();
                    }
                });
        
        confirmButton.clicked.addListener(new SignalListener<WMouseEvent>()
                {
                    public void notify(WMouseEvent a) 
                    {                
                        confirmSequence();
                        
                        seqComboBox.enable();
                        addButton.enable();
                    }
                });
	}
    
    private DataComboMessage<NtSequence> addSeqData()
    {
        String label = getUniqueSequenceLabel(viralIsolateForm_.getViralIsolate());
        NtSequence newSeq = new NtSequence(viralIsolateForm_.getViralIsolate(), null, label, null, null);
        viralIsolateForm_.getViralIsolate().getNtSequences().add(newSeq);
        
        DataComboMessage<NtSequence> msg = new DataComboMessage<NtSequence>(newSeq, label);
        seqComboBox.addItem(msg);
        
        return msg;
    }
    
    private void setSequenceData(NtSequence seq)
    {
        seqLabelTF.setText(seq.getLabel());
        seqDateTF.setDate(seq.getSequenceDate());
        ntTF.setText(seq.getNucleotides());
    }
    
    private String getUniqueSequenceLabel(ViralIsolate vi)
    {
        int largestLabel = 0;
        List<Integer> labelNumbers = new ArrayList<Integer>();
        int labelNumber = 0;
        
        for(NtSequence ntseq : vi.getNtSequences())
        {
            String label = ntseq.getLabel();

            if(label!=null)
            {
                if(label.startsWith(defaultSequenceLabel_));
                {
                    label = label.substring(label.lastIndexOf(" ")+1, ntseq.getLabel().length());
                    try
                    {
                        labelNumber = Integer.parseInt(label);
                        labelNumbers.add(labelNumber);
                    }
                    catch(NumberFormatException e)
                    {
                        //do nothing if it isn't a parsable number
                    }
                }
            }
        }
        Collections.sort(labelNumbers);
        largestLabel++;
        
        
        return defaultSequenceLabel_ + (labelNumbers.size()!=0?(labelNumbers.get(labelNumbers.size()-1) + 1):1);
    }
    
    private void deleteSequence()
    {
        NtSequence currentSequence = ((DataComboMessage<NtSequence>)seqComboBox.currentText()).getValue();
        removedSequences.add(currentSequence);
        seqComboBox.removeItem(seqComboBox.currentIndex());
        
        seqComboBox.setCurrentIndex(seqComboBox.count()-1);
        
        setSequenceData(((DataComboMessage<NtSequence>)seqComboBox.currentText()).getValue());
    }
    
    private void confirmSequence()
    {
        if(validateSequenceFields())
        {
            NtSequence currentSeq = ((DataComboMessage<NtSequence>)seqComboBox.currentText()).getValue();
            currentSeq.setLabel(seqLabelTF.getFormText());
            currentSeq.setSequenceDate(seqDateTF.getDate());
            currentSeq.setNucleotides(ntTF.getFormText());
        }
    }
    
    private boolean validateSequenceFields()
    {
        boolean valid = true;
                
        if(seqLabelTF.validate())
        {
            seqLabelTF.flagValid();
        }
        else
        {
            seqLabelTF.flagErroneous();
            valid = false;
        }
        
        if(seqDateTF.validate())
        {
            seqDateTF.flagValid();
        }
        else
        {
            seqDateTF.flagErroneous();
            valid = false;
        }
        
        if(ntTF.validate())
        {
            ntTF.flagValid();
        }
        else
        {
            ntTF.flagErroneous();
            valid = false;
        }
        
        return valid;
    }
    
    public void saveData(Transaction t)
    {
        confirmSequence();
        
        for(NtSequence ntseq : removedSequences)
        {
            t.delete(ntseq);
            viralIsolateForm_.getViralIsolate().getNtSequences().remove(ntseq);
        }
        
        viralIsolateForm_.getViralIsolate().setSampleDate(sampleDateTF.getDate());
        viralIsolateForm_.getViralIsolate().setSampleId(sampleIdTF.getFormText());
    }

    private void addButtons()
    {
        sequenceGroupTable_.elementAt(0, 1).setContentAlignment(WHorizontalAlignment.AlignRight);
        WTable buttonTable = new WTable(sequenceGroupTable_.elementAt(0, 1));
        sequenceGroupTable_.elementAt(0, 1).setInline(true);
        // add-delete
        addButton = new WPushButton(tr("form.viralIsolate.addButton"), buttonTable.elementAt(0, 0));
        deleteButton = new WPushButton(tr("form.viralIsolate.deleteButton"), buttonTable.elementAt(0, 1));
        // confirm-cancel
        WTable buttonsTable = new WTable(ntSeqGroupTable_.elementAt(3, 1));
        confirmButton = new WPushButton(tr("form.viralIsolate.confirmButton"), buttonsTable.elementAt(0, 0));
        cancelButton = new WPushButton(tr("form.viralIsolate.cancelButton"), buttonsTable.elementAt(0, 1));
        sequenceGroupTable_.elementAt(3, 1).setContentAlignment(WHorizontalAlignment.AlignRight);
       
        boolean sensitivities = viralIsolateForm_.isEditable();
        addButton.setEnabled(sensitivities);
        deleteButton.setEnabled(sensitivities);
        confirmButton.setEnabled(sensitivities);
        cancelButton.setEnabled(sensitivities);
    }
    
    private void setFieldListeners()
    {
        seqLabelTF.addChangeListener(new SignalListener<WEmptyEvent>()
                    {
                        public void notify(WEmptyEvent a) 
                        {
                            seqComboBox.disable();
                            addButton.disable();
                        }
                    });
                    
        seqDateTF.addChangeListener(new SignalListener<WEmptyEvent>()
                    {
                        public void notify(WEmptyEvent a) 
                        {
                            seqComboBox.disable();
                            addButton.disable();
                        }
                    });
                    
        ntTF.addChangeListener(new SignalListener<WEmptyEvent>()
                    {
                        public void notify(WEmptyEvent a) 
                        {
                            seqComboBox.disable();
                            addButton.disable();
                        }
                    });
    }
}
