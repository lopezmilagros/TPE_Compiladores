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
                    ;

bloque              : LLAVEA sentencias LLAVEC
                    ;

sentencias          :sentencias sentencia PUNTOCOMA
                    |sentencia PUNTOCOMA
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
                    |ID PARENTESISA parametros_reales PARENTESISC
                    ;

operador            :MAS
                    |MENOS
                    |AST
                    |BARRA
                    ;

declaracion         :tipo lista_id
                    |tipo ID PARENTESISA parametros_formales PARENTESISC bloque
                    ;

lista_id            :tipo_id COMA tipo_id                                                {ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add($1); arreglo.add($3); $$ = new ParserVal(arreglo); }
                    |lista_id COMA tipo_id                                               {ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) $1.obj; arreglo.add($3); $$ = new ParserVal(arreglo); }
                    ;

tipo                :ULONG
                    ;

parametros_reales   :parametros_reales COMA expresiones FLECHA tipo_id
                    |expresiones FLECHA tipo_id
                    ;

parametros_formales :parametros_formales COMA parametro
                    |parametro
                    ;

parametro           :CVR tipo tipo_id
                    |tipo tipo_id
                    ;

asignaciones        :tipo ID ASIGN expresiones                                          {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion");}
                    |tipo_id ASIGN expresiones                                          {System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion");}
                    |lista_id IGUAL lista_cte                                           {verificar_cantidades ($1, $3); }
                    ;

lista_cte           :CTE                                                                {ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add($1); $$ = new ParserVal(arreglo);}
                    |lista_cte COMA CTE                                                 {ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) $1.obj; arreglo.add($3); $$ = new ParserVal(arreglo);}
                    ;

tipo_id             :ID
                    |ID PUNTO ID
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
        System.out.println("Linea: "+aLex.getNroLinea()+" ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
}

void yyerror (String s){
    System.out.println(s);
}

int yylex () throws IOException{
   return aLex.yylex();

}