package net.sf.wts.client.util;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.activation.DataHandler;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPMessage;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class AxisClient 
{
    private Call call;
    private ArrayList<Object> parameters_ = new ArrayList<Object>();
    
    public AxisClient()
    {
        Service service = new Service();
        try 
        {            
            call = (Call) service.createCall();
        } 
        catch (ServiceException e) 
        {
            e.printStackTrace();
        }
    }
    
    public void setServiceUrl(String url, String service) throws MalformedURLException
    {
        call.setTargetEndpointAddress(new java.net.URL(url+service));

        call.setOperationName(new javax.xml.namespace.QName("urn:"+service, "exec"));
    }
    
    public void addParameter(String param)
    {
        call.addParameter("in"+parameters_.size(), XMLType.XSD_STRING, ParameterMode.IN);
        parameters_.add(param);
    }
    
    public void addParameter(int param)
    {
        call.addParameter("in"+parameters_.size(), XMLType.XSD_INT, ParameterMode.IN);
        parameters_.add(param);
    }
    
    public void addParameter(byte[] param)
    {
        call.addParameter("in"+parameters_.size(), XMLType.XSD_HEXBIN, ParameterMode.IN);
        parameters_.add(param);
    }
    
    public void addParameter(DataHandler dh) {
    	AttachmentPart attachment = call.getMessageContext().getCurrentMessage().createAttachmentPart();
    	attachment.setDataHandler(dh);
        call.addAttachmentPart(attachment);
    }
    
    public String callAndGetStringResult() throws RemoteException
    {
        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
        
        return (String)(call.invoke(parameters_.toArray()));
    }
    
    public byte[] callAndGetByteArrayResult() throws RemoteException
    {
        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_HEXBIN);
        
        return (byte[])(call.invoke(parameters_.toArray()));
    }
    
    public SOAPMessage callAndGetAttachment() throws RemoteException
    {
    	call.setReturnType(org.apache.axis.encoding.XMLType.XSD_ANYTYPE);
    	call.invoke(parameters_.toArray());
    	return call.getMessageContext().getCurrentMessage();
    }
    
    public void call() throws RemoteException
    {   
        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_ANYTYPE);
        call.invoke(parameters_.toArray());
    }
    
    public void removeParameters()
    {
        call.removeAllParameters();
        parameters_.clear();
    }
}
