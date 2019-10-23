package atm;

import java.io.IOException;

public class ArticleTextMiner implements IArticleTextMiner {
	private String path = null;
	private CountryMiner countryExtractor;
	private AbstractMiner abstractExtractor;
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

	@Override
	public String getTitle() throws IOException {
		title = new TitleMiner(getUrlPath());
		return title.getTitleAsString();
	}

	@Override
	public String getAbstract() throws IOException {
		abstractExtractor = new AbstractMiner(getUrlPath());
		return abstractExtractor.getAbstractAsString();
	}

	@Override
	public void getKeywords() throws IOException {

	}

	@Override
	public void getCountry() throws IOException {
		System.out.print("Countries: ");
		countryExtractor = new CountryMiner(getUrlPath());
		System.out.println(countryExtractor.getCountriesAsString());
	}

	@Override
	public void getReferences() throws IOException {
		System.out.print("References: ");
		references = new ReferencesMiner(getUrlPath());
		System.out.println(references.getReferencesAsString());
	}

}
