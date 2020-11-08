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
import accionesSemanticas.AS10_Verificar_Rango_Float;
import accionesSemanticas.AS9_Verificar_Rango_Constante;
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
    0,    0,    1,    1,    1,    1,    2,    2,    5,    5,
    6,    8,    8,    8,    8,    8,    8,   10,   10,   10,
    9,   11,   11,   11,   11,   11,    3,    3,    3,    3,
    3,    3,    3,    3,   14,   15,   18,   17,   20,   20,
   20,   19,   19,   19,   16,   16,   13,   13,   23,   23,
   23,   23,   25,   26,   27,   24,   24,   12,   12,   28,
   28,   28,   29,   29,   29,   30,   30,   22,   22,   22,
   22,   22,   22,   22,    4,    4,    7,   21,   21,   31,
   31,   32,   32,
};
final static short yylen[] = {                            2,
    1,    1,    2,    2,    1,    1,    3,    1,    3,    1,
    2,    0,   11,   13,   16,   19,   20,    1,    3,    5,
    3,    2,    2,    1,    1,    1,    2,    5,    5,    5,
    4,    5,    2,    1,    7,    5,    1,    3,    3,    3,
    3,    2,    2,    2,    2,    1,    2,    2,   10,    8,
    8,    6,    1,    1,    1,    6,    4,    3,    3,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   77,    0,    0,    0,   76,   75,    0,    0,    0,
    5,    6,    0,    8,    0,    0,    0,   34,    0,    0,
   33,    0,    0,    0,    0,    0,    0,    3,    4,    0,
   10,    0,    0,    0,   11,   27,   81,   80,    0,   67,
   66,    0,    0,   65,   78,   79,    0,   53,    0,   47,
   48,    0,    0,    0,    0,    0,    0,    7,    0,    0,
    0,    0,    0,    0,    0,   24,   25,    0,   83,   82,
    0,    0,    0,    0,   74,   68,   69,   70,   71,   72,
   73,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    9,    0,   31,    0,    0,   21,   22,   23,    0,    0,
   63,   64,    0,    0,   39,   41,    0,    0,   29,   28,
   38,    0,    0,   37,    0,    0,   32,    0,   30,   46,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   45,
    0,    0,   52,   35,    0,    0,    0,   36,    0,    0,
    0,   20,    0,    0,    0,   44,   42,   43,    0,    0,
    0,    0,   51,   50,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   49,   13,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   14,    0,    0,    0,    0,
    0,    0,    0,    0,   15,    0,    0,    0,    0,    0,
   16,   17,
};
final static short yydgoto[] = {                          9,
   10,   11,  120,   13,   30,   14,   15,   16,   35,   63,
   68,   17,   21,   18,   55,  144,   56,  113,  138,   48,
   41,   82,   22,   23,   49,  122,  145,   42,   43,   44,
   45,   46,
};
final static short yysindex[] = {                      -167,
  -20,    0,   14,   45,   47,    0,    0, -163,    0, -152,
    0,    0, -163,    0,  -21,  -16,   38,    0,  -45, -163,
    0, -159, -150, -242, -163,   69,  -20,    0,    0,   37,
    0,  -34,  -45, -137,    0,    0,    0,    0, -236,    0,
    0,   39,  -26,    0,    0,    0,  -57,    0,   83,    0,
    0,   85,   91,   72,   93,   77,  -40,    0, -163,   96,
   80,  100,   99,   39,  -20,    0,    0, -101,    0,    0,
  -45,  -45,  -45,  -45,    0,    0,    0,    0,    0,    0,
    0,  -43,  -86,   86,   95,  -41,   35, -163, -110, -163,
    0,  103,    0, -163,  104,    0,    0,    0,  -26,  -26,
    0,    0,  -20,  112,    0,    0,  -55,  -85,    0,    0,
    0,  -55,  115,    0,  116,   11,    0,  134,    0,    0,
  -55,   58,  -76,  -22, -194,  -90, -203,  -83, -163,    0,
  -75,  -55,    0,    0,  -41,  -41,  -41,    0,  144, -163,
  129,    0,  -65,  -55,   70,    0,    0,    0,  -73,   24,
  -80,  -55,    0,    0,  137, -203,  -66,  161,   81,  -58,
 -163,  147,  -52,    0,    0,  -32,  -49,  148,  187,  -38,
  186,  -37,  -31,  177,  -28,    0,  181,  -30,  184,  -27,
  202,  -25,  204,  -19,    0,  -18,  191,  192,  -17,  -15,
    0,    0,
};
final static short yyrindex[] = {                       131,
  255,    0,    0,    0,    0,    0,    0,    0,    0,   12,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  131,    0,    0,    0,    0,    0,    0,
    0,  -14,   -9,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  215,    0,    2, -115,    0,    0,  131,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    8,
    0,    0,    7,   15,    0,    0,    0,   -3,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  219,    0,    0,
  136,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    3,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  138,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    5,   25,  -44,    0,    0,   -2,    0,    0,    0,
    0,  180,    0,    0,    0,  -79,    0,    0,    0,  178,
  -60,    0,    0,    0,    0,    0,  113,  234,   46,   49,
    0,    0,
};
final static int YYTABLESIZE=267;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         39,
   89,   39,   81,   39,   80,   26,   61,   26,  170,   26,
   31,    1,   90,   52,   28,   73,   40,   47,   32,   69,
   74,  106,   54,   96,   12,  111,   59,  121,   53,   62,
   40,   62,  124,   62,   29,   62,  107,   70,   66,   33,
   19,   60,   58,   60,   59,   60,  132,   81,   61,   62,
   61,  128,   61,   20,  127,   40,   91,  152,   67,   60,
   58,  135,    6,    7,  157,   81,   61,  156,   40,   40,
   40,   40,   97,   40,  146,  147,  148,  136,  137,  104,
   59,   71,  140,   72,   24,   47,   25,  116,    1,    2,
    3,  118,   98,    2,    4,   58,   36,    5,    6,    7,
    8,   50,  134,   27,    2,    3,   34,  108,   57,    4,
   51,  161,    5,    6,    7,    8,   99,  100,   65,    2,
    3,  101,  102,   83,    4,   84,  142,    5,    6,    7,
    8,   85,   86,   87,   12,   88,   92,  150,   93,   95,
   26,   26,   26,   94,  109,  130,   26,  133,  130,   26,
   26,   26,   26,  110,   27,    2,    3,  112,  166,  115,
    4,  117,  119,    5,    6,    7,    8,  153,  130,   27,
    2,    3,   33,  125,  123,    4,  126,  129,    5,   27,
    2,    3,  131,  139,  143,    4,  141,  149,    5,  151,
   27,    2,    3,  158,  154,  155,    4,  160,   75,    5,
   27,    2,    3,  162,  163,  164,    4,  167,  172,    5,
   37,    2,  103,    2,   37,  165,  168,   76,   77,   78,
   79,   60,    2,  169,  171,    6,    7,  173,   38,  175,
   38,  174,   38,   27,    2,    3,  176,  178,  177,    4,
  179,  180,    5,  181,  182,  184,  183,  186,  185,  187,
  188,  189,  190,   12,    2,   18,  191,   57,  192,   19,
   54,  105,   55,   56,  159,  114,   64,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
   41,   45,   60,   45,   62,    8,   41,  123,   41,  125,
   13,    0,   57,  256,   10,   42,   19,   20,   40,  256,
   47,   82,   25,  125,    0,   86,   41,  107,  271,   32,
   33,   41,  112,   43,   10,   45,  123,  274,   34,   61,
   61,   41,   41,   43,   59,   45,  123,   41,   41,   59,
   43,   41,   45,   40,   44,   41,   59,  123,   34,   59,
   59,  256,  266,  267,   41,   59,   59,   44,   71,   72,
   73,   74,   68,   59,  135,  136,  137,  272,  273,   82,
   44,   43,  127,   45,   40,   88,   40,   90,  256,  257,
  258,   94,   68,  257,  262,   59,   59,  265,  266,  267,
  268,  261,  125,  256,  257,  258,  123,   83,   40,  262,
  261,  156,  265,  266,  267,  268,   71,   72,  256,  257,
  258,   73,   74,   41,  262,   41,  129,  265,  266,  267,
  268,   41,   61,   41,  123,   59,   41,  140,   59,   41,
  256,  257,  258,   44,   59,  121,  262,  123,  124,  265,
  266,  267,  268,   59,  256,  257,  258,  123,  161,  270,
  262,   59,   59,  265,  266,  267,  268,  143,  144,  256,
  257,  258,   61,   59,  260,  262,   61,   44,  265,  256,
  257,  258,  125,  274,  260,  262,  270,   44,  265,   61,
  256,  257,  258,  274,  125,  269,  262,   61,  256,  265,
  256,  257,  258,  270,   44,  125,  262,   61,   61,  265,
  256,  257,  256,  257,  256,  274,  269,  275,  276,  277,
  278,  256,  257,  256,  274,  266,  267,   41,  274,   44,
  274,  270,  274,  256,  257,  258,  274,   61,  270,  262,
  269,   61,  265,  274,   61,   44,  274,   44,  274,  269,
  269,   61,   61,  123,    0,   41,  274,  261,  274,   41,
  125,   82,  125,  261,  152,   88,   33,
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
"programa : error",
"bloquePrograma : bloquePrograma sentenciaDeclarativa",
"bloquePrograma : bloquePrograma sentenciaEjecutable",
"bloquePrograma : sentenciaDeclarativa",
"bloquePrograma : sentenciaEjecutable",
"sentenciaDeclarativa : tipo listaVariables ';'",
"sentenciaDeclarativa : declaracionProcedimiento",
"listaVariables : listaVariables ',' identificador",
"listaVariables : identificador",
"declaracionProcedimiento : encabezadoProc bloqueProc",
"encabezadoProc :",
"encabezadoProc : PROC identificador '(' ')' NA '=' CTE ',' NS '=' CTE",
"encabezadoProc : PROC identificador '(' tipo identificador ')' NA '=' CTE ',' NS '=' CTE",
"encabezadoProc : PROC identificador '(' tipo identificador ',' tipo identificador ')' NA '=' CTE ',' NS '=' CTE",
"encabezadoProc : PROC identificador '(' tipo identificador ',' tipo identificador ',' tipo identificador ')' NA '=' CTE ',' NS '=' CTE",
"encabezadoProc : PROC identificador '(' tipo identificador ',' tipo identificador ',' tipo identificador error ')' NA '=' CTE ',' NS '=' CTE",
"nombres : identificador",
"nombres : identificador ',' identificador",
"nombres : identificador ',' identificador ',' identificador",
"bloqueProc : '{' bloque '}'",
"bloque : bloque sentenciaDeclarativa",
"bloque : bloque sentenciaEjecutable",
"bloque : sentenciaDeclarativa",
"bloque : sentenciaEjecutable",
"bloque : error",
"sentenciaEjecutable : asignacion ';'",
"sentenciaEjecutable : OUT '(' CADENA ')' ';'",
"sentenciaEjecutable : OUT '(' error ')' ';'",
"sentenciaEjecutable : identificador '(' nombres ')' ';'",
"sentenciaEjecutable : identificador '(' ')' ';'",
"sentenciaEjecutable : identificador '(' error ')' ';'",
"sentenciaEjecutable : IF cuerpoIf",
"sentenciaEjecutable : cicloFor",
"cicloFor : FOR '(' condicionFor ')' '{' bloqueSentencia '}'",
"condicionFor : inicioFor ';' condiFOR ';' incDec",
"condiFOR : condicion",
"inicioFor : identificador '=' constante",
"condicion : identificador comparador asignacion",
"condicion : identificador comparador identificador",
"condicion : identificador comparador constante",
"incDec : UP constante",
"incDec : DOWN constante",
"incDec : error constante",
"bloqueSentencia : bloqueSentencia sentenciaEjecutable",
"bloqueSentencia : sentenciaEjecutable",
"cuerpoIf : cuerpoCompleto END_IF",
"cuerpoIf : cuerpoIncompleto END_IF",
"cuerpoCompleto : '(' condicionIf ')' '{' bloqueThen '}' ELSE '{' bloqueElse '}'",
"cuerpoCompleto : '(' condicionIf ')' sentenciaEjecutable ELSE '{' bloqueElse '}'",
"cuerpoCompleto : '(' condicionIf ')' '{' bloqueThen '}' ELSE sentenciaEjecutable",
"cuerpoCompleto : '(' condicionIf ')' sentenciaEjecutable ELSE sentenciaEjecutable",
"condicionIf : condicion",
"bloqueThen : bloqueSentencia",
"bloqueElse : bloqueSentencia",
"cuerpoIncompleto : '(' condicionIf ')' '{' bloqueThen '}'",
"cuerpoIncompleto : '(' condicionIf ')' sentenciaEjecutable",
"asignacion : identificador '=' expresion",
"asignacion : error '=' expresion",
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
"comparador : error",
"tipo : FLOAT",
"tipo : INTEGER",
"identificador : ID",
"constante : ctePositiva",
"constante : cteNegativa",
"ctePositiva : CTE",
"ctePositiva : error",
"cteNegativa : '-' CTE",
"cteNegativa : '-' error",
};

//#line 406 "gramatica.y"

//////////////////////////////////////////////////// 
//////////////Definiciones propias//////////////////
////////////////////////////////////////////////////

void mostrarMensaje(String mensaje){
	System.out.println(mensaje);
}

void comprobarRango(String sval, boolean negativo){
	double flotante;
	int entero;

	//ES NEGATIVO???
	if(negativo) {	
		//ES FLOAT Y NEGATIVO???
		if (sval.contains("f") || sval.contains(".")){
			flotante = Double.parseDouble(sval.replace('f', 'E'));
			String aux = "-" + sval;
			if ( AS10_Verificar_Rango_Float.estaEnRango(aux) ) {			
				compilador.Compilador.tablaSimbolo.remove(AS10_Verificar_Rango_Float.normalizar(flotante));
				Simbolo s = new Simbolo(AS10_Verificar_Rango_Float.normalizar(flotante));
				s.setValor("-"+s.getValor());
				s.setTipo("float");
				compilador.Compilador.tablaSimbolo.put(s.getValor(),s);
				mostrarMensaje("CTE FLOAT negativa esta dentro del rango");
			}
			else {
				compilador.Compilador.tablaSimbolo.remove(AS10_Verificar_Rango_Float.normalizar(flotante));
				mostrarMensaje("CTE FLOAT negativa esta fuera del rango por lo tanto no aparece en la tabla de simbolos.");
			}
		}
		//ES ENTERO Y NEGATIVO
		else{
			String aux = "-" + sval;
			if ( AS9_Verificar_Rango_Constante.estaEnRango(aux) ) {			
				compilador.Compilador.tablaSimbolo.remove(sval);
				Simbolo s = new Simbolo(sval);
				s.setValor("-"+s.getValor());
				s.setTipo("int");
				compilador.Compilador.tablaSimbolo.put(s.getValor(),s);
				mostrarMensaje("CTE ENTERA negativa esta dentro del rango");
			}
			else {
				compilador.Compilador.tablaSimbolo.remove(sval);
				mostrarMensaje("CTE ENTERA negativa esta fuera del rango por lo tanto no aparece en la tabla de simbolos.");
			}
		}
	//ES POSITIVO	
	}else {
		// ES FLOAT Y POSTIVO???
		if (sval.contains("f") || sval.contains(".")){
			flotante = Double.parseDouble(sval.replace('f', 'E'));
			if ( AS10_Verificar_Rango_Float.estaEnRango(sval) )
			mostrarMensaje("CTE FLOAT postiva esta dentro del rango");
			else {
				compilador.Compilador.tablaSimbolo.remove(AS10_Verificar_Rango_Float.normalizar(flotante));
				mostrarMensaje("CTE FLOAT postiva esta fuera del rango por lo tanto no aparece en la tabla de simbolos.");
			}
		}
		// ES ENTERA Y POSITIVA
		else{
			if ( AS9_Verificar_Rango_Constante.estaEnRango(sval) )
			mostrarMensaje("CTE ENTERA postiva esta dentro del rango");
			else {
				compilador.Compilador.tablaSimbolo.remove(sval);
				mostrarMensaje("CTE ENTERA postiva esta fuera del rango por lo tanto no aparece en la tabla de simbolos.");
			}
		}
		
	}
}

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
	if (error.equals("syntax error"))
		this.errores.add(error + " en linea " + this.lineaActual);
	else
		errores.add(error);
}

public ArrayList<String> getErrores(){
  return this.errores;
}

public ArrayList<String> getReconocidos(){
  return this.reconocidos;
}

//////////////////////////////////////////////////// 
//////////Fin Definiciones propias//////////////////
////////////////////////////////////////////////////
//#line 509 "Parser.java"
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
{
	mostrarMensaje("Reconoce bien el programa");
}
break;
case 2:
//#line 21 "gramatica.y"
{
	yyerror("Error: programa invalido: " + compilador.Compilador.nroLinea);
}
break;
case 3:
//#line 27 "gramatica.y"
{
}
break;
case 4:
//#line 30 "gramatica.y"
{
}
break;
case 5:
//#line 33 "gramatica.y"
{
}
break;
case 6:
//#line 36 "gramatica.y"
{
}
break;
case 7:
//#line 41 "gramatica.y"
{
	mostrarMensaje("Declaracion de una o mas variables en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 8:
//#line 45 "gramatica.y"
{
}
break;
case 9:
//#line 50 "gramatica.y"
{
}
break;
case 10:
//#line 53 "gramatica.y"
{
}
break;
case 11:
//#line 58 "gramatica.y"
{
	mostrarMensaje("Procedimiento completo, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 13:
//#line 64 "gramatica.y"
{
	mostrarMensaje("Procedimiento sin parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(10).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(5).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 14:
//#line 73 "gramatica.y"
{
	mostrarMensaje("Procedimiento con parametros en linea nro: "+compilador.Compilador.nroLinea);
	/*mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);*/
	/*mostrarMensaje("Palabra reservada " + $6.sval + ", en linea " + compilador.Compilador.nroLinea);*/
	/*mostrarMensaje("Token " + $7.sval + ", en linea " + compilador.Compilador.nroLinea);*/
	/*mostrarMensaje("Palabra reservada " + $10.sval + ", en linea " + compilador.Compilador.nroLinea);*/
	/*mostrarMensaje("Token " + $11.sval + ", en linea " + compilador.Compilador.nroLinea);*/
}
break;
case 15:
//#line 82 "gramatica.y"
{
	mostrarMensaje("Procedimiento con parametros en linea nro: "+compilador.Compilador.nroLinea);
	/*mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);*/
	/*mostrarMensaje("Palabra reservada " + $6.sval + ", en linea " + compilador.Compilador.nroLinea);*/
	/*mostrarMensaje("Token " + $7.sval + ", en linea " + compilador.Compilador.nroLinea);*/
	/*mostrarMensaje("Palabra reservada " + $10.sval + ", en linea " + compilador.Compilador.nroLinea);*/
	/*mostrarMensaje("Token " + $11.sval + ", en linea " + compilador.Compilador.nroLinea);*/
}
break;
case 16:
//#line 91 "gramatica.y"
{
	mostrarMensaje("Procedimiento con parametros en linea nro: "+compilador.Compilador.nroLinea);
	/*mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);*/
	/*mostrarMensaje("Palabra reservada " + $6.sval + ", en linea " + compilador.Compilador.nroLinea);*/
	/*mostrarMensaje("Token " + $7.sval + ", en linea " + compilador.Compilador.nroLinea);*/
	/*mostrarMensaje("Palabra reservada " + $10.sval + ", en linea " + compilador.Compilador.nroLinea);*/
	/*mostrarMensaje("Token " + $11.sval + ", en linea " + compilador.Compilador.nroLinea);*/
}
break;
case 17:
//#line 100 "gramatica.y"
{
	yyerror("Error: Cantidad no permitida de parametros, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("parametro1111111 error: "+ val_peek(8).sval+ "LINEA:" +compilador.Compilador.nroLinea);
}
break;
case 18:
//#line 107 "gramatica.y"
{
}
break;
case 19:
//#line 110 "gramatica.y"
{
}
break;
case 20:
//#line 113 "gramatica.y"
{
}
break;
case 21:
//#line 118 "gramatica.y"
{
}
break;
case 22:
//#line 123 "gramatica.y"
{
}
break;
case 23:
//#line 126 "gramatica.y"
{
}
break;
case 24:
//#line 129 "gramatica.y"
{
}
break;
case 25:
//#line 132 "gramatica.y"
{
}
break;
case 26:
//#line 135 "gramatica.y"
{
	yyerror("Error: no puede haber un seccion vacia, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 27:
//#line 141 "gramatica.y"
{
}
break;
case 28:
//#line 144 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(4).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token CADENA, lexema: " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	System.out.println("Salida por pantalla: " + val_peek(2).sval);
}
break;
case 29:
//#line 150 "gramatica.y"
{
	yyerror("Error: Formato de cadena incorrecto, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 30:
//#line 154 "gramatica.y"
{
	mostrarMensaje("Llamada a procedimiento con parametros en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 31:
//#line 158 "gramatica.y"
{
	mostrarMensaje("Llamda a procedimiento sin parametros en linea nro: "+compilador.Compilador.nroLinea);
}
break;
case 32:
//#line 162 "gramatica.y"
{
	yyerror("Error: Cantidad no permitida de parametros, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 33:
//#line 166 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 34:
//#line 170 "gramatica.y"
{
	mostrarMensaje("Ciclo FOR en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 35:
//#line 176 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 36:
//#line 182 "gramatica.y"
{
	mostrarMensaje("Encabezado de ciclo FOR, en linea nro: "+compilador.Compilador.nroLinea);
}
break;
case 37:
//#line 188 "gramatica.y"
{
}
break;
case 38:
//#line 193 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 39:
//#line 199 "gramatica.y"
{
}
break;
case 40:
//#line 202 "gramatica.y"
{
}
break;
case 41:
//#line 205 "gramatica.y"
{
}
break;
case 42:
//#line 210 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 43:
//#line 214 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 44:
//#line 218 "gramatica.y"
{
	yyerror("Error: incremento/decremento mal escrito, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 45:
//#line 224 "gramatica.y"
{
}
break;
case 46:
//#line 227 "gramatica.y"
{
}
break;
case 47:
//#line 232 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 48:
//#line 236 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 49:
//#line 242 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 50:
//#line 247 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, con sentencia unica en rama verdadera, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 51:
//#line 252 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, con sentencia unica en rama falsa, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 52:
//#line 257 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, con sentencia unica en ambas ramas, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 53:
//#line 264 "gramatica.y"
{	
}
break;
case 54:
//#line 269 "gramatica.y"
{
}
break;
case 55:
//#line 274 "gramatica.y"
{
}
break;
case 56:
//#line 279 "gramatica.y"
{
	mostrarMensaje("IF sin ELSE, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 57:
//#line 283 "gramatica.y"
{
	mostrarMensaje("IF sin ELSE, con sentencia unica, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 58:
//#line 289 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 59:
//#line 293 "gramatica.y"
{
	yyerror("Error: identificador mal escrito, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 60:
//#line 299 "gramatica.y"
{
}
break;
case 61:
//#line 302 "gramatica.y"
{
}
break;
case 62:
//#line 305 "gramatica.y"
{	
}
break;
case 63:
//#line 310 "gramatica.y"
{
}
break;
case 64:
//#line 313 "gramatica.y"
{
}
break;
case 65:
//#line 316 "gramatica.y"
{
}
break;
case 66:
//#line 321 "gramatica.y"
{
}
break;
case 67:
//#line 324 "gramatica.y"
{
}
break;
case 68:
//#line 329 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 69:
//#line 333 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 70:
//#line 337 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 71:
//#line 341 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 72:
//#line 345 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 73:
//#line 349 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 74:
//#line 353 "gramatica.y"
{
	yyerror("Error: comparador no permitido, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 75:
//#line 359 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 76:
//#line 363 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 77:
//#line 369 "gramatica.y"
{
	mostrarMensaje("Token ID, lexema: " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 78:
//#line 376 "gramatica.y"
{
}
break;
case 79:
//#line 379 "gramatica.y"
{
}
break;
case 80:
//#line 384 "gramatica.y"
{
	mostrarMensaje("Token: CTE, en linea " + compilador.Compilador.nroLinea);
	comprobarRango(val_peek(0).sval,false);
}
break;
case 81:
//#line 389 "gramatica.y"
{
	yyerror("Error: constante positiva mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 82:
//#line 395 "gramatica.y"
{
	mostrarMensaje("Token: CTE, en linea " + compilador.Compilador.nroLinea);
	comprobarRango(val_peek(0).sval,true);
}
break;
case 83:
//#line 400 "gramatica.y"
{
	yyerror("Error: constante negativa mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);	
}
break;
//#line 1144 "Parser.java"
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
