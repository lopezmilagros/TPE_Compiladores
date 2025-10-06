package AnalizadorLexico;

public class TokenLexema {
    String lexema;
    int token;


    //vaos a tener que tener las tablas con los id y un metodo setID()
    public TokenLexema() {}

    public int getToken(){ return this.token;}
    public String getLexema(){ return  this.lexema;}

    public void setLexema(char caracter) {
        if (this.lexema != null)
            this.lexema = this.lexema + caracter;
        else
            this.lexema = String.valueOf(caracter);
    }

    public void reescribirLexema(String lexema){
        this.lexema = lexema;
    }
    public void setToken(int token) {
        this.token = token;
    }
}
