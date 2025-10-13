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
prog                :ID bloque
cd ..
                    ;

bloque              :LLAVEA sentencias LLAVEC
                    |error sentencias LLAVEC                                            {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitador");}
                    |LLAVEA sentencias error                                            {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitador");}
                    |error sentencias error                                             {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores");}
                    ;

sentencias          :sentencias sentencia PUNTOCOMA
                    |sentencia PUNTOCOMA
                    |sentencia error                                                    {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
                    ;

sentencia           :asignaciones
                    |IF PARENTESISA condicion PARENTESISC bloque ENDIF                  {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: if");}
                    |IF PARENTESISA condicion PARENTESISC bloque ELSE bloque ENDIF      {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:if else");}
                    |WHILE PARENTESISA condicion PARENTESISC DO bloque                  {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:while");}
                    |RETURN PARENTESISA lista_id PARENTESISC                            {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
                    |RETURN PARENTESISA expresiones PARENTESISC                         {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
                    |declaracion                                                        {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:declaracion");}
                    |expresion_lambda                                                   {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:lambda");}
                    |PRINT PARENTESISA CADENA PARENTESISC                               {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
                    |PRINT PARENTESISA termino PARENTESISC                              {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
                    |PRINT PARENTESISA error PARENTESISC                                {System.out.println("LINEA: " + aLex.getNroLinea() + " ERROR SINTÁCTICO: argumento inválido en print");}
                    |IF error condicion PARENTESISC bloque ENDIF                        {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de apertura de la condicion");}
                    |IF PARENTESISA condicion error bloque ENDIF                        {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de cierre de la condicion");}
                    |IF error condicion error bloque ENDIF                              {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: faltan parentesis de la condicion");}
                    |IF error condicion PARENTESISC bloque ELSE bloque ENDIF            {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de apertura de la condicion");}
                    |IF PARENTESISA condicion error bloque ELSE bloque ENDIF            {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de cierre de la condicion");}
                    |IF error condicion error bloque ELSE bloque ENDIF                  {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: faltan parentesis de la condicion");}
                    |WHILE error condicion PARENTESISC DO bloque                        {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de apertura de la condicion");}
                    |WHILE PARENTESISA condicion error DO bloque                        {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de cierre de la condicion");}
                    |WHILE error condicion error DO bloque                              {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: faltan parentesis de la condicion");}
                    |WHILE PARENTESISA condicion PARENTESISC DO error                   {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta cuerpo en la iteracion");}
                    |IF PARENTESISA condicion PARENTESISC bloque error                  {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta 'endif'");}
                    |IF PARENTESISA condicion PARENTESISC bloque ELSE bloque error      {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta 'endif'");}
                    |IF PARENTESISA condicion PARENTESISC error ENDIF                   {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta contenido en bloque");}
                    |IF PARENTESISA condicion PARENTESISC error ELSE bloque ENDIF       {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta contenido en bloque");}
                    |IF PARENTESISA condicion PARENTESISC bloque ELSE error ENDIF       {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta contenido en bloque");}
                    |WHILE PARENTESISA condicion PARENTESISC error bloque               {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta 'do' en iteracion");}
                    ;


condicion           :expresiones MAYOR expresiones
                    |expresiones MAYIG expresiones
                    |expresiones MENOR expresiones
                    |expresiones MENIG expresiones
                    |expresiones IGIG expresiones
                    |expresiones DIF expresiones
                    |expresiones error expresiones                                        {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta comparador en expresion");}
                    ;

expresiones         :expresiones operador termino
                    |termino
                    |expresiones operador error                                           {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta operando en expresion");}
                    ;

termino             :tipo_id
                    |tipo_cte
                    |ID PARENTESISA parametros_reales PARENTESISC
                    ;

operador            :MAS
                    |MENOS
                    |AST
                    |BARRA
                    ;

declaracion         :tipo tipo_id
                    |tipo lista_id
                    |tipo ID PARENTESISA parametros_formales PARENTESISC bloque
                    |tipo error PARENTESISA parametros_formales PARENTESISC bloque       {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
                    ;

lista_id            :tipo_id COMA tipo_id                                                {ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add($1); arreglo.add($3); $$ = new ParserVal(arreglo); }
                    |lista_id COMA tipo_id                                               {ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) $1.obj; arreglo.add($3); $$ = new ParserVal(arreglo); }
                    |lista_id tipo_id                                                    {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
                    |tipo_id tipo_id                                                     {System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
                    ;

tipo                :ULONG
                    ;

parametros_reales   :parametros_reales COMA expresiones FLECHA tipo_id
                    |expresiones FLECHA tipo_id
                    |expresiones FLECHA error                                            {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta especificacion del parametro formal");}
                    ;

parametros_formales :parametros_formales COMA parametro
                    |parametro
                    ;

parametro           :CVR tipo tipo_id
                    |tipo tipo_id
                    |tipo error                                                         {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta nombre del parametro formal");}
                    |CVR tipo error                                                     {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta nombre del parametro formal");}
                    |CVR error tipo_id                                                  {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta tipo del parametro formal");}
                    |error tipo_id                                                      {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta tipo del parametro formal");}
                    ;

asignaciones        :tipo ID ASIGN expresiones                                          {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion");}
                    |tipo_id ASIGN expresiones                                          {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion");}
                    |lista_id IGUAL lista_cte                                           {verificar_cantidades ($1, $3); System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple");}
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

expresion_lambda    :PARENTESISA tipo ID PARENTESISC bloque PARENTESISA tipo_id PARENTESISC
                    |PARENTESISA tipo ID PARENTESISC bloque PARENTESISA tipo_cte PARENTESISC
                    ;
%%

/* CODIGO AUXILIAR */


AnalisisLexico aLex;

public void setAlex(AnalisisLexico a){
    this.aLex = a;
}

public void verificar_cantidades (ParserVal lista1, ParserVal lista2){
    ArrayList<ParserVal> l1 = (ArrayList<ParserVal>)lista1.obj;
    ArrayList<ParserVal> l2 = (ArrayList<ParserVal>)lista2.obj;
    if (l1.size() < l2.size() )
        System.out.println("Linea: "+aLex.getNroLinea()+" ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
}

void yyerror (String s){
    System.out.printf(s);
}

int yylex () throws IOException{
    int token = aLex.yylex();
    yylval = aLex.getYylval();
    return token;
}