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
public final static short PUNTO=288;
public final static short COMA=289;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    5,    5,    5,    5,    5,    5,
    7,    7,    7,   10,   10,   10,   11,   11,   11,   11,
    8,    8,    6,    6,   14,   13,   13,   15,   15,   16,
   16,    4,    4,    4,   17,   17,   12,   12,    9,    9,
};
final static short yylen[] = {                            2,
    2,    3,    3,    2,    1,    6,    8,    6,    4,    4,
    1,    1,    4,    4,    3,    3,    3,    3,    3,    3,
    3,    3,    1,    1,    1,    4,    1,    1,    1,    1,
    2,    6,    2,    3,    1,    5,    3,    3,    1,    3,
    2,    4,    3,    3,    1,    3,    1,    3,    8,    8,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    1,    0,    0,    0,   35,    0,    0,
    0,    0,    0,    5,    0,   11,   12,    0,    0,    0,
    0,    0,    0,    0,    2,    0,    4,    0,    0,    0,
    0,   33,    0,   25,    0,    0,    0,    0,   23,   24,
    0,    0,    0,    0,    0,    0,    0,    3,   45,    0,
    0,   34,    0,    0,    0,    0,    0,    0,   27,   28,
   29,   30,    0,    0,    0,    0,    0,    0,    0,   13,
   14,    9,   10,    0,    0,    0,    0,    0,    0,    0,
   39,   48,    0,    0,   22,    0,    0,    0,    0,    0,
    0,    0,   21,    0,    0,   46,    0,   41,    0,    0,
    0,   26,    0,    0,    6,    8,    0,   40,   32,   38,
   37,    0,    0,    0,    0,    0,    7,   50,   49,   36,
};
final static short yydgoto[] = {                          2,
    4,   12,   13,   14,   37,   15,   38,   16,   17,   39,
   69,   40,   84,   18,   80,   81,   50,
};
final static short yysindex[] = {                      -241,
 -249,    0, -197,    0, -238, -226, -222,    0, -200, -239,
 -172, -240, -193,    0, -271,    0,    0, -209, -257, -131,
 -225, -257, -257, -159,    0, -177,    0, -153, -152, -256,
 -173,    0, -152,    0, -253, -257, -164, -132,    0,    0,
 -163, -140, -251, -149, -129, -141, -128,    0,    0, -134,
 -130,    0, -257, -261, -111, -257, -145, -249,    0,    0,
    0,    0, -257, -257, -257, -257, -257, -257, -116,    0,
    0,    0,    0, -107, -249, -106, -141, -172, -152, -202,
    0,    0, -199, -175,    0, -191, -141, -141, -141, -141,
 -141, -141,    0, -249, -121,    0, -152,    0, -249, -261,
 -152,    0, -257, -249,    0,    0, -115,    0,    0,    0,
    0, -195,  -98, -120, -119, -152,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -260,
 -122,    0,    0,    0, -176,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -118,    0,    0,    0, -117,
 -169,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -114,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -112, -109, -108, -105,
 -104, -103,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -42,    0,  153,    0,  144,   64,  -17,    0,    0,  -15,
    0,  -18,    0,   -9,    0,   67,    0,
};
final static int YYTABLESIZE=179;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         32,
    8,   24,   28,   44,   42,   46,   78,   34,   33,   35,
   52,   33,   53,   47,   32,   86,    5,   29,   57,    6,
    7,    8,    9,   36,   54,    1,   10,   56,   47,   23,
   72,   55,   95,    3,   55,   77,    8,   29,   83,   34,
   11,   35,   19,   25,   79,   87,   88,   89,   90,   91,
   92,  106,    8,   93,   20,   36,  109,   30,   21,    5,
   98,  113,    6,    7,    8,    9,  104,  105,   97,   10,
   59,   60,   61,   62,   59,   60,   61,   62,  108,   99,
   22,   31,  111,   11,   43,  112,  100,  101,  115,    8,
   79,  116,   27,   47,   47,   47,   47,  120,   47,   47,
   47,   47,   47,   47,   47,   47,  102,   47,   48,   47,
   47,   49,   47,  103,   51,   29,   47,   58,   70,   47,
   59,   60,   61,   62,   59,   60,   61,   62,   59,   60,
   61,   62,   73,   34,   41,   35,   85,   59,   60,   61,
   62,   71,   63,   64,   65,   66,   67,   68,   34,  114,
   35,   51,   74,   75,   76,   82,   94,   55,   96,  107,
  117,  118,  119,   31,   26,   45,  110,   43,   44,   16,
    0,   42,   18,   15,    0,    0,   17,   19,   20,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         18,
  262,   11,  274,   21,   20,   23,  268,  265,   18,  267,
   29,   21,  269,  274,   33,   58,  257,  289,   36,  260,
  261,  262,  263,  281,  281,  267,  267,  281,  289,  269,
  282,  288,   75,  283,  288,   53,  262,  289,   56,  265,
  281,  267,  281,  284,   54,   63,   64,   65,   66,   67,
   68,   94,  262,   69,  281,  281,   99,  267,  281,  257,
   79,  104,  260,  261,  262,  263,  258,  259,   78,  267,
  270,  271,  272,  273,  270,  271,  272,  273,   97,  282,
  281,   18,  101,  281,   21,  103,  289,  287,  107,  262,
  100,  287,  286,  270,  271,  272,  273,  116,  275,  276,
  277,  278,  279,  280,  274,  282,  282,  267,  286,  286,
  287,  265,  282,  289,  267,  289,  286,  282,  282,  289,
  270,  271,  272,  273,  270,  271,  272,  273,  270,  271,
  272,  273,  282,  265,  266,  267,  282,  270,  271,  272,
  273,  282,  275,  276,  277,  278,  279,  280,  265,  265,
  267,  267,  282,  282,  289,  267,  264,  288,  265,  281,
  259,  282,  282,  286,   12,   22,  100,  286,  286,  282,
   -1,  286,  282,  282,   -1,   -1,  282,  282,  282,
};
}
final static short YYFINAL=2;
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
"bloque : LLAVEA sentencias LLAVEC",
"sentencias : sentencias sentencia PUNTOCOMA",
"sentencias : sentencia PUNTOCOMA",
"sentencia : asignaciones",
"sentencia : IF PARENTESISA condicion PARENTESISC bloque ENDIF",
"sentencia : IF PARENTESISA condicion PARENTESISC bloque ELSE bloque ENDIF",
"sentencia : WHILE PARENTESISA condicion PARENTESISC DO bloque",
"sentencia : RETURN PARENTESISA lista_id PARENTESISC",
"sentencia : RETURN PARENTESISA expresiones PARENTESISC",
"sentencia : declaracion",
"sentencia : expresion_lambda",
"sentencia : PRINT PARENTESISA CADENA PARENTESISC",
"sentencia : PRINT PARENTESISA termino PARENTESISC",
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
"termino : ID PARENTESISA parametros_reales PARENTESISC",
"operador : MAS",
"operador : MENOS",
"operador : AST",
"operador : BARRA",
"declaracion : tipo lista_id",
"declaracion : tipo ID PARENTESISA parametros_formales PARENTESISC bloque",
"lista_id : tipo tipo_id",
"lista_id : lista_id COMA tipo_id",
"tipo : ULONG",
"parametros_reales : parametros_reales COMA expresiones FLECHA tipo_id",
"parametros_reales : expresiones FLECHA tipo_id",
"parametros_formales : parametros_formales COMA parametro",
"parametros_formales : parametro",
"parametro : CVR tipo tipo_id",
"parametro : tipo tipo_id",
"asignaciones : tipo ID ASIGN expresiones",
"asignaciones : ID ASIGN expresiones",
"asignaciones : lista_id IGUAL lista_cte",
"lista_cte : CTE",
"lista_cte : lista_cte COMA CTE",
"tipo_id : ID",
"tipo_id : ID PUNTO ID",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC bloque PARENTESISA tipo_id PARENTESISC",
"expresion_lambda : PARENTESISA tipo ID PARENTESISC bloque PARENTESISA CTE PARENTESISC",
};

//#line 103 "gramatica.y"

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

void yyerror (String s){
    System.out.ptrintln("ERROR yacc: " +s)
}

int yylex () throws IOException{
   return aLex.yylex();

}
//#line 342 "Parser.java"
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
case 6:
//#line 26 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea+" SENTENCIA: if");}
break;
case 7:
//#line 27 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea+" SENTENCIA:if else");}
break;
case 8:
//#line 28 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea+" SENTENCIA:while");}
break;
case 9:
//#line 29 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea+" SENTENCIA:return");}
break;
case 10:
//#line 30 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea+" SENTENCIA:return");}
break;
case 11:
//#line 31 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea+" SENTENCIA:declaracion");}
break;
case 12:
//#line 32 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea+" SENTENCIA:lambda");}
break;
case 13:
//#line 33 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea+" SENTENCIA:print");}
break;
case 14:
//#line 34 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea+" SENTENCIA:print");}
break;
case 33:
//#line 67 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(1)); yyval = new ParserVal(arreglo); }
break;
case 34:
//#line 68 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo); }
break;
case 42:
//#line 86 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea+" SENTENCIA: declaracion y asignacion");}
break;
case 43:
//#line 87 "gramatica.y"
{System.out.println("LINEA: "+aLex.getNroLinea+" SENTENCIA: asignacion");}
break;
case 44:
//#line 88 "gramatica.y"
{verificar_cantidades (val_peek(2), val_peek(0)); }
break;
case 45:
//#line 91 "gramatica.y"
{ArrayList<ParserVal> arreglo = new ArrayList<ParserVal>(); arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
case 46:
//#line 92 "gramatica.y"
{ArrayList<ParserVal> arreglo = (ArrayList<ParserVal>) val_peek(2).obj; arreglo.add(val_peek(0)); yyval = new ParserVal(arreglo);}
break;
//#line 555 "Parser.java"
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
