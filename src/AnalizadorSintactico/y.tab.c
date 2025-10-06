#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#line 2 "gramatica.y"
    package AnalizadorSintactico;
    import java.io.*;
    import java.util.ArrayList;
    import AnalizadorLexico.*;
#line 11 "y.tab.c"
#define IF 257
#define ELSE 258
#define ENDIF 259
#define PRINT 260
#define RETURN 261
#define ULONG 262
#define WHILE 263
#define DO 264
#define CTE 265
#define CADENA 266
#define ID 267
#define CVR 268
#define ASIGN 269
#define MAS 270
#define MENOS 271
#define AST 272
#define BARRA 273
#define IGUAL 274
#define MAYIG 275
#define MENIG 276
#define MAYOR 277
#define MENOR 278
#define IGIG 279
#define DIF 280
#define PARENTESISA 281
#define PARENTESISC 282
#define LLAVEA 283
#define LLAVEC 284
#define GUIONB 285
#define PUNTOCOMA 286
#define FLECHA 287
#define YYERRCODE 256
short yylhs[] = {                                        -1,
    0,    1,    2,    2,    3,    3,    3,    3,    3,    3,
    3,    5,    5,    5,    5,    5,    5,    6,    6,    6,
   10,   10,   10,    9,    9,    9,    9,   12,   12,    7,
    7,   14,   14,   13,   15,   15,   16,   16,    4,    4,
   17,   17,   11,   11,    8,    8,
};
short yylen[] = {                                         2,
    2,    3,    2,    1,    1,    7,    9,    6,    5,    1,
    1,    3,    3,    3,    3,    3,    3,    3,    3,    1,
    1,    1,    4,    1,    1,    1,    1,    5,    3,    3,
    6,    1,    3,    1,    3,    1,    2,    2,    4,    4,
    1,    3,    1,    3,    8,    8,
};
short yydefred[] = {                                      0,
    0,    0,    0,    1,    0,    0,   34,    0,    0,    0,
    0,    4,    5,   10,   11,    0,    0,    0,    0,    0,
    0,    0,    0,    2,    3,    0,    0,   32,    0,    0,
    0,   22,    0,    0,    0,    0,   20,   21,    0,    0,
   44,    0,    0,    0,   30,   41,    0,   33,    0,    0,
    0,   24,   25,   26,   27,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   39,    0,    0,    0,   36,
   40,    0,    0,    0,   19,    0,    0,    0,    0,    0,
    0,    0,   18,    9,    0,    0,   38,   37,    0,    0,
   42,    0,   23,    0,    0,    0,    8,    0,   31,   35,
   29,    0,    0,    6,    0,    0,    0,    0,   46,   45,
   28,    7,
};
short yydgoto[] = {                                       2,
    4,   11,   12,   13,   35,   36,   14,   15,   62,   37,
   38,   74,   17,   18,   69,   70,   47,
};
short yysindex[] = {                                   -256,
 -263,    0, -197,    0, -238, -235,    0, -234,   -8, -213,
 -222,    0,    0,    0,    0, -219, -209,  -39, -248, -248,
 -248, -204, -192,    0,    0, -248,  -40,    0,  -43, -183,
 -182,    0,  -33, -248, -196, -136,    0,    0, -153, -191,
    0, -190, -170, -252,    0,    0,  -42,    0, -248, -149,
 -263,    0,    0,    0,    0, -248, -248, -248, -248, -248,
 -248, -243, -177, -174, -263,    0, -182, -182,  -36,    0,
    0, -155, -245,  -35,    0, -228, -166, -166, -166, -166,
 -166, -166,    0,    0, -263, -168,    0,    0, -263, -252,
    0, -182,    0, -248, -263, -172,    0, -231,    0,    0,
    0, -175, -148,    0, -167, -157, -182, -160,    0,    0,
    0,    0,
};
short yyrindex[] = {                                      0,
    0,    0,    0,    0,    0,    0,    0,    0,  -44,    0,
    0,    0,    0,    0,    0,  -37,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -41,    0,    0,    0,
    0,    0, -199,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -154, -152, -151, -150,
 -144, -137,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
short yygindex[] = {                                      0,
  -28,    0,  116,    0,  125,   -5,    0,    0,    0,   85,
    1,    0,    4,  131,    0,   59,    0,
};
#define YYTABLESIZE 248
short yytable[] = {                                      43,
   31,   72,   43,   16,   31,   22,   32,   90,   94,    7,
    1,   16,   22,   23,   39,   67,   32,   28,   33,    3,
   43,   32,   76,   33,   52,   53,   54,   55,   50,   95,
   96,   48,   34,  105,    5,    9,   86,   22,    6,    7,
    8,   92,   19,   73,    9,   20,   21,   68,    7,   26,
   77,   78,   79,   80,   81,   82,   97,   27,   10,    5,
   99,   24,   41,    6,    7,    8,  103,   87,   88,    9,
   43,   43,   43,   43,   42,   43,   43,   43,   43,   43,
   43,   46,   43,   10,    9,   51,   43,   43,  102,   85,
   64,   65,  101,   68,   52,   53,   54,   55,  106,   52,
   53,   54,   55,   52,   53,   54,   55,  111,   84,   91,
  108,  107,   98,  104,  109,   66,   52,   53,   54,   55,
   52,   53,   54,   55,  110,  112,   25,   13,   63,   15,
   12,   14,   75,   52,   53,   54,   55,   16,   56,   57,
   58,   59,   60,   61,   17,   40,   83,   29,  100,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   43,    0,    0,    0,    0,   43,
    0,    0,    0,    0,   30,    0,   32,   43,    0,    0,
   44,   43,   45,   71,   43,   89,   93,   49,
};
short yycheck[] = {                                      44,
   44,   44,   44,    3,   44,   46,   44,   44,   44,  262,
  267,   11,   46,   10,   20,  268,  265,   17,  267,  283,
   26,  265,   51,  267,  270,  271,  272,  273,   34,  258,
  259,   31,  281,  265,  257,  267,   65,   46,  261,  262,
  263,  287,  281,   49,  267,  281,  281,   44,  262,  269,
   56,   57,   58,   59,   60,   61,   85,  267,  281,  257,
   89,  284,  267,  261,  262,  263,   95,   67,   68,  267,
  270,  271,  272,  273,  267,  275,  276,  277,  278,  279,
  280,  265,  282,  281,  267,  282,  286,  287,   94,  264,
  282,  282,   92,   90,  270,  271,  272,  273,   98,  270,
  271,  272,  273,  270,  271,  272,  273,  107,  286,  265,
  259,  287,  281,  286,  282,  286,  270,  271,  272,  273,
  270,  271,  272,  273,  282,  286,   11,  282,  282,  282,
  282,  282,  282,  270,  271,  272,  273,  282,  275,  276,
  277,  278,  279,  280,  282,   21,   62,   17,   90,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  269,   -1,   -1,   -1,   -1,  274,
   -1,   -1,   -1,   -1,  274,   -1,  274,  282,   -1,   -1,
  281,  286,  286,  286,  286,  282,  282,  281,
};
#define YYFINAL 2
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 287
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,"','",0,"'.'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"IF","ELSE","ENDIF",
"PRINT","RETURN","ULONG","WHILE","DO","CTE","CADENA","ID","CVR","ASIGN","MAS",
"MENOS","AST","BARRA","IGUAL","MAYIG","MENIG","MAYOR","MENOR","IGIG","DIF",
"PARENTESISA","PARENTESISC","LLAVEA","LLAVEC","GUIONB","PUNTOCOMA","FLECHA",
};
char *yyrule[] = {
"$accept : prog",
"prog : ID bloque",
"bloque : LLAVEA sentencias LLAVEC",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : asignaciones",
"sentencia : IF PARENTESISA condicion PARENTESISC bloque ENDIF PUNTOCOMA",
"sentencia : IF PARENTESISA condicion PARENTESISC bloque ELSE bloque ENDIF PUNTOCOMA",
"sentencia : WHILE PARENTESISA condicion PARENTESISC DO bloque",
"sentencia : RETURN PARENTESISA expresiones PARENTESISC PUNTOCOMA",
"sentencia : declaracion",
"sentencia : expresion_lambda",
"condicion : expresiones MAYOR expresiones",
"condicion : expresiones MAYIG expresiones",
"condicion : expresiones MENOR expresiones",
"condicion : expresiones MENIG expresiones",
"condicion : expresiones IGIG expresiones",
"condicion : expresiones DIF expresiones",
"expresiones : expresiones operador termino",
"expresiones : PARENTESISA expresiones PARENTESISC",
"expresiones : termino",
"termino : tipo_id",
"termino : CTE",
"termino : ID PARENTESISA parametros_formales PARENTESISC",
"operador : MAS",
"operador : MENOS",
"operador : AST",
"operador : BARRA",
"parametros_formales : parametros_formales ',' expresiones FLECHA tipo_id",
"parametros_formales : expresiones FLECHA tipo_id",
"declaracion : tipo lista_id PUNTOCOMA",
"declaracion : tipo ID PARENTESISA parametros PARENTESISC bloque",
"lista_id : tipo_id",
"lista_id : lista_id ',' tipo_id",
"tipo : ULONG",
"parametros : parametros ',' parametro",
"parametros : parametro",
"parametro : tipo tipo_id",
"parametro : CVR tipo_id",
"asignaciones : tipo_id ASIGN expresiones PUNTOCOMA",
"asignaciones : lista_id IGUAL lista_cte PUNTOCOMA",
"lista_cte : CTE",
"lista_cte : lista_cte ',' CTE",
"tipo_id : ID",
"tipo_id : ID '.' ID",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC bloque PARENTESISA tipo_id PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC bloque PARENTESISA CTE PARENTESISC",
};
#endif
#ifndef YYSTYPE
typedef int YYSTYPE;
#endif
#define yyclearin (yychar=(-1))
#define yyerrok (yyerrflag=0)
#ifdef YYSTACKSIZE
#ifndef YYMAXDEPTH
#define YYMAXDEPTH YYSTACKSIZE
#endif
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH 500
#endif
#endif
int yydebug;
int yynerrs;
int yyerrflag;
int yychar;
short *yyssp;
YYSTYPE *yyvsp;
YYSTYPE yyval;
YYSTYPE yylval;
short yyss[YYSTACKSIZE];
YYSTYPE yyvs[YYSTACKSIZE];
#define yystacksize YYSTACKSIZE
#line 96 "gramatica.y"

/* CODIGO AUXILIAR */

public void verificar_cantidades (ParserVal lista1, ParserVal lista2){
    ArrayList<ParserVal> l1 = (ArrayList<ParserVal>)lista1.obj;
    ArrayList<ParserVal> l2 = (ArrayList<ParserVal>)lista2.obj;
    if (l1.size() > l2.size() )
        System.out.println("Linea: "+AnalisisLexico.getNroLinea()+" ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
}

void yyerror (String s){}

int yylex () throws IOException{
    AnalisisLexico aLex = new AnalisisLexico("C:\\FACULTAD\\Cuarto\\compiladores\\TPE_Compiladores\\texto.txt");
    int token = aLex.yylex();
    return token;
}
#line 277 "y.tab.c"
#define YYABORT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR goto yyerrlab
int
yyparse()
{
    register int yym, yyn, yystate;
#if YYDEBUG
    register char *yys;
    extern char *getenv();

    if (yys = getenv("YYDEBUG"))
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = (-1);

    yyssp = yyss;
    yyvsp = yyvs;
    *yyssp = yystate = 0;

yyloop:
    if (yyn = yydefred[yystate]) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, reading %d (%s)\n", yystate,
                    yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: state %d, shifting to state %d (%s)\n",
                    yystate, yytable[yyn],yyrule[yyn]);
#endif
        if (yyssp >= yyss + yystacksize - 1)
        {
            goto yyoverflow;
        }
        *++yyssp = yystate = yytable[yyn];
        *++yyvsp = yylval;
        yychar = (-1);
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;
#ifdef lint
    goto yynewerror;
#endif
yynewerror:
    yyerror("syntax error");
#ifdef lint
    goto yyerrlab;
#endif
yyerrlab:
    ++yynerrs;
yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yyssp]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: state %d, error recovery shifting\
 to state %d\n", *yyssp, yytable[yyn]);
#endif
                if (yyssp >= yyss + yystacksize - 1)
                {
                    goto yyoverflow;
                }
                *++yyssp = yystate = yytable[yyn];
                *++yyvsp = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: error recovery discarding state %d\n",
                            *yyssp);
#endif
                if (yyssp <= yyss) goto yyabort;
                --yyssp;
                --yyvsp;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, error recovery discards token %d (%s)\n",
                    yystate, yychar, yys);
        }
#endif
        yychar = (-1);
        goto yyloop;
    }
yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("yydebug: state %d, reducing by rule %d (%s)\n",
                yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    yyval = yyvsp[1-yym];
    switch (yyn)
    {
case 5:
#line 24 "gramatica.y"
{System.out.println("SENTENCIA: asignacion");}
break;
case 6:
#line 25 "gramatica.y"
{System.out.println("SENTENCIA: if");}
break;
case 7:
#line 26 "gramatica.y"
{System.out.println("SENTENCIA:if else");}
break;
case 8:
#line 27 "gramatica.y"
{System.out.println("SENTENCIA:while");}
break;
case 9:
#line 28 "gramatica.y"
{System.out.println("SENTENCIA:return");}
break;
case 10:
#line 29 "gramatica.y"
{System.out.println("SENTENCIA:declaracion");}
break;
case 11:
#line 30 "gramatica.y"
{System.out.println("SENTENCIA:lambda");}
break;
case 32:
#line 65 "gramatica.y"
{ ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(yyvsp[0]); yyval = new ParserVal(arreglo); }
break;
case 33:
#line 66 "gramatica.y"
{ ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) yyvsp[-2].obj; arreglo.add(yyvsp[0]); yyval = new ParserVal(arreglo); }
break;
case 40:
#line 81 "gramatica.y"
{ verificar_cantidades (yyvsp[-3], yyvsp[-1]); }
break;
case 41:
#line 84 "gramatica.y"
{ ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(yyvsp[0]); yyval = new ParserVal(arreglo);}
break;
case 42:
#line 85 "gramatica.y"
{ ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) yyvsp[-2].obj; arreglo.add(yyvsp[0]); yyval = new ParserVal(arreglo);}
break;
#line 465 "y.tab.c"
    }
    yyssp -= yym;
    yystate = *yyssp;
    yyvsp -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: after reduction, shifting from state 0 to\
 state %d\n", YYFINAL);
#endif
        yystate = YYFINAL;
        *++yyssp = YYFINAL;
        *++yyvsp = yyval;
        if (yychar < 0)
        {
            if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("yydebug: state %d, reading %d (%s)\n",
                        YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("yydebug: after reduction, shifting from state %d \
to state %d\n", *yyssp, yystate);
#endif
    if (yyssp >= yyss + yystacksize - 1)
    {
        goto yyoverflow;
    }
    *++yyssp = yystate;
    *++yyvsp = yyval;
    goto yyloop;
yyoverflow:
    yyerror("yacc stack overflow");
yyabort:
    return (1);
yyaccept:
    return (0);
}
