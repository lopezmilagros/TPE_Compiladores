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
    0,    0,    0,    1,    1,    1,    1,    2,    2,    2,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    5,    5,    5,
    5,    5,    5,    5,    7,    7,   12,    7,   10,   10,
   10,   11,   11,   11,   11,    8,    8,    8,    8,    6,
    6,    6,    6,   16,   15,   15,   15,   17,   17,   18,
   18,   18,   18,   18,   18,    4,    4,    4,   19,   19,
   19,   13,   13,   14,   14,    9,    9,
};
final static short yylen[] = {                            2,
    2,    2,    1,    3,    3,    3,    3,    3,    2,    2,
    1,    6,    8,    6,    4,    4,    1,    1,    4,    4,
    4,    6,    6,    6,    8,    7,    8,    6,    5,    6,
    6,    6,    8,    6,    8,    8,    6,    3,    3,    3,
    3,    3,    3,    3,    3,    1,    0,    4,    1,    1,
    4,    1,    1,    1,    1,    2,    2,    6,    6,    3,
    3,    2,    2,    1,    5,    3,    3,    3,    1,    3,
    2,    2,    3,    3,    2,    4,    3,    3,    1,    3,
    2,    1,    3,    1,    2,    8,    8,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    2,    1,    0,    0,    0,
   64,    0,    0,    0,    0,    0,   11,    0,   17,   18,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    7,    5,    0,   10,    9,    0,    0,   62,    0,
    0,   63,    0,    0,    0,    0,    6,    4,   84,    0,
    0,    0,    0,   46,   49,   50,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   83,    0,    8,   79,    0,
   61,    0,   60,    0,    0,    0,    0,   85,    0,    0,
    0,   52,   53,   54,   55,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   21,   19,   20,   15,   16,
    0,    0,    0,    0,    0,    0,   81,    0,    0,    0,
    0,   69,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   47,   45,    0,    0,    0,
    0,    0,    0,   29,    0,    0,    0,   80,   75,    0,
    0,   72,   71,    0,    0,    0,    0,   51,    0,    0,
   24,    0,   22,   48,   23,    0,   34,   32,    0,   12,
    0,   30,   28,   37,    0,   14,    0,   74,   73,   70,
   59,   68,   58,   67,   66,    0,    0,    0,    0,    0,
    0,   26,    0,    0,    0,   27,   25,   35,   36,   33,
   13,   86,   87,   65,
};
final static short yydgoto[] = {                          3,
    6,   15,   16,   17,   52,   18,   53,   19,   20,   54,
   92,  154,   55,   56,  116,   22,  111,  112,   70,
};
final static short yysindex[] = {                      -102,
 -227, -227,    0,   19,   19,    0,    0, -229, -273, -272,
    0, -160, -220, -164, -133, -250,    0, -115,    0,    0,
 -158, -100,  -53,   34,   34,   41,   34,   34,   34, -125,
 -110,    0,    0, -145,    0,    0,  -93,  -66,    0,   34,
  -66,    0,  -75,  -46, -223, -157,    0,    0,    0, -126,
  -41, -249,  -22,    0,    0,    0, -181,  -57,  -38,  -15,
 -114,   43, -157, -179, -215,    0,   -7,    0,    0, -149,
    0,   69,    0,   65,   34,   65,   34,    0, -227, -227,
   34,    0,    0,    0,    0,   34,   34,   34,   34,   34,
   34,  -24,  -45, -193,  -29,    0,    0,    0,    0,    0,
  -44,   21, -227, -252, -227,  -93,    0,  -66,   74,  -62,
    9,    0,   69,   20,   17,   42, -211, -159,   69,   69,
   69,   69,   69,   69,   69,    0,    0,   25,    2,   87,
 -227, -227, -227,    0, -227, -178,   22,    0,    0,  -66,
  -30,    0,    0, -227,   65, -227,   10,    0,   34, -227,
    0, -227,    0,    0,    0, -227,    0,    0, -168,    0,
   52,    0,    0,    0,   19,    0,   61,    0,    0,    0,
    0,    0,    0,    0,    0,   23,   63,   64,   76,   11,
  -26,    0,   62,   66,  -66,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
  252,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -243,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -235, -246, -241,    0,    0,    0,  -87,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   47,    0,    0,    0,    0,    0,    0, -236,
    0, -231,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -228,    0,    0,    0,    0,    0, -203, -191,
 -169, -144,  -85,  -43,  -37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -226,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   14,  342,  103,    0,  309,  160,   -5,    0,    0,  -23,
    0,    0,   -4,  -32,    0,  -12,  273,  205,    0,
};
final static int YYTABLESIZE=350;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         21,
   21,   31,   60,  135,   69,   35,   79,   26,   27,   57,
   21,  136,   82,   39,   56,    7,   42,   46,   21,   78,
   82,   62,   63,   82,   77,   82,   24,   76,    4,   31,
   82,   82,   80,   71,   72,   36,   73,  107,   82,   57,
   39,   42,   82,   13,   56,   82,  150,  151,  103,   78,
   82,   25,   44,   82,   77,    5,   39,   76,   42,   31,
   44,  110,  129,  110,   39,   38,  104,   30,  127,  113,
   95,  115,   39,  138,   93,  119,  101,  165,   44,   44,
  120,  121,  122,  123,  124,  125,   41,  180,   21,    5,
   39,   39,  117,  118,   41,   28,  141,   11,  152,  153,
   94,    5,  102,  139,    5,  143,  128,  130,   13,   13,
   40,   38,   41,   41,    5,   49,  134,   34,  137,   38,
   29,   51,   32,    8,   21,   34,    9,   10,   11,   12,
   41,   41,  110,   13,  184,  168,  170,   38,   38,  106,
   68,   66,  175,  176,  161,  162,  163,   14,  164,  166,
   33,   13,   13,    1,   77,   43,   67,  171,   37,  173,
   21,   30,  183,  177,    2,  178,   44,   99,   82,  179,
   40,   49,  181,   38,   38,   21,   82,   51,   40,   82,
  194,   45,   82,   82,   82,   82,   61,   82,   82,   82,
   82,   82,   82,  142,   82,   82,   40,   40,   82,   82,
   13,   82,   47,    8,   13,   74,    9,   10,   11,   12,
    4,    8,   42,   13,    9,   10,   11,   12,   43,  132,
   42,   13,   75,   78,   96,  169,   43,   14,  131,  190,
   48,  126,  191,   81,   76,   14,   13,    5,   42,   42,
   49,   30,   50,   97,   43,   43,   51,   82,   83,   84,
   85,    3,   86,   87,   88,   89,   90,   91,    8,  156,
  157,    9,   10,   11,   12,  174,   98,    8,   13,  189,
    9,   10,   11,   12,  105,    8,   13,   13,    9,   10,
   11,   12,   14,  155,  133,   13,   82,   83,   84,   85,
  144,   14,   82,   83,   84,   85,   58,  145,   49,   14,
   50,  146,  167,  147,   51,   49,   59,   50,  145,  185,
  182,   51,   82,   83,   84,   85,   49,   49,   49,   49,
  108,  186,  187,  148,  100,   49,   11,   13,   49,  140,
  149,   51,  109,   57,  188,   11,   64,   65,   82,   83,
   84,   85,  158,  192,  159,  160,   23,  193,  114,  172,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          4,
    5,   14,   26,  256,   37,  256,  256,  281,  281,  256,
   15,  264,  256,   18,  256,    2,   21,   22,   23,  256,
  256,   27,   27,  267,  256,  269,  256,  256,  256,  256,
  274,  267,  282,   38,   40,  286,   41,   70,  282,  286,
   45,   46,  286,  267,  286,  289,  258,  259,  264,  286,
  286,  281,  256,  289,  286,  283,   61,  286,   63,  286,
  264,   74,  256,   76,  256,  289,  282,  288,   92,   75,
   57,   77,  264,  106,  256,   81,  256,  256,  282,  283,
   86,   87,   88,   89,   90,   91,  256,  256,   93,  283,
  282,  283,   79,   80,  264,  256,  109,  262,  258,  259,
  282,  283,  282,  108,  283,  110,   93,   94,  267,  267,
  269,  256,  282,  283,  283,  265,  103,   15,  105,  264,
  281,  271,  256,  257,  129,   23,  260,  261,  262,  263,
  289,  289,  145,  267,  167,  140,  141,  282,  283,  289,
  286,  267,  147,  149,  131,  132,  133,  281,  135,  136,
  284,  267,  267,  256,  281,  256,  267,  144,  274,  146,
  165,  288,  167,  150,  267,  152,  267,  282,  256,  156,
  256,  265,  159,  289,  289,  180,  264,  271,  264,  267,
  185,   22,  270,  271,  272,  273,   27,  275,  276,  277,
  278,  279,  280,  256,  282,  283,  282,  283,  286,  287,
  267,  289,  256,  257,  267,  281,  260,  261,  262,  263,
  256,  257,  256,  267,  260,  261,  262,  263,  256,  264,
  264,  267,  269,  265,  282,  256,  264,  281,  258,  256,
  284,  256,  259,  256,  281,  281,  267,  283,  282,  283,
  265,  288,  267,  282,  282,  283,  271,  270,  271,  272,
  273,    0,  275,  276,  277,  278,  279,  280,  257,  258,
  259,  260,  261,  262,  263,  256,  282,  257,  267,  259,
  260,  261,  262,  263,  282,  257,  267,  267,  260,  261,
  262,  263,  281,  259,  264,  267,  270,  271,  272,  273,
  282,  281,  270,  271,  272,  273,  256,  289,  265,  281,
  267,  282,  281,  287,  271,  265,  266,  267,  289,  287,
  259,  271,  270,  271,  272,  273,  270,  271,  272,  273,
  256,  259,  259,  282,  282,  265,  262,  267,  282,  256,
  289,  271,  268,   25,  259,  262,   28,   29,  270,  271,
  272,  273,  256,  282,  258,  259,    5,  282,   76,  145,
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
"prog : error",
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
"sentencia : IF PARENTESISA condicion bloque ELSE bloque ENDIF",
"sentencia : IF error condicion error bloque ELSE bloque ENDIF",
"sentencia : WHILE error condicion PARENTESISC DO bloque",
"sentencia : WHILE PARENTESISA condicion DO bloque",
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
"$$1 :",
"expresiones : expresiones operador error $$1",
"termino : tipo_id",
"termino : tipo_cte",
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
"lista_cte : tipo_cte",
"lista_cte : lista_cte COMA tipo_cte",
"lista_cte : lista_cte tipo_cte",
"tipo_id : ID",
"tipo_id : ID PUNTO ID",
"tipo_cte : CTE",
"tipo_cte : MENOS CTE",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC bloque PARENTESISA tipo_id PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC bloque PARENTESISA tipo_cte PARENTESISC",
};

//#line 140 "gramatica.y"

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

int yylex () throws IOException{
    int token = aLex.yylex();
    yylval = aLex.getYylval();
    return token;
}
//#line 448 "Parser.java"
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
int yyparse()
{
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
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Error antes del bloque");}
break;
case 3:
//#line 17 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO");}
break;
case 5:
//#line 21 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitador");}
break;
case 6:
//#line 22 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitador");}
break;
case 7:
//#line 23 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores");}
break;
case 10:
//#line 28 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 12:
//#line 32 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: if");}
break;
case 13:
//#line 33 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:if else");}
break;
case 14:
//#line 34 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:while");}
break;
case 15:
//#line 35 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
break;
case 16:
//#line 36 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
break;
case 17:
//#line 37 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:declaracion");}
break;
case 18:
//#line 38 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:lambda");}
break;
case 19:
//#line 39 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
break;
case 20:
//#line 40 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
break;
case 21:
//#line 41 "gramatica.y"
{System.out.println("LINEA: " + aLex.getNroLinea() + " ERROR SINTÁCTICO: argumento inválido en print");}
break;
case 22:
//#line 42 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de apertura de la condicion");}
break;
case 23:
//#line 43 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de cierre de la condicion");}
break;
case 24:
//#line 44 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: faltan parentesis de la condicion");}
break;
case 25:
//#line 45 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de apertura de la condicion");}
break;
case 26:
//#line 46 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de cierre de la condicion");}
break;
case 27:
//#line 47 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: faltan parentesis de la condicion");}
break;
case 28:
//#line 48 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de apertura de la condicion");}
break;
case 29:
//#line 49 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de cierre de la condicion");}
break;
case 30:
//#line 50 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: faltan parentesis de la condicion");}
break;
case 31:
//#line 51 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta cuerpo en la iteracion");}
break;
case 32:
//#line 52 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta 'endif'");}
break;
case 33:
//#line 53 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta 'endif'");}
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
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta contenido en bloque");}
break;
case 37:
//#line 57 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta 'do' en iteracion");}
break;
case 44:
//#line 67 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta comparador en expresion");}
break;
case 47:
//#line 72 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta operando en expresion");}
break;
case 48:
//#line 72 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta operando en expresion");}
break;
case 59:
//#line 89 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
break;
case 60:
//#line 92 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(2)); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 61:
//#line 93 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 62:
//#line 94 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 63:
//#line 95 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 67:
//#line 103 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta especificacion del parametro formal");}
break;
case 72:
//#line 112 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta nombre del parametro formal");}
break;
case 73:
//#line 113 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta nombre del parametro formal");}
break;
case 74:
//#line 114 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta tipo del parametro formal");}
break;
case 75:
//#line 115 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta tipo del parametro formal");}
break;
case 76:
//#line 118 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion");}
break;
case 77:
//#line 119 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion");}
break;
case 78:
//#line 120 "gramatica.y"
{verificar_cantidades(val_peek(2), val_peek(0)); System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple");}
break;
case 79:
//#line 123 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 80:
//#line 124 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 81:
//#line 125 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
break;
case 84:
//#line 132 "gramatica.y"
{aLex.agregarATablaDeSimbolos(val_peek(0).sval);}
break;
case 85:
//#line 133 "gramatica.y"
{String cte = "-" + val_peek(0).sval; aLex.agregarATablaDeSimbolos(cte);}
break;
//#line 809 "Parser.java"
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
public void run()
{
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
