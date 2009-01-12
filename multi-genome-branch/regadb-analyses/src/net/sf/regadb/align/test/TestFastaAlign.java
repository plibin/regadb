package net.sf.regadb.align.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import net.sf.regadb.align.Aligner;
import net.sf.regadb.align.local.LocalAlignmentService;
import net.sf.regadb.db.AaInsertion;
import net.sf.regadb.db.AaMutation;
import net.sf.regadb.db.AaSequence;
import net.sf.regadb.db.Genome;
import net.sf.regadb.db.NtSequence;
import net.sf.regadb.db.Transaction;
import net.sf.regadb.db.login.DisabledUserException;
import net.sf.regadb.db.login.WrongPasswordException;
import net.sf.regadb.db.login.WrongUidException;
import net.sf.regadb.db.session.Login;
import net.sf.regadb.service.wts.BlastAnalysis;

import org.biojava.bio.BioException;
import org.biojava.bio.seq.Sequence;
import org.biojavax.bio.seq.RichSequenceIterator;

public class TestFastaAlign {
	public static void main(String [] args) {
        RichSequenceIterator xna = null;
        FileReader uploadedStream = null;
        BufferedReader br = null;
        
        Aligner aligner = new Aligner(new LocalAlignmentService());
        
        try 
        {
            uploadedStream = new FileReader(args[0]);
            br = new BufferedReader(uploadedStream);
            xna = org.biojavax.bio.seq.RichSequence.IOTools.readFastaDNA(br, null);
        }
        catch (NoSuchElementException ex) 
        {
        	ex.printStackTrace();
        }
        catch (FileNotFoundException ex) 
        {
        	ex.printStackTrace();
        }
        catch(IOException ioe)
        {
        	ioe.printStackTrace();
        }
        
        if(xna!=null)
        { 
            while(xna.hasNext())
            {
                try {
                    Sequence seq = xna.nextRichSequence();
                    NtSequence ntseq = new NtSequence();
                    ntseq.setNucleotides(seq.seqString());
                    		
                    List<AaSequence> result = aligner.align(ntseq, getGenome(ntseq));
                    for(AaSequence aaseq : result) {
                    	for(AaMutation aamut : aaseq.getAaMutations()) {
                    		System.out.print(aaseq.getProtein().getAbbreviation() + 
                    				aamut.getId().getMutationPosition() + 
                    				aamut.getAaMutation()+ " ");
                    	}
                    	
                    	for(AaInsertion aains : aaseq.getAaInsertions()) {
                    		System.out.print(aaseq.getProtein().getAbbreviation()+
                    				aains.getId().getInsertionPosition()+aains.getId().getInsertionOrder()+
                    				aains.getAaInsertion()+" ");
                    	}
                    }
                    System.out.print("\n");
                }
                catch (NoSuchElementException e) 
                {
                    break;
                } 
                catch (BioException e) 
                {
                    break;
                }
            }
        }
        
        try 
        {
            uploadedStream.close();
            br.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
	}
	
    public static Genome getGenome(NtSequence ntseq)
    {
        BlastAnalysis blastAnalysis = new BlastAnalysis(ntseq);
        blastAnalysis.launch();
        return blastAnalysis.getGenome();
    }
}