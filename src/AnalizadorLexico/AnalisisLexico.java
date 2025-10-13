package AnalizadorLexico;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import AnalizadorSintactico.*;


public class AnalisisLexico {
    private int[][] estados;
    private AccionSem [][] accionesSem;
    private Buffer buffer;
    private ArrayList<String> palabrasReservadas;
    private ArrayList<String> erroresLexicos;
    private HashMap<String, Integer> tablaTokens;
    private HashMap<String, ArrayList<String>> tablaDeSimbolos;
    private ParserVal yylval;
    private int nroLinea;

    //Esta tabla es unicamente para imprimir los tokens
    private ArrayList<String> tokensLeidos = new ArrayList<>();

    public AnalisisLexico(String ruta) throws IOException {
        nroLinea = 1;
        this.erroresLexicos = new ArrayList<>();
        this.tablaTokens = new HashMap<>();
        llenarTablaTokens();

        estados = new int[18][28];
        accionesSem = new AccionSem[18][28];
        this.buffer = new Buffer(ruta);

        this.palabrasReservadas = new ArrayList<>();
        this.tablaDeSimbolos = new HashMap<>();

        llenarMatrices();


    }

    public void llenarTablaTokens(){
        tablaTokens.put("if", 257);
        tablaTokens.put("else", 258);
        tablaTokens.put("endif", 259);
        tablaTokens.put("print", 260);
        tablaTokens.put("return", 261);
        tablaTokens.put("ulong", 262);
        tablaTokens.put("while", 263);
        tablaTokens.put("do", 264);
        tablaTokens.put("cte", 265);
        tablaTokens.put("cadena", 266);
        tablaTokens.put("id", 267);
        tablaTokens.put("cvr",268);
        tablaTokens.put(":=", 269);
        tablaTokens.put("+", 270);
        tablaTokens.put("-", 271);
        tablaTokens.put("*", 272);
        tablaTokens.put("/", 273);
        tablaTokens.put("=", 274);
        tablaTokens.put(">=", 275);
        tablaTokens.put("<=", 276);
        tablaTokens.put(">", 277);
        tablaTokens.put("<", 278);
        tablaTokens.put("==", 279);
        tablaTokens.put("!=", 280);
        tablaTokens.put("(", 281);
        tablaTokens.put(")", 282);
        tablaTokens.put("{", 283);
        tablaTokens.put("}", 284);
        tablaTokens.put("_", 285);
        tablaTokens.put(";", 286);
        tablaTokens.put("->", 287);
        tablaTokens.put(".", 288);
        tablaTokens.put(",", 289);
    }

    public void llenarMatrices(){

        //Creamos instancias de las acciones semanticas para llenar la matriz
        AccionSem a1 = new AccionSem1();
        AccionSem a2 = new AccionSem2();
        AccionSem a3 = new AccionSem3(tablaDeSimbolos, erroresLexicos);
        AccionSem a4 = new AccionSem4(buffer, tablaDeSimbolos, erroresLexicos);
        AccionSem a5 = new AccionSem5(buffer, tablaTokens, erroresLexicos);
        AccionSem a6 = new AccionSem6(buffer, tablaDeSimbolos, erroresLexicos);
        AccionSem a7 = new AccionSem7(buffer);
        AccionSem a8 = new AccionSem8(tablaDeSimbolos);

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
                            case 6 -> {estados[i][j] = 4; accionesSem[i][j] = a2;}
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

    public int yylex() throws IOException {
        int estadoActual = 0;
        int estadoSiguiente;

        //Inicializamos el token
        TokenLexema tokenLexema = new TokenLexema();
        tokenLexema.setToken(-1);

        if (buffer.ArchivoVacio()){
            System.out.println();
            System.out.println("Fin de archivo");
            return 0;}

        while (!buffer.ArchivoVacio() & estadoActual < 18) {
            char caracter = buffer.obtenerCaracter();

            //ir reccorriendo las matrices para ver estados y acciones semanticas
            int columna = obtenerColumna(caracter);

            if (columna == 23)
                //Hizo un salto de linea
                nroLinea++;

            if (columna == -1)
                //Un caracter invalido que no coincide con nincula columna de la matriz
                System.out.println("Linea "+nroLinea+": ERROR LEXICO: Caracter invalido: " + caracter + ".");
            else {
                estadoSiguiente = estados[estadoActual][columna];
                if (estadoSiguiente == -1) {
                    //Una transicion invalida, reportar error
                    AccionSem9 a9 = new AccionSem9(estadoActual, columna, erroresLexicos);
                    tokenLexema = a9.ejecutar(tokenLexema, caracter, nroLinea);
                }
                else{
                    //Transicion valida, ejecuto la accion semantica en caso de que tenga
                    AccionSem a = accionesSem[estadoActual][columna];
                    if (a != null)
                        tokenLexema = a.ejecutar(tokenLexema, caracter, nroLinea);
                }
                //Actualizo mi estado
                if (estadoSiguiente != -1)
                    estadoActual = estadoSiguiente;
                else
                    //Hubo un error de transicion y se debe finalizar el token
                    estadoActual = 18;

            }
        }

        if (tokenLexema != null) {
            if (tokenLexema.getToken() != tablaTokens.get("cte") && tokenLexema.getToken() != tablaTokens.get("cadena") && tokenLexema.getToken() != tablaTokens.get("id")) {
                if (tablaTokens.get(tokenLexema.getLexema()) != null)
                    tokenLexema.setToken(tablaTokens.get(tokenLexema.getLexema()));
            }
            else{
                //Enviamos la referencia a el lexema a traves de la variable yylval
                yylval = new ParserVal(tokenLexema.getLexema());
            }
        }
        else{
            tokenLexema = new TokenLexema();
            tokenLexema.setLexema('E');
            tokenLexema.setToken(-1);
        }

        if(tokenLexema.getToken() == -1)
            //Es un caracter de error asi que ignoro el token y vuelvo a llamar
            return yylex();

        //Lo agregamos a una lista para luego imprimir todos los tokens
        String tokenLeido = tokenLexema.getToken() + "      |       " + tokenLexema.getLexema();
        tokensLeidos.add(tokenLeido);

        return tokenLexema.getToken();
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

    public int getNroLinea(){
        return nroLinea;
    }

    public ParserVal getYylval() {
        return this.yylval;
    }

    public void imprimirTabla() {
        System.out.println();
        System.out.println("Tabla de simbolos:");
        System.out.printf("%-10s | %s%n", "Clave", "Valores");
        System.out.println("--------------------------");

        for (Map.Entry<String, ArrayList<String>> entry : tablaDeSimbolos.entrySet()) {
            String clave = entry.getKey();
            String valores = String.join(", ", entry.getValue());
            System.out.printf("%-10s | %s%n", clave, valores);
        }
        System.out.println();
    }


    public void imprimirTokensLeidos(){
        System.out.println();
        System.out.println("Lista de tokens leidos desde el analizador lexico:");
        System.out.println("Token    |      Lexema");
        System.out.println("--------------------------");
        for (String token: tokensLeidos){
            System.out.println(token);
        }
    }

    public void agregarATablaDeSimbolos(String lexema){
        //Por ahora asumimos que solo llegan ctes porque solo utilizamos este metodo para añadir ctes a la t. se simbolos

        ArrayList<String> a = new ArrayList<>();
        //Sacar solo el valor numerico si es UL
        if(lexema.contains("UL")){
            lexema = lexema.substring(0, lexema.length() - 2);
            a.add("ULONG");
        }
        else
            a.add("DFLOAT");
        //Si el lexema no esta en la tabla de simbolos lo agrega
        if (!tablaDeSimbolos.containsKey(lexema)) {
            tablaDeSimbolos.put(lexema, a);
        }
    }

    public void imprimirErroresLexicos(){
        System.out.println();
        System.out.println("Errores lexicos:");
        for (String s: erroresLexicos){
            System.out.println(s);
        }
    }
}

