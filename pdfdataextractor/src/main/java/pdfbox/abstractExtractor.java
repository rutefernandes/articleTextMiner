package pdfbox;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

public class abstractExtractor extends PDFTextStripperByArea {
	 private String filePath;
	 static List<String> words = new ArrayList<String>();
	 private boolean abstractFlag = false;
	
	public abstractExtractor() throws IOException {
		words = new ArrayList<String>();
	}
	
	public abstractExtractor(String path) throws IOException {
        this.filePath = path;
        words = new ArrayList<String>();
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public List<String> getWords() {
		return this.words;
	}
	
	public void setWords(List<String> words) {
		this.words = words;
	}
	
	public void test() throws IOException {
		process();
	}
	
    private boolean process() throws IOException { // ADICIONAR PARAMETROS DE PAGINA INICIAL E FINAL
    	PDFManager pdfManager = new PDFManager((this.getFilePath()));
    	abstractExtractor stripper = new abstractExtractor();
        boolean toReturn = false;
        PDDocument document = null;

        try {
        	document = pdfManager.ToText();
            stripper.setSortByPosition(true);
            Rectangle rect = new Rectangle(10, 60, 280, 700);
            stripper.addRegion("class1", rect);
        	PDPage firstPage = document.getPage(0);
        	stripper.extractRegions( firstPage );
            stripper.addRegion("class1", rect);
           // System.out.println("aa" + stripper.getTextForRegion("class1"));
            
            // creates a writer that works as a bridge from character streams to byte streams 
            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream()); 
            stripper.writeText(document, dummy); // This call starts the parsing process and calls writeString repeatedly.
            toReturn = true;
            
        } finally {
            if (document != null) {
                document.close();
            }
        }
        return toReturn;
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
    //	System.out.println(string +" POSIÇÃO: "+ textPositions.get(0).getYDirAdj() + " FONTE: " + textPositions.get(0).getFontSizeInPt());
    	System.out.println(textPositions);
    	String[] wordsInStream = string.split(getWordSeparator());
        if(wordsInStream!=null){
            for(String word :wordsInStream){
            	if(word.contains("Abstract")) {
            		abstractFlag = true;
            	}
            	if(abstractFlag){
                	if(!word.contains("INTRODUCTION") && !word.contains("Keywords")){
                		words.add(word);
                	} else {
                		abstractFlag = false;
                	}
            	}

            }
        }    	
    }

   public String getAbstractAsString() throws IOException {        
       process();        
       return getListAsString(this.getWords());
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

