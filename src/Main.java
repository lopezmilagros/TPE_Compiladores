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
               System.out.println("3.Programa 1");
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
               System.out.println("1.Funciones                6.Condiciones");
               System.out.println("2.Lambda                   7.Asignaciones");
               System.out.println("3.While                    8.Programa");
               System.out.println("4.If                       9.Print");
               System.out.println("5.Punto y coma             10.prueba");
               System.out.println();
               System.out.print("Opcion: ");

               int op = entrada.nextInt();
               switch (op){
                    case 1 -> path = "test/incorrectos/funcion.txt";
                    case 2 -> path = "test/incorrectos/lambda.txt";
                    case 3 -> path = "test/incorrectos/while.txt";
                    case 4 -> path = "test/incorrectos/if.txt";
                    case 5 -> path = "test/incorrectos/puntocoma.txt";
                    case 6 -> path = "test/incorrectos/condiciones.txt";
                    case 7 -> path = "test/incorrectos/asignaciones.txt";
                    case 8 -> path = "test/incorrectos/programa.txt";
                    case 9 -> path = "test/incorrectos/print.txt";
                    case 10 -> path = "test/incorrectos/prueba.txt";
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
          System.out.println();
          //System.out.println("TABLA DEL LEXICO");
          //aLex.imprimirTabla();
          System.out.println("TABLA DEL SINTACTICO");
          parser.imprimirTabla();
          //aLex.imprimirTokensLeidos();
          if(parser.getPolacaInversa() != null)
               parser.imprimirPolaca();

          System.out.println(parser.getError());

          Assembler a = new Assembler(parser.getTablaDeSimbolos(),parser.getPolacaInversa());
          a.generarAssembler();
     }
}


