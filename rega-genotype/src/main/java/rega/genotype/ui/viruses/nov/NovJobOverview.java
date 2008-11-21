/*
 * Copyright (C) 2008 Rega Institute for Medical Research, KULeuven
 * 
 * See the LICENSE file for terms of use.
 */
package rega.genotype.ui.viruses.nov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rega.genotype.ui.data.SaxParser;
import rega.genotype.ui.forms.AbstractJobOverview;
import rega.genotype.ui.framework.GenotypeWindow;
import rega.genotype.ui.util.GenotypeLib;
import eu.webtoolkit.jwt.WAnchor;
import eu.webtoolkit.jwt.WString;
import eu.webtoolkit.jwt.WText;
import eu.webtoolkit.jwt.WWidget;

/**
 * NoV job overview implementation.
 * 
 * @author simbre1
 *
 */
public class NovJobOverview extends AbstractJobOverview {
	private List<WString> headers = new ArrayList<WString>();
	private List<WWidget> data = new ArrayList<WWidget>();
	
	public NovJobOverview(GenotypeWindow main) {
		super(main);
		
		headers.add(lt("Name"));
		headers.add(lt("Length"));
		headers.add(lt("Report"));
		headers.add(lt("ORF 1"));
		headers.add(lt("ORF 2"));
		headers.add(lt("Genome"));
	}
	
	@Override
	public List<WWidget> getData(final SaxParser p) {
		data.clear();
		
		data.add(new WText(lt(p.getEscapedValue("genotype_result.sequence[name]"))));
		data.add(new WText(lt(p.getEscapedValue("genotype_result.sequence[length]"))));
		
		WAnchor report = createReportLink(p);
		data.add(report);

		data.add(new WText(lt(NovResults.getConclusion(p, "ORF1"))));
		data.add(new WText(lt(NovResults.getConclusion(p, "ORF2"))));

		try {
			data.add(GenotypeLib.getWImageFromFile(getMain().getOrganismDefinition().getGenome().getSmallGenomePNG(jobDir, p.getSequenceIndex(), 
					"-",
					Integer.parseInt(p.getValue("genotype_result.sequence.result['blast'].start")), 
					Integer.parseInt(p.getValue("genotype_result.sequence.result['blast'].end")),
					0, "", null)));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return data;
	}

	@Override
	public List<WString> getHeaders() {
		return headers;
	}

}