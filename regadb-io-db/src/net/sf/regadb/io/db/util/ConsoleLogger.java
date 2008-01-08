package net.sf.regadb.io.db.util;

public class ConsoleLogger implements Logging {

	protected static ConsoleLogger _singleton;
	
	private final boolean ENABLE_INFO_LOGGING = false;
	
	private boolean _isInfoEnabled = ENABLE_INFO_LOGGING;
	
	public static ConsoleLogger getInstance()
	{
		if(_singleton == null)
			_singleton = new ConsoleLogger();
			
		return _singleton;
	}
	
	public void logInfo(String message)
	{
		if(_isInfoEnabled)
			System.out.println(message);
	}
	
	public void logError(String message)
	{
		System.err.println(message);
		
		System.err.println("CRITICAL ERROR...Stopping database export.");
		
		System.exit(0);
	}
	
	public void logWarning(String message)
	{
		System.err.println("WARNING: "+message);
	}
	
	public void logError(String patientID, String message)
	{
		System.err.println(message);
		
		System.err.println("Error occured during export of patient: "+patientID);
		
		System.err.println("CRITICAL ERROR...Stopping database export.");
		
		System.exit(0);
	}
	
	public void logWarning(String patientID, String message)
	{
		System.err.println("WARNING: "+message);
		
		System.err.println("Warning occured during export of patient: "+patientID);
	}
	
	public boolean isInfoEnabled()
	{
		return _isInfoEnabled;
	}
	
	public void setInfoEnabled(boolean enable)
	{
		_isInfoEnabled = enable;
	}
}