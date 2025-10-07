import java.io.IOException;
import AnalizadorLexico.*;
import AnalizadorSintactico.*;

public  class Main {
    public static void main (String []args) throws IOException{
       Parser parser = new Parser();
       AnalisisLexico aLex = new AnalisisLexico("C:\\FACULTAD\\Cuarto\\compiladores\\TPE_Compiladores\\texto.txt");
        parser.setAlex(aLex);
        parser.run();
        aLex.imprimirTabla();

    }}

        /*public static void main(String[] args) throws IOException {
            AnalisisLexico aLex = new AnalisisLexico("C:\\FACULTAD\\Cuarto\\compiladores\\TPE_Compiladores\\texto.txt");
            int token;
            while ((token = aLex.yylex()) != 0) {
                System.out.println("Token: " + token);
            }
            aLex.imprimirTabla();
        }
    }*/

