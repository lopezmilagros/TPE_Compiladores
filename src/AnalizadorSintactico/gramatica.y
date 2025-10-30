%{
package AnalizadorSintactico;
import java.io.*;
import java.util.ArrayList;
import AnalizadorLexico.*;
import java.util.HashMap;
%}



%token IF ELSE ENDIF PRINT RETURN ULONG WHILE DO CTE CADENA ID CVR ASIGN MAS MENOS AST BARRA IGUAL MAYIG MENIG MAYOR MENOR IGIG DIF PARENTESISA PARENTESISC LLAVEA LLAVEC GUIONB PUNTOCOMA FLECHA PUNTO COMA
%left MAS MENOS
%left AST BARRA

%%
prog                :ID LLAVEA sentencias LLAVEC                                                                            {aLex.modificarUsoTS($1.sval, "Nombre de programa"); ambito = $1.sval;}
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
                    |expresiones operador error                                                                             {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta operando en expresion");}
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

declaracion         :tipo tipo_id                                                                                           {ArrayList<String> b = new ArrayList<String>(); b.add($2.sval); aLex.modificarTipoTS(b, $1.sval); aLex.modificarUsoTS($2.sval, "Nombre de variable"); aLex.modificarAmbitoTS($2.sval, ambito);}
                    |tipo lista_id                                                                                          {ArrayList<String> a = (ArrayList<String>)$2.obj; aLex.modificarTipoTS(a, $1.sval); modificarUsos(a, "Nombre de variable"); modificarAmbitos(a);}
                    |header_funcion LLAVEA sentencias LLAVEC                                                                {borrarAmbito();}
                    |tipo error PARENTESISA parametros_formales PARENTESISC LLAVEA sentencias LLAVEC                        {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
                    ;

header_funcion      :tipo ID PARENTESISA parametros_formales PARENTESISC                                                    {aLex.modificarUsoTS($2.sval, "Nombre de funcion"); aLex.modificarAmbitoTS($2.sval, ambito); ambito = ambito + ":" + $2.sval; }
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

parametros_formales :parametros_formales COMA parametro
                    |parametro
                    |parametros_formales parametro                                                                          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta ',' en declaracion de las variables");}
                    ;

parametro           :CVR tipo tipo_id                                                                                       {aLex.modificarUsoTS($3.sval, "Nombre de parametro");}
                    |tipo tipo_id                                                                                           {aLex.modificarUsoTS($2.sval, "Nombre de parametro");}
                    |tipo error                                                                                             {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta nombre del parametro formal");}
                    |CVR tipo error                                                                                         {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta nombre del parametro formal");}
                    |CVR error tipo_id                                                                                      {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta tipo del parametro formal");}
                    |error tipo_id                                                                                          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta tipo del parametro formal");}
                    ;

asignaciones        :tipo ID ASIGN expresiones                                                                              {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion");}
                    |tipo_id ASIGN expresiones                                                                              {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion");}
                    |lista_id IGUAL lista_cte                                                                               {verificar_cantidades($1, $3); System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple");}
                    |tipo_id IGUAL lista_cte                                                                                {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple");}
                    ;

lista_cte           :tipo_cte                                                                                               {ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add($1); $$ = new ParserVal(arreglo);}
                    |lista_cte COMA tipo_cte                                                                                {ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) $1.obj; arreglo.add($3); $$ = new ParserVal(arreglo);}
                    |lista_cte tipo_cte                                                                                     {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
                    ;

tipo_id             :ID                                                                                                     {$$ = new ParserVal($1.sval);}
                    |ID PUNTO ID                                                                                            {String name = $1.sval + "." + $3.sval; $$ = new ParserVal(name);}
                    ;

tipo_cte            :CTE                                                                                                    {aLex.agregarCteNegativaTablaDeSimbolos($1.sval); }
                    |MENOS CTE                                                                                              {String cte = "-" + $2.sval; aLex.agregarCteNegativaTablaDeSimbolos(cte); }
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
        return token;
    } catch (IOException e) {
        System.err.println("Error de lectura en el analizador léxico: " + e.getMessage());
        return 0; //Devuelvo 0 como si fuera fin de archivo
    }

}

//ERRORES SINTACTICOS
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

//CODIGO INTERMEDIO
//private HashMap<String, ArrayList<String>> polacaInversa = new HashMap<String, ArrayList<String>>();
private String ambito;

public ArrayList<String> crearArregloId(String v1, String v2){
        ArrayList<String> listaNombres = new ArrayList<String>();
        listaNombres.add(v1);
        listaNombres.add(v2);
        return listaNombres;
}

public void modificarUsos(ArrayList<String> lista, String uso){
        for (String a: lista){
            aLex.modificarUsoTS(a, uso);
        }
}

public void modificarAmbitos(ArrayList<String> lista){
        for (String a: lista){
            aLex.modificarAmbitoTS(a, ambito);
        }
}

    public void borrarAmbito(){
        int index = ambito.lastIndexOf(":");
        if (index != -1) {
             String texto = ambito;
            texto = texto.substring(0, index);
            ambito = texto;
        }
            }
