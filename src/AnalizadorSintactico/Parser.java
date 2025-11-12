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
    8,    8,   14,    9,    9,    9,    9,    9,    9,    9,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   11,   11,   11,   11,   11,   11,   16,
   16,   16,   17,   17,   17,   17,    6,    6,    6,    6,
   21,   10,   10,   10,   10,   20,   12,   23,   23,   23,
   22,   22,   22,   24,   24,   24,   24,   24,   24,    7,
    7,    7,    7,   25,   25,   25,   18,   18,   19,   19,
   13,   13,   13,   13,   13,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    2,    2,    1,    2,    2,
    1,    2,    2,    1,    1,    1,    8,    4,    4,    1,
    1,    4,    4,    4,    8,    7,    8,   12,   11,   12,
    8,    7,    8,    6,    1,    5,    5,    9,    3,    8,
    6,    2,    7,    3,    3,    3,    3,    3,    3,    1,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    1,    3,    3,    3,    3,    1,    3,    1,
    1,    1,    1,    1,    1,    1,    2,    2,    4,    8,
    5,    3,    3,    2,    2,    1,    4,    5,    3,    3,
    3,    1,    2,    3,    2,    2,    3,    3,    2,    4,
    3,    3,    3,    1,    3,    2,    1,    3,    1,    2,
   10,   10,    9,    9,    8,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    6,    0,    0,    0,   86,    0,
    0,    0,    0,    0,    8,    0,    0,   11,   14,   15,
   16,    0,   20,   21,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,
    7,   12,    9,   13,   10,    0,    0,    0,   84,    0,
   42,    0,    0,    0,   85,    0,    0,    0,    0,    0,
    2,  109,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   72,   50,   68,   70,   71,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  108,    0,    1,
  104,    0,   83,   39,    0,    0,    0,   82,    0,    0,
    0,    0,  110,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   24,   22,   23,   18,   19,    0,
    0,    0,    0,    0,   87,    0,    0,    0,  106,    0,
    0,    0,    0,    0,   92,    0,    0,   79,    0,    0,
   64,   65,   66,   67,    0,    0,    0,    0,    0,    0,
   69,    0,   37,    0,    0,    0,    0,    0,    0,    0,
   90,   89,    0,    0,    0,  105,    0,   99,    0,    0,
   96,   95,    0,    0,   93,   81,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   34,    0,    0,    0,    0,
    0,   41,   98,   97,   94,    0,   91,    0,    0,    0,
   43,    0,   26,    0,    0,   32,    0,    0,   88,    0,
    0,    0,    0,    0,    0,   27,    0,   25,    0,    0,
   33,   31,   40,   17,    0,    0,  115,    0,   80,    0,
    0,   38,    0,  114,    0,    0,  113,    0,    0,    0,
  111,  112,    0,    0,   29,   30,   28,
};
final static short yydgoto[] = {                          4,
   14,   15,   16,   17,   18,   19,   20,   21,   70,   22,
   71,   23,   24,   25,   73,   74,  122,   26,   76,   27,
   28,  144,   87,  145,   92,
};
final static short yysindex[] = {                      -115,
 -280,  421,  438,    0,    0, -254, -255, -191,    0,  -82,
 -201, -189,  438, -156,    0, -240, -236,    0,    0,    0,
    0,  -98,    0,    0, -136,  -59, -248, -188, -131,  446,
  446,  119,  -51,  446,  446,  -51, -130, -127, -118,    0,
    0,    0,    0,    0,    0, -116,  -12, -109,    0, -159,
    0,  -51,  -12, -109,    0, -111,  -64, -176,  -88,  438,
    0,    0,  -90,  -51,  -51,  -51,  -51,  -51,  -51, -243,
  457,    0,    0,    0,    0,    0,  -20,  -78,  -73,  181,
 -186,  222,  -88, -194, -259,   27, -196,    0,  -63,    0,
    0,  -83,    0,    0,  438,  255,  -83,    0,   -4,  -51,
   -4,   78,    0,  255,  255,  255,  255,  255,  255,  -62,
  -54,  -51,  -51,  -51,  -51,  -51,  -51,  -51,  -51,  -51,
  -51,  -25, -150,  438,    0,    0,    0,    0,    0,  -30,
  -23,  -10, -179, -173,    0,  -51,  429,  -12,    0,  158,
 -109,   14, -129, -244,    0,  255, -213,    0,  438,  438,
    0,    0,    0,    0,  255,  255,  255,  255,  255,  255,
    0,   -8,    0,  438,  166,   12,   54,  438,   61, -249,
    0,    0,   36,  438,  174,    0,    1,    0, -109, -100,
    0,    0,   63,   -4,    0,    0,  199,  207,  438,  215,
   32,  438,  438,  240,  438,    0,  438, -109,  248,   39,
   34,    0,    0,    0,    0,  438,    0,   44,   53,  256,
    0,   65,    0,  281,  289,    0,  297,  322,    0,   39,
   55,   79,  -12,  330,   81,    0,   97,    0,  128,  438,
    0,    0,    0,    0,  106,  173,    0,  113,    0,  438,
  438,    0,  338,    0,  126,  130,    0,  363,  371,  163,
    0,    0,  165,  171,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  127,    0,    0,  417,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -228,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  432,    0,
    0,    0,    0,    0,    0, -253,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -214, -226, -216,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -185,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  263,    0,    0,    0,    0,    0,    0,    0,
    0, -212,    0,    0,    0, -208, -204,    0,    0,    0,
    0,    0,    0, -105, -102,  -71,  -28,  -14,   10,    0,
    0,  187,  189,  190,  192,   60,   94,   96,   99,  353,
  357,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -202,    0,    0,    0,    0,
    0,    0,    0,    0,  381,  385,  387,  390,  392,  394,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -197,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   -3,    8,    0,    0,    0,    0,    0,    0,  450,  324,
   -1,  253,    0,    0,    0,  454,    0,  213,  -36,   13,
    0,  356,    0, -143,  412,
};
final static int YYTABLESIZE=737;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
  185,   30,  107,  185,  132,    5,  196,   56,  107,   39,
   91,  141,  110,  107,  107,   42,   91,    9,   57,   44,
  107,   41,  133,  142,   38,   32,   31,   35,  107,   78,
   80,   82,  107,  197,   86,  107,   41,  183,  111,   77,
  207,  107,  141,  102,  184,   43,   41,  101,    9,   45,
   96,  103,  107,  100,  142,  139,  102,   35,   36,   78,
  139,  130,  104,  105,  106,  107,  108,  109,  186,   77,
   63,  107,    9,  102,  107,  184,  169,  101,   63,   36,
   46,  103,  171,  100,  170,  135,   37,  131,   36,   33,
   46,  140,  136,   46,   60,  128,   63,   63,  146,   94,
    6,  176,   48,    7,    8,    9,   10,  162,  163,   41,
   11,  143,   48,  143,  155,  156,  157,  158,  159,  160,
  165,   50,   51,   95,   12,    6,  181,   40,    7,    8,
    9,   10,  164,  175,  173,   11,   88,   46,    6,   89,
    1,    7,    8,    9,   10,  187,  188,   41,   11,   12,
   58,    2,   61,   60,  180,  204,  143,   46,   58,  143,
  190,   60,   12,  222,  194,   90,   46,    3,   46,   99,
  199,   37,   41,   34,  103,   47,   58,   58,   46,   60,
   60,   62,   41,  235,   57,  210,  238,   63,  214,  215,
   48,  217,   57,  218,   41,   41,  143,   41,   35,  246,
   54,   41,  224,  125,  100,  138,   41,   46,  126,   52,
   57,   57,   38,   62,   53,   11,  101,   41,  137,   63,
  149,   41,   41,   37,   41,   41,  243,   59,  150,   54,
  161,   41,   38,  166,   49,   59,  248,  249,   55,   59,
  167,   61,   75,   75,   75,   83,   75,   75,   75,   61,
   41,  141,   62,   59,   59,   41,   41,    9,   63,  202,
   93,  123,  124,  142,   75,   62,   98,   61,   61,  179,
   49,   55,  168,   62,  189,    9,   75,   75,   75,   75,
   75,   75,   72,   72,   72,   72,   72,   72,   72,  212,
  213,   62,   62,   49,  192,   55,  112,  113,  114,  115,
    9,  225,  226,   62,   72,  112,  113,  114,  115,   63,
  227,  228,   75,  134,  223,   52,   72,   72,   72,   72,
   72,   72,  198,   52,   75,   75,   75,   75,   75,   75,
   75,   75,   75,   75,    6,  236,  193,    7,    8,    9,
   10,   52,   52,  195,   11,  206,  172,  230,   75,   54,
   58,   51,   72,  178,   53,  182,   81,   54,   12,   51,
  237,  148,   53,  240,   72,   72,   72,   72,   72,   72,
   72,   72,   72,   72,   78,   54,   54,   51,   51,  241,
   53,   53,  107,   62,   79,   11,  242,  244,   72,   63,
  107,  203,  205,  107,  247,  107,  107,  107,  107,  107,
  107,  107,  107,  107,  107,  107,  107,  251,  107,  107,
  219,  252,  107,  107,    6,  107,    5,    7,    8,    9,
   10,  255,    6,  256,   11,    7,    8,    9,   10,  257,
    6,    4,   11,    7,    8,    9,   10,   62,   12,   46,
   11,  177,   73,   63,   74,   75,   12,   76,  245,  191,
  112,  113,  114,  115,  200,    6,  147,  201,    7,    8,
    9,   10,  127,    6,   97,   11,    7,    8,    9,   10,
    0,    6,    0,   11,    7,    8,    9,   10,    0,   12,
   77,   11,  208,   84,   85,    0,    0,   12,    0,    0,
  209,  112,  113,  114,  115,   12,    6,    0,  211,    7,
    8,    9,   10,  129,    6,    0,   11,    7,    8,    9,
   10,    0,    6,    0,   11,    7,    8,    9,   10,    0,
   12,    0,   11,  216,  112,  113,  114,  115,  220,    0,
    0,  221,   70,   70,   70,   70,   12,    6,    0,  229,
    7,    8,    9,   10,   70,    6,    0,   11,    7,    8,
    9,   10,    0,    6,    0,   11,    7,    8,    9,   10,
    0,   12,    0,   11,  231,  151,  152,  153,  154,   12,
    0,    0,  232,    0,    0,    0,    0,   12,    6,    0,
  233,    7,    8,    9,   10,    0,    6,    0,   11,    7,
    8,    9,   10,    0,    6,    0,   11,    7,    8,    9,
   10,    0,   12,    0,   11,  234,    0,    0,   55,    0,
   12,    0,   56,  239,    0,    0,   55,    0,   12,    6,
   56,  250,    7,    8,    9,   10,    0,    6,    0,   11,
    7,    8,    9,   10,   55,   55,   45,   11,   56,   56,
   47,    0,   44,   12,   45,   46,  253,   48,   47,   49,
   44,   12,    0,   46,  254,   48,    0,   49,    0,    0,
    0,    0,   45,   45,    0,    0,   47,   47,   44,   44,
    0,   46,   46,   48,   48,   49,   49,    6,    0,    0,
    7,    8,    9,   10,    0,    6,    0,   11,    7,    8,
    9,   10,    0,    0,    6,   11,    0,    7,    8,    9,
   10,   12,    0,   13,   11,    0,    0,    0,    0,   12,
   62,  174,   11,    0,    0,    0,   63,    0,   12,    0,
   64,   65,   66,   67,   68,   69,  112,  113,  114,  115,
    0,  116,  117,  118,  119,  120,  121,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
  144,  256,  256,  147,  264,  286,  256,  256,  262,   13,
   47,  256,  256,  267,  268,  256,   53,  262,  267,  256,
  274,   14,  282,  268,   12,  281,  281,  256,  282,  256,
   32,   33,  286,  283,   36,  289,   29,  282,  282,  256,
  184,  256,  256,  256,  289,  286,   39,  256,  262,  286,
   52,  256,  267,  256,  268,   92,   60,  286,  256,  286,
   97,  256,   64,   65,   66,   67,   68,   69,  282,  286,
  256,  286,  262,  286,  289,  289,  256,  286,  264,  281,
  267,  286,  256,  286,  264,  282,  288,  282,  286,  281,
  267,   95,  289,  267,  283,  282,  282,  283,  100,  259,
  257,  138,  289,  260,  261,  262,  263,  258,  259,  102,
  267,   99,  289,  101,  116,  117,  118,  119,  120,  121,
  124,  258,  259,  283,  281,  257,  256,  284,  260,  261,
  262,  263,  283,  137,  136,  267,  267,  267,  257,  267,
  256,  260,  261,  262,  263,  149,  150,  140,  267,  281,
  256,  267,  284,  256,  142,  256,  144,  267,  264,  147,
  164,  264,  281,  200,  168,  284,  267,  283,  267,  281,
  174,  288,  165,  256,  265,  274,  282,  283,  267,  282,
  283,  265,  175,  220,  256,  189,  223,  271,  192,  193,
  289,  195,  264,  197,  187,  188,  184,  190,  281,  236,
  289,  194,  206,  282,  269,  289,  199,  267,  282,  269,
  282,  283,  200,  265,  274,  267,  281,  210,  282,  271,
  283,  214,  215,  288,  217,  218,  230,  256,  283,  289,
  256,  224,  220,  264,   22,  264,  240,  241,   26,   27,
  264,  256,   30,   31,   32,   33,   34,   35,   36,  264,
  243,  256,  265,  282,  283,  248,  249,  262,  271,  259,
   48,  282,  283,  268,   52,  256,   54,  282,  283,  256,
   58,   59,  283,  264,  283,  262,   64,   65,   66,   67,
   68,   69,   30,   31,   32,   33,   34,   35,   36,  258,
  259,  282,  283,   81,  283,   83,  270,  271,  272,  273,
  262,  258,  259,  265,   52,  270,  271,  272,  273,  271,
  258,  259,  100,  287,  281,  256,   64,   65,   66,   67,
   68,   69,  287,  264,  112,  113,  114,  115,  116,  117,
  118,  119,  120,  121,  257,  281,  283,  260,  261,  262,
  263,  282,  283,  283,  267,  283,  134,  283,  136,  256,
   27,  256,  100,  141,  256,  143,   33,  264,  281,  264,
  282,  284,  264,  283,  112,  113,  114,  115,  116,  117,
  118,  119,  120,  121,  256,  282,  283,  282,  283,  283,
  282,  283,  256,  265,  266,  267,  259,  282,  136,  271,
  264,  179,  180,  267,  282,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,  282,  282,  283,
  198,  282,  286,  287,  257,  289,    0,  260,  261,  262,
  263,  259,  257,  259,  267,  260,  261,  262,  263,  259,
  257,    0,  267,  260,  261,  262,  263,  265,  281,  267,
  267,  284,  256,  271,  256,  256,  281,  256,  236,  284,
  270,  271,  272,  273,  281,  257,  101,  284,  260,  261,
  262,  263,  282,  257,   53,  267,  260,  261,  262,  263,
   -1,  257,   -1,  267,  260,  261,  262,  263,   -1,  281,
   31,  267,  284,   34,   35,   -1,   -1,  281,   -1,   -1,
  284,  270,  271,  272,  273,  281,  257,   -1,  284,  260,
  261,  262,  263,  282,  257,   -1,  267,  260,  261,  262,
  263,   -1,  257,   -1,  267,  260,  261,  262,  263,   -1,
  281,   -1,  267,  284,  270,  271,  272,  273,  281,   -1,
   -1,  284,  270,  271,  272,  273,  281,  257,   -1,  284,
  260,  261,  262,  263,  282,  257,   -1,  267,  260,  261,
  262,  263,   -1,  257,   -1,  267,  260,  261,  262,  263,
   -1,  281,   -1,  267,  284,  112,  113,  114,  115,  281,
   -1,   -1,  284,   -1,   -1,   -1,   -1,  281,  257,   -1,
  284,  260,  261,  262,  263,   -1,  257,   -1,  267,  260,
  261,  262,  263,   -1,  257,   -1,  267,  260,  261,  262,
  263,   -1,  281,   -1,  267,  284,   -1,   -1,  256,   -1,
  281,   -1,  256,  284,   -1,   -1,  264,   -1,  281,  257,
  264,  284,  260,  261,  262,  263,   -1,  257,   -1,  267,
  260,  261,  262,  263,  282,  283,  256,  267,  282,  283,
  256,   -1,  256,  281,  264,  256,  284,  256,  264,  256,
  264,  281,   -1,  264,  284,  264,   -1,  264,   -1,   -1,
   -1,   -1,  282,  283,   -1,   -1,  282,  283,  282,  283,
   -1,  282,  283,  282,  283,  282,  283,  257,   -1,   -1,
  260,  261,  262,  263,   -1,  257,   -1,  267,  260,  261,
  262,  263,   -1,   -1,  257,  267,   -1,  260,  261,  262,
  263,  281,   -1,  283,  267,   -1,   -1,   -1,   -1,  281,
  265,  283,  267,   -1,   -1,   -1,  271,   -1,  281,   -1,
  275,  276,  277,  278,  279,  280,  270,  271,  272,  273,
   -1,  275,  276,  277,  278,  279,  280,
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
"sentencia_ejecutable : sentencia_if",
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
"sentencia_ejecutable : header_if",
"sentencia_ejecutable : header_if ELSE LLAVEA sentencias LLAVEC",
"sentencia_ejecutable : IF PARENTESISA condicion PARENTESISC ENDIF",
"sentencia_ejecutable : IF PARENTESISA condicion PARENTESISC ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia_ejecutable : header_if ELSE ENDIF",
"sentencia_ejecutable : WHILE PARENTESISA condicion PARENTESISC error LLAVEA sentencias LLAVEC",
"sentencia_if : header_if ELSE LLAVEA sentencias LLAVEC ENDIF",
"sentencia_if : header_if ENDIF",
"header_if : IF PARENTESISA condicion PARENTESISC LLAVEA sentencias LLAVEC",
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

//#line 186 "gramatica.y"

/* CODIGO AUXILIAR */

AnalisisLexico aLex;
private HashMap<String, ArrayList<String>> tablaDeSimbolos = new HashMap<String, ArrayList<String>>();

public void setAlex(AnalisisLexico a){
    this.aLex = a;

}

public void verificar_cantidades(ArrayList<String> l1, ArrayList<String> l2){
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

public void agregarListaAPolaca(ArrayList<String> a){
    for (String s: a){
        agregarAPolaca(s);
    }
}

public void agregarBifurcacion(String flag){
    ArrayList<String> a = new ArrayList<String>();
    if (polacaInversa.containsKey(ambito)) {
        a = polacaInversa.get(ambito);
    }else{
        a = mainArreglo;
    }
    int i = a.size() - 1;
    while ( i >= 0 && !flag.equals(a.get(i)) ){
        i--;
    }
    Integer saltoBF = a.size() - 1 + 3;
    Integer saltoBI = a.size() - 1 + 1;

    if (flag.equals("cond")) {
       a.set(i, (saltoBF).toString());  //porque agregamos dos bifurcaciones
       a.add(i + 1, "BF");
    }else{
        a.set(i, (saltoBI).toString());  //porque agregamos una bifurcaciones
        a.add(i + 1, "BI");
    }
}

public void acomodarBifurcacion(){
    ArrayList<String> a = new ArrayList<String>();
    if (polacaInversa.containsKey(ambito)) {
        a = polacaInversa.get(ambito);
    }else{
        a = mainArreglo;
    }
    int i = a.size() - 1;
    while ( i >= 0 && !a.get(i).equals("BF")  ){
        i--;
    }
    Integer num = Integer.parseInt(a.get(i - 1)) - 2;

    a.set(i-1,num.toString());
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
//#line 796 "Parser.java"
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
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: if"); agregarAPolaca("if");}
break;
case 17:
//#line 45 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:while");}
break;
case 18:
//#line 46 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");ArrayList<String> a = (ArrayList<String>)val_peek(1).obj; agregarListaAPolaca(a); agregarAPolaca("return");}
break;
case 19:
//#line 47 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return"); agregarAPolaca("return");}
break;
case 20:
//#line 48 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:llamado de funcion");}
break;
case 21:
//#line 49 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:lambda");}
break;
case 22:
//#line 50 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print"); agregarAPolaca(val_peek(1).sval); agregarAPolaca("print");}
break;
case 23:
//#line 51 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print"); agregarAPolaca("print");}
break;
case 24:
//#line 52 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: argumento inválido en print");}
break;
case 25:
//#line 53 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 26:
//#line 54 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 27:
//#line 55 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 28:
//#line 56 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 29:
//#line 57 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 30:
//#line 58 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 31:
//#line 59 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 32:
//#line 60 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 33:
//#line 61 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 34:
//#line 62 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta cuerpo en la iteracion");}
break;
case 35:
//#line 63 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 36:
//#line 64 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 37:
//#line 65 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 38:
//#line 66 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 39:
//#line 67 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else'");}
break;
case 40:
//#line 68 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'do' en iteracion");}
break;
case 41:
//#line 71 "gramatica.y"
{agregarAPolaca("else"); agregarBifurcacion("then"); agregarAPolaca("cuerpo");}
break;
case 42:
//#line 72 "gramatica.y"
{agregarAPolaca("cuerpo"); acomodarBifurcacion();}
break;
case 43:
//#line 75 "gramatica.y"
{agregarAPolaca("then"); agregarBifurcacion("cond");}
break;
case 44:
//#line 78 "gramatica.y"
{agregarAPolaca(">"); agregarAPolaca("cond");}
break;
case 45:
//#line 79 "gramatica.y"
{agregarAPolaca(">="); agregarAPolaca("cond");}
break;
case 46:
//#line 80 "gramatica.y"
{agregarAPolaca("<"); agregarAPolaca("cond");}
break;
case 47:
//#line 81 "gramatica.y"
{agregarAPolaca(">="); agregarAPolaca("cond");}
break;
case 48:
//#line 82 "gramatica.y"
{agregarAPolaca("=="); agregarAPolaca("cond");}
break;
case 49:
//#line 83 "gramatica.y"
{agregarAPolaca("=!"); agregarAPolaca("cond");}
break;
case 50:
//#line 84 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta comparador en expresion");}
break;
case 64:
//#line 102 "gramatica.y"
{agregarAPolaca("+");}
break;
case 65:
//#line 103 "gramatica.y"
{agregarAPolaca("-");}
break;
case 66:
//#line 104 "gramatica.y"
{agregarAPolaca("*");}
break;
case 67:
//#line 105 "gramatica.y"
{agregarAPolaca("/");}
break;
case 69:
//#line 107 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: falta operando en expresion");}
break;
case 70:
//#line 110 "gramatica.y"
{agregarAPolaca(val_peek(0).sval);}
break;
case 71:
//#line 111 "gramatica.y"
{agregarAPolaca(val_peek(0).sval);}
break;
case 73:
//#line 115 "gramatica.y"
{agregarAPolaca("+");}
break;
case 74:
//#line 116 "gramatica.y"
{agregarAPolaca("-");}
break;
case 75:
//#line 117 "gramatica.y"
{agregarAPolaca("*");}
break;
case 76:
//#line 118 "gramatica.y"
{agregarAPolaca("/");}
break;
case 77:
//#line 121 "gramatica.y"
{ArrayList<String> b = new ArrayList<String>(); b.add(val_peek(0).sval); modificarTipoTS(b, val_peek(1).sval); modificarUsoTS(val_peek(0).sval, "Nombre de variable");}
break;
case 78:
//#line 122 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(0).obj; modificarTipoTS(a, val_peek(1).sval); modificarUsos(a, "Nombre de variable");}
break;
case 79:
//#line 123 "gramatica.y"
{String ambitoConFuncion = ambito; borrarAmbito(); limpiarPolaca(ambitoConFuncion);}
break;
case 80:
//#line 124 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
break;
case 81:
//#line 127 "gramatica.y"
{modificarUsoTS(val_peek(3).sval, "Nombre de funcion"); ambito = ambito + ":" + val_peek(3).sval; modificarAmbitosTS((ArrayList<String>)val_peek(1).obj); polacaInversa.put(ambito, (ArrayList<String>) val_peek(1).obj); }
break;
case 82:
//#line 130 "gramatica.y"
{ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add(val_peek(2).sval); arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo); }
break;
case 83:
//#line 131 "gramatica.y"
{ArrayList<String> arreglo = (ArrayList<String>) val_peek(2).obj; arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 84:
//#line 132 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 85:
//#line 133 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 86:
//#line 136 "gramatica.y"
{yyval = new ParserVal("ULONG");}
break;
case 87:
//#line 139 "gramatica.y"
{agregarAPolaca(val_peek(3).sval); agregarAPolaca("call");}
break;
case 88:
//#line 142 "gramatica.y"
{agregarAPolaca(val_peek(0).sval); agregarAPolaca("->");}
break;
case 89:
//#line 143 "gramatica.y"
{agregarAPolaca(val_peek(0).sval); agregarAPolaca("->");}
break;
case 90:
//#line 144 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta especificacion del parametro formal");}
break;
case 91:
//#line 147 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(2).obj; a.add(val_peek(0).sval); yyval = new ParserVal(a);}
break;
case 92:
//#line 148 "gramatica.y"
{ArrayList<String> a = new ArrayList<String>(); a.add(val_peek(0).sval); yyval = new ParserVal(a);}
break;
case 93:
//#line 149 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta ',' en declaracion de las variables");}
break;
case 94:
//#line 152 "gramatica.y"
{modificarUsoTS(val_peek(0).sval, "Nombre de parametro"); yyval = new ParserVal(val_peek(0).sval);}
break;
case 95:
//#line 153 "gramatica.y"
{modificarUsoTS(val_peek(0).sval, "Nombre de parametro"); yyval = new ParserVal(val_peek(0).sval);}
break;
case 96:
//#line 154 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta nombre del parametro formal");}
break;
case 97:
//#line 155 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta nombre del parametro formal");}
break;
case 98:
//#line 156 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta tipo del parametro formal");}
break;
case 99:
//#line 157 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta tipo del parametro formal");}
break;
case 100:
//#line 160 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion"); ArrayList<String> a = new ArrayList<String>(); a.add(val_peek(2).sval); modificarTipoTS(a, val_peek(3).sval); modificarUsos(a, "Nombre de variable"); agregarAPolaca(val_peek(2).sval); agregarAPolaca(":=");}
break;
case 101:
//#line 161 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion"); agregarAPolaca(val_peek(2).sval); agregarAPolaca(":=");}
break;
case 102:
//#line 162 "gramatica.y"
{ArrayList<String> l1 = (ArrayList<String>)val_peek(2).obj; ArrayList<String> l3 = (ArrayList<String>)val_peek(0).obj; verificar_cantidades(l1, l3); System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple"); agregarListaAPolaca(l3); agregarListaAPolaca(l1); agregarAPolaca("=");}
break;
case 103:
//#line 163 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple"); agregarListaAPolaca((ArrayList<String>)val_peek(0).obj); agregarAPolaca(val_peek(2).sval); agregarAPolaca("=");}
break;
case 104:
//#line 166 "gramatica.y"
{ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 105:
//#line 167 "gramatica.y"
{ArrayList<String> arreglo = (ArrayList<String>) val_peek(2).obj; arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 106:
//#line 168 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
break;
case 107:
//#line 171 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval); }
break;
case 108:
//#line 172 "gramatica.y"
{String name = val_peek(2).sval + "." + val_peek(0).sval; yyval = new ParserVal(name);}
break;
case 109:
//#line 175 "gramatica.y"
{aLex.agregarCteNegativaTablaDeSimbolos(val_peek(0).sval); }
break;
case 110:
//#line 176 "gramatica.y"
{String cte = "-" + val_peek(0).sval; aLex.agregarCteNegativaTablaDeSimbolos(cte); }
break;
case 113:
//#line 181 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura en funcion lambda");}
break;
case 114:
//#line 182 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de cierre en funcion lambda");}
break;
case 115:
//#line 183 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores en funcion lambda");}
break;
//#line 1309 "Parser.java"
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
