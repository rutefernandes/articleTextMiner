package pdfbox;

import java.io.IOException;
import java.io.PrintWriter;

public class Teste {
    public static void main(String[] args) throws IOException {
    	PrintWriter out = new PrintWriter("/home/rute/dadosbrutos.txt");
    	abstractExtractor tx = new abstractExtractor("/home/rute/AT/1.pdf");
		System.out.println(tx.getAbstractAsString());
        out.println(tx.getAbstractAsString());
        out.close();

        
    	/*
    	Title ts = new Title("/home/rute/AT/6.pdf");
		System.out.println(ts.getTitleAsString());
        out.println(ts.getTitleAsString());
        out.close();
        */
        
	}	
}
