public class TokenLexema {
    String lexema;
    int token;


    //vaos a tener que tener las tablas con los id y un metodo setID()
    public TokenLexema() {}

    public void concatenar(char token){
        this.token += token;
    }

    public int getToken(){ return this.token;}
    public String getLexema(){ return  this.lexema;}

    public void setLexema(char caracter) {
        if (this.lexema != null)
            this.lexema = this.lexema + caracter;
        else
            this.lexema = String.valueOf(caracter);
    }
    public void setToken(int token) {
        this.token = token;
    }
}
