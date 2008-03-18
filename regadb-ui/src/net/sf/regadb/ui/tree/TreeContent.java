package net.sf.regadb.ui.tree;

import net.sf.regadb.db.Dataset;
import net.sf.regadb.db.QueryDefinition;
import net.sf.regadb.db.QueryDefinitionRun;
import net.sf.regadb.db.SettingsUser;
import net.sf.regadb.db.session.Login;
import net.sf.regadb.ui.datatable.attributeSettings.SelectAttributeForm;
import net.sf.regadb.ui.datatable.attributeSettings.SelectAttributeGroupForm;
import net.sf.regadb.ui.datatable.datasetSettings.SelectDatasetAccessUserForm;
import net.sf.regadb.ui.datatable.datasetSettings.SelectDatasetForm;
import net.sf.regadb.ui.datatable.measurement.SelectMeasurementForm;
import net.sf.regadb.ui.datatable.query.SelectQueryDefinitionForm;
import net.sf.regadb.ui.datatable.query.SelectQueryDefinitionRunForm;
import net.sf.regadb.ui.datatable.settingsUser.SelectSettingsUserForm;
import net.sf.regadb.ui.datatable.testSettings.SelectResRepTemplateForm;
import net.sf.regadb.ui.datatable.testSettings.SelectTestForm;
import net.sf.regadb.ui.datatable.testSettings.SelectTestTypeForm;
import net.sf.regadb.ui.datatable.therapy.SelectTherapyForm;
import net.sf.regadb.ui.datatable.viralisolate.SelectViralIsolateForm;
import net.sf.regadb.ui.form.administrator.UpdateForm;
import net.sf.regadb.ui.form.attributeSettings.AttributeForm;
import net.sf.regadb.ui.form.attributeSettings.AttributeGroupForm;
import net.sf.regadb.ui.form.datasetSettings.DatasetAccessForm;
import net.sf.regadb.ui.form.datasetSettings.DatasetForm;
import net.sf.regadb.ui.form.event.EventForm;
import net.sf.regadb.ui.form.query.QueryDefinitionForm;
import net.sf.regadb.ui.form.query.QueryDefinitionRunForm;
import net.sf.regadb.ui.form.singlePatient.MeasurementForm;
import net.sf.regadb.ui.form.singlePatient.PatientEventForm;
import net.sf.regadb.ui.form.singlePatient.SinglePatientForm;
import net.sf.regadb.ui.form.singlePatient.TherapyForm;
import net.sf.regadb.ui.form.singlePatient.ViralIsolateForm;
import net.sf.regadb.ui.form.singlePatient.chart.PatientChartForm;
import net.sf.regadb.ui.form.testTestTypes.ResistanceInterpretationTemplateForm;
import net.sf.regadb.ui.form.testTestTypes.TestForm;
import net.sf.regadb.ui.form.testTestTypes.TestTypeForm;
import net.sf.regadb.ui.forms.account.AccountForm;
import net.sf.regadb.ui.forms.account.PasswordForm;
import net.sf.regadb.ui.framework.RegaDBMain;
import net.sf.regadb.ui.framework.forms.InteractionState;
import net.sf.regadb.ui.framework.forms.action.ITreeAction;
import net.sf.regadb.ui.framework.tree.RootMenuNode;
import net.sf.regadb.ui.framework.tree.TreeMenuNode;
import net.sf.regadb.ui.tree.items.administrator.AdministratorItem;
import net.sf.regadb.ui.tree.items.administrator.NotRegisteredUserSelectedItem;
import net.sf.regadb.ui.tree.items.administrator.RegisteredUserSelectedItem;
import net.sf.regadb.ui.tree.items.attributeSettings.AttributeGroupSelectedItem;
import net.sf.regadb.ui.tree.items.attributeSettings.AttributeSelectedItem;
import net.sf.regadb.ui.tree.items.datasetSettings.DatasetAccessSelectedItem;
import net.sf.regadb.ui.tree.items.datasetSettings.DatasetSelectedItem;
import net.sf.regadb.ui.tree.items.events.EventSelectedItem;
import net.sf.regadb.ui.tree.items.events.SelectEventForm;
import net.sf.regadb.ui.tree.items.myAccount.LoginItem;
import net.sf.regadb.ui.tree.items.myAccount.LogoutItem;
import net.sf.regadb.ui.tree.items.myAccount.MyAccountItem;
import net.sf.regadb.ui.tree.items.query.QueryDefinitionItem;
import net.sf.regadb.ui.tree.items.query.QueryDefinitionRunItem;
import net.sf.regadb.ui.tree.items.query.QueryDefinitionRunSelectedItem;
import net.sf.regadb.ui.tree.items.query.QueryDefinitionSelectedItem;
import net.sf.regadb.ui.tree.items.query.QueryItem;
import net.sf.regadb.ui.tree.items.singlePatient.ActionItem;
import net.sf.regadb.ui.tree.items.singlePatient.MeasurementSelectedItem;
import net.sf.regadb.ui.tree.items.singlePatient.PatientAddItem;
import net.sf.regadb.ui.tree.items.singlePatient.PatientEventSelectedItem;
import net.sf.regadb.ui.tree.items.singlePatient.PatientItem;
import net.sf.regadb.ui.tree.items.singlePatient.PatientSelectItem;
import net.sf.regadb.ui.tree.items.singlePatient.PatientSelectedItem;
import net.sf.regadb.ui.tree.items.singlePatient.SelectPatientEvent;
import net.sf.regadb.ui.tree.items.singlePatient.TherapySelectedItem;
import net.sf.regadb.ui.tree.items.singlePatient.ViralIsolateSelectedItem;
import net.sf.regadb.ui.tree.items.testSettings.ResRepTemplateSelectedItem;
import net.sf.regadb.ui.tree.items.testSettings.TestSelectedItem;
import net.sf.regadb.ui.tree.items.testSettings.TestTypeSelectedItem;
import net.sf.witty.wt.WWidget;

public class TreeContent
{
    public PatientItem singlePatientMain;
    public PatientSelectItem patientSelect;
    public PatientAddItem patientAdd;
    public PatientSelectedItem patientSelected;
    public ActionItem viewPatient;
    public ActionItem editPatient;
    public ActionItem deletePatient;
    public ActionItem chart;
    public ActionItem measurements;
    public ActionItem measurementsSelect;
    public MeasurementSelectedItem measurementSelected;
    public ActionItem measurementView;
    public ActionItem measurementEdit;
    public ActionItem measurementsAdd;
    public ActionItem measurementsDelete;
    public ActionItem therapies;
    public ActionItem therapiesSelect;
    public ActionItem therapiesAdd;
    public ActionItem therapiesDelete;
    public TherapySelectedItem therapiesSelected;
    public ActionItem therapiesEdit;
    public ActionItem therapiesView;
    public ActionItem viralIsolates;
    public ActionItem viralIsolatesSelect;
    public ActionItem viralIsolatesAdd;
    public ActionItem viralIsolatesDelete;
    public ViralIsolateSelectedItem viralIsolateSelected;
    public ActionItem viralIsolateView;
    public ActionItem viralIsolateEdit;
    
    public MyAccountItem myAccountMain;
    public LoginItem myAccountLogin;
    public ActionItem myAccountView;
    public ActionItem myAccountEdit;
    public ActionItem myAccountCreate;
    public ActionItem myAccountEditPassword;
    //public ActionItem datasetAccess;
    public LogoutItem myAccountLogout;
    
    public ActionItem datasetSettings;
    public ActionItem datasets;
    public ActionItem datasetSelect;
    public ActionItem datasetAdd;
    public ActionItem datasetEdit;
    public ActionItem datasetView;
    public ActionItem datasetDelete;
    public DatasetSelectedItem datasetSelected;
    
    public ActionItem datasetAccess;
    public ActionItem datasetAccessSelect;
    public DatasetAccessSelectedItem datasetAccessSelected;
    public ActionItem datasetAccessView;
    public ActionItem datasetAccessEdit;
    
    public QueryItem queryMain;
    public QueryDefinitionItem queryDefinitionMain;
    public QueryDefinitionSelectedItem queryDefinitionSelected;
    public ActionItem queryDefinitionSelect;
    public ActionItem queryDefinitionAdd;
    public ActionItem queryDefinitionSelectedView;
    public ActionItem queryDefinitionSelectedEdit;
    public ActionItem queryDefinitionSelectedDelete;
    public ActionItem queryDefinitionSelectedRun;
    public QueryDefinitionRunItem queryDefinitionRunMain;
    public QueryDefinitionRunSelectedItem queryDefinitionRunSelected;
    public ActionItem queryDefinitionRunSelect;
    public ActionItem queryDefinitionRunSelectedView;
    public ActionItem queryDefinitionRunSelectedDelete;
    
    public AdministratorItem administratorMain;
    public ActionItem enabledUsers;
    public ActionItem registeredUsersSelect;
    public RegisteredUserSelectedItem registeredUserSelected;
    public ActionItem registeredUserDelete;
    public ActionItem registeredUsersView;
    public ActionItem registeredUsersEdit;
    public ActionItem registeredUsersDelete;
    public ActionItem registeredUsersChangePassword;
    public ActionItem disabledUsers;
    public ActionItem notRegisteredUsersSelect;
    public NotRegisteredUserSelectedItem notRegisteredUserSelected;
    public ActionItem notRegisteredUsersView;
    public ActionItem notRegisteredUsersEdit;
    public ActionItem notRegisteredUsersDelete;
    public ActionItem notRegisteredUsersChangePassword;
    public ActionItem updateFromCentralServer;
    public ActionItem updateFromCentralServerUpdate;
    public ActionItem updateFromCentralServerUpdateView;

    public ActionItem attributesSettings;
    public ActionItem attributes;
    public ActionItem attributesSelect;
    public ActionItem attributesAdd;
    public AttributeSelectedItem attributesSelected;
    public ActionItem attributesView;
    public ActionItem attributesEdit;
    public ActionItem attributesDelete;
    public ActionItem attributeGroups;
    public ActionItem attributeGroupsSelect;
    public ActionItem attributeGroupsAdd;
    public AttributeGroupSelectedItem attributeGroupsSelected;
    public ActionItem attributeGroupsView;
    public ActionItem attributeGroupsEdit;
    public ActionItem attributeGroupsDelete;
    
    public ActionItem testSettings;
    public ActionItem testTypes;
    public ActionItem testTypesSelect;
    public ActionItem testTypesAdd;
    public ActionItem testTypesView;
    public ActionItem testTypesEdit;
    public ActionItem testTypesDelete;
    public TestTypeSelectedItem testTypeSelected;
    
    public ActionItem test;
    public ActionItem testSelect;
    public ActionItem testAdd;
    public ActionItem testView;
    public ActionItem testEdit;
    public ActionItem testDelete;
    public TestSelectedItem testSelected;
    
    public ActionItem resRepTemplate;
    public ActionItem resRepTemplateSelect;
    public ActionItem resRepTemplateAdd;
    public ActionItem resRepTemplateView;
    public ActionItem resRepTemplateEdit;
    public ActionItem resRepTemplateDelete;
    public ResRepTemplateSelectedItem resRepTemplateSelected;
    
    public ActionItem event;
    public ActionItem eventSelect;
    public ActionItem eventAdd;
    public EventSelectedItem eventSelected;
    public ActionItem eventSelectedView;
    public ActionItem eventSelectedEdit;
    public ActionItem eventSelectedDelete;
    
    public ActionItem patientEvent;
    public ActionItem patientEventSelect;
    public ActionItem patientEventAdd;
    public PatientEventSelectedItem patientEventSelected;
    public ActionItem patientEventView;
    public ActionItem patientEventEdit;
    public ActionItem patientEventDelete;
    
	public TreeMenuNode setContent(RootItem rootItem)
	{
		singlePatientMain = new PatientItem(rootItem);
		    patientSelect = new PatientSelectItem(singlePatientMain);
			patientAdd = new PatientAddItem(singlePatientMain);
            patientSelected = new PatientSelectedItem(singlePatientMain);
                viewPatient = new ActionItem(rootItem.tr("menu.singlePatient.view"), patientSelected, new ITreeAction()
                {
                    public void performAction(TreeMenuNode node) 
                    {
                        RegaDBMain.getApp().getFormContainer().setForm(new SinglePatientForm(InteractionState.Viewing, WWidget.tr("form.singlePatient.view"), patientSelected.getSelectedItem()));
                    }
                });
                editPatient = new ActionItem(rootItem.tr("menu.singlePatient.edit"), patientSelected, new ITreeAction()
                {
                    public void performAction(TreeMenuNode node) 
                    {
                        RegaDBMain.getApp().getFormContainer().setForm(new SinglePatientForm(InteractionState.Editing, WWidget.tr("form.singlePatient.edit"), patientSelected.getSelectedItem()));
                    }
                });
                deletePatient = new ActionItem(rootItem.tr("menu.singlePatient.delete"), patientSelected, new ITreeAction()
                {
                    public void performAction(TreeMenuNode node) 
                    {
                        RegaDBMain.getApp().getFormContainer().setForm(new SinglePatientForm(InteractionState.Deleting, WWidget.tr("form.singlePatient.delete"), patientSelected.getSelectedItem()));
                    }
                });
				chart = new ActionItem(rootItem.tr("menu.singlePatient.chart"), patientSelected, new ITreeAction()
                {
                    public void performAction(TreeMenuNode node) 
                    {
                        RegaDBMain.getApp().getFormContainer().setForm(new PatientChartForm(patientSelected.getSelectedItem()));
                    }
                });
    			measurements = new ActionItem(rootItem.tr("menu.singlePatient.measurements"), patientSelected);
    				measurementsSelect = new ActionItem(rootItem.tr("menu.singlePatient.measurements.select"), measurements, new ITreeAction()
                    {
                        public void performAction(TreeMenuNode node) 
                        {
                        	RegaDBMain.getApp().getFormContainer().setForm(new SelectMeasurementForm());
                        }
                    });
    				measurementsAdd = new ActionItem(rootItem.tr("menu.singlePatient.measurements.add"), measurements, new ITreeAction()
    				{
						public void performAction(TreeMenuNode node)
						{
							RegaDBMain.getApp().getFormContainer().setForm(new MeasurementForm(InteractionState.Adding, WWidget.tr("form.measurement.add"), null));
						}
    				});
    				measurementSelected = new MeasurementSelectedItem(measurements);
    				measurementView = new ActionItem(rootItem.tr("menu.singlePatient.measurement.view"), measurementSelected, new ITreeAction()
    				{
						public void performAction(TreeMenuNode node)
						{
							RegaDBMain.getApp().getFormContainer().setForm(new MeasurementForm(InteractionState.Viewing, WWidget.tr("form.measurement.view"), measurementSelected.getSelectedItem()));
						}
    				});
    				measurementEdit = new ActionItem(rootItem.tr("menu.singlePatient.measurement.edit"), measurementSelected, new ITreeAction()
    				{
						public void performAction(TreeMenuNode node)
						{
							RegaDBMain.getApp().getFormContainer().setForm(new MeasurementForm(InteractionState.Editing, WWidget.tr("form.measurement.edit"), measurementSelected.getSelectedItem()));
						}
    				});
                    measurementsDelete = new ActionItem(rootItem.tr("menu.singlePatient.measurement.delete"), measurementSelected, new ITreeAction()
                    {
                        public void performAction(TreeMenuNode node)
                        {
                            RegaDBMain.getApp().getFormContainer().setForm(new MeasurementForm(InteractionState.Deleting, WWidget.tr("form.measurement.delete"), measurementSelected.getSelectedItem()));
                        }
                    });

    			therapies = new ActionItem(rootItem.tr("menu.singlePatient.therapies"), patientSelected);
    				therapiesSelect = new ActionItem(rootItem.tr("menu.singlePatient.therapies.select"), therapies, new ITreeAction()
                    {
                        public void performAction(TreeMenuNode node) 
                        {
                        	RegaDBMain.getApp().getFormContainer().setForm(new SelectTherapyForm());
                        }
                    });
    				therapiesAdd = new ActionItem(rootItem.tr("menu.singlePatient.therapies.add"), therapies, new ITreeAction()
    				{
						public void performAction(TreeMenuNode node)
						{
							RegaDBMain.getApp().getFormContainer().setForm(new TherapyForm(InteractionState.Adding, WWidget.tr("form.therapy.add"), null));
						}
    				});
    				therapiesSelected = new TherapySelectedItem(therapies);
    				therapiesView = new ActionItem(rootItem.tr("menu.singlePatient.therapies.view"), therapiesSelected, new ITreeAction()
    				{
						public void performAction(TreeMenuNode node)
						{
							RegaDBMain.getApp().getFormContainer().setForm(new TherapyForm(InteractionState.Viewing, WWidget.tr("form.therapy.view"), therapiesSelected.getSelectedItem()));
						}
    				});
    				therapiesEdit = new ActionItem(rootItem.tr("menu.singlePatient.therapies.edit"), therapiesSelected, new ITreeAction()
    				{
						public void performAction(TreeMenuNode node)
						{
							RegaDBMain.getApp().getFormContainer().setForm(new TherapyForm(InteractionState.Editing, WWidget.tr("form.therapy.edit"), therapiesSelected.getSelectedItem()));
						}
    				});
                    therapiesDelete = new ActionItem(rootItem.tr("menu.singlePatient.therapies.delete"), therapiesSelected, new ITreeAction()
                    {
                        public void performAction(TreeMenuNode node)
                        {
                            RegaDBMain.getApp().getFormContainer().setForm(new TherapyForm(InteractionState.Deleting, WWidget.tr("form.therapy.delete"), therapiesSelected.getSelectedItem()));
                        }
                    });
    				
    				viralIsolates = new ActionItem(rootItem.tr("menu.singlePatient.viralIsolates"), patientSelected);
    				viralIsolatesSelect = new ActionItem(rootItem.tr("menu.singlePatient.viralIsolates.select"), viralIsolates, new ITreeAction()
                    {
    					public void performAction(TreeMenuNode node) 
                        {
                        	RegaDBMain.getApp().getFormContainer().setForm(new SelectViralIsolateForm());
                        }
                    });
                    viralIsolatesAdd = new ActionItem(rootItem.tr("menu.singlePatient.viralIsolates.add"), viralIsolates, new ITreeAction()
                    {
                        public void performAction(TreeMenuNode node)
                        {
                            RegaDBMain.getApp().getFormContainer().setForm(new ViralIsolateForm(InteractionState.Adding, WWidget.tr("form.viralIsolate.add"), null));
                        }
                    });
    				viralIsolateSelected = new ViralIsolateSelectedItem(viralIsolates);
    				viralIsolateView = new ActionItem(rootItem.tr("menu.singlePatient.viralIsolates.view"), viralIsolateSelected, new ITreeAction()
    				{
						public void performAction(TreeMenuNode node)
						{
							RegaDBMain.getApp().getFormContainer().setForm(new ViralIsolateForm(InteractionState.Viewing, WWidget.tr("form.viralIsolate.view"), viralIsolateSelected.getSelectedItem()));
						}
    				});
                    viralIsolateEdit = new ActionItem(rootItem.tr("menu.singlePatient.viralIsolates.edit"), viralIsolateSelected, new ITreeAction()
                    {
                        public void performAction(TreeMenuNode node)
                        {
                            RegaDBMain.getApp().getFormContainer().setForm(new ViralIsolateForm(InteractionState.Editing, WWidget.tr("form.viralIsolate.edit"), viralIsolateSelected.getSelectedItem()));
                        }
                    });
                    viralIsolatesDelete = new ActionItem(rootItem.tr("menu.singlePatient.viralIsolates.delete"), viralIsolateSelected, new ITreeAction()
                    {
                        public void performAction(TreeMenuNode node)
                        {
                            RegaDBMain.getApp().getFormContainer().setForm(new ViralIsolateForm(InteractionState.Deleting, WWidget.tr("form.viralIsolate.delete"), viralIsolateSelected.getSelectedItem()));
                        }
                    });
    		
                    patientEvent = new ActionItem(RootItem.tr("menu.singlePatient.event"), patientSelected);
                    
    	                patientEventSelect = new ActionItem(RootItem.tr("menu.singlePatient.event.select"), patientEvent, new ITreeAction()
    			        {
    			        	public void performAction(TreeMenuNode node)
    			        	{
    			        		RegaDBMain.getApp().getFormContainer().setForm(new SelectPatientEvent());
    			        	}
    			        });
    	                
    	                patientEventAdd = new ActionItem(RootItem.tr("menu.singlePatient.event.add"), patientEvent, new ITreeAction()
    	                {
    						public void performAction(TreeMenuNode node) {
    							RegaDBMain.getApp().getFormContainer().setForm(new PatientEventForm(InteractionState.Adding, WWidget.tr("menu.singlePatient.event.add"), null));
    						}
    	                });
    	                
    	                patientEventSelected = new PatientEventSelectedItem(patientEvent);
    	                
    		                patientEventView = new ActionItem(RootItem.tr("menu.singlePatient.event.view"), patientEventSelected, new ITreeAction()
    		                {
    							public void performAction(TreeMenuNode node) {
    								RegaDBMain.getApp().getFormContainer().setForm(new PatientEventForm(InteractionState.Viewing, WWidget.tr("menu.singlePatient.event.add"), patientEventSelected.getSelectedItem()));
    							}
    		                });
    		                
    		                patientEventEdit = new ActionItem(RootItem.tr("menu.singlePatient.event.edit"), patientEventSelected, new ITreeAction()
    		                {
    							public void performAction(TreeMenuNode node) {
    								RegaDBMain.getApp().getFormContainer().setForm(new PatientEventForm(InteractionState.Editing, WWidget.tr("menu.singlePatient.event.edit"), patientEventSelected.getSelectedItem()));
    							}
    		                });
    		                
    		                patientEventDelete = new ActionItem(RootItem.tr("menu.singlePatient.event.delete"), patientEventSelected, new ITreeAction()
    		                {
    							public void performAction(TreeMenuNode node) {
    								RegaDBMain.getApp().getFormContainer().setForm(new PatientEventForm(InteractionState.Deleting, WWidget.tr("menu.singlePatient.event.delete"), patientEventSelected.getSelectedItem()));
    							}
    		                });
    		                
       attributesSettings = new ActionItem(rootItem.tr("menu.attributeSettings.attributeSettings"), rootItem)
       {
            @Override
            public boolean isEnabled()
            {
                return RegaDBMain.getApp().getLogin()!=null;
            }
       };
           attributes = new ActionItem(rootItem.tr("menu.attributeSettings.attributes"), attributesSettings);
           attributesSelect  = new ActionItem(rootItem.tr("menu.attributeSettings.attributes.select"), attributes, new ITreeAction()
           {
               public void performAction(TreeMenuNode node) 
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new SelectAttributeForm());
               }
           });
           attributesAdd = new ActionItem(rootItem.tr("menu.attributeSettings.attributes.add"), attributes, new ITreeAction()
           {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new AttributeForm(InteractionState.Adding, WWidget.tr("form.attributeSettings.attribute.add"), null));
                }
            });
           attributesSelected = new AttributeSelectedItem(attributes);
           attributesView = new ActionItem(rootItem.tr("menu.attributeSettings.attributes.view"), attributesSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new AttributeForm(InteractionState.Viewing, WWidget.tr("form.attributeSettings.attribute.view"), attributesSelected.getSelectedItem()));
               }
           });
           attributesEdit = new ActionItem(rootItem.tr("menu.attributeSettings.attributes.edit"), attributesSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new AttributeForm(InteractionState.Editing, WWidget.tr("form.attributeSettings.attribute.edit"), attributesSelected.getSelectedItem()));
               }
           });
           attributesDelete = new ActionItem(rootItem.tr("menu.attributeSettings.attributes.delete"), attributesSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new AttributeForm(InteractionState.Deleting, WWidget.tr("form.attributeSettings.attribute.delete"), attributesSelected.getSelectedItem()));
               }
           });
           
           attributeGroups = new ActionItem(rootItem.tr("menu.attributeSettings.attributeGroups"), attributesSettings);
           attributeGroupsSelect  = new ActionItem(rootItem.tr("menu.attributeSettings.attributeGroups.select"), attributeGroups, new ITreeAction()
           {
               public void performAction(TreeMenuNode node) 
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new SelectAttributeGroupForm());
               }
           });
           attributeGroupsAdd = new ActionItem(rootItem.tr("menu.attributeSettings.attributeGroups.add"), attributeGroups, new ITreeAction()
           {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new AttributeGroupForm(InteractionState.Adding, WWidget.tr("form.attributeSettings.attributeGroups.add"), null));
                }
            });
           attributeGroupsSelected = new AttributeGroupSelectedItem(attributeGroups);
           attributeGroupsView = new ActionItem(rootItem.tr("menu.attributeSettings.attributeGroups.view"), attributeGroupsSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new AttributeGroupForm(InteractionState.Viewing, WWidget.tr("form.attributeSettings.attributeGroups.view"), attributeGroupsSelected.getSelectedItem()));
               }
           });
           attributeGroupsEdit = new ActionItem(rootItem.tr("menu.attributeSettings.attributeGroups.edit"), attributeGroupsSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new AttributeGroupForm(InteractionState.Editing, WWidget.tr("form.attributeSettings.attributeGroups.edit"), attributeGroupsSelected.getSelectedItem()));
               }
           });
           attributeGroupsDelete = new ActionItem(rootItem.tr("menu.attributeSettings.attributeGroups.delete"), attributeGroupsSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new AttributeGroupForm(InteractionState.Deleting, WWidget.tr("form.attributeSettings.attributeGroups.delete"), attributeGroupsSelected.getSelectedItem()));
               }
           });
           
          testSettings = new ActionItem(rootItem.tr("menu.testSettings.testSettings"), rootItem)
           {
                @Override
                public boolean isEnabled()
                {
                    return RegaDBMain.getApp().getLogin()!=null;
                }
           };
          
          testTypes = new ActionItem(rootItem.tr("menu.testSettings.testTypes"), testSettings);
          testTypesSelect  = new ActionItem(rootItem.tr("menu.testSettings.testTypes.select"), testTypes, new ITreeAction()
           {
               public void performAction(TreeMenuNode node) 
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new SelectTestTypeForm());
               }
           });
           testTypesAdd = new ActionItem(rootItem.tr("menu.testSettings.testTypes.add"), testTypes, new ITreeAction()
           {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new TestTypeForm(InteractionState.Adding, WWidget.tr("form.testSettings.testType.add"),null));
                    
                }
            });
           testTypeSelected = new TestTypeSelectedItem(testTypes);
           testTypesView = new ActionItem(rootItem.tr("menu.testSettings.testTypes.view"), testTypeSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new TestTypeForm(InteractionState.Viewing, WWidget.tr("form.testSettings.testType.view"),testTypeSelected.getSelectedItem()));
               }
           });
           testTypesEdit = new ActionItem(rootItem.tr("menu.testSettings.testTypes.edit"), testTypeSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new TestTypeForm(InteractionState.Editing, WWidget.tr("form.testSettings.testType.edit"),testTypeSelected.getSelectedItem()));
               }
           });
           testTypesDelete = new ActionItem(rootItem.tr("menu.testSettings.testTypes.delete"), testTypeSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new TestTypeForm(InteractionState.Deleting, WWidget.tr("form.testSettings.testType.delete"),testTypeSelected.getSelectedItem()));
               }
           });
           
           //test
           test = new ActionItem(rootItem.tr("menu.testSettings.tests"), testSettings);        
           testSelect  = new ActionItem(rootItem.tr("menu.testSettings.tests.select"), test, new ITreeAction()
           {
               public void performAction(TreeMenuNode node) 
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new SelectTestForm());
               }
           });
           testAdd = new ActionItem(rootItem.tr("menu.testSettings.tests.add"), test, new ITreeAction()
           {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new TestForm(InteractionState.Adding, WWidget.tr("form.testSettings.test.add"),null));
                    
                }
            });
           testSelected = new TestSelectedItem(test);
           testView = new ActionItem(rootItem.tr("menu.testSettings.tests.view"), testSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new TestForm(InteractionState.Viewing, WWidget.tr("form.testSettings.testType.view"),testSelected.getSelectedItem()));
               }
           });
           testEdit = new ActionItem(rootItem.tr("menu.testSettings.tests.edit"), testSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new TestForm(InteractionState.Editing, WWidget.tr("form.testSettings.test.edit"),testSelected.getSelectedItem()));
               }
           });
           testDelete = new ActionItem(rootItem.tr("menu.testSettings.tests.delete"), testSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new TestForm(InteractionState.Deleting, WWidget.tr("form.testSettings.test.delete"),testSelected.getSelectedItem()));
               }
           });
           
           //resistance report template
           resRepTemplate = new ActionItem(rootItem.tr("menu.resistance.report.template"), testSettings);        
           resRepTemplateSelect  = new ActionItem(rootItem.tr("menu.resistance.report.template.select"), resRepTemplate, new ITreeAction()
           {
               public void performAction(TreeMenuNode node) 
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new SelectResRepTemplateForm());
               }
           });
           resRepTemplateAdd = new ActionItem(rootItem.tr("menu.resistance.report.template.add"), resRepTemplate, new ITreeAction()
           {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new ResistanceInterpretationTemplateForm(InteractionState.Adding, WWidget.tr("form.resistance.report.template.add"), null));
                    
                }
            });
           resRepTemplateSelected = new ResRepTemplateSelectedItem(resRepTemplate);
           resRepTemplateView = new ActionItem(rootItem.tr("menu.resistance.report.template.view"), resRepTemplateSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new ResistanceInterpretationTemplateForm(InteractionState.Viewing, WWidget.tr("form.resistance.report.template.view"), resRepTemplateSelected.getSelectedItem()));
               }
           });
           resRepTemplateEdit = new ActionItem(rootItem.tr("menu.resistance.report.template.edit"), resRepTemplateSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new ResistanceInterpretationTemplateForm(InteractionState.Editing, WWidget.tr("form.resistance.report.template.edit"), resRepTemplateSelected.getSelectedItem()));
               }
           });
           resRepTemplateDelete = new ActionItem(rootItem.tr("menu.resistance.report.template.delete"), resRepTemplateSelected, new ITreeAction()
           {
               public void performAction(TreeMenuNode node)
               {
                   RegaDBMain.getApp().getFormContainer().setForm(new ResistanceInterpretationTemplateForm(InteractionState.Deleting, WWidget.tr("form.resistance.report.template.delete"), resRepTemplateSelected.getSelectedItem()));
               }
           });
        
        
        //DatasetSettings
        datasetSettings = new ActionItem(rootItem.tr("menu.datasetSettings.datasetSettings"), rootItem)
        {
             @Override
             public boolean isEnabled()
             {
                 return RegaDBMain.getApp().getLogin()!=null;
             }
        }; 
        
        datasets = new ActionItem(rootItem.tr("menu.datasetSettings.dataset"), datasetSettings); 
        datasetSelect  = new ActionItem(rootItem.tr("menu.datasetSettings.dataset.select"), datasets, new ITreeAction()
        {
            public void performAction(TreeMenuNode node) 
            {
                RegaDBMain.getApp().getFormContainer().setForm(new SelectDatasetForm());
            }
        });
        datasetAdd = new ActionItem(rootItem.tr("menu.datasetSettings.dataset.add"), datasets, new ITreeAction()
        {
             public void performAction(TreeMenuNode node)
             {
                 RegaDBMain.getApp().getFormContainer().setForm(new DatasetForm(InteractionState.Adding, WWidget.tr("form.datasetSettings.dataset.add"),null));
                 
             }
         });
        datasetSelected = new DatasetSelectedItem(datasets);
       datasetView = new ActionItem(rootItem.tr("menu.datasetSettings.dataset.view"), datasetSelected, new ITreeAction()
        {
            public void performAction(TreeMenuNode node)
            {
                RegaDBMain.getApp().getFormContainer().setForm(new DatasetForm(InteractionState.Viewing, WWidget.tr("form.datasetSettings.dataset.view"),datasetSelected.getSelectedItem()));
            }
        });
        datasetEdit = new ActionItem(rootItem.tr("menu.datasetSettings.dataset.edit"), datasetSelected, new ITreeAction()
        {
            public void performAction(TreeMenuNode node)
            {
                RegaDBMain.getApp().getFormContainer().setForm(new DatasetForm(InteractionState.Editing, WWidget.tr("form.datasetSettings.dataset.edit"),datasetSelected.getSelectedItem()));
            }
        })
        {
            @Override
            public boolean isEnabled()
            {
                return setDatasetSensitivity();
            }
        };
        datasetDelete = new ActionItem(rootItem.tr("menu.datasetSettings.dataset.delete"), datasetSelected, new ITreeAction()
        {
            public void performAction(TreeMenuNode node)
            {
                RegaDBMain.getApp().getFormContainer().setForm(new DatasetForm(InteractionState.Deleting, WWidget.tr("form.datasetSettings.dataset.delete"), datasetSelected.getSelectedItem()));
            }
        })
        {
            @Override
            public boolean isEnabled()
            {
                return setDatasetSensitivity();
            }
        };
        datasetAccess = new ActionItem(rootItem.tr("menu.dataset.access"), datasetSettings)
        {
            @Override
            public boolean isEnabled()
            {
                return RegaDBMain.getApp().getLogin()!=null;
            }
        };
        datasetAccessSelect = new ActionItem(rootItem.tr("menu.dataset.access.select"), datasetAccess, new ITreeAction()
        {
            public void performAction(TreeMenuNode node) 
            {
                RegaDBMain.getApp().getFormContainer().setForm(new SelectDatasetAccessUserForm());
            }
        });
        datasetAccessSelected = new DatasetAccessSelectedItem(datasetAccess);
        datasetAccessView = new ActionItem(rootItem.tr("menu.dataset.access.view"), datasetAccessSelected, new ITreeAction()
        {
            public void performAction(TreeMenuNode node)
            {
                RegaDBMain.getApp().getFormContainer().setForm(new DatasetAccessForm(InteractionState.Viewing, WWidget.tr("form.dataset.access.view"),datasetAccessSelected.getSelectedItem()));
            }
        });
        datasetAccessEdit = new ActionItem(rootItem.tr("menu.dataset.access.edit"), datasetAccessSelected, new ITreeAction()
        {
            public void performAction(TreeMenuNode node)
            {
                RegaDBMain.getApp().getFormContainer().setForm(new DatasetAccessForm(InteractionState.Editing, WWidget.tr("form.dataset.access.edit"),datasetAccessSelected.getSelectedItem()));
            }
        });
        
        event = new ActionItem(RootItem.tr("menu.event"), rootItem);
        
        eventSelect = new ActionItem(RootItem.tr("menu.event.select"), event, new ITreeAction()
        {
        	public void performAction(TreeMenuNode node)
        	{
        		RegaDBMain.getApp().getFormContainer().setForm(new SelectEventForm());
        	}
        });
        
        eventAdd = new ActionItem(RootItem.tr("menu.event.add"), event, new ITreeAction()
        {
        	public void performAction(TreeMenuNode node)
        	{
        		RegaDBMain.getApp().getFormContainer().setForm(new EventForm(InteractionState.Adding, WWidget.tr("menu.event.add"), null));
        	}
        });
        
        eventSelected = new EventSelectedItem(event);
        
	        eventSelectedView = new ActionItem(RootItem.tr("menu.event.selected.view"), eventSelected, new ITreeAction()
	        {
	        	public void performAction(TreeMenuNode node)
	        	{
	        		RegaDBMain.getApp().getFormContainer().setForm(new EventForm(InteractionState.Viewing, WWidget.tr("menu.event.selected.view"), eventSelected.getSelectedItem()));
	        	}
	        });
	        
	        eventSelectedEdit = new ActionItem(RootItem.tr("menu.event.selected.edit"), eventSelected, new ITreeAction()
	        {
	        	public void performAction(TreeMenuNode node)
	        	{
	        		RegaDBMain.getApp().getFormContainer().setForm(new EventForm(InteractionState.Editing, WWidget.tr("menu.event.selected.edit"), eventSelected.getSelectedItem()));
	        	}
	        });
	        
	        eventSelectedDelete = new ActionItem(RootItem.tr("menu.event.selected.delete"), eventSelected, new ITreeAction()
	        {
	        	public void performAction(TreeMenuNode node)
	        	{
	        		RegaDBMain.getApp().getFormContainer().setForm(new EventForm(InteractionState.Deleting, WWidget.tr("menu.event.selected.delete"), eventSelected.getSelectedItem()));
	        	}
	        });
        
        queryMain = new QueryItem(rootItem);
        queryDefinitionMain = new QueryDefinitionItem(queryMain);
        queryDefinitionSelect = new ActionItem(rootItem.tr("menu.query.definition.select"), queryDefinitionMain, new ITreeAction()
        {
            public void performAction(TreeMenuNode node) 
            {
                RegaDBMain.getApp().getFormContainer().setForm(new SelectQueryDefinitionForm());
            }
        });
        queryDefinitionAdd = new ActionItem(rootItem.tr("menu.query.definition.add"), queryDefinitionMain, new ITreeAction()
        {
            public void performAction(TreeMenuNode node) 
            {
                RegaDBMain.getApp().getFormContainer().setForm(new QueryDefinitionForm(WWidget.tr("form.query.definition.add"), InteractionState.Adding, new QueryDefinition()));
            }
        });
        queryDefinitionSelected = new QueryDefinitionSelectedItem(queryDefinitionMain);
        queryDefinitionSelectedView = new ActionItem(rootItem.tr("menu.query.definition.selected.view"), queryDefinitionSelected, new ITreeAction()
        {
            public void performAction(TreeMenuNode node)
            {
                RegaDBMain.getApp().getFormContainer().setForm(new QueryDefinitionForm(WWidget.tr("form.query.definition.selected.view"), InteractionState.Viewing, queryDefinitionSelected.getSelectedItem()));
            }
        });
        queryDefinitionSelectedEdit = new ActionItem(rootItem.tr("menu.query.definition.selected.edit"), queryDefinitionSelected, new ITreeAction()
        {
            public void performAction(TreeMenuNode node)
            {
            	RegaDBMain.getApp().getFormContainer().setForm(new QueryDefinitionForm(WWidget.tr("form.query.definition.selected.edit"), InteractionState.Editing, queryDefinitionSelected.getSelectedItem()));
            }
        })
        {
            @Override
            public boolean isEnabled()
            {
            	if((RegaDBMain.getApp().getLogin() != null) && (queryDefinitionSelected.getSelectedItem() != null))
            	{
            		return ((RegaDBMain.getApp().getLogin().getUid()).equals(queryDefinitionSelected.getQueryDefinitionCreator(queryDefinitionSelected.getSelectedItem())));
            	}
            	else
            	{
            		return false;
            	}
            	
            }
        };
        queryDefinitionSelectedDelete = new ActionItem(rootItem.tr("menu.query.definition.selected.delete"), queryDefinitionSelected, new ITreeAction()
        {
            public void performAction(TreeMenuNode node)
            {
                RegaDBMain.getApp().getFormContainer().setForm(new QueryDefinitionForm(WWidget.tr("form.query.definition.selected.delete"), InteractionState.Deleting, queryDefinitionSelected.getSelectedItem()));
            }
        })
        {
            @Override
            public boolean isEnabled()
            {
            	if((RegaDBMain.getApp().getLogin() != null) && (queryDefinitionSelected.getSelectedItem() != null))
            	{
            		return ((RegaDBMain.getApp().getLogin().getUid()).equals(queryDefinitionSelected.getQueryDefinitionCreator(queryDefinitionSelected.getSelectedItem())));
            	}
            	else
            	{
            		return false;
            	}
            }
        };
        queryDefinitionSelectedRun = new ActionItem(rootItem.tr("menu.query.definition.selected.run"), queryDefinitionSelected, new ITreeAction()
        {
            public void performAction(TreeMenuNode node)
            {
                RegaDBMain.getApp().getFormContainer().setForm(new QueryDefinitionRunForm(WWidget.tr("form.query.definition.run.add"), InteractionState.Adding, new QueryDefinitionRun()));
            }
        });
        queryDefinitionRunMain = new QueryDefinitionRunItem(queryMain);
        queryDefinitionRunSelect = new ActionItem(rootItem.tr("menu.query.definition.run.select"), queryDefinitionRunMain, new ITreeAction()
        {
            public void performAction(TreeMenuNode node) 
            {
                RegaDBMain.getApp().getFormContainer().setForm(new SelectQueryDefinitionRunForm());
            }
        });
        queryDefinitionRunSelected = new QueryDefinitionRunSelectedItem(queryDefinitionRunMain);
        queryDefinitionRunSelectedView = new ActionItem(rootItem.tr("menu.query.definition.run.selected.view"), queryDefinitionRunSelected, new ITreeAction()
        {
            public void performAction(TreeMenuNode node)
            {
                RegaDBMain.getApp().getFormContainer().setForm(new QueryDefinitionRunForm(WWidget.tr("form.query.definition.run.selected.view"), InteractionState.Viewing, queryDefinitionRunSelected.getSelectedItem()));
            }
        });
        queryDefinitionRunSelectedDelete = new ActionItem(rootItem.tr("menu.query.definition.run.selected.delete"), queryDefinitionRunSelected, new ITreeAction()
        {
            public void performAction(TreeMenuNode node)
            {
                RegaDBMain.getApp().getFormContainer().setForm(new QueryDefinitionRunForm(WWidget.tr("form.query.definition.selected.delete"), InteractionState.Deleting, queryDefinitionRunSelected.getSelectedItem()));
            }
        });
           
        myAccountMain = new MyAccountItem(rootItem);
            myAccountLogin = new LoginItem(myAccountMain);
            myAccountCreate = new ActionItem(rootItem.tr("form.account.create"), myAccountMain, new ITreeAction()
            {
                public void performAction(TreeMenuNode node) 
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new AccountForm(WWidget.tr("menu.myAccount.edit"), InteractionState.Adding, myAccountLogin, myAccountMain, false, new SettingsUser()));
                }
            })
            {
                @Override
                public boolean isEnabled()
                {
                    return RegaDBMain.getApp().getLogin()==null;
                }
            };
            myAccountView = new ActionItem(rootItem.tr("form.account.view"), myAccountMain, new ITreeAction()
            {
                public void performAction(TreeMenuNode node) 
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new AccountForm(WWidget.tr("menu.myAccount.view"), InteractionState.Viewing, myAccountView, myAccountMain, false, null));
                }
            })
            {
                @Override
                public boolean isEnabled()
                {
                    return RegaDBMain.getApp().getLogin()!=null;
                }
            };
            myAccountEdit = new ActionItem(rootItem.tr("form.account.edit"), myAccountMain, new ITreeAction()
            {
                public void performAction(TreeMenuNode node) 
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new AccountForm(WWidget.tr("menu.myAccount.edit"), InteractionState.Editing, myAccountView, myAccountMain, false, null));
                }
            })
            {
                @Override
                public boolean isEnabled()
                {
                    return RegaDBMain.getApp().getLogin()!=null;
                }
            };
            myAccountEditPassword = new ActionItem(rootItem.tr("form.account.edit.password"), myAccountMain, new ITreeAction()
            {
                public void performAction(TreeMenuNode node) 
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new PasswordForm(WWidget.tr("menu.myAccount.passwordForm"), InteractionState.Editing, myAccountView, myAccountMain, false, null));
                }
            })
            {
                @Override
                public boolean isEnabled()
                {
                    return RegaDBMain.getApp().getLogin()!=null;
                }
            };
            myAccountLogout = new LogoutItem(myAccountMain);
        
        administratorMain = new AdministratorItem(rootItem);
            enabledUsers = new ActionItem(rootItem.tr("menu.administrator.users.registered"), administratorMain);
            registeredUsersSelect = new ActionItem(rootItem.tr("menu.administrator.users.select"), enabledUsers, new ITreeAction()
            {
                public void performAction(TreeMenuNode node) 
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new SelectSettingsUserForm(true));
                }
            });
            registeredUserSelected = new RegisteredUserSelectedItem(enabledUsers);
            registeredUsersView = new ActionItem(rootItem.tr("menu.administrator.users.view"), registeredUserSelected, new ITreeAction()
            {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new AccountForm(WWidget.tr("form.administrator.registeredUser.view"), InteractionState.Viewing, registeredUsersSelect, registeredUserSelected, true, registeredUserSelected.getSelectedItem()));
                }
            });
            registeredUsersEdit = new ActionItem(rootItem.tr("menu.administrator.users.edit"), registeredUserSelected, new ITreeAction()
            {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new AccountForm(WWidget.tr("form.administrator.registeredUser.edit"), InteractionState.Editing, registeredUsersSelect, registeredUserSelected, true, registeredUserSelected.getSelectedItem()));
                }
            });
            registeredUsersDelete = new ActionItem(rootItem.tr("form.settings.user.delete"), registeredUserSelected, new ITreeAction()
            {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new AccountForm(WWidget.tr("form.administrator.registeredUser.delete"), InteractionState.Deleting, registeredUsersSelect, registeredUserSelected, true, registeredUserSelected.getSelectedItem()));
                }
            });
            registeredUsersChangePassword = new ActionItem(rootItem.tr("form.settings.user.password"), registeredUserSelected, new ITreeAction()
            {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new PasswordForm(WWidget.tr("menu.myAccount.passwordForm"), InteractionState.Editing, registeredUsersView, registeredUserSelected, true, registeredUserSelected.getSelectedItem()));
                }
            });
            
            disabledUsers = new ActionItem(rootItem.tr("menu.administrator.users.notregistered"), administratorMain);
            notRegisteredUsersSelect = new ActionItem(rootItem.tr("menu.administrator.users.select"), disabledUsers, new ITreeAction()
            {
                public void performAction(TreeMenuNode node) 
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new SelectSettingsUserForm(false));
                }
            });
            notRegisteredUserSelected = new NotRegisteredUserSelectedItem(disabledUsers);
            notRegisteredUsersView = new ActionItem(rootItem.tr("menu.administrator.users.view"), notRegisteredUserSelected, new ITreeAction()
            {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new AccountForm(WWidget.tr("form.administrator.notRegisteredUser.view"), InteractionState.Viewing, notRegisteredUsersSelect, notRegisteredUserSelected, true, notRegisteredUserSelected.getSelectedItem()));
                }
            });
            notRegisteredUsersEdit = new ActionItem(rootItem.tr("menu.administrator.users.edit"), notRegisteredUserSelected, new ITreeAction()
            {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new AccountForm(WWidget.tr("form.administrator.notRegisteredUser.edit"), InteractionState.Editing, notRegisteredUsersSelect, notRegisteredUserSelected, true, notRegisteredUserSelected.getSelectedItem()));
                }
            });
            notRegisteredUsersDelete = new ActionItem(rootItem.tr("form.settings.user.delete"), notRegisteredUserSelected, new ITreeAction()
            {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new AccountForm(WWidget.tr("form.administrator.notregisteredUser.delete"), InteractionState.Deleting, notRegisteredUsersSelect, notRegisteredUserSelected, true, notRegisteredUserSelected.getSelectedItem()));
                }
            });
            notRegisteredUsersChangePassword = new ActionItem(rootItem.tr("form.settings.user.password"), notRegisteredUserSelected, new ITreeAction()
            {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new PasswordForm(WWidget.tr("menu.myAccount.passwordForm"), InteractionState.Editing, registeredUsersView, registeredUserSelected, true, registeredUserSelected.getSelectedItem()));
                }
            });
            
            updateFromCentralServer = new ActionItem(rootItem.tr("menu.administrator.updateFromRegaDBServer"), administratorMain);
            updateFromCentralServerUpdateView = new ActionItem(rootItem.tr("menu.administrator.updateFromRegaDBServer.update.view"), updateFromCentralServer, new ITreeAction()
            {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new UpdateForm(WWidget.tr("form.update_central_server.view"), InteractionState.Viewing));
                }
            });
            updateFromCentralServerUpdate = new ActionItem(rootItem.tr("menu.administrator.updateFromRegaDBServer.update"), updateFromCentralServer, new ITreeAction()
            {
                public void performAction(TreeMenuNode node)
                {
                    RegaDBMain.getApp().getFormContainer().setForm(new UpdateForm(WWidget.tr("form.update_central_server"),InteractionState.Editing));
                }
            });
			
		if(singlePatientMain.isEnabled())
		{
			return singlePatientMain;
		}
		else
		{
			return myAccountLogin;
		}
	}
    
    private boolean setDatasetSensitivity()
    {
        Login login = RegaDBMain.getApp().getLogin();
        if(login!=null)
        {
            Dataset ds = datasetSelected.getSelectedItem();
            if(ds!=null)
            {
                return ds.getSettingsUser().getUid().equals(login.getUid());
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
}
