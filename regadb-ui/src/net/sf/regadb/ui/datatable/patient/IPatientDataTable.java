package net.sf.regadb.ui.datatable.patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.regadb.db.Dataset;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.Transaction;
import net.sf.regadb.ui.framework.RegaDBMain;
import net.sf.regadb.ui.framework.tree.TreeMenuNode;
import net.sf.regadb.ui.framework.widgets.datatable.IDataTable;
import net.sf.regadb.ui.framework.widgets.datatable.IFilter;
import net.sf.regadb.ui.framework.widgets.datatable.StringFilter;
import net.sf.regadb.ui.framework.widgets.datatable.hibernate.HibernateStringUtils;
import net.sf.regadb.ui.tree.GenericSelectedItem;

public class IPatientDataTable implements IDataTable<Patient>
{
	private static String[] colNames_ = { "dataTable.patient.colName.dataSet", "dataTable.patient.colName.patientId",
			"dataTable.patient.colName.name", "dataTable.patient.colName.surName" };

	private IFilter[] filters_ = new IFilter[4];

	private static String[] filterVarNames_ = { "dataset.description", "patient.patientId", "patient.lastName",
			"patient.firstName" };
	
	private static boolean [] sortable_ = {true, true, true, true};

	public IPatientDataTable()
	{

	}

	public void init(Transaction t)
	{
		filters_[0] = new DatasetFilter(t);
		filters_[1] = new StringFilter();
		filters_[2] = new StringFilter();
		filters_[3] = new StringFilter();
	}

	public String[] getColNames()
	{
		return colNames_;
	}

	public List<Patient> getDataBlock(Transaction t, int startIndex, int amountOfRows, int sortColIndex, boolean ascending)
	{
		return t.getPatients(startIndex, amountOfRows, filterVarNames_[sortColIndex], ascending, HibernateStringUtils.filterConstraintsQuery(this));
	}

	public IFilter[] getFilters()
	{
		return filters_;
	}

	public String[] getRowData(Patient type)
	{
		String[] toReturn = new String[4];

		Set<Dataset> dataSets = type.getDatasets();
		for (Dataset set : dataSets)
		{
			if (set.getClosedDate() == null)
			{
				toReturn[0] = set.getDescription();
			}
		}
		toReturn[1] = type.getPatientId();
		toReturn[2] = type.getFirstName();
		toReturn[3] = type.getLastName();

		return toReturn;
	}

    public String[] getFieldNames() 
    {
        return filterVarNames_;
    }
    
    public long getDataSetSize(Transaction t)
    {
        return t.getPatientCount(HibernateStringUtils.filterConstraintsQuery(this));
    }

    public void selectAction(Patient selectedItem) 
    {
        RegaDBMain.getApp().getTree().getTreeContent().patientSelected.setSelectedItem(selectedItem);
        RegaDBMain.getApp().getTree().getTreeContent().patientSelected.expand();
        RegaDBMain.getApp().getTree().getTreeContent().patientSelected.refreshAllChildren();
        RegaDBMain.getApp().getTree().getTreeContent().viewPatient.selectNode();
        
        ArrayList<TreeMenuNode> patientAttributes = RegaDBMain.getApp().getTree().getTreeContent().patientSelected.getChildren();
        
        for(TreeMenuNode tn : patientAttributes)
        {
        	ArrayList<TreeMenuNode> children = tn.getChildren();
        	
        	for(TreeMenuNode tmn : children)
        	{
	        	if(tmn instanceof GenericSelectedItem)
	        	{
					((GenericSelectedItem)tmn).setSelectedItem(null);
				}
        	}
        }
    }

	public boolean[] sortableFields()
	{
		return sortable_;
	}
}
