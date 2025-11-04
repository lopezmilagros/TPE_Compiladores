%{
package AnalizadorSintactico;
import java.io.*;
import java.util.ArrayList;
import AnalizadorLexico.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
%}



%token IF ELSE ENDIF PRINT RETURN ULONG WHILE DO CTE CADENA ID CVR ASIGN MAS MENOS AST BARRA IGUAL MAYIG MENIG MAYOR MENOR IGIG DIF PARENTESISA PARENTESISC LLAVEA LLAVEC GUIONB PUNTOCOMA FLECHA PUNTO COMA
%left MAS MENOS
%left AST BARRA

%%
prog                :ID LLAVEA sentencias LLAVEC                                                                            {modificarUsoTS($1.sval, "Nombre de programa"); polacaInversa.put("MAIN", mainArreglo);}
                    |LLAVEA sentencias LLAVEC                                                                               {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de programa");}
                    |ID sentencias LLAVEC                                                                                   {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura");}
                    |ID LLAVEA sentencias                                                                                   {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador final");}
                    |ID sentencias                                                                                          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores");}
                    |error PUNTOCOMA                                                                                        {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO");}
                    ;

sentencias          :sentencias sentencia
                    |sentencia
                    ;

sentencia           :sentencia_declarativa PUNTOCOMA
                    |sentencia_ejecutable PUNTOCOMA
                    |sentencia_error                                                                                        {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
                    ;

sentencia_error     :sentencia_declarativa error
                    |sentencia_ejecutable error
                    ;


sentencia_declarativa :declaracion                                                                                            {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:declaracion");}
                      ;

sentencia_ejecutable:asignaciones
                    |IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ENDIF                                    {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: if");}
                    |IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF      {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:if else");}
                    |WHILE PARENTESISA condicion PARENTESISC DO LLAVEA sentencias LLAVEC                                    {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:while");}
                    |RETURN PARENTESISA lista_id PARENTESISC                                                                {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
                    |RETURN PARENTESISA expresiones PARENTESISC                                                             {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
                    |llamado_funcion                                                                                        {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:llamado de funcion");}
                    |expresion_lambda                                                                                       {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:lambda");}
                    |PRINT PARENTESISA CADENA PARENTESISC                                                                   {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
                    |PRINT PARENTESISA expresiones PARENTESISC                                                              {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
                    |PRINT PARENTESISA error PARENTESISC                                                                    {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: argumento inválido en print");}
                    |IF error condicion PARENTESISC LLAVEA sentencias LLAVEC ENDIF                                          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
                    |IF PARENTESISA condicion LLAVEA sentencias LLAVEC ENDIF                                                {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
                    |IF error condicion error LLAVEA sentencias LLAVEC ENDIF                                                {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
                    |IF error condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF            {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
                    |IF PARENTESISA condicion  LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF                 {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
                    |IF error condicion error LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF                  {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
                    |WHILE error condicion PARENTESISC DO LLAVEA sentencias LLAVEC                                          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
                    |WHILE PARENTESISA condicion  DO LLAVEA sentencias LLAVEC                                               {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
                    |WHILE error condicion error DO LLAVEA sentencias LLAVEC                                                {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
                    |WHILE PARENTESISA condicion PARENTESISC DO error                                                       {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta cuerpo en la iteracion");}
                    |IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC                                          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
                    |IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC            {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
                    |IF PARENTESISA condicion PARENTESISC ENDIF                                                             {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
                    |IF PARENTESISA condicion PARENTESISC ELSE LLAVEA sentencias LLAVEC ENDIF                               {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
                    |IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE ENDIF                               {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else'");}
                    |WHILE PARENTESISA condicion PARENTESISC error LLAVEA sentencias LLAVEC                                 {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'do' en iteracion");}
                    ;

condicion           :expresiones MAYOR expresiones
                    |expresiones MAYIG expresiones
                    |expresiones MENOR expresiones
                    |expresiones MENIG expresiones
                    |expresiones IGIG expresiones
                    |expresiones DIF expresiones
                    |expresiones_error                                                                                      {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta comparador en expresion");}
                    ;

expresiones_error   :expresiones MAYOR
                    |expresiones MAYIG
                    |expresiones MENOR
                    |expresiones MENIG
                    |expresiones IGIG
                    |expresiones DIF
                    |MAYOR expresiones
                    |MAYIG expresiones
                    |MENOR expresiones
                    |MENIG expresiones
                    |IGIG expresiones
                    |DIF expresiones
                    |expresiones
                    ;

expresiones         :expresiones operador termino
                    |termino
                    |expresiones operador error                                                                             {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: falta operando en expresion");}
                    ;

termino             :tipo_id
                    |tipo_cte
                    |llamado_funcion
                    ;

operador            :MAS
                    |MENOS
                    |AST
                    |BARRA
                    ;

declaracion         :tipo tipo_id                                                                                           {ArrayList<String> b = new ArrayList<String>(); b.add($2.sval); modificarTipoTS(b, $1.sval); modificarUsoTS($2.sval, "Nombre de variable");}
                    |tipo lista_id                                                                                          {ArrayList<String> a = (ArrayList<String>)$2.obj; modificarTipoTS(a, $1.sval); modificarUsos(a, "Nombre de variable");}
                    |header_funcion LLAVEA sentencias LLAVEC                                                                {String ambitoConFuncion = ambito; borrarAmbito(); limpiarPolaca(ambitoConFuncion);}
                    |tipo error PARENTESISA parametros_formales PARENTESISC LLAVEA sentencias LLAVEC                        {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
                    ;

header_funcion      :tipo ID PARENTESISA parametros_formales PARENTESISC                                                    {modificarUsoTS($2.sval, "Nombre de funcion"); ambito = ambito + ":" + $2.sval; modificarAmbitosTS((ArrayList<String>)$4.obj); polacaInversa.put(ambito, (ArrayList<String>) $4.obj); }
                    ;

lista_id            :tipo_id COMA tipo_id                                                                                   {ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add($1.sval); arreglo.add($3.sval); $$ = new ParserVal(crearArregloId($1.sval, $3.sval)); }
                    |lista_id COMA tipo_id                                                                                  {ArrayList<String> arreglo = (ArrayList<String>) $1.obj; arreglo.add($3.sval); $$ = new ParserVal(arreglo);}
                    |lista_id tipo_id                                                                                       {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
                    |tipo_id tipo_id                                                                                        {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
                    ;

tipo                :ULONG                                                                                                  {$$ = new ParserVal("ULONG");}
                    ;

llamado_funcion     :ID PARENTESISA parametros_reales PARENTESISC
                    ;

parametros_reales   :parametros_reales COMA expresiones FLECHA tipo_id
                    |expresiones FLECHA tipo_id
                    |expresiones FLECHA error                                                                               {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta especificacion del parametro formal");}
                    ;

parametros_formales :parametros_formales COMA parametro                                                                     {ArrayList<String> a = (ArrayList<String>)$1.obj; a.add($3.sval); $$ = new ParserVal(a);}
                    |parametro                                                                                              {ArrayList<String> a = new ArrayList<String>(); a.add($1.sval); $$ = new ParserVal(a);}
                    |parametros_formales parametro                                                                          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta ',' en declaracion de las variables");}
                    ;

parametro           :CVR tipo tipo_id                                                                                       {modificarUsoTS($3.sval, "Nombre de parametro"); $$ = new ParserVal($3.sval);}
                    |tipo tipo_id                                                                                           {modificarUsoTS($2.sval, "Nombre de parametro"); $$ = new ParserVal($2.sval);}
                    |tipo error                                                                                             {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta nombre del parametro formal");}
                    |CVR tipo error                                                                                         {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta nombre del parametro formal");}
                    |CVR error tipo_id                                                                                      {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta tipo del parametro formal");}
                    |error tipo_id                                                                                          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta tipo del parametro formal");}
                    ;

asignaciones        :tipo ID ASIGN expresiones                                                                              {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion"); agregarAPolaca(":=");}
                    |tipo_id ASIGN expresiones                                                                              {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion"); agregarAPolaca(":=");}
                    |lista_id IGUAL lista_cte                                                                               {verificar_cantidades($1, $3); System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple"); agregarAPolaca("=");}
                    |tipo_id IGUAL lista_cte                                                                                {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple"); agregarAPolaca("=");}
                    ;

lista_cte           :tipo_cte                                                                                               {ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add($1); $$ = new ParserVal(arreglo);}
                    |lista_cte COMA tipo_cte                                                                                {ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) $1.obj; arreglo.add($3); $$ = new ParserVal(arreglo);}
                    |lista_cte tipo_cte                                                                                     {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
                    ;

tipo_id             :ID                                                                                                     {$$ = new ParserVal($1.sval); agregarAPolaca($1.sval);}
                    |ID PUNTO ID                                                                                            {String name = $1.sval + "." + $3.sval; $$ = new ParserVal(name); agregarAPolaca(name);}
                    ;

tipo_cte            :CTE                                                                                                    {aLex.agregarCteNegativaTablaDeSimbolos($1.sval); agregarAPolaca($1.sval); }
                    |MENOS CTE                                                                                              {String cte = "-" + $2.sval; aLex.agregarCteNegativaTablaDeSimbolos(cte); agregarAPolaca(cte);}
                    ;

expresion_lambda    :PARENTESISA tipo ID PARENTESISC LLAVEA sentencias LLAVEC PARENTESISA tipo_id PARENTESISC
                    |PARENTESISA tipo ID PARENTESISC LLAVEA sentencias LLAVEC PARENTESISA tipo_cte PARENTESISC
                    |PARENTESISA tipo ID PARENTESISC sentencias LLAVEC PARENTESISA tipo_cte PARENTESISC                     {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura en funcion lambda");}
                    |PARENTESISA tipo ID PARENTESISC LLAVEA sentencias PARENTESISA tipo_cte PARENTESISC                     {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de cierre en funcion lambda");}
                    |PARENTESISA tipo ID PARENTESISC sentencias PARENTESISA tipo_cte PARENTESISC                            {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores en funcion lambda");}
                    ;
%%

/* CODIGO AUXILIAR */

AnalisisLexico aLex;
private HashMap<String, ArrayList<String>> tablaDeSimbolos = new HashMap<String, ArrayList<String>>();

public void setAlex(AnalisisLexico a){
    this.aLex = a;

}

public void verificar_cantidades(ParserVal lista1, ParserVal lista2){
    ArrayList<String> l1 = (ArrayList<String>)lista1.obj;
    ArrayList<String> l2 = (ArrayList<String>)lista2.obj;
    if (l1 != null && l2 != null){
    if (l1.size() > l2.size() )
        agregarError("Linea: "+aLex.getNroLinea()+" ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
    }
}

void yyerror (String s){
    //System.out.printf(s);
}

int yylex (){
    try {
        int token = aLex.yylex();
        yylval = aLex.getYylval();

        //Actualizamos la ts del analizador sintactico
        if(token == 265 || token == 266 || token == 267){
            copiarTS(yylval.sval, token);
        }

        return token;
    } catch (IOException e) {
        System.err.println("Error de lectura en el analizador léxico: " + e.getMessage());
        return 0; //Devuelvo 0 como si fuera fin de archivo
    }
}
//------------------------------------------------------------------------------------------------------------ERRORES SINTACTICOS
ArrayList<String> errores = new ArrayList<>();

void agregarError(String s){
    errores.add(s);
}

public void imprimirErrores(){
    System.out.println("");
    System.out.println("Errores sintacticos: ");
    if (!errores.isEmpty()){
        for (String error: errores){
            System.out.println(error);
        }
    }else{
        System.out.println("No se encontraron errores sintacticos");
    }
}

//------------------------------------------------------------------------------------------------------------MODIFICAR TS
public void modificarTipoTS(ArrayList<String> claves, String tipo){
    for (String name: claves){
        String clave = ambito+":"+name;
        if (tablaDeSimbolos.containsKey(clave)) {
            ArrayList<String> fila = tablaDeSimbolos.get(clave);
            fila.add(1, tipo);
        }else{
            System.out.println("(modificarTipoTS) Error, la clave" + clave + " no existe en la tabla de simbolos");
        }
    }
}

public void modificarUsos(ArrayList<String> lista, String uso){
        for (String a: lista){
            modificarUsoTS(a, uso);
        }
}

public void modificarUsoTS(String aux, String uso){
    String clave = ambito+":"+aux;
    if (tablaDeSimbolos.containsKey(clave)) {
        ArrayList<String> fila = tablaDeSimbolos.get(clave);
        //Se agrega el uso en el indice = 2
        fila.add(uso);
    }else{
        System.out.println("(modificarUsoTS) Error, la clave" + clave + " no existe en la tabla de simbolos");
    }
}
public void modificarAmbitosTS(ArrayList<String> a){
    int index = ambito.lastIndexOf(":");
    String ambitoAfuera = ambito.substring(0, index);

    for (String parametro : a){
        //Lo busco en la ts y modifico su clave para que incluya el ambito de la funcion definida
        ArrayList<String> aux = tablaDeSimbolos.get(ambitoAfuera+":"+parametro);
        tablaDeSimbolos.remove(ambitoAfuera+":"+parametro);
        tablaDeSimbolos.put(ambito+":"+parametro, aux);
    }
}
public void copiarTS(String clave, int token){
    String aux = ambito+":"+yylval.sval;
    if(!tablaDeSimbolos.containsKey(aux)){
        String tipo = aLex.getTipoTS(clave);
        ArrayList<String> a = new ArrayList<String>();
        a.add(tipo);
        //Se agrega el tipo en el indice = 1
        if(token == 265){
            String tipoCte = aLex.getTipoCteTS(yylval.sval);
            a.add(tipoCte);
        }else{
            a.add(" ");
        }
        tablaDeSimbolos.put(aux, a);
        System.out.println("Agregue a la ts: "+aux);
        System.out.println("con el tipo: "+tipo);
   }
}

public void limpiarPolaca(String ambitoFuncion) {
    // Esta función se llama luego de salir de un ámbito. Limpia la polaca inversa del ámbito de afuera (borramos parámetros formales)
    // ambito es el externo, ambitoFuncion es el ámbito de la función que fue definida

    ArrayList<String> a;
    if (polacaInversa.containsKey(ambito)) {
        a = polacaInversa.get(ambito);
    } else {
        a = mainArreglo;
    }

    // Usamos Iterator para poder eliminar mientras iteramos
    Iterator<String> it = a.iterator();
    while (it.hasNext()) {
        String s = it.next();
        String clave = ambitoFuncion + ":" + s;
        ArrayList<String> ts = tablaDeSimbolos.get(clave);
        if (ts != null && ts.size() == 3 && "Nombre de parametro".equals(ts.get(2))) {
            it.remove(); // elimino de la polacaInversa el nombre del parametro de la funcion que declare
        }
    }

    // No hace falta volver a asignar porque 'a' es referencia al mismo objeto
}

//-------------------------------------------------------------------------------------------------------------CODIGO INTERMEDIO
private ArrayList<String> mainArreglo = new ArrayList<String>();
private HashMap<String, ArrayList<String>> polacaInversa = new HashMap<String, ArrayList<String>>();
private String ambito = "MAIN";

public ArrayList<String> crearArregloId(String v1, String v2){
        ArrayList<String> listaNombres = new ArrayList<String>();
        listaNombres.add(v1);
        listaNombres.add(v2);
        return listaNombres;
}

public void borrarAmbito(){
    int index = ambito.lastIndexOf(":");
    if (index != -1) {
         String texto = ambito;
        texto = texto.substring(0, index);
        ambito = texto;
    }
}

public void agregarAPolaca(String valor){
    if (polacaInversa.containsKey(ambito)) {
        ArrayList<String> a = tablaDeSimbolos.get(valor);
        if (a!= null && a.size() == 3){
            String uso = a.get(2);
            if(uso != "Nombre de parametro")
                polacaInversa.get(ambito).add(valor);
            //Los parametros formales los agregamos a la polaca inversa manualmente en las reglas
        }else
            polacaInversa.get(ambito).add(valor);
    }
    else{
        mainArreglo.add(valor);
    }
}
//----------------------------------------------------------------------------------------------------------IMPRESIONES
public void imprimirPolaca() {
    System.out.println();
    System.out.println("Tabla Polaca :");
    System.out.printf("%-10s | %s%n", "Clave", "Polaca Inversa");
    System.out.println("--------------------------");

    for (Map.Entry<String, ArrayList<String>> entry : polacaInversa.entrySet()) {
        String clave = entry.getKey();
        String valores = String.join(", ", entry.getValue());
        System.out.printf("%-10s | %s%n", clave, valores);
    }
    System.out.println();
}

 public void imprimirTabla() {
        System.out.println();
        System.out.println("Tabla de simbolos:");
        System.out.printf("%-10s | %-10s | %-10s | %-10s%n", "ambito", "Tipo Token", "Tipo", "Uso");
        System.out.println("--------------------------");

        for (Map.Entry<String, ArrayList<String>> entry : tablaDeSimbolos.entrySet()) {
            String clave = entry.getKey();
            String valores = String.join(" | ", entry.getValue());
            System.out.printf("%-10s | %s%n", clave, valores);
        }
        System.out.println();
    }