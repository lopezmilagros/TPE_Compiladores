import java.io.IOException;
import AnalizadorLexico.*;
import AnalizadorSintactico.*;

public  class Main {
   public static void main (String []args) throws IOException{
       Parser parser = new Parser();
       AnalisisLexico aLex = new AnalisisLexico("/home/eugenia/Documentos/Facultad/Compiladores/tp_especial/TPE_Compiladores/test/programa.txt");
        parser.setAlex(aLex);
        parser.run();
        aLex.imprimirErroresLexicos();
        aLex.imprimirTabla();
        aLex.imprimirTokensLeidos();

    }
}


