package AnalizadorLexico;

import java.util.HashMap;

public class AccionSem8 implements AccionSem{
    private Buffer buffer;
    private HashMap<String, Integer> tablaDeSimbolos;

    public AccionSem8(Buffer buffer, HashMap<String, Integer> tablaDeSimbolos){
        this.buffer = buffer;
        this.tablaDeSimbolos = tablaDeSimbolos;
    }
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter, int nroLinea) {
        lexema.setLexema(caracter);
        if (!tablaDeSimbolos.containsKey(lexema.getLexema())){
            tablaDeSimbolos.put(lexema.getLexema(),266);
            lexema.setToken(266);
        }
        return lexema;
    }
}
