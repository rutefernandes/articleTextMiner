package pdfbox;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFManager {
	private PDFParser parser;
	private PDDocument pdDoc ;
	private COSDocument cosDoc ;
	
	private String filePath;
	private File file;
	
    public PDFManager(String filePath) {
        this.filePath = filePath;
    }
    
    public PDDocument ToText() throws IOException {
        this.pdDoc = null;
        this.cosDoc = null;
        
        file = new File(filePath);
        parser = new PDFParser(new RandomAccessFile(file,"r")); 
        
        parser.parse();
        cosDoc = parser.getDocument();
        pdDoc = new PDDocument(cosDoc);
        return pdDoc;
    }

     public void setFilePath(String filePath) {
         this.filePath = filePath;
     }
}
