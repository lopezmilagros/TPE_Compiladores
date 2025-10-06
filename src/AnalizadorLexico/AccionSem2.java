package AnalizadorLexico;

public class AccionSem2 implements AccionSem{
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter, int nroLinea) {
        lexema.setLexema(caracter);
        return lexema;
    }
}
