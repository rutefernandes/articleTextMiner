package atm;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;  

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

public class AbstractMiner extends PDFTextStripperByArea {
	private String filePath;
	static List<String> words = new ArrayList<String>();
	private boolean abstractFlag = false;
	private boolean finalAbsFlag = false;

	public AbstractMiner() throws IOException {
		words = new ArrayList<String>();
	}

	public AbstractMiner(String path) throws IOException {
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
		return AbstractMiner.words;
	}

	public void setWords(List<String> words) {
		AbstractMiner.words = words;
	}
	
	public boolean isAbstractFlag() {
		return abstractFlag;
	}

	public void setAbstractFlag(boolean abstractFlag) {
		this.abstractFlag = abstractFlag;
	}

	public boolean isFinalAbsFlag() {
		return finalAbsFlag;
	}

	public void setFinalAbsFlag(boolean finalAbsFlag) {
		this.finalAbsFlag = finalAbsFlag;
	}

	public void test() throws IOException {
		process();
	}

	private boolean process() throws IOException { 
		TextParsing pdfManager = new TextParsing((this.getFilePath()));
		AbstractMiner stripper = new AbstractMiner();
		boolean toReturn = false;
		PDDocument document = null;

		try {
			document = pdfManager.ToText();
			stripper.setSortByPosition(true);
			Rectangle rect = new Rectangle(10, 60, 290, 420);
			stripper.addRegion("class1", rect);
			PDPage firstPage = document.getPage(0);
			stripper.extractRegions(firstPage);
			stripper.addRegion("class1", rect);

			// creates a writer that works as a bridge from character streams to byte
			// streams
			Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
			stripper.writeText(document, dummy); // This call starts the parsing process and calls writeString
													// repeatedly.
			toReturn = true;

		} finally {
			if (document != null) {
				document.close();
			}
		}
		return toReturn;
	}


	/* Procuro pelo início do Abstract tanto pelo parametro de string como pelos textpositions
	 * 
	 * 
	 * */
	@Override
	protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
		String[] wordsInStream = string.split(getWordSeparator());
		
		/* getting font weight (from bold is expected a value >= 700) */
		float fontWeight = textPositions.get(0).getFont().getFontDescriptor().getFontWeight(); 
		
		/* checking if the font name has the word "bold" in it */
		boolean fontName = textPositions.get(0).getFont().getName().toLowerCase().contains("bold"); 
		
		/* the pattern bellow verifies if there's a word called "abstract" (either lowercase or uppercase) 
		 * followed or not by a dash with 0 or 1 spaces between them */
		Pattern absDash = Pattern.compile("(?i)abstract(\\s?)\\p{Pd}"); 
		Pattern keyDash = Pattern.compile("(?i)keywords(\\s?)\\p{Pd}");
		
		if (wordsInStream != null) {
			for (String word : wordsInStream) {
				Matcher m = absDash.matcher(word);  
				boolean startPattern = m.lookingAt();
				
				Matcher n = keyDash.matcher(word);  
				boolean kPattern = n.lookingAt();
				
				if(!isFinalAbsFlag()) {
					if(startPattern) {
						setAbstractFlag(true);
					}
					
				/*	if(fontWeight>= 700 || fontName){
						setAbstractFlag(true);
					} */
				}
				
				if (isAbstractFlag()) {  
					if (!kPattern) {
						words.add(word);
					} else {
						setFinalAbsFlag(true);
						setAbstractFlag(false);
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
		for (String word : list) {
			sb.append(word);
			sb.append(" ");
		}
		return sb.toString().trim();
	}

}
