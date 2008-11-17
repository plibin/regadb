package org.sf.hivgensim.queries.framework;

import java.util.ArrayList;
import java.util.List;

public abstract class QueryImpl<T,I> implements Query<T>{
	
	protected Query<I> inputQuery;
	protected List<T> outputList;
	
	
	protected QueryImpl(Query<I> inputQuery){
		this.inputQuery = inputQuery;
	}
	
	public abstract void populateOutputList();
	
	public List<T> getOutputList(){
		if(outputList == null){
			outputList = new ArrayList<T>();
			populateOutputList();
		}
		return outputList;
	}
	
	
	
	
	
}
