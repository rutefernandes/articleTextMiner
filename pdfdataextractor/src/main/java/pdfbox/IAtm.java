package pdfbox;

import java.io.IOException;

public interface IAtm {
	public String getUrlPath();
	public void setUrlPath(String path);
    public void getTitle() throws IOException;
    public void getAbstract() throws IOException;
    public void getKeywords() throws IOException;
    public void getCountry() throws IOException;
    public void getReferences() throws IOException;
}
