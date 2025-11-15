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
prog
    : ID bloque     {agregarSentencia("LINEA: "+aLex.getNroLinea()+" SENTENCIA: Nombre de programa");  modificarUsoTS($1.sval, "Nombre de programa"); polacaInversa.put("MAIN", mainArreglo); }
    | bloque        {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de programa");}
    ;

bloque
    : LLAVEA sentencias LLAVEC
    | LLAVEA sentencias error            {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador final");}
    | error sentencias LLAVEC             {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador inicial");}
    | error sentencias error              {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores");}
    ;

sentencias
    : sentencias sentencia
    | sentencia
    ;

sentencia
    : sentencia_declarativa
    | sentencia_ejecutable
    ;

sentencia_ejecutable
    : asignacion
    | sentencia_return                                {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Return");}
    | sentencia_print                                 {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Print");}
    | sentencia_if                                    {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: If"); agregarAPolaca("if");}
    | sentencia_while                                 {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: While");}
    | expresion_lambda                                {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Lambda");}
    ;

sentencia_return
    : RETURN PARENTESISA lista_id PARENTESISC PUNTOCOMA        {ArrayList<String> a = (ArrayList<String>)$3.obj; agregarListaAPolaca(a); agregarAPolaca("return");}
    | RETURN PARENTESISA expresiones PARENTESISC PUNTOCOMA     {agregarAPolaca("return");}
    | RETURN PARENTESISA lista_id PARENTESISC                  {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    | RETURN PARENTESISA expresiones PARENTESISC               {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    ;

sentencia_print
    : PRINT PARENTESISA CADENA PARENTESISC PUNTOCOMA            {agregarAPolaca($3.sval); agregarAPolaca("print");}
    | PRINT PARENTESISA expresiones PARENTESISC PUNTOCOMA       {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Print"); agregarAPolaca("print");}
    | sentencia_print_error
    ;

sentencia_print_error
    : PRINT PARENTESISA CADENA PARENTESISC                  {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    | PRINT PARENTESISA expresiones PARENTESISC             {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    | PRINT PARENTESISA  PARENTESISC PUNTOCOMA              {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTÁCTICO: Falta argumento en print");}
    | PRINT PARENTESISA  PARENTESISC                        {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTÁCTICO: Falta argumento en print y falta ';' ");}
    ;

sentencia_while
    : header_while DO bloque PUNTOCOMA                      {agregarAPolaca("cuerpo"); bifurcacionWhile(); agregarBifurcacion("cond");}
    | sentencia_while_error
    ;

header_while
    : inicio_header_while PARENTESISA condicion PARENTESISC
    ;

inicio_header_while
    :WHILE                                                 { guardarInicioWhile(); }
    ;

sentencia_while_error
    : inicio_header_while condicion PARENTESISC DO bloque PUNTOCOMA                                   {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
    | inicio_header_while PARENTESISA condicion DO bloque PUNTOCOMA                                   {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
    | inicio_header_while condicion DO bloque PUNTOCOMA                                               {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
    | header_while bloque PUNTOCOMA                                                     {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'do' en iteracion");}
    | header_while DO PUNTOCOMA                                                         {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTÁCTICO: Falta cuerpo de la iteracion");}
;

sentencia_if
    : header_if ELSE bloque ENDIF PUNTOCOMA                 {agregarAPolaca("else"); agregarBifurcacion("then"); agregarAPolaca("cuerpo");}
    | header_if ENDIF PUNTOCOMA                             {agregarAPolaca("cuerpo"); acomodarBifurcacion();}
    | sentencia_if_error
    ;

header_if
    : IF PARENTESISA condicion PARENTESISC bloque        {agregarAPolaca("then"); agregarBifurcacion("cond");}
    ;

sentencia_if_error
    :IF  condicion PARENTESISC bloque ENDIF PUNTOCOMA                                   {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
    |IF PARENTESISA condicion bloque ENDIF PUNTOCOMA                                    {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
    |IF condicion bloque ENDIF PUNTOCOMA                                                {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
    |IF condicion PARENTESISC bloque ELSE bloque ENDIF PUNTOCOMA                        {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
    |IF PARENTESISA condicion  bloque ELSE bloque ENDIF PUNTOCOMA                       {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
    |IF condicion bloque ELSE bloque ENDIF PUNTOCOMA                                    {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
    |header_if PUNTOCOMA                                                                {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
    |header_if ELSE bloque PUNTOCOMA                                                    {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
    |IF PARENTESISA condicion PARENTESISC ENDIF PUNTOCOMA                               {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
    |IF PARENTESISA condicion PARENTESISC ELSE bloque ENDIF PUNTOCOMA                   {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
    |header_if ELSE ENDIF PUNTOCOMA                                                     {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else'");}
    |IF PARENTESISA condicion PARENTESISC ELSE ENDIF PUNTOCOMA                           {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else', 'then'");}
    |header_if ELSE bloque ENDIF                                                        {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    |header_if ENDIF                                                                    {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    |IF  condicion PARENTESISC bloque ENDIF                                             {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion y falta ';' ");}
    |IF PARENTESISA condicion bloque ENDIF                                              {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion y falta ';' ");}
    |IF condicion bloque ENDIF                                                          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion y falta ';' ");}
    |IF condicion PARENTESISC bloque ELSE bloque ENDIF                                  {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion y falta ';' ");}
    |IF PARENTESISA condicion  bloque ELSE bloque ENDIF                                 {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion y falta ';' ");}
    |IF condicion bloque ELSE bloque ENDIF                                              {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion y falta ';' ");}
    |header_if                                                                          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif' y falta ';' ");}
    |header_if ELSE bloque                                                              {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif' y falta ';' ");}
    |IF PARENTESISA condicion PARENTESISC ENDIF                                         {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then' y falta ';' ");}
    |IF PARENTESISA condicion PARENTESISC ELSE bloque ENDIF                             {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then' y falta ';' ");}
    |header_if ELSE ENDIF                                                               {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else' y falta ';' ");}
    |IF PARENTESISA condicion PARENTESISC ELSE ENDIF                                    {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else', 'then' y falta ';' ");}
    ;

condicion
    : expresiones MAYOR expresiones                 {agregarAPolaca(">"); agregarAPolaca("cond");}
    | expresiones MAYIG expresiones                 {agregarAPolaca(">="); agregarAPolaca("cond");}
    | expresiones MENOR expresiones                 {agregarAPolaca("<"); agregarAPolaca("cond");}
    | expresiones MENIG expresiones                 {agregarAPolaca(">="); agregarAPolaca("cond");}
    | expresiones IGIG expresiones                  {agregarAPolaca("=="); agregarAPolaca("cond");}
    | expresiones DIF expresiones                   {agregarAPolaca("=!"); agregarAPolaca("cond");}
    | condicion_error                               {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Condicion incompleta");}
    ;

condicion_error
    : expresiones MAYOR
    | expresiones MAYIG
    | expresiones MENOR
    | expresiones MENIG
    | expresiones IGIG
    | expresiones DIF
    | MAYOR expresiones
    | MAYIG expresiones
    | MENOR expresiones
    | MENIG expresiones
    | IGIG expresiones
    | DIF expresiones
    | expresiones
    ;

asignacion
    : tipo ID ASIGN expresiones PUNTOCOMA               {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion y asignacion"); ArrayList<String> a = new ArrayList<String>(); a.add($2.sval); modificarTipoTS(a, $1.sval); modificarUsos(a, "Nombre de variable"); agregarAPolaca($2.sval); agregarAPolaca(":=");}
    | identificador ASIGN expresiones PUNTOCOMA         {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion"); if(!variablePermitida($1.sval)){System.out.println($1.sval+" NO PERMITIDA");} agregarAPolaca($1.sval); agregarAPolaca(":=");}
    | lista_id IGUAL lista_cte PUNTOCOMA                {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion multiple"); ArrayList<String> l1 = (ArrayList<String>)$1.obj; ArrayList<String> l3 = (ArrayList<String>)$3.obj; verificar_cantidades(l1, l3); agregarListaAPolaca(l3); agregarListaAPolaca(l1); agregarAPolaca("=");}
    | identificador IGUAL lista_cte PUNTOCOMA           {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion multiple"); agregarListaAPolaca((ArrayList<String>)$3.obj); agregarAPolaca($1.sval); agregarAPolaca("=");}
    | asignacion_error                                  {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    ;

asignacion_error
    :tipo ID ASIGN expresiones
    |identificador ASIGN expresiones
    |lista_id IGUAL lista_cte
    |identificador IGUAL lista_cte
    ;

expresiones
    : expresiones MAS termino       {agregarAPolaca("+");}
    | expresiones MENOS termino     {agregarAPolaca("-");}
    | expresiones AST termino       {agregarAPolaca("*");}
    | expresiones BARRA termino     {agregarAPolaca("/");}
    | termino
    | expresiones operador error         {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta operando en expresion");}
    ;

termino
    : identificador         {agregarAPolaca($1.sval);}
    | tipo_cte              {agregarAPolaca($1.sval);}
    | llamado_funcion
    ;

llamado_funcion
    :ID PARENTESISA parametros_reales PARENTESISC       {agregarAPolaca($1.sval); agregarAPolaca("call");}
    ;

operador
    :MAS            {agregarAPolaca("+");}
    |MENOS          {agregarAPolaca("-");}
    |AST            {agregarAPolaca("*");}
    |BARRA          {agregarAPolaca("/");}
    ;

sentencia_declarativa
    : tipo ID PUNTOCOMA                     {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de variable"); ArrayList<String> b = new ArrayList<String>(); b.add($2.sval); modificarTipoTS(b, $1.sval); modificarUsoTS($2.sval, "Nombre de variable");}
    | tipo lista_id PUNTOCOMA               {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de variables"); ArrayList<String> a = (ArrayList<String>)$2.obj; modificarTipoTS(a, $1.sval); modificarUsos(a, "Nombre de variable");}
    | funcion                               {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de funcion");}
    | tipo ID error                         {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    | tipo lista_id error                   {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    ;

funcion
    :  header_funcion bloque                {String ambitoConFuncion = ambito; borrarAmbito(); limpiarPolaca(ambitoConFuncion);}
    ;

header_funcion
    : tipo ID PARENTESISA parametros_formales PARENTESISC       {modificarUsoTS($2.sval, "Nombre de funcion"); ambito = ambito + ":" + $2.sval; modificarAmbitosTS((ArrayList<String>)$4.obj); polacaInversa.put(ambito, (ArrayList<String>) $4.obj); }
    | tipo PARENTESISA parametros_formales PARENTESISC          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
    ;

parametros_formales
    :parametros_formales COMA parametro_formal   {ArrayList<String> a = (ArrayList<String>)$1.obj; a.add($3.sval); $$ = new ParserVal(a);}
    |parametro_formal                            {ArrayList<String> a = new ArrayList<String>(); a.add($1.sval); $$ = new ParserVal(a);}
    |parametros_formales parametro_formal               {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta ',' en declaracion de las variables");}
    ;

parametro_formal
    :CVR tipo ID                      {modificarUsoTS($3.sval, "Nombre de parametro"); $$ = new ParserVal($3.sval);}
    |tipo ID                          {modificarUsoTS($2.sval, "Nombre de parametro"); $$ = new ParserVal($2.sval);}
    |parametro_formal_error                      {$$ = new ParserVal();}
    ;

parametro_formal_error
    :tipo error                              {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta nombre del parametro formal");}
    |CVR tipo                                {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta nombre del parametro formal");}
    |CVR ID                                 {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta tipo del parametro formal");}
    |error ID                               {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta tipo del parametro formal");}
    ;

parametros_reales
    : parametros_reales COMA expresiones FLECHA identificador       {agregarAPolaca($5.sval); agregarAPolaca("->");}
    | expresiones FLECHA identificador                              {agregarAPolaca($3.sval); agregarAPolaca("->");}
    | expresiones FLECHA                                            {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta especificacion del parametro formal");}
    ;

expresion_lambda
    : header_lambda bloque llamado_lambda PUNTOCOMA                 {borrarAmbito(); agregarAPolaca($3.sval); agregarAPolaca($1.sval); agregarAPolaca("->"); agregarAPolaca("LAMBDA"); agregarAPolaca("call");}
    ;

header_lambda
    : FLECHA PARENTESISA tipo ID PARENTESISC                        {ambito = ambito + ":LAMBDA"; ArrayList<String> a = new ArrayList<String>(); a.add($4.sval); modificarAmbitosTS(a); polacaInversa.put(ambito, a); $$ = new ParserVal($4.sval);}
    ;

llamado_lambda
    : PARENTESISA identificador PARENTESISC                         {$$ = new ParserVal($2.sval);}
    | PARENTESISA tipo_cte PARENTESISC                              {$$ = new ParserVal($2.sval);}
    ;

lista_id
    : lista_id COMA identificador           {ArrayList<String> arreglo = (ArrayList<String>) $1.obj; arreglo.add($3.sval); $$ = new ParserVal(arreglo);}
    | identificador COMA identificador      {ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add($1.sval); arreglo.add($3.sval); $$ = new ParserVal(arreglo); }
    | lista_id_error                         {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
    ;

lista_id_error
    : lista_id  identificador
    | identificador  identificador
    ;

lista_cte
    : tipo_cte                          {ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add($1.sval); $$ = new ParserVal(arreglo);}
    | lista_cte COMA tipo_cte           {ArrayList<String> arreglo = (ArrayList<String>) $1.obj; arreglo.add($3.sval); $$ = new ParserVal(arreglo);}
    | lista_cte tipo_cte                {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
    ;

identificador
    :ID                 {$$ = new ParserVal($1.sval); }
    |ID PUNTO ID        {String name = $1.sval + "." + $3.sval; $$ = new ParserVal(name);}
    ;

tipo
    :ULONG              {$$ = new ParserVal("ULONG");}
    ;

tipo_cte
    : CTE               {agregarCteTS($1.sval); $$ = new ParserVal($1.sval);}
    | MENOS CTE         {String cte = "-" + $2.sval; agregarCteTS(cte); if(!cte.contains(".") & !cte.contains("D")){ cte = cte.substring(1); System.out.println("VARIABLE:"+cte);} $$ = new ParserVal(cte);}
    ;

%%

/* -------------------------------------------------------------------------------------------------------------CODIGO AUXILIAR ----------------------------------------------------*/

AnalisisLexico aLex;
private HashMap<String, ArrayList<String>> tablaDeSimbolos = new HashMap<String, ArrayList<String>>();

public void setAlex(AnalisisLexico a){
    this.aLex = a;

}

public void verificar_cantidades(ArrayList<String> l1, ArrayList<String> l2){
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
ArrayList<String> sentencias = new ArrayList<String>();
public void agregarSentencia(String s){
    sentencias.add(s);
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
        if (name != null){
            String clave = ambito+":"+name;
            if (tablaDeSimbolos.containsKey(clave)) {
                ArrayList<String> fila = tablaDeSimbolos.get(clave);
                fila.set(1, tipo);
            }else{
                System.out.println("(modificarTipoTS) Error, la clave" + clave + " no existe en la tabla de simbolos");
            }
        }
    }
}

public void modificarUsos(ArrayList<String> lista, String uso){
        for (String a: lista){
            modificarUsoTS(a, uso);
        }
}

public void modificarUsoTS(String aux, String uso){
    if (aux != null){
        String clave = ambito+":"+aux;
        if (tablaDeSimbolos.containsKey(clave)) {
            ArrayList<String> fila = tablaDeSimbolos.get(clave);
            //Se agrega el uso en el indice = 2
            fila.add(uso);
        }else{
            System.out.println("(modificarUsoTS) Error, la clave" + clave + " no existe en la tabla de simbolos");
        }
    }
}
public void modificarAmbitosTS(ArrayList<String> a){
    int index = ambito.lastIndexOf(":");
    String ambitoAfuera = ambito.substring(0, index);

    for (String parametro : a){
        if (parametro != null){
            //Lo busco en la ts y modifico su clave para que incluya el ambito de la funcion definida
            ArrayList<String> aux = tablaDeSimbolos.get(ambitoAfuera+":"+parametro);
            tablaDeSimbolos.remove(ambitoAfuera+":"+parametro);
            tablaDeSimbolos.put(ambito+":"+parametro, aux);
        }
    }
}

public void copiarTS(String clave, int token){
    //Si es una constante, no le concateno el ambito
    String aux = clave;
    if(!(token == 265)){
        aux = ambito+":"+clave;
    }
    if(!tablaDeSimbolos.containsKey(aux)){
        String tipo = aLex.getTipoTS(clave);
        ArrayList<String> a = new ArrayList<String>();
        a.add(tipo);
        //Se agrega el tipo en el indice = 1
        if(token == 265){
            String tipoCte = aLex.getTipoCteTS(clave);
            a.add(tipoCte);
        }else{
            a.add(" ");
        }
        tablaDeSimbolos.put(aux, a);
   }
}

public void agregarCteTS(String lexema){
    //Actualizacion de la TS para constantes (por si hubo una negativa)
    if (!tablaDeSimbolos.containsKey(lexema)) {
        //Si el lexema no esta en la tabla de simbolos lo agrega
        ArrayList<String> a = new ArrayList<>();
        //Sacar solo el valor numerico si es UL
        if(lexema.contains(".") & lexema.contains("D")) {
            a.add(0, "CTE");
            a.add(1,"DFLOAT");
            tablaDeSimbolos.put(lexema, a);
        }
        else{
            if(lexema.contains("-")){
                agregarError("LINEA "+aLex.getNroLinea()+" WARNING SINTACTICO: No se permiten ULONG negativos. La constante fue truncada a positivo.");
            }
        }
    }
}

//-------------------------------------------------------------------------------------------------------------CODIGO INTERMEDIO
private ArrayList<String> mainArreglo = new ArrayList<String>();
private HashMap<String, ArrayList<String>> polacaInversa = new HashMap<String, ArrayList<String>>();
private String ambito = "MAIN";
private Integer indiceWhile;

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

public void agregarListaAPolaca(ArrayList<String> a){
    for (String s: a){
        agregarAPolaca(s);
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

public void agregarBifurcacion(String flag){
    ArrayList<String> a = new ArrayList<String>();
    if (polacaInversa.containsKey(ambito)) {
        a = polacaInversa.get(ambito);
    }else{
        a = mainArreglo;
    }
    int i = a.size() - 1;
    while ( i >= 0 && !flag.equals(a.get(i)) ){
        i--;
    }
    Integer saltoBF = a.size() - 1 + 3;
    Integer saltoBI = a.size() - 1 + 1;

    if (flag.equals("cond")) {
       a.set(i, (saltoBF).toString());  //porque agregamos dos bifurcaciones
       a.add(i + 1, "BF");
    }else{
        a.set(i, (saltoBI).toString());  //porque agregamos una bifurcaciones
        a.add(i + 1, "BI");
    }
}

public void acomodarBifurcacion(){
    ArrayList<String> a = new ArrayList<String>();
    if (polacaInversa.containsKey(ambito)) {
        a = polacaInversa.get(ambito);
    }else{
        a = mainArreglo;
    }
    int i = a.size() - 1;
    while ( i >= 0 && !a.get(i).equals("BF")  ){
        i--;
    }
    Integer num = Integer.parseInt(a.get(i - 1)) - 2;

    a.set(i-1,num.toString());
}

public void guardarInicioWhile(){

  ArrayList<String> a = new ArrayList<String>();
  if (polacaInversa.containsKey(ambito)) {
      a = polacaInversa.get(ambito);
  }else{
      a = mainArreglo;
  }
  indiceWhile = a.size() -1 +1; //queremos la proxima celda que se va a escribir
}

public void bifurcacionWhile(){
    ArrayList<String> a = new ArrayList<String>();
    if (polacaInversa.containsKey(ambito)) {
        a = polacaInversa.get(ambito);
    }else{
        a = mainArreglo;
    }
    int i = a.size() - 1;
    a.set(i, indiceWhile.toString());
    a.add("BI");
}

//--------------CHEQUEOS SEMANTICOS----------------------

public boolean estaInicializada(String id){

    if (!tablaDeSimbolos.containsKey(id))
        return false;
    else {
      ArrayList<String> a = tablaDeSimbolos.get(id);
      if (a != null && a.size() == 3) {
        String uso = a.get(2);
        if (uso == "Nombre de variable")
          //inicializada
          return true;
      }
    }
    return false;
}

public boolean variablePermitida(String id) {
    //Chequeo si la variable esta al alcance y si esta inicializada
    if (!estaInicializada(id))
        return false;

    //Concateno el ambito con : para evitar falsas coincidencias
    String ambitoActual = ambito+":";

    while (true) {
        String clave = ambitoActual + id;

        // esta al alcance
        if (tablaDeSimbolos.containsKey(clave))
            return true;

        // Si ya estamos en el global, cortar
        if (ambitoActual.equals("MAIN:"))
            break;

        // Quitar el último nivel del ámbito
        int idx = ambitoActual.lastIndexOf(":", ambitoActual.length() - 2);
        if (idx == -1) {
            ambitoActual = "MAIN:";
        } else {
            ambitoActual = ambitoActual.substring(0, idx + 1);
        }
    }

   return false; // no encontrado
}

//----------------------------------------------------------------------------------------------------------IMPRESIONES

public void imprimirSentencias(){
    if (!sentencias.isEmpty()) {
        for (String s : sentencias) {
            System.out.println(s);
        }
    }else{
        System.out.println("El programa esta vacio");
    }
}


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