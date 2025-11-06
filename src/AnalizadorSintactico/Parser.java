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
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
//#line 25 "Parser.java"




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
    0,    0,    0,    0,    0,    0,    1,    1,    2,    2,
    2,    5,    5,    3,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    8,    8,    8,    8,    8,    8,    8,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   10,   10,   10,   10,   10,   10,   14,   14,   14,
   15,   15,   15,   15,    6,    6,    6,    6,   19,    9,
    9,    9,    9,   18,   11,   21,   21,   21,   20,   20,
   20,   22,   22,   22,   22,   22,   22,    7,    7,    7,
    7,   23,   23,   23,   16,   16,   17,   17,   12,   12,
   12,   12,   12,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    2,    2,    1,    2,    2,
    1,    2,    2,    1,    1,    8,   12,    8,    4,    4,
    1,    1,    4,    4,    4,    8,    7,    8,   12,   11,
   12,    8,    7,    8,    6,    7,   11,    5,    9,    9,
    8,    3,    3,    3,    3,    3,    3,    1,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    1,    3,    3,    3,    3,    1,    3,    1,    1,    1,
    1,    1,    1,    1,    2,    2,    4,    8,    5,    3,
    3,    2,    2,    1,    4,    5,    3,    3,    3,    1,
    2,    3,    2,    2,    3,    3,    2,    4,    3,    3,
    3,    1,    3,    2,    1,    3,    1,    2,   10,   10,
    9,    9,    8,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    6,    0,    0,    0,   84,    0,
    0,    0,    0,    0,    8,    0,    0,   11,   14,   15,
    0,   21,   22,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    3,    7,   12,
    9,   13,   10,    0,    0,    0,   82,    0,    0,    0,
   83,    0,    0,    0,    0,    0,    2,  107,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   70,   48,   66,
   68,   69,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  106,    0,    1,  102,    0,   81,    0,
    0,   80,    0,    0,    0,    0,  108,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   25,   23,
   24,   19,   20,    0,    0,    0,    0,    0,   85,    0,
    0,    0,  104,    0,    0,    0,    0,   90,    0,    0,
   77,    0,    0,   62,   63,   64,   65,    0,    0,    0,
    0,    0,    0,   67,    0,   38,    0,    0,    0,    0,
    0,    0,    0,   88,   87,    0,    0,    0,  103,   97,
    0,    0,   94,   93,    0,    0,   91,   79,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   35,    0,    0,
    0,    0,    0,   96,   95,   92,    0,   89,    0,    0,
    0,    0,    0,   27,    0,    0,   33,    0,    0,   86,
    0,    0,    0,    0,    0,    0,   28,    0,   26,    0,
    0,   16,    0,   34,   32,   41,   18,    0,    0,  113,
    0,   78,    0,    0,   39,   40,    0,    0,  112,    0,
    0,  111,    0,    0,    0,    0,  109,  110,    0,    0,
    0,   30,   31,   29,   17,
};
final static short yydgoto[] = {                          4,
   14,   15,   16,   17,   18,   19,   20,   66,   21,   67,
   22,   23,   69,   70,  116,   24,   72,   25,   26,  137,
   83,  138,   88,
};
final static short yysindex[] = {                      -153,
 -281,  399,  432,    0,    0, -192, -225, -184,    0, -165,
  -52, -130,  432, -150,    0, -237, -232,    0,    0,    0,
  -78,    0,    0,  409, -249, -137,    3,  443,  443,   54,
  102,  443,  443,  102, -107, -102,   15,    0,    0,    0,
    0,    0,    0, -113, -213,  -75,    0,  102, -213,  -75,
    0, -125, -122, -132, -106,  432,    0,    0,  -51,  102,
  102,  102,  102,  102,  102, -252,  454,    0,    0,    0,
    0,    0, -268,  -49,  -39,  132, -266,  182, -106, -231,
 -131,  -20,   -8,    0,   16,    0,    0, -121,    0,  174,
 -121,    0,  -55,  102,  -55,   93,    0,  174,  174,  174,
  174,  174,  174,   -4,   30,  102,  102,  102,  102,  102,
  102,  102,  102,  102,  102,    6, -179,  432,    0,    0,
    0,    0,    0,   50,   63,   60,  -77,  -59,    0,  102,
  424, -213,    0,  -75,   61,  -46, -201,    0,  174,  -21,
    0,  432,  432,    0,    0,    0,    0,  174,  174,  174,
  174,  174,  174,    0,   64,    0,  432,  101,   74,   76,
  432,   87, -245,    0,    0,  430,  432,  126,    0,    0,
  -75,   13,    0,    0,   88,  -55,    0,    0,  134,  159,
  432,  167,  -54,  432,  432,  175,  432,    0,  432,  -75,
  200,   21,   94,    0,    0,    0,  432,    0,  -13,   32,
  208,   36,   95,    0,  216,  241,    0,  249,  257,    0,
   21,   98,   99, -213,  282,  107,    0,  109,    0,  117,
 -154,    0,  432,    0,    0,    0,    0,  118,  141,    0,
  127,    0,  432,  432,    0,    0,  432,  290,    0,  129,
  131,    0,  298,  323,  331,  158,    0,    0,  166,  172,
  180,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   62,    0,    0,  433,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  441,    0,    0,    0,
    0,    0,    0, -119,    0,    0,    0,    0,    0,    0,
    0,    0, -239, -229, -227,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -111,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  223,    0,
    0,    0,    0,    0,    0,    0,    0, -217,    0, -194,
 -193,    0,    0,    0,    0,    0,    0,  -66,   29,   33,
  116,  313,  317,    0,    0,  193,  202,  210,  218,  341,
  345,  347,  350,  352,  354,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -186,    0,
    0,    0,    0,    0,    0,    0,    0,  357,  361,  382,
  385,  389,  391,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -185,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -178,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   -3,   -6,    0,    0,    0,    0,    0,   66,  321,  -28,
   12,    0,    0,  379,    0,  194,  -23,    1,    0,  355,
    0, -128,  423,
};
final static int YYTABLESIZE=734;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         27,
   44,   76,   78,  104,    5,   82,   52,   39,  177,   37,
  188,  177,   36,  117,  118,  122,  105,   53,   40,   90,
   39,   87,   46,   42,  124,   87,   76,  105,   75,  105,
   39,   98,   99,  100,  101,  102,  103,  189,  100,   68,
   68,   68,   68,   68,   68,   68,  105,  198,   41,  105,
  125,   58,   96,   43,  134,   30,   76,   59,   75,   68,
    9,   99,  101,   28,  133,  139,  135,  133,  100,   98,
   36,   68,   68,   68,   68,   68,   68,   37,  155,  156,
  175,  148,  149,  150,  151,  152,  153,  176,   29,   39,
   32,   99,  101,  136,   73,  136,   31,   80,   81,   98,
   36,  166,    1,  157,  236,   68,    6,   37,  169,    7,
    8,    9,   10,    2,  158,   33,   11,   68,   68,   68,
   68,   68,   68,   68,   68,   68,   68,  168,  237,    3,
   12,    9,  126,   38,   44,  172,  105,  136,  179,  180,
  136,   68,  105,   58,   61,   56,   94,  105,  105,   59,
  127,   39,   61,  182,  105,   93,   46,  186,   95,   84,
   44,   39,  105,  191,   85,   35,  105,  132,  213,  105,
   61,   61,   39,   39,   35,   39,  136,  201,  162,   39,
  205,  206,   50,  208,   39,  209,  163,  228,   44,   56,
  231,   44,   36,  215,   39,   45,  164,   56,   39,   39,
  134,   39,   39,  203,  204,  241,    9,   44,   39,  173,
   46,   36,  135,   97,   47,   56,   56,   51,   55,  238,
   44,   71,   71,   71,   79,   71,   71,   71,   34,  243,
  244,   39,  119,  245,  134,   35,   39,   39,   39,   89,
    9,   71,  120,   92,  216,  217,  135,   47,   51,  106,
  107,  108,  109,   71,   71,   71,   71,   71,   71,    6,
  178,  154,    7,    8,    9,   10,  128,  176,  195,   11,
   47,    6,   51,  129,    7,    8,    9,   10,  142,   44,
  130,   11,    9,   12,   58,   58,   57,   71,   55,  218,
  219,   59,   58,  221,  222,   12,   55,  131,   86,   71,
   71,   71,   71,   71,   71,   71,   71,   71,   71,   74,
   58,   58,  143,  159,   55,   55,  171,  105,   58,   75,
   11,  165,    9,   71,   59,  105,  160,  170,  105,  174,
  105,  105,  105,  105,  105,  105,  105,  105,  105,  105,
  105,  105,  161,  105,  105,   54,  181,  105,  105,    6,
  105,   77,    7,    8,    9,   10,  184,    6,  185,   11,
    7,    8,    9,   10,  194,  196,   58,   11,   11,  187,
  197,   57,   59,   12,  214,  235,  141,  223,  229,   57,
  230,   12,    6,  210,  183,    7,    8,    9,   10,  233,
    6,  234,   11,    7,    8,    9,   10,   57,   57,  239,
   11,  106,  107,  108,  109,   58,  192,   44,  242,  193,
  247,   59,  248,  121,   12,    6,  252,  199,    7,    8,
    9,   10,  240,    6,  253,   11,    7,    8,    9,   10,
  254,    6,    5,   11,    7,    8,    9,   10,  255,   12,
    4,   11,  200,  106,  107,  108,  109,   12,   71,  140,
  202,  106,  107,  108,  109,   12,    6,   72,  207,    7,
    8,    9,   10,  123,    6,   73,   11,    7,    8,    9,
   10,   91,    6,   74,   11,    7,    8,    9,   10,    0,
  211,    0,   11,  212,  144,  145,  146,  147,   12,    0,
    0,  220,   68,   68,   68,   68,   12,    6,    0,  224,
    7,    8,    9,   10,   68,    6,    0,   11,    7,    8,
    9,   10,    0,    6,    0,   11,    7,    8,    9,   10,
    0,   12,    0,   11,  225,    0,    0,    0,    0,   12,
    0,    0,  226,    0,    0,    0,    0,   12,    6,    0,
  227,    7,    8,    9,   10,    0,    6,    0,   11,    7,
    8,    9,   10,    0,    6,    0,   11,    7,    8,    9,
   10,    0,   12,    0,   11,  232,    0,    0,   59,    0,
   12,    0,   60,  246,    0,    0,   59,    0,   12,    6,
   60,  249,    7,    8,    9,   10,    0,    6,    0,   11,
    7,    8,    9,   10,   59,   59,   50,   11,   60,   60,
   52,    0,   49,   12,   50,   51,  250,   53,   52,   54,
   49,   12,   43,   51,  251,   53,   45,   54,    0,    0,
   43,    0,   50,   50,   45,    0,   52,   52,   49,   49,
    0,   51,   51,   53,   53,   54,   54,   42,   43,   43,
   44,    0,   45,   45,   46,   42,   47,    0,   44,    0,
    0,    0,   46,    0,   47,    6,    0,    0,    7,    8,
    9,   10,    0,   42,   42,   11,   44,   44,    0,    0,
   46,   46,   47,   47,    0,   44,    0,   48,    0,   12,
    6,   13,   49,    7,    8,    9,   10,    0,    6,    0,
   11,    7,    8,    9,   10,    0,    0,   50,   11,  106,
  107,  108,  109,    0,   12,    0,  167,   58,    0,   11,
    0,    0,   12,   59,    0,    0,  190,   60,   61,   62,
   63,   64,   65,  106,  107,  108,  109,    0,  110,  111,
  112,  113,  114,  115,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
  267,   30,   31,  256,  286,   34,  256,   14,  137,   13,
  256,  140,   12,  282,  283,  282,  256,  267,  256,   48,
   27,   45,  289,  256,  256,   49,  256,  267,  256,  282,
   37,   60,   61,   62,   63,   64,   65,  283,  256,   28,
   29,   30,   31,   32,   33,   34,  286,  176,  286,  289,
  282,  265,   56,  286,  256,  281,  286,  271,  286,   48,
  262,  256,  256,  256,   88,   94,  268,   91,  286,  256,
  256,   60,   61,   62,   63,   64,   65,  256,  258,  259,
  282,  110,  111,  112,  113,  114,  115,  289,  281,   96,
  256,  286,  286,   93,   29,   95,  281,   32,   33,  286,
  286,  130,  256,  283,  259,   94,  257,  286,  132,  260,
  261,  262,  263,  267,  118,  281,  267,  106,  107,  108,
  109,  110,  111,  112,  113,  114,  115,  131,  283,  283,
  281,  262,  264,  284,  267,  135,  256,  137,  142,  143,
  140,  130,  262,  265,  256,  283,  269,  267,  268,  271,
  282,  158,  264,  157,  274,  281,  289,  161,  281,  267,
  267,  168,  282,  167,  267,  288,  286,  289,  192,  289,
  282,  283,  179,  180,  288,  182,  176,  181,  256,  186,
  184,  185,  289,  187,  191,  189,  264,  211,  267,  256,
  214,  267,  192,  197,  201,  274,  256,  264,  205,  206,
  256,  208,  209,  258,  259,  229,  262,  267,  215,  256,
  289,  211,  268,  265,   21,  282,  283,   24,   25,  223,
  267,   28,   29,   30,   31,   32,   33,   34,  281,  233,
  234,  238,  282,  237,  256,  288,  243,  244,  245,   46,
  262,   48,  282,   50,  258,  259,  268,   54,   55,  270,
  271,  272,  273,   60,   61,   62,   63,   64,   65,  257,
  282,  256,  260,  261,  262,  263,  287,  289,  256,  267,
   77,  257,   79,  282,  260,  261,  262,  263,  283,  267,
  289,  267,  262,  281,  256,  265,  284,   94,  256,  258,
  259,  271,  264,  258,  259,  281,  264,  282,  284,  106,
  107,  108,  109,  110,  111,  112,  113,  114,  115,  256,
  282,  283,  283,  264,  282,  283,  256,  256,  265,  266,
  267,  128,  262,  130,  271,  264,  264,  134,  267,  136,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  280,  283,  282,  283,   25,  283,  286,  287,  257,
  289,   31,  260,  261,  262,  263,  283,  257,  283,  267,
  260,  261,  262,  263,  171,  172,  265,  267,  267,  283,
  283,  256,  271,  281,  281,  259,  284,  283,  281,  264,
  282,  281,  257,  190,  284,  260,  261,  262,  263,  283,
  257,  283,  267,  260,  261,  262,  263,  282,  283,  282,
  267,  270,  271,  272,  273,  265,  281,  267,  282,  284,
  282,  271,  282,  282,  281,  257,  259,  284,  260,  261,
  262,  263,  229,  257,  259,  267,  260,  261,  262,  263,
  259,  257,    0,  267,  260,  261,  262,  263,  259,  281,
    0,  267,  284,  270,  271,  272,  273,  281,  256,   95,
  284,  270,  271,  272,  273,  281,  257,  256,  284,  260,
  261,  262,  263,  282,  257,  256,  267,  260,  261,  262,
  263,   49,  257,  256,  267,  260,  261,  262,  263,   -1,
  281,   -1,  267,  284,  106,  107,  108,  109,  281,   -1,
   -1,  284,  270,  271,  272,  273,  281,  257,   -1,  284,
  260,  261,  262,  263,  282,  257,   -1,  267,  260,  261,
  262,  263,   -1,  257,   -1,  267,  260,  261,  262,  263,
   -1,  281,   -1,  267,  284,   -1,   -1,   -1,   -1,  281,
   -1,   -1,  284,   -1,   -1,   -1,   -1,  281,  257,   -1,
  284,  260,  261,  262,  263,   -1,  257,   -1,  267,  260,
  261,  262,  263,   -1,  257,   -1,  267,  260,  261,  262,
  263,   -1,  281,   -1,  267,  284,   -1,   -1,  256,   -1,
  281,   -1,  256,  284,   -1,   -1,  264,   -1,  281,  257,
  264,  284,  260,  261,  262,  263,   -1,  257,   -1,  267,
  260,  261,  262,  263,  282,  283,  256,  267,  282,  283,
  256,   -1,  256,  281,  264,  256,  284,  256,  264,  256,
  264,  281,  256,  264,  284,  264,  256,  264,   -1,   -1,
  264,   -1,  282,  283,  264,   -1,  282,  283,  282,  283,
   -1,  282,  283,  282,  283,  282,  283,  256,  282,  283,
  256,   -1,  282,  283,  256,  264,  256,   -1,  264,   -1,
   -1,   -1,  264,   -1,  264,  257,   -1,   -1,  260,  261,
  262,  263,   -1,  282,  283,  267,  282,  283,   -1,   -1,
  282,  283,  282,  283,   -1,  267,   -1,  269,   -1,  281,
  257,  283,  274,  260,  261,  262,  263,   -1,  257,   -1,
  267,  260,  261,  262,  263,   -1,   -1,  289,  267,  270,
  271,  272,  273,   -1,  281,   -1,  283,  265,   -1,  267,
   -1,   -1,  281,  271,   -1,   -1,  287,  275,  276,  277,
  278,  279,  280,  270,  271,  272,  273,   -1,  275,  276,
  277,  278,  279,  280,
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
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : sentencia_declarativa PUNTOCOMA",
"sentencia : sentencia_ejecutable PUNTOCOMA",
"sentencia : sentencia_error",
"sentencia_error : sentencia_declarativa error",
"sentencia_error : sentencia_ejecutable error",
"sentencia_declarativa : declaracion",
"sentencia_ejecutable : asignaciones",
"sentencia_ejecutable : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ENDIF",
"sentencia_ejecutable : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia_ejecutable : WHILE PARENTESISA condicion PARENTESISC DO LLAVEA sentencias LLAVEC",
"sentencia_ejecutable : RETURN PARENTESISA lista_id PARENTESISC",
"sentencia_ejecutable : RETURN PARENTESISA expresiones PARENTESISC",
"sentencia_ejecutable : llamado_funcion",
"sentencia_ejecutable : expresion_lambda",
"sentencia_ejecutable : PRINT PARENTESISA CADENA PARENTESISC",
"sentencia_ejecutable : PRINT PARENTESISA expresiones PARENTESISC",
"sentencia_ejecutable : PRINT PARENTESISA error PARENTESISC",
"sentencia_ejecutable : IF error condicion PARENTESISC LLAVEA sentencias LLAVEC ENDIF",
"sentencia_ejecutable : IF PARENTESISA condicion LLAVEA sentencias LLAVEC ENDIF",
"sentencia_ejecutable : IF error condicion error LLAVEA sentencias LLAVEC ENDIF",
"sentencia_ejecutable : IF error condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia_ejecutable : IF PARENTESISA condicion LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia_ejecutable : IF error condicion error LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia_ejecutable : WHILE error condicion PARENTESISC DO LLAVEA sentencias LLAVEC",
"sentencia_ejecutable : WHILE PARENTESISA condicion DO LLAVEA sentencias LLAVEC",
"sentencia_ejecutable : WHILE error condicion error DO LLAVEA sentencias LLAVEC",
"sentencia_ejecutable : WHILE PARENTESISA condicion PARENTESISC DO error",
"sentencia_ejecutable : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC",
"sentencia_ejecutable : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE LLAVEA sentencias LLAVEC",
"sentencia_ejecutable : IF PARENTESISA condicion PARENTESISC ENDIF",
"sentencia_ejecutable : IF PARENTESISA condicion PARENTESISC ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia_ejecutable : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC ELSE ENDIF",
"sentencia_ejecutable : WHILE PARENTESISA condicion PARENTESISC error LLAVEA sentencias LLAVEC",
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
"expresiones : expresiones MAS termino",
"expresiones : expresiones MENOS termino",
"expresiones : expresiones AST termino",
"expresiones : expresiones BARRA termino",
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
"declaracion : header_funcion LLAVEA sentencias LLAVEC",
"declaracion : tipo error PARENTESISA parametros_formales PARENTESISC LLAVEA sentencias LLAVEC",
"header_funcion : tipo ID PARENTESISA parametros_formales PARENTESISC",
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

//#line 180 "gramatica.y"

/* CODIGO AUXILIAR */

AnalisisLexico aLex;
private HashMap<String, ArrayList<String>> tablaDeSimbolos = new HashMap<String, ArrayList<String>>();

public void setAlex(AnalisisLexico a){
    this.aLex = a;

}

public void verificar_cantidades(ParserVal lista1, ParserVal lista2){
    ArrayList<String> l1 = (ArrayList<String>)lista1.obj;
    ArrayList<String> l2 = (ArrayList<String>)lista2.obj;
    if (l1 != null && l2 != null){
    if (l1.size() > l2.size() )
        agregarError("Linea: "+aLex.getNroLinea()+" ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
    }
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
        String clave = ambito+":"+name;
        if (tablaDeSimbolos.containsKey(clave)) {
            ArrayList<String> fila = tablaDeSimbolos.get(clave);
            fila.set(1, tipo);
        }else{
            System.out.println("(modificarTipoTS) Error, la clave" + clave + " no existe en la tabla de simbolos");
        }
    }
}

public void modificarUsos(ArrayList<String> lista, String uso){
        for (String a: lista){
            modificarUsoTS(a, uso);
        }
}

public void modificarUsoTS(String aux, String uso){
    String clave = ambito+":"+aux;
    if (tablaDeSimbolos.containsKey(clave)) {
        ArrayList<String> fila = tablaDeSimbolos.get(clave);
        //Se agrega el uso en el indice = 2
        fila.add(uso);
    }else{
        System.out.println("(modificarUsoTS) Error, la clave" + clave + " no existe en la tabla de simbolos");
    }
}
public void modificarAmbitosTS(ArrayList<String> a){
    int index = ambito.lastIndexOf(":");
    String ambitoAfuera = ambito.substring(0, index);

    for (String parametro : a){
        //Lo busco en la ts y modifico su clave para que incluya el ambito de la funcion definida
        ArrayList<String> aux = tablaDeSimbolos.get(ambitoAfuera+":"+parametro);
        tablaDeSimbolos.remove(ambitoAfuera+":"+parametro);
        tablaDeSimbolos.put(ambito+":"+parametro, aux);
    }
}
public void copiarTS(String clave, int token){
    String aux = ambito+":"+yylval.sval;
    if(!tablaDeSimbolos.containsKey(aux)){
        String tipo = aLex.getTipoTS(clave);
        ArrayList<String> a = new ArrayList<String>();
        a.add(tipo);
        //Se agrega el tipo en el indice = 1
        if(token == 265){
            String tipoCte = aLex.getTipoCteTS(yylval.sval);
            a.add(tipoCte);
        }else{
            a.add(" ");
        }
        tablaDeSimbolos.put(aux, a);
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

//-------------------------------------------------------------------------------------------------------------CODIGO INTERMEDIO
private ArrayList<String> mainArreglo = new ArrayList<String>();
private HashMap<String, ArrayList<String>> polacaInversa = new HashMap<String, ArrayList<String>>();
private String ambito = "MAIN";

public ArrayList<String> crearArregloId(String v1, String v2){
        ArrayList<String> listaNombres = new ArrayList<String>();
        listaNombres.add(v1);
        listaNombres.add(v2);
        return listaNombres;
}

public void borrarAmbito(){
    int index = ambito.lastIndexOf(":");
    if (index != -1) {
         String texto = ambito;
        texto = texto.substring(0, index);
        ambito = texto;
    }
}

public void agregarAPolaca(String valor){
    if (polacaInversa.containsKey(ambito)) {
        ArrayList<String> a = tablaDeSimbolos.get(valor);
        if (a!= null && a.size() == 3){
            String uso = a.get(2);
            if(uso != "Nombre de parametro")
                polacaInversa.get(ambito).add(valor);
            //Los parametros formales los agregamos a la polaca inversa manualmente en las reglas
        }else
            polacaInversa.get(ambito).add(valor);
    }
    else{
        mainArreglo.add(valor);
    }
}
//----------------------------------------------------------------------------------------------------------IMPRESIONES
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
//#line 758 "Parser.java"
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
case 1:
//#line 18 "gramatica.y"
{modificarUsoTS(val_peek(3).sval, "Nombre de programa"); polacaInversa.put("MAIN", mainArreglo);}
break;
case 2:
//#line 19 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de programa");}
break;
case 3:
//#line 20 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura");}
break;
case 4:
//#line 21 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador final");}
break;
case 5:
//#line 22 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores");}
break;
case 6:
//#line 23 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO");}
break;
case 11:
//#line 32 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 14:
//#line 40 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:declaracion");}
break;
case 16:
//#line 44 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: if");}
break;
case 17:
//#line 45 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:if else");}
break;
case 18:
//#line 46 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:while");}
break;
case 19:
//#line 47 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
break;
case 20:
//#line 48 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
break;
case 21:
//#line 49 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:llamado de funcion");}
break;
case 22:
//#line 50 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:lambda");}
break;
case 23:
//#line 51 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
break;
case 24:
//#line 52 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
break;
case 25:
//#line 53 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: argumento inválido en print");}
break;
case 26:
//#line 54 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 27:
//#line 55 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 28:
//#line 56 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 29:
//#line 57 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 30:
//#line 58 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 31:
//#line 59 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 32:
//#line 60 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 33:
//#line 61 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 34:
//#line 62 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 35:
//#line 63 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta cuerpo en la iteracion");}
break;
case 36:
//#line 64 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 37:
//#line 65 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 38:
//#line 66 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 39:
//#line 67 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 40:
//#line 68 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else'");}
break;
case 41:
//#line 69 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'do' en iteracion");}
break;
case 48:
//#line 78 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta comparador en expresion");}
break;
case 62:
//#line 96 "gramatica.y"
{agregarAPolaca("+");}
break;
case 63:
//#line 97 "gramatica.y"
{agregarAPolaca("-");}
break;
case 64:
//#line 98 "gramatica.y"
{agregarAPolaca("*");}
break;
case 65:
//#line 99 "gramatica.y"
{agregarAPolaca("/");}
break;
case 67:
//#line 101 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: falta operando en expresion");}
break;
case 68:
//#line 104 "gramatica.y"
{agregarAPolaca(val_peek(0).sval);}
break;
case 69:
//#line 105 "gramatica.y"
{agregarAPolaca(val_peek(0).sval);}
break;
case 71:
//#line 109 "gramatica.y"
{agregarAPolaca("+");}
break;
case 72:
//#line 110 "gramatica.y"
{agregarAPolaca("-");}
break;
case 73:
//#line 111 "gramatica.y"
{agregarAPolaca("*");}
break;
case 74:
//#line 112 "gramatica.y"
{agregarAPolaca("/");}
break;
case 75:
//#line 115 "gramatica.y"
{ArrayList<String> b = new ArrayList<String>(); b.add(val_peek(0).sval); modificarTipoTS(b, val_peek(1).sval); modificarUsoTS(val_peek(0).sval, "Nombre de variable");}
break;
case 76:
//#line 116 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(0).obj; modificarTipoTS(a, val_peek(1).sval); modificarUsos(a, "Nombre de variable");}
break;
case 77:
//#line 117 "gramatica.y"
{String ambitoConFuncion = ambito; borrarAmbito(); limpiarPolaca(ambitoConFuncion);}
break;
case 78:
//#line 118 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
break;
case 79:
//#line 121 "gramatica.y"
{modificarUsoTS(val_peek(3).sval, "Nombre de funcion"); ambito = ambito + ":" + val_peek(3).sval; modificarAmbitosTS((ArrayList<String>)val_peek(1).obj); polacaInversa.put(ambito, (ArrayList<String>) val_peek(1).obj); }
break;
case 80:
//#line 124 "gramatica.y"
{ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add(val_peek(2).sval); arreglo.add(val_peek(0).sval); yyval = new ParserVal(crearArregloId(val_peek(2).sval, val_peek(0).sval)); }
break;
case 81:
//#line 125 "gramatica.y"
{ArrayList<String> arreglo = (ArrayList<String>) val_peek(2).obj; arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 82:
//#line 126 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 83:
//#line 127 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 84:
//#line 130 "gramatica.y"
{yyval = new ParserVal("ULONG");}
break;
case 88:
//#line 138 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta especificacion del parametro formal");}
break;
case 89:
//#line 141 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(2).obj; a.add(val_peek(0).sval); yyval = new ParserVal(a);}
break;
case 90:
//#line 142 "gramatica.y"
{ArrayList<String> a = new ArrayList<String>(); a.add(val_peek(0).sval); yyval = new ParserVal(a);}
break;
case 91:
//#line 143 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta ',' en declaracion de las variables");}
break;
case 92:
//#line 146 "gramatica.y"
{modificarUsoTS(val_peek(0).sval, "Nombre de parametro"); yyval = new ParserVal(val_peek(0).sval);}
break;
case 93:
//#line 147 "gramatica.y"
{modificarUsoTS(val_peek(0).sval, "Nombre de parametro"); yyval = new ParserVal(val_peek(0).sval);}
break;
case 94:
//#line 148 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta nombre del parametro formal");}
break;
case 95:
//#line 149 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta nombre del parametro formal");}
break;
case 96:
//#line 150 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta tipo del parametro formal");}
break;
case 97:
//#line 151 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta tipo del parametro formal");}
break;
case 98:
//#line 154 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion"); ArrayList<String> a = new ArrayList<String>(); a.add(val_peek(2).sval); modificarTipoTS(a, val_peek(3).sval); modificarUsos(a, "Nombre de variable"); agregarAPolaca(val_peek(2).sval); agregarAPolaca(":=");}
break;
case 99:
//#line 155 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion"); agregarAPolaca(val_peek(2).sval); agregarAPolaca(":=");}
break;
case 100:
//#line 156 "gramatica.y"
{verificar_cantidades(val_peek(2), val_peek(0)); System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple"); agregarAPolaca("=");}
break;
case 101:
//#line 157 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple"); agregarAPolaca("=");}
break;
case 102:
//#line 160 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 103:
//#line 161 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 104:
//#line 162 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
break;
case 105:
//#line 165 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval); }
break;
case 106:
//#line 166 "gramatica.y"
{String name = val_peek(2).sval + "." + val_peek(0).sval; yyval = new ParserVal(name);}
break;
case 107:
//#line 169 "gramatica.y"
{aLex.agregarCteNegativaTablaDeSimbolos(val_peek(0).sval); }
break;
case 108:
//#line 170 "gramatica.y"
{String cte = "-" + val_peek(0).sval; aLex.agregarCteNegativaTablaDeSimbolos(cte); }
break;
case 111:
//#line 175 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura en funcion lambda");}
break;
case 112:
//#line 176 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de cierre en funcion lambda");}
break;
case 113:
//#line 177 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores en funcion lambda");}
break;
//#line 1227 "Parser.java"
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
