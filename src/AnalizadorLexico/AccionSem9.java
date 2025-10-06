package AnalizadorLexico;

public class AccionSem9 implements AccionSem {
    //Esta accion semantica se ejecuta cuando hay una transicion invalida y reporta error
    private int fila;
    private int columna;

    public AccionSem9(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public TokenLexema ejecutar(TokenLexema lexema, char caracter) {
        //aca iria el switch case
        String[] simbolos = {
                "dígito", "U", "L", "D", "letra minúscula", "letra mayúscula",
                ".", "+", "-", "*", "/", ":", ">", "<", "!", "=", "(", ")", "{", "}", ";",
                "_", "comillas dobles", "salto de línea", "#", "TAB", "espacio", ","
        };

        switch (fila) {

            case 1:
                System.out.println("Error: se esperaba una 'U', un digito o un '.'  y llegó '" + simbolos[columna] + "'");
                break;

            case 2:
                System.out.println("Error: en el estado 2 se esperaba una 'L' y llegó '" + simbolos[columna] + "'");
                break;

            case 3:
                System.out.println("Error: se esperaba un número y llegó '" + simbolos[columna] + "'");
                break;

            case 5:
                System.out.println("Error: se esperaba '+' o '-' y llegó  '" + simbolos[columna] + "'");
                break;

            case 6:
                System.out.println("Error: se esperaba un digito y llegó '" + simbolos[columna] + "'");
                break;

            case 8:
                System.out.println("Error: salto de linea inválido '");
                break;

            case 13:
                System.out.println("Error: se esperaba '=' y llegó  '" + simbolos[columna] + "'");
                break;

            case 15,17:
                System.out.println("Error: se esperaba '#' y llegó  ' '" + simbolos[columna] + "'");
                break;

            default:
                System.out.println("Error: caracter invalido: '" + simbolos[columna] + "'");
                break;
        }
        return null;
    }
}