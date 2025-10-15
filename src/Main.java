import java.io.IOException;
import AnalizadorLexico.*;
import AnalizadorSintactico.*;

public  class Main {
   public static void main (String []args) throws IOException{
       Parser parser = new Parser();
       AnalisisLexico aLex = new AnalisisLexico("/home/eugenia/Documentos/Facultad/Compiladores/tp_especial/TPE_compiladores_grupo_15 /test/if.txt");
        parser.setAlex(aLex);
        parser.run();
        parser.imprimirErrores();
        aLex.imprimirErroresLexicos();
        aLex.imprimirTabla();
        aLex.imprimirTokensLeidos();

    }
}


