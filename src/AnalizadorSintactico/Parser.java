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
    5,    5,    5,    5,    7,    7,    7,    7,    8,    8,
    8,   12,   12,   12,   12,    9,    9,    9,   13,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   15,   15,   15,   15,   15,
   15,   15,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   16,    6,    6,    6,    6,    6,
   20,   20,   20,   20,   11,   11,   11,   11,   11,   11,
   21,   21,   21,   24,   22,   22,   22,   22,    4,    4,
    4,    4,    4,   26,   27,   27,   28,   28,   28,   29,
   29,   29,   30,   30,   30,   30,   25,   25,   25,   10,
   10,   10,   31,   31,   19,   19,   19,   18,   18,   17,
   23,   23,
};
final static short yylen[] = {                            2,
    2,    1,    3,    3,    3,    3,    2,    1,    1,    1,
    1,    1,    1,    1,    5,    5,    4,    4,    5,    5,
    1,    4,    4,    4,    3,    5,    3,    1,    5,    6,
    6,    5,    8,    8,    7,    2,    4,    6,    8,    4,
    7,    4,    2,    5,    5,    4,    7,    7,    6,    1,
    3,    5,    7,    3,    6,    3,    3,    3,    3,    3,
    3,    1,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    1,    5,    4,    4,    4,    1,
    4,    3,    3,    3,    3,    3,    3,    3,    1,    3,
    1,    1,    1,    4,    1,    1,    1,    1,    3,    3,
    1,    3,    3,    2,    5,    4,    3,    1,    2,    3,
    2,    1,    2,    2,    2,    2,    5,    3,    2,    3,
    3,    1,    2,    2,    1,    3,    2,    1,    3,    1,
    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    2,    0,    0,    0,  130,    0,
    0,    8,    9,   10,   11,   12,   13,   14,    0,   21,
    0,   28,    0,    0,   80,  101,    0,  122,    1,    0,
  131,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   62,   91,   89,   92,   93,    0,    0,    0,
    6,    5,    7,    0,    0,  123,    0,    0,   36,    0,
    0,    0,    0,    0,    0,    0,  124,  104,    4,    3,
    0,  132,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  129,    0,
  125,  120,    0,    0,   27,  102,    0,    0,   99,    0,
    0,    0,    0,  108,  112,  103,  100,    0,    0,  121,
    0,    0,    0,    0,   85,   86,   87,   88,    0,    0,
    0,    0,    0,    0,   90,    0,    0,    0,    0,   24,
    0,    0,    0,   78,    0,  127,   40,    0,   37,    0,
    0,  116,    0,  115,  113,  111,  106,    0,  109,   77,
   79,    0,   94,    0,    0,    0,   29,    0,    0,    0,
    0,    0,   32,   19,   20,   15,   16,  126,   26,   76,
  105,  110,  107,  118,    0,    0,    0,   38,    0,   31,
    0,   30,    0,    0,   41,    0,    0,    0,   35,  117,
   39,   34,   33,
};
final static short yydgoto[] = {                          4,
    5,   11,   12,   13,   14,   15,   16,   17,   18,   19,
   41,   20,   21,   22,   42,   43,  112,   44,  100,   25,
   45,   90,   46,   47,  122,   26,   27,  113,  114,  115,
   28,
};
final static short yysindex[] = {                      -241,
  318, -239,  318,    0,    0,  238, -273, -270,    0, -264,
 -171,    0,    0,    0,    0,    0,    0,    0, -127,    0,
 -246,    0, -260, -121,    0,    0, -239,    0,    0, -128,
    0,  -76, -177,  179,  179,  179,  179,  179,  179,  266,
  294, -189,    0,    0,    0,    0,    0,  211,  179, -207,
    0,    0,    0, -215, -161,    0, -157, -179,    0, -222,
  177, -251, -122,  179, -215, -161,    0,    0,    0,    0,
  179,    0,  285,  285,  285,  285,  285,  285, -140,  179,
  179,  179,  179,  179,  179,  179,  179,  179,  179, -144,
 -239,  183, -133, -109,  228, -132,  277, -122,    0,  186,
    0,    0,  -98, -142,    0,    0,  179,  177,    0, -161,
 -226, -152, -205,    0,    0,    0,    0,  250,  197,    0,
  217,  -42, -180,  212,    0,    0,    0,    0,  285,  285,
  285,  285,  285,  285,    0,  215, -239,  -89,  -84,    0,
  -73,  -64,  -59,    0, -215,    0,    0,  -54,    0,  254,
 -181,    0, -161,    0,    0,    0,    0,  177,    0,    0,
    0, -161,    0,  179, -129,  -44,    0, -239,  -26, -239,
  -14,   23,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  224,   -9,   31,    0,   33,    0,
   38,    0,   12, -161,    0,   19,   24,   36,    0,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -237,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -61,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -97,    0,    0,    0,    0,    0,    0,    0,    0,
  -72,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -53,    0,  -95,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -65,  -20,  -15,   30,   35,   80,    0,   71,
   76,   84,   86,   85,  130,  135,  142,  154,  166,    0,
    0,    0,    0,  -41,    0,    0,    0,  281,    0,  -32,
    0,    0,  -23,  -11,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   -3,    9,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  171,  173,
  176,  178,  182,  184,    0,    0,    0,   18,   27,    0,
   39,   47,   59,    0,    0,    0,    0,   68,    0,   77,
    0,    0, -131,    0,    0,    0,    0,    0,    0,    0,
    0,  -34,    0,    0,    0,   89,    0,    0,   97,    0,
  109,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  118,    0,    0,    0,    0,
    0,    0,  127,    0,    0,  139,  147,  159,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,  344,  125,    0,    0,    0,    0,    0,    0,    4,
   34,    0,    0,    0,  308,    0,    3,   -1,  290,    0,
  501,    0,  -45,    0,    0,    0,    0,  252, -112,    0,
    0,
};
final static int YYTABLESIZE=585;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         24,
  159,   24,   29,   23,  116,   23,   60,   48,  101,   24,
   49,   57,   58,   23,    1,   10,    1,   56,  128,  101,
   61,   63,   67,   50,  128,    2,   62,   68,   24,  128,
  128,  128,   23,  106,  117,    9,  128,   55,  159,   59,
   10,    3,   92,    3,  128,  183,  107,   98,  128,   31,
  110,  128,   96,  102,  146,   33,    9,  104,  108,   99,
   56,   67,  111,  109,  120,   50,    1,   73,   74,   75,
   76,   77,   78,  146,  110,    1,  157,  165,  166,  124,
    9,   95,   97,  158,   51,    6,  111,   72,    7,    8,
    9,  136,   91,    3,   56,   10,   67,  118,    1,  178,
  181,  103,    3,  155,  121,   10,  105,  158,  152,  154,
  156,  135,   52,  153,   10,    1,  148,  129,  130,  131,
  132,  133,  134,  167,  114,    3,    1,   69,    6,  186,
  114,    7,    8,    9,   10,   53,  114,  172,   10,   10,
  150,  123,    3,  149,   10,   10,   54,   64,  139,  142,
  114,  182,   65,    3,   53,   70,   55,  114,  128,  128,
  184,   55,  128,  128,  128,  187,   66,   66,  189,  128,
  191,  128,  128,  128,  128,  128,  140,  128,  128,  128,
  128,  128,  128,   75,  128,  128,  128,  147,  128,  128,
   70,  128,  200,  128,   50,   50,  173,  185,   50,   50,
   50,  174,   43,   43,   71,   50,   43,   43,   43,   75,
   75,   50,  175,   43,   25,   25,   70,   70,   25,   25,
   25,  176,   50,   83,   83,   25,  177,   83,   83,   83,
   43,  179,   54,   54,   83,   72,   54,   54,   54,  163,
   69,  188,   25,   54,   51,   51,  164,  119,   51,   51,
   51,   83,   82,   82,  119,   51,   82,   82,   82,  190,
   54,   72,   72,   82,   84,   84,   69,   69,   84,   84,
   84,  192,   51,   46,   46,   84,  195,   46,   46,   46,
   82,  193,   22,   22,   46,   71,   22,   22,   22,  196,
   73,  197,   84,   22,   23,   23,  198,  199,   23,   23,
   23,   46,   17,   17,  201,   23,   17,   17,   17,  202,
   22,   71,   71,   17,   18,   18,   73,   73,   18,   18,
   18,  203,   23,   42,   42,   18,   95,   42,   42,   42,
   17,   96,   81,   81,   42,   74,   81,   81,   81,   97,
   64,   98,   18,   81,   52,   52,   30,   79,   52,   52,
   52,   42,   45,   45,  119,   52,   45,   45,   45,  151,
   81,   74,   74,   45,   44,   44,   64,   64,   44,   44,
   44,    0,   52,   55,   55,   44,    0,   55,   55,   55,
   45,    0,   49,   49,   55,   66,   49,   49,   49,    0,
   63,    0,   44,   49,   53,   53,    0,   65,   53,   53,
   53,   55,   48,   48,    0,   53,   48,   48,   48,   67,
   49,   66,   66,   48,   47,   47,   63,   63,   47,   47,
   47,   68,   53,   65,   65,   47,   57,    0,   59,    0,
   48,   56,  110,   58,    0,   67,   67,   60,    9,   61,
  137,  138,   47,   31,  111,   32,    0,   68,   68,   33,
   31,    0,   57,   57,   59,   59,   33,   56,   56,   58,
   58,   31,    0,   60,   60,   61,   61,   33,    0,  168,
  169,  144,  170,  171,  145,   31,   93,   32,    0,    0,
    0,   33,  161,    0,    0,  145,   80,   81,   82,   83,
    0,    0,   94,   80,   81,   82,   83,   80,   81,   82,
   83,    0,   31,  162,   32,    0,    0,    0,   33,  141,
  194,    0,   34,   35,   36,   37,   38,   39,   40,   80,
   81,   82,   83,   80,   81,   82,   83,    0,    0,    0,
   31,    0,   32,    0,    0,  160,   33,    0,    0,  180,
   34,   35,   36,   37,   38,   39,   80,   81,   82,   83,
   91,   91,   91,   91,   80,   81,   82,   83,  143,    0,
    0,    0,   91,   80,   81,   82,   83,    0,   84,   85,
   86,   87,   88,   89,    6,    0,    0,    7,    8,    9,
  125,  126,  127,  128,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
  113,    3,    2,    1,  256,    3,  267,  281,   54,   11,
  281,  258,  259,   11,  256,  267,  256,   19,  256,   65,
  281,   23,   24,  288,  262,  267,   23,   27,   30,  267,
  268,  269,   30,  256,  286,  262,  274,  289,  151,  286,
  267,  283,   42,  283,  282,  158,  269,   49,  286,  265,
  256,  289,   49,   55,  100,  271,  262,   57,  281,  267,
   62,   63,  268,  286,   66,  288,  256,   34,   35,   36,
   37,   38,   39,  119,  256,  256,  282,  258,  259,   79,
  262,   48,   49,  289,  256,  257,  268,  265,  260,  261,
  262,   91,  282,  283,   96,  267,   98,   64,  256,  145,
  282,  259,  283,  256,   71,  267,  286,  289,  110,  111,
  112,  256,  284,  111,  267,  256,  259,   84,   85,   86,
   87,   88,   89,  123,  256,  283,  256,  256,  257,  259,
  262,  260,  261,  262,  267,   11,  268,  137,  267,  267,
  107,  282,  283,  286,  267,  267,  274,  269,  282,  282,
  282,  153,  274,  283,   30,  284,  289,  289,  256,  257,
  162,  289,  260,  261,  262,  165,  289,  289,  168,  267,
  170,  267,  270,  271,  272,  273,  286,  275,  276,  277,
  278,  279,  280,  256,  282,  283,  284,  286,  286,  287,
  256,  289,  194,  289,  256,  257,  286,  164,  260,  261,
  262,  286,  256,  257,  281,  267,  260,  261,  262,  282,
  283,  288,  286,  267,  256,  257,  282,  283,  260,  261,
  262,  286,  284,  256,  257,  267,  286,  260,  261,  262,
  284,  286,  256,  257,  267,  256,  260,  261,  262,  282,
  256,  286,  284,  267,  256,  257,  289,  282,  260,  261,
  262,  284,  256,  257,  289,  267,  260,  261,  262,  286,
  284,  282,  283,  267,  256,  257,  282,  283,  260,  261,
  262,  286,  284,  256,  257,  267,  286,  260,  261,  262,
  284,  259,  256,  257,  267,  256,  260,  261,  262,  259,
  256,  259,  284,  267,  256,  257,  259,  286,  260,  261,
  262,  284,  256,  257,  286,  267,  260,  261,  262,  286,
  284,  282,  283,  267,  256,  257,  282,  283,  260,  261,
  262,  286,  284,  256,  257,  267,  256,  260,  261,  262,
  284,  256,  256,  257,  267,  256,  260,  261,  262,  256,
  256,  256,  284,  267,  256,  257,    3,   40,  260,  261,
  262,  284,  256,  257,   65,  267,  260,  261,  262,  108,
  284,  282,  283,  267,  256,  257,  282,  283,  260,  261,
  262,   -1,  284,  256,  257,  267,   -1,  260,  261,  262,
  284,   -1,  256,  257,  267,  256,  260,  261,  262,   -1,
  256,   -1,  284,  267,  256,  257,   -1,  256,  260,  261,
  262,  284,  256,  257,   -1,  267,  260,  261,  262,  256,
  284,  282,  283,  267,  256,  257,  282,  283,  260,  261,
  262,  256,  284,  282,  283,  267,  256,   -1,  256,   -1,
  284,  256,  256,  256,   -1,  282,  283,  256,  262,  256,
  258,  259,  284,  265,  268,  267,   -1,  282,  283,  271,
  265,   -1,  282,  283,  282,  283,  271,  282,  283,  282,
  283,  265,   -1,  282,  283,  282,  283,  271,   -1,  258,
  259,  286,  258,  259,  289,  265,  266,  267,   -1,   -1,
   -1,  271,  286,   -1,   -1,  289,  270,  271,  272,  273,
   -1,   -1,  282,  270,  271,  272,  273,  270,  271,  272,
  273,   -1,  265,  287,  267,   -1,   -1,   -1,  271,  282,
  287,   -1,  275,  276,  277,  278,  279,  280,  281,  270,
  271,  272,  273,  270,  271,  272,  273,   -1,   -1,   -1,
  265,   -1,  267,   -1,   -1,  286,  271,   -1,   -1,  286,
  275,  276,  277,  278,  279,  280,  270,  271,  272,  273,
  270,  271,  272,  273,  270,  271,  272,  273,  282,   -1,
   -1,   -1,  282,  270,  271,  272,  273,   -1,  275,  276,
  277,  278,  279,  280,  257,   -1,   -1,  260,  261,  262,
   80,   81,   82,   83,  267,
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

//#line 247 "gramatica.y"

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
//#line 797 "Parser.java"
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
case 14:
//#line 44 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: if"); agregarAPolaca("if");}
break;
case 15:
//#line 48 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Return"); ArrayList<String> a = (ArrayList<String>)val_peek(2).obj; agregarListaAPolaca(a); agregarAPolaca("return");}
break;
case 16:
//#line 49 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Return"); agregarAPolaca("return");}
break;
case 17:
//#line 50 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 18:
//#line 51 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 19:
//#line 55 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Print"); agregarAPolaca(val_peek(2).sval); agregarAPolaca("print");}
break;
case 20:
//#line 56 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Print"); agregarAPolaca("print");}
break;
case 22:
//#line 61 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 23:
//#line 62 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 24:
//#line 63 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTÁCTICO: Falta argumento en print");}
break;
case 25:
//#line 64 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTÁCTICO: Falta argumento en print y falta ';' ");}
break;
case 26:
//#line 68 "gramatica.y"
{agregarAPolaca("else"); agregarBifurcacion("then"); agregarAPolaca("cuerpo");}
break;
case 27:
//#line 69 "gramatica.y"
{agregarAPolaca("cuerpo"); acomodarBifurcacion();}
break;
case 29:
//#line 74 "gramatica.y"
{agregarAPolaca("then"); agregarBifurcacion("cond");}
break;
case 30:
//#line 78 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 31:
//#line 79 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 32:
//#line 80 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 33:
//#line 81 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion");}
break;
case 34:
//#line 82 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion");}
break;
case 35:
//#line 83 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion");}
break;
case 36:
//#line 84 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 37:
//#line 85 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif'");}
break;
case 38:
//#line 86 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 39:
//#line 87 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then'");}
break;
case 40:
//#line 88 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else'");}
break;
case 41:
//#line 89 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else', 'then'");}
break;
case 42:
//#line 90 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 43:
//#line 91 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 44:
//#line 92 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion y falta ';' ");}
break;
case 45:
//#line 93 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion y falta ';' ");}
break;
case 46:
//#line 94 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion y falta ';' ");}
break;
case 47:
//#line 95 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de apertura de la condicion y falta ';' ");}
break;
case 48:
//#line 96 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta parentesis de cierre de la condicion y falta ';' ");}
break;
case 49:
//#line 97 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: faltan parentesis de la condicion y falta ';' ");}
break;
case 50:
//#line 98 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif' y falta ';' ");}
break;
case 51:
//#line 99 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta 'endif' y falta ';' ");}
break;
case 52:
//#line 100 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then' y falta ';' ");}
break;
case 53:
//#line 101 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'then' y falta ';' ");}
break;
case 54:
//#line 102 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else' y falta ';' ");}
break;
case 55:
//#line 103 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTÁCTICO: falta contenido en bloque 'else', 'then' y falta ';' ");}
break;
case 56:
//#line 107 "gramatica.y"
{agregarAPolaca(">"); agregarAPolaca("cond");}
break;
case 57:
//#line 108 "gramatica.y"
{agregarAPolaca(">="); agregarAPolaca("cond");}
break;
case 58:
//#line 109 "gramatica.y"
{agregarAPolaca("<"); agregarAPolaca("cond");}
break;
case 59:
//#line 110 "gramatica.y"
{agregarAPolaca(">="); agregarAPolaca("cond");}
break;
case 60:
//#line 111 "gramatica.y"
{agregarAPolaca("=="); agregarAPolaca("cond");}
break;
case 61:
//#line 112 "gramatica.y"
{agregarAPolaca("=!"); agregarAPolaca("cond");}
break;
case 62:
//#line 113 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Condicion incompleta");}
break;
case 76:
//#line 133 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion y asignacion"); ArrayList<String> a = new ArrayList<String>(); a.add(val_peek(3).sval); modificarTipoTS(a, val_peek(4).sval); modificarUsos(a, "Nombre de variable"); agregarAPolaca(val_peek(3).sval); agregarAPolaca(":=");}
break;
case 77:
//#line 134 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion"); agregarAPolaca(val_peek(3).sval); agregarAPolaca(":=");}
break;
case 78:
//#line 135 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion multiple"); ArrayList<String> l1 = (ArrayList<String>)val_peek(3).obj; ArrayList<String> l3 = (ArrayList<String>)val_peek(1).obj; verificar_cantidades(l1, l3); agregarListaAPolaca(l3); agregarListaAPolaca(l1); agregarAPolaca("=");}
break;
case 79:
//#line 136 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Asignacion multiple"); agregarListaAPolaca((ArrayList<String>)val_peek(1).obj); agregarAPolaca(val_peek(3).sval); agregarAPolaca("=");}
break;
case 80:
//#line 137 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 85:
//#line 148 "gramatica.y"
{agregarAPolaca("+");}
break;
case 86:
//#line 149 "gramatica.y"
{agregarAPolaca("-");}
break;
case 87:
//#line 150 "gramatica.y"
{agregarAPolaca("*");}
break;
case 88:
//#line 151 "gramatica.y"
{agregarAPolaca("/");}
break;
case 90:
//#line 153 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta operando en expresion");}
break;
case 91:
//#line 157 "gramatica.y"
{agregarAPolaca(val_peek(0).sval);}
break;
case 92:
//#line 158 "gramatica.y"
{agregarAPolaca(val_peek(0).sval);}
break;
case 94:
//#line 163 "gramatica.y"
{agregarAPolaca(val_peek(3).sval); agregarAPolaca("call");}
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
case 99:
//#line 174 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de variable"); ArrayList<String> b = new ArrayList<String>(); b.add(val_peek(1).sval); modificarTipoTS(b, val_peek(2).sval); modificarUsoTS(val_peek(1).sval, "Nombre de variable");}
break;
case 100:
//#line 175 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de variables"); ArrayList<String> a = (ArrayList<String>)val_peek(1).obj; modificarTipoTS(a, val_peek(2).sval); modificarUsos(a, "Nombre de variable");}
break;
case 101:
//#line 176 "gramatica.y"
{agregarSentencia("LINEA "+aLex.getNroLinea()+" SENTENCIA: Declaracion de funcion");}
break;
case 102:
//#line 177 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 103:
//#line 178 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ';' al final de la sentencia");}
break;
case 104:
//#line 182 "gramatica.y"
{String ambitoConFuncion = ambito; borrarAmbito(); limpiarPolaca(ambitoConFuncion);}
break;
case 105:
//#line 186 "gramatica.y"
{modificarUsoTS(val_peek(3).sval, "Nombre de funcion"); ambito = ambito + ":" + val_peek(3).sval; modificarAmbitosTS((ArrayList<String>)val_peek(1).obj); polacaInversa.put(ambito, (ArrayList<String>) val_peek(1).obj); }
break;
case 106:
//#line 187 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta nombre de la funcion");}
break;
case 107:
//#line 191 "gramatica.y"
{ArrayList<String> a = (ArrayList<String>)val_peek(2).obj; a.add(val_peek(0).sval); yyval = new ParserVal(a);}
break;
case 108:
//#line 192 "gramatica.y"
{ArrayList<String> a = new ArrayList<String>(); a.add(val_peek(0).sval); yyval = new ParserVal(a);}
break;
case 109:
//#line 193 "gramatica.y"
{agregarError("LINEA: "+aLex.getNroLinea()+" ERROR SINTACTICO:: falta ',' en declaracion de las variables");}
break;
case 110:
//#line 197 "gramatica.y"
{modificarUsoTS(val_peek(0).sval, "Nombre de parametro"); yyval = new ParserVal(val_peek(0).sval);}
break;
case 111:
//#line 198 "gramatica.y"
{modificarUsoTS(val_peek(0).sval, "Nombre de parametro"); yyval = new ParserVal(val_peek(0).sval);}
break;
case 112:
//#line 199 "gramatica.y"
{yyval = new ParserVal();}
break;
case 113:
//#line 203 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta nombre del parametro formal");}
break;
case 114:
//#line 204 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta nombre del parametro formal");}
break;
case 115:
//#line 205 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta tipo del parametro formal");}
break;
case 116:
//#line 206 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta tipo del parametro formal");}
break;
case 117:
//#line 210 "gramatica.y"
{agregarAPolaca(val_peek(0).sval); agregarAPolaca("->");}
break;
case 118:
//#line 211 "gramatica.y"
{agregarAPolaca(val_peek(0).sval); agregarAPolaca("->");}
break;
case 119:
//#line 212 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: falta especificacion del parametro formal");}
break;
case 120:
//#line 216 "gramatica.y"
{ArrayList<String> arreglo = (ArrayList<String>) val_peek(2).obj; arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 121:
//#line 217 "gramatica.y"
{ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add(val_peek(2).sval); arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo); }
break;
case 122:
//#line 218 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre variables de la lista");}
break;
case 125:
//#line 227 "gramatica.y"
{ArrayList<String> arreglo = new ArrayList<String>(); arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 126:
//#line 228 "gramatica.y"
{ArrayList<String> arreglo = (ArrayList<String>) val_peek(2).obj; arreglo.add(val_peek(0).sval); yyval = new ParserVal(arreglo);}
break;
case 127:
//#line 229 "gramatica.y"
{agregarError("LINEA "+aLex.getNroLinea()+" ERROR SINTACTICO: Falta ',' entre constantes de la lista");}
break;
case 128:
//#line 233 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval); }
break;
case 129:
//#line 234 "gramatica.y"
{String name = val_peek(2).sval + "." + val_peek(0).sval; yyval = new ParserVal(name);}
break;
case 130:
//#line 238 "gramatica.y"
{yyval = new ParserVal("ULONG");}
break;
case 131:
//#line 242 "gramatica.y"
{aLex.agregarCteNegativaTS(val_peek(0).sval);}
break;
case 132:
//#line 243 "gramatica.y"
{String cte = "-" + val_peek(0).sval; aLex.agregarCteNegativaTS(cte);}
break;
//#line 1350 "Parser.java"
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
