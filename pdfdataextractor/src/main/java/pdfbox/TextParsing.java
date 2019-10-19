package pdfbox;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;

public class TextParsing {
	private PDFParser parser;
	private PDDocument pdDoc ;
	private COSDocument cosDoc ;
	private String filePath;
	private File file;
	
    public TextParsing(String filePath) {
        this.setFilePath(filePath);
    }

	public PDFParser getParser() {
		return parser;
	}
	
	public void setParser(PDFParser parser) {
		this.parser = parser;
	}
	
	public PDDocument getPdDoc() {
		return pdDoc;
	}

	public void setPdDoc(PDDocument pdDoc) {
		this.pdDoc = pdDoc;
	}
	
	public COSDocument getCosDoc() {
		return cosDoc;
	}

	public void setCosDoc(COSDocument cosDoc) {
		this.cosDoc = cosDoc;
	}
    
	 public void setFilePath(String filePath) {
	     this.filePath = filePath;
	 }

	public String getFilePath() {
		return filePath;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
    
    public PDDocument ToText() throws IOException {
        this.setPdDoc(null);
        this.setCosDoc(null);
        
        setFile(new File(getFilePath()));
        setParser(new PDFParser(new RandomAccessFile(getFile(),"r"))); 
        
        getParser().parse();
        setCosDoc(getParser().getDocument());
        setPdDoc(new PDDocument(getCosDoc()));
        return getPdDoc();
    }

}
