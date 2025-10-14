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
   10,   10,   10,   10,   10,   10,   10,    6,    6,   13,
    6,   12,   12,   12,   11,   11,   11,   11,    7,    7,
    7,    7,    5,    5,    5,    5,   16,    8,   18,   18,
   18,   17,   17,   17,   19,   19,   19,   19,   19,   19,
    3,    3,    3,   20,   20,   20,   14,   14,   15,   15,
    9,    9,    9,    9,    9,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    1,    3,    2,    2,    1,
    8,   12,    8,    4,    4,    1,    1,    1,    4,    4,
    4,    8,    7,    8,   12,   11,   12,    8,    7,    8,
    6,    7,   11,    5,    9,    9,    8,    3,    3,    3,
    3,    3,    3,    1,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    1,    3,    1,    0,
    4,    1,    1,    1,    1,    1,    1,    1,    2,    2,
    8,    8,    3,    3,    2,    2,    1,    4,    5,    3,
    3,    3,    1,    2,    3,    2,    2,    3,    3,    2,
    5,    4,    4,    1,    3,    2,    1,    3,    1,    2,
   10,   10,    9,    9,    8,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    0,    0,    0,   77,    0,    0,
    0,    0,    0,    0,   10,    0,   16,   17,   18,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    3,    0,    9,    8,    0,    0,    0,   75,
    0,    0,   76,    0,    0,    0,    0,    2,   99,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   64,   44,
   59,   62,   63,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   98,    0,    1,    7,   94,    0,
   74,    0,   73,    0,    0,    0,  100,    0,    0,    0,
    0,    0,    0,    0,    0,   65,   66,   67,   68,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   21,   19,
   20,   14,   15,    0,    0,    0,    0,    0,   78,    0,
    0,   93,    0,   96,   92,    0,    0,    0,    0,   83,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   60,   58,    0,   34,    0,    0,    0,    0,    0,    0,
    0,   81,   80,    0,    0,    0,   95,   90,    0,    0,
   87,   86,    0,    0,   84,   91,    0,    0,    0,   61,
    0,    0,    0,    0,    0,    0,    0,   31,    0,    0,
    0,    0,    0,   89,   88,   85,    0,   82,    0,    0,
    0,    0,    0,    0,   23,    0,    0,   29,    0,    0,
   79,    0,    0,    0,    0,    0,    0,    0,   24,    0,
   22,    0,    0,   11,    0,   30,   28,   37,   13,    0,
    0,  105,    0,   72,   71,    0,    0,   35,   36,    0,
    0,  104,    0,    0,  103,    0,    0,    0,    0,  101,
  102,    0,    0,    0,   26,   27,   25,   12,
};
final static short yydgoto[] = {                          4,
   13,   14,   15,   57,   16,   58,   17,   18,   19,   60,
  106,   61,  170,   20,   63,   21,  129,   74,  130,   80,
};
final static short yysindex[] = {                      -190,
    0,  387,  415,    0, -189, -274, -264,    0, -187,  -24,
 -242,  415,   20, -246,    0, -183,    0,    0,    0, -140,
 -251,   32,  434,  434,  148,   74,  434,  434,   74, -233,
 -218,  111,    0, -132,    0,    0, -191,  -66,  -88,    0,
   74,  -88,    0,  -92, -181, -202, -123,    0,    0,  -63,
   74,   74,   74,   74,   74,   74, -254,  445,    0,    0,
    0,    0,    0,   25,  -78,  -67,  -17, -139,  184, -123,
 -192,  -97,  -52, -148,    0,  -65,    0,    0,    0,  380,
    0,  417,    0,  -40,   74,  -40,    0,  234,  234,  234,
  234,  234,  234,  -49,  -25,    0,    0,    0,    0,   74,
   74,   74,   74,   74,   74,  -75, -180,  415,    0,    0,
    0,    0,    0,  -33,   -4,  -20, -243, -241,    0,   74,
  398,    0,  -66,    0,    0,  -88,   23, -119, -250,    0,
  421, -231,  415,  415,  234,  234,  234,  234,  234,  234,
    0,    0,    3,    0,  415,  124,    8,   17,  415,   36,
 -253,    0,    0,  413,  415,  136,    0,    0,  -88,   42,
    0,    0,   44,  -40,    0,    0,   47,  149,  161,    0,
  415,  177,   53,  415,  415,  186,  415,    0,  415,  -88,
  202,   72,   22,    0,    0,    0,  415,    0,  415,   59,
  106,  211,  118,   48,    0,  227,  236,    0,  252,  261,
    0,   72,   34,    2,  -66,  277,  286,   52,    0,   78,
    0,   83, -157,    0,  415,    0,    0,    0,    0,   88,
  123,    0,  100,    0,    0,  415,  415,    0,    0,  415,
  302,    0,  107,  125,    0,  311,  327,  336,   95,    0,
    0,  166,  167,  168,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   80,
    0,    0,  429,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  431,    0,    0,    0,    0, -206,    0,    0,    0,
    0,    0,    0,    0, -248, -234, -232,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -201,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  209,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -147, -124,  -73,
  -69,  -53,  -31,    0,    0,    0,    0,    0,    0,  -14,
   14,   46,   50,  119,  345,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  348,  350,  354,  357,  359,  370,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -229,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -227,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   -3,    1,    0,   71,  112,   60,    0,   19,    0,    0,
    0,  326,    0,  220,  -27,   24,  349,    0, -128,    0,
};
final static int YYTABLESIZE=725;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         22,
  165,   94,  178,  165,   44,  126,   25,   97,   32,   35,
   79,    8,  150,   34,  152,   45,   26,  127,   97,    8,
  151,   70,   34,   69,  126,   37,   32,   95,   33,  179,
    8,  163,   34,   75,   31,  188,  127,   97,  164,   36,
   97,   59,   59,   59,   59,   59,   59,   59,   76,   97,
  167,   70,  124,   69,   57,   97,   32,  164,   33,   59,
   97,   97,   57,  114,   37,    1,   23,   97,   27,   59,
   59,   59,   59,   59,   59,   97,    2,  143,  144,   97,
   57,   57,   97,   37,   67,   69,   39,   85,   73,  115,
   38,   24,    3,   28,   64,  157,   30,   71,   72,   86,
   82,  229,  145,   59,  146,   39,   30,  128,   52,  128,
   88,   89,   90,   91,   92,   93,   52,  156,   59,   59,
   59,   59,   59,   59,   59,  230,   37,   37,   41,  168,
  169,   54,   46,  119,   52,   52,  161,   68,   59,   54,
  120,  172,  112,   37,  131,  176,   34,   37,   42,   39,
  160,  181,  128,   78,  204,  128,   34,   54,   54,  135,
  136,  137,  138,  139,  140,   42,  116,  192,   34,   34,
  196,  197,   34,  199,  220,  200,   34,  223,   37,  154,
  141,   34,   51,  206,  117,  207,   53,  128,   84,   49,
   51,   10,   34,  234,   53,   50,   34,   34,   49,   34,
   34,   87,   55,  109,   50,   31,   34,   34,   51,   51,
   55,  231,   53,   53,  110,  126,  121,   96,   97,   98,
   99,    8,  236,  237,   56,   31,  238,  127,   55,   55,
  147,   34,   56,  133,  118,   40,   34,   34,   34,   43,
   47,   46,   62,   62,   62,   70,   62,   62,   62,   46,
   56,   56,   96,   97,   98,   99,   29,  134,   81,  148,
   62,   83,  149,   30,  111,   40,   43,   46,   46,   48,
   62,   62,   62,   62,   62,   62,    5,   48,  159,    6,
    7,    8,    9,  222,    8,  171,   10,   40,    5,   43,
  174,    6,    7,    8,    9,   48,   48,  185,   10,  175,
   11,   45,  205,   33,   62,   47,  107,  108,   37,   45,
  194,  195,   11,   47,  221,   48,  208,  209,  177,   62,
   62,   62,   62,   62,   62,   62,  187,   45,   45,  189,
  215,   47,   47,    8,  226,   97,   49,  153,   49,   62,
   10,  228,   50,   97,   50,  158,   97,  162,   97,   97,
   97,   97,   97,  245,   97,   97,   97,   97,   97,   97,
  227,   97,   97,  210,  211,   97,   97,    5,   97,  232,
    6,    7,    8,    9,   49,  213,  214,   10,  184,  186,
    5,  235,   49,    6,    7,    8,    9,   49,  240,   37,
   10,   11,    5,   50,   77,    6,    7,    8,    9,  201,
   49,   49,   10,   65,   11,    5,  241,  173,    6,    7,
    8,    9,   49,   66,   10,   10,  182,    5,   50,  183,
    6,    7,    8,    9,  246,  247,  248,   10,    5,   11,
    4,  142,  190,    5,  132,    0,    6,    7,    8,    9,
  233,   11,    5,   10,  191,    6,    7,    8,    9,    0,
    0,    0,   10,   96,   97,   98,   99,   11,    5,    0,
  193,    6,    7,    8,    9,  113,   11,    5,   10,  198,
    6,    7,    8,    9,    0,    0,    0,   10,   62,   62,
   62,   62,  202,    5,    0,  203,    6,    7,    8,    9,
   62,   11,    5,   10,  212,    6,    7,    8,    9,    0,
    0,    0,   10,   96,   97,   98,   99,   11,    5,    0,
  216,    6,    7,    8,    9,    0,   11,    5,   10,  217,
    6,    7,    8,    9,    0,    0,    0,   10,    0,    0,
    0,    0,   11,    5,    0,  218,    6,    7,    8,    9,
    0,   11,    5,   10,  219,    6,    7,    8,    9,    0,
    0,    0,   10,    0,    0,    0,    0,   11,    5,    0,
  224,    6,    7,    8,    9,    0,   11,    5,   10,  225,
    6,    7,    8,    9,    0,    0,    0,   10,    0,    0,
    0,    0,   11,    5,    0,  239,    6,    7,    8,    9,
    0,   11,    5,   10,  242,    6,    7,    8,    9,    0,
   50,    0,   10,   39,    0,   41,    0,   11,   50,   38,
  243,   39,   40,   41,   42,    0,   11,   38,    0,  244,
   40,    0,   42,    0,    0,   43,   50,   50,    0,   39,
   39,   41,   41,   43,    0,   38,   38,    0,   40,   40,
   42,   42,    0,    5,   49,    0,    6,    7,    8,    9,
   50,   43,   43,   10,    5,    0,    0,    6,    7,    8,
    9,    0,    0,    0,   10,  122,    0,   11,  123,   12,
    0,    5,    0,    0,    6,    7,    8,    9,   11,    0,
  155,   10,   96,   97,   98,   99,   96,   97,   98,   99,
   96,   97,   98,   99,    0,   11,    0,    0,   49,  180,
   10,    0,  125,    0,   50,    0,  166,    0,   51,   52,
   53,   54,   55,   56,   96,   97,   98,   99,    0,  100,
  101,  102,  103,  104,  105,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          3,
  129,  256,  256,  132,  256,  256,  281,  256,   12,  256,
   38,  262,  256,   13,  256,  267,  281,  268,  267,  262,
  264,  256,   22,  256,  256,  267,  256,  282,  256,  283,
  262,  282,   32,  267,   11,  164,  268,  286,  289,  286,
  289,   23,   24,   25,   26,   27,   28,   29,  267,  256,
  282,  286,   80,  286,  256,  262,  286,  289,  286,   41,
  267,  268,  264,  256,  267,  256,  256,  274,  256,   51,
   52,   53,   54,   55,   56,  282,  267,  258,  259,  286,
  282,  283,  289,  267,   25,   26,  289,  269,   29,  282,
  274,  281,  283,  281,   24,  123,  288,   27,   28,  281,
   41,  259,  283,   85,  108,  289,  288,   84,  256,   86,
   51,   52,   53,   54,   55,   56,  264,  121,  100,  101,
  102,  103,  104,  105,  106,  283,  267,  267,  269,  133,
  134,  256,   21,  282,  282,  283,  256,   26,  120,  264,
  289,  145,  282,  267,   85,  149,  146,  267,  289,  289,
  127,  155,  129,  286,  182,  132,  156,  282,  283,  100,
  101,  102,  103,  104,  105,  289,  264,  171,  168,  169,
  174,  175,  172,  177,  202,  179,  176,  205,  267,  120,
  256,  181,  256,  187,  282,  189,  256,  164,  281,  265,
  264,  267,  192,  221,  264,  271,  196,  197,  265,  199,
  200,  265,  256,  282,  271,  182,  206,  207,  282,  283,
  264,  215,  282,  283,  282,  256,  282,  270,  271,  272,
  273,  262,  226,  227,  256,  202,  230,  268,  282,  283,
  264,  231,  264,  283,  287,   16,  236,  237,  238,   20,
   21,  256,   23,   24,   25,   26,   27,   28,   29,  264,
  282,  283,  270,  271,  272,  273,  281,  283,   39,  264,
   41,   42,  283,  288,  282,   46,   47,  282,  283,  256,
   51,   52,   53,   54,   55,   56,  257,  264,  256,  260,
  261,  262,  263,  282,  262,  283,  267,   68,  257,   70,
  283,  260,  261,  262,  263,  282,  283,  256,  267,  283,
  281,  256,  281,  284,   85,  256,  282,  283,  267,  264,
  258,  259,  281,  264,  281,  284,  258,  259,  283,  100,
  101,  102,  103,  104,  105,  106,  283,  282,  283,  283,
  283,  282,  283,  262,  283,  256,  265,  118,  265,  120,
  267,  259,  271,  264,  271,  126,  267,  128,  269,  270,
  271,  272,  273,  259,  275,  276,  277,  278,  279,  280,
  283,  282,  283,  258,  259,  286,  287,  257,  289,  282,
  260,  261,  262,  263,  256,  258,  259,  267,  159,  160,
  257,  282,  264,  260,  261,  262,  263,  265,  282,  267,
  267,  281,  257,  271,  284,  260,  261,  262,  263,  180,
  282,  283,  267,  256,  281,  257,  282,  284,  260,  261,
  262,  263,  265,  266,  267,  267,  281,  257,  271,  284,
  260,  261,  262,  263,  259,  259,  259,  267,    0,  281,
    0,  106,  284,  257,   86,   -1,  260,  261,  262,  263,
  221,  281,  257,  267,  284,  260,  261,  262,  263,   -1,
   -1,   -1,  267,  270,  271,  272,  273,  281,  257,   -1,
  284,  260,  261,  262,  263,  282,  281,  257,  267,  284,
  260,  261,  262,  263,   -1,   -1,   -1,  267,  270,  271,
  272,  273,  281,  257,   -1,  284,  260,  261,  262,  263,
  282,  281,  257,  267,  284,  260,  261,  262,  263,   -1,
   -1,   -1,  267,  270,  271,  272,  273,  281,  257,   -1,
  284,  260,  261,  262,  263,   -1,  281,  257,  267,  284,
  260,  261,  262,  263,   -1,   -1,   -1,  267,   -1,   -1,
   -1,   -1,  281,  257,   -1,  284,  260,  261,  262,  263,
   -1,  281,  257,  267,  284,  260,  261,  262,  263,   -1,
   -1,   -1,  267,   -1,   -1,   -1,   -1,  281,  257,   -1,
  284,  260,  261,  262,  263,   -1,  281,  257,  267,  284,
  260,  261,  262,  263,   -1,   -1,   -1,  267,   -1,   -1,
   -1,   -1,  281,  257,   -1,  284,  260,  261,  262,  263,
   -1,  281,  257,  267,  284,  260,  261,  262,  263,   -1,
  256,   -1,  267,  256,   -1,  256,   -1,  281,  264,  256,
  284,  264,  256,  264,  256,   -1,  281,  264,   -1,  284,
  264,   -1,  264,   -1,   -1,  256,  282,  283,   -1,  282,
  283,  282,  283,  264,   -1,  282,  283,   -1,  282,  283,
  282,  283,   -1,  257,  265,   -1,  260,  261,  262,  263,
  271,  282,  283,  267,  257,   -1,   -1,  260,  261,  262,
  263,   -1,   -1,   -1,  267,  286,   -1,  281,  289,  283,
   -1,  257,   -1,   -1,  260,  261,  262,  263,  281,   -1,
  283,  267,  270,  271,  272,  273,  270,  271,  272,  273,
  270,  271,  272,  273,   -1,  281,   -1,   -1,  265,  287,
  267,   -1,  286,   -1,  271,   -1,  286,   -1,  275,  276,
  277,  278,  279,  280,  270,  271,  272,  273,   -1,  275,
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
"$$1 :",
"expresiones : expresiones operador error $$1",
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
"expresion_lambda : PARENTESISA tipo ID PARENTESISC sentencias LLAVEC PARENTESISA tipo_cte PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC LLAVEA sentencias PARENTESISA tipo_cte PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC sentencias PARENTESISA tipo_cte PARENTESISC",
};

//#line 159 "gramatica.y"

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
//#line 566 "Parser.java"
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
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta comparador en expresion");}
break;
case 60:
//#line 84 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta operando en expresion");}
break;
case 61:
//#line 84 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta operando en expresion");}
break;
case 72:
//#line 101 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
break;
case 73:
//#line 104 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(2)); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 74:
//#line 105 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 75:
//#line 106 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 76:
//#line 107 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 81:
//#line 118 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta especificacion del parametro formal");}
break;
case 84:
//#line 123 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta ',' en declaracion de las variables");}
break;
case 87:
//#line 128 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta nombre del parametro formal");}
break;
case 88:
//#line 129 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta nombre del parametro formal");}
break;
case 89:
//#line 130 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta tipo del parametro formal");}
break;
case 90:
//#line 131 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: falta tipo del parametro formal");}
break;
case 91:
//#line 134 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: declaracion y asignacion");}
break;
case 92:
//#line 135 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion");}
break;
case 93:
//#line 136 "gramatica.y"
{verificar_cantidades(val_peek(3), val_peek(1)); System.out.println("LINEA: "+aLex.getNroLinea()+" SENTENCIA: asignacion multiple");}
break;
case 94:
//#line 139 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 95:
//#line 140 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 96:
//#line 141 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
break;
case 99:
//#line 148 "gramatica.y"
{aLex.agregarATablaDeSimbolos(val_peek(0).sval);}
break;
case 100:
//#line 149 "gramatica.y"
{String cte = "-" + val_peek(0).sval; aLex.agregarATablaDeSimbolos(cte);}
break;
case 103:
//#line 154 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de apertura en funcion lambda");}
break;
case 104:
//#line 155 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta delimitador de cierre en funcion lambda");}
break;
case 105:
//#line 156 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Faltan delimitadores en funcion lambda");}
break;
//#line 943 "Parser.java"
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
