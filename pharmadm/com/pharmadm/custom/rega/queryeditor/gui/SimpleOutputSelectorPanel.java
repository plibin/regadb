/*
 * SimpleOutputSelectorPanel.java
 *
 * Created on September 5, 2003, 7:27 PM
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
 */
public class SimpleOutputSelectorPanel extends javax.swing.JPanel {
    
    private OutputSelection selection;
    
    /** Creates new form SimpleOutputSelectorPanel */
    public SimpleOutputSelectorPanel(OutputSelection selection) {
        this.selection = selection;
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jCheckBox1 = new javax.swing.JCheckBox();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

        jCheckBox1.setSelected(selection.isSelected());
        jCheckBox1.setText(((OutputVariable)selection.getObject()).getUniqueName());
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        add(jCheckBox1);

    }//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // Add your handling code here:
        ((SelectionStatusList)selection.getController()).setSelected((OutputVariable)selection.getObject(), jCheckBox1.isSelected());
    }//GEN-LAST:event_jCheckBox1ActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    // End of variables declaration//GEN-END:variables
    
}
