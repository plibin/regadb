package net.sf.regadb.service.ioAssist;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.regadb.align.Aligner;
import net.sf.regadb.align.local.LocalAlignmentService;
import net.sf.regadb.db.AaSequence;
import net.sf.regadb.db.AnalysisType;
import net.sf.regadb.db.DrugGeneric;
import net.sf.regadb.db.NtSequence;
import net.sf.regadb.db.Protein;
import net.sf.regadb.db.Test;
import net.sf.regadb.db.TestObject;
import net.sf.regadb.db.TestResult;
import net.sf.regadb.db.ValueType;
import net.sf.regadb.db.ViralIsolate;
import net.sf.regadb.io.exportXML.ExportToXML;
import net.sf.regadb.io.importXML.ImportHandler;
import net.sf.regadb.io.importXML.ResistanceInterpretationParser;
import net.sf.regadb.io.util.StandardObjects;
import net.sf.regadb.service.wts.RegaDBWtsServer;
import net.sf.regadb.service.wts.ViralIsolateAnalysisHelper;
import net.sf.regadb.service.wts.util.Utils;
import net.sf.wts.client.WtsClient;

import org.biojava.bio.symbol.IllegalSymbolException;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class IOAssistImportHandler implements ImportHandler<ViralIsolate>
{
    private int countViralIsolates = 0;
    private Map<String, Protein> proteinMap_;
    private Aligner aligner_;
    
    private Test subType_;
    private Test type_;
    
    private ExportToXML export_;
    private XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
    
    private FileWriter fileWriter_;
    
    private List<Test> resistanceTests_;
    
    private List<AaSequence> aaSeqs_;
        
    public IOAssistImportHandler(FileWriter fw)
    {
        proteinMap_ = new HashMap<String, Protein>();
        
        for(Protein p : StandardObjects.getProteins()) {
            proteinMap_.put(p.getAbbreviation(), p);
        }
        
        aligner_ = new Aligner(new LocalAlignmentService(), proteinMap_);
        
        subType_ = RegaDBWtsServer.getHIV1SubTypeTest(new TestObject("Sequence analysis", 1), new AnalysisType("wts"), new ValueType("string"));
        type_ = RegaDBWtsServer.getHIVTypeTest(new TestObject("Sequence analysis", 1), new AnalysisType("wts"), new ValueType("string"));
        
        export_ = new ExportToXML();
        fileWriter_ = fw;
        
        //find resistance tests --start
        resistanceTests_ = Utils.getResistanceTests();
        //find resistance tests --end
    }
    
    public void importObject(ViralIsolate object)
    {
        long start = System.currentTimeMillis();
        for(final NtSequence ntseq : object.getNtSequences())
        {
            Thread alignThread = new Thread(new Runnable()
            {
                public void run() 
                {
                    try 
                    {
                        aaSeqs_ = aligner_.alignHiv(ntseq);
                    } 
                    catch (IllegalSymbolException e) 
                    {
                        e.printStackTrace();
                    }
                }
            });
            alignThread.start();
                
            TestResult type = ntSeqAnalysis(ntseq, type_);
            ntseq.getTestResults().add(type);
            TestResult subType = ntSeqAnalysis(ntseq, subType_);
            ntseq.getTestResults().add(subType);
            
            try 
            {
                alignThread.join();
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
            
            if(aaSeqs_!=null)
            {
                for(AaSequence aaseq : aaSeqs_)
                {
                    aaseq.setNtSequence(ntseq);
                    ntseq.getAaSequences().add(aaseq);
                }
            }
        }
        
        for(final Test resistanceTest : resistanceTests_)
        {
            byte[] result = ViralIsolateAnalysisHelper.run(object, resistanceTest, 500);
            final ViralIsolate isolate = object;
            ResistanceInterpretationParser inp = new ResistanceInterpretationParser()
            {
                @Override
                public void completeScore(String drug, int level, double gss, String description, char sir, ArrayList<String> mutations, String remarks) 
                {
                    TestResult resistanceInterpretation = new TestResult();
                    resistanceInterpretation.setViralIsolate(isolate);
                    resistanceInterpretation.setDrugGeneric(new DrugGeneric(null, drug, ""));
                    resistanceInterpretation.setValue(gss+"");
                    resistanceInterpretation.setTestDate(new Date(System.currentTimeMillis()));
                    resistanceInterpretation.setTest(resistanceTest);
                    
                    StringBuffer data = new StringBuffer();
                    data.append("<interpretation><score><drug>");
                    data.append(drug);
                    data.append("</drug><level>");
                    data.append(level);
                    data.append("</level><description>");
                    data.append(description);
                    data.append("</description><sir>");
                    data.append(sir);
                    data.append("</sir><gss>");
                    data.append(gss);
                    data.append("</gss><mutations>");
                    int size = mutations.size();
                    for(int i = 0; i<size; i++)
                    {
                        data.append(mutations.get(i));
                        if(i!=size-1)
                            data.append(' ');
                    }
                    data.append("</mutations><remarks>");
                    data.append(remarks);
                    data.append("</remarks></score></interpretation>");
                    resistanceInterpretation.setData(data.toString().getBytes());
                    
                    isolate.getTestResults().add(resistanceInterpretation);
                }
            };
            try 
            {
                inp.parse(new InputSource(new ByteArrayInputStream(result)));
            } 
            catch (SAXException e) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
            catch (IOException e) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        Element parent = new Element("viralIsolates-el");
        export_.writeViralIsolate(object, parent);
        try 
        {
            fileWriter_.write(outputter.outputString(parent)+'\n');
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        countViralIsolates++;
        System.err.println("Processed viral isolate nr "+countViralIsolates);
        
        long stop = System.currentTimeMillis();
        System.err.println("runtime:" + (stop-start)/1000.0);
    }
    
    private TestResult ntSeqAnalysis(NtSequence ntseq, Test test)
    {
        WtsClient client_ = new WtsClient(test.getAnalysis().getUrl());
        
        String input = '>' + ntseq.getLabel() + '\n' + ntseq.getNucleotides();
        
        String challenge;
        String ticket = null;
        try 
        {
            challenge = client_.getChallenge(test.getAnalysis().getAccount());
            ticket = client_.login(test.getAnalysis().getAccount(), challenge, test.getAnalysis().getPassword(), test.getAnalysis().getServiceName());
        } 
        catch (RemoteException e1) 
        {
            e1.printStackTrace();
        } 
        catch (MalformedURLException e) 
        {
            e.printStackTrace();
        }
        
        try 
        {
            client_.upload(ticket, test.getAnalysis().getServiceName(), "nt_sequence", input.getBytes());
        } 
        catch (RemoteException e) 
        {
            e.printStackTrace();
        } 
        catch (MalformedURLException e) 
        {
            e.printStackTrace();
        }
        
        try 
        {
            client_.start(ticket, test.getAnalysis().getServiceName());
        } 
        catch (RemoteException e) 
        {
            e.printStackTrace();
        } 
        catch (MalformedURLException e) 
        {
            e.printStackTrace();
        }
        
        boolean finished = false;
        while(!finished)
        {
            try 
            {
                Thread.sleep(500);
            } 
            catch (InterruptedException ie) 
            {
                ie.printStackTrace();
            }
            
            try 
            {
                if(client_.monitorStatus(ticket, test.getAnalysis().getServiceName()).startsWith("ENDED"))
                {
                    finished = true;
                }
            } 
            catch (RemoteException e) 
            {
                e.printStackTrace();
            } 
            catch (MalformedURLException e) 
            {
                e.printStackTrace();
            }
        }
        
        byte[] resultArray = null;
        try 
        {
            resultArray = client_.download(ticket, test.getAnalysis().getServiceName(), test.getAnalysis().getBaseoutputfile());
        } 
        catch (RemoteException e) 
        {
            e.printStackTrace();
        } 
        catch (MalformedURLException e) 
        {
            e.printStackTrace();
        }
        
        try 
        {
            client_.closeSession(ticket, test.getAnalysis().getServiceName());
        } 
        catch (RemoteException e) 
        {
            e.printStackTrace();
        } 
        catch (MalformedURLException e) 
        {
            e.printStackTrace();
        }
        
        TestResult tr = new TestResult();
        tr.setNtSequence(ntseq);
        tr.setValue(new String(resultArray));
        tr.setTestDate(new Date(System.currentTimeMillis()));
        tr.setTest(test);
        
        return tr;
    }
}
