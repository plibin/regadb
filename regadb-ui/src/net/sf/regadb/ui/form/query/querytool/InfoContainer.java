package net.sf.regadb.ui.form.query.querytool;

import java.io.File;
import java.io.IOException;

import net.sf.regadb.ui.framework.forms.FormWidget;
import net.sf.regadb.ui.framework.forms.InteractionState;
import net.sf.regadb.ui.framework.forms.fields.FileUpload;
import net.sf.regadb.ui.framework.forms.fields.FileUpload.FileBlob;
import net.sf.regadb.ui.framework.forms.fields.Label;
import net.sf.regadb.ui.framework.forms.fields.TextArea;
import net.sf.regadb.ui.framework.forms.fields.TextField;
import net.sf.regadb.ui.framework.widgets.formtable.FormTable;

import org.apache.commons.io.FileUtils;

import eu.webtoolkit.jwt.Signal1;
import eu.webtoolkit.jwt.WContainerWidget;
import eu.webtoolkit.jwt.WKeyEvent;
import eu.webtoolkit.jwt.WMemoryResource;

public class InfoContainer extends WContainerWidget {
    private Label nameL;
    private TextField nameTF;
    private Label descriptionL;
    private TextArea descriptionTA;
    private Label creatorL;
    private TextField creatorTF;
    private Label postProcessingL;
    private FileUpload postProcessingFU;
	
	public InfoContainer(QueryToolApp mainForm, FormWidget form) {
		super();
		init(mainForm, form);
	}
	
	private void init(final QueryToolApp mainForm, FormWidget form) {
		setStyleClass("infofield");

		
		FormTable infoTable = new FormTable(this);
		
    	nameL = new Label(tr("form.query.definition.label.name"));
    	nameTF = new TextField(form.getInteractionState(), form);
        nameTF.setMandatory(true);
        infoTable.addLineToTable(nameL, nameTF);
        nameTF.keyPressed().addListener(this, new Signal1.Listener<WKeyEvent>() {
			public void trigger(WKeyEvent a) {
				mainForm.getEditorModel().getQueryEditor().setDirty(true);
			}
        });
        
        descriptionL = new Label(tr("form.query.definition.label.description"));
        descriptionTA = new TextArea(form.getInteractionState(), form);
        descriptionTA.setMandatory(true);
        infoTable.addLineToTable(descriptionL, descriptionTA);
        descriptionTA.keyPressed().addListener(this, new Signal1.Listener<WKeyEvent>() {
			public void trigger(WKeyEvent a) {
				mainForm.getEditorModel().getQueryEditor().setDirty(true);
			}
        });
		
        postProcessingL = new Label(tr("form.query.definition.label.postProcessing"));
        postProcessingFU = new FileUpload(form.getInteractionState(), form);
        postProcessingFU.setMandatory(false);
        infoTable.addLineToTable(postProcessingL, postProcessingFU);
        
        if(form.getInteractionState() == InteractionState.Viewing)
        {
        	creatorL = new Label(tr("form.query.definition.label.creator"));
            creatorTF = new TextField(form.getInteractionState(), form);
            infoTable.addLineToTable(creatorL, creatorTF);
        }	
	}
	
	public void setPostProcessingScript(FileBlob script) {
        WMemoryResource memResource = new WMemoryResource("application/txt");
        memResource.suggestFileName(script.fileName);
        memResource.setData(script.data.getBytes());
        postProcessingFU.setAnchor(script.fileName, memResource);
	}
	
	public void setName(String name) {
		nameTF.setText(name);
	}
	
	public void setDescription(String description) {
		descriptionTA.setText(description);
	}
	
	public void setUser(String uid) {
		if (creatorTF != null) {
            creatorTF.setText(uid);
		}
	}
	
	public String getName() {
		return nameTF.getFormText();
	}
	
	public String getDescription() {
		return descriptionTA.getFormText();
	}
	
	public FileBlob getPostProcessingScript() {
		String spool = postProcessingFU.getFileUpload().getSpoolFileName();
		if (!spool.equals("")) {
			try {
				return new FileBlob(postProcessingFU.getFileUpload().getClientFileName(),
							FileUtils.readFileToString(new File(spool)));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public boolean isValid() {
		return nameTF.validate() && descriptionTA.validate();
	}
	
}
