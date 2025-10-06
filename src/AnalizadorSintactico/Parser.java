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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    5,    5,    5,    5,    5,    5,    6,
    6,    6,    9,    9,    9,   10,   10,   10,   10,   12,
   12,    7,    7,   14,   14,   13,   15,   15,   16,   16,
    4,    4,   17,   17,   11,   11,    8,    8,
};
final static short yylen[] = {                            2,
    2,    3,    2,    1,    1,    7,    9,    6,    5,    1,
    1,    5,    5,    3,    3,    3,    3,    3,    3,    3,
    3,    1,    1,    1,    4,    1,    1,    1,    1,    5,
    3,    3,    6,    1,    3,    1,    3,    1,    2,    2,
    4,    4,    1,    3,    1,    3,    8,    8,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    1,    0,    0,    0,   36,    0,    0,
    0,    0,    4,    5,   10,   11,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    2,    3,    0,    0,   34,
    0,    0,    0,   24,    0,    0,    0,    0,   22,   23,
    0,    0,    0,    0,   46,    0,    0,    0,   32,   43,
    0,   35,    0,    0,    0,   26,   27,   28,   29,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   41,    0,    0,    0,   38,   42,    0,    0,    0,
   21,    0,    0,    0,    0,    0,    0,    0,   20,   12,
   13,    9,    0,    0,   40,   39,    0,    0,   44,    0,
   25,    0,    0,    0,    8,    0,   33,   37,   31,    0,
    0,    6,    0,    0,    0,    0,   48,   47,   30,    7,
};
final static short yydgoto[] = {                          2,
    4,   12,   13,   14,   37,   38,   15,   16,   39,   66,
   40,   80,   18,   19,   75,   76,   51,
};
final static short yysindex[] = {                      -253,
 -267,    0, -233,    0, -258, -246, -239,    0, -232,   11,
 -204, -196,    0,    0,    0,    0, -207, -184,  -39, -245,
 -234, -245, -245, -181, -180,    0,    0, -245,  -40,    0,
  -43, -175, -170,    0,  -33, -245, -178, -134,    0,    0,
 -176, -174, -226, -171,    0, -155, -152, -251,    0,    0,
  -42,    0, -245, -147, -267,    0,    0,    0,    0, -245,
 -245, -245, -245, -245, -245, -197, -158, -157, -154, -131,
 -267,    0, -170, -170,  -36,    0,    0, -153, -198,  -35,
    0, -220, -194, -194, -194, -194, -194, -194,    0,    0,
    0,    0, -267, -151,    0,    0, -267, -251,    0, -170,
    0, -245, -267, -146,    0, -183,    0,    0,    0, -156,
 -112,    0, -133, -132, -170, -138,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -44,
    0,    0,    0,    0,    0,    0,  -37,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -41,    0,
    0,    0,    0,    0, -177,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -130, -129, -128, -127, -126, -125,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -34,    0,  139,    0,  135,  -10,    0,    0,   -6,    0,
    7,    0,   -7,  141,    0,   62,    0,
};
final static int YYTABLESIZE=248;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         45,
   33,   78,   45,   25,   33,   24,   34,   98,  102,   17,
    8,   43,   24,    1,   42,    3,   73,   47,   17,   34,
   82,   35,   20,    5,   30,   54,    6,    7,    8,    9,
   34,   41,   35,   10,   21,   36,   94,  103,  104,   52,
   74,   22,   79,   56,   57,   58,   59,   11,   23,   83,
   84,   85,   86,   87,   88,   69,   24,    8,  105,   89,
    5,   28,  107,    6,    7,    8,    9,   34,  111,   35,
   10,   56,   57,   58,   59,   56,   57,   58,   59,   95,
   96,  113,   29,   10,   11,   45,   46,   26,  100,   50,
   74,  110,   45,   45,   45,   45,   10,   45,   45,   45,
   45,   45,   45,   55,   45,   67,  109,   68,   45,   45,
   70,   99,  114,   56,   57,   58,   59,   56,   57,   58,
   59,  119,   56,   57,   58,   59,   71,   90,   91,  106,
  115,   92,   93,   72,   81,   56,   57,   58,   59,  112,
   60,   61,   62,   63,   64,   65,  116,  120,  117,  118,
   27,   15,   17,   14,   16,   18,   19,   44,   31,  108,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   45,    0,    0,    0,    0,   45,
    0,    0,    0,    0,   32,    0,   34,   45,    0,    0,
   48,   45,   49,   77,   45,   97,  101,   53,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
   44,   44,   44,   11,   44,   46,   44,   44,   44,    3,
  262,   22,   46,  267,   21,  283,  268,   28,   12,  265,
   55,  267,  281,  257,   18,   36,  260,  261,  262,  263,
  265,  266,  267,  267,  281,  281,   71,  258,  259,   33,
   48,  281,   53,  270,  271,  272,  273,  281,  281,   60,
   61,   62,   63,   64,   65,  282,   46,  262,   93,   66,
  257,  269,   97,  260,  261,  262,  263,  265,  103,  267,
  267,  270,  271,  272,  273,  270,  271,  272,  273,   73,
   74,  265,  267,  267,  281,  267,  267,  284,  287,  265,
   98,  102,  270,  271,  272,  273,  267,  275,  276,  277,
  278,  279,  280,  282,  282,  282,  100,  282,  286,  287,
  282,  265,  106,  270,  271,  272,  273,  270,  271,  272,
  273,  115,  270,  271,  272,  273,  282,  286,  286,  281,
  287,  286,  264,  286,  282,  270,  271,  272,  273,  286,
  275,  276,  277,  278,  279,  280,  259,  286,  282,  282,
   12,  282,  282,  282,  282,  282,  282,   23,   18,   98,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  269,   -1,   -1,   -1,   -1,  274,
   -1,   -1,   -1,   -1,  274,   -1,  274,  282,   -1,   -1,
  281,  286,  286,  286,  286,  282,  282,  281,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=287;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,"','",
null,"'.'",null,null,null,null,null,null,null,null,null,null,null,null,null,
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
null,null,null,null,null,"IF","ELSE","ENDIF","PRINT","RETURN","ULONG","WHILE",
"DO","CTE","CADENA","ID","CVR","ASIGN","MAS","MENOS","AST","BARRA","IGUAL",
"MAYIG","MENIG","MAYOR","MENOR","IGIG","DIF","PARENTESISA","PARENTESISC",
"LLAVEA","LLAVEC","GUIONB","PUNTOCOMA","FLECHA",
};
final static String yyrule[] = {
"$accept : prog",
"prog : ID bloque",
"bloque : LLAVEA sentencias LLAVEC",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : asignaciones",
"sentencia : IF PARENTESISA condicion PARENTESISC bloque ENDIF PUNTOCOMA",
"sentencia : IF PARENTESISA condicion PARENTESISC bloque ELSE bloque ENDIF PUNTOCOMA",
"sentencia : WHILE PARENTESISA condicion PARENTESISC DO bloque",
"sentencia : RETURN PARENTESISA expresiones PARENTESISC PUNTOCOMA",
"sentencia : declaracion",
"sentencia : expresion_lambda",
"sentencia : PRINT PARENTESISA CADENA PARENTESISC PUNTOCOMA",
"sentencia : PRINT PARENTESISA termino PARENTESISC PUNTOCOMA",
"condicion : expresiones MAYOR expresiones",
"condicion : expresiones MAYIG expresiones",
"condicion : expresiones MENOR expresiones",
"condicion : expresiones MENIG expresiones",
"condicion : expresiones IGIG expresiones",
"condicion : expresiones DIF expresiones",
"expresiones : expresiones operador termino",
"expresiones : PARENTESISA expresiones PARENTESISC",
"expresiones : termino",
"termino : tipo_id",
"termino : CTE",
"termino : ID PARENTESISA parametros_formales PARENTESISC",
"operador : MAS",
"operador : MENOS",
"operador : AST",
"operador : BARRA",
"parametros_formales : parametros_formales ',' expresiones FLECHA tipo_id",
"parametros_formales : expresiones FLECHA tipo_id",
"declaracion : tipo lista_id PUNTOCOMA",
"declaracion : tipo ID PARENTESISA parametros PARENTESISC bloque",
"lista_id : tipo_id",
"lista_id : lista_id ',' tipo_id",
"tipo : ULONG",
"parametros : parametros ',' parametro",
"parametros : parametro",
"parametro : tipo tipo_id",
"parametro : CVR tipo_id",
"asignaciones : tipo_id ASIGN expresiones PUNTOCOMA",
"asignaciones : lista_id IGUAL lista_cte PUNTOCOMA",
"lista_cte : CTE",
"lista_cte : lista_cte ',' CTE",
"tipo_id : ID",
"tipo_id : ID '.' ID",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC bloque PARENTESISA tipo_id PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC bloque PARENTESISA CTE PARENTESISC",
};

//#line 99 "gramatica.y"

/* CODIGO AUXILIAR */


AnalisisLexico aLex;

public void setAlex(AnalisisLexico a){
    this.aLex = a;
}
public void verificar_cantidades (ParserVal lista1, ParserVal lista2){
    ArrayList<ParserVal> l1 = (ArrayList<ParserVal>)lista1.obj;
    ArrayList<ParserVal> l2 = (ArrayList<ParserVal>)lista2.obj;
    if (l1.size() > l2.size() )
        System.out.println("Linea: "+AnalisisLexico.getNroLinea()+" ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
}

void yyerror (String s){}

int yylex () throws IOException{
   return aLex.yylex();

}
//#line 350 "Parser.java"
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
case 5:
//#line 25 "gramatica.y"
{System.out.println("SENTENCIA: asignacion");}
break;
case 6:
//#line 26 "gramatica.y"
{System.out.println("SENTENCIA: if");}
break;
case 7:
//#line 27 "gramatica.y"
{System.out.println("SENTENCIA:if else");}
break;
case 8:
//#line 28 "gramatica.y"
{System.out.println("SENTENCIA:while");}
break;
case 9:
//#line 29 "gramatica.y"
{System.out.println("SENTENCIA:return");}
break;
case 10:
//#line 30 "gramatica.y"
{System.out.println("SENTENCIA:declaracion");}
break;
case 11:
//#line 31 "gramatica.y"
{System.out.println("SENTENCIA:lambda");}
break;
case 34:
//#line 68 "gramatica.y"
{ ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 35:
//#line 69 "gramatica.y"
{ ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 42:
//#line 84 "gramatica.y"
{ verificar_cantidades (val_peek(3), val_peek(1)); }
break;
case 43:
//#line 87 "gramatica.y"
{ ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 44:
//#line 88 "gramatica.y"
{ ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
//#line 547 "Parser.java"
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
