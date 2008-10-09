package rega.genotype.ui.forms;

import net.sf.witty.wt.WBreak;
import net.sf.witty.wt.WContainerWidget;
import net.sf.witty.wt.WText;

import org.jdom.Element;

import rega.genotype.ui.framework.GenotypeWindow;
import rega.genotype.ui.util.GenotypeLib;

public class TutorialForm extends IForm {
	public TutorialForm(GenotypeWindow main) {
		super(main, "tutorial-form");
		
		Element text = main.getResourceManager().getOrganismElement("tutorial-form", "tutorial-text");
		for(Object o : text.getChildren()) {
			final Element e = (Element)o;
			if(e.getName().equals("text")) {
				new WText(lt(getMain().getResourceManager().extractFormattedText(e)), this);
			} else if(e.getName().equals("figure")) {
				WContainerWidget imgDiv = new WContainerWidget(this);
				imgDiv.setStyleClass("imgDiv");
				GenotypeLib.getWImageFromResource(getMain().getOrganismDefinition(),e.getTextTrim(), imgDiv);
			}
			new WBreak(this);
		}
	}
}
