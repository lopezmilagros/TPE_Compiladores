package AnalizadorLexico;

public class AccionSem2 implements AccionSem{

    //Concatena el nuevo carácter al lexema ya existente.
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter, int nroLinea) {
        lexema.setLexema(caracter);
        return lexema;
    }
}
