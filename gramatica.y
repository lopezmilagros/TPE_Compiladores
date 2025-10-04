%{
    import java.io.*;
%}

%token IF ELSE ENDIF PRINT RETURN ULONG WHILE DO CTE CADENA ID := + - * / = >= <= > < == != ( ) { } _ PUNTOCOMA -> CVR
%left '+' '-'
%left '*' '/'

%%
prog                :ID bloque
                    ;

bloque              : { sentencias }
                    ;

sentencias          :sentencias sentencia
                    |sentencia
                    ;

sentencia           :ID := expresion PUNTOCOMA
                    |IF ( expresiones ) sentencia ENDIF PUNTOCOMA
                    |IF ( expresiones ) sentencia ELSE sentencia ENDIF PUNTOCOMA
                    |WHILE ( expresiones ) DO sentencia
                    |WHILE ( expresiones ) DO bloque
                    |RETURN expresiones PUNTOCOMA
                    |bloque
                    |declaraciones
                    ;

expresiones         :epresiones expresion
                    |expresion
                    ;

expresion           :expresion + expresion
                    |expresion - expresion
                    |expreison * expresion
                    |expreison / expresion
                    |CTE
                    |ID
                    ;

declaraciones       :declaraciones declaracion
                    |declaracion
                    ;

declaracion         :tipo ID PUNTOCOMA
                    |tipo ID ( parametros ) bloque
                    ;

tipo                :ulong
                    ;

parametros          :parametros parametro
                    |parametro
                    |/* vacio */
                    ;

parametro           :parametro , tipo ID
                    |tipo ID
                    ;

%%







