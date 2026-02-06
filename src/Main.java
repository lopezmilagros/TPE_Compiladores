import java.io.IOException;
import AnalizadorLexico.*;
import AnalizadorSintactico.*;
import Assembler.Assembler;
import java.nio.file.Path;
import java.nio.file.Files;


public  class Main {
     public static void main (String[] args) throws IOException{
          String path;

          if (args.length >= 1) {
               path = args[0];
          } else {
               System.out.println("Uso: java -jar compilador.jar <archivo_fuente>");
               return;
          }


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
          System.out.println(parser.getError());

          System.out.println();
          //System.out.println("TABLA DEL LEXICO");
          //aLex.imprimirTabla();
          //aLex.imprimirTokensLeidos();
          if(parser.getPolacaInversa() != null) {
               parser.imprimirPolaca();
               Assembler a = new Assembler(parser.getTablaDeSimbolos(),parser.getPolacaInversa());

               // Ruta del archivo fuente (absoluta)
               Path fuente = Path.of(args[0]).toAbsolutePath();

               // Nombre base del archivo (sin extensión)
               String nombre = fuente.getFileName().toString();
               int punto = nombre.lastIndexOf('.');
               if (punto != -1) {
                    nombre = nombre.substring(0, punto);
               }

               // Carpeta assembler al lado del fuente
               Path outDir = fuente.getParent().resolve("assembler");
               Files.createDirectories(outDir);
               // Ruta final del .asm
               Path outAsm = outDir.resolve(nombre + ".asm");

               a.generarAssembler(outAsm.toString());
               System.out.println("ASM generado en: " + outAsm);

          }
     }
}