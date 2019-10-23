package atm;

import java.io.IOException;
import java.util.List;

public class ArticleTextMiner implements IArticleTextMiner {
	private String path = null;
	private CountryMiner country;
	private AbstractMiner abs;
	private TitleMiner title;
	private ReferencesMiner references;

	@Override
	public void setUrlPath(String path) {
		this.path = path;
	}

	@Override
	public String getUrlPath() {
		return this.path;
	}

	public CountryMiner getCountry() {
		return country;
	}

	public void setCountry(CountryMiner country) {
		this.country = country;
	}

	public AbstractMiner getAbs() {
		return abs;
	}

	public void setAbs(AbstractMiner abs) {
		this.abs = abs;
	}

	public TitleMiner getTitle() {
		return title;
	}

	public void setTitle(TitleMiner title) {
		this.title = title;
	}

	public ReferencesMiner getReferences() {
		return references;
	}

	public void setReferences(ReferencesMiner references) {
		this.references = references;
	}
	
	
	@Override
	public String Title() throws IOException {
		setTitle(new TitleMiner(getUrlPath()));
		return getTitle().getTitleAsString();
	}

	@Override
	public String Abstract() throws IOException {
		setAbs(new AbstractMiner(getUrlPath()));
		return getAbs().getAbstractAsString();
	}

	@Override
	public void Keywords() throws IOException {
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
