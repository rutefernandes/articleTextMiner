package atm;

import java.awt.Rectangle;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

public class CountryMiner extends PDFTextStripperByArea {
	private String filePath;
	private boolean brk = false;
	static List<String> words = new ArrayList<String>();
	static List<String> countriesFound = new ArrayList<String>();
	static ArrayList<String> allCountries = new ArrayList<String>(Arrays.asList("Afghanistan", "Aland Islands",
			"Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua",
			"Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh",
			"Barbados", "Barbuda", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia",
			"Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Trty.", "Brunei Darussalam", "Bulgaria",
			"Burkina Faso", "Burundi", "Caicos Islands", "Cambodia", "Cameroon", "Canada", "Cape Verde",
			"Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island",
			"Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, Democratic Republic of the",
			"Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark",
			"Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea",
			"Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland",
			"France", "French Guiana", "French Polynesia", "French Southern Territories", "Futuna Islands", "Gabon",
			"Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe",
			"Guam", "Guatemala", "Guernsey", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard", "Herzegovina",
			"Holy See", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia",
			"Iran (Islamic Republic of)", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Jamaica",
			"Jan Mayen Islands", "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea",
			"Korea (Democratic)", "Kuwait", "Kyrgyzstan", "Lao", "Latvia", "Lebanon", "Lesotho", "Liberia",
			"Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macao", "Macedonia", "Madagascar",
			"Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania",
			"Mauritius", "Mayotte", "McDonald Islands", "Mexico", "Micronesia", "Miquelon", "Moldova", "Monaco",
			"Mongolia", "Montenegro", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal",
			"Netherlands", "Netherlands Antilles", "Nevis", "New Caledonia", "New Zealand", "Nicaragua", "Niger",
			"Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau",
			"Palestinian Territory, Occupied", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines",
			"Pitcairn", "Poland", "Portugal", "Principe", "Puerto Rico", "Qatar", "Reunion", "Romania",
			"Russian Federation", "Rwanda", "Saint Barthelemy", "Saint Helena", "Saint Kitts", "Saint Lucia",
			"Saint Martin (French part)", "Saint Pierre", "Saint Vincent", "Samoa", "San Marino", "Sao Tome",
			"Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia",
			"Solomon Islands", "Somalia", "South Africa", "South Georgia", "South Sandwich Islands", "Spain",
			"Sri Lanka", "Sudan", "Suriname", "Svalbard", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic",
			"Taiwan", "Tajikistan", "Tanzania", "Thailand", "The Grenadines", "Timor-Leste", "Tobago", "Togo",
			"Tokelau", "Tonga", "Trinidad", "Tunisia", "Turkey", "Turkmenistan", "Turks Islands", "Tuvalu", "Uganda",
			"Ukraine", "United Arab Emirates", "United Kingdom", "USA", "United States", "Uruguay",
			"US Minor Outlying Islands", "Uzbekistan", "Vanuatu", "Vatican City State", "Venezuela", "Vietnam",
			"Virgin Islands (British)", "Virgin Islands (US)", "Wallis", "Western Sahara", "Yemen", "Zambia",
			"Zimbabwe"));

	public CountryMiner() throws IOException {
		words = new ArrayList<String>();
	}

	public CountryMiner(String path) throws IOException {
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
		return CountryMiner.words;
	}

	public List<String> getCountriesFound() {
		return CountryMiner.countriesFound;
	}

	public static ArrayList<String> getAllCountries() {
		return allCountries;
	}

	public static void setAllCountries(ArrayList<String> allCountries) {
		CountryMiner.allCountries = allCountries;
	}

	public void test() throws IOException {
		process();
	}

	private boolean process() throws IOException {
		TextParsing pdfManager = new TextParsing((this.getFilePath()));
		CountryMiner stripper = new CountryMiner();
		boolean toReturn = false;
		PDDocument document = null;

		try {
			document = pdfManager.ToText();
			stripper.setSortByPosition(true);
			Rectangle rect = new Rectangle(10, 60, 850, 320);
			stripper.addRegion("class1", rect);
			PDPage firstPage = document.getPage(0);
			stripper.extractRegions(firstPage);

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

	@Override
	protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
		String[] wordsInStream = string.split(getWordSeparator());
		Pattern absDash = Pattern.compile("(?i)abstract(\\s?)\\p{Pd}"); 
		if (!brk && wordsInStream != null && textPositions.get(0).getFontSizeInPt() < 14.0) {
			for (String word : wordsInStream) {
				Matcher m = absDash.matcher(word);  
				boolean startPattern = m.lookingAt();
				if (word.contains("@") || startPattern)  { 
					brk = true;
				}
				words.add(word);
			}
		}
	}

	public String getCountriesAsString() throws IOException {
		process();
		getCountryNames();
		return getListAsString(getCountriesFound());
	}
	
	public List<String> getCountriesAsArrayList() throws IOException {
		process();
		return getCountryNames();
	}
	
	private boolean contains(String item) {
		for (String i : getCountriesFound()) {
			if (i.equalsIgnoreCase(item)) {
				return true;
			}
		}
		return false;
	}

	private List<String> getCountryNames() throws ClientProtocolException, IOException {
		for (String i : getWords()) {
			if (getAllCountries().contains(i)) {
				if (!contains(i)) {
					getCountriesFound().add(i);
				}
			}
		}
		return getCountriesFound();
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