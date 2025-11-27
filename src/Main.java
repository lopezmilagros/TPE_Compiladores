import java.io.IOException;
import AnalizadorLexico.*;
import AnalizadorSintactico.*;
import Assembler.Assembler;

public  class Main {
     public static void main (String []args) throws IOException{
          java.util.Scanner entrada = new java.util.Scanner(System.in);
          System.out.println("1.Compilar tests correctos");
          System.out.println("2.Compilar tests con errores");
          System.out.println("3.Indicar un path de un archivo especifico a compilar");
          System.out.println();
          System.out.print("Ingrese la opcion deseada: ");
          int opcion = entrada.nextInt();
          entrada.nextLine();
          String path = null;
          if(opcion == 1){
               System.out.println();
               System.out.println("1.Programa 1");
               System.out.println("2.Programa 2");
               System.out.println("3.Programa 3");
               System.out.println();
               System.out.print("Opcion: ");

               int op = entrada.nextInt();
               switch (op){
                    case 1 -> path = "test/correctos/programa1.txt";
                    case 2 -> path = "test/correctos/programa2.txt";
                    case 3 -> path = "test/correctos/programa3.txt";
               }
          }
          else if(opcion == 2){
               System.out.println();
               System.out.println("1.Errores lexicos");
               System.out.println("2.Errores de alcance");
               System.out.println("3.Error en ejecucion");

               int op = entrada.nextInt();
               switch (op){
                    case 1 -> path = "test/incorrectos/erroresLexicos.txt";
                    case 2 -> path = "test/incorrectos/erroresAlcance.txt";
                    case 3 -> path = "test/incorrectos/erroresEnEjecucion.txt";
               }
          } else if (opcion == 3){
               System.out.println("Ingrese path: ");
               path = entrada.nextLine();
          } else { System.err.println("Opcion invalida"); System.exit(1);}

          if (path == null){ System.err.println("Opcion invalida"); System.exit(1);}


          System.out.print("Compilando....");
          Parser parser = new Parser();
          AnalisisLexico aLex = new AnalisisLexico(path);
          parser.setAlex(aLex);
          parser.run();
          parser.imprimirSentencias();
          aLex.imprimirErroresLexicos();
          parser.imprimirErrores();
          parser.imprimirErroresSemanticos();
          System.out.println("TABLA DEL SINTACTICO");
          parser.imprimirTabla();
          if(parser.getPolacaInversa() != null) {
               parser.imprimirPolaca();
               Assembler a = new Assembler(parser.getTablaDeSimbolos(),parser.getPolacaInversa());
               path = path.substring(path.lastIndexOf("/")+1, path.lastIndexOf("."));
               path = "assembler/"+path +".asm";
               a.generarAssembler(path);
          }
          System.out.println(parser.getError());

          System.out.println();
          System.out.println("Si desea imprimir las estructuras del lexico ingrese 1, sino, ingrese cualquier caracter: ");
          opcion = entrada.nextInt();
          if(opcion == 1){
               System.out.println("TABLA DEL LEXICO");
               aLex.imprimirTabla();
               aLex.imprimirTokensLeidos();
          }
     }
}


