package accionesSemanticas;

import java.util.HashMap;
import java.util.Hashtable;

import compilador.Simbolo;

public class AS9_Verificar_Rango_Constante extends AccionSemantica{
	
	Hashtable<String,Simbolo> TablaSimbolo;
	HashMap<String,Integer> TablaToken;  
	Simbolo s;

	// se definen rango mminimo y maximo conrrespondientes
	// al valor de las constantes
	static int minValorCte     = -32768;
	static int maxValorCte     =  32767;
	
	// Constructor
	public AS9_Verificar_Rango_Constante(Hashtable<String,Simbolo> TablaSimbolo, HashMap<String,Integer> TablaToken){
		this.TablaToken = TablaToken;
		this.TablaSimbolo = TablaSimbolo;			
	}
	
	
	public int execute(StringBuffer buffer, char c) {
		if(buffer.toString().contains("_") && buffer.toString().contains("i")) {
			this.s = new Simbolo(buffer.toString().substring(0, buffer.toString().length()-2)); 
			s.setTipo("int");
			System.out.println("ENTROO: " + s.getValor());
			// Si la cte ya está en la TS, retornar referencia
			if(TablaSimbolo.contains(this.s) )
				return TablaToken.get("CTE");
			// Si la cte no está en la TS, agregarla y retornarla
			else{                                			
				TablaSimbolo.put(s.getValor(),s);
				return TablaToken.get("CTE");
			}
		}
		else {
			System.out.println("Error: constante entera mal escrita, en linea nro: " + compilador.Compilador.nroLinea);
			buffer.delete(0, buffer.length());
			return 0;
		}
	}

	public static boolean estaEnRango(String cte) {
		int aux = Integer.parseInt(cte.toString().substring(0, cte.toString().length()-2));
		if((aux>=minValorCte) && (aux<=maxValorCte)) 	 
			return true;
		else
			return false;
	}
	
	// Al leer simbolo se puede leer uno que no corresponda a la 
	// constante, con lo cual hay que acomodar la lines, 
	// entonces retorna true
	public boolean acomodarLinea() {
		return true;
	}

}
