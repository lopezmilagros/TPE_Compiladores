package AnalizadorLexico;

import java.util.ArrayList;

public class AccionSem9 implements AccionSem {
    //Esta accion semantica se ejecuta cuando hay una transicion invalida y reporta error
    private int fila;
    private int columna;
    private ArrayList<String> erroresLexicos;

    public AccionSem9(int fila, int columna, ArrayList<String> erroresLexicos) {
        this.fila = fila;
        this.columna = columna;
        this.erroresLexicos = erroresLexicos;
    }

    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter, int nroLinea) {
        String[] simbolos = {
                "dígito", "U", "L", "D", "letra minúscula", "letra mayúscula",
                ".", "+", "-", "*", "/", ":", ">", "<", "!", "=", "(", ")", "{", "}", ";",
                "_", "comillas dobles", "salto de línea", "#", "TAB", "espacio", ","
        };

        switch (fila) {

            case 1:
                erroresLexicos.add("Linea "+nroLinea+": ERROR LEXICO: se esperaba una 'U', un digito o un '.'  y llegó '" + simbolos[columna] + "'");
                break;

            case 2:
                erroresLexicos.add("Linea "+nroLinea+": ERROR LEXICO: en el estado 2 se esperaba una 'L' y llegó '" + simbolos[columna] + "'");
                break;

            case 3:
                erroresLexicos.add("Linea "+nroLinea+": ERROR LEXICO: se esperaba un número y llegó '" + simbolos[columna] + "'");
                break;

            case 5:
                erroresLexicos.add("Linea "+nroLinea+": ERROR LEXICO: se esperaba '+' o '-' y llegó  '" + simbolos[columna] + "'");
                break;

            case 6:
                erroresLexicos.add("Linea "+nroLinea+": ERROR LEXICO: se esperaba un digito y llegó '" + simbolos[columna] + "'");
                break;

            case 8:
                erroresLexicos.add("Linea "+nroLinea+": ERROR LEXICO: salto de linea inválido '");
                break;

            case 13:
                erroresLexicos.add("Linea "+nroLinea+": ERROR LEXICO: se esperaba '=' y llegó  '" + simbolos[columna] + "'");
                break;

            case 15,17:
                erroresLexicos.add("Linea "+nroLinea+": ERROR LEXICO: se esperaba '#' y llegó  ' '" + simbolos[columna] + "'");
                break;

            default:
                System.out.println("Linea "+nroLinea+": ERROR LEXICO: caracter invalido: '" + simbolos[columna] + "'");
                break;
        }
        return null;
    }
}