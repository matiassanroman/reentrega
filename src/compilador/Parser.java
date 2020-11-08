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
    6,    8,    8,    8,   11,   11,   11,   10,   10,   10,
   12,    9,   13,   13,   13,   13,   13,    3,    3,    3,
    3,    3,    3,    3,    3,   16,   17,   20,   19,   22,
   22,   22,   21,   21,   21,   18,   18,   15,   15,   25,
   25,   25,   25,   27,   28,   29,   26,   26,   14,   14,
   30,   30,   30,   31,   31,   31,   32,   32,   24,   24,
   24,   24,   24,   24,   24,    4,    4,    7,   23,   23,
   33,   33,   34,   34,
};
final static short yylen[] = {                            2,
    1,    1,    2,    2,    1,    1,    3,    1,    3,    1,
    2,   12,   11,   12,    1,    3,    5,    1,    3,    5,
    2,    3,    2,    2,    1,    1,    1,    2,    5,    5,
    5,    4,    5,    2,    1,    7,    5,    1,    3,    3,
    3,    3,    2,    2,    2,    2,    1,    2,    2,   10,
    8,    8,    6,    1,    1,    1,    6,    4,    3,    3,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   78,    0,    0,    0,   77,   76,    0,    0,    0,
    5,    6,    0,    8,    0,    0,    0,   35,    0,    0,
   34,    0,    0,    0,    0,    0,    0,    3,    4,    0,
   10,    0,    0,    0,   11,   28,   82,   81,    0,   68,
   67,    0,    0,   66,   79,   80,    0,   54,    0,   48,
   49,    0,    0,    0,    0,    0,    0,    7,    0,    0,
    0,    0,    0,    0,    0,   25,   26,    0,   84,   83,
    0,    0,    0,    0,   75,   69,   70,   71,   72,   73,
   74,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    9,    0,   32,    0,    0,   22,   23,
   24,    0,    0,   64,   65,    0,    0,   40,   42,    0,
    0,   30,   29,   39,    0,    0,   38,    0,    0,   21,
    0,    0,   33,    0,   31,   47,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   46,    0,    0,   53,
   36,    0,    0,    0,   37,    0,    0,    0,    0,   17,
    0,    0,    0,   45,   43,   44,    0,    0,    0,   20,
    0,   52,   51,    0,    0,    0,    0,    0,    0,    0,
   50,    0,   13,    0,   14,   12,
};
final static short yydgoto[] = {                          9,
   10,   11,  126,   13,   30,   14,   15,   16,   35,   92,
   63,   93,   68,   17,   21,   18,   55,  152,   56,  116,
  145,   48,   41,   82,   22,   23,   49,  128,  153,   42,
   43,   44,   45,   46,
};
final static short yysindex[] = {                      -175,
  -49,    0,   20,   39,   40,    0,    0, -209,    0, -144,
    0,    0, -209,    0,  -27,  -37,   -7,    0,  -45, -209,
    0, -173, -167, -232, -209,   55,  -49,    0,    0,    2,
    0,  -34,  -45,  -22,    0,    0,    0,    0, -242,    0,
    0,   21,   12,    0,    0,    0,  -57,    0,   63,    0,
    0,   64,   66,   54,   75,   58,  -40,    0, -209,   79,
   67,   83,   88,   21,  -49,    0,    0, -117,    0,    0,
  -45,  -45,  -45,  -45,    0,    0,    0,    0,    0,    0,
    0,  -43,  -90,   71,   72,  -41,    9, -209,   96, -132,
 -209,  101,   99,    0,   85,    0, -209,   87,    0,    0,
    0,   12,   12,    0,    0,  -49,   86,    0,    0, -159,
 -108,    0,    0,    0, -159,   97,    0, -110,  100,    0,
 -101, -189,    0,  126,    0,    0, -159,   46,  -80,  -60,
 -205,  113,  -95,  119,  137, -209,    0,  -77, -159,    0,
    0,  -41,  -41,  -41,    0,  -85,  140,  -84, -189,    0,
  -70, -159,   68,    0,    0,    0,  147,  -75,  156,    0,
 -159,    0,    0,  -68,  142,  -65,   81,  146,  -66,  148,
    0,  -64,    0,  -50,    0,    0,
};
final static short yyrindex[] = {                         0,
  217,    0,    0,    0,    0,    0,    0,    0,    0,  225,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -21,  -24,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  187,    0,  -12, -103,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  189,    0,    0,    0,    0,    0,    0,    0,
    0,  -18,  -15,    0,    0,  -10,   -4,    0,    0,    0,
  -29,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  196,    0,    0,  114,    0,    0,    0,
    0,    0,    0,    0,  197,    0,    0,  -20,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  117,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    8,    6,  -47,    0,    0,   37,    0,    0,    0,
    0, -113,    0,  165,    0,    0,    0,  -52,    0,    0,
    0,  160,  -71,    0,    0,    0,    0,    0,   89,  216,
   13,   27,    0,    0,
};
final static int YYTABLESIZE=250;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         39,
   90,   39,   81,   39,   80,   12,   61,   99,  135,   91,
  109,   19,   32,   69,  114,   29,   63,   28,   63,   60,
   63,   27,   61,   52,   61,   62,   61,   62,   59,   62,
   82,   70,  110,   33,   63,  160,   41,   60,   53,   67,
   61,   66,  139,   62,   26,   59,   59,    2,   82,   31,
  142,   36,  161,   73,   41,   40,   47,  127,   74,   20,
   58,   54,  130,   71,  141,   72,  143,  144,   62,   40,
  154,  155,  156,  101,   91,  100,    6,    7,   24,   25,
    1,    2,    3,  102,  103,   34,    4,   50,  111,    5,
    6,    7,    8,   51,   57,   94,   27,    2,    3,  104,
  105,   91,    4,   83,   84,    5,   85,   40,   40,   40,
   40,   27,    2,    3,   86,   87,   88,    4,  107,   95,
    5,    6,    7,    8,   47,   96,   97,  120,   98,  112,
  113,  115,  137,  124,  140,  137,  118,  119,   27,    2,
    3,  121,  122,  123,    4,  125,   33,    5,    6,    7,
    8,  129,   27,   27,   27,  131,  162,  137,   27,  132,
  133,   27,   27,   27,   27,   27,    2,    3,  134,  136,
  138,    4,  150,  146,    5,   27,    2,    3,  147,  148,
  149,    4,  151,  158,    5,   27,    2,    3,  157,  159,
  164,    4,  163,  165,    5,   27,    2,    3,   75,  166,
  168,    4,  169,  170,    5,  171,  172,  173,  174,  175,
   37,    2,  106,    2,   37,   89,    2,   76,   77,   78,
   79,   60,    2,  176,    1,    6,    7,   15,   38,   18,
   38,   58,   38,   65,    2,    3,   16,   19,   55,    4,
   57,   56,    5,    6,    7,    8,  108,  117,   64,  167,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
   41,   45,   60,   45,   62,    0,   41,  125,  122,   57,
   82,   61,   40,  256,   86,   10,   41,   10,   43,   41,
   45,  125,   41,  256,   43,   41,   45,   43,   41,   45,
   41,  274,  123,   61,   59,  149,   41,   59,  271,   34,
   59,   34,  123,   59,    8,   44,   59,  257,   59,   13,
  256,   59,  123,   42,   59,   19,   20,  110,   47,   40,
   59,   25,  115,   43,  125,   45,  272,  273,   32,   33,
  142,  143,  144,   68,  122,   68,  266,  267,   40,   40,
  256,  257,  258,   71,   72,  123,  262,  261,   83,  265,
  266,  267,  268,  261,   40,   59,  256,  257,  258,   73,
   74,  149,  262,   41,   41,  265,   41,   71,   72,   73,
   74,  256,  257,  258,   61,   41,   59,  262,   82,   41,
  265,  266,  267,  268,   88,   59,   44,   91,   41,   59,
   59,  123,  127,   97,  129,  130,   41,  270,  256,  257,
  258,   41,   44,   59,  262,   59,   61,  265,  266,  267,
  268,  260,  256,  257,  258,   59,  151,  152,  262,  270,
   61,  265,  266,  267,  268,  256,  257,  258,  270,   44,
  125,  262,  136,   61,  265,  256,  257,  258,  274,   61,
   44,  262,  260,   44,  265,  256,  257,  258,  274,  274,
   44,  262,  125,  269,  265,  256,  257,  258,  256,   44,
  269,  262,   61,  269,  265,  125,   61,  274,   61,  274,
  256,  257,  256,  257,  256,  256,    0,  275,  276,  277,
  278,  256,  257,  274,    0,  266,  267,   41,  274,   41,
  274,  261,  274,  256,  257,  258,   41,   41,  125,  262,
  261,  125,  265,  266,  267,  268,   82,   88,   33,  161,
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
"encabezadoProc : PROC identificador '(' parametrosProc ')' NA '=' CTE ',' NS '=' CTE",
"encabezadoProc : PROC identificador '(' ')' NA '=' CTE ',' NS '=' CTE",
"encabezadoProc : PROC identificador '(' error ')' NA '=' CTE ',' NS '=' CTE",
"nombres : identificador",
"nombres : identificador ',' identificador",
"nombres : identificador ',' identificador ',' identificador",
"parametrosProc : parametro",
"parametrosProc : parametro ',' parametro",
"parametrosProc : parametro ',' parametro ',' parametro",
"parametro : tipo identificador",
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

//#line 404 "gramatica.y"

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
//#line 500 "Parser.java"
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
case 12:
//#line 64 "gramatica.y"
{
	mostrarMensaje("Procedimiento con parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(11).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(5).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 13:
//#line 73 "gramatica.y"
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
//#line 82 "gramatica.y"
{
	yyerror("Error: Cantidad no permitida de parametros, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 15:
//#line 88 "gramatica.y"
{
}
break;
case 16:
//#line 91 "gramatica.y"
{
}
break;
case 17:
//#line 94 "gramatica.y"
{
}
break;
case 18:
//#line 99 "gramatica.y"
{
}
break;
case 19:
//#line 102 "gramatica.y"
{
}
break;
case 20:
//#line 105 "gramatica.y"
{
}
break;
case 21:
//#line 110 "gramatica.y"
{
	mostrarMensaje("Parametro, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 22:
//#line 116 "gramatica.y"
{
}
break;
case 23:
//#line 121 "gramatica.y"
{
}
break;
case 24:
//#line 124 "gramatica.y"
{
}
break;
case 25:
//#line 127 "gramatica.y"
{
}
break;
case 26:
//#line 130 "gramatica.y"
{
}
break;
case 27:
//#line 133 "gramatica.y"
{
	yyerror("Error: no puede haber un seccion vacia, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 28:
//#line 139 "gramatica.y"
{
}
break;
case 29:
//#line 142 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(4).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token CADENA, lexema: " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	System.out.println("Salida por pantalla: " + val_peek(2).sval);
}
break;
case 30:
//#line 148 "gramatica.y"
{
	yyerror("Error: Formato de cadena incorrecto, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 31:
//#line 152 "gramatica.y"
{
	mostrarMensaje("Llamada a procedimiento con parametros en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 32:
//#line 156 "gramatica.y"
{
	mostrarMensaje("Llamda a procedimiento sin parametros en linea nro: "+compilador.Compilador.nroLinea);
}
break;
case 33:
//#line 160 "gramatica.y"
{
	yyerror("Error: Cantidad no permitida de parametros, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 34:
//#line 164 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 35:
//#line 168 "gramatica.y"
{
	mostrarMensaje("Ciclo FOR en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 36:
//#line 174 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 37:
//#line 180 "gramatica.y"
{
	mostrarMensaje("Encabezado de ciclo FOR, en linea nro: "+compilador.Compilador.nroLinea);
}
break;
case 38:
//#line 186 "gramatica.y"
{
}
break;
case 39:
//#line 191 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 40:
//#line 197 "gramatica.y"
{
}
break;
case 41:
//#line 200 "gramatica.y"
{
}
break;
case 42:
//#line 203 "gramatica.y"
{
}
break;
case 43:
//#line 208 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 44:
//#line 212 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 45:
//#line 216 "gramatica.y"
{
	yyerror("Error: incremento/decremento mal escrito, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 46:
//#line 222 "gramatica.y"
{
}
break;
case 47:
//#line 225 "gramatica.y"
{
}
break;
case 48:
//#line 230 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 49:
//#line 234 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 50:
//#line 240 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 51:
//#line 245 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, con sentencia unica en rama verdadera, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 52:
//#line 250 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, con sentencia unica en rama falsa, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 53:
//#line 255 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, con sentencia unica en ambas ramas, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 54:
//#line 262 "gramatica.y"
{	
}
break;
case 55:
//#line 267 "gramatica.y"
{
}
break;
case 56:
//#line 272 "gramatica.y"
{
}
break;
case 57:
//#line 277 "gramatica.y"
{
	mostrarMensaje("IF sin ELSE, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 58:
//#line 281 "gramatica.y"
{
	mostrarMensaje("IF sin ELSE, con sentencia unica, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 59:
//#line 287 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 60:
//#line 291 "gramatica.y"
{
	yyerror("Error: identificador mal escrito, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 61:
//#line 297 "gramatica.y"
{
}
break;
case 62:
//#line 300 "gramatica.y"
{
}
break;
case 63:
//#line 303 "gramatica.y"
{	
}
break;
case 64:
//#line 308 "gramatica.y"
{
}
break;
case 65:
//#line 311 "gramatica.y"
{
}
break;
case 66:
//#line 314 "gramatica.y"
{
}
break;
case 67:
//#line 319 "gramatica.y"
{
}
break;
case 68:
//#line 322 "gramatica.y"
{
}
break;
case 69:
//#line 327 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 70:
//#line 331 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 71:
//#line 335 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 72:
//#line 339 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 73:
//#line 343 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 74:
//#line 347 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 75:
//#line 351 "gramatica.y"
{
	yyerror("Error: comparador no permitido, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 76:
//#line 357 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 77:
//#line 361 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 78:
//#line 367 "gramatica.y"
{
	mostrarMensaje("Token ID, lexema: " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 79:
//#line 374 "gramatica.y"
{
}
break;
case 80:
//#line 377 "gramatica.y"
{
}
break;
case 81:
//#line 382 "gramatica.y"
{
	mostrarMensaje("Token: CTE, en linea " + compilador.Compilador.nroLinea);
	comprobarRango(val_peek(0).sval,false);
}
break;
case 82:
//#line 387 "gramatica.y"
{
	yyerror("Error: constante positiva mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 83:
//#line 393 "gramatica.y"
{
	mostrarMensaje("Token: CTE, en linea " + compilador.Compilador.nroLinea);
	comprobarRango(val_peek(0).sval,true);
}
break;
case 84:
//#line 398 "gramatica.y"
{
	yyerror("Error: constante negativa mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);	
}
break;
//#line 1133 "Parser.java"
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
