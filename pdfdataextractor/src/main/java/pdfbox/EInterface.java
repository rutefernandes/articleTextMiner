package pdfbox;

import java.io.IOException;

public class EInterface {
	private String path = null;
	private CountryExtractor countryExtractor;
	private abstractExtractor abstractExtractor;
	private Title title;
	private References references;
	
	public void setUrlPath(String path) {
		this.path = path;
	}
	
	public String getUrlPath() {
		return this.path;
	}
	
	public void getTitle() throws IOException {
		title = new Title(getUrlPath());
		System.out.print("Title: ");
		System.out.println(title.getTitleAsString());
	}
	
	public void getAbstract() throws IOException {
    	abstractExtractor = new abstractExtractor(getUrlPath());
    	System.out.println(abstractExtractor.getAbstractAsString());
	}
	
	public void getKeywords() throws IOException {
		
	}
	
	public void getCountry() throws IOException{
		System.out.print("Countries: ");
		countryExtractor = new CountryExtractor(getUrlPath());
		System.out.println(countryExtractor.getCountriesAsString());
	}
	
	public void getReferences() throws IOException{
		System.out.print("References: ");
		references = new References(getUrlPath());
		System.out.println(references.getReferencesAsString());
	}
	
	
}
