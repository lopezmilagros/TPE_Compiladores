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
    0,    0,    0,    0,    0,    1,    1,    1,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    4,    4,    4,    4,    4,
    4,    4,   10,   10,   10,   10,   10,   10,   10,   10,
   10,   10,   10,   10,   10,    6,    6,   12,    6,    9,
    9,    9,   11,   11,   11,   11,    7,    7,    7,    7,
    5,    5,    5,    5,   16,   15,   15,   15,   17,   17,
   18,   18,   18,   18,   18,   18,    3,    3,    3,   19,
   19,   19,   13,   13,   14,   14,    8,    8,
};
final static short yylen[] = {                            2,
    4,    3,    3,    2,    1,    3,    2,    2,    1,    8,
   12,    8,    4,    4,    1,    1,    4,    4,    4,    8,
    7,    8,   12,   11,   12,    8,    7,    8,    6,    8,
   12,    6,   10,   10,    8,    3,    3,    3,    3,    3,
    3,    1,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    1,    3,    1,    0,    4,    1,
    1,    4,    1,    1,    1,    1,    2,    2,    8,    8,
    3,    3,    2,    2,    1,    5,    3,    3,    3,    1,
    3,    2,    2,    3,    3,    2,    5,    4,    4,    1,
    3,    2,    1,    3,    1,    2,   10,   10,
};
final static short yydefred[] = {                         0,
    5,    0,    0,    0,    0,    0,   75,    0,    0,    0,
    0,    0,    0,    9,    0,   15,   16,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    0,
    8,    7,    0,    0,   73,    0,    0,   74,    0,    0,
    0,    0,   95,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   57,   42,   60,   61,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   94,    0,    1,    6,
   90,    0,   72,    0,   71,    0,    0,    0,    0,   96,
    0,    0,    0,    0,    0,    0,    0,    0,   63,   64,
   65,   66,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   19,   17,   18,   13,   14,    0,    0,    0,    0,
    0,   89,    0,   92,   88,    0,    0,    0,    0,   80,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   58,   56,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   91,   86,    0,    0,   83,   82,    0,
    0,   87,    0,    0,   62,    0,    0,    0,   59,    0,
   32,    0,    0,    0,    0,    0,    0,   29,    0,    0,
   85,   84,   81,    0,   79,    0,   78,   77,    0,    0,
    0,    0,    0,    0,   21,    0,    0,   27,    0,    0,
    0,    0,    0,    0,    0,   22,    0,   20,    0,   30,
    0,   10,    0,   28,   26,   35,   12,    0,   70,   69,
   76,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   33,   34,    0,    0,   97,   98,    0,    0,    0,
   24,   25,   23,   31,   11,
};
final static short yydgoto[] = {                          3,
   12,   13,   14,   52,   15,   53,   16,   17,   54,   55,
   99,  159,   18,   57,  124,   19,  119,  120,   72,
};
final static short yysindex[] = {                       -99,
    0,  300,    0, -252, -269, -228,    0, -198, -266, -178,
  325, -144, -245,    0, -202,    0,    0, -201,  -66,  345,
  345,  361, -166,  345,  345, -196, -181, -119,    0, -154,
    0,    0, -143, -103,    0, -166, -103,    0,  -89, -263,
 -194, -153,    0, -271,  -63, -166, -166, -166, -166, -166,
 -166, -230,  363,    0,    0,    0,    0, -213,  -78,  -76,
  -48, -116,   42, -153, -200, -259,    0,  -46,    0,    0,
    0,  -86,    0,  328,    0,  -33, -166,  -33, -166,    0,
   75,   75,   75,   75,   75,   75,  -67,  -64,    0,    0,
    0,    0, -166, -166, -166, -166, -166, -166, -132, -253,
  325,    0,    0,    0,    0,    0,   -8,   16,   -6, -249,
    3,    0, -143,    0,    0, -103, -101,  -42, -193,    0,
  332, -191,  306, -126,  325,  325,   75,   75,   75,   75,
   75,   75,    0,    0, -155,  325,  -30,    8,   10,  325,
   23, -235,  325,    0,    0, -103,  -17,    0,    0,   43,
  -33,    0,   49,   14,    0, -166,   27,   35,    0,   57,
    0,   60,  -11,  325,  325,   68,  325,    0,  325,   76,
    0,    0,    0,  325,    0,  325,    0,    0,  324,   41,
   92,  325,   51,   59,    0,  101,  109,    0,  117,  142,
  -28,  150,  158, -103,   84,    0,   90,    0,  183,    0,
 -232,    0,  325,    0,    0,    0,    0,  -10,    0,    0,
    0,  325,  325,  100,  116,  325,  191,   36,   52,  199,
  224,    0,    0,  232,  122,    0,    0,  124,  127, -129,
    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0, -189,    0,
    0,  387,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  388,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -247,
 -243, -242,    0,   -4,    0,    0,    0,    0,    0,    0,
    0,    0, -237,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   83,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -162, -130,  -65,  -18,  214,  218,    0,    0,    0,    0,
    0,    0,  242,  246,  248,  251,  253,  255,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  258,  262,  283,  286,
  290,  292,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   46,  -12,    0,  280,  165,  147,    0,    0,  -20,    0,
    0,    0,   13,  -32,    0,   -2,  311,  240,    0,
};
final static int YYTABLESIZE=643;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         30,
   71,   61,  135,   20,  109,   77,  141,   27,   93,   79,
   31,   22,   68,   67,  142,   30,   26,   78,   55,   93,
  168,   26,  110,  215,   26,   87,   55,   35,   21,  136,
   38,   42,   56,   56,   56,   64,   56,   56,   93,  114,
   32,   93,   68,   67,   55,   55,   73,  169,   56,   75,
  216,   88,   23,   35,   38,  107,   28,   24,   56,   56,
   56,   56,   56,   56,    9,    9,   93,   36,  100,  101,
   67,   33,    9,  118,   35,  118,   38,   93,  134,   93,
  144,  108,   25,    7,   93,   68,   34,   37,  150,   56,
  153,   56,   93,   50,   34,  151,   93,  151,   43,   93,
   44,   50,  160,  161,   45,   56,   56,   56,   56,   56,
   56,   56,    4,    9,  147,    5,    6,    7,    8,   50,
   50,   43,    9,  133,   30,   52,  234,   45,  145,  235,
  149,   70,   43,   52,   44,   37,   10,    4,   45,   29,
    5,    6,    7,    8,   30,   30,  137,    9,  118,   30,
    9,   52,   52,   30,  146,  155,    1,   30,  171,  173,
    7,   10,  156,    9,   69,  105,  178,    2,   56,   63,
  157,  158,   34,   30,   30,  219,   30,   30,   43,   30,
   30,  162,   74,   41,   45,  166,   30,   62,  170,   39,
   49,   76,   81,   82,   83,   84,   85,   86,   49,  112,
   40,   80,  113,  102,   30,  103,  211,   30,   30,  186,
  187,   30,  189,  148,  190,  125,   49,   49,  126,  192,
  218,  193,  116,  121,    9,  123,    4,  199,    7,    5,
    6,    7,    8,  104,  117,  111,    9,   51,  172,  127,
  128,  129,  130,  131,  132,   51,  184,  185,  217,    9,
   10,   93,  208,  163,   43,  138,    9,  220,  221,   93,
   45,  224,   93,   51,   51,   93,   93,   93,   93,  177,
   93,   93,   93,   93,   93,   93,  140,   93,   93,  139,
    9,   93,   93,    4,   93,  143,    5,    6,    7,    8,
  164,    4,  165,    9,    5,    6,    7,    8,  195,  196,
   58,    9,  179,   65,   66,  167,  200,   10,  201,  202,
  180,   89,   90,   91,   92,   10,    4,  226,  181,    5,
    6,    7,    8,  106,    4,  174,    9,    5,    6,    7,
    8,  176,    4,  227,    9,    5,    6,    7,    8,  182,
   10,  203,    9,  183,   89,   90,   91,   92,   10,  197,
  198,  188,   60,   60,   60,   60,   10,    4,  222,  191,
    5,    6,    7,    8,   60,    4,  212,    9,    5,    6,
    7,    8,  213,    4,  223,    9,    5,    6,    7,    8,
  231,   10,  232,    9,  204,  233,    4,    3,  122,   10,
  175,    0,  205,    0,    0,    0,    0,   10,    4,    0,
  206,    5,    6,    7,    8,    0,    4,    0,    9,    5,
    6,    7,    8,    0,    4,    0,    9,    5,    6,    7,
    8,    0,   10,    0,    9,  207,    0,    0,    0,    0,
   10,    0,    0,  209,    0,    0,    0,    0,   10,    4,
    0,  210,    5,    6,    7,    8,    0,    4,    0,    9,
    5,    6,    7,    8,    0,    4,    0,    9,    5,    6,
    7,    8,    0,   10,    0,    9,  214,    0,    0,   53,
    0,   10,    0,   54,  225,    0,    0,   53,    0,   10,
    4,   54,  228,    5,    6,    7,    8,    0,    4,    0,
    9,    5,    6,    7,    8,   53,   53,   44,    9,   54,
   54,   46,    0,   43,   10,   44,   45,  229,   47,   46,
   48,   43,   10,   37,   45,  230,   47,   39,   48,    0,
    0,   37,    0,   44,   44,   39,    0,   46,   46,   43,
   43,    0,   45,   45,   47,   47,   48,   48,   36,   37,
   37,   38,    0,   39,   39,   40,   36,   41,    0,   38,
    0,    0,    0,   40,    0,   41,    4,    0,    0,    5,
    6,    7,    8,    0,   36,   36,    9,   38,   38,    0,
    0,   40,   40,   41,   41,   89,   90,   91,   92,    0,
   10,    4,   11,    0,    5,    6,    7,    8,    0,    0,
    0,    9,  154,   89,   90,   91,   92,   89,   90,   91,
   92,   89,   90,   91,   92,   10,    0,    0,    0,   43,
  194,   44,    0,  115,    0,   45,   59,  152,    0,   46,
   47,   48,   49,   50,   51,   43,   60,   44,    0,    0,
    0,   45,   89,   90,   91,   92,    0,   93,   94,   95,
   96,   97,   98,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         12,
   33,   22,  256,  256,  264,  269,  256,   10,  256,  281,
  256,  281,  256,  256,  264,   28,  288,  281,  256,  267,
  256,  288,  282,  256,  288,  256,  264,   15,  281,  283,
   18,   19,   20,   21,   22,   23,   24,   25,  286,   72,
  286,  289,  286,  286,  282,  283,   34,  283,   36,   37,
  283,  282,  281,   41,   42,  256,   11,  256,   46,   47,
   48,   49,   50,   51,  267,  267,  256,  269,  282,  283,
  267,  274,  267,   76,   62,   78,   64,  267,   99,  269,
  113,  282,  281,  262,  274,  267,  289,  289,  282,   77,
  282,   79,  282,  256,  289,  289,  286,  289,  265,  289,
  267,  264,  258,  259,  271,   93,   94,   95,   96,   97,
   98,   99,  257,  267,  117,  260,  261,  262,  263,  282,
  283,  265,  267,  256,  137,  256,  256,  271,  116,  259,
  118,  286,  265,  264,  267,  289,  281,  257,  271,  284,
  260,  261,  262,  263,  157,  158,  101,  267,  151,  162,
  267,  282,  283,  166,  256,  282,  256,  170,  146,  147,
  262,  281,  289,  267,  284,  282,  154,  267,  156,   23,
  125,  126,  289,  186,  187,  208,  189,  190,  265,  192,
  193,  136,   36,   19,  271,  140,  199,   23,  143,  256,
  256,  281,   46,   47,   48,   49,   50,   51,  264,  286,
  267,  265,  289,  282,  217,  282,  194,  220,  221,  164,
  165,  224,  167,  256,  169,  283,  282,  283,  283,  174,
  208,  176,  256,   77,  267,   79,  257,  182,  262,  260,
  261,  262,  263,  282,  268,  282,  267,  256,  256,   93,
   94,   95,   96,   97,   98,  264,  258,  259,  203,  267,
  281,  256,  281,  284,  265,  264,  267,  212,  213,  264,
  271,  216,  267,  282,  283,  270,  271,  272,  273,  256,
  275,  276,  277,  278,  279,  280,  283,  282,  283,  264,
  267,  286,  287,  257,  289,  283,  260,  261,  262,  263,
  283,  257,  283,  267,  260,  261,  262,  263,  258,  259,
   21,  267,  156,   24,   25,  283,  256,  281,  258,  259,
  284,  270,  271,  272,  273,  281,  257,  282,  284,  260,
  261,  262,  263,  282,  257,  283,  267,  260,  261,  262,
  263,  283,  257,  282,  267,  260,  261,  262,  263,  283,
  281,  283,  267,  284,  270,  271,  272,  273,  281,  258,
  259,  284,  270,  271,  272,  273,  281,  257,  259,  284,
  260,  261,  262,  263,  282,  257,  283,  267,  260,  261,
  262,  263,  283,  257,  259,  267,  260,  261,  262,  263,
  259,  281,  259,  267,  284,  259,    0,    0,   78,  281,
  151,   -1,  284,   -1,   -1,   -1,   -1,  281,  257,   -1,
  284,  260,  261,  262,  263,   -1,  257,   -1,  267,  260,
  261,  262,  263,   -1,  257,   -1,  267,  260,  261,  262,
  263,   -1,  281,   -1,  267,  284,   -1,   -1,   -1,   -1,
  281,   -1,   -1,  284,   -1,   -1,   -1,   -1,  281,  257,
   -1,  284,  260,  261,  262,  263,   -1,  257,   -1,  267,
  260,  261,  262,  263,   -1,  257,   -1,  267,  260,  261,
  262,  263,   -1,  281,   -1,  267,  284,   -1,   -1,  256,
   -1,  281,   -1,  256,  284,   -1,   -1,  264,   -1,  281,
  257,  264,  284,  260,  261,  262,  263,   -1,  257,   -1,
  267,  260,  261,  262,  263,  282,  283,  256,  267,  282,
  283,  256,   -1,  256,  281,  264,  256,  284,  256,  264,
  256,  264,  281,  256,  264,  284,  264,  256,  264,   -1,
   -1,  264,   -1,  282,  283,  264,   -1,  282,  283,  282,
  283,   -1,  282,  283,  282,  283,  282,  283,  256,  282,
  283,  256,   -1,  282,  283,  256,  264,  256,   -1,  264,
   -1,   -1,   -1,  264,   -1,  264,  257,   -1,   -1,  260,
  261,  262,  263,   -1,  282,  283,  267,  282,  283,   -1,
   -1,  282,  283,  282,  283,  270,  271,  272,  273,   -1,
  281,  257,  283,   -1,  260,  261,  262,  263,   -1,   -1,
   -1,  267,  287,  270,  271,  272,  273,  270,  271,  272,
  273,  270,  271,  272,  273,  281,   -1,   -1,   -1,  265,
  287,  267,   -1,  286,   -1,  271,  256,  286,   -1,  275,
  276,  277,  278,  279,  280,  265,  266,  267,   -1,   -1,
   -1,  271,  270,  271,  272,  273,   -1,  275,  276,  277,
  278,  279,  280,
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
"prog : ID LLAVEA sentencias LLAVEC",
"prog : ID sentencias LLAVEC",
"prog : ID LLAVEA sentencias",
"prog : ID sentencias",
"prog : error",
"sentencias : sentencias sentencia PUNTOCOMA",
"sentencias : sentencia PUNTOCOMA",
"sentencias : sentencia error",
"sentencia : asignaciones",
"sentencia : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ENDIF",
"sentencia : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia : WHILE PARENTESISA condicion PARENTESISC DO LLAVEA sentencias LLAVEC",
"sentencia : RETURN PARENTESISA lista_id PARENTESISC",
"sentencia : RETURN PARENTESISA expresiones PARENTESISC",
"sentencia : declaracion",
"sentencia : expresion_lambda",
"sentencia : PRINT PARENTESISA CADENA PARENTESISC",
"sentencia : PRINT PARENTESISA termino PARENTESISC",
"sentencia : PRINT PARENTESISA error PARENTESISC",
"sentencia : IF error condicion PARENTESISC LLAVEA sentencias LLAVEC ENDIF",
"sentencia : IF PARENTESISA condicion LLAVEA sentencias LLAVEC ENDIF",
"sentencia : IF error condicion error LLAVEA sentencias LLAVEC ENDIF",
"sentencia : IF error condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia : IF PARENTESISA condicion LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia : IF error condicion error LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia : WHILE error condicion PARENTESISC DO LLAVEA sentencias LLAVEC",
"sentencia : WHILE PARENTESISA condicion DO LLAVEA sentencias LLAVEC",
"sentencia : WHILE error condicion error DO LLAVEA sentencias LLAVEC",
"sentencia : WHILE PARENTESISA condicion PARENTESISC DO error",
"sentencia : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC error",
"sentencia : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC error",
"sentencia : IF PARENTESISA condicion PARENTESISC error ENDIF",
"sentencia : IF PARENTESISA condicion PARENTESISC error ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE error ENDIF",
"sentencia : WHILE PARENTESISA condicion PARENTESISC error LLAVEA sentencias LLAVEC",
"condicion : expresiones MAYOR expresiones",
"condicion : expresiones MAYIG expresiones",
"condicion : expresiones MENOR expresiones",
"condicion : expresiones MENIG expresiones",
"condicion : expresiones IGIG expresiones",
"condicion : expresiones DIF expresiones",
"condicion : expresiones_error",
"expresiones_error : expresiones MAYOR",
"expresiones_error : expresiones MAYIG",
"expresiones_error : expresiones MENOR",
"expresiones_error : expresiones MENIG",
"expresiones_error : expresiones IGIG",
"expresiones_error : expresiones DIF",
"expresiones_error : MAYOR expresiones",
"expresiones_error : MAYIG expresiones",
"expresiones_error : MENOR expresiones",
"expresiones_error : MENIG expresiones",
"expresiones_error : IGIG expresiones",
"expresiones_error : DIF expresiones",
"expresiones_error : expresiones",
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
"declaracion : tipo ID PARENTESISA parametros_formales PARENTESISC LLAVEA sentencias LLAVEC",
"declaracion : tipo error PARENTESISA parametros_formales PARENTESISC LLAVEA sentencias LLAVEC",
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
"asignaciones : tipo ID ASIGN expresiones PUNTOCOMA",
"asignaciones : tipo_id ASIGN expresiones PUNTOCOMA",
"asignaciones : lista_id IGUAL lista_cte PUNTOCOMA",
"lista_cte : tipo_cte",
"lista_cte : lista_cte COMA tipo_cte",
"lista_cte : lista_cte tipo_cte",
"tipo_id : ID",
"tipo_id : ID PUNTO ID",
"tipo_cte : CTE",
"tipo_cte : MENOS CTE",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC LLAVEA sentencias LLAVEC PARENTESISA tipo_id PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC LLAVEA sentencias LLAVEC PARENTESISA tipo_cte PARENTESISC",
};

//#line 150 "gramatica.y"

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
//#line 533 "Parser.java"
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
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura");}
break;
case 3:
//#line 17 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador final");}
break;
case 4:
//#line 18 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores");}
break;
case 5:
//#line 19 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO");}
break;
case 8:
//#line 24 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 10:
//#line 28 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: if");}
break;
case 11:
//#line 29 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:if else");}
break;
case 12:
//#line 30 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:while");}
break;
case 13:
//#line 31 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
break;
case 14:
//#line 32 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
break;
case 15:
//#line 33 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:declaracion");}
break;
case 16:
//#line 34 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:lambda");}
break;
case 17:
//#line 35 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
break;
case 18:
//#line 36 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
break;
case 19:
//#line 37 "gramatica.y"
{System.out.println("LINEA: " + aLex.getNroLinea() + " ERROR SINTÁCTICO: argumento inválido en print");}
break;
case 20:
//#line 38 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de apertura de la condicion");}
break;
case 21:
//#line 39 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de cierre de la condicion");}
break;
case 22:
//#line 40 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: faltan parentesis de la condicion");}
break;
case 23:
//#line 41 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de apertura de la condicion");}
break;
case 24:
//#line 42 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de cierre de la condicion");}
break;
case 25:
//#line 43 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: faltan parentesis de la condicion");}
break;
case 26:
//#line 44 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de apertura de la condicion");}
break;
case 27:
//#line 45 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta parentesis de cierre de la condicion");}
break;
case 28:
//#line 46 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: faltan parentesis de la condicion");}
break;
case 29:
//#line 47 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta cuerpo en la iteracion");}
break;
case 30:
//#line 48 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta 'endif'");}
break;
case 31:
//#line 49 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta 'endif'");}
break;
case 32:
//#line 50 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta contenido en bloque");}
break;
case 33:
//#line 51 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta contenido en bloque");}
break;
case 34:
//#line 52 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta contenido en bloque");}
break;
case 35:
//#line 53 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta 'do' en iteracion");}
break;
case 42:
//#line 62 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta comparador en expresion");}
break;
case 58:
//#line 82 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta operando en expresion");}
break;
case 59:
//#line 82 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta operando en expresion");}
break;
case 70:
//#line 99 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
break;
case 71:
//#line 102 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(2)); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 72:
//#line 103 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 73:
//#line 104 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 74:
//#line 105 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 78:
//#line 113 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta especificacion del parametro formal");}
break;
case 83:
//#line 122 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta nombre del parametro formal");}
break;
case 84:
//#line 123 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta nombre del parametro formal");}
break;
case 85:
//#line 124 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta tipo del parametro formal");}
break;
case 86:
//#line 125 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta tipo del parametro formal");}
break;
case 87:
//#line 128 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion");}
break;
case 88:
//#line 129 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion");}
break;
case 89:
//#line 130 "gramatica.y"
{verificar_cantidades(val_peek(3), val_peek(1)); System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple");}
break;
case 90:
//#line 133 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 91:
//#line 134 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 92:
//#line 135 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
break;
case 95:
//#line 142 "gramatica.y"
{aLex.agregarATablaDeSimbolos(val_peek(0).sval);}
break;
case 96:
//#line 143 "gramatica.y"
{String cte = "-" + val_peek(0).sval; aLex.agregarATablaDeSimbolos(cte);}
break;
//#line 890 "Parser.java"
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
