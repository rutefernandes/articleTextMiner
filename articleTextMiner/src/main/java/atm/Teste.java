package atm;

import java.io.IOException;

public class Teste {
	public static void main(String[] args) throws IOException {
		ArticleTextMiner i = new ArticleTextMiner("./data/Gravitational.pdf");
		System.out.println(i.Title());
		System.out.println(i.Abstract());
		for (String a : i.Country()) {
			System.out.println(a);
		}
		i.References();
	}
}
