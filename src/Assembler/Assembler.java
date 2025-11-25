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
    private StringBuilder data = new StringBuilder();
    private StringBuilder code = new StringBuilder();
    private StringBuilder errores = new StringBuilder();
    private String ambito;
    private String llamadoFuncion;
    private int nroAux = 0;
    private int nroMensaje = 0;
    private int nroError = 0;

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
                //Quito el : del name mangling de la funcion, reemplazandolo por _
                String funcionEtiqueta = funcion.replace(":","_");
                polacaLineal.add(funcionEtiqueta + ":");  // etiqueta
                polacaLineal.addAll(polacaInversa.get(funcion));
            }
        }

        for (String elemento : polacaLineal) {
            System.out.print(elemento + ",");
        }

    }

    public void generarAssembler() {
        inicializarData();

        Stack<String> pila = new Stack<>();
        ambito = "MAIN";
        String token;
        for (int i = 0; i < polacaLineal.size(); i++){
            token = polacaLineal.get(i);
            if (token.equals("->")){
                int j = i;
                while(j < polacaLineal.size() && !polacaLineal.get(j).equals("call")){
                    String clave = ambito + ":" + polacaLineal.get(j);
                    if (ts.containsKey(clave)){
                        ArrayList<String> a = ts.get(clave);
                        if(a.contains("Nombre de funcion")){
                            llamadoFuncion = clave;
                        }
                    }
                    j++;
                }
            }
            casePolaca(token,pila);
        }

        crearArchivoASM();
    }

    public String tipoToken(String identificador) {
        if (ts.containsKey(identificador)) {
            //ES CTE porque no tiene ambito asociado
            ArrayList<String> info = ts.get(identificador);
            return (info.get(1)); //TIPO DE CTE
        } else {
            if (ts.containsKey(ambito +":"+ identificador)) {
                //puede ser identificador o cadena

                ArrayList<String> info = ts.get(ambito +":"+ identificador);
                return (info.get(0)); //ID O CADENA
            } else {
                if (ts.containsKey(llamadoFuncion+":"+ identificador)){
                    ArrayList<String> info = ts.get(llamadoFuncion +":"+ identificador);
                    return (info.get(0)); //ID O CADENA
                }
            }
        }
        return null;
    }

    public void inicializarData() {
        //Agregamos un espacio de 20 bytes, para poder imrpimir valores numericos pasados a caracteres
        data.append("IMPRESIONES DB 20 dup(0)\n");
        data.append("FORMATO db \"%d\",0\n");
        //Plasmamos la ts al segmento de data

        //ULONG 32 BITS
        for (String s : ts.keySet()) {
            ArrayList<String> info = ts.get(s);
            if(info.size() >= 3 ){ //tiene uso
                if (info.get(0).equals("ID") && (info.get(2).equals("Nombre de variable") || info.get(2).equals("Nombre de parametro"))) {
                    //Como no se pueden declarar variables DFLOAT, asumimos que todas tienen tipo = ULONG
                    //las variables llevan prefijo _, y reemplazo los : del nameMangling por _
                    s = s.replace(":","_");
                    data.append("_" + s + " DD ?\n");
                }
            }
        }
    }

    public void crearArchivoASM() {
        StringBuilder out = new StringBuilder();
        out.append(".586\n");
        out.append(".MODEL flat, stdcall\n");
        out.append("option casemap:none\n\n");

        //Agrego los includes para compilar desde windows, y poder mostrar los prints
        out.append("include \\masm32\\include\\windows.inc\n"); // las \\ se transforman en \
        out.append("include \\masm32\\include\\user32.inc\n");
        out.append("include \\masm32\\include\\kernel32.inc\n");
        out.append("include \\masm32\\include\\masm32.inc\n");

        out.append("includelib \\masm32\\lib\\user32.lib\n");
        out.append("includelib \\masm32\\lib\\kernel32.lib\n");
        out.append("includelib \\masm32\\lib\\masm32.lib\n");
        out.append("\n");


        out.append(".DATA\n");
        out.append(data).append("\n");

        out.append(".CODE\n");
        out.append("START:\n");
        out.append(code).append("\n");

        out.append("JMP FIN\n"); //salta para no imprimir errores
        out.append("; manejo de errores\n");
        out.append(errores).append("\n");

        out.append("FIN:\n invoke ExitProcess, 0\n"); //TERMINAR PROCESO WINDOWS
        out.append("END START\n");

        // Creamos el archivo
        try {
            Files.write(Path.of("programaAssembler.asm"), out.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void conversion(String dfloat){
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

    public void casePolaca(String token, Stack<String> pila){
        System.out.println(token);
        switch (token) {

            // OPERACIONES BINARIAS-----------------------------------------------------------
            case "+" -> {
                // suma
                String operando1 = pila.pop();
                String operando2 = pila.pop();
                cargarOperandos(operando1, operando2);

                code.append("ADD EAX, EBX\n");
                //salta si el flag CF fue modificado a 1 (Hubo overflow)
                nroError++;
                agregarError("La suma ha exedido el rango del tipo utilizado");
                code.append("JC ERROR"+nroError+"\n");

                //El resultado que queda en EAX lo paso a un auxiliar y este lo agrego a la pila
                nroAux++;
                data.append("@AUX" + nroAux + " DD ?\n");
                code.append("MOV @AUX"+nroAux+", EAX\n");
                pila.push("@AUX"+nroAux);
                agregarATS("@AUX"+nroAux);
            }

            case "-" -> {
                // resta
                String operando1 = pila.pop();
                String operando2 = pila.pop();

                cargarOperandos(operando1, operando2);
                code.append("SUB EAX, EBX\n");
                //si el op1 < op2 de modifica CF = 1.
                nroError++;
                agregarError("La resta ha exedido el rango del tipo utilizado");
                code.append("JC ERROR"+nroError+"\n");

                //El resultado que queda en EAX lo paso a un auxiliar y este lo agrego a la pila
                nroAux++;
                data.append("@AUX" + nroAux + " DD ?\n");
                code.append("MOV @AUX"+nroAux+", EAX\n");
                pila.push("@AUX"+nroAux);
                agregarATS("@AUX"+nroAux);
            }

            case "*" -> {
                // multiplicación
                String operando1 = pila.pop();
                String operando2 = pila.pop();

                cargarOperandos(operando1, operando2);
                //El primer operando debe estar en EAX para el mul. La funcion cargarOperandos ya lo dejo en ese reg
                code.append("MUL EBX\n");
                //salta si el flag CF fue modificado a 1 (Hubo overflow)
                nroError++;
                agregarError("La multiplicacion ha exedido el rango del tipo utilizado");
                code.append("JC ERROR"+nroError+"\n");

                //El resultado que queda en EAX lo paso a un auxiliar y este lo agrego a la pila
                nroAux++;
                data.append("@AUX" + nroAux + " DD ?\n");
                code.append("MOV @AUX"+nroAux+", EAX\n");
                pila.push("@AUX"+nroAux);
                agregarATS("@AUX"+nroAux);
            }

            case "/" -> {
                // división
                String operando1 = pila.pop();
                String operando2 = pila.pop();

                cargarOperandos(operando1, operando2);

                //salta si el flag ZF = 1. Este se activa si los numeros son iguales.
                nroError++;
                code.append("CMP EBX, 0\n");
                agregarError("No se puede realizar la division por cero");
                code.append("JZ ERROR"+nroError+"\n");

                //El primer operando debe estar en EDX:EAX, por lo que lleno a EDX de ceros.
                code.append("MOV EDX, 0\n");
                code.append("DIV EBX\n");

                //El resultado que queda en EAX lo paso a un auxiliar y este lo agrego a la pila
                nroAux++;
                data.append("@AUX" + nroAux + " DD ?\n");
                code.append("MOV @AUX"+nroAux+", EAX\n");
                pila.push("@AUX"+nroAux);
                agregarATS("@AUX"+nroAux);
            }

            case "->" -> {
                //parametro real -> parametro formal
                String operando1 = pila.pop(); // formal
                String operando2 = pila.pop(); // real
                cargarOperandos(operando1, operando2);

                //llamadoFuncion es una variable que indica el proximo ambito a llamar
                String a = llamadoFuncion.replace(":", "_");
                code.append("MOV _"+a+"_"+operando1+", EBX\n");
            }

            case "<-" -> {
                //parametro formal -> parametro real
                String operando1 = pila.pop(); // real
                String operando2 = pila.pop(); // formal
                cargarOperandos(operando1, operando2);

                //llamadoFuncion es una variable que indica el proximo ambito a llamar
                String a = ambito.replace(":", "_");
                code.append("MOV _"+a+"_"+operando1+", EBX\n");
            }

            case ":=" -> {
                // asignación común
                String operando1 = pila.pop(); //variable a asignar
                String operando2 = pila.pop();
                cargarOperandos(operando1, operando2);
                String a = ambito.replace(":", "_");
                code.append("MOV _"+a+"_"+operando1+", EBX\n");

            }

            case ">" -> {
                // mayor que
                String operando1 = pila.pop();
                String operando2 = pila.pop();

                cargarOperandos(operando1, operando2);
                //Siempre va a ser Jump above (saltos sin signo) porque los dfloats son convertidos a ulong
                code.append("CMP EAX, EBX\n");
                code.append("JA ");
            }

            case ">=" -> {

                String operando1 = pila.pop();
                String operando2 = pila.pop();

                cargarOperandos(operando1, operando2);
                code.append("CMP EAX, EBX\n");
                code.append("JAE ");
            }

            case "<" -> {
                // menor que
                String operando1 = pila.pop();
                String operando2 = pila.pop();

                cargarOperandos(operando1, operando2);
                code.append("CMP EAX, EBX\n");
                code.append("JB ");
            }

            case "<=" -> {
                // menor que
                String operando1 = pila.pop();
                String operando2 = pila.pop();

                cargarOperandos(operando1, operando2);
                code.append("CMP EAX, EBX\n");
                code.append("JBE ");
            }

            case "==" -> {
                // igualdad
                String operando1 = pila.pop();
                String operando2 = pila.pop();

                cargarOperandos(operando1, operando2);
                code.append("CMP EAX, EBX\n");
                code.append("JE ");
            }

            case "=!" -> {
                // distinto
                String operando1 = pila.pop();
                String operando2 = pila.pop();

                cargarOperandos(operando1, operando2);
                code.append("CMP EAX, EBX\n");
                code.append("JNE ");
            }

            case "BF" -> {
                // branch condicional falso, como el salto depende del tipo de condicion, el salto lo agrego en las condiciones, y aca solo agrego el salto a donde es:
                String label = pila.pop();
                // como tengo "SALTO A X", solo me quedo el X para generar la etiqueta
                int indice = label.lastIndexOf(" ");
                label = "LABEL" + label.substring(indice + 1);
                code.append(label + "\n");
            }

            // OPERACIONES UNARIAS-------------------------------------------------------

            case "call" -> {
                // llamada a función
                String operando = pila.pop();
                code.append("CALL "+operando+"\n");
            }

            case "print" -> {
                // imprimir
                String mensaje = pila.pop();
                if(mensaje.startsWith("\"")){ //Empriza con comillas, es una cadena
                    //Creo un messagebox y lo imrpimo
                    nroMensaje++;
                    data.append("msj"+nroMensaje+" db "+mensaje+", 0\n");
                    code.append("invoke MessageBox, NULL, addr msj"+nroMensaje+", addr msj"+nroMensaje+", MB_OK\n");
                }else{
                    String tipo = tipoToken(mensaje);
                    if(tipo.equals("ID")){
                        String a = ambito.replace(":","_");
                        code.append("MOV EAX, _"+a+"_"+mensaje+"\n");
                    }else if(tipo.equals("ULONG")){
                        conversion(mensaje);
                        code.append("MOV EAX, @AUX"+nroAux+"\n");
                    }else{
                        code.append("MOV EAX, _"+mensaje+"\n");
                    }
                    //Ahora imrpimo el mensaje, usando el lugar en memoria IMPRESIONES que cree en la seccion data
                    code.append("invoke wsprintf, addr IMPRESIONES, addr FORMATO, EAX\n");
                    code.append("invoke MessageBox, NULL, addr IMPRESIONES, addr IMPRESIONES, MB_OK\n");
                }
            }

            case "BI" -> {
                // branch incondicional
                String label = pila.pop();
                // como tengo "SALTO A X", solo me quedo el X para generar la etiqueta
                int indice = label.lastIndexOf(" ");
                label = "LABEL" + label.substring(indice + 1);
                code.append("JMP " + label + "\n");
            }

            // OPERACIONES ESPECIALES-----------------------------------------------------

            case "return" -> {
                // return
                System.out.println("ENTRE AL RETURN");
                ArrayList<String> variables = new ArrayList<>();
                while (!pila.peek().equals("empieza lista")) {
                    String var = pila.pop();
                    variables.add(var);
                    System.out.println("RETORNO "+var);
                }
                // saco el "empieza lista"
                pila.pop();

                code.append("RET\n");
                //Busco donde esta reemplazar_(funcion) y le asigno la primera variable del return
                //Tengo que cambiar el code a string para reemplazar lo que coincida
                String variable = variables.getLast();
                System.out.println("RETORNO "+variable);
                String flag = "reemplazar_" + ambito;
                String reemplazo = "_"+ambito.replace(":","_")+"_"+variable;

                String nuevo = code.toString().replace(flag, reemplazo);
                code.setLength(0); // vacía el StringBuilder
                code.append(nuevo); // lo vuelve a cargar

            }

            default -> {
                if (token.endsWith(":")) {
                    //label;
                    code.append("\n"+token + "\n");
                    if (token.startsWith("MAIN")) {
                        ambito = token.replace("_", ":"); //ambito necesito que este como son las claves en la TS, con :
                        ambito = ambito.substring(0, ambito.length() - 1); // le saco el ultimo ':'
                    }
                } else {
                    //identificador, constantes, cadenas  o señales de control, solo apilo
                    pila.push(token);
                }
            }
        }
    }

    public void cargarOperandos(String op1, String op2){
        //Esta funcion toma OP1 y OP2 y los carga en EAX y en EBX respectivamente

        code.append("\n");
        code.append("; cargar operandos en registros\n");
        String tipo1 = tipoToken(op1);
        String a;
        if(ts.containsKey(llamadoFuncion+ ":" +op1))
            a = llamadoFuncion.replace(":", "_");
        else
            a = ambito.replace(":","_");

        switch (tipo1){
            case "ID" ->
                    code.append("MOV EAX,_"+a+"_"+op1+"\n");
            case "ULONG" ->
                    code.append("MOV EAX,"+op1+"\n");
            case "DFLOAT" -> {
                    conversion(op1);
                    code.append("MOV EAX, @AUX"+nroAux+"\n");
            }
        }
        if(!op2.startsWith("reemplazar_")){
            String tipo2 = tipoToken(op2);
            if(ts.containsKey(llamadoFuncion+ ":" +op2))
                a = llamadoFuncion.replace(":", "_");
            else
                a = ambito.replace(":","_");

            switch (tipo2){
                case "ID" ->
                        code.append("MOV EBX,_"+a+"_"+op2+"\n");
                case "ULONG" ->
                        code.append("MOV EBX,"+op2+"\n");
                case "DFLOAT" -> {
                        conversion(op2);
                        code.append("MOV EBX, @AUX"+nroAux+"\n");
                }
            }
        }else{
            code.append("MOV EBX, "+op2+"\n");
        }

        code.append("\n");
    }

    public void agregarError(String error){
        nroMensaje++;
        //Creo un messagebox y lo imrpimo
        data.append("msj"+nroMensaje+" db \""+error+"\", 0\n");
        errores.append("ERROR"+nroError+":\n");
        errores.append("invoke MessageBox, NULL, addr msj"+nroMensaje+", addr msj"+nroMensaje+", MB_OK\n");
        errores.append("JMP FIN\n"); //terminamos la ejecucion
    }

    public void agregarATS(String aux){
        ArrayList<String> a = new ArrayList<>();
        a.add(0,"ID");
        a.add(1,"ULONG");
        a.add(2,"Variable auxiliar");
        ts.put(aux,a);
    }
}




