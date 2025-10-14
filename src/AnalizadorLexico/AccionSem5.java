package AnalizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;

public class AccionSem5 implements AccionSem{
    //Devolver el ultimo caracter y chequear que sea palabra reservada
    private Buffer buffer;
    private HashMap<String, Integer> tablaTokens;
    private ArrayList<String> erroresLexicos;

    public AccionSem5(Buffer buffer, HashMap<String, Integer> tablaTokens, ArrayList<String> erroresLexicos){
        this.buffer = buffer;
        this.tablaTokens = tablaTokens;
        this.erroresLexicos = erroresLexicos;
    }
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter, int nroLinea) {
        buffer.agregarCaracter(caracter);

        if (tablaTokens.containsKey(lexema.getLexema())) {
            lexema.setToken(tablaTokens.get(lexema.getLexema()));
            return lexema;
        }

        else {
            erroresLexicos.add("Linea "+nroLinea+":ERROR LEXICO: no es una palabra reservada "+lexema.getLexema()+".");
            return null;
        }
    }
}
