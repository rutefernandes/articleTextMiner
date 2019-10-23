package atm;

import java.io.IOException;
import java.util.List;

public interface IArticleTextMiner {
	public String getUrlPath();

	public void setUrlPath(String path);

	public String Title() throws IOException;

	public String Abstract() throws IOException;

	public void Keywords() throws IOException; //TO DO

	public List<String> Country() throws IOException;

	public void References() throws IOException; // TO DO
}
