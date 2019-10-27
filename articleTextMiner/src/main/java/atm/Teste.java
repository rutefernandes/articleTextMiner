package atm;

import java.io.IOException;

public class Teste {
	public static void main(String[] args) throws IOException {
		ArticleTextMiner i = new ArticleTextMiner("./data/games.pdf");
		System.out.println(i.Keywords());
		/*for (String a : i.Country()) {
			System.out.println(a);
		} */ 
	}
}
