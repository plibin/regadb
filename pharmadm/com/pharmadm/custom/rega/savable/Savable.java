/*
 * Savable.java
 *
 * Created on December 15, 2003, 2:43 PM
 */

/*
 * (C) Copyright 2000-2007 PharmaDM n.v. All rights reserved.
 * 
 * This file is licensed under the terms of the GNU General Public License (GPL) version 2.
 * See http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt
 */
package com.pharmadm.custom.rega.savable;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author  kdg
 */
public interface Savable {
    
    public void load(File file) throws IOException;
    
    public void save(File file) throws IOException;
    
    public boolean isDirty();
    
    public void addDirtinessListener(DirtinessListener listener);
}
