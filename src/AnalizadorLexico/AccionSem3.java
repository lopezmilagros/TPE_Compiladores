package AnalizadorLexico;

import java.util.HashMap;

public class AccionSem3 implements AccionSem{
    private HashMap<String, Integer> tablaDeSimbolos;

    public AccionSem3(HashMap<String, Integer> tablaDeSimbolos){
        this.tablaDeSimbolos = tablaDeSimbolos;
    }
    //mandarias el caracter en null
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter) {
        int numero = Integer.parseInt(lexema.getLexema());
        long max = 4294967295L; // 2^32 -1
        int min = 0;
        if ( numero <= max & numero >= min){
            lexema.setToken(6);
            tablaDeSimbolos.put(lexema.getLexema(), lexema.getToken());
            lexema.setLexema(caracter);     //id de ulong en tablaTokens

            return lexema;
        }
        else {
            System.out.println("ACCION SEMANTICA 3: el entero se exedio del rango "+lexema.getLexema());
            return null;
        }
    }
}
