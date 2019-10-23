package atm;

import java.io.IOException;

public class Teste {
	public static void main(String[] args) throws IOException {
		ArticleTextMiner i = new ArticleTextMiner();
		i.setUrlPath("./data/countrytest.pdf");
		for (String a : i.Country()) {
			System.out.println(a);
		}
	}
}
