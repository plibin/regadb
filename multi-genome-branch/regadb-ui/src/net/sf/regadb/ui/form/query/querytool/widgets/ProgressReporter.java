package net.sf.regadb.ui.form.query.querytool.widgets;

import net.sf.witty.wt.i8n.WMessage;

public interface ProgressReporter {
	boolean isDone();

	int getProgress();

	WMessage getMessage();

}