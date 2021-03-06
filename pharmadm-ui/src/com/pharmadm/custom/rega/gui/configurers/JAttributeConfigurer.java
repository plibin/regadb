package com.pharmadm.custom.rega.gui.configurers;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.pharmadm.custom.rega.queryeditor.ConfigurableWord;
import com.pharmadm.custom.rega.queryeditor.wordconfiguration.ComposedWordConfigurer;
import com.pharmadm.custom.rega.queryeditor.wordconfiguration.WordConfigurer;

public class JAttributeConfigurer extends JPanel implements ComposedWordConfigurer {

	private JComposedWordConfigurer ovar;
	private List<JCombinedConfigurer> constantPanels;
	
	public JAttributeConfigurer(JComposedWordConfigurer ovar, JCombinedConfigurer constantPanel) {
		this.ovar = ovar;
		this.constantPanels = new ArrayList<JCombinedConfigurer>();
		constantPanels.add(constantPanel);
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(ovar);
		this.add(constantPanels.get(ovar.getSelectedIndex()));
		
		this.ovar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				changeSelection(arg0);
			}
		});
	}
	
	private void changeSelection(ActionEvent evt) {
		this.remove(1);
		this.add(constantPanels.get(ovar.getSelectedIndex()));
		this.revalidate();
	}
	
	public void add(List<WordConfigurer> keys, List<WordConfigurer> words) {
		ovar.add(keys, words);
		constantPanels.add(new JCombinedConfigurer(words));
	}

	public void configureWord() {
		constantPanels.get(ovar.getSelectedIndex()).configureWord();
		ovar.configureWord();
	}

	public int getSelectedIndex() {
		return ovar.getSelectedIndex();
	}

	public ConfigurableWord getWord() {
		return ovar.getWord();
	}

	public void reAssign(Object o) {
		JAttributeConfigurer confy = (JAttributeConfigurer) o;
		this.constantPanels = confy.constantPanels;
		this.ovar = confy.ovar;
	}
	
    public void addFocusListener(java.awt.event.FocusListener listener) {
		for (WordConfigurer  confy : this.constantPanels) {
			Component comp = (Component) confy;
			comp.addFocusListener(listener);
    	}
		ovar.addFocusListener(listener);
		super.addFocusListener(listener);
    }
	
    public void addMouseListener(MouseListener listener) {
		for (WordConfigurer  confy : this.constantPanels) {
			Component comp = (Component) confy;
			comp.addMouseListener(listener);
    	}
		ovar.addMouseListener(listener);
		super.addMouseListener(listener);
    }

	public void setSelectedIndex(int index) {}

	public boolean isUseless() {
		return false;
	}
}
