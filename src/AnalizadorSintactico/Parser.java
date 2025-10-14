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
//#line 22 "Parser.java"




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
    0,    0,    0,    0,    0,    0,    1,    1,    1,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    4,    4,    4,
    4,    4,    4,    4,   10,   10,   10,   10,   10,   10,
   10,   10,   10,   10,   10,   10,   10,    6,    6,    6,
   12,   12,   12,   11,   11,   11,   11,    7,    7,    7,
    7,    5,    5,    5,    5,   15,    8,   17,   17,   17,
   16,   16,   16,   18,   18,   18,   18,   18,   18,    3,
    3,    3,    3,   19,   19,   19,   13,   13,   14,   14,
    9,    9,    9,    9,    9,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    2,    3,    2,    2,    1,
    8,   12,    8,    4,    4,    1,    1,    1,    4,    4,
    4,    8,    7,    8,   12,   11,   12,    8,    7,    8,
    6,    7,   11,    5,    9,    9,    8,    3,    3,    3,
    3,    3,    3,    1,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    1,    3,    1,    3,
    1,    1,    1,    1,    1,    1,    1,    2,    2,    8,
    8,    3,    3,    2,    2,    1,    4,    5,    3,    3,
    3,    1,    2,    3,    2,    2,    3,    3,    2,    4,
    3,    3,    3,    1,    3,    2,    1,    3,    1,    2,
   10,   10,    9,    9,    8,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    6,    0,    0,    0,   76,    0,
    0,    0,    0,    0,    0,   10,    0,   16,   17,   18,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    3,    0,    9,    8,    0,    0,    0,
   74,    0,    0,    0,   75,    0,    0,    0,    0,    2,
   99,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   63,   44,   59,   61,   62,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   98,    0,    1,    7,
   94,    0,   73,    0,    0,   72,    0,    0,    0,  100,
    0,    0,    0,    0,    0,    0,    0,    0,   64,   65,
   66,   67,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   21,   19,   20,   14,   15,    0,    0,    0,    0,
    0,   77,    0,    0,    0,   96,    0,    0,    0,    0,
   82,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   60,   58,    0,   34,    0,    0,    0,    0,    0,
    0,    0,   80,   79,    0,    0,    0,   95,   89,    0,
    0,   86,   85,    0,    0,   83,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   31,    0,    0,    0,
    0,    0,   88,   87,   84,    0,   81,    0,    0,    0,
    0,    0,    0,   23,    0,    0,   29,    0,    0,   78,
    0,    0,    0,    0,    0,    0,    0,   24,    0,   22,
    0,    0,   11,    0,   30,   28,   37,   13,    0,    0,
  105,    0,   71,   70,    0,    0,   35,   36,    0,    0,
  104,    0,    0,  103,    0,    0,    0,    0,  101,  102,
    0,    0,    0,   26,   27,   25,   12,
};
final static short yydgoto[] = {                          4,
   14,   15,   16,   59,   17,   60,   18,   19,   20,   62,
  109,   63,   21,   65,   22,  130,   76,  131,   82,
};
final static short yysindex[] = {                      -154,
 -260,  385,  418,    0,    0,  -99, -216, -166,    0,  -91,
 -259, -158,  418, -167, -247,    0,  -61,    0,    0,    0,
   60, -123, -112,  437,  437,  453,   27,  437,  437,   27,
 -199, -140,  -64,    0, -125,    0,    0, -124,   55,  -93,
    0,   27,   55,  -93,    0, -100,  -50, -191, -126,    0,
    0,  -56,   27,   27,   27,   27,   27,   27, -215,  455,
    0,    0,    0,    0,    0,  -17,  -57,  -32,  -10,  -30,
  168, -126, -213, -152,  416, -219,    0,  -26,    0,    0,
    0, -143,    0,  160, -143,    0, -190,   27, -190,    0,
  160,  160,  160,  160,  160,  160,  -65,  -62,    0,    0,
    0,    0,   27,   27,   27,   27,   27,   27,   79, -177,
  418,    0,    0,    0,    0,    0,   29,   47,   42,  -77,
  -55,    0,   27,  410,   55,    0,  -93, -250,   43, -254,
    0,  160, -251,  418,  418,  160,  160,  160,  160,  160,
  160,    0,    0,   48,    0,  418,  -27,   50,   53,  418,
   58, -253,    0,    0,  424,  418,   61,    0,    0,  -93,
   63,    0,    0,   65, -190,    0,   68,  145,  153,  418,
  161,   12,  418,  418,  186,  418,    0,  418,  -93,  194,
  109,   62,    0,    0,    0,  418,    0,  418,   80,  102,
  202,  118,   86,    0,  227,  235,    0,  243,  268,    0,
  109,  123,  127,   55,  276,  284,  128,    0,  134,    0,
  166, -141,    0,  418,    0,    0,    0,    0,  137,  108,
    0,  154,    0,    0,  418,  418,    0,    0,  418,  309,
    0,  162,  170,    0,  317,  325,  350,  176,    0,    0,
  199,  201,  207,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  114,    0,    0,  427,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  468,    0,    0,    0,    0, -249,    0,    0,
    0,    0,    0,    0,    0,    0, -240, -241, -236,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -173,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  209,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -235,    0, -232, -222,    0,    0,    0,    0,    0,
 -159, -157,  -88,  -40,  -15,   -9,    0,    0,    0,    0,
    0,    0,   13,   26,   76,  333,  338,  340,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -212,    0,    0,    0,  344,  368,  372,  374,  377,
  379,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -209,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -194,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   -3,    9,    0,  370,  226,  249,    0,  259,    0,    0,
    0,  362,   31,   77,   99,  383,    0, -129,  430,
};
final static int YYTABLESIZE=735;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         23,
  166,  127,  177,  166,  127,  160,   97,    9,   36,   33,
    9,    9,   97,  128,   69,   97,  128,   97,   97,   68,
   92,   30,   35,   91,   97,    5,   97,  164,   31,  178,
  167,   35,   97,   93,  165,  187,   97,  165,   37,   97,
   97,   35,  117,   90,   69,   97,   32,   41,   97,   68,
   92,   45,   49,   91,   64,   64,   64,   72,   64,   64,
   64,   33,  122,   93,   26,  127,   98,   77,  118,  123,
   83,    9,   64,   90,   86,   38,   32,  128,   41,   45,
  144,  145,   57,   64,   64,   64,   64,   64,   64,    6,
   57,   33,    7,    8,    9,   10,   52,   40,   54,   11,
   41,    1,   45,    9,   52,  146,   54,  147,   57,   57,
   32,  119,    2,   12,   27,   81,   34,  228,   64,   81,
  157,   51,   52,   52,   54,   54,   78,   52,    3,  120,
  168,  169,   46,   64,   64,   64,   64,   64,   64,   64,
   38,  229,  171,   47,    6,  125,  175,    7,    8,    9,
   10,  154,  180,   64,   11,   35,   24,  159,  126,  163,
   80,  126,   44,   31,   28,   35,  191,   51,   12,  195,
  196,   50,  198,   38,  199,   51,   35,   35,  151,   35,
   87,   25,  205,   35,  206,  129,  152,  129,   35,   29,
  183,  185,    6,   51,   51,    7,    8,    9,   10,   35,
  153,  158,   11,   35,   35,   38,   35,   35,   90,  200,
  230,   38,   39,   35,   35,   53,   12,  134,   88,   79,
  135,  235,  236,   53,  112,  237,  161,   40,  129,    6,
   89,  129,    7,    8,    9,   10,   38,   31,   35,   11,
   55,   53,   53,   35,   35,   35,   56,   48,   55,  113,
  232,  115,   70,   12,   56,  124,  172,  203,   40,   99,
  100,  101,  102,  129,  110,  111,   55,   55,   46,  193,
  194,  114,   56,   56,   69,   71,   46,  219,   75,   32,
  222,   48,   61,   61,   61,   61,   61,   61,   61,   48,
   84,   51,  148,   11,   46,   46,  233,   52,  162,   32,
   61,   91,   92,   93,   94,   95,   96,   48,   48,   38,
  149,   61,   61,   61,   61,   61,   61,    6,  184,   51,
    7,    8,    9,   10,  150,   52,   38,   11,   42,   38,
  170,   45,  173,   43,  142,  174,  132,  207,  208,   45,
  176,  181,  204,   51,  182,   11,   61,  186,   44,   52,
  188,  136,  137,  138,  139,  140,  141,   45,   45,  209,
  210,   61,   61,   61,   61,   61,   61,   61,  214,   97,
    9,  155,   51,   51,   38,  212,  213,   97,   52,   52,
   97,   61,   97,   97,   97,   97,   97,   97,   97,   97,
   97,   97,   97,   97,   66,   97,   97,   73,   74,   97,
   97,    6,   97,  220,    7,    8,    9,   10,  221,    6,
  225,   11,    7,    8,    9,   10,  226,    6,  231,   11,
    7,    8,    9,   10,  227,   12,    5,   11,  189,   99,
  100,  101,  102,   12,  244,  234,  190,   99,  100,  101,
  102,   12,    6,  239,  192,    7,    8,    9,   10,  116,
    6,  240,   11,    7,    8,    9,   10,  245,    6,  246,
   11,    7,    8,    9,   10,  247,   12,    4,   11,  197,
  143,  133,   85,    0,  201,    0,    0,  202,   61,   61,
   61,   61,   12,    6,    0,  211,    7,    8,    9,   10,
   61,    6,    0,   11,    7,    8,    9,   10,    0,    6,
    0,   11,    7,    8,    9,   10,    0,   12,    0,   11,
  215,    0,    0,    0,    0,   12,    0,    0,  216,    0,
    0,    0,    0,   12,    6,    0,  217,    7,    8,    9,
   10,    0,    6,    0,   11,    7,    8,    9,   10,    0,
    6,    0,   11,    7,    8,    9,   10,    0,   12,    0,
   11,  218,    0,    0,    0,    0,   12,    0,    0,  223,
    0,    0,    0,    0,   12,    6,    0,  224,    7,    8,
    9,   10,    0,    6,    0,   11,    7,    8,    9,   10,
    0,    6,    0,   11,    7,    8,    9,   10,   47,   12,
    0,   11,  238,   49,    0,   50,   47,   12,    0,   39,
  241,   49,    0,   50,    0,   12,    6,   39,  242,    7,
    8,    9,   10,    0,   47,   47,   11,    0,    0,   49,
   49,   50,   50,   41,    0,   39,   39,   38,    0,   40,
   12,   41,   42,  243,   43,   38,    0,   40,    0,    0,
   42,    6,   43,    0,    7,    8,    9,   10,    0,   41,
   41,   11,    0,   38,   38,   40,   40,    0,   42,   42,
   43,   43,    0,    0,    0,   12,    6,   13,    0,    7,
    8,    9,   10,    0,    6,    0,   11,    7,    8,    9,
   10,    0,    0,    0,   11,   99,  100,  101,  102,    0,
   12,    0,  156,   99,  100,  101,  102,    0,   12,    0,
    0,   51,  121,   11,    0,    0,    0,   52,   67,    0,
  179,   53,   54,   55,   56,   57,   58,   51,   68,   11,
    0,    0,    0,   52,   99,  100,  101,  102,    0,  103,
  104,  105,  106,  107,  108,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
  130,  256,  256,  133,  256,  256,  256,  262,  256,   13,
  262,  262,  262,  268,  256,  256,  268,  267,  268,  256,
  256,  281,   14,  256,  274,  286,  267,  282,  288,  283,
  282,   23,  282,  256,  289,  165,  286,  289,  286,  289,
  256,   33,  256,  256,  286,  286,  256,   17,  289,  286,
  286,   21,   22,  286,   24,   25,   26,   27,   28,   29,
   30,  256,  282,  286,  281,  256,  282,  267,  282,  289,
   40,  262,   42,  286,   44,  267,  286,  268,   48,   49,
  258,  259,  256,   53,   54,   55,   56,   57,   58,  257,
  264,  286,  260,  261,  262,  263,  256,  289,  256,  267,
   70,  256,   72,  262,  264,  283,  264,  111,  282,  283,
   12,  264,  267,  281,  281,   39,  284,  259,   88,   43,
  124,  265,  282,  283,  282,  283,  267,  271,  283,  282,
  134,  135,  256,  103,  104,  105,  106,  107,  108,  109,
  267,  283,  146,  267,  257,  289,  150,  260,  261,  262,
  263,  121,  156,  123,  267,  147,  256,  127,   82,  129,
  286,   85,  289,  288,  256,  157,  170,  256,  281,  173,
  174,  284,  176,  267,  178,  264,  168,  169,  256,  171,
  281,  281,  186,  175,  188,   87,  264,   89,  180,  281,
  160,  161,  257,  282,  283,  260,  261,  262,  263,  191,
  256,  125,  267,  195,  196,  267,  198,  199,  265,  179,
  214,  267,  274,  205,  206,  256,  281,  283,  269,  284,
  283,  225,  226,  264,  282,  229,  128,  289,  130,  257,
  281,  133,  260,  261,  262,  263,  267,  288,  230,  267,
  256,  282,  283,  235,  236,  237,  256,   22,  264,  282,
  220,  282,   27,  281,  264,  282,  284,  181,  289,  270,
  271,  272,  273,  165,  282,  283,  282,  283,  256,  258,
  259,  282,  282,  283,   26,   27,  264,  201,   30,  181,
  204,  256,   24,   25,   26,   27,   28,   29,   30,  264,
   42,  265,  264,  267,  282,  283,  220,  271,  256,  201,
   42,   53,   54,   55,   56,   57,   58,  282,  283,  267,
  264,   53,   54,   55,   56,   57,   58,  257,  256,  265,
  260,  261,  262,  263,  283,  271,  267,  267,  269,  267,
  283,  256,  283,  274,  256,  283,   88,  258,  259,  264,
  283,  281,  281,  265,  284,  267,   88,  283,  289,  271,
  283,  103,  104,  105,  106,  107,  108,  282,  283,  258,
  259,  103,  104,  105,  106,  107,  108,  109,  283,  256,
  262,  123,  265,  265,  267,  258,  259,  264,  271,  271,
  267,  123,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  280,   25,  282,  283,   28,   29,  286,
  287,  257,  289,  281,  260,  261,  262,  263,  282,  257,
  283,  267,  260,  261,  262,  263,  283,  257,  282,  267,
  260,  261,  262,  263,  259,  281,    0,  267,  284,  270,
  271,  272,  273,  281,  259,  282,  284,  270,  271,  272,
  273,  281,  257,  282,  284,  260,  261,  262,  263,  282,
  257,  282,  267,  260,  261,  262,  263,  259,  257,  259,
  267,  260,  261,  262,  263,  259,  281,    0,  267,  284,
  109,   89,   43,   -1,  281,   -1,   -1,  284,  270,  271,
  272,  273,  281,  257,   -1,  284,  260,  261,  262,  263,
  282,  257,   -1,  267,  260,  261,  262,  263,   -1,  257,
   -1,  267,  260,  261,  262,  263,   -1,  281,   -1,  267,
  284,   -1,   -1,   -1,   -1,  281,   -1,   -1,  284,   -1,
   -1,   -1,   -1,  281,  257,   -1,  284,  260,  261,  262,
  263,   -1,  257,   -1,  267,  260,  261,  262,  263,   -1,
  257,   -1,  267,  260,  261,  262,  263,   -1,  281,   -1,
  267,  284,   -1,   -1,   -1,   -1,  281,   -1,   -1,  284,
   -1,   -1,   -1,   -1,  281,  257,   -1,  284,  260,  261,
  262,  263,   -1,  257,   -1,  267,  260,  261,  262,  263,
   -1,  257,   -1,  267,  260,  261,  262,  263,  256,  281,
   -1,  267,  284,  256,   -1,  256,  264,  281,   -1,  256,
  284,  264,   -1,  264,   -1,  281,  257,  264,  284,  260,
  261,  262,  263,   -1,  282,  283,  267,   -1,   -1,  282,
  283,  282,  283,  256,   -1,  282,  283,  256,   -1,  256,
  281,  264,  256,  284,  256,  264,   -1,  264,   -1,   -1,
  264,  257,  264,   -1,  260,  261,  262,  263,   -1,  282,
  283,  267,   -1,  282,  283,  282,  283,   -1,  282,  283,
  282,  283,   -1,   -1,   -1,  281,  257,  283,   -1,  260,
  261,  262,  263,   -1,  257,   -1,  267,  260,  261,  262,
  263,   -1,   -1,   -1,  267,  270,  271,  272,  273,   -1,
  281,   -1,  283,  270,  271,  272,  273,   -1,  281,   -1,
   -1,  265,  287,  267,   -1,   -1,   -1,  271,  256,   -1,
  287,  275,  276,  277,  278,  279,  280,  265,  266,  267,
   -1,   -1,   -1,  271,  270,  271,  272,  273,   -1,  275,
  276,  277,  278,  279,  280,
};
}
final static short YYFINAL=4;
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
"prog : LLAVEA sentencias LLAVEC",
"prog : ID sentencias LLAVEC",
"prog : ID LLAVEA sentencias",
"prog : ID sentencias",
"prog : error PUNTOCOMA",
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
"sentencia : llamado_funcion",
"sentencia : expresion_lambda",
"sentencia : PRINT PARENTESISA CADENA PARENTESISC",
"sentencia : PRINT PARENTESISA expresiones PARENTESISC",
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
"sentencia : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC",
"sentencia : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC",
"sentencia : IF PARENTESISA condicion PARENTESISC ENDIF",
"sentencia : IF PARENTESISA condicion PARENTESISC ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE ENDIF",
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
"expresiones : expresiones operador error",
"termino : tipo_id",
"termino : tipo_cte",
"termino : llamado_funcion",
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
"llamado_funcion : ID PARENTESISA parametros_reales PARENTESISC",
"parametros_reales : parametros_reales COMA expresiones FLECHA tipo_id",
"parametros_reales : expresiones FLECHA tipo_id",
"parametros_reales : expresiones FLECHA error",
"parametros_formales : parametros_formales COMA parametro",
"parametros_formales : parametro",
"parametros_formales : parametros_formales parametro",
"parametro : CVR tipo tipo_id",
"parametro : tipo tipo_id",
"parametro : tipo error",
"parametro : CVR tipo error",
"parametro : CVR error tipo_id",
"parametro : error tipo_id",
"asignaciones : tipo ID ASIGN expresiones",
"asignaciones : tipo_id ASIGN expresiones",
"asignaciones : lista_id IGUAL lista_cte",
"asignaciones : tipo_id IGUAL lista_cte",
"lista_cte : tipo_cte",
"lista_cte : lista_cte COMA tipo_cte",
"lista_cte : lista_cte tipo_cte",
"tipo_id : ID",
"tipo_id : ID PUNTO ID",
"tipo_cte : CTE",
"tipo_cte : MENOS CTE",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC LLAVEA sentencias LLAVEC PARENTESISA tipo_id PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC LLAVEA sentencias LLAVEC PARENTESISA tipo_cte PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC sentencias LLAVEC PARENTESISA tipo_cte PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC LLAVEA sentencias PARENTESISA tipo_cte PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC sentencias PARENTESISA tipo_cte PARENTESISC",
};

//#line 160 "gramatica.y"

/* CODIGO AUXILIAR */


AnalisisLexico aLex;

public void setAlex(AnalisisLexico a){
    this.aLex = a;

}

public void verificar_cantidades(ParserVal lista1, ParserVal lista2){
    ArrayList<ParserVal> l1 = (ArrayList<ParserVal>)lista1.obj;
    ArrayList<ParserVal> l2 = (ArrayList<ParserVal>)lista2.obj;
    if (l1 != null && l2 != null){
    if (l1.size() > l2.size() )
        System.out.println("Linea: "+aLex.getNroLinea()+" ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
    }
}

void yyerror (String s){
    //System.out.printf(s);
}

int yylex (){
    try {
        int token = aLex.yylex();
        yylval = aLex.getYylval();
        return token;
    } catch (IOException e) {
        System.err.println("Error de lectura en el analizador léxico: " + e.getMessage());
        return 0; //Devuelvo 0 como si fuera fin de archivo
    }

}
//#line 570 "Parser.java"
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
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de programa");}
break;
case 3:
//#line 17 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura");}
break;
case 4:
//#line 18 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador final");}
break;
case 5:
//#line 19 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores");}
break;
case 6:
//#line 20 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO");}
break;
case 9:
//#line 25 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 11:
//#line 29 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: if");}
break;
case 12:
//#line 30 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:if else");}
break;
case 13:
//#line 31 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:while");}
break;
case 14:
//#line 32 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
break;
case 15:
//#line 33 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
break;
case 16:
//#line 34 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:declaracion");}
break;
case 17:
//#line 35 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:llamado de funcion");}
break;
case 18:
//#line 36 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:lambda");}
break;
case 19:
//#line 37 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
break;
case 20:
//#line 38 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
break;
case 21:
//#line 39 "gramatica.y"
{System.out.println("LINEA: " + aLex.getNroLinea() + " ERROR SINTÁCTICO: argumento inválido en print");}
break;
case 22:
//#line 40 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 23:
//#line 41 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 24:
//#line 42 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 25:
//#line 43 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 26:
//#line 44 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 27:
//#line 45 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 28:
//#line 46 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 29:
//#line 47 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 30:
//#line 48 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 31:
//#line 49 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta cuerpo en la iteracion");}
break;
case 32:
//#line 50 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 33:
//#line 51 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 34:
//#line 52 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 35:
//#line 53 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 36:
//#line 54 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else'");}
break;
case 37:
//#line 55 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'do' en iteracion");}
break;
case 44:
//#line 64 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta comparador en expresion");}
break;
case 60:
//#line 84 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta operando en expresion");}
break;
case 71:
//#line 101 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
break;
case 72:
//#line 104 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(2)); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 73:
//#line 105 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 74:
//#line 106 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 75:
//#line 107 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 80:
//#line 118 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta especificacion del parametro formal");}
break;
case 83:
//#line 123 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta ',' en declaracion de las variables");}
break;
case 86:
//#line 128 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta nombre del parametro formal");}
break;
case 87:
//#line 129 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta nombre del parametro formal");}
break;
case 88:
//#line 130 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta tipo del parametro formal");}
break;
case 89:
//#line 131 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta tipo del parametro formal");}
break;
case 90:
//#line 134 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion");}
break;
case 91:
//#line 135 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion");}
break;
case 92:
//#line 136 "gramatica.y"
{verificar_cantidades(val_peek(2), val_peek(0)); System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple");}
break;
case 93:
//#line 137 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple");}
break;
case 94:
//#line 140 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 95:
//#line 141 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 96:
//#line 142 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
break;
case 99:
//#line 149 "gramatica.y"
{aLex.agregarATablaDeSimbolos(val_peek(0).sval);}
break;
case 100:
//#line 150 "gramatica.y"
{String cte = "-" + val_peek(0).sval; aLex.agregarATablaDeSimbolos(cte);}
break;
case 103:
//#line 155 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura en funcion lambda");}
break;
case 104:
//#line 156 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de cierre en funcion lambda");}
break;
case 105:
//#line 157 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores en funcion lambda");}
break;
//#line 951 "Parser.java"
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
