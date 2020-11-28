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
   23,   23,   25,   26,   27,   24,   24,   12,   12,   12,
   28,   28,   28,   29,   29,   29,   30,   30,   22,   22,
   22,   22,   22,   22,   22,    4,    4,    7,   21,   21,
   33,   31,   34,   32,
};
final static short yylen[] = {                            2,
    1,    1,    2,    2,    1,    1,    3,    1,    3,    1,
    2,    0,   11,   13,   16,   19,   20,    1,    3,    5,
    3,    2,    2,    1,    1,    1,    2,    5,    5,    5,
    4,    5,    2,    1,    7,    5,    1,    3,    3,    3,
    3,    2,    2,    2,    2,    1,    2,    2,   10,    8,
    8,    6,    1,    1,    1,    6,    4,    3,    3,    3,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    0,    2,    0,    3,
};
final static short yydefred[] = {                         0,
    0,   78,    0,    0,    0,   77,   76,    0,    0,    0,
    5,    6,    0,    8,    0,    0,    0,   34,    0,    0,
   33,    0,    0,    0,    0,    0,    0,    3,    4,    0,
   10,    0,    0,    0,   11,   27,   81,    0,   68,   67,
    0,    0,   66,   79,   80,    0,   53,    0,   47,   48,
    0,    0,    0,    0,    0,    0,    7,    0,    0,    0,
    0,    0,   60,    0,    0,   24,   25,    0,   82,   83,
    0,    0,    0,    0,   75,   69,   70,   71,   72,   73,
   74,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    9,    0,   31,    0,    0,   21,   22,   23,   84,    0,
    0,   64,   65,    0,   39,   41,    0,    0,   29,   28,
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
   10,   11,  120,   13,   30,   14,   15,   16,   35,   62,
   68,   17,   21,   18,   54,  144,   55,  113,  138,   47,
   40,   82,   22,   23,   48,  122,  145,   41,   42,   43,
   44,   45,   69,   99,
};
final static short yysindex[] = {                      -160,
  -41,    0,    8,   40,   44,    0,    0, -220,    0, -145,
    0,    0, -220,    0,  -27,  -28,    3,    0,  -42, -220,
    0, -162, -161, -199, -220,   63,  -41,    0,    0,   33,
    0,  -32,  -45,  -11,    0,    0,    0, -165,    0,    0,
  -19,    9,    0,    0,    0,  -56,    0,   69,    0,    0,
   73,   74,   55,   77,   65,  -33,    0, -220,   84,   72,
   88,   93,    0,  -19,  -41,    0,    0,  -93,    0,    0,
  -42,  -42,  -42,  -42,    0,    0,    0,    0,    0,    0,
    0,  -43,  -80,   76,   79,  -44,   20, -220, -130, -220,
    0,   85,    0, -220,   89,    0,    0,    0,    0,    9,
    9,    0,    0,   91,    0,    0, -129, -110,    0,    0,
    0, -129,   90,    0,   92,   17,    0,  115,    0,    0,
 -129,   35,  -70,  -21, -206, -108, -184, -103, -220,    0,
  -92, -129,    0,    0,  -44,  -44,  -44,    0,  126, -220,
  110,    0,  -60, -129,   54,    0,    0,    0,  -89,   37,
  -91, -129,    0,    0,  120, -184,  -86,  145,   66,  -84,
 -220,  132,  -75,    0,    0,  -30,  -73,  138,  162,  -66,
  163,  -68,  -62,  148,  -59,    0,  155,  -57,  157,  -51,
  183,  -46,  194,  -29,    0,  -26,  178,  181,  -25,  -24,
    0,    0,
};
final static short yyrindex[] = {                       125,
  252,    0,    0,    0,    0,    0,    0,    0,    0,    7,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  125,    0,    0,    0,    0,    0,    0,
  -18,  -10,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  212,    0,    0,  -14, -111,    0,    0,  125,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   -5,
    1,    0,    0,    6,    0,    0,    0,   -2,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  217,    0,    0,
  135,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    2,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  136,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    5,   18,  -37,    0,    0,   -3,    0,    0,    0,
    0,  180,    0,    0,    0,  -48,    0,    0,    0,  176,
  -61,    0,    0,    0,    0,    0,  113,  233,   22,   15,
    0,    0,    0,    0,
};
final static int YYTABLESIZE=266;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         38,
   38,   38,   38,   81,   26,   80,    1,   89,   60,   31,
  170,   26,   32,   26,   28,   39,   46,   12,   90,   19,
  106,   53,   59,   71,  111,   72,   58,   29,   61,   39,
   63,   96,   63,   33,   63,   61,    2,   61,   66,   61,
   59,   62,  107,   62,   58,   62,   40,   20,   63,  135,
   73,   67,  132,   61,   91,   74,   51,  128,  121,   62,
  127,   36,  152,  124,   40,  136,  137,   39,   39,   39,
   39,   52,   97,  146,  147,  148,   58,  157,  104,   24,
  156,    6,    7,   25,   46,   98,  116,  102,  103,  140,
  118,   57,  100,  101,   34,    1,    2,    3,   49,   50,
  108,    4,   56,  134,    5,    6,    7,    8,   70,   83,
   27,    2,    3,   84,   85,   86,    4,   87,  161,    5,
    6,    7,    8,   88,   92,  142,   27,    2,    3,   12,
   93,   94,    4,   95,  109,    5,  150,  110,  130,  115,
  133,  130,  112,  117,   26,   26,   26,  119,  125,  123,
   26,   33,  126,   26,   26,   26,   26,  166,  129,  131,
  153,  130,   27,    2,    3,  139,  141,  143,    4,  149,
  151,    5,    6,    7,    8,   27,    2,    3,  154,  155,
  160,    4,  158,  162,    5,   27,    2,    3,  163,  165,
  164,    4,  167,  168,    5,   27,    2,    3,  172,   75,
  171,    4,  173,  174,    5,  176,  175,  177,  178,  179,
   63,    2,   27,    2,    2,  180,  181,  182,   76,   77,
   78,   79,  183,   59,    2,  169,  184,  185,   37,   37,
   37,   37,    6,    7,   27,    2,    3,  186,  189,  187,
    4,  190,  188,    5,   65,    2,    3,   12,  191,  192,
    4,    2,   18,    5,    6,    7,    8,   19,   57,   54,
   55,  105,   56,  114,  159,   64,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
   45,   45,   45,   60,    8,   62,    0,   41,   41,   13,
   41,  123,   40,  125,   10,   19,   20,    0,   56,   61,
   82,   25,   41,   43,   86,   45,   41,   10,   32,   33,
   41,  125,   43,   61,   45,   41,  257,   43,   34,   45,
   59,   41,  123,   43,   59,   45,   41,   40,   59,  256,
   42,   34,  123,   59,   58,   47,  256,   41,  107,   59,
   44,   59,  123,  112,   59,  272,  273,   71,   72,   73,
   74,  271,   68,  135,  136,  137,   44,   41,   82,   40,
   44,  266,  267,   40,   88,   68,   90,   73,   74,  127,
   94,   59,   71,   72,  123,  256,  257,  258,  261,  261,
   83,  262,   40,  125,  265,  266,  267,  268,  274,   41,
  256,  257,  258,   41,   41,   61,  262,   41,  156,  265,
  266,  267,  268,   59,   41,  129,  256,  257,  258,  123,
   59,   44,  262,   41,   59,  265,  140,   59,  121,  270,
  123,  124,  123,   59,  256,  257,  258,   59,   59,  260,
  262,   61,   61,  265,  266,  267,  268,  161,   44,  125,
  143,  144,  256,  257,  258,  274,  270,  260,  262,   44,
   61,  265,  266,  267,  268,  256,  257,  258,  125,  269,
   61,  262,  274,  270,  265,  256,  257,  258,   44,  274,
  125,  262,   61,  269,  265,  256,  257,  258,   61,  256,
  274,  262,   41,  270,  265,  274,   44,  270,   61,  269,
  256,  257,  256,  257,  257,   61,  274,   61,  275,  276,
  277,  278,  274,  256,  257,  256,   44,  274,  274,  274,
  274,  274,  266,  267,  256,  257,  258,   44,   61,  269,
  262,   61,  269,  265,  256,  257,  258,  123,  274,  274,
  262,    0,   41,  265,  266,  267,  268,   41,  261,  125,
  125,   82,  261,   88,  152,   33,
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
"asignacion : identificador '=' error",
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
"$$1 :",
"ctePositiva : CTE $$1",
"$$2 :",
"cteNegativa : '-' CTE $$2",
};

//#line 409 "gramatica.y"

//////////////////////////////////////////////////// 
//////////////Definiciones propias//////////////////
////////////////////////////////////////////////////

void mostrarMensaje(String mensaje){
	System.out.println(mensaje);
}

void comprobarRango(String sval, boolean negativo){
	double flotante;

	//ES NEGATIVO???
	if(negativo) {	
		//ES FLOAT Y NEGATIVO???
		if (sval.contains("f") || sval.contains(".")){
			flotante = Double.parseDouble(sval.replace('f', 'E'));
			String aux = "-" + sval;
			if ( AS10_Verificar_Rango_Float.estaEnRango(aux) ) {			
				//compilador.Compilador.tablaSimbolo.remove(AS10_Verificar_Rango_Float.normalizar(flotante));
				String auxx = AS10_Verificar_Rango_Float.normalizar(flotante);
				
				compilador.Compilador.tablaSimbolo.get(auxx).get(compilador.Compilador.tablaSimbolo.get(auxx).size()-1).setValor("-"+auxx);
				compilador.Compilador.tablaSimbolo.get(auxx).get(compilador.Compilador.tablaSimbolo.get(auxx).size()-1).setTipo("float");
				
				//compilador.Compilador.tablaSimbolo.get(auxx).remove(compilador.Compilador.tablaSimbolo.get(auxx).size()-1);
				/*
				Simbolo s = new Simbolo(AS10_Verificar_Rango_Float.normalizar(flotante));
				s.setValor("-"+s.getValor());
				s.setTipo("float");
				s.setUso("CTE");
				*/
				//compilador.Compilador.tablaSimbolo.put(s.getValor(),s);
				//compilador.Compilador.tablaSimbolo.get(auxx).add(s);
				mostrarMensaje("CTE FLOAT negativa esta dentro del rango");
			}
			else {
				//compilador.Compilador.tablaSimbolo.remove(AS10_Verificar_Rango_Float.normalizar(flotante));
				compilador.Compilador.tablaSimbolo.get(AS10_Verificar_Rango_Float.normalizar(flotante)).remove(compilador.Compilador.tablaSimbolo.get(AS10_Verificar_Rango_Float.normalizar(flotante)).size()-1);
				mostrarMensaje("CTE FLOAT negativa esta fuera del rango por lo tanto no aparece en la tabla de simbolos.");
			}
		}
		//ES ENTERO Y NEGATIVO
		else{
			String aux = "-" + sval;
			if ( AS9_Verificar_Rango_Constante.estaEnRango(aux) ) {			
				//compilador.Compilador.tablaSimbolo.remove(sval);
				sval = sval.toString().substring(0, sval.length()-2);
				
				compilador.Compilador.tablaSimbolo.get(sval).get(compilador.Compilador.tablaSimbolo.get(sval).size()-1).setValor("-"+sval);
				compilador.Compilador.tablaSimbolo.get(sval).get(compilador.Compilador.tablaSimbolo.get(sval).size()-1).setTipo("int");
				
				//compilador.Compilador.tablaSimbolo.put(s.getValor(),s);
				//compilador.Compilador.tablaSimbolo.get(sval).add(s);
				mostrarMensaje("CTE ENTERA negativa esta dentro del rango");
			}
			else {
				//compilador.Compilador.tablaSimbolo.remove(sval);
				sval = sval.toString().substring(0, sval.length()-2);
				compilador.Compilador.tablaSimbolo.get(sval).remove(compilador.Compilador.tablaSimbolo.get(sval).size()-1);
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
				//compilador.Compilador.tablaSimbolo.remove(AS10_Verificar_Rango_Float.normalizar(flotante));
				compilador.Compilador.tablaSimbolo.get(AS10_Verificar_Rango_Float.normalizar(flotante)).remove(compilador.Compilador.tablaSimbolo.get(AS10_Verificar_Rango_Float.normalizar(flotante)).size()-1);
				mostrarMensaje("CTE FLOAT positiva esta fuera del rango por lo tanto no aparece en la tabla de simbolos.");
			}
		}
		// ES ENTERA Y POSITIVA
		else{
			if ( AS9_Verificar_Rango_Constante.estaEnRango(sval) )
			mostrarMensaje("CTE ENTERA postiva esta dentro del rango");
			else {
				//compilador.Compilador.tablaSimbolo.remove(sval);
				sval = sval.toString().substring(0, sval.length()-2);
				compilador.Compilador.tablaSimbolo.get(sval).remove(compilador.Compilador.tablaSimbolo.get(sval).size()-1);
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
//#line 529 "Parser.java"
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
        //yyerror("syntax error");
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
	yyerror("Programa invalido, error en linea: " + compilador.Compilador.nroLinea);
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
	mostrarMensaje("Palabra reservada " + val_peek(12).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(5).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 15:
//#line 82 "gramatica.y"
{
	mostrarMensaje("Procedimiento con parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(15).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(5).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 16:
//#line 91 "gramatica.y"
{
	mostrarMensaje("Procedimiento con parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(18).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(5).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 17:
//#line 100 "gramatica.y"
{
	yyerror("Error: Cantidad no permitida de parametros, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 18:
//#line 106 "gramatica.y"
{
}
break;
case 19:
//#line 109 "gramatica.y"
{
}
break;
case 20:
//#line 112 "gramatica.y"
{
}
break;
case 21:
//#line 117 "gramatica.y"
{
}
break;
case 22:
//#line 122 "gramatica.y"
{
}
break;
case 23:
//#line 125 "gramatica.y"
{
}
break;
case 24:
//#line 128 "gramatica.y"
{
}
break;
case 25:
//#line 131 "gramatica.y"
{
}
break;
case 26:
//#line 134 "gramatica.y"
{
	yyerror("Error: no puede haber un seccion vacia, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 27:
//#line 140 "gramatica.y"
{
}
break;
case 28:
//#line 143 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(4).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token CADENA, lexema: " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	System.out.println("Salida por pantalla: " + val_peek(2).sval);
}
break;
case 29:
//#line 149 "gramatica.y"
{
	yyerror("Error: Formato de cadena incorrecto, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 30:
//#line 153 "gramatica.y"
{
	mostrarMensaje("Llamada a procedimiento con parametros en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 31:
//#line 157 "gramatica.y"
{
	mostrarMensaje("Llamda a procedimiento sin parametros en linea nro: "+compilador.Compilador.nroLinea);
}
break;
case 32:
//#line 161 "gramatica.y"
{
	yyerror("Error: Cantidad no permitida de parametros, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 33:
//#line 165 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 34:
//#line 169 "gramatica.y"
{
	mostrarMensaje("Ciclo FOR en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 35:
//#line 175 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 36:
//#line 181 "gramatica.y"
{
	mostrarMensaje("Encabezado de ciclo FOR, en linea nro: "+compilador.Compilador.nroLinea);
}
break;
case 37:
//#line 187 "gramatica.y"
{
}
break;
case 38:
//#line 192 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 39:
//#line 198 "gramatica.y"
{
}
break;
case 40:
//#line 201 "gramatica.y"
{
}
break;
case 41:
//#line 204 "gramatica.y"
{
}
break;
case 42:
//#line 209 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 43:
//#line 213 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 44:
//#line 217 "gramatica.y"
{
	yyerror("Error: incremento/decremento mal escrito, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 45:
//#line 223 "gramatica.y"
{
}
break;
case 46:
//#line 226 "gramatica.y"
{
}
break;
case 47:
//#line 231 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 48:
//#line 235 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 49:
//#line 241 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 50:
//#line 246 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, con sentencia unica en rama verdadera, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 51:
//#line 251 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, con sentencia unica en rama falsa, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 52:
//#line 256 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, con sentencia unica en ambas ramas, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 53:
//#line 263 "gramatica.y"
{	
}
break;
case 54:
//#line 268 "gramatica.y"
{
}
break;
case 55:
//#line 273 "gramatica.y"
{
}
break;
case 56:
//#line 278 "gramatica.y"
{
	mostrarMensaje("IF sin ELSE, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 57:
//#line 282 "gramatica.y"
{
	mostrarMensaje("IF sin ELSE, con sentencia unica, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 58:
//#line 288 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 59:
//#line 292 "gramatica.y"
{
	yyerror("Error: identificador mal escrito, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 60:
//#line 296 "gramatica.y"
{
	yyerror("Error: asignacion mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);
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
//#line 308 "gramatica.y"
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
//#line 319 "gramatica.y"
{
}
break;
case 67:
//#line 324 "gramatica.y"
{
}
break;
case 68:
//#line 327 "gramatica.y"
{
}
break;
case 69:
//#line 332 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 70:
//#line 336 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 71:
//#line 340 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 72:
//#line 344 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 73:
//#line 348 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 74:
//#line 352 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 75:
//#line 356 "gramatica.y"
{
	yyerror("Error: comparador no permitido, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 76:
//#line 362 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 77:
//#line 366 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 78:
//#line 372 "gramatica.y"
{
	mostrarMensaje("Token ID, lexema: " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 79:
//#line 379 "gramatica.y"
{
}
break;
case 80:
//#line 382 "gramatica.y"
{
}
break;
case 81:
//#line 387 "gramatica.y"
{
	mostrarMensaje("Token: CTE, en linea " + compilador.Compilador.nroLinea);
	comprobarRango(val_peek(0).sval,false);
}
break;
case 82:
//#line 392 "gramatica.y"
{
	/*yyerror("Error: constante positiva mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);*/
}
break;
case 83:
//#line 398 "gramatica.y"
{
	mostrarMensaje("Token: CTE, en linea " + compilador.Compilador.nroLinea);
	comprobarRango(val_peek(0).sval,true);
}
break;
case 84:
//#line 403 "gramatica.y"
{
	/*yyerror("Error: constante negativa mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);	*/
}
break;
//#line 1169 "Parser.java"
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
