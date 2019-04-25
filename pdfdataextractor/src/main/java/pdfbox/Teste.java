package pdfbox;

import java.io.IOException;
import java.io.PrintWriter;

public class Teste {
    public static void main(String[] args) throws IOException {
    	// GET ABSTRACT
    	PrintWriter out = new PrintWriter("C:\\Users\\troll\\Downloads\\dadosbrutos.txt");
    	abstractExtractor tx = new abstractExtractor("C:\\\\Users\\\\troll\\\\Downloads\\\\Minimizing-Impact-on-Jvm.pdf");
	//	System.out.println(tx.getAbstractAsString());
		out.println(tx.getAbstractAsString());
		out.close();
		
		// GET TITTLE
    	Title ts = new Title("C:\\\\Users\\\\troll\\\\Downloads\\\\Minimizing-Impact-on-Jvm.pdf");
    //	System.out.print("Title: ");
    	//System.out.println(ts.getTitleAsString());

        // GET COUNTRIES 
    	CountryExtractor ca = new CountryExtractor("C:\\Users\\troll\\Downloads\\Minimizing-Impact-on-Jvm.pdf");
    	System.out.print("Países: ");
		System.out.println(ca.getCountriesAsString());
        out.close();
        	
    }
}
