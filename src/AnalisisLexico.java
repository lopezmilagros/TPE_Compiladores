import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalisisLexico {
    private int[][] estados;
    private AccionSem [][] accionesSem;
    private Buffer buffer;
    private ArrayList<String> palabrasReservadas;
    private HashMap<String, Integer> tablaTokens;
    private HashMap<String, Integer> tablaDeSimbolos;  //HACER UN HASMAP DE STRING, LISTA

    public AnalisisLexico(String ruta) throws IOException {
        this.tablaTokens = new HashMap<>();
        llenarTablaTokens();

        estados = new int[18][28];
        accionesSem = new AccionSem[18][28];
        this.buffer = new Buffer(ruta);

        this.palabrasReservadas = new ArrayList<>();
        this.tablaDeSimbolos = new HashMap<>();

        llenarMatrices();
        //llenarPalabrasReservadas();
        System.out.println("La primera fila de la tabla de tokens: "+tablaTokens.get("if"));
    }
    public void llenarTablaTokens(){
        tablaTokens.put("if", 1);
        tablaTokens.put("else", 2);
        tablaTokens.put("endif", 3);
        tablaTokens.put("print", 4);
        tablaTokens.put("return", 5);
        tablaTokens.put("ulong", 6);
        tablaTokens.put("dfloat", 7);
        tablaTokens.put("while", 8);
        tablaTokens.put("do", 9);
        tablaTokens.put("cte", 10);
        tablaTokens.put("cadena", 11);
        tablaTokens.put("id", 12);
        tablaTokens.put(":=", 13);
        tablaTokens.put("+", 14);
        tablaTokens.put("-", 15);
        tablaTokens.put("*", 16);
        tablaTokens.put("/", 17);
        tablaTokens.put("=", 18);
        tablaTokens.put(">=", 19);
        tablaTokens.put("<=", 20);
        tablaTokens.put(">", 21);
        tablaTokens.put("<", 22);
        tablaTokens.put("==", 23);
        tablaTokens.put("!=", 24);
        tablaTokens.put("(", 25);
        tablaTokens.put(")", 26);
        tablaTokens.put("{", 27);
        tablaTokens.put("}", 28);
        tablaTokens.put("_", 29);
        tablaTokens.put(";", 30);
        tablaTokens.put("->", 31);
        tablaTokens.put("cvr",32);
    }

    public void llenarMatrices(){

        //Creamos instancias de las acciones semanticas para llenar la matriz
        AccionSem a1 = new AccionSem1();
        AccionSem a2 = new AccionSem2();
        AccionSem a3 = new AccionSem3(tablaDeSimbolos);
        AccionSem a4 = new AccionSem4(buffer, tablaDeSimbolos);
        AccionSem a5 = new AccionSem5(buffer, tablaTokens);
        AccionSem a6 = new AccionSem6(buffer, tablaDeSimbolos);
        AccionSem a7 = new AccionSem7(buffer);
        AccionSem a8 = new AccionSem8(buffer, tablaDeSimbolos);

        //El estado final sera el numero 18, el estado de error sera -1

        for (int i = 0; i <= 17; i++) {
            for (int j = 0; j <= 27; j++) {
                // primero le pongo valor de error
                estados[i][j] = -1;
                accionesSem[i][j] = null;

                switch (i) {
                    case 0 -> {
                        switch (j) {
                            case 0 -> estados[i][j] = 1;
                            case 1, 2, 3, 5 -> estados[i][j] = 10;
                            case 4 -> estados[i][j] = 11;
                            case 6 -> estados[i][j] = 3;
                            case 7, 9, 10, 14, 16, 17, 18, 19, 20, 21, 27 -> estados[i][j] = 18;
                            case 8 -> estados[i][j] = 9;
                            case 11 -> estados[i][j] = 13;
                            case 12, 13 -> estados[i][j] = 14;
                            case 15 -> estados[i][j] = 12;
                            case 22 -> estados[i][j] = 8;
                            case 23, 25, 26 -> estados[i][j] = 0;
                            case 24 -> estados[i][j] = 15;
                        }
                        if (j!=23 & j!=24 & j!=25 & j!=26)
                            accionesSem[i][j] = a1;
                    }
                    case 1 -> {
                        switch (j) {
                            case 0 -> {estados[i][j] = 1; accionesSem[i][j] = a2;}
                            case 1 -> {estados[i][j] = 2; accionesSem[i][j] = a3;}
                            case 6 -> estados[i][j] = 4;
                        }
                    }
                    case 2 -> {
                        if(j == 2) {
                            estados[i][j] = 18;
                            accionesSem[i][j] = a2;
                        }
                    }
                    case 3 -> {
                        if(j == 0) {
                            estados[i][j] = 4;
                            accionesSem[i][j] = a2;
                        }
                    }
                    case 4 -> {
                        switch (j) {
                            case 0 -> {estados[i][j] = 4; accionesSem[i][j] = a2;}
                            case 3 -> {estados[i][j] = 5; accionesSem[i][j] = a2;}
                            default -> {estados[i][j] = 18; accionesSem[i][j] = a4;} //todos los demas los envio a finalizado
                        }
                    }
                    case 5 -> {
                        if (j == 7 || j == 8) {
                            estados[i][j] = 6;
                            accionesSem[i][j] = a2;
                        }
                    }
                    case 6 -> {
                        if(j == 0) {
                            estados[i][j] = 7;
                            accionesSem[i][j] = a2;
                        }
                    }
                    case 7 -> {
                        if (j == 0) {
                            estados[i][j] = 7;
                            accionesSem[i][j] = a2;
                        }
                        else {
                            estados[i][j] = 18;
                            accionesSem[i][j] = a4;
                        }
                    }
                    case 8 -> {
                        if(j == 22) {
                            estados[i][j] = 18;
                            accionesSem[i][j] = a8;
                        }
                        else if (j != 23) {
                            estados[i][j] = 8;
                            accionesSem[i][j] = a2;
                        }
                    }
                    case 9 -> {
                        estados[i][j] = 18;
                        if (j == 12)
                            accionesSem[i][j] = a2;
                        else
                            accionesSem[i][j] = a7;
                    }
                    case 10 -> {
                        switch (j) {
                            case 1, 2, 3, 5 ->{ estados[i][j] = 10; accionesSem[i][j] = a2;}
                            default ->{ estados[i][j] = 18; accionesSem[i][j] = a6;}
                        }
                    }
                    case 11 -> {
                        if (j == 4) {
                            estados[i][j] = 11;
                            accionesSem[i][j] = a2;
                        }
                        else {
                            estados[i][j] = 18;
                            accionesSem[i][j] = a5;
                        }
                    }
                    case 12 -> {
                        estados[i][j] = 18;
                        if (j == 15 | j == 14)
                            accionesSem[i][j] = a2;
                        else
                            accionesSem[i][j] = a7;
                    }
                    case 13 -> {
                        if (j == 15) {
                            estados[i][j] = 18;
                            accionesSem[i][j] = a2;
                        }
                    }
                    case 14 ->{
                        estados[i][j] = 18;
                        if ( j == 15)
                            accionesSem[i][j] = a2;
                        else
                            accionesSem[i][j] = a7;
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

    public int yylex() {
        int estadoActual = 0;
        int estadoSiguiente;

        //Inicializamos el token
        TokenLexema tokenLexema = new TokenLexema();
        tokenLexema.setToken(-1);

        if (buffer.ArchivoVacio())
            return 0;

        while (!buffer.ArchivoVacio() & estadoActual < 18) {
            char caracter = buffer.obtenerCaracter();
            System.out.println("caracter: " + caracter);

            //ir reccorriendo las matrices para ver estados y acciones semanticas
            int columna = obtenerColumna(caracter);
            if (columna == -1)
                //Un caracter invalido que no coincide con nincula columna de la matriz
                System.out.println("Caracter invalido: " + caracter + ".");
            else {
                estadoSiguiente = estados[estadoActual][columna];
                if (estadoSiguiente == -1) {
                    //Una transicion invalida, reportar error
                    AccionSem9 a9 = new AccionSem9(estadoActual, columna);
                    tokenLexema = a9.ejecutar(tokenLexema, caracter);
                }
                else{
                    //Transicion valida, ejecuto la accion semantica en caso de que tenga
                    AccionSem a = accionesSem[estadoActual][columna];
                    if (a != null)
                        tokenLexema = a.ejecutar(tokenLexema, caracter);
                }
                //Actualizo mi estado
                if (estadoSiguiente != -1)
                    estadoActual = estadoSiguiente;
                else
                    //Hubo un error de transicion y se debe finalizar el token
                    estadoActual = 18;

            }
        }

        //Llamamos de nuevo a la funcion porque el lexema anterior dio error y no termino el archivo.
        if (tokenLexema != null) {
            if (tokenLexema.getToken() != 10 & tokenLexema.getToken() != 11 & tokenLexema.getToken() != 12) {
                if (tablaTokens.get(tokenLexema.getLexema()) != null)
                    tokenLexema.setToken(tablaTokens.get(tokenLexema.getLexema()));
            }
        }
        else{
            tokenLexema = new TokenLexema();
            tokenLexema.setLexema('E');
            tokenLexema.setToken(-1);
        }
        return tokenLexema.getToken();
    }

    public void imprimirTabla(){
        for (Map.Entry<String, Integer> entry : tablaDeSimbolos.entrySet()) {
            System.out.printf("%-20s | %d%n", entry.getKey(), entry.getValue());
        }
    }


    public int obtenerColumna(char c) {
        //Este metodo recibe un caracter y segun el tipo que sea, te devuelve el numero de columna correspondiente a las matrices

        // Dígito
        if (Character.isDigit(c)) {
            return 0;
        }

        // Letras
        if (Character.isLowerCase(c)) {
            return 4; // letra minúscula
        }
        if (Character.isUpperCase(c)) {
            if (c == 'U')
                return 1;
            if (c == 'L')
                return 2;
            if (c == 'D')
                return 3;
            return 5; // letra mayúscula
        }

        // Otros símbolos según tabla
        switch (c) {
            case '.':
                return 6;
            case '+':
                return 7;
            case '-':
                return 8;
            case '*':
                return 9;
            case '/':
                return 10;
            case ':':
                return 11;
            case '>':
                return 12;
            case '<':
                return 13;
            case '!':
                return 14;
            case '=':
                return 15;
            case '(':
                return 16;
            case ')':
                return 17;
            case '{':
                return 18;
            case '}':
                return 19;
            case ';':
                return 20;
            case '_':
                return 21;
            case '"':
                return 22;
            case '\n':
                return 23;  // salto de línea
            case '#':
                return 24;
            case '\t':
                return 25;  // tab
            case ' ':
                return 26;
            case ',':
                return 27;
            default:
                return -1;    // no encontrado
        }
    }
}
