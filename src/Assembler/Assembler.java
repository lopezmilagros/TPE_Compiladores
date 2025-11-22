package Assembler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.nio.file.Files;
import java.nio.file.Path;
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
        //Creamos dos estructuras en donde vamos a ir escribiendo para luego pasar todo a un archivo .asm
        StringBuilder data = new StringBuilder();
        StringBuilder code = new StringBuilder();

        inicializarData(data);


        Stack<String> pila = new Stack<>();
        String ambito = "MAIN:";
        for (String token : polacaLineal) {
            switch (token) {

                // OPERACIONES BINARIAS-----------------------------------------------------------
                case "+" -> {
                    // suma
                    String operando1 = pila.pop();
                    String operando2 = pila.pop();
                }

                case "-" -> {
                    // resta
                    String operando1 = pila.pop();
                    String operando2 = pila.pop();
                }

                case "*" -> {
                    // multiplicación
                    String operando1 = pila.pop();
                    String operando2 = pila.pop();
                }

                case "/" -> {
                    // división
                    String operando1 = pila.pop();
                    String operando2 = pila.pop();
                }

                case "->" -> {
                    // asignación especial (según tu lenguaje)
                    String operando1 = pila.pop();
                    String operando2 = pila.pop();
                }

                case ":=" -> {
                    // asignación común
                    String operando1 = pila.pop();
                    String operando2 = pila.pop();
                }

                case ">" -> {
                    // mayor que
                    String operando1 = pila.pop();
                    String operando2 = pila.pop();

                    code.append("JG");
                }

                case ">=" -> {
                    // menor que
                    String operando1 = pila.pop();
                    String operando2 = pila.pop();

                    code.append("JGE ");
                }

                case "<" -> {
                    // menor que
                    String operando1 = pila.pop();
                    String operando2 = pila.pop();

                    code.append("JL ");
                }

                case "<=" -> {
                    // menor que
                    String operando1 = pila.pop();
                    String operando2 = pila.pop();

                    code.append("JLE ");
                }

                case "==" -> {
                    // igualdad
                    String operando1 = pila.pop();
                    String operando2 = pila.pop();

                    code.append("JE ");
                }

                case "=!" -> {
                    // distinto
                    String operando1 = pila.pop();
                    String operando2 = pila.pop();

                    //--- COMPARO

                    code.append("JNE ");
                }

                case "BF" -> {
                    // branch condicional falso, como el salto depende del tipo de condicion, el salto lo agrego en las condiciones, y aca solo agrego el salto a donde es:
                    String label = pila.pop();
                    // como tengo "SALTO A X", solo me quedo el X para generar la etiqueta
                    int indice = label.lastIndexOf(" ");
                    label = "LABEL "+label.substring(indice + 1);
                    code.append(label+"\n");
                }

                // OPERACIONES UNARIAS-------------------------------------------------------
                case "call" -> {
                    // llamada a función
                    String operando = pila.pop();
                }

                case "print" -> {
                    // imprimir
                    String operando = pila.pop();
                }

                case "BI" -> {
                    // branch incondicional
                    String label = pila.pop();
                    // como tengo "SALTO A X", solo me quedo el X para generar la etiqueta
                    int indice = label.lastIndexOf(" ");
                    label = "LABEL "+label.substring(indice + 1);
                    code.append("JMP "+label+"\n");
                }

                // OPERACIONES ESPECIALES-----------------------------------------------------

                case "return" -> {
                    // return
                    ArrayList<String> variables = new ArrayList<>();
                    while (!pila.firstElement().equals("empieza lista")){
                        variables.add(pila.pop());
                    }
                    // saco el "empieza lista"
                    pila.pop();
                }

                case "=" -> {
                    // Asignacion multiple
                    String varAsignar = pila.pop();
                    ArrayList<String> variables = new ArrayList<>();
                    while (!pila.firstElement().equals("empieza lista")){
                        variables.add(pila.pop());
                    }
                    // saco el "empieza lista"
                    pila.pop();
                }
            }
            if(token.endsWith(":")){
                //label
                code.append(token+"\n");
                if(token.startsWith("MAIN:"))
                    ambito = token;

            }
            else{
                //identificador, constantes, cadenas  o señales de control, solo apilo
                pila.push(token);
            }
        }

        crearArchivoASM(data, code);
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

    public void inicializarData(StringBuilder data){
        //Plasmamos la ts al segmento de data

        //ULONG 32 BITS
        for (String s : ts.keySet()){
            ArrayList<String> info = ts.get(s);
            if (info.get(0).equals("ID") && (info.get(2).equals("Nombre de variable") || info.get(2).equals("Nombre de parametro"))){
                //Como no se pueden declarar variables DFLOAT, asumimos que todas tienen tipo = ULONG
                //las variables llevan prefijo _
                data.append("_"+s+" DD ?");
            }
            //los CTES los declaramos aca?
        }
    }

    public void crearArchivoASM(StringBuilder data, StringBuilder code){
        StringBuilder out = new StringBuilder();

        out.append(".MODEL small\n");
        out.append(".STACK 200h\n\n");

        out.append(".DATA\n");
        out.append(data).append("\n");

        out.append(".CODE\n");
        out.append("START:\n");
        out.append(code).append("\n");

        out.append(code).append("\n");
        out.append("END START\n");

        // Creamos el archivo
        try {
            Files.write(Path.of("programa.asm"), out.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
