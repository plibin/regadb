/*
 * DatabaseManager.java
 *
 * Created on September 8, 2003, 9:18 AM
 */

/*
 * (C) Copyright 2000-2007 PharmaDM n.v. All rights reserved.
 * 
 * This file is licensed under the terms of the GNU General Public License (GPL) version 2.
 * See http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt
 */
package com.pharmadm.custom.rega.queryeditor;

import java.sql.*;
import java.util.*;



/**
 * Manages a JBDC connection.
 *
 * @author  kdg
 */
public class DatabaseManager implements DatabaseConnector {
	private static DatabaseManager instance;
	
    private DatabaseConnector DbManager;
	private QueryVisitor visitor;
    private DatabaseTableCatalog tableCatalog = new DatabaseTableCatalog();
    private List<String> tableNames = null;
    
    private DatabaseManager(QueryVisitor queryBuilder, DatabaseConnector conn) {
    	this.visitor = queryBuilder;
    	DbManager = conn;
    }
    
    public static DatabaseManager getInstance() {
    	return instance;
    }
    
    public static DatabaseManager initInstance(QueryVisitor queryBuilder, DatabaseConnector conn) {
    	if (instance == null) {
    		instance = new DatabaseManager(queryBuilder, conn);
    	}
    	return instance;
    }
    
    public QueryVisitor getQueryBuilder() {
    	return visitor;
    }
    
    public DatabaseTableCatalog getTableCatalog() {
        return tableCatalog;
    }
    
    public List<String> getTableNames() {
        if (tableNames == null) {
        	tableNames = DbManager.getTableNames();
        }
        return tableNames;
    }
    
    /**
     * returns true if a table with the given name exists in the database
     * @param tableName the name of a table
     * @return
     */
    public boolean tableExists(String tableName) {
        return getTableNames().contains(tableName);
    }    
    
    public QueryResult executeQuery(String query) throws SQLException {
    	return DbManager.executeQuery(query);
    }
    
    public List<String> getColumnNames(String tableName) {
    	return DbManager.getColumnNames(tableName);
    }
    
    public String getColumnType(String tableName, String columnName) {
    	return DbManager.getColumnType(tableName, columnName);
    }
    
    public List<String> getPrimaryKeys(String tableName) {
    	return DbManager.getPrimaryKeys(tableName);
    }
    
    public String getCommentForTable(String tableName) {
    	return DbManager.getCommentForTable(tableName);
    }
    
    public String getCommentForColumn(String tableName, String fieldName) {
    	return DbManager.getCommentForColumn(tableName, fieldName);
    }
    
    public QueryStatement createScrollableReadOnlyStatement() throws SQLException {
        return DbManager.createScrollableReadOnlyStatement();
    }
}