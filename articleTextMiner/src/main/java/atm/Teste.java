package atm;

import java.io.IOException;

public class Teste {
	public static void main(String[] args) throws IOException {
		String path = "./data/Gravitational.pdf";
		ArticleTextMiner i = new ArticleTextMiner(path);
		i.setUrlPath();
		System.out.println(i.Title());
		System.out.println(i.Abstract());
		for (String a : i.Country()) {
			System.out.println(a);
		}
		i.References();
	}
}
