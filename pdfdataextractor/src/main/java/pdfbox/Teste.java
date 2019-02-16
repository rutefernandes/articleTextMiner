package pdfbox;

import java.io.IOException;
import java.io.PrintWriter;

public class Teste {
    public static void main(String[] args) throws IOException {
    	PDFManager pdfManager = new PDFManager();
        pdfManager.setFilePath("/home/rute/ex.pdf");
        PrintWriter out = new PrintWriter("/home/rute/dadosbrutos.txt");
        out.println(pdfManager.ToText());
        out.close(); 
	}	
}
