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
   17,   16,   16,   16,   16,   16,    9,    9,    9,   19,
   20,   20,   20,   20,   20,   20,   20,   20,   20,   20,
   20,   20,   20,   20,   20,   20,   20,   20,   20,   20,
   20,   20,   20,   20,   20,   20,   18,   18,   18,   18,
   18,   18,   18,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   21,    6,    6,    6,    6,
    6,   25,   25,   25,   25,   13,   13,   13,   13,   13,
   13,   26,   26,   26,   29,   27,   27,   27,   27,    4,
    4,    4,    4,    4,    4,   31,   32,   32,   33,   33,
   33,   34,   34,   34,   35,   35,   35,   35,   30,   30,
   30,   11,   36,   37,   37,   12,   12,   12,   38,   38,
   24,   24,   24,   23,   23,   22,   28,   28,
};
final static short yylen[] = {                            2,
    2,    1,    3,    3,    3,    3,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    5,    5,    4,    4,
    5,    5,    1,    4,    4,    4,    3,    4,    1,    4,
    1,    6,    6,    5,    3,    3,    5,    3,    1,    5,
    6,    6,    5,    8,    8,    7,    2,    4,    6,    8,
    4,    7,    4,    2,    5,    5,    4,    7,    7,    6,
    1,    3,    5,    7,    3,    6,    3,    3,    3,    3,
    3,    3,    1,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    1,    5,    4,    4,    4,
    1,    4,    3,    3,    3,    3,    3,    3,    3,    1,
    3,    1,    1,    1,    4,    1,    1,    1,    1,    3,
    3,    1,    3,    3,    5,    2,    5,    4,    3,    1,
    2,    3,    2,    1,    2,    2,    2,    2,    5,    3,
    2,    4,    5,    3,    3,    3,    3,    1,    2,    2,
    1,    3,    2,    1,    3,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    2,    0,    0,    0,  146,   31,
    0,    0,    0,    8,    9,   10,   11,   12,   13,   14,
   15,   16,    0,   23,    0,   29,    0,    0,   39,    0,
    0,   91,  112,    0,    0,  138,    1,    0,  147,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   73,  102,  100,  103,  104,    0,    0,    0,    0,    6,
    5,    7,    0,    0,  139,    0,    0,    0,    0,    0,
    0,   47,    0,    0,    0,    0,    0,    0,    0,  140,
  116,    0,    4,    3,    0,  148,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  145,    0,    0,  141,  136,   36,    0,   35,
    0,    0,    0,    0,    0,   38,  113,    0,    0,  110,
    0,    0,    0,    0,    0,  120,  124,  114,  111,    0,
    0,  137,    0,    0,    0,    0,    0,    0,   96,   97,
   98,   99,    0,    0,    0,    0,    0,    0,  101,    0,
    0,    0,    0,   26,    0,    0,    0,    0,   89,    0,
  143,   28,    0,   30,    0,    0,   51,    0,   48,    0,
    0,    0,  128,  127,    0,  125,  123,  118,    0,  121,
   88,   90,    0,    0,  132,    0,  105,    0,    0,    0,
   40,    0,    0,    0,    0,    0,   43,   21,   22,   17,
   18,  133,  142,    0,   34,    0,   37,   87,  117,  115,
  122,  119,  134,  135,  130,    0,    0,    0,   49,    0,
   42,    0,   41,    0,   33,   32,    0,   52,    0,    0,
    0,   46,  129,   50,   45,   44,
};
final static short yydgoto[] = {                          4,
    5,   13,   14,   15,   16,   17,   18,   19,   20,   21,
   22,   23,   49,   24,   25,   26,   27,   50,   28,   29,
   51,  134,   52,  115,   32,   53,  104,   54,   55,  146,
   33,   34,  135,  136,  137,   35,  144,   36,
};
final static short yysindex[] = {                      -148,
  -81, -239,  -81,    0,    0,  420, -201, -196,    0,    0,
 -170, -161, -117,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -206,    0, -251,    0,  437, -234,    0, -208,
  377,    0,    0, -239, -239,    0,    0,  -15,    0, -270,
 -118,  -12,  -12,  -12,  -12,  -12,  -12,  465,  493, -217,
    0,    0,    0,    0,    0,   74,  -12, -116, -128,    0,
    0,    0,  -61, -103,    0, -241, -120,  465, -133, -130,
 -101,    0, -164,   -6, -246, -129,  -12,  -61, -103,    0,
    0, -113,    0,    0,  -12,    0,  484,  484,  484,  484,
  484,  484,  -84,  -12,  -12,  -12,  -12,  -12,  -12,  -12,
  -12,  -12,  -12,  -54, -239,   23,  -99,  -77,  411, -210,
  476, -129,    0,  -35,  371,    0,    0,    0,  -47,    0,
  -86, -239,  -13,  -28, -232,    0,    0,  -12,   -6,    0,
   18,   20, -248, -135, -198,    0,    0,    0,    0,  449,
  383,    0,  103,  -23,  403, -200, -131,   39,    0,    0,
    0,    0,  484,  484,  484,  484,  484,  484,    0,   53,
 -239,  -18,  -16,    0,    6,   13,   28,   34,    0,  -61,
    0,    0, -239,    0,   29, -239,    0,   35,    0,  453,
 -175,   40,    0,    0,   38,    0,    0,    0,   -6,    0,
    0,    0,   45,   52,    0, -103,    0,  -12,   27,   42,
    0, -239,   57, -239,   58,   96,    0,    0,    0,    0,
    0,    0,    0,   64,    0,   71,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  407,   77,  110,    0,  113,
    0,  114,    0,   93,    0,    0, -103,    0,   98,   99,
  100,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -236,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    4,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -49,
    0,    0,    0,    0,    0,    0,    0,    0, -187,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   17,    0,  -92,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -95,  -93,  -91,  -63,
  -39,   -7,    0,  136,  141,  142,  143,  331,  333,  336,
  340,  342,  345,    0,    0,    0,    0,   33,    0,    0,
    0,  480,    0,    0,   46,    0,    0,    0,    0,    0,
    0,    0,    0,   62,   75,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   91,
  104,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  347,  356,  370,  376,  379,  381,    0,    0,
    0,  120,  133,    0,  149,  162,  178,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  191,    0,  207,
    0,  -73,    0,    0, -152,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -192,    0,    0,    0,  220,
    0,    0,  236,    0,  249,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  265,    0,    0,    0,
    0,    0,    0,  278,    0,    0,    0,    0,  294,  307,
  323,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,  398,   -4,    0,    0,    0,    0,    0,    0,    0,
    0,   19,   56,    0,    0,    0,    0,  -20,    0,    0,
    0,    3,   -1,  324,    0,  680,    0,  -55,    0,    0,
    0,    0,  274, -134,    0,    0,    0,    0,
};
final static int YYTABLESIZE=777;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         31,
  190,   31,   37,   30,    1,   30,   69,  116,   62,  138,
   85,   31,   66,    9,    1,   30,    1,   58,  184,  144,
   11,   65,  116,   70,   71,   67,  178,   93,   76,   80,
  144,    3,  144,   62,   81,   82,   31,  144,    1,  139,
   30,    3,   64,    3,  118,  144,  190,  121,   75,  144,
  106,   72,  144,  179,  222,  112,   11,  132,   73,  171,
   11,  114,  117,    9,  105,    3,  119,   63,   86,  133,
  125,  166,   74,   65,   80,  110,   86,  142,   64,   56,
  132,  197,   64,  188,   57,  171,    9,  194,  198,  131,
  189,  127,  133,  148,   86,   86,  131,   87,   88,   89,
   90,   91,   92,  126,  128,  160,  219,    1,   65,  126,
   80,  109,  111,  189,  213,  126,  129,   58,    2,   59,
  186,  130,  175,  131,    1,    1,  199,  200,  124,  126,
  122,  187,  140,    9,    3,  185,  126,   11,   60,    6,
  145,  193,    7,    8,    9,   10,   86,  201,  123,   11,
  113,    3,    3,  153,  154,  155,  156,  157,  158,   79,
   81,  206,   83,   11,   80,  120,   61,  143,   81,   12,
   83,    1,   80,  214,  144,    6,  216,  173,    7,    8,
    9,   10,  163,  180,  126,   11,   81,   81,   83,   83,
   80,   80,   82,  145,  225,  174,  144,  147,    3,  228,
   82,  159,  230,   39,  232,   12,  144,  144,  164,   41,
  144,  144,  144,  144,  144,  145,   84,  144,   82,   82,
  144,  144,  144,  144,   84,  144,  144,  144,  144,  144,
  144,  168,  144,  144,  144,  243,  144,  144,  172,  144,
   83,    6,   84,   84,    7,    8,    9,   10,   85,  132,
  176,   11,   39,  226,   40,    9,   85,  177,   41,   61,
   61,  133,  195,   61,   61,   61,   61,  207,   84,  208,
   61,   12,   54,   54,   85,   85,   54,   54,   54,   54,
  161,  162,    1,   54,  182,  227,  183,   61,   27,   27,
   61,  209,   27,   27,   27,   27,  202,  203,  210,   27,
   54,   94,   94,   54,  221,   94,   94,   94,   94,    3,
  204,  205,   94,  211,  215,  212,   27,   65,   65,   27,
  217,   65,   65,   65,   65,  220,  223,  229,   65,   94,
   62,   62,   94,  224,   62,   62,   62,   62,   39,  107,
   40,   62,  231,  233,   41,   65,   93,   93,   65,  235,
   93,   93,   93,   93,  234,  108,  236,   93,   62,   95,
   95,   62,  238,   95,   95,   95,   95,   39,  239,   11,
   95,  240,  241,   41,   93,   57,   57,   93,  242,   57,
   57,   57,   57,  244,  245,  246,   57,   95,   24,   24,
   95,  106,   24,   24,   24,   24,  107,  108,  109,   24,
   38,  141,  181,   57,   25,   25,   57,    0,   25,   25,
   25,   25,    0,    0,    0,   25,   24,   19,   19,   24,
    0,   19,   19,   19,   19,    0,    0,    0,   19,    0,
    0,    0,   25,   20,   20,   25,    0,   20,   20,   20,
   20,    0,    0,    0,   20,   19,   53,   53,   19,    0,
   53,   53,   53,   53,    0,    0,    0,   53,    0,    0,
    0,   20,   92,   92,   20,    0,   92,   92,   92,   92,
    0,    0,    0,   92,   53,   63,   63,   53,    0,   63,
   63,   63,   63,    0,    0,    0,   63,    0,    0,    0,
   92,   56,   56,   92,    0,   56,   56,   56,   56,    0,
    0,    0,   56,   63,   55,   55,   63,    0,   55,   55,
   55,   55,    0,    0,    0,   55,    0,    0,    0,   56,
   66,   66,   56,    0,   66,   66,   66,   66,    0,    0,
    0,   66,   55,   60,   60,   55,    0,   60,   60,   60,
   60,    0,    0,    0,   60,    0,    0,    0,   66,   64,
   64,   66,    0,   64,   64,   64,   64,    0,    0,    0,
   64,   60,   59,   59,   60,    0,   59,   59,   59,   59,
    0,    0,    0,   59,    0,    0,    0,   64,   58,   58,
   64,    0,   58,   58,   58,   58,   75,    0,   77,   58,
   59,   74,    0,   59,   75,   76,   77,   78,    0,   74,
   79,    0,   68,   76,    0,   78,   58,    0,   79,   58,
   68,   70,   75,   75,   77,   77,    0,   74,   74,   70,
    0,   76,   76,   78,   78,   67,   79,   79,   68,   68,
    0,   69,    0,   67,   71,   39,   72,   70,   70,   69,
    0,   41,   71,   11,   72,   77,    0,   39,    0,    0,
   78,   67,   67,   41,    0,    0,  169,   69,   69,  170,
   71,   71,   72,   72,    0,   79,    0,    0,  192,    0,
    0,  170,   94,   95,   96,   97,   94,   95,   96,   97,
   94,   95,   96,   97,   39,    0,   40,    0,    0,  196,
   41,    0,  165,  237,   42,   43,   44,   45,   46,   47,
   48,   39,    0,   40,    0,    0,    0,   41,    0,    0,
    0,   42,   43,   44,   45,   46,   47,   68,   94,   95,
   96,   97,   94,   95,   96,   97,    0,    0,    0,   39,
    0,   40,    0,    0,  191,   41,    0,    0,  218,   42,
   43,   44,   45,   46,   47,   94,   95,   96,   97,  102,
  102,  102,  102,   94,   95,   96,   97,  167,    0,    0,
    0,  102,   94,   95,   96,   97,    0,   98,   99,  100,
  101,  102,  103,  149,  150,  151,  152,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
  135,    3,    2,    1,  256,    3,   27,   63,   13,  256,
  281,   13,  264,  262,  256,   13,  256,  288,  267,  256,
  267,   23,   78,  258,  259,   25,  259,   48,   30,   31,
  267,  283,  269,   38,   34,   35,   38,  274,  256,  286,
   38,  283,  289,  283,  286,  282,  181,   68,   30,  286,
   50,  286,  289,  286,  189,   57,  267,  256,  267,  115,
  267,   59,   64,  262,  282,  283,   66,  274,  256,  268,
   70,  282,  281,   75,   76,   57,  264,   79,  289,  281,
  256,  282,  289,  282,  281,  141,  262,  143,  289,  282,
  289,  256,  268,   93,  282,  283,  289,   42,   43,   44,
   45,   46,   47,  256,  269,  105,  282,  256,  110,  262,
  112,   56,   57,  289,  170,  268,  281,  288,  267,  281,
  256,  286,  122,  288,  256,  256,  258,  259,  259,  282,
  264,  267,   77,  262,  283,  133,  289,  267,  256,  257,
   85,  143,  260,  261,  262,  263,  265,  147,  282,  267,
  267,  283,  283,   98,   99,  100,  101,  102,  103,  289,
  256,  161,  256,  267,  256,  286,  284,  281,  264,  287,
  264,  256,  264,  173,  267,  257,  176,  264,  260,  261,
  262,  263,  282,  128,  286,  267,  282,  283,  282,  283,
  282,  283,  256,  267,  196,  282,  289,  282,  283,  199,
  264,  256,  202,  265,  204,  287,  256,  257,  286,  271,
  260,  261,  262,  263,  264,  289,  256,  267,  282,  283,
  270,  271,  272,  273,  264,  275,  276,  277,  278,  279,
  280,  267,  282,  283,  284,  237,  286,  287,  286,  289,
  256,  257,  282,  283,  260,  261,  262,  263,  256,  256,
  264,  267,  265,  198,  267,  262,  264,  286,  271,  256,
  257,  268,  286,  260,  261,  262,  263,  286,  284,  286,
  267,  287,  256,  257,  282,  283,  260,  261,  262,  263,
  258,  259,  256,  267,  267,  259,  267,  284,  256,  257,
  287,  286,  260,  261,  262,  263,  258,  259,  286,  267,
  284,  256,  257,  287,  267,  260,  261,  262,  263,  283,
  258,  259,  267,  286,  286,  282,  284,  256,  257,  287,
  286,  260,  261,  262,  263,  286,  282,  286,  267,  284,
  256,  257,  287,  282,  260,  261,  262,  263,  265,  266,
  267,  267,  286,  286,  271,  284,  256,  257,  287,  286,
  260,  261,  262,  263,  259,  282,  286,  267,  284,  256,
  257,  287,  286,  260,  261,  262,  263,  265,  259,  267,
  267,  259,  259,  271,  284,  256,  257,  287,  286,  260,
  261,  262,  263,  286,  286,  286,  267,  284,  256,  257,
  287,  256,  260,  261,  262,  263,  256,  256,  256,  267,
    3,   78,  129,  284,  256,  257,  287,   -1,  260,  261,
  262,  263,   -1,   -1,   -1,  267,  284,  256,  257,  287,
   -1,  260,  261,  262,  263,   -1,   -1,   -1,  267,   -1,
   -1,   -1,  284,  256,  257,  287,   -1,  260,  261,  262,
  263,   -1,   -1,   -1,  267,  284,  256,  257,  287,   -1,
  260,  261,  262,  263,   -1,   -1,   -1,  267,   -1,   -1,
   -1,  284,  256,  257,  287,   -1,  260,  261,  262,  263,
   -1,   -1,   -1,  267,  284,  256,  257,  287,   -1,  260,
  261,  262,  263,   -1,   -1,   -1,  267,   -1,   -1,   -1,
  284,  256,  257,  287,   -1,  260,  261,  262,  263,   -1,
   -1,   -1,  267,  284,  256,  257,  287,   -1,  260,  261,
  262,  263,   -1,   -1,   -1,  267,   -1,   -1,   -1,  284,
  256,  257,  287,   -1,  260,  261,  262,  263,   -1,   -1,
   -1,  267,  284,  256,  257,  287,   -1,  260,  261,  262,
  263,   -1,   -1,   -1,  267,   -1,   -1,   -1,  284,  256,
  257,  287,   -1,  260,  261,  262,  263,   -1,   -1,   -1,
  267,  284,  256,  257,  287,   -1,  260,  261,  262,  263,
   -1,   -1,   -1,  267,   -1,   -1,   -1,  284,  256,  257,
  287,   -1,  260,  261,  262,  263,  256,   -1,  256,  267,
  284,  256,   -1,  287,  264,  256,  264,  256,   -1,  264,
  256,   -1,  256,  264,   -1,  264,  284,   -1,  264,  287,
  264,  256,  282,  283,  282,  283,   -1,  282,  283,  264,
   -1,  282,  283,  282,  283,  256,  282,  283,  282,  283,
   -1,  256,   -1,  264,  256,  265,  256,  282,  283,  264,
   -1,  271,  264,  267,  264,  269,   -1,  265,   -1,   -1,
  274,  282,  283,  271,   -1,   -1,  286,  282,  283,  289,
  282,  283,  282,  283,   -1,  289,   -1,   -1,  286,   -1,
   -1,  289,  270,  271,  272,  273,  270,  271,  272,  273,
  270,  271,  272,  273,  265,   -1,  267,   -1,   -1,  287,
  271,   -1,  282,  287,  275,  276,  277,  278,  279,  280,
  281,  265,   -1,  267,   -1,   -1,   -1,  271,   -1,   -1,
   -1,  275,  276,  277,  278,  279,  280,  281,  270,  271,
  272,  273,  270,  271,  272,  273,   -1,   -1,   -1,  265,
   -1,  267,   -1,   -1,  286,  271,   -1,   -1,  286,  275,
  276,  277,  278,  279,  280,  270,  271,  272,  273,  270,
  271,  272,  273,  270,  271,  272,  273,  282,   -1,   -1,
   -1,  282,  270,  271,  272,  273,   -1,  275,  276,  277,
  278,  279,  280,   94,   95,   96,   97,
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
"header_while : inicio_header_while PARENTESISA condicion PARENTESISC",
"inicio_header_while : WHILE",
"sentencia_while_error : inicio_header_while condicion PARENTESISC DO bloque PUNTOCOMA",
"sentencia_while_error : inicio_header_while PARENTESISA condicion DO bloque PUNTOCOMA",
"sentencia_while_error : inicio_header_while condicion DO bloque PUNTOCOMA",
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
"sentencia_declarativa : tipo ID PUNTO ID PUNTOCOMA",
"funcion : header_funcion bloque",
"header_funcion : tipo ID PARENTESISA parametros_formales PARENTESISC",
"header_funcion : tipo PARENTESISA parametros_formales PARENTESISC",
"parametros_formales : parametros_formales COMA parametro_formal",
"parametros_formales : parametro_formal",
"parametros_formales : parametros_formales parametro_formal",
"parametro_formal : CVR tipo ID",
"parametro_formal : tipo ID",
"parametro_formal : parametro_formal_error",
"parametro_formal_error : tipo error",
"parametro_formal_error : CVR tipo",
"parametro_formal_error : CVR ID",
"parametro_formal_error : error ID",
"parametros_reales : parametros_reales COMA expresiones FLECHA identificador",
"parametros_reales : expresiones FLECHA identificador",
"parametros_reales : expresiones FLECHA",
"expresion_lambda : header_lambda bloque llamado_lambda PUNTOCOMA",
"header_lambda : FLECHA PARENTESISA tipo ID PARENTESISC",
"llamado_lambda : PARENTESISA identificador PARENTESISC",
"llamado_lambda : PARENTESISA tipo_cte PARENTESISC",
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

//#line 353 "gramatica.y"

/* -------------------------------------------------------------------------------------------------------------CODIGO AUXILIAR ----------------------------------------------------*/

AnalisisLexico aLex;
private HashMap<String, ArrayList<String>> tablaDeSimbolos = new HashMap<String, ArrayList<String>>();

public void setAlex(AnalisisLexico a){
    this.aLex = a;

}

public boolean verificar_cantidades(ArrayList<String> l1, ArrayList<String> l2){
    if (l1 != null && l2 != null){
        if (l1.size() > l2.size() ){
            agregarError("Linea "+aLex.getNroLinea()+" ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
            return false;
            }
        }
        if (l1.size() < l2.size()){
            agregarErrorSemantico("Linea "+aLex.getNroLinea()+" WARNING: Quedan elementos sin asignar del lado derecho.");
            return true;
        }
        if (l1.size() == l2.size()) {
            return true;
        }
    return false;

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

public void modificarUsosParametros(ArrayList<String> lista, String uso){
    for (String parametro: lista){
        int indice = parametro.lastIndexOf(" ");
        parametro = parametro.substring(indice + 1);
        modificarUsoTS(parametro, uso);
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
            if(fila.size() == 1)
                fila.add(""); //agrego tipo vacio en indice 1
            if(fila.size() == 2)
                fila.add(uso); //Se agrega el uso en el indice = 2
            else
                fila.set(2,uso);
        }else{
            System.out.println("(modificarUsoTS) Error, la clave" + clave + " no existe en la tabla de simbolos");
        }
    }
}

public void sacarParamFormTS(String clave){
    ArrayList<String> aux = tablaDeSimbolos.get(clave);
    //Solo la elimino si es una variable no inicializada.
    if (aux.size() <= 2 || aux.get(2).equals(""))
        tablaDeSimbolos.remove(clave);
}

public void modificarAmbitosTS(ArrayList<String> a){
    int index = ambito.lastIndexOf(":");
    String ambitoAfuera = ambito.substring(0, index);



    for (String parametro : a){
        if (parametro != null){
            //me quedo solo con su nombre, saco semantica y tipo
            int indice1 = parametro.indexOf(" ");
            parametro = parametro.substring(indice1 + 1);
            int indice2 = parametro.indexOf(" ");
            String tipo = parametro.substring(0, indice2);
            parametro = parametro.substring(indice2 + 1);

            ArrayList<String> aux = tablaDeSimbolos.get(ambitoAfuera+":"+parametro);

            //le agrego el tipo del parametro a la TS
            aux.set(1,tipo);
            tablaDeSimbolos.put(ambito+":"+parametro, aux);

            //Lo busco en la ts y modifico su clave para que incluya el ambito de la funcion definida
            sacarParamFormTS(ambitoAfuera+":"+parametro);
        }
    }
}

public void copiarTS(String clave, int token){
    //Si es una constante, no le concateno el ambito
    String aux = clave;
    if(!(token == 265)){
        aux = ambito+":"+clave;
    }
    if(!tablaDeSimbolos.containsKey(aux)){
        String tipo = aLex.getTipoTS(clave);
        ArrayList<String> a = new ArrayList<String>();
        a.add(tipo);
        //Se agrega el tipo en el indice = 1
        if(token == 265){
            String tipoCte = aLex.getTipoCteTS(clave);
            a.add(tipoCte);
        }else{
            a.add(" ");
        }
        tablaDeSimbolos.put(aux, a);
   }
}

public void agregarCteTS(String lexema){
    //Actualizacion de la TS para constantes (por si hubo una negativa)
    if (!tablaDeSimbolos.containsKey(lexema)) {
        //Si el lexema no esta en la tabla de simbolos lo agrega
        ArrayList<String> a = new ArrayList<>();
        //Sacar solo el valor numerico si es UL
        if(lexema.contains(".") & lexema.contains("D")) {
            a.add(0, "CTE");
            a.add(1,"DFLOAT");
            tablaDeSimbolos.put(lexema, a);
        }
        else{
            if(lexema.contains("-")){
                agregarError("LINEA "+aLex.getNroLinea()+" WARNING SINTACTICO: No se permiten ULONG negativos. La constante fue truncada a positivo.");
            }
        }
    }
}

public void agregarInfoFuncionTS(String tipoReturn, ArrayList<String> parametros){
    //Convierto los parametros en un string separados por ','
    String aux = "";
    if(parametros != null){
        aux = String.join(", ", parametros);
    }
    //Ya se concateno el nombre de la funcion al ambito
    String funcion = ambito;
    if (tablaDeSimbolos.containsKey(funcion)){
        ArrayList<String> a = tablaDeSimbolos.get(funcion);
        if(a.size() == 3){
            a.set(1, tipoReturn); //en TIPO indico el tipo que retorna la funcion
            a.add(aux); //Agrego en indice 3 un string con los parametros formales de la funcion (sus tipos y su semantica)
        }
    }else{
      System.out.println("(agregarInfoFuncionTS) Error, la clave" + funcion + " no existe en la tabla de simbolos");
    }
}

//-------------------------------------------------------------------------------------------------------------CODIGO INTERMEDIO
private ArrayList<String> mainArreglo = new ArrayList<String>();
private HashMap<String, ArrayList<String>> polacaInversa = new HashMap<String, ArrayList<String>>();
private String ambito = "MAIN";
private Integer indiceWhile;
private Integer label = 0;
private java.util.Stack<Integer> pilaWhile = new java.util.Stack<>();
private int nroLambda = 0;

public HashMap<String, ArrayList<String>> getPolacaInversa(){ return polacaInversa; }

public void borrarAmbito(){
    int index = ambito.lastIndexOf(":");
    if (index != -1) {
         String texto = ambito;
        texto = texto.substring(0, index);
        ambito = texto;
    }
}

public void cvrAPolaca(String funcion, ArrayList<String> parametros_reales, ArrayList<String> arreglo){
    ArrayList<String> a;

    String semantica = tablaDeSimbolos.get(ambito+":"+funcion).get(3);
    if (semantica.contains("cvr")) {
        String[] parametros = semantica.split(",");

        //si la semantica es CVR agrego a la polaca una asignacion
        for (String p : parametros) {
          int indice = p.lastIndexOf(" ");
          String nombre = p.substring(indice + 1);

          //encuentro el real que esta asignado a el formal
          String real = parametros_reales.get(parametros_reales.indexOf(nombre) - 1);

          if (p.contains("cvr")) {
            arreglo.add(nombre);
            arreglo.add(real);
            arreglo.add("<-");
          }
        }
    }
}
public void agregarAPolaca(String valor){
if (polacaInversa != null){

    if (polacaInversa.containsKey(ambito)) {
        ArrayList<String> a = tablaDeSimbolos.get(valor);
        if (a!= null && a.size() == 3){
            String uso = a.get(2);
            if(!uso.equals("Nombre de parametro"))
                polacaInversa.get(ambito).add(valor);
            //Los parametros formales los agregamos a la polaca inversa manualmente en las reglas
        }else
            polacaInversa.get(ambito).add(valor);
    }
    else{
        mainArreglo.add(valor);
    }
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

    String l = label.toString();

    if (flag.equals("cond")) {
       a.set(i, "SALTO A "+l);
       a.add(i + 1, "BF");
    }else{
        a.set(i, "SALTO A "+l);
        a.add(i + 1, "BI");
        a.add(i + 2, flag);
    }
    label++;
}

public void guardarInicioWhile(){

  ArrayList<String> a = new ArrayList<String>();
  if (polacaInversa.containsKey(ambito)) {
      a = polacaInversa.get(ambito);
  }else{
      a = mainArreglo;
  }
  indiceWhile = a.size() -1 +1; //queremos la proxima celda que se va a escribir
}

public void bifurcacionWhile(){
    ArrayList<String> a = new ArrayList<String>();
    if (polacaInversa.containsKey(ambito)) {
        a = polacaInversa.get(ambito);
    }else{
        a = mainArreglo;
    }
    int i = a.size() - 1;
    Integer l = pilaWhile.pop();
    a.set(i, "SALTO A "+l);
    a.add("BI");
}


//-----------------------------------CHEQUEOS SEMANTICOS----------------------------------------


// Devuelve true si ya existe alguna función con ese nombre
// en el ámbito actual o en alguno de sus padres
public boolean existeFuncionVisibleConNombre(String nombre) {
    String amb = ambito;  // ej: "MAIN", "MAIN:FUNC2", "MAIN:FUNC2:FUNC3"

    while (true) {
        String clave = amb + ":" + nombre;   // ej: "MAIN:FUNC2", "MAIN:FUNC2:FUNC2"
        if (tablaDeSimbolos.containsKey(clave)) {
            ArrayList<String> fila = tablaDeSimbolos.get(clave);
            if (fila.size() >= 3 && "Nombre de funcion".equals(fila.get(2))) {
                return true;   // ya hay una función con ese nombre en algún ámbito visible
            }
        }

        // Subo un nivel en la cadena de ámbitos
        int idx = amb.lastIndexOf(':');
        if (idx == -1) break;        // ya no hay más padres
        amb = amb.substring(0, idx); // ej: "MAIN:FUNC2" -> "MAIN"
    }

    return false;
}


public void agregarPolacaMultiple(ArrayList<String> l1, ArrayList<String> l2){
    for (int i=0; i<l1.size(); i++){
        agregarAPolaca(l2.get(i));
        agregarAPolaca(l1.get(i));
        agregarAPolaca(":=");
    }

}

public boolean estaInicializada(String id){
//Recibe el id concatenado con el ambito
    if (!tablaDeSimbolos.containsKey(id))
        return false;
    else {
      ArrayList<String> a = tablaDeSimbolos.get(id);
      if (a != null && a.size() == 3) {
        String uso = a.get(2);
        if (uso.equals("Nombre de variable") || uso.equals("Nombre de parametro") || uso.equals("Nombre de funcion"))
          //inicializada
          return true;
      }
    }
    return false;
}

public boolean estaDeclarada(String id){
//Recibe el id concatenado con el ambito
    if (!tablaDeSimbolos.containsKey(id))
        return false;
    else {
      ArrayList<String> a = tablaDeSimbolos.get(id);
      if (a != null && a.size() >= 3) {
        String uso = a.get(2);
        if (uso.equals("Nombre de funcion"))
          //declarada
          return true;
      }
    }
    return false;
}

public String funcionAlAlcance(String id){
    //Si una variable esta al alcance, devuelve su clave en la TS (ambito+variable), sino devuelve null
    String ambitoActual = ambito;

    //Para variables sin prefijado
    while (true) {
        String clave = ambitoActual + ":" + id;

        // esta al alcance
        if (tablaDeSimbolos.containsKey(clave))
                return clave;

        // Si ya estamos en el global, cortar
        if (ambitoActual.equals("MAIN"))
            break;

        // Quitar el último nivel del ámbito
        int idx = ambitoActual.lastIndexOf(":");
        if (idx == -1) {
            ambitoActual = "MAIN";
        } else {
            ambitoActual = ambitoActual.substring(0, idx);
        }
    }

   return null; // no encontrado
}

public boolean funcionPermitida(String id) {
    //Chequeo si la funcion esta al alcance y si esta inicializada
    String clave = funcionAlAlcance(id);
    if(clave != null)
        // esta al alcance
        if(estaDeclarada(clave))
            // fue inicializada en ese ambiente
                return true;
   return false; // no se puede usar
}

public String variableAlAlcance(String id){
    //Si una variable esta al alcance, devuelve su clave en la TS (ambito+variable), sino devuelve null
    String ambitoActual = ambito;

    //Para variables sin prefijado
    while (true) {
        String clave = ambitoActual + ":" + id;

        // esta al alcance
        if (tablaDeSimbolos.containsKey(clave))
            if(estaInicializada(clave))
                return clave;

        // Si ya estamos en el global, cortar
        if (ambitoActual.equals("MAIN"))
            break;

        // Quitar el último nivel del ámbito
        int idx = ambitoActual.lastIndexOf(":");
        if (idx == -1) {
            ambitoActual = "MAIN";
        } else {
            ambitoActual = ambitoActual.substring(0, idx);
        }
    }

   return null; // no encontrado
}

//Este metodo tambien se puede usar para declarar una variable. Si la variable no esta permitida,
//significa que no hay una declaracion de esa variable en su ambito, por lo que se puede declarar.

public boolean variablePermitida(String id) {
    //Para variables con prefijado
    if(id.contains(".")){
        int indice = id.lastIndexOf(".");
        String funcion = id.substring(0, indice);

        if(ambito.contains(funcion))
            return true;
        else
            return false;
    }

    String clave = variableAlAlcance(id);
    //Chequeo si la variable esta al alcance y si esta inicializada
    if(clave != null)
        // esta al alcance y fue inicializada en ese ambiente o en uno padre
        return true;
   return false; // no se puede usar
}

public boolean variablesPermitidas(ArrayList<String> a){
    boolean permitido = true;
    if (!a.isEmpty()){
        for (String variable: a){
            if (!variablePermitida(variable)){
                agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+variable+"' no declarada.(val permtida)");
                //Uso un booleano para no retornar antes y que se agreguen todas las que no esten permitidas
                permitido = false;
            }
        }
    }
    return permitido;
}

public boolean variablesDeclaradas(ArrayList<String> a){
    boolean declarada = false;
    if (!a.isEmpty()){
        for (String variable: a){
            if (variablePermitida(variable)){
                //ya fue declarada
                agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+variable+"' ya fue declarada.");
                //Uso un booleano para no retornar antes y que se agreguen todas las ya fueron declaradas
                declarada = true;
            }
        }
    }
    return declarada;
}

public boolean chequeoReturn(){
    for(String clave: polacaInversa.keySet()){
        if(!clave.equals("MAIN") && !clave.contains("LAMBDA")){
            ArrayList<String> a = polacaInversa.get(clave);
            if (!a.contains("return"))
                return false;
        }
    }
    return true;
}

//----------------------------------------------------------------------------------------------------------ERRORES SEMANTICOS
ArrayList<String> erroresSemanticos = new ArrayList<>();

public boolean dentroDeRango(String lex){
        lex = lex.substring(1, lex.length() - 1);
        String base = "";
        String exponente = "";
        boolean d = false;
        for (int i = 0 ; i < lex.length(); i++){
            char c = lex.charAt(i);
            if (!d) {
                if (c == 'D')
                    d = true;
                else
                    base = base + c;
            }
            else
                exponente = exponente + c;
        }
        double base2;
        if (base.equals(""))
            base2 = 1;
        else
            base2 = Double.parseDouble(base);

        base2 = base2 * (-1);

        double exponente2;
        if(exponente.equals(""))
            exponente2 = 0;
        else
            exponente2 = Double.parseDouble(exponente);

        double resultado = base2 * Math.pow(10, exponente2);

        double minP = -1.7976931348623157 * Math.pow(10, 308);   //-1.7976931348623157D+308
        double maxP = -2.2250738585072014D * Math.pow(10, -308);    //-2.2250738585072014D-308

        if ((minP < resultado && resultado < maxP) | resultado == 0.0){
            return true;
        }
        return false;
}

void agregarErrorSemantico(String s){
    erroresSemanticos.add(s);
}

public boolean huboError(){
    if(!erroresSemanticos.isEmpty()){
        for (String e :erroresSemanticos){
            if(e.contains("ERROR"))
                return true;
        }
    }

    if(!errores.isEmpty()){
        for (String e :errores){
            if(e.contains("ERROR"))
                return true;
        }
    }
    ArrayList<String> a = aLex.getErroresLexicos();
    if(!a.isEmpty()){
        for (String e : a){
            if(e.contains("ERROR"))
                return true;
        }
    }

    return false;
}

public boolean existeVariable(String name){
    for (String clave : tablaDeSimbolos.keySet()){
        if(clave.endsWith(name)){
            return true;
        }
    }
    return false;
}
//----------------------------------------------------------------------------------------------------------IMPRESIONES

private String errorGeneral = "";

public String getError(){
    return errorGeneral;
}

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
    System.out.println("\nTabla de símbolos:");

    // Encabezados con buen espaciado
    System.out.printf(
            "%-35s | %-15s | %-15s | %-20s | %-20s%n",
            "Ámbito / Lexema",
            "Tipo Token",
            "Tipo",
            "Uso",
            "Semántica"
    );

    System.out.println("----------------------------------------------------------------------------------------------");

    for (Map.Entry<String, ArrayList<String>> entry : tablaDeSimbolos.entrySet()) {

      String clave = entry.getKey();
      ArrayList<String> valores = entry.getValue();

      String tipoToken   = valores.size() > 0 ? valores.get(0) : "";
      String tipo        = valores.size() > 1 ? valores.get(1) : "";
      String uso         = valores.size() > 2 ? valores.get(2) : "";
      String otraInfo    = valores.size() > 3 ? valores.get(3) : "";
      String semantica   = valores.size() > 4 ? valores.get(4) : "";

      System.out.printf(
              "%-35s | %-15s | %-15s | %-20s | %-20s%n",
              clave,
              tipoToken,
              tipo,
              uso,
              semantica
      );
    }

    System.out.println();
  }


public void imprimirErroresSemanticos(){
    System.out.println("");
    System.out.println("Errores semanticos: ");
    if (!erroresSemanticos.isEmpty()){
        for (String error: erroresSemanticos){
            System.out.println(error);
        }
    }else{
        System.out.println("No se encontraron errores semanticos");
    }
}

public HashMap<String, ArrayList<String>> getTablaDeSimbolos() {
    return tablaDeSimbolos;
}
//#line 1298 "Parser.java"
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
{agregarSentencia("LINEA: "+aLex.getNroLinea()+" SENTENCIA: Nombre de programa");  modificarUsoTS(val_peek(1).sval, "Nombre de programa"); polacaInversa.put("MAIN", mainArreglo); if(!chequeoReturn()){agregarErrorSemantico("ERROR SEMANTICO: Falta 'return' en funcion");} if(huboError()){errorGeneral = "No se genero codigo assembler por presencia de errores"; polacaInversa = null; } }
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
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: If");}
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
{ArrayList<String> a = (ArrayList<String>)val_peek(2).obj; if(variablesPermitidas(a)){agregarAPolaca("empieza lista");agregarListaAPolaca(a); agregarAPolaca("return");}}
break;
case 18:
//#line 51 "gramatica.y"
{ArrayList<String> b = (ArrayList<String>) val_peek(2).obj; if (!b.contains("null")) {agregarAPolaca("empieza lista");agregarListaAPolaca(b); agregarAPolaca("return");}}
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
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Print"); ArrayList<String> b = (ArrayList<String>) val_peek(2).obj; if (!b.contains("null")) { agregarListaAPolaca(b); } agregarAPolaca("print");}
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
case 28:
//#line 70 "gramatica.y"
{if (!val_peek(3).sval.equals("null")){agregarAPolaca("cuerpo"); bifurcacionWhile(); agregarAPolaca("LABEL"+label+":");  agregarBifurcacion("cond");}}
break;
case 30:
//#line 75 "gramatica.y"
{if (val_peek(1).sval.equals("null")){yyval = new ParserVal("null");} else {yyval = new ParserVal("sin error");}}
break;
case 31:
//#line 79 "gramatica.y"
{ pilaWhile.push(label); agregarAPolaca("LABEL"+label+":"); label++; }
break;
case 32:
//#line 83 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 33:
//#line 84 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 34:
//#line 85 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 35:
//#line 86 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'do' en iteracion");}
break;
case 36:
//#line 87 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTÁCTICO: Falta cuerpo de la iteracion");}
break;
case 37:
//#line 91 "gramatica.y"
{if (!val_peek(4).sval.equals("null")) {agregarAPolaca("LABEL"+label+":"); Integer l = label - 1; agregarBifurcacion("LABEL"+l+":");}}
break;
case 40:
//#line 97 "gramatica.y"
{if (!val_peek(2).sval.equals("null")) {agregarAPolaca("LABEL"+label+":"); agregarBifurcacion("cond"); yyval = new ParserVal("sin error");} else {yyval = new ParserVal("null");}}
break;
case 41:
//#line 101 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 42:
//#line 102 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 43:
//#line 103 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 44:
//#line 104 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 45:
//#line 105 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 46:
//#line 106 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 47:
//#line 107 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 48:
//#line 108 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 49:
//#line 109 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 50:
//#line 110 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 51:
//#line 111 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else'");}
break;
case 52:
//#line 112 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else', 'then'");}
break;
case 53:
//#line 113 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 54:
//#line 114 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 55:
//#line 115 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion y falta ';' ");}
break;
case 56:
//#line 116 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion y falta ';' ");}
break;
case 57:
//#line 117 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion y falta ';' ");}
break;
case 58:
//#line 118 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion y falta ';' ");}
break;
case 59:
//#line 119 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion y falta ';' ");}
break;
case 60:
//#line 120 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion y falta ';' ");}
break;
case 61:
//#line 121 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif' y falta ';' ");}
break;
case 62:
//#line 122 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif' y falta ';' ");}
break;
case 63:
//#line 123 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then' y falta ';' ");}
break;
case 64:
//#line 124 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then' y falta ';' ");}
break;
case 65:
//#line 125 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else' y falta ';' ");}
break;
case 66:
//#line 126 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else', 'then' y falta ';' ");}
break;
case 67:
//#line 130 "gramatica.y"
{ArrayList<String> b = (ArrayList<String>) val_peek(2).obj;
                                                    yyval = new ParserVal("null");
                                                    if (!b.contains("null")){
                                                        ArrayList<String> a = (ArrayList<String>) val_peek(0).obj;
                                                        if (!a.contains("null")) {
                                                            agregarListaAPolaca(b);
                                                            agregarListaAPolaca(a);
                                                            agregarAPolaca(">");
                                                            agregarAPolaca("cond");
                                                            yyval = new ParserVal("sin error");
                                                        }
                                                    }
                                                    }
break;
case 68:
//#line 143 "gramatica.y"
{yyval = new ParserVal("null"); ArrayList<String> b = (ArrayList<String>) val_peek(2).obj; if (!b.contains("null")) { ArrayList<String> a = (ArrayList<String>) val_peek(0).obj; if (!a.contains("null")) { agregarListaAPolaca(b); agregarListaAPolaca(a); agregarAPolaca(">="); agregarAPolaca("cond"); yyval = new ParserVal("sin error");}}}
break;
case 69:
//#line 144 "gramatica.y"
{yyval = new ParserVal("null"); ArrayList<String> b = (ArrayList<String>) val_peek(2).obj; if (!b.contains("null")) { ArrayList<String> a = (ArrayList<String>) val_peek(0).obj; if (!a.contains("null")) { agregarListaAPolaca(b); agregarListaAPolaca(a); agregarAPolaca("<"); agregarAPolaca("cond"); yyval = new ParserVal("sin error");}}}
break;
case 70:
//#line 145 "gramatica.y"
{yyval = new ParserVal("null"); ArrayList<String> b = (ArrayList<String>) val_peek(2).obj; if (!b.contains("null")) { ArrayList<String> a = (ArrayList<String>) val_peek(0).obj; if (!a.contains("null")) { agregarListaAPolaca(b); agregarListaAPolaca(a); agregarAPolaca(">="); agregarAPolaca("cond"); yyval = new ParserVal("sin error");}}}
break;
case 71:
//#line 146 "gramatica.y"
{yyval = new ParserVal("null"); ArrayList<String> b = (ArrayList<String>) val_peek(2).obj; if (!b.contains("null")) { ArrayList<String> a = (ArrayList<String>) val_peek(0).obj; if (!a.contains("null")) { agregarListaAPolaca(b); agregarListaAPolaca(a); agregarAPolaca("=="); agregarAPolaca("cond"); yyval = new ParserVal("sin error");}}}
break;
case 72:
//#line 147 "gramatica.y"
{yyval = new ParserVal("null"); ArrayList<String> b = (ArrayList<String>) val_peek(2).obj; if (!b.contains("null")) { ArrayList<String> a = (ArrayList<String>) val_peek(0).obj; if (!a.contains("null")) { agregarListaAPolaca(b); agregarListaAPolaca(a); agregarAPolaca("=!"); agregarAPolaca("cond"); yyval = new ParserVal("sin error");}}}
break;
case 73:
//#line 148 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Condicion incompleta");}
break;
case 87:
//#line 168 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion y asignacion"); ArrayList<String> a = new ArrayList<String>(); a.add(val_peek(3).sval); if(!variablePermitida(val_peek(3).sval)) {
                                                                                                                                                                                                                         ArrayList<String> b = (ArrayList<String>) val_peek(1).obj;
                                                                                                                                                                                                                         if (!b.contains("null")) {
                                                                                                                                                                                                                             modificarTipoTS(a, val_peek(4).sval);
                                                                                                                                                                                                                             modificarUsos(a, "Nombre de variable");
                                                                                                                                                                                                                             agregarListaAPolaca(b);
                                                                                                                                                                                                                             agregarAPolaca(val_peek(3).sval);
                                                                                                                                                                                                                             agregarAPolaca(":=");
                                                                                                                                                                                                                         }
                                                                                                                                                                                                                     }
                                                                                                                                                                                                                      else{
                                                                                                                                                                                                                          agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+val_peek(3).sval+"' ya fue declarada.");}
                                                                                                                                                                                                                      }
break;
case 88:
//#line 181 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion"); if(!variablePermitida(val_peek(3).sval)){agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+val_peek(3).sval+"' no declarada.");} else {ArrayList<String> a = (ArrayList<String>) val_peek(1).obj; if (!a.contains("null")) {agregarListaAPolaca(a); agregarAPolaca(val_peek(3).sval); agregarAPolaca(":=");}}}
break;
case 89:
//#line 182 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion multiple"); ArrayList<String> l1 = (ArrayList<String>)val_peek(3).obj; ArrayList<String> l3 = (ArrayList<String>)val_peek(1).obj; if (verificar_cantidades(l1, l3)){ if(variablesPermitidas(l1)){ agregarPolacaMultiple(l1, l3);}}}
break;
case 90:
//#line 183 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion multiple"); if(!variablePermitida(val_peek(3).sval)){agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+val_peek(3).sval+"' no declarada.");} else {agregarListaAPolaca((ArrayList<String>)val_peek(1).obj); agregarAPolaca(val_peek(3).sval); agregarAPolaca("=");}}
break;
case 91:
//#line 184 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 96:
//#line 195 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(2).obj; ArrayList<String> b= (ArrayList<String>)val_peek(0).obj; a.addAll(b); a.add("+"); yyval = new ParserVal(a);}
break;
case 97:
//#line 196 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(2).obj; ArrayList<String> b= (ArrayList<String>)val_peek(0).obj; a.addAll(b); a.add("-"); yyval = new ParserVal(a);}
break;
case 98:
//#line 197 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(2).obj; ArrayList<String> b= (ArrayList<String>)val_peek(0).obj; a.addAll(b);a.add("*"); yyval = new ParserVal(a);}
break;
case 99:
//#line 198 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(2).obj; ArrayList<String> b= (ArrayList<String>)val_peek(0).obj; a.addAll(b); a.add("/"); yyval = new ParserVal(a);}
break;
case 100:
//#line 199 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(0).obj; yyval = new ParserVal(val_peek(0).obj);}
break;
case 101:
//#line 200 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta operando en expresion");}
break;
case 102:
//#line 204 "gramatica.y"
{ArrayList<String> a = new ArrayList<String>(); if(!variablePermitida(val_peek(0).sval)){agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+val_peek(0).sval+"' no declarada."); a.add("null"); yyval = new ParserVal(a);} else {a.add(val_peek(0).sval); yyval = new ParserVal(a);} }
break;
case 103:
//#line 205 "gramatica.y"
{ArrayList<String> a = new ArrayList<String>(); a.add(val_peek(0).sval); yyval = new ParserVal(a); }
break;
case 104:
//#line 206 "gramatica.y"
{yyval = new ParserVal(val_peek(0).obj); }
break;
case 105:
//#line 210 "gramatica.y"
{ArrayList<String> a = new ArrayList<>((ArrayList<String>) val_peek(1).obj);
                                                         if (funcionPermitida(val_peek(3).sval)){
                                                            a.add(val_peek(3).sval);
                                                            a.add("call");
                                                            /*Chequeo si es CVR reasigno los formales a los reales*/
                                                            if(val_peek(1) != null) {cvrAPolaca(val_peek(3).sval, (ArrayList<String>)val_peek(1).obj, a);}
                                                            a.add("reemplazar_"+ambito+":"+val_peek(3).sval);
                                                            yyval = new ParserVal(a);
                                                         }else {
                                                            agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: funcion '"+val_peek(3).sval+"' fuera de alcance.");
                                                            a.add("null");} yyval = new ParserVal(a);
                                                         }
break;
case 106:
//#line 225 "gramatica.y"
{agregarAPolaca("+");}
break;
case 107:
//#line 226 "gramatica.y"
{agregarAPolaca("-");}
break;
case 108:
//#line 227 "gramatica.y"
{agregarAPolaca("*");}
break;
case 109:
//#line 228 "gramatica.y"
{agregarAPolaca("/");}
break;
case 110:
//#line 232 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de variable"); ArrayList<String> b = new ArrayList<String>(); b.add(val_peek(1).sval); if(!variablePermitida(val_peek(1).sval)){modificarTipoTS(b, val_peek(2).sval); modificarUsoTS(val_peek(1).sval, "Nombre de variable");}else{agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+val_peek(1).sval+"' ya fue declarada.");}}
break;
case 111:
//#line 233 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de variables"); ArrayList<String> a = (ArrayList<String>)val_peek(1).obj; if(!variablesDeclaradas(a)){modificarTipoTS(a, val_peek(2).sval); modificarUsos(a, "Nombre de variable");}}
break;
case 112:
//#line 234 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de funcion");}
break;
case 113:
//#line 235 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 114:
//#line 236 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 116:
//#line 241 "gramatica.y"
{String ambitoConFuncion = ambito;
                                            borrarAmbito();}
break;
case 117:
//#line 246 "gramatica.y"
{String nombre = val_peek(3).sval;
                                                                 if (existeFuncionVisibleConNombre(nombre)) {
                                                                   agregarErrorSemantico("LINEA " + aLex.getNroLinea() +" ERROR SEMANTICO: Funcion '" + nombre + "' redeclarada");
                                                                 } else {
                                                                   modificarUsoTS(nombre, "Nombre de funcion");
                                                                   ambito = ambito + ":" + nombre;
                                                                   modificarAmbitosTS((ArrayList<String>)val_peek(1).obj);
                                                                   modificarUsosParametros((ArrayList<String>)val_peek(1).obj, "Nombre de parametro");
                                                                   agregarInfoFuncionTS(val_peek(4).sval, (ArrayList<String>) val_peek(1).obj);
                                                                   if(polacaInversa != null){
                                                                   polacaInversa.put(ambito, new ArrayList<String>());}
                                                               }}
break;
case 118:
//#line 258 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
break;
case 119:
//#line 262 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(2).obj; a.add(val_peek(0).sval); yyval = new ParserVal(a);}
break;
case 120:
//#line 263 "gramatica.y"
{ArrayList<String> a = new ArrayList<String>(); a.add(val_peek(0).sval); yyval = new ParserVal(a);}
break;
case 121:
//#line 264 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta ',' en declaracion de las variables");}
break;
case 122:
//#line 268 "gramatica.y"
{ yyval = new ParserVal("cvr "+val_peek(1).sval+" "+val_peek(0).sval);}
break;
case 123:
//#line 269 "gramatica.y"
{ yyval = new ParserVal("cv "+val_peek(1).sval+" "+val_peek(0).sval);}
break;
case 124:
//#line 270 "gramatica.y"
{yyval = new ParserVal();}
break;
case 125:
//#line 274 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta nombre del parametro formal");}
break;
case 126:
//#line 275 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta nombre del parametro formal");}
break;
case 127:
//#line 276 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta tipo del parametro formal");}
break;
case 128:
//#line 277 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta tipo del parametro formal");}
break;
case 129:
//#line 281 "gramatica.y"
{ArrayList<String> b = (ArrayList<String>) val_peek(4).obj; b.addAll((ArrayList<String>)val_peek(2).obj); b.add(val_peek(0).sval); b.add("->"); yyval = new ParserVal(b); sacarParamFormTS(ambito+":"+val_peek(0).sval);}
break;
case 130:
//#line 282 "gramatica.y"
{ArrayList<String> b = (ArrayList<String>) val_peek(2).obj; b.add(val_peek(0).sval); b.add("->"); yyval = new ParserVal(b); sacarParamFormTS(ambito+":"+val_peek(0).sval);}
break;
case 131:
//#line 283 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta especificacion del parametro formal");}
break;
case 132:
//#line 287 "gramatica.y"
{agregarAPolaca("returnLambda");
                                                                     borrarAmbito();
                                                                     agregarAPolaca(val_peek(1).sval);
                                                                     agregarAPolaca(val_peek(3).sval);
                                                                     agregarAPolaca("->");
                                                                     agregarAPolaca("LAMBDA"+nroLambda);
                                                                     agregarAPolaca("call");
                                                                     nroLambda++;}
break;
case 133:
//#line 298 "gramatica.y"
{ambito = ambito + ":LAMBDA"+nroLambda;
                                                                     ArrayList<String> polaca = new ArrayList<String>();
                                                                     if(polacaInversa != null){
                                                                     polacaInversa.put(ambito, polaca);}

                                                                    ArrayList<String> parametroCompleto = new ArrayList<String>();
                                                                    String parametro =  "cv " + val_peek(2).sval+ " "+val_peek(1).sval;
                                                                    parametroCompleto.add(parametro);
                                                                    modificarAmbitosTS(parametroCompleto);

                                                                    ArrayList<String> parametroID = new ArrayList<String>();
                                                                    parametroID.add(val_peek(1).sval);
                                                                    modificarUsosParametros(parametroID, "Nombre de parametro");


                                                                    yyval = new ParserVal(val_peek(1).sval);}
break;
case 134:
//#line 317 "gramatica.y"
{if(!variablePermitida(val_peek(1).sval)){agregarErrorSemantico("LINEA "+aLex.getNroLinea()+" ERROR SEMANTICO: variable '"+val_peek(2).sval+"' no declarada.");} yyval = new ParserVal(val_peek(1).sval);}
break;
case 135:
//#line 318 "gramatica.y"
{yyval = new ParserVal(val_peek(1).sval);}
break;
case 136:
//#line 322 "gramatica.y"
{ArrayList<String> arreglo = (ArrayList<String>) val_peek(2).obj; arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 137:
//#line 323 "gramatica.y"
{ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add(val_peek(2).sval); arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo); }
break;
case 138:
//#line 324 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 141:
//#line 333 "gramatica.y"
{ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 142:
//#line 334 "gramatica.y"
{ArrayList<String> arreglo = (ArrayList<String>) val_peek(2).obj; arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 143:
//#line 335 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
break;
case 144:
//#line 339 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 145:
//#line 340 "gramatica.y"
{String name = val_peek(2).sval + "." + val_peek(0).sval; String a = val_peek(2).sval + ":" + val_peek(0).sval; yyval = new ParserVal(name); }
break;
case 146:
//#line 344 "gramatica.y"
{yyval = new ParserVal("ULONG");}
break;
case 147:
//#line 348 "gramatica.y"
{agregarCteTS(val_peek(0).sval); yyval = new ParserVal(val_peek(0).sval);}
break;
case 148:
//#line 349 "gramatica.y"
{String cte = "-" + val_peek(0).sval; if(dentroDeRango(cte)){ agregarCteTS(cte); if(!cte.contains(".") & !cte.contains("D")){ cte = cte.substring(1);}} else {agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Dfloat:"+cte+" fuera de rango.");} yyval = new ParserVal(cte);}
break;
//#line 1988 "Parser.java"
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
