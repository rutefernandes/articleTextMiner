package atm;

import java.io.IOException;

public class Teste {
	public static void main(String[] args) throws IOException {
		ArticleTextMiner i = new ArticleTextMiner();
		i.setUrlPath("./data/template_ieee.pdf");
		i.getAbstract();
	}
}
