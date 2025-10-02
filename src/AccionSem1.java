public class AccionSem1 implements AccionSem{

    //va a recibir el lexema null (a chequear)
    public TokenLexema ejecutar(TokenLexema lexema, char caracter) {
        TokenLexema tokenLexema = new TokenLexema();
        tokenLexema.setLexema(caracter);
        return tokenLexema;
    }
}
