package AnalizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;

public class AccionSem3 implements AccionSem{
    private HashMap<String, ArrayList<String>> tablaDeSimbolos;

    public AccionSem3(HashMap<String, ArrayList<String>> tablaDeSimbolos){
        this.tablaDeSimbolos = tablaDeSimbolos;
    }

    //
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter, int nroLinea) {
        int numero = Integer.parseInt(lexema.getLexema());
        long max = 4294967295L; // 2^32 -1
        int min = 0;
        if ( numero <= max & numero >= min){
            lexema.setToken(265);
            ArrayList<String> a = new ArrayList<>();
            a.add("ULONG");
            tablaDeSimbolos.put(lexema.getLexema(), a);
            lexema.setLexema(caracter);

            return lexema;
        }
        else {
            System.out.println("Linea "+nroLinea+": ERROR LEXICO el entero se exedio del rango "+lexema.getLexema());
            return null;
        }
    }
}
