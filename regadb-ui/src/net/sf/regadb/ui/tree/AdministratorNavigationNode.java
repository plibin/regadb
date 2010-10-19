package net.sf.regadb.ui.tree;

import java.io.File;

import net.sf.regadb.db.Attribute;
import net.sf.regadb.db.AttributeGroup;
import net.sf.regadb.db.Dataset;
import net.sf.regadb.db.DatasetAccess;
import net.sf.regadb.db.Event;
import net.sf.regadb.db.ResistanceInterpretationTemplate;
import net.sf.regadb.db.SettingsUser;
import net.sf.regadb.db.Test;
import net.sf.regadb.db.TestType;
import net.sf.regadb.ui.datatable.attributeSettings.SelectAttributeForm;
import net.sf.regadb.ui.datatable.attributeSettings.SelectAttributeGroupForm;
import net.sf.regadb.ui.datatable.datasetSettings.SelectDatasetAccessUserForm;
import net.sf.regadb.ui.datatable.datasetSettings.SelectDatasetForm;
import net.sf.regadb.ui.datatable.importTool.SelectImportToolForm;
import net.sf.regadb.ui.datatable.log.SelectLogForm;
import net.sf.regadb.ui.datatable.settingsUser.SelectSettingsUserForm;
import net.sf.regadb.ui.datatable.testSettings.SelectResRepTemplateForm;
import net.sf.regadb.ui.datatable.testSettings.SelectTestForm;
import net.sf.regadb.ui.datatable.testSettings.SelectTestTypeForm;
import net.sf.regadb.ui.form.administrator.UpdateForm;
import net.sf.regadb.ui.form.administrator.VersionForm;
import net.sf.regadb.ui.form.attributeSettings.AttributeForm;
import net.sf.regadb.ui.form.attributeSettings.AttributeGroupForm;
import net.sf.regadb.ui.form.batchtest.BatchTestAddForm;
import net.sf.regadb.ui.form.batchtest.BatchTestRunningForm;
import net.sf.regadb.ui.form.datasetSettings.DatasetAccessForm;
import net.sf.regadb.ui.form.datasetSettings.DatasetForm;
import net.sf.regadb.ui.form.event.EventForm;
import net.sf.regadb.ui.form.impex.ExportForm;
import net.sf.regadb.ui.form.impex.ImportFormAdd;
import net.sf.regadb.ui.form.impex.ImportFormRunning;
import net.sf.regadb.ui.form.importTool.ImportToolForm;
import net.sf.regadb.ui.form.importTool.data.ImportDefinition;
import net.sf.regadb.ui.form.log.LogForm;
import net.sf.regadb.ui.form.testTestTypes.ResistanceInterpretationTemplateForm;
import net.sf.regadb.ui.form.testTestTypes.TestForm;
import net.sf.regadb.ui.form.testTestTypes.TestTypeForm;
import net.sf.regadb.ui.framework.RegaDBMain;
import net.sf.regadb.ui.framework.forms.IForm;
import net.sf.regadb.ui.framework.forms.InteractionState;
import net.sf.regadb.ui.framework.tree.TreeMenuNode;
import net.sf.regadb.ui.tree.items.events.SelectEventForm;
import eu.webtoolkit.jwt.WResource;
import eu.webtoolkit.jwt.WString;

public class AdministratorNavigationNode extends DefaultNavigationNode{
	
	private SelectedItemNavigationNode<DatasetAccess> datasetAccessSelected;
	private FormNavigationNode datasetAccessSelect;

	public AdministratorNavigationNode(TreeMenuNode parent) {
		super(WString.tr("menu.administrator.administrator"), parent);
		
		DefaultNavigationNode attributeSettings = new DefaultNavigationNode(WString.tr("menu.attributeSettings.attributeSettings"),this);
		new ObjectTreeNode<Attribute>("attributeSettings.attributes", attributeSettings){

			@Override
			protected IForm createForm(WString name, InteractionState interactionState, Attribute selectedObject) {
				return new AttributeForm(interactionState, name, selectedObject);
			}

			@Override
			protected IForm createSelectionForm() {
				return new SelectAttributeForm();
			}

			@Override
			public String getArgument(Attribute type) {
				return type.getName();
			}
		};
		
		new ObjectTreeNode<AttributeGroup>("attributeSettings.attributeGroups", attributeSettings){

			@Override
			protected IForm createForm(WString name, InteractionState interactionState, AttributeGroup selectedObject) {
				return new AttributeGroupForm(interactionState, name, selectedObject);
			}

			@Override
			protected IForm createSelectionForm() {
				return new SelectAttributeGroupForm();
			}

			@Override
			public String getArgument(AttributeGroup type) {
				return type.getGroupName();
			}
			
		};
		
		DefaultNavigationNode testSettings = new DefaultNavigationNode(WString.tr("menu.testSettings.testSettings"), this);
		
		new ObjectTreeNode<TestType>("testSettings.testTypes", testSettings){
			@Override
			protected IForm createForm(WString name, InteractionState interactionState, TestType selectedObject) {
				return new TestTypeForm(interactionState, name, selectedObject);
			}

			@Override
			protected IForm createSelectionForm() {
				return new SelectTestTypeForm();
			}

			@Override
			public String getArgument(TestType type) {
				return type.getDescription();
			}
		};
		
		new ObjectTreeNode<Test>("testSettings.tests", testSettings){
			@Override
			protected IForm createForm(WString name, InteractionState interactionState, Test selectedObject) {
				return new TestForm(interactionState,name,selectedObject);
			}

			@Override
			protected IForm createSelectionForm() {
				return new SelectTestForm();
			}

			@Override
			public String getArgument(Test type) {
				return type.getDescription();
			}
		};
		
		new ObjectTreeNode<ResistanceInterpretationTemplate>("resistance.report.template", testSettings){
			@Override
			protected IForm createForm(WString name, InteractionState interactionState, ResistanceInterpretationTemplate selectedObject) {
				return new ResistanceInterpretationTemplateForm(interactionState, name, selectedObject);
			}

			@Override
			protected IForm createSelectionForm() {
				return new SelectResRepTemplateForm();
			}

			@Override
			public String getArgument(ResistanceInterpretationTemplate type) {
				return type.getName();
			}
		};
		
		new ObjectTreeNode<Event>("event", this){
			@Override
			protected IForm createForm(WString name, InteractionState interactionState, Event selectedObject) {
				return new EventForm(interactionState, name, selectedObject);
			}

			@Override
			protected IForm createSelectionForm() {
				return new SelectEventForm();
			}

			@Override
			public String getArgument(Event type) {
				return type.getName();
			}
		};
		
		DefaultNavigationNode datasetSettings = new DefaultNavigationNode(WString.tr("menu.datasetSettings.dataset"), this);
		
		new ObjectTreeNode<Dataset>("datasetSettings.dataset",datasetSettings){
			@Override
			protected IForm createForm(WString name, InteractionState interactionState, Dataset selectedObject) {
				return new DatasetForm(interactionState, name, selectedObject);
			}

			@Override
			protected IForm createSelectionForm() {
				return new SelectDatasetForm();
			}

			@Override
			public String getArgument(Dataset type) {
				return type.getDescription();
			}
		};
		
		DefaultNavigationNode datasetAccess = new DefaultNavigationNode(WString.tr("menu.dataset.access"), datasetSettings);
		
		datasetAccessSelect = new FormNavigationNode(WString.tr("menu.dataset.access.select"),datasetSettings){
			@Override
			public IForm createForm() {
				return new SelectDatasetAccessUserForm();
			}
		};
		
        datasetAccessSelected = new SelectedItemNavigationNode<DatasetAccess>(WString.tr("menu.dataset.acces.SelectedItem"), datasetAccess){
        	public DatasetAccess getSelectedItem(){
        		//TODO
        		return null;
        	}
        };
        
        new FormNavigationNode(WString.tr("menu.dataset.acces.view"), datasetAccessSelected){
        	@Override
        	public IForm createForm(){
        		return new DatasetAccessForm(InteractionState.Viewing, WString.tr("form.dataset.access.view"),null);//datasetAccessSelected.getSelectedItem());
        	}
        };
        
        new FormNavigationNode(WString.tr("menu.dataset.access.edit"), datasetAccessSelected){
        	@Override
        	public IForm createForm(){
        		return new DatasetAccessForm(InteractionState.Editing, WString.tr("form.dataset.access.edit"),null);//datasetAccessSelected.getSelectedItem());
        	}
        };
        
        
        ObjectTreeNode<SettingsUser> settingsUser = new ObjectTreeNode<SettingsUser>("administrator.users", this){

			@Override
			protected IForm createForm(WString name, InteractionState interactionState, SettingsUser selectedObject) {
//TODO				return new AccountForm(interactionState,name,selectedObject);
				return null;
			}

			@Override
			protected IForm createSelectionForm() {
				return new SelectSettingsUserForm();
			}

			@Override
			public String getArgument(SettingsUser type) {
				return type.getUid() == null ? type.getFirstName() : type.getUid();
			}
        	
        };
        new FormNavigationNode(WString.tr("form.settings.user.password"), settingsUser.getSelectedItemNavigationNode()){

			@Override
			public IForm createForm() {
//TODO				return new PasswordForm(InteractionState.Editing, WString.tr("menu.myAccount.passwordForm"), settingsUser.getSelectedItem());
				return null;
			}
        };
        
        
        DefaultNavigationNode update = new DefaultNavigationNode(WString.tr("menu.administrator.updateFromCentralRepos"), this);
        new FormNavigationNode(WString.tr("menu.administrator.updateFromCentralRepos.update.view"), update)
        {
            public IForm createForm()
            {
                return new UpdateForm(WString.tr("form.update_central_server.view"), InteractionState.Viewing);
            }
        };
        new FormNavigationNode(WString.tr("menu.administrator.updateFromCentralRepos.update"), update)
        {
            public IForm createForm()
            {
                return new UpdateForm(WString.tr("form.update_central_server"),InteractionState.Editing);
            }
        };
        
        new ObjectTreeNode<ImportDefinition>("importTool", this){
			@Override
			protected IForm createForm(WString name, InteractionState interactionState, ImportDefinition selectedObject) {
				return new ImportToolForm(interactionState, name, selectedObject);
			}

			@Override
			protected IForm createSelectionForm() {
				return new SelectImportToolForm();
			}

			@Override
			public String getArgument(ImportDefinition type) {
				return type.getDescription();
			}
        };
        
        DefaultNavigationNode importXML = new DefaultNavigationNode(WString.tr("menu.impex.import"), this);
        new FormNavigationNode(WString.tr("menu.impex.import.run"), importXML)
        {
            public IForm createForm() 
            {
                return new ImportFormRunning(WString.tr("form.impex.import.title"), InteractionState.Viewing);
            }
        };
        new FormNavigationNode(WString.tr("menu.impex.import.add"), importXML)
        {
            public IForm createForm() 
            {
                return new ImportFormAdd(WString.tr("form.impex.import.title"), InteractionState.Adding);
            }
        };
        
        new FormNavigationNode(WString.tr("menu.impex.export"), this)
        {
            public IForm createForm() 
            {
                return new ExportForm(WResource.tr("form.impex.export.title"), InteractionState.Viewing);
            }
        };
        
        DefaultNavigationNode batchTest = new DefaultNavigationNode(WString.tr("menu.batchtest"), this);
        
        new FormNavigationNode(WString.tr("menu.batchtest.running"), batchTest) {
            public IForm createForm() 
            {
                return new BatchTestRunningForm(WString.tr("form.batchtest.title"), InteractionState.Viewing);
            }
        };

        new FormNavigationNode(WString.tr("menu.batchtest.add"), batchTest) {
            public IForm createForm() 
            {
                return new BatchTestAddForm(WString.tr("form.batchtest.title"), InteractionState.Adding);
            }
        };
        
        
        new ObjectTreeNode<File>("log",this){

			@Override
			protected IForm createForm(WString name, InteractionState interactionState, File selectedObject) {
				return new LogForm(name,interactionState,selectedObject);
			}

			@Override
			protected IForm createSelectionForm() {
				return new SelectLogForm();
			}

			@Override
			public String getArgument(File type) {
				return type.getName();
			}
        	
        };
        
        new FormNavigationNode(WString.tr("menu.version"),this)
        {
            public IForm createForm() 
            {
                return new VersionForm();
            }
        };
	}

    @Override
    public boolean isDisabled()
    {
        return RegaDBMain.getApp().getLogin()==null
            || !RegaDBMain.getApp().getRole().isAdmin();
    }
}
