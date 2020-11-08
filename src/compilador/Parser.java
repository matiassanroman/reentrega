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
    0,    1,    1,    1,    1,    2,    2,    5,    5,    6,
    8,    8,    8,   11,   11,   11,   10,   10,   10,   12,
    9,   13,   13,   13,   13,   13,    3,    3,    3,    3,
    3,    3,    3,    3,   16,   17,   20,   19,   22,   22,
   22,   21,   21,   21,   18,   18,   15,   15,   25,   25,
   25,   25,   27,   28,   29,   26,   26,   14,   14,   30,
   30,   30,   31,   31,   31,   32,   32,   24,   24,   24,
   24,   24,   24,   24,    4,    4,    7,   23,   23,   33,
   33,   34,   34,
};
final static short yylen[] = {                            2,
    1,    2,    2,    1,    1,    3,    1,    3,    1,    2,
   12,   11,   12,    1,    3,    5,    1,    3,    5,    2,
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
    4,    5,    0,    7,    0,    0,    0,   34,    0,    0,
   33,    0,    0,    0,    0,    0,    2,    3,    0,    9,
    0,    0,    0,   10,   27,   81,   80,    0,   67,   66,
    0,    0,   65,   78,   79,    0,   53,    0,   47,   48,
    0,    0,    0,    0,    0,    0,    6,    0,    0,    0,
    0,    0,    0,    0,   24,   25,    0,   83,   82,    0,
    0,    0,    0,   74,   68,   69,   70,   71,   72,   73,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    8,    0,   31,    0,    0,   21,   22,   23,
    0,    0,   63,   64,    0,    0,   39,   41,    0,    0,
   29,   28,   38,    0,    0,   37,    0,    0,   20,    0,
    0,   32,    0,   30,   46,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   45,    0,    0,   52,   35,
    0,    0,    0,   36,    0,    0,    0,    0,   16,    0,
    0,    0,   44,   42,   43,    0,    0,    0,   19,    0,
   51,   50,    0,    0,    0,    0,    0,    0,    0,   49,
    0,   12,    0,   13,   11,
};
final static short yydgoto[] = {                          9,
   10,   11,  125,   13,   29,   14,   15,   16,   34,   91,
   62,   92,   67,   17,   21,   18,   54,  151,   55,  115,
  144,   47,   40,   81,   22,   23,   48,  127,  152,   41,
   42,   43,   44,   45,
};
final static short yysindex[] = {                      -154,
  -37,    0,  -26,  -13,   -7,    0,    0, -211,    0, -154,
    0,    0, -211,    0,  -17,  -73,    3,    0,  -45, -211,
    0, -191, -172, -245, -211,   58,    0,    0,    7,    0,
  -34,  -45, -141,    0,    0,    0,    0, -243,    0,    0,
   16,   18,    0,    0,    0,  -57,    0,   68,    0,    0,
   69,   77,   59,   78,   63,  -40,    0, -211,   82,   70,
   84,   89,   16,  -37,    0,    0, -117,    0,    0,  -45,
  -45,  -45,  -45,    0,    0,    0,    0,    0,    0,    0,
  -43,  -91,   72,   74,  -41,   14, -211,   95, -132, -211,
  101,   99,    0,   87,    0, -211,   88,    0,    0,    0,
   18,   18,    0,    0,  -37,   94,    0,    0, -165, -101,
    0,    0,    0, -165,  109,    0, -110,  108,    0, -100,
 -180,    0,  128,    0,    0, -165,   48,  -81,  -61, -188,
  117,  -95,  119,  138, -211,    0,  -77, -165,    0,    0,
  -41,  -41,  -41,    0,  -86,  145,  -84, -180,    0,  -71,
 -165,   67,    0,    0,    0,  149,  -69,  154,    0, -165,
    0,    0,  -67,  142,  -64,   81,  146,  -65,  147,    0,
  -50,    0,  -49,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  210,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -23,    4,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  176,    0,  -21, -104,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  187,    0,    0,    0,    0,    0,    0,    0,    0,
   12,   13,    0,    0,  -16,  -11,    0,    0,    0,  -29,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  189,    0,    0,  110,    0,    0,    0,    0,
    0,    0,    0,  193,    0,    0,  -25,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  112,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    2,    6,  -47,    0,    0,    9,    0,    0,    0,
    0, -111,    0,  157,    0,    0,    0,  -31,    0,    0,
    0,  152,  -66,    0,    0,    0,    0,    0,   80,  209,
   24,   34,    0,    0,
};
final static int YYTABLESIZE=241;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         38,
   89,   38,   80,   38,   79,   12,   60,   98,   90,  134,
   51,   27,   68,   20,  108,   28,   26,   59,  113,   58,
   26,   30,   31,   19,   81,   52,   24,   39,   46,   40,
   69,  109,   25,   53,   65,   59,  159,   58,   66,   61,
   39,  138,   81,   32,   62,    2,   62,   40,   62,   33,
   58,  160,   60,   61,   60,   61,   60,   61,   70,   72,
   71,   35,   62,  140,   73,   57,   93,  141,   99,   49,
   60,   61,  100,   90,  153,  154,  155,  126,   39,   39,
   39,   39,  129,  142,  143,    6,    7,  110,   50,  106,
    1,    2,    3,  101,  102,   46,    4,   56,  119,    5,
   90,    1,    2,    3,  123,  103,  104,    4,   82,   83,
    5,    6,    7,    8,   64,    2,    3,   84,   86,   85,
    4,   87,   94,    5,    6,    7,    8,   96,   95,   97,
  111,  136,  112,  139,  136,  117,  114,  118,    1,    2,
    3,  120,  121,  149,    4,  122,  124,    5,    6,    7,
    8,   26,   26,   26,   32,  161,  136,   26,  128,  131,
   26,   26,   26,   26,    1,    2,    3,  130,  132,  133,
    4,  135,  137,    5,    1,    2,    3,  145,  146,  147,
    4,  148,  150,    5,    1,    2,    3,  156,  157,  158,
    4,  162,  163,    5,    1,    2,    3,  165,   74,  164,
    4,  167,  168,    5,  169,  170,  171,  173,  172,    1,
   36,    2,  105,    2,   36,   88,   14,   75,   76,   77,
   78,   59,    2,  174,  175,    6,    7,   17,   37,   15,
   37,   57,   37,   18,   54,   56,   55,  107,  116,  166,
   63,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
   41,   45,   60,   45,   62,    0,   41,  125,   56,  121,
  256,   10,  256,   40,   81,   10,    8,   41,   85,   41,
  125,   13,   40,   61,   41,  271,   40,   19,   20,   41,
  274,  123,   40,   25,   33,   59,  148,   59,   33,   31,
   32,  123,   59,   61,   41,  257,   43,   59,   45,  123,
   44,  123,   41,   41,   43,   43,   45,   45,   43,   42,
   45,   59,   59,  125,   47,   59,   58,  256,   67,  261,
   59,   59,   67,  121,  141,  142,  143,  109,   70,   71,
   72,   73,  114,  272,  273,  266,  267,   82,  261,   81,
  256,  257,  258,   70,   71,   87,  262,   40,   90,  265,
  148,  256,  257,  258,   96,   72,   73,  262,   41,   41,
  265,  266,  267,  268,  256,  257,  258,   41,   41,   61,
  262,   59,   41,  265,  266,  267,  268,   44,   59,   41,
   59,  126,   59,  128,  129,   41,  123,  270,  256,  257,
  258,   41,   44,  135,  262,   59,   59,  265,  266,  267,
  268,  256,  257,  258,   61,  150,  151,  262,  260,  270,
  265,  266,  267,  268,  256,  257,  258,   59,   61,  270,
  262,   44,  125,  265,  256,  257,  258,   61,  274,   61,
  262,   44,  260,  265,  256,  257,  258,  274,   44,  274,
  262,  125,   44,  265,  256,  257,  258,   44,  256,  269,
  262,  269,   61,  265,  269,  125,   61,   61,  274,    0,
  256,  257,  256,  257,  256,  256,   41,  275,  276,  277,
  278,  256,  257,  274,  274,  266,  267,   41,  274,   41,
  274,  261,  274,   41,  125,  261,  125,   81,   87,  160,
   32,
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

//#line 403 "gramatica.y"

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
<<<<<<< HEAD
//#line 433 "Parser.java"
=======
//#line 496 "Parser.java"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
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
//#line 23 "gramatica.y"
{
}
break;
case 3:
//#line 26 "gramatica.y"
{
}
break;
case 4:
//#line 29 "gramatica.y"
{
}
break;
case 5:
//#line 32 "gramatica.y"
{
}
break;
case 6:
//#line 37 "gramatica.y"
{
	mostrarMensaje("Declaracion de una o mas variables en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 7:
//#line 41 "gramatica.y"
{
}
break;
case 8:
//#line 46 "gramatica.y"
{
}
break;
case 9:
//#line 49 "gramatica.y"
{
}
break;
case 10:
//#line 54 "gramatica.y"
{
	mostrarMensaje("Procedimiento completo, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 11:
//#line 60 "gramatica.y"
{
	mostrarMensaje("Procedimiento con parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(11).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(5).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 12:
//#line 70 "gramatica.y"
{
	mostrarMensaje("Procedimiento sin parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(10).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(5).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 13:
//#line 80 "gramatica.y"
{
	yyerror("Error: Cantidad no permitida de parametros, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 14:
//#line 86 "gramatica.y"
{
}
break;
case 15:
//#line 89 "gramatica.y"
{
}
break;
case 16:
//#line 92 "gramatica.y"
{
}
break;
case 17:
//#line 97 "gramatica.y"
{
}
break;
case 18:
//#line 100 "gramatica.y"
{
}
break;
case 19:
//#line 103 "gramatica.y"
{
}
break;
case 20:
//#line 108 "gramatica.y"
{
	mostrarMensaje("Parametro, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 21:
//#line 114 "gramatica.y"
{
	mostrarMensaje("Bloque de procedimiento en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 22:
//#line 120 "gramatica.y"
{
}
break;
case 23:
//#line 123 "gramatica.y"
{
}
break;
case 24:
//#line 126 "gramatica.y"
{
}
break;
case 25:
//#line 129 "gramatica.y"
{
}
break;
case 26:
<<<<<<< HEAD
//#line 130 "gramatica.y"
=======
//#line 134 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	yyerror("Error: caracteres invalidos, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 27:
<<<<<<< HEAD
//#line 136 "gramatica.y"
=======
//#line 137 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 28:
<<<<<<< HEAD
//#line 139 "gramatica.y"
=======
//#line 143 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	mostrarMensaje("Palabra reservada " + val_peek(4).sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token CADENA, lexema: " + val_peek(2).sval + ", en linea " + compilador.Compilador.nroLinea);
	System.out.println("Salida por pantalla: " + val_peek(2).sval);
}
break;
case 29:
//#line 147 "gramatica.y"
{
	yyerror("Error: Formato de cadena incorrecto, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 30:
//#line 151 "gramatica.y"
{
	mostrarMensaje("Llamada a procedimiento con parametros en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 31:
//#line 155 "gramatica.y"
{
	mostrarMensaje("Llamda a procedimiento sin parametros en linea nro: "+compilador.Compilador.nroLinea);
}
break;
case 32:
//#line 159 "gramatica.y"
{
	yyerror("Error: Cantidad no permitida de parametros, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 33:
//#line 163 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 34:
<<<<<<< HEAD
//#line 165 "gramatica.y"
=======
//#line 169 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	mostrarMensaje("Ciclo FOR en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 35:
<<<<<<< HEAD
//#line 171 "gramatica.y"
=======
//#line 175 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	mostrarMensaje("Palabra reservada " + val_peek(6).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 36:
<<<<<<< HEAD
//#line 177 "gramatica.y"
=======
//#line 181 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	mostrarMensaje("Encabezado de ciclo FOR, en linea nro: "+compilador.Compilador.nroLinea);
}
break;
case 37:
<<<<<<< HEAD
//#line 183 "gramatica.y"
=======
//#line 186 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 38:
<<<<<<< HEAD
//#line 188 "gramatica.y"
=======
//#line 192 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 39:
<<<<<<< HEAD
//#line 194 "gramatica.y"
=======
//#line 195 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 40:
<<<<<<< HEAD
//#line 197 "gramatica.y"
=======
//#line 198 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 41:
<<<<<<< HEAD
//#line 200 "gramatica.y"
=======
//#line 203 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 42:
//#line 207 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 43:
//#line 211 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 44:
<<<<<<< HEAD
//#line 213 "gramatica.y"
=======
//#line 217 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	yyerror("Error: incremento/decremento mal escrito, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 45:
<<<<<<< HEAD
//#line 219 "gramatica.y"
=======
//#line 220 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 46:
<<<<<<< HEAD
//#line 222 "gramatica.y"
=======
//#line 225 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 47:
//#line 229 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 48:
<<<<<<< HEAD
//#line 231 "gramatica.y"
=======
//#line 235 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 49:
<<<<<<< HEAD
//#line 237 "gramatica.y"
=======
//#line 240 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	mostrarMensaje("IF con ELSE, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 50:
<<<<<<< HEAD
//#line 242 "gramatica.y"
=======
//#line 245 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	mostrarMensaje("IF con ELSE, con sentencia unica en rama verdadera, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(3).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 51:
<<<<<<< HEAD
//#line 247 "gramatica.y"
=======
//#line 250 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	mostrarMensaje("IF con ELSE, con sentencia unica en rama falsa, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 52:
<<<<<<< HEAD
//#line 252 "gramatica.y"
{
	mostrarMensaje("IF con ELSE, con sentencia unica en ambas ramas, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 53:
//#line 259 "gramatica.y"
{	
}
break;
case 54:
//#line 264 "gramatica.y"
=======
//#line 257 "gramatica.y"
{	
}
break;
case 53:
//#line 262 "gramatica.y"
{
}
break;
case 54:
//#line 267 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 55:
<<<<<<< HEAD
//#line 269 "gramatica.y"
=======
//#line 272 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 56:
//#line 276 "gramatica.y"
{
	mostrarMensaje("IF sin ELSE, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 57:
<<<<<<< HEAD
//#line 278 "gramatica.y"
=======
//#line 282 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	mostrarMensaje("IF sin ELSE, con sentencia unica, en linea nro: " + compilador.Compilador.nroLinea);
}
break;
case 58:
//#line 286 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 59:
<<<<<<< HEAD
//#line 288 "gramatica.y"
=======
//#line 292 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	yyerror("Error: identificador mal escrito, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 60:
//#line 296 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 61:
<<<<<<< HEAD
//#line 298 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 62:
//#line 302 "gramatica.y"
{	
=======
//#line 300 "gramatica.y"
{	
}
break;
case 62:
//#line 305 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
}
break;
case 63:
//#line 309 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 64:
//#line 313 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(1).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 65:
<<<<<<< HEAD
//#line 315 "gramatica.y"
=======
//#line 318 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 66:
<<<<<<< HEAD
//#line 320 "gramatica.y"
=======
//#line 321 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 67:
<<<<<<< HEAD
//#line 323 "gramatica.y"
=======
//#line 326 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 68:
//#line 330 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 69:
//#line 334 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 70:
//#line 338 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 71:
//#line 342 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 72:
//#line 346 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 73:
//#line 350 "gramatica.y"
{
	mostrarMensaje("Token " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 74:
<<<<<<< HEAD
//#line 352 "gramatica.y"
=======
//#line 356 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	yyerror("Error: comparador no permitido, en linea nro: "+ compilador.Compilador.nroLinea);
}
break;
case 75:
//#line 360 "gramatica.y"
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 76:
<<<<<<< HEAD
//#line 362 "gramatica.y"
=======
//#line 366 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	mostrarMensaje("Palabra reservada " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 77:
<<<<<<< HEAD
//#line 368 "gramatica.y"
=======
//#line 373 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
	mostrarMensaje("Token ID, lexema: " + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 78:
<<<<<<< HEAD
//#line 375 "gramatica.y"
=======
//#line 376 "gramatica.y"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
{
}
break;
case 79:
<<<<<<< HEAD
//#line 378 "gramatica.y"
{
=======
//#line 381 "gramatica.y"
{
	mostrarMensaje("Token: CTE, lexema: "+ val_peek(0).ival + ", en linea " + compilador.Compilador.nroLinea);
	comprobarRango(val_peek(0).sval,false);
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
}
break;
case 80:
//#line 386 "gramatica.y"
{
	mostrarMensaje("Token: CTE, lexema: "+ val_peek(0).ival + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 81:
<<<<<<< HEAD
//#line 387 "gramatica.y"
{
	yyerror("Error: constante positiva mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);
=======
//#line 392 "gramatica.y"
{
	mostrarMensaje("Token: CTE, lexema: -" + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
	comprobarRango(val_peek(0).sval,true);
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
}
break;
case 82:
//#line 397 "gramatica.y"
{
	mostrarMensaje("Token: CTE, lexema: -" + val_peek(0).sval + ", en linea " + compilador.Compilador.nroLinea);
}
break;
case 83:
//#line 397 "gramatica.y"
{
	yyerror("Error: constante negativa mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);	
}
break;
<<<<<<< HEAD
//#line 1065 "Parser.java"
=======
//#line 1124 "Parser.java"
>>>>>>> 384b1bd24415329ddf6a0f51e8879c8dee00cbc6
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
