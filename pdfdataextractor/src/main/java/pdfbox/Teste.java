package pdfbox;

import java.io.IOException;
import java.io.PrintWriter;

public class Teste {
    public static void main(String[] args) throws IOException {
    	ArticleTextMiner i = new ArticleTextMiner();
    	i.setUrlPath("C:\\Users\\Rute\\Documents\\testesPDFE\\New construction heuristic algorithm for.pdf");
    	i.getCountry();
    }
}
