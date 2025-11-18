import java.io.IOException;
import AnalizadorLexico.*;
import AnalizadorSintactico.*;

public  class Main {
   public static void main (String []args) throws IOException{
        Parser parser = new Parser();
        AnalisisLexico aLex = new AnalisisLexico("C:\\FACULTAD\\Cuarto\\compiladores\\TPE_Compiladores\\test\\prueba.txt");
        parser.setAlex(aLex);
        parser.run();
        parser.imprimirSentencias();
        aLex.imprimirErroresLexicos();
        parser.imprimirErrores();
        parser.imprimirErroresSemanticos();
        System.out.println();
        System.out.println("TABLA DEL LEXICO");
        aLex.imprimirTabla();
        System.out.println("TABLA DEL SINTACTICO");
        parser.imprimirTabla();
        //aLex.imprimirTokensLeidos();
        parser.imprimirPolaca();
    }
}


