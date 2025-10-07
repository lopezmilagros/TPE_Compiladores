package AnalizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;

public class AccionSem8 implements AccionSem{
    private HashMap<String, ArrayList<String>> tablaDeSimbolos;

    public AccionSem8(HashMap<String, ArrayList<String>> tablaDeSimbolos){
        this.tablaDeSimbolos = tablaDeSimbolos;
    }
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter, int nroLinea) {
        lexema.setLexema(caracter);
        if (!tablaDeSimbolos.containsKey(lexema.getLexema())){
            ArrayList<String> a = new ArrayList<>();
            a.add("CADENA");
            tablaDeSimbolos.put(lexema.getLexema(), a);
        }
        lexema.setToken(266);
        return lexema;
    }
}
