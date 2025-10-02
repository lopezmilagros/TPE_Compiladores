import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        AnalisisLexico aLex = new AnalisisLexico("C:\\FACULTAD\\Cuarto\\compiladores\\TPE_Compiladores\\texto.txt");

        int i = aLex.obtenerToken();

        while (i != -2) {
            System.out.println("TOKEN: "+i);
            i = aLex.obtenerToken();
        }
        //System.out.println("TOKEN COMPLETO: "+tokenLexema.getLexema());

        aLex.imprimirTabla();
    }
}