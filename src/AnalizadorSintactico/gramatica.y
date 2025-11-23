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
    : ID bloque     {agregarSentencia("LINEA: "+aLex.getNroLinea()+" SENTENCIA: Nombre de programa");  modificarUsoTS($1.sval, "Nombre de programa"); polacaInversa.put("MAIN", mainArreglo); if(huboError()){errorGeneral = "No se genero codigo assembler por presencia de errores"; } }
    | bloque        {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de programa");}
    ;

bloque
    : LLAVEA sentencias LLAVEC              {System.out.println("AMBITOOO: "+ ambito);}
    | LLAVEA sentencias error             {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador final");}
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
    | sentencia_if                                    {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: If");}
    | sentencia_while                                 {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: While");}
    | expresion_lambda                                {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Lambda");}
    ;

sentencia_return
    : RETURN PARENTESISA lista_id PARENTESISC PUNTOCOMA        {ArrayList<String> a = (ArrayList<String>)$3.obj; if(variablesPermitidas(a)){agregarAPolaca("empieza lista");agregarListaAPolaca(a); agregarAPolaca("return");}}
    | RETURN PARENTESISA expresiones PARENTESISC PUNTOCOMA     {ArrayList<String> b = (ArrayList<String>) $3.obj; if (!b.contains("null")) {agregarAPolaca("empieza lista");agregarListaAPolaca(b); agregarAPolaca("return");}}
    | RETURN PARENTESISA lista_id PARENTESISC                  {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    | RETURN PARENTESISA expresiones PARENTESISC               {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    ;

sentencia_print
    : PRINT PARENTESISA CADENA PARENTESISC PUNTOCOMA            {agregarAPolaca($3.sval); agregarAPolaca("print");}
    | PRINT PARENTESISA expresiones PARENTESISC PUNTOCOMA       {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Print"); ArrayList<String> b = (ArrayList<String>) $3.obj; if (!b.contains("null")) { agregarListaAPolaca(b); } agregarAPolaca("print");}
    | sentencia_print_error
    ;

sentencia_print_error
    : PRINT PARENTESISA CADENA PARENTESISC                  {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    | PRINT PARENTESISA expresiones PARENTESISC             {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    | PRINT PARENTESISA  PARENTESISC PUNTOCOMA              {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTÁCTICO: Falta argumento en print");}
    | PRINT PARENTESISA  PARENTESISC                        {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTÁCTICO: Falta argumento en print y falta ';' ");}
    ;

sentencia_while
    : header_while DO bloque PUNTOCOMA                      {if (!$1.sval.equals("null")){agregarAPolaca("cuerpo"); bifurcacionWhile(); agregarAPolaca("LABEL "+label+":");  agregarBifurcacion("cond");}}
    | sentencia_while_error
    ;

header_while
    : inicio_header_while PARENTESISA condicion PARENTESISC {if ($3.sval.equals("null")){$$ = new ParserVal("null");} else {$$ = new ParserVal("sin error");}}
    ;

inicio_header_while
    :WHILE                                                 { pilaWhile.push(label); agregarAPolaca("LABEL "+label+":"); label++; }
    ;

sentencia_while_error
    : inicio_header_while condicion PARENTESISC DO bloque PUNTOCOMA                                   {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
    | inicio_header_while PARENTESISA condicion DO bloque PUNTOCOMA                                   {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
    | inicio_header_while condicion DO bloque PUNTOCOMA                                               {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
    | header_while bloque PUNTOCOMA                                                     {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'do' en iteracion");}
    | header_while DO PUNTOCOMA                                                         {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTÁCTICO: Falta cuerpo de la iteracion");}
;

sentencia_if
    : header_if ELSE bloque ENDIF PUNTOCOMA                 {if (!$1.sval.equals("null")) {agregarAPolaca("LABEL "+label+":"); Integer l = label - 1; agregarBifurcacion("LABEL "+l+":");}}
    | header_if ENDIF PUNTOCOMA
    | sentencia_if_error
    ;

header_if
    : IF PARENTESISA condicion PARENTESISC bloque        {System.out.println("Ambito en if: " + ambito); if (!$3.sval.equals("null")) {agregarAPolaca("LABEL "+label+":"); agregarBifurcacion("cond"); $$ = new ParserVal("sin error");} else {$$ = new ParserVal("null");}}
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
    |IF PARENTESISA condicion PARENTESISC ELSE ENDIF PUNTOCOMA                          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else', 'then'");}
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
    : expresiones MAYOR expresiones                 {ArrayList<String> b = (ArrayList<String>) $1.obj;
                                                    $$ = new ParserVal("null");
                                                    if (!b.contains("null")){
                                                        ArrayList<String> a = (ArrayList<String>) $3.obj;
                                                        if (!a.contains("null")) {
                                                            agregarListaAPolaca(b);
                                                            agregarListaAPolaca(a);
                                                            agregarAPolaca(">");
                                                            agregarAPolaca("cond");
                                                            $$ = new ParserVal("sin error");
                                                        }
                                                    }
                                                    }
    | expresiones MAYIG expresiones                 {$$ = new ParserVal("null"); ArrayList<String> b = (ArrayList<String>) $1.obj; if (!b.contains("null")) { ArrayList<String> a = (ArrayList<String>) $3.obj; if (!a.contains("null")) { agregarListaAPolaca(b); agregarListaAPolaca(a); agregarAPolaca(">="); agregarAPolaca("cond"); $$ = new ParserVal("sin error");}}}
    | expresiones MENOR expresiones                 {$$ = new ParserVal("null"); ArrayList<String> b = (ArrayList<String>) $1.obj; if (!b.contains("null")) { ArrayList<String> a = (ArrayList<String>) $3.obj; if (!a.contains("null")) { agregarListaAPolaca(b); agregarListaAPolaca(a); agregarAPolaca("<"); agregarAPolaca("cond"); $$ = new ParserVal("sin error");}}}
    | expresiones MENIG expresiones                 {$$ = new ParserVal("null"); ArrayList<String> b = (ArrayList<String>) $1.obj; if (!b.contains("null")) { ArrayList<String> a = (ArrayList<String>) $3.obj; if (!a.contains("null")) { agregarListaAPolaca(b); agregarListaAPolaca(a); agregarAPolaca(">="); agregarAPolaca("cond"); $$ = new ParserVal("sin error");}}}
    | expresiones IGIG expresiones                  {$$ = new ParserVal("null"); ArrayList<String> b = (ArrayList<String>) $1.obj; if (!b.contains("null")) { ArrayList<String> a = (ArrayList<String>) $3.obj; if (!a.contains("null")) { agregarListaAPolaca(b); agregarListaAPolaca(a); agregarAPolaca("=="); agregarAPolaca("cond"); $$ = new ParserVal("sin error");}}}
    | expresiones DIF expresiones                   {$$ = new ParserVal("null"); ArrayList<String> b = (ArrayList<String>) $1.obj; if (!b.contains("null")) { ArrayList<String> a = (ArrayList<String>) $3.obj; if (!a.contains("null")) { agregarListaAPolaca(b); agregarListaAPolaca(a); agregarAPolaca("=!"); agregarAPolaca("cond"); $$ = new ParserVal("sin error");}}}
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
    : tipo ID ASIGN expresiones PUNTOCOMA               {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion y asignacion"); ArrayList<String> a = new ArrayList<String>(); a.add($2.sval); if(!variablePermitida($2.sval)) {
                                                                                                                                                                                                                         ArrayList<String> b = (ArrayList<String>) $4.obj;
                                                                                                                                                                                                                         if (!b.contains("null")) {
                                                                                                                                                                                                                             modificarTipoTS(a, $1.sval);
                                                                                                                                                                                                                             modificarUsos(a, "Nombre de variable");
                                                                                                                                                                                                                             agregarListaAPolaca(b);
                                                                                                                                                                                                                             agregarAPolaca($2.sval);
                                                                                                                                                                                                                             agregarAPolaca(":=");
                                                                                                                                                                                                                         }
                                                                                                                                                                                                                     }
                                                                                                                                                                                                                      else{
                                                                                                                                                                                                                          agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+$2.sval+"' ya fue declarada.");}
                                                                                                                                                                                                                      }
    | identificador ASIGN expresiones PUNTOCOMA         {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion"); if(!variablePermitida($1.sval)){agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+$1.sval+"' no declarada.");} else {ArrayList<String> a = (ArrayList<String>) $3.obj; if (!a.contains("null")) {agregarListaAPolaca(a); agregarAPolaca($1.sval); agregarAPolaca(":=");}}}
    | lista_id IGUAL lista_cte PUNTOCOMA                {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion multiple"); ArrayList<String> l1 = (ArrayList<String>)$1.obj; ArrayList<String> l3 = (ArrayList<String>)$3.obj; if (verificar_cantidades(l1, l3)){ if(variablesPermitidas(l1)){ agregarPolacaMultiple(l1, l3);}}}
    | identificador IGUAL lista_cte PUNTOCOMA           {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion multiple"); if(!variablePermitida($1.sval)){agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+$1.sval+"' no declarada.");} else {agregarListaAPolaca((ArrayList<String>)$3.obj); agregarAPolaca($1.sval); agregarAPolaca("=");}}
    | asignacion_error                                  {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    ;

asignacion_error
    :tipo ID ASIGN expresiones
    |identificador ASIGN expresiones
    |lista_id IGUAL lista_cte
    |identificador IGUAL lista_cte
    ;

expresiones
    : expresiones MAS termino       {ArrayList<String> a = (ArrayList<String>)$1.obj; ArrayList<String> b= (ArrayList<String>)$3.obj; a.addAll(b); a.add("+"); $$ = new ParserVal(a);}
    | expresiones MENOS termino     {ArrayList<String> a = (ArrayList<String>)$1.obj; ArrayList<String> b= (ArrayList<String>)$3.obj; a.addAll(b); a.add("-"); $$ = new ParserVal(a);}
    | expresiones AST termino       {ArrayList<String> a = (ArrayList<String>)$1.obj; ArrayList<String> b= (ArrayList<String>)$3.obj; a.addAll(b);a.add("*"); $$ = new ParserVal(a);}
    | expresiones BARRA termino     {ArrayList<String> a = (ArrayList<String>)$1.obj; ArrayList<String> b= (ArrayList<String>)$3.obj; a.addAll(b); a.add("/"); $$ = new ParserVal(a);}
    | termino                       {ArrayList<String> a = (ArrayList<String>)$1.obj; $$ = new ParserVal($1.obj);}
    | expresiones operador error         {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta operando en expresion");}
    ;

termino
    : identificador         {ArrayList<String> a = new ArrayList<String>(); if(!variablePermitida($1.sval)){agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+$1.sval+"' no declarada."); a.add("null"); $$ = new ParserVal(a);} else {a.add($1.sval); $$ = new ParserVal(a);} }
    | tipo_cte              {ArrayList<String> a = new ArrayList<String>(); a.add($1.sval); $$ = new ParserVal(a); }
    | llamado_funcion       {$$ = new ParserVal($1.obj); }
    ;

llamado_funcion
    :ID PARENTESISA parametros_reales PARENTESISC       {ArrayList<String> a = new ArrayList<>((ArrayList<String>) $3.obj);
                                                         if (funcionPermitida($1.sval)){
                                                            a.add($1.sval);
                                                            a.add("call");
                                                            //Chequeo si es CVR reasigno los formales a los reales
                                                            if($3 != null) {cvrAPolaca($1.sval, (ArrayList<String>)$3.obj, a);}
                                                         }else {
                                                            agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: funcion '"+$1.sval+"' fuera de alcance.");
                                                            a.add("null");} $$ = new ParserVal(a);
                                                         }
    ;

operador
    :MAS            {agregarAPolaca("+");}
    |MENOS          {agregarAPolaca("-");}
    |AST            {agregarAPolaca("*");}
    |BARRA          {agregarAPolaca("/");}
    ;

sentencia_declarativa
    : tipo ID PUNTOCOMA                     {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de variable"); ArrayList<String> b = new ArrayList<String>(); b.add($2.sval); if(!variablePermitida($2.sval)){modificarTipoTS(b, $1.sval); modificarUsoTS($2.sval, "Nombre de variable");}else{agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+$2.sval+"' ya fue declarada.");}}
    | tipo lista_id PUNTOCOMA               {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de variables"); ArrayList<String> a = (ArrayList<String>)$2.obj; if(!variablesDeclaradas(a)){modificarTipoTS(a, $1.sval); modificarUsos(a, "Nombre de variable");}}
    | funcion                               {agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de funcion");}
    | tipo ID error                         {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    | tipo lista_id error                   {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
    | tipo ID PUNTO ID PUNTOCOMA {System.out.println("ERRORRRRRR");}
    ;

funcion
    :  header_funcion bloque                {String ambitoConFuncion = ambito;
                                            borrarAmbito();}
    ;

header_funcion
    : tipo ID PARENTESISA parametros_formales PARENTESISC       {String nombre = $2.sval;
                                                                 if (existeFuncionVisibleConNombre(nombre)) {
                                                                   agregarErrorSemantico("LINEA " + aLex.getNroLinea() +" ERROR SEMANTICO: Funcion '" + nombre + "' redeclarada");
                                                                 } else {
                                                                   modificarUsoTS(nombre, "Nombre de funcion");
                                                                   ambito = ambito + ":" + nombre;
                                                                   modificarAmbitosTS((ArrayList<String>)$4.obj);
                                                                   modificarUsosParametros((ArrayList<String>)$4.obj, "Nombre de parametro");
                                                                   agregarInfoFuncionTS($1.sval, (ArrayList<String>) $4.obj);
                                                                   polacaInversa.put(ambito, new ArrayList<String>());
                                                               }}
    | tipo PARENTESISA parametros_formales PARENTESISC          {agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
    ;

parametros_formales
    :parametros_formales COMA parametro_formal   {ArrayList<String> a = (ArrayList<String>)$1.obj; a.add($3.sval); $$ = new ParserVal(a);}
    |parametro_formal                            {ArrayList<String> a = new ArrayList<String>(); a.add($1.sval); $$ = new ParserVal(a);}
    |parametros_formales parametro_formal        {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta ',' en declaracion de las variables");}
    ;

parametro_formal
    :CVR tipo ID                      { $$ = new ParserVal("cvr "+$2.sval+" "+$3.sval);}
    |tipo ID                          { $$ = new ParserVal("cv "+$1.sval+" "+$2.sval);}
    |parametro_formal_error                      {$$ = new ParserVal();}
    ;

parametro_formal_error
    :tipo error                              {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta nombre del parametro formal");}
    |CVR tipo                                {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta nombre del parametro formal");}
    |CVR ID                                 {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta tipo del parametro formal");}
    |error ID                               {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta tipo del parametro formal");}
    ;

parametros_reales
    : parametros_reales COMA expresiones FLECHA identificador       {ArrayList<String> b = (ArrayList<String>) $3.obj; b.add($5.sval); b.add("->"); $$ = new ParserVal(b); sacarParamFormTS(ambito+":"+$5.sval);}
    | expresiones FLECHA identificador                              {ArrayList<String> b = (ArrayList<String>) $1.obj; b.add($3.sval); b.add("->"); $$ = new ParserVal(b); sacarParamFormTS(ambito+":"+$3.sval);}
    | expresiones FLECHA                                            {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta especificacion del parametro formal");}
    ;

expresion_lambda
        : header_lambda bloque llamado_lambda PUNTOCOMA              {agregarPolacaLambda($3.sval, $1.sval);

                                                                     borrarAmbito();
                                                                     agregarAPolaca("->");
                                                                     agregarAPolaca("LAMBDA");
                                                                     agregarAPolaca("call");}
    ;

header_lambda
    : FLECHA PARENTESISA tipo ID PARENTESISC                        {ambito = ambito + ":LAMBDA";
                                                                     ArrayList<String> polaca = new ArrayList<String>();
                                                                     polacaInversa.put(ambito, polaca);

                                                                    ArrayList<String> parametroCompleto = new ArrayList<String>();
                                                                    String parametro =  "cv " + $3.sval+ " "+$4.sval;
                                                                    parametroCompleto.add(parametro);
                                                                    modificarAmbitosTS(parametroCompleto);

                                                                    ArrayList<String> parametroID = new ArrayList<String>();
                                                                    parametroID.add($4.sval);
                                                                    modificarUsosParametros(parametroID, "Nombre de parametro");
                                                                    System.out.println("AMBITO header LAMBDA2 : "+ambito);

                                                                    $$ = new ParserVal($4.sval);}
    ;

llamado_lambda
    : PARENTESISA identificador PARENTESISC                         {if(!variablePermitida($2.sval)){agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+$1.sval+"' no declarada.");} $$ = new ParserVal($2.sval);}
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
    :ID                 {$$ = new ParserVal($1.sval);}
    |ID PUNTO ID        {String name = $1.sval + "." + $3.sval; String a = $1.sval + ":" + $3.sval; $$ = new ParserVal(name); }
    ;

tipo
    :ULONG              {$$ = new ParserVal("ULONG");}
    ;

tipo_cte
    : CTE               {agregarCteTS($1.sval); $$ = new ParserVal($1.sval);}
    | MENOS CTE         {String cte = "-" + $2.sval; agregarCteTS(cte); if(!cte.contains(".") & !cte.contains("D")){ cte = cte.substring(1);} $$ = new ParserVal(cte);}
    ;

%%

/* -------------------------------------------------------------------------------------------------------------CODIGO AUXILIAR ----------------------------------------------------*/

AnalisisLexico aLex;
private HashMap<String, ArrayList<String>> tablaDeSimbolos = new HashMap<String, ArrayList<String>>();

public void setAlex(AnalisisLexico a){
    this.aLex = a;

}

public boolean verificar_cantidades(ArrayList<String> l1, ArrayList<String> l2){
    if (l1 != null && l2 != null){
        if (l1.size() > l2.size() ){
            agregarError("Linea "+aLex.getNroLinea()+" ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
            return false;
            }
        }
        if (l1.size() < l2.size()){
            agregarErrorSemantico("Linea "+aLex.getNroLinea()+" WARNING: Quedan elementos sin asignar del lado derecho.");
            return true;
        }
        if (l1.size() == l2.size()) {
            return true;
        }
    return false;

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

public void modificarUsosParametros(ArrayList<String> lista, String uso){
    for (String parametro: lista){
        int indice = parametro.lastIndexOf(" ");
        parametro = parametro.substring(indice + 1);
        modificarUsoTS(parametro, uso);
        System.out.println("Se modifico el uso del parametro" +ambito+parametro);
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
            if(fila.size() == 1)
                fila.add(""); //agrego tipo vacio en indice 1
            if(fila.size() == 2)
                fila.add(uso); //Se agrega el uso en el indice = 2
            else
                fila.set(2,uso);
        }else{
            System.out.println("(modificarUsoTS) Error, la clave" + clave + " no existe en la tabla de simbolos");
        }
    }
}

public void sacarParamFormTS(String clave){
    ArrayList<String> aux = tablaDeSimbolos.get(clave);
    //Solo la elimino si es una variable no inicializada.
    if (aux.size() <= 2 || aux.get(2).equals(""))
        tablaDeSimbolos.remove(clave);
}

public void modificarAmbitosTS(ArrayList<String> a){
    int index = ambito.lastIndexOf(":");
    String ambitoAfuera = ambito.substring(0, index);



    for (String parametro : a){
        if (parametro != null){
            //me quedo solo con su nombre, saco semantica y tipo
            int indice1 = parametro.indexOf(" ");
            parametro = parametro.substring(indice1 + 1);
            int indice2 = parametro.indexOf(" ");
            String tipo = parametro.substring(0, indice2);
            parametro = parametro.substring(indice2 + 1);

            System.out.println("mi parametro: "+parametro);
            System.out.println("mi ambito afuera: "+ambitoAfuera);
            ArrayList<String> aux = tablaDeSimbolos.get(ambitoAfuera+":"+parametro);

            //le agrego el tipo del parametro a la TS
            aux.set(1,tipo);
            tablaDeSimbolos.put(ambito+":"+parametro, aux);

            //Lo busco en la ts y modifico su clave para que incluya el ambito de la funcion definida
            sacarParamFormTS(ambitoAfuera+":"+parametro);
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

public void agregarInfoFuncionTS(String tipoReturn, ArrayList<String> parametros){
    //Convierto los parametros en un string separados por ','
    String aux = "";
    if(parametros != null){
        aux = String.join(", ", parametros);
    }
    //Ya se concateno el nombre de la funcion al ambito
    String funcion = ambito;
    if (tablaDeSimbolos.containsKey(funcion)){
        ArrayList<String> a = tablaDeSimbolos.get(funcion);
        if(a.size() == 3){
            a.set(1, tipoReturn); //en TIPO indico el tipo que retorna la funcion
            a.add(aux); //Agrego en indice 3 un string con los parametros formales de la funcion (sus tipos y su semantica)
        }
    }else{
      System.out.println("(agregarInfoFuncionTS) Error, la clave" + funcion + " no existe en la tabla de simbolos");
    }
}

//-------------------------------------------------------------------------------------------------------------CODIGO INTERMEDIO
private ArrayList<String> mainArreglo = new ArrayList<String>();
private HashMap<String, ArrayList<String>> polacaInversa = new HashMap<String, ArrayList<String>>();
private String ambito = "MAIN";
private Integer indiceWhile;
private Integer label = 0;
private java.util.Stack<Integer> pilaWhile = new java.util.Stack<>();

public HashMap<String, ArrayList<String>> getPolacaInversa(){ return polacaInversa; }

public void borrarAmbito(){
    int index = ambito.lastIndexOf(":");
    if (index != -1) {
         String texto = ambito;
        texto = texto.substring(0, index);
        ambito = texto;
    }
}

public void cvrAPolaca(String funcion, ArrayList<String> parametros_reales, ArrayList<String> arreglo){
    ArrayList<String> a;

    String semantica = tablaDeSimbolos.get(ambito+":"+funcion).get(3);
    String[] parametros = semantica.split(",");

    //si la semantica es CVR agrego a la polaca una asignacion
    for(String p : parametros){
        int indice = p.lastIndexOf(" ");
        String nombre = p.substring(indice+1);

        //encuentro el real que esta asignado a el formal
        String real = parametros_reales.get(parametros_reales.indexOf(nombre) - 1);

        if(p.contains("cvr")){
            arreglo.add(nombre);
            arreglo.add(real);
            arreglo.add("->");
        }
    }
}
public void agregarAPolaca(String valor){
if (polacaInversa != null){

    if (polacaInversa.containsKey(ambito)) {
        ArrayList<String> a = tablaDeSimbolos.get(valor);
        if (a!= null && a.size() == 3){
            String uso = a.get(2);
            if(!uso.equals("Nombre de parametro"))
                polacaInversa.get(ambito).add(valor);
            //Los parametros formales los agregamos a la polaca inversa manualmente en las reglas
        }else
            polacaInversa.get(ambito).add(valor);
    }
    else{
        mainArreglo.add(valor);
    }
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

    String l = label.toString();

    if (flag.equals("cond")) {
       a.set(i, "SALTO A "+l);
       a.add(i + 1, "BF");
    }else{
        a.set(i, "SALTO A "+l);
        a.add(i + 1, "BI");
        a.add(i + 2, flag);
    }
    label++;
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
    Integer l = pilaWhile.pop();
    a.set(i, "SALTO A "+l);
    a.add("BI");
}


//-----------------------------------CHEQUEOS SEMANTICOS----------------------------------------

public void agregarPolacaLambda(String valor1, String valor2){
    if (polacaInversa != null){
        if (polacaInversa.containsKey(ambito)) {
            ArrayList<String> arreglo = polacaInversa.get(ambito);
            arreglo.add(0,valor1);
            arreglo.add(1, valor2);
            arreglo.add(2, ":=");
        }
    }
}

// Devuelve true si ya existe alguna función con ese nombre
// en el ámbito actual o en alguno de sus padres
public boolean existeFuncionVisibleConNombre(String nombre) {
    String amb = ambito;  // ej: "MAIN", "MAIN:FUNC2", "MAIN:FUNC2:FUNC3"

    while (true) {
        String clave = amb + ":" + nombre;   // ej: "MAIN:FUNC2", "MAIN:FUNC2:FUNC2"
        if (tablaDeSimbolos.containsKey(clave)) {
            ArrayList<String> fila = tablaDeSimbolos.get(clave);
            if (fila.size() >= 3 && "Nombre de funcion".equals(fila.get(2))) {
                return true;   // ya hay una función con ese nombre en algún ámbito visible
            }
        }

        // Subo un nivel en la cadena de ámbitos
        int idx = amb.lastIndexOf(':');
        if (idx == -1) break;        // ya no hay más padres
        amb = amb.substring(0, idx); // ej: "MAIN:FUNC2" -> "MAIN"
    }

    return false;
}


public void agregarPolacaMultiple(ArrayList<String> l1, ArrayList<String> l2){
    for (int i=0; i<l1.size(); i++){
        agregarAPolaca(l1.get(i));
        agregarAPolaca(l2.get(i));
        agregarAPolaca(":=");
    }

}

public boolean estaInicializada(String id){
//Recibe el id concatenado con el ambito
    if (!tablaDeSimbolos.containsKey(id))
        return false;
    else {
      ArrayList<String> a = tablaDeSimbolos.get(id);
      if (a != null && a.size() == 3) {
        String uso = a.get(2);
        if (uso.equals("Nombre de variable") || uso.equals("Nombre de parametro"))
          //inicializada
          return true;
      }
    }
    return false;
}

public String variableAlAlcance(String id){
    //Si una variable esta al alcance, devuelve su clave en la TS (ambito+variable), sino devuelve null
    String ambitoActual = ambito+":";

    //Para variables sin prefijado
    while (true) {
        String clave = ambitoActual + id;

        // esta al alcance
        if (tablaDeSimbolos.containsKey(clave))
           return clave;

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

   return null; // no encontrado
}


public boolean estaDeclarada(String id){
//Recibe el id concatenado con el ambito
    if (!tablaDeSimbolos.containsKey(id))
        return false;
    else {
      ArrayList<String> a = tablaDeSimbolos.get(id);
      if (a != null && a.size() >= 3) {
        String uso = a.get(2);
        if (uso.equals("Nombre de funcion"))
          //declarada
          return true;
      }
    }
    return false;
}

public boolean funcionPermitida(String id) {
    //Chequeo si la funcion esta al alcance y si esta inicializada
    String clave = variableAlAlcance(id);
    if(clave != null)
        // esta al alcance
        if(estaDeclarada(clave))
            // fue inicializada en ese ambiente
                return true;
   return false; // no se puede usar
}



//Este metodo tambien se puede usar para declarar una variable. Si la variable no esta permitida,
//significa que no hay una declaracion de esa variable en su ambito, por lo que se puede declarar.

public boolean variablePermitida(String id) {
    //Para variables con prefijado
    if(id.contains(".")){
        int indice = id.lastIndexOf(".");
        String funcion = id.substring(0, indice);

        if(ambito.contains(funcion))
            return true;
        else
            return false;
    }

    String clave = variableAlAlcance(id);
    //Chequeo si la variable esta al alcance y si esta inicializada
    if(clave != null)
        // esta al alcance
        if(estaInicializada(clave))
            // fue inicializada en ese ambiente
                return true;
   return false; // no se puede usar
}

public boolean variablesPermitidas(ArrayList<String> a){
    boolean permitido = true;
    if (!a.isEmpty()){
        for (String variable: a){
            if (!variablePermitida(variable)){
                agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+variable+"' no declarada.(val permtida)");
                //Uso un booleano para no retornar antes y que se agreguen todas las que no esten permitidas
                permitido = false;
            }
        }
    }
    return permitido;
}

public boolean variablesDeclaradas(ArrayList<String> a){
    boolean declarada = false;
    if (!a.isEmpty()){
        for (String variable: a){
            if (variablePermitida(variable)){
                //ya fue declarada
                agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+variable+"' ya fue declarada.");
                //Uso un booleano para no retornar antes y que se agreguen todas las ya fueron declaradas
                declarada = true;
            }
        }
    }
    return declarada;
}
//----------------------------------------------------------------------------------------------------------ERRORES SEMANTICOS
ArrayList<String> erroresSemanticos = new ArrayList<>();

void agregarErrorSemantico(String s){
    erroresSemanticos.add(s);
}

public boolean huboError(){
    if(!erroresSemanticos.isEmpty()){
            for (String e :errores){
                if(e.contains("ERROR"))
                    return true;
            }
        }

    if(!errores.isEmpty()){
        for (String e :errores){
            if(e.contains("ERROR"))
                return true;
        }
    }
    ArrayList<String> a = aLex.getErroresLexicos();
    if(!a.isEmpty()){
        for (String e : a){
            if(e.contains("ERROR"))
                return true;
        }
    }

    return false;
}

public boolean existeVariable(String name){
    System.out.println("VARIABLE!!: "+name);
    for (String clave : tablaDeSimbolos.keySet()){
        if(clave.endsWith(name)){
            return true;
        }
    }
    return false;
}
//----------------------------------------------------------------------------------------------------------IMPRESIONES

private String errorGeneral = "";

public String getError(){
    return errorGeneral;
}

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


public void imprimirErroresSemanticos(){
    System.out.println("");
    System.out.println("Errores semanticos: ");
    if (!erroresSemanticos.isEmpty()){
        for (String error: erroresSemanticos){
            System.out.println(error);
        }
    }else{
        System.out.println("No se encontraron errores semanticos");
    }
}

public HashMap<String, ArrayList<String>> getTablaDeSimbolos() {
    return tablaDeSimbolos;
}