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
    private int nroAux = 0;

    public Assembler(HashMap<String, ArrayList<String>> ts, HashMap<String, ArrayList<String>> polacaInversa) {
        this.ts = ts;

        //Convierte el hashmap en una secuencia con etiquetas segun funcion-----------------------------------------------

        // agregar primero el main
        if (polacaInversa.containsKey("MAIN")) {
            polacaLineal.addAll(polacaInversa.get("MAIN"));
        }
        // agregar el resto de las funciones
        for (String funcion : polacaInversa.keySet()) {
            if (!funcion.equals("MAIN")) { // evitamos repetir el main
                polacaLineal.add(funcion + ":");  // etiqueta
                polacaLineal.addAll(polacaInversa.get(funcion));
            }
        }

        for (String elemento : polacaLineal) {
            System.out.print(elemento + ",");
        }

    }

    public void generarAssembler() {
        //Creamos dos estructuras en donde vamos a ir escribiendo para luego pasar todo a un archivo .asm
        StringBuilder data = new StringBuilder();
        StringBuilder code = new StringBuilder();

        inicializarData(data);


        Stack<String> pila = new Stack<>();
        String ambito = "MAIN:";
        for (String token : polacaLineal)
            casePolaca(token, code, data, pila, ambito);

        crearArchivoASM(data, code);
    }

    public String tipoToken(String ambito, String identificador) {
        if (ts.containsKey(identificador)) {
            //ES CTE porque no tiene ambito asociado
            ArrayList<String> info = ts.get(identificador);
            return (info.get(1)); //TIPO DE CTE
        } else {
            if (ts.containsKey(ambito + identificador)) {
                //puede ser identificador o cadena
                ArrayList<String> info = ts.get(identificador);
                return (info.get(0)); //ID O CADENA
            }
        }
        return null;
    }

    public void inicializarData(StringBuilder data) {
        //Plasmamos la ts al segmento de data

        //ULONG 32 BITS
        for (String s : ts.keySet()) {
            ArrayList<String> info = ts.get(s);
            if (info.get(0).equals("ID") && (info.get(2).equals("Nombre de variable") || info.get(2).equals("Nombre de parametro"))) {
                //Como no se pueden declarar variables DFLOAT, asumimos que todas tienen tipo = ULONG
                //las variables llevan prefijo _
                data.append("_" + s + " DD ?");
            }
        }
    }

    public void crearArchivoASM(StringBuilder data, StringBuilder code) {
        StringBuilder out = new StringBuilder();

        out.append(".MODEL small\n");
        out.append(".STACK 200h\n\n");

        out.append(".DATA\n");
        out.append(data).append("\n");

        out.append(".CODE\n");
        out.append("START:\n");
        out.append(code).append("\n");

        out.append("END START\n");

        // Creamos el archivo
        try {
            Files.write(Path.of("programa.asm"), out.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void cargarOperandos(String op1, String op2, String ambito, StringBuilder code, StringBuilder data) {
        //tipo1 siempre nos aseguramos que sea un ID de tipo ULONG para poder usar el CMP

        code.append("MOV EAX,"+ambito+op1+"\n");

        String tipo2 = tipoToken(ambito, op2);
        if (tipo2 != null){
            switch (tipo2){
                case "DFLOAT" -> {
                    conversion(op2, code, data);
                    code.append("MOV EBX, @AUX"+nroAux+"\n");
                    code.append("CMP EAX, EBX\n");

                }
                case "ULONG" -> {
                    code.append("CMP EAX,"+op2+"\n");
                }
                case "ID" -> {
                    // variable de tipo ulong
                    code.append("MOV EBX,"+ambito+op2+"\n");
                    code.append("CMP EAX, EBX\n");
                }
            }
        }else{
            //error
        }
    }

    public void conversion(String dfloat, StringBuilder code, StringBuilder data){
        if(!dfloat.startsWith("-")) {
            //la operacion FISTP toma el DFLOAT la pila SP y guarda en aux el nro parseado
            nroAux++;
            data.append("@AUX" + nroAux + " DQ ?"); //dfloat 64 bits
            code.append("FLD @AUX" + nroAux + "\n");// cargo en st(0) el dfloat

            nroAux++;
            data.append("@AUX" + nroAux + " DD ?"); //resultado ulong
            code.append("FISTP @AUX" + nroAux + "\n"); //convierte ulong y lo guarda en var aux
        }else{
            //No puedo convertir de negativo a unsigned

            //error
        }
    }

    public void casePolaca(String token, StringBuilder code, StringBuilder data, Stack<String> pila, String ambito){
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
                // asignación especial (parametros)
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

                String tipo1 = tipoToken(ambito, operando1);
                String tipo2 = tipoToken(ambito, token);
                if(tipo1.equals("ID")) {
                    cargarOperandos(operando1, operando2, ambito, code, data);
                    //Siempre va a ser Jump above (saltos sin signo) porque el operador 1 siemrpe es un ID
                    code.append("JA ");
                }else{
                    if(tipo2.equals("ID")) {
                        //tengo que invertir la condicion para que el inmediato sea operador 2
                        pila.push(operando1);
                        pila.push(operando2);
                        casePolaca("<", code, data, pila, ambito);
                    }else {
                        //error
                    }
                }
            }

            case ">=" -> {

                String operando1 = pila.pop();
                String operando2 = pila.pop();

                String tipo1 = tipoToken(ambito, operando1);
                String tipo2 = tipoToken(ambito, token);
                if(tipo1.equals("ID")) {
                    cargarOperandos(operando1, operando2, ambito, code, data);
                    //Siempre va a ser Jump above (saltos sin signo) porque el operador 1 siemrpe es un ID
                    code.append("JAE ");
                }else{
                    if(tipo2.equals("ID")) {
                        //tengo que invertir la condicion para que el inmediato sea operador 2
                        pila.push(operando1);
                        pila.push(operando2);
                        casePolaca("<=", code, data, pila, ambito);
                    }else {
                        //error
                    }
                }
            }

            case "<" -> {
                // menor que
                String operando1 = pila.pop();
                String operando2 = pila.pop();

                String tipo1 = tipoToken(ambito, operando1);
                String tipo2 = tipoToken(ambito, token);
                if(tipo1.equals("ID")) {
                    cargarOperandos(operando1, operando2, ambito, code, data);
                    //Siempre va a ser Jump above (saltos sin signo) porque el operador 1 siemrpe es un ID
                    code.append("JB ");
                }else{
                    if(tipo2.equals("ID")) {
                        //tengo que invertir la condicion para que el inmediato sea operador 2
                        pila.push(operando1);
                        pila.push(operando2);
                        casePolaca(">", code, data, pila, ambito);
                    }else {
                        //error
                    }
                }
            }

            case "<=" -> {
                // menor que
                String operando1 = pila.pop();
                String operando2 = pila.pop();

                String tipo1 = tipoToken(ambito, operando1);
                String tipo2 = tipoToken(ambito, token);
                if(tipo1.equals("ID")) {
                    cargarOperandos(operando1, operando2, ambito, code, data);
                    //Siempre va a ser Jump above (saltos sin signo) porque el operador 1 siemrpe es un ID
                    code.append("JBE ");
                }else{
                    if(tipo2.equals("ID")) {
                        //tengo que invertir la condicion para que el inmediato sea operador 2
                        pila.push(operando1);
                        pila.push(operando2);
                        casePolaca(">=", code, data, pila, ambito);
                    }else {
                        //error
                    }
                }
            }

            case "==" -> {
                // igualdad
                String operando1 = pila.pop();
                String operando2 = pila.pop();

                String tipo1 = tipoToken(ambito, operando1);
                String tipo2 = tipoToken(ambito, token);

                if(tipo1.equals("ID"))
                    cargarOperandos(operando1, operando2, ambito, code, data);
                else
                    if(tipo2.equals("ID"))
                        //Solo invierto los operandos en la llamada, porque el comparador es igual
                        cargarOperandos(operando2, operando1, ambito, code, data);
                    else
                        //error

                code.append("JE ");
            }

            case "=!" -> {
                // distinto
                String operando1 = pila.pop();
                String operando2 = pila.pop();

                String tipo1 = tipoToken(ambito, operando1);
                String tipo2 = tipoToken(ambito, token);

                if(tipo1.equals("ID"))
                    cargarOperandos(operando1, operando2, ambito, code, data);
                else
                    if(tipo2.equals("ID"))
                        //Solo invierto los operandos en la llamada, porque el comparador es igual
                        cargarOperandos(operando2, operando1, ambito, code, data);
                    else {
                    //error
                    }

                code.append("JNE ");
            }

            case "BF" -> {
                // branch condicional falso, como el salto depende del tipo de condicion, el salto lo agrego en las condiciones, y aca solo agrego el salto a donde es:
                String label = pila.pop();
                // como tengo "SALTO A X", solo me quedo el X para generar la etiqueta
                int indice = label.lastIndexOf(" ");
                label = "LABEL " + label.substring(indice + 1);
                code.append(label + "\n");
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
                label = "LABEL " + label.substring(indice + 1);
                code.append("JMP " + label + "\n");
            }

            // OPERACIONES ESPECIALES-----------------------------------------------------

            case "return" -> {
                // return
                ArrayList<String> variables = new ArrayList<>();
                while (!pila.firstElement().equals("empieza lista")) {
                    variables.add(pila.pop());
                }
                // saco el "empieza lista"
                pila.pop();
            }

        }

        if (token.endsWith(":")) {
            //label
            code.append(token + "\n");
            if (token.startsWith("MAIN:"))
                ambito = token;

        } else {
            //identificador, constantes, cadenas  o señales de control, solo apilo
            pila.push(token);
        }
    }
}




