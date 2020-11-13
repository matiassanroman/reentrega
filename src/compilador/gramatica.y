%{
package compilador;
import java.io.IOException;
import java.util.ArrayList;
import accionesSemanticas.AS10_Verificar_Rango_Float;
import accionesSemanticas.AS9_Verificar_Rango_Constante;
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
		 | error
{
	yyerror("Programa invalido, error en linea: " + compilador.Compilador.nroLinea);
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

encabezadoProc : | PROC identificador '(' ')' NA '=' CTE ',' NS '=' CTE
{
	mostrarMensaje("Procedimiento sin parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $5.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $6.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $9.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $10.sval + ", en linea " + compilador.Compilador.nroLinea);
}
				 | PROC identificador '(' tipo identificador ')' NA '=' CTE ',' NS '=' CTE
{
	mostrarMensaje("Procedimiento con parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $7.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $8.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $11.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $12.sval + ", en linea " + compilador.Compilador.nroLinea);
}
				 | PROC identificador '(' tipo identificador ',' tipo identificador ')' NA '=' CTE ',' NS '=' CTE
{
	mostrarMensaje("Procedimiento con parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $10.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $11.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $14.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $15.sval + ", en linea " + compilador.Compilador.nroLinea);
}
				 |PROC identificador '(' tipo identificador ',' tipo identificador ',' tipo identificador ')' NA '=' CTE ',' NS '=' CTE
{
	mostrarMensaje("Procedimiento con parametros en linea nro: "+compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $1.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $13.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $14.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Palabra reservada " + $17.sval + ", en linea " + compilador.Compilador.nroLinea);
	mostrarMensaje("Token " + $18.sval + ", en linea " + compilador.Compilador.nroLinea);
}		  
			     | PROC identificador '(' tipo identificador ',' tipo identificador ',' tipo identificador error ')' NA '=' CTE ',' NS '=' CTE
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

bloqueProc : '{' bloque '}'
{
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
	   | error
{
	yyerror("Error: no puede haber un seccion vacia, en linea nro: "+ compilador.Compilador.nroLinea);
}
       ;

sentenciaEjecutable : asignacion ';'
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

asignacion : identificador '=' expresion
{
	mostrarMensaje("Token " + $2.sval + ", en linea " + compilador.Compilador.nroLinea);
}
		   | error '=' expresion
{
	yyerror("Error: identificador mal escrito, en linea nro: "+ compilador.Compilador.nroLinea);
}
		   ;

expresion : expresion '+' termino
{
}
		  | expresion '-' termino
{
}
		  | termino
{	
}             
		  ;

termino : termino '*' factor
{
}
		| termino '/' factor
{
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
	mostrarMensaje("Token: CTE, en linea " + compilador.Compilador.nroLinea);
	comprobarRango($1.sval,false);
}
			//| error 
{
	//yyerror("Error: constante positiva mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);
}
			;

cteNegativa : '-' CTE 
{
	mostrarMensaje("Token: CTE, en linea " + compilador.Compilador.nroLinea);
	comprobarRango($2.sval,true);
}         
			//| '-' error
{
	//yyerror("Error: constante negativa mal escrita, en linea nro: "+ compilador.Compilador.nroLinea);	
}
		  ;

%%

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