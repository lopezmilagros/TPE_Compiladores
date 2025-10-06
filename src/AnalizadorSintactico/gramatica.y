%{
    package AnalizadorSintactico;
    import java.io.*;
    import java.util.ArrayList;
    import AnalizadorLexico.*;

%}


%token IF ELSE ENDIF PRINT RETURN ULONG WHILE DO CTE CADENA ID CVR ASIGN MAS MENOS AST BARRA IGUAL MAYIG MENIG MAYOR MENOR IGIG DIF PARENTESISA PARENTESISC LLAVEA LLAVEC GUIONB PUNTOCOMA FLECHA
%left MAS MENOS
%left AST BARRA

%%
prog                :ID bloque
                    ;

bloque              : LLAVEA sentencias LLAVEC
                    ;

sentencias          :sentencias sentencia
                    |sentencia
                    ;

sentencia           :asignaciones                         {System.out.println("SENTENCIA: asignacion");}
                    |IF PARENTESISA condicion PARENTESISC bloque ENDIF PUNTOCOMA    {System.out.println("SENTENCIA: if");}
                    |IF PARENTESISA condicion PARENTESISC bloque ELSE bloque ENDIF PUNTOCOMA  {System.out.println("SENTENCIA:if else");}
                    |WHILE PARENTESISA condicion PARENTESISC DO bloque                 {System.out.println("SENTENCIA:while");}
                    |RETURN PARENTESISA expresiones PARENTESISC PUNTOCOMA                     {System.out.println("SENTENCIA:return");}
                    |declaracion                                      {System.out.println("SENTENCIA:declaracion");}
                    |expresion_lambda                                   {System.out.println("SENTENCIA:lambda");}
                    |PRINT PARENTESISA CADENA PARENTESISC PUNTOCOMA
                    |PRINT PARENTESISA termino PARENTESISC PUNTOCOMA
                    ;

condicion           :expresiones MAYOR expresiones
                    |expresiones MAYIG expresiones
                    |expresiones MENOR expresiones
                    |expresiones MENIG expresiones
                    |expresiones IGIG expresiones
                    |expresiones DIF expresiones
                    ;

expresiones         :expresiones operador termino
                    |PARENTESISA expresiones PARENTESISC
                    |termino
                    ;

termino             :tipo_id
                    |CTE
                    |ID PARENTESISA parametros_formales PARENTESISC
                    ;

operador            :MAS
                    |MENOS
                    |AST
                    |BARRA
                    ;

parametros_formales : parametros_formales ',' expresiones FLECHA tipo_id
                    |expresiones FLECHA tipo_id
                    ;

declaracion         :tipo lista_id PUNTOCOMA
                    |tipo ID PARENTESISA parametros PARENTESISC bloque
                    ;

lista_id            :tipo_id                 { ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add($1); $$ = new ParserVal(arreglo); }
                    |lista_id ',' tipo_id    { ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) $1.obj; arreglo.add($3); $$ = new ParserVal(arreglo); }
                    ;

tipo                :ULONG
                    ;

parametros          :parametros ',' parametro
                    |parametro
                    ;

parametro           :tipo tipo_id
                    |CVR tipo_id
                    ;

asignaciones        :tipo_id ASIGN expresiones PUNTOCOMA
                    |lista_id IGUAL lista_cte PUNTOCOMA         { verificar_cantidades ($1, $3); }
                    ;

lista_cte           :CTE                { ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add($1); $$ = new ParserVal(arreglo);}
                    |lista_cte ',' CTE  { ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) $1.obj; arreglo.add($3); $$ = new ParserVal(arreglo);}
                    ;

tipo_id             :ID
                    |ID'.'ID
                    ;

expresion_lambda    :PARENTESISA tipo ID PARENTESISC bloque PARENTESISA tipo_id PARENTESISC
                    |PARENTESISA tipo ID PARENTESISC bloque PARENTESISA CTE PARENTESISC
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
    if (l1.size() > l2.size() )
        System.out.println("Linea: "+AnalisisLexico.getNroLinea()+" ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
}

void yyerror (String s){}

int yylex () throws IOException{
   return aLex.yylex();

}