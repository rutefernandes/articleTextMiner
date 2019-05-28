package pdfbox;

import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

public class CountryExtractor extends PDFTextStripperByArea {
	 private String filePath;
	 private boolean brk = false;
	 static List<String> words = new ArrayList<String>();
	 static List<String> countries = new ArrayList<String>();
	
	public CountryExtractor() throws IOException {
		words = new ArrayList<String>();
	}
	
	public CountryExtractor(String path) throws IOException {
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
		return CountryExtractor.words;
	}
	
	public List<String> getCountries() {
		return CountryExtractor.countries;
	}
	
	public void test() throws IOException {
		process();
	}
	
    private boolean process() throws IOException { // ADICIONAR PARAMETROS DE PAGINA INICIAL E FINAL
    	PDFManager pdfManager = new PDFManager((this.getFilePath()));
    	CountryExtractor stripper = new CountryExtractor();
        boolean toReturn = false;
        PDDocument document = null;

        try {
        	document = pdfManager.ToText();
            stripper.setSortByPosition(true);
            Rectangle rect = new Rectangle(10, 60, 560, 180);
            stripper.addRegion("class1", rect);
        	PDPage firstPage = document.getPage(0);
        	stripper.extractRegions( firstPage );
            
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
    	String[] wordsInStream = string.split(getWordSeparator());
	    if(!brk && wordsInStream!=null && textPositions.get(0).getFontSizeInPt() < 14.0) {
        	for(String word :wordsInStream){
            	if(word.contains("@") || word.contains(".com")){
            		brk = true;
            	}
            	words.add(word);
            }  	
	    }
    }
    
   public String getCountriesAsString() throws IOException {        
       process();
	   getCountryNames();
       return getListAsString(getCountries());
   }
   
   private void getCountryNames() throws ClientProtocolException, IOException {
	   for(String i:getWords()){
		   	if(CallCountries.CallHTTPGetService(i)) {
		   		getCountries().add(i);
		   	}
	   }
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