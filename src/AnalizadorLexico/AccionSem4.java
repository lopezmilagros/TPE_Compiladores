package AnalizadorLexico;

import java.util.HashMap;

public class AccionSem4 implements AccionSem{

    private Buffer buffer;
    private HashMap<String, Integer> tablaDeSimbolos;

    public AccionSem4(Buffer buffer, HashMap<String, Integer> tablaDeSimbolos){
        this.tablaDeSimbolos = tablaDeSimbolos;
        this.buffer = buffer;
    }

    //escribir en el buffer denuevo el caracter leido y chequear que no pase de 64 bits
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter, int nroLinea) {
        String lex = lexema.getLexema();
        String base = null;
        String exponente = null;
        boolean d = false;
        for (int i = 0 ; i < lex.length(); i++){
            char c = lex.charAt(i);
            if (!d) {
                if (c == 'D')
                    d = true;
                else
                    base = base + c;
            }
            else
                exponente = exponente + c;
        }

        float base2 = Float.parseFloat(base);
        float exponente2 = Float.parseFloat(exponente);

        double resultado = Math.pow(base2, exponente2);

        double minP = 2.2250738585072014 * Math.pow(10, -308);
        double maxP = 1.7976931348623157 * Math.pow(10, 308);
        double minN = -1.7976931348623157 * Math.pow(10, 308);
        double maxN = -2.2250738585072014 * Math.pow(10, -308);
        if ((minP < resultado && resultado < maxP) | (minN < resultado && resultado < maxN ) | resultado == 0.0){
            buffer.agregarCaracter(caracter);
            lexema.setToken(7);     //id de dfloat en tablaToken
            tablaDeSimbolos.put(lexema.getLexema(), lexema.getToken());
            return lexema;
        }
        System.out.println("Linea "+nroLinea+": ERROR: float se exedio de rango "+lexema.getLexema());
        return null;
    }
}
