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
//#line 24 "Parser.java"




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
   13,   10,   10,   10,   15,   15,   15,   14,   14,   14,
   14,    6,    6,    6,    6,   19,    9,    9,    9,    9,
   18,   11,   21,   21,   21,   20,   20,   20,   22,   22,
   22,   22,   22,   22,    7,    7,    7,    7,   23,   23,
   23,   16,   16,   17,   17,   12,   12,   12,   12,   12,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    2,    2,    1,    2,    2,
    1,    2,    2,    1,    1,    8,   12,    8,    4,    4,
    1,    1,    4,    4,    4,    8,    7,    8,   12,   11,
   12,    8,    7,    8,    6,    7,   11,    5,    9,    9,
    8,    3,    3,    3,    3,    3,    3,    1,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    1,    3,    1,    3,    1,    1,    1,    1,    1,    1,
    1,    2,    2,    4,    8,    5,    3,    3,    2,    2,
    1,    4,    5,    3,    3,    3,    1,    2,    3,    2,
    2,    3,    3,    2,    4,    3,    3,    3,    1,    3,
    2,    1,    3,    1,    2,   10,   10,    9,    9,    8,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    6,    0,    0,    0,   81,    0,
    0,    0,    0,    0,    8,    0,    0,   11,   14,   15,
    0,   21,   22,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    3,    7,   12,
    9,   13,   10,    0,    0,    0,   79,    0,    0,    0,
   80,    0,    0,    0,    0,    0,    2,  104,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   67,   48,   63,
   65,   66,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  103,    0,    1,   99,    0,   78,    0,
    0,   77,    0,    0,    0,    0,  105,    0,    0,    0,
    0,    0,    0,    0,    0,   68,   69,   70,   71,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   25,   23,
   24,   19,   20,    0,    0,    0,    0,    0,   82,    0,
    0,    0,  101,    0,    0,    0,    0,   87,    0,    0,
   74,    0,    0,    0,    0,    0,    0,    0,    0,   64,
   62,    0,   38,    0,    0,    0,    0,    0,    0,    0,
   85,   84,    0,    0,    0,  100,   94,    0,    0,   91,
   90,    0,    0,   88,   76,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   35,    0,    0,    0,    0,    0,
   93,   92,   89,    0,   86,    0,    0,    0,    0,    0,
   27,    0,    0,   33,    0,    0,   83,    0,    0,    0,
    0,    0,    0,   28,    0,   26,    0,    0,   16,    0,
   34,   32,   41,   18,    0,    0,  110,    0,   75,    0,
    0,   39,   40,    0,    0,  109,    0,    0,  108,    0,
    0,    0,    0,  106,  107,    0,    0,    0,   30,   31,
   29,   17,
};
final static short yydgoto[] = {                          4,
   14,   15,   16,   17,   18,   19,   20,   66,   21,   67,
   22,   23,   69,  116,   70,   24,   72,   25,   26,  137,
   83,  138,   88,
};
final static short yysindex[] = {                      -192,
 -281,  391,  425,    0,    0, -177, -267, -207,    0, -138,
  -55, -173,  425, -162,    0, -245, -240,    0,    0,    0,
  -81,    0,    0,  136, -196, -180, -150,  437,  437,  133,
  174,  437,  437,  174, -161, -146, -137,    0,    0,    0,
    0,    0,    0, -159, -256, -121,    0,  174, -256, -121,
    0, -116,   -2, -105,  -87,  425,    0,    0,  -67,  174,
  174,  174,  174,  174,  174, -202,  448,    0,    0,    0,
    0,    0, -210, -122,  -72,  141,  -78,  182,  -87, -168,
 -263,  406,   -5,    0,  -52,    0,    0, -113,    0,  215,
 -113,    0,  -18,  174,  -18,   51,    0,  215,  215,  215,
  215,  215,  215,  -82,  -40,    0,    0,    0,    0,  174,
  174,  174,  174,  174,  174,   66, -126,  425,    0,    0,
    0,    0,    0,  -17,   10,  -12, -148, -100,    0,  174,
  400, -256,    0, -121,  -14,  -62, -213,    0,  215, -212,
    0,  425,  425,  215,  215,  215,  215,  215,  215,    0,
    0,   -1,    0,  425,  125,    6,   32,  425,   46, -231,
    0,    0,  424,  425,  134,    0,    0, -121,  -49,    0,
    0,   56,  -18,    0,    0,  159,  167,  425,  175,   29,
  425,  425,  200,  425,    0,  425, -121,  208,    7,   60,
    0,    0,    0,  425,    0,   93,  149,  216,  268,   70,
    0,  241,  249,    0,  257,  282,    0,    7,   68,   97,
 -256,  290,   92,    0,  101,    0,  131, -111,    0,  425,
    0,    0,    0,    0,  111,  179,    0,  120,    0,  425,
  425,    0,    0,  425,  298,    0,  151,  165,    0,  323,
  331,  339,  172,    0,    0,  190,  199,  207,    0,    0,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   94,    0,    0,  472,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  474,    0,    0,    0,
    0,    0,    0, -244,    0,    0,    0,    0,    0,    0,
    0,    0, -228, -239, -229,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -129,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  223,    0,
    0,    0,    0,    0,    0,    0,    0, -227,    0, -208,
 -205,    0,    0,    0,    0,    0,    0, -119, -114,  -69,
  -24,   -7,   17,    0,    0,    0,    0,    0,    0,   27,
   34,   72,   74,  347,  349,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -194,    0,
    0,    0,    0,  352,  354,  358,  361,  363,  383,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -193,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -189,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
   -3,   -6,    0,    0,    0,    0,    0,  291,  309,  -28,
  232,    0,    0,    0,  364,  191,  -23,    1,    0,  387,
    0, -133,  441,
};
final static int YYTABLESIZE=728;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         27,
  126,   76,   78,  174,    5,   82,  174,   39,   58,   37,
   40,  102,   36,   30,   59,   42,   73,  102,  127,   90,
   39,   87,  102,  102,  185,   87,   72,  102,   97,  102,
   39,   98,   99,  100,  101,  102,  103,  102,  102,  195,
   41,  102,  134,  134,  102,   43,   73,   96,    9,    9,
   98,  186,   96,  104,  135,  135,   72,  102,   97,   52,
  102,   95,   36,    1,  133,  139,   37,  133,  172,  175,
   53,  117,  118,   31,    2,  173,  173,   96,   28,  105,
   98,  144,  145,  146,  147,  148,  149,  124,    9,   39,
    3,   95,   36,  136,    6,  136,   37,    7,    8,    9,
   10,  163,   56,   29,   11,   84,    6,  159,  166,    7,
    8,    9,   10,  125,  155,  160,   11,   32,   12,    6,
   85,   38,    7,    8,    9,   10,   61,  165,   35,   11,
   12,  152,  153,   57,   61,  169,   56,  136,  176,  177,
  136,   58,   33,   12,   56,   44,   86,  233,   39,   58,
  179,   58,   61,   61,  183,  161,  154,   59,   39,  119,
  188,   44,   56,   56,   93,  210,   44,   58,   58,   39,
   39,  234,   39,  136,  198,  132,   39,  202,  203,   44,
  205,   39,  206,   46,  225,   44,   55,  228,   44,   36,
  212,   39,   45,  170,   55,   39,   39,   97,   39,   39,
  142,   50,  238,  122,   44,   39,  192,   46,   36,  120,
   46,   47,   55,   55,   51,   55,  235,   44,   71,   71,
   71,   79,   71,   71,   71,   34,  240,  241,   39,  131,
  242,   57,   35,   39,   39,   39,   89,  134,   71,   57,
   92,  168,  143,    9,   47,   51,  156,    9,   59,  135,
   71,   71,   71,   71,   71,   71,   59,   57,   57,   68,
   68,   68,   68,   68,   68,   68,   94,   47,    9,   51,
  158,   58,   60,  157,   59,   59,  129,   59,   95,   68,
   60,  178,   50,  130,   71,   35,  200,  201,  181,   52,
   50,   68,   68,   68,   68,   68,   68,   52,   60,   60,
   71,   71,   71,   71,   71,   71,   71,    6,   50,   50,
    7,    8,    9,   10,  182,   52,   52,   11,  162,   73,
   71,  150,   80,   81,  167,   68,  171,   49,  184,   51,
   58,   12,   11,   54,  141,   49,   59,   51,  194,   77,
  211,   68,   68,   68,   68,   68,   68,   68,  226,  102,
  213,  214,  220,   49,   49,   51,   51,  102,  191,  193,
  102,   68,  102,  102,  102,  102,  102,  102,  102,  102,
  102,  102,  102,  102,  230,  102,  102,  207,  227,  102,
  102,    6,  102,  231,    7,    8,    9,   10,   74,  232,
    6,   11,  236,    7,    8,    9,   10,   58,   75,   11,
   11,  239,   44,   59,   48,   12,  215,  216,  180,   49,
  106,  107,  108,  109,  189,    6,  237,  190,    7,    8,
    9,   10,  121,    6,   50,   11,    7,    8,    9,   10,
  249,    6,  244,   11,    7,    8,    9,   10,   58,   12,
   11,   11,  196,   58,   59,   44,  245,   12,  250,   59,
  197,  106,  107,  108,  109,   12,    6,  251,  199,    7,
    8,    9,   10,  123,    6,  252,   11,    7,    8,    9,
   10,    5,    6,    4,   11,    7,    8,    9,   10,  151,
   12,  140,   11,  204,  106,  107,  108,  109,  208,   91,
    0,  209,   65,   65,   65,   65,   12,    6,    0,  217,
    7,    8,    9,   10,   65,    6,    0,   11,    7,    8,
    9,   10,    0,    6,    0,   11,    7,    8,    9,   10,
    0,   12,    0,   11,  221,  218,  219,    0,    0,   12,
    0,    0,  222,    0,    0,    0,    0,   12,    6,    0,
  223,    7,    8,    9,   10,    0,    6,    0,   11,    7,
    8,    9,   10,    0,    6,    0,   11,    7,    8,    9,
   10,    0,   12,    0,   11,  224,    0,    0,    0,    0,
   12,    0,    0,  229,    0,    0,    0,    0,   12,    6,
    0,  243,    7,    8,    9,   10,    0,    6,    0,   11,
    7,    8,    9,   10,    0,    6,    0,   11,    7,    8,
    9,   10,   53,   12,   54,   11,  246,   43,    0,   45,
   53,   12,   54,   42,  247,   43,   44,   45,   46,   12,
    0,   42,  248,    0,   44,    0,   46,    0,   53,   53,
   54,   54,    0,   43,   43,   45,   45,    0,   47,   42,
   42,    0,   44,   44,   46,   46,   47,    6,    0,    0,
    7,    8,    9,   10,    0,    0,    6,   11,    0,    7,
    8,    9,   10,    0,   47,   47,   11,    0,    0,    0,
    0,   12,    0,   13,    0,  106,  107,  108,  109,    0,
   12,    6,  164,    0,    7,    8,    9,   10,    0,    0,
    0,   11,  128,  106,  107,  108,  109,    0,    0,    0,
    0,   58,    0,   11,    0,   12,    0,   59,    0,    0,
  187,   60,   61,   62,   63,   64,   65,  106,  107,  108,
  109,    0,  110,  111,  112,  113,  114,  115,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
  264,   30,   31,  137,  286,   34,  140,   14,  265,   13,
  256,  256,   12,  281,  271,  256,  256,  262,  282,   48,
   27,   45,  267,  268,  256,   49,  256,  256,  256,  274,
   37,   60,   61,   62,   63,   64,   65,  282,  267,  173,
  286,  286,  256,  256,  289,  286,  286,  256,  262,  262,
  256,  283,   56,  256,  268,  268,  286,  286,  286,  256,
  289,  256,  256,  256,   88,   94,  256,   91,  282,  282,
  267,  282,  283,  281,  267,  289,  289,  286,  256,  282,
  286,  110,  111,  112,  113,  114,  115,  256,  262,   96,
  283,  286,  286,   93,  257,   95,  286,  260,  261,  262,
  263,  130,  283,  281,  267,  267,  257,  256,  132,  260,
  261,  262,  263,  282,  118,  264,  267,  256,  281,  257,
  267,  284,  260,  261,  262,  263,  256,  131,  288,  267,
  281,  258,  259,  284,  264,  135,  256,  137,  142,  143,
  140,  256,  281,  281,  264,  267,  284,  259,  155,  264,
  154,  265,  282,  283,  158,  256,  283,  271,  165,  282,
  164,  267,  282,  283,  281,  189,  267,  282,  283,  176,
  177,  283,  179,  173,  178,  289,  183,  181,  182,  267,
  184,  188,  186,  289,  208,  267,  256,  211,  267,  189,
  194,  198,  274,  256,  264,  202,  203,  265,  205,  206,
  283,  289,  226,  282,  267,  212,  256,  289,  208,  282,
  289,   21,  282,  283,   24,   25,  220,  267,   28,   29,
   30,   31,   32,   33,   34,  281,  230,  231,  235,  282,
  234,  256,  288,  240,  241,  242,   46,  256,   48,  264,
   50,  256,  283,  262,   54,   55,  264,  262,  256,  268,
   60,   61,   62,   63,   64,   65,  264,  282,  283,   28,
   29,   30,   31,   32,   33,   34,  269,   77,  262,   79,
  283,  265,  256,  264,  282,  283,  282,  271,  281,   48,
  264,  283,  256,  289,   94,  288,  258,  259,  283,  256,
  264,   60,   61,   62,   63,   64,   65,  264,  282,  283,
  110,  111,  112,  113,  114,  115,  116,  257,  282,  283,
  260,  261,  262,  263,  283,  282,  283,  267,  128,   29,
  130,  256,   32,   33,  134,   94,  136,  256,  283,  256,
  265,  281,  267,   25,  284,  264,  271,  264,  283,   31,
  281,  110,  111,  112,  113,  114,  115,  116,  281,  256,
  258,  259,  283,  282,  283,  282,  283,  264,  168,  169,
  267,  130,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  280,  283,  282,  283,  187,  282,  286,
  287,  257,  289,  283,  260,  261,  262,  263,  256,  259,
  257,  267,  282,  260,  261,  262,  263,  265,  266,  267,
  267,  282,  267,  271,  269,  281,  258,  259,  284,  274,
  270,  271,  272,  273,  281,  257,  226,  284,  260,  261,
  262,  263,  282,  257,  289,  267,  260,  261,  262,  263,
  259,  257,  282,  267,  260,  261,  262,  263,  265,  281,
  267,  267,  284,  265,  271,  267,  282,  281,  259,  271,
  284,  270,  271,  272,  273,  281,  257,  259,  284,  260,
  261,  262,  263,  282,  257,  259,  267,  260,  261,  262,
  263,    0,  257,    0,  267,  260,  261,  262,  263,  116,
  281,   95,  267,  284,  270,  271,  272,  273,  281,   49,
   -1,  284,  270,  271,  272,  273,  281,  257,   -1,  284,
  260,  261,  262,  263,  282,  257,   -1,  267,  260,  261,
  262,  263,   -1,  257,   -1,  267,  260,  261,  262,  263,
   -1,  281,   -1,  267,  284,  258,  259,   -1,   -1,  281,
   -1,   -1,  284,   -1,   -1,   -1,   -1,  281,  257,   -1,
  284,  260,  261,  262,  263,   -1,  257,   -1,  267,  260,
  261,  262,  263,   -1,  257,   -1,  267,  260,  261,  262,
  263,   -1,  281,   -1,  267,  284,   -1,   -1,   -1,   -1,
  281,   -1,   -1,  284,   -1,   -1,   -1,   -1,  281,  257,
   -1,  284,  260,  261,  262,  263,   -1,  257,   -1,  267,
  260,  261,  262,  263,   -1,  257,   -1,  267,  260,  261,
  262,  263,  256,  281,  256,  267,  284,  256,   -1,  256,
  264,  281,  264,  256,  284,  264,  256,  264,  256,  281,
   -1,  264,  284,   -1,  264,   -1,  264,   -1,  282,  283,
  282,  283,   -1,  282,  283,  282,  283,   -1,  256,  282,
  283,   -1,  282,  283,  282,  283,  264,  257,   -1,   -1,
  260,  261,  262,  263,   -1,   -1,  257,  267,   -1,  260,
  261,  262,  263,   -1,  282,  283,  267,   -1,   -1,   -1,
   -1,  281,   -1,  283,   -1,  270,  271,  272,  273,   -1,
  281,  257,  283,   -1,  260,  261,  262,  263,   -1,   -1,
   -1,  267,  287,  270,  271,  272,  273,   -1,   -1,   -1,
   -1,  265,   -1,  267,   -1,  281,   -1,  271,   -1,   -1,
  287,  275,  276,  277,  278,  279,  280,  270,  271,  272,
  273,   -1,  275,  276,  277,  278,  279,  280,
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

//#line 176 "gramatica.y"

/* CODIGO AUXILIAR */

AnalisisLexico aLex;

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
        return token;
    } catch (IOException e) {
        System.err.println("Error de lectura en el analizador léxico: " + e.getMessage());
        return 0; //Devuelvo 0 como si fuera fin de archivo
    }

}

//ERRORES SINTACTICOS
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

//CODIGO INTERMEDIO
private ArrayList<String> mainArreglo = new ArrayList<String>();
private HashMap<String, ArrayList<String>> polacaInversa = new HashMap<String, ArrayList<String>>();
private String ambito = "MAIN";

public ArrayList<String> crearArregloId(String v1, String v2){
        ArrayList<String> listaNombres = new ArrayList<String>();
        listaNombres.add(v1);
        listaNombres.add(v2);
        return listaNombres;
}

public void modificarUsos(ArrayList<String> lista, String uso){
        for (String a: lista){
            aLex.modificarUsoTS(a, uso);
        }
}

public void modificarAmbitos(ArrayList<String> lista){
        for (String a: lista){
            aLex.modificarAmbitoTS(a, ambito);
        }
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
    System.out.println("Entro a agregar "+valor);
    if (polacaInversa.containsKey(ambito)) {
        polacaInversa.get(ambito).add(valor);
    }
    else{
        mainArreglo.add(valor);
    }
}

public void imprimirPolaca() {
    System.out.println();
    System.out.println("Tabla Polaca :");
    System.out.printf("%-10s | %s%n", "Clave", "Tipo");
    System.out.println("--------------------------");

    for (Map.Entry<String, ArrayList<String>> entry : polacaInversa.entrySet()) {
        String clave = entry.getKey();
        String valores = String.join(", ", entry.getValue());
        System.out.printf("%-10s | %s%n", clave, valores);
    }
    System.out.println();
}
//#line 655 "Parser.java"
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
//#line 17 "gramatica.y"
{aLex.modificarUsoTS(val_peek(3).sval, "Nombre de programa"); polacaInversa.put(val_peek(3).sval, mainArreglo); }
break;
case 2:
//#line 18 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de programa");}
break;
case 3:
//#line 19 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura");}
break;
case 4:
//#line 20 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador final");}
break;
case 5:
//#line 21 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores");}
break;
case 6:
//#line 22 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO");}
break;
case 11:
//#line 31 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 14:
//#line 39 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:declaracion");}
break;
case 16:
//#line 43 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: if");}
break;
case 17:
//#line 44 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:if else");}
break;
case 18:
//#line 45 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:while");}
break;
case 19:
//#line 46 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
break;
case 20:
//#line 47 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:return");}
break;
case 21:
//#line 48 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:llamado de funcion");}
break;
case 22:
//#line 49 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:lambda");}
break;
case 23:
//#line 50 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
break;
case 24:
//#line 51 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA:print");}
break;
case 25:
//#line 52 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: argumento inválido en print");}
break;
case 26:
//#line 53 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 27:
//#line 54 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 28:
//#line 55 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 29:
//#line 56 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 30:
//#line 57 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 31:
//#line 58 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 32:
//#line 59 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 33:
//#line 60 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 34:
//#line 61 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 35:
//#line 62 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta cuerpo en la iteracion");}
break;
case 36:
//#line 63 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 37:
//#line 64 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 38:
//#line 65 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 39:
//#line 66 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 40:
//#line 67 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else'");}
break;
case 41:
//#line 68 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'do' en iteracion");}
break;
case 48:
//#line 77 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta comparador en expresion");}
break;
case 64:
//#line 97 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta operando en expresion");}
break;
case 72:
//#line 111 "gramatica.y"
{ArrayList<String> b = new ArrayList<String>(); b.add(val_peek(0).sval); aLex.modificarTipoTS(b, val_peek(1).sval); aLex.modificarUsoTS(val_peek(0).sval, "Nombre de variable"); aLex.modificarAmbitoTS(val_peek(0).sval, ambito);}
break;
case 73:
//#line 112 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(0).obj; aLex.modificarTipoTS(a, val_peek(1).sval); modificarUsos(a, "Nombre de variable"); modificarAmbitos(a);}
break;
case 74:
//#line 113 "gramatica.y"
{borrarAmbito();}
break;
case 75:
//#line 114 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
break;
case 76:
//#line 117 "gramatica.y"
{aLex.modificarUsoTS(val_peek(3).sval, "Nombre de funcion"); aLex.modificarAmbitoTS(val_peek(3).sval, ambito); ambito = ambito + ":" + val_peek(3).sval; polacaInversa.put(ambito, new ArrayList<String>()); }
break;
case 77:
//#line 120 "gramatica.y"
{ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add(val_peek(2).sval); arreglo.add(val_peek(0).sval); yyval = new ParserVal(crearArregloId(val_peek(2).sval, val_peek(0).sval)); }
break;
case 78:
//#line 121 "gramatica.y"
{ArrayList<String> arreglo = (ArrayList<String>) val_peek(2).obj; arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 79:
//#line 122 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 80:
//#line 123 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 81:
//#line 126 "gramatica.y"
{yyval = new ParserVal("ULONG");}
break;
case 85:
//#line 134 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta especificacion del parametro formal");}
break;
case 88:
//#line 139 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta ',' en declaracion de las variables");}
break;
case 89:
//#line 142 "gramatica.y"
{aLex.modificarUsoTS(val_peek(0).sval, "Nombre de parametro");}
break;
case 90:
//#line 143 "gramatica.y"
{aLex.modificarUsoTS(val_peek(0).sval, "Nombre de parametro");}
break;
case 91:
//#line 144 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta nombre del parametro formal");}
break;
case 92:
//#line 145 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta nombre del parametro formal");}
break;
case 93:
//#line 146 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta tipo del parametro formal");}
break;
case 94:
//#line 147 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta tipo del parametro formal");}
break;
case 95:
//#line 150 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion"); agregarAPolaca(":=");}
break;
case 96:
//#line 151 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion"); agregarAPolaca(":=");}
break;
case 97:
//#line 152 "gramatica.y"
{verificar_cantidades(val_peek(2), val_peek(0)); System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple"); agregarAPolaca("=");}
break;
case 98:
//#line 153 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple"); agregarAPolaca("=");}
break;
case 99:
//#line 156 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 100:
//#line 157 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 101:
//#line 158 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
break;
case 102:
//#line 161 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval); agregarAPolaca(val_peek(0).sval);}
break;
case 103:
//#line 162 "gramatica.y"
{String name = val_peek(2).sval + "." + val_peek(0).sval; yyval = new ParserVal(name); agregarAPolaca(name);}
break;
case 104:
//#line 165 "gramatica.y"
{aLex.agregarCteNegativaTablaDeSimbolos(val_peek(0).sval); agregarAPolaca(val_peek(0).sval); }
break;
case 105:
//#line 166 "gramatica.y"
{String cte = "-" + val_peek(0).sval; aLex.agregarCteNegativaTablaDeSimbolos(cte); agregarAPolaca(cte);}
break;
case 108:
//#line 171 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura en funcion lambda");}
break;
case 109:
//#line 172 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de cierre en funcion lambda");}
break;
case 110:
//#line 173 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores en funcion lambda");}
break;
//#line 1076 "Parser.java"
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
