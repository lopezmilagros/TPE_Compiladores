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
        //llenamos la matriz estados.

        //El estado final sera el numero 18, el estado de error sera -1
        for (int i = 0; i <= 17; i++) {
            for (int j = 0; j <= 26; j++) {
                // primero le pongo valor de error
                estados[i][j] = -1;

                switch (i) {
                    case 0 -> {
                        switch (j) {
                            case 0 -> estados[i][j] = 1;
                            case 1, 2, 3, 5 -> estados[i][j] = 10;
                            case 4 -> estados[i][j] = 11;
                            case 6 -> estados[i][j] = 3;
                            case 7, 9, 10, 14, 16, 17, 18, 19, 20, 21 -> estados[i][j] = 18;
                            case 8 -> estados[i][j] = 9;
                            case 11 -> estados[i][j] = 13;
                            case 12, 13 -> estados[i][j] = 14;
                            case 15 -> estados[i][j] = 12;
                            case 22 -> estados[i][j] = 8;
                            case 23, 25, 26 -> estados[i][j] = 0;
                            case 24 -> estados[i][j] = 15;
                        }
                    }
                    case 1 -> {
                        switch (j) {
                            case 0 -> estados[i][j] = 1;
                            case 1 -> estados[i][j] = 2;
                            case 6 -> estados[i][j] = 4;
                        }
                    }
                    case 2 -> {
                        if(j == 2)
                            estados[i][j] = 18;
                    }
                    case 3 -> {
                        if(j == 0)
                            estados[i][j] = 4;
                    }
                    case 4 -> {
                        switch (j) {
                            case 0 -> estados[i][j] = 4;
                            case 3 -> estados[i][j] = 5;
                            default -> estados[i][j] = 18; //todos los demas los envio a finalizado
                        }
                    }
                    case 5 -> {
                        if (j == 7 || j == 8)
                            estados[i][j] = 6;
                    }
                    case 6 -> {
                        if(j == 0)
                            estados[i][j] = 7;
                    }
                    case 7 -> {
                        if (j == 0)
                            estados[i][j] = 7;
                        else
                            estados[i][j] = 18;
                    }
                    case 8 -> {
                        if(j == 22)
                            estados[i][j] = 18;
                        else if (j != 23)
                            estados[i][j] = 8;
                    }
                    case 9, 12, 14 -> {
                        estados[i][j] = 18;
                    }
                    case 10 -> {
                        switch (j) {
                            case 1, 2, 3, 5 -> estados[i][j] = 10;
                            default -> estados[i][j] = 18;
                        }
                    }
                    case 11 -> {
                        if (j == 4)
                            estados[i][j] = 11;
                        else
                            estados[i][j] = 18;
                    }
                    case 13 -> {
                        if (j == 15)
                            estados [i][j] = 18;
                    }
                    case 15 -> {
                        if(j == 24)
                            estados [i][j] = 16;
                    }
                    case 16 -> {
                        if (j == 24)
                            estados [i][j] = 17;
                        else
                            estados [i][j] = 16;
                    }
                    case 17 -> {
                        if(j == 24)
                            estados [i][j] = 0;
                    }
                }
            }
        }
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
