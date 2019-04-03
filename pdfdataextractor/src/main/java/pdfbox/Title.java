package pdfbox;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class Title extends PDFTextStripper {
	 private String filePath;
	 private LinkedHashMap<String, List<TextPosition>> title;
	 public static List<String> words = new ArrayList<String>();
	 private boolean titleStartFlag = false,
	            titleEndFlag = false;
	
	public Title() throws IOException {
        title = new LinkedHashMap<>();
	}
	
	public Title(String path) throws IOException {
        this.filePath = path;
		title = new LinkedHashMap<>();
	}
	
	public boolean getTitleStartFlag() {
		return this.titleStartFlag;
	}
	
	public void setTitleStartFlag(boolean titleStartFlag) {
		this.titleStartFlag = titleStartFlag;
	}
	
	public boolean getTitleEndFlag() {
		return this.titleEndFlag;
	}
	
	public void setTitleEndFlag(boolean titleEndFlag) {
		this.titleEndFlag = titleEndFlag;
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public LinkedHashMap<String, List<TextPosition>> getTitle() {
		return this.title;
	}
	
    public void setTitle(LinkedHashMap<String, List<TextPosition>> title) {
        this.title = title;
    }
	
    private boolean process() throws IOException { // ADICIONAR PARAMETROS DE PAGINA INICIAL E FINAL
    	PDFManager pdfManager = new PDFManager((this.getFilePath()));
    	Title stripper = new Title();
        boolean toReturn = false;
        PDDocument document = null;

        try {
        	document = pdfManager.ToText();
        	stripper.setStartPage(1); 
        	stripper.setEndPage(1); 
            stripper.setSortByPosition(true);
     
            /* TO DO
            	- ESPECIFICAR AŔEA DO TITULO E SALVAR MAIOR FONTE ENCONTRADA
            */
            
            // creates a writer that works as a bridge from character streams to byte streams 
            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream()); 
            stripper.writeText(document, dummy); // This call starts the parsing process and calls writeString repeatedly.
          
            setTitle(stripper.getTitle());
            toReturn = true;
            
        } finally {
            if (document != null) {
                document.close();
            }
        }
        return toReturn;
    }
	
 /*   
    public void printWords() {
        for(String word:words){
        	if(word.equalsIgnoreCase("Keywords")){
        		System.out.println(word); 
        	}
        }
    }
    
   */


    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
        // if text size is 14 and title not started yet, then mark start of title
        if (textPositions.get(0).getFontSizeInPt() >= 14.0 && this.titleStartFlag == false) {
            this.titleStartFlag = true;
            // set string into title map
            title.put(string, textPositions);
        } else if (textPositions.get(0).getFontSizeInPt() >= 14.0 && this.titleStartFlag == true) {
            // set string into title map
            title.put(string, textPositions);
        } // if string size is less than
        else if (textPositions.get(0).getFontSizeInPt() < 14.0) {
            // mark the end of title
        }
	      /*for (TextPosition text : textPositions) {
	    	  System.out.println(string);
	      System.out.println("String[" + text.getXDirAdj() + ", " + text.getYDirAdj()
	              + " fs=" + text.getFontSize()
	              + " xscale=" + text.getXScale()
	              + " height=" + text.getHeightDir()
	              + " space=" + text.getWidthOfSpace()
	              + " width=" + text.getWidthDirAdj()
	              + " font=" + text.getFont().toString()
	              + " fontSizeInPT=" + text.getFontSizeInPt()
	              + "]" + text.getUnicode()
	      )
  }
  System.out.println(string);
  System.out.println("\n");
        
       ; 
      */  
    }

   public String getTitleAsString() throws IOException {        
       process();        
       return getMapAsString(this.getTitle());
   }

   private String getMapAsString(LinkedHashMap<String, List<TextPosition>> map) {
       StringBuffer sb = new StringBuffer();
       for (Map.Entry<String, List<TextPosition>> entry : map.entrySet()) {
           String word = entry.getKey();
        //   List<TextPosition> positions = entry.getValue();
           sb.append(word);
           sb.append(" ");
       }
       
       return sb.toString().trim();
   }
	
}

