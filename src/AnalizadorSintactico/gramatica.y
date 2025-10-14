%{
package AnalizadorSintactico;
import java.io.*;
import java.util.ArrayList;
import AnalizadorLexico.*;
%}



%token IF ELSE ENDIF PRINT RETURN ULONG WHILE DO CTE CADENA ID CVR ASIGN MAS MENOS AST BARRA IGUAL MAYIG MENIG MAYOR MENOR IGIG DIF PARENTESISA PARENTESISC LLAVEA LLAVEC GUIONB PUNTOCOMA FLECHA PUNTO COMA
%left MAS MENOS
%left AST BARRA

%%
prog                :ID LLAVEA sentencias LLAVEC
                    |LLAVEA sentencias LLAVEC
                    |ID sentencias LLAVEC                                                   {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura");}
                    |ID LLAVEA sentencias                                                   {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador final");}
                    |ID sentencias                                                          {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores");}
                    |error                                                                  {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO");}
                    ;

sentencias          :sentencias sentencia PUNTOCOMA
                    |sentencia PUNTOCOMA
                    |sentencia error                                                             {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
                    ;

sentencia           :asignaciones
                    |IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ENDIF                                    {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: if");}
                    |IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF      {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:if else");}
                    |WHILE PARENTESISA condicion PARENTESISC DO LLAVEA sentencias LLAVEC                                    {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:while");}
                    |RETURN PARENTESISA lista_id PARENTESISC                                                                {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
                    |RETURN PARENTESISA expresiones PARENTESISC                                                             {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
                    |declaracion                                                                                            {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:declaracion");}
                    |llamado_funcion                                                                                        {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:llamado de funcion");}
                    |expresion_lambda                                                                                       {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:lambda");}
                    |PRINT PARENTESISA CADENA PARENTESISC                                                                   {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
                    |PRINT PARENTESISA expresiones PARENTESISC                                                                {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
                    |PRINT PARENTESISA error PARENTESISC                                                                    {System.out.println("LINEA: " + aLex.getNroLinea() + " ERROR SINTÁCTICO: argumento inválido en print");}
                    |IF error condicion PARENTESISC LLAVEA sentencias LLAVEC ENDIF                                          {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
                    |IF PARENTESISA condicion LLAVEA sentencias LLAVEC ENDIF                                                {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
                    |IF error condicion error LLAVEA sentencias LLAVEC ENDIF                                                {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
                    |IF error condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF            {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
                    |IF PARENTESISA condicion  LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF                 {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
                    |IF error condicion error LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF                  {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
                    |WHILE error condicion PARENTESISC DO LLAVEA sentencias LLAVEC                                          {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
                    |WHILE PARENTESISA condicion  DO LLAVEA sentencias LLAVEC                                               {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
                    |WHILE error condicion error DO LLAVEA sentencias LLAVEC                                                {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
                    |WHILE PARENTESISA condicion PARENTESISC DO error                                                       {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta cuerpo en la iteracion");}
                    |IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC                                          {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
                    |IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC            {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
                    |IF PARENTESISA condicion PARENTESISC ENDIF                                                             {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
                    |IF PARENTESISA condicion PARENTESISC ELSE LLAVEA sentencias LLAVEC ENDIF                               {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
                    |IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE ENDIF                               {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else'");}
                    |WHILE PARENTESISA condicion PARENTESISC error LLAVEA sentencias LLAVEC                                 {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'do' en iteracion");}
                    ;

condicion           :expresiones MAYOR expresiones
                    |expresiones MAYIG expresiones
                    |expresiones MENOR expresiones
                    |expresiones MENIG expresiones
                    |expresiones IGIG expresiones
                    |expresiones DIF expresiones
                    |expresiones_error                                                                                      {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta comparador en expresion");}
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
                    |expresiones operador error                                           {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta operando en expresion");}                                                {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta operando en expresion");}
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

declaracion         :tipo tipo_id
                    |tipo lista_id
                    |tipo ID PARENTESISA parametros_formales PARENTESISC LLAVEA sentencias LLAVEC
                    |tipo error PARENTESISA parametros_formales PARENTESISC LLAVEA sentencias LLAVEC       {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
                    ;

lista_id            :tipo_id COMA tipo_id                                                {ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add($1); arreglo.add($3); $$ = new ParserVal(arreglo); }
                    |lista_id COMA tipo_id                                               {ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) $1.obj; arreglo.add($3); $$ = new ParserVal(arreglo); }
                    |lista_id tipo_id                                                    {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
                    |tipo_id tipo_id                                                     {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
                    ;

tipo                :ULONG
                    ;

llamado_funcion     :ID PARENTESISA parametros_reales PARENTESISC
                    ;

parametros_reales   :parametros_reales COMA expresiones FLECHA tipo_id
                    |expresiones FLECHA tipo_id
                    |expresiones FLECHA error                                            {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta especificacion del parametro formal");}
                    ;

parametros_formales :parametros_formales COMA parametro
                    |parametro
                    |parametros_formales parametro                                      {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta ',' en declaracion de las variables");}
                    ;

parametro           :CVR tipo tipo_id
                    |tipo tipo_id
                    |tipo error                                                         {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta nombre del parametro formal");}
                    |CVR tipo error                                                     {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta nombre del parametro formal");}
                    |CVR error tipo_id                                                  {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta tipo del parametro formal");}
                    |error tipo_id                                                      {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta tipo del parametro formal");}
                    ;

asignaciones        :tipo ID ASIGN expresiones PUNTOCOMA                                         {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion");}
                    |tipo_id ASIGN expresiones PUNTOCOMA                                         {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion");}
                    |lista_id IGUAL lista_cte  PUNTOCOMA                                         {verificar_cantidades($1, $3); System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple");}
                    ;

lista_cte           :tipo_cte                                                                {ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add($1); $$ = new ParserVal(arreglo);}
                    |lista_cte COMA tipo_cte                                                 {ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) $1.obj; arreglo.add($3); $$ = new ParserVal(arreglo);}
                    |lista_cte tipo_cte                                                      {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
                    ;

tipo_id             :ID
                    |ID PUNTO ID
                    ;

tipo_cte            :CTE                                                                     {aLex.agregarATablaDeSimbolos($1.sval);}
                    |MENOS CTE                                                               {String cte = "-" + $2.sval; aLex.agregarATablaDeSimbolos(cte);}
                    ;

expresion_lambda    :PARENTESISA tipo ID PARENTESISC LLAVEA sentencias LLAVEC PARENTESISA tipo_id PARENTESISC
                    |PARENTESISA tipo ID PARENTESISC LLAVEA sentencias LLAVEC PARENTESISA tipo_cte PARENTESISC
                    |PARENTESISA tipo ID PARENTESISC sentencias LLAVEC PARENTESISA tipo_cte PARENTESISC         {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura en funcion lambda");}
                    |PARENTESISA tipo ID PARENTESISC LLAVEA sentencias PARENTESISA tipo_cte PARENTESISC         {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de cierre en funcion lambda");}
                    |PARENTESISA tipo ID PARENTESISC sentencias PARENTESISA tipo_cte PARENTESISC                {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores en funcion lambda");}
                    ;
%%

/* CODIGO AUXILIAR */


AnalisisLexico aLex;

public void setAlex(AnalisisLexico a){
    this.aLex = a;

}

public void verificar_cantidades(ParserVal lista1, ParserVal lista2){
    ArrayList<ParserVal> l1 = (ArrayList<ParserVal>)lista1.obj;
    ArrayList<ParserVal> l2 = (ArrayList<ParserVal>)lista2.obj;
    if (l1.size() < l2.size() )
        System.out.println("Linea: "+aLex.getNroLinea()+" ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
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