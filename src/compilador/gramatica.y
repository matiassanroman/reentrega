%{
package compilador;
import java.io.IOException;
import java.util.ArrayList;
%}

%token ID IF THEN ELSE END_IF OUT FUNC RETURN FOR INTEGER FLOAT PROC NS NA CADENA UP DOWN CTE
%token '<=' '>=' '!=' '==' 

%left '+' '-'
%left '*' '/'

%%
programa : bloquePrograma
{
	mostrarMensaje("Reconoce bien el programa");
}
		 ;

bloquePrograma : bloquePrograma sentenciaDeclarativa
{
}
			   | bloquePrograma sentenciaEjecutable
{
}
			   | sentenciaDeclarativa
{
}
               | sentenciaEjecutable
{
}
               ;

sentenciaDeclarativa : tipo listaVariables ';'
{
	mostrarMensaje("Declaracion de una o mas variables en linea nro: " + compilador.Compilador.nroLinea);
}
					 | declaracionProcedimiento
{
}	
					 ;

listaVariables : listaVariables ',' identificador
{
}
			   | identificador
{
}
			   ;

declaracionProcedimiento : encabezadoProc bloqueProc
{
	mostrarMensaje("Procedimiento completo, en linea nro: " + compilador.Compilador.nroLinea);
}
						 ;

encabezadoProc : PROC identificador '(' parametrosProc ')' NA '=' CTE ',' NS '=' CTE
{
	mostrarMensaje("Procedimiento con parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $6.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $7.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $9.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $10.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $11.sval + ", en linea " + compilador.Compilador.nroLinea);
}
			   | PROC identificador '(' ')' NA '=' CTE ',' NS '=' CTE
{
	mostrarMensaje("Procedimiento sin parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $5.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $6.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $8.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $9.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $10.sval + ", en linea " + compilador.Compilador.nroLinea);
}
			   | PROC identificador '(' error ')' NA '=' CTE ',' NS '=' CTE
{
	yyerror("Error: Cantidad no permitida de parametros, en linea nro: " + compilador.Compilador.nroLinea);
}
			   ; 

nombres : identificador
{
}
		| identificador ',' identificador
{
}
		| identificador ',' identificador ',' identificador
{
}
		;

parametrosProc : parametro
{
}
			   | parametro ',' parametro
{
}
			   | parametro ',' parametro ',' parametro
{
}
			   ;

parametro : tipo identificador
{
	mostrarMensaje("Parametro, en linea nro: " + compilador.Compilador.nroLinea);
}
		  ;

bloqueProc : '{' bloque '}'
{
	mostrarMensaje("Bloque de procedimiento en linea nro: " + compilador.Compilador.nroLinea);
}
		   ;

bloque : bloque sentenciaDeclarativa
{
}
	   | bloque sentenciaEjecutable
{
}
	   | sentenciaDeclarativa
{
}
       | sentenciaEjecutable
{
}
       ;

sentenciaEjecutable : asignacion
{
}
					| OUT '(' CADENA ')' ';'
{
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token CADENA, lexema: " + $3.sval + ", en linea " + compilador.Compilador.nroLinea);
	System.out.println("Salida por pantalla: " + $3.sval);
}                        
					| OUT '(' error ')' ';'
{
	yyerror("Error: Formato de cadena incorrecto, en linea nro: "+ compilador.Compilador.nroLinea);
}
					| identificador '(' nombres ')' ';'
{
	mostrarMensaje("Llamada a procedimiento con parametros en linea nro: " + compilador.Compilador.nroLinea);
}
					| identificador '(' ')' ';'
{
	mostrarMensaje("Llamda a procedimiento sin parametros en linea nro: "+compilador.Compilador.nroLinea);
}
					| identificador '(' error ')' ';'
{
	yyerror("Error: Cantidad no permitida de parametros, en linea nro: "+ compilador.Compilador.nroLinea);
}
					| IF cuerpoIf
{
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}
					| cicloFor
{
	mostrarMensaje("Ciclo FOR en linea nro: " + compilador.Compilador.nroLinea);
}
					;

cicloFor : FOR '(' condicionFor ')' '{' bloqueSentencia '}'
{
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}
         ;

condicionFor : inicioFor ';' condiFOR ';' incDec
{
	mostrarMensaje("Encabezado de ciclo FOR, en linea nro: "+compilador.Compilador.nroLinea);
}
			 ;

condiFOR : condicion
{
}
		 ;

inicioFor : identificador '=' constante
{
	mostrarMensaje("Token " + $2.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		  ;

condicion : identificador comparador asignacion
{
}
		  | identificador comparador identificador
{
}
		  | identificador comparador constante
{
}
		  ;

incDec : UP constante
{
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}
	   | DOWN constante
{
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}
	   | error constante
{
	yyerror("Error: incremento/decremento mal escrito, en linea nro: "+ compilador.Compilador.nroLinea);
}
	   ;

bloqueSentencia : bloqueSentencia sentenciaEjecutable
{
}
				| sentenciaEjecutable
{
}
				;

cuerpoIf : cuerpoCompleto END_IF
{
	mostrarMensaje("Palabra reservada " + $2.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		 | cuerpoIncompleto END_IF
{
	mostrarMensaje("Palabra reservada " + $2.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		 ;
		 
cuerpoCompleto : '(' condicionIf ')' '{' bloqueThen '}' ELSE '{' bloqueElse '}'
{
	mostrarMensaje("IF con ELSE, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $7.sval + ", en linea " + compilador.Compilador.nroLinea);
}			 
			   | '(' condicionIf ')' sentenciaEjecutable ELSE '{' bloqueElse '}' 
{
	mostrarMensaje("IF con ELSE, con sentencia unica en rama verdadera, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $5.sval + ", en linea " + compilador.Compilador.nroLinea);
}
			   | '(' condicionIf ')' '{' bloqueThen '}' ELSE sentenciaEjecutable
{
	mostrarMensaje("IF con ELSE, con sentencia unica en rama falsa, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $7.sval + ", en linea " + compilador.Compilador.nroLinea);
}
			   | '(' condicionIf ')' sentenciaEjecutable ELSE sentenciaEjecutable
{
	mostrarMensaje("IF con ELSE, con sentencia unica en ambas ramas, en linea nro: " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $5.sval + ", en linea " + compilador.Compilador.nroLinea);
}
			   ;  

condicionIf : condicion
{	
}
			;

bloqueThen : bloqueSentencia
{
}
		   ;

bloqueElse : bloqueSentencia
{
}
		   ;

cuerpoIncompleto : '(' condicionIf ')' '{' bloqueThen '}'
{
	mostrarMensaje("IF sin ELSE, en linea nro: " + compilador.Compilador.nroLinea);
}
				 | '(' condicionIf ')' sentenciaEjecutable
{
	mostrarMensaje("IF sin ELSE, con sentencia unica, en linea nro: " + compilador.Compilador.nroLinea);
}
				 ;

asignacion : identificador '=' expresion ';'
{
	mostrarMensaje("Token " + $2.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		   | error '=' expresion ';'
{
	yyerror("Error: identificador mal escrito, en linea nro: "+ compilador.Compilador.nroLinea);
}
		   ;

expresion : expresion '+' termino
{
	mostrarMensaje("Token " + $2.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		  | expresion '-' termino
{
	mostrarMensaje("Token " + $2.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		  | termino
{	
}             
		  ;

termino : termino '*' factor
{
	mostrarMensaje("Token " + $2.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		| termino '/' factor
{
	mostrarMensaje("Token " + $2.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		| factor
{
}
		;

factor : constante
{
}
	   | identificador
{
} 
	   ;

comparador : '<='
{
	mostrarMensaje("Token " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		   | '>='
{
	mostrarMensaje("Token " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		   | '!='
{
	mostrarMensaje("Token " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		   | '=='
{
	mostrarMensaje("Token " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		   | '>'
{
	mostrarMensaje("Token " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		   | '<'
{
	mostrarMensaje("Token " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		   | error
{
	yyerror("Error: comparador no permitido, en linea nro: "+ compilador.Compilador.nroLinea);
}
		   ;

tipo : FLOAT 
{
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}
     | INTEGER
{
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}
     ;

identificador : ID
{
	mostrarMensaje("Token ID, lexema: " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
}

			  ;
			  
constante : ctePositiva
{
}
		  | cteNegativa
{
}
		  ;

ctePositiva : CTE 
{
	mostrarMensaje("Token: CTE, lexema: "+ $1.ival + ", en linea " + compilador.Compilador.nroLinea);
}
			| error 
{
	yyerror("Error: constante positiva mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);
}
			;

cteNegativa : '-' CTE 
{
	mostrarMensaje("Token: CTE, lexema: -" + $2.sval + ", en linea " + compilador.Compilador.nroLinea);
}         
			| '-' error
{
	yyerror("Error: constante negativa mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);	
}
		  ;

%%

//////////////////////////////////////////////////// 
//////////////Definiciones propias//////////////////
////////////////////////////////////////////////////

void mostrarMensaje(String mensaje){
	System.out.println(mensaje);
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