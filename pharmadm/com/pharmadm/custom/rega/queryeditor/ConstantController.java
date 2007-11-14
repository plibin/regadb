/*
 * ConstantController.java
 *
 * Created on November 25, 2003, 6:37 PM
 */

/*
 * (C) Copyright 2000-2007 PharmaDM n.v. All rights reserved.
 * 
 * This file is licensed under the terms of the GNU General Public License (GPL) version 2.
 * See http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt
 */
package com.pharmadm.custom.rega.queryeditor;

/**
 *
 * @author  kristof
 */
public interface ConstantController {
    
    public String getConstantValueString(Constant cst);
    
    public boolean setConstantValueString(Constant constant, String valueString);
    
}
