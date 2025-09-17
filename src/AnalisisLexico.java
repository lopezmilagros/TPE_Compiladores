import java.io.IOException;

public class AnalisisLexico {
    private int[][] estados;
    private int [][] accionesSem;
    Buffer buffer;

    public AnalisisLexico(String ruta) throws IOException {
        estados = new int[27][17];
        accionesSem = new int[27][17];
        llenarMatrices();

        this.buffer = new Buffer(ruta);
    }
    public void llenarMatrices(){

    }

    public TokenLexema obtenerToken(){
        int estadoActual = 0;
        int estadoSiguiente;
        TokenLexema tokenLexema = new TokenLexema();

        while (!buffer.ArchivoVacio()) | (estadoActual <> 18){
            char caracter = buffer.obtenerCaracter();

            //ir reccorriendo las matrices para ver estados y acciones semanticas
            int columna = obtenerColumna(caracter);
            estadoSiguiente = estados[estadoActual][columna];
            AccionSem a = accionesSem[estadoActual][columna];
             estadoActual = estadoSiguiente;

            }
        return tokenLexema;
    }


    public int obtenerColumna(char caracter){

        //switch case, nos va a quedar algo re largo hay que hacerlo 1x1
        return 0;
    }

}
