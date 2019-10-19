package pdfbox;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
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
		TextParsing pdfManager = new TextParsing((this.getFilePath()));
		References stripper = new References();
	    boolean toReturn = false;
	    PDDocument document = null;
	    //int count =0;
	
	   try {
	   	document = pdfManager.ToText();
       // count = document.getNumberOfPages();
    	stripper.setStartPage(1); 
    	stripper.setEndPage(1); 
	   	stripper.setSortByPosition(true);

	   // creates a writer that works as a bridge from character streams to byte streams 
	   Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream()); 
	   stripper.writeText(document, dummy); // This call starts the parsing process and calls writeString repeatedly.
       setReferences(stripper.getReferences());
	   toReturn = true;
	       
	   } finally {
	       if (document != null) {
	           document.close();
	       }
	   }
	   return toReturn;
   }

   protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
	   System.out.println("testeeeeeeee");
	   	//	System.out.println(string +" POSIÇÃO: "+ textPositions.get(0).getYDirAdj() + " FONTE: " + textPositions.get(0).getFontSizeInPt());
	    //	System.out.println(textPositions);
	    	String[] wordsInStream = string.split(getWordSeparator());
	        if(wordsInStream!=null){
	            for(String word :wordsInStream){
	          //  	if(word.contains("REFERENCES")) {
	            		flag = true;
	          //  	}
	            	if(flag){
	            		references.add(word);
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
