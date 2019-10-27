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

public class KeywordsMiner extends PDFTextStripperByArea {
	private String filePath;
	static List<String> words = new ArrayList<String>();
	private boolean keywordsFlag = false;
	private boolean finalKeyFlag = false;
	static private String teste = null; 
	
	public KeywordsMiner() throws IOException {
		words = new ArrayList<String>();
	}

	public KeywordsMiner(String path) throws IOException {
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
		return KeywordsMiner.words;
	}

	public void setWords(List<String> words) {
		KeywordsMiner.words = words;
	}
	
	public boolean isKeywordsFlag() {
		return keywordsFlag;
	}

	public void setKeywordsFlag(boolean keywordsFlag) {
		this.keywordsFlag = keywordsFlag;
	}

	public boolean isFinalKeyFlag() {
		return finalKeyFlag;
	}

	public void setFinalKeywordsFlag(boolean finalKeyFlag) {
		this.finalKeyFlag = finalKeyFlag;
	}

	public void test() throws IOException {
		process();
	}

	private boolean process() throws IOException { 
		TextParsing pdfManager = new TextParsing((this.getFilePath()));
		KeywordsMiner stripper = new KeywordsMiner();
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

			// creates a writer that works as a bridge from character streams to byte streams
			Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
			// This call starts the parsing process and calls writeString repeatedly.
			stripper.writeText(document, dummy); 
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
		Pattern keyDash = Pattern.compile("(?i)keywords(\\s?)\\p{Pd}");
		Pattern indexDash = Pattern.compile("(?i)Terms(\\s?)\\p{Pd}");
		Pattern introduction = Pattern.compile("I.|1."); 
		String a = "null", b = "null", c = "null";
		
		if (wordsInStream != null) {
			for (String word : wordsInStream) {
				Matcher m = keyDash.matcher(word);  
				Matcher n = indexDash.matcher(word);
				teste = word;
				Matcher o = introduction.matcher(teste);
				if(!isFinalKeyFlag()) {
					if(m.lookingAt() || n.lookingAt()) {
						setKeywordsFlag(true);
					}
					
				/*	if(fontWeight>= 700 || fontName){
						setAbstractFlag(true);
					} */
				}
				
				if (isKeywordsFlag()) {
					System.out.println(o.lookingAt());
					if(o.lookingAt()) {
						if (word.equalsIgnoreCase("introduction")) {
							words.add(word);
						} else {
							setFinalKeywordsFlag(true);
							setKeywordsFlag(false);
						}
					}
				}
				b = word;
			}
		}
	}

	public String getKeywordsAsString() throws IOException {
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
