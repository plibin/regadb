/*
 * AWCWordConfigurer.java
 *
 * Created on September 1, 2003, 2:21 PM
 */

/*
 * (C) Copyright 2000-2007 PharmaDM n.v. All rights reserved.
 * 
 * This file is licensed under the terms of the GNU General Public License (GPL) version 2.
 * See http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt
 */
package com.pharmadm.custom.rega.queryeditor.gui;

import com.pharmadm.custom.rega.queryeditor.*;

/**
 *
 * @author  kristof
 *
 * <p>
 * Implementing classes of AWCWordConfigurer must be subclasses of 
 * java.awt.Component in order to be able to access them in the GUI,
 * and are preferably subclasses of javax.swing.JComponent. A
 * constructor taking the AWCWord to be configured as parameter 
 * should be provided.
 * </p>
 * 
 */
public interface WordConfigurer {
    
    /** returns the AWCWord managed by this configurer */
    public ConfigurableWord getWord();
    
    /** configures the AWCWord managed by this configurer with the relevant value */ 
    public void configureWord();
    
    /**
     * Frees any open database resources.
     */
    public void freeResources();
}
