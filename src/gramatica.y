%{
    import java.io.*;
    import java.util.ArrayList;
    AnalisisLexico lexer;
%}


%token IF ELSE ENDIF PRINT RETURN ULONG WHILE DO CTE CADENA ID CVR ':=' '+' '-' '*' '/' '=' '>=' '<=' '>' '<' '==' '!=' '(' ')' '{' '}' '_' ';' '->'
%left '+' '-'
%left '*' '/'

%%
prog                :ID bloque
                    ;

bloque              : '{' sentencias '}'
                    ;

sentencias          :sentencias sentencia
                    |sentencia
                    ;

sentencia           :tipo_id ':=' expresiones ';'                         {System.out.println("SENTENCIA: asignacion");}
                    |IF '(' condicion ')' bloque ENDIF ';'              {System.out.println("SENTENCIA: if");}
                    |IF '(' condicion ')' bloque ELSE bloque ENDIF ';'  {System.out.println("SENTENCIA:if else");}
                    |WHILE '(' condicion ')' DO bloque                  {System.out.println("SENTENCIA:while");}
                    |RETURN '(' expresiones ')' ';'                     {System.out.println("SENTENCIA:return");}
                    |declaraciones                                      {System.out.println("SENTENCIA:declaracion");}
                    |expresion_lambda                                   {System.out.println("SENTENCIA:lambda");}
                    ;

condicion           :expresiones '>' expresiones
                    |expresiones '>=' expresiones
                    |expresiones '<' expresiones
                    |expresiones '<=' expresiones
                    |expresiones '==' expresiones
                    |expresiones '!=' expresiones
                    ;

expresiones         :expresiones operador termino
                    |'(' expresiones ')'
                    |termino
                    ;

termino             :tipo_id
                    |CTE
                    |ID '(' parametros_formales ')'
                    ;

operador            :'+'
                    |'-'
                    |'*'
                    |'/'
                    ;

parametros_formales : parametros_formales ',' expresiones '->' tipo_id
                    |expresiones '->' tipo_id
                    ;

declaraciones       :declaraciones declaracion
                    |declaracion
                    ;

declaracion         :tipo lista_id ';'
                    |tipo ID '(' parametros ')' bloque
                    ;

lista_id            :tipo_id                 { $$ = new ArrayList<>(); $$.add($1); }
                    |lista_id ',' tipo_id    { $$ = $1; $$.add($3); }
                    ;

tipo                :ULONG
                    ;

parametros          :parametros ',' parametro
                    |parametro
                    ;

parametro           :tipo tipo_id
                    |CVR tipo_id
                    ;

asignaciones        :tipo_id ':=' expresiones ';'
                    |lista_id '=' lista_cte ';'         { verificar_cantidades ($1, $3); }
                    ;

lista_cte           :CTE                { $$ = new ArrayList<>(); $$.add($1); }
                    |lista_cte ',' CTE  { $$ = $1; $$.add($3); }
                    ;

tipo_id             :ID
                    |ID'.'ID
                    ;

expresion_lambda    :'(' tipo ID ')' bloque '(' tipo_id ')'
                    |'(' tipo ID ')' bloque '(' CTE ')'
                    ;
%%

/* CODIGO AUXILIAR */

public void verificar_cantidades (ArrayList<String> lista1, ArrayList<String> lista2){
    if (lista1.size() > lista2.size() )
        System.out.println("ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
}

public void setLexer (AnalisisLexico aLex){
    this.lexer = aLex;
}

public int yylex () throws IOException {
    return lexer.yylex();
}

public void yyerror(String s) {
    System.out.println("Error: " + s);
}

public static void main (String[] args) throws Exception {
    AnalisisLexico aLex = new AnalisisLexico("/home/milagros/Documentos/Compiladores/Compiladores/texto.txt");
    Parser p = new Parser();
    p.setLexer(aLex);
    p.yyparse();
}