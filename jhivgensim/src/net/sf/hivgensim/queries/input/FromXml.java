package net.sf.hivgensim.queries.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.hivgensim.queries.framework.IQuery;
import net.sf.hivgensim.queries.framework.QueryInput;
import net.sf.regadb.db.Patient;
import net.sf.regadb.db.Transaction;
import net.sf.regadb.db.login.DisabledUserException;
import net.sf.regadb.db.login.WrongPasswordException;
import net.sf.regadb.db.login.WrongUidException;
import net.sf.regadb.db.session.Login;
import net.sf.regadb.io.importXML.ImportFromXML;
import net.sf.regadb.io.importXML.ImportHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class FromXml extends QueryInput {
	private List<Patient> patientsCache;
	private boolean cacheOn = false;

	private File file;
	private String loginname;
	private String passwd;

	public FromXml(File file, String loginname, String passwd,
			IQuery<Patient> nextQuery) {
		super(nextQuery);
		this.file = file;
		this.loginname = loginname;
		this.passwd = passwd;
	}

	@Override
	public void run() {
		if (patientsCache == null) {
			if(cacheOn)
				patientsCache = new ArrayList<Patient>();
				
			Login login = null;
			try {
				login = Login.authenticate(loginname, passwd);
			} catch (WrongUidException e) {
				e.printStackTrace();
			} catch (WrongPasswordException e) {
				e.printStackTrace();
			} catch (DisabledUserException e) {
				e.printStackTrace();
			}

			Transaction t = login.createTransaction();

			ImportFromXML imp = new ImportFromXML();
			imp.loadDatabaseObjects(t);
			FileReader r;
			try {
				r = new FileReader(file);
				imp.readPatients(new InputSource(r),
						new ImportHandler<Patient>() {
							public void importObject(Patient p) {
								getNextQuery().process(p);
								if(cacheOn)
									patientsCache.add(p);
							}
						});
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			for (Patient p : patientsCache) {
				getNextQuery().process(p);
			}
		}
	}
	
	public boolean isCacheOn() {
		return cacheOn;
	}

	public void setCacheOn(boolean cacheOn) {
		this.cacheOn = cacheOn;
	}
}
