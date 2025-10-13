package AnalizadorLexico;

public class AccionSem1 implements AccionSem{

    //Crea un nuevo objeto TokenLexema vacío y asigna como lexema el primer carácter leído.
    public TokenLexema ejecutar(TokenLexema lexema, char caracter, int nroLinea) {
        TokenLexema tokenLexema = new TokenLexema();
        tokenLexema.setLexema(caracter);
        return tokenLexema;
    }
}
