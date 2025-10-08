//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
    package AnalizadorSintactico;
    import java.io.*;
    import java.util.ArrayList;
    import AnalizadorLexico.*;

//#line 23 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short ELSE=258;
public final static short ENDIF=259;
public final static short PRINT=260;
public final static short RETURN=261;
public final static short ULONG=262;
public final static short WHILE=263;
public final static short DO=264;
public final static short CTE=265;
public final static short CADENA=266;
public final static short ID=267;
public final static short CVR=268;
public final static short ASIGN=269;
public final static short MAS=270;
public final static short MENOS=271;
public final static short AST=272;
public final static short BARRA=273;
public final static short IGUAL=274;
public final static short MAYIG=275;
public final static short MENIG=276;
public final static short MAYOR=277;
public final static short MENOR=278;
public final static short IGIG=279;
public final static short DIF=280;
public final static short PARENTESISA=281;
public final static short PARENTESISC=282;
public final static short LLAVEA=283;
public final static short LLAVEC=284;
public final static short GUIONB=285;
public final static short PUNTOCOMA=286;
public final static short FLECHA=287;
public final static short PUNTO=288;
public final static short COMA=289;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    1,    2,    2,    2,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    5,    5,    5,    5,
    5,    5,    5,    7,    7,    7,   10,   10,   10,   11,
   11,   11,   11,    8,    8,    8,    8,    6,    6,    6,
    6,   14,   13,   13,   13,   15,   15,   16,   16,   16,
   16,   16,   16,    4,    4,    4,   17,   17,   17,   12,
   12,    9,    9,
};
final static short yylen[] = {                            2,
    2,    2,    3,    3,    3,    3,    3,    2,    2,    1,
    6,    8,    6,    4,    4,    1,    1,    4,    4,    4,
    6,    6,    6,    8,    8,    8,    6,    6,    6,    6,
    6,    8,    6,    8,    8,    6,    3,    3,    3,    3,
    3,    3,    3,    3,    1,    3,    1,    1,    4,    1,
    1,    1,    1,    2,    2,    6,    6,    3,    3,    2,
    2,    1,    5,    3,    3,    3,    1,    3,    2,    2,
    3,    3,    2,    4,    3,    3,    1,    3,    2,    1,
    3,    8,    8,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    2,    1,    0,    0,    0,
   62,    0,    0,    0,    0,    0,   10,    0,   16,   17,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    6,    4,    0,    9,    8,    0,    0,   60,    0,
    0,   61,    0,    0,    0,    0,    5,    3,   48,    0,
    0,    0,   45,   47,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   81,    0,    7,   77,    0,   59,    0,
   58,    0,    0,    0,    0,    0,    0,    0,   50,   51,
   52,   53,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   20,   18,   19,   14,   15,    0,    0,    0,    0,
    0,   79,    0,    0,    0,    0,    0,   67,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   46,   44,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   78,   73,    0,    0,   70,   69,    0,    0,
    0,    0,   49,    0,    0,   23,    0,   21,    0,   22,
    0,   33,   31,    0,   11,   29,   27,   28,   36,    0,
   13,    0,   72,   71,   68,   57,   66,   56,   65,   64,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   26,   24,   25,   34,   35,   32,   12,   83,   82,   63,
};
final static short yydgoto[] = {                          3,
    6,   15,   16,   17,   51,   18,   52,   19,   20,   53,
   89,   54,  112,   22,  107,  108,   68,
};
final static short yysindex[] = {                      -172,
 -227, -227,    0,  -35,  -35,    0,    0, -249, -269, -265,
    0, -189, -184, -181,  -94, -251,    0, -103,    0,    0,
 -242, -169,  -82,  -83,  -83,    5,  -83,  -83,  -83, -108,
  -84,    0,    0, -157,    0,    0,  -65,  -69,    0,  -83,
  -69,    0,  -74, -127, -188, -148,    0,    0,    0, -138,
 -252,  -67,    0,    0, -193,  -51,  -38,  -20,  -46,  -18,
 -148, -191, -179,    0,  -15,    0,    0, -221,    0,   12,
    0,  -33,  -83,  -33,  -83, -227, -227,  -83,    0,    0,
    0,    0,  -83,  -83,  -83,  -83,  -83,  -83,   10, -227,
 -208,    0,    0,    0,    0,    0,  -17,   14,   39,  -19,
 -227,    0,   27,  -69, -116, -168,   -9,    0,   12,   -3,
  -31,   -1,   38,   40,   12,   12,   12,   12,   12,   12,
   12,    0,    0,   42,  -66,   31, -227, -227, -227, -227,
 -207,   21,    0,    0,  -69,  -79,    0,    0, -227,  -33,
 -227,    7,    0,  -83, -227,    0, -227,    0, -227,    0,
 -227,    0,    0, -205,    0,    0,    0,    0,    0,  -35,
    0,   28,    0,    0,    0,    0,    0,    0,    0,    0,
  -22,   45,   46,   47,   48,  -43,   35,   26,   29,  -69,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -236,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -246, -250, -247,    0,    0,    0, -162,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -13,    0,    0,    0,    0,    0,    0, -241,    0, -234,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -232,    0,
    0,    0,    0,    0, -176, -170, -134, -133, -130, -117,
 -112,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -228,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    6,  304,  253,    0,  205,   63,  -14,    0,    0,  -23,
    0,   -4,    0,  -12,  236,  172,    0,
};
final static int YYTABLESIZE=312;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         21,
   21,   31,   58,   76,   35,   55,   24,    7,   54,   80,
   21,   26,   60,   39,   76,   27,   42,   46,   21,   80,
   80,   75,   61,   74,   13,   70,   40,   30,    4,   77,
   80,   25,   80,   69,   36,   55,   71,   80,   54,   80,
   39,   42,   80,  102,   76,   80,   41,  125,  160,   80,
  176,   75,   80,   74,   39,    5,   42,   30,  109,  106,
  111,  106,   90,  115,   97,  123,   28,  103,  116,  117,
  118,  119,  120,  121,    5,    5,   99,    5,   13,   43,
   11,  113,  114,    1,   45,   38,   43,  137,   91,   59,
   98,   29,  136,   80,    2,  124,  126,   44,   13,  134,
   38,  138,  100,   30,   80,   43,  132,   80,   80,   80,
   80,   38,   80,   80,   80,   80,   80,   80,   13,   80,
   21,   40,   37,   80,   80,   39,   80,  106,   66,  171,
  163,  165,  156,  157,  158,  159,  161,  170,   41,  135,
   41,   73,   75,   42,  166,   11,  168,   40,   37,   30,
  172,   39,  173,   74,  174,   21,  175,  179,   64,  177,
   30,   32,    8,   13,   41,    9,   10,   11,   12,   42,
   37,   21,   13,   47,    8,  190,  164,    9,   10,   11,
   12,   49,   65,   50,   13,   38,   14,   13,   78,   33,
    8,  151,  152,    9,   10,   11,   12,   13,   14,   67,
   13,   48,   79,   80,   81,   82,   72,   83,   84,   85,
   86,   87,   88,    8,   14,  185,    9,   10,   11,   12,
   13,    8,  104,   13,    9,   10,   11,   12,   11,   55,
   92,   13,   62,   63,  105,   95,  130,   14,   79,   80,
   81,   82,   38,   93,  131,   14,  127,   79,   80,   81,
   82,   79,   80,   81,   82,  142,   47,   47,   47,   47,
   56,   94,  169,   96,  180,  122,  101,   34,   47,   49,
   57,   50,  139,   13,   49,   34,   50,  128,  141,  140,
  143,   79,   80,   81,   82,  140,  153,  144,  154,  155,
  186,  133,  178,  187,   13,  145,  146,  147,  148,  149,
  150,  162,  129,  181,  182,  183,  184,  188,   23,  110,
  189,  167,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
    5,   14,   26,  256,  256,  256,  256,    2,  256,  256,
   15,  281,   27,   18,  256,  281,   21,   22,   23,  256,
  267,  256,   27,  256,  267,   40,  269,  256,  256,  282,
  267,  281,  269,   38,  286,  286,   41,  274,  286,  286,
   45,   46,  289,  265,  286,  282,  289,  256,  256,  286,
  256,  286,  289,  286,   59,  283,   61,  286,   73,   72,
   75,   74,  256,   78,  256,   89,  256,  289,   83,   84,
   85,   86,   87,   88,  283,  283,  256,  283,  267,  256,
  262,   76,   77,  256,   22,  256,  256,  256,  282,   27,
  282,  281,  105,  256,  267,   90,   91,  267,  267,  104,
  289,  106,  282,  288,  267,  282,  101,  270,  271,  272,
  273,  282,  275,  276,  277,  278,  279,  280,  267,  282,
  125,  256,  256,  286,  287,  256,  289,  140,  286,  144,
  135,  136,  127,  128,  129,  130,  131,  142,  256,  256,
  289,  269,  281,  256,  139,  262,  141,  282,  282,  288,
  145,  282,  147,  281,  149,  160,  151,  162,  267,  154,
  288,  256,  257,  267,  282,  260,  261,  262,  263,  282,
  274,  176,  267,  256,  257,  180,  256,  260,  261,  262,
  263,  265,  267,  267,  267,  289,  281,  267,  256,  284,
  257,  258,  259,  260,  261,  262,  263,  267,  281,  265,
  267,  284,  270,  271,  272,  273,  281,  275,  276,  277,
  278,  279,  280,  257,  281,  259,  260,  261,  262,  263,
  267,  257,  256,  267,  260,  261,  262,  263,  262,   25,
  282,  267,   28,   29,  268,  282,  256,  281,  270,  271,
  272,  273,  289,  282,  264,  281,  264,  270,  271,  272,
  273,  270,  271,  272,  273,  287,  270,  271,  272,  273,
  256,  282,  256,  282,  287,  256,  282,   15,  282,  265,
  266,  267,  282,  267,  265,   23,  267,  264,  282,  289,
  282,  270,  271,  272,  273,  289,  256,  289,  258,  259,
  256,  265,  265,  259,  267,  258,  259,  258,  259,  258,
  259,  281,  264,  259,  259,  259,  259,  282,    5,   74,
  282,  140,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=289;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"IF","ELSE","ENDIF","PRINT","RETURN","ULONG","WHILE","DO","CTE",
"CADENA","ID","CVR","ASIGN","MAS","MENOS","AST","BARRA","IGUAL","MAYIG","MENIG",
"MAYOR","MENOR","IGIG","DIF","PARENTESISA","PARENTESISC","LLAVEA","LLAVEC",
"GUIONB","PUNTOCOMA","FLECHA","PUNTO","COMA",
};
final static String yyrule[] = {
"$accept : prog",
"prog : ID bloque",
"prog : error bloque",
"bloque : LLAVEA sentencias LLAVEC",
"bloque : error sentencias LLAVEC",
"bloque : LLAVEA sentencias error",
"bloque : error sentencias error",
"sentencias : sentencias sentencia PUNTOCOMA",
"sentencias : sentencia PUNTOCOMA",
"sentencias : sentencia error",
"sentencia : asignaciones",
"sentencia : IF PARENTESISA condicion PARENTESISC bloque ENDIF",
"sentencia : IF PARENTESISA condicion PARENTESISC bloque ELSE bloque ENDIF",
"sentencia : WHILE PARENTESISA condicion PARENTESISC DO bloque",
"sentencia : RETURN PARENTESISA lista_id PARENTESISC",
"sentencia : RETURN PARENTESISA expresiones PARENTESISC",
"sentencia : declaracion",
"sentencia : expresion_lambda",
"sentencia : PRINT PARENTESISA CADENA PARENTESISC",
"sentencia : PRINT PARENTESISA termino PARENTESISC",
"sentencia : PRINT PARENTESISA error PARENTESISC",
"sentencia : IF error condicion PARENTESISC bloque ENDIF",
"sentencia : IF PARENTESISA condicion error bloque ENDIF",
"sentencia : IF error condicion error bloque ENDIF",
"sentencia : IF error condicion PARENTESISC bloque ELSE bloque ENDIF",
"sentencia : IF PARENTESISA condicion error bloque ELSE bloque ENDIF",
"sentencia : IF error condicion error bloque ELSE bloque ENDIF",
"sentencia : WHILE error condicion PARENTESISC DO bloque",
"sentencia : WHILE PARENTESISA condicion error DO bloque",
"sentencia : WHILE error condicion error DO bloque",
"sentencia : WHILE PARENTESISA condicion PARENTESISC DO error",
"sentencia : IF PARENTESISA condicion PARENTESISC bloque error",
"sentencia : IF PARENTESISA condicion PARENTESISC bloque ELSE bloque error",
"sentencia : IF PARENTESISA condicion PARENTESISC error ENDIF",
"sentencia : IF PARENTESISA condicion PARENTESISC error ELSE bloque ENDIF",
"sentencia : IF PARENTESISA condicion PARENTESISC bloque ELSE error ENDIF",
"sentencia : WHILE PARENTESISA condicion PARENTESISC error bloque",
"condicion : expresiones MAYOR expresiones",
"condicion : expresiones MAYIG expresiones",
"condicion : expresiones MENOR expresiones",
"condicion : expresiones MENIG expresiones",
"condicion : expresiones IGIG expresiones",
"condicion : expresiones DIF expresiones",
"condicion : expresiones error expresiones",
"expresiones : expresiones operador termino",
"expresiones : termino",
"expresiones : expresiones operador error",
"termino : tipo_id",
"termino : CTE",
"termino : ID PARENTESISA parametros_reales PARENTESISC",
"operador : MAS",
"operador : MENOS",
"operador : AST",
"operador : BARRA",
"declaracion : tipo tipo_id",
"declaracion : tipo lista_id",
"declaracion : tipo ID PARENTESISA parametros_formales PARENTESISC bloque",
"declaracion : tipo error PARENTESISA parametros_formales PARENTESISC bloque",
"lista_id : tipo_id COMA tipo_id",
"lista_id : lista_id COMA tipo_id",
"lista_id : lista_id tipo_id",
"lista_id : tipo_id tipo_id",
"tipo : ULONG",
"parametros_reales : parametros_reales COMA expresiones FLECHA tipo_id",
"parametros_reales : expresiones FLECHA tipo_id",
"parametros_reales : expresiones FLECHA error",
"parametros_formales : parametros_formales COMA parametro",
"parametros_formales : parametro",
"parametro : CVR tipo tipo_id",
"parametro : tipo tipo_id",
"parametro : tipo error",
"parametro : CVR tipo error",
"parametro : CVR error tipo_id",
"parametro : error tipo_id",
"asignaciones : tipo ID ASIGN expresiones",
"asignaciones : tipo_id ASIGN expresiones",
"asignaciones : lista_id IGUAL lista_cte",
"lista_cte : CTE",
"lista_cte : lista_cte COMA CTE",
"lista_cte : lista_cte CTE",
"tipo_id : ID",
"tipo_id : ID PUNTO ID",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC bloque PARENTESISA tipo_id PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC bloque PARENTESISA CTE PARENTESISC",
};

//#line 135 "gramatica.y"

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
    System.out.println(s);
}

int yylex () throws IOException{
   return aLex.yylex();

}
//#line 432 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse() throws IOException {
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 16 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de programa");}
break;
case 4:
//#line 20 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitador");}
break;
case 5:
//#line 21 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitador");}
break;
case 6:
//#line 22 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores");}
break;
case 9:
//#line 27 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 11:
//#line 31 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: if");}
break;
case 12:
//#line 32 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:if else");}
break;
case 13:
//#line 33 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:while");}
break;
case 14:
//#line 34 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
break;
case 15:
//#line 35 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
break;
case 16:
//#line 36 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:declaracion");}
break;
case 17:
//#line 37 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:lambda");}
break;
case 18:
//#line 38 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
break;
case 19:
//#line 39 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
break;
case 20:
//#line 40 "gramatica.y"
{System.out.println("LINEA: " + aLex.getNroLinea() + " ERROR SINTÁCTICO: argumento inválido en print");}
break;
case 21:
//#line 41 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de apertura de la condicion");}
break;
case 22:
//#line 42 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de cierre de la condicion");}
break;
case 23:
//#line 43 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: faltan parentesis de la condicion");}
break;
case 24:
//#line 44 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de apertura de la condicion");}
break;
case 25:
//#line 45 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de cierre de la condicion");}
break;
case 26:
//#line 46 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: faltan parentesis de la condicion");}
break;
case 27:
//#line 47 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de apertura de la condicion");}
break;
case 28:
//#line 48 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de cierre de la condicion");}
break;
case 29:
//#line 49 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: faltan parentesis de la condicion");}
break;
case 30:
//#line 50 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta cuerpo en la iteracion");}
break;
case 31:
//#line 51 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta 'endif'");}
break;
case 32:
//#line 52 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta 'endif'");}
break;
case 33:
//#line 53 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta contenido en bloque");}
break;
case 34:
//#line 54 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta contenido en bloque");}
break;
case 35:
//#line 55 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta contenido en bloque");}
break;
case 36:
//#line 56 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta 'do' en iteracion");}
break;
case 43:
//#line 66 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta comparador en expresion");}
break;
case 46:
//#line 71 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta operando en expresion");}
break;
case 57:
//#line 88 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
break;
case 58:
//#line 91 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(2)); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 59:
//#line 92 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 60:
//#line 93 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 61:
//#line 94 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 65:
//#line 102 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta especificacion del parametro formal");}
break;
case 70:
//#line 111 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta nombre del parametro formal");}
break;
case 71:
//#line 112 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta nombre del parametro formal");}
break;
case 72:
//#line 113 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta tipo del parametro formal");}
break;
case 73:
//#line 114 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta tipo del parametro formal");}
break;
case 74:
//#line 117 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion");}
break;
case 75:
//#line 118 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion");}
break;
case 76:
//#line 119 "gramatica.y"
{verificar_cantidades (val_peek(2), val_peek(0)); System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple");}
break;
case 77:
//#line 122 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 78:
//#line 123 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 79:
//#line 124 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
break;
//#line 777 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run() throws IOException {
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
