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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    3,    3,    3,    3,    3,    3,
    3,    6,    6,    6,    6,    6,    6,    5,    5,    5,
   10,   10,   10,    9,    9,    9,    9,   11,   11,    7,
    7,   12,   12,   14,   14,   13,   15,   15,   16,   16,
   17,   17,   18,   18,    4,    4,    8,    8,
};
final static short yylen[] = {                            2,
    2,    3,    2,    1,    4,    7,    9,    6,    5,    1,
    1,    3,    3,    3,    3,    3,    3,    3,    3,    1,
    1,    1,    4,    1,    1,    1,    1,    5,    3,    2,
    1,    3,    6,    1,    3,    1,    3,    1,    2,    2,
    4,    4,    1,    3,    1,    3,    8,    8,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    1,    0,    0,   36,    0,    0,    0,
    0,    4,    0,    0,   11,   31,    0,    0,    0,    0,
    0,    0,    2,    3,    0,   30,    0,   34,    0,   22,
    0,    0,   21,    0,    0,   20,    0,    0,   46,    0,
    0,    0,   32,    0,    0,    0,   24,   25,   26,   27,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    5,    0,    0,    0,   38,   35,    0,    0,   19,
    0,    0,    0,    0,    0,    0,   18,    0,    9,    0,
    0,   40,   39,    0,    0,    0,   23,    0,    0,    0,
    8,    0,   33,   37,   29,    0,    0,    6,    0,    0,
    0,    0,   48,   47,   28,    7,
};
final static short yydgoto[] = {                          2,
    4,   11,   12,   33,   34,   35,   14,   15,   57,   36,
   69,   16,   17,   29,   65,   66,    0,    0,
};
final static short yysindex[] = {                      -252,
  -86,    0,    3,    0,   19,   25,    0,   29,   -5, -213,
  -16,    0, -193, -213,    0,    0, -190,  -31,  -31,  -31,
 -182, -181,    0,    0,  -31,    0,   -6,    0,  -39,    0,
   15,  -31,    0,  -14,   46,    0,   51,   47,    0,   48,
    9, -205,    0, -172,  -31,   58,    0,    0,    0,    0,
  -31,  -31,  -31,  -31,  -31,  -31, -201,  -86,   38, -162,
  -86,    0, -172, -172,  -28,    0,    0,  -35,    1,    0,
   65,   65,   65,   65,   65,   65,    0, -184,    0,  -86,
   64,    0,    0,  -86, -205, -172,    0,  -31,  -86,   52,
    0, -195,    0,    0,    0,  -20, -153,    0,   73,   74,
 -172,   59,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  -27,    0,
    0,    0,    0,   10,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -33,    0,    0,    0,
  -41,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   76,   79,   80,   81,   82,   83,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -22,    0,  114,   27,   28,  106,    0,    0,    0,   70,
    0,  115,   -7,    0,    0,   45,    0,    0,
};
final static int YYTABLESIZE=277;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         45,
   45,   45,   22,   45,   44,   45,   49,   47,   32,   48,
   45,   50,   84,   45,    1,   85,   45,   45,   45,   43,
   45,   49,   47,   10,   48,   45,   50,   49,   47,   13,
   48,   45,   50,   42,   64,   78,    3,   13,   81,   21,
   21,   87,   10,   28,   88,   54,   37,   53,    7,   10,
   49,   47,   41,   48,   45,   50,    7,   91,   18,   46,
   21,   93,   63,   30,   19,   31,   97,   62,   20,   99,
   67,    9,   68,   89,   90,   25,   27,   64,   71,   72,
   73,   74,   75,   76,   39,   40,   58,   60,   61,   82,
   83,   59,   49,   47,    9,   48,   79,   50,   70,   49,
   47,   80,   48,   92,   50,  102,   49,   47,   23,   48,
   98,   50,   95,  103,  104,   96,   13,  106,  100,   15,
   12,   14,   16,   17,   24,   38,   77,  105,   26,   94,
    0,    0,    0,    0,   10,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   45,   45,
   45,   45,   45,   30,    0,   31,    0,    0,   86,    0,
    5,   45,    0,    0,    6,    7,    8,    0,    0,    0,
    9,    0,    0,  101,    0,   51,   52,   55,   56,    5,
    0,    0,    0,    6,    7,    8,   10,    0,    0,    9,
   10,    0,   10,    0,    0,    0,   10,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   10,   45,   44,   47,   42,   43,   40,   45,
   44,   47,   41,   41,  267,   44,   44,   59,   60,   59,
   62,   42,   43,   40,   45,   59,   47,   42,   43,    3,
   45,   59,   47,   40,   42,   58,  123,   11,   61,   46,
   46,   41,   40,   17,   44,   60,   19,   62,  262,   40,
   42,   43,   25,   45,   40,   47,  262,   80,   40,   32,
   46,   84,  268,  265,   40,  267,   89,   59,   40,  265,
   44,  267,   45,  258,  259,  269,  267,   85,   51,   52,
   53,   54,   55,   56,  267,  267,   41,   41,   41,   63,
   64,   41,   42,   43,  267,   45,   59,   47,   41,   42,
   43,  264,   45,   40,   47,  259,   42,   43,  125,   45,
   59,   47,   86,   41,   41,   88,   41,   59,   92,   41,
   41,   41,   41,   41,   11,   20,   57,  101,   14,   85,
   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  270,  271,
  272,  273,  274,  265,   -1,  267,   -1,   -1,  274,   -1,
  257,  269,   -1,   -1,  261,  262,  263,   -1,   -1,   -1,
  267,   -1,   -1,  274,   -1,  270,  271,  272,  273,  257,
   -1,   -1,   -1,  261,  262,  263,  257,   -1,   -1,  267,
  261,   -1,  263,   -1,   -1,   -1,  267,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=274;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"'_'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"IF","ELSE","ENDIF","PRINT","RETURN",
"ULONG","WHILE","DO","CTE","CADENA","ID","CVR","\":=\"","\">=\"","\"<=\"",
"\"==\"","\"!=\"","\"->\"",
};
final static String yyrule[] = {
"$accept : prog",
"prog : ID bloque",
"bloque : '{' sentencias '}'",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : tipo_id \":=\" expresiones ';'",
"sentencia : IF '(' condicion ')' bloque ENDIF ';'",
"sentencia : IF '(' condicion ')' bloque ELSE bloque ENDIF ';'",
"sentencia : WHILE '(' condicion ')' DO bloque",
"sentencia : RETURN '(' expresiones ')' ';'",
"sentencia : declaraciones",
"sentencia : expresion_lambda",
"condicion : expresiones '>' expresiones",
"condicion : expresiones \">=\" expresiones",
"condicion : expresiones '<' expresiones",
"condicion : expresiones \"<=\" expresiones",
"condicion : expresiones \"==\" expresiones",
"condicion : expresiones \"!=\" expresiones",
"expresiones : expresiones operador termino",
"expresiones : '(' expresiones ')'",
"expresiones : termino",
"termino : tipo_id",
"termino : CTE",
"termino : ID '(' parametros_formales ')'",
"operador : '+'",
"operador : '-'",
"operador : '*'",
"operador : '/'",
"parametros_formales : parametros_formales ',' expresiones \"->\" tipo_id",
"parametros_formales : expresiones \"->\" tipo_id",
"declaraciones : declaraciones declaracion",
"declaraciones : declaracion",
"declaracion : tipo lista_id ';'",
"declaracion : tipo ID '(' parametros ')' bloque",
"lista_id : tipo_id",
"lista_id : lista_id ',' tipo_id",
"tipo : ULONG",
"parametros : parametros ',' parametro",
"parametros : parametro",
"parametro : tipo tipo_id",
"parametro : CVR tipo_id",
"asignaciones : tipo_id \":=\" expresiones ';'",
"asignaciones : lista_id '=' lista_cte ';'",
"lista_cte : CTE",
"lista_cte : lista_cte ',' CTE",
"tipo_id : ID",
"tipo_id : ID '.' ID",
"expresion_lambda : '(' tipo ID ')' bloque '(' tipo_id ')'",
"expresion_lambda : '(' tipo ID ')' bloque '(' CTE ')'",
};

//#line 100 "gramatica.y"

/* CODIGO AUXILIAR */

public void verificar_cantidades (ArrayList<String> lista1, ArrayList<String> lista2){
    if (lista1.size() > lista2.size() )
        System.out.println("ERROR: se esperaba que el lado izquierdo de la asignacion tenga menor o igual cantidad de elementos que el lado derecho");
}

void yyerror (String s){}

int yylex () throws IOException{
    AnalisisLexico aLex = new AnalisisLexico("C:\\FACULTAD\\Cuarto\\compiladores\\TPE_Compiladores\\texto.txt");
    int token = aLex.yylex();
    return token;
}
//#line 325 "Parser.java"
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
//#line 24 "gramatica.y"
{System.out.println("SENTENCIA: asignacion");}
break;
case 6:
//#line 25 "gramatica.y"
{System.out.println("SENTENCIA: if");}
break;
case 7:
//#line 26 "gramatica.y"
{System.out.println("SENTENCIA:if else");}
break;
case 8:
//#line 27 "gramatica.y"
{System.out.println("SENTENCIA:while");}
break;
case 9:
//#line 28 "gramatica.y"
{System.out.println("SENTENCIA:return");}
break;
case 10:
//#line 29 "gramatica.y"
{System.out.println("SENTENCIA:declaracion");}
break;
case 11:
//#line 30 "gramatica.y"
{System.out.println("SENTENCIA:lambda");}
break;
case 34:
//#line 69 "gramatica.y"
{ yyval = new ArrayList<>(); yyval.add(val_peek(0)); }
break;
case 35:
//#line 70 "gramatica.y"
{ yyval = val_peek(2); yyval.add(val_peek(0)); }
break;
case 42:
//#line 85 "gramatica.y"
{ verificar_cantidades (val_peek(3), val_peek(1)); }
break;
case 43:
//#line 88 "gramatica.y"
{ yyval = new ArrayList<>(); yyval.add(val_peek(0)); }
break;
case 44:
//#line 89 "gramatica.y"
{ yyval = val_peek(2); yyval.add(val_peek(0)); }
break;
//#line 522 "Parser.java"
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
