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
    0,    0,    1,    1,    1,    1,    2,    2,    3,    3,
    5,    5,    5,    5,    5,    5,    7,    7,    7,    7,
    8,    8,    8,   14,   14,   14,   14,   10,   10,   15,
   16,   16,   16,   16,   16,    9,    9,    9,   18,   19,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   19,   19,   19,   19,   19,   17,   17,   17,   17,   17,
   17,   17,   20,   20,   20,   20,   20,   20,   20,   20,
   20,   20,   20,   20,   20,    6,    6,    6,    6,    6,
   24,   24,   24,   24,   13,   13,   13,   13,   13,   13,
   25,   25,   25,   28,   26,   26,   26,   26,    4,    4,
    4,    4,    4,   30,   31,   31,   32,   32,   32,   33,
   33,   33,   34,   34,   34,   34,   29,   29,   29,   11,
   11,   12,   12,   12,   35,   35,   23,   23,   23,   22,
   22,   21,   27,   27,
};
final static short yylen[] = {                            2,
    2,    1,    3,    3,    3,    3,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    5,    5,    4,    4,
    5,    5,    1,    4,    4,    4,    3,    4,    1,    4,
    6,    6,    5,    3,    3,    5,    3,    1,    5,    6,
    6,    5,    8,    8,    7,    2,    4,    6,    8,    4,
    7,    4,    2,    5,    5,    4,    7,    7,    6,    1,
    3,    5,    7,    3,    6,    3,    3,    3,    3,    3,
    3,    1,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    1,    5,    4,    4,    4,    1,
    4,    3,    3,    3,    3,    3,    3,    3,    1,    3,
    1,    1,    1,    4,    1,    1,    1,    1,    3,    3,
    1,    3,    3,    2,    5,    4,    3,    1,    2,    3,
    2,    1,    2,    2,    2,    2,    5,    3,    2,    8,
    8,    3,    3,    1,    2,    2,    1,    3,    2,    1,
    3,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    2,    0,    0,    0,  142,    0,
    0,    0,    0,    8,    9,   10,   11,   12,   13,   14,
   15,   16,    0,   23,    0,   29,    0,   38,    0,    0,
   90,  111,    0,  134,    1,    0,  143,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   72,  101,
   99,  102,  103,    0,    0,    0,    0,    0,    0,    6,
    5,    7,    0,    0,  135,    0,    0,    0,    0,   46,
    0,    0,    0,    0,    0,    0,    0,  136,  114,    4,
    3,    0,  144,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  141,    0,    0,  137,  132,   35,    0,   34,
    0,    0,   37,  112,    0,    0,  109,    0,    0,    0,
    0,  118,  122,  113,  110,    0,    0,  133,    0,    0,
    0,    0,   95,   96,   97,   98,    0,    0,    0,    0,
    0,    0,  100,    0,    0,    0,    0,   26,    0,    0,
    0,    0,   30,    0,    0,    0,   88,    0,  139,   28,
   50,    0,   47,    0,    0,  126,    0,  125,  123,  121,
  116,    0,  119,   87,   89,    0,  104,    0,    0,    0,
   39,    0,    0,    0,    0,    0,   42,   21,   22,   17,
   18,    0,   33,    0,    0,  138,   36,   86,  115,  120,
  117,  128,    0,    0,    0,   48,    0,   41,    0,   40,
    0,   32,   31,    0,    0,   51,    0,    0,    0,   45,
    0,    0,  127,   49,   44,   43,  130,  131,
};
final static short yydgoto[] = {                          4,
    5,   13,   14,   15,   16,   17,   18,   19,   20,   21,
   22,   23,   47,   24,   25,   26,   48,   27,   28,   49,
  130,   50,  115,   31,   51,  101,   52,   53,  140,   32,
   33,  131,  132,  133,   34,
};
final static short yysindex[] = {                      -237,
  385, -179,  385,    0,    0,  414, -275, -271,    0,  431,
 -246, -203, -113,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -17,    0, -166,    0, -250,    0, -260,  -63,
    0,    0, -179,    0,    0,  -83,    0, -270, -194,  134,
  134,  134,  134,  134,  134,  448,  480, -131,    0,    0,
    0,    0,    0,  388,  134,  448,   14, -205, -192,    0,
    0,    0, -145, -122,    0, -236, -124, -101,  -69,    0,
 -170,  132, -201, -197,  134, -145, -122,    0,    0,    0,
    0,  134,    0,   11,   11,   11,   11,   11,   11,  -86,
  134,  134,  134,  134,  134,  134,  134,  134,  134,  134,
  -54, -179,  -99,  -66,  -47,  459, -136,  463, -197,   56,
 -179,  -32,    0,  -36,   -9,    0,    0,    0,  -28,    0,
  -23, -146,    0,    0,  134,  132,    0, -122,  -62,  -64,
 -229,    0,    0,    0,    0,   45,  346,    0,  390, -266,
 -126,   16,    0,    0,    0,    0,   11,   11,   11,   11,
   11,   11,    0,  104, -179,  -18,   -7,    0,    3,   18,
   24, -179,    0,   26, -179, -179,    0, -145,    0,    0,
    0,   35,    0,   87, -225,    0, -122,    0,    0,    0,
    0,  132,    0,    0,    0, -122,    0,  134,  -84,   60,
    0, -179,   66, -179,   68,  106,    0,    0,    0,    0,
    0,   81,    0,   82,   85,    0,    0,    0,    0,    0,
    0,    0,  401,   94,  148,    0,  150,    0,  151,    0,
  110,    0,    0,  137, -122,    0,  129,  136,  144,    0,
  154,  159,    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -167,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   -8,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -42,    0,    0,
    0,    0,    0,    0,    0,    0, -159,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    4,    0,
 -193,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -95,  -75,  -73,  -70,  -13,  306,    0,
  182,  186,  187,  188,  308,  311,  313,  317,  320,  322,
    0,    0,    0,    0,   30,    0,    0,    0,  467,    0,
    0,    0,    0,    0,   38,    0,    0,    0,    0,    0,
   46,   72,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   80,   88,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  342,  345,  351,  354,
  356,  358,    0,    0,    0,  114,  122,    0,  130,  156,
  164,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  172,    0,  198,    0,    0, -224,    0,    0,    0,
    0,    0,    0,    0,    0, -221,    0,    0,    0,  206,
    0,    0,  214,    0,  240,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  248,    0,    0,    0,    0,    0,    0,
  256,    0,    0,    0,    0,    0,  282,  290,  298,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,  443,   74,    0,    0,    0,    0,    0,    0,    0,
    0,  -15,   39,    0,    0,    0,   -5,    0,    0,    0,
   12,   -1,  371,    0,  232,    0,  -59,    0,    0,    0,
    0,  323, -130,    0,    0,
};
final static int YYTABLESIZE=760;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         30,
  183,   30,   35,  116,   57,   54,   71,   68,   69,   55,
   82,   30,   29,   73,   29,  187,  116,   58,    1,    1,
   72,   65,  188,   59,   29,   67,  128,   74,   78,    2,
  128,  124,    9,   79,   30,   70,    9,  124,  129,  107,
   90,   58,  129,  124,  183,    3,    3,   29,  103,  118,
  110,  211,  181,  109,  134,  169,  209,  124,    9,  182,
  129,  113,  117,  182,  124,   11,  119,  129,  122,   11,
   83,   65,   78,  140,  114,  138,    1,  169,   84,   85,
   86,   87,   88,   89,  135,  124,   62,   64,  140,    1,
  142,   77,  106,  108,  140,  140,   85,   66,  125,  140,
  140,  140,  154,    3,   85,   65,  140,   78,  206,   62,
  126,  164,  172,  136,  140,  127,    3,   58,  140,   37,
  139,  140,   85,   85,    1,   39,  176,  178,  180,    1,
   11,  189,  190,  147,  148,  149,  150,  151,  152,  173,
  177,  191,   60,    6,   11,  160,    7,    8,    9,   10,
  102,    3,   64,   11,    1,  196,    3,  121,  155,  156,
   80,  120,  202,  174,  232,  204,  205,   12,   80,    1,
   61,    1,   80,    6,  214,  210,    7,    8,    9,   10,
   82,    3,   79,   11,  212,   81,   80,   80,   82,  215,
   79,  179,  217,   81,  219,  141,    3,   12,    3,    9,
   81,  153,   11,   11,   11,   75,   82,   82,   79,   79,
   76,   81,   81,  140,  140,  157,  123,  140,  140,  140,
  140,  140,  231,  233,  140,   77,  213,  140,  140,  140,
  140,  165,  140,  140,  140,  140,  140,  140,  158,  140,
  140,  140,   83,  140,  140,  166,  140,   60,   60,   11,
   83,   60,   60,   60,   60,   37,   63,  170,   60,   53,
   53,   39,  171,   53,   53,   53,   53,  197,   83,   83,
   53,   64,   60,  192,  193,   60,  167,  111,  198,  168,
   91,   92,   93,   94,   53,   27,   27,   53,  199,   27,
   27,   27,   27,   93,   93,  112,   27,   93,   93,   93,
   93,   64,   64,  200,   93,   64,   64,   64,   64,  201,
   27,  203,   64,   27,   91,   92,   93,   94,   93,  162,
  207,   93,  143,  144,  145,  146,   64,   61,   61,   64,
  184,   61,   61,   61,   61,   92,   92,  163,   61,   92,
   92,   92,   92,   94,   94,  216,   92,   94,   94,   94,
   94,  218,   61,  220,   94,   61,   91,   92,   93,   94,
   92,  194,  195,   92,  221,  224,  222,  223,   94,   56,
   56,   94,  208,   56,   56,   56,   56,   24,   24,  226,
   56,   24,   24,   24,   24,   25,   25,  128,   24,   25,
   25,   25,   25,    9,   56,  230,   25,   56,   37,  129,
   38,   37,   24,   11,   39,   24,  227,   39,  228,  229,
   25,   19,   19,   25,  234,   19,   19,   19,   19,   20,
   20,  235,   19,   20,   20,   20,   20,   52,   52,  236,
   20,   52,   52,   52,   52,  237,   19,  105,   52,   19,
  238,  106,  107,  108,   20,   36,  137,   20,  175,    0,
    0,    0,   52,   91,   91,   52,    0,   91,   91,   91,
   91,   62,   62,    0,   91,   62,   62,   62,   62,   55,
   55,    0,   62,   55,   55,   55,   55,    0,   91,    0,
   55,   91,    0,    0,    0,    0,   62,    0,    0,   62,
    0,    0,    0,    0,   55,   54,   54,   55,    0,   54,
   54,   54,   54,   65,   65,    0,   54,   65,   65,   65,
   65,   59,   59,    0,   65,   59,   59,   59,   59,    0,
   54,    0,   59,   54,    0,    0,    0,    0,   65,    0,
    0,   65,    0,    0,    0,    0,   59,   63,   63,   59,
    0,   63,   63,   63,   63,   58,   58,    0,   63,   58,
   58,   58,   58,   57,   57,    0,   58,   57,   57,   57,
   57,   84,   63,   74,   57,   63,   76,    0,   73,   84,
   58,   74,   75,   58,   76,   77,   73,   78,   57,    0,
   75,   57,    0,   77,    0,   78,    0,   84,   84,   74,
   74,    0,   76,   76,   73,   73,    0,   67,   75,   75,
   69,   77,   77,   78,   78,   67,   66,    0,   69,   68,
   37,   70,    0,   71,   66,    0,   39,   68,    0,   70,
    0,   71,    0,   67,   67,    0,   69,   69,    0,    0,
    0,  185,   66,   66,  168,   68,   68,   70,   70,   71,
   71,    6,    0,    0,    7,    8,    9,   10,    0,    0,
    0,   11,   37,  104,   38,    0,    0,    0,   39,   91,
   92,   93,   94,    0,    0,   12,    0,    0,    0,  105,
   91,   92,   93,   94,    0,    0,  186,    0,   37,    0,
   38,    0,    0,    0,   39,    0,    0,  225,   40,   41,
   42,   43,   44,   45,   46,   37,    0,   38,    0,    0,
    0,   39,    0,    0,    0,   40,   41,   42,   43,   44,
   45,   56,   37,    0,   38,    0,    0,    0,   39,    0,
    0,    0,   40,   41,   42,   43,   44,   45,   91,   92,
   93,   94,   91,   92,   93,   94,  101,  101,  101,  101,
  159,    0,    0,    0,  161,    0,    0,    0,  101,   91,
   92,   93,   94,    0,   95,   96,   97,   98,   99,  100,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
  131,    3,    2,   63,   10,  281,  267,  258,  259,  281,
  281,   13,    1,   29,    3,  282,   76,  288,  256,  256,
  281,   23,  289,   12,   13,   25,  256,   29,   30,  267,
  256,  256,  262,   33,   36,  286,  262,  262,  268,   55,
   46,  288,  268,  268,  175,  283,  283,   36,   48,  286,
   56,  182,  282,   55,  256,  115,  282,  282,  262,  289,
  282,  267,   64,  289,  289,  267,   66,  289,   68,  267,
  265,   73,   74,  267,  267,   77,  256,  137,   40,   41,
   42,   43,   44,   45,  286,  256,   13,  289,  256,  256,
   90,  289,   54,   55,  262,  289,  256,  264,  269,  267,
  268,  269,  102,  283,  264,  107,  274,  109,  168,   36,
  281,  111,  259,   75,  282,  286,  283,  288,  286,  265,
   82,  289,  282,  283,  256,  271,  128,  129,  130,  256,
  267,  258,  259,   95,   96,   97,   98,   99,  100,  286,
  129,  141,  256,  257,  267,  282,  260,  261,  262,  263,
  282,  283,  289,  267,  256,  155,  283,  259,  258,  259,
  256,  286,  162,  125,  224,  165,  166,  281,  264,  256,
  284,  256,  256,  257,  259,  177,  260,  261,  262,  263,
  256,  283,  256,  267,  186,  256,  282,  283,  264,  189,
  264,  256,  192,  264,  194,  282,  283,  281,  283,  262,
  284,  256,  267,  267,  267,  269,  282,  283,  282,  283,
  274,  282,  283,  256,  257,  282,  286,  260,  261,  262,
  263,  264,  224,  225,  267,  289,  188,  270,  271,  272,
  273,  264,  275,  276,  277,  278,  279,  280,  286,  282,
  283,  284,  256,  286,  287,  282,  289,  256,  257,  267,
  264,  260,  261,  262,  263,  265,  274,  286,  267,  256,
  257,  271,  286,  260,  261,  262,  263,  286,  282,  283,
  267,  289,  281,  258,  259,  284,  286,  264,  286,  289,
  270,  271,  272,  273,  281,  256,  257,  284,  286,  260,
  261,  262,  263,  256,  257,  282,  267,  260,  261,  262,
  263,  256,  257,  286,  267,  260,  261,  262,  263,  286,
  281,  286,  267,  284,  270,  271,  272,  273,  281,  264,
  286,  284,   91,   92,   93,   94,  281,  256,  257,  284,
  286,  260,  261,  262,  263,  256,  257,  282,  267,  260,
  261,  262,  263,  256,  257,  286,  267,  260,  261,  262,
  263,  286,  281,  286,  267,  284,  270,  271,  272,  273,
  281,  258,  259,  284,  259,  281,  286,  286,  281,  256,
  257,  284,  286,  260,  261,  262,  263,  256,  257,  286,
  267,  260,  261,  262,  263,  256,  257,  256,  267,  260,
  261,  262,  263,  262,  281,  286,  267,  284,  265,  268,
  267,  265,  281,  267,  271,  284,  259,  271,  259,  259,
  281,  256,  257,  284,  286,  260,  261,  262,  263,  256,
  257,  286,  267,  260,  261,  262,  263,  256,  257,  286,
  267,  260,  261,  262,  263,  282,  281,  256,  267,  284,
  282,  256,  256,  256,  281,    3,   76,  284,  126,   -1,
   -1,   -1,  281,  256,  257,  284,   -1,  260,  261,  262,
  263,  256,  257,   -1,  267,  260,  261,  262,  263,  256,
  257,   -1,  267,  260,  261,  262,  263,   -1,  281,   -1,
  267,  284,   -1,   -1,   -1,   -1,  281,   -1,   -1,  284,
   -1,   -1,   -1,   -1,  281,  256,  257,  284,   -1,  260,
  261,  262,  263,  256,  257,   -1,  267,  260,  261,  262,
  263,  256,  257,   -1,  267,  260,  261,  262,  263,   -1,
  281,   -1,  267,  284,   -1,   -1,   -1,   -1,  281,   -1,
   -1,  284,   -1,   -1,   -1,   -1,  281,  256,  257,  284,
   -1,  260,  261,  262,  263,  256,  257,   -1,  267,  260,
  261,  262,  263,  256,  257,   -1,  267,  260,  261,  262,
  263,  256,  281,  256,  267,  284,  256,   -1,  256,  264,
  281,  264,  256,  284,  264,  256,  264,  256,  281,   -1,
  264,  284,   -1,  264,   -1,  264,   -1,  282,  283,  282,
  283,   -1,  282,  283,  282,  283,   -1,  256,  282,  283,
  256,  282,  283,  282,  283,  264,  256,   -1,  264,  256,
  265,  256,   -1,  256,  264,   -1,  271,  264,   -1,  264,
   -1,  264,   -1,  282,  283,   -1,  282,  283,   -1,   -1,
   -1,  286,  282,  283,  289,  282,  283,  282,  283,  282,
  283,  257,   -1,   -1,  260,  261,  262,  263,   -1,   -1,
   -1,  267,  265,  266,  267,   -1,   -1,   -1,  271,  270,
  271,  272,  273,   -1,   -1,  281,   -1,   -1,   -1,  282,
  270,  271,  272,  273,   -1,   -1,  287,   -1,  265,   -1,
  267,   -1,   -1,   -1,  271,   -1,   -1,  287,  275,  276,
  277,  278,  279,  280,  281,  265,   -1,  267,   -1,   -1,
   -1,  271,   -1,   -1,   -1,  275,  276,  277,  278,  279,
  280,  281,  265,   -1,  267,   -1,   -1,   -1,  271,   -1,
   -1,   -1,  275,  276,  277,  278,  279,  280,  270,  271,
  272,  273,  270,  271,  272,  273,  270,  271,  272,  273,
  282,   -1,   -1,   -1,  282,   -1,   -1,   -1,  282,  270,
  271,  272,  273,   -1,  275,  276,  277,  278,  279,  280,
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
"prog : ID bloque",
"prog : bloque",
"bloque : LLAVEA sentencias LLAVEC",
"bloque : LLAVEA sentencias error",
"bloque : error sentencias LLAVEC",
"bloque : error sentencias error",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : sentencia_return",
"sentencia_ejecutable : sentencia_print",
"sentencia_ejecutable : sentencia_if",
"sentencia_ejecutable : sentencia_while",
"sentencia_ejecutable : expresion_lambda",
"sentencia_return : RETURN PARENTESISA lista_id PARENTESISC PUNTOCOMA",
"sentencia_return : RETURN PARENTESISA expresiones PARENTESISC PUNTOCOMA",
"sentencia_return : RETURN PARENTESISA lista_id PARENTESISC",
"sentencia_return : RETURN PARENTESISA expresiones PARENTESISC",
"sentencia_print : PRINT PARENTESISA CADENA PARENTESISC PUNTOCOMA",
"sentencia_print : PRINT PARENTESISA expresiones PARENTESISC PUNTOCOMA",
"sentencia_print : sentencia_print_error",
"sentencia_print_error : PRINT PARENTESISA CADENA PARENTESISC",
"sentencia_print_error : PRINT PARENTESISA expresiones PARENTESISC",
"sentencia_print_error : PRINT PARENTESISA PARENTESISC PUNTOCOMA",
"sentencia_print_error : PRINT PARENTESISA PARENTESISC",
"sentencia_while : header_while DO bloque PUNTOCOMA",
"sentencia_while : sentencia_while_error",
"header_while : WHILE PARENTESISA condicion PARENTESISC",
"sentencia_while_error : WHILE condicion PARENTESISC DO bloque PUNTOCOMA",
"sentencia_while_error : WHILE PARENTESISA condicion DO bloque PUNTOCOMA",
"sentencia_while_error : WHILE condicion DO bloque PUNTOCOMA",
"sentencia_while_error : header_while bloque PUNTOCOMA",
"sentencia_while_error : header_while DO PUNTOCOMA",
"sentencia_if : header_if ELSE bloque ENDIF PUNTOCOMA",
"sentencia_if : header_if ENDIF PUNTOCOMA",
"sentencia_if : sentencia_if_error",
"header_if : IF PARENTESISA condicion PARENTESISC bloque",
"sentencia_if_error : IF condicion PARENTESISC bloque ENDIF PUNTOCOMA",
"sentencia_if_error : IF PARENTESISA condicion bloque ENDIF PUNTOCOMA",
"sentencia_if_error : IF condicion bloque ENDIF PUNTOCOMA",
"sentencia_if_error : IF condicion PARENTESISC bloque ELSE bloque ENDIF PUNTOCOMA",
"sentencia_if_error : IF PARENTESISA condicion bloque ELSE bloque ENDIF PUNTOCOMA",
"sentencia_if_error : IF condicion bloque ELSE bloque ENDIF PUNTOCOMA",
"sentencia_if_error : header_if PUNTOCOMA",
"sentencia_if_error : header_if ELSE bloque PUNTOCOMA",
"sentencia_if_error : IF PARENTESISA condicion PARENTESISC ENDIF PUNTOCOMA",
"sentencia_if_error : IF PARENTESISA condicion PARENTESISC ELSE bloque ENDIF PUNTOCOMA",
"sentencia_if_error : header_if ELSE ENDIF PUNTOCOMA",
"sentencia_if_error : IF PARENTESISA condicion PARENTESISC ELSE ENDIF PUNTOCOMA",
"sentencia_if_error : header_if ELSE bloque ENDIF",
"sentencia_if_error : header_if ENDIF",
"sentencia_if_error : IF condicion PARENTESISC bloque ENDIF",
"sentencia_if_error : IF PARENTESISA condicion bloque ENDIF",
"sentencia_if_error : IF condicion bloque ENDIF",
"sentencia_if_error : IF condicion PARENTESISC bloque ELSE bloque ENDIF",
"sentencia_if_error : IF PARENTESISA condicion bloque ELSE bloque ENDIF",
"sentencia_if_error : IF condicion bloque ELSE bloque ENDIF",
"sentencia_if_error : header_if",
"sentencia_if_error : header_if ELSE bloque",
"sentencia_if_error : IF PARENTESISA condicion PARENTESISC ENDIF",
"sentencia_if_error : IF PARENTESISA condicion PARENTESISC ELSE bloque ENDIF",
"sentencia_if_error : header_if ELSE ENDIF",
"sentencia_if_error : IF PARENTESISA condicion PARENTESISC ELSE ENDIF",
"condicion : expresiones MAYOR expresiones",
"condicion : expresiones MAYIG expresiones",
"condicion : expresiones MENOR expresiones",
"condicion : expresiones MENIG expresiones",
"condicion : expresiones IGIG expresiones",
"condicion : expresiones DIF expresiones",
"condicion : condicion_error",
"condicion_error : expresiones MAYOR",
"condicion_error : expresiones MAYIG",
"condicion_error : expresiones MENOR",
"condicion_error : expresiones MENIG",
"condicion_error : expresiones IGIG",
"condicion_error : expresiones DIF",
"condicion_error : MAYOR expresiones",
"condicion_error : MAYIG expresiones",
"condicion_error : MENOR expresiones",
"condicion_error : MENIG expresiones",
"condicion_error : IGIG expresiones",
"condicion_error : DIF expresiones",
"condicion_error : expresiones",
"asignacion : tipo ID ASIGN expresiones PUNTOCOMA",
"asignacion : identificador ASIGN expresiones PUNTOCOMA",
"asignacion : lista_id IGUAL lista_cte PUNTOCOMA",
"asignacion : identificador IGUAL lista_cte PUNTOCOMA",
"asignacion : asignacion_error",
"asignacion_error : tipo ID ASIGN expresiones",
"asignacion_error : identificador ASIGN expresiones",
"asignacion_error : lista_id IGUAL lista_cte",
"asignacion_error : identificador IGUAL lista_cte",
"expresiones : expresiones MAS termino",
"expresiones : expresiones MENOS termino",
"expresiones : expresiones AST termino",
"expresiones : expresiones BARRA termino",
"expresiones : termino",
"expresiones : expresiones operador error",
"termino : identificador",
"termino : tipo_cte",
"termino : llamado_funcion",
"llamado_funcion : ID PARENTESISA parametros_reales PARENTESISC",
"operador : MAS",
"operador : MENOS",
"operador : AST",
"operador : BARRA",
"sentencia_declarativa : tipo ID PUNTOCOMA",
"sentencia_declarativa : tipo lista_id PUNTOCOMA",
"sentencia_declarativa : funcion",
"sentencia_declarativa : tipo ID error",
"sentencia_declarativa : tipo lista_id error",
"funcion : header_funcion bloque",
"header_funcion : tipo ID PARENTESISA parametros_formales PARENTESISC",
"header_funcion : tipo PARENTESISA parametros_formales PARENTESISC",
"parametros_formales : parametros_formales COMA parametro_formal",
"parametros_formales : parametro_formal",
"parametros_formales : parametros_formales parametro_formal",
"parametro_formal : CVR tipo identificador",
"parametro_formal : tipo identificador",
"parametro_formal : parametro_formal_error",
"parametro_formal_error : tipo error",
"parametro_formal_error : CVR tipo",
"parametro_formal_error : CVR identificador",
"parametro_formal_error : error identificador",
"parametros_reales : parametros_reales COMA expresiones FLECHA identificador",
"parametros_reales : expresiones FLECHA identificador",
"parametros_reales : expresiones FLECHA",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC bloque PARENTESISA identificador PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC bloque PARENTESISA tipo_cte PARENTESISC",
"lista_id : lista_id COMA identificador",
"lista_id : identificador COMA identificador",
"lista_id : lista_id_error",
"lista_id_error : lista_id identificador",
"lista_id_error : identificador identificador",
"lista_cte : tipo_cte",
"lista_cte : lista_cte COMA tipo_cte",
"lista_cte : lista_cte tipo_cte",
"identificador : ID",
"identificador : ID PUNTO ID",
"tipo : ULONG",
"tipo_cte : CTE",
"tipo_cte : MENOS CTE",
};

//#line 271 "gramatica.y"

/* -------------------------------------------------------------------------------------------------------------CODIGO AUXILIAR ----------------------------------------------------*/

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
ArrayList<String> sentencias = new ArrayList<String>();
public void agregarSentencia(String s){
    sentencias.add(s);
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
        if (name != null){
            String clave = ambito+":"+name;
            if (tablaDeSimbolos.containsKey(clave)) {
                ArrayList<String> fila = tablaDeSimbolos.get(clave);
                fila.set(1, tipo);
            }else{
                System.out.println("(modificarTipoTS) Error, la clave" + clave + " no existe en la tabla de simbolos");
            }
        }
    }
}

public void modificarUsos(ArrayList<String> lista, String uso){
        for (String a: lista){
            modificarUsoTS(a, uso);
        }
}

public void modificarUsoTS(String aux, String uso){
    if (aux != null){
        String clave = ambito+":"+aux;
        if (tablaDeSimbolos.containsKey(clave)) {
            ArrayList<String> fila = tablaDeSimbolos.get(clave);
            //Se agrega el uso en el indice = 2
            fila.add(uso);
        }else{
            System.out.println("(modificarUsoTS) Error, la clave" + clave + " no existe en la tabla de simbolos");
        }
    }
}
public void modificarAmbitosTS(ArrayList<String> a){
    int index = ambito.lastIndexOf(":");
    String ambitoAfuera = ambito.substring(0, index);

    for (String parametro : a){
        if (parametro != null){
            //Lo busco en la ts y modifico su clave para que incluya el ambito de la funcion definida
            ArrayList<String> aux = tablaDeSimbolos.get(ambitoAfuera+":"+parametro);
            tablaDeSimbolos.remove(ambitoAfuera+":"+parametro);
            tablaDeSimbolos.put(ambito+":"+parametro, aux);
        }
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

public void imprimirSentencias(){
    if (!sentencias.isEmpty()) {
        for (String s : sentencias) {
            System.out.println(s);
        }
    }else{
        System.out.println("El programa esta vacio");
    }
}


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
//#line 854 "Parser.java"
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
//#line 19 "gramatica.y"
{agregarSentencia("LINEA: "+aLex.getNroLinea()+" SENTENCIA: Nombre de programa");  modificarUsoTS(val_peek(1).sval, "Nombre de programa"); polacaInversa.put("MAIN", mainArreglo); }
break;
case 2:
//#line 20 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de programa");}
break;
case 4:
//#line 25 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador final");}
break;
case 5:
//#line 26 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador inicial");}
break;
case 6:
//#line 27 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores");}
break;
case 12:
//#line 42 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Return");}
break;
case 13:
//#line 43 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Print");}
break;
case 14:
//#line 44 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: If"); agregarAPolaca("if");}
break;
case 15:
//#line 45 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: While");}
break;
case 16:
//#line 46 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Lambda");}
break;
case 17:
//#line 50 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(2).obj; agregarListaAPolaca(a); agregarAPolaca("return");}
break;
case 18:
//#line 51 "gramatica.y"
{agregarAPolaca("return");}
break;
case 19:
//#line 52 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 20:
//#line 53 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 21:
//#line 57 "gramatica.y"
{agregarAPolaca(val_peek(2).sval); agregarAPolaca("print");}
break;
case 22:
//#line 58 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Print"); agregarAPolaca("print");}
break;
case 24:
//#line 63 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 25:
//#line 64 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 26:
//#line 65 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTÁCTICO: Falta argumento en print");}
break;
case 27:
//#line 66 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTÁCTICO: Falta argumento en print y falta ';' ");}
break;
case 30:
//#line 75 "gramatica.y"
{}
break;
case 31:
//#line 79 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 32:
//#line 80 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 33:
//#line 81 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 34:
//#line 82 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'do' en iteracion");}
break;
case 35:
//#line 83 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTÁCTICO: Falta cuerpo de la iteracion");}
break;
case 36:
//#line 87 "gramatica.y"
{agregarAPolaca("else"); agregarBifurcacion("then"); agregarAPolaca("cuerpo");}
break;
case 37:
//#line 88 "gramatica.y"
{agregarAPolaca("cuerpo"); acomodarBifurcacion();}
break;
case 39:
//#line 93 "gramatica.y"
{agregarAPolaca("then"); agregarBifurcacion("cond");}
break;
case 40:
//#line 97 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 41:
//#line 98 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 42:
//#line 99 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 43:
//#line 100 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 44:
//#line 101 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 45:
//#line 102 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 46:
//#line 103 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 47:
//#line 104 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 48:
//#line 105 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 49:
//#line 106 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 50:
//#line 107 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else'");}
break;
case 51:
//#line 108 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else', 'then'");}
break;
case 52:
//#line 109 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 53:
//#line 110 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 54:
//#line 111 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion y falta ';' ");}
break;
case 55:
//#line 112 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion y falta ';' ");}
break;
case 56:
//#line 113 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion y falta ';' ");}
break;
case 57:
//#line 114 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion y falta ';' ");}
break;
case 58:
//#line 115 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion y falta ';' ");}
break;
case 59:
//#line 116 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion y falta ';' ");}
break;
case 60:
//#line 117 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif' y falta ';' ");}
break;
case 61:
//#line 118 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif' y falta ';' ");}
break;
case 62:
//#line 119 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then' y falta ';' ");}
break;
case 63:
//#line 120 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then' y falta ';' ");}
break;
case 64:
//#line 121 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else' y falta ';' ");}
break;
case 65:
//#line 122 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else', 'then' y falta ';' ");}
break;
case 66:
//#line 126 "gramatica.y"
{agregarAPolaca(">"); agregarAPolaca("cond");}
break;
case 67:
//#line 127 "gramatica.y"
{agregarAPolaca(">="); agregarAPolaca("cond");}
break;
case 68:
//#line 128 "gramatica.y"
{agregarAPolaca("<"); agregarAPolaca("cond");}
break;
case 69:
//#line 129 "gramatica.y"
{agregarAPolaca(">="); agregarAPolaca("cond");}
break;
case 70:
//#line 130 "gramatica.y"
{agregarAPolaca("=="); agregarAPolaca("cond");}
break;
case 71:
//#line 131 "gramatica.y"
{agregarAPolaca("=!"); agregarAPolaca("cond");}
break;
case 72:
//#line 132 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Condicion incompleta");}
break;
case 86:
//#line 152 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion y asignacion"); ArrayList<String> a = new ArrayList<String>(); a.add(val_peek(3).sval); modificarTipoTS(a, val_peek(4).sval); modificarUsos(a, "Nombre de variable"); agregarAPolaca(val_peek(3).sval); agregarAPolaca(":=");}
break;
case 87:
//#line 153 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion"); agregarAPolaca(val_peek(3).sval); agregarAPolaca(":=");}
break;
case 88:
//#line 154 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion multiple"); ArrayList<String> l1 = (ArrayList<String>)val_peek(3).obj; ArrayList<String> l3 = (ArrayList<String>)val_peek(1).obj; verificar_cantidades(l1, l3); agregarListaAPolaca(l3); agregarListaAPolaca(l1); agregarAPolaca("=");}
break;
case 89:
//#line 155 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion multiple"); agregarListaAPolaca((ArrayList<String>)val_peek(1).obj); agregarAPolaca(val_peek(3).sval); agregarAPolaca("=");}
break;
case 90:
//#line 156 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 95:
//#line 167 "gramatica.y"
{agregarAPolaca("+");}
break;
case 96:
//#line 168 "gramatica.y"
{agregarAPolaca("-");}
break;
case 97:
//#line 169 "gramatica.y"
{agregarAPolaca("*");}
break;
case 98:
//#line 170 "gramatica.y"
{agregarAPolaca("/");}
break;
case 100:
//#line 172 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta operando en expresion");}
break;
case 101:
//#line 176 "gramatica.y"
{agregarAPolaca(val_peek(0).sval);}
break;
case 102:
//#line 177 "gramatica.y"
{agregarAPolaca(val_peek(0).sval);}
break;
case 104:
//#line 182 "gramatica.y"
{agregarAPolaca(val_peek(3).sval); agregarAPolaca("call");}
break;
case 105:
//#line 186 "gramatica.y"
{agregarAPolaca("+");}
break;
case 106:
//#line 187 "gramatica.y"
{agregarAPolaca("-");}
break;
case 107:
//#line 188 "gramatica.y"
{agregarAPolaca("*");}
break;
case 108:
//#line 189 "gramatica.y"
{agregarAPolaca("/");}
break;
case 109:
//#line 193 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de variable"); ArrayList<String> b = new ArrayList<String>(); b.add(val_peek(1).sval); modificarTipoTS(b, val_peek(2).sval); modificarUsoTS(val_peek(1).sval, "Nombre de variable");}
break;
case 110:
//#line 194 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de variables"); ArrayList<String> a = (ArrayList<String>)val_peek(1).obj; modificarTipoTS(a, val_peek(2).sval); modificarUsos(a, "Nombre de variable");}
break;
case 111:
//#line 195 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de funcion");}
break;
case 112:
//#line 196 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 113:
//#line 197 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 114:
//#line 201 "gramatica.y"
{String ambitoConFuncion = ambito; borrarAmbito(); limpiarPolaca(ambitoConFuncion);}
break;
case 115:
//#line 205 "gramatica.y"
{modificarUsoTS(val_peek(3).sval, "Nombre de funcion"); ambito = ambito + ":" + val_peek(3).sval; modificarAmbitosTS((ArrayList<String>)val_peek(1).obj); polacaInversa.put(ambito, (ArrayList<String>) val_peek(1).obj); }
break;
case 116:
//#line 206 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
break;
case 117:
//#line 210 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(2).obj; a.add(val_peek(0).sval); yyval = new ParserVal(a);}
break;
case 118:
//#line 211 "gramatica.y"
{ArrayList<String> a = new ArrayList<String>(); a.add(val_peek(0).sval); yyval = new ParserVal(a);}
break;
case 119:
//#line 212 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta ',' en declaracion de las variables");}
break;
case 120:
//#line 216 "gramatica.y"
{modificarUsoTS(val_peek(0).sval, "Nombre de parametro"); yyval = new ParserVal(val_peek(0).sval);}
break;
case 121:
//#line 217 "gramatica.y"
{modificarUsoTS(val_peek(0).sval, "Nombre de parametro"); yyval = new ParserVal(val_peek(0).sval);}
break;
case 122:
//#line 218 "gramatica.y"
{yyval = new ParserVal();}
break;
case 123:
//#line 222 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta nombre del parametro formal");}
break;
case 124:
//#line 223 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta nombre del parametro formal");}
break;
case 125:
//#line 224 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta tipo del parametro formal");}
break;
case 126:
//#line 225 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta tipo del parametro formal");}
break;
case 127:
//#line 229 "gramatica.y"
{agregarAPolaca(val_peek(0).sval); agregarAPolaca("->");}
break;
case 128:
//#line 230 "gramatica.y"
{agregarAPolaca(val_peek(0).sval); agregarAPolaca("->");}
break;
case 129:
//#line 231 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta especificacion del parametro formal");}
break;
case 132:
//#line 240 "gramatica.y"
{ArrayList<String> arreglo = (ArrayList<String>) val_peek(2).obj; arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 133:
//#line 241 "gramatica.y"
{ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add(val_peek(2).sval); arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo); }
break;
case 134:
//#line 242 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 137:
//#line 251 "gramatica.y"
{ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 138:
//#line 252 "gramatica.y"
{ArrayList<String> arreglo = (ArrayList<String>) val_peek(2).obj; arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 139:
//#line 253 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
break;
case 140:
//#line 257 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval); }
break;
case 141:
//#line 258 "gramatica.y"
{String name = val_peek(2).sval + "." + val_peek(0).sval; yyval = new ParserVal(name);}
break;
case 142:
//#line 262 "gramatica.y"
{yyval = new ParserVal("ULONG");}
break;
case 143:
//#line 266 "gramatica.y"
{aLex.agregarCteNegativaTS(val_peek(0).sval);}
break;
case 144:
//#line 267 "gramatica.y"
{String cte = "-" + val_peek(0).sval; aLex.agregarCteNegativaTS(cte);}
break;
//#line 1447 "Parser.java"
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
