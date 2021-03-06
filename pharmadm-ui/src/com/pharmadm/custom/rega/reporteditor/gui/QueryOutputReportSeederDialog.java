/*
 * AtomicClauseSelectionDialog.java
 *
 * Created on September 2, 2003, 7:16 PM
 */

/*
 * (C) Copyright 2000-2007 PharmaDM n.v. All rights reserved.
 * 
 * This file is licensed under the terms of the GNU General Public License (GPL) version 2.
 * See http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt
 */
package com.pharmadm.custom.rega.reporteditor.gui;

import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import com.pharmadm.custom.rega.reporteditor.*;
import com.pharmadm.custom.rega.gui.configurers.JVisualizationComponentFactory;

/**
 *
 * @author  kristof
 */
public class QueryOutputReportSeederDialog extends javax.swing.JDialog {
    
    /** Creates new form DataGroupSelectionDialog */
    public QueryOutputReportSeederDialog(java.awt.Frame parent, QueryOutputReportSeeder controller, boolean modal) {
        super(parent, modal);
        initComponents();
        initListComponents(controller);
        getRootPane().setDefaultButton(okButton);
        pack();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();

        setTitle("Select query variables to use for seeding");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
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

        jScrollPane2.setPreferredSize(new java.awt.Dimension(600, 570));
        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents
    
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed
    
    private void initListComponents(QueryOutputReportSeeder controller) {
        editPanel = new ScrollableEditPanel();
        editPanel.setLayout(new java.awt.GridBagLayout());
        jScrollPane2.setViewportView(editPanel);
        
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        editPanel.add(new javax.swing.JLabel(""),gridBagConstraints);
        
        Iterator iter = controller.getObjectListVariables().iterator();
        while (iter.hasNext()) {
            ObjectListVariable olvar = (ObjectListVariable)iter.next();
            ObjectListSeederPanel seedPanel = new ObjectListSeederPanel(olvar, controller);
            seeders.add(seedPanel);
            
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
            gridBagConstraints.weightx = 1;
            gridBagConstraints.weighty = 1;
            editPanel.add(seedPanel,gridBagConstraints);
            //jScrollPane2.setViewport(jScrollPane2.getViewport());
        }
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 0);
        editPanel.add(new javax.swing.JLabel(""),gridBagConstraints);
    }
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // Add your handling code here:
        setVisible(false);
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed
    
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // Add your handling code here:
        apply();
        setVisible(false);
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed
    
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    private void apply() {
        Iterator iter = seeders.iterator();
        while(iter.hasNext()) {
            ObjectListSeederPanel seeder = (ObjectListSeederPanel)iter.next();
            seeder.apply();
        }
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
    private DataGroup selectedGroup = null;
    private Collection selectorList; // of type DataGroupSelectorPanel
    private javax.swing.JPanel editPanel;
    private ArrayList seeders = new ArrayList();
        
    
    private static class ScrollableEditPanel extends JPanel implements Scrollable {
        
        public java.awt.Dimension getPreferredScrollableViewportSize() {
            return new Dimension(580, 560);
        }
        
        public int getScrollableBlockIncrement(java.awt.Rectangle visibleRect, int orientation, int direction) {
            Dimension size = getSize();
            if (orientation == SwingConstants.HORIZONTAL) {
                return (int)(size.getWidth() / 3);
            } else {
                return (int)(size.getHeight() / 3);
            }
        }
        
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
        
        public boolean getScrollableTracksViewportWidth() {
            return false;
        }
        
        public int getScrollableUnitIncrement(java.awt.Rectangle visibleRect, int orientation, int direction) {
            return 20;
        }
        
    }
}
