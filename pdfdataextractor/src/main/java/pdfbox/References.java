package pdfbox;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

public class References extends PDFTextStripperByArea {
	 private String filePath;
	 private boolean flag = false;
	 static List<String> references;
	 
	public References() throws IOException {
		references = new ArrayList<String>();
	}
	
	public References(String path) throws IOException {
        this.filePath = path;
        references = new ArrayList<String>();
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public List<String> getReferences() {
		return References.references;
	}
	
	public void setReferences(List<String> references) {
		References.references = references;
	}
	
   private boolean process() throws IOException { // ADICIONAR PARAMETROS DE PAGINA INICIAL E FINAL
		PDFManager pdfManager = new PDFManager((this.getFilePath()));
		References stripper = new References();
	    boolean toReturn = false;
	    PDDocument document = null;
	    int count =0;
	
	   try {
	   	document = pdfManager.ToText();
        count = document.getNumberOfPages();
//    	stripper.setStartPage(1); 
//    	stripper.setEndPage(count);
	   	stripper.setSortByPosition(true);
	   	Rectangle comunist = new Rectangle(0, 50, 298, 842);
        Rectangle capitalPig = new Rectangle(299, 50, 298, 842);
        stripper.addRegion("leftColumn", capitalPig);
        stripper.addRegion("rightColumn", comunist);
        
        for (int i = 0; i < count; i++) {
        	PDPage firstPage = document.getPage(i);
        	stripper.extractRegions( firstPage );
            
        }
        setReferences(stripper.getReferences());
	   toReturn = true;
	       
	   } catch (Exception ex) {
		   System.out.println(ex);
		   
	   }finally {
	       if (document != null) {
	           document.close();
	       }
	   }
	   return toReturn;
   }

   @Override
   protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
	   	//	System.out.println(string +" POSIÇÃO: "+ textPositions.get(0).getYDirAdj() + " FONTE: " + textPositions.get(0).getFontSizeInPt());
	    //	System.out.println(textPositions);
	    	String[] wordsInStream = string.split(getWordSeparator());
	    	Pattern p = Pattern.compile("\\[[0-9]*\\]");
	    	boolean ff = false;
	        if(wordsInStream!=null){
	            for(String word :wordsInStream){
	            	if(word.contains("REFERENCES")) {
	            		flag = true;
	            	}
	            	if(flag){
	            		references.add(word.replaceAll("\\[[0-9]*\\]", "\n"));
	                } else {
                		flag = false;
	            	}

	            }
	        }    	
	    }

	   public String getReferencesAsString() throws IOException {        
		   process();        
	       return getListAsString(this.getReferences());
	   }

	   private String getListAsString(List<String> list) {
	       StringBuffer sb = new StringBuffer();
	       for(String word:list){
	           sb.append(word);
	           sb.append(" ");
	       }
	       return sb.toString().trim();
	   }
}
