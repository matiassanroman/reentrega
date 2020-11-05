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
package compilador;
import java.io.IOException;
import java.util.ArrayList;
//#line 21 "Parser.java"




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
public final static short ID=257;
public final static short IF=258;
public final static short THEN=259;
public final static short ELSE=260;
public final static short END_IF=261;
public final static short OUT=262;
public final static short FUNC=263;
public final static short RETURN=264;
public final static short FOR=265;
public final static short INTEGER=266;
public final static short FLOAT=267;
public final static short PROC=268;
public final static short NS=269;
public final static short NA=270;
public final static short CADENA=271;
public final static short UP=272;
public final static short DOWN=273;
public final static short CTE=274;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    1,    2,    2,    2,    5,    5,
    5,    6,    8,    8,    8,   10,   10,   10,   11,   11,
    9,    9,   12,   12,   12,   12,    3,    3,    3,    3,
    3,    3,    3,    3,   15,   15,   15,   16,   19,   18,
   21,   21,   21,   20,   20,   17,   17,   14,   14,   24,
   26,   27,   28,   25,   13,   29,   29,   29,   30,   30,
   30,   31,   31,   23,   23,   23,   23,   23,   23,    4,
    4,    7,   22,   22,
};
final static short yylen[] = {                            2,
    1,    2,    2,    1,    1,    3,    1,    3,    3,    1,
    1,    2,   12,   11,   12,    1,    3,    5,    2,    2,
    3,    3,    2,    2,    1,    1,    1,    5,    5,    4,
    3,    1,    5,    3,    7,    7,    7,    5,    1,    3,
    3,    3,    3,    2,    2,    2,    1,    1,    1,   10,
    1,    1,    1,    6,    4,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,   72,    0,    0,    0,   71,   70,    0,    0,    0,
    4,    5,    0,    7,    0,    0,   27,   32,   11,    0,
   10,    0,    0,    0,   48,   49,    0,    0,    0,    2,
    3,    0,    0,    0,    0,   12,    8,    0,   34,    0,
   51,    0,   31,    0,    0,    0,    0,    0,    0,    0,
    6,    0,    0,    0,    0,    0,   73,    0,   63,   62,
    0,    0,   61,    0,   25,   26,    0,    9,   64,   65,
   66,   67,   68,   69,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   20,   30,   19,    0,    0,
   74,    0,    0,   55,    0,    0,   22,   21,   23,   24,
    0,   41,   43,    0,   33,   28,    0,   40,    0,    0,
   39,    0,    0,    0,   29,    0,    0,    0,   59,   60,
   47,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   46,    0,   36,   37,   35,    0,    0,   38,    0,
    0,    0,   18,    0,   44,   45,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   50,    0,
   14,    0,   15,   13,
};
final static short yydgoto[] = {                          9,
   10,   11,  121,   13,   20,   14,   15,   16,   36,   55,
   56,   67,   17,   24,   18,   48,  122,   49,  110,  139,
   41,   60,   75,   25,   26,   42,  123,  155,   61,   62,
   63,
};
final static short yysindex[] = {                      -176,
 -202,    0,  -37,   30,   37,    0,    0, -219,    0, -176,
    0,    0, -202,    0,  -17,  -71,    0,    0,    0,  -18,
    0, -173, -219, -168,    0,    0, -226, -195,   45,    0,
    0,   -2,  -39,  -45, -146,    0,    0, -219,    0,  -53,
    0,   55,    0,   59,   60,   61,   52,   73,   56,  -35,
    0, -219,   58, -219,   77,   79,    0, -147,    0,    0,
  -16,   16,    0, -117,    0,    0, -120,    0,    0,    0,
    0,    0,    0,    0,  -45,   10,   72,   75,   18,  -44,
   20, -219,  -31, -135,  103,    0,    0,    0,   94, -207,
    0,  -45,  -45,    0,  -45,  -45,    0,    0,    0,    0,
   99,    0,    0, -133,    0,    0, -133,    0, -159,   96,
    0, -109,  101, -107,    0,  120,   16,   16,    0,    0,
    0, -133,   40, -108,   41, -106, -200,  106, -105,  107,
 -207,    0,  -90,    0,    0,    0,  -44,  -44,    0, -103,
  128, -101,    0,   51,    0,    0,  131,  -93,  133, -133,
  -91,  118,  -89, -133,   57,  122,  -88,  123,    0,  -87,
    0,  -86,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  181,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  144,    0,    0,    0,    0,
    0,  -12,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -23,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  148,  -11,    8,    0,    0,
    0,   65,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -70,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   67,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    2,    4,  -22,  180,    0,   12,    0,    0,  145,
  -75,    0,  119,    0,    0,    0,  -85,    0,    0,    0,
  114,  -59,    0,    0,    0,    0,    0,    0,    0,   -9,
  -21,
};
final static int YYTABLESIZE=232;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         58,
   58,   53,   23,   12,   98,   84,   74,   97,   73,  112,
   54,   30,   21,   31,  116,  103,  134,   42,  136,   29,
  108,  124,   33,  126,   21,   38,   92,   54,   93,   44,
   58,   56,   58,   56,   40,   42,   65,    2,   66,   47,
   37,   38,   94,   34,   45,   59,   58,   56,   52,   68,
   57,   35,   57,   19,    2,  143,   51,   95,    6,    7,
   46,    2,   96,   86,  154,   88,   57,   54,   99,   27,
  100,  137,  138,  119,  120,   21,   28,  145,  146,    1,
    2,    3,  117,  118,   50,    4,  101,   39,    5,    6,
    7,    8,   43,   40,   86,   76,  125,    2,    3,   77,
   78,   79,    4,   59,   59,    5,   59,   59,   54,   64,
    2,    3,   80,   81,   82,    4,   87,   89,    5,    6,
    7,    8,   90,    2,    3,  132,   91,  132,    4,  132,
  105,    5,  104,  106,  113,    1,    2,    3,   19,    2,
  107,    4,  109,  114,    5,    6,    7,    8,    2,    3,
    2,    3,  115,    4,  127,    4,    5,  132,    5,   34,
  128,  129,  130,  131,  133,  135,  140,  142,  141,  144,
  147,  148,  149,  150,  151,  152,  153,  156,  157,  158,
    1,  159,  160,  162,   16,  161,  163,  164,   17,   52,
   54,   53,   32,  102,   85,  111,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    2,    0,    0,    0,    0,   52,    0,   22,    0,
   83,   69,   70,   71,   72,    2,    6,    7,   57,   57,
    6,    7,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
   45,   41,   40,    0,  125,   41,   60,  125,   62,   41,
   33,   10,    1,   10,   90,   75,  125,   41,  125,    8,
   80,  107,   40,  109,   13,   44,   43,   50,   45,  256,
   43,   43,   45,   45,   23,   59,   35,  257,   35,   28,
   59,   44,   59,   61,  271,   34,   59,   59,  256,   38,
   43,  123,   45,  256,  257,  131,   59,   42,  266,  267,
  256,  257,   47,   52,  150,   54,   59,   90,   67,   40,
   67,  272,  273,   95,   96,   64,   40,  137,  138,  256,
  257,  258,   92,   93,   40,  262,   75,  261,  265,  266,
  267,  268,  261,   82,   83,   41,  256,  257,  258,   41,
   41,   41,  262,   92,   93,  265,   95,   96,  131,  256,
  257,  258,   61,   41,   59,  262,   59,   41,  265,  266,
  267,  268,   44,  257,  258,  122,  274,  124,  262,  126,
   59,  265,  123,   59,  270,  256,  257,  258,  256,  257,
  123,  262,  123,   41,  265,  266,  267,  268,  257,  258,
  257,  258,   59,  262,   59,  262,  265,  154,  265,   61,
  270,   61,  270,   44,  125,  125,   61,   61,  274,  260,
  274,   44,  274,  123,   44,  269,   44,  269,   61,  269,
    0,  125,   61,   61,   41,  274,  274,  274,   41,  125,
  261,  125,   13,   75,   50,   82,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,   -1,   -1,   -1,   -1,  256,   -1,  256,   -1,
  256,  275,  276,  277,  278,  257,  266,  267,  274,  274,
  266,  267,
};
}
final static short YYFINAL=9;
final static short YYMAXTOKEN=278;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ID","IF","THEN","ELSE","END_IF","OUT",
"FUNC","RETURN","FOR","INTEGER","FLOAT","PROC","NS","NA","CADENA","UP","DOWN",
"CTE","\"<=\"","\">=\"","\"!=\"","\"==\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloquePrograma",
"bloquePrograma : bloquePrograma sentenciaDeclarativa",
"bloquePrograma : bloquePrograma sentenciaEjecutable",
"bloquePrograma : sentenciaDeclarativa",
"bloquePrograma : sentenciaEjecutable",
"sentenciaDeclarativa : tipo listaVariables ';'",
"sentenciaDeclarativa : declaracionProcedimiento",
"sentenciaDeclarativa : error listaVariables ';'",
"listaVariables : listaVariables ',' identificador",
"listaVariables : identificador",
"listaVariables : error",
"declaracionProcedimiento : encabezadoProc bloqueProc",
"encabezadoProc : PROC identificador '(' parametrosProc ')' NA '=' CTE ',' NS '=' CTE",
"encabezadoProc : PROC identificador '(' ')' NA '=' CTE ',' NS '=' CTE",
"encabezadoProc : PROC identificador '(' error ')' NA '=' CTE ',' NS '=' CTE",
"parametrosProc : parametro",
"parametrosProc : parametro ',' parametro",
"parametrosProc : parametro ',' parametro ',' parametro",
"parametro : tipo identificador",
"parametro : error identificador",
"bloqueProc : '{' bloque '}'",
"bloqueProc : '{' error '}'",
"bloque : bloque sentenciaDeclarativa",
"bloque : bloque sentenciaEjecutable",
"bloque : sentenciaDeclarativa",
"bloque : sentenciaEjecutable",
"sentenciaEjecutable : asignacion",
"sentenciaEjecutable : OUT '(' CADENA ')' ';'",
"sentenciaEjecutable : identificador '(' parametrosProc ')' ';'",
"sentenciaEjecutable : identificador '(' ')' ';'",
"sentenciaEjecutable : IF cuerpoIf END_IF",
"sentenciaEjecutable : cicloFor",
"sentenciaEjecutable : OUT '(' error ')' ';'",
"sentenciaEjecutable : IF error END_IF",
"cicloFor : FOR '(' condicionFor ')' '{' bloqueSentencia '}'",
"cicloFor : FOR '(' error ')' '{' bloqueSentencia '}'",
"cicloFor : FOR '(' condicionFor ')' '{' error '}'",
"condicionFor : inicioFor ';' condiFOR ';' incDec",
"condiFOR : condicion",
"inicioFor : identificador '=' constante",
"condicion : identificador comparador asignacion",
"condicion : identificador comparador identificador",
"condicion : identificador comparador constante",
"incDec : UP constante",
"incDec : DOWN constante",
"bloqueSentencia : bloqueSentencia sentenciaEjecutable",
"bloqueSentencia : sentenciaEjecutable",
"cuerpoIf : cuerpoCompleto",
"cuerpoIf : cuerpoIncompleto",
"cuerpoCompleto : '(' condicionIf ')' '{' bloqueThen '}' ELSE '{' bloqueElse '}'",
"condicionIf : condicion",
"bloqueThen : bloqueSentencia",
"bloqueElse : bloqueSentencia",
"cuerpoIncompleto : '(' condicionIf ')' '{' bloqueThen '}'",
"asignacion : identificador '=' expresion ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : constante",
"factor : identificador",
"comparador : \"<=\"",
"comparador : \">=\"",
"comparador : \"!=\"",
"comparador : \"==\"",
"comparador : '>'",
"comparador : '<'",
"tipo : FLOAT",
"tipo : INTEGER",
"identificador : ID",
"constante : CTE",
"constante : '-' CTE",
};

//#line 391 "gramatica.y"
void mostrarMensaje(String mensaje){
	System.out.println(mensaje);
}

//Definiciones propias

Compilador c;
ArrayList<String> errores = new ArrayList<String>();
Token t;
int lineaActual;
ArrayList<String> reconocidos = new ArrayList<String>();

public Parser(Compilador c, ArrayList<String> errores)
{
this.c =c;
this.errores =errores;
}

int i= 0;

public int yylex() {
  
  try {
    Token token = c.getToken();
    this.lineaActual = token.getLinea();
    yylval = new ParserVal(t);
    yylval.sval = token.getLexema();
    return token.getToken();
  } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  return 0;
}

public void yyerror(String error){
  this.errores.add(error + " en linea " + this.lineaActual) ;
}

public ArrayList<String> getErrores(){
  return this.errores;
}

public ArrayList<String> getReconocidos(){
  return this.reconocidos;
}
//#line 407 "Parser.java"
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
//#line 15 "gramatica.y"
{
	mostrarMensaje("Reconoce bien el programa");
}
break;
case 2:
//#line 21 "gramatica.y"
{
}
break;
case 3:
//#line 24 "gramatica.y"
{
}
break;
case 4:
//#line 27 "gramatica.y"
{
}
break;
case 5:
//#line 30 "gramatica.y"
{
}
break;
case 6:
//#line 35 "gramatica.y"
{
	mostrarMensaje("Declaracion de una o mas variables en linea " + compilador.Compilador.nroLinea);
}
break;
case 7:
//#line 40 "gramatica.y"
{
}
break;
case 8:
//#line 43 "gramatica.y"
{
	yyerror("Error, tipo invalido en linea nro: "+compilador.Compilador.nroLinea);
}
break;
case 9:
//#line 49 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 10:
//#line 53 "gramatica.y"
{
}
break;
case 11:
//#line 56 "gramatica.y"
{
	yyerror("Error en la o las varibles, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 12:
//#line 62 "gramatica.y"
{
	mostrarMensaje("Procedimiento completo, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 13:
//#line 68 "gramatica.y"
{
	mostrarMensaje("Procedimiento con parametros en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(11).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(9).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(7).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(5).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 14:
//#line 80 "gramatica.y"
{
	mostrarMensaje("Procedimiento sin parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(10).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(8).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(7).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(5).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 15:
//#line 92 "gramatica.y"
{
	yyerror("Error en los parametros de procedimiento en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 16:
//#line 98 "gramatica.y"
{
}
break;
case 17:
//#line 101 "gramatica.y"
{
}
break;
case 18:
//#line 104 "gramatica.y"
{
}
break;
case 19:
//#line 109 "gramatica.y"
{
	mostrarMensaje("Parametro, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 20:
//#line 113 "gramatica.y"
{
	yyerror("Error, tipo invalido en el parametro, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 21:
//#line 119 "gramatica.y"
{
	mostrarMensaje("Bloque de procedimiento en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 22:
//#line 125 "gramatica.y"
{
	yyerror("Error en el cuerpo del procedimiento en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 23:
//#line 131 "gramatica.y"
{
}
break;
case 24:
//#line 134 "gramatica.y"
{
}
break;
case 25:
//#line 137 "gramatica.y"
{
}
break;
case 26:
//#line 140 "gramatica.y"
{
}
break;
case 27:
//#line 145 "gramatica.y"
{
}
break;
case 28:
//#line 148 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(4).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token CADENA, lexema: " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 29:
//#line 156 "gramatica.y"
{
	mostrarMensaje("Llamada a procedimiento con parametros en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 30:
//#line 163 "gramatica.y"
{
	mostrarMensaje("Llamda a procedimiento sin parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 31:
//#line 170 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 32:
//#line 175 "gramatica.y"
{
	mostrarMensaje("Ciclo FOR en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 33:
//#line 179 "gramatica.y"
{
	yyerror("Error en la cadena en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 34:
//#line 183 "gramatica.y"
{
	yyerror("Error en el cuerpo del IF en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 35:
//#line 189 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(5).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 36:
//#line 197 "gramatica.y"
{
	yyerror("Error en la condicion del FOR en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 37:
//#line 201 "gramatica.y"
{
	yyerror("Error en el cuerpo del FOR en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 38:
//#line 207 "gramatica.y"
{
	mostrarMensaje("Encabezado de FOR, en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(3).sval + " separacion entre inicio del FOR y la condicion, en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + " separacion entre condicion del FOR y el incremento o decremento, en linea " + compilador.Compilador.nroLinea);
}
break;
case 39:
//#line 215 "gramatica.y"
{
}
break;
case 40:
//#line 220 "gramatica.y"
{
}
break;
case 41:
//#line 225 "gramatica.y"
{
}
break;
case 42:
//#line 228 "gramatica.y"
{
}
break;
case 43:
//#line 231 "gramatica.y"
{
}
break;
case 44:
//#line 236 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 45:
//#line 240 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 46:
//#line 246 "gramatica.y"
{
}
break;
case 47:
//#line 249 "gramatica.y"
{
}
break;
case 48:
//#line 254 "gramatica.y"
{
}
break;
case 49:
//#line 257 "gramatica.y"
{
}
break;
case 50:
//#line 262 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(9).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(7).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(4).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 51:
//#line 275 "gramatica.y"
{	
}
break;
case 52:
//#line 280 "gramatica.y"
{
}
break;
case 53:
//#line 285 "gramatica.y"
{
}
break;
case 54:
//#line 290 "gramatica.y"
{
	mostrarMensaje("IF sin ELSE, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 55:
//#line 300 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 56:
//#line 307 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 57:
//#line 311 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 58:
//#line 315 "gramatica.y"
{	
}
break;
case 59:
//#line 320 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 60:
//#line 324 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 62:
//#line 331 "gramatica.y"
{
}
break;
case 63:
//#line 334 "gramatica.y"
{
}
break;
case 64:
//#line 339 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 65:
//#line 343 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 66:
//#line 347 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 67:
//#line 351 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 68:
//#line 355 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 69:
//#line 359 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 70:
//#line 365 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 71:
//#line 369 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 72:
//#line 375 "gramatica.y"
{
	mostrarMensaje("Token ID, lexema: " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 73:
//#line 381 "gramatica.y"
{
	mostrarMensaje("Token: CTE, valor: "+ val_peek(0).ival + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 74:
//#line 385 "gramatica.y"
{
	mostrarMensaje("Token: CTE, valor: -" + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
//#line 1013 "Parser.java"
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
