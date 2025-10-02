public class AccionSem7 implements AccionSem{

    private Buffer buffer;

    public AccionSem7(Buffer buffer){
        this.buffer = buffer;
    }
    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter) {
        buffer.agregarCaracter(caracter);
        return lexema;
    }
}
