package Assembler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import AnalizadorSintactico.*;



public class Assembler {
    private HashMap<String, ArrayList<String>> ts;
    private ArrayList<String> polacaLineal = new ArrayList<>();
    public Assembler(HashMap<String, ArrayList<String>> ts, HashMap<String, ArrayList<String>> polacaInversa){
        this.ts = ts;

        //Convierte el hashmap en una secuencia con etiquetas segun funcion-----------------------------------------------

        // agregar primero el main
        if (polacaInversa.containsKey("MAIN")) {
            polacaLineal.add("MAIN:"); // etiqueta
            polacaLineal.addAll(polacaInversa.get("MAIN"));
        }
        // agregar el resto de las funciones
        for (String funcion : polacaInversa.keySet()) {
            if (!funcion.equals("MAIN")) { // evitamos repetir el main
                polacaLineal.add(funcion + ":");  // etiqueta
                polacaLineal.addAll(polacaInversa.get(funcion));
            }
        }

        for(String elemento : polacaLineal){
            System.out.print(elemento+",");
        }

    }
    public void generarAssembler(HashMap<String, ArrayList<String>> ts, HashMap<String, ArrayList<String>> polacaInversa){

        Stack<String> pila = new Stack<>();
        String ambito = "MAIN:";
        for (String token : polacaLineal) {
            switch (token) {
                case "+", "-", "*", "/", "->", ":=", ">", "<", "==", "=!" -> {/*operaciones binarias*/}
                case "call", "print", "BI", "BF" -> {/*operaciones unarias*/}
                case "return" , "=" -> {/*puede ser > a binaria, ver como sabemos desde donde leer*/}
                case "cond", "if", "then", "else", "cuerpo", "while" -> {/*ignorar*/}
            }
            if(token.endsWith(":")){
                /*label*/
                if(token.startsWith("MAIN:")){
                    ambito = token;
                }
            }
            else{
                //identificador o constante, solo apilo
                pila.push(token);
            }
        }
    }

    public String tipoToken(String ambito, String identificador){
        if(ts.containsKey(identificador)){
            //ES CTE porque no tiene ambito asociado
            ArrayList<String> info = ts.get(identificador);
            return(info.get(1)); //TIPO DE CTE
        }else{
            if (ts.containsKey(ambito + identificador)) {
                //puede ser identificador o cadena
                ArrayList<String> info = ts.get(identificador);
                return (info.get(0)); //ID O CADENA
            }
        }
        return null;
    }
}
