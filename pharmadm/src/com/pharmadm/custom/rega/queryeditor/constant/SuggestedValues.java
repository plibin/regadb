package com.pharmadm.custom.rega.queryeditor.constant;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pharmadm.custom.rega.queryeditor.port.DatabaseManager;

public class SuggestedValues implements Serializable {
	private ArrayList<SuggestedValuesOption> options;
	private String query = null;
	private boolean mandatory;
	
	public SuggestedValues() {
		options = new ArrayList<SuggestedValuesOption>();
	}
	
	public SuggestedValues(ArrayList<SuggestedValuesOption> options, String query, boolean mandatory) {
		this.options = options;
		this.query = query;
		this.mandatory = mandatory;
	}
	
	public ArrayList<SuggestedValuesOption> getSuggestedValuesWithoutQuery() {
		return options;
	}
	
	public void addOption(SuggestedValuesOption option) {
		options.add(option);
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public ArrayList<SuggestedValuesOption> getSuggestedValues() {
		ArrayList<SuggestedValuesOption>result = new ArrayList<SuggestedValuesOption>();
		result.addAll(options);

		if (query != null) {
			try {
				result.addAll(DatabaseManager.getInstance().getSuggestedValues(query));
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return result;
	}
	
	public boolean isEmpty() {
		return (options.isEmpty() && query == null);
	}
	
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	public boolean isMandatory() {
		return mandatory;
	}
	
	public String getQuery() {
		return query;
	}
}
