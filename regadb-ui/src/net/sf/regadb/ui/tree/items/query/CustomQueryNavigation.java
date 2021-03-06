package net.sf.regadb.ui.tree.items.query;

import net.sf.regadb.ui.form.query.custom.NadirQuery;
import net.sf.regadb.ui.form.query.custom.ghb_hcv.GhbHcvQueryForm;
import net.sf.regadb.ui.framework.forms.IForm;
import net.sf.regadb.ui.framework.tree.TreeMenuNode;
import net.sf.regadb.ui.tree.DefaultNavigationNode;
import net.sf.regadb.ui.tree.FormNavigationNode;
import net.sf.regadb.util.settings.RegaDBSettings;
import eu.webtoolkit.jwt.WString;

public class CustomQueryNavigation extends DefaultNavigationNode {

	public CustomQueryNavigation(TreeMenuNode parent) {
		super(WString.tr("menu.query.custom"), parent);
		
		new FormNavigationNode(WString.tr("form.query.custom.nadir.name"), this, true) {
			@Override
			public IForm createForm() {
				return new NadirQuery();
			}
		};
		
		if (RegaDBSettings.getInstance().getInstituteConfig().getGhbHcvExportFormConfig() != null) {
			new FormNavigationNode(WString.tr("form.query.custom.ghb-hcv.name"), this, true) {
				@Override
				public IForm createForm() {
					return new GhbHcvQueryForm();
				}
			};
		}
	}

}
