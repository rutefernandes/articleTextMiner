package atm;

import java.io.IOException;

public interface IArticleTextMiner {
	public String getUrlPath();

	public void setUrlPath(String path);

	public String getTitle() throws IOException;

	public String getAbstract() throws IOException;

	public void getKeywords() throws IOException;

	public void getCountry() throws IOException;

	public void getReferences() throws IOException;
}
