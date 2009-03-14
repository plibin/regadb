package net.sf.hivgensim.services;

import java.io.File;
import java.util.HashMap;

import net.sf.wts.client.WtsClient;

public abstract class AbstractService {
	
	private String url = "http://localhost:8080/wts/services/";
	private String uid = "gbehey0";
	private String passwd = "bla123";
	
	private String serviceName = "";
	private HashMap<String,File> uploads = new HashMap<String,File>();
	private HashMap<String,File> downloads = new HashMap<String,File>();
	
	protected AbstractService(String serviceName){
		setServiceName(serviceName);
	}
	
	protected String getServiceName() {
		return serviceName;
	}
	protected void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	protected String getUrl() {
		return url;
	}
	protected void setUrl(String url) {
		this.url = url;
	}
	protected String getUid() {
		return uid;
	}
	protected void setUid(String uid) {
		this.uid = uid;
	}
	protected String getPasswd() {
		return passwd;
	}
	protected void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	protected void addUpload(String filename, File file){
		uploads.put(filename,file);
	}
	
	protected void addDownload(String filename, File file){
		downloads.put(filename, file);
	}
	
	public void run(){
		WtsClient wc = new WtsClient(getUrl());		
		try{
			String challenge = wc.getChallenge(getUid());
			String sessionTicket = wc.login(getUid(), challenge, getPasswd(), getServiceName());
			
			for(String filename : uploads.keySet()){
				wc.upload(sessionTicket, getServiceName(), filename, uploads.get(filename));
			}
			
			wc.start(sessionTicket, getServiceName());
			String status = wc.monitorStatus(sessionTicket, getServiceName());			
			while (!status.equals("ENDED_SUCCES")) {
				status = wc.monitorStatus(sessionTicket, getServiceName());			
			}
			
			for(String filename : downloads.keySet()){
				wc.download(sessionTicket, getServiceName(), filename, downloads.get(filename));
			}			
			wc.closeSession(sessionTicket, serviceName);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}