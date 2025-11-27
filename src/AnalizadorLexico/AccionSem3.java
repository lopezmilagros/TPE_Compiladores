package AnalizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;

public class AccionSem3 implements AccionSem{
    private HashMap<String, ArrayList<String>> tablaDeSimbolos;
    private ArrayList<String> erroresLexicos;

    public AccionSem3(HashMap<String, ArrayList<String>> tablaDeSimbolos, ArrayList<String> erroresLexicos){
        this.tablaDeSimbolos = tablaDeSimbolos;
        this.erroresLexicos = erroresLexicos;
    }

    //
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter, int nroLinea) {
        long numero = Long.parseLong(lexema.getLexema());
        long max = 4294967295L; // 2^32 -1
        int min = 0;
        if ( numero <= max & numero >= min){
            lexema.setToken(265);
            ArrayList<String> a = new ArrayList<>();
            a.add(0, "CTE");
            a.add(1,"ULONG");
            tablaDeSimbolos.put(lexema.getLexema(), a);
            lexema.setLexema(caracter);
            return lexema;
        }
        else {
            erroresLexicos.add("Linea "+nroLinea+": ERROR LEXICO: el ulong se exedio del rango "+lexema.getLexema());
            return null;
        }
    }
}
