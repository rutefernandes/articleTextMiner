package pdfbox;

import java.io.IOException;
import java.io.PrintWriter;

public class Teste {
    public static void main(String[] args) throws IOException {
    	TXFontSize tx = new TXFontSize();
		tx.setFilePath("/home/rute/h.pdf");
		System.out.println(tx.getTitleAsString());
		/*
        PrintWriter out = new PrintWriter("/home/rute/dadosbrutos.txt");
        out.println(pdfManager.ToText());
        out.close();
        */ 
	}	
}
