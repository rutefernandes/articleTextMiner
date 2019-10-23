package atm;

import java.io.IOException;
import java.util.List;

public class ArticleTextMiner implements IArticleTextMiner {
	private String path = null;
	private CountryMiner country;
	private AbstractMiner abs;
	private TitleMiner title;
	private ReferencesMiner references;

	public ArticleTextMiner() {
		
	}
	
	public ArticleTextMiner(String path){
		this.path = path;
	}
	
	@Override
	public void setUrlPath(String path) {
		this.path = path;
	}

	@Override
	public String getUrlPath() {
		return this.path;
	}

	private CountryMiner getCountry() {
		return country;
	}

	private void setCountry(CountryMiner country) {
		this.country = country;
	}

	private AbstractMiner getAbs() {
		return abs;
	}

	private void setAbs(AbstractMiner abs) {
		this.abs = abs;
	}

	private TitleMiner getTitle() {
		return title;
	}

	private void setTitle(TitleMiner title) {
		this.title = title;
	}

	private ReferencesMiner getReferences() {
		return references;
	}

	private void setReferences(ReferencesMiner references) {
		this.references = references;
	}
	
	@Override
	public String Title() throws IOException {
		setTitle(new TitleMiner(getUrlPath()));
		return getTitle().getMapAsString();
	}

	@Override
	public String Abstract() throws IOException {
		setAbs(new AbstractMiner(getUrlPath()));
		return getAbs().getAbstractAsString();
	}

	@Override
	public void Keywords() throws IOException { // TO DO
		//setKeywordsMiner(new Keywor)
	}

	@Override
	public List<String> Country() throws IOException {
		setCountry(new CountryMiner(getUrlPath()));
		return country.getCountriesAsArrayList();
	}
	
	@Override
	public void References() throws IOException {
		setReferences(new ReferencesMiner(getUrlPath()));
		references = new ReferencesMiner(getUrlPath());
		System.out.println(references.getReferencesAsString());
	}

}
