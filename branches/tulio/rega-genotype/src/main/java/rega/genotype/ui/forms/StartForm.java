/*
 * Copyright (C) 2008 Rega Institute for Medical Research, KULeuven
 * 
 * See the LICENSE file for terms of use.
 */
package rega.genotype.ui.forms;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import rega.genotype.FileFormatException;
import rega.genotype.ParameterProblemException;
import rega.genotype.SequenceAlignment;
import rega.genotype.ui.framework.GenotypeWindow;
import rega.genotype.ui.util.FileUpload;
import rega.genotype.ui.util.GenotypeLib;
import rega.genotype.ui.util.Settings;
import eu.webtoolkit.jwt.Signal;
import eu.webtoolkit.jwt.Signal1;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WInteractWidget;
import eu.webtoolkit.jwt.WLineEdit;
import eu.webtoolkit.jwt.WMouseEvent;
import eu.webtoolkit.jwt.WPushButton;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WTextArea;

/**
 * StartForm implementation implements a widget which allows the user to submit
 * sequences for a new job or get the results of an existing job using the job Id.
 */
public class StartForm extends AbstractForm {
	private WText note;
	private WTextArea ta;
	private WPushButton run, clear;
	private FileUpload fileUpload;
	
	private WLineEdit jobIdTF;
	private WPushButton monitorButton;

	private WText errorJobId, errorSeq;
	
	public StartForm(GenotypeWindow main) {
		super(main, "start-form");

		List<String> noteArgs = new ArrayList<String>();
		noteArgs.add(Settings.getInstance().getMaxAllowedSeqs()+"");
		note = new WText(getMain().getResourceManager().getOrganismValue("start-form", "note", noteArgs), this);
		note.setId("");
		note.setStyleClass("note");
		
		WContainerWidget seqinput = new WContainerWidget(this);
		seqinput.setId("");
		seqinput.setStyleClass("seqInput");

		WText h = new WText(tr("sequenceInput.inputSequenceInFastaFormat"), seqinput);
		h.setId("");
		
		WContainerWidget textAreaDiv = new WContainerWidget(seqinput);
		textAreaDiv.setObjectName("seq-input-fasta-div");
		ta = new WTextArea(textAreaDiv);
		ta.setObjectName("seq-input-fasta");
		ta.setColumns(83);
		ta.setRows(15);

		run = new WPushButton(seqinput);
		run.setObjectName("button-run");
		run.setText(tr("sequenceInput.run"));
	
		clear = new WPushButton(seqinput);
		clear.setObjectName("button-clear");
		clear.setText(tr("sequenceInput.clear"));
		clear.clicked().addListener(this, new Signal1.Listener<WMouseEvent>() {
			public void trigger(WMouseEvent a) {
				ta.setText("");
			}
		});
	
		run.clicked().addListener(this, new Signal1.Listener<WMouseEvent>() {
			public void trigger(WMouseEvent a) {
				verifyFasta(ta.getText());
			}
		});
		
		h = new WText(tr("sequenceInput.uploadSequenceInFastaFormat"), seqinput);
		h.setId("");

		fileUpload = new FileUpload();
		fileUpload.setId("sequences-file-upload");
		seqinput.addWidget(fileUpload);
		fileUpload.getUploadFile().uploaded().addListener(this, new Signal.Listener() {
            public void trigger() {                
				try {
					if (!fileUpload.getUploadFile().getSpoolFileName().equals("")) {
						String fasta = GenotypeLib.readFileToString(new File(fileUpload.getUploadFile().getSpoolFileName()));
						verifyFasta(fasta);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
		errorSeq = new WText(tr("startForm.errorSequence"), seqinput);
		errorSeq.setId("sequence-error-message");
		errorSeq.setStyleClass("error");
		errorSeq.hide();		

		h = new WText(tr("startForm.monitorJob"), seqinput);
		h.setId("");
		h = new WText(tr("startForm.labelJobId"), seqinput);
		h.setId("");
		jobIdTF = new WLineEdit(seqinput);
		jobIdTF.setObjectName("job-input-field");
		monitorButton = new WPushButton(tr("startForm.monitor"), seqinput);
		monitorButton.clicked().addListener(this, new Signal1.Listener<WMouseEvent>() {
			public void trigger(WMouseEvent a) {
				File jobDir = getMain().getJobDir(jobIdTF.getText());
				if (jobDir.exists()) {
					setValid(jobIdTF, errorJobId);
					getMain().monitorForm(jobDir, true);
				} else {
					setInvalid(jobIdTF, errorJobId);
				}
			}
		});
		errorJobId = new WText(tr("startForm.errorJobId"), seqinput);
		errorJobId.setId("jobid-error-message");
		errorJobId.setStyleClass("error");
		errorJobId.hide();
	}
	
	private void setValid(WInteractWidget w, WText errorMsg){
		w.setStyleClass("edit-valid");
		errorMsg.hide();
	}
	private void setInvalid(WInteractWidget w, WText errorMsg){
		w.setStyleClass("edit-invalid");
		errorMsg.show();
	}
	
	private void startJob(final String fastaContent) {
		final File thisJobDir = GenotypeLib.createJobDir(getMain().getOrganismDefinition());

		Thread analysis = new Thread(new Runnable(){
			public void run() {
				try {
					File seqFile = new File(thisJobDir.getAbsolutePath()+File.separatorChar+"sequences.fasta");
					GenotypeLib.writeStringToFile(seqFile, fastaContent);
					getMain().getOrganismDefinition().startAnalysis(thisJobDir);
					File done = new File(thisJobDir.getAbsolutePath()+File.separatorChar+"DONE");
					GenotypeLib.writeStringToFile(done, System.currentTimeMillis()+"");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParameterProblemException e) {
					e.printStackTrace();
				} catch (FileFormatException e) {
					e.printStackTrace();
				}
			}
		});
		analysis.start();
		
		getMain().monitorForm(thisJobDir, true);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		List seqs = getMain().getResourceManager().getOrganismElement("exampleSequences-form", "exampleSequences-sequences").getChildren();
		
		if(seqs.size()>0) {
			Element seq = (Element)seqs.get(0);
			StringBuilder text = new StringBuilder(">" + seq.getAttributeValue("name") + "\n");
			final int nucleotidesPerLine = 80;
			int counter = 0;
			String nucleotides = seq.getTextTrim();
			for(int i = 0; i<nucleotides.length(); i++) {
				if(!Character.isWhitespace(nucleotides.charAt(i))) {
					text.append(nucleotides.charAt(i));
					counter++;
					if(counter%nucleotidesPerLine==0) {
						text.append("\n");
					}
				}
			}
			ta.setText(text.toString());
		}
	}

	private void verifyFasta(String fastaContent) {
		int sequenceCount = 0;

		LineNumberReader r = new LineNumberReader(new StringReader(fastaContent));

		try {
			while (true) {
				if (SequenceAlignment.readFastaFileSequence(r, SequenceAlignment.SEQUENCE_DNA)
						== null)
					break;
				++sequenceCount;
			}

			if (sequenceCount <= Settings.getInstance().getMaxAllowedSeqs()) {
				setValid(ta, errorSeq);
				startJob(fastaContent);
			}

		} catch (IOException e) {
			errorSeq.setText("I/O error reading the sequence.");
			setInvalid(ta, errorSeq);
			e.printStackTrace();
		} catch (FileFormatException e) {
			errorSeq.setText(e.getMessage());
			setInvalid(ta, errorSeq);
		}
	}
}