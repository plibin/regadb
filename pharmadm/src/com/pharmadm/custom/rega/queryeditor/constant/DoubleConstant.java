
/** Java class "DoubleConstant.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
/*
 * (C) Copyright 2000-2007 PharmaDM n.v. All rights reserved.
 * 
 * This file is licensed under the terms of the GNU General Public License (GPL) version 2.
 * See http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt
 */
package com.pharmadm.custom.rega.queryeditor.constant;

import java.io.Serializable;
import java.text.Format;
import java.text.DecimalFormat;
import java.text.ParsePosition;

import com.pharmadm.custom.rega.queryeditor.catalog.DbObject;
import com.pharmadm.custom.rega.queryeditor.catalog.DbObject.ValueType;
import com.pharmadm.custom.rega.queryeditor.port.DatabaseManager;
import com.pharmadm.custom.rega.queryeditor.port.QueryVisitor;



/**
 * <p>
 * This class supports xml-encoding. No new properties are encoded.
 * </p>
 */
public class DoubleConstant extends Constant implements Serializable{
    
	private static final Format DOUBLE_FORMAT = new DecimalFormat();
    
	@Override
	public DbObject getDbObject() {
		return DatabaseManager.getInstance().getAWCCatalog().getObject(ValueType.Number.toString());
	}

	@Override
	public Object getdefaultValue() {
		return 0;
	}
	
    public String acceptWhereClause(QueryVisitor visitor) {
        return visitor.visitWhereClauseConstant(this);
    }  	

	@Override
	protected String parseObject(Object o) {
		ParsePosition pos = new ParsePosition(0);
		Object result = DOUBLE_FORMAT.parseObject(o.toString(),pos);
		if (result == null || pos.getIndex()< o.toString().length()) {
			return null;
		}
		return o.toString();
	}
}
