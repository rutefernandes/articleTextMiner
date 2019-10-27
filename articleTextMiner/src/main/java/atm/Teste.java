package atm;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Teste {
	public static void main(String[] args) throws IOException {
		ArticleTextMiner i = new ArticleTextMiner("./data/template_ieee.pdf");
		System.out.println(i.Title());
		System.out.println(i.Abstract());
		for (String a : i.Country()) {
			System.out.println(a);
		}
		System.out.println("REFERENCES");
		System.out.println(i.References());
	}
}
