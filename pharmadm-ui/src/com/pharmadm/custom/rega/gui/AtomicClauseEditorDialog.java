/*
 * AtomicClauseEditorDialog.java
 *
 * Created on August 29, 2003, 4:33 PM
 */

/*
 * (C) Copyright 2000-2007 PharmaDM n.v. All rights reserved.
 * 
 * This file is licensed under the terms of the GNU General Public License (GPL) version 2.
 * See http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt
 */
package com.pharmadm.custom.rega.gui;


import com.pharmadm.custom.rega.gui.awceditor.AWCEditorPanel;
import com.pharmadm.custom.rega.gui.awceditor.AWCSelectorPanel;
import com.pharmadm.custom.rega.queryeditor.AtomicWhereClause;

/**
 *
 * @author  kristof
 */
public class AtomicClauseEditorDialog extends javax.swing.JDialog {
    
	private AtomicWhereClause selectedClause;
    private AWCSelectorPanel editPanel;
	
	public AtomicWhereClause getSelectedClause() {
		return selectedClause;
	}
	
    /** Creates new form AtomicClauseEditorDialog */
    public AtomicClauseEditorDialog(java.awt.Frame parent, AWCSelectorPanel panel, boolean modal) {
        super(parent, modal);
        initComponents();
        initEditorComponents(panel);
        getRootPane().setDefaultButton(okButton);
        pack();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        buttonPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setTitle("Edit Query Component");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {}
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        buttonPanel.add(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        buttonPanel.add(cancelButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents
    
    private void initEditorComponents(AWCSelectorPanel panel) {
        editPanel = panel;
        editPanel.getRadioButtons().get(0).setVisible(false);
        editPanel.getRadioButtons().get(0).setSelected(true);
        getContentPane().add(editPanel, java.awt.BorderLayout.CENTER);
    }
    
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // Add your handling code here:
        setVisible(false);
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed
    
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // Add your handling code here:
		AWCEditorPanel selectedEditPanel = editPanel.getSelectedClause();
		selectedEditPanel.getManager().applyEditings();
		selectedClause = selectedEditPanel.getManager().getClause();
        setVisible(false);
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
}
