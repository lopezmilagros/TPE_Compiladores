package AnalizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;

public class AccionSem6 implements AccionSem{
    //Devuelve el ultimo caracter u chequea si el identificador esta en la tabla de simbolos, sino lo agrega
    private Buffer buffer;
    private HashMap<String, ArrayList<String>> tablaDeSimbolos;

    public AccionSem6(Buffer buffer, HashMap<String, ArrayList<String>> tablaDeSimbolos){
        this.buffer = buffer;
        this.tablaDeSimbolos = tablaDeSimbolos;
    }
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter, int nroLinea) {
        buffer.agregarCaracter(caracter);
        if(lexema.getLexema().length() > 20){
            String nuevoLexema = lexema.getLexema().substring(0,20);
            System.out.println("Linea "+nroLinea+":WARNING: El identificador "+lexema.getLexema()+" fue truncado a: "+nuevoLexema);
            lexema.reescribirLexema(nuevoLexema);
        }
        if (!tablaDeSimbolos.containsKey(lexema.getLexema())){
            ArrayList<String> a = new ArrayList<>();
            a.add("ID");
            tablaDeSimbolos.put(lexema.getLexema(), a);
        }
        lexema.setToken(267);
        return lexema;
    }
}
