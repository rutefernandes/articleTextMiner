package atm;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

public class TitleMiner extends PDFTextStripperByArea {
	private String filePath;
	private LinkedHashMap<String, List<TextPosition>> title;
	private static List<String> words = new ArrayList<String>();
	private boolean tittleSrt = false, titleEndFlag = false;

	public TitleMiner() throws IOException {
		title = new LinkedHashMap<>();
	}

	public TitleMiner(String path) throws IOException {
		this.filePath = path;
		title = new LinkedHashMap<>();
	}

	private boolean getTittleSrt() {
		return this.tittleSrt;
	}

	private void setTitleStartFlag(boolean titleStartFlag) {
		this.tittleSrt = titleStartFlag;
	}

	private boolean getTitleEndFlag() {
		return this.titleEndFlag;
	}

	private void setTitleEndFlag(boolean titleEndFlag) {
		this.titleEndFlag = titleEndFlag;
	}

	private String getFilePath() {
		return this.filePath;
	}

	private LinkedHashMap<String, List<TextPosition>> getTitle() {
		return this.title;
	}

	private void setTitle(LinkedHashMap<String, List<TextPosition>> title) {
		this.title = title;
	}

	private boolean process() throws IOException {
		TextParsing pdfManager = new TextParsing((this.getFilePath()));
		TitleMiner stripper = new TitleMiner();
		boolean toReturn = false;
		PDDocument document = null;

		try {
			document = pdfManager.ToText();
			stripper.setSortByPosition(true);
			Rectangle rect = new Rectangle(10, 60, 850, 220);
			stripper.addRegion("class1", rect);
			PDPage firstPage = document.getPage(0);
			stripper.extractRegions(firstPage);

			// creates a writer that works as a bridge from character streams to byte
			// streams
			Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
			stripper.writeText(document, dummy); // This call starts the parsing process and calls writeString
													// repeatedly.
			setTitle(stripper.getTitle());
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
		if (textPositions.get(0).getFontSizeInPt() >= 14.0 && !getTittleSrt() && !getTitleEndFlag()) {
			setTitleStartFlag(true);
			title.put(string, textPositions);
		} else if (textPositions.get(0).getFontSizeInPt() >= 14.0 && getTittleSrt() && !getTitleEndFlag()) {
			title.put(string, textPositions);
		} else if (textPositions.get(0).getFontSizeInPt() < 14.0 && getTittleSrt() && !getTitleEndFlag()) {
			setTitleStartFlag(false);
			setTitleEndFlag(true);
		}
	}

	
	public String getMapAsString() throws IOException {
		process();
		return getMapAsString(this.getTitle());
	}
	
	private String getMapAsString(LinkedHashMap<String, List<TextPosition>> map) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, List<TextPosition>> entry : map.entrySet()) {
			String word = entry.getKey();
			sb.append(word);
			sb.append(" ");
		}
		
		return sb.toString().trim();
	}
}
