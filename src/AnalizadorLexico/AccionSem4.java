package AnalizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;

public class AccionSem4 implements AccionSem{

    private Buffer buffer;
    private HashMap<String, ArrayList<String>> tablaDeSimbolos;
    private ArrayList<String> erroresLexicos;

    public AccionSem4(Buffer buffer, HashMap<String, ArrayList<String>> tablaDeSimbolos, ArrayList<String> erroresLexicos){
        this.tablaDeSimbolos = tablaDeSimbolos;
        this.erroresLexicos = erroresLexicos;
        this.buffer = buffer;
    }

    //escribir en el buffer denuevo el caracter leido y chequear que no pase de 64 bits
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter, int nroLinea) {
        String lex = lexema.getLexema();
        String base = "";
        String exponente = "";
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
        float base2;
        if (base == null || base == "")
            base2 = 1;
        else
            base2 = Float.parseFloat(base);

        float exponente2;
        if(exponente == null || exponente == "")
            exponente2 = 0;
        else
            exponente2 = Float.parseFloat(exponente);

        double resultado = base2 * Math.pow(10, exponente2);

        double minP = 2.2250738585072014 * Math.pow(10, -308);   //2.2250738585072014D-308
        double maxP = 1.7976931348623157 * Math.pow(10, 308);    //1.7976931348623157D+308
        //No se controlan los rangos negativos porque el lexico solo lee numeros positivos

        if ((minP < resultado && resultado < maxP) | resultado == 0.0){
            buffer.agregarCaracter(caracter);
            lexema.setToken(265);
            ArrayList<String> a = new ArrayList<>();
            a.add("DFLOAT");
            tablaDeSimbolos.put(lexema.getLexema(), a);
            return lexema;
        }
        erroresLexicos.add("Linea "+nroLinea+": ERROR LEXICO: float se exedio de rango "+lexema.getLexema());
        return null;
    }
}
