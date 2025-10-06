package AnalizadorLexico;

public interface AccionSem {

    public TokenLexema ejecutar(TokenLexema lexema, char caracter);
}
