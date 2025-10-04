import java.util.ArrayList;
import java.util.HashMap;

public class AccionSem6 implements AccionSem{
    //Devuelve el ultimo caracter u chequea si el identificador esta en la tabla de simbolos, sino lo agrega
    private Buffer buffer;
    private HashMap<String, Integer> tablaDeSimbolos;

    public AccionSem6(Buffer buffer, HashMap<String, Integer> tablaDeSimbolos){
        this.buffer = buffer;
        this.tablaDeSimbolos = tablaDeSimbolos;
    }
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter) {
        buffer.agregarCaracter(caracter);
        if(lexema.getLexema().length() > 20){
            String nuevoLexema = lexema.getLexema().substring(0,20);
            System.out.println("Warning: El identificador "+lexema.getLexema()+" fue truncado a: "+nuevoLexema);
            lexema.reescribirLexema(nuevoLexema);
        }
        if (!tablaDeSimbolos.containsKey(lexema.getLexema())){
            tablaDeSimbolos.put(lexema.getLexema(), 12);
            lexema.setToken(12);
        }
        return lexema;
    }
}
